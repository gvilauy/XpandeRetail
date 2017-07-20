package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para organizaciones a procesar en la actualización PVP.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/19/17.
 */
public class MZActualizacionPVPOrg extends X_Z_ActualizacionPVPOrg {

    public MZActualizacionPVPOrg(Properties ctx, int Z_ActualizacionPVPOrg_ID, String trxName) {
        super(ctx, Z_ActualizacionPVPOrg_ID, trxName);
    }

    public MZActualizacionPVPOrg(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
