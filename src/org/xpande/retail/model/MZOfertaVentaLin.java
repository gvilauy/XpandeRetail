package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.xpande.core.utils.PriceListUtils;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Modelo para líneas del proceso de ofertas periódicas en retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 1/10/18.
 */
public class MZOfertaVentaLin extends X_Z_OfertaVentaLin {

    public MZOfertaVentaLin(Properties ctx, int Z_OfertaVentaLin_ID, String trxName) {
        super(ctx, Z_OfertaVentaLin_ID, trxName);
    }

    public MZOfertaVentaLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    @Override
    protected boolean beforeSave(boolean newRecord) {

        MZOfertaVenta ofertaVenta = (MZOfertaVenta) this.getZ_OfertaVenta();

        // Para nuevos registros o cambio de producto
        if (newRecord){

            MProduct product = (MProduct) this.getM_Product();

            // Valido que el producto de esta linea no tengo una oferta definida dentro del mismo rango de fechas de esta oferta.
            MZProductoOferta productoOferta = MZProductoOferta.getByProductDate(getCtx(), this.getM_Product_ID(), ofertaVenta.getStartDate(), ofertaVenta.getEndDate(), get_TrxName());
            if ((productoOferta != null) && (productoOferta.get_ID() > 0)){
                MZOfertaVenta ofertaVentaAux = (MZOfertaVenta) productoOferta.getZ_OfertaVenta();

                log.saveError("ATENCIÓN", "Ya existe una oferta para el producto " + product.getValue() + " - " + product.getName() +
                            " (Oferta nro.: " + ofertaVentaAux.getDocumentNo() + ")");
                return false;
            }

            // Inicializo Precio anterior es igual al nuevo precio para productos nuevos en la oferta.
            this.setLastPriceSO(this.getNewPriceSO());

            // Si estoy en correccion de oferta, marco este nuevo producto ingresado como nuevo en la corrección
            if (ofertaVenta.isModified()){
                this.setIsModified(true);
                this.setIsNew(true);

                // Me aseguro que no hayan marcas de este producto en la tablas de eliminados.
                // Esto es por si elimino un producto y lo vuelve a dar de alta, durante el proceso de corrección.
                String action = " delete from z_ofertaventalindel where z_ofertaventa_id =" + ofertaVenta.get_ID() +
                        " and m_product_id =" + this.getM_Product_ID();
                DB.executeUpdateEx(action, get_TrxName());
            }
            else{
                this.setIsModified(false);
                this.setIsNew(false);
            }
        }

        // Si se modifica el precio de venta
        if (this.is_ValueChanged(X_Z_OfertaVentaLin.COLUMNNAME_NewPriceSO)){
            // Si no estoy en corrección, o estoy en corrección y el producto es nuevo para la oferta
            if ((!ofertaVenta.isModified()) || (ofertaVenta.isModified() && this.isNew())){
                // Actualizo precio anterior igual al nuevo precio
                this.setLastPriceSO(this.getNewPriceSO());
            }
            // Si estoy en corrección y el producto ya existia en la oferta y le esta cambiando el precio, marco producto modificado.
            if ((ofertaVenta.isModified()) && (!this.isNew())){
                if (this.getNewPriceSO().compareTo(this.getLastPriceSO()) != 0){
                    this.setIsModified(true);
                }
                else{
                    this.setIsModified(false);
                }
            }
        }

        return true;
    }


