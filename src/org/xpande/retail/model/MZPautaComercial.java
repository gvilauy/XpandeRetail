package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.xpande.retail.utils.ProductPricesInfo;

import java.math.BigDecimal;
import java.sql.ResultSet;
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


    public ProductPricesInfo calculatePrices(int mProductID, BigDecimal priceList) {

        ProductPricesInfo ppi = null;

        try{
            // Obtengo segmentos para el producto recibido

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return ppi;
    }


    /***
     * Calcula y retorna información relacionada a precios de compra según un precio de lista base.
     * Pueden haber segmentos especiales a considerar en el cálculo.
     * No hay producto determinado.
     * @param priceList
     * @param setEspecialID_1
     * @param setEspecialID_2
     * @return
     */
    public ProductPricesInfo calculatePrices(BigDecimal priceList, int precisionDecimalCompra, int setEspecialID_1, int setEspecialID_2) {

        ProductPricesInfo ppi = null;

        try{

            // Verifico que esta pauta esta activa y este aplicada correctamente
            if ((!this.isActive()) || (this.isConfirmed())){
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
            ppi.setPrecisionDecimal(precisionDecimalCompra);
            ppi.setSumPercentageDiscountsOC(Env.ZERO);
            ppi.setSumPercentageDiscountsFinal(Env.ZERO);
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
     * @return
     */
    public String applyPauta() {

        String message = null;

        try{

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return message;
    }
}
