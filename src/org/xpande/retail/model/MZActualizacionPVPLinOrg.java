package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de organización de linea de proceso en actualizacion PVP.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/19/17.
 */
public class MZActualizacionPVPLinOrg extends X_Z_ActualizacionPVPLinOrg {

    public MZActualizacionPVPLinOrg(Properties ctx, int Z_ActualizacionPVPLinOrg_ID, String trxName) {
        super(ctx, Z_ActualizacionPVPLinOrg_ID, trxName);
    }

    public MZActualizacionPVPLinOrg(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
