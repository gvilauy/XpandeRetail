package org.xpande.retail.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Doc;
import org.compiere.model.*;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.xpande.core.model.MZSocioListaPrecio;
import org.xpande.retail.model.*;
import org.xpande.retail.model.MProductPricing;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Proceso para generar facturas recibidas en una Recepcion de productos en Retail.
 * Se generan en estado Borrador para su posterior verificación.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/4/17.
 */
public class GenerarFacturasRecibidas extends SvrProcess {

    private MInOut mInOut = null;

    @Override
    protected void prepare() {

        this.mInOut = new MInOut(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        try{

            // Obtengo documento a utilizar para generar facturas del proveedor recibidas
            MDocType[] docTypes = MDocType.getOfDocBaseType(getCtx(), Doc.DOCTYPE_APInvoice);
            MDocType docType = docTypes[0];
            if ((docType == null) || (docType.get_ID() <= 0)){
                return "@Error@ " + "No se pudo obtener Documento de Factura a considerar";
            }

            // Instancio modelos necesarios
            MBPartner bp = (MBPartner)mInOut.getC_BPartner();

            Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

            // Obtengo y recorro modelos de facturas recibidas
            List<MZRecepcionProdFact> recepcionProdFacts = MZRecepcionProdFact.getByInOut(getCtx(), mInOut.get_ID(), get_TrxName());
            for (MZRecepcionProdFact recepcionProdFact: recepcionProdFacts){

                Timestamp dateInvoiced = TimeUtil.trunc(recepcionProdFact.getDateDoc(), TimeUtil.TRUNC_DAY);

                // Seteo cabezal de nueva factura
                MInvoice invoice = new MInvoice(mInOut, dateInvoiced);
                invoice.setDateInvoiced(dateInvoiced);
                invoice.setDateAcct(fechaHoy);
                invoice.setC_DocTypeTarget_ID(docType.get_ID());
                invoice.setC_DocType_ID(docType.get_ID());
                invoice.set_ValueOfColumn("DocumentSerie", recepcionProdFact.getDocumentSerie());
                invoice.setDocumentNo(recepcionProdFact.getManualDocumentNo());
                invoice.setC_BPartner_ID(mInOut.getC_BPartner_ID());
                invoice.setC_BPartner_Location_ID(mInOut.getC_BPartner_Location_ID());
                invoice.setC_Currency_ID(recepcionProdFact.getC_Currency_ID());
                invoice.setAD_Org_ID(mInOut.getAD_Org_ID());

                if (bp.getPaymentRulePO() != null){
                    invoice.setPaymentRule(bp.getPaymentRulePO());
                }
                if (bp.getPO_PaymentTerm_ID() > 0){
                    invoice.setC_PaymentTerm_ID(bp.getPO_PaymentTerm_ID());
                }

                // Seteo lista de precios de compra del proveedor segun moneda
                MZSocioListaPrecio socioListaPrecio = MZSocioListaPrecio.getByPartnerCurrency(getCtx(), bp.get_ID(), recepcionProdFact.getC_Currency_ID(), get_TrxName());
                if ((socioListaPrecio == null) || (socioListaPrecio.get_ID() <= 0)){
                    MCurrency currency = (MCurrency) recepcionProdFact.getC_Currency();
                    return "@Error@ " + "No se pudo obtener Lista de Precios de Compra para este Socio de Negocio en Moneda : " + currency.getISO_Code();
                }
                invoice.setM_PriceList_ID(socioListaPrecio.getM_PriceList_ID());

                invoice.saveEx();

                // Seteo lineas de nueva factura según lineas de recepcion
                List<MInOutLine> inOutLines = recepcionProdFact.getInOutLines();
                for (MInOutLine inOutLine: inOutLines){

                    MInvoiceLine invLine = new MInvoiceLine(invoice);

                    invLine.setC_Invoice_ID(invoice.get_ID());
                    invLine.setM_Product_ID(inOutLine.getM_Product_ID());
                    invLine.setC_UOM_ID(inOutLine.getC_UOM_ID());

                    if (inOutLine.get_Value("UPC") != null){
                        invLine.set_ValueOfColumn("UPC", inOutLine.get_Value("UPC"));
                    }
                    if (inOutLine.get_Value("VendorProductNo") != null){
                        invLine.set_ValueOfColumn("VendorProductNo", inOutLine.get_Value("VendorProductNo"));
                    }


                    if (inOutLine.get_Value("QtyEnteredInvoice") != null){
                        invLine.setQtyEntered((BigDecimal) inOutLine.get_Value("QtyEnteredInvoice"));
                        invLine.setQtyInvoiced(invLine.getQtyEntered());
                    }
                    else{
                        invLine.setQtyEntered(Env.ZERO);
                        invLine.setQtyInvoiced(Env.ZERO);
                    }

                    // Impuesto del producto (primero impuesto especial de compra, y si no tiene, entonces el impuesto normal
                    MProduct prod = (MProduct)inOutLine.getM_Product();

                    if (prod.get_ValueAsInt("C_TaxCategory_ID_2") > 0){
                        MTaxCategory taxCat = new MTaxCategory(getCtx(), prod.get_ValueAsInt("C_TaxCategory_ID_2"), null);
                        MTax tax = taxCat.getDefaultTax();
                        if (tax != null){
                            if (tax.get_ID() > 0){
                                invLine.setC_Tax_ID(tax.get_ID());
                            }
                        }
                    }
                    else{
                        if (prod.getC_TaxCategory_ID() > 0){
                            MTaxCategory taxCat = (MTaxCategory)prod.getC_TaxCategory();
                            MTax tax = taxCat.getDefaultTax();
                            if (tax != null){
                                if (tax.get_ID() > 0){
                                    invLine.setC_Tax_ID(tax.get_ID());
                                }
                            }
                        }
                    }

                    // Precios
                    org.xpande.retail.model.MProductPricing productPricing = this.getProductPricing(invLine, invoice);
                    if (productPricing == null){
                        throw new AdempiereException("No se pudo calcular precios y montos para el producto : " + prod.getValue() + " - " + prod.getName());
                    }
                    invLine.setPriceActual(productPricing.getPriceStd());
                    invLine.setPriceList(productPricing.getPriceList());
                    invLine.setPriceLimit(productPricing.getPriceLimit());
                    invLine.setPriceEntered(invLine.getPriceActual());
                    invLine.set_ValueOfColumn("PricePO", invLine.getPriceEntered());
                    invLine.setLineNetAmt();

                    invLine.saveEx();
                }
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return "OK";
    }


    /**
     * 	Get and calculate Product Pricing
     *	@param invoiceLine
     *  @param invoice
     *	@return product pricing
     */
    private MProductPricing getProductPricing (MInvoiceLine invoiceLine, MInvoice invoice)
    {
        MProductPricing productPricing = null;

        try{
            productPricing = new MProductPricing (invoiceLine.getM_Product_ID(), invoice.getC_BPartner_ID(), invoiceLine.getQtyEntered(), false, get_TrxName());
            productPricing.setM_PriceList_ID(invoice.getM_PriceList_ID());
            productPricing.setPriceDate(invoice.getDateInvoiced());

            productPricing.calculatePrice();

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        return productPricing;
    }

}