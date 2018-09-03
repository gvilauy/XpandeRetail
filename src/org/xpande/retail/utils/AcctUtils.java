package org.xpande.retail.utils;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.util.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Clase de métodos staticos referidos a funcionalidades contables para Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/28/18.
 */
public final class AcctUtils {


    /***
     * Configuración contable de un determinado producto en el módulo de Retail.
     * Xpande. Created by Gabriel Vila on 7/12/18.
     * @param ctx
     * @param adClientID
     * @param mProductID
     * @param cTaxCategoryID
     * @param zRetailConfigID
     * @param trxName
     */
    public static void setRetailProdAcct(Properties ctx, int adClientID, int mProductID, int cTaxCategoryID, int zRetailConfigID, String trxName){

        String action = "";
        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{

            // Esquema contable
            MClient client = new MClient(ctx, adClientID, null);
            MAcctSchema schema = client.getAcctSchema();

            int pExpenseAcctID = 0, pRevenueAcctID = 0;

            // Obtengo parametrización de cuentas contables, en retail, para categoría de impuesto recibida.
            sql = " select * from z_retailconfigprodacct " +
                    " where z_retailconfig_id =" + zRetailConfigID +
                    " and c_taxcategory_id =" + cTaxCategoryID;

            pstmt = DB.prepareStatement(sql, trxName);
            rs = pstmt.executeQuery();

            if (rs.next()){
                pExpenseAcctID = rs.getInt("P_Expense_Acct");
                pRevenueAcctID = rs.getInt("P_Revenue_Acct");
            }
            else{
                return;
            }

            // Actualizo las cuentas del producto, en caso de ya estar seteadas
            action = " update m_product_acct set P_InventoryClearing_Acct =" + pExpenseAcctID + ", " +
                    " P_Expense_Acct =" + pExpenseAcctID + ", " +
                    " P_Revenue_Acct =" + pRevenueAcctID +
                    " where ad_client_id =" + client.get_ID() +
                    " and C_AcctSchema_ID =" + schema.get_ID() +
                    " and m_product_id =" + mProductID;
            int updated = DB.executeUpdate(action, trxName);
            if (updated == 0){
                action = " INSERT INTO m_product_acct(ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, " +
                        " m_product_id, c_acctschema_id, p_revenue_acct, p_expense_acct, p_inventoryclearing_acct) " +
                        " values (" + client.get_ID() + ", 0, 'Y', now(), 100, now(), 100, " + mProductID + ", " +
                        schema.get_ID() + ", " + pRevenueAcctID + ", " + pExpenseAcctID + ", " + pExpenseAcctID + ")";
                DB.executeUpdateEx(action, trxName);
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
            rs = null; pstmt = null;
        }
    }

}
