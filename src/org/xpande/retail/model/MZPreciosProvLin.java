package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPrice;
import org.compiere.util.Env;
import org.xpande.core.model.MZProductoUPC;
import org.xpande.retail.utils.ProductPricesInfo;

import java.math.BigDecimal;
import java.sql.ResultSet;
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

        // Si modifica segmentos de pauta comercial
        if ((is_ValueChanged(X_Z_PreciosProvLin.COLUMNNAME_Z_PautaComercialSet_ID_1))
                || (is_ValueChanged(X_Z_PreciosProvLin.COLUMNNAME_Z_PautaComercialSet_ID_2))){

            // Valido segmentos de pautas comerciales aplicados a este productos
            message = this.validateSegmentos();

            if (message != null){
                log.saveError("ATENCIÓN", message);
                return false;
            }

            // Recalculo precios de compra de esta linea ya que se modificaron los segmentos especiales en la misma
            this.calculatePricesPO(this.getPriceList(), cab.getPrecisionPO(), (MZPautaComercial) cab.getZ_PautaComercial());
        }




        return true;
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
    public void calculatePricesPO(BigDecimal priceList, int precisionDecimalCompra, MZPautaComercial pautaComercial) {

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
                    // Cuando tengo producto, voy a considerar los segmentos que ya se definieron en la pauta
                    ppi = pautaComercial.calculatePrices(this.getM_Product_ID(), this.getPriceList());
                }
                else{
                    // No tengo producto, por lo tanto me baso en el precio de lista y segmentos especiales que pueda tener asociados en esta linea
                    ppi = pautaComercial.calculatePrices(this.getPriceList(), precisionDecimalCompra, this.getZ_PautaComercialSet_ID_1(), this.getZ_PautaComercialSet_ID_2());
                }

                if (ppi != null){

                    // Seteo precios de compra y segmentos si es necesario
                    this.setPricePO(ppi.getPricePO());
                    this.setPriceFinal(ppi.getPriceFinal());

                    if (ppi.getSegmentoGral_ID() > 0) this.setZ_PautaComercialSet_ID_Gen(ppi.getSegmentoGral_ID());
                    if (ppi.getSegmentoEspecial1_ID() > 0) this.setZ_PautaComercialSet_ID_1(ppi.getSegmentoEspecial1_ID());
                    if (ppi.getSegmentoEspecial2_ID() > 0) this.setZ_PautaComercialSet_ID_2(ppi.getSegmentoEspecial2_ID());

                }
                else{
                    log.saveError("ATENCIÓN", "No se obtuvieron precios de compra para la Pauta Comercial seleccionada.");
                    return;
                }
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }


    public void calculatePricesSO(BigDecimal priceSO, int precisionDecimalVenta) {

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
                    prod.setC_UOM_ID(this.getC_UOM_ID());
                    prod.setIsSold(true);
                    prod.setIsPurchased(true);
                    prod.setIsStocked(true);
                    if (this.getInternalCode() != null){
                        String value = this.getInternalCode().trim().toUpperCase();
                        if (!value.equalsIgnoreCase("")){
                            prod.setValue(value);
                        }
                    }
                }
            }

            // Seteo atributos al producto que el usuario pudo haber modificado en este documento
            prod.setName(this.getName().trim().toUpperCase());
            prod.set_ValueOfColumn(X_Z_ProductoSeccion.COLUMNNAME_Z_ProductoSeccion_ID, this.getZ_ProductoSeccion_ID());
            prod.set_ValueOfColumn(X_Z_ProductoRubro.COLUMNNAME_Z_ProductoRubro_ID, this.getZ_ProductoRubro_ID());
            prod.set_ValueOfColumn(X_Z_ProductoFamilia.COLUMNNAME_Z_ProductoFamilia_ID, this.getZ_ProductoFamilia_ID());
            prod.set_ValueOfColumn(X_Z_ProductoSubfamilia.COLUMNNAME_Z_ProductoSubfamilia_ID, this.getZ_ProductoSubfamilia_ID());
            prod.setC_TaxCategory_ID(this.getC_TaxCategory_ID());
            prod.set_ValueOfColumn(X_Z_PreciosProvLin.COLUMNNAME_C_TaxCategory_ID_2, this.getC_TaxCategory_ID_2());
            if (this.getDescription() != null){
                prod.setDescription(this.getDescription().trim().toUpperCase());
            }
            prod.saveEx();

            // Para nuevo producto, tengo o no asociado uno existente
            if (this.isNew()){
                // Agrego nuevo UPC al producto
                MZProductoUPC pupc = new MZProductoUPC(getCtx(), 0, get_TrxName());
                pupc.setM_Product_ID(prod.get_ID());
                pupc.setUPC(this.getUPC());
                pupc.saveEx();
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
     * Setea precios de compra y demas datos según información contenida en esta linea de documento.
     * Procedimiento:
     * 1. Actualizo o da de alta producto en version de lista de precios de compra
     * 2. Actualiza auditoria de precios de compra del producto
     * 3. Actualiza relación producto-socio-lista-precios del socio de negocio del documento y de todos
     * sus ditribuidores.
     * Xpande. Created by Gabriel Vila on 6/19/17.
     * @param plCompra
     * @param plVersionCompra
     */
    public void setDataPO(MPriceList plCompra, MPriceListVersion plVersionCompra) {

        try{

            // Intento obtener precio de lista actual para el producto de esta linea, en la versión de lista
            // de precios de compra recibida.
            MProductPrice pprice = MProductPrice.get(getCtx(), plVersionCompra.get_ID(), this.getM_Product_ID(), get_TrxName());

            // Si no tengo precio para este producto, entonces lo ingreso en la lista
            if ((pprice == null) || (pprice.getM_Product_ID() <= 0)){
                pprice = new MProductPrice(plVersionCompra, this.getM_Product_ID(), this.getPriceList(), this.getPriceList(), this.getPriceList());
                pprice.saveEx();
            }
            else {
                // Producto existe en lista de preciosde compra
                // Actualizo precio de lista si el precio cambio
                if (pprice.getPriceList().compareTo(this.getPriceList()) != 0){
                    pprice.setPriceList(this.getPriceList());
                    pprice.setPriceStd(this.getPriceList());
                    pprice.setPriceLimit(this.getPriceList());
                    pprice.saveEx();
                }
            }



        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }
}
