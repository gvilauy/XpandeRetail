package org.xpande.retail.model;

import org.compiere.model.Query;
import org.compiere.process.DocumentEngine;

import java.lang.annotation.Documented;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Modelo para gestionar histórico de ofertas periodicas por producto y oferta.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 1/10/18.
 */
public class MZProductoOferta extends X_Z_ProductoOferta {

    public MZProductoOferta(Properties ctx, int Z_ProductoOferta_ID, String trxName) {
        super(ctx, Z_ProductoOferta_ID, trxName);
    }

    public MZProductoOferta(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Obtiene y retorna modelo según producto y fecha recibida.
     * Xpande. Created by Gabriel Vila on 1/15/18.
     * @param ctx
     * @param mProductID
     * @param fechaDesde
     * @param fechaHasta
     * @param trxName
     * @return
     */
    public static MZProductoOferta getByProductDate(Properties ctx, int mProductID, Timestamp fechaDesde, Timestamp fechaHasta, String trxName){

        String whereClause = X_Z_ProductoOferta.COLUMNNAME_M_Product_ID + " =" + mProductID +
                " AND (('" + fechaDesde + "' <=" + X_Z_ProductoOferta.COLUMNNAME_StartDate +
                        " AND '" + fechaHasta + "' >=" + X_Z_ProductoOferta.COLUMNNAME_StartDate + ")" +
                " OR ('" + fechaDesde + "' >=" + X_Z_ProductoOferta.COLUMNNAME_StartDate +
                " AND '" + fechaDesde + "' <=" + X_Z_ProductoOferta.COLUMNNAME_EndDate + "))";

        MZProductoOferta model = new Query(ctx, I_Z_ProductoOferta.Table_Name, whereClause, trxName)
                .setOrderBy(X_Z_ProductoOferta.COLUMNNAME_StartDate).first();

        if ((model != null) && (model.get_ID() > 0)){
            // Verifico que la oferta este en estado completa
            MZOfertaVenta ofertaVenta = (MZOfertaVenta) model.getZ_OfertaVenta();
            if (!ofertaVenta.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Completed)){
                model = null;
            }
        }

        return model;
    }

}
