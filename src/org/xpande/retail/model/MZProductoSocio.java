package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.xpande.core.utils.CurrencyUtils;
import org.xpande.retail.utils.ProductPricesInfo;
import org.zkoss.zhtml.Big;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

/**
 * Modelo para relación socio de negocio - producto para el modulo de retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/13/17.
 */
public class MZProductoSocio extends X_Z_ProductoSocio {

    public MZProductoSocio(Properties ctx, int Z_ProductoSocio_ID, String trxName) {
        super(ctx, Z_ProductoSocio_ID, trxName);
    }

    public MZProductoSocio(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Obtiene y retorna modelo segun id de socio de negocio y id de producto recibidos.
     * Xpande. Created by Gabriel Vila on 6/13/17.
     * @param ctx
     * @param cBPartnerID
     * @param mProductID
     * @param trxName
     * @return
     */
    public static MZProductoSocio getByBPartnerProduct(Properties ctx, int cBPartnerID, int mProductID, String trxName) {

        String whereClause = X_Z_ProductoSocio.COLUMNNAME_C_BPartner_ID + " =" + cBPartnerID +
                " AND " + X_Z_ProductoSocio.COLUMNNAME_M_Product_ID + " =" + mProductID;

        MZProductoSocio model = new Query(ctx, I_Z_ProductoSocio.Table_Name, whereClause, trxName).first();

        return model;
    }


    /***
     * Obtiene y retorna modelo según id de socio de negocio y codigo de producto del proveedor recibidos.
     * Xpande. Created by Gabriel Vila on 6/25/17.
     * @param ctx
     * @param cBPartnerID
     * @param vendorProductNo
     * @param trxName
     * @return
     */
    public static MZProductoSocio getByBPartnerVendorProdNo(Properties ctx, int cBPartnerID, String vendorProductNo, String trxName) {

        String whereClause = X_Z_ProductoSocio.COLUMNNAME_C_BPartner_ID + " =" + cBPartnerID +
                " AND " + X_Z_ProductoSocio.COLUMNNAME_VendorProductNo + " ='" + vendorProductNo + "'";

        MZProductoSocio model = new Query(ctx, I_Z_ProductoSocio.Table_Name, whereClause, trxName).first();

        return model;
    }


    /***
     * Obtiene y retorna modelo de organización asociada a este producto-socio.
     * Xpande. Created by Gabriel Vila on 6/21/17.
     * @param adOrgTrxID
     * @return
     */
    public MZProductoSocioOrg getOrg(int adOrgTrxID) {

        String whereClause = X_Z_ProductoSocioOrg.COLUMNNAME_Z_ProductoSocio_ID + " =" + this.get_ID() +
                " AND " + X_Z_ProductoSocioOrg.COLUMNNAME_AD_OrgTrx_ID + " =" + adOrgTrxID;

        MZProductoSocioOrg model = new Query(getCtx(), I_Z_ProductoSocioOrg.Table_Name, whereClause, get_TrxName()).first();

        return model;
    }


    /***
     * Obtiene y retorna lista de organizaciones asociadas a este producto-socio.
     * Xpande. Created by Gabriel Vila on 7/2/17.
     * @return
     */
    public List<MZProductoSocioOrg> getOrgs() {

        String whereClause = X_Z_ProductoSocioOrg.COLUMNNAME_Z_ProductoSocio_ID + " =" + this.get_ID();

        List<MZProductoSocioOrg> lines = new Query(getCtx(), I_Z_ProductoSocioOrg.Table_Name, whereClause, get_TrxName()).list();

        return lines;
    }


    /***
     * Obtiene y retorna lista de este modelo para un socio y una linea de productos recibidos.
     * Xpande. Created by Gabriel Vila on 6/22/17.
     * @param ctx
     * @param cBPartnerID
     * @param zLineaProductoSocioID
     * @param mPriceListID
     * @param trxName
     * @return
     */
    public static List<MZProductoSocio> getByBPartnerLineaPriceList(Properties ctx, int cBPartnerID, int zLineaProductoSocioID, int mPriceListID, String trxName) {

        String whereClause = X_Z_ProductoSocio.COLUMNNAME_C_BPartner_ID + " =" + cBPartnerID +
                " AND " + X_Z_ProductoSocio.COLUMNNAME_Z_LineaProductoSocio_ID + " =" + zLineaProductoSocioID +
                " AND " + X_Z_ProductoSocio.COLUMNNAME_M_PriceList_ID + " =" + mPriceListID;

        List<MZProductoSocio> lines = new Query(ctx, I_Z_ProductoSocio.Table_Name, whereClause, trxName).list();

        return lines;
    }


    /***
     * Obtiene y retorna lista de este modelo para un socio y una linea de productos recibidos.
     * Xpande. Created by Gabriel Vila on 6/22/17.
     * @param ctx
     * @param cBPartnerID
     * @param zLineaProductoSocioID
     * @param trxName
     * @return
     */
    public static List<MZProductoSocio> getByBPartnerLinea(Properties ctx, int cBPartnerID, int zLineaProductoSocioID, String trxName) {

        String whereClause = X_Z_ProductoSocio.COLUMNNAME_C_BPartner_ID + " =" + cBPartnerID +
                " AND " + X_Z_ProductoSocio.COLUMNNAME_Z_LineaProductoSocio_ID + " =" + zLineaProductoSocioID;

        List<MZProductoSocio> lines = new Query(ctx, I_Z_ProductoSocio.Table_Name, whereClause, trxName).list();

        return lines;
    }


    /***
     * Obtiene y retorna lista de socios de negocio que comercializan un producto recibido.
     * Xpande. Created by Gabriel Vila on 1/10/18.
     * @param ctx
     * @param mProductID
     * @param trxName
     * @return
     */
    public static List<MZProductoSocio> getByProduct(Properties ctx, int mProductID, String trxName) {

        String whereClause = X_Z_ProductoSocio.COLUMNNAME_M_Product_ID + " =" + mProductID;

        List<MZProductoSocio> lines = new Query(ctx, I_Z_ProductoSocio.Table_Name, whereClause, trxName).list();

        return lines;
    }

    /***
     * Metodo que calcula y setea margenes de esta linea.
     * Xpande. Created by Gabriel Vila on 6/23/17.
     */
    public void calculateMargins() {

        try{

            // Si no tengo nuevo precio de venta, seteo margenes nulos y salgo
            if ((this.getPriceSO() == null) || (this.getPriceSO().compareTo(Env.ZERO) <= 0)){
                this.setPriceFinalMargin(null);
                this.setPricePOMargin(null);
                this.setPriceInvoicedMargin(null);
                return;
            }

            // Si no tengo precio de lista, seteo margenes nulos y salgo
            if ((this.getPriceList() == null) || (this.getPriceList().compareTo(Env.ZERO) <= 0)){
                this.setPriceFinalMargin(null);
                this.setPricePOMargin(null);
                this.setPriceInvoicedMargin(null);
                return;
            }

            Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

            // Tasa de cambio para compra vs venta
            BigDecimal ratePO = Env.ONE;
            if (this.getC_Currency_ID() != this.getC_Currency_ID_SO()){
                ratePO = CurrencyUtils.getCurrencyRate(getCtx(), this.getAD_Client_ID(), 0,
                        this.getC_Currency_ID(), this.getC_Currency_ID_SO(), 114, fechaHoy, null);
                if (ratePO == null){
                    throw new AdempiereException("No hay Tasa de Cambio cargada en el sistema para moneda de compra y fecha de hoy.");
                }
            }

            // Tasa de cambio para ultima factura vs venta
            BigDecimal rateFact = Env.ONE;
            if (this.get_ValueAsInt("C_Currency_1_ID") > 0){
                if (this.get_ValueAsInt("C_Currency_1_ID") != this.getC_Currency_ID_SO()){
                    rateFact = CurrencyUtils.getCurrencyRate(getCtx(), this.getAD_Client_ID(), 0,
                            this.get_ValueAsInt("C_Currency_1_ID"), this.getC_Currency_ID_SO(), 114, fechaHoy, null);
                    if (rateFact == null){
                        throw new AdempiereException("No hay Tasa de Cambio cargada en el sistema para moneda de última factura y fecha de hoy.");
                    }
                }
            }

            // Margen final
            BigDecimal priceFinal = this.getPriceFinal();
            if ((priceFinal == null) || (priceFinal.compareTo(Env.ZERO) <= 0)){
                this.setPriceFinalMargin(null);
            }
            else{
                if ((ratePO != null) && (ratePO.compareTo(Env.ONE) > 0)){
                    priceFinal = priceFinal.multiply(ratePO).setScale(4, BigDecimal.ROUND_HALF_UP);
                }
                this.setPriceFinalMargin(((this.getPriceSO().multiply(Env.ONEHUNDRED).setScale(2, BigDecimal.ROUND_HALF_UP))
                        .divide(priceFinal, 2, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED));
            }

            // Margen OC
            BigDecimal pricePO = this.getPricePO();
            if ((pricePO == null) || (pricePO.compareTo(Env.ZERO) <= 0)){
                this.setPricePOMargin(null);
            }
            else{
                if ((ratePO != null) && (ratePO.compareTo(Env.ONE) > 0)){
                    pricePO = pricePO.multiply(ratePO).setScale(4, BigDecimal.ROUND_HALF_UP);
                }
                this.setPricePOMargin(((this.getPriceSO().multiply(Env.ONEHUNDRED).setScale(2, BigDecimal.ROUND_HALF_UP))
                        .divide(pricePO, 2, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED));
            }

            // Margen Factura
            BigDecimal priceInvoiced = this.getPriceInvoiced();
            if ((priceInvoiced == null) || (priceInvoiced.compareTo(Env.ZERO) <= 0)){
                this.setPriceInvoicedMargin(null);
            }
            else{
                if ((rateFact != null) && (rateFact.compareTo(Env.ONE) > 0)){
                    priceInvoiced = priceInvoiced.multiply(rateFact).setScale(4, BigDecimal.ROUND_HALF_UP);
                }
                this.setPriceInvoicedMargin(((this.getPriceSO().multiply(Env.ONEHUNDRED).setScale(2, BigDecimal.ROUND_HALF_UP))
                        .divide(priceInvoiced, 2, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED));
            }

            this.set_ValueOfColumn("Rate", ratePO);
            this.saveEx();
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

    }

    /***
     * Refresco datos de compras de las organizaciones asociadas a este produto-socio.
     * Xpande. Created by Gabriel Vila on 7/5/17.
     * @param precisionDecimalCompra
     * @param pautaComercial
     */
    public void orgsRefreshPO(int mProductID, int precisionDecimalCompra, MZPautaComercial pautaComercial) {

        try{
            List<MZProductoSocioOrg> productoSocioOrgs = this.getOrgs();
            for (MZProductoSocioOrg productoSocioOrg: productoSocioOrgs){

                ProductPricesInfo ppi = pautaComercial.calculatePrices(mProductID, productoSocioOrg.getPriceList(), precisionDecimalCompra);
                if (ppi != null){

                    // Seteo precios de compra y segmentos si es necesario en producto-socio
                    productoSocioOrg.setPricePO(ppi.getPricePO());
                    productoSocioOrg.setPriceFinal(ppi.getPriceFinal());
                    productoSocioOrg.calculateMargins();
                    productoSocioOrg.saveEx();

                }
                else{
                    throw new AdempiereException("No se pudo Aplicar la Pauta Comercial al producto: " + mProductID + ", organizacion: " + productoSocioOrg.getAD_OrgTrx_ID());
                }
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }


    /***
     * Obtiene y retorna modelo para un determinado producto y ultima factura realizada.
     * Xpande. Created by Gabriel Vila on 7/19/17.
     * @param ctx
     * @param mProductID
     * @param trxName
     * @return
     */
    public static MZProductoSocio getByLastInvoice(Properties ctx, int mProductID, String trxName) {

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        MZProductoSocio productoSocio = null;

        try{
            sql = " select z_productosocio_id " +
                    " from z_productosocio " +
                    " where m_product_id =" + mProductID +
                    " and dateinvoiced is not null " +
                    " order by dateinvoiced desc";

        	pstmt = DB.prepareStatement(sql, trxName);
        	rs = pstmt.executeQuery();

        	if(rs.next()){
        	    productoSocio = new MZProductoSocio(ctx, rs.getInt("z_productosocio_id"), trxName);
        	}
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
        	rs = null; pstmt = null;
        }

        return productoSocio;
    }

    /***
     * Obtiene y retorna modelo para un determinado producto y ultima actualizacion de precio OC.
     * Xpande. Created by Gabriel Vila on 7/19/17.
     * @param ctx
     * @param mProductID
     * @param trxName
     * @return
     */
    public static MZProductoSocio getByLastPriceOC(Properties ctx, int mProductID, String trxName) {

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        MZProductoSocio productoSocio = null;

        try{
            sql = " select z_productosocio_id " +
                    " from z_productosocio " +
                    " where m_product_id =" + mProductID +
                    " and datevalidpo is not null " +
                    " order by datevalidpo desc";

            pstmt = DB.prepareStatement(sql, trxName);
            rs = pstmt.executeQuery();

            if(rs.next()){
                productoSocio = new MZProductoSocio(ctx, rs.getInt("z_productosocio_id"), trxName);
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
            rs = null; pstmt = null;
        }

        return productoSocio;
    }

}
