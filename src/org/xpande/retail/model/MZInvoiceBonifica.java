package org.xpande.retail.model;

import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para bonificación en unidades asociada a una linea de factura.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 4/26/18.
 */
public class MZInvoiceBonifica extends X_Z_InvoiceBonifica {

    public MZInvoiceBonifica(Properties ctx, int Z_InvoiceBonifica_ID, String trxName) {
        super(ctx, Z_InvoiceBonifica_ID, trxName);
    }

    public MZInvoiceBonifica(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    @Override
    protected boolean beforeSave(boolean newRecord) {

        if (this.getTipoBonificaQty().equalsIgnoreCase(X_Z_InvoiceBonifica.TIPOBONIFICAQTY_MISMOPRODUCTO)){
            this.setM_Product_To_ID(this.getM_Product_ID());
        }

        // En nuevos registros, obtengo precio OC del producto bonificado
        if ((newRecord) || (is_ValueChanged(X_Z_InvoiceBonifica.COLUMNNAME_M_Product_ID)) || (is_ValueChanged(X_Z_InvoiceBonifica.COLUMNNAME_M_Product_To_ID))){

            // Si es una bonificacion simple del mismo producto, el precio OC = precio facturado
            if ((this.getM_Product_To_ID() > 0) && (this.getM_Product_ID() == this.getM_Product_To_ID())){
                MInvoiceLine invoiceLine = (MInvoiceLine) this.getC_InvoiceLine();
                this.setPricePO(invoiceLine.getPriceEntered());
            }
            else{
                if (this.getM_Product_To_ID() > 0){
                    MInvoice invoice = (MInvoice) this.getC_Invoice();
                    MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(getCtx(), invoice.getC_BPartner_ID(), this.getM_Product_To_ID(), null);
                    if ((productoSocio != null) && (productoSocio.getZ_ProductoSocio_ID() > 0)){
                        this.setPricePO(productoSocio.getPricePO());
                    }
                }
            }
        }

        // Para bonificaciones manuales, al crearse le seteo cantidades en cero
        if ((newRecord) && (this.isManual())){
            this.setQtyBase(Env.ZERO);
            this.setQtyCalculated(Env.ZERO);
            if (this.getQtyReward() == null){
                this.setQtyReward(Env.ZERO);
            }
        }

        return true;
    }

    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {

        if (!success) return success;

        // Marco la linea de factura asociada con esta bonificacion, como bonificada
        if (this.getC_InvoiceLine_ID() > 0){
            String action = " update c_invoiceline set IsBonificada ='Y' where c_invoiceline_id =" + this.getC_InvoiceLine_ID();
            DB.executeUpdateEx(action, get_TrxName());
        }

        return true;
    }

    @Override
    protected boolean beforeDelete() {

        if (!this.isManual()){
            log.saveError("ATENCIÓN", "No es posible Eliminar una Bonificación que viene de Pautas Comerciales.\n" +
                                                           "Si no hay bonificación digite cantidad bonificada = CERO.");
            return false;

        }

        return true;
    }

    @Override
    protected boolean afterDelete(boolean success) {

        if (!success) return success;

        // En caso de no tener mas lineas de bonificación para la linea de facttura asociada a esta bonificacion,
        // marco la linea de factura como no bonificada
        if (this.getC_InvoiceLine_ID() > 0){
            int contador = DB.getSQLValueEx(get_TrxName(), " select count(*) from z_invoicebonifica where c_invoiceline_id =" + this.getC_InvoiceLine_ID());
            if (contador <= 0){
                String action = " update c_invoiceline set IsBonificada ='N' where c_invoiceline_id =" + this.getC_InvoiceLine_ID();
                DB.executeUpdateEx(action, get_TrxName());
            }
        }

        return true;
    }
}
