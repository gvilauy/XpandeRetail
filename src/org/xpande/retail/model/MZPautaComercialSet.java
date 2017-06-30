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
                pricePO = pricePO.multiply(Env.ONE.subtract(dto.getDiscount().divide(Env.ONEHUNDRED, 6, BigDecimal.ROUND_HALF_UP))).setScale(ppi.getPrecisionDecimal(), BigDecimal.ROUND_HALF_UP);
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
            BigDecimal priceFinal = ppi.getPriceFinal();
            if (priceFinal.compareTo(ppi.getPricePO()) > 0){
                priceFinal = ppi.getPricePO();
            }

            for (MZPautaComercialSetDto dtoPago: dtosPago){
                priceFinal = priceFinal.multiply(Env.ONE.subtract(dtoPago.getDiscount().divide(Env.ONEHUNDRED, 6, BigDecimal.ROUND_HALF_UP))).setScale(ppi.getPrecisionDecimal(), BigDecimal.ROUND_HALF_UP);
                ppi.setSumPercentageDiscountsFinal(ppi.getSumPercentageDiscountsFinal().add(dtoPago.getDiscount()));
            }

            ppi.setPriceFinal(priceFinal);

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }


    /***
     * Inserta nuevo producto en este segmento según id de producto recibido.
     * Xpande. Created by Gabriel Vila on 6/29/17.
     * @param mProductID
     */
    public void insertProduct(int mProductID){

        try{

            // Si este segmento es general no hago nada
            if (this.isGeneral()) return;

            // Si el producto recibido ya existe en este segmento, no hago nada
            MZPautaComercialSetProd pautaComercialSetProd = this.getSetProduct(mProductID);
            if ((pautaComercialSetProd != null) && (pautaComercialSetProd.get_ID() > 0)){
                return;
            }

            // Inserto producto en este segmento
            pautaComercialSetProd = new MZPautaComercialSetProd(getCtx(), 0, get_TrxName());
            pautaComercialSetProd.setZ_PautaComercialSet_ID(this.get_ID());
            pautaComercialSetProd.setM_Product_ID(mProductID);
            pautaComercialSetProd.saveEx();

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }


    /***
     * Obtiene y retorna modelo de producto de este segmento para el id de producto recibido.
     * Xpande. Created by Gabriel Vila on 6/29/17.
     * @param mProductID
     * @return
     */
    private MZPautaComercialSetProd getSetProduct(int mProductID) {

        String whereClause = X_Z_PautaComercialSetProd.COLUMNNAME_Z_PautaComercialSet_ID + " =" + this.get_ID() +
                " AND " + X_Z_PautaComercialSetProd.COLUMNNAME_M_Product_ID + " =" + mProductID;

        MZPautaComercialSetProd model = new Query(getCtx(), I_Z_PautaComercialSetProd.Table_Name, whereClause, get_TrxName()).setOnlyActiveRecords(true).first();

        return model;

    }

}
