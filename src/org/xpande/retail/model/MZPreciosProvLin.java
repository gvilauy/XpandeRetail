package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
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
}
