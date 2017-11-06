package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para configuración de interface en Retail contra DGC.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 11/6/17.
 */
public class MZRetailConfigDGC extends X_Z_RetailConfigDGC {

    public MZRetailConfigDGC(Properties ctx, int Z_RetailConfigDGC_ID, String trxName) {
        super(ctx, Z_RetailConfigDGC_ID, trxName);
    }

    public MZRetailConfigDGC(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
