package org.xpande.retail.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.xpande.retail.model.MZActualizacionPVP;
import org.xpande.retail.model.MZConfirmacionEtiquetaDoc;

/**
 * Proceso para acciones masivas en productos de un determinado documento de una confirmacion de precios al local.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 5/23/18.
 */
public class AccionProdConfEtiqueta extends SvrProcess {

    MZConfirmacionEtiquetaDoc etiquetaDoc = null;
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

        this.etiquetaDoc = new MZConfirmacionEtiquetaDoc(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        try{

            if (this.tipoAccion == null) return "No se obtuvo acción a considerar.";

            if (this.tipoAccion.equalsIgnoreCase("IMPRIMIR")){
                this.etiquetaDoc.setProductosImprimir(true);
            }
            else if (this.tipoAccion.equalsIgnoreCase("NO_IMPRIMIR")){
                this.etiquetaDoc.setProductosImprimir(false);
            }
            if (this.tipoAccion.equalsIgnoreCase("OMITIR")){
                this.etiquetaDoc.setProductosOmitir(true);
            }
            else if (this.tipoAccion.equalsIgnoreCase("NO_OMITIR")){
                this.etiquetaDoc.setProductosOmitir(false);
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return "OK";
    }


}
