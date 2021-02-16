package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para apertura de concepto de formulario de efectivo por cajas.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 9/16/19.
 */
public class MZFormEfectivoCaja extends X_Z_FormEfectivoCaja {

    public MZFormEfectivoCaja(Properties ctx, int Z_FormEfectivoCaja_ID, String trxName) {
        super(ctx, Z_FormEfectivoCaja_ID, trxName);
    }

    public MZFormEfectivoCaja(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {

        if (!success) return false;

        if ((newRecord) || (is_ValueChanged(X_Z_FormEfectivoLin.COLUMNNAME_AmtSubtotal1)) || (is_ValueChanged(X_Z_FormEfectivoLin.COLUMNNAME_AmtSubtotal2))){

            // Actualizo linea del concepto asociado a esta caja
            MZFormEfectivoLin efectivoLin = (MZFormEfectivoLin) this.getZ_FormEfectivoLin();
            efectivoLin.updateTotals();

            // Actualizo totales del documento
            MZFormEfectivo formEfectivo = (MZFormEfectivo) efectivoLin.getZ_FormEfectivo();
            formEfectivo.updateTotals();
        }

        return true;
    }

    @Override
    protected boolean afterDelete(boolean success) {

        if (!success) return false;

        // Actualizo linea del concepto asociado a esta caja
        MZFormEfectivoLin efectivoLin = (MZFormEfectivoLin) this.getZ_FormEfectivoLin();
        efectivoLin.updateTotals();

        return true;
    }
}
