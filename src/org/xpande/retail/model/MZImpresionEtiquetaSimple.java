package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MProductPrice;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.xpande.core.utils.SequenceUtils;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Modelo cabezal de impresión simple de etiquetas.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/31/17.
 */
public class MZImpresionEtiquetaSimple extends X_Z_ImpresionEtiquetaSimple {

    public MZImpresionEtiquetaSimple(Properties ctx, int Z_ImpresionEtiquetaSimple_ID, String trxName) {
        super(ctx, Z_ImpresionEtiquetaSimple_ID, trxName);
    }

    public MZImpresionEtiquetaSimple(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {

        if (newRecord){
            // Genera ID para impresión de etiquetas. Este ID es el que se pasa por parametro al reporte.
            int impresionID = SequenceUtils.getNextID_NoTable(get_TrxName(), "impresion_id");
            this.setImpresion_ID(impresionID);
        }

        return true;
    }


    /***
     * Genera registros para impresión de etiquetas.
     * Xpande. Created by Gabriel Vila on 7/12/17.
     */
    public void generatePrintRecords() {

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{

            // Elimino registros de impresion anteriores
            String action = " delete from z_confirmacionetiquetaprint where impresion_id =" + this.getImpresion_ID();
            DB.executeUpdateEx(action, null);

            sql = " select lin.z_impetiqsimplelin_id " +
                    " from z_impetiqsimplelin lin " +
                    " inner join z_impresionetiquetasimple cab on cab.z_impresionetiquetasimple_id = lin.z_impresionetiquetasimple_id " +
                    " inner join m_product prod on lin.m_product_id = prod.m_product_id " +
                    " where cab.z_impresionetiquetasimple_id =" + this.get_ID() +
                    " order by prod.z_productoseccion_id, prod.z_productorubro_id, prod.z_productofamilia_id, prod.z_productosubfamilia_id";

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            while(rs.next()){

                MZImpEtiqSimpleLin etiqSimpleLin = new MZImpEtiqSimpleLin(getCtx(), rs.getInt("z_impetiqsimplelin_id"), get_TrxName());

                // Actualizo precio de venta y validez para este producto en la lista de venta seleccionada
                BigDecimal priceSO = Env.ZERO;
                Timestamp validFrom = null;

                MProductPrice productPrice = MProductPrice.get(getCtx(), this.getM_PriceList_Version_ID(), etiqSimpleLin.getM_Product_ID(), get_TrxName());
                priceSO = productPrice.getPriceList();
                validFrom = (Timestamp)productPrice.get_Value("ValidFrom");

                // Verifico si este producto tiene una oferta vigente para la fecha actual.
                // Si es asi, el precio que tiene que considerarse es el precio oferta y no el precio de lista.
                Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
                MZProductoOferta productoOferta = MZProductoOferta.getByProductDate(getCtx(), etiqSimpleLin.getM_Product_ID(), fechaHoy, fechaHoy, null);
                if ((productoOferta != null) && (productoOferta.get_ID() > 0)){
                    MZOfertaVenta ofertaVenta = (MZOfertaVenta) productoOferta.getZ_OfertaVenta();
                    MZOfertaVentaLin ventaLin = ofertaVenta.getLineByProduct(etiqSimpleLin.getM_Product_ID());
                    if ((ventaLin != null) && (ventaLin.get_ID() > 0)){
                        if ((ventaLin.getNewPriceSO() != null) && (ventaLin.getNewPriceSO().compareTo(Env.ZERO) > 0)){
                            priceSO = ventaLin.getNewPriceSO();
                        }
                    }
                    validFrom = ofertaVenta.getStartDate();
                }

                etiqSimpleLin.setPriceSO(priceSO);
                etiqSimpleLin.setDateValidSO(validFrom);

                // Para este producto debo agregar tantos registros de impresión como cantidad de etiquetas a imprimir
                for (int i = 1; i <= etiqSimpleLin.getQtyCount(); i++){
                    MZConfirmacionEtiquetaPrint etiquetaPrint = new MZConfirmacionEtiquetaPrint(getCtx(), 0, null);
                    etiquetaPrint.setZ_ImpresionEtiquetaSimple_ID(this.get_ID());
                    etiquetaPrint.setImpresion_ID(this.getImpresion_ID());
                    etiquetaPrint.setM_Product_ID(etiqSimpleLin.getM_Product_ID());
                    etiquetaPrint.setPriceSO(etiqSimpleLin.getPriceSO().setScale(2, BigDecimal.ROUND_HALF_UP));
                    etiquetaPrint.setDateValidSO(etiqSimpleLin.getDateValidSO());
                    etiquetaPrint.setC_Currency_ID_SO(this.getC_Currency_ID());
                    etiquetaPrint.saveEx();
                }

                etiqSimpleLin.saveEx();
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
            rs = null; pstmt = null;
        }

    }

}
