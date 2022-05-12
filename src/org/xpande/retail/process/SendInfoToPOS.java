package org.xpande.retail.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.process.DocumentEngine;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.xpande.core.utils.PriceListUtils;
import org.xpande.geocom.model.MZGCInterfaceOut;
import org.xpande.geocom.model.X_Z_GCInterfaceOut;
import org.xpande.retail.model.MZComunicacionPOS;
import org.xpande.retail.model.MZConfirmacionEtiqueta;
import org.xpande.retail.model.MZConfirmacionEtiquetaDoc;
import org.xpande.retail.model.MZConfirmacionEtiquetaProd;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 5/3/22.
 */
public class SendInfoToPOS extends SvrProcess {

    private String documentNo = "";
    private MZComunicacionPOS comunicacionPOS = null;

    @Override
    protected void prepare() {
        ProcessInfoParameter[] para = getParameter();
        for (int i = 0; i < para.length; i++){
            String name = para[i].getParameterName();
            if (name != null){
                if (para[i].getParameter() != null){
                    if (name.trim().equalsIgnoreCase("DocumentNoRef")){
                        this.documentNo = ((String)para[i].getParameter()).trim();
                    }
                }
            }
        }
    }

    @Override
    protected String doIt() throws Exception {

        try{
            String sql = " select z_comunicacionpos_id from z_comunicacionpos where documentno='" + this.documentNo + "' ";
            int zComunicacionPOSID = DB.getSQLValueEx(get_TrxName(), sql);
            if (zComunicacionPOSID <= 0){
                throw new AdempiereException("No existe un documento de Comunicación al POS con ese número.");
            }
            this.comunicacionPOS = new MZComunicacionPOS(getCtx(), zComunicacionPOSID, get_TrxName());
            if (!comunicacionPOS.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Completed)){
                throw new AdempiereException("El Documento de Comunicación al POS no esta COMPLETADO");
            }
            Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
            List<MZConfirmacionEtiqueta> confirmacionEtiquetaList = comunicacionPOS.getConfEtiquetas();
            for (MZConfirmacionEtiqueta confirmacionEtiqueta: confirmacionEtiquetaList) {
                List<MZConfirmacionEtiquetaDoc> etiquetaDocs = confirmacionEtiqueta.getConfirmedEtiquetaDocs();
                for (MZConfirmacionEtiquetaDoc etiquetaDoc: etiquetaDocs) {
                    List<MZConfirmacionEtiquetaProd> etiquetaProds = etiquetaDoc.getProducts();
                    for (MZConfirmacionEtiquetaProd etiquetaProd: etiquetaProds){
                        if ((etiquetaProd.isPrinted()) || (etiquetaProd.isOmitted())){
                            MZGCInterfaceOut gcOut = new MZGCInterfaceOut(getCtx(), 0, get_TrxName());
                            gcOut.setAD_Org_ID(this.comunicacionPOS.getAD_Org_ID());
                            gcOut.setCRUDType(X_Z_GCInterfaceOut.CRUDTYPE_UPDATE);
                            gcOut.setM_Product_ID(etiquetaProd.getM_Product_ID());
                            MProduct product = (MProduct) gcOut.getM_Product();
                            MTaxCategory taxCategory = (MTaxCategory) product.getC_TaxCategory();
                            gcOut.setCodImpuestoPOS(taxCategory.getCommodityCode());
                            gcOut.setISO_Code("UYU");
                            gcOut.setTipoDatoInterface("P");
                            gcOut.setDateTrx(fechaHoy);
                            gcOut.setPrice(etiquetaProd.getPriceSO());
                            gcOut.saveEx();
                        }
                    }
                }
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return "OK";
    }
}
