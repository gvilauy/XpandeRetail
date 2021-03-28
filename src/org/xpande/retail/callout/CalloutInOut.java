package org.xpande.retail.callout;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MProduct;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xpande.core.model.MZProductoUPC;
import org.xpande.retail.model.MZProductoSocio;
import org.zkoss.zhtml.Big;

import java.math.BigDecimal;
import java.util.Properties;

/**
 * Callouts para entregas/rececpiones/devoluciones en el módulo de retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/4/17.
 */
public class CalloutInOut extends CalloutEngine {

    /***
     * Al ingresar código de barras, o codigo de producto del proveedor, o el producto directamente,
     * se deben setear los otros dos valores asociados.
     * Xpande. Created by Gabriel Vila on 6/25/17.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String upcVendProdNoProduct(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (isCalloutActive()) return "";

        if ((value == null) || (value.toString().trim().equalsIgnoreCase(""))){
            mTab.setValue("UPC", null);
            mTab.setValue("VendorProductNo", null);
            mTab.setValue("M_Product_ID", null);
            return "";
        }

        int cBPartnerID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_ID");

        String column = mField.getColumnName();

        if (column.equalsIgnoreCase("UPC")){
            MZProductoUPC pupc = MZProductoUPC.getByUPC(ctx, value.toString().trim(), null);
            if ((pupc != null) && (pupc.get_ID() > 0)){
                MProduct prod = (MProduct) pupc.getM_Product();
                MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(ctx, cBPartnerID, prod.get_ID(), null);
                if ((productoSocio == null) || (productoSocio.get_ID() <= 0)){
                    mTab.setValue("VendorProductNo", null);
                    mTab.setValue("M_Product_ID", null);
                    mTab.fireDataStatusEEvent ("Error", "El código de barras ingresado no pertenece a un Producto de este Socio de Negocio.", true);
                }
                else{
                    if (productoSocio.getVendorProductNo() != null){
                        if (!productoSocio.getVendorProductNo().trim().equalsIgnoreCase("")){
                            mTab.setValue("VendorProductNo", productoSocio.getVendorProductNo().trim());
                        }
                    }
                    mTab.setValue("M_Product_ID", prod.get_ID());
                }
            }
        }
        else if (column.equalsIgnoreCase("VendorProductNo")){
            MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerVendorProdNo(ctx, cBPartnerID, value.toString().trim(), null);
            if ((productoSocio != null) && (productoSocio.get_ID() > 0)){
                MProduct prod = (MProduct) productoSocio.getM_Product();
                MZProductoUPC pupc = MZProductoUPC.getByProduct(ctx, prod.get_ID(), null);
                if ((pupc != null) & (pupc.get_ID() > 0)){
                    mTab.setValue("UPC", pupc.getUPC());
                }
                mTab.setValue("M_Product_ID", prod.get_ID());
            }
        }
        else if (column.equalsIgnoreCase("M_Product_ID")){
            int mProductID = ((Integer) value).intValue();
            MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(ctx, cBPartnerID, mProductID, null);
            if ((productoSocio != null) && (productoSocio.get_ID() > 0)){
                if (productoSocio.getVendorProductNo() != null){
                    if (!productoSocio.getVendorProductNo().trim().equalsIgnoreCase("")){
                        mTab.setValue("VendorProductNo", productoSocio.getVendorProductNo().trim());
                    }
                    else{
                        mTab.setValue("VendorProductNo", null);
                    }
                }
            }
            else{
                mTab.fireDataStatusEEvent ("Error", "El Producto ingresado No pertenece a este Socio de Negocio.", true);
            }
            // Seteo UPC traído del producto, cuando el usuario no ingreso UPC
            if ((mTab.getValue("UPC") == null) || (mTab.getValue("UPC").toString().trim().equalsIgnoreCase(""))){
                MZProductoUPC pupc = MZProductoUPC.getByProduct(ctx, mProductID, null);
                if ((pupc != null) && (pupc.get_ID() > 0)){
                    mTab.setValue("UPC", pupc.getUPC());
                }
            }
            else{
                // El usuario ingreso un UPC y ademas selecciono un producto.
                // Puede pasar que quiera asociar un nuevo UPC al producto seleccionado o que cambió el producto y el UPC es de otro.
                // En el segundo caso, tengo que cargar el UPC correcto del nuevo producto seleccionado.
                String upc = mTab.getValue("UPC").toString().trim();
                MZProductoUPC pupc = MZProductoUPC.getByUPC(ctx, upc, null);
                if ((pupc != null) && (pupc.get_ID() > 0)){
                    if (pupc.getM_Product_ID() != mProductID){
                        MZProductoUPC pupcProd = MZProductoUPC.getByProduct(ctx, mProductID, null);
                        if ((pupcProd != null) && (pupcProd.get_ID() > 0)){
                            mTab.setValue("UPC", pupcProd.getUPC());
                        }
                        else{
                            mTab.setValue("UPC", null);
                        }
                    }
                }
            }
        }

        return "";
    }

    /***
     * En linea de inout, setea cantidad recibida según cantidad facturada.
     * Xpande. Created by Gabriel Vila on 3/26/21.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String setQtyByQtyInvoiced(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if ((value == null) || (((Integer) value).intValue() <= 0)){
            return "";
        }

        BigDecimal qtyInvoiced = (BigDecimal) value;
        BigDecimal qtyEntered = (BigDecimal) mTab.getValue("QtyEntered");

        if ((qtyEntered == null) || (qtyEntered.compareTo(Env.ZERO) == 0)){
            mTab.setValue("QtyEntered", qtyInvoiced);
        }

        return "";
    }


}
