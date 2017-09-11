package org.xpande.retail.utils;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.zkoss.zhtml.Big;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Clase de métodos staticos referidos a funcionalidades comerciales para Retail.
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
    public static BigDecimal getPrecioPromedioVta(Properties ctx, int adOrgID, int mProductID, int cCurrencyID,
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
