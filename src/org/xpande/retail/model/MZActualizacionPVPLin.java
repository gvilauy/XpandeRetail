package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.Query;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

/**
 * Modelo de linea de proceso de actualización PVP.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 7/19/17.
 */
public class MZActualizacionPVPLin extends X_Z_ActualizacionPVPLin {

    public MZActualizacionPVPLin(Properties ctx, int Z_ActualizacionPVPLin_ID, String trxName) {
        super(ctx, Z_ActualizacionPVPLin_ID, trxName);
    }

    public MZActualizacionPVPLin(Properties ctx, ResultSet rs, String trxName) {
        super(ctx, rs, trxName);
    }

    @Override
    protected boolean beforeSave(boolean newRecord) {

        // Si se modifica nuevo precio de venta
        if ((!newRecord) && (is_ValueChanged(X_Z_PreciosProvLin.COLUMNNAME_NewPriceSO))){
            // Refresco precio de venta y margenes en organizaciones asociadas a esta linea (si no es *PVP)
            if (!this.isDistinctPriceSO()){
                this.orgsRefreshSO();
            }
        }

        return true;
    }

    @Override
    protected boolean afterSave(boolean newRecord, boolean success) {

        if (!success) return success;

        if (newRecord){
            this.orgsCreate();
        }

        return true;
    }

    /***
     * Exploto linea en organizaciones del proceso.
     * Xpande. Created by Gabriel Vila on 7/5/17.
     */
    public void orgsCreate() {

        try{
            MZActualizacionPVP actualizacionPVP = (MZActualizacionPVP) this.getZ_ActualizacionPVP();
            List<MZActualizacionPVPOrg> pvpOrgs = actualizacionPVP.getSelectedOrgs();
            for (MZActualizacionPVPOrg pvpOrg: pvpOrgs){
                MZActualizacionPVPLinOrg pvpLinOrg = new MZActualizacionPVPLinOrg(getCtx(), 0, get_TrxName());
                pvpLinOrg.setZ_ActualizacionPVPLin_ID(this.get_ID());
                pvpLinOrg.setAD_OrgTrx_ID(pvpOrg.getAD_OrgTrx_ID());
                pvpLinOrg.setC_Currency_ID(this.getC_Currency_ID());
                pvpLinOrg.setNewPriceSO(this.getNewPriceSO());
                pvpLinOrg.setPriceFinal(this.getPriceFinal());
                pvpLinOrg.setPriceFinalMargin(this.getPriceFinalMargin());
                pvpLinOrg.setPriceSO(this.getPriceSO());
                pvpLinOrg.setPricePO(this.getPricePO());
                pvpLinOrg.setPricePOMargin(this.getPricePOMargin());
                pvpLinOrg.saveEx();
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

    /***
     * Refresco datos de venta y margenes de las organizaciones de esta linea.
     * Xpande. Created by Gabriel Vila on 7/5/17.
     */
    private void orgsRefreshSO() {

        try{
            List<MZActualizacionPVPLinOrg> pvpLinOrgs = this.getOrganizaciones();
            for (MZActualizacionPVPLinOrg pvpLinOrg: pvpLinOrgs){
                pvpLinOrg.setNewPriceSO(this.getNewPriceSO());
                pvpLinOrg.setPriceFinalMargin(this.getPriceFinalMargin());
                pvpLinOrg.setPricePOMargin(this.getPricePOMargin());
                pvpLinOrg.saveEx();
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
    }

    /***
     * Obtiene y retorna lista de organizaciones asociadas a esta linea.
     * Xpande. Created by Gabriel Vila on 7/5/17.
     * @return
     */
    private List<MZActualizacionPVPLinOrg> getOrganizaciones() {

        String whereClause = X_Z_ActualizacionPVPLinOrg.COLUMNNAME_Z_ActualizacionPVPLin_ID + " =" + this.get_ID();

        List<MZActualizacionPVPLinOrg> lines = new Query(getCtx(), I_Z_ActualizacionPVPLinOrg.Table_Name, whereClause, get_TrxName()).setOnlyActiveRecords(true).list();

        return lines;
    }


    /***
     * Obtiene y retorna modelo de organización de esta linea para ID de organizacion recibida.
     * Xpande. Created by Gabriel Vila on 7/20/17.
     * @param adOrgTrxID
     * @return
     */
    public MZActualizacionPVPLinOrg getOrg(int adOrgTrxID) {

        String whereClause = X_Z_ActualizacionPVPLinOrg.COLUMNNAME_Z_ActualizacionPVPLin_ID + " =" + this.get_ID();

        MZActualizacionPVPLinOrg model = new Query(getCtx(), I_Z_ActualizacionPVPLinOrg.Table_Name, whereClause, get_TrxName()).first();

        return model;
    }

}