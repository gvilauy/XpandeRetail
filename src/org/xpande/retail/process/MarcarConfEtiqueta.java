package org.xpande.retail.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.xpande.retail.model.MZConfirmacionEtiqueta;
import org.xpande.retail.model.MZConfirmacionEtiquetaDoc;

import java.util.List;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 3/15/21.
 */
public class MarcarConfEtiqueta extends SvrProcess {

    MZConfirmacionEtiqueta etiqueta = null;
    private String tipoAccion = null;

    @Override
    protected void prepare() {

        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){

            String name = para[i].getParameterName();

            if (name != null){
                if (para[i].getParameter() != null){
                    if (name.trim().equalsIgnoreCase("TipoAccion")){
                        this.tipoAccion = ((String)para[i].getParameter()).trim();
                    }
                }
            }
        }

        this.etiqueta = new MZConfirmacionEtiqueta(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        try{

            if (this.tipoAccion == null) return "No se obtuvo acción a considerar.";

            // Obtengo y recorro todos los documentos contenidos en esta comunicación al local
            List<MZConfirmacionEtiquetaDoc> etiquetaDocList = this.etiqueta.getEtiquetaDocs();
            for (MZConfirmacionEtiquetaDoc etiquetaDoc: etiquetaDocList){

                if (this.tipoAccion.equalsIgnoreCase("MARCAR")){
                    etiquetaDoc.setIsSelected(true);
                }
                else if (this.tipoAccion.equalsIgnoreCase("DESMARCAR")){
                    etiquetaDoc.setIsSelected(false);
                }
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return "OK";
    }
}
