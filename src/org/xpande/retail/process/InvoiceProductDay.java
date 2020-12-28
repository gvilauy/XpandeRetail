package org.xpande.retail.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.xpande.sisteco.model.MZSistecoConfig;
import org.xpande.sisteco.model.MZSistecoConfigOrg;
import org.xpande.stech.model.MZScanntechConfig;
import org.xpande.stech.model.MZScanntechConfigOrg;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;

/**
 * Proceso para consolidar información de venta y compra de un determinado producto, para una determinada organización,
 * en un determinado día.
 * Los datos de compra salen de ADempiere desde invoices.
 * Los datos de venta salen de las interfaces de venta de los diferentes POS.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 12/16/20.
 */
public class InvoiceProductDay extends SvrProcess {

    private Timestamp startDate = null;
    private Timestamp endDate = null;
    private int adOrgID = 0;
    private int adClientID = 1000000;
    private boolean procesarCompras = true;
    private boolean procesarVentas = true;

    @Override
    protected void prepare() {

        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){

            String name = para[i].getParameterName();

            if (name != null){
                if (para[i].getParameter() != null){
                    if (name.trim().equalsIgnoreCase("DateTrx")){
                        this.startDate = (Timestamp)para[i].getParameter();
                        this.endDate = (Timestamp)para[i].getParameter_To();
                    }
                    else if (name.trim().equalsIgnoreCase("AD_Org_ID")){
                        this.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("IsPurchased")) {
                        this.procesarCompras = (((String) para[i].getParameter()).trim().equalsIgnoreCase("Y")) ? true : false;
                    }
                    else if (name.trim().equalsIgnoreCase("IsSold")) {
                        this.procesarVentas = (((String) para[i].getParameter()).trim().equalsIgnoreCase("Y")) ? true : false;
                    }
                }
            }
        }

    }

    @Override
    protected String doIt() throws Exception {

        // Proceso información de compras desde ADempiere
        if (this.procesarCompras){
            this.getComprasAdempiere();
        }

        // Proceso informacuón de venteas desde interfaces a los distintos POS
        if (this.procesarVentas){
            this.getVentasPOS();
        }

        return "OK";
    }

    /***
     * Obtiene información de compras generadas en ADempiere.
     * Xpande. Created by Gabriel Vila on 12/16/20.
     */
    private void getComprasAdempiere(){

        String sql, insert, action;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{

            insert = " insert into z_bi_invprodday (ad_client_id, ad_org_id, dateinvoiced, c_currency_id, m_product_id, " +
                    " c_uom_id, qtypurchased, qtysold, amtsubtotal, totalamt, amtsubtotalpo, totalamtpo, z_bi_dia_id) ";

            MZScanntechConfig scanntechConfig = MZScanntechConfig.getDefault(getCtx(), null);

            // Si indico organización, proceso solo para esta, sino proceso para todas las que tenga asociadas al proveedor de POS
            List<MZScanntechConfigOrg> orgList = scanntechConfig.getOrganizationsByOrg(this.adOrgID);

            for (MZScanntechConfigOrg configOrg: orgList){

                sql = " select inv.ad_org_id, inv.dateinvoiced, dimday.z_bi_dia_id, inv.c_currency_id, l.m_product_id, prod.c_uom_id, " +
                        " coalesce(sum(case when doc.docbasetype='API' then l.qtyinvoiced else (l.qtyinvoiced * -1) end),0) as cantidad, " +
                        " coalesce(sum(case when doc.docbasetype='API' then l.amtsubtotal else (l.amtsubtotal * -1) end),0) as subtotal, " +
                        " coalesce(sum(case when doc.docbasetype='API' then l.linetotalamt else (l.linetotalamt * -1) end),0) as total " +
                        " from c_invoiceline l " +
                        " inner join c_invoice inv on l.c_invoice_id = inv.c_invoice_id " +
                        " inner join c_doctype doc on inv.c_doctypetarget_id = doc.c_doctype_id " +
                        " inner join m_product prod on l.m_product_id = prod.m_product_id " +
                        " left outer join z_bi_dia dimday on inv.dateinvoiced = dimday.datetrx " +
                        " where inv.docstatus='CO' " +
                        " and inv.ad_org_id =" + configOrg.getAD_OrgTrx_ID() +
                        " and inv.issotrx='N' " +
                        " and inv.dateinvoiced between '" + this.startDate + "' " +
                        " and '" + this.endDate + "' " +
                        " group by inv.ad_org_id, inv.dateinvoiced, dimday.z_bi_dia_id, inv.c_currency_id, l.m_product_id, prod.c_uom_id ";

                pstmt = DB.prepareStatement(sql, null);
                rs = pstmt.executeQuery();

                while(rs.next()){

                    // Verifico si no existe un registro para esta clave en la tabla de analisis
                    sql = " select count(*) from z_bi_invprodday " +
                            " where ad_org_id =" + rs.getInt("ad_org_id") +
                            " and m_product_id =" + rs.getInt("m_product_id") +
                            " and c_currency_id =" + rs.getInt("c_currency_id") +
                            " and dateinvoiced ='" + rs.getTimestamp("dateinvoiced") + "' ";
                    int contador = DB.getSQLValueEx(null, sql);

                    // Si no existe aún un registro para esta clave
                    if (contador <= 0){

                        // Insert
                        action = " values (" + this.adClientID + ", " + rs.getInt("ad_org_id") + ", '" +
                                rs.getTimestamp("dateinvoiced") + "', " + rs.getInt("c_currency_id") + ", " +
                                rs.getInt("m_product_id") + ", " + rs.getInt("c_uom_id") + ", " +
                                rs.getBigDecimal("cantidad") + ", 0, 0, 0, " + rs.getBigDecimal("subtotal") + ", " +
                                rs.getBigDecimal("total") + ", " + rs.getInt("z_bi_dia_id") + ") ";

                        DB.executeUpdateEx(insert + action, null);
                    }
                    else {
                        // Actualizo
                        action = " update z_bi_invprodday set " +
                                " qtypurchased = qtypurchased + " + rs.getBigDecimal("cantidad") + ", " +
                                " amtsubtotalpo = amtsubtotalpo + " + rs.getBigDecimal("subtotal") + ", " +
                                " totalamtpo = totalamtpo + " + rs.getBigDecimal("total") +
                                " where ad_org_id =" + rs.getInt("ad_org_id") +
                                " and m_product_id =" + rs.getInt("m_product_id") +
                                " and c_currency_id =" + rs.getInt("c_currency_id") +
                                " and dateinvoiced ='" + rs.getTimestamp("dateinvoiced") + "' ";

                        DB.executeUpdateEx(action, null);
                    }

                }

                DB.close(rs, pstmt);
                rs = null; pstmt = null;

            }

            MZSistecoConfig sistecoConfig = MZSistecoConfig.getDefault(getCtx(), null);

            // Si indico organización, proceso solo para esta, sino proceso para todas las que tenga asociadas a SISTECO
            List<MZSistecoConfigOrg> orgListSist = sistecoConfig.getOrganizationsByOrg(this.adOrgID);

            for (MZSistecoConfigOrg configOrgSist: orgListSist){

                sql = " select inv.ad_org_id, inv.dateinvoiced, dimday.z_bi_dia_id, inv.c_currency_id, l.m_product_id, prod.c_uom_id, " +
                        " coalesce(sum(case when doc.docbasetype='API' then l.qtyinvoiced else (l.qtyinvoiced * -1) end),0) as cantidad, " +
                        " coalesce(sum(case when doc.docbasetype='API' then l.amtsubtotal else (l.amtsubtotal * -1) end),0) as subtotal, " +
                        " coalesce(sum(case when doc.docbasetype='API' then l.linetotalamt else (l.linetotalamt * -1) end),0) as total " +
                        " from c_invoiceline l " +
                        " inner join c_invoice inv on l.c_invoice_id = inv.c_invoice_id " +
                        " inner join c_doctype doc on inv.c_doctypetarget_id = doc.c_doctype_id " +
                        " inner join m_product prod on l.m_product_id = prod.m_product_id " +
                        " left outer join z_bi_dia dimday on inv.dateinvoiced = dimday.datetrx " +
                        " where inv.docstatus='CO' " +
                        " and inv.ad_org_id =" + configOrgSist.getAD_OrgTrx_ID() +
                        " and inv.issotrx='N' " +
                        " and inv.dateinvoiced between '" + this.startDate + "' " +
                        " and '" + this.endDate + "' " +
                        " group by inv.ad_org_id, inv.dateinvoiced, dimday.z_bi_dia_id, inv.c_currency_id, l.m_product_id, prod.c_uom_id ";

                pstmt = DB.prepareStatement(sql, null);
                rs = pstmt.executeQuery();

                while(rs.next()){

                    // Verifico si no existe un registro para esta clave en la tabla de analisis
                    sql = " select count(*) from z_bi_invprodday " +
                            " where ad_org_id =" + rs.getInt("ad_org_id") +
                            " and m_product_id =" + rs.getInt("m_product_id") +
                            " and c_currency_id =" + rs.getInt("c_currency_id") +
                            " and dateinvoiced ='" + rs.getTimestamp("dateinvoiced") + "' ";
                    int contador = DB.getSQLValueEx(null, sql);

                    // Si no existe aún un registro para esta clave
                    if (contador <= 0){

                        // Insert
                        action = " values (" + this.adClientID + ", " + rs.getInt("ad_org_id") + ", '" +
                                rs.getTimestamp("dateinvoiced") + "', " + rs.getInt("c_currency_id") + ", " +
                                rs.getInt("m_product_id") + ", " + rs.getInt("c_uom_id") + ", " +
                                rs.getBigDecimal("cantidad") + ", 0, 0, 0, " + rs.getBigDecimal("subtotal") + ", " +
                                rs.getBigDecimal("total") + ", " + rs.getInt("z_bi_dia_id") + ") ";

                        DB.executeUpdateEx(insert + action, null);
                    }
                    else {
                        // Actualizo
                        action = " update z_bi_invprodday set " +
                                " qtypurchased = qtypurchased + " + rs.getBigDecimal("cantidad") + ", " +
                                " amtsubtotalpo = amtsubtotalpo + " + rs.getBigDecimal("subtotal") + ", " +
                                " totalamtpo = totalamtpo + " + rs.getBigDecimal("total") +
                                " where ad_org_id =" + rs.getInt("ad_org_id") +
                                " and m_product_id =" + rs.getInt("m_product_id") +
                                " and c_currency_id =" + rs.getInt("c_currency_id") +
                                " and dateinvoiced ='" + rs.getTimestamp("dateinvoiced") + "' ";

                        DB.executeUpdateEx(action, null);
                    }

                }

                DB.close(rs, pstmt);
                rs = null; pstmt = null;

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
     * Obtiene información de ventas desde interfaces a los diferentes POS.
     * Xpande. Created by Gabriel Vila on 12/16/20.
     */
    private void getVentasPOS() {

        try{

            // Proceso organizaciones de Scanntech
            this.getVentasScanntech();

            // Proceso organizaciones de Sisteco
            this.getVentasSisteco();

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

    private void getVentasScanntech() {

        String sql, action;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{

            String insert = " insert into z_bi_invprodday (ad_client_id, ad_org_id, dateinvoiced, c_currency_id, m_product_id, " +
                    " c_uom_id, qtysold, amtsubtotal, totalamt, qtypurchased, amtsubtotalpo, totalamtpo, z_bi_dia_id) ";

            MZScanntechConfig scanntechConfig = MZScanntechConfig.getDefault(getCtx(), null);

            // Si indico organización, proceso solo para esta, sino proceso para todas las que tenga asociadas al proveedor de POS
            List<MZScanntechConfigOrg> orgList = scanntechConfig.getOrganizationsByOrg(this.adOrgID);

            for (MZScanntechConfigOrg configOrg: orgList){

                // Cupones no cancelados
                sql = " select a.ad_org_id, a.datetrx, dimday.z_bi_dia_id, mov.sc_codigomoneda, coalesce(mdet.m_product_id, " +
                        " upc.m_product_id) as m_product_id, prod.c_uom_id, " +
                        " sum(mdet.sc_cantidad) as cantidad, sum(mdet.sc_importe - mdet.sc_montoiva) as subtotal, " +
                        " sum(mdet.sc_importe) as total " +
                        " from z_stechinterfacevta a " +
                        " inner join z_stech_tk_mov mov on a.z_stechinterfacevta_id = mov.z_stechinterfacevta_id " +
                        " inner join z_stech_tk_movdet mdet on mov.z_stech_tk_mov_id = mdet.z_stech_tk_mov_id " +
                        " left outer join m_product prod on mdet.m_product_id = prod.m_product_id " +
                        " left outer join z_bi_dia dimday on a.datetrx = dimday.datetrx " +
                        " left outer join z_productoupc upc on mdet.sc_codigobarras = upc.upc " +
                        " where a.ad_org_id =" + configOrg.getAD_OrgTrx_ID() +
                        " and a.datetrx between '" + this.startDate + "' and '" + this.endDate + "' " +
                        " and mov.sc_cuponcancelado ='N' " +
                        " and mov.sc_cuponanulada ='N'" +
                        " group by 1,2,3,4,5,6";

                pstmt = DB.prepareStatement(sql, get_TrxName());
                rs = pstmt.executeQuery();

                while(rs.next()){

                    int currencyID = 142;
                    if (rs.getString("sc_codigomoneda") != null){
                        if (!rs.getString("sc_codigomoneda").equalsIgnoreCase("858")){
                            currencyID = 100;
                        }
                    }

                    // Verifico si no existe un registro para esta clave en la tabla de analisis
                    sql = " select count(*) from z_bi_invprodday " +
                            " where ad_org_id =" + rs.getInt("ad_org_id") +
                            " and m_product_id =" + rs.getInt("m_product_id") +
                            " and c_currency_id =" + currencyID +
                            " and dateinvoiced ='" + rs.getTimestamp("datetrx") + "' ";
                    int contador = DB.getSQLValueEx(null, sql);

                    // Si no existe aún un registro para esta clave
                    if (contador <= 0){

                        // Insert
                        action = " values (" + this.adClientID + ", " + rs.getInt("ad_org_id") + ", '" +
                                rs.getTimestamp("datetrx") + "', " + currencyID + ", " +
                                rs.getInt("m_product_id") + ", " + rs.getInt("c_uom_id") + ", " +
                                rs.getBigDecimal("cantidad") + ", " + rs.getBigDecimal("subtotal") + ", " +
                                rs.getBigDecimal("total") + ", 0, 0, 0, " + rs.getInt("z_bi_dia_id") + ") ";

                        DB.executeUpdateEx(insert + action, null);

                    }
                    else {
                        // Actualizo
                        action = " update z_bi_invprodday set " +
                                " qtysold = qtysold + " + rs.getBigDecimal("cantidad") + ", " +
                                " amtsubtotal = amtsubtotal + " + rs.getBigDecimal("subtotal") + ", " +
                                " totalamt = totalamt + " + rs.getBigDecimal("total") +
                                " where ad_org_id =" + rs.getInt("ad_org_id") +
                                " and m_product_id =" + rs.getInt("m_product_id") +
                                " and c_currency_id =" + currencyID +
                                " and dateinvoiced ='" + rs.getTimestamp("datetrx") + "' ";

                        DB.executeUpdateEx(action, null);
                    }


                }
                DB.close(rs, pstmt);
                rs = null; pstmt = null;


                // Cupones cancelados
                sql = " select a.ad_org_id, a.datetrx, dimday.z_bi_dia_id, mov.sc_codigomoneda, coalesce(mdet.m_product_id, " +
                        " upc.m_product_id) as m_product_id, prod.c_uom_id, " +
                        " sum(mdet.sc_cantidad) as cantidad, sum(mdet.sc_importe - mdet.sc_montoiva) as subtotal, " +
                        " sum(mdet.sc_importe) as total " +
                        " from z_stechinterfacevta a " +
                        " inner join z_stech_tk_mov mov on a.z_stechinterfacevta_id = mov.z_stechinterfacevta_id " +
                        " inner join z_stech_tk_movdet mdet on mov.z_stech_tk_mov_id = mdet.z_stech_tk_mov_id " +
                        " left outer join m_product prod on mdet.m_product_id = prod.m_product_id " +
                        " left outer join z_bi_dia dimday on a.datetrx = dimday.datetrx " +
                        " left outer join z_productoupc upc on mdet.sc_codigobarras = upc.upc " +
                        " where a.ad_org_id =" + configOrg.getAD_OrgTrx_ID() +
                        " and a.datetrx between '" + this.startDate + "' and '" + this.endDate + "' " +
                        " and mov.sc_cuponcancelado ='Y' " +
                        " group by 1,2,3,4,5,6";

                pstmt = DB.prepareStatement(sql, get_TrxName());
                rs = pstmt.executeQuery();

                while(rs.next()){

                    int currencyID = 142;
                    if (rs.getString("sc_codigomoneda") != null){
                        if (!rs.getString("sc_codigomoneda").equalsIgnoreCase("858")){
                            currencyID = 100;
                        }
                    }

                    // Verifico si no existe un registro para esta clave en la tabla de analisis
                    sql = " select count(*) from z_bi_invprodday " +
                            " where ad_org_id =" + rs.getInt("ad_org_id") +
                            " and m_product_id =" + rs.getInt("m_product_id") +
                            " and c_currency_id =" + currencyID +
                            " and dateinvoiced ='" + rs.getTimestamp("datetrx") + "' ";
                    int contador = DB.getSQLValueEx(null, sql);

                    // Si no existe aún un registro para esta clave
                    if (contador <= 0){

                        // Insert
                        action = " values (" + this.adClientID + ", " + rs.getInt("ad_org_id") + ", '" +
                                rs.getTimestamp("datetrx") + "', " + currencyID + ", " +
                                rs.getInt("m_product_id") + ", " + rs.getInt("c_uom_id") + ", " +
                                rs.getBigDecimal("cantidad") + ", " + rs.getBigDecimal("subtotal") + ", " +
                                rs.getBigDecimal("total") + ", 0, 0, 0, " + rs.getInt("z_bi_dia_id") + ") ";

                        DB.executeUpdateEx(insert + action, null);
                    }
                    else {
                        // Actualizo
                        action = " update z_bi_invprodday set " +
                                " qtysold = qtysold + " + rs.getBigDecimal("cantidad") + ", " +
                                " amtsubtotal = amtsubtotal + " + rs.getBigDecimal("subtotal") + ", " +
                                " totalamt = totalamt + " + rs.getBigDecimal("total") +
                                " where ad_org_id =" + rs.getInt("ad_org_id") +
                                " and m_product_id =" + rs.getInt("m_product_id") +
                                " and c_currency_id =" + currencyID +
                                " and dateinvoiced ='" + rs.getTimestamp("datetrx") + "' ";

                        DB.executeUpdateEx(action, null);
                    }


                }
                DB.close(rs, pstmt);
                rs = null; pstmt = null;

            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
        	rs = null; pstmt = null;
        }

        try{


        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

    private void getVentasSisteco() {

        String sql, action;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{

            String insert = " insert into z_bi_invprodday (ad_client_id, ad_org_id, dateinvoiced, c_currency_id, m_product_id, " +
                    " c_uom_id, qtysold, amtsubtotal, totalamt, qtypurchased, amtsubtotalpo, totalamtpo, z_bi_dia_id) ";

            MZSistecoConfig sistecoConfig = MZSistecoConfig.getDefault(getCtx(), null);

            // Si indico organización, proceso solo para esta, sino proceso para todas las que tenga asociadas a SISTECO
            List<MZSistecoConfigOrg> orgList = sistecoConfig.getOrganizationsByOrg(this.adOrgID);

            for (MZSistecoConfigOrg configOrg: orgList){

                // Ventas
                sql = " select a.ad_org_id, a.datetrx, dimday.z_bi_dia_id, 142::numeric(10,0) as c_currency_id, " +
                        " coalesce(l.m_product_id, upc.m_product_id) as m_product_id, prod.c_uom_id, " +
                        " sum(l.st_cantidad) as cantidad, sum(l.st_preciodescuentototal) as subtotal, " +
                        " sum(round(l.st_cantidad * l.st_preciounitarioconiva,2)) as total " +
                        " from z_sistecointerfacepazos a " +
                        " inner join z_sisteco_tk_lvta l on a.z_sistecointerfacepazos_id = l.z_sistecointerfacepazos_id " +
                        " left outer join m_product prod on l.m_product_id = prod.m_product_id " +
                        " left outer join z_bi_dia dimday on a.datetrx = dimday.datetrx " +
                        " left outer join z_productoupc upc on l.st_codigoarticulooriginal = upc.upc " +
                        " where a.ad_org_id =" + configOrg.getAD_OrgTrx_ID() +
                        " and a.datetrx between '" + this.startDate + "' and '" + this.endDate + "' " +
                        " and l.st_lineacancelada = 0 " +
                        " and ((l.st_siesobsequio is null) or (l.st_siesobsequio='0')) " +
                        " group by 1,2,3,4,5,6 ";

                pstmt = DB.prepareStatement(sql, get_TrxName());
                rs = pstmt.executeQuery();

                while(rs.next()){

                    // Verifico si no existe un registro para esta clave en la tabla de analisis
                    sql = " select count(*) from z_bi_invprodday " +
                            " where ad_org_id =" + rs.getInt("ad_org_id") +
                            " and m_product_id =" + rs.getInt("m_product_id") +
                            " and c_currency_id =" + rs.getInt("c_currency_id") +
                            " and dateinvoiced ='" + rs.getTimestamp("datetrx") + "' ";
                    int contador = DB.getSQLValueEx(null, sql);

                    // Si no existe aún un registro para esta clave
                    if (contador <= 0){

                        // Insert
                        action = " values (" + this.adClientID + ", " + rs.getInt("ad_org_id") + ", '" +
                                rs.getTimestamp("datetrx") + "', " + rs.getInt("c_currency_id") + ", " +
                                rs.getInt("m_product_id") + ", " + rs.getInt("c_uom_id") + ", " +
                                rs.getBigDecimal("cantidad") + ", " + rs.getBigDecimal("subtotal") + ", " +
                                rs.getBigDecimal("total") + ", 0, 0, 0, " + rs.getInt("z_bi_dia_id") + ") ";

                        DB.executeUpdateEx(insert + action, null);
                    }
                    else {
                        // Actualizo
                        action = " update z_bi_invprodday set " +
                                " qtysold = qtysold + " + rs.getBigDecimal("cantidad") + ", " +
                                " amtsubtotal = amtsubtotal + " + rs.getBigDecimal("subtotal") + ", " +
                                " totalamt = totalamt + " + rs.getBigDecimal("total") +
                                " where ad_org_id =" + rs.getInt("ad_org_id") +
                                " and m_product_id =" + rs.getInt("m_product_id") +
                                " and c_currency_id =" + rs.getInt("c_currency_id") +
                                " and dateinvoiced ='" + rs.getTimestamp("datetrx") + "' ";

                        DB.executeUpdateEx(action, null);
                    }


                }
                DB.close(rs, pstmt);
                rs = null; pstmt = null;

                // Devoluciones
                sql = " select a.ad_org_id, a.datetrx, dimday.z_bi_dia_id, 142::numeric(10,0) as c_currency_id, " +
                        " l.m_product_id, prod.c_uom_id, " +
                        " sum(l.st_cantidad * -1) as cantidad, sum(l.st_precio) as subtotal, " +
                        " sum(round(l.st_iva + l.st_precio,2)) as total " +
                        " from z_sistecointerfacepazos a " +
                        " inner join z_sisteco_tk_ldev l on a.z_sistecointerfacepazos_id = l.z_sistecointerfacepazos_id " +
                        " left outer join m_product prod on l.m_product_id = prod.m_product_id " +
                        " left outer join z_bi_dia dimday on a.datetrx = dimday.datetrx " +
                        " where a.ad_org_id =" + configOrg.getAD_OrgTrx_ID() +
                        " and a.datetrx between '" + this.startDate + "' and '" + this.endDate + "' " +
                        " and l.st_lineacancelada = 0 " +
                        " and l.m_product_id is not null " +
                        " group by 1,2,3,4,5,6 ";

                pstmt = DB.prepareStatement(sql, get_TrxName());
                rs = pstmt.executeQuery();

                while(rs.next()){

                    // Verifico si no existe un registro para esta clave en la tabla de analisis
                    sql = " select count(*) from z_bi_invprodday " +
                            " where ad_org_id =" + rs.getInt("ad_org_id") +
                            " and m_product_id =" + rs.getInt("m_product_id") +
                            " and c_currency_id =" + rs.getInt("c_currency_id") +
                            " and dateinvoiced ='" + rs.getTimestamp("datetrx") + "' ";
                    int contador = DB.getSQLValueEx(null, sql);

                    // Si no existe aún un registro para esta clave
                    if (contador <= 0){

                        // Insert
                        action = " values (" + this.adClientID + ", " + rs.getInt("ad_org_id") + ", '" +
                                rs.getTimestamp("datetrx") + "', " + rs.getInt("c_currency_id") + ", " +
                                rs.getInt("m_product_id") + ", " + rs.getInt("c_uom_id") + ", " +
                                rs.getBigDecimal("cantidad") + ", " + rs.getBigDecimal("subtotal") + ", " +
                                rs.getBigDecimal("total") + ", 0, 0, 0, " + rs.getInt("z_bi_dia_id") + ") ";

                        DB.executeUpdateEx(insert + action, null);
                    }
                    else {
                        // Actualizo
                        action = " update z_bi_invprodday set " +
                                " qtysold = qtysold + " + rs.getBigDecimal("cantidad") + ", " +
                                " amtsubtotal = amtsubtotal + " + rs.getBigDecimal("subtotal") + ", " +
                                " totalamt = totalamt + " + rs.getBigDecimal("total") +
                                " where ad_org_id =" + rs.getInt("ad_org_id") +
                                " and m_product_id =" + rs.getInt("m_product_id") +
                                " and c_currency_id =" + rs.getInt("c_currency_id") +
                                " and dateinvoiced ='" + rs.getTimestamp("datetrx") + "' ";

                        DB.executeUpdateEx(action, null);
                    }
                }
                DB.close(rs, pstmt);
                rs = null; pstmt = null;

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
