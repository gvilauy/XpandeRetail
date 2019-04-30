package org.xpande.retail.process;

import org.compiere.process.SvrProcess;
import org.xpande.retail.model.MZGeneraAstoVta;

/**
 * Proceso para obtener ventas por medio de pago en la generación de asientos de venta POS.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 4/29/19.
 */
public class ObtenerAstoVtaMP extends SvrProcess {

    MZGeneraAstoVta astoVta = null;

    @Override
    protected void prepare() {
        this.astoVta = new MZGeneraAstoVta(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        String message = this.astoVta.getVentasMedioPago();

        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";
    }
}
