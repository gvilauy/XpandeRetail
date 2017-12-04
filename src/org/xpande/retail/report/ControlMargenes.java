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
 * Xpande. Created by Gabriel Vila on 12/4/17.
 */
public class ControlMargenes extends SvrProcess {

    private final String TABLA_REPORTE = "Z_RP_Retail_CtrlMargen";

    private int adOrgID = 0;
    private int cBPartnerID = 0;
    private int mProductID = 0;
    private int zProductoSeccionID = 0;
    private int zProductoRubroID = 0;
    private int zProductoFamiliaID = 0;
    private int zProductoSubfamiliaID = 0;
    private String estadoMargen = "";

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
                        this.zProductoFamiliaID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("Z_ProductoSubfamilia_ID")){
                        this.zProductoSubfamiliaID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("EstadoMargen")){
                        this.estadoMargen = (String)para[i].getParameter();
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
            action = " insert into " + TABLA_REPORTE + " (ad_client_id, ad_org_id, ad_user_id, c_bpartner_id, m_product_id, pricefinalmargin, margintolerance, margin, estadomargen) ";

            // Armo condicion where dinámica del reporte
            String whereClause = "";

            if (this.adOrgID > 0){
                whereClause += " and ad_orgtrx_id =" + this.adOrgID;
            }

            if (this.cBPartnerID > 0){
                whereClause += " and c_bpartner_id =" + this.cBPartnerID;
            }

            if (this.mProductID > 0){
                whereClause += " and m_product_id =" + this.mProductID;
            }

            // Control contra margenes de Productos
            if (this.estadoMargen.equalsIgnoreCase("RANGO")){

                sql = " select distinct ad_client_id, ad_orgtrx_id, " + this.getAD_User_ID() + ", c_bpartner_id, m_product_id, pricefinalmargin, tol4, margen4, control4 as control " +
                        " from zv_comercial_gralmargen " +
                        " where control1 ='N' and control2 ='N' and control3 ='N' and control4 ='N' AND margen4 is not null";
            }
            else if (this.estadoMargen.equalsIgnoreCase("BAJO")){

                sql = " select distinct ad_client_id, ad_orgtrx_id, " + this.getAD_User_ID() + ", c_bpartner_id, m_product_id, pricefinalmargin, tol4, margen4, control4 as control " +
                        " from zv_comercial_gralmargen " +
                        " where control4 ='YB'";
            }
            else if (this.estadoMargen.equalsIgnoreCase("ALTO")){

                sql = " select distinct ad_client_id, ad_orgtrx_id, " + this.getAD_User_ID() + ", c_bpartner_id, m_product_id, pricefinalmargin, tol4, margen4, control4 as control " +
                        " from zv_comercial_gralmargen " +
                        " where control4 ='YA'";
            }
            else{
                sql = " select distinct ad_client_id, ad_orgtrx_id, " + this.getAD_User_ID() + ", c_bpartner_id, m_product_id, pricefinalmargin, tol4, margen4, control4 as control " +
                        " from zv_comercial_gralmargen " +
                        " where control4 !='N'";
            }
            DB.executeUpdateEx(action + sql, null);

            // Control contra margenes de Segmentos
            if (this.estadoMargen.equalsIgnoreCase("RANGO")){

                sql = " select distinct ad_client_id, ad_orgtrx_id, " + this.getAD_User_ID() + ", c_bpartner_id, m_product_id, pricefinalmargin, tol3, margen3, control3 as control " +
                        " from zv_comercial_gralmargen " +
                        " where control1 ='N' and control2 ='N' and control3 ='N' and control4 ='N' AND margen3 is not null " +
                        " and not exists(select * from " + TABLA_REPORTE + " where ad_org_id = zv_comercial_gralmargen.ad_orgtrx_id " +
                        " and c_bpartner_id = zv_comercial_gralmargen.c_bpartner_id " +
                        " and m_product_id = zv_comercial_gralmargen.m_product_id) " +
                        " and not exists(select * from zv_comercial_gralmargen a where a.ad_orgtrx_id = zv_comercial_gralmargen.ad_orgtrx_id " +
                        " and a.c_bpartner_id = zv_comercial_gralmargen.c_bpartner_id " +
                        " and a.m_product_id = zv_comercial_gralmargen.m_product_id " +
                        " and a.control3 !='N')";
            }
            else if (this.estadoMargen.equalsIgnoreCase("BAJO")){

                sql = " select distinct ad_client_id, ad_orgtrx_id, " + this.getAD_User_ID() + ", c_bpartner_id, m_product_id, pricefinalmargin, tol3, margen3, control3 as control " +
                        " from zv_comercial_gralmargen " +
                        " where control3 ='YB' " +
                        " and not exists(select * from " + TABLA_REPORTE + " where ad_org_id = zv_comercial_gralmargen.ad_orgtrx_id " +
                        " and c_bpartner_id = zv_comercial_gralmargen.c_bpartner_id " +
                        " and m_product_id = zv_comercial_gralmargen.m_product_id)";
            }
            else if (this.estadoMargen.equalsIgnoreCase("ALTO")){

                sql = " select distinct ad_client_id, ad_orgtrx_id, " + this.getAD_User_ID() + ", c_bpartner_id, m_product_id, pricefinalmargin, tol3, margen3, control3 as control " +
                        " from zv_comercial_gralmargen " +
                        " where control3 ='YA' " +
                        " and not exists(select * from " + TABLA_REPORTE + " where ad_org_id = zv_comercial_gralmargen.ad_orgtrx_id " +
                        " and c_bpartner_id = zv_comercial_gralmargen.c_bpartner_id " +
                        " and m_product_id = zv_comercial_gralmargen.m_product_id)";
            }
            else{
                sql = " select distinct ad_client_id, ad_orgtrx_id, " + this.getAD_User_ID() + ", c_bpartner_id, m_product_id, pricefinalmargin, tol3, margen3, control3 as control " +
                        " from zv_comercial_gralmargen " +
                        " where control3 !='N' " +
                        " and not exists(select * from " + TABLA_REPORTE + " where ad_org_id = zv_comercial_gralmargen.ad_orgtrx_id " +
                        " and c_bpartner_id = zv_comercial_gralmargen.c_bpartner_id " +
                        " and m_product_id = zv_comercial_gralmargen.m_product_id)";
            }
            DB.executeUpdateEx(action + sql, null);

            // Control contra margenes de Lineas
            if (this.estadoMargen.equalsIgnoreCase("RANGO")){

                sql = " select distinct ad_client_id, ad_orgtrx_id, " + this.getAD_User_ID() + ", c_bpartner_id, m_product_id, pricefinalmargin, tol2, margen2, control2 as control " +
                        " from zv_comercial_gralmargen " +
                        " where control1 ='N' and control2 ='N' and control3 ='N' and control4 ='N' AND margen2 is not null " +
                        " and not exists(select * from " + TABLA_REPORTE + " where ad_org_id = zv_comercial_gralmargen.ad_orgtrx_id " +
                        " and c_bpartner_id = zv_comercial_gralmargen.c_bpartner_id " +
                        " and m_product_id = zv_comercial_gralmargen.m_product_id) " +
                        " and not exists(select * from zv_comercial_gralmargen a where a.ad_orgtrx_id = zv_comercial_gralmargen.ad_orgtrx_id " +
                        " and a.c_bpartner_id = zv_comercial_gralmargen.c_bpartner_id " +
                        " and a.m_product_id = zv_comercial_gralmargen.m_product_id " +
                        " and a.control2 !='N')";

            }
            else if (this.estadoMargen.equalsIgnoreCase("BAJO")){

                sql = " select distinct ad_client_id, ad_orgtrx_id, " + this.getAD_User_ID() + ", c_bpartner_id, m_product_id, pricefinalmargin, tol2, margen2, control2 as control " +
                        " from zv_comercial_gralmargen " +
                        " where control2 ='YB' " +
                        " and not exists(select * from " + TABLA_REPORTE + " where ad_org_id = zv_comercial_gralmargen.ad_orgtrx_id " +
                        " and c_bpartner_id = zv_comercial_gralmargen.c_bpartner_id " +
                        " and m_product_id = zv_comercial_gralmargen.m_product_id)";
            }
            else if (this.estadoMargen.equalsIgnoreCase("ALTO")){

                sql = " select distinct ad_client_id, ad_orgtrx_id, " + this.getAD_User_ID() + ", c_bpartner_id, m_product_id, pricefinalmargin, tol2, margen2, control2 as control " +
                        " from zv_comercial_gralmargen " +
                        " where control2 ='YA' " +
                        " and not exists(select * from " + TABLA_REPORTE + " where ad_org_id = zv_comercial_gralmargen.ad_orgtrx_id " +
                        " and c_bpartner_id = zv_comercial_gralmargen.c_bpartner_id " +
                        " and m_product_id = zv_comercial_gralmargen.m_product_id)";
            }
            else{
                sql = " select distinct ad_client_id, ad_orgtrx_id, " + this.getAD_User_ID() + ", c_bpartner_id, m_product_id, pricefinalmargin, tol2, margen2, control2 as control " +
                        " from zv_comercial_gralmargen " +
                        " where control2 !='N' " +
                        " and not exists(select * from " + TABLA_REPORTE + " where ad_org_id = zv_comercial_gralmargen.ad_orgtrx_id " +
                        " and c_bpartner_id = zv_comercial_gralmargen.c_bpartner_id " +
                        " and m_product_id = zv_comercial_gralmargen.m_product_id)";
            }
            DB.executeUpdateEx(action + sql, null);

            // Control contra margenes de Proveedores
            if (this.estadoMargen.equalsIgnoreCase("RANGO")){

                sql = " select distinct ad_client_id, ad_orgtrx_id, " + this.getAD_User_ID() + ", c_bpartner_id, m_product_id, pricefinalmargin, tol1, margen1, control1 as control " +
                        " from zv_comercial_gralmargen " +
                        " where control1 ='N' and control2 ='N' and control3 ='N' and control4 ='N' AND margen1 is not null " +
                        " and not exists(select * from " + TABLA_REPORTE + " where ad_org_id = zv_comercial_gralmargen.ad_orgtrx_id " +
                        " and c_bpartner_id = zv_comercial_gralmargen.c_bpartner_id " +
                        " and m_product_id = zv_comercial_gralmargen.m_product_id) " +
                        " and not exists(select * from zv_comercial_gralmargen a where a.ad_orgtrx_id = zv_comercial_gralmargen.ad_orgtrx_id " +
                        " and a.c_bpartner_id = zv_comercial_gralmargen.c_bpartner_id " +
                        " and a.m_product_id = zv_comercial_gralmargen.m_product_id " +
                        " and a.control1 !='N')";

            }
            else if (this.estadoMargen.equalsIgnoreCase("BAJO")){

                sql = " select distinct ad_client_id, ad_orgtrx_id, " + this.getAD_User_ID() + ", c_bpartner_id, m_product_id, pricefinalmargin, tol1, margen1, control1 as control " +
                        " from zv_comercial_gralmargen " +
                        " where control1 ='YB' " +
                        " and not exists(select * from " + TABLA_REPORTE + " where ad_org_id = zv_comercial_gralmargen.ad_orgtrx_id " +
                        " and c_bpartner_id = zv_comercial_gralmargen.c_bpartner_id " +
                        " and m_product_id = zv_comercial_gralmargen.m_product_id)";
            }
            else if (this.estadoMargen.equalsIgnoreCase("ALTO")){

                sql = " select distinct ad_client_id, ad_orgtrx_id, " + this.getAD_User_ID() + ", c_bpartner_id, m_product_id, pricefinalmargin, tol1, margen1, control1 as control " +
                        " from zv_comercial_gralmargen " +
                        " where control1 ='YA' " +
                        " and not exists(select * from " + TABLA_REPORTE + " where ad_org_id = zv_comercial_gralmargen.ad_orgtrx_id " +
                        " and c_bpartner_id = zv_comercial_gralmargen.c_bpartner_id " +
                        " and m_product_id = zv_comercial_gralmargen.m_product_id)";
            }
            else{
                sql = " select distinct ad_client_id, ad_orgtrx_id, " + this.getAD_User_ID() + ", c_bpartner_id, m_product_id, pricefinalmargin, tol1, margen1, control1 as control " +
                        " from zv_comercial_gralmargen " +
                        " where control1 !='N' " +
                        " and not exists(select * from " + TABLA_REPORTE + " where ad_org_id = zv_comercial_gralmargen.ad_orgtrx_id " +
                        " and c_bpartner_id = zv_comercial_gralmargen.c_bpartner_id " +
                        " and m_product_id = zv_comercial_gralmargen.m_product_id)";
            }
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

        String action = "";

        try{

            action = " update " + TABLA_REPORTE + " set estadomargen ='RANGO' " +
                    " where ad_user_id =" + this.getAD_User_ID() +
                    " and estadomargen ='N'";
            DB.executeUpdateEx(action, null);

            action = " update " + TABLA_REPORTE + " set estadomargen ='BAJO' " +
                    " where ad_user_id =" + this.getAD_User_ID() +
                    " and estadomargen ='YB'";
            DB.executeUpdateEx(action, null);

            action = " update " + TABLA_REPORTE + " set estadomargen ='ALTO' " +
                    " where ad_user_id =" + this.getAD_User_ID() +
                    " and estadomargen ='YA'";
            DB.executeUpdateEx(action, null);

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

    }

}
