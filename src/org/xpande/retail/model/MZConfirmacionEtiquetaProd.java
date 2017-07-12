package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para manejar productos en el proceso de Confirmación de Etiquetas.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/12/17.
 */
public class MZConfirmacionEtiquetaProd extends X_Z_ConfirmacionEtiquetaProd {

    public MZConfirmacionEtiquetaProd(Properties ctx, int Z_ConfirmacionEtiquetaProd_ID, String trxName) {
        super(ctx, Z_ConfirmacionEtiquetaProd_ID, trxName);
    }

    public MZConfirmacionEtiquetaProd(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
