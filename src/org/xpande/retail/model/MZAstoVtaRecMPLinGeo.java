package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 3/17/22.
 */
public class MZAstoVtaRecMPLinGeo extends X_Z_AstoVtaRecMPLinGeo {

    public MZAstoVtaRecMPLinGeo(Properties ctx, int Z_AstoVtaRecMPLinGeo_ID, String trxName) {
        super(ctx, Z_AstoVtaRecMPLinGeo_ID, trxName);
    }

    public MZAstoVtaRecMPLinGeo(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
