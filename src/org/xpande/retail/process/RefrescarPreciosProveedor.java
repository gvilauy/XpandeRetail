package org.xpande.retail.process;

import org.compiere.process.SvrProcess;
import org.xpande.retail.model.MZPreciosProvCab;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/26/17.
 */
public class RefrescarPreciosProveedor extends SvrProcess {

    private MZPreciosProvCab preciosProvCab = null;

    @Override
    protected void prepare() {
        this.preciosProvCab = new MZPreciosProvCab(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        String message = preciosProvCab.refrescarPrecios();

        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";
    }

}
