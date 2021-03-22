package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPrice;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.xpande.core.model.MZProductoUPC;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

/**
 * Modelo de linea de proceso de actualización PVP.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/19/17.
 */
public class MZActualizacionPVPLin extends X_Z_ActualizacionPVPLin {

    public MZActualizacionPVPLin(Properties ctx, int Z_ActualizacionPVPLin_ID, String trxName) {
        super(ctx, Z_ActualizacionPVPLin_ID, trxName);
    }

    public MZActualizacionPVPLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {

        // si es nuevo registro
        if (newRecord){
            // Si el precio actual de venta no esta seteado
            if ((this.getPriceSO() == null) || (this.getPriceSO().compareTo(Env.ZERO) <= 0)){

                // Seteo datos asociados al producto de esta linea
                MProduct product = (MProduct) this.getM_Product();
                MZActualizacionPVP actualizacionPVP = (MZActualizacionPVP) this.getZ_ActualizacionPVP();

                // Codigo de barras
                MZProductoUPC pupc = MZProductoUPC.getByProduct(getCtx(), this.getM_Product_ID(), null);
                if ((pupc != null) && (pupc.get_ID() > 0)){
                    this.setUPC(pupc.getUPC());
                }

                // Jerarquía del producto
                this.setZ_ProductoSeccion_ID(product.get_ValueAsInt("Z_ProductoSeccion_ID"));
                this.setZ_ProductoRubro_ID(product.get_ValueAsInt("Z_ProductoRubro_ID"));
                if (product.get_ValueAsInt(X_Z_ProductoFamilia.COLUMNNAME_Z_ProductoFamilia_ID) > 0){
                    this.setZ_ProductoFamilia_ID(product.get_ValueAsInt(X_Z_ProductoFamilia.COLUMNNAME_Z_ProductoFamilia_ID));
                }
                if (product.get_ValueAsInt(X_Z_ProductoSubfamilia.COLUMNNAME_Z_ProductoSubfamilia_ID) > 0){
                    this.setZ_ProductoSubfamilia_ID(product.get_ValueAsInt(X_Z_ProductoSubfamilia.COLUMNNAME_Z_ProductoSubfamilia_ID));
                }

                this.setC_UOM_ID(product.getC_UOM_ID());
                this.setC_TaxCategory_ID(product.getC_TaxCategory_ID());

                // Precios
                // Precios de compra
                // Obtengo socio de negocio de la ultima factura, sino hay facturas, obtengo socio de ultima gestión de precios de proveedor.
                MZProductoSocio productoSocio = MZProductoSocio.getByLastInvoice(getCtx(), product.get_ID(), null);
                if ((productoSocio == null) || (productoSocio.get_ID() <= 0)){
                    productoSocio = MZProductoSocio.getByLastPriceOC(getCtx(), product.get_ID(), null);
                }

                if ((productoSocio != null) && (productoSocio.get_ID() > 0)){
                    this.setPriceFinal(productoSocio.getPriceFinal());
                    this.setPriceInvoiced(productoSocio.getPriceInvoiced());
                    this.setPricePO(productoSocio.getPricePO());

                    if (productoSocio.getC_Invoice_ID() > 0){
                        this.set_ValueOfColumn("C_Invoice_ID", productoSocio.getC_Invoice_ID());
                    }

                    if (productoSocio.get_ValueAsInt("C_Currency_1_ID") > 0){
                        this.set_ValueOfColumn("C_Currency_1_ID", productoSocio.get_ValueAsInt("C_Currency_1_ID"));
                    }
                }

                BigDecimal priceSO = Env.ZERO;
                Timestamp validFrom = null;

                // Obtengo y seteo precio de venta actual desde lista de precios de venta del cabezal
                MProductPrice productPrice = MProductPrice.get(getCtx(), actualizacionPVP.getM_PriceList_Version_ID(), product.get_ID(), null);
                if (productPrice != null){
                    priceSO = productPrice.getPriceList();
                    validFrom = (Timestamp)productPrice.get_Value("ValidFrom");
                }

                // Verifico si este producto tiene una oferta vigente para la fecha actual.
                // Si es asi, el precio que tiene que considerarse es el precio oferta y no el precio de lista.
                Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
                MZProductoOferta productoOferta = MZProductoOferta.getByProductDate(getCtx(), product.get_ID(), fechaHoy, fechaHoy, null);
                if ((productoOferta != null) && (productoOferta.get_ID() > 0)){
                    MZOfertaVenta ofertaVenta = (MZOfertaVenta) productoOferta.getZ_OfertaVenta();
                    MZOfertaVentaLin ventaLin = ofertaVenta.getLineByProduct(product.get_ID());
                    if ((ventaLin != null) && (ventaLin.get_ID() > 0)){
                        if ((ventaLin.getNewPriceSO() != null) && (ventaLin.getNewPriceSO().compareTo(Env.ZERO) > 0)){
                            priceSO = ventaLin.getNewPriceSO();
                        }
                    }
                    validFrom = ofertaVenta.getStartDate();
                }
                if (productPrice != null){
                    this.setPriceSO(priceSO);
                    if ((this.getNewPriceSO() == null) || (this.getNewPriceSO().compareTo(Env.ZERO) <= 0)){
                        this.setNewPriceSO(priceSO);
                    }
                    this.setDateValidSO(validFrom);
                }
            }
        }

        // Si se modifica nuevo precio de venta
        if ((!newRecord) && (is_ValueChanged(X_Z_PreciosProvLin.COLUMNNAME_NewPriceSO))){
            // Refresco precio de venta y margenes en organizaciones asociadas a esta linea (si no es *PVP)
            if (!this.isDistinctPriceSO()){
                this.orgsRefreshSO();
            }
        }

        return true;
    }

    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {

        if (!success) return false;

        if (newRecord){
            this.orgsCreate();
        }

        return true;
    }

