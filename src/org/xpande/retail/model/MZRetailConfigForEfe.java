package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para conceptos de formulario de movimientos de efectivo en Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/29/19.
 */
public class MZRetailConfigForEfe extends X_Z_RetailConfigForEfe {

    public MZRetailConfigForEfe(Properties ctx, int Z_RetailConfigForEfe_ID, String trxName) {
        super(ctx, Z_RetailConfigForEfe_ID, trxName);
    }

    public MZRetailConfigForEfe(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
