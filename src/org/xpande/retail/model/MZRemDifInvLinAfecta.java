package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de afectación de linea de remito por diferencia de facturación de proveedores.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 2/27/18.
 */
public class MZRemDifInvLinAfecta extends X_Z_RemDifInvLinAfecta {

    public MZRemDifInvLinAfecta(Properties ctx, int Z_RemDifInvLinAfecta_ID, String trxName) {
        super(ctx, Z_RemDifInvLinAfecta_ID, trxName);
    }

    public MZRemDifInvLinAfecta(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
