package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProductPrice;
import org.xpande.core.utils.PriceListUtils;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de organización que se procesa en la gestión de precios de proveedor.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/15/17.
 */
public class MZPreciosProvOrg extends X_Z_PreciosProvOrg {

    public MZPreciosProvOrg(Properties ctx, int Z_PreciosProvOrg_ID, String trxName) {
        super(ctx, Z_PreciosProvOrg_ID, trxName);
    }

    public MZPreciosProvOrg(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Actualiza lista de precios de venta de esta organización para el producto de la linea recibida.
     * Xpande. Created by Gabriel Vila on 6/21/17.
     * @param plVenta
     * @param plVersionVenta
     * @param line
     */
    public void updateProductPriceListSO(MZPreciosProvLin line, int cCurrencyID) {

        try{

            // Obtengo lista de venta para organización seleccionada en este documento y moneda.
            // Si ya tengo seteada esta lista en este modelo, la utilizo, sino la seteo ahora.
            MPriceList plVenta = null;
            MPriceListVersion plVersionVenta = null;

            if (this.getM_PriceList_ID_SO() <= 0){
                plVenta = PriceListUtils.getPriceListByOrg(getCtx(), line.getAD_Client_ID(), this.getAD_OrgTrx_ID(), cCurrencyID, true, get_TrxName());
                if ((plVenta == null) || (plVenta.get_ID() <= 0)){
                    throw new AdempiereException("No se pudo obtener Lista de Precios de Venta para organización : " + this.getAD_OrgTrx_ID() + ", moneda : " + cCurrencyID);
                }
                plVersionVenta = plVenta.getPriceListVersion(null);
                if ((plVersionVenta == null) || (plVersionVenta.get_ID() <= 0)){
                    throw new AdempiereException("No se pudo obtener Versión de Lista de Precios de Venta para organización : " + this.getAD_OrgTrx_ID() + ", moneda : " + cCurrencyID);
                }
                this.setM_PriceList_ID_SO(plVenta.get_ID());
                this.setM_PriceList_Version_ID_SO(plVersionVenta.get_ID());
                this.saveEx();
            }
            else{
                plVenta = new MPriceList(getCtx(), this.getM_PriceList_ID_SO(), get_TrxName());
                plVersionVenta = new MPriceListVersion(getCtx(), this.getM_PriceList_Version_ID_SO(), get_TrxName());
            }

            // Intento obtener precio de lista actual para el producto de esta linea, en la versión de lista
            // de precios de venta recibida.
            MProductPrice pprice = MProductPrice.get(getCtx(), plVersionVenta.get_ID(), line.getM_Product_ID(), get_TrxName());

            // Si no tengo precio para este producto, lo creo.
            if ((pprice == null) || (pprice.getM_Product_ID() <= 0)){
                pprice = new MProductPrice(plVersionVenta, line.getM_Product_ID(), line.getNewPriceSO(), line.getNewPriceSO(), line.getNewPriceSO());
            }
            else{
                // Actualizo precios
                pprice.setPriceList(line.getNewPriceSO());
                pprice.setPriceStd(line.getNewPriceSO());
                pprice.setPriceLimit(line.getNewPriceSO());
            }
            pprice.saveEx();

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

    }

}
