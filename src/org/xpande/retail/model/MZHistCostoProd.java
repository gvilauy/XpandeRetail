package org.xpande.retail.model;

import org.compiere.model.Query;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Modelo para histórico de costos de producto por socio de negocio y organización.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 12/5/18.
 */
public class MZHistCostoProd extends X_Z_HistCostoProd {

    public MZHistCostoProd(Properties ctx, int Z_HistCostoProd_ID, String trxName) {
        super(ctx, Z_HistCostoProd_ID, trxName);
    }

    public MZHistCostoProd(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Metodo que obtiene y retorna modelo de historico de costos segun parametros recibidos.
     * Xpande. Created by Gabriel Vila on 12/5/18.
     * @param ctx
     * @param dateDocument
     * @param adOrgTrxID
     * @param partnerId
     * @param productId
     * @param currencyId
     * @param trxName
     * @return
     */
    public static MZHistCostoProd getByDateOrgProdPartner(Properties ctx, Timestamp dateDocument, int adOrgTrxID, int partnerId, int productId, int currencyId, String trxName) {

        String whereClause = X_Z_HistCostoProd.COLUMNNAME_AD_OrgTrx_ID + " =" + adOrgTrxID +
                " AND " + X_Z_HistCostoProd.COLUMNNAME_C_BPartner_ID + " =" + partnerId +
                " AND " + X_Z_HistCostoProd.COLUMNNAME_M_Product_ID + " =" + productId +
                " AND " + X_Z_HistCostoProd.COLUMNNAME_C_Currency_ID + " =" + currencyId +
                " AND " + X_Z_HistCostoProd.COLUMNNAME_DateValidPO + " <='" + dateDocument + "'";

        MZHistCostoProd model = new Query(ctx, I_Z_HistCostoProd.Table_Name, whereClause, trxName).setOrderBy(X_Z_HistCostoProd.COLUMNNAME_DateValidPO + " DESC ").first();

        return model;
    }


}
