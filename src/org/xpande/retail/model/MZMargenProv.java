package org.xpande.retail.model;

import org.compiere.util.Env;
import org.xpande.comercial.model.MZComercialConfig;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para definicion de margenes por proveedor.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 11/22/17.
 */
public class MZMargenProv extends X_Z_MargenProv {

    public MZMargenProv(Properties ctx, int Z_MargenProv_ID, String trxName) {
        super(ctx, Z_MargenProv_ID, trxName);
    }

    public MZMargenProv(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {

        if ((newRecord) || (is_ValueChanged(X_Z_MargenProv.COLUMNNAME_Margin))){

            if ((this.getMargin() != null) && (this.getMargin().compareTo(Env.ZERO) > 0)){
                // Calculo tolerancia segun parametros general del modulo comercial
                MZComercialConfig comercialConfig = MZComercialConfig.getDefault(getCtx(), get_TrxName());
                if ((comercialConfig.getMarginTolerance() != null) && (comercialConfig.getMarginTolerance().compareTo(Env.ZERO) > 0)){
                    this.setMarginTolerance(this.getMargin().multiply(comercialConfig.getMarginTolerance().divide(Env.ONEHUNDRED, 2, BigDecimal.ROUND_HALF_UP)));
                }
                else{
                    this.setMarginTolerance(Env.ZERO);
                }
            }

        }

        return true;
    }

}
