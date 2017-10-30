package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.acct.Doc;
import org.compiere.model.*;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xpande.core.model.MZActividadDocumento;
import org.xpande.core.model.MZProductoUPC;
import org.xpande.retail.utils.ProductPricesInfo;

import java.math.BigDecimal;
import java.sql.Timestamp;

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

            // Calcula descuento manual
            BigDecimal Discount2 = (BigDecimal)model.get_Value("Discount2");
            BigDecimal PriceActual = model.getPriceActual();
            if (Discount2 == null) Discount2 = Env.ZERO;
            if (model.getPriceList().doubleValue() != 0 ){
                PriceActual = new BigDecimal ((100.0 - model.getDiscount().doubleValue()) / 100.0 * model.getPriceList().doubleValue());
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
        }

        if ((type == ModelValidator.TYPE_BEFORE_NEW) || (type == ModelValidator.TYPE_BEFORE_CHANGE)
                || (type == ModelValidator.TYPE_BEFORE_DELETE)){

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


    /**
     * 	Get and calculate Product Pricing
     *	@param orderLine
     *	@return product pricing
     */
    private MProductPricing getProductPricing (MOrderLine orderLine, MOrder order) {
        MProductPricing productPricing = null;

        try{
            productPricing = new MProductPricing (orderLine.getM_Product_ID(), order.getC_BPartner_ID(), order.getAD_Org_ID(), orderLine.getQtyOrdered(), false, null);
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
                                productoSocio.setVendorProductNo(vendorProductNo);
                                productoSocio.saveEx();
                            }
                            else{
                                MProduct prod = (MProduct) mInOutLine.getM_Product();
                                return "No se pudo obtener información de producto-socio para el producto : " + prod.getValue() + " - " + prod.getName();
                            }
                        }
                    }
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

        String mensaje = null;

        if ((type == ModelValidator.TYPE_BEFORE_NEW) || (type == ModelValidator.TYPE_BEFORE_CHANGE)){

            // Me aseguro nombre corto del producto (campo: Description) con un máximo de 30 caracteres
            if ((model.getDescription() == null) || (model.getDescription().trim().equalsIgnoreCase(""))){
                return "Debe indicar Nombre Corto para el nuevo Producto";
            }
            String nombreCorto = model.getDescription().trim();
            if (nombreCorto.length() > 30){
                nombreCorto = nombreCorto.substring(0,30);
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

        if (timing == TIMING_AFTER_COMPLETE){

            // Calculo de descuentos por Notas de Credito al Pago.

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
            actividadDocumento.saveEx();

            // No aplica en comprobantes de venta cuyo documento no sea del tipo API (Facturas)
            MDocType docType = (MDocType) model.getC_DocTypeTarget();
            if (!docType.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_APInvoice)){
                return null;
            }

            // Precisión decimal de lista de precios de compra, si es que tengo.
            int precision = 2;
            if (model.getM_PriceList_ID() > 0){
                precision = MPriceList.getPricePrecision(model.getCtx(), model.getM_PriceList_ID());
            }

            // Recorro lineas del comprobante y si corresponde calculo precio con descuento por NC al pago
            boolean hayDescuntoNC = false;

            for (int i = 0; i < invoiceLines.length; i++){
                MInvoiceLine invoiceLine = invoiceLines[i];
                if (invoiceLine.getM_Product_ID() > 0){
                    if ((invoiceLine.getPriceEntered() != null) && (invoiceLine.getPriceEntered().compareTo(Env.ZERO) > 0)){
                        // Instancio modelo producto-socio, y si este modelo tiene pauta comercial asociada, calculo descuentos por NC al pago.
                        MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(Env.getCtx(), model.getC_BPartner_ID(), invoiceLine.getM_Product_ID(), null);
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
                                        DB.executeUpdateEx(action, model.get_TrxName());

                                        hayDescuntoNC = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (hayDescuntoNC){
                action = " update c_invoice set TieneDtosNC ='Y' " +
                        " where c_invoice_id =" + model.get_ID();
                DB.executeUpdateEx(action, model.get_TrxName());
            }

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

            // No aplica en comprobantes de venta cuyo documento no sea del tipo API (Facturas)
            MDocType docType = (MDocType) model.getC_DocTypeTarget();
            if (!docType.getDocBaseType().equalsIgnoreCase(Doc.DOCTYPE_APInvoice)){
                return null;
            }

            action = " update c_invoiceline set PriceDtoNC = null, " +
                     " AmtDtoNC = null, " +
                     " TieneDtosNC ='N' " +
                     " where c_invoice_id =" + model.get_ID();
            DB.executeUpdateEx(action, model.get_TrxName());

            action = " update c_invoice set TieneDtosNC ='N' " +
                    " where c_invoice_id =" + model.get_ID();
            DB.executeUpdateEx(action, model.get_TrxName());

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

}
