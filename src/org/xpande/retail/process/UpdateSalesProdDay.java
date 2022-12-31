package org.xpande.retail.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 12/30/22.
 */
public class UpdateSalesProdDay extends SvrProcess {

    private int adOrgID = -1;
    private Timestamp dateFrom = null;
    private Timestamp dateTo = null;

    @Override
    protected void prepare() {
        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){

            String name = para[i].getParameterName();

            if (name != null){
                if (para[i].getParameter() != null){
                    if (name.trim().equalsIgnoreCase("AD_Org_ID")){
                        this.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("DateTrx")){
                        this.dateFrom = (Timestamp)para[i].getParameter();
                        this.dateTo = (Timestamp)para[i].getParameter_To();
                    }
                }
            }
        }
    }

    @Override
    protected String doIt() throws Exception {

        this.deleteData();
        this.setDataInformes();

        return "OK";
    }

    private void deleteData(){

        try{
            String action = " update z_bi_invprodday set " +
                    " qtysold = 0, " +
                    " amtsubtotal = 0, " +
                    " totalamt = 0 " +
                    " where ad_org_id =" + this.adOrgID +
                    " and dateinvoiced between '" + this.dateFrom + "' and '" + this.dateTo + "' ";

            DB.executeUpdateEx(action, get_TrxName());

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

    }

    private void setDataInformes() {

        String sql, action;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{

            String insert = " insert into z_bi_invprodday (ad_client_id, ad_org_id, dateinvoiced, c_currency_id, m_product_id, " +
                    " c_uom_id, qtysold, amtsubtotal, totalamt, qtypurchased, amtsubtotalpo, totalamtpo, z_bi_dia_id) ";

            // Ventas
            sql = " select a.ad_client_id, a.ad_org_id, a.dateinvoiced, 142::numeric(10,0) as c_currency_id, " +
                    " a.m_product_id, prod.c_uom_id, " +
                    " a.qtyinvoiced, a.amtsubtotal, a.z_bi_dia_id " +
                    " from z_bi_vtaproddia a " +
                    " inner join m_product prod on a.m_product_id = prod.m_product_id " +
                    " where a.ad_org_id =" + this.adOrgID +
                    " and a.dateinvoiced between '" + this.dateFrom + "' and '" + this.dateTo + "' ";

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            while(rs.next()){

                // Verifico si no existe un registro para esta clave en la tabla de analisis
                sql = " select count(*) from z_bi_invprodday " +
                        " where ad_org_id =" + rs.getInt("ad_org_id") +
                        " and m_product_id =" + rs.getInt("m_product_id") +
                        " and c_currency_id =" + rs.getInt("c_currency_id") +
                        " and dateinvoiced ='" + rs.getTimestamp("dateinvoiced") + "' ";
                int contador = DB.getSQLValueEx(get_TrxName(), sql);

                // Si no existe aún un registro para esta clave
                if (contador <= 0){

                    // Insert
                    action = " values (" + this.getAD_Client_ID() + ", " + rs.getInt("ad_org_id") + ", '" +
                            rs.getTimestamp("dateinvoiced") + "', " + rs.getInt("c_currency_id") + ", " +
                            rs.getInt("m_product_id") + ", " + rs.getInt("c_uom_id") + ", " +
                            rs.getBigDecimal("qtyinvoiced") + ", " + rs.getBigDecimal("amtsubtotal") + ", " +
                            rs.getBigDecimal("amtsubtotal") + ", 0, 0, 0, " + rs.getInt("z_bi_dia_id") + ") ";

                    DB.executeUpdateEx(insert + action, get_TrxName());
                }
                else {
                    // Actualizo
                    action = " update z_bi_invprodday set " +
                            " qtysold = qtysold + " + rs.getBigDecimal("qtyinvoiced") + ", " +
                            " amtsubtotal = amtsubtotal + " + rs.getBigDecimal("amtsubtotal") + ", " +
                            " totalamt = totalamt + " + rs.getBigDecimal("amtsubtotal") +
                            " where ad_org_id =" + rs.getInt("ad_org_id") +
                            " and m_product_id =" + rs.getInt("m_product_id") +
                            " and c_currency_id =" + rs.getInt("c_currency_id") +
                            " and dateinvoiced ='" + rs.getTimestamp("dateinvoiced") + "' ";

                    DB.executeUpdateEx(action, get_TrxName());
                }
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
