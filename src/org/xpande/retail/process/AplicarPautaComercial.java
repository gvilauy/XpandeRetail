package org.xpande.retail.process;

import org.compiere.process.SvrProcess;
import org.xpande.retail.model.MZPautaComercial;

/**
 * Proceso para Aplicar una Pauta Comercial del módulo de Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/18/17.
 */
public class AplicarPautaComercial extends SvrProcess {

    private MZPautaComercial pauta = null;

    @Override
    protected void prepare() {
        pauta = new MZPautaComercial(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        String message = pauta.applyPauta();

        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";
    }

}
