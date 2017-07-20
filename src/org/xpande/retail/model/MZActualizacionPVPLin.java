package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de linea de proceso de actualización PVP.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/19/17.
 */
public class MZActualizacionPVPLin extends X_Z_ActualizacionPVPLin {

    public MZActualizacionPVPLin(Properties ctx, int Z_ActualizacionPVPLin_ID, String trxName) {
        super(ctx, Z_ActualizacionPVPLin_ID, trxName);
    }

    public MZActualizacionPVPLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {

        if (!success) return success;

        if (newRecord){

        }

        return true;
    }
}