    @Override
    protected boolean beforeDelete() {

        // Obtengo cabezal de oferta
        MZOfertaVenta ofertaVenta = (MZOfertaVenta) this.getZ_OfertaVenta();

        // Si estoy en corrección de oferta
        if (ofertaVenta.isModified()){
            // Al eliminar un producto, guardo información de esta eliminación para procesarlo al completar nuevamente la oferta
            MZOfertaVentaLinDel linDel = new MZOfertaVentaLinDel(getCtx(), 0, get_TrxName());
            linDel.setZ_OfertaVenta_ID(ofertaVenta.get_ID());
            linDel.setM_Product_ID(this.getM_Product_ID());
            linDel.setZ_OfertaVentaLin_ID(this.get_ID());
            linDel.setValidFrom(ofertaVenta.getStartDate());
            linDel.saveEx();
        }

        return true;
    }

    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {

        if (!success) return success;

        // Para nuevos registros
        if (newRecord){

            MZOfertaVenta ofertaVenta = (MZOfertaVenta) this.getZ_OfertaVenta();

            // Obtengo y cargo proveedores de este producto en tabla correspondiente
            List<MZProductoSocio> productoSocioList = MZProductoSocio.getByProduct(getCtx(), this.getM_Product_ID(), get_TrxName());
            for (MZProductoSocio productoSocio: productoSocioList){
                MZOfertaVentaLinBP ofertaVentaLinBP = new MZOfertaVentaLinBP(getCtx(), 0, get_TrxName());
                ofertaVentaLinBP.setC_BPartner_ID(productoSocio.getC_BPartner_ID());
                ofertaVentaLinBP.setC_Currency_ID(productoSocio.getC_Currency_ID());
                ofertaVentaLinBP.setIsLockedPO(true);
                ofertaVentaLinBP.setAplicanDtosPago(true);
                ofertaVentaLinBP.setM_PriceList_ID(productoSocio.getM_PriceList_ID());
                ofertaVentaLinBP.setM_PriceList_Version_ID(productoSocio.getM_PriceList_Version_ID());
                ofertaVentaLinBP.setPricePO(productoSocio.getPricePO());
                ofertaVentaLinBP.setZ_OfertaVentaLin_ID(this.get_ID());
                ofertaVentaLinBP.saveEx();
            }

            // Obtengo y cargo precios PVP actuales para este productos, de las organizaciones que participan en este proceso de oferta.
            List<MZOfertaVentaOrg> ventaOrgList = ofertaVenta.getOrgsSelected();
            for (MZOfertaVentaOrg ventaOrg: ventaOrgList){

                // Obtengo lista de venta para organización y moneda de venta
                MPriceList priceList = PriceListUtils.getPriceListByOrg(getCtx(), ofertaVenta.getAD_Client_ID(), ventaOrg.getAD_OrgTrx_ID(),
                        ofertaVenta.getC_Currency_ID_SO(), true, null, null);

                if (priceList != null){
                    // Obtengo versión de lista vigente
                    MPriceListVersion plv = priceList.getPriceListVersion(null);
                    if (plv != null){
                        MProductPrice productPrice = MProductPrice.get(getCtx(), plv.get_ID(), this.getM_Product_ID(), null);
                        if (productPrice != null){

                            MZOfertaVentaLinOrg ventaLinOrg = new MZOfertaVentaLinOrg(getCtx(), 0, get_TrxName());
                            ventaLinOrg.setAD_OrgTrx_ID(ventaOrg.getAD_OrgTrx_ID());
                            ventaLinOrg.setZ_OfertaVentaLin_ID(this.get_ID());
                            ventaLinOrg.setM_PriceList_ID(priceList.get_ID());
                            ventaLinOrg.setM_PriceList_Version_ID(plv.get_ID());
                            ventaLinOrg.setC_Currency_ID(priceList.getC_Currency_ID());
                            ventaLinOrg.setPriceSO(productPrice.getPriceList());
                            ventaLinOrg.saveEx();

                        }
                        else{
                            MProduct product = (MProduct) this.getM_Product();
                            throw new AdempiereException("No se pudo obtener precio de producto (" + product.getValue() + ") en Lista de Precios de Venta : " + priceList.getName());
                        }
                    }
                    else{
                        throw new AdempiereException("No se pudo obtener Versión de Lista de Precios de Venta : " + priceList.getName());
                    }

                }
                else{
                    MOrg org = new MOrg(getCtx(), ventaOrg.getAD_OrgTrx_ID(), null);
                    throw new AdempiereException("No se pudo obtener Lista de Precios de Venta para la Organización : " + org.getName());
                }

            }

        }

        return true;
    }


    /***
     * Obtiene y retorna socios de negocio no bloqueados asociados a esta linea.
     * Xpande. Created by Gabriel Vila on 1/10/18.
     * @return
     */
    public List<MZOfertaVentaLinBP> getSelectedPartners(){

        String whereClause = X_Z_OfertaVentaLinBP.COLUMNNAME_Z_OfertaVentaLin_ID + " =" + this.get_ID() +
                " AND " + X_Z_OfertaVentaLinBP.COLUMNNAME_IsLockedPO + " ='N'";

        List<MZOfertaVentaLinBP> lines = new Query(getCtx(), I_Z_OfertaVentaLinBP.Table_Name, whereClause, get_TrxName()).list();

        return lines;
    }

}
