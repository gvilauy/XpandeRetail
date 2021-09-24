package org.xpande.retail.model;

import org.compiere.model.I_M_Product;
import org.compiere.model.MProduct;
import org.xpande.geocom.model.MZGeocomInterfaceOut;
import org.xpande.sisteco.model.MZSistecoInterfaceOut;
import org.xpande.sisteco.model.X_Z_SistecoInterfaceOut;
import org.xpande.stech.model.MZStechInterfaceOut;
import org.xpande.stech.model.X_Z_StechInterfaceOut;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Modelo para la configuracion de impuestos especiales por organización y por producto.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 12/11/18.
 */
public class MZProductoTaxOrg extends X_Z_ProductoTaxOrg {

    public MZProductoTaxOrg(Properties ctx, int Z_ProductoTaxOrg_ID, String trxName) {
        super(ctx, Z_ProductoTaxOrg_ID, trxName);
    }

    public MZProductoTaxOrg(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    @Override
    protected boolean beforeSave(boolean newRecord) {

        // Si corresponde debo generar marcas para el proveedor de POS asociado a la organización de este modelo
        boolean generarMarcaPOS = false;

        // Nuevo registro
        if (newRecord){
            if (this.isActive()){
                // Si tengo valores para impuesto de venta y/o impuesto especial de venta para contribuyentes
                if ((this.getC_TaxCategory_ID() > 0) || (this.getC_TaxCategory_ID_3() > 0)){
                    // Marca a POS
                    generarMarcaPOS = true;
                }
            }
        }
        else{
            // Si cambio el valor de impuesto de venta o del impuesto especial de venta a contribuyentes o de registro activo
            if ((is_ValueChanged(X_Z_ProductoTaxOrg.COLUMNNAME_C_TaxCategory_ID)) || (is_ValueChanged(X_Z_ProductoTaxOrg.COLUMNNAME_C_TaxCategory_ID_3))
                                            || (is_ValueChanged(X_Z_ProductoTaxOrg.COLUMNNAME_IsActive))){
                // Marca a POS
                generarMarcaPOS = true;
            }
        }

        // Si tengo el flag de generar marca para POS
        if (generarMarcaPOS){

            // Genero marca según proveedor de POS asociado a esta organización
            MZPosVendorOrg posVendorOrg = MZPosVendor.getByOrg(getCtx(), this.getAD_OrgTrx_ID(), get_TrxName());
            if ((posVendorOrg != null) && (posVendorOrg.get_ID() > 0)) {

                MZPosVendor posVendor = (MZPosVendor) posVendorOrg.getZ_PosVendor();

                if (posVendor.getValue().trim().equalsIgnoreCase("SISTECO")) {

                    // Marca Update para Sisteco
                    MZSistecoInterfaceOut sistecoInterfaceOut = MZSistecoInterfaceOut.getRecord(getCtx(), I_M_Product.Table_ID, this.getM_Product_ID(), this.getAD_OrgTrx_ID(), get_TrxName());
                    if ((sistecoInterfaceOut != null) && (sistecoInterfaceOut.get_ID() > 0)) {
                        // Proceso segun marca que ya tenía este producto antes de su actualización.
                        // Si marca anterior es CREATE
                        if (sistecoInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_SistecoInterfaceOut.CRUDTYPE_CREATE)) {

                            // No hago nada y respeto primer marca
                            return true;
                        } else if (sistecoInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_SistecoInterfaceOut.CRUDTYPE_DELETE)) {

                            // Si marca anterior es DELETEAR, es porque el producto se inactivo anteriormente. No hago nada y respeto primer marca.
                            return true;

                        }
                    }

                    // Si no tengo marca de update, la creo ahora.
                    if ((sistecoInterfaceOut == null) || (sistecoInterfaceOut.get_ID() <= 0)) {

                        // No existe aun marca de UPDATE sobre este producto, la creo ahora.
                        sistecoInterfaceOut = new MZSistecoInterfaceOut(getCtx(), 0, get_TrxName());
                        sistecoInterfaceOut.setCRUDType(X_Z_SistecoInterfaceOut.CRUDTYPE_UPDATE);
                        sistecoInterfaceOut.setAD_Table_ID(I_M_Product.Table_ID);
                        sistecoInterfaceOut.setSeqNo(20);
                        sistecoInterfaceOut.setRecord_ID(this.getM_Product_ID());
                        sistecoInterfaceOut.setIsPriceChanged(false);
                        sistecoInterfaceOut.setAD_OrgTrx_ID(this.getAD_OrgTrx_ID());
                        sistecoInterfaceOut.saveEx();
                    }
                } else if (posVendor.getValue().trim().equalsIgnoreCase("SCANNTECH")) {

                    // Marca Update para Sisteco
                    MZStechInterfaceOut scanntechInterfaceOut = MZStechInterfaceOut.getRecord(getCtx(), I_M_Product.Table_ID, this.getM_Product_ID(), this.getAD_OrgTrx_ID(), get_TrxName());
                    if ((scanntechInterfaceOut != null) && (scanntechInterfaceOut.get_ID() > 0)) {
                        // Proceso segun marca que ya tenía este producto antes de su actualización.
                        // Si marca anterior es CREATE
                        if (scanntechInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_StechInterfaceOut.CRUDTYPE_CREATE)) {

                            // No hago nada y respeto primer marca
                            return true;
                        } else if (scanntechInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_StechInterfaceOut.CRUDTYPE_DELETE)) {

                            // Si marca anterior es DELETEAR, es porque el producto se inactivo anteriormente.
                            return true;
                        }
                    }

                    // Si no tengo marca de update, la creo ahora.
                    if ((scanntechInterfaceOut == null) || (scanntechInterfaceOut.get_ID() <= 0)) {

                        MProduct product = (MProduct) this.getM_Product();

                        // No existe aun marca de UPDATE sobre este producto, la creo ahora.
                        scanntechInterfaceOut = new MZStechInterfaceOut(getCtx(), 0, get_TrxName());
                        scanntechInterfaceOut.setCRUDType(X_Z_StechInterfaceOut.CRUDTYPE_UPDATE);
                        scanntechInterfaceOut.setAD_Table_ID(I_M_Product.Table_ID);
                        scanntechInterfaceOut.setSeqNo(20);
                        scanntechInterfaceOut.setRecord_ID(this.getM_Product_ID());
                        scanntechInterfaceOut.setIsPriceChanged(false);
                        scanntechInterfaceOut.setAD_OrgTrx_ID(this.getAD_OrgTrx_ID());
                        scanntechInterfaceOut.setEntidadInterPOS(X_Z_StechInterfaceOut.ENTIDADINTERPOS_PRODUCTO);
                        scanntechInterfaceOut.setDescription("PRODUCTO : " + product.getValue() + "- " + product.getName());
                        scanntechInterfaceOut.saveEx();
                    }

                } else if (posVendor.getValue().trim().equalsIgnoreCase("GEOCOM")) {

                    // Marca Update para Geocom
                    MZGeocomInterfaceOut geocomInterfaceOut = MZGeocomInterfaceOut.getRecord(getCtx(), I_M_Product.Table_ID, this.getM_Product_ID(), this.getAD_OrgTrx_ID(), get_TrxName());
                    if ((geocomInterfaceOut != null) && (geocomInterfaceOut.get_ID() > 0)) {
                        // Proceso segun marca que ya tenía este producto antes de su actualización.
                        // Si marca anterior es CREATE
                        if (geocomInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_SistecoInterfaceOut.CRUDTYPE_CREATE)) {

                            // No hago nada y respeto primer marca
                            return true;
                        } else if (geocomInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_SistecoInterfaceOut.CRUDTYPE_DELETE)) {

                            // Si marca anterior es DELETEAR, es porque el producto se inactivo anteriormente. No hago nada y respeto primer marca.
                            return true;

                        }
                    }

                    // Si no tengo marca de update, la creo ahora.
                    if ((geocomInterfaceOut == null) || (geocomInterfaceOut.get_ID() <= 0)) {

                        // No existe aun marca de UPDATE sobre este producto, la creo ahora.
                        geocomInterfaceOut = new MZGeocomInterfaceOut(getCtx(), 0, get_TrxName());
                        geocomInterfaceOut.setCRUDType(X_Z_SistecoInterfaceOut.CRUDTYPE_UPDATE);
                        geocomInterfaceOut.setAD_Table_ID(I_M_Product.Table_ID);
                        geocomInterfaceOut.setSeqNo(20);
                        geocomInterfaceOut.setRecord_ID(this.getM_Product_ID());
                        geocomInterfaceOut.setIsPriceChanged(false);
                        geocomInterfaceOut.setAD_OrgTrx_ID(this.getAD_OrgTrx_ID());
                        geocomInterfaceOut.saveEx();
                    }

                }
            }
        }

        return true;
    }

    @Override
    protected boolean beforeDelete() {

        // Al eliminar una configuración de impuestos diferenciales para un produto - organización,
        // debo si o si actualizar el POS

        // Genero marca para POS según proveedor de POS asociado a esta organización
        MZPosVendorOrg posVendorOrg = MZPosVendor.getByOrg(getCtx(), this.getAD_OrgTrx_ID(), get_TrxName());
        if ((posVendorOrg != null) && (posVendorOrg.get_ID() > 0)){

            MZPosVendor posVendor = (MZPosVendor) posVendorOrg.getZ_PosVendor();

            if (posVendor.getValue().trim().equalsIgnoreCase("SISTECO")){

                // Marca Update para Sisteco
                MZSistecoInterfaceOut sistecoInterfaceOut = MZSistecoInterfaceOut.getRecord(getCtx(), I_M_Product.Table_ID, this.getM_Product_ID(), this.getAD_OrgTrx_ID(), get_TrxName());
                if ((sistecoInterfaceOut != null) && (sistecoInterfaceOut.get_ID() > 0)){
                    // Proceso segun marca que ya tenía este producto antes de su actualización.
                    // Si marca anterior es CREATE
                    if (sistecoInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_SistecoInterfaceOut.CRUDTYPE_CREATE)){

                        // No hago nada y respeto primer marca
                        return true;
                    }
                    else if (sistecoInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_SistecoInterfaceOut.CRUDTYPE_DELETE)){

                        // Si marca anterior es DELETEAR, es porque el producto se inactivo anteriormente. No hago nada y respeto primer marca.
                        return true;

                    }
                }

                // Si no tengo marca de update, la creo ahora.
                if ((sistecoInterfaceOut == null) || (sistecoInterfaceOut.get_ID() <= 0)){

                    // No existe aun marca de UPDATE sobre este producto, la creo ahora.
                    sistecoInterfaceOut = new MZSistecoInterfaceOut(getCtx(), 0, get_TrxName());
                    sistecoInterfaceOut.setCRUDType(X_Z_SistecoInterfaceOut.CRUDTYPE_UPDATE);
                    sistecoInterfaceOut.setAD_Table_ID(I_M_Product.Table_ID);
                    sistecoInterfaceOut.setSeqNo(20);
                    sistecoInterfaceOut.setRecord_ID(this.getM_Product_ID());
                    sistecoInterfaceOut.setIsPriceChanged(false);
                    sistecoInterfaceOut.setAD_OrgTrx_ID(this.getAD_OrgTrx_ID());
                    sistecoInterfaceOut.saveEx();
                }
            }
            else if (posVendor.getValue().trim().equalsIgnoreCase("SCANNTECH")){

                // Marca Update para Sisteco
                MZStechInterfaceOut scanntechInterfaceOut = MZStechInterfaceOut.getRecord(getCtx(), I_M_Product.Table_ID, this.getM_Product_ID(), this.getAD_OrgTrx_ID(), get_TrxName());
                if ((scanntechInterfaceOut != null) && (scanntechInterfaceOut.get_ID() > 0)){
                    // Proceso segun marca que ya tenía este producto antes de su actualización.
                    // Si marca anterior es CREATE
                    if (scanntechInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_StechInterfaceOut.CRUDTYPE_CREATE)){

                        // No hago nada y respeto primer marca
                        return true;
                    }
                    else if (scanntechInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_StechInterfaceOut.CRUDTYPE_DELETE)){

                        // Si marca anterior es DELETEAR, es porque el producto se inactivo anteriormente.
                        return true;
                    }
                }

                // Si no tengo marca de update, la creo ahora.
                if ((scanntechInterfaceOut == null) || (scanntechInterfaceOut.get_ID() <= 0)){

                    MProduct product = (MProduct) this.getM_Product();

                    // No existe aun marca de UPDATE sobre este producto, la creo ahora.
                    scanntechInterfaceOut = new MZStechInterfaceOut(getCtx(), 0, get_TrxName());
                    scanntechInterfaceOut.setCRUDType(X_Z_StechInterfaceOut.CRUDTYPE_UPDATE);
                    scanntechInterfaceOut.setAD_Table_ID(I_M_Product.Table_ID);
                    scanntechInterfaceOut.setSeqNo(20);
                    scanntechInterfaceOut.setRecord_ID(this.getM_Product_ID());
                    scanntechInterfaceOut.setIsPriceChanged(false);
                    scanntechInterfaceOut.setAD_OrgTrx_ID(this.getAD_OrgTrx_ID());
                    scanntechInterfaceOut.setEntidadInterPOS(X_Z_StechInterfaceOut.ENTIDADINTERPOS_PRODUCTO);
                    scanntechInterfaceOut.setDescription("PRODUCTO : " + product.getValue() + "- " + product.getName());
                    scanntechInterfaceOut.saveEx();
                }

            }
            else if (posVendor.getValue().trim().equalsIgnoreCase("GEOCOM")){

                // Marca Update para Geocom
                MZGeocomInterfaceOut geocomInterfaceOut = MZGeocomInterfaceOut.getRecord(getCtx(), I_M_Product.Table_ID, this.getM_Product_ID(), this.getAD_OrgTrx_ID(), get_TrxName());
                if ((geocomInterfaceOut != null) && (geocomInterfaceOut.get_ID() > 0)){
                    // Proceso segun marca que ya tenía este producto antes de su actualización.
                    // Si marca anterior es CREATE
                    if (geocomInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_SistecoInterfaceOut.CRUDTYPE_CREATE)){

                        // No hago nada y respeto primer marca
                        return true;
                    }
                    else if (geocomInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_SistecoInterfaceOut.CRUDTYPE_DELETE)){

                        // Si marca anterior es DELETEAR, es porque el producto se inactivo anteriormente. No hago nada y respeto primer marca.
                        return true;

                    }
                }

                // Si no tengo marca de update, la creo ahora.
                if ((geocomInterfaceOut == null) || (geocomInterfaceOut.get_ID() <= 0)){

                    // No existe aun marca de UPDATE sobre este producto, la creo ahora.
                    geocomInterfaceOut = new MZGeocomInterfaceOut(getCtx(), 0, get_TrxName());
                    geocomInterfaceOut.setCRUDType(X_Z_SistecoInterfaceOut.CRUDTYPE_UPDATE);
                    geocomInterfaceOut.setAD_Table_ID(I_M_Product.Table_ID);
                    geocomInterfaceOut.setSeqNo(20);
                    geocomInterfaceOut.setRecord_ID(this.getM_Product_ID());
                    geocomInterfaceOut.setIsPriceChanged(false);
                    geocomInterfaceOut.setAD_OrgTrx_ID(this.getAD_OrgTrx_ID());
                    geocomInterfaceOut.saveEx();
                }
            }
        }

        return true;
    }
}
