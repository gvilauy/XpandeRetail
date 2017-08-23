package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para secciones de productos en Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/22/17.
 */
public class MZProductoSeccion extends X_Z_ProductoSeccion {

    public MZProductoSeccion(Properties ctx, int Z_ProductoSeccion_ID, String trxName) {
        super(ctx, Z_ProductoSeccion_ID, trxName);
    }

    public MZProductoSeccion(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
