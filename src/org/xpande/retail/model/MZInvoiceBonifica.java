package org.xpande.retail.model;

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

        // Para bonificaciones manuales, al crearse le seteo cantidades en cero
        if ((newRecord) && (this.isManual())){
            this.setQtyBase(Env.ZERO);
            this.setQtyCalculated(Env.ZERO);
            this.setQtyReward(Env.ZERO);
        }

        return true;
    }

    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {

        if (!success) return success;

        // Marco la linea de factura asociada con esta bonificacion, como bonificada
        String action = " update c_invoiceline set IsBonificada ='Y' where c_invoiceline_id =" + this.getC_InvoiceLine_ID();
        DB.executeUpdateEx(action, get_TrxName());

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
        int contador = DB.getSQLValueEx(get_TrxName(), " select count(*) from z_invoicebonifica where c_invoiceline_id =" + this.getC_InvoiceLine_ID());
        if (contador <= 0){
            String action = " update c_invoiceline set IsBonificada ='N' where c_invoiceline_id =" + this.getC_InvoiceLine_ID();
            DB.executeUpdateEx(action, get_TrxName());
        }

        return true;
    }
}