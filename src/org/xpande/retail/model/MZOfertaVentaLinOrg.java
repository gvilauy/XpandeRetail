package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para organizaciones asociadas a una linea del proceso de ofertas periódicas.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 1/10/18.
 */
public class MZOfertaVentaLinOrg extends X_Z_OfertaVentaLinOrg {

    public MZOfertaVentaLinOrg(Properties ctx, int Z_OfertaVentaLinOrg_ID, String trxName) {
        super(ctx, Z_OfertaVentaLinOrg_ID, trxName);
    }

    public MZOfertaVentaLinOrg(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
