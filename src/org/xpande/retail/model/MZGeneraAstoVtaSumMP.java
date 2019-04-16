package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para información resumida de medio de pago en proceso de generación de asientos de venta POS.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 4/16/19.
 */
public class MZGeneraAstoVtaSumMP extends X_Z_GeneraAstoVtaSumMP {

    public MZGeneraAstoVtaSumMP(Properties ctx, int Z_GeneraAstoVtaSumMP_ID, String trxName) {
        super(ctx, Z_GeneraAstoVtaSumMP_ID, trxName);
    }

    public MZGeneraAstoVtaSumMP(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
