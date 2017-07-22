package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de documento a considerarse en el proceso de Comunicacion de datos al POS.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/22/17.
 */
public class MZComunicacionPOSDoc extends X_Z_ComunicacionPOSDoc {

    public MZComunicacionPOSDoc(Properties ctx, int Z_ComunicacionPOSDoc_ID, String trxName) {
        super(ctx, Z_ComunicacionPOSDoc_ID, trxName);
    }

    public MZComunicacionPOSDoc(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
