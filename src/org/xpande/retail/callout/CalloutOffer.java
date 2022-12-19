package org.xpande.retail.callout;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.xpande.core.model.MZProductoUPC;
import org.xpande.core.model.MZSocioListaPrecio;
import org.xpande.core.utils.PriceListUtils;
import org.xpande.retail.model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 12/19/22.
 */
public class CalloutOffer extends CalloutEngine {

    /**
     * Setea información del producto seleccionado.
     * Tanane. Created by Gabriel Vila on 2022-11-28
     * @param ctx
     * @param windowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String setProductInfo(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value) {

        try {
            if (value == null) {
                return null;
            }
            Timestamp today = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
            I_Z_RegularOfferLine offerLine = GridTabWrapper.create(mTab, I_Z_RegularOfferLine.class);
            MZRegularOffer regularOffer = (MZRegularOffer) offerLine.getZ_RegularOffer();
            MZProductoSocio vendorLineProd = null;
            MProduct product = null;
            String upc = null, vendorProdNo = null;
            String column = mField.getColumnName();
            boolean validateBPProduct = false;

            // Inicializo precios
            offerLine.setPriceSO(null);
            offerLine.setPricePO(null);
            offerLine.setNewPricePO(null);
            offerLine.setDiscount(null);
            offerLine.setNewPriceSO(null);

            if (column.equalsIgnoreCase(X_Z_RegularOfferLine.COLUMNNAME_UPC)){
                MZProductoUPC pupc = MZProductoUPC.getByUPC(ctx, value.toString().trim(), null);
                if ((pupc != null) && (pupc.get_ID() > 0)){
                    product = (MProduct) pupc.getM_Product();
                    upc = pupc.getUPC();
                    if (regularOffer.getC_BPartner_ID() > 0) validateBPProduct = true;
                }
            }
            else if (column.equalsIgnoreCase(X_Z_RegularOfferLine.COLUMNNAME_M_Product_ID)){
                int mProductID = ((Integer) value).intValue();
                product = new MProduct(ctx, mProductID, null);
                upc = product.getUPC();
            }
            else if (column.equalsIgnoreCase(X_Z_RegularOfferLine.COLUMNNAME_ProdCode)){
                String codInterno = (String) value;
                if (!codInterno.trim().equalsIgnoreCase("")){
                    MProduct[] prods = MProduct.get(ctx, " Value ='" + codInterno + "'", null);
                    if (prods.length > 0){
                        product = prods[0];
                        upc = product.getUPC();
                        if (regularOffer.getC_BPartner_ID() > 0) validateBPProduct = true;
                    }
                }
            }
            else if (column.equalsIgnoreCase(X_Z_RegularOfferLine.COLUMNNAME_VendorProductNo)) {
                vendorLineProd = MZProductoSocio.getByBPartnerVendorProdNo(ctx, regularOffer.getC_BPartner_ID(), value.toString().trim(), null);
                if ((vendorLineProd != null) && (vendorLineProd.get_ID() > 0)){
                    product = (MProduct) vendorLineProd.getM_Product();
                    upc = product.getUPC();
                    vendorProdNo = vendorLineProd.getVendorProductNo();
                }
            }

            // Seteo atributos asociados al producto
            if ((product != null) && (product.get_ID() > 0)){
                // Si tengo flag de validar producto de socio de negocio, lo hago ahora
                if (validateBPProduct) {
                    // Obtengo ficha de producto-socio para el proveedor seleccionado
                    vendorLineProd = MZProductoSocio.getByBPartnerProduct(ctx, regularOffer.getC_BPartner_ID(), product.get_ID(), null);
                    if ((vendorLineProd == null) || (vendorLineProd.get_ID() <= 0)) {
                        mTab.fireDataStatusEEvent("Error", "El producto ingresado no pertenece al Socio de Negocio de la Oferta", true);
                    }
                }

                // Atributos del producto
                offerLine.setProdCode(product.getValue());
                offerLine.setProdName(product.getName());
                offerLine.setM_Product_ID(product.get_ID());
                if (upc != null) offerLine.setUPC(upc);
                if (vendorProdNo != null) {
                    offerLine.setVendorProductNo(vendorProdNo);
                }
                else {
                    if (regularOffer.getC_BPartner_ID() > 0) {
                        vendorLineProd = MZProductoSocio.getByBPartnerProduct(ctx, regularOffer.getC_BPartner_ID(), product.get_ID(), null);
                        if ((vendorLineProd != null) && (vendorLineProd.get_ID() > 0)){
                            offerLine.setVendorProductNo(vendorLineProd.getVendorProductNo());
                        }
                    }
                }
                // Precio OC Actual
                // Obtengo datos actuales de precio de compra
                if (vendorLineProd == null) {
                    // Si es oferta por proveedor
                    if (regularOffer.getRegularOfferType().equalsIgnoreCase(X_Z_RegularOffer.REGULAROFFERTYPE_OfertaPorProveedor)) {
                        // Obtengo ficha de producto-socio para el proveedor seleccionado
                        vendorLineProd = MZProductoSocio.getByBPartnerProduct(ctx, regularOffer.getC_BPartner_ID(), product.get_ID(), null);
                    }
                    // Oferta General
                    else if (regularOffer.getRegularOfferType().equalsIgnoreCase(X_Z_RegularOffer.REGULAROFFERTYPE_OfertaGeneral)) {
                        // Obtengo ficha de producto-socio para el proveedor con la última factura de compra ingresada
                        vendorLineProd = MZProductoSocio.getByLastInvoice(ctx, product.get_ID(), null);
                        // Si no obtuve por ultima factura, busco la ficha según última fecha de vigencia de precio de compra
                        if ((vendorLineProd == null) || (vendorLineProd.get_ID() <= 0)) {
                            vendorLineProd = MZProductoSocio.getByLastPriceOC(ctx, product.get_ID(), null);
                        }
                    }
                }
                // Si obtuve ficha cargo datos de precios de compra
                if ((vendorLineProd != null) && (vendorLineProd.get_ID() > 0)) {
                    offerLine.setC_Currency_ID(vendorLineProd.getC_Currency_ID());
                    offerLine.setPricePO(vendorLineProd.getPricePO());
                }
                // Precio de Venta Actual
                MPriceListVersion plvSO = PriceListUtils.getPriceListVersion(ctx, regularOffer.getAD_Client_ID(), regularOffer.getAD_Org_ID(), product.get_ID(), today, true, null);
                if ((plvSO != null) && (plvSO.get_ID() > 0)) {
                    offerLine.setM_PriceList_Version_ID(plvSO.get_ID());
                    MPriceList priceList = (MPriceList) plvSO.getM_PriceList();
                    offerLine.setC_Currency_ID_To(priceList.getC_Currency_ID());
                    MProductPrice productPrice = MProductPrice.get(ctx, plvSO.get_ID(), product.get_ID(), null);
                    if (productPrice != null){
                        offerLine.setPriceSO(productPrice.getPriceList());
                    }
                }
            }
        }
        catch (Exception e) {
            throw new AdempiereException(e);
        }
        return null;
    }

    /**
     * Setea información de nuevo precio de oferta compra
     * Tanane. Created by Gabriel Vila on 2022-12-12
     * @param ctx
     * @param windowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String setNewPricePO(Properties ctx, int windowNo, GridTab mTab, GridField mField, Object value) {

        try {
            if (value == null) {
                return null;
            }
            I_Z_RegularOfferLine offerLine = GridTabWrapper.create(mTab, I_Z_RegularOfferLine.class);
            MAcctSchema schema = MClient.get(ctx, offerLine.getAD_Client_ID()).getAcctSchema();
            String column = mField.getColumnName();
            if (column.equalsIgnoreCase(X_Z_RegularOfferLine.COLUMNNAME_NewPricePO)){
                // Calculo descuento en base al nuevo precio de oferta compra
                BigDecimal discount = Env.ZERO;
                BigDecimal pricePO = offerLine.getPricePO();
                if (pricePO == null) pricePO = Env.ZERO;
                if (pricePO.compareTo(Env.ZERO) > 0) {
                    BigDecimal newPricePO = (BigDecimal) value;
                    if (offerLine.getPurchaseCurrency_ID() > 0) {
                        if (offerLine.getC_Currency_ID() != offerLine.getPurchaseCurrency_ID()) {
                            if (offerLine.getPurchaseCurrency_ID() == schema.getC_Currency_ID()) {
                                newPricePO = newPricePO.divide(offerLine.getCurrencyRate(), 2, RoundingMode.HALF_UP);
                            }
                            else {
                                newPricePO = newPricePO.multiply(offerLine.getCurrencyRate()).setScale(2, RoundingMode.HALF_UP);
                            }
                        }
                    }
                    discount = (newPricePO.multiply(Env.ONEHUNDRED)).divide(pricePO, 2, RoundingMode.HALF_UP);
                }
                offerLine.setDiscount(Env.ONEHUNDRED.subtract(discount));
            }
            else if (column.equalsIgnoreCase(X_Z_RegularOfferLine.COLUMNNAME_Discount)){
                // Calculo nuevo precio de oferta compra según el descuento digitado
                BigDecimal newPricePO = Env.ZERO;
                BigDecimal pricePO = offerLine.getPricePO();
                if (pricePO == null) pricePO = Env.ZERO;
                if (pricePO.compareTo(Env.ZERO) > 0) {
                    if (offerLine.getPurchaseCurrency_ID() > 0) {
                        if (offerLine.getC_Currency_ID() != offerLine.getPurchaseCurrency_ID()) {
                            if (offerLine.getPurchaseCurrency_ID() == schema.getC_Currency_ID()) {
                                pricePO = pricePO.multiply(offerLine.getCurrencyRate()).setScale(2, RoundingMode.HALF_UP);
                            }
                            else {
                                pricePO = pricePO.divide(offerLine.getCurrencyRate(), 2, RoundingMode.HALF_UP);
                            }
                        }
                    }
                    BigDecimal discount = (BigDecimal) value;
                    newPricePO = (discount.multiply(pricePO)).divide(Env.ONEHUNDRED, 2, RoundingMode.HALF_UP);
                }
                offerLine.setNewPricePO(pricePO.subtract(newPricePO));
            }
        }
        catch (Exception e) {
            throw new AdempiereException(e);
        }
        return null;
    }

}
