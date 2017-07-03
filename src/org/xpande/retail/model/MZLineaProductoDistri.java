package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.xpande.core.model.MZSocioListaPrecio;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de socio de negocio distribuidor de una linea de productos de otro socio.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/21/17.
 */
public class MZLineaProductoDistri extends X_Z_LineaProductoDistri {

    private MPriceList plCompra = null;
    private MPriceListVersion plVersionCompra = null;

    public MZLineaProductoDistri(Properties ctx, int Z_LineaProductoDistri_ID, String trxName) {
        super(ctx, Z_LineaProductoDistri_ID, trxName);
    }

    public MZLineaProductoDistri(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Estoy parado en el modelo de un distribuidor de linea de productos de otro socio padre.
     * En este metodo se crea una linea de producto nueva para este distribuidor y se asocian
     * ambas lineas: la linea del padre con la linea que se crea para este distribuidor.
     * Xpande. Created by Gabriel Vila on 7/2/17.
     */
    public void setLineaProductoDistribuidor(){

        try{
            // Si ya tengo una linea de producto relacionada de este distribuidor, no hago nada.
            if (this.getZ_LineaProductoSocioRelated_ID() > 0) return;

            MZLineaProductoSocio lineaProductoSocioPadre = (MZLineaProductoSocio) this.getZ_LineaProductoSocio();

            // Creo nueva linea de productos para este distribuidor asociada a la linea de producto que esta distribuyendo
            MZLineaProductoSocio lineaProductoSocio = new MZLineaProductoSocio(getCtx(), 0, get_TrxName());
            lineaProductoSocio.setC_BPartner_ID(this.getC_BPartner_ID());
            lineaProductoSocio.setName(lineaProductoSocioPadre.getName());
            lineaProductoSocio.setIsOwn(false);
            lineaProductoSocio.setC_BPartnerRelation_ID(lineaProductoSocio.getC_BPartner_ID());
            lineaProductoSocio.setZ_LineaProductoSocioRelated_ID(lineaProductoSocioPadre.get_ID());
            lineaProductoSocio.setIsLockedPO(false);
            lineaProductoSocio.saveEx();

            this.setZ_LineaProductoSocioRelated_ID(lineaProductoSocio.get_ID());

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }


    /***
     * Dada una moneda y un producto, se actualiza precio del mismo en la lista de precios correspondiente a
     * este socio de negocio. En caso de no existir lista de precio de compra para este socio y moneda recibida,
     * se crea en este momento.
     * Xpande. Created by Gabriel Vila on 7/2/17.
     * @param cCurrencyID
     * @param mProductID
     * @param priceList
     */
    public void updateProductPriceListPO (int cCurrencyID, int mProductID, BigDecimal priceList) {

        try{

            this.plCompra = null;
            this.plVersionCompra = null;

            // Veo si hay una lista existente para socio de negocio y moneda de compra
            MZSocioListaPrecio bpl = MZSocioListaPrecio.getByPartnerCurrency(getCtx(), this.getC_BPartner_ID(), cCurrencyID, get_TrxName());

            // No existe lista para este socio de negocio y moneda de compra, la creo y seteo al socio de negocio.
            if ((bpl == null) || (bpl.get_ID() <= 0)){
                MBPartner bp = (MBPartner) this.getC_BPartner();
                MCurrency cur = new MCurrency(getCtx(), cCurrencyID, null);
                plCompra = new MPriceList(getCtx(), 0, get_TrxName());
                plCompra.setName("LISTA " + bp.getName2().toUpperCase() + " " + cur.getISO_Code());
                plCompra.setC_Currency_ID(cCurrencyID);
                plCompra.setIsSOPriceList(false);
                plCompra.setIsTaxIncluded(true);
                plCompra.setIsNetPrice(false);
                plCompra.setPricePrecision(cur.getStdPrecision());
                plCompra.setAD_Org_ID(0);
                plCompra.saveEx();

                plVersionCompra = new MPriceListVersion(plCompra);
                plVersionCompra.setName("VIGENTE");
                plVersionCompra.setM_DiscountSchema_ID(1000000);
                plVersionCompra.saveEx();

                bpl =  new MZSocioListaPrecio(getCtx(), 0, get_TrxName());
                bpl.setC_BPartner_ID(this.getC_BPartner_ID());
                bpl.setC_Currency_ID(cCurrencyID);
                bpl.setM_PriceList_ID(plCompra.get_ID());
                bpl.saveEx();
            }
            else{
                plCompra = (MPriceList) bpl.getM_PriceList();
                plVersionCompra = plCompra.getPriceListVersion(null);
            }

            // Intento obtener precio de lista actual para el producto recibido en la lista de precios del socio
            MProductPrice pprice = MProductPrice.get(getCtx(), plVersionCompra.get_ID(), mProductID, get_TrxName());

            // Si no tengo precio para este producto, lo creo.
            if ((pprice == null) || (pprice.getM_Product_ID() <= 0)){
                pprice = new MProductPrice(plVersionCompra, mProductID, priceList, priceList, priceList);
            }
            else{
                // Actualizo precios
                pprice.setPriceList(priceList);
                pprice.setPriceStd(priceList);
                pprice.setPriceLimit(priceList);
            }
            pprice.saveEx();

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

    public MPriceList getPlCompra() {
        return plCompra;
    }

    public MPriceListVersion getPlVersionCompra() {
        return plVersionCompra;
    }

}
