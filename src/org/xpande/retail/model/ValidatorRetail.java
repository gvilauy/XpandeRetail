package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.lang.math.NumberUtils;
import org.compiere.acct.Doc;
import org.compiere.model.*;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xpande.comercial.model.MZComercialConfig;
import org.xpande.comercial.utils.ComercialUtils;
import org.xpande.core.model.MZActividadDocumento;
import org.xpande.core.model.MZProductoUPC;
import org.xpande.retail.utils.AcctUtils;
import org.xpande.retail.utils.ProductPricesInfo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

/**
 * Model Validator para Retail
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/12/17.
 */
public class ValidatorRetail implements ModelValidator {

    private int adClientID = 0;

    @Override
    public void initialize(ModelValidationEngine engine, MClient client) {

        // Guardo compañia
        if (client != null){
            this.adClientID = client.get_ID();
        }

        // Document Validations
        engine.addDocValidate(I_M_InOut.Table_Name, this);
        engine.addDocValidate(I_C_Invoice.Table_Name, this);
        engine.addDocValidate(I_C_Order.Table_Name, this);

        // DB Validations
        engine.addModelChange(I_C_Order.Table_Name, this);
        engine.addModelChange(I_C_OrderLine.Table_Name, this);
        engine.addModelChange(I_C_Invoice.Table_Name, this);
        engine.addModelChange(I_C_InvoiceLine.Table_Name, this);
        engine.addModelChange(I_M_ProductPrice.Table_Name, this);
        engine.addModelChange(I_M_Product.Table_Name, this);
    }

    @Override
    public int getAD_Client_ID() {
        return this.adClientID;
    }

