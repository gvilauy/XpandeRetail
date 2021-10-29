package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 10/29/21.
 */
public class MZCreditLineCategory extends X_Z_CreditLineCategory {

    public MZCreditLineCategory(Properties ctx, int Z_CreditLineCategory_ID, String trxName) {
        super(ctx, Z_CreditLineCategory_ID, trxName);
    }

    public MZCreditLineCategory(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
