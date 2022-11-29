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
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for Z_CreditLine
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1 - $Id$ */
public class MZCreditLine extends X_Z_CreditLine implements DocAction, DocOptions {

	/**
	 *
	 */
	private static final long serialVersionUID = 20211029L;

    /** Standard Constructor */
    public MZCreditLine (Properties ctx, int Z_CreditLine_ID, String trxName)
    {
      super (ctx, Z_CreditLine_ID, trxName);
    }

    /** Load Constructor */
    public MZCreditLine (Properties ctx, ResultSet rs, String trxName)
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
			options[newIndex++] = DocumentEngine.ACTION_Void;
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

		m_processMsg = this.validateDocument();
		if (m_processMsg != null){
			return DocAction.STATUS_Invalid;
		}

		// Seteo info en el socio de negocio
		MBPartner partner = (MBPartner) this.getC_BPartner();
		Timestamp creditDueDate = (Timestamp) partner.get_Value("CreditDueDate");
		int creditLineIDAux = partner.get_ValueAsInt("Z_CreditLine_ID");
		if ((creditLineIDAux > 0) && (creditLineIDAux != this.get_ID())) {
			if (creditDueDate != null){
				if (creditDueDate.after(this.getEndDate())){
					MZCreditLine creditLineAux = new MZCreditLine(getCtx(), creditLineIDAux, null);
					m_processMsg = "El socio de negocio tiene otra Linea de Crédito vigente (nro.: " + creditLineAux.getDocumentNo() + "). " +
							 "No puede tener dos lineas de crédito al mismo tiempo para un mismo período.";
				}
			}
		}
		if (creditLineIDAux <= 0){
			partner.set_ValueOfColumn("Z_CreditLine_ID", this.get_ID());
			partner.set_ValueOfColumn("CreditLimit", this.getCreditLimit());
			partner.set_ValueOfColumn("AmtCreditActual", Env.ZERO);
			partner.set_ValueOfColumn("CreditDueDate", this.getEndDate());
		}
		else if (creditLineIDAux == this.get_ID()){
			partner.set_ValueOfColumn("CreditLimit", this.getCreditLimit());
			partner.set_ValueOfColumn("CreditDueDate", this.getEndDate());
		}
		else{
			partner.set_ValueOfColumn("Z_CreditLine_ID", this.get_ID());
			partner.set_ValueOfColumn("CreditLimit", this.getCreditLimit());
			partner.set_ValueOfColumn("AmtCreditActual", Env.ZERO);
			partner.set_ValueOfColumn("CreditDueDate", this.getEndDate());
		}
		partner.saveEx();

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

		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		this.setProcessed(false);
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
      StringBuffer sb = new StringBuffer ("MZCreditLine[")
        .append(getSummary()).append("]");
      return sb.toString();
    }

	@Override
	protected boolean beforeSave(boolean newRecord) {

		if (!newRecord){
			if (is_ValueChanged(X_Z_CreditLine.COLUMNNAME_CreditLimit)) {
				// Limite Credito
				MZCreditLineAudit lineAudit = new MZCreditLineAudit(getCtx(), 0, get_TrxName());
				lineAudit.setAD_Org_ID(this.getAD_Org_ID());
				lineAudit.setAD_User_ID(this.getUpdatedBy());
				lineAudit.setC_BPartner_ID(this.getC_BPartner_ID());
				lineAudit.setC_Currency_ID(this.getC_Currency_ID());
				lineAudit.setC_DocType_ID(this.getC_DocType_ID());
				lineAudit.setCreditLimit(this.getCreditLimit());
				lineAudit.setDateDoc(this.getDateDoc());
				lineAudit.setDateTrx(this.getCreated());
				lineAudit.setDocStatus(this.getDocStatus());
				lineAudit.setEndDate(this.getEndDate());
				lineAudit.setIsAuthSupervisor(this.get_ValueAsBoolean("IsAuthSupervisor"));
				lineAudit.setStartDate(this.getStartDate());
				lineAudit.setZ_CreditLine_ID(this.get_ID());
				lineAudit.setZ_CreditLineCategory_ID(this.getZ_CreditLineCategory_ID());
				lineAudit.setColumnName("Limite de Crédito");
				lineAudit.setOldValue(this.get_ValueOld(X_Z_CreditLine.COLUMNNAME_CreditLimit).toString());
				lineAudit.setNewValue(this.getCreditLimit().toString());
				lineAudit.saveEx();
			}
			if (is_ValueChanged(X_Z_CreditLine.COLUMNNAME_StartDate)) {
				MZCreditLineAudit lineAudit = new MZCreditLineAudit(getCtx(), 0, get_TrxName());
				lineAudit.setAD_Org_ID(this.getAD_Org_ID());
				lineAudit.setAD_User_ID(this.getUpdatedBy());
				lineAudit.setC_BPartner_ID(this.getC_BPartner_ID());
				lineAudit.setC_Currency_ID(this.getC_Currency_ID());
				lineAudit.setC_DocType_ID(this.getC_DocType_ID());
				lineAudit.setCreditLimit(this.getCreditLimit());
				lineAudit.setDateDoc(this.getDateDoc());
				lineAudit.setDateTrx(this.getCreated());
				lineAudit.setDocStatus(this.getDocStatus());
				lineAudit.setEndDate(this.getEndDate());
				lineAudit.setIsAuthSupervisor(this.get_ValueAsBoolean("IsAuthSupervisor"));
				lineAudit.setStartDate(this.getStartDate());
				lineAudit.setZ_CreditLine_ID(this.get_ID());
				lineAudit.setZ_CreditLineCategory_ID(this.getZ_CreditLineCategory_ID());
				lineAudit.setColumnName("Fecha Desde");
				lineAudit.setNewValue(this.getStartDate().toString());
				lineAudit.setOldValue(this.get_ValueOld(X_Z_CreditLine.COLUMNNAME_StartDate).toString());
				lineAudit.saveEx();
			}
			if (is_ValueChanged(X_Z_CreditLine.COLUMNNAME_EndDate)) {
				MZCreditLineAudit lineAudit = new MZCreditLineAudit(getCtx(), 0, get_TrxName());
				lineAudit.setAD_Org_ID(this.getAD_Org_ID());
				lineAudit.setAD_User_ID(this.getUpdatedBy());
				lineAudit.setC_BPartner_ID(this.getC_BPartner_ID());
				lineAudit.setC_Currency_ID(this.getC_Currency_ID());
				lineAudit.setC_DocType_ID(this.getC_DocType_ID());
				lineAudit.setCreditLimit(this.getCreditLimit());
				lineAudit.setDateDoc(this.getDateDoc());
				lineAudit.setDateTrx(this.getCreated());
				lineAudit.setDocStatus(this.getDocStatus());
				lineAudit.setEndDate(this.getEndDate());
				lineAudit.setIsAuthSupervisor(this.get_ValueAsBoolean("IsAuthSupervisor"));
				lineAudit.setStartDate(this.getStartDate());
				lineAudit.setZ_CreditLine_ID(this.get_ID());
				lineAudit.setZ_CreditLineCategory_ID(this.getZ_CreditLineCategory_ID());
				lineAudit.setColumnName("Fecha Hasta");
				lineAudit.setNewValue(this.getEndDate().toString());
				lineAudit.setOldValue(this.get_ValueOld(X_Z_CreditLine.COLUMNNAME_EndDate).toString());
				lineAudit.saveEx();
			}
			if (is_ValueChanged(X_Z_CreditLine.COLUMNNAME_DocStatus)) {
				MZCreditLineAudit lineAudit = new MZCreditLineAudit(getCtx(), 0, get_TrxName());
				lineAudit.setAD_Org_ID(this.getAD_Org_ID());
				lineAudit.setAD_User_ID(this.getUpdatedBy());
				lineAudit.setC_BPartner_ID(this.getC_BPartner_ID());
				lineAudit.setC_Currency_ID(this.getC_Currency_ID());
				lineAudit.setC_DocType_ID(this.getC_DocType_ID());
				lineAudit.setCreditLimit(this.getCreditLimit());
				lineAudit.setDateDoc(this.getDateDoc());
				lineAudit.setDateTrx(this.getCreated());
				lineAudit.setDocStatus(this.getDocStatus());
				lineAudit.setEndDate(this.getEndDate());
				lineAudit.setIsAuthSupervisor(this.get_ValueAsBoolean("IsAuthSupervisor"));
				lineAudit.setStartDate(this.getStartDate());
				lineAudit.setZ_CreditLine_ID(this.get_ID());
				lineAudit.setZ_CreditLineCategory_ID(this.getZ_CreditLineCategory_ID());
				lineAudit.setColumnName("Estado Documento");
				if (this.getDocStatus().equalsIgnoreCase("DR")){
					lineAudit.setNewValue("Borrador");
				}
				else if (this.getDocStatus().equalsIgnoreCase("CO")){
					lineAudit.setNewValue("Completo");
				}
				else if (this.getDocStatus().equalsIgnoreCase("IP")){
					lineAudit.setNewValue("Reactivado");
				}
				else if (this.getDocStatus().equalsIgnoreCase("VO")){
					lineAudit.setNewValue("Anulado");
				}
				String oldValue = this.get_ValueOld(X_Z_CreditLine.COLUMNNAME_DocStatus).toString();
				if (oldValue.equalsIgnoreCase("DR")){
					lineAudit.setOldValue("Borrador");
				}
				else if (oldValue.equalsIgnoreCase("CO")){
					lineAudit.setOldValue("Completo");
				}
				else if (oldValue.equalsIgnoreCase("IP")){
					lineAudit.setOldValue("Reactivado");
				}
				else if (oldValue.equalsIgnoreCase("VO")){
					lineAudit.setOldValue("Anulado");
				}
				lineAudit.saveEx();
			}
			if (is_ValueChanged("IsAuthSupervisor")) {
				MZCreditLineAudit lineAudit = new MZCreditLineAudit(getCtx(), 0, get_TrxName());
				lineAudit.setAD_Org_ID(this.getAD_Org_ID());
				lineAudit.setAD_User_ID(this.getUpdatedBy());
				lineAudit.setC_BPartner_ID(this.getC_BPartner_ID());
				lineAudit.setC_Currency_ID(this.getC_Currency_ID());
				lineAudit.setC_DocType_ID(this.getC_DocType_ID());
				lineAudit.setCreditLimit(this.getCreditLimit());
				lineAudit.setDateDoc(this.getDateDoc());
				lineAudit.setDateTrx(this.getCreated());
				lineAudit.setDocStatus(this.getDocStatus());
				lineAudit.setEndDate(this.getEndDate());
				lineAudit.setIsAuthSupervisor(this.get_ValueAsBoolean("IsAuthSupervisor"));
				lineAudit.setStartDate(this.getStartDate());
				lineAudit.setZ_CreditLine_ID(this.get_ID());
				lineAudit.setZ_CreditLineCategory_ID(this.getZ_CreditLineCategory_ID());
				lineAudit.setColumnName("Autoriza Sobregiro Supervisor");
				if (this.get_ValueAsBoolean("IsAuthSupervisor")){
					lineAudit.setNewValue("Si");
				}
				else{
					lineAudit.setNewValue("No");
				}
				lineAudit.setOldValue(this.get_ValueOld("IsAuthSupervisor").toString());
				lineAudit.saveEx();
			}
		}
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		if (!success) return false;

		if (newRecord){
			// Fecha Inicio
			MZCreditLineAudit lineAudit = new MZCreditLineAudit(getCtx(), 0, get_TrxName());
			lineAudit.setAD_Org_ID(this.getAD_Org_ID());
			lineAudit.setAD_User_ID(this.getUpdatedBy());
			lineAudit.setC_BPartner_ID(this.getC_BPartner_ID());
			lineAudit.setC_Currency_ID(this.getC_Currency_ID());
			lineAudit.setC_DocType_ID(this.getC_DocType_ID());
			lineAudit.setCreditLimit(this.getCreditLimit());
			lineAudit.setDateDoc(this.getDateDoc());
			lineAudit.setDateTrx(this.getCreated());
			lineAudit.setDocStatus(this.getDocStatus());
			lineAudit.setEndDate(this.getEndDate());
			lineAudit.setIsAuthSupervisor(this.get_ValueAsBoolean("IsAuthSupervisor"));
			lineAudit.setStartDate(this.getStartDate());
			lineAudit.setZ_CreditLine_ID(this.get_ID());
			lineAudit.setZ_CreditLineCategory_ID(this.getZ_CreditLineCategory_ID());
			lineAudit.setColumnName("Fecha Desde");
			lineAudit.setNewValue(this.getStartDate().toString());
			lineAudit.saveEx();

			// Fecha Fin
			lineAudit = new MZCreditLineAudit(getCtx(), 0, get_TrxName());
			lineAudit.setAD_Org_ID(this.getAD_Org_ID());
			lineAudit.setAD_User_ID(this.getUpdatedBy());
			lineAudit.setC_BPartner_ID(this.getC_BPartner_ID());
			lineAudit.setC_Currency_ID(this.getC_Currency_ID());
			lineAudit.setC_DocType_ID(this.getC_DocType_ID());
			lineAudit.setCreditLimit(this.getCreditLimit());
			lineAudit.setDateDoc(this.getDateDoc());
			lineAudit.setDateTrx(this.getCreated());
			lineAudit.setDocStatus(this.getDocStatus());
			lineAudit.setEndDate(this.getEndDate());
			lineAudit.setIsAuthSupervisor(this.get_ValueAsBoolean("IsAuthSupervisor"));
			lineAudit.setStartDate(this.getStartDate());
			lineAudit.setZ_CreditLine_ID(this.get_ID());
			lineAudit.setZ_CreditLineCategory_ID(this.getZ_CreditLineCategory_ID());
			lineAudit.setColumnName("Fecha Hasta");
			lineAudit.setNewValue(this.getEndDate().toString());
			lineAudit.saveEx();

			// Limite Credito
			lineAudit = new MZCreditLineAudit(getCtx(), 0, get_TrxName());
			lineAudit.setAD_Org_ID(this.getAD_Org_ID());
			lineAudit.setAD_User_ID(this.getUpdatedBy());
			lineAudit.setC_BPartner_ID(this.getC_BPartner_ID());
			lineAudit.setC_Currency_ID(this.getC_Currency_ID());
			lineAudit.setC_DocType_ID(this.getC_DocType_ID());
			lineAudit.setCreditLimit(this.getCreditLimit());
			lineAudit.setDateDoc(this.getDateDoc());
			lineAudit.setDateTrx(this.getCreated());
			lineAudit.setDocStatus(this.getDocStatus());
			lineAudit.setEndDate(this.getEndDate());
			lineAudit.setIsAuthSupervisor(this.get_ValueAsBoolean("IsAuthSupervisor"));
			lineAudit.setStartDate(this.getStartDate());
			lineAudit.setZ_CreditLine_ID(this.get_ID());
			lineAudit.setZ_CreditLineCategory_ID(this.getZ_CreditLineCategory_ID());
			lineAudit.setColumnName("Limite de Crédito");
			lineAudit.setNewValue(this.getCreditLimit().toString());
			lineAudit.saveEx();

			// Estado Documento
			lineAudit = new MZCreditLineAudit(getCtx(), 0, get_TrxName());
			lineAudit.setAD_Org_ID(this.getAD_Org_ID());
			lineAudit.setAD_User_ID(this.getUpdatedBy());
			lineAudit.setC_BPartner_ID(this.getC_BPartner_ID());
			lineAudit.setC_Currency_ID(this.getC_Currency_ID());
			lineAudit.setC_DocType_ID(this.getC_DocType_ID());
			lineAudit.setCreditLimit(this.getCreditLimit());
			lineAudit.setDateDoc(this.getDateDoc());
			lineAudit.setDateTrx(this.getCreated());
			lineAudit.setDocStatus(this.getDocStatus());
			lineAudit.setEndDate(this.getEndDate());
			lineAudit.setIsAuthSupervisor(this.get_ValueAsBoolean("IsAuthSupervisor"));
			lineAudit.setStartDate(this.getStartDate());
			lineAudit.setZ_CreditLine_ID(this.get_ID());
			lineAudit.setZ_CreditLineCategory_ID(this.getZ_CreditLineCategory_ID());
			lineAudit.setColumnName("Estado Documento");
			if (this.getDocStatus().equalsIgnoreCase("DR")){
				lineAudit.setNewValue("Borrador");
			}
			else if (this.getDocStatus().equalsIgnoreCase("CO")){
				lineAudit.setNewValue("Completo");
			}
			else if (this.getDocStatus().equalsIgnoreCase("IP")){
				lineAudit.setNewValue("Reactivado");
			}
			else if (this.getDocStatus().equalsIgnoreCase("VO")){
				lineAudit.setNewValue("Anulado");
			}
			lineAudit.saveEx();

			// Autoriza
			lineAudit = new MZCreditLineAudit(getCtx(), 0, get_TrxName());
			lineAudit.setAD_Org_ID(this.getAD_Org_ID());
			lineAudit.setAD_User_ID(this.getUpdatedBy());
			lineAudit.setC_BPartner_ID(this.getC_BPartner_ID());
			lineAudit.setC_Currency_ID(this.getC_Currency_ID());
			lineAudit.setC_DocType_ID(this.getC_DocType_ID());
			lineAudit.setCreditLimit(this.getCreditLimit());
			lineAudit.setDateDoc(this.getDateDoc());
			lineAudit.setDateTrx(this.getCreated());
			lineAudit.setDocStatus(this.getDocStatus());
			lineAudit.setEndDate(this.getEndDate());
			lineAudit.setIsAuthSupervisor(this.get_ValueAsBoolean("IsAuthSupervisor"));
			lineAudit.setStartDate(this.getStartDate());
			lineAudit.setZ_CreditLine_ID(this.get_ID());
			lineAudit.setZ_CreditLineCategory_ID(this.getZ_CreditLineCategory_ID());
			lineAudit.setColumnName("Autoriza Sobregiro Supervisor");
			if (this.get_ValueAsBoolean("IsAuthSupervisor")){
				lineAudit.setNewValue("Si");
			}
			else{
				lineAudit.setNewValue("No");
			}
			lineAudit.saveEx();
		}
		else{
			if ((is_ValueChanged(X_Z_CreditLine.COLUMNNAME_CreditLimit)) || (is_ValueChanged(X_Z_CreditLine.COLUMNNAME_StartDate))
				|| (is_ValueChanged(X_Z_CreditLine.COLUMNNAME_EndDate)) || (is_ValueChanged(X_Z_CreditLine.COLUMNNAME_DocStatus))
				|| (is_ValueChanged("IsAuthSupervisor"))) {
			}
		}
		return true;
	}

	@Override
	protected boolean beforeDelete() {

		if (this.getC_BPartner_ID() > 0){
			MBPartner partner = (MBPartner) this.getC_BPartner();
			int creditLineIDAux = partner.get_ValueAsInt("Z_CreditLine_ID");
			if (creditLineIDAux == this.get_ID()){
				String action = " update c_bpartner " +
						" set Z_CreditLine_ID = null, CreditDueDate = null, creditlimit = null, amtcreditactual = null " +
						" where c_bpartner_id =" + this.getC_BPartner_ID();
				DB.executeUpdateEx(action, get_TrxName());
			}
		}
		return true;
	}

	private String validateDocument() {
		try{
			// Valido fechas
			if (this.getStartDate().after(this.getEndDate())){
				return "Fecha desde no puede ser mayor a fecha hasta";
			}
			// Verifico que no haya otra linea de credito vigente para este socio de negocio en estas fechas
			String sql= " select max(z_creditline_id) as id " +
					"from z_creditline " +
					"where z_creditline_id !=" + this.get_ID() +
					"and docstatus='CO' " +
					"and c_bpartner_id=" + this.getC_BPartner_ID() +
					"and ((startdate between '" + this.getStartDate() + "' and '" + this.getEndDate() + "') " +
					" or (enddate between '" + this.getStartDate() + "' and '" + this.getEndDate() + "')) ";
			int idAux = DB.getSQLValueEx(get_TrxName(), sql);
			if (idAux > 0){
				return "Este Socio de Negocio ya tiene una Linea de Crédito Activa en este rango de Fechas.";
			}
		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
		return null;
	}
}