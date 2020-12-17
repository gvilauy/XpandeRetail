package org.xpande.retail.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.xpande.retail.model.MZRetailConfig;
import org.xpande.retail.model.X_Z_OfertaVenta;
import org.xpande.retail.utils.AcctUtils;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Proceso para refrescar cuentas contables de productos según parametrización según impuesto de compra en Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 3/24/19.
 */
public class RefrescarAcctProdTax extends SvrProcess {

    private int cTaxCategoryID = 0;

    @Override
    protected void prepare() {

        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){

            String name = para[i].getParameterName();

            if (name != null){

                if (para[i].getParameter() != null){

                    if (name.trim().equalsIgnoreCase("C_TaxCategory_ID")){
                        this.cTaxCategoryID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                }
            }
        }

    }

    @Override
    protected String doIt() throws Exception {

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{

            MZRetailConfig retailConfig = MZRetailConfig.getDefault(getCtx(), get_TrxName());

            sql = " select m_product_id " +
                    " from m_product " +
                    " where ispurchased ='Y' " +
                    " and producttype ='I' " +
                    " and ((c_taxcategory_id =" + this.cTaxCategoryID + " and c_taxcategory_id_2 is null) " +
                    " or c_taxcategory_id_2 =" + this.cTaxCategoryID + ")";

        	pstmt = DB.prepareStatement(sql, get_TrxName());
        	rs = pstmt.executeQuery();

        	while(rs.next()){

        	    // Actualizo cuentas contables para este producto según impuesto
                AcctUtils.setRetailProdAcct(getCtx(), getAD_Client_ID(), rs.getInt("m_product_id"), this.cTaxCategoryID, retailConfig.get_ID(), get_TrxName());
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
