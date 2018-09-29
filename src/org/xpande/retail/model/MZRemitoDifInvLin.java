package org.xpande.retail.model;

import org.compiere.util.DB;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de lineas de remitos por diferencia en facturas de proveedores.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 2/15/18.
 */
public class MZRemitoDifInvLin extends X_Z_RemitoDifInvLin {

    public MZRemitoDifInvLin(Properties ctx, int Z_RemitoDifInvLin_ID, String trxName) {
        super(ctx, Z_RemitoDifInvLin_ID, trxName);
    }

    public MZRemitoDifInvLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {

        if (!success) return success;

        // Si no es nuevo registro, y se modifico el atributo de linea cerrada
        if (!newRecord){
            if (is_ValueChanged(X_Z_RemitoDifInvLin.COLUMNNAME_IsClosed)){

                String action = "";

                // Actualizo estado de documento del remito de esta linea, segun estado de sus lineas.
                String sql = " select count(*) as contador from z_remitodifinvlin " +
                        " where z_remitodifinv_id =" + this.getZ_RemitoDifInv_ID() +
                        " and isclosed ='N'";
                int contador = DB.getSQLValueEx(get_TrxName(), sql);
                if (contador > 0){
                    action = " update z_remitodifinv set docstatus='CO', docaction='CL' where z_remitodifinv_id =" + this.getZ_RemitoDifInv_ID();
                }
                else{
                    action = " update z_remitodifinv set docstatus='CL', docaction='--' where z_remitodifinv_id =" + this.getZ_RemitoDifInv_ID();
                }
                DB.executeUpdateEx(action, get_TrxName());
            }
        }

        return true;
    }
}
