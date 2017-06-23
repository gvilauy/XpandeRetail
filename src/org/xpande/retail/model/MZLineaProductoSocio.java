package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de linea de productos de un socio de negocio.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/21/17.
 */
public class MZLineaProductoSocio extends X_Z_LineaProductoSocio {

    public MZLineaProductoSocio(Properties ctx, int Z_LineaProductoSocio_ID, String trxName) {
        super(ctx, Z_LineaProductoSocio_ID, trxName);
    }

    public MZLineaProductoSocio(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
