package org.xpande.retail.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MClient;
import org.compiere.model.MDocType;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.xpande.core.utils.DateUtils;
import org.xpande.retail.model.MZGeneraAstoVta;
import org.xpande.retail.model.MZPosVendor;
import org.xpande.sisteco.model.MZSistecoConfig;
import org.xpande.sisteco.model.MZSistecoConfigOrg;
import org.xpande.sisteco.model.MZSistecoInterfacePazos;
import org.xpande.stech.model.MZScanntechConfig;
import org.xpande.stech.model.MZScanntechConfigOrg;
import org.xpande.stech.model.MZStechInterfaceVta;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 5/22/20.
 */
public class GenerarAstoVtaPOS extends SvrProcess {

    private Timestamp fechaProceso = null;
    private int adOrgID = 0;
    private int zPosVendorID = -1;

    @Override
    protected void prepare() {

        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){

            String name = para[i].getParameterName();

            if (name != null){
                if (para[i].getParameter() != null){
                    if (name.trim().equalsIgnoreCase("DateTrx")){
                        this.fechaProceso = (Timestamp) para[i].getParameter();
                    }
                    else if (name.trim().equalsIgnoreCase("AD_Org_ID")){
                        this.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("Z_PosVendor_ID")){
                        this.zPosVendorID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                }
            }
        }
    }

    @Override
    protected String doIt() throws Exception {

        String message = null;

        try{

            // Fecha de proceso es del día anterior
            if (this.fechaProceso == null){
                this.fechaProceso = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
            }

            // Resto un día
            Date dateFechaAux = new Date(this.fechaProceso.getTime());
            dateFechaAux = DateUtils.addDays(dateFechaAux, -1);
            this.fechaProceso = new Timestamp(dateFechaAux.getTime());

            // Genero asiento de venta según proveedor de POS.
            MZPosVendor posVendor = new MZPosVendor(getCtx(), this.zPosVendorID, null);
            if (posVendor.getValue().equalsIgnoreCase("SISTECO")){
                message = this.generarAstoVtaSisteco();
            }
            else if (posVendor.getValue().equalsIgnoreCase("SCANNTECH")){
                message = this.generarAstoVtaScanntech();
            }

            if (message != null){
                return "@Error@ " + message;
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return "OK";
    }

    /***
     * Genera Asiento de Venta para POS Sisteco.
     * Xpande. Created by Gabriel Vila on 5/22/20.
     * @return
     */
    private String generarAstoVtaSisteco(){

        String message = null;

        try{

            MZSistecoConfig sistecoConfig = MZSistecoConfig.getDefault(getCtx(), null);

            // Si indico organización, proceso solo para esta, sino proceso para todas las que tenga asociadas a SISTECO
            List<MZSistecoConfigOrg> orgList = sistecoConfig.getOrganizationsByOrg(this.adOrgID);

            // Seteo info de contexto
            Env.setContext(getCtx(), "AD_Client_ID", sistecoConfig.getAD_Client_ID());

            for (MZSistecoConfigOrg configOrg: orgList){

                // Seteo info de contexto
                Env.setContext(getCtx(), "AD_Org_ID", configOrg.getAD_OrgTrx_ID());

                // Obtengo interface de venta para esta organización y fecha pos
                MZSistecoInterfacePazos interfacePazos = MZSistecoInterfacePazos.getByOrgDate(getCtx(), configOrg.getAD_OrgTrx_ID(), this.fechaProceso, get_TrxName());

                // Si tengo interface de venta procesada para esta organización u fecha pos
                if ((interfacePazos != null) && (interfacePazos.get_ID() > 0)){

                    // Genero asiento de venta

                    MDocType[] docTypeVtaList = MDocType.getOfDocBaseType(getCtx(), "AVG");
                    if (docTypeVtaList.length <= 0){
                        return "Falta definr Tipo de Documento para Generación de Asiento de Venta POS.";
                    }

                    MClient client = new MClient(getCtx(), interfacePazos.getAD_Client_ID(), null);

                    MZGeneraAstoVta astoVta = new MZGeneraAstoVta(getCtx(), 0, get_TrxName());
                    astoVta.set_ValueOfColumn("AD_Client_ID", interfacePazos.getAD_Client_ID());
                    astoVta.setAD_Org_ID(interfacePazos.getAD_Org_ID());
                    astoVta.setDateDoc(interfacePazos.getDateTrx());
                    astoVta.setDateAcct(interfacePazos.getDateTrx());
                    astoVta.setDateTo(interfacePazos.getDateTrx());
                    astoVta.setC_DocType_ID(docTypeVtaList[0].get_ID());
                    astoVta.setC_AcctSchema_ID(client.getAcctSchema().get_ID());
                    astoVta.setC_Currency_ID(client.getAcctSchema().getC_Currency_ID());
                    astoVta.setDescription("Generada Automáticamente");
                    astoVta.saveEx();
                    astoVta.getInfo();
                    astoVta.saveEx();
                }

            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return message;
    }

    /***
     * Genera Asiento de Venta para POS Scanntech.
     * Xpande. Created by Gabriel Vila on 5/22/20.
     * @return
     */
    private String generarAstoVtaScanntech(){

        String message = null;

        try{

            MZScanntechConfig scanntechConfig = MZScanntechConfig.getDefault(getCtx(), get_TrxName());

            // Si indico organización, proceso solo para esta, sino proceso para todas las que tenga asociadas al proveedor de POS
            List<MZScanntechConfigOrg> orgList = scanntechConfig.getOrganizationsByOrg(this.adOrgID);

            // Seteo info de contexto
            Env.setContext(getCtx(), "AD_Client_ID", scanntechConfig.getAD_Client_ID());

            for (MZScanntechConfigOrg configOrg: orgList){

                // Seteo info de contexto
                Env.setContext(getCtx(), "AD_Org_ID", configOrg.getAD_OrgTrx_ID());

                // Obtengo interface de venta para esta organización y fecha pos
                MZStechInterfaceVta interfaceVta = MZStechInterfaceVta.getByOrgDate(getCtx(), configOrg.getAD_OrgTrx_ID(), this.fechaProceso, get_TrxName());

                // Si tengo interface de venta procesada para esta organización u fecha pos
                if ((interfaceVta != null) && (interfaceVta.get_ID() > 0)){

                    // Genero asiento de venta

                    MDocType[] docTypeVtaList = MDocType.getOfDocBaseType(getCtx(), "AVG");
                    if (docTypeVtaList.length <= 0){
                        return "Falta definr Tipo de Documento para Generación de Asiento de Venta POS.";
                    }

                    MClient client = new MClient(getCtx(), interfaceVta.getAD_Client_ID(), null);

                    MZGeneraAstoVta astoVta = new MZGeneraAstoVta(getCtx(), 0, get_TrxName());
                    astoVta.set_ValueOfColumn("AD_Client_ID", interfaceVta.getAD_Client_ID());
                    astoVta.setAD_Org_ID(interfaceVta.getAD_Org_ID());
                    astoVta.setDateDoc(interfaceVta.getDateTrx());
                    astoVta.setDateAcct(interfaceVta.getDateTrx());
                    astoVta.setDateTo(interfaceVta.getDateTrx());
                    astoVta.setC_DocType_ID(docTypeVtaList[0].get_ID());
                    astoVta.setC_AcctSchema_ID(client.getAcctSchema().get_ID());
                    astoVta.setC_Currency_ID(client.getAcctSchema().getC_Currency_ID());
                    astoVta.setDescription("Generada Automáticamente");
                    astoVta.saveEx();
                    astoVta.getInfo();
                    astoVta.saveEx();
                }

            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return message;
    }

}
