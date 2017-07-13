package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para lineas de productos que luego se asociaran a socios de negocios, con la particularidad
 * que uno de esos socios sera el dueños de esta linea y los demás serán distribuidores.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/13/17.
 */
public class MZLineaProducto extends X_Z_LineaProducto {

    public MZLineaProducto(Properties ctx, int Z_LineaProducto_ID, String trxName) {
        super(ctx, Z_LineaProducto_ID, trxName);
    }

    public MZLineaProducto(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
