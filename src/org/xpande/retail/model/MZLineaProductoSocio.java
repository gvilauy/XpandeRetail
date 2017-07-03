package org.xpande.retail.model;

import org.compiere.model.Query;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Modelo de linea de productos de un socio de negocio.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/21/17.
 */
public class MZLineaProductoSocio extends X_Z_LineaProductoSocio {

    public MZLineaProductoSocio(Properties ctx, int Z_LineaProductoSocio_ID, String trxName) {
        super(ctx, Z_LineaProductoSocio_ID, trxName);
    }

    public MZLineaProductoSocio(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Obtiene y retorna linea de productos de un distribuidor que esta relacionada a una lista de productos de otro socio de negocio (dueño de la linea).
     * Xpande. Created by Gabriel Vila on 7/2/17.
     * @param ctx
     * @param cBPartnerDistriID
     * @param cBPartnerRelatedID
     * @param zLineaProductoSocioRelatedID
     * @param trxName
     * @return
     */
    public static MZLineaProductoSocio getByDistriRelatedPartnerLinea(Properties ctx, int cBPartnerDistriID, int cBPartnerRelatedID, int zLineaProductoSocioRelatedID, String trxName) {

        String whereClause = X_Z_LineaProductoSocio.COLUMNNAME_C_BPartner_ID + " =" + cBPartnerDistriID +
                " AND " + X_Z_LineaProductoSocio.COLUMNNAME_C_BPartnerRelation_ID + " =" + cBPartnerRelatedID +
                " AND " + X_Z_LineaProductoSocio.COLUMNNAME_Z_LineaProductoSocioRelated_ID + " =" + zLineaProductoSocioRelatedID;

        MZLineaProductoSocio model = new Query(ctx, I_Z_LineaProductoSocio.Table_Name, whereClause, trxName).first();

        return model;

    }

    /***
     * Obtiene y retorna lista de distribuidores de esta linea de productos.
     * Xpande. Created by Gabriel Vila on 7/2/17.
     * @return
     */
    public List<MZLineaProductoDistri> getDistribuidores() {

        String whereClause = X_Z_LineaProductoDistri.COLUMNNAME_Z_LineaProductoSocio_ID + " =" + this.get_ID();

        List<MZLineaProductoDistri> lines = new Query(getCtx(), I_Z_LineaProductoDistri.Table_Name, whereClause, get_TrxName()).setOnlyActiveRecords(true).list();

        return lines;
    }

}
