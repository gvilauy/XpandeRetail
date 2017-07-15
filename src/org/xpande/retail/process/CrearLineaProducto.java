package org.xpande.retail.process;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.xpande.retail.model.MZPreciosProvCab;
import org.xpande.retail.model.X_Z_LineaProductoGral;
import org.xpande.retail.model.X_Z_LineaProductoSocio;

import java.math.BigDecimal;

/**
 * Proceso que crea una nueva linea de productos
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/15/17.
 */
public class CrearLineaProducto extends SvrProcess {

    MZPreciosProvCab preciosProvCab = null;

    String nombreLinea = "";
    String descripcionLinea = "";

    @Override
    protected void prepare() {

        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){

            String name = para[i].getParameterName();

            if (name != null){
                if (para[i].getParameter() != null){
                    if (name.trim().equalsIgnoreCase(X_Z_LineaProductoGral.COLUMNNAME_Name)){
                        this.nombreLinea = (String)para[i].getParameter().toString().trim();
                    }
                    if (name.trim().equalsIgnoreCase(X_Z_LineaProductoGral.COLUMNNAME_Description)){
                        this.descripcionLinea = (String)para[i].getParameter().toString().trim();
                    }
                }
            }
        }

        this.preciosProvCab = new MZPreciosProvCab(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        String message = preciosProvCab.crearLineaProducto(nombreLinea, descripcionLinea);

        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";
    }

}
