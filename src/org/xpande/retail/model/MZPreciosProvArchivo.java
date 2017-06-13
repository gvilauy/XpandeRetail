package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de linea leída desde archivo de interface de precios de proveedor.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/13/17.
 */
public class MZPreciosProvArchivo extends X_Z_PreciosProvArchivo {

    public MZPreciosProvArchivo(Properties ctx, int Z_PreciosProvArchivo_ID, String trxName) {
        super(ctx, Z_PreciosProvArchivo_ID, trxName);
    }

    public MZPreciosProvArchivo(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
