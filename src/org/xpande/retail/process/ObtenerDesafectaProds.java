package org.xpande.retail.process;

import org.compiere.process.SvrProcess;
import org.xpande.retail.model.MZDesafectaProdBP;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/1/20.
 */
public class ObtenerDesafectaProds extends SvrProcess {

    MZDesafectaProdBP desafectaProdBP = null;

    @Override
    protected void prepare() {
        this.desafectaProdBP = new MZDesafectaProdBP(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        this.desafectaProdBP.getProducts();

        return "OK";
    }
}
