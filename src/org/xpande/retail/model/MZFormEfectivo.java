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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/** Generated Model for Z_FormEfectivo
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class MZFormEfectivo extends X_Z_FormEfectivo implements DocAction, DocOptions {

	/**
	 *
	 */
	private static final long serialVersionUID = 20190829L;

    /** Standard Constructor */
    public MZFormEfectivo (Properties ctx, int Z_FormEfectivo_ID, String trxName)
    {
      super (ctx, Z_FormEfectivo_ID, trxName);
    }

    /** Load Constructor */
    public MZFormEfectivo (Properties ctx, ResultSet rs, String trxName)
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

			options[newIndex++] = DocumentEngine.ACTION_ReActivate;
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

		//	Std Period open?
		if (!MPeriod.isOpen(getCtx(), getDateDoc(), dt.getDocBaseType(), getAD_Org_ID()))
		{
			m_processMsg = "@PeriodClosed@";
			return DocAction.STATUS_Invalid;
		}
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
		log.info(toString());

		// Before Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_VOID);
		if (m_processMsg != null)
			return false;

		// Control de período contable
		MPeriod.testPeriodOpen(getCtx(), this.getDateAcct(), this.getC_DocType_ID(), this.getAD_Org_ID());

		// Elimino asientos contables
		MFactAcct.deleteEx(this.get_Table_ID(), this.get_ID(), get_TrxName());

		// After Void
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_VOID);
		if (m_processMsg != null)
			return false;

		setProcessed(true);
		setDocStatus(DOCSTATUS_Voided);
		setDocAction(DOCACTION_None);
		return true;
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

		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		// Control de período contable
		MPeriod.testPeriodOpen(getCtx(), this.getDateAcct(), this.getC_DocType_ID(), this.getAD_Org_ID());

		// Elimino asientos contables
		MFactAcct.deleteEx(this.get_Table_ID(), this.get_ID(), get_TrxName());

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		this.setProcessed(false);
		this.setPosted(false);
		this.setDocStatus(DOCSTATUS_InProgress);
		this.setDocAction(DOCACTION_Complete);

		return true;
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
      StringBuffer sb = new StringBuffer ("MZFormEfectivo[")
        .append(getSummary()).append("]");
      return sb.toString();
    }

	@Override
	protected boolean beforeSave(boolean newRecord) {

		if (newRecord){
			this.setAmtBalanceo(Env.ZERO);
			this.setAmtBalanceo2(Env.ZERO);
			this.setDifferenceAmt(Env.ZERO);
			this.setDifferenceAmt2(Env.ZERO);
		}

		return true;
	}

	public String getConceptos(){

		String message = null;

		try{

			// Intancio modelo de configuracion de retail
			MZRetailConfig retailConfig = MZRetailConfig.getDefault(getCtx(), get_TrxName());

			if ((retailConfig == null) || (retailConfig.get_ID() <= 0)){
				return "No hay información de Configuraciones de Retail";
			}

			// Elimino conceptos anteriores
			String action = " delete from z_formefectivolin where z_formefectivo_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

			// Obtengo configuracion de conceptos para formulario de movimientos de efectivo segun documento
			boolean esFormulario01 = true;
			MDocType docType = (MDocType) this.getC_DocType();
			if (docType.getDocSubTypeSO() != null){
				if (docType.getDocSubTypeSO().equalsIgnoreCase("F2")){
					esFormulario01 = false;
				}
			}

			List<MZRetailConfigForEfe> configForEfeList = retailConfig.getConceptosFormEfe(esFormulario01);
			for (MZRetailConfigForEfe configForEfe: configForEfeList){
				MZFormEfectivoLin efectivoLin = new MZFormEfectivoLin(getCtx(), 0, get_TrxName());
				efectivoLin.setZ_FormEfectivo_ID(this.get_ID());
				efectivoLin.setAD_Org_ID(this.getAD_Org_ID());
				efectivoLin.setZ_RetailConfigForEfe_ID(configForEfe.get_ID());
				efectivoLin.setTipoConceptoForEfe(configForEfe.getTipoConceptoForEfe());
				efectivoLin.setName(configForEfe.getName());
				efectivoLin.setC_Currency_ID(142);
				efectivoLin.setC_Currency_2_ID(100);
				efectivoLin.setTieneCaja(configForEfe.isTieneCaja());
				efectivoLin.saveEx();
			}

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return message;
	}

	/***
	 * Obtiene y retorna lineas de este documento.
	 * Xpande. Created by Gabriel Vila on 8/30/19.
	 * @return
	 */
    public List<MZFormEfectivoLin> getLines() {

    	String whereClause = X_Z_FormEfectivoLin.COLUMNNAME_Z_FormEfectivo_ID + " =" + this.get_ID();

    	List<MZFormEfectivoLin> lines = new Query(getCtx(), I_Z_FormEfectivoLin.Table_Name, whereClause, get_TrxName()).list();

    	return lines;
    }

	/***
	 * Actualiza totalizadores de este documento.
	 * Xpande. Created by Gabriel Vila on 8/30/19.
	 */
	public void updateTotals() {

    	String sql = "", action = "";
    	PreparedStatement pstmt = null;
    	ResultSet rs = null;

    	try{
    	    sql = " select a.tipoconceptoforefe, coalesce(sum(a.amtsubtotal1),0) as monto1, coalesce(sum(a.amtsubtotal2),0) as monto2 " +
					" from z_formefectivolin a " +
					" inner join z_retailconfigforefe b on a.z_retailconfigforefe_id = b.z_retailconfigforefe_id " +
					" where a.z_formefectivo_id =" + this.get_ID() +
					" and b.afectasaldo ='Y' " +
					" group by a.tipoconceptoforefe ";

    		pstmt = DB.prepareStatement(sql, get_TrxName());
    		rs = pstmt.executeQuery();

    		while(rs.next()){
    			if (rs.getString("tipoconceptoforefe").equalsIgnoreCase(X_Z_FormEfectivoLin.TIPOCONCEPTOFOREFE_SALDOINICIAL)){
    				action = " update z_formefectivo set amttotal1 =" + rs.getBigDecimal("monto1") + ", " +
							" amttotal2 =" + rs.getBigDecimal("monto2") +
							" where z_formefectivo_id =" + this.get_ID();
					DB.executeUpdateEx(action, get_TrxName());
				}
    			else if (rs.getString("tipoconceptoforefe").equalsIgnoreCase(X_Z_FormEfectivoLin.TIPOCONCEPTOFOREFE_ENTRADASDEEFECTIVO)){
					action = " update z_formefectivo set amttotal3 =" + rs.getBigDecimal("monto1") + ", " +
							" amttotal4 =" + rs.getBigDecimal("monto2") +
							" where z_formefectivo_id =" + this.get_ID();
					DB.executeUpdateEx(action, get_TrxName());
				}
				else if (rs.getString("tipoconceptoforefe").equalsIgnoreCase(X_Z_FormEfectivoLin.TIPOCONCEPTOFOREFE_SALIDASDEEFECTIVO)){
					action = " update z_formefectivo set amttotal5 =" + rs.getBigDecimal("monto1") + ", " +
							" amttotal6 =" + rs.getBigDecimal("monto2") +
							" where z_formefectivo_id =" + this.get_ID();
					DB.executeUpdateEx(action, get_TrxName());
				}
				else if (rs.getString("tipoconceptoforefe").equalsIgnoreCase(X_Z_FormEfectivoLin.TIPOCONCEPTOFOREFE_RESULTADODELARQUEO)){
					action = " update z_formefectivo set amttotal7 =" + rs.getBigDecimal("monto1") + ", " +
							" amttotal8 =" + rs.getBigDecimal("monto2") +
							" where z_formefectivo_id =" + this.get_ID();
					DB.executeUpdateEx(action, get_TrxName());
				}
			}

    		// Actualizo montos de balanceos
			action = " update z_formefectivo set amtbalanceo = (coalesce(amttotal1,0) + coalesce(amttotal3,0)) - coalesce(amttotal5,0), " +
					" amtbalanceo2 = (coalesce(amttotal2,0) + coalesce(amttotal4,0)) - coalesce(amttotal6,0) " +
					" where z_formefectivo_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

			// Actualizo montos de diferencias
			action = " update z_formefectivo set differenceamt = coalesce(amttotal7,0) - coalesce(amtbalanceo,0) " +
					" where z_formefectivo_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

			action = " update z_formefectivo set differenceamt2 = coalesce(amttotal8,0) - coalesce(amtbalanceo2,0) " +
					" where z_formefectivo_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

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