package org.xpande.retail.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MInvoice;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.xpande.retail.model.MZInvoiceBonifica;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Genera lineas de bonificacion en una factura destino a partir de una factura origen del mismo socio de negocio
 * y con documento: Factura Bonificacion.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/1/18.
 */
public class GenerarBonifProv extends SvrProcess {

    private MInvoice invoiceDestino = null;
    private int cBPartnerID = 0;
    private int cDocTypeBonificada = 0;
    private String documentNo = null;

    @Override
    protected void prepare() {

        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){

            String name = para[i].getParameterName();

            if (name != null){
                if (para[i].getParameter() != null){
                    if (name.trim().equalsIgnoreCase("C_BPartner_ID")){
                        this.cBPartnerID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("C_DocTypeTarget_ID")){
                        this.cDocTypeBonificada = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                    else if (name.trim().equalsIgnoreCase("DocumentNoRef")){
                        this.documentNo = ((String) para[i].getParameter()).trim();
                    }
                }
            }
        }

        this.invoiceDestino = new MInvoice(getCtx(), this.getRecord_ID(), get_TrxName());
    }

    @Override
    protected String doIt() throws Exception {

        String sql = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{

            // Obtengo ID de invoice origen (la factura bonificada)
            sql = " select c_invoice_id " +
                    " from c_invoice " +
                    " where docstatus='CO' " +
                    " and c_bpartner_id =" + cBPartnerID +
                    " and c_doctypetarget_id =" + cDocTypeBonificada +
                    " and documentno ='" + documentNo + "'";
            int cInvoiceOrigenID = DB.getSQLValueEx(null, sql);

            if (cInvoiceOrigenID <= 0){
                return "@Error@ " + "No existe Factura de Bonificación para ese Socio de Negocio y número ingresado.";
            }

            // Elimino bonificaciones manuales anteriores
            String action = " delete from z_invoicebonifica where c_invoice_id =" + this.invoiceDestino.get_ID() +
                            " and IsManual ='Y' ";
            DB.executeUpdateEx(action, get_TrxName());

            // Cargo bonificaciones desde invoice origen
            sql = " select m_product_id, qtyentered, c_uom_id " +
                    " from c_invoiceline " +
                    " where c_invoice_id =" + cInvoiceOrigenID +
                    " and m_product_id > 0 " +
                    " and priceentered = 0";

        	pstmt = DB.prepareStatement(sql, get_TrxName());
        	rs = pstmt.executeQuery();

        	while(rs.next()){

                MZInvoiceBonifica invoiceBonifica = new MZInvoiceBonifica(getCtx(), 0, get_TrxName());
                invoiceBonifica.setC_Invoice_ID(this.invoiceDestino.get_ID());
                invoiceBonifica.setM_Product_To_ID(rs.getInt("m_product_id"));
                invoiceBonifica.setQtyReward(rs.getBigDecimal("qtyentered"));
                invoiceBonifica.setIsManual(true);
                invoiceBonifica.saveEx();
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
