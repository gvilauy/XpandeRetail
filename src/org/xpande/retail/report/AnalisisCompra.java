package org.xpande.retail.report;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.xpande.core.utils.DateUtils;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Reporte para poder analisar información de compra y venta de productos al momento de generar una
 * orden de compra.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 4/16/20.
 */
public class AnalisisCompra  extends SvrProcess {

    private final String TABLA_REPORTE = "Z_RP_Retail_ACpra";

    private int adOrgID = 0;
    private int cBPartnerID = 0;
    private int zProductoSeccionID = 0;
    private int zProductoRubroID = 0;
    private int mProductID = 0;
    private int zLineaProductoSocioID = 0;
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
                    else if (name.trim().equalsIgnoreCase("Z_LineaProductoSocio_ID")){
                        this.zLineaProductoSocioID = ((BigDecimal)para[i].getParameter()).intValueExact();
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
     * Xpande. Created by Gabriel Vila on 4/16/20.
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
     * Xpande. Created by Gabriel Vila on 4/16/20.
     */
    private void getData() {

        String sql = "", action = "";

        try{

            // Cadenas de insert en tablas del reporte
            action = " insert into " + TABLA_REPORTE + " (ad_client_id, ad_org_id, ad_user_id, enddate, c_bpartner_id, " +
                     " m_product_id, c_uom_id, z_productoseccion_id, z_productorubro_id, z_lineaproductosocio_id) ";

            // Armo condicion where dinámica del reporte
            String whereClause = "";

            if (this.mProductID > 0){
                whereClause += " and p.m_product_id =" + this.mProductID;
            }
            if (this.zProductoSeccionID > 0){
                whereClause += " and p.z_productoseccion_id =" + this.zProductoSeccionID;
            }
            if (this.zProductoRubroID > 0){
                whereClause += " and p.z_productorubro_id =" + this.zProductoRubroID;
            }
            if (this.zLineaProductoSocioID > 0){
                whereClause += " and bpp.Z_LineaProductoSocio_ID =" + this.zLineaProductoSocioID;
            }

            sql = " select p.ad_client_id, " + this.adOrgID + ", " + this.getAD_User_ID() + ", '" + this.endDate + "', " +
                    this.cBPartnerID + ", p.m_product_id, p.c_uom_id, p.z_productoseccion_id, p.z_productorubro_id,  " +
                    ((this.zLineaProductoSocioID > 0) ? String.valueOf(this.zLineaProductoSocioID) : "null") + "::numeric(10,0) " +
                    " from m_product p " +
                    " inner join z_productosocio bpp on (bpp.m_product_id = p.m_product_id and bpp.c_bpartner_id =" + this.cBPartnerID + ") " +
                    " where p.ispurchased ='Y' and p.isactive ='Y' " + whereClause +
                    " order by p.name ";

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
            sql = " select * from " + TABLA_REPORTE + " where ad_user_id =" + this.getAD_User_ID() +
                    " order by m_product_id ";

            pstmt = DB.prepareStatement(sql, null);
            rs = pstmt.executeQuery();

            while(rs.next()){

                // Actualizo datos desde hitórico de compras
                this.updateHistoricoCompra(rs.getInt("m_product_id"));

                // Actualizo datos desde hitórico de costos
                this.updateHistoricoCostos(rs.getInt("m_product_id"));

                // Actualizo dato de precio de venta
                this.updatePrecioVenta(rs.getInt("m_product_id"));

                // Actualizo datos de ventas del año pasado
                this.updateVentasAnioAnterior(rs.getInt("m_product_id"));

                // Actualizo datos de ventas del mes pasado
                this.updateVentasMesPasado(rs.getInt("m_product_id"));
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
     * Actualizo datos desde histórico de compras para un determinado producto.
     * Xpande. Created by Gabriel Vila on 4/17/20.
     * @param productID
     */
    private void updateHistoricoCompra(int productID) {

        String sql = "", action = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select a.dateinvoiced, round(a.priceentered,2) as priceentered, round(a.qtyentered,2) as qtyentered " +
                    " from zv_historicocompras a " +
                    " inner join c_doctype doc on a.c_doctypetarget_id = doc.c_doctype_id " +
                    " where a.ad_org_id =" + this.adOrgID +
                    " and a.dateinvoiced <='" + this.endDate + "'" +
                    " and a.c_bpartner_id =" + this.cBPartnerID +
                    " and a.m_product_id =" + productID +
                    " and doc.docbasetype='API' " +
                    " order by a.dateinvoiced desc ";

        	pstmt = DB.prepareStatement(sql, get_TrxName());
        	rs = pstmt.executeQuery();

        	if (rs.next()){

        	    Timestamp dateInvoiced = rs.getTimestamp("dateinvoiced");
        	    if (dateInvoiced != null){
        	        action = " update " + TABLA_REPORTE +
                            " set dateinvoiced ='" + dateInvoiced + "', " +
                            " priceentered =" + rs.getBigDecimal("priceentered") + ", " +
                            " qtyentered =" + rs.getBigDecimal("qtyentered") +
                            " where ad_user_id =" + this.getAD_User_ID() +
                            " and m_product_id =" + productID;
                }
        	    else {
                    action = " update " + TABLA_REPORTE +
                            " set priceentered =" + rs.getBigDecimal("priceentered") + ", " +
                            " qtyentered =" + rs.getBigDecimal("qtyentered") +
                            " where ad_user_id =" + this.getAD_User_ID() +
                            " and m_product_id =" + productID;
                }

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

    /***
     * Actualizo datos desde histórico de costos para un determinado producto.
     * Xpande. Created by Gabriel Vila on 4/17/20.
     * @param productID
     */
    private void updateHistoricoCostos(int productID) {

        String sql = "", action = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select round(pricefinal,2) as pricefinal, round(pricepo,2) as pricepo " +
                    " from Z_HistCostoProd " +
                    " where ad_orgtrx_id =" + this.adOrgID +
                    " and datevalidpo <='" + this.endDate + "'" +
                    " and c_bpartner_id =" + this.cBPartnerID +
                    " and m_product_id =" + productID +
                    " order by datevalidpo desc  ";

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            if (rs.next()){

                action = " update " + TABLA_REPORTE +
                        " set pricefinal =" + rs.getBigDecimal("pricefinal") + ", " +
                        " pricepo =" + rs.getBigDecimal("pricepo") +
                        " where ad_user_id =" + this.getAD_User_ID() +
                        " and m_product_id =" + productID;

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


    /***
     * Actualizo precio de venta.
     * Xpande. Created by Gabriel Vila on 4/17/20.
     * @param productID
     */
    private void updatePrecioVenta(int productID) {

        String sql = "", action = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select c_currency_id, round(priceso,2) as priceso " +
                    " from Z_EvolPrecioVtaProdOrg " +
                    " where ad_orgtrx_id =" + this.adOrgID +
                    " and datevalidso <='" + this.endDate + "'" +
                    " and m_product_id =" + productID +
                    " order by datevalidso desc ";

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            if (rs.next()){

                action = " update " + TABLA_REPORTE +
                        " set c_currency_id =" + rs.getInt("c_currency_id") + ", " +
                        " priceso =" + rs.getBigDecimal("priceso") +
                        " where ad_user_id =" + this.getAD_User_ID() +
                        " and m_product_id =" + productID;

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

    /***
     * Actualiza cantidad vendida en los 30 días anteriores a la fecha.
     * Xpande. Created by Gabriel Vila on 4/17/20.
     * @param productID
     */
    private void updateVentasAnioAnterior(int productID) {

        String sql = "", action = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{

            Date dateFechaDesdeAux = new Date(this.endDate.getTime());
            Date dateFechaDesde = DateUtils.addDays(dateFechaDesdeAux, -365);
            Timestamp tsFechaDesde = new Timestamp(dateFechaDesde.getTime());

            Date dateFechaHasta = DateUtils.addDays(dateFechaDesde, 30);
            Timestamp tsFechaHasta = new Timestamp(dateFechaHasta.getTime());

            sql = " select sum(qtyinvoiced) as qtyinvoiced " +
                    "from z_bi_vtaproddia " +
                    "where ad_org_id =" + this.adOrgID +
                    "and m_product_id =" + productID +
                    "and dateinvoiced between '" + tsFechaDesde + "' and '" + tsFechaHasta + "' ";

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            if (rs.next()){

                action = " update " + TABLA_REPORTE +
                        " set qty1 =" + rs.getBigDecimal("qtyinvoiced") +
                        " where ad_user_id =" + this.getAD_User_ID() +
                        " and m_product_id =" + productID;

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

    /***
     * Actualiza cantidad vendida en los 30 días siguientes a la fecha, en el año anterior.
     * Xpande. Created by Gabriel Vila on 4/17/20.
     * @param productID
     */
    private void updateVentasMesPasado(int productID) {

        String sql = "", action = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{

            Date dateFechaDesdeAux = new Date(this.endDate.getTime());
            Date dateFechaDesde = DateUtils.addDays(dateFechaDesdeAux, -30);
            Timestamp tsFechaDesde = new Timestamp(dateFechaDesde.getTime());

            Date dateFechaHasta = DateUtils.addDays(dateFechaDesde, 29);
            Timestamp tsFechaHasta = new Timestamp(dateFechaHasta.getTime());

            sql = " select sum(qtyinvoiced) as qtyinvoiced " +
                    "from z_bi_vtaproddia " +
                    "where ad_org_id =" + this.adOrgID +
                    "and m_product_id =" + productID +
                    "and dateinvoiced between '" + tsFechaDesde + "' and '" + tsFechaHasta + "' ";

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            if (rs.next()){

                action = " update " + TABLA_REPORTE +
                        " set qty2 =" + rs.getBigDecimal("qtyinvoiced") +
                        " where ad_user_id =" + this.getAD_User_ID() +
                        " and m_product_id =" + productID;

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
