package org.xpande.retail.report;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.xpande.retail.model.MZPosVendor;
import org.xpande.retail.model.MZPosVendorOrg;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 5/28/19.
 */
public class DetalleTicketPOS extends SvrProcess {

    private final String TABLA_REPORTE = "Z_RP_TicketPOS";

    private int adOrgID = 0;
    private int mProductID = 0;
    private String upc = "";
    private int cCurrencyID = 142;
    private Timestamp startDate = null;
    private Timestamp endDate = null;
    private String nroTicket = null;
    private String nroComprobante = null;
    private String taxID = null;
    private BigDecimal amtDesde = null;
    private BigDecimal amtHasta = null;
    private String codigoCaja = null;
    private String codigoCajero = null;
    private MZPosVendorOrg posVendorOrg = null;
    private MZPosVendor posVendor = null;
    private int adUserID = 0;

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
                    else if (name.trim().equalsIgnoreCase("M_Product_ID")){
                        this.mProductID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("C_Currency_ID")){
                        this.cCurrencyID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("UPC")){
                        this.upc = para[i].getParameter().toString().trim();
                    }
                    else if (name.trim().equalsIgnoreCase("DateTrx")){
                        this.startDate = (Timestamp)para[i].getParameter();
                        this.endDate = (Timestamp)para[i].getParameter_To();
                    }
                    else if (name.trim().equalsIgnoreCase("TotalAmt")){
                        this.amtDesde = (BigDecimal) para[i].getParameter();
                        this.amtHasta = (BigDecimal) para[i].getParameter_To();
                    }
                    else if (name.trim().equalsIgnoreCase("NroTicket")){
                        this.nroTicket = para[i].getParameter().toString().trim();
                    }
                    else if (name.trim().equalsIgnoreCase("TaxID")){
                        this.taxID = para[i].getParameter().toString().trim();
                    }
                    else if (name.trim().equalsIgnoreCase("DocumentNoRef")){
                        this.nroComprobante = para[i].getParameter().toString().trim();
                    }
                    else if (name.trim().equalsIgnoreCase("CodigoCajaPOS")){
                        this.codigoCaja = para[i].getParameter().toString().trim();
                    }
                    else if (name.trim().equalsIgnoreCase("CodigoCajeroPOS")){
                        this.codigoCajero = para[i].getParameter().toString().trim();
                    }

                }
            }
        }

        this.adUserID = this.getAD_User_ID();
        this.posVendorOrg = MZPosVendor.getByOrg(getCtx(), this.adOrgID, null);
        this.posVendor = (MZPosVendor) this.posVendorOrg.getZ_PosVendor();
    }

    @Override
    protected String doIt() throws Exception {

        this.deleteData();

        if (this.posVendor.getValue().equalsIgnoreCase("SISTECO")){
            this.getDataSisteco();
        }
        else if (this.posVendor.getValue().equalsIgnoreCase("SCANNTECH")){

        }

        this.updateData();

        return "OK";
    }


    /***
     * Elimina información anterior para este usuario en tablas de reporte
     * Xpande. Created by Gabriel Vila on 5/28/19.
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
     * Xpande. Created by Gabriel Vila on 5/28/19.
     */
    private void getDataSisteco() {

        String sql = "", action = "";

        try{

            // Cadenas de insert en tablas del reporte
            action = " insert into " + TABLA_REPORTE + " (ad_client_id, ad_org_id, ad_user_id, c_bpartner_id, m_product_id, value, name, datetrx, " +
                    "z_productoseccion_id, z_productorubro_id, z_productofamilia_id, z_productosubfamilia_id, z_productoupc_id, upc) ";

            // Armo condicion where dinámica del reporte
            String whereClause = "";

            sql = " ";

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