    @Override
    public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) {
        return null;
    }

    @Override
    public String modelChange(PO po, int type) throws Exception {

        if (po.get_TableName().equalsIgnoreCase(I_C_OrderLine.Table_Name)){
            return modelChange((MOrderLine) po, type);
        }
        else if (po.get_TableName().equalsIgnoreCase(I_C_Order.Table_Name)){
            return modelChange((MOrder) po, type);
        }
        else if (po.get_TableName().equalsIgnoreCase(I_C_Invoice.Table_Name)){
            return modelChange((MInvoice) po, type);
        }
        else if (po.get_TableName().equalsIgnoreCase(I_C_InvoiceLine.Table_Name)){
            return modelChange((MInvoiceLine) po, type);
        }
        else if (po.get_TableName().equalsIgnoreCase(I_M_Product.Table_Name)){
            return modelChange((MProduct) po, type);
        }
        else if (po.get_TableName().equalsIgnoreCase(I_M_ProductPrice.Table_Name)){
            return modelChange((MProductPrice) po, type);
        }

        return null;
    }

    @Override
    public String docValidate(PO po, int timing) {

        if (po.get_TableName().equalsIgnoreCase(I_M_InOut.Table_Name)){
            return docValidate((MInOut) po, timing);
        }
        else if (po.get_TableName().equalsIgnoreCase(I_C_Invoice.Table_Name)){
            return docValidate((MInvoice) po, timing);
        }
        else if (po.get_TableName().equalsIgnoreCase(I_C_Order.Table_Name)){
            return docValidate((MOrder) po, timing);
        }


        return null;
    }


    /***
     * Validaciones para el modelo de Lineas de Ordenes de Compra.
     * Xpande. Created by Gabriel Vila on 6/30/17.
     * @param model
     * @param type
     * @return
     * @throws Exception
     */
    public String modelChange(MOrderLine model, int type) throws Exception {

        String mensaje = null;

        MOrder order = (MOrder)model.getC_Order();
        // No hago nada para ordenes de venta.
        if (order.isSOTrx()) return mensaje;


        if ((type == ModelValidator.TYPE_BEFORE_NEW) || (type == ModelValidator.TYPE_BEFORE_CHANGE)){

            // Debido a la posibilidad de ingresar un descuento manual en la linea de la orden para Retail (campo Discount2),
            // y también debido a la posibilidad de que se haga una orden de compra en una moneda distinta a la moneda de la lista de precios
            // Se agregan estas lineas que recalculas los precios de compra, descuentos y conversión a moneda de compra.
            MProductPricing productPricing = this.getProductPricing(model, order);
            if (productPricing == null){
                throw new AdempiereException("No se pudo calcular precios y montos para esta linea de Orden de Compra");
            }


            // Precisión de la lista de precios
            MPriceList priceList = (MPriceList) order.getM_PriceList();
            int precisionDecimal = priceList.getPricePrecision();

            // Si moneda de orden es distinta a moneda de lista
            if (order.getC_Currency_ID() != priceList.getC_Currency_ID()){
                // Precision decimal de la moneda
                precisionDecimal = ((MCurrency) order.getC_Currency()).getStdPrecision();
            }
            productPricing.setForcedPrecision(precisionDecimal);

            model.setPriceActual(productPricing.getPriceStd());
            model.setPriceList(productPricing.getPriceList());
            model.setPriceLimit(productPricing.getPriceLimit());

            if (model.getQtyEntered().compareTo(model.getQtyOrdered()) == 0)
                model.setPriceEntered(model.getPriceActual());
            else
                model.setPriceEntered(model.getPriceActual().multiply(model.getQtyOrdered()
                        .divide(model.getQtyEntered(), 12, BigDecimal.ROUND_HALF_UP)));	//	recision

            //	Calculate Discount
            model.setDiscount(productPricing.getDiscount());
            //	Set UOM
            if(model.getC_UOM_ID() == 0 ){
                model.setC_UOM_ID(productPricing.getC_UOM_ID());
            }

            // Nuevo registro, dejo fijo el precio OC en una columna para que en caso de haber descuentos manuales, se visualice diferencias
            // entre precio oc original, y el nuevo precio oc con descuentos manuales aplicados.
            if (type == ModelValidator.TYPE_BEFORE_NEW){
                model.set_ValueOfColumn("PricePO", model.getPriceEntered());
            }

            // Calcula descuento manual
            BigDecimal Discount2 = (BigDecimal)model.get_Value("Discount2");
            if (Discount2 == null) Discount2 = Env.ZERO;

            boolean precioConDescuentos = true;
            BigDecimal PriceActual = (BigDecimal) model.get_Value("PricePO");
            if (PriceActual == null){
                precioConDescuentos = false;
                PriceActual = model.getPriceActual();
            }
            if (PriceActual == null) PriceActual = Env.ZERO;

            if (PriceActual.compareTo(Env.ZERO) != 0 ){
                if (!precioConDescuentos){
                    PriceActual = new BigDecimal ((100.0 - model.getDiscount().doubleValue()) / 100.0 * PriceActual.doubleValue());
                }
            }
            PriceActual = new BigDecimal ((100.0 - Discount2.doubleValue()) / 100.0 * PriceActual.doubleValue());
            if (PriceActual.scale() > 2)
                PriceActual = PriceActual.setScale(precisionDecimal, BigDecimal.ROUND_HALF_UP);
            BigDecimal PriceEntered = MUOMConversion.convertProductFrom (model.getCtx(), model.getM_Product_ID(),
                    model.getC_UOM_ID(), PriceActual);
            if (PriceEntered == null)
                PriceEntered = PriceActual;
            model.setPriceActual(PriceActual);
            model.setPriceEntered(PriceEntered);

            if (model.getC_BPartner_Location_ID() <= 0) model.setC_BPartner_Location_ID(order.getC_BPartner_Location_ID());

            // Me aseguro de dejar marcada la linea como no convertida a moneda de compra en caso de bimoneda, ya que se acaba de actualizar precios
            // en moneda de lista
            model.set_ValueOfColumn("IsConverted",false);

            // Para ordenes de compra que tiene moneda de compra != a moneda de la lista,
            // debo llevar todos los montos de esta linea a la moneda de compra.
            if (order.get_ValueAsInt("C_Currency_PriceList_ID") != order.getC_Currency_ID()){
                if (!model.get_ValueAsBoolean("IsConverted")){
                    if (order.get_Value("MultiplyRate") != null){
                        BigDecimal multiplyRate = (BigDecimal) order.get_Value("MultiplyRate");
                        if (multiplyRate.compareTo(Env.ZERO) != 0){

                            // Actualizo montos de esta linea de orden de compra
                            model.setPriceList(model.getPriceList().multiply(multiplyRate).setScale(precisionDecimal, BigDecimal.ROUND_HALF_UP));
                            model.setPriceActual(model.getPriceActual().multiply(multiplyRate).setScale(precisionDecimal, BigDecimal.ROUND_HALF_UP));
                            model.setPriceLimit(model.getPriceLimit().multiply(multiplyRate).setScale(precisionDecimal, BigDecimal.ROUND_HALF_UP));
                            model.setPriceEntered(model.getPriceEntered().multiply(multiplyRate).setScale(precisionDecimal, BigDecimal.ROUND_HALF_UP));
                            model.setLineNetAmt();
                            model.set_ValueOfColumn("IsConverted", true);
                        }
                    }
                }
            }

            // Seteo monto de impuesto de esta linea si aun no tiene valor.
            BigDecimal taxAmt = (BigDecimal) model.get_Value("TaxAmt");
            if (taxAmt == null) taxAmt = Env.ZERO;
            if (taxAmt.compareTo(Env.ZERO) == 0){
                MTax tax = new MTax (model.getCtx(), model.getC_Tax_ID(), null);
                taxAmt = tax.calculateTax(model.getLineNetAmt(), order.isTaxIncluded(), precisionDecimal);
                model.set_ValueOfColumn("TaxAmt", taxAmt);
            }

            // Si no tengo subtotal, lo seteo ahora
            BigDecimal amtSubtotal = (BigDecimal) model.get_Value("AmtSubtotal");
            if (amtSubtotal == null) amtSubtotal = Env.ZERO;
            if (amtSubtotal.compareTo(Env.ZERO) <= 0){
                if (!order.isTaxIncluded()){
                    model.set_ValueOfColumn("AmtSubtotal", model.getLineNetAmt());
                }
                else{
                    model.set_ValueOfColumn("AmtSubtotal", model.getLineNetAmt().subtract(taxAmt));
                }
            }

        }
        else if ((type == ModelValidator.TYPE_AFTER_NEW) || (type == ModelValidator.TYPE_AFTER_CHANGE)
                || (type == ModelValidator.TYPE_AFTER_DELETE)){


            // Cuando modifico linea de orden de compra, me aseguro que se calcule bien el campo del cabezal
            // para subtotal en retail. Esto es porque Adempiere de fábrica, cuando maneja lista de precios con
            // impuestos incluídos, me muestra el total de lineas = grand total en el cabezal de la orden.
            // En retail, se tiene mostrar subtotal = total - impuestos.
            // Para ello no se modifico el comportamiento original de ADempiere y se mantuvo el campo: TotalLines.
            // Pero se agrego campo nuevo para desplegarse con el calculo requerido.

            BigDecimal grandTotal = order.getGrandTotal();
            if ((grandTotal == null) || (grandTotal.compareTo(Env.ZERO) <= 0)){
                order.set_ValueOfColumn("AmtSubtotal", Env.ZERO);
                order.saveEx();
            }
            else{
                // Obtengo suma de impuestos para esta orden
                String sql = " select sum(coalesce(taxamt,0)) as taxamt from c_ordertax where c_order_id =" + order.get_ID();
                BigDecimal sumImpuestos = DB.getSQLValueBDEx(model.get_TrxName(), sql);
                if (sumImpuestos != null){
                    sumImpuestos = sumImpuestos.setScale(2, BigDecimal.ROUND_HALF_UP);
                    order.set_ValueOfColumn("AmtSubtotal", grandTotal.subtract(sumImpuestos));
                    order.saveEx();
                }
            }


        }

        return mensaje;
    }


    /***
     * Validaciones para el modelo de Invoices
     * Xpande. Created by Gabriel Vila on 6/30/17.
     * @param model
     * @param type
     * @return
     * @throws Exception
     */
    public String modelChange(MInvoice model, int type) throws Exception {

        String mensaje = null, action = "";

        if ((type == ModelValidator.TYPE_BEFORE_NEW) || (type == ModelValidator.TYPE_BEFORE_CHANGE)){
            // Para comprobantes de compra en retail, es necesario que el usuario digite un numero de documento,
            // y que el sistema no le asigne un numero automático a pesar que el tipo de documento no tiene secuencia
            // asignada.
            if (!model.isSOTrx()){
                if ((model.getDocumentNo() == null) || (model.getDocumentNo().trim().equalsIgnoreCase(""))){
                    return "Debe indicar el Número de Documento";
                }
            }

        }

        if ((type == ModelValidator.TYPE_AFTER_NEW) || (type == ModelValidator.TYPE_AFTER_CHANGE)){

            // Debo considerar la posibilidad de que el usuario haya ingresado de manera manual un monto de Redondeo para el comprobante.
            // Si es asi, debo reflejarlo en el total del comprobante.
            if ((model.is_ValueChanged("AmtRounding") || (model.is_ValueChanged("AmtSubtotal"))
                || (model.is_ValueChanged("Grandtotal")))){
                BigDecimal amtRounding = (BigDecimal) model.get_Value("AmtRounding");
                if (amtRounding == null) amtRounding = Env.ZERO;

                // Select para monto total de impuestos manuales
                String sql = " select coalesce(sum(taxamt), 0) as total " +
                        " from z_invoicetaxmanual " +
                        " where c_invoice_id =" + model.get_ID();

                action = " update c_invoice set grandtotal = Totallines + (" + amtRounding + ") + (" + sql + ") " +
                        " where c_invoice_id =" + model.get_ID();
                DB.executeUpdateEx(action, model.get_TrxName());
            }

        }

        return mensaje;
    }


    /***
     * Validaciones para el modelo de Orders (c_order)
     * Xpande. Created by Gabriel Vila on 6/30/17.
     * @param model
     * @param type
     * @return
     * @throws Exception
     */
    public String modelChange(MOrder model, int type) throws Exception {

        String mensaje = null;

        if ((type == ModelValidator.TYPE_BEFORE_NEW)
                || ((type == ModelValidator.TYPE_BEFORE_CHANGE) && (model.is_ValueChanged("C_Currency_ID")))){

            // Si es una orden de venta no hago nada
            if (model.isSOTrx()) return mensaje;

            // Para ordenes de compra, si la moneda de lista es distinta a la moneda de orden de compra
            if (model.get_ValueAsInt("C_Currency_PriceList_ID") != model.getC_Currency_ID()){
                // Obtengo tasa de cambio (multiplyRate) entre ambas monedas para fecha del documento.
                int cCurrencyListaID = model.get_ValueAsInt("C_Currency_PriceList_ID");
                BigDecimal multiplyRate = MConversionRate.getRate(cCurrencyListaID, model.getC_Currency_ID(), model.getDateOrdered(), 0, model.getAD_Client_ID(), 0);
                if (multiplyRate == null){
                    multiplyRate = Env.ZERO;
                }
                model.set_ValueOfColumn("MultiplyRate", multiplyRate);
            }
        }

        return mensaje;
    }


    /***
     * Validaciones para el modelo de Lineas de Invoices.
     * Xpande. Created by Gabriel Vila on 6/30/17.
     * @param model
     * @param type
     * @return
     * @throws Exception
     */
    public String modelChange(MInvoiceLine model, int type) throws Exception {

        String mensaje = null;

        if ((type == ModelValidator.TYPE_AFTER_NEW) || (type == ModelValidator.TYPE_AFTER_CHANGE)
                || (type == ModelValidator.TYPE_AFTER_DELETE)){

            MInvoice invoice = (MInvoice)model.getC_Invoice();
            MDocType docType = (MDocType) invoice.getC_DocTypeTarget();

            // Cuando modifico linea de comprobante, me aseguro que se calcule bien el campo del cabezal
            // para subtotal en retail. Esto es porque Adempiere de fábrica, cuando maneja lista de precios con
            // impuestos incluídos, me muestra el total de lineas = grand total en el cabezal del comprobante.
            // En retail, se tiene mostrar subtotal = total - impuestos.
            // Para ello no se modifico el comportamiento original de ADempiere y se mantuvo el campo: TotalLines.
            // Pero se agrego campo nuevo para desplegarse con el calculo requerido.

            BigDecimal grandTotal = invoice.getGrandTotal();
            if ((grandTotal == null) || (grandTotal.compareTo(Env.ZERO) <= 0)){
                invoice.set_ValueOfColumn("AmtSubtotal", Env.ZERO);
                invoice.saveEx();
            }
            else{
                // Obtengo suma de impuestos para esta invoice
                String sql = " select sum(coalesce(taxamt,0)) as taxamt from c_invoicetax where c_invoice_id =" + invoice.get_ID();
                BigDecimal sumImpuestos = DB.getSQLValueBDEx(model.get_TrxName(), sql);
                if (sumImpuestos == null){
                    sumImpuestos = Env.ZERO;
                }
                else{
                    sumImpuestos = sumImpuestos.setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                invoice.set_ValueOfColumn("AmtSubtotal", grandTotal.subtract(sumImpuestos));
                invoice.saveEx();
            }

            if ((type == ModelValidator.TYPE_AFTER_NEW) || ((type == ModelValidator.TYPE_AFTER_CHANGE) && model.is_ValueChanged(X_C_InvoiceLine.COLUMNNAME_QtyInvoiced))){

                // Para comprobantes de compra del tipo factura (API), verifico si se tienen pautadas bonificaciones para socio-producto-pauta comercial.
                // En caso de tenerlas, genero las lineas de bonificaciones pertinentes en el comprobante asociadas a esta linea de factura.
                // Eso aplica solo para productos del tipo producto y no para servicios, etc.
                if ((!invoice.isSOTrx()) && (docType.getDocBaseType().equalsIgnoreCase("API"))){
                    MProduct product = (MProduct) model.getM_Product();
                    if ((product != null) && (product.get_ID() > 0)){
                        if (product.getProductType().equalsIgnoreCase(X_M_Product.PRODUCTTYPE_Item)){
                            this.setBonificacionesLinea(model.getCtx(), invoice, model, model.get_TrxName());
                        }
                    }
                }
            }

        }

        else if ((type == ModelValidator.TYPE_BEFORE_NEW) || (type == ModelValidator.TYPE_BEFORE_CHANGE)
                || (type == ModelValidator.TYPE_BEFORE_DELETE)){

            MInvoice invoice = (MInvoice)model.getC_Invoice();
            MDocType docType = (MDocType) invoice.getC_DocTypeTarget();

            // Cuando estoy en comprobantes de compra
            if (type == ModelValidator.TYPE_BEFORE_NEW){
                if (!invoice.isSOTrx()){
                    if (model.get_Value("PricePO") == null){
                        MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(model.getCtx(), invoice.getC_BPartner_ID(), model.getM_Product_ID(), null);
                        if ((productoSocio != null) && (productoSocio.get_ID() > 0)){
                            MZProductoSocioOrg productoSocioOrg = productoSocio.getOrg(invoice.getAD_Org_ID());
                            if ((productoSocioOrg != null) && (productoSocioOrg.get_ID() > 0)){
                                model.set_ValueOfColumn("PricePO", productoSocioOrg.getPricePO());
                                model.set_ValueOfColumn("PricePONoDto", productoSocioOrg.getPricePO());
                            }
                            else{
                                model.set_ValueOfColumn("PricePO", productoSocio.getPricePO());
                                model.set_ValueOfColumn("PricePONoDto", productoSocioOrg.getPricePO());
                            }
                        }
                    }
                }
            }

            if ((type == ModelValidator.TYPE_BEFORE_NEW) || (type == ModelValidator.TYPE_BEFORE_CHANGE)){
                if (!invoice.isSOTrx()){
                    if ((model.is_ValueChanged("Discount1")) || (model.is_ValueChanged("Discount2"))
                            || (model.is_ValueChanged("Discount3"))){

                        int StdPrecision = MPriceList.getPricePrecision(model.getCtx(), invoice.getM_PriceList_ID());

                        BigDecimal discount1, discount2, discount3, pricePO, pricePONoDto, priceActual, priceEntered;
                        discount1 = (BigDecimal) model.get_Value("Discount1");
                        discount2 = (BigDecimal) model.get_Value("Discount2");
                        discount3 = (BigDecimal) model.get_Value("Discount3");
                        pricePONoDto = (BigDecimal) model.get_Value("PricePONoDto");

                        if (discount1 == null) discount1 = Env.ZERO;
                        if (discount2 == null) discount2 = Env.ZERO;
                        if (discount3 == null) discount3 = Env.ZERO;

                        if (pricePONoDto != null) {
                            if (pricePONoDto.compareTo(Env.ZERO) != 0) {
                                pricePO = new BigDecimal((100.0 - discount1.doubleValue()) / 100.0 * pricePONoDto.doubleValue());
                                pricePO = new BigDecimal((100.0 - discount2.doubleValue()) / 100.0 * pricePO.doubleValue());
                                pricePO = new BigDecimal((100.0 - discount3.doubleValue()) / 100.0 * pricePO.doubleValue());
                                if (pricePO.scale() > StdPrecision){
                                    pricePO = pricePO.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
                                }
                                priceActual = pricePO;
                                priceEntered = MUOMConversion.convertProductFrom(model.getCtx(), model.getM_Product_ID(), model.getC_UOM_ID(), priceActual);
                                if (priceEntered == null) priceEntered = priceActual;
                                model.set_ValueOfColumn("PricePO", pricePO);
                                model.setPriceActual(priceActual);
                                model.setPriceEntered(priceEntered);
                                model.setLineNetAmt();
                                model.setTaxAmt();
                            }
                        }
                    }
                }
                else{
                    // Para facturas de venta, en caso de facturación entre organizaciones, se debe tomar precio de ultima factura de proveedor
                    // Instancio modelo de socio de negocio para verificar si esta linkeado con una organización de este compañia.
                    if (model.getM_Product_ID() > 0){
                        MBPartner partner = (MBPartner) invoice.getC_BPartner();
                        if (partner.getAD_OrgBP_ID_Int() > 0){
                            MOrg orgLinked = new MOrg(model.getCtx(), partner.getAD_OrgBP_ID_Int(), null);
                            if ((orgLinked != null) && (orgLinked.get_ID() > 0)){

                                // Obtengo precio de ultima factura de compra (API), para producto y organización de este comprobante de venta.
                                BigDecimal priceActual = ComercialUtils.getProdOrgLastAPInvoicePrice(model.getCtx(), model.getM_Product_ID(), invoice.getAD_Org_ID(), null);

                                // Si no tengo facturas de proveedor para este articulo, busco precio OC del ultimo proveedor para esta organización
                                if ((priceActual == null) || (priceActual.compareTo(Env.ZERO) <= 0)){
                                    // Instancio modelo de producto-socio con ultima vigencia de precio OC
                                    MZProductoSocio productoSocio = MZProductoSocio.getByLastPriceOC(model.getCtx(), model.getM_Product_ID(), null);

                                    // Si tengo producto soccio, obtengo precio OC para esta organizacion
                                    if ((productoSocio != null) && (productoSocio.get_ID() > 0)){
                                        MZProductoSocioOrg socioOrg = productoSocio.getOrg(invoice.getAD_Org_ID());
                                        if ((socioOrg != null) && (socioOrg.get_ID() > 0)){
                                            priceActual = socioOrg.getPricePO();
                                        }
                                        else{
                                            priceActual = productoSocio.getPricePO();
                                        }
                                    }
                                }

                                if (priceActual == null) priceActual = Env.ZERO;
                                BigDecimal priceEntered = MUOMConversion.convertProductFrom(model.getCtx(), model.getM_Product_ID(), model.getC_UOM_ID(), priceActual);
                                if (priceEntered == null) priceEntered = priceActual;
                                model.setPriceActual(priceActual);
                                model.setPriceEntered(priceEntered);
                                model.setLineNetAmt();
                                model.setTaxAmt();
                            }
                        }
                    }
                }
            }

            // Siguiendo el mismo concepto que el cabezal, se actualiza subtotal de esta linea.
            // Nuevo campo de subtotal, no se toca el original de ADempiere.
            BigDecimal lineTotal = model.getLineTotalAmt();
            if (lineTotal != null){
                BigDecimal lineTaxAmt = model.getTaxAmt();
                if (lineTaxAmt != null){
                    model.set_ValueOfColumn("AmtSubtotal", lineTotal.subtract(lineTaxAmt));
                }
                else{
                    model.set_ValueOfColumn("AmtSubtotal", lineTotal);
                }
            }
        }

        return mensaje;
    }

    /***
     * Para una linea de factura, verifico y obtengo descuentos en bonificaciones de unidades.
     * Si tiene alguna bonificación pautada, genero las lineas de bonificación en el comprobante.
     * Xpande. Created by Gabriel Vila on 4/25/18.
     * @param ctx
     * @param invoice
     * @param invoiceLine
     * @param trxName
     */
    private void setBonificacionesLinea(Properties ctx, MInvoice invoice, MInvoiceLine invoiceLine, String trxName) {

        try{

            // Me aseguro de eliminar bonificaciones no manuales asociadas a la linea de factura recibida, antes de volver a calcular.
            String action = " delete from z_invoicebonifica where c_invoiceline_id =" + invoiceLine.get_ID() + " and ismanual='N'";
            DB.executeUpdateEx(action, trxName);

            // Obtengo modelo de asociación de producto-socio de negocio
            MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(ctx, invoice.getC_BPartner_ID(), invoiceLine.getM_Product_ID(), trxName);
            if ((productoSocio != null) && (productoSocio.get_ID() > 0)){

                // Si existe pauta comercial para este producto-socio de negocio
                if (productoSocio.getZ_PautaComercial_ID() > 0){
                    MZPautaComercial pautaComercial = (MZPautaComercial) productoSocio.getZ_PautaComercial();

                    // Obtengo lista de descuentos por concepto de bonificación en unidades
                    List<MZPautaComercialSetDto> dtosList = pautaComercial.getSetsDtosXBonificacion(invoiceLine.getM_Product_ID());
                    for (MZPautaComercialSetDto setDto: dtosList){
                        // Si esta bonificacion tiene seteado una cantidad a bonificar
                        if ((setDto.getQtyReward() != null) && (setDto.getQtyReward().compareTo(Env.ZERO) > 0)){
                            // Si esta bonificación tiene seteado una cantidad de corte
                            if ((setDto.getBreakValue() != null) && (setDto.getBreakValue().compareTo(Env.ZERO) > 0)){
                                // Calculo la cantidad que tengo que bonificar segun la cantidad de la linea de la factura y la cantidad de corte
                                if (invoiceLine.getQtyInvoiced().compareTo(setDto.getBreakValue()) >= 0){
                                    long intPart = (invoiceLine.getQtyInvoiced().divide(setDto.getBreakValue(), 2, RoundingMode.HALF_UP)).longValue();
                                    BigDecimal qtyBonifica = new BigDecimal(intPart).multiply(setDto.getQtyReward()).setScale(2, RoundingMode.HALF_UP);

                                    // Genero linea de bonificación en factura
                                    MZInvoiceBonifica bonifica = new MZInvoiceBonifica(ctx, 0, trxName);
                                    bonifica.set_ValueOfColumn("AD_Client_ID", invoice.getAD_Client_ID());
                                    bonifica.setAD_Org_ID(invoice.getAD_Org_ID());
                                    bonifica.setZ_PautaComercialSet_ID(setDto.getZ_PautaComercialSet_ID());
                                    bonifica.setC_Invoice_ID(invoice.get_ID());
                                    bonifica.setC_InvoiceLine_ID(invoiceLine.get_ID());
                                    bonifica.setIsManual(false);
                                    bonifica.setM_Product_ID(invoiceLine.getM_Product_ID());
                                    bonifica.setQtyBase(invoiceLine.getQtyInvoiced());
                                    bonifica.setQtyCalculated(qtyBonifica);
                                    bonifica.setQtyReward(qtyBonifica);
                                    bonifica.setTipoBonificaQty(setDto.getTipoBonificaQty());
                                    bonifica.setLine(invoiceLine.getLine());
                                    bonifica.saveEx();
                                }
                            }
                        }

                    }
                }
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }


    /**
     * 	Get and calculate Product Pricing
     *	@param orderLine
     *	@return product pricing
     */
    private MProductPricing getProductPricing (MOrderLine orderLine, MOrder order) {
        MProductPricing productPricing = null;

        try{
            productPricing = new MProductPricing (orderLine.getM_Product_ID(), order.getC_BPartner_ID(), order.getAD_Org_ID(),
                    order.getDateOrdered(), orderLine.getQtyOrdered(), false, null);
            productPricing.setM_PriceList_ID(order.getM_PriceList_ID());
            productPricing.setPriceDate(order.getDateOrdered());

            productPricing.calculatePrice();

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        return productPricing;
    }


    /***
     * Validaciones para documentos de la tabla M_InOut en Retail
     * Xpande. Created by Gabriel Vila on 7/4/17.
     * @param model
     * @param timing
     * @return
     */
    private String docValidate(MInOut model, int timing) {

        String message = null;
        String action = "";

        if (timing == TIMING_BEFORE_COMPLETE){

            // En recepciones de productos
            if (model.getMovementType().equalsIgnoreCase(X_M_InOut.MOVEMENTTYPE_VendorReceipts)){

                // Obtengo y recorro lineas
                MInOutLine[] mInOutLines = model.getLines();
                for (int i = 0; i < mInOutLines.length; i++){
                    MInOutLine mInOutLine = mInOutLines[i];

                    // Asocio posibles nuevos códigos de barra a los productos del socio de negocio
                    if (mInOutLine.get_Value("UPC") != null){
                        String upc = mInOutLine.get_ValueAsString("UPC").toString().trim();
                        if (!upc.equalsIgnoreCase("")){
                            MZProductoUPC pupc = MZProductoUPC.getByUPC(model.getCtx(), upc, model.get_TrxName());
                            if ((pupc == null) || (pupc.get_ID() <= 0)){
                                // Asocio nuevo UPC a este producto
                                pupc = new MZProductoUPC(model.getCtx(), 0, model.get_TrxName());
                                pupc.setUPC(upc);
                                pupc.setM_Product_ID(mInOutLine.getM_Product_ID());
                                pupc.saveEx();
                            }
                            else{
                                if (pupc.getM_Product_ID() != mInOutLine.getM_Product_ID()){
                                    MProduct prod = (MProduct)pupc.getM_Product();
                                    return "El Código de Barras ingresado (" + upc + ") esta asociado a otro Producto : " + prod.getValue() + " - " + prod.getName();
                                }
                            }
                        }
                    }

                    // Asocio posible nuevo codigo de producto del proveedor al producto
                    if (mInOutLine.get_Value("VendorProductNo") != null){
                        String vendorProductNo = mInOutLine.get_Value("VendorProductNo").toString().trim();
                        if (!vendorProductNo.equalsIgnoreCase("")){
                            MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(model.getCtx(), model.getC_BPartner_ID(), mInOutLine.getM_Product_ID(), model.get_TrxName());
                            if ((productoSocio != null) && (productoSocio.get_ID() > 0)){

                                if ((vendorProductNo != null) && (!vendorProductNo.trim().equalsIgnoreCase(""))){
                                    productoSocio.setVendorProductNo(vendorProductNo);
                                    productoSocio.saveEx();
                                }
                            }
                            else{
                                MProduct prod = (MProduct) mInOutLine.getM_Product();
                                return "No se pudo obtener información de producto-socio para el producto : " + prod.getValue() + " - " + prod.getName();
                            }
                        }
                    }

                    // Si tengo diferencia entre cantidad facturada y recibida (cuanto me facturan mas de lo que me entregan), lo seteo aca.
                    if (mInOutLine.get_Value("QtyEnteredInvoice") != null){
                        BigDecimal qtyInvoiced = (BigDecimal) mInOutLine.get_Value("QtyEnteredInvoice");
                        if (qtyInvoiced.compareTo(mInOutLine.getQtyEntered()) > 0){
                            action = " update m_inoutline set DifferenceQty =" + qtyInvoiced.subtract(mInOutLine.getQtyEntered()) +
                                    " where m_inoutline_id =" + mInOutLine.get_ID();
                        }
                        else{
                            action = " update m_inoutline set DifferenceQty = null " +
                                    " where m_inoutline_id =" + mInOutLine.get_ID();
                        }
                        DB.executeUpdateEx(action, model.get_TrxName());
                    }
                }
            }

            // Devoluciones de proveedores.
            else if (model.getMovementType().equalsIgnoreCase(X_M_InOut.MOVEMENTTYPE_VendorReturns)){

                // Valido que la devolución tenga la marca de confirmada antes de completarla
                if (!model.get_ValueAsBoolean("IsConfirmed")){
                    return "Debe confirmar la Devolución antes de Completarla.";
                }

                // Obtengo y recorro lineas
                MInOutLine[] mInOutLines = model.getLines();
                for (int i = 0; i < mInOutLines.length; i++){
                    MInOutLine mInOutLine = mInOutLines[i];

                    // Valido que este linea de devolución tenga seteado el destino.
                    String destino = (String) mInOutLine.get_Value("DestinoDevol");
                    if ((destino == null) || (destino.trim().equalsIgnoreCase(""))){
                        MProduct product = (MProduct) mInOutLine.getM_Product();
                        return "Falta indicar Destino de Devolución para el Producto : " + product.getValue() + " - " + product.getName();
                    }

                    // Valido cantidad confirmada no nula y que no sea nunca superior a la cantidad original a devolver.

                }
            }
        }
        else if (timing == TIMING_BEFORE_REACTIVATE){

            // En recepciones de productos
            if (model.getMovementType().equalsIgnoreCase(X_M_InOut.MOVEMENTTYPE_VendorReceipts)){

                // Valido que esta recepcion no tenga facturas asociadas que esten completas o cerradas.
                // Obtengo lista de facturas que pueden estar asociadas
                List<MInvoice> invoiceList = ComercialUtils.getInvoicesByInOut(model.getCtx(), model.get_ID(), true, model.get_TrxName());
                if (invoiceList.size() > 0){
                    message = "No se puede reactivar este documento ya que tiene las siguientes facturas completas asociadas : ";
                    for (MInvoice invoice: invoiceList){
                        message += invoice.getDocumentNo() + ", ";
                    }
                    message += " debe reactivarlas para poder continuar.";
                    return message;
                }

                // Para cada linea de esta inout, dejo seteada en null la cantidad diferencia entre recibida y facturada.
                action = " update m_inoutline set DifferenceQty = null where m_inout_id =" + model.get_ID();
                DB.executeUpdateEx(action, model.get_TrxName());
                
                // Cuando reactivo una recepción de proveedor, me aseguro de eliminar posibles documentos de Remitos por Cantidad que
                // puedan estar asociados a dicha recepción.
                action = " delete from z_remitodifinv where m_inout_id =" + model.get_ID() +
                        " and c_doctype_id in (select c_doctype_id from c_doctype where docbasetype ='RDC')";
                DB.executeUpdateEx(action, model.get_TrxName());

            }
        }

        return null;
    }

    /***
     * Validaciones para el modelo de Productos
     * Xpande. Created by Gabriel Vila on 6/30/17.
     * @param model
     * @param type
     * @return
     * @throws Exception
     */
    public String modelChange(MProduct model, int type) throws Exception {

        String mensaje = null;

        if ((type == ModelValidator.TYPE_BEFORE_NEW) || (type == ModelValidator.TYPE_BEFORE_CHANGE)){

            // Me aseguro nombre corto del producto (campo: Description) con un máximo de 30 caracteres
            if ((model.getDescription() == null) || (model.getDescription().trim().equalsIgnoreCase(""))){
                return "Debe indicar Nombre Corto para el nuevo Producto";
            }
            String nombreCorto = model.getDescription().trim();
            if (nombreCorto.length() > 31){
                nombreCorto = nombreCorto.substring(0,31);
                model.setDescription(nombreCorto);
            }

            // En retail, si la unidad del producto es Kilogramo, si se compra y se vende entonces me aseguro de setear producto de balanza
            if (model.getC_UOM_ID() > 0){
                MUOM uom = (MUOM)model.getC_UOM();
                if (uom.getUOMSymbol().toLowerCase().equalsIgnoreCase("kg")){
                    if (model.isSold() && model.isPurchased()){
                        model.set_ValueOfColumn("EsProductoBalanza", true);
                    }
                }
            }

            // En retail si el producto es de balanza hago validaciones de codigo y nombre
            if (model.get_ValueAsBoolean("EsProductoBalanza")){

                // Codigo númerico, máximo cuantro digitos (9999)
                if ((!NumberUtils.isNumber(model.getValue())) || (model.getValue().length() > 4)){
                    return "Código interno del Producto debe ser númerico y no mayor a 9999 : " + model.getValue() + " - " + model.getName();
                }

                // Nombre corto del producto máximo 20 caracteres
                if (model.getDescription().trim().length() > 20){
                    return "Nombre corto de Producto supera los 20 caracteres para Balanza : " + model.getValue() + " - " + model.getName();
                }
            }
        }

        else if ((type == ModelValidator.TYPE_AFTER_NEW) || (type == ModelValidator.TYPE_AFTER_CHANGE)){

            // Para productos nuevos o modificados en su categoría de impuesto
            if ((type == ModelValidator.TYPE_AFTER_NEW) || (model.is_ValueChanged(X_M_Product.COLUMNNAME_C_TaxCategory_ID))
                    || (model.is_ValueChanged("C_TaxCategory_ID_2"))){

                // Seteo Configuración contable para este producto
                MZRetailConfig retailConfig = MZRetailConfig.getDefault(model.getCtx(), model.get_TrxName());

                int cTaxCategoryID = model.getC_TaxCategory_ID();
                // Impuesto especial de compra para productos en retail.
                if (model.get_ValueAsInt("C_TaxCategory_ID_2") > 0){
                    cTaxCategoryID = model.get_ValueAsInt("C_TaxCategory_ID_2");
                }
                AcctUtils.setRetailProdAcct(model.getCtx(), model.getAD_Client_ID(), model.get_ID(), cTaxCategoryID, retailConfig.get_ID(), model.get_TrxName());

            }
        }

        return mensaje;
    }


    /***
     * Validaciones para el modelo de Precios de Productos en Retail
     * Xpande. Created by Gabriel Vila on 8/8/17.
     * @param model
     * @param type
     * @return
     * @throws Exception
     */
    public String modelChange(MProductPrice model, int type) throws Exception {

        String mensaje = null;

        if ((type == ModelValidator.TYPE_BEFORE_NEW) || (type == ModelValidator.TYPE_BEFORE_CHANGE)){

            // Obtengo lista de precios asociada a este precio de producto
            MPriceListVersion priceListVersion = (MPriceListVersion) model.getM_PriceList_Version();
            MPriceList priceList = priceListVersion.getPriceList();

            // Si lista de precio no es de venta, no hago nada
            if (!priceList.isSOPriceList()){
                return mensaje;
            }

            // Si lista de precio no tiene organización asociada, no hago nada.
            if (priceList.getAD_Org_ID() <= 0){
                return mensaje;
            }

            // Si es modificacion pero no se modifica el precio o la vigencia, no hago nada
            if (type == ModelValidator.TYPE_BEFORE_CHANGE){
                if (!model.is_ValueChanged(X_M_ProductPrice.COLUMNNAME_PriceList) && (!model.is_ValueChanged("ValidFrom"))){
                    return mensaje;
                }
            }

            // Genero registro en evolución de precio de venta para este producto-organizacion
            MZEvolPrecioVtaProdOrg evolPrecioVtaProdOrg = new MZEvolPrecioVtaProdOrg(model.getCtx(), 0, model.get_TrxName());
            evolPrecioVtaProdOrg.setM_Product_ID(model.getM_Product_ID());
            evolPrecioVtaProdOrg.setM_PriceList_ID(priceList.get_ID());
            evolPrecioVtaProdOrg.setC_Currency_ID(priceList.getC_Currency_ID());
            evolPrecioVtaProdOrg.setAD_OrgTrx_ID(priceList.getAD_Org_ID());
            evolPrecioVtaProdOrg.setDateValidSO((Timestamp) model.get_Value("ValidFrom"));
            evolPrecioVtaProdOrg.setPriceSO(model.getPriceList());
            evolPrecioVtaProdOrg.setAD_User_ID(Env.getAD_User_ID(model.getCtx()));

            if (model.get_ValueAsInt("C_DocType_ID") > 0){
                evolPrecioVtaProdOrg.setC_DocType_ID(model.get_ValueAsInt("C_DocType_ID"));
            }
            if (model.get_ValueAsString("DocumentNoRef") != null){
                evolPrecioVtaProdOrg.setDocumentNoRef(model.get_ValueAsString("DocumentNoRef"));
            }

            evolPrecioVtaProdOrg.saveEx();
        }

        return mensaje;
    }

    /***
     * Validaciones para documentos de la tabla C_Invoice en gestión de retail.
     * Xpande. Created by Gabriel Vila on 8/8/17.
     * @param model
     * @param timing
     * @return
     */
    private String docValidate(MInvoice model, int timing) {

        String message = null, sql = "";
        String action = "";

        MDocType docType = (MDocType) model.getC_DocTypeTarget();
        MZRetailConfig retailConfig = MZRetailConfig.getDefault(model.getCtx(), model.get_TrxName());

        if (timing == TIMING_BEFORE_COMPLETE) {

            // No aplica en comprobantes de venta
            if (model.isSOTrx()) {
                return null;
            }

            // Para comprobantes de compra del tipo API (facturas de proveedores)
            if (docType.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_APInvoice)) {

                // Si para esta factura de proveedor tengo lineas de bonificacion,
                // me aseguro que dichas lineas tengan producto a bonificar y bonificador.
                sql = " select count(*) from z_invoicebonifica where c_invoice_id =" + model.get_ID() +
                        " and ((m_product_to_id is null or m_product_to_id <= 0) or (m_product_id is null or m_product_id <= 0))";
                int contador = DB.getSQLValueEx(model.get_TrxName(), sql);
                if (contador > 0){
                    throw new AdempiereException("Falta indicar Producto a Bonificar y/o Producto Bonificador en líneas de Bonificación");
                }

            }
        }

        if (timing == TIMING_AFTER_COMPLETE){

            // No aplica en comprobantes de venta
            if (model.isSOTrx()){
                return null;
            }

            MInvoiceLine[] invoiceLines = model.getLines();

            // Guardo documento en tabla para informes de actividad por documento
            MZActividadDocumento actividadDocumento = new MZActividadDocumento(model.getCtx(), 0, model.get_TrxName());
            actividadDocumento.setAD_Table_ID(model.get_Table_ID());
            actividadDocumento.setRecord_ID(model.get_ID());
            actividadDocumento.setC_DocType_ID(model.getC_DocTypeTarget_ID());
            String documentSerie = model.get_ValueAsString("DocumentSerie");
            if (documentSerie == null) documentSerie = "";
            actividadDocumento.setDocumentNoRef(documentSerie + model.getDocumentNo());
            actividadDocumento.setDocCreatedBy(model.getCreatedBy());
            actividadDocumento.setDocDateCreated(model.getCreated());
            actividadDocumento.setCompletedBy(Env.getAD_User_ID(model.getCtx()));
            actividadDocumento.setDateCompleted(new Timestamp(System.currentTimeMillis()));
            actividadDocumento.setLineNo(invoiceLines.length);
            actividadDocumento.setAD_Role_ID(Env.getAD_Role_ID(model.getCtx()));
            actividadDocumento.setDiferenciaTiempo(new BigDecimal((actividadDocumento.getDateCompleted().getTime()-actividadDocumento.getDocDateCreated().getTime())/1000).divide(new BigDecimal(60),2,BigDecimal.ROUND_HALF_UP));
            actividadDocumento.saveEx();

            // Precisión decimal de lista de precios de compra, si es que tengo.
            int precision = 2;
            if (model.getM_PriceList_ID() > 0){
                precision = MPriceList.getPricePrecision(model.getCtx(), model.getM_PriceList_ID());
            }


            // Recorro lineas del comprobante y actualizo datos de ultima factura en ficha producto-socio
            for (int i = 0; i < invoiceLines.length; i++){

                MInvoiceLine invoiceLine = invoiceLines[i];

                // Instancio modelo producto-socio, y si este modelo tiene pauta comercial asociada, calculo descuentos por NC al pago.
                if (invoiceLine.getM_Product_ID() > 0){
                    MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(Env.getCtx(), model.getC_BPartner_ID(), invoiceLine.getM_Product_ID(), model.get_TrxName());
                    if ((productoSocio != null) && (productoSocio.get_ID() > 0)){
                        String serieDocumento = model.get_ValueAsString("DocumentSerie");
                        if (serieDocumento == null) serieDocumento = "";
                        productoSocio.setInvoiceNo(serieDocumento + model.getDocumentNo());
                        productoSocio.setDateInvoiced(model.getDateInvoiced());
                        productoSocio.setPriceInvoiced(invoiceLine.getPriceEntered());
                        productoSocio.setC_Invoice_ID(model.get_ID());
                        productoSocio.saveEx();
                    }
                }
            }

            // Calculo de descuentos por Notas de Credito al Pago.
            this.setNCAlPago(model, invoiceLines, precision);

            // Para comprobantes de compra del tipo API (facturas de proveedores)
            if (docType.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_APInvoice)){

                // Calculo y generación de remitos por diferencia de cantidad y montos

                // Instancio cabezal de remito por diferencia, si luego no tiene monto, lo elimino.
                BigDecimal totalAmtRemito = Env.ZERO;
                MZRemitoDifInv remitoDif = null;
                MDocType[] docTypeRemitoList = MDocType.getOfDocBaseType(model.getCtx(), "RDI");
                if (docTypeRemitoList.length <= 0){
                    throw new AdempiereException("No esta definido el Documento para Remito por Diferencia");
                }
                MDocType docRemito = docTypeRemitoList[0];
                remitoDif = new MZRemitoDifInv(model.getCtx(), 0, model.get_TrxName());
                remitoDif.setC_BPartner_ID(model.getC_BPartner_ID());
                remitoDif.setC_Currency_ID(model.getC_Currency_ID());
                remitoDif.setC_DocType_ID(docRemito.get_ID());
                remitoDif.setC_Invoice_ID(model.get_ID());
                remitoDif.setDateDoc(model.getDateInvoiced());
                remitoDif.setAD_Org_ID(model.getAD_Org_ID());
                remitoDif.setTotalAmt(Env.ZERO);

                for (int i = 0; i < invoiceLines.length; i++){

                    MInvoiceLine invoiceLine = invoiceLines[i];

                    // Proceso remitos por diferencia
                    BigDecimal amtRemitoLin = remitoDif.setRemitoDiferencia(model, invoiceLine, precision, false);
                    if (amtRemitoLin != null){
                        totalAmtRemito = totalAmtRemito.add(amtRemitoLin);
                    }
                }

                if (totalAmtRemito.compareTo(Env.ZERO) > 0){
                    remitoDif.setTotalAmt(totalAmtRemito);
                    if (!remitoDif.processIt(DocAction.ACTION_Complete)){
                        message = remitoDif.getProcessMsg();
                        if (message == null){
                            message = "Error al completar documento de Remito por Diferencia";
                        }
                        return message;
                    }

                    // Si esta factura esta asociada a un Remito por Diferencia de Cantidad, asocio este nuevo remito por diferencias con
                    // dicho documento anterior.
                    MZRemitoDifInv remDifCant = MZRemitoDifInv.getByDocInvoice(model.getCtx(), model.get_ID(), retailConfig.getDefDocRemDifCant_ID(), null);
                    if ((remDifCant != null) && (remDifCant.get_ID() > 0)){
                        remitoDif.setM_InOut_ID(remDifCant.getM_InOut_ID());
                        remitoDif.setRemDifCant_ID(remDifCant.get_ID());
                    }
                    remitoDif.saveEx();

                    // Marco invoice como Bloqueada ya que tiene diferencias
                    DB.executeUpdateEx(" update c_invoice set EstadoAprobacion ='BLOQUEADO' where c_invoice_id =" + model.get_ID(), model.get_TrxName());
                }
            }
            else if (docType.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_APCredit)){

                // Si estoy complentando una nota de credito por diferencia en facturación, contra remitos
                MZComercialConfig comercialConfig = MZComercialConfig.getDefault(model.getCtx(), null);
                if (comercialConfig.getDefaultDocAPCDif_ID() == model.getC_DocTypeTarget_ID()) {
                    for (int i = 0; i < invoiceLines.length; i++) {
                        MInvoiceLine invoiceLine = invoiceLines[i];

                        // Obtengo linea de afectación de remito asociada a esta linea de nota de crédito
                        MZRemDifInvLinAfecta lineAfecta = MZRemDifInvLinAfecta.getByInvoiceLine(model.getCtx(), invoiceLine.get_ID(), model.get_TrxName());
                        if ((lineAfecta != null) && (lineAfecta.get_ID() > 0)){

                            // Obtengo linea de remito y actualizo datos de la misma
                            MZRemitoDifInvLin remLin = (MZRemitoDifInvLin) lineAfecta.getZ_RemitoDifInvLin();
                            if ((remLin != null) && (remLin.get_ID() > 0)){
                                // Actualizo cantidad o neto abierto segun tipo de diferencia
                                boolean closeLine = false;
                                if (remLin.isDifferenceAmt()){
                                    remLin.setAmtOpen(remLin.getAmtOpen().subtract(invoiceLine.getLineTotalAmt()));
                                    if (remLin.getAmtOpen().compareTo(Env.ZERO) <= 0){
                                        closeLine = true;
                                    }
                                }
                                if (remLin.isDifferenceQty()){
                                    remLin.setQtyOpen(remLin.getQtyOpen().subtract(invoiceLine.getQtyEntered()));
                                    if (remLin.getQtyOpen().compareTo(Env.ZERO) <= 0){
                                        closeLine = true;
                                    }
                                }
                                remLin.setIsClosed(closeLine);
                                remLin.saveEx();
                            }
                        }
                    }
                }
            }

        }
        else if (timing == TIMING_BEFORE_REACTIVATE){

            // No aplica en comprobantes de venta
            if (model.isSOTrx()){
                return null;
            }

            // Elimino este documento de la tabla para informes de actividades de documentos.
            MZActividadDocumento actividadDocumento = MZActividadDocumento.getByTableRecord(model.getCtx(), model.get_Table_ID(), model.get_ID(), model.get_TrxName());
            if ((actividadDocumento != null) && (actividadDocumento.get_ID() > 0)){
                actividadDocumento.deleteEx(true);
            }

            // Para comprobantes de compra del tipo API (facturas de proveedores)
            if (docType.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_APInvoice)){
                // Cuando reactivo un documento, me aseguro de no dejar factura marcada con datos de descuentos por notas de credito al pago.
                // Elimino información para notas de credito al pago que pueda tener esta factura
                action = " update c_invoiceline set PriceDtoNC = null, " +
                        " AmtDtoNC = null, " +
                        " TieneDtosNC ='N' " +
                        " where c_invoice_id =" + model.get_ID();
                DB.executeUpdateEx(action, model.get_TrxName());

                action = " update c_invoice set TieneDtosNC ='N' " +
                        " where c_invoice_id =" + model.get_ID();
                DB.executeUpdateEx(action, model.get_TrxName());

                // Elimino remito por diferencia que pueda tener asociada esta factura
                action = " delete from " + X_Z_RemitoDifInv.Table_Name +
                        " where c_invoice_id =" + model.get_ID();
                DB.executeUpdateEx(action, model.get_TrxName());

                // Cuando reactivo una factura de proveedor, me aseguro de eliminar un posible documento de Remito por Diferencia que
                // pueda estar asociado a dicha factura.
                action = " delete from z_remitodifinv where c_invoice_id =" + model.get_ID() +
                        " and c_doctype_id in (select c_doctype_id from c_doctype where docbasetype ='RDI')";
                DB.executeUpdateEx(action, model.get_TrxName());
            }

        }

        return null;
    }


    /***
     * Validaciones para documentos de la tabla C_Invoice en gestión de retail.
     * Xpande. Created by Gabriel Vila on 8/8/17.
     * @param model
     * @param timing
     * @return
     */
    private String docValidate(MOrder model, int timing) {

        String message = null, sql = "";
        String action = "";

        if (timing == TIMING_AFTER_COMPLETE){

            // Calculo de descuentos por Notas de Credito al Pago.

            // No aplica en comprobantes de venta
            if (model.isSOTrx()){
                return null;
            }

            MOrderLine[] orderLines = model.getLines();

            // Guardo documento en tabla para informes de actividad por documento
            MZActividadDocumento actividadDocumento = new MZActividadDocumento(model.getCtx(), 0, model.get_TrxName());
            actividadDocumento.setAD_Table_ID(model.get_Table_ID());
            actividadDocumento.setRecord_ID(model.get_ID());
            actividadDocumento.setC_DocType_ID(model.getC_DocTypeTarget_ID());
            actividadDocumento.setDocumentNoRef(model.getDocumentNo());
            actividadDocumento.setDocCreatedBy(model.getCreatedBy());
            actividadDocumento.setDocDateCreated(model.getCreated());
            actividadDocumento.setCompletedBy(Env.getAD_User_ID(model.getCtx()));
            actividadDocumento.setDateCompleted(new Timestamp(System.currentTimeMillis()));
            actividadDocumento.setLineNo(orderLines.length);
            actividadDocumento.setAD_Role_ID(Env.getAD_Role_ID(model.getCtx()));
            actividadDocumento.setDiferenciaTiempo(new BigDecimal((actividadDocumento.getDateCompleted().getTime()-actividadDocumento.getDocDateCreated().getTime())/1000).divide(new BigDecimal(60),2,BigDecimal.ROUND_HALF_UP));
            actividadDocumento.saveEx();

        }
        else if (timing == TIMING_BEFORE_REACTIVATE){

            // Cuando reactivo un documento, me aseguro de no dejar factura marcada con datos de descuentos por notas de credito al pago.

            // No aplica en comprobantes de venta
            if (model.isSOTrx()){
                return null;
            }

            // Elimino este documento de la tabla para informes de actividades de documentos.
            MZActividadDocumento actividadDocumento = MZActividadDocumento.getByTableRecord(model.getCtx(), model.get_Table_ID(), model.get_ID(), model.get_TrxName());
            if ((actividadDocumento != null) && (actividadDocumento.get_ID() > 0)){
                actividadDocumento.deleteEx(true);
            }
        }

        return null;
    }


    /***
     * Genera invoice por el concepto de nota de credito al pago para proveedores.
     * Xpande. Created by Gabriel Vila on 6/1/18.
     * @param invoice
     * @param invoiceLines
     * @param precision
     */
    private void setNCAlPago(MInvoice invoice, MInvoiceLine[] invoiceLines, int precision){

        String action = "";

        try{

            MZComercialConfig comercialConfig = MZComercialConfig.getDefault(invoice.getCtx(), null);

            // Si estoy completando una nota de credito proveedor que ya es por descuentos al pago, no hago nada
            if (invoice.getC_DocTypeTarget_ID() == comercialConfig.getDefaultDocAPCDtoPag_ID()){
                return;
            }

            boolean hayDescuntoNC = false;

            // Recorro lineas del comprobante
            for (int i = 0; i < invoiceLines.length; i++){

                MInvoiceLine invoiceLine = invoiceLines[i];

                // Proceso notas de credito al pago
                boolean resultadoNC = this.setLineaNCAlPago(invoice, invoiceLine, precision);
                if (resultadoNC){
                    hayDescuntoNC = true;
                }
            }

            if (hayDescuntoNC){
                action = " update c_invoice set TieneDtosNC ='Y' " +
                        " where c_invoice_id =" + invoice.get_ID();
                DB.executeUpdateEx(action, invoice.get_TrxName());
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

    /***
     * Dada una linea de factura verifica si para la misma corresponde o no un descuento con NC al pago.
     * Xpande. Created by Gabriel Vila on 3/5/18.
     * @param invoice
     * @param invoiceLine
     * @param precision
     * @return
     */
    private boolean setLineaNCAlPago(MInvoice invoice, MInvoiceLine invoiceLine, int precision){

        boolean hayDescuntoNC = false;
        String action = "";

        try{

            if (invoiceLine.getM_Product_ID() <= 0){
                return false;
            }

            if ((invoiceLine.getPriceEntered() == null) || (invoiceLine.getPriceEntered().compareTo(Env.ZERO) == 0)){
                return false;
            }

            // Si corresponde calculo precio con descuento por NC al pago
            // Instancio modelo producto-socio, y si este modelo tiene pauta comercial asociada, calculo descuentos por NC al pago.
            MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(Env.getCtx(), invoice.getC_BPartner_ID(), invoiceLine.getM_Product_ID(), null);
            if ((productoSocio != null) && (productoSocio.get_ID() > 0)){
                if (productoSocio.getZ_PautaComercial_ID() > 0){
                    MZPautaComercial pautaComercial = (MZPautaComercial) productoSocio.getZ_PautaComercial();
                    ProductPricesInfo ppi = pautaComercial.calculatePrices(invoiceLine.getM_Product_ID(), invoiceLine.getPriceEntered(), precision);
                    if (ppi != null){
                        if ((ppi.getPriceDtoNC() != null) && (ppi.getPriceDtoNC().compareTo(Env.ZERO) > 0)){
                            BigDecimal priceDtoNC = ppi.getPriceDtoNC();
                            BigDecimal amtDtoNC = priceDtoNC.multiply(invoiceLine.getQtyEntered()).setScale(precision, BigDecimal.ROUND_HALF_UP);

                            action = " update c_invoiceline set PriceDtoNC =" + priceDtoNC + ", " +
                                    " AmtDtoNC =" + amtDtoNC + ", " +
                                    " PorcDtoNC =" + ppi.getSumPercentageDiscountsNC() + ", " +
                                    " TieneDtosNC ='Y' " +
                                    " where c_invoiceline_id =" + invoiceLine.get_ID();
                            DB.executeUpdateEx(action, invoice.get_TrxName());

                            hayDescuntoNC = true;
                        }
                    }
                }

            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return hayDescuntoNC;
    }



}
