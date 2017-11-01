package org.xpande.retail.callout;

import org.compiere.model.*;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xpande.core.model.MZProductoUPC;
import org.xpande.core.model.MZSocioListaPrecio;
import org.xpande.core.utils.PriceListUtils;
import org.xpande.retail.model.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Callouts relacionados a la gestión de precios en el módulo de Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/14/17.
 */
public class CalloutPrecios extends CalloutEngine {


    /***
     * Al cambiar moneda de compra o de venta se carga lista de precios adecuada.
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
     * Al cambiar moneda se carga lista de precios de venta para moneda y organizacion
     * Xpande. Created by Gabriel Vila on 6/15/17.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String priceListSO_ByOrgCurrency(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (value == null) return "";

        String column = mField.getColumnName();

        int cCurrencyID = ((Integer)value).intValue();
        int adClientID = Env.getContextAsInt(ctx, WindowNo, "AD_Client_ID");
        int adOrgID = Env.getContextAsInt(ctx, WindowNo, "AD_Org_ID");

        // Obtengo lista de precios para organización y moneda de este documento
        MPriceList priceList = PriceListUtils.getPriceListByOrg(ctx, adClientID, adOrgID, cCurrencyID, true, null);

        if ((priceList != null) && (priceList.get_ID() > 0)){

            mTab.setValue("M_PriceList_ID", priceList.getM_PriceList_ID());

            // Obtengo versión de lista vigente
            MPriceListVersion plv = priceList.getPriceListVersion(null);
            if ((plv != null) && (plv.get_ID() > 0)){
                mTab.setValue("M_PriceList_Version_ID", plv.get_ID());
            }
            else{
                mTab.setValue("M_PriceList_Version_ID", null);
            }
        }
        else{
            mTab.setValue("M_PriceList_ID", null);

        }
        return "";
    }


    /***
     * Setea version de lista para lista de precios recibida.
     * Xpande. Created by Gabriel Vila on 7/31/17.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String priceVersionByList(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (value == null) return "";

        int mPriceListID = ((Integer)value).intValue();

        // Obtengo versión de lista de precios para lista recibida
        MPriceList priceList = MPriceList.get(ctx, mPriceListID, null);
        if ((priceList != null) && (priceList.get_ID() > 0)){

            // Obtengo versión de lista vigente
            MPriceListVersion plv = priceList.getPriceListVersion(null);
            if ((plv != null) && (plv.get_ID() > 0)){
                mTab.setValue("M_PriceList_Version_ID", plv.get_ID());
            }
            else{
                mTab.setValue("M_PriceList_Version_ID", null);
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
        int zPreciosProvCabID = Env.getContextAsInt(ctx, WindowNo, "Z_PreciosProvCab_ID");

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

        MZPreciosProvCab preciosProvCab = new MZPreciosProvCab(ctx, zPreciosProvCabID, null);

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
                    if ((preciosProvCab.getRate() != null) && (preciosProvCab.getRate().compareTo(Env.ZERO) > 0)){
                        priceFinal = priceFinal.multiply(preciosProvCab.getRate()).setScale(4, BigDecimal.ROUND_HALF_UP);
                    }
                    mTab.setValue("PriceFinalMargin", (((newPriceSO.multiply(Env.ONEHUNDRED).setScale(4, BigDecimal.ROUND_HALF_UP))
                            .divide(priceFinal, 4, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED)));
                }

                // Margen OC
                if ((pricePO == null) || (pricePO.compareTo(Env.ZERO) <= 0)){
                    mTab.setValue("PricePOMargin", null);
                }
                else{
                    if ((preciosProvCab.getRate() != null) && (preciosProvCab.getRate().compareTo(Env.ZERO) > 0)){
                        pricePO = pricePO.multiply(preciosProvCab.getRate()).setScale(4, BigDecimal.ROUND_HALF_UP);
                    }
                    mTab.setValue("PricePOMargin", (((newPriceSO.multiply(Env.ONEHUNDRED).setScale(4, BigDecimal.ROUND_HALF_UP))
                            .divide(pricePO, 4, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED)));
                }

                // Margen Factura
                if ((priceInvoiced == null) || (priceInvoiced.compareTo(Env.ZERO) <= 0)){
                    mTab.setValue("PriceInvoicedMargin", null);
                }
                else{
                    if ((preciosProvCab.getRate() != null) && (preciosProvCab.getRate().compareTo(Env.ZERO) > 0)){
                        priceInvoiced = priceInvoiced.multiply(preciosProvCab.getRate()).setScale(4, BigDecimal.ROUND_HALF_UP);
                    }
                    mTab.setValue("PriceInvoicedMargin", (((newPriceSO.multiply(Env.ONEHUNDRED).setScale(4, BigDecimal.ROUND_HALF_UP))
                            .divide(priceInvoiced, 4, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED)));
                }
            }
        }
        else if (column.equalsIgnoreCase("PriceFinalMargin")){

            BigDecimal newFinalMargin = (BigDecimal)value;
            BigDecimal newPriceSO = null;

            // Nuevo Precio segun sea multimoneda o no
            if ((preciosProvCab.getRate() != null) && (preciosProvCab.getRate().compareTo(Env.ZERO) > 0)){
                newPriceSO = ((newFinalMargin.add(Env.ONEHUNDRED)).multiply(priceFinal).setScale(4, BigDecimal.ROUND_HALF_UP)
                        .divide(Env.ONEHUNDRED, 4, BigDecimal.ROUND_HALF_UP));
                newPriceSO = newPriceSO.multiply(preciosProvCab.getRate()).setScale(precisionVenta, BigDecimal.ROUND_HALF_UP);
            }
            else{
                newPriceSO = ((newFinalMargin.add(Env.ONEHUNDRED)).multiply(priceFinal).setScale(precisionVenta, BigDecimal.ROUND_HALF_UP)
                        .divide(Env.ONEHUNDRED, precisionVenta, BigDecimal.ROUND_HALF_UP));

            }
            mTab.setValue("NewPriceSO", newPriceSO);

            // Margen OC
            if ((pricePO == null) || (pricePO.compareTo(Env.ZERO) <= 0)){
                mTab.setValue("PricePOMargin", null);
            }
            else{
                if ((preciosProvCab.getRate() != null) && (preciosProvCab.getRate().compareTo(Env.ZERO) > 0)){
                    pricePO = pricePO.multiply(preciosProvCab.getRate()).setScale(4, BigDecimal.ROUND_HALF_UP);
                }
                mTab.setValue("PricePOMargin", (((newPriceSO.multiply(Env.ONEHUNDRED).setScale(4, BigDecimal.ROUND_HALF_UP))
                        .divide(pricePO, 4, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED)));
            }

            // Margen Factura
            if ((priceInvoiced == null) || (priceInvoiced.compareTo(Env.ZERO) <= 0)){
                mTab.setValue("PriceInvoicedMargin", null);
            }
            else{
                if ((preciosProvCab.getRate() != null) && (preciosProvCab.getRate().compareTo(Env.ZERO) > 0)){
                    priceInvoiced = priceInvoiced.multiply(preciosProvCab.getRate()).setScale(4, BigDecimal.ROUND_HALF_UP);
                }
                mTab.setValue("PriceInvoicedMargin", (((newPriceSO.multiply(Env.ONEHUNDRED).setScale(4, BigDecimal.ROUND_HALF_UP))
                        .divide(priceInvoiced, 4, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED)));
            }

        }
        else if (column.equalsIgnoreCase("PriceInvoicedMargin")){

            BigDecimal newInvoiceMargin = (BigDecimal)value;
            BigDecimal newPriceSO = null;

            if (priceInvoiced != null){
                // Nuevo Precio segun sea multimoneda o no
                if ((preciosProvCab.getRate() != null) && (preciosProvCab.getRate().compareTo(Env.ZERO) > 0)){
                    newPriceSO = ((newInvoiceMargin.add(Env.ONEHUNDRED)).multiply(priceInvoiced).setScale(4, BigDecimal.ROUND_HALF_UP)
                            .divide(Env.ONEHUNDRED, 4, BigDecimal.ROUND_HALF_UP));
                    newPriceSO = newPriceSO.multiply(preciosProvCab.getRate()).setScale(precisionVenta, BigDecimal.ROUND_HALF_UP);
                }
                else{
                    newPriceSO = ((newInvoiceMargin.add(Env.ONEHUNDRED)).multiply(priceInvoiced).setScale(precisionVenta, BigDecimal.ROUND_HALF_UP)
                            .divide(Env.ONEHUNDRED, precisionVenta, BigDecimal.ROUND_HALF_UP));
                }
                mTab.setValue("NewPriceSO", newPriceSO);


                // Nuevo Precio
                if ((preciosProvCab.getRate() != null) && (preciosProvCab.getRate().compareTo(Env.ZERO) > 0)){
                    newPriceSO = newPriceSO.multiply(preciosProvCab.getRate()).setScale(precisionVenta, BigDecimal.ROUND_HALF_UP);
                }
                mTab.setValue("NewPriceSO", newPriceSO);

                // Margen OC
                if ((pricePO == null) || (pricePO.compareTo(Env.ZERO) <= 0)){
                    mTab.setValue("PricePOMargin", null);
                }
                else{
                    if ((preciosProvCab.getRate() != null) && (preciosProvCab.getRate().compareTo(Env.ZERO) > 0)){
                        pricePO = pricePO.multiply(preciosProvCab.getRate()).setScale(4, BigDecimal.ROUND_HALF_UP);
                    }
                    mTab.setValue("PricePOMargin", (((newPriceSO.multiply(Env.ONEHUNDRED).setScale(4, BigDecimal.ROUND_HALF_UP))
                            .divide(pricePO, 4, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED)));
                }

                // Margen final
                if ((priceFinal == null) || (priceFinal.compareTo(Env.ZERO) <= 0)){
                    mTab.setValue("PriceFinalMargin", null);
                }
                else{
                    if ((preciosProvCab.getRate() != null) && (preciosProvCab.getRate().compareTo(Env.ZERO) > 0)){
                        priceFinal = priceFinal.multiply(preciosProvCab.getRate()).setScale(4, BigDecimal.ROUND_HALF_UP);
                    }
                    mTab.setValue("PriceFinalMargin", (((newPriceSO.multiply(Env.ONEHUNDRED).setScale(4, BigDecimal.ROUND_HALF_UP))
                            .divide(priceFinal, 4, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED)));
                }

            }

        }
        else{
            return "";
        }

        return "";
    }

    /***
     * Setea pauta comercial para linea de producto seleccionada.
     * Xpande. Created by Gabriel Vila on 7/15/17.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String pautaByLineaProducto(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if ((value == null) || (((Integer) value).intValue() <= 0)){
            return "";
        }

        int zlineaProductoSocioID = ((Integer) value).intValue();

        String sql = " select z_pautacomercial_id from z_pautacomercial where z_lineaproductosocio_id =" + zlineaProductoSocioID;
        int zPautaComercialID = DB.getSQLValueEx(null, sql);

        if (zPautaComercialID > 0){
            mTab.setValue("Z_PautaComercial_ID", zPautaComercialID);
        }

        return "";
    }


    /***
     * Al ingresar código de barras, o el producto directamente, se deben setear demás campos asociados.
     * Xpande. Created by Gabriel Vila on 6/25/17.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String upcProduct(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (isCalloutActive()) return "";

        if ((value == null) || (value.toString().trim().equalsIgnoreCase(""))){
            mTab.setValue("UPC", null);
            mTab.setValue("M_Product_ID", null);
            return "";
        }

        MProduct prod = null;

        String column = mField.getColumnName();

        if (column.equalsIgnoreCase("UPC")){
            MZProductoUPC pupc = MZProductoUPC.getByUPC(ctx, value.toString().trim(), null);
            if ((pupc != null) && (pupc.get_ID() > 0)){
                prod = (MProduct) pupc.getM_Product();
                mTab.setValue("M_Product_ID", prod.get_ID());
            }
            else{
                mTab.setValue("M_Product_ID", null);
            }
        }
        else if (column.equalsIgnoreCase("M_Product_ID")){

            int mProductID = ((Integer) value).intValue();
            prod = new MProduct(ctx, mProductID, null);

            MZProductoUPC pupc = MZProductoUPC.getByProduct(ctx, mProductID, null);
            if ((pupc != null) && (pupc.get_ID() > 0)){
                mTab.setValue("UPC", pupc.getUPC());
            }
            else{
                mTab.setValue("UPC", null);
            }
        }

        // Seteo atrbutos asociados al producto
        if ((prod != null) && (prod.get_ID() > 0)){

            // Atributos del producto
            mTab.setValue("Z_ProductoSeccion_ID", prod.get_ValueAsInt(X_Z_ProductoSeccion.COLUMNNAME_Z_ProductoSeccion_ID));
            mTab.setValue("Z_ProductoRubro_ID", prod.get_ValueAsInt(X_Z_ProductoRubro.COLUMNNAME_Z_ProductoRubro_ID));
            mTab.setValue("C_UOM_ID", prod.getC_UOM_ID());
            mTab.setValue("C_TaxCategory_ID", prod.getC_TaxCategory_ID());

            if (prod.get_ValueAsInt(X_Z_ProductoFamilia.COLUMNNAME_Z_ProductoFamilia_ID) > 0){
                mTab.setValue("Z_ProductoFamilia_ID", prod.get_ValueAsInt(X_Z_ProductoFamilia.COLUMNNAME_Z_ProductoFamilia_ID));
            }

            if (prod.get_ValueAsInt(X_Z_ProductoSubfamilia.COLUMNNAME_Z_ProductoSubfamilia_ID) > 0){
                mTab.setValue("Z_ProductoSubfamilia_ID", prod.get_ValueAsInt(X_Z_ProductoSubfamilia.COLUMNNAME_Z_ProductoSubfamilia_ID));
            }

            // Precios de compra
            // Obtengo socio de negocio de la ultima factura, sino hay facturas, obtengo socio de ultima gestión de precios de proveedor.
            MZProductoSocio productoSocio = MZProductoSocio.getByLastInvoice(ctx, prod.get_ID(), null);
            if ((productoSocio == null) || (productoSocio.get_ID() <= 0)){
                productoSocio = MZProductoSocio.getByLastPriceOC(ctx, prod.get_ID(), null);
            }

            if ((productoSocio != null) && (productoSocio.get_ID() > 0)){
                mTab.setValue("PriceFinal", productoSocio.getPriceFinal());
                mTab.setValue("PriceInvoiced", productoSocio.getPriceInvoiced());
                mTab.setValue("PricePO", productoSocio.getPricePO());
            }
            else{
                mTab.setValue("PriceFinal", Env.ZERO);
                mTab.setValue("PriceInvoiced", Env.ZERO);
                mTab.setValue("PricePO", Env.ZERO);
            }

            // Obtengo y seteo precio de venta actual desde lista de precios de venta del cabezal
            int mPriceListID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_ID");
            int mPriceListVersionID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_Version_ID");
            MProductPrice productPrice = MProductPrice.get(ctx, mPriceListVersionID, prod.get_ID(), null);
            if (productPrice != null){
                mTab.setValue("PriceSO", productPrice.getPriceList());
                mTab.setValue("NewPriceSO", productPrice.getPriceList());
                mTab.setValue("DateValidSO", (Timestamp)productPrice.get_Value("ValidFrom"));
            }
            else{
                mTab.setValue("PriceSO", Env.ZERO);
                mTab.setValue("NewPriceSO", Env.ZERO);
                mTab.setValue("DateValidSO",null);
            }
        }
        else{
            mTab.setValue("Z_ProductoSeccion_ID", null);
            mTab.setValue("Z_ProductoRubro_ID", null);
            mTab.setValue("Z_ProductoFamilia_ID", null);
            mTab.setValue("Z_ProductoSubfamilia_ID", null);
            mTab.setValue("C_UOM_ID", null);
            mTab.setValue("C_TaxCategory_ID", null);
            mTab.setValue("PriceFinal", Env.ZERO);
            mTab.setValue("PriceInvoiced", Env.ZERO);
            mTab.setValue("PricePO", Env.ZERO);
            mTab.setValue("PriceSO", Env.ZERO);
            mTab.setValue("NewPriceSO", Env.ZERO);
        }

        return "";
    }


    /***
     * Setea moneda segun lista de precios recibida.
     * Xpande. Created by Gabriel Vila on 7/31/17.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String currencyByPriceList(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if ((value == null) || (((Integer) value).intValue() <= 0)){
            return "";
        }

        int mPriceListID = ((Integer) value).intValue();

        MPriceList priceList = MPriceList.get(ctx, mPriceListID, null);
        mTab.setValue("C_Currency_ID", priceList.getC_Currency_ID());

        return "";
    }


    /***
     * Cuando estoy en el preceso de asociacion de productos a proveedores en Gestión de Precios, seteo producto-codigo de barra, segun valor
     * digitado.
     * Xpande. Created by Gabriel Vila on 11/1/17.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String upcProductAsociacion(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        /*
        if ((value == null) || (((Integer) value).intValue() <= 0)){
            return "";
        }
        */

        // Solo para modalida de carga de precios: Asociación de Productos Existentes.
        int preciosProvCabID = Env.getContextAsInt(ctx, WindowNo, "Z_PreciosProvCab_ID");
        if (preciosProvCabID > 0){
            MZPreciosProvCab provCab = new MZPreciosProvCab(ctx, preciosProvCabID, null);
            if (provCab.getModalidadPreciosProv().equalsIgnoreCase("ASOCIA")){
                return upcProduct(ctx, WindowNo, mTab, mField, value);
            }
        }

        return "";
    }


}
