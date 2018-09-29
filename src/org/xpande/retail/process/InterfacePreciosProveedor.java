package org.xpande.retail.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.xpande.retail.model.MZPreciosProvCab;
import org.xpande.retail.model.X_Z_PreciosProvCab;

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

        try{

            // Valido según modalidad de proceso
            if (model.getModalidadPreciosProv().equalsIgnoreCase(X_Z_PreciosProvCab.MODALIDADPRECIOSPROV_ARCHIVODECARGA)){
                // Verifico que se haya seleccionado un archivo
                if (model.getFileName() == null){
                    return "@Error@ Debe indicar archivo a procesar ";
                }
            }
            else if (model.getModalidadPreciosProv().equalsIgnoreCase(X_Z_PreciosProvCab.MODALIDADPRECIOSPROV_LINEADEPRODUCTOS)){
                if (model.getZ_LineaProductoSocio_ID() <= 0){
                    return "@Error@ Debe indicar Linea de Productos a procesar ";
                }
            }

            model.execute();

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return "OK";
    }
}
