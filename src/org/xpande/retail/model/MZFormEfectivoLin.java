package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xpande.financial.model.MZPago;
import org.xpande.financial.model.X_Z_PagoLin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para linea en el documento de Formulario de Movimientos de Efectivo en Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/29/19.
 */
public class MZFormEfectivoLin extends X_Z_FormEfectivoLin {

    public MZFormEfectivoLin(Properties ctx, int Z_FormEfectivoLin_ID, String trxName) {
        super(ctx, Z_FormEfectivoLin_ID, trxName);
    }

    public MZFormEfectivoLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {

        if (!success) return success;

        if (newRecord) return success;

        if ((is_ValueChanged(X_Z_FormEfectivoLin.COLUMNNAME_AmtSubtotal1)) || (is_ValueChanged(X_Z_FormEfectivoLin.COLUMNNAME_AmtSubtotal2))) {

            // Actualizo totales del documento
            MZFormEfectivo formEfectivo = (MZFormEfectivo) this.getZ_FormEfectivo();
            formEfectivo.updateTotals();

        }

        return true;

    }

    /***
     * Actualiza montos de esta linea.
     * Xpande. Created by Gabriel Vila on 9/16/19.
     */
    public void updateTotals() {

        String sql = "", action = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            sql = " select coalesce(sum(amtsubtotal1),0) as monto1, coalesce(sum(amtsubtotal2),0) as monto2 " +
                    " from z_formefectivocaja " +
                    " where z_formefectivolin_id =" + this.get_ID();

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                action = " update z_formefectivolin set amtsubtotal1 =" + rs.getBigDecimal("monto1") + ", " +
                        " amtsubtotal2 =" + rs.getBigDecimal("monto2") +
                        " where z_formefectivolin_id =" + this.get_ID();
                DB.executeUpdateEx(action, get_TrxName());
            }

        } catch (Exception e) {
            throw new AdempiereException(e);
        } finally {
            DB.close(rs, pstmt);
            rs = null;
            pstmt = null;
        }
    }

}