package org.xpande.retail.process;

import org.compiere.process.SvrProcess;
import org.xpande.retail.model.MZConfirmacionEtiqueta;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/12/17.
 */
public class ObtenerDocsConfEtiqueta extends SvrProcess {

    private MZConfirmacionEtiqueta confirmacionEtiqueta = null;

    @Override
    protected void prepare() {
        this.confirmacionEtiqueta = new MZConfirmacionEtiqueta(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        String message = this.confirmacionEtiqueta.getDocuments();

        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";
    }
}
