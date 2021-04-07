package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.lang.math.NumberUtils;
import org.compiere.acct.Doc;
import org.compiere.model.*;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xpande.cfe.model.MZCFEConfig;
import org.xpande.comercial.model.MZComercialConfig;
import org.xpande.comercial.utils.ComercialUtils;
import org.xpande.core.utils.CurrencyUtils;
import org.xpande.retail.utils.AcctUtils;
import org.xpande.retail.utils.ProductPricesInfo;
import org.zkoss.zhtml.Big;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        engine.addModelChange(I_M_InOutLine.Table_Name, this);
        engine.addModelChange(I_M_ProductPrice.Table_Name, this);
        engine.addModelChange(I_M_Product.Table_Name, this);
        engine.addModelChange(I_M_InventoryLine.Table_Name, this);
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
        else if (po.get_TableName().equalsIgnoreCase(I_M_InOutLine.Table_Name)){
            return modelChange((MInOutLine) po, type);
        }
        else if (po.get_TableName().equalsIgnoreCase(I_M_Product.Table_Name)){
            return modelChange((MProduct) po, type);
        }
        else if (po.get_TableName().equalsIgnoreCase(I_M_ProductPrice.Table_Name)){
            return modelChange((MProductPrice) po, type);
        }
        else if (po.get_TableName().equalsIgnoreCase(I_M_InventoryLine.Table_Name)){
            return modelChange((MInventoryLine) po, type);
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
        if (order.isSOTrx()) return null;


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
                if (productPricing.isCostoHistorico()){
                    model.set_ValueOfColumn("PricePO", productPricing.getPricePO());
                }
                else{
                    model.set_ValueOfColumn("PricePO", model.getPriceEntered());
                }

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

        return null;
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

        /*
        else if ((type == ModelValidator.TYPE_AFTER_NEW) || (type == ModelValidator.TYPE_AFTER_CHANGE)){

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
        */

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
            if (model.isSOTrx()) return null;

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

        return null;
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

        MInvoice invoice = (MInvoice)model.getC_Invoice();
        MDocType docType = (MDocType) invoice.getC_DocTypeTarget();

        if ((type == ModelValidator.TYPE_AFTER_NEW) || (type == ModelValidator.TYPE_AFTER_CHANGE)){

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

            // Cuando estoy en comprobantes de compra
            if (type == ModelValidator.TYPE_BEFORE_NEW){
                if (!invoice.isSOTrx()){
                    if (model.get_Value("PricePO") == null){
                        MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(model.getCtx(), invoice.getC_BPartner_ID(), model.getM_Product_ID(), null);
                        if ((productoSocio != null) && (productoSocio.get_ID() > 0)){
                            MZProductoSocioOrg productoSocioOrg = productoSocio.getOrg(invoice.getAD_Org_ID());
                            if ((productoSocioOrg != null) && (productoSocioOrg.get_ID() > 0)){
                                model.set_ValueOfColumn("PricePO", productoSocioOrg.getPricePO());
                            }
                            else{
                                model.set_ValueOfColumn("PricePO", productoSocio.getPricePO());
                            }
                            model.set_ValueOfColumn("PricePONoDto", productoSocioOrg.getPricePO());
                        }
                    }

                    // Si esstoy creado linea de invoice desde linea de inout
                    if (model.getM_InOutLine_ID() > 0){
                        // Si tengo precio de ultima factura en esta linea de inout, tomo este precio como precio de esta linea
                        MInOutLine inOutLine = (MInOutLine) model.getM_InOutLine();
                        BigDecimal precioUltFact = (BigDecimal) inOutLine.get_Value("PriceInvoiced");
                        if (precioUltFact != null){
                            if (precioUltFact.compareTo(Env.ZERO) > 0){
                                model.setPriceEntered(precioUltFact);
                                model.setPriceActual(precioUltFact);
                                model.setPriceLimit(precioUltFact);
                                model.setPriceList(precioUltFact);
                                model.setLineNetAmt();
                                model.setTaxAmt();
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

                        boolean hayDtos = false;
                        boolean calculoDtos = true;

                        if (discount1.compareTo(Env.ZERO) > 0) hayDtos = true;
                        if (discount2.compareTo(Env.ZERO) > 0) hayDtos = true;
                        if (discount3.compareTo(Env.ZERO) > 0) hayDtos = true;

                        // No calculo descuentos cuando es nuevo registro y ademas tengo todos los campos de descuentos en cero.
                        if (type == ModelValidator.TYPE_BEFORE_NEW ){
                            if (!hayDtos) {
                                calculoDtos = false;
                            }
                        }

                        if (calculoDtos){
                            // Cuando el registro es nuevo y se digita precio, la base del calculo es ese precio.
                            if (model.get_ID() <= 0){
                                pricePONoDto = model.getPriceEntered();
                            }

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
                }
                else{
                    // Si este registro se genera como producto de un proceso de seleccion (Ej: Seleccion de Remitos en comprobante de venta
                    if ((model.get_ValueAsBoolean("IsBySelection")) ||
                            ((model.getPriceActual() == null) || (model.getPriceActual().compareTo(Env.ZERO) == 0))){

                        if (type == ModelValidator.TYPE_BEFORE_NEW){
                            // Para facturas de venta, en caso de facturación entre organizaciones, se debe tomar precio de ultima factura de proveedor
                            // Instancio modelo de socio de negocio para verificar si esta linkeado con una organización de este compañia.
                            if (model.getM_Product_ID() > 0){
                                MBPartner partner = (MBPartner) invoice.getC_BPartner();
                                if (partner.getAD_OrgBP_ID_Int() > 0){

                                    // Si el comprobante de venta tiene secuencia definitiva
                                    // (si viene por POS no debe tocar importes a pesar que sea entre locales)
                                    //if (docType.getDefiniteSequence_ID() > 0){
                                        MOrg orgLinked = new MOrg(model.getCtx(), partner.getAD_OrgBP_ID_Int(), null);
                                        if ((orgLinked != null) && (orgLinked.get_ID() > 0)){

                                            // Obtengo precio de ultima factura de compra (API), para producto y organización de este comprobante de venta.
                                            // Si tengo entrega asociada, tomo fecha de este remito para obtener precio de ultima factura.
                                            Timestamp dateInvoiced = invoice.getDateInvoiced();
                                            if (model.getM_InOutLine_ID() > 0){
                                                MInOut inOut =(MInOut)((MInOutLine) model.getM_InOutLine()).getM_InOut();
                                                dateInvoiced = inOut.getMovementDate();
                                            }
                                            BigDecimal priceActual = ComercialUtils.getProdOrgLastAPInvoicePrice(model.getCtx(), model.getM_Product_ID(), invoice.getAD_Org_ID(), invoice.getC_Currency_ID(),dateInvoiced,null);

                                            // Si no tengo facturas de proveedor para este articulo, busco precio OC del ultimo proveedor para esta organización
                                            if ((priceActual == null) || (priceActual.compareTo(Env.ZERO) <= 0)){

                                                // Instancio modelo de producto-socio con ultima vigencia de precio OC
                                                MZProductoSocio productoSocio = MZProductoSocio.getByLastPriceOC(model.getCtx(), model.getM_Product_ID(), null);

                                                // Si tengo producto soccio, obtengo precio OC para esta organizacion
                                                if ((productoSocio != null) && (productoSocio.get_ID() > 0)){

                                                    int cCurrencyIDFrom = productoSocio.getC_Currency_ID();

                                                    MZProductoSocioOrg socioOrg = productoSocio.getOrg(invoice.getAD_Org_ID());
                                                    if ((socioOrg != null) && (socioOrg.get_ID() > 0)){

                                                        cCurrencyIDFrom = socioOrg.getC_Currency_ID();

                                                        priceActual = socioOrg.getPricePO();
                                                    }
                                                    else{
                                                        priceActual = productoSocio.getPricePO();
                                                    }

                                                    // Si la moneda del precio obtenido no es igual a la moneda de la invoice,
                                                    // debo convertir precio a moneda de invoice según tasa de cambio de la fecha del comprobante.
                                                    if (cCurrencyIDFrom != invoice.getC_Currency_ID()){

                                                        // Aplico conversión a tasa de cambio del día del comprobante
                                                        BigDecimal currencyRate = CurrencyUtils.getCurrencyRate(model.getCtx(), invoice.getAD_Client_ID(), 0, cCurrencyIDFrom, invoice.getC_Currency_ID(), 114, invoice.getDateInvoiced(), null);
                                                        if ((currencyRate == null) || (currencyRate.compareTo(Env.ZERO) == 0)){
                                                            currencyRate = Env.ONE;
                                                        }

                                                        priceActual = priceActual.multiply(currencyRate).setScale(2, RoundingMode.HALF_UP);
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
                                    //}
                                }
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

        if ((type == ModelValidator.TYPE_AFTER_NEW) || (type == ModelValidator.TYPE_AFTER_CHANGE)
                || (type == ModelValidator.TYPE_AFTER_DELETE)){

            // Para comprobantes de compra actualizo tasas de impuesto en el cabezal
            if (!invoice.isSOTrx()){

                String action, sql;

                // Actualizo campos con importes de impuestos en el cabezal de este comprobante de compra
                MZCFEConfig cfeConfig = MZCFEConfig.getDefault(model.getCtx(), null);

                // Tasa Basica
                sql = " select sum(taxamt) as monto " +
                        " from c_invoicetax " +
                        " where c_invoice_id =" + invoice.get_ID() +
                        " and c_tax_id =" + cfeConfig.getTaxBasico_ID();
                BigDecimal taxAmt = DB.getSQLValueBDEx(model.get_TrxName(), sql);
                if (taxAmt == null) taxAmt = Env.ZERO;
                action = " update c_invoice set taxamtbasico =" + taxAmt + " where c_invoice_id =" + invoice.get_ID();
                DB.executeUpdateEx(action, model.get_TrxName());

                // Tasa Mínima
                sql = " select sum(taxamt) as monto " +
                        " from c_invoicetax " +
                        " where c_invoice_id =" + invoice.get_ID() +
                        " and c_tax_id =" + cfeConfig.getTaxMinimo_ID();
                taxAmt = DB.getSQLValueBDEx(model.get_TrxName(), sql);
                if (taxAmt == null) taxAmt = Env.ZERO;
                action = " update c_invoice set taxamtmin =" + taxAmt + " where c_invoice_id =" + invoice.get_ID();
                DB.executeUpdateEx(action, model.get_TrxName());
            }
        }

        return null;
    }

    /***
     * Validaciones para el modelo de Lineas de InOut.
     * Xpande. Created by Gabriel Vila on 2/22/21.
     * @param model
     * @param type
     * @return
     * @throws Exception
     */
    public String modelChange(MInOutLine model, int type) throws Exception {

        if ((type == ModelValidator.TYPE_BEFORE_NEW) || (type == ModelValidator.TYPE_BEFORE_CHANGE)){

            MInOut inOut = (MInOut) model.getM_InOut();
            MDocType docType = (MDocType) inOut.getC_DocType();

            // Si estoy en recepciones de producto
            if (inOut.getMovementType().equalsIgnoreCase(X_M_InOut.MOVEMENTTYPE_VendorReceipts)){
                // Si no tengo valor en el campo de factura asociada
                if (model.get_ValueAsInt("Z_RecepcionProdFact_ID") <= 0){
                    // Veo si tengo un valor de una linea anterior
                    int zRecepProdFactID = this.getLastRecepcionProdFactID(model, model.get_TrxName());
                    if (zRecepProdFactID > 0){
                        model.set_ValueOfColumn("Z_RecepcionProdFact_ID", zRecepProdFactID);
                    }
                    else{
                        return "Debe indicar número de Factura Asociada a esta línea de Recepción.";
                    }
                }

                // Seteo cantidad diferencia entre recepcionaso y facturado, y total de la linea
                BigDecimal qtyInvoiced = (BigDecimal) model.get_Value("QtyEnteredInvoice");
                BigDecimal qtyEntered = model.getQtyEntered();
                BigDecimal priceInvoiced = (BigDecimal) model.get_Value("PriceInvoiced");
                if (qtyInvoiced == null) qtyInvoiced = Env.ZERO;
                if (qtyEntered == null) qtyEntered = Env.ZERO;
                if (priceInvoiced == null) priceInvoiced = Env.ZERO;

                BigDecimal qtyDiff = qtyEntered.subtract(qtyInvoiced);
                model.set_ValueOfColumn("DifferenceQty", qtyDiff);

                BigDecimal amtTotal = qtyEntered.multiply(priceInvoiced).setScale(2, RoundingMode.HALF_UP);
                model.set_ValueOfColumn("LineTotalAmt", amtTotal);
            }

            // Cuando estoy devoluciones de compra
            if ((type == ModelValidator.TYPE_BEFORE_NEW) ||
                    (type == ModelValidator.TYPE_BEFORE_CHANGE && model.is_ValueChanged(X_M_InOutLine.COLUMNNAME_M_Product_ID))){

                if (inOut.getMovementType().equalsIgnoreCase(X_M_InOut.MOVEMENTTYPE_VendorReturns)){

                    BigDecimal priceInvoiced = (BigDecimal) model.get_Value("PriceInvoiced");
                    if (priceInvoiced == null) priceInvoiced = Env.ZERO;

                    if (priceInvoiced.compareTo(Env.ZERO) == 0){

                        Timestamp dateInvoiced = null;
                        int cCurrencyID = 0;
                        String vendorProductCode = null, documentNoRef = null;

                        // Instancio modelo de producto-socio para obtener datos de ultima factura
                        MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(model.getCtx(), inOut.getC_BPartner_ID(), model.getM_Product_ID(), null);

                        // Si no tengo modelo para este socio de negocio de la ultima factura
                        if ((productoSocio == null) || (productoSocio.get_ID() <= 0)){
                            productoSocio = MZProductoSocio.getByLastInvoice(model.getCtx(), model.getM_Product_ID(), null);
                        }
                        else{
                            vendorProductCode = productoSocio.getVendorProductNo();

                            // Si no tengo precio de ultima factura
                            if ((productoSocio.getPriceInvoiced() == null) || (productoSocio.getPriceInvoiced().compareTo(Env.ZERO) <= 0)){
                                // Si tampoco tengo precio OC
                                if ((productoSocio.getPricePO() == null) || (productoSocio.getPricePO().compareTo(Env.ZERO) <= 0)){
                                    productoSocio = MZProductoSocio.getByLastInvoice(model.getCtx(), model.getM_Product_ID(), null);
                                }
                            }
                        }

                        // Si no hay facturas, obtengo socio de ultima gestión de precios de proveedor.
                        if ((productoSocio == null) || (productoSocio.get_ID() <= 0)){
                            productoSocio = MZProductoSocio.getByLastPriceOC(model.getCtx(), model.getM_Product_ID(), null);
                        }

                        if ((productoSocio != null) && (productoSocio.get_ID() > 0)){
                            priceInvoiced = productoSocio.getPriceInvoiced();
                            if ((priceInvoiced == null) || (priceInvoiced.compareTo(Env.ZERO) == 0)){
                                priceInvoiced = productoSocio.getPricePO();
                            }
                            dateInvoiced = productoSocio.getDateInvoiced();
                            cCurrencyID = productoSocio.getC_Currency_ID();
                            if (productoSocio.getC_Invoice_ID() > 0){
                                MInvoice invoiceRef = (MInvoice) productoSocio.getC_Invoice();
                                if (invoiceRef != null){
                                    documentNoRef = invoiceRef.getDocumentNo();
                                }
                                else{
                                    documentNoRef = "";
                                }
                            }
                        }
                        model.set_ValueOfColumn("DocumentNoRef", documentNoRef);
                        model.set_ValueOfColumn("PriceInvoiced", priceInvoiced);
                        model.set_ValueOfColumn("DateInvoiced", dateInvoiced);
                        model.set_ValueOfColumn("LineTotalAmt", model.getMovementQty().multiply(priceInvoiced).setScale(2, RoundingMode.HALF_UP));
                        model.set_ValueOfColumn("C_Currency_ID", inOut.getC_Currency_ID());
                        model.setM_Locator_ID(MLocator.getDefault((MWarehouse) inOut.getM_Warehouse()).get_ID());
                        model.set_ValueOfColumn("DestinoDevol", "NOTACREDITO");
                    }
                }
            }
        }
        else if ((type == ModelValidator.TYPE_AFTER_NEW) || (type == ModelValidator.TYPE_AFTER_CHANGE)
                || (type == ModelValidator.TYPE_AFTER_DELETE)){

            MInOut inOut = (MInOut) model.getM_InOut();

            if (inOut.getMovementType().equalsIgnoreCase(X_M_InOut.MOVEMENTTYPE_VendorReturns)){

                String sql = " select sum(linetotalamt) from m_inoutline where m_inout_id =" + inOut.get_ID();
                BigDecimal amtTotal = DB.getSQLValueBDEx(model.get_TrxName(), sql);
                if (amtTotal == null) amtTotal = Env.ZERO;

                String action = " update m_inout set amttotal =" + amtTotal +
                        " where m_inout_id =" + inOut.get_ID();
                DB.executeUpdateEx(action, model.get_TrxName());
            }
        }

        return null;
    }

    /***
     * Obtiene y retorna ultima factura asociada ingresada en un documento de inout.
     * Xpande. Created by Gabriel Vila on 3/16/21.
     * @param inOutLine
     * @param trxName
     * @return
     */
    private int getLastRecepcionProdFactID(MInOutLine inOutLine, String trxName) {

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int value = -1;

        try{
            sql = " select Z_RecepcionProdFact_ID from m_inoutline where m_inout_id =" + inOutLine.getM_InOut_ID() +
                    " order by created desc";

        	pstmt = DB.prepareStatement(sql, trxName);
        	rs = pstmt.executeQuery();

        	if (rs.next()){
                value = rs.getInt("Z_RecepcionProdFact_ID");
        	}
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
        	rs = null; pstmt = null;
        }

        return value;
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
        else if (timing == TIMING_BEFORE_REACTIVATE) {

            // En recepciones de productos
            if (model.getMovementType().equalsIgnoreCase(X_M_InOut.MOVEMENTTYPE_VendorReceipts)) {

                // Para cada linea de esta inout, dejo seteada en null la cantidad diferencia entre recibida y facturada.
                //action = " update m_inoutline set DifferenceQty = null where m_inout_id =" + model.get_ID();
                action = " update m_inoutline set DifferenceQty = null where m_inout_id =" + model.get_ID() +
                        " and m_inoutline_id in (select m_inoutline_id from c_invoiceline where c_invoice_id not in " +
                        " (select a.c_invoice_id from z_recepcionprodfact a " +
                        " inner join c_invoice b on a.c_invoice_id = b.c_invoice_id " +
                        " where a.m_inout_id =" + model.get_ID() + " and b.docstatus='CO')) ";

                DB.executeUpdateEx(action, model.get_TrxName());

                // Cuando reactivo una recepción de proveedor, me aseguro de eliminar posibles documentos de Remitos por Cantidad que
                // puedan estar asociados a dicha recepción.
                action = " delete from z_remitodifinv where m_inout_id =" + model.get_ID() +
                        " and c_doctype_id in (select c_doctype_id from c_doctype where docbasetype ='RDC') " +
                        " and c_invoice_id not in " +
                        " (select a.c_invoice_id from z_recepcionprodfact a " +
                        " inner join c_invoice b on a.c_invoice_id = b.c_invoice_id " +
                        " where a.m_inout_id =" + model.get_ID() + " and b.docstatus='CO') ";

                DB.executeUpdateEx(action, model.get_TrxName());
            }

            // Devoluciones de proveedores.
            else if (model.getMovementType().equalsIgnoreCase(X_M_InOut.MOVEMENTTYPE_VendorReturns)) {

                // Si tengo remito por diferencia asociado, lo reactivo y elimino
                if (model.get_ValueAsInt("Z_RemitoDifInv_ID") > 0){
                    MZRemitoDifInv remitoDifInv = new MZRemitoDifInv(model.getCtx(), model.get_ValueAsInt("Z_RemitoDifInv_ID"), model.get_TrxName());
                    if ((remitoDifInv != null) && (remitoDifInv.get_ID() > 0)){
                        if (!remitoDifInv.processIt(DocAction.ACTION_ReActivate)){
                            message = "No se pudo reactivar el documento de Remito por Diferencia asociado. ";
                            if (remitoDifInv.getProcessMsg() != null){
                                message += remitoDifInv.getProcessMsg();
                                return message;
                            }
                        }
                        // Elimino remito por diferencia asociado
                        remitoDifInv.deleteEx(true);
                    }
                }
            }

        }
        else if (timing == TIMING_AFTER_COMPLETE) {

            // Devoluciones de proveedores.
            if (model.getMovementType().equalsIgnoreCase(X_M_InOut.MOVEMENTTYPE_VendorReturns)) {

                MDocType[] docTypeRemitoList = MDocType.getOfDocBaseType(model.getCtx(), "RDI");
                if (docTypeRemitoList.length <= 0){
                    throw new AdempiereException("No esta definido el Documento para Remito por Diferencia");
                }
                MDocType docRemito = docTypeRemitoList[0];

                // Genero documento de Remito por Diferencia con motivo : DEVOLUCION A PROVEEDOR
                MZRemitoDifInv remitoDifInv = new MZRemitoDifInv(model.getCtx(), 0, model.get_TrxName());
                remitoDifInv.setAD_Org_ID(model.getAD_Org_ID());
                remitoDifInv.setC_BPartner_ID(model.getC_BPartner_ID());
                remitoDifInv.setC_Currency_ID(model.getC_Currency_ID());
                remitoDifInv.setC_DocType_ID(docRemito.get_ID());
                remitoDifInv.setDateDoc(model.getMovementDate());
                remitoDifInv.setDescription("Generado Automaticamente desde Devolucion: " + model.getDocumentNo());
                remitoDifInv.setM_InOut_ID(model.get_ID());
                remitoDifInv.setTotalAmt(Env.ZERO);
                remitoDifInv.setTipoRemitoDifInv(X_Z_RemitoDifInv.TIPOREMITODIFINV_DEVOLUCIONAPROVEEDOR);

                remitoDifInv.saveEx();

                BigDecimal totalAmt = Env.ZERO;

                // Obtengo y recorro lineas
                MInOutLine[] mInOutLines = model.getLines();
                for (int i = 0; i < mInOutLines.length; i++) {
                    MInOutLine mInOutLine = mInOutLines[i];

                    MZRemitoDifInvLin remitoDifInvLin = new MZRemitoDifInvLin(model.getCtx(), 0, model.get_TrxName());
                    remitoDifInvLin.setZ_RemitoDifInv_ID(remitoDifInv.get_ID());
                    remitoDifInvLin.setAD_Org_ID(model.getAD_Org_ID());
                    remitoDifInvLin.setAmtOpen(Env.ZERO);
                    remitoDifInvLin.setAmtSubtotal((BigDecimal) mInOutLine.get_Value("LineTotalAmt"));
                    remitoDifInvLin.setAmtSubtotalPO(remitoDifInvLin.getAmtSubtotal());
                    remitoDifInvLin.setC_UOM_ID(mInOutLine.getC_UOM_ID());
                    remitoDifInvLin.setDifferenceAmt(Env.ZERO);
                    remitoDifInvLin.setDifferencePrice(Env.ZERO);
                    remitoDifInvLin.setDifferenceQty(mInOutLine.getQtyEntered());
                    remitoDifInvLin.setIsClosed(false);
                    remitoDifInvLin.setIsDifferenceAmt(false);
                    remitoDifInvLin.setIsDifferenceQty(true);
                    remitoDifInvLin.setM_InOutLine_ID(mInOutLine.get_ID());
                    remitoDifInvLin.setM_Product_ID(mInOutLine.getM_Product_ID());
                    remitoDifInvLin.setPriceInvoiced((BigDecimal) mInOutLine.get_Value("PriceInvoiced"));
                    remitoDifInvLin.setPricePO(remitoDifInvLin.getPriceInvoiced());
                    remitoDifInvLin.setQtyDelivered(Env.ZERO);
                    remitoDifInvLin.setQtyInvoiced(mInOutLine.getQtyEntered());
                    remitoDifInvLin.setQtyOpen(mInOutLine.getQtyEntered());
                    remitoDifInvLin.setUPC(mInOutLine.get_ValueAsString("UPC"));
                    remitoDifInvLin.setVendorProductNo(mInOutLine.get_ValueAsString("VendorProductNo"));

                    remitoDifInvLin.saveEx();

                    totalAmt = totalAmt.add(remitoDifInvLin.getAmtSubtotal());

                }

                remitoDifInv.setTotalAmt(totalAmt);
                remitoDifInv.saveEx();

                // Completo remito por diferencia
                if (!remitoDifInv.processIt(DocAction.ACTION_Complete)) {
                    message = "No se pudo completar el Documento de Remito por Diferencia asociado. ";
                    if (remitoDifInv.getProcessMsg() != null){
                        message += remitoDifInv.getProcessMsg();
                    }
                    return message;
                }
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

        return null;
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

        if ((type == ModelValidator.TYPE_BEFORE_NEW) || (type == ModelValidator.TYPE_BEFORE_CHANGE)){

            // Obtengo lista de precios asociada a este precio de producto
            MPriceListVersion priceListVersion = (MPriceListVersion) model.getM_PriceList_Version();
            MPriceList priceList = priceListVersion.getPriceList();

            // Si lista de precio no es de venta, no hago nada
            if (!priceList.isSOPriceList()){
                return null;
            }

            // Si lista de precio no tiene organización asociada, no hago nada.
            if (priceList.getAD_Org_ID() <= 0){
                return null;
            }

            // Si es modificacion pero no se modifica el precio o la vigencia, no hago nada
            if (type == ModelValidator.TYPE_BEFORE_CHANGE){
                if (!model.is_ValueChanged(X_M_ProductPrice.COLUMNNAME_PriceList) && (!model.is_ValueChanged("ValidFrom"))){
                    return null;
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

        return null;
    }

    /***
     * Validaciones para el modelo de Lineas de Inventario en Retail
     * Xpande. Created by Gabriel Vila on 8/8/17.
     * @param model
     * @param type
     * @return
     * @throws Exception
     */
    public String modelChange(MInventoryLine model, int type) throws Exception {

        if ((type == ModelValidator.TYPE_BEFORE_NEW) ||
                ((type == ModelValidator.TYPE_BEFORE_CHANGE) && (model.is_ValueChanged(X_M_InventoryLine.COLUMNNAME_M_Product_ID)))){

            MProduct product = (MProduct) model.getM_Product();

            String upc = DB.getSQLValueStringEx(model.get_TrxName(), "select z_producto_ult_upc(" + product.get_ID() + ")");

            String sql = " select codigoproducto from z_difprodorg " +
                    " where ad_orgtrx_id = " + model.getAD_Org_ID() + " and m_product_id =" + product.get_ID();
            String codProdAlter = DB.getSQLValueStringEx(model.get_TrxName(), sql);

            model.set_ValueOfColumn("CodigoProducto", product.getValue());
            model.set_ValueOfColumn("UPC", upc);
            model.set_ValueOfColumn("CodProdAlter", codProdAlter);
            model.set_ValueOfColumn("Z_ProductoSeccion_ID", product.get_Value("Z_ProductoSeccion_ID"));
            model.set_ValueOfColumn("Z_ProductoRubro_ID", product.get_Value("Z_ProductoRubro_ID"));
            model.set_ValueOfColumn("Z_ProductoFamilia_ID", product.get_Value("Z_ProductoFamilia_ID"));
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

            // Precisión decimal de lista de precios de compra, si es que tengo.
            int precision = 2;
            if (model.getM_PriceList_ID() > 0){
                precision = MPriceList.getPricePrecision(model.getCtx(), model.getM_PriceList_ID());
            }

            // Si el documento es del tipo factura
            if (docType.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_APInvoice)){

                // Contador de bonificaciones asociadas a este comprobante
                sql = " select count(*) from z_invoicebonifica where c_invoice_id =" + model.get_ID();
                int contBonifLines = DB.getSQLValueEx(model.get_TrxName(), sql);

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
                            productoSocio.set_ValueOfColumn("C_Currency_1_ID", model.getC_Currency_ID());

                            // Si este comprobante tiene bonificaciones asociadas
                            if (contBonifLines > 0){
                                productoSocio.setIsBonificable(true);
                            }
                            productoSocio.saveEx();
                        }
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
                    BigDecimal amtRemitoLin = remitoDif.setRemitoDiferencia(model, invoiceLine, precision, retailConfig.getToleraRemDifLin(), false);
                    if (amtRemitoLin != null){
                        totalAmtRemito = totalAmtRemito.add(amtRemitoLin);
                    }

                    // Guardo información de compra de este producto en estructuras de reportes
                    if (invoiceLine.getM_Product_ID() > 0){
                        this.setPOInvoiceProductDay(true, model, invoiceLine, model.get_TrxName());
                    }
                }

                if (totalAmtRemito.compareTo(Env.ZERO) > 0){

                    boolean generarRemito = true;

                    // Verifico total del remito contra tolerancia sobre total de la configuración de retail
                    if (retailConfig.getToleraRemDifTot() != null){
                        if (totalAmtRemito.compareTo(retailConfig.getToleraRemDifTot()) < 0){
                            generarRemito = false;
                        }
                    }

                    if (generarRemito){
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
                    else {
                        remitoDif.deleteEx(true);
                    }
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

                // Guardo información de compra de este producto en estructuras de reportes
                for (int i = 0; i < invoiceLines.length; i++){

                    MInvoiceLine invoiceLine = invoiceLines[i];

                    if (invoiceLine.getM_Product_ID() > 0){
                        this.setPOInvoiceProductDay(false, model, invoiceLine, model.get_TrxName());
                    }
                }
            }

            // Proceso comprobantes marcados con Asiento Manual Contable
            if (model.get_ValueAsBoolean("AsientoManualInvoice")){

                int contador = -1;

                // Valido que no haya cuentas contables asociadas a impuestos que no tengan el valir deol impuesto en dicha linea.
                sql = " select count(*) as contador " +
                        " from z_invoiceastomanual am " +
                        " inner join c_elementvalue ev on am.account_id = ev.c_elementvalue_id " +
                        " where am.c_invoice_id =" + model.get_ID() +
                        " and am.c_tax_id is null " +
                        " and ev.istaxaccount='Y' ";
                contador = DB.getSQLValueEx(model.get_TrxName(), sql);
                if (contador > 0){
                    return "No se puede Completar este Documento, ya que tiene lineas de Asiento Manual con cuentas contables que requieren un valor para IMPUESTO";
                }

                // Valido que no haya cuentas contables asociadas a Retenciones que no tengan el valor de la retencion en su linea
                sql = " select count(*) as contador " +
                        " from z_invoiceastomanual am " +
                        " inner join c_elementvalue ev on am.account_id = ev.c_elementvalue_id " +
                        " where am.c_invoice_id =" + model.get_ID() +
                        " and am.z_retencionsocio_id is null " +
                        " and ev.IsRetencionAcct='Y' ";
                contador = DB.getSQLValueEx(model.get_TrxName(), sql);
                if (contador > 0){
                    return "No se puede Completar este Documento, ya que tiene lineas de Asiento Manual con cuentas contables que requieren un valor para RETENCION";
                }
            }
        }
        else if (timing == TIMING_BEFORE_REACTIVATE){

            // No aplica en comprobantes de venta
            if (model.isSOTrx()){
                return null;
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

            // Guardo información de compra de este producto en estructuras de reportes
            MInvoiceLine[] invoiceLines = model.getLines();
            boolean sumar = false;
            if (docType.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_APCredit)){
                sumar = true;
            }
            for (int i = 0; i < invoiceLines.length; i++){

                MInvoiceLine invoiceLine = invoiceLines[i];

                if (invoiceLine.getM_Product_ID() > 0){
                    this.setPOInvoiceProductDay(sumar, model, invoiceLine, model.get_TrxName());
                }
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

            // No aplica en comprobantes de venta
            if (model.isSOTrx()){
                return null;
            }
        }
        else if (timing == TIMING_BEFORE_REACTIVATE){

            // No aplica en comprobantes de venta
            if (model.isSOTrx()){
                return null;
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

    /***
     * Seteo información de compra de un producto, en las estructuras de informes.
     * Xpande. Created by Gabriel Vila on 3/13/21.
     * @param sumar
     * @param invoice
     * @param invoiceLine
     * @param trxName
     */
    private void setPOInvoiceProductDay(boolean sumar, MInvoice invoice, MInvoiceLine invoiceLine, String trxName){

        String insert, sql, action;

        try{

            BigDecimal amtSubtotalLine = (BigDecimal) invoiceLine.get_Value("AmtSubtotal");
            if (amtSubtotalLine == null) amtSubtotalLine = Env.ZERO;

            BigDecimal qtyEntered = invoiceLine.getQtyEntered();
            BigDecimal lineTotalAmt = invoiceLine.getLineTotalAmt();

            if (!sumar){
                amtSubtotalLine = amtSubtotalLine.negate();
                qtyEntered = qtyEntered.negate();
                lineTotalAmt = lineTotalAmt.negate();
            }

            sql = " select z_bi_dia_id from z_bi_dia where datetrx ='" + invoice.getDateInvoiced() + "' ";
            int zBiaDayID = DB.getSQLValueEx(trxName, sql);

            insert = " insert into z_bi_invprodday (ad_client_id, ad_org_id, dateinvoiced, c_currency_id, m_product_id, " +
                    " c_uom_id, qtypurchased, qtysold, amtsubtotal, totalamt, amtsubtotalpo, totalamtpo, z_bi_dia_id) ";

            // Verifico si no existe un registro para esta clave en la tabla de analisis
            sql = " select count(*) from z_bi_invprodday " +
                    " where ad_org_id =" + invoice.getAD_Org_ID() +
                    " and m_product_id =" + invoiceLine.getM_Product_ID() +
                    " and c_currency_id =" + invoice.getC_Currency_ID() +
                    " and dateinvoiced ='" + invoice.getDateInvoiced() + "' ";
            int contador = DB.getSQLValueEx(trxName, sql);

            // Si no existe aún un registro para esta clave
            if (contador <= 0){

                // Insert
                action = " values (" + invoice.getAD_Client_ID() + ", " + invoice.getAD_Org_ID() + ", '" +
                        invoice.getDateInvoiced() + "', " + invoice.getC_Currency_ID() + ", " +
                        invoiceLine.getM_Product_ID() + ", " + invoiceLine.getC_UOM_ID() + ", " +
                        qtyEntered + ", 0, 0, 0, " + amtSubtotalLine + ", " +
                        lineTotalAmt + ", " + zBiaDayID + ") ";

                DB.executeUpdateEx(insert + action, trxName);
            }
            else {
                // Actualizo
                action = " update z_bi_invprodday set " +
                        " qtypurchased = qtypurchased + " + qtyEntered + ", " +
                        " amtsubtotalpo = amtsubtotalpo + " + amtSubtotalLine + ", " +
                        " totalamtpo = totalamtpo + " + lineTotalAmt +
                        " where ad_org_id =" + invoice.getAD_Org_ID() +
                        " and m_product_id =" + invoiceLine.getM_Product_ID() +
                        " and c_currency_id =" + invoice.getC_Currency_ID() +
                        " and dateinvoiced ='" + invoice.getDateInvoiced() + "' ";

                DB.executeUpdateEx(action, trxName);
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

    }

}
