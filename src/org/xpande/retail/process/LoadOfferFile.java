package org.xpande.retail.process;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.xpande.retail.model.MZRegularOffer;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 12/19/22.
 */
public class LoadOfferFile extends SvrProcess {

    private MZRegularOffer regularOffer = null;
    String fileName = null;

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
        this.regularOffer = new MZRegularOffer(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        // Ejecuto el proceso en el modelo
        String message = this.regularOffer.loadDataFile(fileName);

        if (message != null){
            return "@Error@ " + message;
        }
        return "OK";
    }
}
