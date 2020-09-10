package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Modelo para configuración general del módulo de retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 11/6/17.
 */
public class MZRetailConfig extends X_Z_RetailConfig {

    public MZRetailConfig(Properties ctx, int Z_RetailConfig_ID, String trxName) {
        super(ctx, Z_RetailConfig_ID, trxName);
    }

    public MZRetailConfig(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    /***
     * Obtiene modelo único de configuración de modulo de retail.
     * Xpande. Created by Gabriel Vila on 11/6/17.
     * @param ctx
     * @param trxName
     * @return
     */
    public static MZRetailConfig getDefault(Properties ctx, String trxName){

        MZRetailConfig model = new Query(ctx, I_Z_RetailConfig.Table_Name, "", trxName).first();

        return model;
    }


    /***
     * Obtiene y retorna configuración para interface contra DGC para una determinada organización.
     * Xpande. Created by Gabriel Vila on 11/6/17.
     * @param adOrgID
     * @return
     */
    public MZRetailConfigDGC getConfigDGCByOrg(int adOrgID){

        String whereClause = X_Z_RetailConfigDGC.COLUMNNAME_Z_RetailConfig_ID + " =" + this.get_ID() +
                " AND " + X_Z_RetailConfigDGC.COLUMNNAME_AD_OrgTo_ID + " =" + adOrgID;

        MZRetailConfigDGC model = new Query(getCtx(), I_Z_RetailConfigDGC.Table_Name, whereClause, get_TrxName()).first();

        return model;
    }

    /***
     * Obtiene y retorna conceptos de formulario de movimientos de efectivo en Retail.
     * Xpande. Created by Gabriel Vila on 8/29/19.
     * @return
     */
    public List<MZRetailConfigForEfe> getConceptosFormEfe(boolean esFormularioF01){

        String whereClause = X_Z_RetailConfigForEfe.COLUMNNAME_Z_RetailConfig_ID + " =" + this.get_ID();

        if (esFormularioF01){
            whereClause += " AND " + X_Z_RetailConfigForEfe.COLUMNNAME_AplicaF01 + " ='Y' ";
        }
        else{
            whereClause += " AND " + X_Z_RetailConfigForEfe.COLUMNNAME_AplicaF02 + " ='Y' ";
        }

        List<MZRetailConfigForEfe> lines = new Query(getCtx(), I_Z_RetailConfigForEfe.Table_Name, whereClause, get_TrxName())
                .setOnlyActiveRecords(true).setOrderBy(" SeqNo ").list();

        return lines;
    }

    /***
     * Obtiene y retorna configuración contable para formulario de movimientos de efectivo.
     * Xpande. Created by Gabriel Vila on 8/30/19.
     * @param adOrgID
     * @param cAcctSchemaID
     * @param cCurrencyID
     * @return
     */
    public X_Z_RetailForEfe_Acct getFormEfectivoAcct(int cAcctSchemaID, int cCurrencyID, boolean esFormulario01) {

        X_Z_RetailForEfe_Acct forEfeAcct = null;

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{

            String whereClause = "";
            if (esFormulario01){
                whereClause = " and aplicaf01 ='Y' ";
            }
            else{
                whereClause = " and aplicaf02 ='Y' ";
            }

            sql = " select z_retailforefe_acct_id " +
                    " from z_retailforefe_acct " +
                    " where z_retailconfig_id =" + this.get_ID() +
                    " and c_acctschema_id =" + cAcctSchemaID +
                    " and c_currency_id =" + cCurrencyID + whereClause;

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            if(rs.next()){
                forEfeAcct = new X_Z_RetailForEfe_Acct(getCtx(), rs.getInt("z_retailforefe_acct_id"), null);
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
            rs = null; pstmt = null;
        }

        return forEfeAcct;
    }


}
