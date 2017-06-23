package org.xpande.retail.model;

import org.compiere.model.Query;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de socio de negocio distribuidor de una linea de productos de otro socio.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/21/17.
 */
public class MZLineaProductoDistri extends X_Z_LineaProductoDistri {

    public MZLineaProductoDistri(Properties ctx, int Z_LineaProductoDistri_ID, String trxName) {
        super(ctx, Z_LineaProductoDistri_ID, trxName);
    }

    public MZLineaProductoDistri(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Obtiene y retorna modelo según distribuidor y linea de productos de un socio.
     * Xpande. Created by Gabriel Vila on 6/21/17.
     * @param ctx
     * @param zLineaProductoID
     * @param cBPartnerDistriID
     * @param trxName
     * @return
     */
    public static MZLineaProductoDistri getByLineaDistri(Properties ctx, int zLineaProductoID, int cBPartnerDistriID, String trxName){

        String whereClause = X_Z_LineaProductoDistri.COLUMNNAME_Z_LineaProductoSocio_ID + " =" + zLineaProductoID +
                " AND " + X_Z_LineaProductoDistri.COLUMNNAME_C_BPartner_ID + " =" + cBPartnerDistriID;

        MZLineaProductoDistri model = new Query(ctx, I_Z_LineaProductoDistri.Table_Name, whereClause, trxName).setOnlyActiveRecords(true).first();

        return model;
    }

}
