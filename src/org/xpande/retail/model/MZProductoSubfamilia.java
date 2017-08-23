package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para subfamilia de productos en Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/22/17.
 */
public class MZProductoSubfamilia extends X_Z_ProductoSubfamilia {

    public MZProductoSubfamilia(Properties ctx, int Z_ProductoSubfamilia_ID, String trxName) {
        super(ctx, Z_ProductoSubfamilia_ID, trxName);
    }

    public MZProductoSubfamilia(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
