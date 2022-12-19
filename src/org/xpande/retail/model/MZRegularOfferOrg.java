package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 12/19/22.
 */
public class MZRegularOfferOrg extends X_Z_RegularOfferOrg {

    public MZRegularOfferOrg(Properties ctx, int Z_RegularOfferOrg_ID, String trxName) {
        super(ctx, Z_RegularOfferOrg_ID, trxName);
    }

    public MZRegularOfferOrg(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
