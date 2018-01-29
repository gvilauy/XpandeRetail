package org.xpande.retail.model;

import org.compiere.model.I_M_InOutLine;
import org.compiere.model.MInOutLine;
import org.compiere.model.Query;
import org.compiere.model.X_M_InOutLine;
import org.compiere.util.DB;
import org.xpande.core.utils.StringUtils;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Modelo para facturas recibidas en el documento de Recepcion de Productos en Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/4/17.
 */
public class MZRecepcionProdFact extends X_Z_RecepcionProdFact {

    public MZRecepcionProdFact(Properties ctx, int Z_RecepcionProdFact_ID, String trxName) {
        super(ctx, Z_RecepcionProdFact_ID, trxName);
    }

    public MZRecepcionProdFact(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {

        // Me aseguro serie en mayusculas y sin espacios
        this.setDocumentSerie(this.getDocumentSerie().trim().toUpperCase());

        // Valido serie solo letras
        if (!StringUtils.cadenaSoloLetras(this.getDocumentSerie())){
            log.saveError("ATENCIÓN", "Serie de Factura debe contener solo Letras");
            return false;
        }

        // Me aseguro numero de factura solo numeros, sin espacios
        String numeroFactura = this.getManualDocumentNo().trim().replaceAll("[^0-9]", "");

        // Valido numero de factura
        if (numeroFactura.equalsIgnoreCase("")){
            log.saveError("ATENCIÓN", "Número de Factura ingresado no es válido. Verifique que solo contenga números");
            return false;
        }

        // Valido que no se repita serie y numero de factura
        String sql = "select count(*) from z_recepcionprodfact where m_inout_id =" + this.getM_InOut_ID() +
                " and documentserie ='" + this.getDocumentSerie() + "' " +
                " and manualdocumentno ='" + numeroFactura + "'";
        int contador = DB.getSQLValueEx(null, sql);
        if (contador > 0){
            log.saveError("ATENCIÓN", "Factura ya fue ingresada con ese Serie-Numero");
            return false;
        }

        this.setManualDocumentNo(numeroFactura);

        // Concateno serie + numero
        this.setSerieNumberDoc(this.getDocumentSerie() + this.getManualDocumentNo());

        return true;
    }


    /***
     * Obtiene y retorna lista de modelos para ID de recepcion recibido.
     * Xpande. Created by Gabriel Vila on 7/4/17.
     * @param ctx
     * @param mInOutID
     * @param trxName
     * @return
     */
    public static List<MZRecepcionProdFact> getByInOut(Properties ctx, int mInOutID, String trxName) {

        String whereClause = X_Z_RecepcionProdFact.COLUMNNAME_M_InOut_ID + " =" + mInOutID;

        List<MZRecepcionProdFact> lines = new Query(ctx, I_Z_RecepcionProdFact.Table_Name, whereClause, trxName).setOrderBy(" Z_RecepcionProdFact_ID ").list();

        return lines;
    }


    /***
     * Obtiene y retorna lineas de recepcion asociadas a esta factura recibida.
     * Xpande. Created by Gabriel Vila on 7/4/17.
     * @return
     */
    public List<MInOutLine> getInOutLines() {

        String whereClause = X_M_InOutLine.COLUMNNAME_M_InOut_ID + " =" + this.getM_InOut_ID() +
                " AND " + X_Z_RecepcionProdFact.COLUMNNAME_Z_RecepcionProdFact_ID + " =" + this.get_ID();

        List<MInOutLine> lines = new Query(getCtx(), I_M_InOutLine.Table_Name, whereClause, get_TrxName()).setOrderBy(" M_InOutLine_ID ").list();

        return lines;
    }
}
