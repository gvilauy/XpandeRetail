package org.xpande.retail.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MOrg;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.process.SvrProcess;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.ZipUtil;
import org.xpande.core.utils.PriceListUtils;
import org.xpande.retail.model.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 3/10/23.
 */
public class CloseRegOffer extends SvrProcess {

    private MZRegularOffer regularOffer = null;

    @Override
    protected void prepare() {
        this.regularOffer = new MZRegularOffer(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        try{
            if (!this.regularOffer.getDocStatus().equalsIgnoreCase(DocumentEngine.STATUS_Completed)){
                return null;
            }

            Timestamp today = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
            // Obtengo lineas del documento
            List<MZRegularOfferLine> offerLines = this.regularOffer.getLines();
            // Obtengo organizaciones que participan en este documento
            List<MZRegularOfferOrg> offerOrgs = this.regularOffer.getSelectedOrgs();
            // Genero nueva actualización pvp
            MZActualizacionPVP actualizacionPVP = new MZActualizacionPVP(getCtx(), 0, get_TrxName());
            actualizacionPVP.setAD_Org_ID(0);
            actualizacionPVP.setC_Currency_ID(142);
            actualizacionPVP.setC_DocType_ID(1000050);
            actualizacionPVP.setDateDoc(today);
            actualizacionPVP.setDescription("Generada en Finalización de Oferta número:" + this.regularOffer.getDocumentNo());
            actualizacionPVP.setOnlyOneOrg(false);
            actualizacionPVP.set_ValueOfColumn("SendSamePrice", true);
            actualizacionPVP.setValidFrom(actualizacionPVP.getDateDoc());
            // Obtengo lista de precios para organización y moneda
            MPriceList priceList = PriceListUtils.getPriceListByOrg(getCtx(), this.regularOffer.getAD_Client_ID(), 0, 142, true, null, null);
            if ((priceList != null) && (priceList.get_ID() > 0)){
                actualizacionPVP.setM_PriceList_ID(priceList.getM_PriceList_ID());
                // Obtengo versión de lista vigente
                MPriceListVersion plv = priceList.getPriceListVersion(null);
                if ((plv != null) && (plv.get_ID() > 0)){
                    actualizacionPVP.setM_PriceList_Version_ID(plv.get_ID());
                }
                else{
                    throw new AdempiereException("No se pudo obtener versión de lista de precios de venta para la organización: 0");
                }
            }
            else{
                throw new AdempiereException("No se pudo obtener lista de precios de venta para la organización: 0");
            }
            actualizacionPVP.saveEx();
            // Recorro y proceso lineas
            for (MZRegularOfferLine offerLine: offerLines){
                MZActualizacionPVPLin pvpLin = new MZActualizacionPVPLin(getCtx(), 0, get_TrxName());
                pvpLin.setZ_ActualizacionPVP_ID(actualizacionPVP.get_ID());
                pvpLin.setM_Product_ID(offerLine.getM_Product_ID());
                pvpLin.setUPC(offerLine.getUPC());
                pvpLin.saveEx();
            }
            // Completo actualizacion pvp
            if (!actualizacionPVP.processIt(X_Z_ActualizacionPVP.DOCACTION_Complete)){
                String message = "No se pudo generar la Actualizacion PVP";
                if (actualizacionPVP.getProcessMsg() != null){
                    message = actualizacionPVP.getProcessMsg();
                }
                throw new AdempiereException(message);
            }
            actualizacionPVP.saveEx();

            this.regularOffer.setDocStatus(X_Z_RegularOffer.DOCSTATUS_Closed);
            this.regularOffer.setDocAction(X_Z_RegularOffer.DOCACTION_None);
            this.regularOffer.saveEx();
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return "OK";
    }
}
