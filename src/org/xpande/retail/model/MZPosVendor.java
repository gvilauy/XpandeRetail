package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para gestión de proveedores de POS.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/6/17.
 */
public class MZPosVendor extends X_Z_PosVendor {

    public MZPosVendor(Properties ctx, int Z_PosVendor_ID, String trxName) {
        super(ctx, Z_PosVendor_ID, trxName);
    }

    public MZPosVendor(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
