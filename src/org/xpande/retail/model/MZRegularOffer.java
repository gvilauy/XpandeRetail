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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.impexp.ImpFormat;
import org.compiere.impexp.MImpFormat;
import org.compiere.model.*;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.TimeUtil;
import org.xpande.comercial.model.MZProdPurchaseOffer;
import org.xpande.comercial.model.MZProdSalesOffer;
import org.xpande.core.model.MZProductoUPC;
import org.xpande.core.utils.CurrencyUtils;
import org.xpande.core.utils.PriceListUtils;

/** Generated Model for Z_RegularOffer
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1 - $Id$ */
public class MZRegularOffer extends X_Z_RegularOffer implements DocAction, DocOptions {

	/**
	 *
	 */
	private static final long serialVersionUID = 20221218L;
	private MSequence ZProdPriceSOEvolSequence = null;

    /** Standard Constructor */
    public MZRegularOffer (Properties ctx, int Z_RegularOffer_ID, String trxName)
    {
      super (ctx, Z_RegularOffer_ID, trxName);
    }

    /** Load Constructor */
    public MZRegularOffer (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

	@Override
	public int customizeValidActions(String docStatus, Object processing, String orderType, String isSOTrx, int AD_Table_ID, String[] docAction, String[] options, int index) {

		int newIndex = 0;

		if ((docStatus.equalsIgnoreCase(STATUS_Drafted)) || (docStatus.equalsIgnoreCase(STATUS_Invalid))
				|| (docStatus.equalsIgnoreCase(STATUS_InProgress))){

			options[newIndex++] = DocumentEngine.ACTION_Complete;

		}
		else if (docStatus.equalsIgnoreCase(STATUS_Completed)){

			//options[newIndex++] = DocumentEngine.ACTION_None;
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

		// Obtengo secuencias necesarias para el proceso. Esto para aumentar la velocidad del proceso al completar.
		//this.ZProdPriceSOEvolSequence = MSequence.get(getCtx(), MZProdPriceSOEvol.Table_Name);

		// Obtengo lineas del documento
		List<MZRegularOfferLine> offerLines = this.getLines();

		// Validaciones del documento
		m_processMsg = this.validateDocument(offerLines);
		if (m_processMsg != null) {
			return DocAction.STATUS_Invalid;
		}

		// Obtengo organizaciones que participan en este documento
		List<MZRegularOfferOrg> offerOrgs = this.getSelectedOrgs();

		// Si la organización indicada en este documento es *, debo actulizar precio de venta de este producto en la lista *
		// Esto porque cuando proceso productos ya existentes y selecciono organizacion *, el precio de venta actual que traigo será
		// el precio de venta de la lista *
		if (this.getAD_Org_ID() == 0) {
			// Para ello agrego simplemente la organización a las organizaciones a procesar
			MZRegularOfferOrg offerOrg = new MZRegularOfferOrg(getCtx(), 0, get_TrxName());
			offerOrg.setAD_OrgTrx_ID(0);
			offerOrgs.add(offerOrg);
		}

		// Recorro y proceso lineas (ya fueron validadas)
		for (MZRegularOfferLine offerLine: offerLines){
			// Si la oferta no es solo de venta
			if (!this.getRegularOfferType().equalsIgnoreCase(REGULAROFFERTYPE_OfertaSoloDeVenta)) {
				// Seteo información de precio de oferta compra para esta linea
				if ((offerLine.getNewPricePO() != null) && (offerLine.getNewPricePO().compareTo(Env.ZERO) > 0)) {
					this.setLinePricePO(offerOrgs, offerLine);
				}
			}

			// Seteo información de precio de oferta venta para esta linea
			if ((offerLine.getNewPriceSO() != null) && (offerLine.getNewPriceSO().compareTo(Env.ZERO) > 0)) {
				m_processMsg = this.setLinePriceSO(offerOrgs, offerLine);
				if (m_processMsg != null) {
					return DocAction.STATUS_Invalid;
				}
			}
			offerLine.saveEx();
		}

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
		if (log.isLoggable(Level.INFO)) log.info(toString());

		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		// Elimino oferta de venta de cada producto
		String action = " delete from z_prodsalesoffer where ad_table_id =" + MZRegularOffer.Table_ID +
				" and record_id =" + this.get_ID();
		DB.executeUpdateEx(action, get_TrxName());

		// Elimino oferta de compra de cada producto
		action = " delete from z_prodpurchaseoffer where ad_table_id =" + MZRegularOffer.Table_ID +
				" and record_id =" + this.get_ID();
		DB.executeUpdateEx(action, get_TrxName());

		// Elimino asociación de oferta con documento de envio de precios
		if (this.getZ_ConfirmacionEtiqueta_ID() > 0) {
			action = " update z_regularoffer set Z_ConfirmacionEtiqueta_ID = null where z_regularoffer_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
		}

		// After reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_AFTER_REACTIVATE);
		if (m_processMsg != null)
			return false;

		this.setProcessed(false);
		this.setProcessing(false);
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
      StringBuffer sb = new StringBuffer ("MZRegularOffer[")
        .append(getSummary()).append("]");
      return sb.toString();
    }

	/**
	 * Validaciones generales del documento.
	 * Tanane. Created by Gabriel Vila on 2022-11-22
	 * @param offerLines
	 * @return
	 */
	private String validateDocument(List<MZRegularOfferLine> offerLines) {

		// Me aseguro que fecha del documento y fecha contable no sean mayor a hoy
		Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
		if (this.getDateDoc().after(fechaHoy)){
			return "La Fecha del Documento no puede ser posterior a la fecha actual";
		}
		// Valido que haya info para procesar.
		if (offerLines.size() <= 0){
			return "No hay lineas de oferta para procesar";
		}

		return null;
	}

	@Override
	protected boolean beforeSave(boolean newRecord) {

		// Aseguro consistencia de fechas
		Timestamp today = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
		if (this.getDateDoc().after(today)) {
			this.setDateDoc(today);
		}
		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		if (!success) return false;

		if (newRecord) {
			// Si este documento tiene organización *, entonces cargo todas las organizaciones de la empresa para
			// que luego el usuario indique cuales quiere procesar
			int contOrg = 1;
			if (this.getAD_Org_ID() == 0){
				MOrg orgs[] = MOrg.getOfClient(this);
				for (int i = 0; i < orgs.length; i++){
					MZRegularOfferOrg offerOrg = new MZRegularOfferOrg(getCtx(), 0, get_TrxName());
					offerOrg.setZ_RegularOffer_ID(this.get_ID());
					offerOrg.setAD_OrgTrx_ID(orgs[i].get_ID());
					offerOrg.setIsExecuted(true);
					offerOrg.saveEx();
				}
				contOrg = orgs.length;
			}
			else{
				// Este documento tiene una organización determinada, cargo entonces esa única organización.
				MZRegularOfferOrg offerOrg = new MZRegularOfferOrg(getCtx(), 0, get_TrxName());
				offerOrg.setZ_RegularOffer_ID(this.get_ID());
				offerOrg.setAD_OrgTrx_ID(this.getAD_Org_ID());
				offerOrg.setIsExecuted(true);
				offerOrg.saveEx();
			}

			// Si manejo mas de una organización, actualizo flag
			if (contOrg > 1) {
				String action = " update z_regularoffer set isdetailorg='Y' where z_regularoffer_id =" + this.get_ID();
				DB.executeUpdateEx(action, get_TrxName());
			}
		}
		return true;
	}

	/**
	 * Obtiene y retorna lineas de este documento.
	 * Tanane. Created by Gabriel Vila on 2022-11-22
	 * @return
	 */
	public List<MZRegularOfferLine> getLines() {

		String whereClause = X_Z_RegularOfferLine.COLUMNNAME_Z_RegularOffer_ID + " =" + this.get_ID();

		List<MZRegularOfferLine> lines = new Query(getCtx(), I_Z_RegularOfferLine.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}

	/**
	 * Carga información desde archivo CSV recibido.
	 * Tanane. Created by Gabriel Vila on 2022-11-22
	 * @param fileName
	 * @return
	 */
	public String loadDataFile(String fileName) {

		if ((fileName == null) || (fileName.trim().equalsIgnoreCase(""))){
			return "Debe indicar Archivo a Procesar.";
		}
		// Actualizo flag de manejo de detalle por organizaciones.
		if (this.isDetailOrg()) {
			this.refreshIsDetailOrganization();
		}

		// Leo archivo con formato de importación
		this.getDataFromFile(fileName);

		// Seteo tabla de lineas de este documento (productos) a partir de las lineas leídas del arhivo
		this.setDataFromFile(true);

		this.saveEx();

		return null;
	}

	/**
	 * Refresco flag de manejo de detalle por organizaciones.
	 * Si el mismo estabe en True y ahora solo hay una organización seleccionada, lo dejo en False.
	 * Tanane. Created by Gabriel Vila on 2022-11-24
	 */
	private void refreshIsDetailOrganization() {

		try {
			if (!this.isDetailOrg()) return;

			String sql = " select count(*) as contador " +
					" from Z_RegularOfferOrg " +
					" where Z_RegularOffer_ID =" + this.get_ID() +
					" and isexecuted ='Y' ";
			int countRows = DB.getSQLValueEx(get_TrxName(), sql);
			if (countRows <= 0) {
				throw new AdempiereException("No hay organizaciones seleccionadas");
			}
			// Si dejo una sola organización seleccionada, indico que no maneja detalle por organizaciones.
			if (countRows == 1) {
				this.setIsDetailOrg(false);
				this.saveEx();
			}
		}
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	/**
	 * Lee archivo de importación de datos y carga información en tabla correspondiente.
	 * Tanane. Created by Gabriel Vila on 2022-11-24
	 * @param fileName
	 */
	private void getDataFromFile(String fileName) {

		FileReader fReader = null;
		BufferedReader bReader = null;
		String lineaArchivo = null;
		String action;

		try{
			// Obtengo ID de formato de importación de datos
			String importDataName = "Z_Retail_RegularOffer";
			String whereClause = "lower(" + MImpFormat.COLUMNNAME_Name + ")='" + importDataName.toLowerCase() + "'";
			int[] impFormatIDs = MImpFormat.getAllIDs(MImpFormat.Table_Name, whereClause, null);
			if (impFormatIDs.length <= 0) {
				throw new AdempiereException("No se encuentra Formato de Importación");
			}

			// Formato de importación de archivo de interface de precios de proveedor
			ImpFormat dataImportFile = ImpFormat.load(importDataName);

			// Abro archivo
			File dataFile = new File(fileName);
			fReader = new FileReader(dataFile);
			bReader = new BufferedReader(fReader);

			int contLineas = 0;
			int fileID = 0;

			// Leo lineas del archivo
			lineaArchivo = bReader.readLine();

			while (lineaArchivo != null) {

				lineaArchivo = lineaArchivo.replace("'", "");
				contLineas++;

				fileID = dataImportFile.updateDB(lineaArchivo, getCtx(), get_TrxName());

				if (fileID <= 0){
					throw new AdempiereException("Error al procesar linea " + contLineas + " : " + lineaArchivo);
				}
				else{
					// Seteo atributos de linea procesada en tabla
					action = " update " + I_Z_RegularOfferFile.Table_Name +
							" set " + I_Z_RegularOfferFile.COLUMNNAME_Z_RegularOffer_ID + " = " + this.get_ID() + ", " +
							" LineNo =" + contLineas + ", " +
							" FileLineText ='" + lineaArchivo + "' " +
							" where " + X_Z_RegularOfferFile.COLUMNNAME_Z_RegularOfferFile_ID + " = " + fileID;
					DB.executeUpdateEx(action, get_TrxName());
				}
				lineaArchivo = bReader.readLine();
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			if (bReader != null){
				try{
					bReader.close();
					if (fReader != null){
						fReader.close();
					}
				}
				catch (Exception e){
					log.log(Level.SEVERE, e.getMessage());
				}
			}
		}
	}

	/**
	 * Actualiza información a partir de datos leídos desde archivo de interface
	 * Tanane. Created by Gabriel Vila on 2022-11-24
	 * @param allLines: true si considera todas las lineas, false si considera solamente aquellas lineas con inconsistencias y
	 * 					que no estan marcadas para omitir.
	 */
	private void setDataFromFile(boolean allLines) {

		HashMap<String, Integer> hashUPC = new HashMap<String, Integer>();
		HashMap<String, Integer> hashValue = new HashMap<String, Integer>();
		String whereClause;
		boolean isError = false;

		try{
			Timestamp today = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

			// Obtengo lineas leídas del archivo de interface segun flag recibido
			List<MZRegularOfferFile> offerFiles = null;
			if (allLines){
				offerFiles = this.getFileLines();
			}
			else{
				offerFiles = this.getFileNotValidLines();
			}
			// Recorro y proceso lineas
			for (MZRegularOfferFile offerFile: offerFiles) {

				MZRegularOfferLine offerLine = new MZRegularOfferLine(getCtx(), 0, get_TrxName());
				offerLine.setZ_RegularOffer_ID(this.get_ID());
				offerLine.setC_Currency_ID_To(this.getC_Currency_ID());
				offerLine.setIsValid(true);
				offerLine.setIsManual(false);

				MProduct product = null;

				// Verifico si producto de esta linea, es nuevo o ya existe en el sistema.
				// Primero busco producto por código de barras
				if (offerFile.getUPC() != null){
					if (!offerFile.getUPC().trim().equalsIgnoreCase("")){

						// Codigo de barras solo con carácteres numéricos
						String upc = offerFile.getUPC().trim().replaceAll("[^0-9]", "");

						// Controlo código de barra repetido en otra linea del mismo archivo
						if (hashUPC.containsKey(upc)){
							isError = true;
							offerFile.setIsValid(false);
							offerFile.setErrorMsg("Código de barras repetido en lineas de archivo : " + offerFile.getLineNo() + " y " + hashUPC.get(upc).intValue());
							offerFile.saveEx();
							continue;
						}
						// Si estoy reprocesando lineas inconsistentes, controlo código de barras duplicado contra lineas ya procesadas
						if (!allLines){
							MZRegularOfferFile offerFileAux = this.getFileLineValidByUpc(upc);
							if ((offerFileAux != null) && (offerFileAux.get_ID() > 0)){
								isError = true;
								offerFile.setIsValid(false);
								offerFile.setErrorMsg("Código de barras repetido en lineas de archivo : " + offerFileAux.getLineNo() + " y " + offerFile.getLineNo());
								offerFile.saveEx();
								continue;
							}
						}
						hashUPC.put(upc, offerFile.getLineNo());
						offerLine.setUPC(upc);

						MZProductoUPC pupc = MZProductoUPC.getByUPC(getCtx(), offerFile.getUPC().trim(), get_TrxName());
						if ((pupc != null) && (pupc.get_ID() > 0)){
							// Tengo producto por codigo de barras
							product = (MProduct) pupc.getM_Product();
						}
					}
				}

				// Si no tengo producto, busco por codigo interno
				if (product == null){
					// Codigo interno
					if ((offerFile.getProdCode() != null) && (!offerFile.getProdCode().trim().equalsIgnoreCase(""))){

						String valueProd = offerFile.getProdCode().trim();

						// Controlo código interno repetido en otra linea del mismo archivo
						if (hashValue.containsKey(valueProd)){
							isError = true;
							offerFile.setIsValid(false);
							offerFile.setErrorMsg("Código Interno repetido en lineas de archivo : " + offerFile.getLineNo() + " y " + hashValue.get(valueProd).intValue());
							offerFile.saveEx();
							continue;
						}

						hashValue.put(valueProd, offerFile.getLineNo());
						whereClause = MProduct.COLUMNNAME_Value + " ='" + valueProd + "' ";
						MProduct[] prods = MProduct.get(getCtx(), whereClause, get_TrxName());
						if (prods != null){
							if (prods.length > 0){
								// Tengo producto por codigo interno
								product = (MProduct) prods[0];
								offerLine.setUPC(product.getUPC());
							}
						}
					}
				}
				// Si tengo producto, lo marco en la tabla de lineas
				if ((product != null) && (product.get_ID() > 0)){
					offerLine.setM_Product_ID(product.get_ID());
				}
				else{
					// Aviso que este producto no existe
					isError = true;
					offerFile.setIsValid(false);
					offerFile.setErrorMsg("No existe en el sistema el producto indicado en linea de archivo : " + offerFile.getLineNo());
					offerFile.saveEx();
					continue;
				}

				// Codigo de producto del proveedor
				if ((offerFile.getVendorProductNo() != null) && (!offerFile.getVendorProductNo().trim().equalsIgnoreCase(""))){
					offerLine.setVendorProductNo(offerFile.getVendorProductNo().trim());
				}

				// Monedas
				if (offerFile.getPurchaseCurrency_ID() > 0) {
					offerLine.setPurchaseCurrency_ID(offerFile.getPurchaseCurrency_ID());
				}
				if (offerFile.getSalesCurrency_ID() > 0) {
					offerLine.setSalesCurrency_ID(offerFile.getSalesCurrency_ID());
				}

				// Precio especial de compra
				offerLine.setNewPricePO(offerFile.getPricePO());
				// Descuento de compra
				offerLine.setDiscount(offerFile.getDiscount());
				// Precio de Venta
				offerLine.setNewPriceSO(offerFile.getPriceSO());
				// Bonificacion en unidades
				offerLine.setQtyPromo(offerFile.getQtyPromo());
				offerLine.setBreakValue(offerFile.getBreakValue());

				// Si no es oferta periódica solo de venta
				if (!this.getRegularOfferType().equalsIgnoreCase(X_Z_RegularOffer.REGULAROFFERTYPE_OfertaSoloDeVenta)) {
					// Valido que tenga precio especial de compra o descuento de compra
					if (((offerLine.getNewPricePO() == null) || (offerLine.getNewPricePO().compareTo(Env.ZERO) <= 0))
							&& ((offerLine.getDiscount() == null) || (offerLine.getDiscount().compareTo(Env.ZERO) <= 0))) {
						isError = true;
						offerFile.setIsValid(false);
						offerFile.setErrorMsg("Debe indicar Precio de Compra o Porcentaje de Descuento en linea de archivo : " + offerFile.getLineNo());
						offerFile.saveEx();
						continue;
					}
					// Valido que si tiene un valor de promo, tenga el otro
					if (((offerLine.getQtyPromo() != null) && (offerLine.getBreakValue() == null))
							|| ((offerLine.getQtyPromo() == null) && (offerLine.getBreakValue() != null))) {
						isError = true;
						offerFile.setIsValid(false);
						offerFile.setErrorMsg("Debe indicar Cantidad Bonificada y Cantidad de Corte, o ninguna de ellas, en linea de archivo : " + offerFile.getLineNo());
						offerFile.saveEx();
						continue;
					}
				}
				else {
					// Si es oferta periódica solo de venta, valido que se haya ingresado el precio de venta
					if ((offerLine.getNewPriceSO() == null) || (offerLine.getNewPriceSO().compareTo(Env.ZERO) <= 0)) {
						isError = true;
						offerFile.setIsValid(false);
						offerFile.setErrorMsg("Debe indicar Precio de Venta en linea de archivo : " + offerFile.getLineNo());
						offerFile.saveEx();
						continue;
					}
				}

				// Todas las orgs, seleccionadas en este documento, van a tener los mismos precios en esta instancia.
				offerLine.setIsDifferPriceSO(false);
				offerLine.setIsDifferPricePO(false);

				// Si no es oferta periódica solo de venta
				if (!this.getRegularOfferType().equalsIgnoreCase(X_Z_RegularOffer.REGULAROFFERTYPE_OfertaSoloDeVenta)) {
					// Obtengo datos actuales de precio de compra
					MZProductoSocio vendorLineProd = null;
					// Si es oferta por proveedor
					if (this.getRegularOfferType().equalsIgnoreCase(X_Z_RegularOffer.REGULAROFFERTYPE_OfertaPorProveedor)) {
						// Obtengo ficha de producto-socio para el proveedor seleccionado
						vendorLineProd = MZProductoSocio.getByBPartnerProduct(getCtx(), this.getC_BPartner_ID(), product.get_ID(), null);
					}
					else {
						// Oferta General
						// Obtengo ficha de producto-socio para el proveedor con la última factura de compra ingresada
						vendorLineProd = MZProductoSocio.getByLastInvoice(getCtx(), product.get_ID(), null);
						// Si no obtuve por ultima factura, busco la ficha según última fecha de vigencia de precio de compra
						if ((vendorLineProd == null) || (vendorLineProd.get_ID() <= 0)) {
							vendorLineProd = MZProductoSocio.getByLastPriceOC(getCtx(), product.get_ID(), null);
						}
					}
					// Si obtuve ficha cargo datos de precios de compra
					if ((vendorLineProd != null) && (vendorLineProd.get_ID() > 0)) {
						offerLine.setC_Currency_ID(vendorLineProd.getC_Currency_ID());
						offerLine.setPricePO(vendorLineProd.getPricePO());
					}
					else {
						// Si no obtuve ficha y por lo tano no tengo datos de Precio OC actual, no es válido
						isError = true;
						offerFile.setIsValid(false);
						offerFile.setErrorMsg("No se obtuvieron datos de Precio OC actual en linea de archivo : " + offerFile.getLineNo());
						offerFile.saveEx();
						continue;
					}
					// Tasa de cambio entre moneda de compra actual (si tengo) y moneda especial de compra
					Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
					BigDecimal rate = Env.ONE;
					int conversionTypeID = 114;
					if ((offerLine.getC_Currency_ID() > 0) && (offerLine.getPurchaseCurrency_ID() > 0)) {
						if (offerLine.getC_Currency_ID() != offerLine.getPurchaseCurrency_ID()){
							rate = CurrencyUtils.getCurrencyRate(getCtx(), this.getAD_Client_ID(), 0, offerLine.getC_Currency_ID(),
									offerLine.getPurchaseCurrency_ID(), conversionTypeID, fechaHoy, null);
							if (rate == null){
								isError = true;
								offerFile.setIsValid(false);
								offerFile.setIsExclude(true);
								offerFile.setErrorMsg("Tasa de Cambio No Encontrada");
								offerFile.saveEx();
								continue;
							}
						}
					}
					offerLine.setCurrencyRate(rate);

					// Validaciones y seteos del precio de compra de oferta
					BigDecimal priceOffer = offerLine.getNewPricePO();
					// Si no vino el precio de compra oferta lo calculo y seteo ahora
					if ((priceOffer == null) || (priceOffer.compareTo(Env.ZERO) <= 0)) {
						priceOffer = offerLine.getPricePO().multiply(offerLine.getDiscount().divide(Env.ONEHUNDRED, 2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);
						priceOffer = priceOffer.multiply(offerLine.getCurrencyRate()).setScale(2, RoundingMode.HALF_UP);
						offerLine.setNewPricePO(priceOffer);
					}
					// Si el precio compra actual es menor al precio de compra oferta (en misma moneda), no es válido
					BigDecimal priceSource = priceOffer.divide(offerLine.getCurrencyRate(), 2, RoundingMode.HALF_UP);
					if (offerLine.getPricePO().compareTo(priceSource) <= 0) {
						isError = true;
						offerFile.setIsValid(false);
						offerFile.setErrorMsg("Precio de Compra Oferta no puede ser mayor o igual al Precio OC actual en linea de archivo : " + offerFile.getLineNo());
						offerFile.saveEx();
						continue;
					}
				}

				// Seteos datos de Precio de Venta
				// Obtengo lista de precios en donde este este producto (solo puede estar en una lista de venta por moneda)
				MPriceListVersion plvSO = PriceListUtils.getPriceListVersion(getCtx(), this.getAD_Client_ID(), this.getAD_Org_ID(), product.get_ID(), today, true, get_TrxName());
				MProductPrice productPrice = null;
				if (plvSO != null){
					productPrice = MProductPrice.get(getCtx(), plvSO.get_ID(), product.get_ID(), null);
					if (productPrice != null){
						offerLine.setPriceSO(productPrice.getPriceList());
					}
					MPriceList priceList = (MPriceList) plvSO.getM_PriceList();
					offerLine.setC_Currency_ID_To(priceList.getC_Currency_ID());
					offerLine.setM_PriceList_Version_ID(plvSO.get_ID());
				}
				else {
					offerLine.setPriceSO(null);
				}

				offerLine.saveEx();

				offerFile.setIsValid(true);
				offerFile.setErrorMsg(null);
				offerFile.saveEx();
			}

			// Marco cabezal con inconsistencias en caso que hubiera
			if (isError){
				this.setIsError(true);
				this.saveEx();
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
	}

	/**
	 * Obtiene y retorna todas las lineas leídas desde archivo.
	 * Tanane. Created by Gabriel Vila on 2022-11-24
	 * @return
	 */
	public List<MZRegularOfferFile> getFileLines(){

		String whereClause = X_Z_RegularOfferFile.COLUMNNAME_Z_RegularOffer_ID + "=" + this.get_ID();

		List<MZRegularOfferFile> lines = new Query(getCtx(), I_Z_RegularOfferFile.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}

	/**
	 * Obtiene y retorna lineas leidas desde archivos que no son validas y no estan excluídas para su proceso.
	 * Tanane. Created by Gabriel Vila on 2022-11-24
	 * @return
	 */
	private List<MZRegularOfferFile> getFileNotValidLines() {

		String whereClause = X_Z_RegularOfferFile.COLUMNNAME_Z_RegularOffer_ID + " =" + this.get_ID() +
				" AND " + X_Z_RegularOfferFile.COLUMNNAME_IsValid + " ='N'" +
				" AND " + X_Z_RegularOfferFile.COLUMNNAME_IsExclude + " ='N'";

		List<MZRegularOfferFile> lines = new Query(getCtx(), I_Z_RegularOfferFile.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}

	/**
	 * Obtiene y retorna linea de archivo según UPC recibido.
	 * Tanane. Created by Gabriel Vila on 2022-11-24
	 * @param upc
	 * @return
	 */
	private MZRegularOfferFile getFileLineValidByUpc(String upc) {

		String whereClause = X_Z_RegularOfferFile.COLUMNNAME_Z_RegularOffer_ID + " =" + this.get_ID() +
				" AND " + X_Z_RegularOfferFile.COLUMNNAME_UPC + " ='" + upc + "'";

		MZRegularOfferFile model = new Query(getCtx(), I_Z_RegularOfferFile.Table_Name, whereClause, get_TrxName()).first();

		return model;
	}

	/**
	 * Obtiene y retorna organizaciones seleccionadas en este documento.
	 * Tanane. Created by Gabriel Vila on 2022-12-06
	 * @return
	 */
	public List<MZRegularOfferOrg> getSelectedOrgs(){

		String whereClause = X_Z_RegularOfferOrg.COLUMNNAME_Z_RegularOffer_ID + " =" + this.get_ID() +
				" AND " + X_Z_RegularOfferOrg.COLUMNNAME_IsExecuted + " ='Y' ";
		List<MZRegularOfferOrg> lines = new Query(getCtx(), I_Z_RegularOfferOrg.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}

	/**
	 * Obtiene y retorna organizaciones NO seleccionadas en este documento.
	 * Tanane. Created by Gabriel Vila on 2022-12-06
	 * @return
	 */
	public List<MZRegularOfferOrg> getNotSelectedOrgs(){

		String whereClause = X_Z_RegularOfferOrg.COLUMNNAME_Z_RegularOffer_ID + " =" + this.get_ID() +
				" AND " + X_Z_RegularOfferOrg.COLUMNNAME_IsExecuted + " ='N' ";
		List<MZRegularOfferOrg> lines = new Query(getCtx(), I_Z_RegularOffer.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}

	/**
	 * Setea información de precio de oferta de venta para un determinado producto y organizaciones.
	 * Tanane. Created by Gabriel Vila on 2022-12-08
	 * @param offerOrgs
	 * @param offerLine
	 */
	private String setLinePriceSO(List<MZRegularOfferOrg> offerOrgs, MZRegularOfferLine offerLine) {

		try {
			// Recorro organizaciones a considerar
			for (MZRegularOfferOrg offerOrg: offerOrgs) {
				// Precio oferta de venta, Moneda y version de lista de precio
				int plVersionID = offerLine.getM_PriceList_Version_ID();
				int cCurrencyID = offerLine.getC_Currency_ID_To();
				int offerCurrencyID = offerLine.getSalesCurrency_ID();
				BigDecimal newPriceSO = offerLine.getNewPriceSO();
				// Si esta linea maneja precios diferenciales de compra por organización y no estoy procesando la organizacion *
				if ((offerLine.isDifferPriceSO()) && (offerOrg.getAD_OrgTrx_ID() != 0)) {
					// Busco modelo según organización
					MZRegOfferLineOrg offerLineOrg = offerLine.getLineOrg(offerOrg.getAD_OrgTrx_ID());
					// Si tengo, entonces el precio oferta de venta y moneda que tengo que considerar es el diferencial para esta organizacion
					if ((offerLineOrg != null) && (offerLineOrg.get_ID() > 0)) {
						cCurrencyID = offerLineOrg.getC_Currency_ID_To();
						plVersionID = offerLine.getM_PriceList_Version_ID();
						newPriceSO = offerLineOrg.getNewPriceSO();
						offerCurrencyID = offerLineOrg.getSalesCurrency_ID();
					}
				}
				if (offerCurrencyID > 0) {
					// Si la moneda de venta actual es distinta a la moneda especial de venta
					if (cCurrencyID != offerCurrencyID) {
						// Tengo que obtener id de version de lista de precios para esta moneda
						MPriceList pl = PriceListUtils.getPriceListByOrg(getCtx(), this.getAD_Client_ID(), offerOrg.getAD_OrgTrx_ID(), offerCurrencyID, true, false, null);
						if ((pl != null) && (pl.get_ID() > 0)) {
							MPriceListVersion plv = pl.getPriceListVersion(null);
							plVersionID = plv.get_ID();
						}
						else {
							MOrg org = new MOrg(getCtx(), offerOrg.getAD_OrgTrx_ID(), null);
							MCurrency currency = new MCurrency(getCtx(), offerCurrencyID, null);
							return "No se pudo obtener lista de precios de venta para la organización: " + org.getName() +
									", y Moneda :" + currency.getISO_Code();

						}
					}
				}

				int finalCurrencyID = cCurrencyID;
				if (offerCurrencyID > 0) finalCurrencyID = offerCurrencyID;

				// Intancio y guardo nuevo modelo de asociación de oferta a este producto en esta organizacion (no considero la org. *)
				if (offerOrg.getAD_OrgTrx_ID() != 0) {
					MZProdSalesOffer prodSalesOffer = new MZProdSalesOffer(getCtx(), 0, get_TrxName());
					prodSalesOffer.setAD_Org_ID(offerOrg.getAD_OrgTrx_ID());
					prodSalesOffer.setAD_Table_ID(MZRegularOffer.Table_ID);
					prodSalesOffer.setC_Currency_ID(finalCurrencyID);
					prodSalesOffer.setC_DocType_ID(this.getC_DocType_ID());
					prodSalesOffer.setDocumentNoRef(this.getDocumentNo());
					prodSalesOffer.setEndDate(this.getDateTo());
					prodSalesOffer.setM_Product_ID(offerLine.getM_Product_ID());
					prodSalesOffer.setPrice(newPriceSO);
					prodSalesOffer.setRecord_ID(this.get_ID());
					prodSalesOffer.setStartDate(this.getDateFrom());
					prodSalesOffer.saveEx();
				}

				// Actualizo precios de venta del producto de esta linea
				MPriceListVersion plVersion = new MPriceListVersion(getCtx(), plVersionID, get_TrxName());
				this.updateProductPriceListSO(plVersion, offerLine.getM_Product_ID(), finalCurrencyID, newPriceSO);
			}
		}
		catch (Exception e) {
			throw new AdempiereException(e);
		}
		return null;
	}

	/**
	 * Setea información de precio de oferta de compra para un determinado producto y organizaciones.
	 * Tanane. Created by Gabriel Vila on 2022-12-06
	 * @param offerOrgs
	 * @param offerLine
	 * @return
	 */
	private void setLinePricePO(List<MZRegularOfferOrg> offerOrgs, MZRegularOfferLine offerLine) {

		try {
			// Recorro organizaciones a considerar
			for (MZRegularOfferOrg offerOrg: offerOrgs) {

				// Precio de oferta de compra y moneda tomada desde la linea
				BigDecimal newPricePO = offerLine.getNewPricePO();
				int cCurrencyID = offerLine.getPurchaseCurrency_ID();
				// Si esta linea maneja precios diferenciales de compra por organización y no estoy procesando la organizacion *
				if ((offerLine.isDifferPriceSO()) && (offerOrg.getAD_OrgTrx_ID() != 0)) {
					// Busco modelo según organización
					MZRegOfferLineOrg offerLineOrg = offerLine.getLineOrg(offerOrg.getAD_OrgTrx_ID());
					// Si tengo, entonces el precio oferta de compra y moneda que tengo que considerar es el diferencial para esta organizacion
					if ((offerLineOrg != null) && (offerLineOrg.get_ID() > 0)) {
						newPricePO = offerLineOrg.getNewPricePO();
						cCurrencyID = offerLineOrg.getPurchaseCurrency_ID();
					}
				}
				// Intancio y guardo nuevo modelo de asociación de oferta a este producto en esta organizacion (no considero la org. *)
				if (offerOrg.getAD_OrgTrx_ID() != 0) {
					MZProdPurchaseOffer prodPurchaseOffer = new MZProdPurchaseOffer(getCtx(), 0, get_TrxName());
					prodPurchaseOffer.setAD_Org_ID(offerOrg.getAD_OrgTrx_ID());
					prodPurchaseOffer.setAD_Table_ID(MZRegularOffer.Table_ID);
					if (this.getC_BPartner_ID() > 0) {
						prodPurchaseOffer.setC_BPartner_ID(this.getC_BPartner_ID());
					}
					prodPurchaseOffer.setC_Currency_ID(cCurrencyID);
					prodPurchaseOffer.setC_DocType_ID(this.getC_DocType_ID());
					prodPurchaseOffer.setDocumentNoRef(this.getDocumentNo());
					prodPurchaseOffer.setEndDate(this.getDateTo());
					prodPurchaseOffer.setM_Product_ID(offerLine.getM_Product_ID());
					prodPurchaseOffer.setPrice(newPricePO);
					prodPurchaseOffer.setRecord_ID(this.get_ID());
					prodPurchaseOffer.setStartDate(this.getDateFrom());
					prodPurchaseOffer.saveEx();
				}
			}
		}
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}

	/**
	 * Actualiza lista de precios de venta para el producto de la linea recibida.
	 * Tanane. Created by Gabriel Vila on 2021-08-07
	 * @param plVersion
	 * @param mProductID
	 * @param cCurrencyID
	 * @param newPriceSO
	 */
	private void updateProductPriceListSO(MPriceListVersion plVersion, int mProductID, int cCurrencyID, BigDecimal newPriceSO) {

		String sql;

		try {
			MProductPrice productPrice = null;

			// Verifico si tengo precio de lista actual para el producto de esta linea.
			sql = " select count(*) as contador " +
					" from m_productprice " +
					" where m_pricelist_version_id =" + plVersion.get_ID() +
					" and m_product_id =" + mProductID;
			int contador = DB.getSQLValueEx(get_TrxName(), sql);
			if (contador <= 0){
				// Inserto nuevo producto con precio
				productPrice = new MProductPrice(getCtx(), 0, get_TrxName());
				productPrice.setAD_Org_ID(plVersion.getAD_Org_ID());
				productPrice.setM_PriceList_Version_ID(plVersion.get_ID());
				productPrice.setM_Product_ID(mProductID);
				productPrice.setPriceList(newPriceSO);
				productPrice.setPriceLimit(newPriceSO);
				productPrice.setPriceStd(newPriceSO);
				productPrice.set_ValueOfColumn("ValidFrom", this.getDateFrom());
				productPrice.set_ValueOfColumn("C_DocType_ID", this.getC_DocType_ID());
				productPrice.set_ValueOfColumn("DocumentNoRef", this.getDocumentNo());
				productPrice.saveEx();
			}
			else{
				productPrice = MProductPrice.get(getCtx(), plVersion.get_ID(), mProductID, get_TrxName());
				Env.setContext(getCtx(), "UpdatePrice", "Y");
				// Actualizo fecha de precio recibido para que se disparen los Validators de cambio en la M_ProductPrice
				// Necesito actualizar al menos un campo para eso.
				productPrice.set_ValueOfColumn("DateReceived", new Timestamp(System.currentTimeMillis()));
				productPrice.saveEx();
			}
			// Actualizo información de evolución de precio de venta para este producto y organización (cuando no se trata de la organizazion *)
			if (plVersion.getAD_Org_ID() != 0) {
				//this.setPriceSOEvolution(plVersion, mProductID, cCurrencyID, newPriceSO);
			}
		}
		catch (Exception e) {
			throw new AdempiereException(e);
		}
	}
}