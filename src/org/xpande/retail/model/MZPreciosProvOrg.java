package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de organización que se procesa en la gestión de precios de proveedor.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/15/17.
 */
public class MZPreciosProvOrg extends X_Z_PreciosProvOrg {

    public MZPreciosProvOrg(Properties ctx, int Z_PreciosProvOrg_ID, String trxName) {
        super(ctx, Z_PreciosProvOrg_ID, trxName);
    }

    public MZPreciosProvOrg(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
