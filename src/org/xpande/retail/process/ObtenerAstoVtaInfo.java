package org.xpande.retail.process;

import org.compiere.process.SvrProcess;
import org.xpande.retail.model.MZGeneraAstoVta;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 5/22/20.
 */
public class ObtenerAstoVtaInfo extends SvrProcess {

    MZGeneraAstoVta astoVta = null;

    @Override
    protected void prepare() {
        this.astoVta = new MZGeneraAstoVta(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        String message = "";

        // Cargo información de medios de pago e impuestos.
        message = this.astoVta.getVentasMedioPago();
        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";
    }

}
