package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProductPrice;
import org.compiere.util.DB;
import org.xpande.core.utils.PriceListUtils;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Modelo para organizaciones a procesar en la actualización PVP.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/19/17.
 */
public class MZActualizacionPVPOrg extends X_Z_ActualizacionPVPOrg {

    public MZActualizacionPVPOrg(Properties ctx, int Z_ActualizacionPVPOrg_ID, String trxName) {
        super(ctx, Z_ActualizacionPVPOrg_ID, trxName);
    }

    public MZActualizacionPVPOrg(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Actualiza lista de precios de venta de esta organización para el producto de la linea recibida.
     * Xpande. Created by Gabriel Vila on 7/20/17.
     * @param mProductID
     * @param cCurrencyID
     * @param newPriceSO
     * @param fechaVigencia
     */
    public void updateProductPriceListSO(int mProductID, int cCurrencyID, BigDecimal newPriceSO, Timestamp fechaVigencia) {

        try{

            // Obtengo lista de venta para organización seleccionada en este documento y moneda.
            // Si ya tengo seteada esta lista en este modelo, la utilizo, sino la seteo ahora.
            MPriceList plVenta = null;
            MPriceListVersion plVersionVenta = null;

            MZActualizacionPVP actualizacionPVP = (MZActualizacionPVP) this.getZ_ActualizacionPVP();

            if (this.getM_PriceList_ID() <= 0){
                plVenta = PriceListUtils.getPriceListByOrg(getCtx(), this.getAD_Client_ID(), this.getAD_OrgTrx_ID(), cCurrencyID, true, null, get_TrxName());
                if ((plVenta == null) || (plVenta.get_ID() <= 0)){
                    throw new AdempiereException("No se pudo obtener Lista de Precios de Venta para organización : " + this.getAD_OrgTrx_ID() + ", moneda : " + cCurrencyID);
                }
                plVersionVenta = plVenta.getPriceListVersion(null);
                if ((plVersionVenta == null) || (plVersionVenta.get_ID() <= 0)){
                    throw new AdempiereException("No se pudo obtener Versión de Lista de Precios de Venta para organización : " + this.getAD_OrgTrx_ID() + ", moneda : " + cCurrencyID);
                }
                this.setM_PriceList_ID(plVenta.get_ID());
                this.setM_PriceList_Version_ID(plVersionVenta.get_ID());
                this.saveEx();
            }
            else{
                plVenta = new MPriceList(getCtx(), this.getM_PriceList_ID(), get_TrxName());
                plVersionVenta = plVenta.getPriceListVersion(fechaVigencia);
            }

            // Intento obtener precio de lista actual para el producto de esta linea, en la versión de lista
            // de precios de venta recibida.
            MProductPrice pprice = MProductPrice.get(getCtx(), plVersionVenta.get_ID(), mProductID, get_TrxName());

            // Si no tengo precio para este producto, lo creo.
            if ((pprice == null) || (pprice.getM_Product_ID() <= 0)){
                pprice = new MProductPrice(plVersionVenta, mProductID, newPriceSO, newPriceSO, newPriceSO);
            }
            else{
                // Actualizo precios
                pprice.setPriceList(newPriceSO);
                pprice.setPriceStd(newPriceSO);
                pprice.setPriceLimit(newPriceSO);
            }
            pprice.set_ValueOfColumn("C_DocType_ID", actualizacionPVP.getC_DocType_ID());
            pprice.set_ValueOfColumn("DocumentNoRef", actualizacionPVP.getDocumentNo());
            pprice.set_ValueOfColumn("ValidFrom", fechaVigencia);
            pprice.saveEx();

            // Actualizo datos venta para el producto en esta lista en asociaciones producto-socio-org
            String action = " update z_productosocioorg " +
                    " set priceso =" + newPriceSO + ", " +
                    " datevalidso ='" + fechaVigencia + "' " +
                    " where z_productosocio_id in (select z_productosocio_id from z_productosocio where m_product_id =" + mProductID + ")" +
                    " and ad_orgtrx_id =" + this.getAD_OrgTrx_ID() +
                    " and m_pricelist_id_so =" + plVenta.get_ID();
            DB.executeUpdateEx(action, get_TrxName());

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }
}
