package org.xpande.retail.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.xpande.retail.model.MZActualizacionPVP;
import org.xpande.retail.model.MZPreciosProvCab;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Proceso para completar y procesar documento de actualizaciones de PVP con vigencia futura.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 4/14/21.
 */
public class CompleteActPVPVigFut extends SvrProcess {

    @Override
    protected void prepare() {

    }

    @Override
    protected String doIt() throws Exception {

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{

            Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

            sql = " select z_actualizacionpvp_id " +
                    " from z_actualizacionpvp " +
                    " where docstatus ='CO' " +
                    " and vigenciafutura ='Y' " +
                    " and vigenciaprocesada ='N' " +
                    " and validfrom <='" + fechaHoy + "' " +
                    " order by validfrom ";

            pstmt = DB.prepareStatement(sql, get_TrxName());
            rs = pstmt.executeQuery();

            while(rs.next()){

                MZActualizacionPVP actualizacionPVP = new MZActualizacionPVP(getCtx(), rs.getInt("z_actualizacionpvp_id"), get_TrxName());

                Env.setContext(getCtx(), "AD_Client_ID", actualizacionPVP.getAD_Client_ID());

                if ((actualizacionPVP.isVigenciaFutura()) && (!actualizacionPVP.isVigenciaProcesada())){
                    actualizacionPVP.executeComplete();
                    if (actualizacionPVP.getProcessMsg() != null){
                        return "@Error@ " + actualizacionPVP.getProcessMsg();
                    }

                    actualizacionPVP.setVigenciaProcesada(true);
                    actualizacionPVP.saveEx();
                }
            }
        }
        catch (Exception e){
            throw new AdempiereException(e);
        }
        finally {
            DB.close(rs, pstmt);
            rs = null; pstmt = null;
        }

        return "OK";
    }

}
