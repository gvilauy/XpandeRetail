package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;
import org.compiere.util.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Segmento asociado a linea de productos asociada a definición de márgenes de proveedor.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 11/22/17.
 */
public class MZMargenProvLineaSet extends X_Z_MargenProvLineaSet {

    public MZMargenProvLineaSet(Properties ctx, int Z_MargenProvLineaSet_ID, String trxName) {
        super(ctx, Z_MargenProvLineaSet_ID, trxName);
    }

    public MZMargenProvLineaSet(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    /***
     * Inserta nuevo producto para este segmento de márgen a proveedor, según id de producto recibido.
     * Xpande. Created by Gabriel Vila on 11/22/17.
     * @param mProductID
     */
    public void insertProduct(int mProductID) {

        try{
            // Si el producto recibido ya existe en este segmento, no hago nada
            MZMargenProvLineaSetProd provLineaSetProd = this.getSetProduct(mProductID);
            if ((provLineaSetProd != null) && (provLineaSetProd.get_ID() > 0)){
                return;
            }

            // Inserto producto en este segmento
            provLineaSetProd = new MZMargenProvLineaSetProd(getCtx(), 0, get_TrxName());
            provLineaSetProd.setM_Product_ID(mProductID);
            provLineaSetProd.setZ_MargenProvLineaSet_ID(this.get_ID());
            provLineaSetProd.saveEx();

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

    /***
     * Obtiene y retorna modelo de producto de este segmento para el id de producto recibido.
     * Xpande. Created by Gabriel Vila on 11/22/17.
     * @param mProductID
     * @return
     */
    private MZMargenProvLineaSetProd getSetProduct(int mProductID) {

        String whereClause = X_Z_MargenProvLineaSetProd.COLUMNNAME_Z_MargenProvLineaSet_ID + " =" + this.get_ID() +
                " AND " + X_Z_MargenProvLineaSetProd.COLUMNNAME_M_Product_ID + " =" + mProductID;

        MZMargenProvLineaSetProd model = new Query(getCtx(), I_Z_MargenProvLineaSetProd.Table_Name, whereClause, get_TrxName()).setOnlyActiveRecords(true).first();

        return model;

    }

    /***
     * Obtiene productos de este segmento asociado y los carga.
     * Xpande. Created by Gabriel Vila on 11/22/17.
     * @return
     */
    public String getProducts() {

        String message = null;

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            sql = " select m_product_id from z_pautacomercialsetprod where z_pautacomercialset_id =" + this.getZ_PautaComercialSet_ID();

        	pstmt = DB.prepareStatement(sql, get_TrxName());
        	rs = pstmt.executeQuery();

        	while(rs.next()){
        	    this.insertProduct(rs.getInt("m_product_id"));
        	}
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
        	rs = null; pstmt = null;
        }

        return message;
    }
}
