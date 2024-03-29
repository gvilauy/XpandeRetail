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
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.impexp.ImpFormat;
import org.compiere.model.*;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.xpande.core.model.MZActividadDocumento;
import org.xpande.core.utils.PriceListUtils;

/** Generated Model for Z_ActualizacionPVP
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class MZActualizacionPVP extends X_Z_ActualizacionPVP implements DocAction, DocOptions {

	/**
	 *
	 */
	private static final long serialVersionUID = 20170719L;

    /** Standard Constructor */
    public MZActualizacionPVP (Properties ctx, int Z_ActualizacionPVP_ID, String trxName)
    {
      super (ctx, Z_ActualizacionPVP_ID, trxName);
    }

    /** Load Constructor */
    public MZActualizacionPVP (Properties ctx, ResultSet rs, String trxName)
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

		// IMPORTANTE:
		// Este documento tiene fecha vigencia desde, que puede ser futura.
		// Por este hecho, se pasa la lógica del completar a un método que puede ser luego invocado desde un proceso batch, cuando
		// se cumpla la fecha de vigencia desde.

		//	Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		// Me aseguro de tener fecha de vigencia, siempre posterior a hoy


		// Seteo flags para determinar si la vigencia de esta gestión de precios es con fecha pasada o futura.
		// Si es con vigencia en el pasado, solo debo actualizar un historico de costos.
		// Si es con vigencia en el futuro, no hago nada ahora. Dejo este documento completo y luego cuando llegue la fecha
		// de vigencia, un proceso batch simulará el completar de este documento como se debe,
		Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

		// Me aseguro de tener fecha de vigencia, siempre posterior a hoy
		if (this.getValidFrom() == null){
			this.setValidFrom(fechaHoy);
		}
		else{
			if (this.getValidFrom().before(fechaHoy)){
				this.setValidFrom(fechaHoy);
			}
		}

		Timestamp validFrom = TimeUtil.trunc(this.getValidFrom(), TimeUtil.TRUNC_DAY);

		boolean vigenciaFutura = false;
		if (validFrom.after(fechaHoy)){
			vigenciaFutura = true;
		}

		// Si este documento tiene fecha de vigencia futura
		if (vigenciaFutura){
			// Marco flag de documento con vigencia futura para luego cuando llegue la fecha, poder completarlo.
			this.setVigenciaFutura(true);
			this.setVigenciaProcesada(false);
		}
		else{
			this.setVigenciaFutura(false);
			this.setVigenciaProcesada(true);
			this.executeComplete();  // Logica del completar en otro metodo para poder ser invocado desde proceso batch.
		}

		if (m_processMsg != null){
			return DocAction.STATUS_Invalid;
		}

		//	Set Definitive Document No
		setDefiniteDocumentNo();

		setProcessed(true);
		setDocAction(DOCACTION_Close);
		return DocAction.STATUS_Completed;
	}	//	completeIt


	/***
	 * Metodo que implementa el COMPLETAR de este documento.
	 * La razón por la cual esta fuera del metodo completeIt, se debe a que este documento puede tener fecha de vigencia desde futura, lo cual
	 * lleva a que debe completarse cuando se cumpla la fecha de vigencia desde.
	 * Xpande. Created by Gabriel Vila on 4/14/21.
	 * @return
	 */
	public void executeComplete() {

		try{
			Timestamp fechaVigencia = TimeUtil.trunc(this.getValidFrom(), TimeUtil.TRUNC_DAY);

			m_processMsg = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
			if (m_processMsg != null){
				return;
			}

			//	Implicit Approval
			if (!isApproved())
				approveIt();
			log.info(toString());

			// Obtengo lineas del documento
			List<MZActualizacionPVPLin> pvpLineas = this.getLines();

			// Instancio modelo de lista de precios del documento
			MPriceList priceList = (MPriceList) this.getM_PriceList();
			MPriceListVersion priceListVersion = priceList.getPriceListVersion(null);

			// Recorro y proceso lineas
			for (MZActualizacionPVPLin pvpLinea: pvpLineas){

				// Actualizo lista de precios de venta del documento para el producto de esta linea
				this.updateProductPriceListSO(priceList, priceListVersion, pvpLinea, fechaVigencia);

				// Debo actualizar lista de venta de cada organizacion participante
				List<MZActualizacionPVPOrg> pvpOrgs = this.getSelectedOrgs();

				for (MZActualizacionPVPOrg pvpOrg: pvpOrgs){
					BigDecimal newPriceSO = pvpLinea.getNewPriceSO();
					if (pvpLinea.isDistinctPriceSO()){
						MZActualizacionPVPLinOrg pvpLinOrg = pvpLinea.getOrg(pvpOrg.getAD_OrgTrx_ID());
						newPriceSO = pvpLinOrg.getNewPriceSO();
					}
					pvpOrg.updateProductPriceListSO(pvpLinea.getM_Product_ID(), priceList.getC_Currency_ID(), newPriceSO, fechaVigencia);
				}

			}

			//	User Validation
			String valid = ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
			if (valid != null)
			{
				m_processMsg = valid;
				return;
			}

			this.setVigenciaProcesada(true);
			this.saveEx();
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

		return;
	}


	/***
	 * Actualiza lista de precios de venta para el producto de la linea recibida.
	 * Xpande. Created by Gabriel Vila on 7/20/17.
 	 * @param priceList
	 * @param priceListVersion
	 * @param pvpLinea
	 */
	private void updateProductPriceListSO(MPriceList priceList, MPriceListVersion priceListVersion, MZActualizacionPVPLin pvpLinea, Timestamp fechaVigencia) {

		try{
			boolean forcePriceUpdate = this.get_ValueAsBoolean("SendSamePrice");
			if (forcePriceUpdate){
				Env.setContext(getCtx(), "UpdatePrice", "Y");
			}
			else{
				Env.setContext(getCtx(), "UpdatePrice", "N");
			}

			// Intento obtener precio de lista actual para el producto de esta linea, en la versión de lista
			// de precios de venta recibida.
			MProductPrice pprice = MProductPrice.get(getCtx(), priceListVersion.get_ID(), pvpLinea.getM_Product_ID(), get_TrxName());

			// Si no tengo precio para este producto, lo creo.
			if ((pprice == null) || (pprice.getM_Product_ID() <= 0)){
				pprice = new MProductPrice(priceListVersion, pvpLinea.getM_Product_ID(), pvpLinea.getNewPriceSO(), pvpLinea.getNewPriceSO(), pvpLinea.getNewPriceSO());
			}
			else{
				// Actualizo precios
				pprice.setPriceList(pvpLinea.getNewPriceSO());
				pprice.setPriceStd(pvpLinea.getNewPriceSO());
				pprice.setPriceLimit(pvpLinea.getNewPriceSO());
			}
			pprice.set_ValueOfColumn("ValidFrom", fechaVigencia);
			pprice.set_ValueOfColumn("C_DocType_ID", this.getC_DocType_ID());
			pprice.set_ValueOfColumn("DocumentNoRef", this.getDocumentNo());

			pprice.saveEx();

			// Actualizo datos venta para el producto en esta lista en asociaciones producto-socio
			String action = " update z_productosocio " +
							" set priceso =" + pvpLinea.getNewPriceSO() + ", " +
							" datevalidso ='" + fechaVigencia + "' " +
							" where m_product_id =" + pvpLinea.getM_Product_ID() +
							" and m_pricelist_id_so =" + priceList.get_ID();
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
		return super.getC_Currency_ID();
	}	//	getC_Currency_ID

    @Override
    public String toString()
    {
      StringBuffer sb = new StringBuffer ("MZActualizacionPVP[")
        .append(getSummary()).append("]");
      return sb.toString();
    }

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		if (!success) return false;

		if (newRecord){

			// Carga organizaciones según organización de este documento

			// Si este documento tiene organización *, entonces cargo todas las organizaciones de la empresa para
			// que luego el usuario indique cuales quiere procesar
			if (this.getAD_Org_ID() == 0){
				MOrg orgs[] = MOrg.getOfClient(this);
				for (int i = 0; i < orgs.length; i++){
					MZActualizacionPVPOrg pOrg = new MZActualizacionPVPOrg(getCtx(), 0, get_TrxName());
					pOrg.setZ_ActualizacionPVP_ID(this.get_ID());
					pOrg.setAD_OrgTrx_ID(orgs[i].get_ID());
					pOrg.saveEx();
				}

				// Marco flag que determina si tengo mas de una organización o no para procesar
				if (orgs.length > 1){
					this.setOnlyOneOrg(false);
				}
				else{
					this.setOnlyOneOrg(true);
				}
			}
			else{
				// Este documento tiene una organización determinada, cargo entonces esa única organización.
				MZActualizacionPVPOrg pOrg = new MZActualizacionPVPOrg(getCtx(), 0, get_TrxName());
				pOrg.setZ_ActualizacionPVP_ID(this.get_ID());
				pOrg.setAD_OrgTrx_ID(this.getAD_Org_ID());
				pOrg.saveEx();

				// Marco flag que indica que solo tengo una organización para procesar
				this.setOnlyOneOrg(true);
			}

			// Seto lista de precio y versión de lista de las organizaciones del proceso
			List<MZActualizacionPVPOrg> pvpOrgs = this.getAllOrgs();
			for (MZActualizacionPVPOrg pvpOrg: pvpOrgs){
				// Obtengo lista de precios para organización y moneda de este documento
				MPriceList priceList = PriceListUtils.getPriceListByOrg(getCtx(), this.getAD_Client_ID(), pvpOrg.getAD_OrgTrx_ID(), this.getC_Currency_ID(), true, null,null);
				if ((priceList == null) || (priceList.get_ID() <= 0)){
					log.saveError("ATENCIÓN", "No se pudo obtener Lista de Precios de Venta para Moneda y Organización (ID: " + pvpOrg.getAD_OrgTrx_ID() + ")");
					return false;
				}

				MPriceListVersion priceListVersion = priceList.getPriceListVersion(null);
				if ((priceListVersion == null) || (priceList.get_ID() <= 0)){
					log.saveError("ATENCIÓN", "No se pudo obtener Versión de Lista de Precios de Venta para Moneda y Organización (ID: " + pvpOrg.getAD_OrgTrx_ID() + ")");
					return false;
				}

				pvpOrg.setM_PriceList_ID(priceList.get_ID());
				pvpOrg.setM_PriceList_Version_ID(priceListVersion.get_ID());
				pvpOrg.saveEx();
			}

			this.saveEx();
		}

		return true;
	}


	@Override
	protected boolean beforeDelete() {

		String action = "";

		try{

			// Si tengo oferta asociada a esta actualizacion, quito dicha asociación al eliminar esta actualizacion
			if (this.getZ_OfertaVenta_ID() > 0){
				action = " update z_ofertaventa set z_actualizacionpvp_id = null where z_ofertaventa_id =" + this.getZ_OfertaVenta_ID();
				DB.executeUpdateEx(action, get_TrxName());
			}
		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return true;
	}

	/***
	 * Obtiene y retorna organizaciones a procesar (no discrimina si estan o no seleccionadas).
	 * Xpande. Created by Gabriel Vila on 7/19/17.
	 * @return
	 */
	public List<MZActualizacionPVPOrg> getAllOrgs(){

		String whereClause = X_Z_ActualizacionPVPOrg.COLUMNNAME_Z_ActualizacionPVP_ID + " =" + this.get_ID();

		List<MZActualizacionPVPOrg> lines =  new Query(getCtx(), I_Z_ActualizacionPVPOrg.Table_Name, whereClause, get_TrxName()).list();

		return lines;

	}


	/***
	 * Obtiene y retorna organizaciones a procesar que esten seleccionadas.
	 * Xpande. Created by Gabriel Vila on 7/19/17.
	 * @return
	 */
	public List<MZActualizacionPVPOrg> getSelectedOrgs(){

		String whereClause = X_Z_ActualizacionPVPOrg.COLUMNNAME_Z_ActualizacionPVP_ID + " =" + this.get_ID() +
				" AND " + X_Z_ActualizacionPVPOrg.COLUMNNAME_IsSelected + " ='Y'";

		List<MZActualizacionPVPOrg> lines =  new Query(getCtx(), I_Z_ActualizacionPVPOrg.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}


	/***
	 * Obtiene y retorna lineas del documento.
	 * Xpande. Created by Gabriel Vila on 7/20/17.
	 * @return
	 */
	public List<MZActualizacionPVPLin> getLines(){

		String whereClause = X_Z_ActualizacionPVPLin.COLUMNNAME_Z_ActualizacionPVP_ID + " =" + this.get_ID();

		List<MZActualizacionPVPLin> lines = new Query(getCtx(), I_Z_ActualizacionPVPLin.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}


	/***
	 * Carga datos de productos a actualizar PVP a partir de una oferta periódica recibida.
	 * @param zOfertaVentaID
	 * @return
	 */
    public String getInfoFromOffer(int zOfertaVentaID) {

		String message =  null;

		try{

			// Instancio modelo de oferta
			MZOfertaVenta ofertaVenta = new MZOfertaVenta(getCtx(), zOfertaVentaID, get_TrxName());
			if ((ofertaVenta == null) || (ofertaVenta.get_ID() <= 0)){
				return "No se obtuvo Oferta Periódica con ID =" + zOfertaVentaID;
			}


			// ME DEBO ASEGURAR QUE SI LA OFERTA SELECCIONADA TIENE N ORGANIZACIONES, LA ACTUALIZACION PVP SEA CON ORGANIZACION = *
			// Y ADEMAS EN LA PESTAÑA DE ORGANIZACIONES A CONSIDERAR SE COPIEN EXACTAMENTE LAS MISMAS ORGANIZACIONES QUE SE CONSIDERARON EN LA OFERTA.

			// Obtengo y recorro lineas de esta oferta (productos)
			List<MZOfertaVentaLin> ventaLinList = ofertaVenta.getLines();
			for (MZOfertaVentaLin ventaLin: ventaLinList){

				// Si para este linea tengo precio de venta
				if ((ventaLin.getNewPriceSO() != null) && (ventaLin.getNewPriceSO().compareTo(Env.ZERO) > 0)){

					// Cargo producto con el precio actual de venta = precio oferta, y nuevo precio = precio actual de la lista de precios de venta

				}
			}




			// Seteo asociación de oferta con este modelo y fecha de actualizacion al POS
			this.setZ_OfertaVenta_ID(zOfertaVentaID);

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return message;
    }


	/***
	 * Carga información de productos desde archivo recibido mediante formato de importación de datos.
	 * Xpande. Created by Gabriel Vila on 5/15/18.
	 * @param fileName
	 * @return
	 */
	public String getDataFromFile(String fileName) {

		FileReader fReader = null;
		BufferedReader bReader = null;

		String lineaArchivo = null;
		String action = "", sql = "";

		try{

			// Formato de importación de archivo de interface de precios de proveedor
			ImpFormat formatoImpArchivo = ImpFormat.load("Retail_ActualizacionPVP");

			// Abro archivo
			File archivoPazos = new File(fileName);
			fReader = new FileReader(archivoPazos);
			bReader = new BufferedReader(fReader);

			int contLineas = 0;
			int zActualizacionPVPArchID = 0;

			// Leo lineas del archivo
			lineaArchivo = bReader.readLine();

			while (lineaArchivo != null) {

				lineaArchivo = lineaArchivo.replace("'", "");
				contLineas++;

				zActualizacionPVPArchID = formatoImpArchivo.updateDB(lineaArchivo, getCtx(), get_TrxName());

				if (zActualizacionPVPArchID <= 0){
					return "Error al procesar linea " + contLineas + " : " + lineaArchivo;
				}
				else{

					// Seteo atributos de linea procesada en tabla
					action = " update " + I_Z_ActualizacionPVPArch.Table_Name +
							" set " + X_Z_ActualizacionPVPArch.COLUMNNAME_Z_ActualizacionPVP_ID + " = " + this.get_ID() + ", " +
							" LineNumber =" + contLineas + ", " +
							" FileLineText ='" + lineaArchivo + "' " +
							" where " + X_Z_ActualizacionPVPArch.COLUMNNAME_Z_ActualizacionPVPArch_ID + " = " + zActualizacionPVPArchID;
					DB.executeUpdateEx(action, get_TrxName());
				}

				lineaArchivo = bReader.readLine();
			}

			// Si el archivo no tiene lineas aviso y salgo.
			if (contLineas <= 0){
				return "El archivo seleccionado no tiene lineas para procesar.";
			}

			// Cargo info desde tabla de formato de importación a las tablas finales.
			List<MZActualizacionPVPArch> pvpArchList = this.getLineasArch();
			for (MZActualizacionPVPArch pvpArch: pvpArchList){

				boolean inconsistente = false;

				// Valido que se haya indicado codigo interno de producto en esta linea de archivo
				if ((pvpArch.getValue() == null) || (pvpArch.getValue().trim().equalsIgnoreCase(""))){
					pvpArch.setErrorMsg("No se indica Codigo Interno de Producto");
					pvpArch.setIsConfirmed(false);
					pvpArch.saveEx();
					inconsistente = true;
				}
				else{
					// Obtengo ID para el codigo interno leído
					sql = " select m_product_id from m_product where value='" + pvpArch.getValue().trim() + "' " +
							" and isactive ='Y' ";
					int mProductID = DB.getSQLValueEx(null, sql);
					if (mProductID <= 0){
						pvpArch.setErrorMsg("Codigo Interno de Producto incorrecto o el Producto NO esta ACTIVO en el sistema: " + pvpArch.getValue());
						pvpArch.setIsConfirmed(false);
						pvpArch.saveEx();
						inconsistente = true;
					}
					else{
						pvpArch.setM_Product_ID(mProductID);
						pvpArch.saveEx();
					}
				}

				System.out.println(pvpArch.getValue());

				if (pvpArch.getM_Product_ID() <= 0){
					pvpArch.setErrorMsg("Codigo Interno de Producto incorrecto o el Producto NO esta ACTIVO en el sistema: " + pvpArch.getValue());
					pvpArch.setIsConfirmed(false);
					pvpArch.saveEx();
					inconsistente = true;
				}

				// Valido precio de venta de esta linea
				if ((pvpArch.getPriceSO() == null) || (pvpArch.getPriceSO().compareTo(Env.ZERO) <= 0)){
					pvpArch.setErrorMsg("Precio de Venta incorrecto, para codigo interno de producto :" + pvpArch.getValue());
					pvpArch.setIsConfirmed(false);
					pvpArch.saveEx();
					inconsistente = true;
				}

				// Si no tengo inconsitencias, guardo flag en linea
				if (!inconsistente){
					pvpArch.setIsConfirmed(true);
					pvpArch.saveEx();

					// Guardo linea
					MZActualizacionPVPLin pvpLin = new MZActualizacionPVPLin(getCtx(), 0, get_TrxName());
					pvpLin.setZ_ActualizacionPVP_ID(this.get_ID());
					pvpLin.setM_Product_ID(pvpArch.getM_Product_ID());
					pvpLin.setC_Currency_ID(this.getC_Currency_ID());
					pvpLin.setNewPriceSO(pvpArch.getPriceSO());
					pvpLin.saveEx();

					// Actualizo margenes
					pvpLin.calculateMargins();
				}
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


    	return null;
	}


	/***
	 * Obtiene y retorna lineas leidas desde un archivo para este modelo de actualizacion pvp.
	 * Xpande. Created by Gabriel Vila on 5/15/18.
	 * @return
	 */
	private List<MZActualizacionPVPArch> getLineasArch(){

		String whereClause = X_Z_ActualizacionPVPArch.COLUMNNAME_Z_ActualizacionPVP_ID + " =" + this.get_ID();

		List<MZActualizacionPVPArch> lines = new Query(getCtx(), I_Z_ActualizacionPVPArch.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}


}