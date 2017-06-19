package org.xpande.retail.model;

import org.compiere.model.Query;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de linea leída desde archivo de interface de precios de proveedor.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/13/17.
 */
public class MZPreciosProvArchivo extends X_Z_PreciosProvArchivo {

    public MZPreciosProvArchivo(Properties ctx, int Z_PreciosProvArchivo_ID, String trxName) {
        super(ctx, Z_PreciosProvArchivo_ID, trxName);
    }

    public MZPreciosProvArchivo(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Obtiene y retorna linea de archivo procesada, que este confirmada, para un determinado codigo de barras recibido.
     * @param ctx
     * @param zPreciosProvCabID
     * @param upc
     * @param trxName
     * @return
     */
    public static MZPreciosProvArchivo getConfirmedByUPC(Properties ctx, int zPreciosProvCabID, String upc, String trxName) {

        String whereClause = X_Z_PreciosProvArchivo.COLUMNNAME_Z_PreciosProvCab_ID + " =" + zPreciosProvCabID +
                " AND " + X_Z_PreciosProvArchivo.COLUMNNAME_UPC + " ='" + upc + "'" +
                " AND " + X_Z_PreciosProvArchivo.COLUMNNAME_IsConfirmed + " ='Y'";

        MZPreciosProvArchivo model = new Query(ctx, I_Z_PreciosProvArchivo.Table_Name, whereClause, trxName).first();

        return model;

    }


}

