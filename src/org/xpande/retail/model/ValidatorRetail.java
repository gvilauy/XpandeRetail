package org.xpande.retail.model;

import org.compiere.model.*;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.math.BigDecimal;

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

        // DB Validations
        //engine.addModelChange(I_M_Product.Table_Name, this);
        engine.addModelChange(I_C_Order.Table_Name, this);
        engine.addModelChange(I_C_OrderLine.Table_Name, this);
        engine.addModelChange(I_C_Invoice.Table_Name, this);
        engine.addModelChange(I_C_InvoiceLine.Table_Name, this);

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

        return null;
    }

    @Override
    public String docValidate(PO po, int timing) {
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
}
