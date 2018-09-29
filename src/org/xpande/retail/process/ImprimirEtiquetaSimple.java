package org.xpande.retail.process;

import org.compiere.apps.ProcessCtl;
import org.compiere.model.MPInstance;
import org.compiere.model.MPInstancePara;
import org.compiere.process.ProcessInfo;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.xpande.core.model.MZFormatoEtiqueta;
import org.xpande.retail.model.MZImpresionEtiquetaSimple;
import org.xpande.retail.model.X_Z_ConfirmacionEtiqueta;

import java.math.BigDecimal;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/31/17.
 */
public class ImprimirEtiquetaSimple extends SvrProcess {

    MZImpresionEtiquetaSimple etiquetaSimple = null;

    @Override
    protected void prepare() {

        this.etiquetaSimple = new MZImpresionEtiquetaSimple(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {


        // Genero registros para impresión de etiquetas
        this.etiquetaSimple.generatePrintRecords();

        // Obtengo id de proceso de impresion asociado al formato de etiqueta del documento
        MZFormatoEtiqueta formatoEtiqueta = new MZFormatoEtiqueta(getCtx(), this.etiquetaSimple.getZ_FormatoEtiqueta_ID(), null);
        int processID = formatoEtiqueta.getAD_Process_ID();


        // Mando vista previa del reporte asociado al formato de etiqueta
        MPInstance instance = new MPInstance(Env.getCtx(), processID, 0);
        instance.saveEx();

        ProcessInfo pi = new ProcessInfo ("ImpresionEtiquetasSimple", processID);
        pi.setAD_PInstance_ID (instance.getAD_PInstance_ID());
        pi.setPrintPreview(true);

        MPInstancePara para = new MPInstancePara(instance, 10);
        para.setParameter(X_Z_ConfirmacionEtiqueta.COLUMNNAME_Impresion_ID, new BigDecimal(this.etiquetaSimple.getImpresion_ID()));
        para.saveEx();

        ProcessCtl worker = new ProcessCtl(null, 0, pi, null);
        worker.start();


        return "OK";
    }
}
