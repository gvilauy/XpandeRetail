package org.xpande.retail.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo de descuento de un segmento de pauta comercial.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/18/17.
 */
public class MZPautaComercialSetDto extends X_Z_PautaComercialSetDto {

    public MZPautaComercialSetDto(Properties ctx, int Z_PautaComercialSetDto_ID, String trxName) {
        super(ctx, Z_PautaComercialSetDto_ID, trxName);
    }

    public MZPautaComercialSetDto(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

}
