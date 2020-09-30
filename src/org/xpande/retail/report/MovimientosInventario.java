package org.xpande.retail.report;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 9/29/20.
 */
public class MovimientosInventario extends SvrProcess {

    private final String TABLA_REPORTE = "Z_RP_RetailMovInv";

    private int adOrgID = 0;
    private int cDocTypeID = 0;
    private int mWarehouseID = 0;
    private int mLocatorID = 0;
    private int userAuditID = 0;
    private int mProductID = 0;
    private int zProductoSeccionID = 0;
    private int zProductoRubroID = 0;
    private int zProductoFamiliaID = 0;
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
                    else if (name.trim().equalsIgnoreCase("C_DocType_ID")){
                        this.cDocTypeID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("M_Warehouse_ID")){
                        this.mProductID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("M_Locator_ID")){
                        this.mProductID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("CreatedBy")){
                        this.userAuditID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("M_Product_ID")){
                        this.mProductID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("Z_ProductoSeccion_ID")){
                        this.zProductoSeccionID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("Z_ProductoRubro_ID")){
                        this.zProductoRubroID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("Z_ProductoFamilia_ID")){
                        this.zProductoRubroID = ((BigDecimal)para[i].getParameter()).intValueExact();
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
     * Xpande. Created by Gabriel Vila on 9/29/20.
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
     * Xpande. Created by Gabriel Vila on 9/29/20.
     */
    private void getData() {

        String sql = "", action = "";

        try{

            // Cadenas de insert en tablas del reporte
            action = " insert into " + TABLA_REPORTE + " (ad_client_id, ad_org_id, ad_user_id, " +
                    " c_doctype_id, codigoproducto, createdby, " +
                    " c_uom_id, datetrx, description, documentno, " +
                    " m_locator_id, m_product_id, m_warehouse_id, priceentered, " +
                    " qtyentered, upc, value, z_productofamilia_id, " +
                    " z_productorubro_id, z_productoseccion_id, z_productosubfamilia_id) ";

            // Armo condicion where dinámica del reporte
            String whereClause = "";

            if (this.adOrgID > 0){
                whereClause += " and hdr.ad_org_id =" + this.adOrgID;
            }
            if (this.cDocTypeID > 0){
                whereClause += " and hdr.c_doctype_id =" + this.cDocTypeID;
            }
            if (this.mWarehouseID > 0){
                whereClause += " and hdr.m_warehousesource_id =" + this.mWarehouseID;
            }
            if (this.mLocatorID > 0){
                whereClause += " and hdr.m_locatorto_id =" + this.mLocatorID;
            }
            if (this.userAuditID > 0){
                whereClause += " and hdr.createdby =" + this.userAuditID;
            }
            if (this.mProductID > 0){
                whereClause += " and lin.m_product_id =" + this.mProductID;
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


            sql = " select a.ad_client_id, a.ad_org_id, " + this.getAD_User_ID() + ", a.c_doctype_id, l.codigoproducto, a.createdby, " +
                    "l.c_uom_id, a.datedoc, a.description, a.documentno, a.m_locatorto_id, l.m_product_id, " +
                    "a.m_warehousesource_id, l.priceentered, l.qtyentered, l.upc, " +
                    "p.value, p.z_productofamilia_id, p.z_productorubro_id, p.z_productoseccion_id, p.z_productosubfamilia_id " +
                    "from z_stktransfer a " +
                    "inner join z_stktransferlin l on a.z_stktransfer_id = l.z_stktransfer_id " +
                    "inner join m_product p on l.m_product_id = p.m_product_id " +
                    "where a.docstatus='CO' " +
                    "and a.datedoc between '"  + this.startDate + "' and '" + this.endDate + "' " + whereClause;

            DB.executeUpdateEx(action + sql, null);

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

    }


    /***
     * Actualiza campos de las Tablas del Reporte.
     * Xpande. Created by Gabriel Vila on 4/16/20.
     */
    private void updateData() {

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select * from " + TABLA_REPORTE + " where ad_user_id =" + this.getAD_User_ID();

            pstmt = DB.prepareStatement(sql, null);
            rs = pstmt.executeQuery();

            while(rs.next()){

                // Actualizo información de compra
                this.updateInfoCompras(rs.getInt("ad_org_id"), rs.getInt("m_product_id"),
                        rs.getBigDecimal("qtyentered"));
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

    /***
     * Actualiza info de compras para este reporte.
     * Xpande. Created by Gabriel Vila on 9/30/20.
     * @param adOrgID
     * @param mProductID
     * @param qtyEntered
     */
    private void updateInfoCompras(int adOrgID, int mProductID, BigDecimal qtyEntered){

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String action = "";

        try{
            sql = " select a.c_bpartner_id, a.c_currency_id, a.c_doctypetarget_id, a.dateinvoiced, " +
                    "a.documentnoref, a.priceentered, bpp.vendorproductno " +
                    "from ZV_HistoricoCompras a " +
                    "inner join c_doctype doc on a.c_doctypetarget_id = doc.c_doctype_id " +
                    "left outer join z_productosocio bpp on (a.c_bpartner_id = bpp.c_bpartner_id and a.m_product_id = bpp.m_product_id) " +
                    "where a.ad_org_id =" + adOrgID +
                    "and a.m_product_id =" + mProductID +
                    "and doc.docbasetype='API' " +
                    "order by a.dateinvoiced desc";

        	pstmt = DB.prepareStatement(sql, get_TrxName());
        	rs = pstmt.executeQuery();

        	if (rs.next()){

        	    if (qtyEntered == null) qtyEntered = Env.ZERO;
        	    BigDecimal pricePO = rs.getBigDecimal("priceentered");
        	    BigDecimal amtTotal = Env.ZERO;
        	    if (pricePO == null) pricePO = Env.ZERO;
        	    if (pricePO.compareTo(Env.ZERO) > 0){
        	        amtTotal = pricePO.multiply(qtyEntered).setScale(2, RoundingMode.HALF_UP);
                }

                action = " update " + TABLA_REPORTE +
                        " set c_bpartner_id =" + rs.getInt("c_bpartner_id") + ", " +
                        " c_currency_id =" + rs.getInt("c_currency_id") + ", " +
                        " c_doctypetarget_id =" + rs.getInt("c_doctypetarget_id") + ", " +
                        " dateinvoiced ='" + rs.getTimestamp("dateinvoiced") + "', " +
                        " documentnoref ='" + rs.getString("documentnoref") + "', " +
                        " pricepo =" + rs.getBigDecimal("priceentered") + ", " +
                        " totalamt =" + amtTotal + ", " +
                        " vendorproductno ='" + rs.getString("vendorproductno") + "' " +
                        " where ad_user_id =" + this.getAD_User_ID() +
                        " and ad_org_id =" + adOrgID +
                        " and m_product_id =" + mProductID;
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
    }

}
