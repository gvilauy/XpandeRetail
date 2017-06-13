package org.xpande.retail.process;

import org.compiere.process.SvrProcess;
import org.xpande.retail.model.MZPreciosProvCab;

/**
 * Proceso de interface de precios de proveedor
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/13/17.
 */
public class InterfacePreciosProveedor extends SvrProcess{

    private MZPreciosProvCab model = null;

    @Override
    protected void prepare() {
        this.model = new MZPreciosProvCab(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        model.execute();

        return "OK";
    }
}
