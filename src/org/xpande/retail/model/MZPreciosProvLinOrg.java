package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para diferencial de organizaciones para linea de documento de gestión de precios por proveedor.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/23/17.
 */
public class MZPreciosProvLinOrg extends X_Z_PreciosProvLinOrg {

    public MZPreciosProvLinOrg(Properties ctx, int Z_PreciosProvLinOrg_ID, String trxName) {
        super(ctx, Z_PreciosProvLinOrg_ID, trxName);
    }

    public MZPreciosProvLinOrg(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

}
