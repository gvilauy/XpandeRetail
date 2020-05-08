package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 5/8/20.
 */
public class MZAstoVtaRecMPLinSC extends X_Z_AstoVtaRecMPLinSC {

    public MZAstoVtaRecMPLinSC(Properties ctx, int Z_AstoVtaRecMPLinSC_ID, String trxName) {
        super(ctx, Z_AstoVtaRecMPLinSC_ID, trxName);
    }

    public MZAstoVtaRecMPLinSC(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
