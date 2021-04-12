package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProductPrice;
import org.compiere.model.MSequence;
import org.compiere.util.DB;
import org.xpande.core.utils.PriceListUtils;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Modelo de organización que se procesa en la gestión de precios de proveedor.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/15/17.
 */
public class MZPreciosProvOrg extends X_Z_PreciosProvOrg {

    public MZPreciosProvOrg(Properties ctx, int Z_PreciosProvOrg_ID, String trxName) {
        super(ctx, Z_PreciosProvOrg_ID, trxName);
    }

    public MZPreciosProvOrg(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Actualiza lista de precios de venta de esta organización para el producto de la linea recibida.
     * Xpande. Created by Gabriel Vila on 6/21/17.
     * @param mProductID
     * @param cCurrencyID
     * @param newPriceSO
     * @param validFrom
     * @param vigenciaPasada
     */
    public void updateProductPriceListSO(int mProductID, int cCurrencyID, BigDecimal newPriceSO, Timestamp validFrom, boolean vigenciaPasada) {

        try{

            // Obtengo lista de venta para organización seleccionada en este documento y moneda.
            // Si ya tengo seteada esta lista en este modelo, la utilizo, sino la seteo ahora.
            MPriceList plVenta = null;
            MPriceListVersion plVersionVenta = null;

            MZPreciosProvCab preciosProvCab = (MZPreciosProvCab) this.getZ_PreciosProvCab();

            if (this.getM_PriceList_ID_SO() <= 0){
                plVenta = PriceListUtils.getPriceListByOrg(getCtx(), this.getAD_Client_ID(), this.getAD_OrgTrx_ID(), cCurrencyID, true, null, get_TrxName());
                if ((plVenta == null) || (plVenta.get_ID() <= 0)){
                    throw new AdempiereException("No se pudo obtener Lista de Precios de Venta para organización : " + this.getAD_OrgTrx_ID() + ", moneda : " + cCurrencyID);
                }
                plVersionVenta = plVenta.getPriceListVersion(null);
                if ((plVersionVenta == null) || (plVersionVenta.get_ID() <= 0)){
                    throw new AdempiereException("No se pudo obtener Versión de Lista de Precios de Venta para organización : " + this.getAD_OrgTrx_ID() + ", moneda : " + cCurrencyID);
                }
                this.setM_PriceList_ID_SO(plVenta.get_ID());
                this.setM_PriceList_Version_ID_SO(plVersionVenta.get_ID());
                this.saveEx();
            }
            else{
                plVenta = new MPriceList(getCtx(), this.getM_PriceList_ID_SO(), get_TrxName());
                plVersionVenta = new MPriceListVersion(getCtx(), this.getM_PriceList_Version_ID_SO(), get_TrxName());
            }

            // Intento obtener precio de lista actual para el producto de esta linea, en la versión de lista
            // de precios de venta recibida.
            MProductPrice pprice = MProductPrice.get(getCtx(), plVersionVenta.get_ID(), mProductID, get_TrxName());

            // Si no tengo precio para este producto, lo creo.
            if ((pprice == null) || (pprice.getM_Product_ID() <= 0)){
                pprice = new MProductPrice(plVersionVenta, mProductID, newPriceSO, newPriceSO, newPriceSO);
            }
            else{
                // Ya existe precio para este producto en la lista

                // Si este documento tiene marcada fecha de vigencia pasada
                if (vigenciaPasada){
                    // Si el precio que esta en la lista tiene vigencia
                    Timestamp vigenciaPrecioProd = (Timestamp) pprice.get_Value("ValidFrom");
                    if (vigenciaPrecioProd != null){
                        // Si la vigencia actual del precio de este producto es mayor a la fecha de vigencia para este documento, no hago nada.
                        if (vigenciaPrecioProd.after(validFrom)){
                            return;
                        }
                    }
                }

                // Actualizo precios si hay cambios
                if (pprice.getPriceList().compareTo(newPriceSO) != 0){
                    pprice.setPriceList(newPriceSO);
                    pprice.setPriceStd(newPriceSO);
                    pprice.setPriceLimit(newPriceSO);
                }
            }
            pprice.set_ValueOfColumn("C_DocType_ID", preciosProvCab.getC_DocType_ID());
            pprice.set_ValueOfColumn("DocumentNoRef", preciosProvCab.getDocumentNo());
            pprice.set_ValueOfColumn("ValidFrom", validFrom);
            pprice.saveEx();

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }


        /*
        String sql, action;

        try{
            boolean updateEvolucionPrecio = false;

            // Obtengo lista de venta para organización seleccionada en este documento y moneda.
            // Si ya tengo seteada esta lista en este modelo, la utilizo, sino la seteo ahora.
            MPriceList plVenta = null;
            MPriceListVersion plVersionVenta = null;

            MZPreciosProvCab preciosProvCab = (MZPreciosProvCab) this.getZ_PreciosProvCab();

            if (this.getM_PriceList_ID_SO() <= 0){
                plVenta = PriceListUtils.getPriceListByOrg(getCtx(), this.getAD_Client_ID(), this.getAD_OrgTrx_ID(), cCurrencyID, true, null, get_TrxName());
                if ((plVenta == null) || (plVenta.get_ID() <= 0)){
                    throw new AdempiereException("No se pudo obtener Lista de Precios de Venta para organización : " + this.getAD_OrgTrx_ID() + ", moneda : " + cCurrencyID);
                }
                plVersionVenta = plVenta.getPriceListVersion(null);
                if ((plVersionVenta == null) || (plVersionVenta.get_ID() <= 0)){
                    throw new AdempiereException("No se pudo obtener Versión de Lista de Precios de Venta para organización : " + this.getAD_OrgTrx_ID() + ", moneda : " + cCurrencyID);
                }
                this.setM_PriceList_ID_SO(plVenta.get_ID());
                this.setM_PriceList_Version_ID_SO(plVersionVenta.get_ID());
                this.saveEx();
            }
            else{
                plVenta = new MPriceList(getCtx(), this.getM_PriceList_ID_SO(), get_TrxName());
                plVersionVenta = new MPriceListVersion(getCtx(), this.getM_PriceList_Version_ID_SO(), get_TrxName());
            }

            int stdPrecision = plVersionVenta.getM_PriceList().getPricePrecision();
            BigDecimal priceLimit = newPriceSO.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP);
            BigDecimal priceList = newPriceSO.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP);
            BigDecimal priceStd = newPriceSO.setScale(stdPrecision, BigDecimal.ROUND_HALF_UP);

            // Verifico si tengo precio de lista actual para el producto de esta linea.
            sql = " select pricelist " +
                    " from m_productprice " +
                    " where m_pricelist_version_id =" + plVersionVenta.get_ID() +
                    " and m_product_id =" + mProductID;
            BigDecimal priceListActual = DB.getSQLValueBDEx(get_TrxName(), sql);

            if (priceListActual == null){
                // Inserto nuevo producto con precio en esta lista
                action = " insert into m_productprice (m_pricelist_version_id, m_product_id, ad_client_id, ad_org_id, " +
                        " isactive, created, createdby, updated, updatedby, pricelist,  pricestd, pricelimit, " +
                        " validfrom, c_doctype_id, documentnoref) " +
                        " values (" + plVersionVenta.get_ID() + ", " + mProductID + ", " +
                        this.getAD_Client_ID() + ", " + plVersionVenta.getAD_Org_ID() + ", 'Y', now(), " +
                        this.getCreatedBy() + ", now(), " + this.getUpdatedBy() + ", " + priceList + ", " +
                        priceStd + ", " + priceLimit + ", '" + validFrom + "', " + preciosProvCab.getC_DocType_ID() + ", '" +
                        preciosProvCab.getDocumentNo() + "')";
            }
            else{
                // Si este documento tiene marcada fecha de vigencia pasada
                if (vigenciaPasada){
                    // Si el precio que esta en la lista tiene vigencia
                    sql = " select validfrom " +
                            " from m_productprice " +
                            " where m_pricelist_version_id =" + plVersionVenta.get_ID() +
                            " and m_product_id =" + mProductID;
                    Timestamp vigenciaPrecioProd = DB.getSQLValueTSEx(get_TrxName(), sql);
                    if (vigenciaPrecioProd != null){
                        // Si la vigencia actual del precio de este producto es mayor a la fecha de vigencia para este documento, no hago nada.
                        if (vigenciaPrecioProd.after(validFrom)){
                            return;
                        }
                        if (!vigenciaPrecioProd.equals(validFrom)){
                            updateEvolucionPrecio = true;
                        }
                    }
                }

                // Actualizo datos de precio para este producto segun haya cambios o no en el precio de lista
                if (priceListActual.compareTo(priceList) != 0){
                    updateEvolucionPrecio = true;
                    action = " update m_productprice set pricelist =" + priceList + ", " +
                            " pricelimit =" + priceLimit + ", " +
                            " pricestd =" + priceStd + ", " +
                            " c_doctype_id =" + preciosProvCab.getC_DocType_ID() + ", " +
                            " validfrom ='" + validFrom + "', " +
                            " documentnoref ='" + preciosProvCab.getDocumentNo() + "' " +
                            " where m_pricelist_version_id =" + plVersionVenta.get_ID() +
                            " and m_product_id =" + mProductID;
                }
                else{
                    // No cambió el precio, solo actualizo vigencia y datos de este documento
                    action = " update m_productprice set c_doctype_id =" + preciosProvCab.getC_DocType_ID() + ", " +
                            " validfrom ='" + validFrom + "', " +
                            " documentnoref ='" + preciosProvCab.getDocumentNo() + "' " +
                            " where m_pricelist_version_id =" + plVersionVenta.get_ID() +
                            " and m_product_id =" + mProductID;
                }
            }

            DB.executeUpdateEx(action, get_TrxName());

            // Si tengo que actualizar la evolucion del precio de venta
            if (updateEvolucionPrecio){
                MSequence sequence = MSequence.get(getCtx(), I_Z_EvolPrecioVtaProdOrg.Table_Name);
                action = " insert into z_evolpreciovtaprodorg (ad_client_id, ad_org_id, ad_orgtrx_id, c_currency_id, " +
                        " created, createdby, updated, updatedby, isactive, datevalidso, m_pricelist_id, priceso, " +
                        " z_evolpreciovtaprodorg_id, m_product_id, ad_user_id, " +
                        " c_doctype_id, documentnoref) " +
                        " values (" + plVersionVenta.getAD_Client_ID() + ", " + plVersionVenta.getAD_Org_ID() + ", " +
                        plVersionVenta.getAD_Org_ID() + ", " + plVersionVenta.getPriceList().getC_Currency_ID() + ", now(), " +
                        preciosProvCab.getCreatedBy() + ", now(), " + preciosProvCab.getUpdatedBy() + ", 'Y', '" +
                        validFrom + "', " +
                        plVersionVenta.getM_PriceList_ID() + ", " + priceList + ", nextid(" + sequence.get_ID() + ", 'N'), " +
                        mProductID + ", " + preciosProvCab.getUpdatedBy() + ", " + preciosProvCab.getC_DocType_ID() + ", '" +
                        preciosProvCab.getDocumentNo() + "') ";
                DB.executeUpdateEx(action, get_TrxName());
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        */

    }

}
