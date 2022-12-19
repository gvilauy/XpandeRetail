package org.xpande.retail.model;

import org.compiere.model.MAcctSchema;
import org.compiere.model.MClient;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.xpande.core.utils.CurrencyUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 12/19/22.
 */
public class MZRegularOfferLine extends X_Z_RegularOfferLine {

    public MZRegularOfferLine(Properties ctx, int Z_RegularOfferLine_ID, String trxName) {
        super(ctx, Z_RegularOfferLine_ID, trxName);
    }

    public MZRegularOfferLine(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {

        if (newRecord) {
            // Si es un registro de ingreso manual
            if (this.isManual()) {
                this.setIsValid(true);
                // Tasa de cambio entre moneda de compra actual (si tengo) y moneda especial de compra
                Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
                BigDecimal rate = Env.ONE;
                int conversionTypeID = 114;
                if ((this.getC_Currency_ID() > 0) && (this.getPurchaseCurrency_ID() > 0)) {
                    if (this.getC_Currency_ID() != this.getPurchaseCurrency_ID()){
                        rate = CurrencyUtils.getCurrencyRate(getCtx(), this.getAD_Client_ID(), 0, this.getC_Currency_ID(),
                                this.getPurchaseCurrency_ID(), conversionTypeID, fechaHoy, null);
                        if (rate == null){
                            log.saveError("Error", "Tasa de Cambio no encontrada.");
                            return false;
                        }
                    }
                }
                this.setCurrencyRate(rate);
            }
        }
        // Valido que este producto no este duplicado en la misma oferta
        String sql = " select max(z_regularofferline_id) as id from z_regularofferline where z_regularoffer_id =" + this.getZ_RegularOffer_ID() +
                " and m_product_id =" + this.getM_Product_ID() + " and z_regularofferline_id != " + this.get_ID();
        int idAux = DB.getSQLValueEx(get_TrxName(), sql);
        if (idAux > 0) {
            log.saveError("Error", "Este producto ya existe en este Oferta");
            return false;
        }

        // Si no es oferta periódica solo de venta
        MZRegularOffer regularOffer = (MZRegularOffer) this.getZ_RegularOffer();
        if (!regularOffer.getRegularOfferType().equalsIgnoreCase(X_Z_RegularOffer.REGULAROFFERTYPE_OfertaSoloDeVenta)) {
            // Validaciones de precio de oferta de compra
            // No tengo moneda oferta de compra
            if (this.getPurchaseCurrency_ID() <= 0) {
                // Precio oferta no puede superar el precio de compra actual
                if (this.getNewPricePO().compareTo(this.getPricePO()) >= 0) {
                    log.saveError("Error", "Precio de Compra Oferta no puede ser mayor o igual al Precio OC actual");
                    return false;
                }
            }
            else {
                // Tengo moneda especial de compra
                // Si ambas monedas son iguales
                if (this.getC_Currency_ID() == this.getPurchaseCurrency_ID()) {
                    // Precio oferta no puede superar el precio de compra actual
                    if (this.getNewPricePO().compareTo(this.getPricePO()) >= 0) {
                        log.saveError("Error", "Precio de Compra Oferta no puede ser mayor o igual al Precio OC actual");
                        return false;
                    }
                }
                else {
                    // Debo llevar precio oferta a la moneda del precio OC para poder validarlo
                    MAcctSchema schema = MClient.get(getCtx(), regularOffer.getAD_Client_ID()).getAcctSchema();
                    BigDecimal priceOfferAux = this.getNewPricePO();
                    if (this.getPurchaseCurrency_ID() == schema.getC_Currency_ID()) {
                        priceOfferAux = priceOfferAux.divide(this.getCurrencyRate(), 2, RoundingMode.HALF_UP);
                    }
                    else {
                        priceOfferAux = priceOfferAux.multiply(this.getCurrencyRate()).setScale(2, RoundingMode.HALF_UP);
                    }
                    // Precio oferta no puede superar el precio de compra actual
                    if (priceOfferAux.compareTo(this.getPricePO()) >= 0) {
                        log.saveError("Error", "Precio de Compra Oferta no puede ser mayor o igual al Precio OC actual");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Obtiene y retorna modelo de organización diferencial asociada a esta linea de oferta periódica.
     * Tanane. Created by Gabriel Vila on 2022-12-06
     * @param adOrgTrxID
     * @return
     */
    public MZRegOfferLineOrg getLineOrg(int adOrgTrxID) {

        String whereClause = X_Z_RegOfferLineOrg.COLUMNNAME_Z_RegularOfferLine_ID + " =" + this.get_ID() +
                " AND " + X_Z_RegOfferLineOrg.COLUMNNAME_AD_OrgTrx_ID + " =" + adOrgTrxID;

        MZRegOfferLineOrg model = new Query(getCtx(), I_Z_RegOfferLineOrg.Table_Name, whereClause, get_TrxName()).first();

        return model;
    }
}
