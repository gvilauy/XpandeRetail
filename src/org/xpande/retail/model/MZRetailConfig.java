package org.xpande.retail.model;

import org.compiere.model.Query;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para configuración general del módulo de retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 11/6/17.
 */
public class MZRetailConfig extends X_Z_RetailConfig {

    public MZRetailConfig(Properties ctx, int Z_RetailConfig_ID, String trxName) {
        super(ctx, Z_RetailConfig_ID, trxName);
    }

    public MZRetailConfig(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    /***
     * Obtiene modelo único de configuración de modulo de retail.
     * Xpande. Created by Gabriel Vila on 11/6/17.
     * @param ctx
     * @param trxName
     * @return
     */
    public static MZRetailConfig getDefault(Properties ctx, String trxName){

        MZRetailConfig model = new Query(ctx, I_Z_RetailConfig.Table_Name, "", trxName).first();

        return model;
    }


    /***
     * Obtiene y retorna configuración para interface contra DGC para una determinada organización.
     * Xpande. Created by Gabriel Vila on 11/6/17.
     * @param adOrgID
     * @return
     */
    public MZRetailConfigDGC getConfigDGCByOrg(int adOrgID){

        String whereClause = X_Z_RetailConfigDGC.COLUMNNAME_Z_RetailConfig_ID + " =" + this.get_ID() +
                " AND " + X_Z_RetailConfigDGC.COLUMNNAME_AD_OrgTo_ID + " =" + adOrgID;

        MZRetailConfigDGC model = new Query(getCtx(), I_Z_RetailConfigDGC.Table_Name, whereClause, get_TrxName()).first();

        return model;
    }

}
