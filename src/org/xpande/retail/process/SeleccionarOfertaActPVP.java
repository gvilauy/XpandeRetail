package org.xpande.retail.process;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.xpande.retail.model.MZActualizacionPVP;
import org.xpande.retail.model.X_Z_OfertaVenta;

import java.math.BigDecimal;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 1/17/18.
 */
public class SeleccionarOfertaActPVP extends SvrProcess {

    private MZActualizacionPVP actualizacionPVP = null;
    private int zOfertaVentaID = 0;

    @Override
    protected void prepare() {

        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){

            String name = para[i].getParameterName();

            if (name != null){

                if (para[i].getParameter() != null){

                    if (name.trim().equalsIgnoreCase(X_Z_OfertaVenta.COLUMNNAME_Z_OfertaVenta_ID)){
                        this.zOfertaVentaID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                }
            }
        }

        this.actualizacionPVP = new MZActualizacionPVP(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        String message = this.actualizacionPVP.getInfoFromOffer(zOfertaVentaID);

        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";
    }

}
