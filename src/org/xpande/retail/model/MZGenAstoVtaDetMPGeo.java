package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 3/9/22.
 */
public class MZGenAstoVtaDetMPGeo extends X_Z_GenAstoVtaDetMPGeo {

    public MZGenAstoVtaDetMPGeo(Properties ctx, int Z_GenAstoVtaDetMPGeo_ID, String trxName) {
        super(ctx, Z_GenAstoVtaDetMPGeo_ID, trxName);
    }

    public MZGenAstoVtaDetMPGeo(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
