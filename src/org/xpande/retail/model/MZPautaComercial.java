package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.xpande.core.model.MZSocioListaPrecio;
import org.xpande.retail.utils.ProductPricesInfo;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Modelo de pautas comerciales para el módulo de Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/16/17.
 */
public class MZPautaComercial extends X_Z_PautaComercial {

    public MZPautaComercial(Properties ctx, int Z_PautaComercial_ID, String trxName) {
        super(ctx, Z_PautaComercial_ID, trxName);
    }

    public MZPautaComercial(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Calcula y retorna información relacionada a precios de compra según producto y precio de lista base recibidos.
     * Xpande. Created by Gabriel Vila on 6/23/17.
     * @param mProductID
     * @param priceList
     * @param precisionDecimalCompra
     * @return
     */
    public ProductPricesInfo calculatePrices(int mProductID, BigDecimal priceList, int precisionDecimalCompra) {

        ProductPricesInfo ppi = null;

        try{

            int zPautaComercialSetID_1 = 0, zPautaComercialSetID_2 = 0;

            // Obtengo lista de segmentos especiales para este producto en esta pauta
            List<MZPautaComercialSet> pautaComercialSets = this.getSetsByProduct(mProductID);
            int i = 1;
            for (MZPautaComercialSet pautaComercialSet: pautaComercialSets){
                if (i == 1){
                    zPautaComercialSetID_1 = pautaComercialSet.get_ID();
                    i++;
                }
                else if (i == 2){
                    zPautaComercialSetID_2 = pautaComercialSet.get_ID();
                    i++;
                }
            }

            ppi = this.calculatePrices(priceList, precisionDecimalCompra, zPautaComercialSetID_1, zPautaComercialSetID_2);

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return ppi;
    }


    /***
     * Obtiene y retorna lista de segmentos especiales que contienen el producto recibido.
     * Xpande. Created by Gabriel Vila on 6/27/17.
     * @param mProductID
     * @return
     */
    private List<MZPautaComercialSet> getSetsByProduct(int mProductID) {

        String whereClause = X_Z_PautaComercialSet.COLUMNNAME_Z_PautaComercial_ID + " =" + this.get_ID() +
                " AND " + X_Z_PautaComercialSet.COLUMNNAME_IsGeneral + " ='N'" +
                " AND " + X_Z_PautaComercialSet.COLUMNNAME_Z_PautaComercialSet_ID +
                " IN (select " + X_Z_PautaComercialSet.COLUMNNAME_Z_PautaComercialSet_ID +
                " FROM " + I_Z_PautaComercialSetProd.Table_Name +
                " WHERE " + X_Z_PautaComercialSetProd.COLUMNNAME_M_Product_ID + " =" + mProductID +
                " AND " + X_Z_PautaComercialSetProd.COLUMNNAME_IsActive + " ='Y')";

        List<MZPautaComercialSet> lines = new Query(getCtx(), I_Z_PautaComercialSet.Table_Name, whereClause, get_TrxName())
                .setOnlyActiveRecords(true).setOrderBy(X_Z_PautaComercialSet.COLUMNNAME_Created).list();

        return lines;
    }


    /***
     * Calcula y retorna información relacionada a precios de compra según un precio de lista base recibido.
     * Pueden haber segmentos especiales a considerar en el cálculo.
     * No hay producto determinado.
     * Xpande. Created by Gabriel Vila on 6/23/17.
     * @param priceList
     * @param setEspecialID_1
     * @param setEspecialID_2
     * @return
     */
    public ProductPricesInfo calculatePrices(BigDecimal priceList, int precisionDecimalCompra, int setEspecialID_1, int setEspecialID_2) {

        ProductPricesInfo ppi = null;

        try{

            // Verifico que esta pauta esta activa y tenga productos para aplicar
            if ((!this.isActive()) || ((this.getZ_LineaProductoSocio_ID() <= 0) && (!this.isConfirmed()))){
                return null;
            }

            // Solo voy a calcular si se dan las siguientes condiciones:
            // 1. Esta pauta tiene un segemento de descuentos generales
            // 2. Recibo por parametro al menos un segmento especial para considerar en el calculo

            ppi = new ProductPricesInfo();
            ppi.setZ_PautaComercial_ID(this.get_ID());
            ppi.setPriceList(priceList);
            ppi.setPricePO(priceList);
            ppi.setPriceFinal(priceList);
            ppi.setPriceDtoNC(null);
            ppi.setPrecisionDecimal(precisionDecimalCompra);
            ppi.setSumPercentageDiscountsOC(Env.ZERO);
            ppi.setSumPercentageDiscountsFinal(Env.ZERO);
            ppi.setSumPercentageDiscountsNC(Env.ZERO);
            ppi.setCascadeDiscountsFinal(false);
            ppi.setCascadeDiscountsOC(false);

            // Obtengo segmento general
            MZPautaComercialSet pSetGen = this.getGeneralSet();

            // Si obtuve segmento general, hago los calculos en base a sus descuentos
            if ((pSetGen != null) && (pSetGen.get_ID() > 0)){
                ppi.setSegmentoGral_ID(pSetGen.get_ID());
                pSetGen.calculatePrices(ppi);
            }

            // Si recibo segmento espcial 1, hago los calculos en base a sus descuentos
            if (setEspecialID_1 > 0){
                MZPautaComercialSet pSetEsp1 = new MZPautaComercialSet(getCtx(), setEspecialID_1, get_TrxName());
                ppi.setSegmentoEspecial1_ID(pSetEsp1.get_ID());
                pSetEsp1.calculatePrices(ppi);
            }

            // Si recibo segmento espcial 2, hago los calculos en base a sus descuentos
            if (setEspecialID_2 > 0){
                MZPautaComercialSet pSetEsp2 = new MZPautaComercialSet(getCtx(), setEspecialID_2, get_TrxName());
                ppi.setSegmentoEspecial2_ID(pSetEsp2.get_ID());
                pSetEsp2.calculatePrices(ppi);
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return ppi;

    }

    /***
     * Obtiene y retorna modelo de segmento de descuentos generales en caso de haber uno.
     * Xpande. Created by Gabriel Vila on 6/18/17.
     * @return
     */
    private MZPautaComercialSet getGeneralSet() {

        String whereClause =  X_Z_PautaComercialSet.COLUMNNAME_Z_PautaComercial_ID + " =" + this.get_ID() +
                " AND " + X_Z_PautaComercialSet.COLUMNNAME_IsGeneral + " ='Y'";

        MZPautaComercialSet pset = new Query(getCtx(), I_Z_PautaComercialSet.Table_Name, whereClause, get_TrxName())
                .setOnlyActiveRecords(true).first();

        return pset;
    }


    /***
     * Aplica pauta comercial a los productos requeridos.
     * Xpande. Created by Gabriel Vila on 6/18/17.
     * @parem recalculate : true si se debe recalcular los descuentos en los productos, false sino.
     * @return : String, null o mensaje de inconsistencia.
     */
    public String applyPauta(boolean recalculate) {

        String message = null;

        try{
            message = this.validate();
            if (message != null){
                return message;
            }

            Timestamp today = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

            // Si tengo que recalcular descuentos y tengo linea de producto asociada
            if ((recalculate) && (this.getZ_LineaProductoSocio_ID() > 0)){

                MZLineaProductoSocio lineaProductoSocio = (MZLineaProductoSocio)this.getZ_LineaProductoSocio();
                MZLineaProductoGral lineaProductoGral = (MZLineaProductoGral) lineaProductoSocio.getZ_LineaProductoGral();

                // Recalculo descuentos en productos que pertenecen al socio dueño de la linea de productos asociada a esta pauta
                this.recalculateLineaSocioPrices(lineaProductoSocio);

                // Hago lo mismo para cada distribuidor de la linea de productos pertenciente al socio de negocio de esta pauta
                // Obtengo distribuidores, los recorro y les recalculo descuentos en productos
                List<MZLineaProductoSocio> lineaProductoDistris = lineaProductoGral.getDistribuidores();
                for (MZLineaProductoSocio lineaProductoDistri: lineaProductoDistris){
                    this.recalculateLineaSocioPrices(lineaProductoDistri);
                }

                lineaProductoGral.setZ_PautaComercial_ID(this.get_ID());
                lineaProductoGral.saveEx();
            }

            this.setIsConfirmed(true);
            this.setDateValidFrom(today);
            this.saveEx();
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return message;
    }

    /***
     * Recalcula descuentos y precios de productos que pertenecen a una linea de productos de un socio de negocios.
     * Xpande. Created by Gabriel Vila on 7/13/17.
     * @param lineaProductoSocio
     */
    private void recalculateLineaSocioPrices(MZLineaProductoSocio lineaProductoSocio) {

        try{

            // Asocio pauta a linea de producto del socio
            lineaProductoSocio.setZ_PautaComercial_ID(this.get_ID());
            lineaProductoSocio.saveEx();

            // Instancio lista de precios de compra y versión vigente asociados a la linea de productos-socio
            MPriceList priceList = (MPriceList) lineaProductoSocio.getM_PriceList();
            MPriceListVersion priceListVersion = priceList.getPriceListVersion(null);

            // Obtengo y recorro lista de modelos de producto-socio para este socio y linea recibidos
            List<MZProductoSocio> productoSocios = MZProductoSocio.getByBPartnerLinea(getCtx(), lineaProductoSocio.getC_BPartner_ID(), lineaProductoSocio.get_ID(), get_TrxName());
            for (MZProductoSocio productoSocio: productoSocios){

                // Instancio modelo de producto y aplico pauta en la lista de precios que viene en la relacion producto-socio
                MProduct prod = (MProduct) productoSocio.getM_Product();

                MProductPrice productPrice = MProductPrice.get(getCtx(), priceListVersion.get_ID(), prod.get_ID(), get_TrxName());
                if (productPrice == null) return;

                ProductPricesInfo ppi = this.calculatePrices(prod.get_ID(), productPrice.getPriceList(), priceList.getPricePrecision());
                if (ppi != null){

                    // Seteo precios de compra y segmentos si es necesario en producto-socio
                    productoSocio.setPricePO(ppi.getPricePO());
                    productoSocio.setPriceFinal(ppi.getPriceFinal());
                    if (ppi.getSumPercentageDiscountsOC() != null) {
                        productoSocio.setTotalDiscountsPO(ppi.getSumPercentageDiscountsOC());
                    }
                    else{
                        productoSocio.setTotalDiscountsPO(Env.ZERO);
                    }
                    if (ppi.getSumPercentageDiscountsFinal() != null) {
                        productoSocio.setTotalDiscountsFinal(ppi.getSumPercentageDiscountsFinal());
                    }
                    else{
                        productoSocio.setTotalDiscountsFinal(Env.ZERO);
                    }

                    productoSocio.setZ_PautaComercial_ID(this.get_ID());
                    if (ppi.getSegmentoGral_ID() > 0) productoSocio.setZ_PautaComercialSet_ID_Gen(ppi.getSegmentoGral_ID());
                    if (ppi.getSegmentoEspecial1_ID() > 0) productoSocio.setZ_PautaComercialSet_ID_1(ppi.getSegmentoEspecial1_ID());
                    if (ppi.getSegmentoEspecial2_ID() > 0) productoSocio.setZ_PautaComercialSet_ID_2(ppi.getSegmentoEspecial2_ID());

                    productoSocio.calculateMargins();

                    productoSocio.saveEx();

                    productoSocio.orgsRefreshPO(prod.getM_Product_ID(), priceList.getPricePrecision(), this);

                }
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }


    /***
     * Valida datos de la pauta.
     * Xpande. Created by Gabriel Vila on 6/23/17.
     * @return
     */
    private String validate() {

        String message = null;

        try{

            // Verifico que tenga socio de negocio
            if (this.getC_BPartner_ID() <= 0) {
                return "Debe indicar Socio de Negocio";
            }

            // Verifico que no exista otra pauta con el mismo socio de negocio - linea
            if (this.getZ_LineaProductoSocio_ID() > 0){
                String whereClause = X_Z_PautaComercial.COLUMNNAME_Z_PautaComercial_ID + " !=" + this.get_ID() +
                        " AND " + X_Z_PautaComercial.COLUMNNAME_C_BPartner_ID + " =" + this.getC_BPartner_ID() +
                        " AND " + X_Z_PautaComercial.COLUMNNAME_Z_LineaProductoSocio_ID + " =" + this.getZ_LineaProductoSocio_ID() +
                        " AND " + X_Z_PautaComercial.COLUMNNAME_IsConfirmed + " ='Y'" +
                        " AND " + X_Z_PautaComercial.COLUMNNAME_IsActive + " ='Y'";

                int[] pautasIDs = MZPautaComercial.getAllIDs(I_Z_PautaComercial.Table_Name, whereClause, get_TrxName());
                if (pautasIDs.length > 0){
                    return "Ya existe una Pauta Comercial Confirmada para este Socio de Negocio - Linea de Producto.";
                }
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return message;
    }

    /***
     * Actualizo lista de productos en esta pauta comercial y en sus segmentos.
     * Xpande. Created by Gabriel Vila on 6/30/17.
     * @param zLineaProductoSocioID
     */
    public void updateLineaProducto(int zLineaProductoSocioID) {

        try{
            this.setZ_LineaProductoSocio_ID(zLineaProductoSocioID);
            this.saveEx();

            String action = " update z_pautacomercialset set z_lineaproductosocio_id =" + zLineaProductoSocioID +
                    " where z_pautacomercial_id =" + this.get_ID();
            DB.executeUpdateEx(action, get_TrxName());

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }


    /***
     * Obtiene y retorna lista de descuentos por concepto de bonificacion en unidades que tiene el producto recibido.
     * Xpande. Created by Gabriel Vila on 4/26/18.
     * @param mProductID
     * @return
     */
    public List<MZPautaComercialSetDto> getSetsDtosXBonificacion(int mProductID) {

        List<MZPautaComercialSetDto> lines = new ArrayList<MZPautaComercialSetDto>();

        try{

            // Si esta pauta comercial tiene segmento general
            MZPautaComercialSet setGeneral = this.getGeneralSet();
            if ((setGeneral != null) && (setGeneral.get_ID() > 0)){
                // Obtengo descuentos por concepto de bonificacion en unidades, si este segmento tiene.
                List<MZPautaComercialSetDto> setDtoList = setGeneral.getBonifDiscounts();
                for (MZPautaComercialSetDto setDto: setDtoList){
                    lines.add(setDto);
                }
            }

            // Obtengo lista de segmentos especiales para este producto en esta pauta
            List<MZPautaComercialSet> pautaComercialSets = this.getSetsByProduct(mProductID);
            for (MZPautaComercialSet pautaComercialSet: pautaComercialSets){
                // Obtengo descuentos por concepto de bonificacion en unidades, si este segmento tiene.
                List<MZPautaComercialSetDto> setDtoList = pautaComercialSet.getBonifDiscounts();
                for (MZPautaComercialSetDto setDto: setDtoList){
                    lines.add(setDto);
                }
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return lines;
    }

}
