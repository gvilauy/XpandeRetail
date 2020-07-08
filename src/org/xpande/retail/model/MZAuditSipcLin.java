package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para lineas del proceso de información de precios al consumidor (SPIC) en Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/7/20.
 */
public class MZAuditSipcLin extends X_Z_AuditSipcLin{

    public MZAuditSipcLin(Properties ctx, int Z_AuditSipcLin_ID, String trxName) {
        super(ctx, Z_AuditSipcLin_ID, trxName);
    }

    public MZAuditSipcLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
