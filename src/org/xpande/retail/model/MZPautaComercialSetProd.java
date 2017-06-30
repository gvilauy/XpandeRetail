package org.xpande.retail.model;

import org.compiere.model.MProduct;
import org.compiere.model.Query;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Modelo para producto asociado a una segmento especial de una pauta comercial.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/27/17.
 */
public class MZPautaComercialSetProd extends X_Z_PautaComercialSetProd {

    public MZPautaComercialSetProd(Properties ctx, int Z_PautaComercialSetProd_ID, String trxName) {
        super(ctx, Z_PautaComercialSetProd_ID, trxName);
    }

    public MZPautaComercialSetProd(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {

        // Regla : un producto solo puede estar en dos segmentos (incluyendo un segmento general)
        // Valido por lo tanto que al intentar insertar un nuevo producto en este segmento, dicho producto
        // no este asociado ya a dos o mas segmentos de esta misma pauta.
        List<MZPautaComercialSet>  pautaComercialSets = this.getOtherProductSets();
        if (pautaComercialSets.size() >= 2){
            MProduct prod = (MProduct) this.getM_Product();
            log.saveError("ATENCIÓN", "El producto : " + prod.getValue() + " - " + prod.getName() + " ya se encuentra incluído " +
                        "en dos segmentos de esta pauta comercial, y por lo tanto no puede asociarse a un tercer segmento.");
            return false;
        }

        return true;
    }


    /***
     * Obtiene y retorna lista de segmentos asociados al producto de este segmento (excluyendo este segmento).
     * Xpande. Created by Gabriel Vila on 6/29/17.
     * @return
     */
    private List<MZPautaComercialSet> getOtherProductSets() {

        String whereClause = X_Z_PautaComercialSetProd.COLUMNNAME_Z_PautaComercialSet_ID + " !=" + this.getZ_PautaComercialSet_ID() +
                " AND " + X_Z_PautaComercialSetProd.COLUMNNAME_M_Product_ID + " =" + this.getM_Product_ID();

        List<MZPautaComercialSet> lines = new Query(getCtx(), I_Z_PautaComercialSetProd.Table_Name, whereClause, null).setOnlyActiveRecords(true).list();

        return lines;
    }

}
