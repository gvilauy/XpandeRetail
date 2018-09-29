package org.xpande.retail.process;

import org.compiere.process.SvrProcess;
import org.xpande.retail.model.MZComunicacionPOS;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/22/17.
 */
public class ObtenerDocsComunicaPOS extends SvrProcess {

    MZComunicacionPOS comunicacionPOS = null;

    @Override
    protected void prepare() {
        this.comunicacionPOS = new MZComunicacionPOS(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        String message = this.comunicacionPOS.getDocuments();

        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";
    }

}
