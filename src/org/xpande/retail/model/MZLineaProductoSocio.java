package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Modelo de linea de productos de un socio de negocio.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/21/17.
 */
public class MZLineaProductoSocio extends X_Z_LineaProductoSocio {

    public MZLineaProductoSocio(Properties ctx, int Z_LineaProductoSocio_ID, String trxName) {
        super(ctx, Z_LineaProductoSocio_ID, trxName);
    }

    public MZLineaProductoSocio(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }


    /***
     * Obtiene y retorna modelo para linea de producto y socio de negocio.
     * Xpande. Created by Gabriel Vila on 7/2/17.
     * @param ctx
     * @param zLineaProductoGralID
     * @param cBPartnerID
     * @param trxName
     * @return
     */
    public static MZLineaProductoSocio getByLineaBPartner(Properties ctx, int zLineaProductoGralID, int cBPartnerID, String trxName) {

        String whereClause = X_Z_LineaProductoSocio.COLUMNNAME_Z_LineaProductoGral_ID + " =" + zLineaProductoGralID +
                " AND " + X_Z_LineaProductoSocio.COLUMNNAME_C_BPartner_ID + " =" + cBPartnerID;

        MZLineaProductoSocio model = new Query(ctx, I_Z_LineaProductoSocio.Table_Name, whereClause, trxName).first();

        return model;

    }

    /***
     * Obtiene y retorna lista de distribuidores de esta linea de productos.
     * Xpande. Created by Gabriel Vila on 7/2/17.
     * @return
     */
    public List<MZLineaProductoDistri> getDistribuidores() {

        String whereClause = X_Z_LineaProductoDistri.COLUMNNAME_Z_LineaProductoSocio_ID + " =" + this.get_ID();

        List<MZLineaProductoDistri> lines = new Query(getCtx(), I_Z_LineaProductoDistri.Table_Name, whereClause, get_TrxName()).setOnlyActiveRecords(true).list();

        return lines;
    }


