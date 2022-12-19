package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 12/19/22.
 */
public class MZRegOfferLineOrg extends X_Z_RegOfferLineOrg {

    public MZRegOfferLineOrg(Properties ctx, int Z_RegOfferLineOrg_ID, String trxName) {
        super(ctx, Z_RegOfferLineOrg_ID, trxName);
    }

    public MZRegOfferLineOrg(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
