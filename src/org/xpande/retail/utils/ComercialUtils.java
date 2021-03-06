package org.xpande.retail.utils;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProductPrice;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xpande.core.utils.DateUtils;
import org.xpande.core.utils.PriceListUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Clase de métodos staticos referidos a funcionalidades comerciales.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 9/11/17.
 */
public final class ComercialUtils {


    /***
     * Obtiene y retorna precio promedio de venta de un determinado producto, para una determinada organización, moneda y período.
     * Xpande. Created by Gabriel Vila on 9/11/17.
     * @param ctx
     * @param adOrgID
     * @param mProductID
     * @param cCurrencyID
     * @param startDate
     * @param endDate
     * @return
     */
    public static BigDecimal getPrecioPromedioVta(Properties ctx, int adClientID, int adOrgID, int mProductID, int cCurrencyID,
                                                  Timestamp startDate, Timestamp endDate, String trxName){

        BigDecimal value = Env.ZERO;

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select coalesce(priceso,0) as priceso " +
                    " from z_evolpreciovtaprodorg " +
                    " where m_product_id =" + mProductID +
                    " and ad_orgtrx_id =" + adOrgID +
                    " and datevalidso between ? and ? ";

        	pstmt = DB.prepareStatement(sql, trxName);
        	pstmt.setTimestamp(1, startDate);
            pstmt.setTimestamp(2, endDate);

            int contador = 0;
            BigDecimal amt = Env.ZERO;

            rs = pstmt.executeQuery();

            while(rs.next()){
                contador++;
                amt = amt.add(rs.getBigDecimal("priceso"));
        	}

        	if (amt.compareTo(Env.ZERO) > 0){
                if (contador > 0){
                    value = amt.divide(new BigDecimal(contador), 2, RoundingMode.HALF_UP);
                }
            }
            else{
        	    // No tengo aun datos de evolucion de precios para este producto.
                // Tomo precio de venta vigente en la moneda y organización recibidos.
                sql = " select coalesce(max(pp.pricelist),0) as pricelist " +
                        "from m_productprice pp " +
                        "inner join m_pricelist_version plv on pp.m_pricelist_version_id = plv.m_pricelist_version_id " +
                        "inner join m_pricelist pl on plv.m_pricelist_id = pl.m_pricelist_id " +
                        "where pl.ad_client_id =" + adClientID +
                        "and pl.ad_org_id =" + adOrgID +
                        "and pl.c_currency_id =" + cCurrencyID +
                        "and pl.issopricelist='Y' " +
                        "and pp.m_product_id =" + mProductID;

                value = DB.getSQLValueBDEx(null, sql);
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
        	rs = null; pstmt = null;
        }

        return value;
    }

    /***
     * Obtiene y retorna cantidad comprada de un determinado producto, para una determinada organización y período.
     * @param ctx
     * @param adClientID
     * @param adOrgID
     * @param mProductID
     * @param startDate
     * @param endDate
     * @param trxName
     * @return
     */
    public static BigDecimal getCantidadComprada(Properties ctx, int adClientID, int adOrgID, int mProductID, Timestamp startDate, Timestamp endDate, String trxName){

        BigDecimal value =  Env.ZERO;

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select coalesce(sum(case when doc.docbasetype='API' then l.qtyinvoiced else (l.qtyinvoiced * -1) end),0) as cantidad " +
                    " from c_invoiceline l " +
                    " inner join c_invoice inv on l.c_invoice_id = inv.c_invoice_id" +
                    " inner join c_doctype doc on inv.c_doctypetarget_id = doc.c_doctype_id" +
                    " where inv.docstatus='CO'" +
                    " and inv.issotrx='N'" +
                    " and inv.ad_org_id = " + adOrgID +
                    " and inv.dateinvoiced between ? and ? " +
                    " and l.m_product_id =" + mProductID;

        	pstmt = DB.prepareStatement(sql, trxName);
            pstmt.setTimestamp(1, startDate);
            pstmt.setTimestamp(2, endDate);

            rs = pstmt.executeQuery();

        	if (rs.next()){
               value = rs.getBigDecimal("cantidad");
        	}
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
        	rs = null; pstmt = null;
        }

