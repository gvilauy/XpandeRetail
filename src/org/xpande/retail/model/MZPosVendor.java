package org.xpande.retail.model;

import org.compiere.model.Query;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Modelo para gestión de proveedores de POS.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/6/17.
 */
public class MZPosVendor extends X_Z_PosVendor {

    public MZPosVendor(Properties ctx, int Z_PosVendor_ID, String trxName) {
        super(ctx, Z_PosVendor_ID, trxName);
    }

    public MZPosVendor(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Obtiene y retorna lista de pos activos definidos en el sistema.
     * Xpande. Created by Gabriel Vila on 7/10/17.
     * @param ctx
     * @param trxName
     * @return
     */
    public static List<MZPosVendor> getPosList(Properties ctx, String trxName){

        List<MZPosVendor> lines = new Query(ctx, I_Z_PosVendor.Table_Name, null, trxName).setOnlyActiveRecords(true).list();

        return lines;

    }


    /***
     * Obtiene y retorna proveedor de pos para una determinada organización recibida.
     * Xpande. Created by Gabriel Vila on 2/9/18.
     * @param ctx
     * @param adOrgID
     * @param trxName
     * @return
     */
    public static MZPosVendor getByOrg(Properties ctx, int adOrgID, String trxName){

        String whereClause = X_Z_PosVendorOrg.COLUMNNAME_AD_Org_ID + " =" + adOrgID;

        MZPosVendor model = new Query(ctx, I_Z_PosVendorOrg.Table_Name, whereClause, trxName).setOnlyActiveRecords(true).first();

        return model;
    }

}
