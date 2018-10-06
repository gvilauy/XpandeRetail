package org.xpande.retail.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MProduct;
import org.compiere.process.SvrProcess;
import org.xpande.retail.model.MZProductoSocio;
import org.xpande.retail.model.MZProductoSocioOrg;

/**
 * Proceso que refresca costos (campo precio OC) en las lineas de un comprobante de proveedor.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 10/5/18.
 */
public class RefrescarCostosFact extends SvrProcess {

    private MInvoice invoice = null;

    @Override
    protected void prepare() {
        this.invoice = new MInvoice(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        String message = "OK";

        try{
            // Sino es un comprobante de compra, salgo sin hacer nada.
            if (this.invoice.isSOTrx()){
                return message;
            }

            int contador = 0;

            // Obtengo y recorro lineas de este comprobante de compra
            MInvoiceLine[] invoiceLines = this.invoice.getLines(true);
            for (int i = 0; i < invoiceLines.length; i++){

                MInvoiceLine invoiceLine = invoiceLines[i];
                if (invoiceLine.getM_Product_ID() <= 0){
                    continue;
                }

                // Refresco precio OC de esta linea de comprobante.
                MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(getCtx(), this.invoice.getC_BPartner_ID(), invoiceLine.getM_Product_ID(), null);
                if ((productoSocio != null) && (productoSocio.get_ID() > 0)){
                    MZProductoSocioOrg productoSocioOrg = productoSocio.getOrg(invoice.getAD_Org_ID());
                    if ((productoSocioOrg != null) && (productoSocioOrg.get_ID() > 0)){
                        invoiceLine.set_ValueOfColumn("PricePO", productoSocioOrg.getPricePO());
                        invoiceLine.set_ValueOfColumn("PricePONoDto", productoSocioOrg.getPricePO());
                    }
                    else{
                        invoiceLine.set_ValueOfColumn("PricePO", productoSocio.getPricePO());
                        invoiceLine.set_ValueOfColumn("PricePONoDto", productoSocioOrg.getPricePO());
                    }
                    invoiceLine.saveEx();

                    contador++;
                }
            }

            message = "OK. Lineas Procesadas = " + contador;

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return message;
    }

}
