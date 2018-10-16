package org.xpande.retail.model;

import org.compiere.model.MInvoiceLine;
import org.compiere.model.Query;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de afectación de linea de remito por diferencia de facturación de proveedores.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 2/27/18.
 */
public class MZRemDifInvLinAfecta extends X_Z_RemDifInvLinAfecta {

    public MZRemDifInvLinAfecta(Properties ctx, int Z_RemDifInvLinAfecta_ID, String trxName) {
        super(ctx, Z_RemDifInvLinAfecta_ID, trxName);
    }

    public MZRemDifInvLinAfecta(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Obtiene y retorna modelo segun id de linea de invoice recibida.
     * Xpande. Created by Gabriel Vila on 3/6/18.
     * @param ctx
     * @param cInvoiceLineID
     * @param trxName
     * @return
     */
    public static MZRemDifInvLinAfecta getByInvoiceLine(Properties ctx, int cInvoiceLineID, String trxName) {

        String whereClause = X_Z_RemDifInvLinAfecta.COLUMNNAME_C_InvoiceLine_ID + " =" + cInvoiceLineID;

        MZRemDifInvLinAfecta model = new Query(ctx, I_Z_RemDifInvLinAfecta.Table_Name, whereClause, trxName).first();

        return model;
    }

    /*
    @Override
    protected boolean beforeDelete() {

        // Elimino lineas de factura asociadas a esta afectación de linea de remito.
        MInvoiceLine invoiceLine = (MInvoiceLine) this.getC_InvoiceLine();
        if ((invoiceLine != null) && (invoiceLine.get_ID() > 0)){
            invoiceLine.deleteEx(false);
        }

        return true;
    }
    */

    @Override
    protected boolean afterDelete(boolean success) {

        if (!success) return success;

        // Elimino lineas de factura asociadas a esta afectación de linea de remito.
        MInvoiceLine invoiceLine = (MInvoiceLine) this.getC_InvoiceLine();
        if ((invoiceLine != null) && (invoiceLine.get_ID() > 0)){
            invoiceLine.deleteEx(false);
        }

        return true;
    }
}
