package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Linea de productos asociada a una definición de márgenes de proveedor.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 11/22/17.
 */
public class MZMargenProvLinea extends X_Z_MargenProvLinea {

    public MZMargenProvLinea(Properties ctx, int Z_MargenProvLinea_ID, String trxName) {
        super(ctx, Z_MargenProvLinea_ID, trxName);
    }

    public MZMargenProvLinea(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