    /***
     * Exploto linea en organizaciones del proceso.
     * Xpande. Created by Gabriel Vila on 7/5/17.
     */
    public void orgsCreate() {

        try{
            MZActualizacionPVP actualizacionPVP = (MZActualizacionPVP) this.getZ_ActualizacionPVP();
            List<MZActualizacionPVPOrg> pvpOrgs = actualizacionPVP.getSelectedOrgs();
            for (MZActualizacionPVPOrg pvpOrg: pvpOrgs){
                MZActualizacionPVPLinOrg pvpLinOrg = new MZActualizacionPVPLinOrg(getCtx(), 0, get_TrxName());
                pvpLinOrg.setZ_ActualizacionPVPLin_ID(this.get_ID());
                pvpLinOrg.setAD_OrgTrx_ID(pvpOrg.getAD_OrgTrx_ID());
                pvpLinOrg.setC_Currency_ID(this.getC_Currency_ID());
                pvpLinOrg.setNewPriceSO(this.getNewPriceSO());
                pvpLinOrg.setPriceFinal(this.getPriceFinal());
                pvpLinOrg.setPriceFinalMargin(this.getPriceFinalMargin());
                pvpLinOrg.setPriceSO(this.getPriceSO());
                pvpLinOrg.setPricePO(this.getPricePO());
                pvpLinOrg.setPricePOMargin(this.getPricePOMargin());
                pvpLinOrg.saveEx();
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

    /***
     * Refresco datos de venta y margenes de las organizaciones de esta linea.
     * Xpande. Created by Gabriel Vila on 7/5/17.
     */
    private void orgsRefreshSO() {

        try{
            List<MZActualizacionPVPLinOrg> pvpLinOrgs = this.getOrganizaciones();
            for (MZActualizacionPVPLinOrg pvpLinOrg: pvpLinOrgs){
                pvpLinOrg.setNewPriceSO(this.getNewPriceSO());
                pvpLinOrg.setPriceFinalMargin(this.getPriceFinalMargin());
                pvpLinOrg.setPricePOMargin(this.getPricePOMargin());
                pvpLinOrg.saveEx();
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

    /***
     * Obtiene y retorna lista de organizaciones asociadas a esta linea.
     * Xpande. Created by Gabriel Vila on 7/5/17.
     * @return
     */
    private List<MZActualizacionPVPLinOrg> getOrganizaciones() {

        String whereClause = X_Z_ActualizacionPVPLinOrg.COLUMNNAME_Z_ActualizacionPVPLin_ID + " =" + this.get_ID();

        List<MZActualizacionPVPLinOrg> lines = new Query(getCtx(), I_Z_ActualizacionPVPLinOrg.Table_Name, whereClause, get_TrxName()).setOnlyActiveRecords(true).list();

        return lines;
    }


    /***
     * Obtiene y retorna modelo de organización de esta linea para ID de organizacion recibida.
     * Xpande. Created by Gabriel Vila on 7/20/17.
     * @param adOrgTrxID
     * @return
     */
    public MZActualizacionPVPLinOrg getOrg(int adOrgTrxID) {

        String whereClause = X_Z_ActualizacionPVPLinOrg.COLUMNNAME_Z_ActualizacionPVPLin_ID + " =" + this.get_ID() +
                " AND " + X_Z_ActualizacionPVPLinOrg.COLUMNNAME_AD_OrgTrx_ID + " =" + adOrgTrxID;

        MZActualizacionPVPLinOrg model = new Query(getCtx(), I_Z_ActualizacionPVPLinOrg.Table_Name, whereClause, get_TrxName()).first();

        return model;
    }

}