        return value;

    }


    /***
     * Obtiene y retorna importe comprado de un determinado producto, para una determinada moneda, organización y período.
     * @param ctx
     * @param adClientID
     * @param adOrgID
     * @param mProductID
     * @param startDate
     * @param endDate
     * @param trxName
     * @return
     */
    public static BigDecimal getImporteComprado(Properties ctx, int adClientID, int adOrgID, int mProductID, int cCurrencyID,
                                                Timestamp startDate, Timestamp endDate, String trxName){

        BigDecimal value =  Env.ZERO;

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select coalesce(sum(case when doc.docbasetype='API' then l.amtsubtotal else (l.amtsubtotal * -1) end),0) as importe " +
                    " from c_invoiceline l " +
                    " inner join c_invoice inv on l.c_invoice_id = inv.c_invoice_id" +
                    " inner join c_doctype doc on inv.c_doctypetarget_id = doc.c_doctype_id" +
                    " where inv.docstatus='CO'" +
                    " and inv.issotrx='N'" +
                    " and inv.c_currency_id =" + cCurrencyID +
                    " and inv.ad_org_id = " + adOrgID +
                    " and inv.dateinvoiced between ? and ? " +
                    " and l.m_product_id =" + mProductID;

            pstmt = DB.prepareStatement(sql, trxName);
            pstmt.setTimestamp(1, startDate);
            pstmt.setTimestamp(2, endDate);

            rs = pstmt.executeQuery();

            if (rs.next()){
                value = rs.getBigDecimal("importe");
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
            rs = null; pstmt = null;
        }

        return value;

    }


    /***
     * Obtiene y retorna cantidad vendida de un determinado producto, para una determinada organización y período.
     * @param ctx
     * @param adClientID
     * @param adOrgID
     * @param mProductID
     * @param startDate
     * @param endDate
     * @param trxName
     * @return
     */
    public static BigDecimal getCantidadVendida(Properties ctx, int adClientID, int adOrgID, int mProductID, Timestamp startDate, Timestamp endDate, String trxName){

        BigDecimal value =  Env.ZERO;

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{

            Timestamp fechaConsultaAuxDesde = new Timestamp(startDate.getTime());
            Timestamp fechaConsultaAuxHasta = new Timestamp(endDate.getTime());
            Timestamp fechaDesde = DateUtils.getTSManualHour(fechaConsultaAuxDesde, 0, 0, 0, 0);
            Timestamp fechaHasta = DateUtils.getTSManualHour(fechaConsultaAuxHasta, 23, 59, 59, 0);

            sql = " select coalesce(sum(qtyinvoiced),0) as cantidad " +
                " from z_bi_vtaproddia " +
                " where ad_org_id =" + adOrgID +
                " and dateinvoiced >= ? " +
                " and dateinvoiced <= ? " +
                " and m_product_id =" + mProductID;

            pstmt = DB.prepareStatement(sql, trxName);
            pstmt.setTimestamp(1, fechaDesde);
            pstmt.setTimestamp(2, fechaHasta);

            rs = pstmt.executeQuery();

            if (rs.next()){
                value = rs.getBigDecimal("cantidad");
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
            rs = null; pstmt = null;
        }

        return value;
    }


    /***
     * Obtiene y retorna importe vendido de un determinado producto, para una determinada moneda, organización y período.
     * @param ctx
     * @param adClientID
     * @param adOrgID
     * @param mProductID
     * @param startDate
     * @param endDate
     * @param trxName
     * @return
     */
    public static BigDecimal getImporteVendido(Properties ctx, int adClientID, int adOrgID, int mProductID, int cCurrencyID,
                                                Timestamp startDate, Timestamp endDate, String trxName){

        BigDecimal value =  Env.ZERO;

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{

            Timestamp fechaConsultaAuxDesde = new Timestamp(startDate.getTime());
            Timestamp fechaConsultaAuxHasta = new Timestamp(endDate.getTime());
            Timestamp fechaDesde = DateUtils.getTSManualHour(fechaConsultaAuxDesde, 0, 0, 0, 0);
            Timestamp fechaHasta = DateUtils.getTSManualHour(fechaConsultaAuxHasta, 23, 59, 59, 0);

            sql = " select coalesce(sum(amtsubtotal),0) as importe " +
                    " from z_bi_vtaproddia " +
                    " where ad_org_id =" + adOrgID +
                    " and dateinvoiced >= ? " +
                    " and dateinvoiced <= ? " +
                    " and m_product_id =" + mProductID;

            pstmt = DB.prepareStatement(sql, trxName);
            pstmt.setTimestamp(1, fechaDesde);
            pstmt.setTimestamp(2, fechaHasta);

            rs = pstmt.executeQuery();

            if (rs.next()){
                value = rs.getBigDecimal("importe");
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
            rs = null; pstmt = null;
        }

        return value;

    }

}
