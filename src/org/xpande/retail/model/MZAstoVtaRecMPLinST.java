package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modeolo para linea de asiento de reclasificación de medios de pago para POS sisteco.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 10/1/19.
 */
public class MZAstoVtaRecMPLinST extends X_Z_AstoVtaRecMPLinST {

    public MZAstoVtaRecMPLinST(Properties ctx, int Z_AstoVtaRecMPLinST_ID, String trxName) {
        super(ctx, Z_AstoVtaRecMPLinST_ID, trxName);
    }

    public MZAstoVtaRecMPLinST(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
