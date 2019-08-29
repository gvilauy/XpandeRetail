package org.xpande.retail.process;

import org.compiere.process.SvrProcess;
import org.xpande.retail.model.MZFormEfectivo;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/29/19.
 */
public class ObtenerConceptosFormEfec extends SvrProcess {

    MZFormEfectivo formEfectivo = null;

    @Override
    protected void prepare() {
        this.formEfectivo = new MZFormEfectivo(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        String message = this.formEfectivo.getConceptos();

        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";

    }
}
