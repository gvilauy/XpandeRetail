package org.xpande.retail.callout;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MPriceList;
import org.compiere.util.Env;
import org.xpande.core.model.MZSocioListaPrecio;

import java.util.Properties;

/**
 * Callouts relacionados a la gestión de precios en el módulo de Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/14/17.
 */
public class CalloutPrecios extends CalloutEngine {

    public String priceListByCurrency(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (value == null) return "";

        String column = mField.getColumnName();

        int cCurrencyID = ((Integer)value).intValue();
        int cBpartnerID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_ID");

        String sql = "";

        // Si es moneda de compra la recibida, busco lista de compra del proveedor en esa moneda
        if (column.equalsIgnoreCase("C_Currency_ID")){
            MZSocioListaPrecio bplist = MZSocioListaPrecio.getByPartnerCurrency(ctx, cBpartnerID, cCurrencyID, null);
            if ((bplist != null) && (bplist.get_ID() > 0)){
                mTab.setValue("M_PriceList_ID", bplist.getM_PriceList_ID());
            }
            else{
                mTab.setValue("M_PriceList_ID", null);
            }
        }



        return "";
    }

}
