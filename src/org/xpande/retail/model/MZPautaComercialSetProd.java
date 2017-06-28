package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para producto asociado a una segmento especial de una pauta comercial.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/27/17.
 */
public class MZPautaComercialSetProd extends X_Z_PautaComercialSetProd {

    public MZPautaComercialSetProd(Properties ctx, int Z_PautaComercialSetProd_ID, String trxName) {
        super(ctx, Z_PautaComercialSetProd_ID, trxName);
    }

    public MZPautaComercialSetProd(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
