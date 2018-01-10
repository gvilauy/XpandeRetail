package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para socios de negocios asociados a una linea del proceso de ofertas periódicas en retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 1/10/18.
 */
public class MZOfertaVentaLinBP extends X_Z_OfertaVentaLinBP {

    public MZOfertaVentaLinBP(Properties ctx, int Z_OfertaVentaLinBP_ID, String trxName) {
        super(ctx, Z_OfertaVentaLinBP_ID, trxName);
    }

    public MZOfertaVentaLinBP(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
