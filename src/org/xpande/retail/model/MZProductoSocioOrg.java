package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Organización con precios diferenciales de compra y/o venta para un producto-socio de negocio.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/19/17.
 */
public class MZProductoSocioOrg extends X_Z_ProductoSocioOrg {

    public MZProductoSocioOrg(Properties ctx, int Z_ProductoSocioOrg_ID, String trxName) {
        super(ctx, Z_ProductoSocioOrg_ID, trxName);
    }

    public MZProductoSocioOrg(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

}
