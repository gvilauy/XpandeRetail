package org.xpande.retail.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/6/21.
 */
public class FixProdCodDif extends SvrProcess {

    @Override
    protected void prepare() {

    }

    @Override
    protected String doIt() throws Exception {

        String sql, action;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select * from aux_ff";

        	pstmt = DB.prepareStatement(sql, get_TrxName());
        	rs = pstmt.executeQuery();

        	while(rs.next()){
                int mProductID = rs.getInt("m_product_id");
                String codDif = rs.getString("coddif").trim();

        	    action = " delete from Z_DifProdOrg where m_product_id =" + mProductID;
                DB.executeUpdateEx(action, get_TrxName());

                // Alencur
                action = " INSERT INTO z_difprodorg(ad_client_id, ad_org_id, ad_orgtrx_id, codigoproducto, created," +
                        " createdby, isactive, m_product_id, updated, updatedby, z_difprodorg_id) " +
                        " values (1000000, 0, 1000010, '" + codDif + "', now(), 100, 'Y', " + mProductID +
                        ", now(), 100, nextid(1000836, 'N')) ";
                DB.executeUpdateEx(action, get_TrxName());

                // Bekemar
                action = " INSERT INTO z_difprodorg(ad_client_id, ad_org_id, ad_orgtrx_id, codigoproducto, created," +
                        " createdby, isactive, m_product_id, updated, updatedby, z_difprodorg_id) " +
                        " values (1000000, 0, 1000008, '" + codDif + "', now(), 100, 'Y', " + mProductID +
                        ", now(), 100, nextid(1000836, 'N')) ";
                DB.executeUpdateEx(action, get_TrxName());

                // Cafanor
                action = " INSERT INTO z_difprodorg(ad_client_id, ad_org_id, ad_orgtrx_id, codigoproducto, created," +
                        " createdby, isactive, m_product_id, updated, updatedby, z_difprodorg_id) " +
                        " values (1000000, 0, 1000009, '" + codDif + "', now(), 100, 'Y', " + mProductID +
                        ", now(), 100, nextid(1000836, 'N')) ";
                DB.executeUpdateEx(action, get_TrxName());

                // Solvencia
                action = " INSERT INTO z_difprodorg(ad_client_id, ad_org_id, ad_orgtrx_id, codigoproducto, created," +
                        " createdby, isactive, m_product_id, updated, updatedby, z_difprodorg_id) " +
                        " values (1000000, 0, 1000007, '" + codDif + "', now(), 100, 'Y', " + mProductID +
                        ", now(), 100, nextid(1000836, 'N')) ";
                DB.executeUpdateEx(action, get_TrxName());
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
        	rs = null; pstmt = null;
        }
        return "OK";
    }
}
