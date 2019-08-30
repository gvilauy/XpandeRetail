package org.xpande.retail.model;

import org.xpande.financial.model.MZPago;
import org.xpande.financial.model.X_Z_PagoLin;

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

        if ((is_ValueChanged(X_Z_FormEfectivoLin.COLUMNNAME_AmtSubtotal1)) || (is_ValueChanged(X_Z_FormEfectivoLin.COLUMNNAME_AmtSubtotal2))){

            // Actualizo totales del documento
            MZFormEfectivo formEfectivo = (MZFormEfectivo) this.getZ_FormEfectivo();
            formEfectivo.updateTotals();

        }

        return true;

    }
}
