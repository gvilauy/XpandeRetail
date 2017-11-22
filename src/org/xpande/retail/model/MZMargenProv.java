package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para definicion de margenes por proveedor.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 11/22/17.
 */
public class MZMargenProv extends X_Z_MargenProv {

    public MZMargenProv(Properties ctx, int Z_MargenProv_ID, String trxName) {
        super(ctx, Z_MargenProv_ID, trxName);
    }

    public MZMargenProv(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
