package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para gestionar histórico de ofertas periodicas por producto y oferta.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 1/10/18.
 */
public class MZProductoOferta extends X_Z_ProductoOferta {

    public MZProductoOferta(Properties ctx, int Z_ProductoOferta_ID, String trxName) {
        super(ctx, Z_ProductoOferta_ID, trxName);
    }

    public MZProductoOferta(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
