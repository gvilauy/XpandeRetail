package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para imprimir etiquetas.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/12/17.
 */
public class MZConfirmacionEtiquetaPrint extends X_Z_ConfirmacionEtiquetaPrint {

    public MZConfirmacionEtiquetaPrint(Properties ctx, int Z_ConfirmacionEtiquetaPrint_ID, String trxName) {
        super(ctx, Z_ConfirmacionEtiquetaPrint_ID, trxName);
    }

    public MZConfirmacionEtiquetaPrint(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
