package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para familia de productos en Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/22/17.
 */
public class MZProductoFamilia extends X_Z_ProductoFamilia {

    public MZProductoFamilia(Properties ctx, int Z_ProductoFamilia_ID, String trxName) {
        super(ctx, Z_ProductoFamilia_ID, trxName);
    }

    public MZProductoFamilia(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
