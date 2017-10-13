package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xpande.core.model.MZProductoUPC;
import org.xpande.retail.utils.ProductPricesInfo;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Modelo de lineas (productos) del proceso de mantenimiento de precios de proveedor.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/13/17.
 */
public class MZPreciosProvLin extends X_Z_PreciosProvLin {

    public MZPreciosProvLin(Properties ctx, int Z_PreciosProvLin_ID, String trxName) {
        super(ctx, Z_PreciosProvLin_ID, trxName);
    }

    public MZPreciosProvLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {

        String message = null;

        MZPreciosProvCab cab = (MZPreciosProvCab)this.getZ_PreciosProvCab();

        // Cuando estoy en modalidad ingreso manual
        if (cab.getModalidadPreciosProv().equalsIgnoreCase(X_Z_PreciosProvCab.MODALIDADPRECIOSPROV_INGRESOMANUAL)){

            // Valido información cargada manualmente
            message = this.validateIngresoManual(cab);

            if (message != null){
                log.saveError("ATENCIÓN", message);
                return false;
            }

            // Si además estoy en nuevo registro, seteo información necesaria para el ingreso manual
            if (newRecord){
                this.setInfoIngresoManual(cab);
            }
        }

        // Si modifica segmentos de pauta comercial
        if ((!newRecord) && ((is_ValueChanged(X_Z_PreciosProvLin.COLUMNNAME_Z_PautaComercialSet_ID_1))
                || (is_ValueChanged(X_Z_PreciosProvLin.COLUMNNAME_Z_PautaComercialSet_ID_2)))){

            // Valido segmentos de pautas comerciales aplicados a este productos
            message = this.validateSegmentos();

            if (message != null){
                log.saveError("ATENCIÓN", message);
                return false;
            }

            // Recalculo precios de compra de esta linea ya que se modificaron los segmentos especiales en la misma
            this.calculatePricesPO(this.getPriceList(), cab.getPrecisionPO(), (MZPautaComercial) cab.getZ_PautaComercial(), true);

            // Recalculo márgenes
            this.calculateMargins(cab.getRate());

            // Refresco precio de venta y margenes en organizaciones asociadas a esta linea (si no es *PC)
            if (!this.isDistinctPricePO()){
                this.orgsRefreshPO();
            }

        }

        // Si se modifica precio de lista
        if ((!newRecord) && (is_ValueChanged(X_Z_PreciosProvLin.COLUMNNAME_PriceList))){

            // Recalculo precios de compra de esta linea ya que se modifico el precio de lista base
            this.calculatePricesPO(this.getPriceList(), cab.getPrecisionPO(), (MZPautaComercial) cab.getZ_PautaComercial(), true);

            // Recalculo márgenes
            this.calculateMargins(cab.getRate());

            // Refresco precio de venta y margenes en organizaciones asociadas a esta linea (si no es *PC)
            if (!this.isDistinctPricePO()){
                this.orgsRefreshPO();
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

    /***
     * Refresco datos de compra y margenes de las organizaciones de esta linea.
     * Xpande. Created by Gabriel Vila on 7/5/17.
     */
    private void orgsRefreshPO() {
        try{
            List<MZPreciosProvLinOrg> preciosProvLinOrgs = this.getOrganizaciones();
            for (MZPreciosProvLinOrg preciosProvLinOrg: preciosProvLinOrgs){
                preciosProvLinOrg.setPriceList(this.getPriceList());
                preciosProvLinOrg.setPricePO(this.getPricePO());
                preciosProvLinOrg.setPriceFinal(this.getPriceFinal());
                preciosProvLinOrg.setPriceFinalMargin(this.getPriceFinalMargin());
                preciosProvLinOrg.setPricePOMargin(this.getPricePOMargin());
                preciosProvLinOrg.saveEx();
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
            List<MZPreciosProvLinOrg> preciosProvLinOrgs = this.getOrganizaciones();
            for (MZPreciosProvLinOrg preciosProvLinOrg: preciosProvLinOrgs){
                preciosProvLinOrg.setNewPriceSO(this.getNewPriceSO());
                preciosProvLinOrg.setPriceFinalMargin(this.getPriceFinalMargin());
                preciosProvLinOrg.setPricePOMargin(this.getPricePOMargin());
                preciosProvLinOrg.saveEx();
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }


    /***
     * Refresco solamenta los datos de compra afectados por cambios en pauta comerciales.
     * Xpande. Created by Gabriel Vila on 7/5/17.
     * @param precisionDecimalCompra
     * @param pautaComercial
     * @param useLineSegments
     * @param multiplyRate
     */
    public void orgsRefreshPautaPO(int precisionDecimalCompra, MZPautaComercial pautaComercial, boolean useLineSegments, BigDecimal multiplyRate) {
        try{
            List<MZPreciosProvLinOrg> preciosProvLinOrgs = this.getOrganizaciones();
            for (MZPreciosProvLinOrg preciosProvLinOrg: preciosProvLinOrgs){
                preciosProvLinOrg.calculatePricesPO(precisionDecimalCompra, pautaComercial, useLineSegments);
                preciosProvLinOrg.calculateMargins(multiplyRate);
                preciosProvLinOrg.saveEx();
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
    private List<MZPreciosProvLinOrg> getOrganizaciones() {

        String whereClause = X_Z_PreciosProvLinOrg.COLUMNNAME_Z_PreciosProvLin_ID + " =" + this.get_ID();

        List<MZPreciosProvLinOrg> lines = new Query(getCtx(), I_Z_PreciosProvLinOrg.Table_Name, whereClause, get_TrxName()).setOnlyActiveRecords(true).list();

        return lines;
    }


    /***
     * Valido información para un ingreso manual de nuevo producto.
     * Xpande. Created by Gabriel Vila on 6/26/17.
     * @param cab
     * @return
     */
    private String validateIngresoManual(MZPreciosProvCab cab) {

        String message = null;
        String sql = "";

        try{
            // Valido código de barras repetido en este documento y en otro producto existente
            if (this.getUPC() != null){
                this.setUPC(this.getUPC().trim().replaceAll("[^0-9]", ""));
                sql = " select z_preciosprovlin_id from z_preciosprovlin " +
                        " where z_preciosprovcab_id = " + this.getZ_PreciosProvCab_ID() +
                        " and z_preciosprovlin_id !=" + this.get_ID() +
                        " and upc ='" + this.getUPC() + "'";
                int idExistente = DB.getSQLValueEx(null, sql);
                if (idExistente > 0){
                    return "Código de Barras duplicado en otro producto de este Documento.";
                }

                MZProductoUPC pupc = MZProductoUPC.getByUPC(getCtx(), this.getUPC(), null);
                if ((pupc != null) && (pupc.get_ID() > 0)){
                    MProduct prod = (MProduct) pupc.getM_Product();
                    return "Código de Barras ya existe asociado el producto : " + prod.getValue() + " - " + prod.getName();
                }
            }

            // Valido codigo de producto del proveedor duplicado en este documento y en otro producto existente
            if ((this.getVendorProductNo() != null) && (!this.getVendorProductNo().trim().equalsIgnoreCase(""))){
                this.setVendorProductNo(this.getVendorProductNo().trim());
                sql = " select z_preciosprovlin_id from z_preciosprovlin " +
                        " where z_preciosprovcab_id = " + this.getZ_PreciosProvCab_ID() +
                        " and z_preciosprovlin_id !=" + this.get_ID() +
                        " and vendorproductno ='" + this.getVendorProductNo() + "'";
                int idExistente = DB.getSQLValueEx(null, sql);
                if (idExistente > 0){
                    return "Código Producto de Proveedor duplicado en otro producto de este Documento.";
                }

                MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerVendorProdNo(getCtx(), cab.getC_BPartner_ID(), this.getVendorProductNo(), null);
                if ((productoSocio != null) && (productoSocio.get_ID() > 0)){
                    MProduct prod = (MProduct) productoSocio.getM_Product();
                    return "Código Producto de Proveedor ya existe asociado el producto : " + prod.getValue() + " - " + prod.getName();
                }
            }

            // Valido codigo interno duplicado en este documento y en otro producto existente
            if ((this.getInternalCode() != null) && (!this.getInternalCode().trim().equalsIgnoreCase(""))){
                this.setInternalCode(this.getInternalCode().trim());
                sql = " select z_preciosprovlin_id from z_preciosprovlin " +
                        " where z_preciosprovcab_id = " + this.getZ_PreciosProvCab_ID() +
                        " and z_preciosprovlin_id !=" + this.get_ID() +
                        " and InternalCode ='" + this.getInternalCode() + "'";
                int idExistente = DB.getSQLValueEx(null, sql);
                if (idExistente > 0){
                    return "Código Interno de Producto duplicado en otro producto de este Documento.";
                }

                MProduct[] prods = MProduct.get(getCtx(), X_M_Product.COLUMNNAME_Value + " ='" + this.getInternalCode() + "'", null);
                if (prods.length > 0){
                    MProduct prod = (MProduct) prods[0];
                    return "Código Interno de Producto ya existe asociado el producto : " + prod.getValue() + " - " + prod.getName();
                }
            }

            // Valido que haya ingresado nombre corto
            if ((this.getName() == null) || (this.getName().trim().equalsIgnoreCase(""))){
                return "Debe indicar Nombre Corto para el Nuevo Producto";
            }
            this.setName(this.getName().trim().toUpperCase());

            // Si no ingreso nombre largo, le seteo el nombre corto
            if ((this.getDescription() == null) || (this.getDescription().trim().equalsIgnoreCase(""))){
                this.setDescription(this.getName());
            }
            else{
                this.setDescription(this.getDescription().trim().toUpperCase());
            }

            // Valido que haya ingresado precio de lista
            if ((this.getPriceList() == null) || (this.getPriceList().compareTo(Env.ZERO) <= 0)){
                return "Debe indicar Precio de Lista para el Nuevo Producto.";
            }

            // Valido que haya ingresado precio de venta
            if ((this.getNewPriceSO() == null) || (this.getNewPriceSO().compareTo(Env.ZERO) <= 0)){
                return "Debe indicar Precio de Venta para el Nuevo Producto.";
            }

            // Valido que haya ingresado seccion y rubro
            if (this.getZ_ProductoSeccion_ID() <= 0){
                return "Debe indicar Sección para el Nuevo Producto";
            }
            if (this.getZ_ProductoRubro_ID() <= 0){
                return "Debe indicar Rubro para el Nuevo Producto";
            }

            // Valido impuesto
            if (this.getC_TaxCategory_ID() <= 0){
                return "Debe indicar Impuesto para el Nuevo Producto";
            }

            // Si no tengo unidad de medida, asocio unidad por defecto
            if (this.getC_UOM_ID() <= 0){
                this.setC_UOM_ID(100);
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return message;
    }

    /***
     * Seteo información de nuevo registro ingresado manualmente.
     * Xpande. Created by Gabriel Vila on 6/26/17.
     * @param cab
     */
    private void setInfoIngresoManual(MZPreciosProvCab cab) {

        try{

            this.setIsNew(true);
            this.setC_Currency_ID(cab.getC_Currency_ID());
            this.setC_Currency_ID_SO(cab.getC_Currency_ID_SO());

            // Seto precio de compra
            this.setOrgDifferentPricePO(false);
            this.calculatePricesPO(this.getPriceList(), cab.getPrecisionPO(), (MZPautaComercial) cab.getZ_PautaComercial(), false);
            this.calculateMargins(cab.getRate());

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

    /***
     * Valida segmentos aplicados a este producto.
     * Xpande. Created by Gabriel Vila on 6/17/17.
     * @return
     */
    private String validateSegmentos() {

        String message = null;

        try{

            // Si tengo segmento general, solo admito un segmento especial
            if (this.getZ_PautaComercialSet_ID_Gen() > 0){
                if ((this.getZ_PautaComercialSet_ID_1() > 0) && (this.getZ_PautaComercialSet_ID_2() > 0)) {
                    message = "Solo se admite un segmento especial para este producto.";
                    return message;
                }
            }
            else{
                if ((this.getZ_PautaComercialSet_ID_1() > 0) && (this.getZ_PautaComercialSet_ID_2() > 0)) {
                    if (this.getZ_PautaComercialSet_ID_1() == this.getZ_PautaComercialSet_ID_2()) {
                        message = "Segmentos especiales iguales. Deben ser diferentes.";
                        return message;
                    }
                }
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return message;
    }


    /***
     * Calcula y setea precios de compra de este linea.
     * Setea ademas segmentos de descuentos que aplican a esta linea si es que los hubiera.
     * Xpande. Created by Gabriel Vila on 6/18/17.
     * @param priceList
     * @param precisionDecimalCompra
     * @param pautaComercial
     */
    public void calculatePricesPO(BigDecimal priceList, int precisionDecimalCompra, MZPautaComercial pautaComercial, boolean useLineSegments) {

        try{
            // Precio de lista viene por parametro y será la base para calcular los demas
            this.setPriceList(priceList);
            this.setPricePO(priceList);
            this.setPriceFinal(priceList);

            // Si tengo pauta comercial
            if ((pautaComercial != null) && (pautaComercial.get_ID() > 0)){

                // Obtengo toda la información referida a precios de compra desde la pauta comercial
                ProductPricesInfo ppi = null;

                // Si tengo producto, calculo precios basandome en este producto y precio de lista
                if (this.getM_Product_ID() > 0){

                    // Cuando tengo producto y no uso segmentos seleccionados en este linea, voy a considerar los segmentos que ya se definieron en la pauta
                    if (!useLineSegments){
                        ppi = pautaComercial.calculatePrices(this.getM_Product_ID(), this.getPriceList(), precisionDecimalCompra);
                    }
                    else{
                        // Tengo producto, pero el usuario le cambio los segmentos especiales editando esta linea
                        ppi = pautaComercial.calculatePrices(this.getPriceList(), precisionDecimalCompra, this.getZ_PautaComercialSet_ID_1(), this.getZ_PautaComercialSet_ID_2());
                    }
                }
                else{
                    // No tengo producto, por lo tanto me baso en el precio de lista y segmentos especiales que pueda tener asociados en esta linea
                    ppi = pautaComercial.calculatePrices(this.getPriceList(), precisionDecimalCompra, this.getZ_PautaComercialSet_ID_1(), this.getZ_PautaComercialSet_ID_2());
                }

                if (ppi != null){

                    // Seteo precios de compra y segmentos si es necesario
                    this.setPricePO(ppi.getPricePO());
                    this.setPriceFinal(ppi.getPriceFinal());
                    if (ppi.getSumPercentageDiscountsOC() != null) {
                        this.setTotalDiscountsPO(ppi.getSumPercentageDiscountsOC());
                    }
                    else{
                        this.setTotalDiscountsPO(Env.ZERO);
                    }
                    if (ppi.getSumPercentageDiscountsFinal() != null) {
                        this.setTotalDiscountsFinal(ppi.getSumPercentageDiscountsFinal());
                    }
                    else{
                        this.setTotalDiscountsFinal(Env.ZERO);
                    }

                    if (ppi.getSegmentoGral_ID() > 0) this.setZ_PautaComercialSet_ID_Gen(ppi.getSegmentoGral_ID());
                    if (ppi.getSegmentoEspecial1_ID() > 0) this.setZ_PautaComercialSet_ID_1(ppi.getSegmentoEspecial1_ID());
                    if (ppi.getSegmentoEspecial2_ID() > 0) this.setZ_PautaComercialSet_ID_2(ppi.getSegmentoEspecial2_ID());
                }
                else{
                    this.setIsConfirmed(false);
                    this.setErrorMsg("No se obtuvieron precios de compra para la Pauta Comercial seleccionada.");
                }
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }


    /***
     * Calcula y seteo precios de venta de esta linea.
     * Xpande. Created by Gabriel Vila on 6/23/17.
     * @param priceSO
     * @param precisionDecimalVenta
     */
    public void calculatePricesSO(BigDecimal priceSO, int precisionDecimalVenta) {

        try{
            this.setPriceSO(priceSO);
            this.setNewPriceSO(priceSO);
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }


    /***
     * Valida linea.
     * Xpande. Created by Gabriel Vila on 6/19/17.
     * @return null si no hay inconsistencias, o mensaje de inconsistencia
     */
    public String validate() {

        String message = null;

        try{

            if ((this.getName() == null) || (this.getName().trim().equalsIgnoreCase(""))){
                return "Producto no tiene Nombre";
            }

            if (this.getZ_ProductoSeccion_ID() <= 0){
                return "Producto no tiene Sección";
            }

            if (this.getZ_ProductoRubro_ID() <= 0){
                return "Producto no tiene Rubro";
            }

            if (this.getC_TaxCategory_ID()<= 0){
                return "Producto no tiene Impuesto";
            }

            if ((this.getNewPriceSO() == null) || (this.getNewPriceSO().compareTo(Env.ZERO) <= 0)){
                return "Producto no tiene Nuevo Precio de Venta";
            }

            // Codigo de barras duplicado en otro producto
            if (this.getUPC() != null){
                MZProductoUPC pupc = MZProductoUPC.getByUPC(getCtx(), this.getUPC(), get_TrxName());
                if ((pupc != null) && (pupc.get_ID() > 0)){
                    if (this.getM_Product_ID() > 0){
                        if (pupc.getM_Product_ID() != this.getM_Product_ID()){
                            MProduct prod = (MProduct)pupc.getM_Product();
                            return "Código de Barras ya existe en el producto con Código Interno : " + prod.getValue();
                        }
                    }
                    else{
                        MProduct prod = (MProduct)pupc.getM_Product();
                        return "Código de Barras ya existe en el producto con Código Interno : " + prod.getValue();
                    }
                }
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return message;

    }

    /***
     * Método que setea datos al producto asociado o no a este linea.
     * Procedimiento:
     * 1. Si esta linea ya tiene m_product_id, retorno este producto pero antes le seteo atributos que pudieron ser
     * modificados por el usuario en este documento, como ser por ejemplo la sección o el rubro.
     * 2. Si esta linea no tiene m_product_id, debo crear un NUEVO producto en el sistema con los datos brindados
     * por el usuario como por ejemplo el código de barras, nombre, etc.
     * Xpande. Created by Gabriel Vila on 6/19/17.
     */
    public void setProduct() {

        MProduct prod = null;
        boolean seCreoProductoNuevo = false;

        try{

            // Si la linea tiene la marca de nuevo producto
            if (this.isNew()){

                // Si a esta linea que surgió como un producto nuevo, no se le asocio un producto existente anteriormente
                if (this.getM_Product_ID() > 0){
                    prod = (MProduct) this.getM_Product(); // Ya tengo producto
                }
                else{
                    // No tengo producto asociado, creo nuevo producto y seteo algunos atributos
                    prod = new MProduct(getCtx(), 0, get_TrxName());

                    prod.setIsSold(true);
                    prod.setIsPurchased(true);
                    prod.setIsStocked(true);
                    if (this.getC_UOM_ID() <= 0) this.setC_UOM_ID(100);
                    prod.setC_UOM_ID(this.getC_UOM_ID());
                    if (this.getInternalCode() != null){
                        String value = this.getInternalCode().trim().toUpperCase();
                        if (!value.equalsIgnoreCase("")){
                            prod.setValue(value);
                        }
                    }
                }
            }
            else{
                prod = (MProduct) this.getM_Product();
            }

            // Seteo atributos al producto que el usuario pudo haber modificado en este documento
            prod.setName(this.getName().trim().toUpperCase());
            prod.set_ValueOfColumn(X_Z_ProductoSeccion.COLUMNNAME_Z_ProductoSeccion_ID, this.getZ_ProductoSeccion_ID());
            prod.set_ValueOfColumn(X_Z_ProductoRubro.COLUMNNAME_Z_ProductoRubro_ID, this.getZ_ProductoRubro_ID());
            prod.setC_TaxCategory_ID(this.getC_TaxCategory_ID());

            if (this.getZ_ProductoFamilia_ID() > 0){
                prod.set_ValueOfColumn(X_Z_ProductoFamilia.COLUMNNAME_Z_ProductoFamilia_ID, this.getZ_ProductoFamilia_ID());
            }

            if (this.getZ_ProductoSubfamilia_ID() > 0){
                prod.set_ValueOfColumn(X_Z_ProductoSubfamilia.COLUMNNAME_Z_ProductoSubfamilia_ID, this.getZ_ProductoSubfamilia_ID());
            }

            if (this.getC_TaxCategory_ID_2() > 0){
                prod.set_ValueOfColumn(X_Z_PreciosProvLin.COLUMNNAME_C_TaxCategory_ID_2, this.getC_TaxCategory_ID_2());
            }

            if (this.getDescription() != null){
                prod.setDescription(this.getDescription().trim().toUpperCase());
            }
            prod.saveEx();

            // Para nuevo producto, tengo o no asociado uno existente
            if (this.isNew()){
                // Agrego nuevo UPC al producto en caso de tener
                if ((this.getUPC() != null) && (!this.getUPC().trim().equalsIgnoreCase(""))){
                    MZProductoUPC pupc = new MZProductoUPC(getCtx(), 0, get_TrxName());
                    pupc.setM_Product_ID(prod.get_ID());
                    pupc.setUPC(this.getUPC());
                    pupc.saveEx();
                }
            }

            // Me aseguro de dejar el m_product_id seteado en este linea de documento
            if (this.getM_Product_ID() <= 0){
                this.setM_Product_ID(prod.get_ID());
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

    /***
     * Metodo que calcula y setea margenes de esta linea.
     * Xpande. Created by Gabriel Vila on 6/23/17.
     */
    public void calculateMargins(BigDecimal multiplyRate) {

        try{

            // Si no tengo nuevo precio de venta, seteo margenes nulos y salgo
            if ((this.getNewPriceSO() == null) || (this.getNewPriceSO().compareTo(Env.ZERO) <= 0)){
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
                BigDecimal priceFinal = this.getPriceFinal();
                if ((multiplyRate != null) && (multiplyRate.compareTo(Env.ZERO) > 0)){
                    priceFinal = priceFinal.multiply(multiplyRate).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                this.setPriceFinalMargin(((this.getNewPriceSO().multiply(Env.ONEHUNDRED).setScale(2, BigDecimal.ROUND_HALF_UP))
                        .divide(priceFinal, 2, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED));
            }

            // Margen OC
            if ((this.getPricePO() == null) || (this.getPricePO().compareTo(Env.ZERO) <= 0)){
                this.setPricePOMargin(null);
            }
            else{
                BigDecimal pricePO = this.getPricePO();
                if ((multiplyRate != null) && (multiplyRate.compareTo(Env.ZERO) > 0)){
                    pricePO = pricePO.multiply(multiplyRate).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                this.setPricePOMargin(((this.getNewPriceSO().multiply(Env.ONEHUNDRED).setScale(2, BigDecimal.ROUND_HALF_UP))
                        .divide(pricePO, 2, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED));
            }

            // Margen Factura
            if ((this.getPriceInvoiced() == null) || (this.getPriceInvoiced().compareTo(Env.ZERO) <= 0)){
                this.setPriceInvoicedMargin(null);
            }
            else{
                BigDecimal priceInv = this.getPriceInvoiced();
                if ((multiplyRate != null) && (multiplyRate.compareTo(Env.ZERO) > 0)){
                    priceInv = priceInv.multiply(multiplyRate).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                this.setPriceInvoicedMargin(((this.getNewPriceSO().multiply(Env.ONEHUNDRED).setScale(2, BigDecimal.ROUND_HALF_UP))
                        .divide(priceInv, 2, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED));
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

    }

    /***
     * Exploto linea en organizaciones del proceso.
     * Xpande. Created by Gabriel Vila on 7/5/17.
     */
    public void orgsCreate() {

        try{
            MZPreciosProvCab preciosProvCab = (MZPreciosProvCab) this.getZ_PreciosProvCab();
            List<MZPreciosProvOrg> preciosProvOrgs = preciosProvCab.getOrgsSelected();
            for (MZPreciosProvOrg preciosProvOrg: preciosProvOrgs){
                MZPreciosProvLinOrg preciosProvLinOrg = new MZPreciosProvLinOrg(getCtx(), 0, get_TrxName());
                preciosProvLinOrg.setZ_PreciosProvLin_ID(this.get_ID());
                preciosProvLinOrg.setAD_OrgTrx_ID(preciosProvOrg.getAD_OrgTrx_ID());
                preciosProvLinOrg.setC_Currency_ID(this.getC_Currency_ID());
                preciosProvLinOrg.setC_Currency_ID_SO(this.getC_Currency_ID_SO());
                preciosProvLinOrg.setNewPriceSO(this.getNewPriceSO());
                preciosProvLinOrg.setPriceFinal(this.getPriceFinal());
                preciosProvLinOrg.setPriceFinalMargin(this.getPriceFinalMargin());
                preciosProvLinOrg.setPriceList(this.getPriceList());
                preciosProvLinOrg.setPriceSO(this.getPriceSO());
                preciosProvLinOrg.setPricePO(this.getPricePO());
                preciosProvLinOrg.setPricePOMargin(this.getPricePOMargin());
                preciosProvLinOrg.saveEx();
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }


    /***
     * Exploto linea en organizaciones del proceso.
     * Xpande. Created by Gabriel Vila on 7/5/17.
     */
    public void orgsCreateFromProductoSocio(MZProductoSocio productoSocio) {

        try{
            MZPreciosProvCab preciosProvCab = (MZPreciosProvCab) this.getZ_PreciosProvCab();
            List<MZPreciosProvOrg> preciosProvOrgs = preciosProvCab.getOrgsSelected();
            for (MZPreciosProvOrg preciosProvOrg: preciosProvOrgs){

                // Si no esta creada esta organizacion para esta linea, la creo ahora
                MZPreciosProvLinOrg preciosProvLinOrg = this.getOrganizacion(preciosProvOrg.getAD_OrgTrx_ID());
                if ((preciosProvLinOrg == null) || (preciosProvLinOrg.get_ID() <= 0)){
                    preciosProvLinOrg = new MZPreciosProvLinOrg(getCtx(), 0, get_TrxName());
                    preciosProvLinOrg.setZ_PreciosProvLin_ID(this.get_ID());
                    preciosProvLinOrg.setAD_OrgTrx_ID(preciosProvOrg.getAD_OrgTrx_ID());
                }

                // Seteo datos de este producto-socio-org
                MZProductoSocioOrg productoSocioOrg = productoSocio.getOrg(preciosProvOrg.getAD_OrgTrx_ID());
                preciosProvLinOrg.setC_Currency_ID(productoSocio.getC_Currency_ID());
                preciosProvLinOrg.setC_Currency_ID_SO(productoSocio.getC_Currency_ID_SO());
                preciosProvLinOrg.setNewPriceSO(productoSocioOrg.getPriceSO());
                preciosProvLinOrg.setPriceFinal(productoSocioOrg.getPriceFinal());
                preciosProvLinOrg.setPriceFinalMargin(productoSocioOrg.getPriceFinalMargin());
                preciosProvLinOrg.setPriceList(productoSocioOrg.getPriceList());
                preciosProvLinOrg.setPriceSO(productoSocioOrg.getPriceSO());
                preciosProvLinOrg.setPricePO(productoSocioOrg.getPricePO());
                preciosProvLinOrg.setPricePOMargin(productoSocioOrg.getPricePOMargin());
                preciosProvLinOrg.saveEx();
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }


    /***
     * Obtiene y retorna modelo segun organización recibida.
     * Xpande. Created by Gabriel Vila on 7/5/17.
     * @param adOrgTrxID
     * @return
     */
    public MZPreciosProvLinOrg getOrganizacion(int adOrgTrxID) {

        String whereClause = X_Z_PreciosProvLinOrg.COLUMNNAME_Z_PreciosProvLin_ID + " =" + this.get_ID() +
                " AND " + X_Z_PreciosProvLinOrg.COLUMNNAME_AD_OrgTrx_ID + " =" + adOrgTrxID;

        MZPreciosProvLinOrg model = new Query(getCtx(), I_Z_PreciosProvLinOrg.Table_Name, whereClause, get_TrxName()).first();

        return model;
    }

    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {

        if (!success) return success;

        if (newRecord){
            this.orgsCreate();
        }

        return true;
    }

    @Override
    protected boolean beforeDelete() {

        if (!this.isNew()){
            log.saveError("ATENCIÓN", "No es posible Eliminar un Producto Existente.");
            return false;
        }

        return true;
    }
}
