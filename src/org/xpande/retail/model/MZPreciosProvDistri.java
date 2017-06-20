package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para dsitribuidor asociado al socio de negocio del documento de gestión de precios de proveedor.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/19/17.
 */
public class MZPreciosProvDistri extends X_Z_PreciosProvDistri {

    public MZPreciosProvDistri(Properties ctx, int Z_PreciosProvDistri_ID, String trxName) {
        super(ctx, Z_PreciosProvDistri_ID, trxName);
    }

    public MZPreciosProvDistri(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean beforeDelete() {

        if (!this.isManualRecord()){
            log.saveError("ATENCIÓN", "No es posible eliminar Distribuidor.\nDebe desafectarlo desde la ventana de Socios de Negocio.");
            return false;
        }

        return true;
    }
}
