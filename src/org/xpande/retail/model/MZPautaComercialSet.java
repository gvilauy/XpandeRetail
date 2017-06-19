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
 * Modelo para sets o grupos de pautas comerciales en el módulo de Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/16/17.
 */
public class MZPautaComercialSet extends X_Z_PautaComercialSet {

    public MZPautaComercialSet(Properties ctx, int Z_PautaComercialSet_ID, String trxName) {
        super(ctx, Z_PautaComercialSet_ID, trxName);
    }

    public MZPautaComercialSet(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Obtiene y retorna lista de descuentos pertenecientes a este segmento y que aplican a la FACTURA.
     * Xpande. Created by Gabriel Vila on 6/18/17.
     * @return
     */
    public List<MZPautaComercialSetDto> getInvoiceDiscounts() {

        String whereClause = X_Z_PautaComercialSetDto.COLUMNNAME_Z_PautaComercialSet_ID + " =" + this.get_ID() +
                " AND " + X_Z_PautaComercialSetDto.COLUMNNAME_DiscountType +
                " IN('" + X_Z_PautaComercialSetDto.DISCOUNTTYPE_OPERATIVOENFACTURA + "','" +
                X_Z_PautaComercialSetDto.DISCOUNTTYPE_FINANCIEROENFACTURA + "')";

        List<MZPautaComercialSetDto> lines = new Query(getCtx(), I_Z_PautaComercialSetDto.Table_Name, whereClause, get_TrxName())
                .setOnlyActiveRecords(true).list();

        return lines;
    }

    /***
     * Obtiene y retorna lista de descuentos pertenecientes a este segmento y que aplican al PAGO.
     * Xpande. Created by Gabriel Vila on 6/18/17.
     * @return
     */
    public List<MZPautaComercialSetDto> getPaymentDiscounts() {

        String whereClause = X_Z_PautaComercialSetDto.COLUMNNAME_Z_PautaComercialSet_ID + " =" + this.get_ID() +
                " AND " + X_Z_PautaComercialSetDto.COLUMNNAME_DiscountType +
                " IN('" + X_Z_PautaComercialSetDto.DISCOUNTTYPE_NOTADECREDITOALPAGO + "','" +
                X_Z_PautaComercialSetDto.DISCOUNTTYPE_FINANCIEROALPAGO + "','" +
                X_Z_PautaComercialSetDto.DISCOUNTTYPE_RETORNO + "')";


        List<MZPautaComercialSetDto> lines = new Query(getCtx(), I_Z_PautaComercialSetDto.Table_Name, whereClause, get_TrxName())
                .setOnlyActiveRecords(true).list();

        return lines;
    }

    /***
     * Calcula y retorna por valor, los precios de compra y demas atributos.
     * @param ppi
     */
    public void calculatePrices(ProductPricesInfo ppi) {

        try{



            // Recorro descuentos de este segmento que aplican a la FACTURA y calculo precio OC
            List<MZPautaComercialSetDto> dtos = this.getInvoiceDiscounts();
            if (dtos.size() > 1){
                ppi.setCascadeDiscountsOC(true);
            }
            else{
                ppi.setCascadeDiscountsOC(false);
            }
            BigDecimal pricePO = ppi.getPricePO();
            for (MZPautaComercialSetDto dto: dtos){
                pricePO = pricePO.multiply(Env.ONE.subtract(dto.getDiscount().divide(Env.ONEHUNDRED, 2, BigDecimal.ROUND_HALF_UP))).setScale(ppi.getPrecisionDecimal(), BigDecimal.ROUND_HALF_UP);
                ppi.setSumPercentageDiscountsOC(ppi.getSumPercentageDiscountsOC().add(dto.getDiscount()));
            }

            ppi.setPricePO(pricePO);

            // Recorro descuentos de este segmento que aplican al PAGO y calculo precio Final
            List<MZPautaComercialSetDto> dtosPago = this.getPaymentDiscounts();
            if (dtosPago.size() > 1){
                ppi.setCascadeDiscountsFinal(true);
            }
            else{
                ppi.setCascadeDiscountsFinal(false);
            }
            BigDecimal priceFinal = ppi.getPricePO();
            for (MZPautaComercialSetDto dtoPago: dtosPago){
                priceFinal = priceFinal.multiply(Env.ONE.subtract(dtoPago.getDiscount().divide(Env.ONEHUNDRED, 2, BigDecimal.ROUND_HALF_UP))).setScale(ppi.getPrecisionDecimal(), BigDecimal.ROUND_HALF_UP);
                ppi.setSumPercentageDiscountsFinal(ppi.getSumPercentageDiscountsFinal().add(dtoPago.getDiscount()));
            }

            ppi.setPriceFinal(priceFinal);

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

}
