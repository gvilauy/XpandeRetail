package org.xpande.retail.model;

import org.compiere.model.Query;
import org.compiere.process.DocumentEngine;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Clase para información de histórico de ofertas comerciales en retail asociadas a un producto - socio.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 1/15/18.
 */
public class MZProductoSocioOferta extends X_Z_ProductoSocioOferta {

    public MZProductoSocioOferta(Properties ctx, int Z_ProductoSocioOferta_ID, String trxName) {
        super(ctx, Z_ProductoSocioOferta_ID, trxName);
    }

    public MZProductoSocioOferta(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    /***
     * Obtiene y retorna modelo según producto-socio y fecha recibida.
     * Xpande. Created by Gabriel Vila on 1/15/18.
     * @param ctx
     * @param zProductoSocioID
     * @param fecha
     * @param trxName
     * @return
     */
    public static MZProductoSocioOferta getByProductBPDate(Properties ctx, int zProductoSocioID, Timestamp fecha, String trxName){

        String whereClause = X_Z_ProductoSocioOferta.COLUMNNAME_Z_ProductoSocio_ID + " =" + zProductoSocioID +
                " AND ('" + fecha + "' >=" + X_Z_ProductoSocioOferta.COLUMNNAME_StartDate +
                " AND '" + fecha + "' <=" + X_Z_ProductoSocioOferta.COLUMNNAME_EndDate + ")";

        MZProductoSocioOferta model = new Query(ctx, I_Z_ProductoSocioOferta.Table_Name, whereClause, trxName)
                .setOrderBy(X_Z_ProductoSocioOferta.COLUMNNAME_StartDate).first();

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
