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

                /*

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
