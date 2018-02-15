package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de lineas de remitos por diferencia en facturas de proveedores.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 2/15/18.
 */
public class MZRemitoDifInvLin extends X_Z_RemitoDifInvLin {

    public MZRemitoDifInvLin(Properties ctx, int Z_RemitoDifInvLin_ID, String trxName) {
        super(ctx, Z_RemitoDifInvLin_ID, trxName);
    }

    public MZRemitoDifInvLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
