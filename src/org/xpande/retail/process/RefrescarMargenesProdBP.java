package org.xpande.retail.process;

import org.compiere.process.SvrProcess;
import org.xpande.retail.model.MZProductoSocio;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 3/23/21.
 */
public class RefrescarMargenesProdBP  extends SvrProcess {

    private MZProductoSocio productoSocio = null;

    @Override
    protected void prepare() {
        this.productoSocio = new MZProductoSocio(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        this.productoSocio.calculateMargins();;

        return "OK";
    }
}
