package org.xpande.retail.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.xpande.retail.model.*;

import java.math.BigDecimal;

/**
 * Proceso para carga de datos desde archivo en Actualización PVP.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 5/15/18.
 */
public class InterfaceActPVP extends SvrProcess {

    private MZActualizacionPVP actualizacionPVP = null;
    private String fileName = "";

    @Override
    protected void prepare() {

        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){

            String name = para[i].getParameterName();

            if (name != null){
                if (para[i].getParameter() != null){
                    if (name.trim().equalsIgnoreCase("FileName")){
                        this.fileName = ((String)para[i].getParameter()).trim();
                    }
                }
            }
        }

        this.actualizacionPVP = new MZActualizacionPVP(getCtx(), this.getRecord_ID(), get_TrxName());

    }

    @Override
    protected String doIt() throws Exception {

        String message = this.actualizacionPVP.getDataFromFile(fileName);

        if (message != null){
            return "@Error@ " + message;
        }


        return "OK";
    }
}
