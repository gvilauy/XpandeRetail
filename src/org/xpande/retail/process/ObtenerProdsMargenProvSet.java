package org.xpande.retail.process;

import org.compiere.process.SvrProcess;
import org.xpande.retail.model.MZMargenProvLineaSet;

/**
 * Proceso para obtener los productos de un segmento asociado a una definición de márgenes de proveedor.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 11/22/17.
 */
public class ObtenerProdsMargenProvSet extends SvrProcess {

    private MZMargenProvLineaSet provLineaSet = null;

    @Override
    protected void prepare() {
        this.provLineaSet = new MZMargenProvLineaSet(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        String message = this.provLineaSet.getProducts();

        if (message != null){
            return "@Error@ " + message;
        }

        return "OK";

    }
}
