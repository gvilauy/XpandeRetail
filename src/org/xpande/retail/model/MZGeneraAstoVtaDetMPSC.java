package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para linea de detalle de medio de pago de Scanntech en proceso de generación de asientos de venta.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 10/21/19.
 */
public class MZGeneraAstoVtaDetMPSC extends X_Z_GeneraAstoVtaDetMPSC {

    public MZGeneraAstoVtaDetMPSC(Properties ctx, int Z_GeneraAstoVtaDetMPSC_ID, String trxName) {
        super(ctx, Z_GeneraAstoVtaDetMPSC_ID, trxName);
    }

    public MZGeneraAstoVtaDetMPSC(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
