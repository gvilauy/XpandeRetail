package org.xpande.retail.model;

import org.compiere.model.*;
import org.xpande.comercial.model.MZComercialConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @Override
    protected boolean beforeSave(boolean newRecord) {

        // Si se modifica precio o cantidad, recalculo importe de la linea y refreso linea de factura asociada
        if (!newRecord){
            if ((is_ValueChanged(X_Z_RemDifInvLinAfecta.COLUMNNAME_PriceEntered)) || (is_ValueChanged(X_Z_RemDifInvLinAfecta.COLUMNNAME_QtyEntered))){
                this.setLineTotalAmt(this.getQtyEntered().multiply(this.getPriceEntered()).setScale(2, RoundingMode.HALF_UP));

                MInvoiceLine invoiceLine = (MInvoiceLine) this.getC_InvoiceLine();
                if ((invoiceLine != null) && (invoiceLine.get_ID() > 0)){
                    invoiceLine.setQtyInvoiced(this.getQtyEntered());
                    invoiceLine.setQtyEntered(this.getQtyEntered());
                    invoiceLine.setPriceActual(this.getPriceEntered());
                    invoiceLine.setPriceEntered(this.getPriceEntered());

                    MProduct prod = (MProduct) invoiceLine.getM_Product();

                    // Impuesto del producto (primero impuesto especial de compra, y si no tiene, entonces el impuesto normal
                    if (prod.get_ValueAsInt("C_TaxCategory_ID_2") > 0) {
                        MTaxCategory taxCat = new MTaxCategory(getCtx(), prod.get_ValueAsInt("C_TaxCategory_ID_2"), null);
                        MTax tax = taxCat.getDefaultTax();
                        if (tax != null) {
                            if (tax.get_ID() > 0) {
                                invoiceLine.setC_Tax_ID(tax.get_ID());
                            }
                        }
                    } else {
                        if (prod.getC_TaxCategory_ID() > 0) {
                            MTaxCategory taxCat = (MTaxCategory) prod.getC_TaxCategory();
                            MTax tax = taxCat.getDefaultTax();
                            if (tax != null) {
                                if (tax.get_ID() > 0) {
                                    invoiceLine.setC_Tax_ID(tax.get_ID());
                                }
                            }
                        }
                    }

                    invoiceLine.setLineNetAmt();
                    invoiceLine.setTaxAmt();
                    invoiceLine.saveEx();
                }

            }
        }

        return true;
    }
}
