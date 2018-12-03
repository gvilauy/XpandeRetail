package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.xpande.core.model.MZSocioListaPrecio;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
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
     * Dada una moneda y un producto, se actualiza precio del mismo en la lista de precios correspondiente a
     * este socio de negocio.
     * se crea en este momento.
     * Xpande. Created by Gabriel Vila on 7/2/17.
     * @param cCurrencyID
     * @param mProductID
     * @param priceList
     * @param validFrom
     * @param vigenciaPasada
     */
    public void updateProductPriceListPO (int cCurrencyID, int mProductID, BigDecimal priceList, Timestamp validFrom, boolean vigenciaPasada) {

        try{

            // Si no tengo lista, la creo en este momento
            if (this.plCompra == null){
                this.getPlCompra(cCurrencyID);
            }

            // Intento obtener precio de lista actual para el producto recibido en la lista de precios del socio
            MProductPrice pprice = MProductPrice.get(getCtx(), plVersionCompra.get_ID(), mProductID, get_TrxName());

            // Si no tengo precio para este producto, lo creo.
            if ((pprice == null) || (pprice.getM_Product_ID() <= 0)){
                pprice = new MProductPrice(plVersionCompra, mProductID, priceList, priceList, priceList);
            }
            else{
                // Ya existe precio para este producto en la lista

                // Si este documento tiene marcada fecha de vigencia pasada
                if (vigenciaPasada){
                    // Si el precio que esta en la lista tiene vigencia
                    Timestamp vigenciaPrecioProd = (Timestamp) pprice.get_Value("ValidFrom");
                    if (vigenciaPrecioProd != null){
                        // Si la vigencia actual del precio de este producto es mayor a la fecha de vigencia para este documento, no hago nada.
                        if (vigenciaPrecioProd.after(validFrom)){
                            return;
                        }
                    }
                }

                // Actualizo precios
                pprice.setPriceList(priceList);
                pprice.setPriceStd(priceList);
                pprice.setPriceLimit(priceList);
            }
            pprice.set_ValueOfColumn("ValidFrom", validFrom);
            pprice.saveEx();

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

    /***
     * Obtiene y retorna lista de precios de compra para este distribuidor en la moneda recibida.
     * Si no existe la crea en este momento.
     * @return
     */
    public MPriceList getPlCompra(int cCurrencyID) {

        if (plCompra == null){
            // Veo si hay una lista existente para socio de negocio y moneda de compra
            MZSocioListaPrecio bpl = MZSocioListaPrecio.getByPartnerCurrency(getCtx(), this.getC_BPartner_ID(), cCurrencyID, get_TrxName());

            // No existe lista para este socio de negocio y moneda de compra, la creo y seteo al socio de negocio.
            if ((bpl == null) || (bpl.get_ID() <= 0)){
                MBPartner bp = (MBPartner) this.getC_BPartner();
                MCurrency cur = new MCurrency(getCtx(), cCurrencyID, null);
                plCompra = new MPriceList(getCtx(), 0, get_TrxName());
                plCompra.setName("LISTA " + bp.getName().toUpperCase() + " " + cur.getISO_Code());
                plCompra.setC_Currency_ID(cCurrencyID);
                plCompra.setIsSOPriceList(false);
                plCompra.setIsTaxIncluded(true);
                plCompra.setIsNetPrice(false);
                plCompra.setPricePrecision(cur.getStdPrecision());
                plCompra.setAD_Org_ID(0);
                plCompra.saveEx();

                plVersionCompra = new MPriceListVersion(plCompra);
                plVersionCompra.setName("VIGENTE " + bp.getName().toUpperCase() + " " + cur.getISO_Code());
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

        }

        return plCompra;
    }

    public MPriceListVersion getPlVersionCompra() {

        return plVersionCompra;
    }

}
