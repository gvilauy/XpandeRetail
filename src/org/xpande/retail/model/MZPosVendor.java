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
}
