package org.xpande.retail.model;

import org.compiere.model.Query;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Modelo para lineas de productos. Cada linea tiene un socio dueño y n socios distribuidores.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/13/17.
 */
public class MZLineaProductoGral extends X_Z_LineaProductoGral {

    public MZLineaProductoGral(Properties ctx, int Z_LineaProductoGral_ID, String trxName) {
        super(ctx, Z_LineaProductoGral_ID, trxName);
    }

    public MZLineaProductoGral(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Obtiene y retorna lista de distribuidores de esta linea.
     * Xpande. Created by Gabriel Vila on 7/13/17.
     * @return
     */
    public List<MZLineaProductoSocio> getDistribuidores(){

        String whereClause = X_Z_LineaProductoSocio.COLUMNNAME_Z_LineaProductoGral_ID + " =" + this.get_ID() +
                " AND " + X_Z_LineaProductoSocio.COLUMNNAME_IsOwn + " ='N'";

        List<MZLineaProductoSocio> lines = new Query(getCtx(), I_Z_LineaProductoSocio.Table_Name, whereClause, get_TrxName()).setOnlyActiveRecords(true).list();

        return lines;
    }
}
