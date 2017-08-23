package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para rubros de productos en Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/22/17.
 */
public class MZProductoRubro extends X_Z_ProductoRubro {

    public MZProductoRubro(Properties ctx, int Z_ProductoRubro_ID, String trxName) {
        super(ctx, Z_ProductoRubro_ID, trxName);
    }

    public MZProductoRubro(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
