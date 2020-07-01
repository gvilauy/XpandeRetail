package org.xpande.retail.model;

import org.compiere.model.MProduct;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/1/20.
 */
public class MZDesafectaProdBPLin extends X_Z_DesafectaProdBPLin{

    public MZDesafectaProdBPLin(Properties ctx, int Z_DesafectaProdBPLin_ID, String trxName) {
        super(ctx, Z_DesafectaProdBPLin_ID, trxName);
    }

    public MZDesafectaProdBPLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {

        // Traigo datos del producto
        if ((newRecord) || (!newRecord && is_ValueChanged(X_Z_DesafectaProdBPLin.COLUMNNAME_M_Product_ID))){
            MProduct product = (MProduct) this.getM_Product();
            this.setZ_ProductoSeccion_ID(product.get_ValueAsInt("Z_ProductoSeccion_ID"));
            this.setZ_ProductoRubro_ID(product.get_ValueAsInt("Z_ProductoRubro_ID"));

            if (product.get_ValueAsInt("Z_ProductoFamilia_ID") > 0){
                this.setZ_ProductoFamilia_ID(product.get_ValueAsInt("Z_ProductoFamilia_ID"));
            }

            if (product.get_ValueAsInt("Z_ProductoSubfamilia_ID") > 0){
                this.setZ_ProductoSubfamilia_ID(product.get_ValueAsInt("Z_ProductoSubfamilia_ID"));
            }
        }

        return true;
    }
}
