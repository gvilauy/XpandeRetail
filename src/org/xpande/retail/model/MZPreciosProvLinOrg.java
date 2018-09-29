package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.xpande.retail.utils.ProductPricesInfo;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para diferencial de organizaciones para linea de documento de gestión de precios por proveedor.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/23/17.
 */
public class MZPreciosProvLinOrg extends X_Z_PreciosProvLinOrg {

    public MZPreciosProvLinOrg(Properties ctx, int Z_PreciosProvLinOrg_ID, String trxName) {
        super(ctx, Z_PreciosProvLinOrg_ID, trxName);
    }

    public MZPreciosProvLinOrg(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Calcula y setea precios de compra de este organización.
     * Setea ademas segmentos de descuentos que aplican a esta linea si es que los hubiera.
     * Xpande. Created by Gabriel Vila on 6/18/17.
     * @param priceList
     * @param precisionDecimalCompra
     * @param pautaComercial
     */
    public void calculatePricesPO(int precisionDecimalCompra, MZPautaComercial pautaComercial, boolean useLineSegments) {

        try{
            // Precio de lista viene por parametro y será la base para calcular los demas
            this.setPricePO(this.getPriceList());
            this.setPriceFinal(this.getPriceList());

            // Si tengo pauta comercial
            if ((pautaComercial != null) && (pautaComercial.get_ID() > 0)){

                MZPreciosProvLin preciosProvLin = (MZPreciosProvLin) this.getZ_PreciosProvLin();

                // Obtengo toda la información referida a precios de compra desde la pauta comercial
                ProductPricesInfo ppi = null;

                // Si tengo producto, calculo precios basandome en este producto y precio de lista
                if (preciosProvLin.getM_Product_ID() > 0){

                    // Cuando tengo producto y no uso segmentos seleccionados en este linea, voy a considerar los segmentos que ya se definieron en la pauta
                    if (!useLineSegments){
                        ppi = pautaComercial.calculatePrices(preciosProvLin.getM_Product_ID(), this.getPriceList(), precisionDecimalCompra);
                    }
                    else{
                        // Tengo producto, pero el usuario le cambio los segmentos especiales editando esta linea
                        ppi = pautaComercial.calculatePrices(this.getPriceList(), precisionDecimalCompra, preciosProvLin.getZ_PautaComercialSet_ID_1(), preciosProvLin.getZ_PautaComercialSet_ID_2());
                    }
                }
                else{
                    // No tengo producto, por lo tanto me baso en el precio de lista y segmentos especiales que pueda tener asociados en esta linea
                    ppi = pautaComercial.calculatePrices(this.getPriceList(), precisionDecimalCompra, preciosProvLin.getZ_PautaComercialSet_ID_1(), preciosProvLin.getZ_PautaComercialSet_ID_2());
                }

                if (ppi != null){
                    // Seteo precios de compra y segmentos si es necesario
                    this.setPricePO(ppi.getPricePO());
                    this.setPriceFinal(ppi.getPriceFinal());
                }
                else{
                    throw new AdempiereException("No se pudo setear precio de compra para linea : " + preciosProvLin.get_ID() + ", organización :" + this.getAD_OrgTrx_ID());
                }
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }


    /***
     * Metodo que calcula y setea margenes de esta organizacion.
     * Xpande. Created by Gabriel Vila on 6/23/17.
     */
    public void calculateMargins(BigDecimal multiplyRate) {

        try{

            // Si no tengo nuevo precio de venta, seteo margenes nulos y salgo
            if ((this.getNewPriceSO() == null) || (this.getNewPriceSO().compareTo(Env.ZERO) <= 0)){
                this.setPriceFinalMargin(null);
                this.setPricePOMargin(null);
                return;
            }

            // Si no tengo precio de lista, seteo margenes nulos y salgo
            if ((this.getPriceList() == null) || (this.getPriceList().compareTo(Env.ZERO) <= 0)){
                this.setPriceFinalMargin(null);
                this.setPricePOMargin(null);
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
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

    }

    @Override
    protected boolean beforeSave(boolean newRecord) {

        // Si se modifica precio de lista
        if ((!newRecord) && (is_ValueChanged(X_Z_PreciosProvLin.COLUMNNAME_PriceList))){

            // Si la linea de este producto es *PC
            MZPreciosProvLin preciosProvLin = (MZPreciosProvLin) this.getZ_PreciosProvLin();
            if (preciosProvLin.isDistinctPricePO()){

                MZPreciosProvCab cab = (MZPreciosProvCab) preciosProvLin.getZ_PreciosProvCab();

                // Recalculo precios de compra de esta linea ya que se modifico el precio de lista base
                this.calculatePricesPO(cab.getPrecisionPO(), (MZPautaComercial) cab.getZ_PautaComercial(), true);

                // Recalculo márgenes
                this.calculateMargins(cab.getRate());
            }
        }

        return true;
    }

}
