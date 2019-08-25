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
 * Reporte de Valoración de Inventario a una determinado fecha para Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 8/25/19.
 */
public class ValoracionInventario extends SvrProcess {

    private final String TABLA_REPORTE = "Z_RP_RetailValInv";

    private int adOrgID = 0;
    private int zProductoSeccionID = 0;
    private int zProductoRubroID = 0;
    private int zProductoFamiliaID = 0;
    private int zProductoSubfamiliaID = 0;
    private int mProductID = 0;
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
                    else if (name.trim().equalsIgnoreCase("EndDate")){
                        this.endDate = (Timestamp)para[i].getParameter();
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
            action = " insert into " + TABLA_REPORTE + " (ad_client_id, ad_org_id, ad_user_id, m_product_id, codigoproducto, name, enddate, " +
                    "z_productoseccion_id, z_productorubro_id, z_productofamilia_id, z_productosubfamilia_id) ";

            // Armo condicion where dinámica del reporte
            String whereClause = "";

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

            sql = " select p.ad_client_id, " + this.adOrgID + ", " + this.getAD_User_ID() + ", " +
                    " p.m_product_id, p.value, p.name, '" +  this.endDate + "', p.z_productoseccion_id, p.z_productorubro_id,  " +
                    " p.z_productofamilia_id, p.z_productosubfamilia_id " +
                    " from m_product p " +
                    " where p.issold ='Y' and p.ispurchased ='Y' and p.isactive ='Y' and p.ProductType ='I' " + whereClause +
                    " order by p.name ";

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

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            while(rs.next()){

                // Fecha ultima factura, moneda y precio
                this.updateInfoFactura(rs.getInt("m_product_id"));

                // Datos costos y PVP
                this.updateInfoCosto(rs.getInt("m_product_id"));
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

    private void updateInfoFactura(int mProductID) {

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select dateinvoiced, c_currency_id, priceentered " +
                    " from zv_historicocompras " +
                    " where ad_org_id =" + this.adOrgID +
                    " and m_product_id =" + mProductID +
                    " and priceentered > 0 " +
                    " and dateinvoiced <='" + this.endDate + "' " +
                    " order by dateinvoiced desc ";

        	pstmt = DB.prepareStatement(sql, get_TrxName());
        	rs = pstmt.executeQuery();

        	if(rs.next()){

                String action = " update " + TABLA_REPORTE +
                        " set DateInvoiced ='" + rs.getTimestamp("dateinvoiced") + "', " +
                        " c_currency_id =" + rs.getInt("c_currency_id") + ", " +
                        " priceinvoiced =" + rs.getBigDecimal("priceentered") +
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

    private void updateInfoCosto(int mProductID) {

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select c_currency_id, pricefinal, c_currency_id_so, priceso " +
                    " from Z_HistCostoProd " +
                    " where ad_orgtrx_id =" + this.adOrgID +
                    " and m_product_id =" + mProductID +
                    " and datevalidpo <='" + this.endDate + "' " +
                    " order by datevalidpo desc ";

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            if(rs.next()){

                String action = " update " + TABLA_REPORTE +
                        " set c_currency_1_id =" + rs.getInt("c_currency_id") + ", " +
                        " pricefinal =" + rs.getBigDecimal("pricefinal") + ", " +
                        " c_currency_id_so =" + rs.getInt("c_currency_id_so") + ", " +
                        " priceso =" + rs.getBigDecimal("priceso") +
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
