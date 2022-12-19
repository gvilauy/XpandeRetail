package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 12/19/22.
 */
public class MZRegularOfferFile extends X_Z_RegularOfferFile {

    public MZRegularOfferFile(Properties ctx, int Z_RegularOfferFile_ID, String trxName) {
        super(ctx, Z_RegularOfferFile_ID, trxName);
    }

    public MZRegularOfferFile(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
