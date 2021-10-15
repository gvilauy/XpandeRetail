package org.xpande.retail.report;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 9/2/21.
 */
public class SumVtaTaxProd extends SvrProcess {

    String REPORT_TABLE = "Z_RP_SumVtaTaxProd";
    private int adOrgID = -1;
    private Timestamp startDate = null;
    private Timestamp endDate = null;
    private int zSeccionID = -1;
    private int zRubroID = -1;

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
                        this.zSeccionID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("Z_ProductoRubro_ID")){
                        this.zRubroID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("DateInvoiced")){
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

        return "OK";
    }

    /***
     * Elimina información anterior para este usuario en tablas de reporte
     * Xpande. Created by Gabriel Vila on 4/16/20.
     */
    private void deleteData() {

        try{
            String action = " delete from " + REPORT_TABLE + " where ad_user_id =" + this.getAD_User_ID();
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
            action = " insert into " + REPORT_TABLE + " (ad_client_id, ad_org_id, ad_user_id, dateinvoiced, " +
                    " z_productoseccion_id, z_productorubro_id, c_tax_id, c_currency_id, amtsubtotal, taxamt) ";

            // Armo condicion where dinámica del reporte
            String whereClause = "";

            if (this.zSeccionID > 0){
                whereClause += " and p.z_productoseccion_id =" + this.zSeccionID;
            }
            if (this.zRubroID > 0){
                whereClause += " and p.z_productorubro_id =" + this.zRubroID;
            }
            sql = " select a.ad_client_id, a.ad_org_id, " + this.getAD_User_ID() + ", cast('" + this.endDate + "' as timestamp without time zone), " +
                    " p.z_productoseccion_id, p.z_productorubro_id, " +
                    " t.c_tax_id, a.c_currency_id, sum(amtsubtotal) as amtsubtotal, sum(taxamt) as taxamt " +
                    " from z_bi_vtapos a " +
                    " inner join m_product p on a.m_product_id = p.m_product_id " +
                    " inner join c_tax t on (a.c_taxcategory_id = t.c_taxcategory_id and t.isdefault='Y') " +
                    " where a.ad_org_id =" + this.adOrgID +
                    " and (a.dateinvoiced >='" + this.startDate + "' and a.dateinvoiced <='" + this.endDate + "') " +
                    whereClause +
                    " group by 1,2,3,4,5,6,7,8  ";

            DB.executeUpdateEx(action + sql, null);
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }
}
