package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo linea de impresión simple de etiquetas.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/31/17.
 */
public class MZImpEtiqSimpleLin extends X_Z_ImpEtiqSimpleLin {

    public MZImpEtiqSimpleLin(Properties ctx, int Z_ImpEtiqSimpleLin_ID, String trxName) {
        super(ctx, Z_ImpEtiqSimpleLin_ID, trxName);
    }

    public MZImpEtiqSimpleLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

}
