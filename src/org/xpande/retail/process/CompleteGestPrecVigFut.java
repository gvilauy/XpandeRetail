package org.xpande.retail.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.xpande.retail.model.MZPreciosProvCab;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 12/5/18.
 */
public class CompleteGestPrecVigFut extends SvrProcess {

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

            sql = " select z_preciosprovcab_id " +
                    " from z_preciosprovcab " +
                    " where docstatus ='CO' " +
                    " and vigenciafutura ='Y' " +
                    " and vigenciaprocesada ='N' " +
                    " and datevalidpo <='" + fechaHoy + "' " +
                    " order by datevalidpo";

        	pstmt = DB.prepareStatement(sql, get_TrxName());
        	rs = pstmt.executeQuery();

        	while(rs.next()){

                MZPreciosProvCab preciosProvCab = new MZPreciosProvCab(getCtx(), rs.getInt("z_preciosprovcab_id"), get_TrxName());

                Env.setContext(getCtx(), "AD_Client_ID", preciosProvCab.getAD_Client_ID());

                if ((preciosProvCab.isVigenciaFutura()) && (!preciosProvCab.isVigenciaProcesada())){
                    preciosProvCab.executeComplete();
                    if (preciosProvCab.getProcessMsg() != null){
                        return "@Error@ " + preciosProvCab.getProcessMsg();
                    }

                    preciosProvCab.setVigenciaProcesada(true);
                    preciosProvCab.saveEx();
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