    /***
     * Metodo para crear un nuevo distribuidor de la linea de productos que tiene este socio como dueño.
     * Clona modelos prducto-socio del socio dueño de esta linea, para el ditribuidor.
     * Clona modelos de organizaciones de los prducto-socio antes mencionados.
     * Xpande. Created by Gabriel Vila on 7/2/17.
     * @param cBPartnerDistribuidorID
     * @return
     */
    public String setNuevoDistribuidor(int cBPartnerDistribuidorID) {

        String message = null;

        try{

            // Verifico si el socio de negocio recibido por su ID, ya existe como distribuidor de esta linea de productos
            MZLineaProductoDistri lineaProductoDistri = this.getDistribuidor(cBPartnerDistribuidorID);
            if ((lineaProductoDistri != null) && (lineaProductoDistri.get_ID() > 0)){
                return "Este distribuidor ya esta asociado a esta Linea de Productos del Socio de Negocio.";
            }

            // Instancio modelo de lista de precios de compra y versión vigente de la misma
            MPriceList plCompra = (MPriceList) this.getM_PriceList();
            MPriceListVersion plVersionCompra = plCompra.getPriceListVersion(null);

            // Obtengo Linea de producto de este socio
            MZLineaProductoGral lineaProductoGral = (MZLineaProductoGral)this.getZ_LineaProductoGral();

            // Creo asociación con nuevo distribuidor
            lineaProductoDistri = new MZLineaProductoDistri(getCtx(), 0, get_TrxName());
            lineaProductoDistri.setZ_LineaProductoSocio_ID(this.get_ID());
            lineaProductoDistri.setC_BPartner_ID(cBPartnerDistribuidorID);
            lineaProductoDistri.setIsLockedPO(false);
            lineaProductoDistri.saveEx();

            // Creo asociacion nuevo distribuidor de linea de productos
            MZLineaProductoSocio lineaProductoSocioDistri = new MZLineaProductoSocio(getCtx(), 0, get_TrxName());
            lineaProductoSocioDistri.setZ_LineaProductoGral_ID(lineaProductoGral.get_ID());
            lineaProductoSocioDistri.setC_BPartner_ID(cBPartnerDistribuidorID);
            lineaProductoSocioDistri.setIsOwn(false);
            lineaProductoSocioDistri.setC_BPartnerRelation_ID(this.getC_BPartner_ID());
            lineaProductoSocioDistri.setM_PriceList_ID(lineaProductoDistri.getPlCompra(plCompra.getC_Currency_ID()).get_ID());
            if (this.getZ_PautaComercial_ID() > 0) lineaProductoSocioDistri.setZ_PautaComercial_ID(this.getZ_PautaComercial_ID());
            lineaProductoSocioDistri.setIsLockedPO(false);
            lineaProductoSocioDistri.saveEx();

            // Obtengo lista de producto-socio para socio-linea de productos-lista de precios.
            List<MZProductoSocio> productoSocios = MZProductoSocio.getByBPartnerLineaPriceList(getCtx(), this.getC_BPartner_ID(), this.get_ID(), this.getM_PriceList_ID(), get_TrxName());
            if ((productoSocios == null) || (productoSocios.size() <= 0)){
                throw new AdempiereException("No se obtuvieron Productos para la Linea, Socio de Negocios y Lista de Precios de Compra.");
            }


            // Recorro lista de modelos producto-socio para socio y linea de productos
            for (MZProductoSocio productoSocio: productoSocios){

                // Actualizo lista de precios de compra del distribuidor para este producto (creo lista si no existe)
                lineaProductoDistri.updateProductPriceListPO(plCompra.getC_Currency_ID(), productoSocio.getM_Product_ID(), productoSocio.getPriceList(), productoSocio.getDateValidPO());

                // Nuevo modelo producto-socio para el distribuidor
                MZProductoSocio productoDistribuidor = new MZProductoSocio(getCtx(), 0, get_TrxName());
                productoDistribuidor.setM_Product_ID(productoSocio.getM_Product_ID());
                productoDistribuidor.setC_BPartner_ID(lineaProductoDistri.getC_BPartner_ID());
                productoDistribuidor.setDateValidPO(productoSocio.getDateValidPO());
                productoDistribuidor.setDateValidSO(productoSocio.getDateValidSO());
                productoDistribuidor.setZ_LineaProductoSocio_ID(lineaProductoSocioDistri.get_ID());
                productoDistribuidor.setPriceList(productoSocio.getPriceList());
                productoDistribuidor.setPriceSO(productoSocio.getPriceSO());
                productoDistribuidor.setM_PriceList_ID(lineaProductoDistri.getPlCompra(plCompra.getC_Currency_ID()).get_ID());
                productoDistribuidor.setM_PriceList_Version_ID(lineaProductoDistri.getPlVersionCompra().get_ID());
                productoDistribuidor.setM_PriceList_ID_SO(productoSocio.getM_PriceList_ID_SO());
                productoDistribuidor.setM_PriceList_Version_ID_SO(productoSocio.getM_PriceList_Version_ID_SO());
                productoDistribuidor.setC_Currency_ID(plCompra.getC_Currency_ID());
                productoDistribuidor.setC_Currency_ID_SO(productoSocio.getC_Currency_ID_SO());
                productoDistribuidor.setPricePO(productoSocio.getPricePO());
                productoDistribuidor.setPricePOMargin(productoSocio.getPricePOMargin());
                productoDistribuidor.setPriceFinal(productoSocio.getPriceFinal());
                productoDistribuidor.setPriceFinalMargin(productoSocio.getPriceFinalMargin());

                if (productoSocio.getZ_PautaComercial_ID() > 0){
                    productoDistribuidor.setZ_PautaComercial_ID(productoSocio.getZ_PautaComercial_ID());
                    productoDistribuidor.setTotalDiscountsPO(productoSocio.getTotalDiscountsPO());
                    productoDistribuidor.setTotalDiscountsFinal(productoSocio.getTotalDiscountsFinal());
                }
                if (productoSocio.getZ_PautaComercialSet_ID_Gen() > 0){
                    productoDistribuidor.setZ_PautaComercialSet_ID_Gen(productoSocio.getZ_PautaComercialSet_ID_Gen());
                }
                if (productoSocio.getZ_PautaComercialSet_ID_1() > 0){
                    productoDistribuidor.setZ_PautaComercialSet_ID_1(productoSocio.getZ_PautaComercialSet_ID_1());
                }
                if (productoSocio.getZ_PautaComercialSet_ID_2() > 0){
                    productoDistribuidor.setZ_PautaComercialSet_ID_2(productoSocio.getZ_PautaComercialSet_ID_2());
                }
                productoDistribuidor.saveEx();

                // Clono cada organizacion de este modelo de producto-socio
                List<MZProductoSocioOrg> productoSocioOrgs = productoSocio.getOrgs();
                for (MZProductoSocioOrg productoSocioOrg: productoSocioOrgs){

                    MZProductoSocioOrg productoDistriOrg = new MZProductoSocioOrg(getCtx(), 0, get_TrxName());
                    productoDistriOrg.setZ_ProductoSocio_ID(productoDistribuidor.get_ID());
                    productoDistriOrg.setAD_OrgTrx_ID(productoSocioOrg.getAD_OrgTrx_ID());
                    productoDistriOrg.setDateValidPO(productoSocioOrg.getDateValidPO());
                    productoDistriOrg.setDateValidSO(productoSocioOrg.getDateValidSO());
                    productoDistriOrg.setPriceList(productoSocioOrg.getPriceList());
                    productoDistriOrg.setPriceSO(productoSocioOrg.getPriceSO());
                    productoDistriOrg.setM_PriceList_ID(lineaProductoDistri.getPlCompra(plCompra.getC_Currency_ID()).get_ID());
                    productoDistriOrg.setM_PriceList_Version_ID(lineaProductoDistri.getPlVersionCompra().get_ID());
                    productoDistriOrg.setM_PriceList_ID_SO(productoSocioOrg.getM_PriceList_ID_SO());
                    productoDistriOrg.setM_PriceList_Version_ID_SO(productoSocioOrg.getM_PriceList_Version_ID_SO());
                    productoDistriOrg.setC_Currency_ID(plCompra.getC_Currency_ID());
                    productoDistriOrg.setC_Currency_ID_SO(productoSocioOrg.getC_Currency_ID_SO());
                    productoDistriOrg.setPricePO(productoSocioOrg.getPricePO());
                    productoDistriOrg.setPricePOMargin(productoSocioOrg.getPricePOMargin());
                    productoDistriOrg.setPriceFinal(productoSocioOrg.getPriceFinal());
                    productoDistriOrg.setPriceFinalMargin(productoSocioOrg.getPriceFinalMargin());
                    productoDistriOrg.saveEx();
                }
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return message;
    }


    /***
     * Obtiene y retorna distribuidor de esta linea, según id de distribuidor recibido.
     * Xpande. Created by Gabriel Vila on 7/2/17.
     * @param cBPartnerDistribuidorID
     * @return
     */
    private MZLineaProductoDistri getDistribuidor(int cBPartnerDistribuidorID) {

        String whereClause = X_Z_LineaProductoDistri.COLUMNNAME_Z_LineaProductoSocio_ID + " =" + this.get_ID() +
                " AND " + X_Z_LineaProductoDistri.COLUMNNAME_C_BPartner_ID + " =" + cBPartnerDistribuidorID;

        MZLineaProductoDistri model = new Query(getCtx(), I_Z_LineaProductoDistri.Table_Name, whereClause, get_TrxName()).first();

        return model;
    }
}
