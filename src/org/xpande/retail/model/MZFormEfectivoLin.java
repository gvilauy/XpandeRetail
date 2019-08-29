package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para linea en el documento de Formulario de Movimientos de Efectivo en Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/29/19.
 */
public class MZFormEfectivoLin extends X_Z_FormEfectivoLin {

    public MZFormEfectivoLin(Properties ctx, int Z_FormEfectivoLin_ID, String trxName) {
        super(ctx, Z_FormEfectivoLin_ID, trxName);
    }

    public MZFormEfectivoLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }
}
