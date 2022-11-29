package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 11/16/22.
 */
public class MZCreditLineAudit extends X_Z_CreditLineAudit {

    public MZCreditLineAudit(Properties ctx, int Z_CreditLineAudit_ID, String trxName) {
        super(ctx, Z_CreditLineAudit_ID, trxName);
    }

    public MZCreditLineAudit(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
