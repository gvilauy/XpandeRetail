package org.xpande.retail.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.process.DocAction;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.xpande.retail.model.MZComunicacionPOS;
import org.xpande.retail.model.MZConfirmacionEtiqueta;
import org.xpande.retail.model.MZRetailConfig;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;

/**
 * Proceso para comunicar de manera automática, ofertas periódicas al POS. Solamente aquellas que ya fueron previamente comunicadas al local y
 * que estan en fecha.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 1/15/18.
 */
public class ComunicarOfertasPOS extends SvrProcess {

    @Override
    protected void prepare() {

    }

    @Override
    protected String doIt() throws Exception {

        String message = null;

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{

            Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

            // Instancio configurador de retail
            MZRetailConfig retailConfig = MZRetailConfig.getDefault(getCtx(), get_TrxName());

            // Obtengo documento para procesos de Comunicacion al POS.
            MDocType[] docTypes = MDocType.getOfDocBaseType(getCtx(), "RCP");
            if (docTypes.length <= 0) {
                return "@Error@ " + "No se obtuvo Documento para el proceso de Comunicación al POS";
            }
            MDocType docType = docTypes[0];

            // Obtengo documentos de confirmaciones al local a considerar, ordenados por organización
            sql = " select a.z_confirmacionetiqueta_id, a.ad_org_id " +
                    "from z_confirmacionetiqueta a " +
                    "inner join z_confirmacionetiquetadoc b on a.z_confirmacionetiqueta_id  = b.z_confirmacionetiqueta_id " +
                    "where a.docstatus='CO' " +
                    "and a.comunicadopos ='N' " +
                    "and (b.datetopos is not null and b.datetopos <='" + fechaHoy + "') " +
                    "and b.comunicadopos ='N' " +
                    "order by a.ad_org_id, a.datedoc";

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            // Corte de control por organización, ya que debo generar un documento de comunnicacio al POS por organización.
            int adOrgIDAux = -1;
            MZComunicacionPOS comunicacionPOS = null;

            while(rs.next()){

                if (adOrgIDAux != rs.getInt("ad_org_id")){

                    if (comunicacionPOS != null){
                        // Completo modelo de comunicacion pos al cambiar la organizacion
                        if (!comunicacionPOS.processIt(DocAction.ACTION_Complete)) {

                            String messageError = comunicacionPOS.getProcessMsg();
                            if (messageError == null){
                                messageError = "Se detectaron errores al momneto de completar este documento.";
                            }
                            comunicacionPOS.setErrorMsg(messageError);
                            comunicacionPOS.saveEx();
                        }
                    }

                    adOrgIDAux = rs.getInt("ad_org_id");

                    comunicacionPOS = new MZComunicacionPOS(getCtx(), 0, get_TrxName());
                    comunicacionPOS.set_ValueOfColumn("AD_Client_ID", retailConfig.getAD_Client_ID());
                    comunicacionPOS.setAD_Org_ID(adOrgIDAux);
                    comunicacionPOS.setC_DocType_ID(docType.get_ID());
                    comunicacionPOS.setDateDoc(fechaHoy);
                    comunicacionPOS.setDescription("Generado Automáticamente");
                    comunicacionPOS.setOnlyBasicData(false);
                    comunicacionPOS.saveEx();
                }

                MZConfirmacionEtiqueta confirmacionEtiqueta = new MZConfirmacionEtiqueta(getCtx(), rs.getInt("z_confirmacionetiqueta_id"), get_TrxName());
                confirmacionEtiqueta.setZ_ComunicacionPOS_ID(comunicacionPOS.get_ID());
                confirmacionEtiqueta.saveEx();
            }

            // Proceso ultima comunicacion cargada
            if (comunicacionPOS != null){
                // Completo modelo de comunicacion pos al cambiar la organizacion
                if (!comunicacionPOS.processIt(DocAction.ACTION_Complete)) {

                    String messageError = comunicacionPOS.getProcessMsg();
                    if (messageError == null){
                        messageError = "Se detectaron errores al momneto de completar este documento.";
                    }
                    comunicacionPOS.setErrorMsg(messageError);
                }
                comunicacionPOS.saveEx();
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
            rs = null; pstmt = null;
        }

        return "OK";
    }

}
