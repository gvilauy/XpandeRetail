package org.xpande.retail.model;

import org.compiere.model.Query;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para cabezal de auditoria del sistema de información de precios al consumidor (SPIC) en Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/7/20.
 */
public class MZAuditSipc extends X_Z_AuditSipc{

    public MZAuditSipc(Properties ctx, int Z_AuditSipc_ID, String trxName) {
        super(ctx, Z_AuditSipc_ID, trxName);
    }

    public MZAuditSipc(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    /***
     * Obtiene y retorna modelo de linea según UPC recibido.
     * Xpande. Created by Gabriel Vila on 7/7/20.
     * @param upc
     * @return
     */
    public MZAuditSipcLin getByUPC(String upc){

        String whereClause = X_Z_AuditSipcLin.COLUMNNAME_Z_AuditSipc_ID + " =" + this.get_ID() +
                " AND " + X_Z_AuditSipcLin.COLUMNNAME_UPC + " ='" + upc + "' ";

        MZAuditSipcLin model = new Query(getCtx(), I_Z_AuditSipcLin.Table_Name, whereClause, get_TrxName()).first();

        return model;
    }

    /***
     * Obtiene y retorna modelo de linea según codigo producto DGC recibido.
     * Xpande. Created by Gabriel Vila on 7/7/20.
     * @param upc
     * @return
     */
    public MZAuditSipcLin getByCodDGC(String codigoDGC){

        String whereClause = X_Z_AuditSipcLin.COLUMNNAME_Z_AuditSipc_ID + " =" + this.get_ID() +
                " AND " + X_Z_AuditSipcLin.COLUMNNAME_CodigoProducto + " ='" + codigoDGC + "' ";

        MZAuditSipcLin model = new Query(getCtx(), I_Z_AuditSipcLin.Table_Name, whereClause, get_TrxName()).first();

        return model;
    }
}
