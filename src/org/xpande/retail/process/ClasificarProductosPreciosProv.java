package org.xpande.retail.process;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.xpande.retail.model.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Proceso para clasificar productos en el documento de gestión de precios de proveedor.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/23/17.
 */
public class ClasificarProductosPreciosProv extends SvrProcess {

    private int zSeccionID = 0;
    private int zRubroID = 0;
    private int zFamiliaID = 0;
    private int zSubfamiliaID = 0;
    private int cTaxCategoryID = 0;
    private int cTaxCategoryEspecialID = 0;
    private int cUomID = 0;
    private boolean clasificarTodosExistentes = false;
    private boolean clasificarTodosNuevos = false;

    private MZPreciosProvCab preciosProvCab = null;

    @Override
    protected void prepare() {

        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){

            String name = para[i].getParameterName();

            if (name != null){

                if (para[i].getParameter() != null){

                    if (name.trim().equalsIgnoreCase(X_Z_ProductoSeccion.COLUMNNAME_Z_ProductoSeccion_ID)){
                        this.zSeccionID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase(X_Z_ProductoRubro.COLUMNNAME_Z_ProductoRubro_ID)){
                        this.zRubroID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase(X_Z_ProductoFamilia.COLUMNNAME_Z_ProductoFamilia_ID)){
                        this.zFamiliaID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase(X_Z_ProductoSubfamilia.COLUMNNAME_Z_ProductoSubfamilia_ID)){
                        this.zSubfamiliaID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase(X_Z_PreciosProvLin.COLUMNNAME_C_TaxCategory_ID)){
                        this.cTaxCategoryID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase(X_Z_PreciosProvLin.COLUMNNAME_C_TaxCategory_ID_2)){
                        this.cTaxCategoryEspecialID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase(X_Z_PreciosProvLin.COLUMNNAME_C_UOM_ID)){
                        this.cUomID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("IsClassified")){
                        this.clasificarTodosExistentes = ((String)para[i].getParameter()).equalsIgnoreCase("Y") ? true : false;
                    }
                    else if (name.trim().equalsIgnoreCase("IsClassified2")){
                        this.clasificarTodosNuevos = ((String)para[i].getParameter()).equalsIgnoreCase("Y") ? true : false;
                    }
                }
            }
        }

        this.preciosProvCab = new MZPreciosProvCab(getCtx(), this.getRecord_ID(), get_TrxName());

    }

    @Override
    protected String doIt() throws Exception {

        List<MZPreciosProvLin> preciosProvLinList = null;

        // Clasifico Productos Existentes
        preciosProvLinList = this.preciosProvCab.getLinesToClassified(false, this.clasificarTodosExistentes);
        for (MZPreciosProvLin preciosProvLin: preciosProvLinList){
            if (this.zSeccionID > 0) preciosProvLin.setZ_ProductoSeccion_ID(this.zSeccionID);
            if (this.zRubroID > 0) preciosProvLin.setZ_ProductoRubro_ID(this.zRubroID);
            if (this.zFamiliaID > 0) preciosProvLin.setZ_ProductoFamilia_ID(this.zFamiliaID);
            if (this.zSubfamiliaID > 0) preciosProvLin.setZ_ProductoSubfamilia_ID(this.zSubfamiliaID);
            if (this.cTaxCategoryID > 0) preciosProvLin.setC_TaxCategory_ID(this.cTaxCategoryID);
            if (this.cTaxCategoryEspecialID > 0) preciosProvLin.setC_TaxCategory_ID_2(this.cTaxCategoryEspecialID);
            if (this.cUomID > 0) preciosProvLin.setC_UOM_ID(this.cUomID);

            if (preciosProvLin.is_Changed()){
                preciosProvLin.saveEx();
            }
        }

        // Clasifico Productos Nuevos
        preciosProvLinList = this.preciosProvCab.getLinesToClassified(true, this.clasificarTodosNuevos);
        for (MZPreciosProvLin preciosProvLin: preciosProvLinList){
            if (this.zSeccionID > 0) preciosProvLin.setZ_ProductoSeccion_ID(this.zSeccionID);
            if (this.zRubroID > 0) preciosProvLin.setZ_ProductoRubro_ID(this.zRubroID);
            if (this.zFamiliaID > 0) preciosProvLin.setZ_ProductoFamilia_ID(this.zFamiliaID);
            if (this.zSubfamiliaID > 0) preciosProvLin.setZ_ProductoSubfamilia_ID(this.zSubfamiliaID);
            if (this.cTaxCategoryID > 0) preciosProvLin.setC_TaxCategory_ID(this.cTaxCategoryID);
            if (this.cTaxCategoryEspecialID > 0) preciosProvLin.setC_TaxCategory_ID_2(this.cTaxCategoryEspecialID);
            if (this.cUomID > 0) preciosProvLin.setC_UOM_ID(this.cUomID);

            if (preciosProvLin.is_Changed()){
                preciosProvLin.saveEx();
            }
        }

        // Desmarco el check de clasificar en todas las lineas para resetear
        DB.executeUpdateEx(" update z_preciosprovlin set isclassified='N' where z_preciosprovcab_id =" + this.preciosProvCab.get_ID(), get_TrxName());

        return "OK";
    }
}
