package org.xpande.retail.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDocType;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPrice;
import org.compiere.process.DocAction;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.xpande.core.utils.DateUtils;
import org.xpande.retail.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

/**
 * Proceso que genera documentos de Actualización de PVP en base a las ofertas periódicas que ya fueron previamente comunicadas al local y
 * que estan en fecha.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 1/17/18.
 */
public class GenerarActPVPOferta extends SvrProcess {

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

            // Obtengo documento para procesos de Actualizacion PVP
            MDocType[] docTypes = MDocType.getOfDocBaseType(getCtx(), "PVP");
            if (docTypes.length <= 0) {
                return "@Error@ " + "No se obtuvo Documento para el proceso de Actualización PVP";
            }
            MDocType docType = docTypes[0];

            // Obtengo ofertas periódicas que se vencen un día despues de hoy y no hayan sido procesadas por este batch
            sql = " select z_ofertaventa_id, cast((EndDate +1) as timestamp without time zone) as DateToPos " +
                    " from z_ofertaventa " +
                    " where cast((EndDate -1) as timestamp without time zone) <= cast(date_trunc('day', now()) as timestamp without time zone) " +
                    " and DocStatus='CO' " +
                    " and Z_ActualizacionPVP_ID is null " +
                    " order by datedoc";

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            while(rs.next()){

                // Instancio modelo de oferta
                MZOfertaVenta ofertaVenta = new MZOfertaVenta(getCtx(), rs.getInt("z_ofertaventa_id"), get_TrxName());

                // Obtengo lista de organizaciones que se consideran en esta oferta
                List<MZOfertaVentaOrg> ventaOrgList = ofertaVenta.getOrgsSelected();

                // Si solo tengo una organización en la oferta, marco actualizacion como de una sola organizacion
                int adOrgIDAux = -1;
                if (ventaOrgList.size() == 1){
                    adOrgIDAux = ventaOrgList.get(0).getAD_OrgTrx_ID();
                }

                // Genero nuevo modelo de actualización PVP para esta oferta
                MZActualizacionPVP actualizacionPVP = new MZActualizacionPVP(getCtx(), 0, get_TrxName());
                actualizacionPVP.set_ValueOfColumn("AD_Client_ID", retailConfig.getAD_Client_ID());
                actualizacionPVP.setAD_Org_ID(0);  // Multi organización
                actualizacionPVP.setC_DocType_ID(docType.get_ID());
                actualizacionPVP.setDateDoc(fechaHoy);
                actualizacionPVP.setDescription("Generado Automáticamente para Ofertas a Vencer");
                actualizacionPVP.setC_Currency_ID(ofertaVenta.getC_Currency_ID_SO());
                actualizacionPVP.setM_PriceList_ID(ofertaVenta.getM_PriceList_ID_SO());
                actualizacionPVP.setM_PriceList_Version_ID(ofertaVenta.getM_PriceList_Version_ID_SO());
                actualizacionPVP.setZ_OfertaVenta_ID(ofertaVenta.get_ID());
                actualizacionPVP.setDateToPos(rs.getTimestamp("DateToPos"));

                // Organizaciones del modelo de actualización según organizaciones de la oferta
                if (adOrgIDAux > 0){
                    actualizacionPVP.setAD_Org_ID(adOrgIDAux);
                    actualizacionPVP.setOnlyOneOrg(true);
                }
                else{
                    // Multi-organización
                    actualizacionPVP.setAD_Org_ID(0);
                    actualizacionPVP.setOnlyOneOrg(false);
                }
                actualizacionPVP.saveEx();

                // Asocio oferta con actualizacion pvp
                ofertaVenta.setZ_ActualizacionPVP_ID(actualizacionPVP.get_ID());
                ofertaVenta.saveEx();;

                // Elimino organizaciones actuales de la actualización ya que tiene que ser identicas a las de la oferta
                String action = " delete from z_actualizacionpvporg where z_actualizacionpvp_id =" + actualizacionPVP.get_ID();
                DB.executeUpdateEx(action, get_TrxName());

                // Copio organizaciones a considerar de la oferta en este modelo de actualizacion pvp
                for (MZOfertaVentaOrg ventaOrg: ventaOrgList){
                    MZActualizacionPVPOrg actualizacionPVPOrg = new MZActualizacionPVPOrg(getCtx(), 0, get_TrxName());
                    actualizacionPVPOrg.set_ValueOfColumn("AD_Client_ID", actualizacionPVP.getAD_Client_ID());
                    actualizacionPVPOrg.setAD_Org_ID(actualizacionPVP.getAD_Org_ID());
                    actualizacionPVPOrg.setZ_ActualizacionPVP_ID(actualizacionPVP.get_ID());
                    actualizacionPVPOrg.setAD_OrgTrx_ID(ventaOrg.getAD_OrgTrx_ID());
                    actualizacionPVPOrg.setIsSelected(true);
                    actualizacionPVPOrg.saveEx();
                }

                // Genero lineas de la actualización PVP a partir de las lineas de la oferta
                List<MZOfertaVentaLin> ventaLinList = ofertaVenta.getLines();
                for (MZOfertaVentaLin ventaLin: ventaLinList){

                    if ((ventaLin.getNewPriceSO() != null) && (ventaLin.getNewPriceSO().compareTo(Env.ZERO) > 0)){

                        MProduct product = (MProduct) ventaLin.getM_Product();

                        // Precio actual del producto en lista de precios
                        MProductPrice productPrice = MProductPrice.get(getCtx(), actualizacionPVP.getM_PriceList_Version_ID(), product.get_ID(), get_TrxName());
                        if (productPrice == null){
                            return "No se pudo obtener precio de venta para el producto : " + product.getValue() + " - " + product.getName();
                        }

                        MZActualizacionPVPLin pvpLin = new MZActualizacionPVPLin(getCtx(), 0, get_TrxName());
                        pvpLin.setZ_ActualizacionPVP_ID(actualizacionPVP.get_ID());
                        pvpLin.setZ_ProductoSeccion_ID(product.get_ValueAsInt("Z_ProductoSeccion_ID"));
                        pvpLin.setZ_ProductoRubro_ID(product.get_ValueAsInt("Z_ProductoRubro_ID"));

                        if (product.get_ValueAsInt("Z_ProductoFamilia_ID") > 0){
                            pvpLin.setZ_ProductoFamilia_ID(product.get_ValueAsInt("Z_ProductoFamilia_ID"));
                        }
                        if (product.get_ValueAsInt("Z_ProductoSubfamilia_ID") > 0){
                            pvpLin.setZ_ProductoSubfamilia_ID(product.get_ValueAsInt("Z_ProductoSubfamilia_ID"));
                        }

                        pvpLin.setM_Product_ID(product.get_ID());
                        pvpLin.setC_Currency_ID(actualizacionPVP.getC_Currency_ID());
                        pvpLin.setC_TaxCategory_ID(product.getC_TaxCategory_ID());
                        pvpLin.setC_UOM_ID(product.getC_UOM_ID());
                        pvpLin.setDescription("Generada Automáticamente");
                        pvpLin.setDistinctPriceSO(false);
                        pvpLin.setInternalCode(product.getValue());
                        pvpLin.setName(product.getName());
                        pvpLin.setPriceSO(ventaLin.getNewPriceSO());
                        pvpLin.setNewPriceSO(productPrice.getPriceList());
                        pvpLin.setUPC(ventaLin.getUPC());
                        pvpLin.saveEx();
                    }

                }

                // Completo actualización PVP asociada a esta oferta
                if (!actualizacionPVP.processIt(DocAction.ACTION_Complete)){

                    String mesageError = actualizacionPVP.getProcessMsg();
                    if (mesageError == null){
                        mesageError = "No se pudo completar Actualización PVP para Oferta número : " + ofertaVenta.getDocumentNo();
                    }
                    actualizacionPVP.setErrorMsg(mesageError);
                }
                actualizacionPVP.saveEx();

            } // Loop de ofertas

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
