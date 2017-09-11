package org.xpande.retail.report;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.xpande.retail.model.X_Z_LineaProductoSocio;
import org.xpande.retail.utils.ComercialUtils;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Reporte de Balance de Servicios.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 9/11/17.
 */
public class BalanceServicios extends SvrProcess {

    private final String TABLA_REPORTE = "Z_RP_Retail_BalServ";

    private int adOrgID = 0;
    private int cBPartnerID = 0;
    private int zProductoSeccionID = 0;
    private int zProductoRubroID = 0;
    private int zProductoFamiliaID = 0;
    private int zProductoSubfamiliaID = 0;
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
                    "z_productoseccion_id, z_productorubro_id, z_productofamilia_id, z_productosubfamilia_id) ";

            // Armo condicion where dinámica del reporte
            String whereClause = "";

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

            sql = " select p.ad_client_id, " + this.adOrgID + ", " + this.getAD_User_ID() + ", "
                    + ((cBPartnerID > 0) ? String.valueOf(this.cBPartnerID) : "null") + ", " +
                    " p.m_product_id, p.value, p.name, '" +  this.startDate + "', p.z_productoseccion_id, p.z_productorubro_id,  " +
                    " p.z_productofamilia_id, p.z_productosubfamilia_id " +
                    " from m_product p " +
                    " where p.issold ='Y' and p.ispurchased ='Y' and p.isactive ='Y' " + whereClause +
                    " order by p.name ";

            DB.executeUpdateEx(action + sql, null);

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

    }


    /***
     * Actualiza campos de las Tablas de Reporte.
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

        	    // Precio Promedio Venta
        	    BigDecimal precioPromedioVta = ComercialUtils.getPrecioPromedioVta(getCtx(), this.getAD_Client_ID(), this.adOrgID,
                        rs.getInt("m_product_id"), this.cCurrencyID, this.startDate, this.endDate, null);

                // Cantidad comprada
                BigDecimal cantComprada = ComercialUtils.getCantidadComprada(getCtx(), this.getAD_Client_ID(), this.adOrgID,
                        rs.getInt("m_product_id"), this.startDate, this.endDate, null);

                // Importe comprado
                BigDecimal amtComprado = ComercialUtils.getImporteComprado(getCtx(), this.getAD_Client_ID(), this.adOrgID,
                        rs.getInt("m_product_id"), cCurrencyID, this.startDate, this.endDate, null);

                // Cantidad vendida
                BigDecimal cantVendida = ComercialUtils.getCantidadVendida(getCtx(), this.getAD_Client_ID(), this.adOrgID,
                        rs.getInt("m_product_id"), this.startDate, this.endDate, null);

                // Importe vendido
                BigDecimal amtVendido = ComercialUtils.getImporteVendido(getCtx(), this.getAD_Client_ID(), this.adOrgID,
                        rs.getInt("m_product_id"), cCurrencyID, this.startDate, this.endDate, null);

                String action = " update " + TABLA_REPORTE +
                        " set PriceSO =" + precioPromedioVta + ", " +
                        " qtypurchased =" + cantComprada + ", " +
                        " amtpurchased =" + amtComprado + ", " +
                        " qtysold =" + cantVendida + ", " +
                        " amtsold =" + amtVendido +
                        " where m_product_id =" + rs.getInt("m_product_id");
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
