package org.xpande.retail.report;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.xpande.retail.utils.ComercialUtils;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 10/31/22.
 */
public class DisponibleCtaCte extends SvrProcess {

    private final String TABLA_REPORTE = "Z_RP_GeoFinOperDet";

    private int adOrgID = 0;
    private int cBPartnerID = 0;
    private int cBPGroupID = 0;
    private int cCurrencyID = 142;
    private int zCreditLineID = 0;
    private int zCreditLineCategoryID = 0;

    private Timestamp startDate = null;
    private Timestamp endDate = null;

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
                    else if (name.trim().equalsIgnoreCase("C_BPartner_ID")){
                        this.cBPartnerID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("C_BP_Group_ID")){
                        this.cBPGroupID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("C_Currency_ID")){
                        this.cCurrencyID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("Z_CreditLine_ID")){
                        this.zCreditLineID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("Z_CreditLineCategory_ID")){
                        this.zCreditLineCategoryID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("DateTrx")){
                        this.startDate = (Timestamp)para[i].getParameter();
                        this.endDate = (Timestamp)para[i].getParameter_To();
                    }
                }
            }
        }
    }

    @Override
    protected String doIt() throws Exception {

        this.deleteData();

        this.getData();

        this.updateData();

        return "OK";
    }

    /***
     * Elimina información anterior para este usuario en tablas de reporte
     * Xpande. Created by Gabriel Vila on 9/11/17.
     */
    private void deleteData() {

        try{
            String action = " delete from " + TABLA_REPORTE + " where ad_user_id =" + this.getAD_User_ID();
            DB.executeUpdateEx(action, null);
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

    /***
     * Obtiene información inicial necesaria para el reporte e inserta la misma en tablas del reporte
     * Xpande. Created by Gabriel Vila on 9/11/17.
     */
    private void getData() {

        String sql = "", action = "";
        try{
            // Cadenas de insert en tablas del reporte
            action = " insert into " + TABLA_REPORTE + " (ad_client_id, ad_org_id, ad_user_id, datetrx, c_bpartner_id, " +
                    " c_bp_group_id, c_currency_id, bpcode, taxid, tipoidentificacion, z_creditline_id, z_creditlinecategory_id, " +
                    " amttotal1, amttotal2) ";

            // Armo condicion where dinámica del reporte
            String whereClause = "";

            if (this.cBPartnerID > 0){
                whereClause += " and a.c_bpartner_id =" + this.cBPartnerID;
            }
            if (this.cCurrencyID > 0){
                whereClause += " and a.c_currency_id =" + this.cCurrencyID;
            }
            if (this.zCreditLineID > 0){
                whereClause += " and a.z_creditline_id =" + this.zCreditLineID;
            }
            if (this.zCreditLineCategoryID > 0){
                whereClause += " and a.z_creditlinecategory_id =" + this.zCreditLineCategoryID;
            }
            if (this.cBPGroupID > 0){
                whereClause += " and bp.c_bp_group_id =" + this.cBPGroupID;
            }

            sql = " select a.ad_client_id, a.ad_org_id, " + this.getAD_User_ID() + ", '" + this.startDate + "', " +
                    " a.c_bpartner_id, bp.c_bp_group_id, a.c_currency_id, bp.value, bp.taxid, b.name as tipoident, " +
                    " a.z_creditline_id, a.z_creditlinecategory_id, a.amtbase, a.creditlimit " +
                    " from z_creditline a " +
                    " inner join c_bpartner bp on a.c_bpartner_id = bp.c_bpartner_id " +
                    " inner join c_taxgroup b on bp.c_taxgroup_id = b.c_taxgroup_id " +
                    " where a.ad_org_id =" + this.adOrgID +
                    " and startdate >='" + this.startDate + "' " +
                    " and enddate <='" +  this.endDate + "' " + whereClause +
                    " order by startdate, z_creditline_id ";

            DB.executeUpdateEx(action + sql, null);
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

    /***
     * Actualiza campos de las Tablas del Reporte.
     * Xpande. Created by Gabriel Vila on 9/11/17.
     */
    private void updateData() {

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select * from " + TABLA_REPORTE + " where ad_user_id =" + this.getAD_User_ID() +
                    " order by datetrx ";

            pstmt = DB.prepareStatement(sql, null);
            rs = pstmt.executeQuery();

            while(rs.next()){

                /*
                String action = " update " + TABLA_REPORTE +
                        " set PriceSO =" + precioPromedioVta + ", " +
                        " qtypurchased =" + cantComprada + ", " +
                        " amtpurchased =" + amtComprado + ", " +
                        " qtysold =" + cantVendida + ", " +
                        " amtsold =" + amtVendido +
                        " where ad_user_id =" + this.getAD_User_ID() +
                        " and m_product_id =" + rs.getInt("m_product_id");

                DB.executeUpdateEx(action, null);

                 */
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
