package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para asociar organización en el proceso de ofertas periódicas de retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 1/10/18.
 */
public class MZOfertaVentaOrg extends X_Z_OfertaVentaOrg {

    public MZOfertaVentaOrg(Properties ctx, int Z_OfertaVentaOrg_ID, String trxName) {
        super(ctx, Z_OfertaVentaOrg_ID, trxName);
    }

    public MZOfertaVentaOrg(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
