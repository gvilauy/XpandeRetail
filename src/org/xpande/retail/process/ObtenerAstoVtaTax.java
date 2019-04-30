package org.xpande.retail.process;

import org.compiere.process.SvrProcess;
import org.xpande.retail.model.MZGeneraAstoVta;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 4/29/19.
 */
public class ObtenerAstoVtaTax extends SvrProcess {

    MZGeneraAstoVta astoVta = null;

    @Override
    protected void prepare() {
        this.astoVta = new MZGeneraAstoVta(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        String message = this.astoVta.getVentasImpuesto();

        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";
    }

}
