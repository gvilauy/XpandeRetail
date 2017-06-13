package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de lineas (productos) del proceso de mantenimiento de precios de proveedor.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/13/17.
 */
public class MZPreciosProvLin extends X_Z_PreciosProvLin {

    public MZPreciosProvLin(Properties ctx, int Z_PreciosProvLin_ID, String trxName) {
        super(ctx, Z_PreciosProvLin_ID, trxName);
    }

    public MZPreciosProvLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
