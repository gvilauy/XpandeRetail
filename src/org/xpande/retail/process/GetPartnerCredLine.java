package org.xpande.retail.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.xpande.retail.model.MZCredLineGen;
import org.xpande.retail.model.MZCredLineGenBP;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 10/29/21.
 */
public class GetPartnerCredLine extends SvrProcess {

    private MZCredLineGen credLineGen = null;
    private int cBPartnerID = -1;
    private int zCreditLineCategoryID = -1;
    private int cBPGroupID = -1;

    @Override
    protected void prepare() {
        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){
            String name = para[i].getParameterName();
            if (name != null){
                if (para[i].getParameter() != null){
                    if (name.trim().equalsIgnoreCase("C_BPartner_ID")){
                        this.cBPartnerID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("C_BP_Group_ID")){
                        this.cBPGroupID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("Z_CreditLineCategory_ID")){
                        this.zCreditLineCategoryID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                }
            }
        }
        this.credLineGen = new MZCredLineGen(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{

            String action = " delete from z_credlinegenbp where z_credlinegen_id =" + this.credLineGen.get_ID();
            DB.executeUpdateEx(action, get_TrxName());

            String whereClause = "";
            if (this.cBPGroupID > 0){
                whereClause += " and bp.c_bp_group_id =" + this.cBPGroupID;
            }
            if (this.cBPartnerID > 0){
                whereClause += " and bp.c_bpartner_id =" + this.cBPartnerID;
            }
            if (this.zCreditLineCategoryID > 0){
                whereClause += " and bp.z_creditlinecategory_id =" + this.zCreditLineCategoryID;
            }

            sql = " select bp.c_bpartner_id, bp.taxid, bp.z_creditlinecategory_id, " +
                    " cat.c_currency_id, cat.creditlimit " +
                    " from c_bpartner bp " +
                    " inner join z_creditlinecategory cat on bp.z_creditlinecategory_id = cat.z_creditlinecategory_id " +
                    " where bp.isactive='Y' " +
                    " and bp.iscustomer ='Y' " + whereClause;
        	pstmt = DB.prepareStatement(sql, get_TrxName());
        	rs = pstmt.executeQuery();

        	while(rs.next()){
                MZCredLineGenBP genBP = new MZCredLineGenBP(getCtx(), 0, get_TrxName());
                genBP.setZ_CredLineGen_ID(this.credLineGen.get_ID());
                genBP.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
                genBP.setAmtBase(rs.getBigDecimal("creditlimit"));
                genBP.setCreditLimit(rs.getBigDecimal("creditlimit"));
                genBP.setTaxID(rs.getString("taxid"));
                genBP.setZ_CreditLineCategory_ID(rs.getInt("z_creditlinecategory_id"));
                genBP.setC_Currency_ID(rs.getInt("c_currency_id"));
                genBP.saveEx();
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
