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
import org.compiere.util.TimeUtil;
import org.xpande.core.model.I_Z_ProductoUPC;
import org.xpande.core.model.MZActividadDocumento;
import org.xpande.sisteco.model.I_Z_SistecoInterfaceOut;
import org.xpande.sisteco.model.MZSistecoInterfaceOut;
import org.xpande.sisteco.utils.ProcesadorInterfaceOut;
import org.xpande.stech.model.MZStechInterfaceOut;
import org.xpande.stech.utils.*;

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

		// No comunico precios si eso se indica en proceso de comunicacion de datos al pos.
		boolean processPrices = true;
		if (this.isOnlyBasicData()){
			processPrices = false;
		}


		int contadorLineas = 0;

		// Obtengo proveedor de pos para la organización de este modelo.
		// Por ahora manejo Sisteco y Scanntech
		MZPosVendorOrg posVendorOrg = MZPosVendor.getByOrg(getCtx(), this.getAD_Org_ID(), get_TrxName());
		MZPosVendor posVendor = (MZPosVendor) posVendorOrg.getZ_PosVendor();
		if (posVendor.getValue().equalsIgnoreCase("SISTECO")){

			// Proceso interface de salida por sisteco.
			ProcesadorInterfaceOut procesadorInterfaceOut = new ProcesadorInterfaceOut(getCtx(), get_TrxName());
			m_processMsg = procesadorInterfaceOut.executeInterfaceOut(this.getAD_Org_ID(), this.get_ID(), processPrices, true, true);
			if (m_processMsg != null)
				return DocAction.STATUS_Invalid;

			// Guardo auditoria del proceso con datos de las entidades comunicadas, en caso de haber alguna
			List<MZSistecoInterfaceOut> interfaceOuts = procesadorInterfaceOut.getMarcasProcesadas(this.get_ID());
			if (interfaceOuts.size() > 0){
				this.generarAuditoriaSisteco(interfaceOuts);
				contadorLineas = interfaceOuts.size();
			}

		}
		else if (posVendor.getValue().equalsIgnoreCase("SCANNTECH")){

			// Proceso interface de salida por scanntech.
			org.xpande.stech.utils.ProcesadorInterfaceOut procesadorInterfaceOut = new org.xpande.stech.utils.ProcesadorInterfaceOut(getCtx(), get_TrxName());
			m_processMsg = procesadorInterfaceOut.executeInterfaceOut(this.getAD_Org_ID(), this.get_ID(), processPrices, true, true);
			if (m_processMsg != null)
				return DocAction.STATUS_Invalid;

			// Guardo auditoria del proceso con datos de las entidades comunicadas, en caso de haber alguna
			List<MZStechInterfaceOut> interfaceOuts = procesadorInterfaceOut.getMarcasProcesadas(this.get_ID());
			if (interfaceOuts.size() > 0){
				this.generarAuditoriaScanntech(interfaceOuts);
				contadorLineas = interfaceOuts.size();
			}


		}

		// Actualizo marca de comunicado al pos en documentos comunicados
		this.updateDocumentosComunicados();

		/*
		// Guardo documento en tabla para informes de actividad por documento
		MZActividadDocumento actividadDocumento = new MZActividadDocumento(getCtx(), 0, get_TrxName());
		actividadDocumento.setAD_Table_ID(this.get_Table_ID());
		actividadDocumento.setRecord_ID(this.get_ID());
		actividadDocumento.setC_DocType_ID(this.getC_DocType_ID());
		actividadDocumento.setDocumentNoRef(this.getDocumentNo());
		actividadDocumento.setDocCreatedBy(this.getCreatedBy());
		actividadDocumento.setDocDateCreated(this.getCreated());
		actividadDocumento.setCompletedBy(Env.getAD_User_ID(getCtx()));
		actividadDocumento.setDateCompleted(new Timestamp(System.currentTimeMillis()));
		actividadDocumento.setAD_Role_ID(Env.getAD_Role_ID(getCtx()));
		actividadDocumento.setLineNo(contadorLineas);
		actividadDocumento.setDiferenciaTiempo(new BigDecimal((actividadDocumento.getDateCompleted().getTime()-actividadDocumento.getDocDateCreated().getTime())/1000).divide(new BigDecimal(60),2,BigDecimal.ROUND_HALF_UP));
		actividadDocumento.saveEx();
		 */

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


	/***
	 * Genera auditoria de este proceso con las marcas comunicadas al pos de Sisteco.
	 * Xpande. Created by Gabriel Vila on 10/13/17.
	 * @param interfaceOuts
	 */
	private void generarAuditoriaSisteco(List<MZSistecoInterfaceOut> interfaceOuts) {

		try{
			for (MZSistecoInterfaceOut interfaceOut: interfaceOuts){
				MZComunicacionPOSAud posAud = new MZComunicacionPOSAud(getCtx(), 0, get_TrxName());
				posAud.setZ_ComunicacionPOS_ID(this.get_ID());
				posAud.setAD_Table_ID(I_Z_SistecoInterfaceOut.Table_ID);
				posAud.setRecord_ID(interfaceOut.get_ID());
				posAud.setCRUDType(interfaceOut.getCRUDType());
				if (interfaceOut.getAD_Table_ID() == I_M_Product.Table_ID){
					posAud.setM_Product_ID(interfaceOut.getRecord_ID());
					posAud.setPriceSO(interfaceOut.getPriceSO());
					posAud.setTipoComunicaPOS(X_Z_ComunicacionPOSAud.TIPOCOMUNICAPOS_PRODUCTO);
				}
				else if (interfaceOut.getAD_Table_ID() == I_Z_ProductoUPC.Table_ID){
					posAud.setUPC(interfaceOut.getDescription());
					posAud.setTipoComunicaPOS(X_Z_ComunicacionPOSAud.TIPOCOMUNICAPOS_CODIGODEBARRAS);
				}
				else if (interfaceOut.getAD_Table_ID() == I_C_BPartner.Table_ID){
					posAud.setC_BPartner_ID(interfaceOut.getRecord_ID());
					posAud.setTipoComunicaPOS(X_Z_ComunicacionPOSAud.TIPOCOMUNICAPOS_SOCIODENEGOCIO);
				}

				posAud.saveEx();
			}

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}


	/***
	 * Genera auditoria de este proceso con las marcas comunicadas al pos de Scanntech.
	 * Xpande. Created by Gabriel Vila on 10/13/17.
	 * @param interfaceOuts
	 */
	private void generarAuditoriaScanntech(List<MZStechInterfaceOut> interfaceOuts) {

		try{
			for (MZStechInterfaceOut interfaceOut: interfaceOuts){
				MZComunicacionPOSAud posAud = new MZComunicacionPOSAud(getCtx(), 0, get_TrxName());
				posAud.setZ_ComunicacionPOS_ID(this.get_ID());
				posAud.setAD_Table_ID(I_Z_SistecoInterfaceOut.Table_ID);
				posAud.setRecord_ID(interfaceOut.get_ID());
				posAud.setCRUDType(interfaceOut.getCRUDType());
				if (interfaceOut.getAD_Table_ID() == I_M_Product.Table_ID){
					posAud.setM_Product_ID(interfaceOut.getRecord_ID());
					posAud.setPriceSO(interfaceOut.getPriceSO());
					posAud.setTipoComunicaPOS(X_Z_ComunicacionPOSAud.TIPOCOMUNICAPOS_PRODUCTO);
				}
				else if (interfaceOut.getAD_Table_ID() == I_Z_ProductoUPC.Table_ID){
					posAud.setUPC(interfaceOut.getDescription());
					posAud.setTipoComunicaPOS(X_Z_ComunicacionPOSAud.TIPOCOMUNICAPOS_CODIGODEBARRAS);
				}
				else if (interfaceOut.getAD_Table_ID() == I_C_BPartner.Table_ID){
					posAud.setC_BPartner_ID(interfaceOut.getRecord_ID());
					posAud.setTipoComunicaPOS(X_Z_ComunicacionPOSAud.TIPOCOMUNICAPOS_SOCIODENEGOCIO);
				}

				posAud.saveEx();
			}

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}


	/***
	 * Actualiza marca de comunicados a los documentos que participaron de este proeso y fueron comunicados al pos.
	 * Xpande. Created by Gabriel Vila on 9/21/17.
	 */
	private void updateDocumentosComunicados() {

		String action = "";

		try{

			Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

			// Actualizo marca de documentos
			action = " update z_confirmacionetiquetadoc set comunicadopos='Y' " +
						" where comunicadopos='N' and isselected='Y' and isconfirmed='Y' " +
						" and ((DateToPos is null) or (DateToPos <='" + fechaHoy + "')) " +
						" and z_confirmacionetiqueta_id in " +
						" (select z_confirmacionetiqueta_id from z_confirmacionetiqueta where z_comunicacionpos_id =" + this.get_ID() + ")";
			DB.executeUpdateEx(action, get_TrxName());

			// Actualizo cabezales de confirmacion de etiquetas
			action = " update z_confirmacionetiqueta set comunicadopos='Y' " +
					 " where z_comunicacionpos_id =" + this.get_ID() +
					 " and z_confirmacionetiqueta_id not in " +
					 " (select distinct z_confirmacionetiqueta_id from z_confirmacionetiquetadoc " +
			         " where comunicadopos ='N' " +
					 " and z_confirmacionetiqueta_id = z_confirmacionetiqueta.z_confirmacionetiqueta_id)";
			DB.executeUpdateEx(action, get_TrxName());

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}

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
	 * Estos son documentos existentes en procesos completos de Confirmación de Precios al Local.
	 * Xpande. Created by Gabriel Vila on 7/12/17.
	 * @return
	 */
	public String getDocuments(){

		String message = null;

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{

			// Elimino datos anteriores antes de cargar
			this.deleteDocuments();

		    sql = " select z_confirmacionetiqueta_id " +
					" from z_confirmacionetiqueta " +
					" where ad_org_id =" + this.getAD_Org_ID() +
					" and docstatus='CO' " +
					" and z_comunicacionpos_id is null " +
					" and comunicadopos ='N' " +
					" order by datedoc";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){
				MZConfirmacionEtiqueta confirmacionEtiqueta = new MZConfirmacionEtiqueta(getCtx(), rs.getInt("z_confirmacionetiqueta_id"), get_TrxName());
				confirmacionEtiqueta.setZ_ComunicacionPOS_ID(this.get_ID());
				confirmacionEtiqueta.saveEx();
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
	 * Elimina información de documentos asociados a este modelo.
	 * Xpande. Created by Gabriel Vila on 9/13/17.
	 */
	private void deleteDocuments(){

		try{
			String action = " update z_confirmacionetiqueta set z_comunicacionpos_id = null where z_comunicacionpos_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		// No acepto organización *
		if (this.getAD_Org_ID() <= 0){
			log.saveError("ATENCIÓN", "Debe Indicar Organización a considerar (no se acepta organización = * )");
			return false;

		}

		// Si se indica comunicar solo clientes y codigos de barra, me aseguro de no tener documentos cargados para comunicacion de precios
		if (this.isOnlyBasicData()){
			this.deleteDocuments();
		}

		return true;
	}

	@Override
	protected boolean beforeDelete() {

		// Al eliminar cabezal me aseguro de no tener documentos cargados para comunicacion de precios
		this.deleteDocuments();

		return true;
	}
}