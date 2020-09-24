package org.xpande.retail.callout;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MProduct;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xpande.core.model.MZProductoUPC;
import org.xpande.retail.model.MZLineaProductoSocio;
import org.xpande.retail.model.MZProductoSocio;

import java.util.Properties;

/**
 * Callouts generales relacionados al módulo de retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 11/22/17.
 */
public class CalloutRetail extends CalloutEngine {

    /***
     * Al cambiar linea de productos de un socio de negocio, se actualiza pauta comercial asociada, si tiene una.
     * Xpande. Created by Gabriel Vila on 11/22/17.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String pautaComercialByLinea(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (value == null) return "";

        int zLineaProductoSocioID = ((Integer) value).intValue();

        if (zLineaProductoSocioID > 0){
            MZLineaProductoSocio lineaProductoSocio = new MZLineaProductoSocio(ctx, zLineaProductoSocioID, null);
            if (lineaProductoSocio.getZ_PautaComercial_ID() > 0){
                mTab.setValue("Z_PautaComercial_ID", lineaProductoSocio.getZ_PautaComercial_ID());
            }
            else{
                mTab.setValue("Z_PautaComercial_ID", null);
            }
        }
        else{
            mTab.setValue("Z_PautaComercial_ID", null);
        }

        return "";
    }


    /***
     * Al ingresar código de barras o producto, se deben setear los otros valores asociados.
     * Xpande. Created by Gabriel Vila on 1/10/18.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String upcProduct(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (isCalloutActive()) return "";

        if ((value == null) || (value.toString().trim().equalsIgnoreCase(""))){
            mTab.setValue("UPC", null);
            mTab.setValue("M_Product_ID", null);
            return "";
        }

        String column = mField.getColumnName();

        if (column.equalsIgnoreCase("UPC")){
            MZProductoUPC pupc = MZProductoUPC.getByUPC(ctx, value.toString().trim(), null);
            if ((pupc != null) && (pupc.get_ID() > 0)){
                mTab.setValue("M_Product_ID", pupc.getM_Product_ID());
            }
            else{
                mTab.setValue("M_Product_ID", null);
                mTab.fireDataStatusEEvent ("Error", "No existe Producto con código de barras ingresado", true);
            }
        }
        else if (column.equalsIgnoreCase("M_Product_ID")){
            int mProductID = ((Integer) value).intValue();
            MZProductoUPC pupc = MZProductoUPC.getByProduct(ctx, mProductID, null);
            if ((pupc != null) && (pupc.get_ID() > 0)){
                mTab.setValue("UPC", pupc.getUPC());
            }
            else{
                mTab.setValue("UPC", null);
            }
        }

        return "";
    }

    /***
     * Al ingresar código de barras, o codigo de producto del proveedor, o el producto directamente,
     * se deben setear los otros dos valores asociados, en el proceso de desarectación de productos.
     * Xpande. Created by Gabriel Vila on 7/1/20.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String upcVendProdNoProdDesafecta(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (isCalloutActive()) return "";

        if ((value == null) || (value.toString().trim().equalsIgnoreCase(""))){
            mTab.setValue("UPC", null);
            mTab.setValue("VendorProductNo", null);
            mTab.setValue("M_Product_ID", null);
            return "";
        }

        int cBPartnerID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_ID");
        int zLineaProductoSocioID = Env.getContextAsInt(ctx, WindowNo, "Z_LineaProductoSocio_ID");

        String column = mField.getColumnName();

        if (column.equalsIgnoreCase("UPC")){
            MZProductoUPC pupc = MZProductoUPC.getByUPC(ctx, value.toString().trim(), null);
            if ((pupc != null) && (pupc.get_ID() > 0)){
                MProduct prod = (MProduct) pupc.getM_Product();
                MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(ctx, cBPartnerID, prod.get_ID(), null);
                if ((productoSocio == null) || (productoSocio.get_ID() <= 0)){
                    mTab.setValue("VendorProductNo", null);
                    mTab.setValue("M_Product_ID", null);
                    mTab.setValue("Z_ProductoSocio_ID", null);
                    mTab.fireDataStatusEEvent ("Error", "El código de barras ingresado no pertenece a un Producto de este Socio de Negocio.", true);
                }
                else{
                    if (productoSocio.getZ_LineaProductoSocio_ID() == zLineaProductoSocioID){
                        if (productoSocio.getVendorProductNo() != null){
                            if (!productoSocio.getVendorProductNo().trim().equalsIgnoreCase("")){
                                mTab.setValue("VendorProductNo", productoSocio.getVendorProductNo().trim());
                            }
                        }
                        mTab.setValue("M_Product_ID", prod.get_ID());
                        mTab.setValue("Z_ProductoSocio_ID", productoSocio.get_ID());
                    }
                    else {
                        mTab.setValue("VendorProductNo", null);
                        mTab.setValue("M_Product_ID", null);
                        mTab.setValue("Z_ProductoSocio_ID", null);
                        mTab.fireDataStatusEEvent ("Error", "El código de barras ingresado no pertenece a un Producto de este Linea de Productos.", true);
                    }
                }
            }
            else{
                mTab.setValue("VendorProductNo", null);
                mTab.setValue("M_Product_ID", null);
                mTab.setValue("Z_ProductoSocio_ID", null);
                mTab.fireDataStatusEEvent ("Error", "No existe Producto con código de barras ingresado", true);
            }
        }
        else if (column.equalsIgnoreCase("VendorProductNo")){
            MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerVendorProdNo(ctx, cBPartnerID, value.toString().trim(), null);
            if ((productoSocio != null) && (productoSocio.get_ID() > 0)){
                if (productoSocio.getZ_LineaProductoSocio_ID() == zLineaProductoSocioID){
                    MProduct prod = (MProduct) productoSocio.getM_Product();
                    MZProductoUPC pupc = MZProductoUPC.getByProduct(ctx, prod.get_ID(), null);
                    if ((pupc != null) && (pupc.get_ID() > 0)){
                        mTab.setValue("UPC", pupc.getUPC());
                    }
                    mTab.setValue("M_Product_ID", prod.get_ID());
                    mTab.setValue("Z_ProductoSocio_ID", productoSocio.get_ID());
                }
                else{
                    mTab.setValue("UPC", null);
                    mTab.setValue("M_Product_ID", null);
                    mTab.setValue("Z_ProductoSocio_ID", null);
                    mTab.fireDataStatusEEvent ("Error", "El código ingresado no pertenece a un Producto de esta Linea de Productos.", true);
                }
            }
            else{
                mTab.setValue("UPC", null);
                mTab.setValue("M_Product_ID", null);
                mTab.setValue("Z_ProductoSocio_ID", null);
                mTab.fireDataStatusEEvent ("Error", "No existe Producto para el código ingresado de producto del proveedor", true);
            }
        }
        else if (column.equalsIgnoreCase("M_Product_ID")){
            int mProductID = ((Integer) value).intValue();
            MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(ctx, cBPartnerID, mProductID, null);
            if ((productoSocio != null) && (productoSocio.get_ID() > 0)){
                if (productoSocio.getZ_LineaProductoSocio_ID() == zLineaProductoSocioID){
                    if (productoSocio.getVendorProductNo() != null){
                        if (!productoSocio.getVendorProductNo().trim().equalsIgnoreCase("")){
                            mTab.setValue("VendorProductNo", productoSocio.getVendorProductNo().trim());
                        }
                        else{
                            mTab.setValue("VendorProductNo", null);
                        }
                    }
                    else{
                        mTab.setValue("VendorProductNo", null);
                    }
                    mTab.setValue("Z_ProductoSocio_ID", productoSocio.get_ID());

                    MZProductoUPC pupc = MZProductoUPC.getByProduct(ctx, mProductID, null);
                    if ((pupc != null) && (pupc.get_ID() > 0)){
                        mTab.setValue("UPC", pupc.getUPC());
                    }
                    else{
                        mTab.setValue("UPC", null);
                    }
                }
                else{
                    mTab.setValue("VendorProductNo", null);
                    mTab.setValue("UPC", null);
                    mTab.setValue("Z_ProductoSocio_ID", null);
                    mTab.fireDataStatusEEvent ("Error", "El producto no pertenece a la Linea de Productos seleccionada", true);
                }
            }
            else{
                mTab.setValue("VendorProductNo", null);
                mTab.setValue("UPC", null);
                mTab.setValue("Z_ProductoSocio_ID", null);
                mTab.fireDataStatusEEvent ("Error", "El producto no pertenece a este socio de negocio.", true);
            }
        }

        return "";
    }

    /***
     * Al ingresar cadena de codigo interno de producto, se debe setear ID correspondiente a dicho producto, según
     * distintos criterios.
     * Xpande. Created by Gabriel Vila on 9/24/20.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String setProductStkTransferLin(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (isCalloutActive()) return "";

        if ((value == null) || (value.toString().trim().equalsIgnoreCase(""))){
            mTab.setValue("UPC", null);
            mTab.setValue("M_Product_ID", null);
            mTab.setValue("C_UOM_ID", null);
            return "";
        }

        String codInternoAux = value.toString().trim();
        int adOrgID = Env.getContextAsInt(ctx, WindowNo, "AD_Org_ID");

        // Primero busco producto por organización y codigo interno. Esto por si hay un diferencial para el codigo interno del producto
        // y en esta organización.
        String sql = " select m_product_id from z_difprodorg " +
                        " where ad_orgtrx_id = " + adOrgID +
                        " and codigoproducto ='" + codInternoAux + "' ";
        int mProductID = DB.getSQLValueEx(null, sql);

        // Si no obtuve producto
        if (mProductID <= 0){
            // Busco producto normal por codigo interno
            sql = " select m_product_id from m_product " +
                    " where value ='" + codInternoAux + "' " +
                    " and isactive ='Y'";
            mProductID = DB.getSQLValueEx(null, sql);

            // Si no obtuve producto por codigo interno
            if (mProductID <= 0){

                // Busco por codigo de barras
                sql = " select m_product_id from z_productoupc " +
                        " where upc ='" + codInternoAux + "' ";
                mProductID = DB.getSQLValueEx(null, sql);

                // Si no lo encuentro por codigo de barras
                if (mProductID <= 0){

                    // Busco por codigo interno en etiqueta
                    // Si el largo del texto recibido tiene 13 digitos de largo
                    if (codInternoAux.length() == 13){
                        // Si comienza con 22 o 26
                        if ((codInternoAux.startsWith("22")) || (codInternoAux.startsWith("26"))){
                            // Tomo el codigo interno de 4 digitos luego del prefijo
                            String codInternoEtiq = codInternoAux.substring(3,6);

                            // Busco por diferencial de codigo por organizacion
                            sql = " select m_product_id from z_difprodorg " +
                                    " where ad_orgtrx_id = " + adOrgID +
                                    " and codigoproducto ='" + codInternoEtiq + "' ";
                            mProductID = DB.getSQLValueEx(null, sql);

                            // Si no lo encuentro
                            if (mProductID <= 0){

                                // Busco producto normal por codigo interno
                                sql = " select m_product_id from m_product " +
                                        " where value ='" + codInternoAux + "' " +
                                        " and isactive ='Y'";
                                mProductID = DB.getSQLValueEx(null, sql);

                                if (mProductID <= 0){

                                }
                            }

                        }
                    }
                }

            }
        }

        if (mProductID <= 0){
            mTab.setValue("UPC", null);
            mTab.setValue("M_Product_ID", null);
            mTab.setValue("C_UOM_ID", null);
            return "";
        }

        mTab.setValue("M_Product_ID", mProductID);

        // Busco unidad de medida del producto
        sql = " select c_uom_id from m_product where m_product_id =" + mProductID;
        int cUomID = DB.getSQLValueEx(null, sql);
        if (cUomID > 0){
            mTab.setValue("C_UOM_ID", cUomID);
        }

        // Busco codigo de barras
        sql = " select upc from z_productoupc where m_product_id =" + mProductID;
        String upc = DB.getSQLValueStringEx(null, sql);
        if (upc != null){
            mTab.setValue("UPC", upc);
        }

        return "";
    }

}
