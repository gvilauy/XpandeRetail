package org.xpande.retail.model;

import org.compiere.model.Query;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Modelo para manejar documentos a considerar en el proceso de Confirmación de Etiquetas.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/12/17.
 */
public class MZConfirmacionEtiquetaDoc extends X_Z_ConfirmacionEtiquetaDoc {

    public MZConfirmacionEtiquetaDoc(Properties ctx, int Z_ConfirmacionEtiquetaDoc_ID, String trxName) {
        super(ctx, Z_ConfirmacionEtiquetaDoc_ID, trxName);
    }

    public MZConfirmacionEtiquetaDoc(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Obtiene y retorna lista de productos marcados para impresión de este modelo.
     * Xpande. Created by Gabriel Vila on 7/12/17.
     * @return
     */
    public List<MZConfirmacionEtiquetaProd> getPrintedProducts(){

        String whereClause = X_Z_ConfirmacionEtiquetaProd.COLUMNNAME_Z_ConfirmacionEtiquetaDoc_ID + " =" + this.get_ID() +
                " AND " + X_Z_ConfirmacionEtiquetaProd.COLUMNNAME_IsPrinted + " ='Y'";

        List<MZConfirmacionEtiquetaProd> lines = new Query(getCtx(), I_Z_ConfirmacionEtiquetaProd.Table_Name, whereClause, get_TrxName()).list();

        return lines;
    }

}
