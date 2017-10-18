package org.xpande.retail.utils;

import javafx.util.converter.PercentageStringConverter;

import java.math.BigDecimal;

/**
 * Clase con atributos para intercambio de información de precios de compra y pautas comerciales.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/17/17.
 */
public class ProductPricesInfo {

    private int M_Product_ID = 0;
    private int Z_PautaComercial_ID = 0;
    private int SegmentoGral_ID = 0;
    private int SegmentoEspecial1_ID = 0;
    private int SegmentoEspecial2_ID = 0;

    private BigDecimal PriceList = null;
    private BigDecimal PricePO = null;
    private BigDecimal PriceFinal = null;

    private BigDecimal PriceDtoNC = null;
    private int PrecisionDecimal = 2;

    private BigDecimal SumPercentageDiscountsOC = null;
    private BigDecimal SumPercentageDiscountsFinal = null;

    private boolean CascadeDiscountsOC = false;
    private boolean CascadeDiscountsFinal = false;

    public int getPrecisionDecimal() {
        return PrecisionDecimal;
    }

    public void setPrecisionDecimal(int precisionDecimal) {
        PrecisionDecimal = precisionDecimal;
    }

    public int getZ_PautaComercial_ID() {
        return Z_PautaComercial_ID;
    }

    public int getSegmentoGral_ID() {
        return SegmentoGral_ID;
    }

    public int getSegmentoEspecial1_ID() {
        return SegmentoEspecial1_ID;
    }

    public int getSegmentoEspecial2_ID() {
        return SegmentoEspecial2_ID;
    }

    public BigDecimal getPriceList() {
        return PriceList;
    }

    public BigDecimal getPricePO() {
        return PricePO;
    }

    public BigDecimal getPriceFinal() {
        return PriceFinal;
    }

    public BigDecimal getPriceDtoNC() {
        return PriceDtoNC;
    }


    public BigDecimal getSumPercentageDiscountsOC() {
        return SumPercentageDiscountsOC;
    }

    public BigDecimal getSumPercentageDiscountsFinal() {
        return SumPercentageDiscountsFinal;
    }

    public boolean isCascadeDiscountsOC() {
        return CascadeDiscountsOC;
    }

    public boolean isCascadeDiscountsFinal() {
        return CascadeDiscountsFinal;
    }

    public int getM_Product_ID() {
        return M_Product_ID;
    }

    public void setM_Product_ID(int m_Product_ID) {
        M_Product_ID = m_Product_ID;
    }

    public void setZ_PautaComercial_ID(int z_PautaComercial_ID) {
        Z_PautaComercial_ID = z_PautaComercial_ID;
    }

    public void setPriceList(BigDecimal priceList) {
        PriceList = priceList;
    }

    public void setSegmentoGral_ID(int segmentoGral_ID) {
        SegmentoGral_ID = segmentoGral_ID;
    }

    public void setSegmentoEspecial1_ID(int segmentoEspecial1_ID) {
        SegmentoEspecial1_ID = segmentoEspecial1_ID;
    }

    public void setSegmentoEspecial2_ID(int segmentoEspecial2_ID) {
        SegmentoEspecial2_ID = segmentoEspecial2_ID;
    }

    public void setPricePO(BigDecimal pricePO) {
        PricePO = pricePO;
    }

    public void setPriceFinal(BigDecimal priceFinal) {
        PriceFinal = priceFinal;
    }

    public void setPriceDtoNC(BigDecimal priceDtoNC) {
        PriceDtoNC = priceDtoNC;
    }

    public void setSumPercentageDiscountsOC(BigDecimal sumPercentageDiscountsOC) {
        SumPercentageDiscountsOC = sumPercentageDiscountsOC;
    }

    public void setSumPercentageDiscountsFinal(BigDecimal sumPercentageDiscountsFinal) {
        SumPercentageDiscountsFinal = sumPercentageDiscountsFinal;
    }

    public void setCascadeDiscountsOC(boolean cascadeDiscountsOC) {
        CascadeDiscountsOC = cascadeDiscountsOC;
    }

    public void setCascadeDiscountsFinal(boolean cascadeDiscountsFinal) {
        CascadeDiscountsFinal = cascadeDiscountsFinal;
    }
}
