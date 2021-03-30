package org.xpande.retail.callout;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MProduct;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.xpande.core.model.MZProductoUPC;
import org.xpande.retail.model.MZProductoSocio;
import org.zkoss.zhtml.Big;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Callouts para entregas/rececpiones/devoluciones en el módulo de retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/4/17.
 */
public class CalloutInOut extends CalloutEngine {

    /***
     * Al ingresar código de barras, o codigo de producto del proveedor, o el producto directamente,
     * se deben setear los otros dos valores asociados.
     * Xpande. Created by Gabriel Vila on 6/25/17.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String upcVendProdNoProduct(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (isCalloutActive()) return "";

        if ((value == null) || (value.toString().trim().equalsIgnoreCase(""))){
            mTab.setValue("UPC", null);
            mTab.setValue("VendorProductNo", null);
            mTab.setValue("M_Product_ID", null);
            return "";
        }

        int cBPartnerID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_ID");
        String column = mField.getColumnName();

        if (column.equalsIgnoreCase("UPC")){
            MZProductoUPC pupc = MZProductoUPC.getByUPC(ctx, value.toString().trim(), null);
            if ((pupc != null) && (pupc.get_ID() > 0)){
                MProduct prod = (MProduct) pupc.getM_Product();
                MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(ctx, cBPartnerID, prod.get_ID(), null);
                if ((productoSocio == null) || (productoSocio.get_ID() <= 0)){
                    mTab.setValue("VendorProductNo", null);
                    mTab.setValue("M_Product_ID", null);
                    mTab.fireDataStatusEEvent ("Error", "El código de barras ingresado no pertenece a un Producto de este Socio de Negocio.", true);
                }
                else{
                    if (productoSocio.getVendorProductNo() != null){
                        if (!productoSocio.getVendorProductNo().trim().equalsIgnoreCase("")){
                            mTab.setValue("VendorProductNo", productoSocio.getVendorProductNo().trim());
                        }
                    }
                    mTab.setValue("M_Product_ID", prod.get_ID());
                }
            }
        }
        else if (column.equalsIgnoreCase("VendorProductNo")){
            MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerVendorProdNo(ctx, cBPartnerID, value.toString().trim(), null);
            if ((productoSocio != null) && (productoSocio.get_ID() > 0)){
                MProduct prod = (MProduct) productoSocio.getM_Product();
                MZProductoUPC pupc = MZProductoUPC.getByProduct(ctx, prod.get_ID(), null);
                if ((pupc != null) & (pupc.get_ID() > 0)){
                    mTab.setValue("UPC", pupc.getUPC());
                }
                mTab.setValue("M_Product_ID", prod.get_ID());
            }
        }
        else if (column.equalsIgnoreCase("M_Product_ID")){
            int mProductID = ((Integer) value).intValue();
            MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(ctx, cBPartnerID, mProductID, null);
            if ((productoSocio != null) && (productoSocio.get_ID() > 0)){
                if (productoSocio.getVendorProductNo() != null){
                    if (!productoSocio.getVendorProductNo().trim().equalsIgnoreCase("")){
                        mTab.setValue("VendorProductNo", productoSocio.getVendorProductNo().trim());
                    }
                    else{
                        mTab.setValue("VendorProductNo", null);
                    }
                }
            }
            else{
                mTab.fireDataStatusEEvent ("Error", "El Producto ingresado No pertenece a este Socio de Negocio.", true);
            }
            // Seteo UPC traído del producto, cuando el usuario no ingreso UPC
            if ((mTab.getValue("UPC") == null) || (mTab.getValue("UPC").toString().trim().equalsIgnoreCase(""))){
                MZProductoUPC pupc = MZProductoUPC.getByProduct(ctx, mProductID, null);
                if ((pupc != null) && (pupc.get_ID() > 0)){
                    mTab.setValue("UPC", pupc.getUPC());
                }
            }
            else{
                // El usuario ingreso un UPC y ademas selecciono un producto.
                // Puede pasar que quiera asociar un nuevo UPC al producto seleccionado o que cambió el producto y el UPC es de otro.
                // En el segundo caso, tengo que cargar el UPC correcto del nuevo producto seleccionado.
                String upc = mTab.getValue("UPC").toString().trim();
                MZProductoUPC pupc = MZProductoUPC.getByUPC(ctx, upc, null);
                if ((pupc != null) && (pupc.get_ID() > 0)){
                    if (pupc.getM_Product_ID() != mProductID){
                        MZProductoUPC pupcProd = MZProductoUPC.getByProduct(ctx, mProductID, null);
                        if ((pupcProd != null) && (pupcProd.get_ID() > 0)){
                            mTab.setValue("UPC", pupcProd.getUPC());
                        }
                        else{
                            mTab.setValue("UPC", null);
                        }
                    }
                }
            }
        }

        return "";
    }

    /***
     * En linea de inout, setea cantidad recibida según cantidad facturada.
     * Xpande. Created by Gabriel Vila on 3/26/21.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String setQtyByQtyInvoiced(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if ((value == null) || (((Integer) value).intValue() <= 0)){
            return "";
        }

        BigDecimal qtyInvoiced = (BigDecimal) value;
        BigDecimal qtyEntered = (BigDecimal) mTab.getValue("QtyEntered");

        if ((qtyEntered == null) || (qtyEntered.compareTo(Env.ZERO) == 0)){
            mTab.setValue("QtyEntered", qtyInvoiced);
        }

        return "";
    }

    /**
     *	M_InOut - Defaults for BPartner.
     *			- Location
     *			- Contact
     *	@param ctx
     *	@param WindowNo
     *	@param mTab
     *	@param mField
     *	@param value
     *	@return error message or ""
     */
    public String bpartner (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
    {
        Integer C_BPartner_ID = (Integer)value;
        if (C_BPartner_ID == null || C_BPartner_ID.intValue() == 0)
            return "";

        String sql = "SELECT p.AD_Language,p.C_PaymentTerm_ID,"
                + "p.M_PriceList_ID,p.PaymentRule,p.POReference,"
                + "p.SO_Description,p.IsDiscountPrinted, p.TaxID, "
                + "p.SO_CreditLimit-p.SO_CreditUsed AS CreditAvailable,"
                + "l.C_BPartner_Location_ID,c.AD_User_ID "
                + "FROM C_BPartner p, C_BPartner_Location l, AD_User c "
                + "WHERE l.IsActive='Y' AND p.C_BPartner_ID=l.C_BPartner_ID(+)"
                + " AND p.C_BPartner_ID=c.C_BPartner_ID(+)"
                + " AND p.C_BPartner_ID=?";		//	1

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            pstmt = DB.prepareStatement(sql, null);
            pstmt.setInt(1, C_BPartner_ID.intValue());
            rs = pstmt.executeQuery();
            if (rs.next())
            {
                //[ 1867464 ]
                boolean IsSOTrx = "Y".equals(Env.getContext(ctx, WindowNo, "IsSOTrx"));
                if (!IsSOTrx)
                {
                    //	Location
                    Integer ii = new Integer(rs.getInt("C_BPartner_Location_ID"));
                    if (rs.wasNull())
                        mTab.setValue("C_BPartner_Location_ID", null);
                    else
                        mTab.setValue("C_BPartner_Location_ID", ii);
                    //	Contact
                    ii = new Integer(rs.getInt("AD_User_ID"));
                    if (rs.wasNull())
                        mTab.setValue("AD_User_ID", null);
                    else
                        mTab.setValue("AD_User_ID", ii);

                    // Xpande. Gabriel Vila. 29/03/2021.
                    // Cargo TaxID del socio para comprobantes de compra
                    mTab.setValue("TaxID", rs.getString("TaxID"));
                    // Fin Xpande
                }

                //Bugs item #1679818: checking for SOTrx only
                if (IsSOTrx)
                {
                    //	CreditAvailable
                    double CreditAvailable = rs.getDouble("CreditAvailable");
                    if (!rs.wasNull() && CreditAvailable < 0)
                        mTab.fireDataStatusEEvent("CreditLimitOver",
                                DisplayType.getNumberFormat(DisplayType.Amount).format(CreditAvailable),
                                false);
                }//
            }
        }
        catch (SQLException e)
        {
            log.log(Level.SEVERE, sql, e);
            return e.getLocalizedMessage();
        }
        finally
        {
            DB.close(rs, pstmt);
        }

        return "";
    }	//	bpartner

}
