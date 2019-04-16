package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para información resumida de ventas por impuesto en proceso de generación de asientos de venta POS.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 4/16/19.
 */
public class MZGeneraAstoVtaSumTax extends X_Z_GeneraAstoVtaSumTax {

    public MZGeneraAstoVtaSumTax(Properties ctx, int Z_GeneraAstoVtaSumTax_ID, String trxName) {
        super(ctx, Z_GeneraAstoVtaSumTax_ID, trxName);
    }

    public MZGeneraAstoVtaSumTax(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
