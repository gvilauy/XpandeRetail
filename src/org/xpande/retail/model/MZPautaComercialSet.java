package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para sets o grupos de pautas comerciales en el módulo de Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/16/17.
 */
public class MZPautaComercialSet extends X_Z_PautaComercialSet {

    public MZPautaComercialSet(Properties ctx, int Z_PautaComercialSet_ID, String trxName) {
        super(ctx, Z_PautaComercialSet_ID, trxName);
    }

    public MZPautaComercialSet(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

}
