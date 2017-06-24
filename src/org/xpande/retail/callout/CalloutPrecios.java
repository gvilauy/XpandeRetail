package org.xpande.retail.callout;

import org.compiere.model.*;
import org.compiere.util.Env;
import org.xpande.core.model.MZSocioListaPrecio;
import org.xpande.core.utils.PriceListUtils;
import org.xpande.retail.model.*;

import java.math.BigDecimal;
import java.util.Properties;

/**
 * Callouts relacionados a la gestión de precios en el módulo de Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/14/17.
 */
public class CalloutPrecios extends CalloutEngine {


    /***
     * Al cambiar moneda se carga lista de precios adecuada.
     * Xpande. Created by Gabriel Vila on 6/15/17.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String priceListByCurrency(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (value == null) return "";

        String column = mField.getColumnName();

        int cCurrencyID = ((Integer)value).intValue();
        int cBpartnerID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_ID");
        int adClientID = Env.getContextAsInt(ctx, WindowNo, "AD_Client_ID");
        int adOrgID = Env.getContextAsInt(ctx, WindowNo, "AD_Org_ID");


        String sql = "";

        // Si es moneda de compra la recibida, busco lista de compra del proveedor en esa moneda
        if (column.equalsIgnoreCase("C_Currency_ID")){

            MZSocioListaPrecio bplist = MZSocioListaPrecio.getByPartnerCurrency(ctx, cBpartnerID, cCurrencyID, null);

            if ((bplist != null) && (bplist.get_ID() > 0)){
                mTab.setValue("M_PriceList_ID", bplist.getM_PriceList_ID());

                // Obtengo versión de lista vigente
                MPriceListVersion plv = ((MPriceList)bplist.getM_PriceList()).getPriceListVersion(null);
                if ((plv != null) && (plv.get_ID() > 0)){
                    mTab.setValue("M_PriceList_Version_ID", plv.get_ID());
                }
                else{
                    mTab.setValue("M_PriceList_Version_ID", null);
                }
            }
            else{
                mTab.setValue("M_PriceList_ID", null);
                mTab.setValue("M_PriceList_Version_ID", null);
            }
        }

        // Si es moneda de venta la recibida, busco lista de venta según la organización seleccionada en este documento
        else if (column.equalsIgnoreCase("C_Currency_ID_SO")){

            // Obtengo lista de venta para organización seleccionada en este documento y moneda
            MPriceList priceList = PriceListUtils.getPriceListByOrg(ctx, adClientID, adOrgID, cCurrencyID, true, null);

            if ((priceList != null) && (priceList.get_ID() > 0)){

                mTab.setValue("M_PriceList_ID_SO", priceList.getM_PriceList_ID());

                // Obtengo versión de lista vigente
                MPriceListVersion plv = priceList.getPriceListVersion(null);
                if ((plv != null) && (plv.get_ID() > 0)){
                    mTab.setValue("M_PriceList_Version_ID_SO", plv.get_ID());
                }
                else{
                    mTab.setValue("M_PriceList_Version_ID_SO", null);
                }
            }
            else{
                mTab.setValue("M_PriceList_ID_SO", null);

            }
        }

        return "";
    }


    /***
     * Al cambiar producto en linea de documento de gestión de precios de proveedor, se setean atributos correspondientes.
     * Xpande. Created by Gabriel Vila on 6/19/17.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String productInfoByProduct(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if ((value == null) || (((Integer) value).intValue() <= 0)){
            return "";
        }

        int mProductID = ((Integer) value).intValue();
        MProduct prod = new MProduct(ctx, mProductID, null);
        if ((prod == null) || (prod.get_ID() <= 0)){
            return "";
        }

        mTab.setValue(X_Z_PreciosProvLin.COLUMNNAME_InternalCode, prod.getValue());
        mTab.setValue(X_Z_PreciosProvLin.COLUMNNAME_Name, prod.getName());
        mTab.setValue(X_Z_PreciosProvLin.COLUMNNAME_Description, prod.getDescription());
        mTab.setValue(X_Z_PreciosProvLin.COLUMNNAME_Z_ProductoSeccion_ID, prod.get_ValueAsInt(X_Z_ProductoSeccion.COLUMNNAME_Z_ProductoSeccion_ID));
        mTab.setValue(X_Z_PreciosProvLin.COLUMNNAME_Z_ProductoRubro_ID, prod.get_ValueAsInt(X_Z_ProductoRubro.COLUMNNAME_Z_ProductoRubro_ID));
        mTab.setValue(X_Z_PreciosProvLin.COLUMNNAME_C_UOM_ID, prod.getC_UOM_ID());
        mTab.setValue(X_Z_PreciosProvLin.COLUMNNAME_C_TaxCategory_ID, prod.getC_TaxCategory_ID());

        if (prod.get_ValueAsInt(X_Z_ProductoFamilia.COLUMNNAME_Z_ProductoFamilia_ID) > 0){
            mTab.setValue(X_Z_PreciosProvLin.COLUMNNAME_Z_ProductoFamilia_ID, prod.get_ValueAsInt(X_Z_ProductoFamilia.COLUMNNAME_Z_ProductoFamilia_ID));
        }

        if (prod.get_ValueAsInt(X_Z_ProductoSubfamilia.COLUMNNAME_Z_ProductoSubfamilia_ID) > 0){
            mTab.setValue(X_Z_PreciosProvLin.COLUMNNAME_Z_ProductoSubfamilia_ID, prod.get_ValueAsInt(X_Z_ProductoSubfamilia.COLUMNNAME_Z_ProductoSubfamilia_ID));
        }

        if (prod.get_ValueAsInt(X_Z_PreciosProvLin.COLUMNNAME_C_TaxCategory_ID_2) > 0){
            mTab.setValue(X_Z_PreciosProvLin.COLUMNNAME_C_TaxCategory_ID_2, prod.get_ValueAsInt(X_Z_PreciosProvLin.COLUMNNAME_C_TaxCategory_ID_2));
        }

        return "";
    }


    /***
     * Al cambiar nuevo precio de venta, o margen final, o margen factura, se recalculan los margenes y nuevo precio de venta.
     * Xpande. Created by Gabriel Vila on 6/23/17.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String calculateMargins(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (value == null) return "";

        String column = mField.getColumnName();

        BigDecimal priceFinal = (BigDecimal) mTab.getValue("PriceFinal");
        BigDecimal pricePO = (BigDecimal) mTab.getValue("PricePO");
        BigDecimal priceInvoiced = (BigDecimal) mTab.getValue("PriceInvoiced");

        int mPriceListCompraID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_ID");
        int mPriceListVentaID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_ID_SO");

        int precisionCompra = 2;
        int precisionVenta = 2;

        if (mPriceListCompraID > 0){
            MPriceList plCompra = new MPriceList(ctx, mPriceListCompraID, null);
            precisionCompra = plCompra.getPricePrecision();
        }

        if (mPriceListVentaID > 0){
            MPriceList plVenta = new MPriceList(ctx, mPriceListVentaID, null);
            precisionVenta = plVenta.getPricePrecision();
        }

        // Si es moneda de compra la recibida, busco lista de compra del proveedor en esa moneda
        if (column.equalsIgnoreCase("NewPriceSO")){

            BigDecimal newPriceSO = (BigDecimal)value;
            if ((newPriceSO == null) || (newPriceSO.compareTo(Env.ZERO) <= 0)){
                mTab.setValue("PriceFinalMargin", null);
                mTab.setValue("PricePOMargin", null);
                mTab.setValue("PriceInvoicedMargin", null);
            }
            else{
                // Margen final
                if ((priceFinal == null) || (priceFinal.compareTo(Env.ZERO) <= 0)){
                    mTab.setValue("PriceFinalMargin", null);
                }
                else{
                    mTab.setValue("PriceFinalMargin", (((newPriceSO.multiply(Env.ONEHUNDRED).setScale(precisionCompra, BigDecimal.ROUND_HALF_UP))
                            .divide(priceFinal, precisionCompra, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED)));
                }

                // Margen OC
                if ((pricePO == null) || (pricePO.compareTo(Env.ZERO) <= 0)){
                    mTab.setValue("PricePOMargin", null);
                }
                else{
                    mTab.setValue("PricePOMargin", (((newPriceSO.multiply(Env.ONEHUNDRED).setScale(precisionCompra, BigDecimal.ROUND_HALF_UP))
                            .divide(pricePO, precisionCompra, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED)));
                }

                // Margen Factura
                if ((priceInvoiced == null) || (priceInvoiced.compareTo(Env.ZERO) <= 0)){
                    mTab.setValue("PriceInvoicedMargin", null);
                }
                else{
                    mTab.setValue("PriceInvoicedMargin", (((newPriceSO.multiply(Env.ONEHUNDRED).setScale(precisionCompra, BigDecimal.ROUND_HALF_UP))
                            .divide(priceInvoiced, precisionCompra, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED)));
                }
            }
        }
        else if (column.equalsIgnoreCase("PriceFinalMargin")){

            BigDecimal newFinalMargin = (BigDecimal)value;
            BigDecimal newPriceSO = ((newFinalMargin.add(Env.ONEHUNDRED)).multiply(priceFinal).setScale(precisionVenta, BigDecimal.ROUND_HALF_UP)
                    .divide(Env.ONEHUNDRED, precisionVenta, BigDecimal.ROUND_HALF_UP));

            // Nuevo Precio
            mTab.setValue("NewPriceSO", newPriceSO);

            // Margen OC
            if ((pricePO == null) || (pricePO.compareTo(Env.ZERO) <= 0)){
                mTab.setValue("PricePOMargin", null);
            }
            else{
                mTab.setValue("PricePOMargin", (((newPriceSO.multiply(Env.ONEHUNDRED).setScale(precisionCompra, BigDecimal.ROUND_HALF_UP))
                        .divide(pricePO, precisionCompra, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED)));
            }

            // Margen Factura
            if ((priceInvoiced == null) || (priceInvoiced.compareTo(Env.ZERO) <= 0)){
                mTab.setValue("PriceInvoicedMargin", null);
            }
            else{
                mTab.setValue("PriceInvoicedMargin", (((newPriceSO.multiply(Env.ONEHUNDRED).setScale(precisionCompra, BigDecimal.ROUND_HALF_UP))
                        .divide(priceInvoiced, precisionCompra, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED)));
            }

        }
        else if (column.equalsIgnoreCase("PriceInvoicedMargin")){

            BigDecimal newInvoiceMargin = (BigDecimal)value;
            BigDecimal newPriceSO = ((newInvoiceMargin.add(Env.ONEHUNDRED)).multiply(priceInvoiced).setScale(precisionVenta, BigDecimal.ROUND_HALF_UP)
                    .divide(Env.ONEHUNDRED, precisionVenta, BigDecimal.ROUND_HALF_UP));

            // Nuevo Precio
            mTab.setValue("NewPriceSO", newPriceSO);

            // Margen OC
            if ((pricePO == null) || (pricePO.compareTo(Env.ZERO) <= 0)){
                mTab.setValue("PricePOMargin", null);
            }
            else{
                mTab.setValue("PricePOMargin", (((newPriceSO.multiply(Env.ONEHUNDRED).setScale(precisionCompra, BigDecimal.ROUND_HALF_UP))
                        .divide(pricePO, precisionCompra, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED)));
            }

            // Margen final
            if ((priceFinal == null) || (priceFinal.compareTo(Env.ZERO) <= 0)){
                mTab.setValue("PriceFinalMargin", null);
            }
            else{
                mTab.setValue("PriceFinalMargin", (((newPriceSO.multiply(Env.ONEHUNDRED).setScale(precisionCompra, BigDecimal.ROUND_HALF_UP))
                        .divide(priceFinal, precisionCompra, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED)));
            }

        }
        else{
            return "";
        }

        return "";
    }


}
