package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de asociación de organización a un proveedor de pos.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 2/9/18.
 */
public class MZPosVendorOrg extends X_Z_PosVendorOrg {

    public MZPosVendorOrg(Properties ctx, int Z_PosVendorOrg_ID, String trxName) {
        super(ctx, Z_PosVendorOrg_ID, trxName);
    }

    public MZPosVendorOrg(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
