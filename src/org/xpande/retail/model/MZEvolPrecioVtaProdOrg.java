package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para registrar evolución de precios de venta por producto-organización en Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/8/17.
 */
public class MZEvolPrecioVtaProdOrg extends X_Z_EvolPrecioVtaProdOrg {

    public MZEvolPrecioVtaProdOrg(Properties ctx, int Z_EvolPrecioVtaProdOrg_ID, String trxName) {
        super(ctx, Z_EvolPrecioVtaProdOrg_ID, trxName);
    }

    public MZEvolPrecioVtaProdOrg(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
