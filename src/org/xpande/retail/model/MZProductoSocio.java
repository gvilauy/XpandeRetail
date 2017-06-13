package org.xpande.retail.model;

import org.compiere.model.Query;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para relación socio de negocio - producto para el modulo de retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/13/17.
 */
public class MZProductoSocio extends X_Z_ProductoSocio {

    public MZProductoSocio(Properties ctx, int Z_ProductoSocio_ID, String trxName) {
        super(ctx, Z_ProductoSocio_ID, trxName);
    }

    public MZProductoSocio(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    /***
     * Obtiene y retorna modelo segun id de socio de negocio y id de producto recibidos.
     * Xpande. Created by Gabriel Vila on 6/13/17.
     * @param ctx
     * @param cBPartnerID
     * @param mProductID
     * @param trxName
     * @return
     */
    public static MZProductoSocio getByBPartnerProduct(Properties ctx, int cBPartnerID, int mProductID, String trxName) {

        String whereClause = X_Z_ProductoSocio.COLUMNNAME_C_BPartner_ID + " =" + cBPartnerID +
                " AND " + X_Z_ProductoSocio.COLUMNNAME_M_Product_ID + " =" + mProductID;

        MZProductoSocio model = new Query(ctx, I_Z_ProductoSocio.Table_Name, whereClause, trxName).first();

        return model;
    }

}
