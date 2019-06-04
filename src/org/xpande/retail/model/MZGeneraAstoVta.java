/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2006-2017 ADempiere Foundation, All Rights Reserved.         *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * or (at your option) any later version.										*
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * or via info@adempiere.net or http://www.adempiere.net/license.html         *
 *****************************************************************************/
package org.xpande.retail.model;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/** Generated Model for Z_GeneraAstoVta
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class MZGeneraAstoVta extends X_Z_GeneraAstoVta implements DocAction, DocOptions {

	/**
	 *
	 */
	private static final long serialVersionUID = 20190416L;

    /** Standard Constructor */
    public MZGeneraAstoVta (Properties ctx, int Z_GeneraAstoVta_ID, String trxName)
    {
      super (ctx, Z_GeneraAstoVta_ID, trxName);
    }

    /** Load Constructor */
    public MZGeneraAstoVta (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

	@Override
	public int customizeValidActions(String docStatus, Object processing, String orderType, String isSOTrx, int AD_Table_ID, String[] docAction, String[] options, int index) {

		int newIndex = 0;

		if ((docStatus.equalsIgnoreCase(STATUS_Drafted))
				|| (docStatus.equalsIgnoreCase(STATUS_Invalid))
				|| (docStatus.equalsIgnoreCase(STATUS_InProgress))){

			options[newIndex++] = DocumentEngine.ACTION_Complete;

		}
		else if (docStatus.equalsIgnoreCase(STATUS_Completed)){

			//options[newIndex++] = DocumentEngine.ACTION_ReActivate;
			//options[newIndex++] = DocumentEngine.ACTION_Void;
		}

		return newIndex;
	}


	/**
	 * 	Get Document Info
	 *	@return document info (untranslated)
	 */
	public String getDocumentInfo()
	{
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		return dt.getName() + " " + getDocumentNo();
	}	//	getDocumentInfo

	/**
	 * 	Create PDF
	 *	@return File or null
	 */
	public File createPDF ()
	{
		try
		{
			File temp = File.createTempFile(get_TableName() + get_ID() +"_", ".pdf");
			return createPDF (temp);
		}
		catch (Exception e)
		{
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;
	}	//	getPDF

	/**
	 * 	Create PDF file
	 *	@param file output file
	 *	@return file if success
	 */
	public File createPDF (File file)
	{
	//	ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.INVOICE, getC_Invoice_ID());
	//	if (re == null)
			return null;
	//	return re.getPDF(file);
	}	//	createPDF

	
	/**************************************************************************
	 * 	Process document
	 *	@param processAction document action
	 *	@return true if performed
	 */
	public boolean processIt (String processAction)
	{
		m_processMsg = null;
		DocumentEngine engine = new DocumentEngine (this, getDocStatus());
		return engine.processIt (processAction, getDocAction());
	}	//	processIt
	
	/**	Process Message 			*/
	private String		m_processMsg = null;
	/**	Just Prepared Flag			*/
	private boolean		m_justPrepared = false;

	/**
	 * 	Unlock Document.
	 * 	@return true if success 
	 */
	public boolean unlockIt()
	{
		log.info("unlockIt - " + toString());
	//	setProcessing(false);
		return true;
	}	//	unlockIt
	
	/**
	 * 	Invalidate Document
	 * 	@return true if success 
	 */
	public boolean invalidateIt()
	{
		log.info("invalidateIt - " + toString());
		setDocAction(DOCACTION_Prepare);
		return true;
	}	//	invalidateIt
	
	/**
	 *	Prepare Document
	 * 	@return new status (In Progress or Invalid) 
	 */
	public String prepareIt()
	{
		log.info(toString());
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());

		/*
		//	Std Period open?
		if (!MPeriod.isOpen(getCtx(), getDateDoc(), dt.getDocBaseType(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return DocAction.STATUS_Invalid;
		}
		*/


		//	Add up Amounts
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		m_justPrepared = true;
		if (!DOCACTION_Complete.equals(getDocAction()))
			setDocAction(DOCACTION_Complete);
		return DocAction.STATUS_InProgress;
	}	//	prepareIt
	
	/**
	 * 	Approve Document
	 * 	@return true if success 
	 */
	public boolean  approveIt()
	{
		log.info("approveIt - " + toString());
		setIsApproved(true);
		return true;
	}	//	approveIt
	
	/**
	 * 	Reject Approval
	 * 	@return true if success 
	 */
	public boolean rejectIt()
	{
		log.info("rejectIt - " + toString());
		setIsApproved(false);
		return true;
	}	//	rejectIt
	
	/**
	 * 	Complete Document
	 * 	@return new status (Complete, In Progress, Invalid, Waiting ..)
	 */
	public String completeIt()
	{
		//	Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		if (m_processMsg != null)
			return DocAction.STATUS_Invalid;
		
		//	Implicit Approval
		if (!isApproved())
			approveIt();
		log.info(toString());
		//
		
		//	User Validation
		String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		if (valid != null)
		{
			m_processMsg = valid;
			return DocAction.STATUS_Invalid;
		}
		//	Set Definitive Document No
		setDefiniteDocumentNo();

		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}	//	completeIt
	
	/**
	 * 	Set the definite document number after completed
	 */
	private void setDefiniteDocumentNo() {
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());
		if (dt.isOverwriteDateOnComplete()) {
			setDateDoc(new Timestamp(System.currentTimeMillis()));
		}
		if (dt.isOverwriteSeqOnComplete()) {
			String value = null;
			int index = p_info.getColumnIndex("C_DocType_ID");
			if (index == -1)
				index = p_info.getColumnIndex("C_DocTypeTarget_ID");
			if (index != -1)		//	get based on Doc Type (might return null)
				value = DB.getDocumentNo(get_ValueAsInt(index), get_TrxName(), true);
			if (value != null) {
				setDocumentNo(value);
			}
		}
	}

	/**
	 * 	Void Document.
	 * 	Same as Close.
	 * 	@return true if success 
	 */
	public boolean voidIt()
	{
		log.info("voidIt - " + toString());
		return closeIt();
	}	//	voidIt
	
	/**
	 * 	Close Document.
	 * 	Cancel not delivered Qunatities
	 * 	@return true if success 
	 */
	public boolean closeIt()
	{
		log.info("closeIt - " + toString());

		//	Close Not delivered Qty
		setDocAction(DOCACTION_None);
		return true;
	}	//	closeIt
	
	/**
	 * 	Reverse Correction
	 * 	@return true if success 
	 */
	public boolean reverseCorrectIt()
	{
		log.info("reverseCorrectIt - " + toString());
		return false;
	}	//	reverseCorrectionIt
	
	/**
	 * 	Reverse Accrual - none
	 * 	@return true if success 
	 */
	public boolean reverseAccrualIt()
	{
		log.info("reverseAccrualIt - " + toString());
		return false;
	}	//	reverseAccrualIt
	
	/** 
	 * 	Re-activate
	 * 	@return true if success 
	 */
	public boolean reActivateIt()
	{
		log.info("reActivateIt - " + toString());
		setProcessed(false);
		if (reverseCorrectIt())
			return true;
		return false;
	}	//	reActivateIt
	
	
	/*************************************************************************
	 * 	Get Summary
	 *	@return Summary of Document
	 */
	public String getSummary()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getDocumentNo());
	//	sb.append(": ")
	//		.append(Msg.translate(getCtx(),"TotalLines")).append("=").append(getTotalLines())
	//		.append(" (#").append(getLines(false).length).append(")");
		//	 - Description
		if (getDescription() != null && getDescription().length() > 0)
			sb.append(" - ").append(getDescription());
		return sb.toString();
	}	//	getSummary

	/**
	 * 	Get Process Message
	 *	@return clear text error message
	 */
	public String getProcessMsg()
	{
		return m_processMsg;
	}	//	getProcessMsg
	
	/**
	 * 	Get Document Owner (Responsible)
	 *	@return AD_User_ID
	 */
	public int getDoc_User_ID()
	{
	//	return getSalesRep_ID();
		return 0;
	}	//	getDoc_User_ID

	/**
	 * 	Get Document Approval Amount
	 *	@return amount
	 */
	public BigDecimal getApprovalAmt()
	{
		return null;	//getTotalLines();
	}	//	getApprovalAmt
	
	/**
	 * 	Get Document Currency
	 *	@return C_Currency_ID
	 */
	public int getC_Currency_ID()
	{
	//	MPriceList pl = MPriceList.get(getCtx(), getM_PriceList_ID());
	//	return pl.getC_Currency_ID();
		return 0;
	}	//	getC_Currency_ID

    @Override
    public String toString()
    {
      StringBuffer sb = new StringBuffer ("MZGeneraAstoVta[")
        .append(getSummary()).append("]");
      return sb.toString();
    }


	@Override
	protected boolean beforeSave(boolean newRecord) {

		if (this.getAD_Org_ID() == 0){
			log.saveError("ATENCIÓN", "Debe Indicar Organización a considerar (no se acepta organización = * )");
			return false;
		}

		// Seteo POS Vendor según organización de este documento
		if ((newRecord) || (is_ValueChanged(X_Z_GeneraAstoVta.COLUMNNAME_AD_Org_ID))){
			MZPosVendorOrg posVendorOrg = MZPosVendor.getByOrg(getCtx(), this.getAD_Org_ID(), null);
			if ((posVendorOrg == null) || (posVendorOrg.get_ID() <= 0)){
				log.saveError("ATENCIÓN", "No hay Proveedor de POS asociado a la Organización seleccionada.");
				return false;
			}
			this.setZ_PosVendorOrg_ID(posVendorOrg.get_ID());
			this.setZ_PosVendor_ID(posVendorOrg.getZ_PosVendor_ID());
		}

		if ((newRecord) || (is_ValueChanged(X_Z_GeneraAstoVta.COLUMNNAME_C_AcctSchema_ID))){
			MAcctSchema acctSchema = (MAcctSchema) this.getC_AcctSchema();
			this.setC_Currency_ID(acctSchema.getC_Currency_ID());
		}

		if (this.getDateAcct() == null){
			this.setDateAcct(this.getDateTo());
		}

		return true;
	}


	/***
	 * Obtiene y carga información resumida de ventas por medios de pago según proveedor de POS.
	 * Xpande. Created by Gabriel Vila on 4/26/19.
	 * @return
	 */
	public String getVentasMedioPago(){

		String message = null;

		try{

			// Elimino registros anteriores
			String action = " delete from " + X_Z_GeneraAstoVtaSumMP.Table_Name +
					" where " + X_Z_GeneraAstoVtaSumMP.COLUMNNAME_Z_GeneraAstoVta_ID + " =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

			if (this.getZ_PosVendor_ID() <= 0){
				return "Falta indicar Proveedor de POS para la Organización seleccionada";
			}

			MZPosVendor posVendor = (MZPosVendor) this.getZ_PosVendor();
			if (posVendor.getValue().equalsIgnoreCase("SISTECO")){
				message = this.getVentasMedioPagoSisteco();
			}
			else if (posVendor.getValue().equalsIgnoreCase("SCANNTECH")){
				message = this.getVentasMedioPagoScanntech();
			}

			// Actualizo monto redondeo
			this.setRedondeo();

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return message;
	}


	/***
	 * Obtiene y carga información de ventas por medios de pago desde SISTECO.
	 * Xpande. Created by Gabriel Vila on 4/29/19.
	 * @return
	 */
	private String getVentasMedioPagoSisteco(){

		String message = null;

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
		    sql = " select a.st_codigomediopago, a.st_nombremediopago, a.st_tipolinea, b.name as nomtipolinea, a.st_tipotarjetacredito, " +
					" a.st_nombretarjeta, a.st_codigomoneda, " +
					" sum(a.st_totalentregado) as st_totalentregado, sum(a.st_totalmppagomoneda) as st_totalmppagomoneda, " +
					" sum(a.st_totalentregadomonedaref) as st_totalentregadomonedaref, sum(a.st_totalmppagomonedaref) as st_totalmppagomonedaref, " +
					" sum(coalesce(a.st_cambio,0)) as st_cambio, sum(a.totalamt) as totalamt " +
					" from zv_sisteco_vtasmpagodet a " +
					" left outer join z_sistecotipolineapazos b on a.st_tipolinea = b.value " +
					" where a.ad_org_id =" + this.getAD_Org_ID() +
					" and a.datetrx ='" + this.getDateTo() + "' " +
					" and b.IsAsientoVtaPOS ='Y' " +
					" group by a.st_codigomediopago, a.st_nombremediopago, a.st_tipolinea, b.name, a.st_tipotarjetacredito, a.st_nombretarjeta, a.st_codigomoneda " +
					" order by a.st_codigomediopago, a.st_nombremediopago, a.st_tipolinea, b.name, a.st_tipotarjetacredito, a.st_nombretarjeta, a.st_codigomoneda ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			MZGeneraAstoVtaSumMP astoVtaSumMP = null;

			while(rs.next()){

				String codigoMP = rs.getString("st_tipotarjetacredito");
				String nombreMP = rs.getString("st_nombretarjeta");

				if ((codigoMP == null) || (codigoMP.trim().equalsIgnoreCase(""))){
					codigoMP = rs.getString("st_codigomediopago");
				}
				if ((nombreMP == null) || (nombreMP.trim().equalsIgnoreCase(""))){
					nombreMP = rs.getString("st_nombremediopago");
				}

				astoVtaSumMP = new MZGeneraAstoVtaSumMP(getCtx(), 0, get_TrxName());
				astoVtaSumMP.setZ_GeneraAstoVta_ID(this.get_ID());
				astoVtaSumMP.setAD_Org_ID(this.getAD_Org_ID());
				astoVtaSumMP.setCodMedioPagoPOS(codigoMP);
				astoVtaSumMP.setNomMedioPagoPOS(nombreMP);
				astoVtaSumMP.setC_Currency_ID(142);
				astoVtaSumMP.setC_Currency_2_ID(100);
				astoVtaSumMP.setCodTipoLineaPOS(rs.getString("st_tipolinea"));
				astoVtaSumMP.setNomTipoLineaPOS(rs.getString("nomtipolinea"));

				String codMoneda = rs.getString("st_codigomoneda");
				if ((codMoneda == null) || (codMoneda.trim().equalsIgnoreCase(""))){
					return "Hay información de ventas por medios de pago que no tiene MONEDA.";
				}

				BigDecimal amt1 = Env.ZERO, amt2 = Env.ZERO;
				BigDecimal currencyRate = Env.ONE;

				if (codMoneda.trim().equalsIgnoreCase("PESOS")){

					amt1 = rs.getBigDecimal("st_totalentregadomonedaref");
					if (amt1 == null) amt1 = Env.ZERO;

					// Resto cambio
					if (rs.getBigDecimal("st_cambio") != null){
						amt1 = amt1.subtract(rs.getBigDecimal("st_cambio"));
					}

				}
				else if (codMoneda.trim().equalsIgnoreCase("DOLARES")){

					amt2 = rs.getBigDecimal("st_totalentregado");
					amt1 = rs.getBigDecimal("st_totalentregadomonedaref");

					if (amt2 == null) amt2 = Env.ZERO;
					if (amt1 == null) amt1 = Env.ZERO;

					if (rs.getBigDecimal("st_totalmppagomoneda").compareTo(Env.ZERO) > 0){
						currencyRate = rs.getBigDecimal("st_totalmppagomonedaref").divide(rs.getBigDecimal("st_totalmppagomoneda"), 3, RoundingMode.HALF_UP);
					}
				}
				else {
					return "Se encontró una Moneda disinta a PESOS o DOLARES y no es posible procesarla : " + codMoneda;
				}

				astoVtaSumMP.setAmtTotal1(amt1);
				astoVtaSumMP.setAmtTotal2(amt2);
				astoVtaSumMP.setCurrencyRate(currencyRate);
				astoVtaSumMP.setAmtTotal(amt1);

				if (rs.getBigDecimal("st_cambio") != null){
					astoVtaSumMP.setChangeAmt(rs.getBigDecimal("st_cambio"));
				}

				astoVtaSumMP.saveEx();
			}

			// Refresco montos en pesos, cuando para el mismo medio de pago pero en dolares, hubo cambio en pesos.
			this.updateMontoCambios();

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


	/***
	 * Obtiene y carga información de ventas por medios de pago desde SCANNTECH.
	 * Xpande. Created by Gabriel Vila on 6/3/19.
	 * @return
	 */
	private String getVentasMedioPagoScanntech(){

		String message = null;

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
			sql = " select coalesce(a.sc_codigocredito, a.sc_codigotipopago) as codmpago,  coalesce(cc.name,mp.name) as nommpago, " +
					" a.sc_codigomoneda, a.sc_cotizacionventa, sum(a.sc_importe) as sc_importe " +
					" from z_stech_tk_movpago a " +
					" left outer join z_stechmediopago mp on a.z_stechmediopago_id = mp.z_stechmediopago_id " +
					" left outer join z_stechcreditos cc on a.z_stechcreditos_id = cc.z_stechcreditos_id " +
					" where a.ad_org_id =" + this.getAD_Org_ID() +
					" and a.datetrx='" + this.getDateTo() + "' " +
					" and mp.IsAsientoVtaPOS ='Y' " +
					" group by 1,2,3,4 " +
					" order by 1,2,3,4 ";

					/*
					" group by a.sc_codigotipopago, mp.name, a.sc_codigocredito, cc.name, a.sc_codigomoneda, a.sc_cotizacionventa " +
					" order by a.sc_codigotipopago, mp.name, a.sc_codigocredito, cc.name, a.sc_codigomoneda, a.sc_cotizacionventa ";

					 */

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			MZGeneraAstoVtaSumMP astoVtaSumMP = null;

			while(rs.next()){

				String codigoMP = rs.getString("codmpago");
				String nombreMP = rs.getString("nommpago");

				/*
				if ((codigoMP == null) || (codigoMP.trim().equalsIgnoreCase(""))){
					codigoMP = rs.getString("sc_codigotipopago");
				}
				if ((nombreMP == null) || (nombreMP.trim().equalsIgnoreCase(""))){
					nombreMP = rs.getString("nomtipopago");
				}
				*/


				astoVtaSumMP = new MZGeneraAstoVtaSumMP(getCtx(), 0, get_TrxName());
				astoVtaSumMP.setZ_GeneraAstoVta_ID(this.get_ID());
				astoVtaSumMP.setAD_Org_ID(this.getAD_Org_ID());
				astoVtaSumMP.setCodMedioPagoPOS(codigoMP);
				astoVtaSumMP.setNomMedioPagoPOS(nombreMP);
				astoVtaSumMP.setC_Currency_ID(142);
				astoVtaSumMP.setC_Currency_2_ID(100);

				String codMoneda = rs.getString("sc_codigomoneda");
				if ((codMoneda == null) || (codMoneda.trim().equalsIgnoreCase(""))){
					return "Hay información de ventas por medios de pago que no tiene MONEDA.";
				}

				BigDecimal amt1 = Env.ZERO, amt2 = Env.ZERO;
				BigDecimal currencyRate = Env.ONE;

				if (codMoneda.trim().equalsIgnoreCase("858")){

					amt1 = rs.getBigDecimal("sc_importe");
					if (amt1 == null) amt1 = Env.ZERO;

				}
				else if (codMoneda.trim().equalsIgnoreCase("840")){

					amt2 = rs.getBigDecimal("sc_importe");
					if (amt2 == null) amt2 = Env.ZERO;

					currencyRate = rs.getBigDecimal("sc_cotizacionventa");
					if (currencyRate == null) currencyRate = Env.ONE;

					amt1 = amt2.multiply(currencyRate).setScale(2, RoundingMode.HALF_UP);
					if (amt1 == null) amt1 = Env.ZERO;
				}
				else {
					return "Se encontró una Moneda disinta a PESOS o DOLARES y no es posible procesarla : " + codMoneda;
				}

				astoVtaSumMP.setAmtTotal1(amt1);
				astoVtaSumMP.setAmtTotal2(amt2);
				astoVtaSumMP.setCurrencyRate(currencyRate);
				astoVtaSumMP.setAmtTotal(amt1);

				astoVtaSumMP.saveEx();
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


	/***
	 * Cuando para un medio de pago, como por ejemplo EFECTIVO, hubo ventas en pesos y en dolares, debo considerar un posible
	 * cambio dado en pesos para las ventas en dolares. Considerarlo significa que debo restarlo a las ventas en pesos de ese
	 * medio de pago.
	 * Xpande. Created by Gabriel Vila on 4/30/19.
	 */
	private void updateMontoCambios() {

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String action = "";

		try{
		    sql = " select codmediopagopos, coalesce(changeamt,0) as changeamt  " +
					" from z_generaastovtasummp " +
					" where z_generaastovta_id =" + this.get_ID() +
					" and amttotal2 > 0 and changeamt > 0 ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){
				action = " update z_generaastovtasummp " +
						 " set amttotal1 = amttotal1 - " + rs.getBigDecimal("changeamt") + ", " +
						 " amttotal = amttotal - " + rs.getBigDecimal("changeamt") +
						 " where z_generaastovta_id =" + this.get_ID() +
						 " and codmediopagopos ='" + rs.getString("codmediopagopos") + "' " +
						 " and amttotal2 <= 0 ";
				DB.executeUpdateEx(action, get_TrxName());
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


	/***
	 * Obtiene y carga información resumida de ventas por impuesto según proveedor de POS.
	 * @return
	 */
	public String getVentasImpuesto(){

		String message = null;

		try{

			// Elimino registros anteriores
			String action = " delete from " + X_Z_GeneraAstoVtaSumTax.Table_Name +
					" where " + X_Z_GeneraAstoVtaSumTax.COLUMNNAME_Z_GeneraAstoVta_ID + " =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

			if (this.getZ_PosVendor_ID() <= 0){
				return "Falta indicar Proveedor de POS para la Organización seleccionada";
			}

			MZPosVendor posVendor = (MZPosVendor) this.getZ_PosVendor();
			if (posVendor.getValue().equalsIgnoreCase("SISTECO")){
				message = this.getVentasImpuestoSisteco();
			}
			else if (posVendor.getValue().equalsIgnoreCase("SCANNTECH")){
				message = this.getVentasImpuestoScanntech();
			}

			// Actualizo monto redondeo
			this.setRedondeo();

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

		return message;
	}

	/***
	 * Setea importe de Redondeo, producto de total medios de pago menos total impuestos.
	 * Xpande. Created by Gabriel Vila on 5/16/19.
	 */
	private void setRedondeo() {

		String sql = "";

		try{

			// Obtengo total de medios de pago
			sql = " select sum(amttotal) as total " +
					" from " + X_Z_GeneraAstoVtaSumMP.Table_Name +
					" where " + X_Z_GeneraAstoVtaSumMP.COLUMNNAME_Z_GeneraAstoVta_ID + " =" + this.get_ID();

			BigDecimal amtMediosPago = DB.getSQLValueBDEx(get_TrxName(), sql);
			if (amtMediosPago == null) amtMediosPago = Env.ZERO;

			// Obtengo total de impuestos
			sql = " select sum(taxamt) as total " +
					" from " + X_Z_GeneraAstoVtaSumTax.Table_Name +
					" where " + X_Z_GeneraAstoVtaSumTax.COLUMNNAME_Z_GeneraAstoVta_ID + " =" + this.get_ID();

			BigDecimal amtImpuestos = DB.getSQLValueBDEx(get_TrxName(), sql);
			if (amtImpuestos == null) amtImpuestos = Env.ZERO;

			// Obtengo total base de impuestos
			sql = " select sum(taxbaseamt) as total " +
					" from " + X_Z_GeneraAstoVtaSumTax.Table_Name +
					" where " + X_Z_GeneraAstoVtaSumTax.COLUMNNAME_Z_GeneraAstoVta_ID + " =" + this.get_ID();

			BigDecimal amtBaseImp = DB.getSQLValueBDEx(get_TrxName(), sql);
			if (amtBaseImp == null) amtBaseImp = Env.ZERO;

			this.setAmtRounding(amtMediosPago.subtract(amtBaseImp).subtract(amtImpuestos));
			this.saveEx();

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}


	/***
	 * Obtiene y carga información de ventas por impuesto desde SISTECO.
	 * Xpande. Created by Gabriel Vila on 4/29/19.
	 * @return
	 */
	private String getVentasImpuestoSisteco(){

		String message = null;

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
			sql = " select c_taxcategory_id, name, taxamt, taxbaseamt " +
					" from zv_sisteco_vtastax " +
					" where ad_org_id =" + this.getAD_Org_ID() +
					" and datetrx ='" + this.getDateTo() + "' ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				MZGeneraAstoVtaSumTax astoVtaSumTax = new MZGeneraAstoVtaSumTax(getCtx(), 0, get_TrxName());
				astoVtaSumTax.setZ_GeneraAstoVta_ID(this.get_ID());
				astoVtaSumTax.setAD_Org_ID(this.getAD_Org_ID());
				astoVtaSumTax.setC_TaxCategory_ID(rs.getInt("c_taxcategory_id"));
				astoVtaSumTax.setC_Currency_ID(142);
				astoVtaSumTax.setTaxAmt(rs.getBigDecimal("taxamt"));
				astoVtaSumTax.setTaxBaseAmt(rs.getBigDecimal("taxbaseamt"));
				astoVtaSumTax.saveEx();
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


	/***
	 * Obtiene y carga información de ventas por impuesto desde SCANNTECH
	 * Xpande. Created by Gabriel Vila on 6/4/19.
	 * @return
	 */
	private String getVentasImpuestoScanntech(){

		String message = null;

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
			sql = " select a.c_taxcategory_id, b.name, sum(a.sc_montoiva) as taxamt, " +
					" sum(round((a.sc_importe*100)/122,2)) as taxbaseamt " +
					" from zv_scanntech_detvtas a " +
					" inner join c_taxcategory b on a.c_taxcategory_id = b.c_taxcategory_id " +
					" where a.ad_org_id =" + this.getAD_Org_ID() +
					" and a.datetrx ='" + this.getDateTo() + "' " +
					" group by a.c_taxcategory_id, b.name ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				MZGeneraAstoVtaSumTax astoVtaSumTax = new MZGeneraAstoVtaSumTax(getCtx(), 0, get_TrxName());
				astoVtaSumTax.setZ_GeneraAstoVta_ID(this.get_ID());
				astoVtaSumTax.setAD_Org_ID(this.getAD_Org_ID());
				astoVtaSumTax.setC_TaxCategory_ID(rs.getInt("c_taxcategory_id"));
				astoVtaSumTax.setC_Currency_ID(142);
				astoVtaSumTax.setTaxAmt(rs.getBigDecimal("taxamt"));
				astoVtaSumTax.setTaxBaseAmt(rs.getBigDecimal("taxbaseamt"));
				astoVtaSumTax.saveEx();
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


	/***
	 * Obtiene y retorna modelo resumen de venta por medio de pago según código de medio de pago recibido.
	 * Xpande. Created by Gabriel Vila on 4/29/19.
	 * @param codigoMedioPagoPOS
	 * @return
	 */
	private MZGeneraAstoVtaSumMP getAstoVtaSumMPByCod(String codigoMedioPagoPOS){

		String whereClause = X_Z_GeneraAstoVtaSumMP.COLUMNNAME_Z_GeneraAstoVta_ID + " =" + this.get_ID() +
				" AND " + X_Z_GeneraAstoVtaSumMP.COLUMNNAME_CodMedioPagoPOS + " ='" + codigoMedioPagoPOS + "' ";

		MZGeneraAstoVtaSumMP model = new Query(getCtx(), I_Z_GeneraAstoVtaSumMP.Table_Name, whereClause, get_TrxName()).first();

		return model;
	}


	/***
	 * Obtiene y retorna lista de medios de pago sumarizados asociados a este documento.
	 * Xpande. Created by Gabriel Vila on 5/14/19.
	 * @return
	 */
	public List<MZGeneraAstoVtaSumMP> getLineasMediosPago(){

		String whereClause = X_Z_GeneraAstoVtaSumMP.COLUMNNAME_Z_GeneraAstoVta_ID + " =" + this.get_ID();

		List<MZGeneraAstoVtaSumMP> lines = new Query(getCtx(), I_Z_GeneraAstoVtaSumMP.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}

	/***
	 * Obtiene y retorna lista de impuestos sumarizados asociados a este documento.
	 * Xpande. Created by Gabriel Vila on 5/14/19.
	 * @return
	 */
	public List<MZGeneraAstoVtaSumTax> getLineasImpuestos(){

		String whereClause = X_Z_GeneraAstoVtaSumTax.COLUMNNAME_Z_GeneraAstoVta_ID + " =" + this.get_ID();

		List<MZGeneraAstoVtaSumTax> lines = new Query(getCtx(), I_Z_GeneraAstoVtaSumTax.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}



}