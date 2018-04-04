package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para registrar lineas de ofertas que se eliminan al momento de una corrección de oferta periódica.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 4/4/18.
 */
public class MZOfertaVentaLinDel extends X_Z_OfertaVentaLinDel {

    public MZOfertaVentaLinDel(Properties ctx, int Z_OfertaVentaLinDel_ID, String trxName) {
        super(ctx, Z_OfertaVentaLinDel_ID, trxName);
    }

    public MZOfertaVentaLinDel(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
