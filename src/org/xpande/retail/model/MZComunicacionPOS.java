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
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;

/** Generated Model for Z_ComunicacionPOS
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class MZComunicacionPOS extends X_Z_ComunicacionPOS implements DocAction, DocOptions {

	/**
	 *
	 */
	private static final long serialVersionUID = 20170722L;

    /** Standard Constructor */
    public MZComunicacionPOS (Properties ctx, int Z_ComunicacionPOS_ID, String trxName)
    {
      super (ctx, Z_ComunicacionPOS_ID, trxName);
    }

    /** Load Constructor */
    public MZComunicacionPOS (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("MZComunicacionPOS[")
        .append(getSummary()).append("]");
      return sb.toString();
    }


	/***
	 * Obtiene y carga documentos a considerarse en este documento.
	 * Xpande. Created by Gabriel Vila on 7/12/17.
	 * @return
	 */
	public String getDocuments(){

		String message = null;

		try{

			// Obtiene documentos que pasaron por la confirmacion de precios al local.
			this.getDocumentosConfirmadosAlLocal();

			// Obtiene marcas restantes a enviar al POS que no tienen que ver con cambios de precio y alta de productos.
			this.getMarcasNoPrecios();

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		return message;
	}

	/***
	 * Obtiene y carga documentos y productos con cambios de precios ya confirmados al local.
	 * Xpande. Created by Gabriel Vila on 7/12/17.
	 */
	private void getDocumentosConfirmadosAlLocal(){

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
			int zPreciosProvCabID = 0;
			MZComunicacionPOSDoc comunicacionDoc = null;
			int adTableID = MTable.getTable_ID(I_Z_PreciosProvCab.Table_Name);

			sql = " select cab.z_preciosprovcab_id, cab.c_doctype_id, cab.documentno, cab.datevalidpo, cab.updated, cab.updatedby, " +
					" cab.c_bpartner_id, cab.z_lineaproductosocio_id, " +
					" lin.c_currency_id_so, lin.m_product_id, linorg.newpriceso, linorg.ad_orgtrx_id " +
					" from z_preciosprovlinorg linorg " +
					" inner join z_preciosprovlin lin on linorg.z_preciosprovlin_id = lin.z_preciosprovlin_id " +
					" inner join z_preciosprovcab cab on lin.z_preciosprovcab_id = cab.z_preciosprovcab_id " +
					" where linorg.ad_orgtrx_id =" + this.getAD_Org_ID() +
					" and linorg.newpriceso <> linorg.priceso " +
					" and cab.docstatus='CO' " +
					" and cab.z_preciosprovcab_id not in " +
					" (select confdoc.record_id from z_confirmacionetiquetadoc confdoc " +
					" inner join z_confirmacionetiqueta conf on confdoc.z_confirmacionetiqueta_id = conf.z_confirmacionetiqueta_id " +
					" where confdoc.ad_table_id =" + adTableID + " and conf.ad_org_id =" + this.getAD_Org_ID() + ") " +
					" order by cab.updated, cab.z_preciosprovcab_id";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				// Corte por id de documento de gestión de precios
				if (rs.getInt("z_preciosprovcab_id") != zPreciosProvCabID){
					// Nuevo documento
					comunicacionDoc = new MZComunicacionPOSDoc(getCtx(), 0, get_TrxName());
					comunicacionDoc.setZ_ComunicacionPOS_ID(this.get_ID());
					comunicacionDoc.setAD_Table_ID(adTableID);
					comunicacionDoc.setRecord_ID(rs.getInt("z_preciosprovcab_id"));
					comunicacionDoc.setAD_User_ID(rs.getInt("updatedby"));
					comunicacionDoc.setC_DocTypeTarget_ID(rs.getInt("c_doctype_id"));
					comunicacionDoc.setDateDoc(rs.getTimestamp("updated"));
					comunicacionDoc.setDocumentNoRef(rs.getString("documentno"));
					comunicacionDoc.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
					comunicacionDoc.setZ_LineaProductoSocio_ID(rs.getInt("z_lineaproductosocio_id"));
					comunicacionDoc.saveEx();

					zPreciosProvCabID = rs.getInt("z_preciosprovcab_id");
				}

				// Nuevo producto
				/*
				MZConfirmacionEtiquetaProd etiquetaProd = new MZConfirmacionEtiquetaProd(getCtx(), 0, get_TrxName());
				etiquetaProd.setZ_ConfirmacionEtiquetaDoc_ID(etiquetaDoc.get_ID());
				etiquetaProd.setM_Product_ID(rs.getInt("m_product_id"));
				etiquetaProd.setPriceSO(rs.getBigDecimal("newpriceso"));
				etiquetaProd.setDateValidSO(rs.getTimestamp("datevalidpo"));
				etiquetaProd.setC_Currency_ID_SO(rs.getInt("c_currency_id_so"));
				etiquetaProd.setQtyCount(1);
				etiquetaProd.saveEx();
				*/
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
	 * Obtiene marcas a enviar al POS que NO involucran cambios de precios.
	 * Xpande. Created by Gabriel Vila on 7/22/17.
	 */
	private void getMarcasNoPrecios() {

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
			int zActualizacionPVPID = 0;
			MZConfirmacionEtiquetaDoc etiquetaDoc = null;
			int adTableID = MTable.getTable_ID(I_Z_ActualizacionPVP.Table_Name);

			sql = " select cab.z_actualizacionpvp_id, cab.c_doctype_id, cab.documentno, cab.datedoc, cab.updated, cab.updatedby, " +
					" lin.c_currency_id, lin.m_product_id, linorg.newpriceso, linorg.ad_orgtrx_id " +
					" from z_actualizacionpvplinorg linorg " +
					" inner join z_actualizacionpvplin lin on linorg.z_actualizacionpvplin_id = lin.z_actualizacionpvplin_id " +
					" inner join z_actualizacionpvp cab on lin.z_actualizacionpvp_id = cab.z_actualizacionpvp_id " +
					" where linorg.ad_orgtrx_id =" + this.getAD_Org_ID() +
					" and linorg.newpriceso <> linorg.priceso " +
					" and cab.docstatus='CO' " +
					" and cab.z_actualizacionpvp_id not in " +
					" (select confdoc.record_id from z_confirmacionetiquetadoc confdoc " +
					" inner join z_confirmacionetiqueta conf on confdoc.z_confirmacionetiqueta_id = conf.z_confirmacionetiqueta_id " +
					" where confdoc.ad_table_id =" + adTableID + " and conf.ad_org_id =" + this.getAD_Org_ID() + ") " +
					" order by cab.updated, cab.z_actualizacionpvp_id";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				// Corte por id de documento de gestión de precios
				if (rs.getInt("z_actualizacionpvp_id") != zActualizacionPVPID){
					// Nuevo documento
					etiquetaDoc = new MZConfirmacionEtiquetaDoc(getCtx(), 0, get_TrxName());
					etiquetaDoc.setZ_ConfirmacionEtiqueta_ID(this.get_ID());
					etiquetaDoc.setAD_Table_ID(adTableID);
					etiquetaDoc.setRecord_ID(rs.getInt("z_actualizacionpvp_id"));
					etiquetaDoc.setAD_User_ID(rs.getInt("updatedby"));
					etiquetaDoc.setC_DocTypeTarget_ID(rs.getInt("c_doctype_id"));
					etiquetaDoc.setDateDoc(rs.getTimestamp("updated"));
					etiquetaDoc.setDocumentNoRef(rs.getString("documentno"));
					etiquetaDoc.save();

					zActualizacionPVPID = rs.getInt("z_actualizacionpvp_id");
				}

				// Nuevo producto
				MZConfirmacionEtiquetaProd etiquetaProd = new MZConfirmacionEtiquetaProd(getCtx(), 0, get_TrxName());
				etiquetaProd.setZ_ConfirmacionEtiquetaDoc_ID(etiquetaDoc.get_ID());
				etiquetaProd.setM_Product_ID(rs.getInt("m_product_id"));
				etiquetaProd.setPriceSO(rs.getBigDecimal("newpriceso"));
				etiquetaProd.setDateValidSO(rs.getTimestamp("datedoc"));
				etiquetaProd.setC_Currency_ID_SO(rs.getInt("c_currency_id"));
				etiquetaProd.setQtyCount(1);
				etiquetaProd.saveEx();
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