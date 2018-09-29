package org.xpande.retail.process;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.xpande.retail.model.MZLineaProductoSocio;
import org.xpande.retail.model.X_Z_LineaProductoSocio;
import org.xpande.retail.model.X_Z_ProductoSeccion;

import java.math.BigDecimal;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/2/17.
 */
public class AgregarDistribuidor extends SvrProcess {

    MZLineaProductoSocio lineaProductoSocio = null;
    private int cBPartnerDistribuidorID = 0;

    @Override
    protected void prepare() {

        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){

            String name = para[i].getParameterName();

            if (name != null){
                if (para[i].getParameter() != null){
                    if (name.trim().equalsIgnoreCase(X_Z_LineaProductoSocio.COLUMNNAME_C_BPartnerRelation_ID)){
                        this.cBPartnerDistribuidorID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                }
            }
        }

        this.lineaProductoSocio = new MZLineaProductoSocio(getCtx(), this.getRecord_ID(), get_TrxName());

    }

    @Override
    protected String doIt() throws Exception {

        String message = this.lineaProductoSocio.setNuevoDistribuidor(this.cBPartnerDistribuidorID);

        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";
    }
}
