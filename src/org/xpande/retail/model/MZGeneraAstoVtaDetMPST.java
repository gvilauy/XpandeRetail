package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de detalle de venta por medio de pago, mediante Sisteco, en el proceso de generación de asientos de venta POS.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/2/19.
 */
public class MZGeneraAstoVtaDetMPST extends X_Z_GeneraAstoVtaDetMPST {

    public MZGeneraAstoVtaDetMPST(Properties ctx, int Z_GeneraAstoVtaDetMPST_ID, String trxName) {
        super(ctx, Z_GeneraAstoVtaDetMPST_ID, trxName);
    }

    public MZGeneraAstoVtaDetMPST(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
