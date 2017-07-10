package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xpande.core.model.I_Z_ProductoUPC;
import org.xpande.core.model.MZProductoUPC;
import org.xpande.stech.model.MZStechInterfaceOut;
import org.xpande.stech.model.X_Z_StechInterfaceOut;
import org.zkoss.zhtml.Big;

import java.math.BigDecimal;
import java.util.List;

/**
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

        // DB Validations
        engine.addModelChange(I_C_Order.Table_Name, this);
        engine.addModelChange(I_C_OrderLine.Table_Name, this);
        engine.addModelChange(I_C_Invoice.Table_Name, this);
        engine.addModelChange(I_C_InvoiceLine.Table_Name, this);

        engine.addModelChange(I_M_Product.Table_Name, this);
        engine.addModelChange(I_Z_ProductoUPC.Table_Name, this);
        engine.addModelChange(I_M_ProductPrice.Table_Name, this);

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
        else if (po.get_TableName().equalsIgnoreCase(I_Z_ProductoUPC.Table_Name)){
            return modelChange((MZProductoUPC) po, type);
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
                PriceActual = PriceActual.setScale(2, BigDecimal.ROUND_HALF_UP);
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
                        int precisionDecimal = ((MCurrency) order.getC_Currency()).getStdPrecision(); // Precision decimal compra de moneda de compra
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

        String mensaje = null;

        if ((type == ModelValidator.TYPE_BEFORE_NEW) || (type == ModelValidator.TYPE_BEFORE_CHANGE)){

            // Para comprobantes de compra en Retail, no considero una única orden de compra a nivel de cabezal.
            // ADempiere tiene una validacion en MInvoice.beforeDelete() que impide eliminar por ejemplo
            // facturas de compra cuando hay un valor en c_invoice.c_order_id.
            // Por esta razón seteo siempre c_order_id en null en el cabezal de este comprobante. ya que
            // ademas se pueden seleccionar lineas desde múltiples ordenes de compra.
            if (model.getC_Order_ID() > 0){
                model.setC_Order_ID(0);
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

            // No hago nada para comprobantes a clientes
            if (invoice.isSOTrx()) return mensaje;

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
                // Obtengo suma de impuestos para esta orden
                String sql = " select sum(coalesce(taxamt,0)) as taxamt from c_invoicetax where c_invoice_id =" + invoice.get_ID();
                BigDecimal sumImpuestos = DB.getSQLValueBDEx(model.get_TrxName(), sql);
                if (sumImpuestos != null){
                    sumImpuestos = sumImpuestos.setScale(2, BigDecimal.ROUND_HALF_UP);
                    invoice.set_ValueOfColumn("AmtSubtotal", grandTotal.subtract(sumImpuestos));
                    invoice.saveEx();
                }
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
            productPricing = new MProductPricing (orderLine.getM_Product_ID(), order.getC_BPartner_ID(), orderLine.getQtyOrdered(), false, null);
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

        // Retail. Interface salida POS
        if ((type == ModelValidator.TYPE_AFTER_NEW) || (type == ModelValidator.TYPE_AFTER_CHANGE)){

            // Si el producto no tiene información del proveedor de pos a considerar, no hago nada.
            if (model.get_ValueAsInt("Z_PosVendor_ID") <= 0){
                return mensaje;
            }

            // Si el producto no se vende, no hago nada
            if (!model.isSold()){
                return mensaje;
            }

            MZPosVendor posVendor = new MZPosVendor(model.getCtx(), model.get_ValueAsInt("Z_PosVendor_ID"), null);

            if (type == ModelValidator.TYPE_AFTER_NEW){

                // Para proveedores de POS, los nuevos productos deben pasar por un etapa de impresión de etiquetas, antes
                // de ser enviados a las cajas. Es por eso que solo se genera la marca y nada mas en este momento.
                MZStechInterfaceOut stechInterfaceOut = new MZStechInterfaceOut(model.getCtx(), 0, model.get_TrxName());
                stechInterfaceOut.setCRUDType(X_Z_StechInterfaceOut.CRUDTYPE_CREATE);
                stechInterfaceOut.setSeqNo(10);
                stechInterfaceOut.setAD_Table_ID(I_M_Product.Table_ID);
                stechInterfaceOut.setRecord_ID(model.get_ID());
                stechInterfaceOut.saveEx();

            }
            else if (type == ModelValidator.TYPE_AFTER_CHANGE){

                // Ante una actualización de un producto, verifico si hay una marca anterior para este producto.
                // Si es asi, se procede según dicha marca anterior.
                MZStechInterfaceOut stechInterfaceOut = MZStechInterfaceOut.getRecord(model.getCtx(), I_M_Product.Table_ID, model.get_ID(), model.get_TrxName());
                if ((stechInterfaceOut != null) && (stechInterfaceOut.get_ID() > 0)){
                    // Proceso segun marca que ya tenía este producto antes de su actualización.
                    // Si marca anterior es CREATE
                    if (stechInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_StechInterfaceOut.CRUDTYPE_CREATE)){
                        // No hago nada y respeto primer marca
                        return mensaje;
                    }
                    else if (stechInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_StechInterfaceOut.CRUDTYPE_DELETE)){
                        // Si marca anterior es DELETEAR, es porque el producto se inactivo anteriormente.
                        // Si este producto sigue estando inactivo
                        if (!model.isActive()){
                            // No hago nada y respeto primer marca.
                            return mensaje;
                        }
                    }
                }
                // Proceso según proveedor de POS
                if (posVendor.getValue().equalsIgnoreCase("SCANNTECH")){

                    // Las actualizaciones de producto deben viajar inmediatamente a las cajas cuando el pos es Scanntech.
                    // Envio actualización a las cajas en este momento.
                    if ((stechInterfaceOut == null) || (stechInterfaceOut.get_ID() <= 0)){
                        // No existe aun marca de UPDATE sobre este producto, la creo ahora.
                        stechInterfaceOut = new MZStechInterfaceOut(model.getCtx(), 0, model.get_TrxName());
                        stechInterfaceOut.setCRUDType(X_Z_StechInterfaceOut.CRUDTYPE_UPDATE);
                        stechInterfaceOut.setAD_Table_ID(I_M_Product.Table_ID);
                        stechInterfaceOut.setSeqNo(20);
                        stechInterfaceOut.setRecord_ID(model.get_ID());
                        stechInterfaceOut.setIsPriceChanged(false);
                        stechInterfaceOut.saveEx();
                    }
                    mensaje = stechInterfaceOut.execute(); // Info a cajas del pos en este momento.
                }
                else if (posVendor.getValue().equalsIgnoreCase("SISTECO")){
                    // Para Sisteco, las actualizaciones de producto se guardan como marcas para en un proceso posterior
                    // considerarse en la generación de un archivo plano de interface.
                    // Si no tengo marca de update, la creo ahora.
                    if ((stechInterfaceOut == null) || (stechInterfaceOut.get_ID() <= 0)){
                        // No existe aun marca de UPDATE sobre este producto, la creo ahora.
                        stechInterfaceOut = new MZStechInterfaceOut(model.getCtx(), 0, model.get_TrxName());
                        stechInterfaceOut.setCRUDType(X_Z_StechInterfaceOut.CRUDTYPE_UPDATE);
                        stechInterfaceOut.setAD_Table_ID(I_M_Product.Table_ID);
                        stechInterfaceOut.setSeqNo(20);
                        stechInterfaceOut.setRecord_ID(model.get_ID());
                        stechInterfaceOut.setIsPriceChanged(false);
                        stechInterfaceOut.saveEx();
                    }
                }
            }
        }

        return mensaje;
    }


    /***
     * Validaciones para el modelo de Códigos de Barras de Productos.
     * Xpande. Created by Gabriel Vila on 6/30/17.
     * @param model
     * @param type
     * @return
     * @throws Exception
     */
    public String modelChange(MZProductoUPC model, int type) throws Exception {

        String mensaje = null;

        // Retail. Interface salida POS
        if (type == ModelValidator.TYPE_AFTER_NEW){

            MProduct product = (MProduct)model.getM_Product();
            if (product.get_ValueAsInt("Z_PosVendor_ID") <= 0){
                return mensaje;
            }

            // Si el producto no se vende, no hago nada
            if (!product.isSold()){
                return mensaje;
            }

            MZPosVendor posVendor = new MZPosVendor(model.getCtx(), product.get_ValueAsInt("Z_PosVendor_ID"), null);

            // Para Scanntech, los nuevos códigos de barra deben enviarse inmediatamente a las cajas.
            if (posVendor.getValue().equalsIgnoreCase("SCANNTECH")){
                MZStechInterfaceOut stechInterfaceOut = new MZStechInterfaceOut(model.getCtx(), 0, model.get_TrxName());
                stechInterfaceOut.setCRUDType(X_Z_StechInterfaceOut.CRUDTYPE_CREATE);
                stechInterfaceOut.setAD_Table_ID(I_Z_ProductoUPC.Table_ID);
                stechInterfaceOut.setRecord_ID(model.get_ID());
                stechInterfaceOut.setSeqNo(15);
                stechInterfaceOut.saveEx();

                stechInterfaceOut.execute(); // Envío inmediato a las cajas del pos
            }
            else if (posVendor.getValue().equalsIgnoreCase("SISTECO")){
                // Para Sisteco, solo se crea la marca de nuevo y para luego considerarse en la generación del archivo plano.
                MZStechInterfaceOut stechInterfaceOut = new MZStechInterfaceOut(model.getCtx(), 0, model.get_TrxName());
                stechInterfaceOut.setCRUDType(X_Z_StechInterfaceOut.CRUDTYPE_CREATE);
                stechInterfaceOut.setAD_Table_ID(I_Z_ProductoUPC.Table_ID);
                stechInterfaceOut.setRecord_ID(model.get_ID());
                stechInterfaceOut.setSeqNo(15);
                stechInterfaceOut.saveEx();
            }
        }
        else if (type == ModelValidator.TYPE_AFTER_DELETE){

            MProduct product = (MProduct)model.getM_Product();
            if (product.get_ValueAsInt("Z_PosVendor_ID") <= 0){
                return mensaje;
            }

            // Si el producto no se vende, no hago nada
            if (!product.isSold()){
                return mensaje;
            }

            MZPosVendor posVendor = new MZPosVendor(model.getCtx(), product.get_ValueAsInt("Z_PosVendor_ID"), null);

            // Para Scanntech, la eliminación de códigos de barra deben enviarse inmediatamente a las cajas.
            if (posVendor.getValue().equalsIgnoreCase("SCANNTECH")){
                MZStechInterfaceOut stechInterfaceOut = new MZStechInterfaceOut(model.getCtx(), 0, model.get_TrxName());
                stechInterfaceOut.setCRUDType(X_Z_StechInterfaceOut.CRUDTYPE_DELETE);
                stechInterfaceOut.setAD_Table_ID(I_Z_ProductoUPC.Table_ID);
                stechInterfaceOut.setRecord_ID(model.get_ID());
                stechInterfaceOut.setSeqNo(13);
                stechInterfaceOut.saveEx();

                stechInterfaceOut.execute(); // Envío inmediato a las cajas del pos
            }
            else if (posVendor.getValue().equalsIgnoreCase("SISTECO")){
                // Para Sisteco, solo se crea la marca de eliminación y para luego considerarse en la generación del archivo plano.
                MZStechInterfaceOut stechInterfaceOut = new MZStechInterfaceOut(model.getCtx(), 0, model.get_TrxName());
                stechInterfaceOut.setCRUDType(X_Z_StechInterfaceOut.CRUDTYPE_DELETE);
                stechInterfaceOut.setAD_Table_ID(I_Z_ProductoUPC.Table_ID);
                stechInterfaceOut.setRecord_ID(model.get_ID());
                stechInterfaceOut.setSeqNo(13);
                stechInterfaceOut.saveEx();
            }

        }

        return mensaje;
    }

    /***
     * Validaciones para el modelo de Precios de Productos
     * Xpande. Created by Gabriel Vila on 6/30/17.
     * @param model
     * @param type
     * @return
     * @throws Exception
     */
    public String modelChange(MProductPrice model, int type) throws Exception {

        String mensaje = null;

        // Retail. Interface salida POS
        if ((type == ModelValidator.TYPE_BEFORE_NEW) || (type == ModelValidator.TYPE_BEFORE_CHANGE)){

            // Solo listas de ventas con organización distinto de *
            MPriceListVersion priceListVersion = new MPriceListVersion(model.getCtx(), model.getM_PriceList_Version_ID(), null);
            MPriceList priceList = priceListVersion.getPriceList();
            if (!priceList.isSOPriceList()) return mensaje;
            if (priceList.getAD_Org_ID() == 0) return mensaje;

            MProduct product = (MProduct)model.getM_Product();
            if (product.get_ValueAsInt("Z_PosVendor_ID") <= 0){
                return mensaje;
            }

            // Si el producto no se vende, no hago nada
            if (!product.isSold()){
                return mensaje;
            }

            MZPosVendor posVendor = new MZPosVendor(model.getCtx(), product.get_ValueAsInt("Z_PosVendor_ID"), null);

            if (type == ModelValidator.TYPE_AFTER_NEW){
                // Para proveedores de POS, los nuevos productos deben pasar por un etapa de impresión de etiquetas, antes
                // de ser enviados a las cajas. Es por eso que solo se genera la marca y nada mas en este momento.
                MZStechInterfaceOut stechInterfaceOut = new MZStechInterfaceOut(model.getCtx(), 0, model.get_TrxName());
                stechInterfaceOut.setCRUDType(X_Z_StechInterfaceOut.CRUDTYPE_UPDATE);
                stechInterfaceOut.setAD_Table_ID(I_M_Product.Table_ID);
                stechInterfaceOut.setRecord_ID(product.get_ID());
                stechInterfaceOut.setSeqNo(30);
                stechInterfaceOut.setIsPriceChanged(true);
                stechInterfaceOut.setAD_OrgTrx_ID(priceList.getAD_Org_ID());
                stechInterfaceOut.saveEx();
            }
            else if (type == ModelValidator.TYPE_AFTER_CHANGE){

                // Ante una actualización de precio de un producto, verifico si hay una marca anterior para este producto.
                // Si es asi, se procede según dicha marca anterior.
                MZStechInterfaceOut stechInterfaceOut = MZStechInterfaceOut.getRecord(model.getCtx(), I_M_Product.Table_ID, model.get_ID(), model.get_TrxName());
                if ((stechInterfaceOut != null) && (stechInterfaceOut.get_ID() > 0)){
                    // Proceso segun marca que ya tenía este producto antes de su actualización.
                    // Si marca anterior es CREATE
                    if (stechInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_StechInterfaceOut.CRUDTYPE_CREATE)){
                        // No hago nada y respeto primer marca
                        return mensaje;
                    }
                    else if (stechInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_StechInterfaceOut.CRUDTYPE_DELETE)){
                        // Si marca anterior es DELETEAR, es porque el producto se inactivo anteriormente.
                        // Si este producto sigue estando inactivo
                        if (!model.isActive()){
                            // No hago nada y respeto primer marca.
                            return mensaje;
                        }
                    }
                    else if (stechInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_StechInterfaceOut.CRUDTYPE_UPDATE)){
                        // Si tenia marca anterior de cambio de precio para este producto, no hago nada
                        if (stechInterfaceOut.isPriceChanged()){
                            return  mensaje;
                        }
                    }
                }

                // Para proveedores de pos, los cambios de precios se marcan pero se procesan luego (primero se imprimen etiquetas antes de enviar
                // cambios de precios a las cajas).
                stechInterfaceOut = new MZStechInterfaceOut(model.getCtx(), 0, model.get_TrxName());
                stechInterfaceOut.setCRUDType(X_Z_StechInterfaceOut.CRUDTYPE_UPDATE);
                stechInterfaceOut.setAD_Table_ID(I_M_Product.Table_ID);
                stechInterfaceOut.setRecord_ID(product.get_ID());
                stechInterfaceOut.setSeqNo(30);
                stechInterfaceOut.setIsPriceChanged(true);
                stechInterfaceOut.setAD_OrgTrx_ID(priceList.getAD_Org_ID());
                stechInterfaceOut.saveEx();
            }

        }

        return mensaje;
    }

}
