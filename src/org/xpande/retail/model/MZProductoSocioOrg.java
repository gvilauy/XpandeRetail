package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Organización con precios diferenciales de compra y/o venta para un producto-socio de negocio.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/19/17.
 */
public class MZProductoSocioOrg extends X_Z_ProductoSocioOrg {

    public MZProductoSocioOrg(Properties ctx, int Z_ProductoSocioOrg_ID, String trxName) {
        super(ctx, Z_ProductoSocioOrg_ID, trxName);
    }

    public MZProductoSocioOrg(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Metodo que calcula y setea margenes de esta linea.
     * Xpande. Created by Gabriel Vila on 6/23/17.
     */
    public void calculateMargins() {

        try{

            // Si no tengo nuevo precio de venta, seteo margenes nulos y salgo
            if ((this.getPriceSO() == null) || (this.getPriceSO().compareTo(Env.ZERO) <= 0)){
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
                this.setPriceFinalMargin(((this.getPriceSO().multiply(Env.ONEHUNDRED).setScale(2, BigDecimal.ROUND_HALF_UP))
                        .divide(this.getPriceFinal(), 2, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED));
            }

            // Margen OC
            if ((this.getPricePO() == null) || (this.getPricePO().compareTo(Env.ZERO) <= 0)){
                this.setPricePOMargin(null);
            }
            else{
                this.setPricePOMargin(((this.getPriceSO().multiply(Env.ONEHUNDRED).setScale(2, BigDecimal.ROUND_HALF_UP))
                        .divide(this.getPricePO(), 2, BigDecimal.ROUND_HALF_UP)).subtract(Env.ONEHUNDRED));
            }

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

    }


}
