package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.util.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para conceptos de formulario de movimientos de efectivo en Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/29/19.
 */
public class MZRetailConfigForEfe extends X_Z_RetailConfigForEfe {

    public MZRetailConfigForEfe(Properties ctx, int Z_RetailConfigForEfe_ID, String trxName) {
        super(ctx, Z_RetailConfigForEfe_ID, trxName);
    }

    public MZRetailConfigForEfe(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Obtiene y retorna configuración contable de este concepto.
     * Xpande. Created by Gabriel Vila on 8/30/19.
     * @param adOrgID
     * @param cAcctSchemaID
     * @param cCurrencyID
     * @return
     */
    public X_Z_RetailConfForEfe_Acct getAccountConfig(int adOrgID, int cAcctSchemaID, int cCurrencyID) {

        X_Z_RetailConfForEfe_Acct confForEfe_acct = null;

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select z_retailconfforefe_acct_id " +
                    " from z_retailconfforefe_acct " +
                    " where z_retailconfigforefe_id =" + this.get_ID() +
                    " and ad_orgtrx_id =" + adOrgID +
                    " and c_acctschema_id =" + cAcctSchemaID +
                    " and c_currency_id =" + cCurrencyID;

        	pstmt = DB.prepareStatement(sql, get_TrxName());
        	rs = pstmt.executeQuery();

        	if(rs.next()){
                confForEfe_acct = new X_Z_RetailConfForEfe_Acct(getCtx(), rs.getInt("z_retailconfforefe_acct_id"), null);
        	}
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
        	rs = null; pstmt = null;
        }

        return confForEfe_acct;
    }
}
