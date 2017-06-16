package org.xpande.retail.callout;

import org.compiere.model.*;
import org.compiere.util.Env;
import org.xpande.core.model.MZSocioListaPrecio;
import org.xpande.core.utils.PriceListUtils;

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

}
