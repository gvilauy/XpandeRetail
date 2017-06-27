package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
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

            // Margen final
            if ((this.getPriceFinal() == null) || (this.getPriceFinal().compareTo(Env.ZERO) <= 0)){
                this.setPriceFinalMargin(null);
            }
            else{
                this.setPriceFinalMargin(((this.getPriceSO().multiply(Env.ONEHUNDRED).setScale(2, BigDecimal.ROUND_HALF_UP))
                        .divide(this.getPriceFinal(), 2, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED));
            }

            // Margen OC
            if ((this.getPricePO() == null) || (this.getPricePO().compareTo(Env.ZERO) <= 0)){
                this.setPricePOMargin(null);
            }
            else{
                this.setPricePOMargin(((this.getPriceSO().multiply(Env.ONEHUNDRED).setScale(2, BigDecimal.ROUND_HALF_UP))
                        .divide(this.getPricePO(), 2, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED));
            }

            // Margen Factura
            if ((this.getPriceInvoiced() == null) || (this.getPriceInvoiced().compareTo(Env.ZERO) <= 0)){
                this.setPriceInvoicedMargin(null);
            }
            else{
                this.setPriceInvoicedMargin(((this.getPriceSO().multiply(Env.ONEHUNDRED).setScale(2, BigDecimal.ROUND_HALF_UP))
                        .divide(this.getPriceInvoiced(), 2, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED));
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

    }
}
