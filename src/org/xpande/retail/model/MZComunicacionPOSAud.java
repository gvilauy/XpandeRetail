package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para auditar resultado del procese de comunicacion de datos al POS.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 10/13/17.
 */
public class MZComunicacionPOSAud extends X_Z_ComunicacionPOSAud {

    public MZComunicacionPOSAud(Properties ctx, int Z_ComunicacionPOSAud_ID, String trxName) {
        super(ctx, Z_ComunicacionPOSAud_ID, trxName);
    }

    public MZComunicacionPOSAud(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
