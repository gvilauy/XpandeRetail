package org.xpande.retail.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.xpande.retail.model.MZConfirmacionEtiqueta;
import org.xpande.retail.model.MZConfirmacionEtiquetaDoc;

import java.util.List;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 9/15/18.
 */
public class AccionAllConfEtiqueta extends SvrProcess {

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
            List<MZConfirmacionEtiquetaDoc> etiquetaDocList = this.etiqueta.getSelectedEtiquetaDocs();
            for (MZConfirmacionEtiquetaDoc etiquetaDoc: etiquetaDocList){

                if (this.tipoAccion.equalsIgnoreCase("IMPRIMIR")){
                    etiquetaDoc.setProductosImprimir(true);
                }
                else if (this.tipoAccion.equalsIgnoreCase("NO_IMPRIMIR")){
                    etiquetaDoc.setProductosImprimir(false);
                }
                if (this.tipoAccion.equalsIgnoreCase("OMITIR")){
                    etiquetaDoc.setProductosOmitir(true);
                }
                else if (this.tipoAccion.equalsIgnoreCase("NO_OMITIR")){
                    etiquetaDoc.setProductosOmitir(false);
                }
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return "OK";
    }

}
