package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 10/29/21.
 */
public class MZCredLineGenBP extends X_Z_CredLineGenBP {

    public MZCredLineGenBP(Properties ctx, int Z_CredLineGenBP_ID, String trxName) {
        super(ctx, Z_CredLineGenBP_ID, trxName);
    }

    public MZCredLineGenBP(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
