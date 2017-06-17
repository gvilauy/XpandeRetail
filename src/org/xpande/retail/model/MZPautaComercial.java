package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de pautas comerciales para el módulo de Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/16/17.
 */
public class MZPautaComercial extends X_Z_PautaComercial {

    public MZPautaComercial(Properties ctx, int Z_PautaComercial_ID, String trxName) {
        super(ctx, Z_PautaComercial_ID, trxName);
    }

    public MZPautaComercial(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

}
