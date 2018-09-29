package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para carga mediante archivo de datos en proceso de Actualizacion PVP.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 5/15/18.
 */
public class MZActualizacionPVPArch extends X_Z_ActualizacionPVPArch {

    public MZActualizacionPVPArch(Properties ctx, int Z_ActualizacionPVPArch_ID, String trxName) {
        super(ctx, Z_ActualizacionPVPArch_ID, trxName);
    }

    public MZActualizacionPVPArch(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
