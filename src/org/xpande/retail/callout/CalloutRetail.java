package org.xpande.retail.callout;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MProduct;
import org.xpande.core.model.MZProductoUPC;
import org.xpande.retail.model.MZLineaProductoSocio;

import java.util.Properties;

/**
 * Callouts generales relacionados al módulo de retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 11/22/17.
 */
public class CalloutRetail extends CalloutEngine {

    /***
     * Al cambiar linea de productos de un socio de negocio, se actualiza pauta comercial asociada, si tiene una.
     * Xpande. Created by Gabriel Vila on 11/22/17.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String pautaComercialByLinea(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (value == null) return "";

        int zLineaProductoSocioID = ((Integer) value).intValue();

        if (zLineaProductoSocioID > 0){
            MZLineaProductoSocio lineaProductoSocio = new MZLineaProductoSocio(ctx, zLineaProductoSocioID, null);
            if (lineaProductoSocio.getZ_PautaComercial_ID() > 0){
                mTab.setValue("Z_PautaComercial_ID", lineaProductoSocio.getZ_PautaComercial_ID());
            }
            else{
                mTab.setValue("Z_PautaComercial_ID", null);
            }
        }
        else{
            mTab.setValue("Z_PautaComercial_ID", null);
        }

        return "";
    }


    /***
     * Al ingresar código de barras o producto, se deben setear los otros valores asociados.
     * Xpande. Created by Gabriel Vila on 1/10/18.
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

        String column = mField.getColumnName();

        if (column.equalsIgnoreCase("UPC")){
            MZProductoUPC pupc = MZProductoUPC.getByUPC(ctx, value.toString().trim(), null);
            if ((pupc != null) && (pupc.get_ID() > 0)){
                mTab.setValue("M_Product_ID", pupc.getM_Product_ID());
            }
            else{
                mTab.setValue("M_Product_ID", null);
                mTab.fireDataStatusEEvent ("Error", "No existe Producto con código de barras ingresado", true);
            }
        }
        else if (column.equalsIgnoreCase("M_Product_ID")){
            int mProductID = ((Integer) value).intValue();
            MZProductoUPC pupc = MZProductoUPC.getByProduct(ctx, mProductID, null);
            if ((pupc != null) && (pupc.get_ID() > 0)){
                mTab.setValue("UPC", pupc.getUPC());
            }
            else{
                mTab.setValue("UPC", null);
            }
        }

        return "";
    }

}
