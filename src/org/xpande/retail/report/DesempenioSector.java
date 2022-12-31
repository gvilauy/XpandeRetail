package org.xpande.retail.report;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xpande.retail.utils.ComercialUtils;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 12/17/20.
 */
public class DesempenioSector extends SvrProcess {

    private final String TABLA_REPORTE = "Z_RP_Retail_BalServ";

    private int adOrgID = 0;
    private int cBPartnerID = 0;
    private int zProductoSeccionID = 0;
    private int zProductoRubroID = 0;
    private int zProductoFamiliaID = 0;
    private int zProductoSubfamiliaID = 0;
    private int mProductID = 0;
    private String upc = "";
    private int cCurrencyID = 142;

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
                    else if (name.trim().equalsIgnoreCase("Z_ProductoSeccion_ID")){
                        this.zProductoSeccionID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("Z_ProductoRubro_ID")){
                        this.zProductoRubroID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("Z_ProductoFamilia_ID")){
                        this.zProductoFamiliaID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("Z_ProductoSubfamilia_ID")){
                        this.zProductoSubfamiliaID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("M_Product_ID")){
                        this.mProductID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("UPC")){
                        this.upc = para[i].getParameter().toString().trim();
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
            action = " insert into " + TABLA_REPORTE + " (ad_client_id, ad_org_id, ad_user_id, c_bpartner_id, m_product_id, value, name, datetrx, " +
                    " z_productoseccion_id, z_productorubro_id, z_productofamilia_id, z_productosubfamilia_id, z_productoupc_id, upc," +
                    " qtypurchased, qtysold, amtpurchased, amtsold) ";

            // Armo condicion where dinámica del reporte
            String whereClause = "";
            boolean filtraUPC = false;

            if (this.cBPartnerID > 0){
                whereClause += " and p.m_product_id in (select ps.m_product_id from z_productosocio ps where ps.c_bpartner_id =" + this.cBPartnerID +
                        " and ps.z_productosocio_id in (select z_productosocio_id from z_productosocioorg where ad_orgtrx_id =" + this.adOrgID + ")) ";

            }

            if (this.zProductoSeccionID > 0){
                whereClause += " and p.z_productoseccion_id =" + this.zProductoSeccionID;
            }
            if (this.zProductoRubroID > 0){
                whereClause += " and p.z_productorubro_id =" + this.zProductoRubroID;
            }
            if (this.zProductoFamiliaID > 0){
                whereClause += " and p.z_productofamilia_id =" + this.zProductoFamiliaID;
            }
            if (this.zProductoSubfamiliaID > 0){
                whereClause += " and p.z_productosubfamilia_id =" + this.zProductoSubfamiliaID;
            }
            if (this.mProductID > 0){
                whereClause += " and p.m_product_id =" + this.mProductID;
            }
            if ((this.upc != null) && (!this.upc.trim().equalsIgnoreCase(""))){
                whereClause += " and pupc.upc ='" + this.upc + "' ";
                filtraUPC = true;
            }

            if (!filtraUPC){

                sql = " select a.ad_client_id, a.ad_org_id, " + this.getAD_User_ID() + ", " +
                        " z_prodorg_ult_bp_po(a.ad_org_id, a.m_product_id)::numeric(10,0), " +
                        " a.m_product_id, p.value, p.name, '" +  this.startDate + "'::timestamp without time zone, " +
                        " p.z_productoseccion_id, p.z_productorubro_id, " +
                        " p.z_productofamilia_id, p.z_productosubfamilia_id, " +
                        " pupc.z_productoupc_id, pupc.upc, " +
                        " round(sum(qtypurchased),2) as qtypurchased, round(sum(qtysold),2) as qtysold, " +
                        " round(sum(amtsubtotalpo),2) as amtsubtotalpo, round(sum(amtsubtotal),2) as amtsubtotal " +
                        " from z_bi_invprodday a " +
                        " inner join m_product p on a.m_product_id = p.m_product_id " +
                        " left outer join zv_ultimoproductoupc vupc on p.m_product_id = vupc.m_product_id  " +
                        " left outer join z_productoupc pupc on vupc.z_productoupc_id = pupc.z_productoupc_id " +
                        " where a.ad_org_id =" + this.adOrgID +
                        " and a.dateinvoiced between '" + this.startDate + "' and '" + this.endDate + "' " + whereClause +
                        " group by 1,2,3,4,5,6,7,8,9,10,11,12,13,14 ";

            }
            else {

                sql = " select a.ad_client_id, a.ad_org_id, " + this.getAD_User_ID() + ", " +
                        " z_prodorg_ult_bp_po(a.ad_org_id, a.m_product_id)::numeric(10,0), " +
                        //((cBPartnerID > 0) ? String.valueOf(this.cBPartnerID) : "null") + "::numeric(10,0), " +
                        " a.m_product_id, p.value, p.name, '" +  this.startDate + "'::timestamp without time zone, " +
                        " p.z_productoseccion_id, p.z_productorubro_id, " +
                        " p.z_productofamilia_id, p.z_productosubfamilia_id, " +
                        " pupc.z_productoupc_id, pupc.upc, " +
                        " round(sum(qtypurchased),2) as qtypurchased, round(sum(qtysold),2) as qtysold, " +
                        " round(sum(amtsubtotalpo),2) as amtsubtotalpo, round(sum(amtsubtotal),2) as amtsubtotal " +
                        //" sum(totalamtpo) as totalamtpo, sum(totalamt) as totalamt " +
                        " from z_bi_invprodday a " +
                        " inner join m_product p on a.m_product_id = p.m_product_id " +
                        " left outer join z_productoupc pupc on p.m_product_id = pupc.m_product_id " +
                        " where a.ad_org_id =" + this.adOrgID +
                        " and a.dateinvoiced between '" + this.startDate + "' and '" + this.endDate + "' " + whereClause +
                        " group by 1,2,3,4,5,6,7,8,9,10,11,12,13,14 ";
            }

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
                    " order by m_product_id ";

            pstmt = DB.prepareStatement(sql, null);
            rs = pstmt.executeQuery();

            while(rs.next()){

                /*
                BigDecimal qtySold = rs.getBigDecimal("qtysold");
                if (qtySold == null) qtySold = Env.ZERO;
                if (qtySold.compareTo(Env.ZERO) == 0){
                    this.updateSalesInfo(rs.getInt("m_product_id"));
                }
                 */

                // Precio Promedio Venta
                BigDecimal precioPromedioVta = ComercialUtils.getPrecioPromedioVta(getCtx(), this.getAD_Client_ID(), this.adOrgID,
                        rs.getInt("m_product_id"), this.cCurrencyID, this.startDate, this.endDate, null);

                String action = " update " + TABLA_REPORTE +
                        " set PriceSO =" + precioPromedioVta +
                        " where ad_user_id =" + this.getAD_User_ID() +
                        " and m_product_id =" + rs.getInt("m_product_id");

                DB.executeUpdateEx(action, null);
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

    private void updateSalesInfo(int mProductID) {

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select round(sum(qtyinvoiced),2) as qtyinvoiced, round(sum(amtsubtotal),2) as amtsubtotal " +
                    " from z_bi_vtaproddia a " +
                    " where a.ad_org_id =" + this.adOrgID +
                    " and a.m_product_id =" + mProductID +
                    " and a.dateinvoiced between '" + this.startDate + "' and '" + this.endDate + "' ";
            pstmt = DB.prepareStatement(sql, null);
        	rs = pstmt.executeQuery();

        	if (rs.next()){
                BigDecimal qty = rs.getBigDecimal("qtyinvoiced");
                BigDecimal amt = rs.getBigDecimal("amtsubtotal");
                if (qty == null) qty = Env.ZERO;
                if (amt == null) amt = Env.ZERO;

                String action = " update " + TABLA_REPORTE +
                        " set qtysold =" + qty + ", amtsold =" + amt +
                        " where ad_user_id =" + this.getAD_User_ID() +
                        " and m_product_id =" + mProductID;

                DB.executeUpdateEx(action, null);
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
