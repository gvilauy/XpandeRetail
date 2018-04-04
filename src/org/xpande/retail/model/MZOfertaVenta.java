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
import org.xpande.core.utils.PriceListUtils;
import org.xpande.sisteco.model.MZSistecoInterfaceOut;
import org.xpande.sisteco.model.X_Z_SistecoInterfaceOut;
import org.xpande.stech.model.MZStechInterfaceOut;
import org.xpande.stech.model.X_Z_StechInterfaceOut;

/** Generated Model for Z_OfertaVenta
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class MZOfertaVenta extends X_Z_OfertaVenta implements DocAction, DocOptions {

	/**
	 *
	 */
	private static final long serialVersionUID = 20180110L;

    /** Standard Constructor */
    public MZOfertaVenta (Properties ctx, int Z_OfertaVenta_ID, String trxName)
    {
      super (ctx, Z_OfertaVenta_ID, trxName);
    }

    /** Load Constructor */
    public MZOfertaVenta (Properties ctx, ResultSet rs, String trxName)
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

		// Obtengo lineas a procesar
		List<MZOfertaVentaLin> ventaLinList = this.getLines();

		// Validaciones del documento
		m_processMsg = this.validateDocument(ventaLinList);
		if (m_processMsg != null){
			return DocAction.STATUS_Invalid;
		}

		// Proceso lineas de la oferta (oferta nueva y correccion de oferta donde se modificaron o agregaron productos, y/o se agregaron organizaciones).
		m_processMsg = this.processLines(ventaLinList);
		if (m_processMsg != null){
			return DocAction.STATUS_Invalid;
		}

		// Si estoy en una correccion
		if (this.isModified()){
			// Proceso productos y organizaciones eliminadas de esta oferta
			m_processMsg = this.processDeleted();
			if (m_processMsg != null){
				return DocAction.STATUS_Invalid;
			}
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
		log.info("reActivateIt - " + toString());

		setProcessed(false);
		setDocStatus(DOCSTATUS_InProgress);
		setDocAction(DOCACTION_Complete);

		// Maro como modificada, para que la corrección de esta oferta siga su flujo en comunicacion al local y pos.
		// Al reactivarse, quiero bajar o subir productos a la oferta, cambiar precios, bajar o subir sucursales.
		// Esta marca me sirve para que esta oferta pueda ser seleccionada nuevamente en el proceso de comunicacion al local.
		// Este proceso será el que luego le quite la marca de modificada.
		this.setIsModified(true);

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
      StringBuffer sb = new StringBuffer ("MZOfertaVenta[")
        .append(getSummary()).append("]");
      return sb.toString();
    }


	@Override
	protected boolean beforeSave(boolean newRecord) {

		if (newRecord){

			// Si no tengo aun seteada la lista de venta, la seteo ahora.
			if (this.getM_PriceList_ID_SO() <= 0){

				// Obtengo lista de precios para organización y moneda de este documento
				MPriceList priceList = PriceListUtils.getPriceListByOrg(getCtx(), this.getAD_Client_ID(), this.getAD_Org_ID(),
						this.getC_Currency_ID_SO(), true, null);

				if ((priceList != null) && (priceList.get_ID() > 0)) {
					this.setM_PriceList_ID_SO(priceList.getM_PriceList_ID());
					// Obtengo versión de lista vigente
					MPriceListVersion plv = priceList.getPriceListVersion(null);
					if ((plv != null) && (plv.get_ID() > 0)) {
						this.setM_PriceList_Version_ID_SO(plv.get_ID());
					}
				}
			}
		}

		return true;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		if (!success) return success;

		// En caso de nuevo registro
		if (newRecord){

			// Seteo precisión decimal para precios de compra y venta
			this.setPrecisionDecimal();

			if (this.getM_PriceList_Version_ID_SO() <= 0){
				MPriceList priceList = new MPriceList(getCtx(), this.getM_PriceList_ID_SO(), get_TrxName());
				MPriceListVersion plv = priceList.getPriceListVersion(null);
				if (plv != null){
					this.setM_PriceList_Version_ID_SO(plv.get_ID());
				}
			}

			// Carga organizaciones según organización de este documento

			// Si este documento tiene organización *, entonces cargo todas las organizaciones de la empresa para
			// que luego el usuario indique cuales quiere procesar
			if (this.getAD_Org_ID() == 0){
				MOrg orgs[] = MOrg.getOfClient(this);
				for (int i = 0; i < orgs.length; i++){
					MZOfertaVentaOrg ofertaVentaOrg = new MZOfertaVentaOrg(getCtx(), 0, get_TrxName());
					ofertaVentaOrg.setZ_OfertaVenta_ID(this.get_ID());
					ofertaVentaOrg.setAD_OrgTrx_ID(orgs[i].get_ID());
					ofertaVentaOrg.saveEx();
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
				MZOfertaVentaOrg ofertaVentaOrg = new MZOfertaVentaOrg(getCtx(), 0, get_TrxName());
				ofertaVentaOrg.setZ_OfertaVenta_ID(this.get_ID());
				ofertaVentaOrg.setAD_OrgTrx_ID(this.getAD_Org_ID());
				ofertaVentaOrg.saveEx();

				// Marco flag que indica que solo tengo una organización para procesar
				this.setOnlyOneOrg(true);
			}

			this.saveEx();
		}


		return true;
	}

	/***
	 * Setea precisión decimal para precios de venta.
	 * Xpande. Created by Gabriel Vila on 1/10/18.
	 */
	private void setPrecisionDecimal(){

		try{

			// Obtengo precisión decimal para precios según precisión de la lista, o en caso de no tener lista,
			// tomo la precisión de la moneda directamente

			// Precisión decimal para precios de venta
			if (this.getM_PriceList_ID_SO() > 0){
				MPriceList pl = new MPriceList(getCtx(), this.getM_PriceList_ID_SO(), null);
				this.setPrecisionSO(pl.getPricePrecision());
			}
			else{
				MCurrency cur = new MCurrency(getCtx(), this.getC_Currency_ID_SO(), null);
				this.setPrecisionSO(cur.getCostingPrecision());
			}

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

	}


	/***
	 * Obtiene y retorna lista de organizaciones seleccionadas para participar de este proceso de ofertas periódicas.
	 * Xpande. Created by Gabriel Vila on 1/10/18.
	 * @return
	 */
	public List<MZOfertaVentaOrg> getOrgsSelected() {

		String whereClause = X_Z_OfertaVentaOrg.COLUMNNAME_Z_OfertaVenta_ID + " =" + this.get_ID() +
				" AND " + X_Z_OfertaVentaOrg.COLUMNNAME_IsSelected + " ='Y'";

		List<MZOfertaVentaOrg> lines = new Query(getCtx(), I_Z_OfertaVentaOrg.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}


	/***
	 * Obtiene y retorna lineas de este proceso
	 * Xpande. Created by Gabriel Vila on 1/10/18.
	 * @return
	 */
	public List<MZOfertaVentaLin> getLines() {

		String whereClause = X_Z_OfertaVentaLin.COLUMNNAME_Z_OfertaVenta_ID + " =" + this.get_ID();

		List<MZOfertaVentaLin> lines = new Query(getCtx(), I_Z_OfertaVentaLin.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}


	/***
	 * Validaciones del documento al momento de completar.
	 * Xpande. Created by Gabriel Vila on 1/10/18.
	 * @return
	 */
	private String validateDocument(List<MZOfertaVentaLin> ventaLinList) {

		String message = null;

		try{

			Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

			// Verifico que tenga lineas
			if (ventaLinList.size() <= 0){
				return "No hay lineas para procesar.";
			}

			// Verifico rango de fechas de la oferta

			if (!this.isModified()){
				if (this.getStartDate().before(fechaHoy)){
					return "Fecha Oferta Desde no puede ser anterior al día de hoy.";
				}
			}

			if (!this.getEndDate().after(fechaHoy)){
				return "Fecha Oferta Hasta no puede ser anterior o igual al día de hoy.";
			}

			if (!this.getEndDate().after(this.getStartDate())){
				return "Fecha Oferta Hasta no puede ser anterior a la fecha Oferta Desde.";
			}

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return message;

	}


	/***
	 * Obtiene y retorna linea según id de producto recibido.
	 * Xpande. Created by Gabriel Vila on 4/2/18.
	 * @param mProductID
	 * @return
	 */
	public MZOfertaVentaLin getLineByProduct(int mProductID) {

		String whereClause = X_Z_OfertaVentaLin.COLUMNNAME_Z_OfertaVenta_ID + " =" + this.get_ID() +
				" AND " + X_Z_OfertaVentaLin.COLUMNNAME_M_Product_ID + " =" + mProductID;

		MZOfertaVentaLin model = new Query(getCtx(), I_Z_OfertaVentaLin.Table_Name, whereClause, get_TrxName()).first();

		return model;
	}


	/***
	 * Genera marca para interface con POS.
	 * Xpande. Created by Gabriel Vila on 4/4/18.
	 * @param posVendor
	 * @param product
	 * @param adOrgTrxID
	 * @param newPriceSO
	 */
	private void setInterfacePOS(MZPosVendor posVendor, MProduct product, int adOrgTrxID, BigDecimal newPriceSO, boolean withOffer, int mPriceListID) {

		try{
			// Segun el proveedor de pos
			if (posVendor.getValue().equalsIgnoreCase("SISTECO")){

				// Marca Update
				MZSistecoInterfaceOut sistecoInterfaceOut = MZSistecoInterfaceOut.getRecord(getCtx(), I_M_Product.Table_ID, product.get_ID(), adOrgTrxID, get_TrxName());
				if ((sistecoInterfaceOut != null) && (sistecoInterfaceOut.get_ID() > 0)){
					// Proceso segun marca que ya tenía este producto antes de su actualización.
					// Si marca anterior es CREATE o UPDATE
					if ((sistecoInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_SistecoInterfaceOut.CRUDTYPE_CREATE))
							|| (sistecoInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_SistecoInterfaceOut.CRUDTYPE_UPDATE))){

						// Actualizo datos de esta marca para pasar precio de oferta al POS
						sistecoInterfaceOut.setWithOfferSO(withOffer);
						sistecoInterfaceOut.setPriceSO(newPriceSO);

						if (withOffer){
							sistecoInterfaceOut.setStartDate(this.getStartDate());
							sistecoInterfaceOut.setEndDate(this.getEndDate());
						}
						else{
							sistecoInterfaceOut.setM_PriceList_ID(mPriceListID);
							sistecoInterfaceOut.setStartDate(null);
							sistecoInterfaceOut.setEndDate(null);
						}

						sistecoInterfaceOut.saveEx();
						return;
					}
					else if (sistecoInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_SistecoInterfaceOut.CRUDTYPE_DELETE)){
						// Si marca anterior es DELETEAR, es porque el producto se inactivo anteriormente.
						// Si este producto sigue estando inactivo
						if (!product.isActive()){
							return; // No hago nada y respeto primer marca.
						}
					}
				}
				// Si no tenia marca, la creo ahora con accion UPDATE
				if ((sistecoInterfaceOut == null) || (sistecoInterfaceOut.get_ID() <= 0)){
					sistecoInterfaceOut = new MZSistecoInterfaceOut(getCtx(), 0, get_TrxName());
					sistecoInterfaceOut.setCRUDType(X_Z_SistecoInterfaceOut.CRUDTYPE_UPDATE);
					sistecoInterfaceOut.setAD_Table_ID(I_M_Product.Table_ID);
					sistecoInterfaceOut.setSeqNo(20);
					sistecoInterfaceOut.setRecord_ID(product.get_ID());
					sistecoInterfaceOut.setIsPriceChanged(true);
					sistecoInterfaceOut.setAD_OrgTrx_ID(adOrgTrxID);
					sistecoInterfaceOut.setWithOfferSO(withOffer);
					sistecoInterfaceOut.setPriceSO(newPriceSO);

					if (withOffer){
						sistecoInterfaceOut.setStartDate(this.getStartDate());
						sistecoInterfaceOut.setEndDate(this.getEndDate());
					}
					else{
						sistecoInterfaceOut.setM_PriceList_ID(mPriceListID);
						sistecoInterfaceOut.setStartDate(null);
						sistecoInterfaceOut.setEndDate(null);
					}
					sistecoInterfaceOut.saveEx();
				}

			}
			else if (posVendor.getValue().equalsIgnoreCase("SCANNTECH")){

				// Marca Update
				MZStechInterfaceOut scanntechInterfaceOut = MZStechInterfaceOut.getRecord(getCtx(), I_M_Product.Table_ID, product.get_ID(), adOrgTrxID, get_TrxName());
				if ((scanntechInterfaceOut != null) && (scanntechInterfaceOut.get_ID() > 0)){
					// Proceso segun marca que ya tenía este producto antes de su actualización.
					// Si marca anterior es CREATE o UPDATE
					if ((scanntechInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_StechInterfaceOut.CRUDTYPE_CREATE))
							|| (scanntechInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_StechInterfaceOut.CRUDTYPE_UPDATE))){

						// Actualizo datos de esta marca para pasar precio de oferta al POS
						scanntechInterfaceOut.setWithOfferSO(withOffer);
						scanntechInterfaceOut.setPriceSO(newPriceSO);

						if (withOffer){
							scanntechInterfaceOut.setStartDate(this.getStartDate());
							scanntechInterfaceOut.setEndDate(this.getEndDate());
						}
						else{
							scanntechInterfaceOut.setM_PriceList_ID(mPriceListID);
							scanntechInterfaceOut.setStartDate(null);
							scanntechInterfaceOut.setEndDate(null);
						}
						scanntechInterfaceOut.saveEx();

						return;
					}
					else if (scanntechInterfaceOut.getCRUDType().equalsIgnoreCase(X_Z_StechInterfaceOut.CRUDTYPE_DELETE)){
						// Si marca anterior es DELETEAR, es porque el producto se inactivo anteriormente.
						// Si este producto sigue estando inactivo
						if (!product.isActive()){
							return; // No hago nada y respeto primer marca.
						}
					}
				}
				// Si no tenia marca, la creo ahora con accion UPDATE
				if ((scanntechInterfaceOut == null) || (scanntechInterfaceOut.get_ID() <= 0)){
					scanntechInterfaceOut = new MZStechInterfaceOut(getCtx(), 0, get_TrxName());
					scanntechInterfaceOut.setCRUDType(X_Z_SistecoInterfaceOut.CRUDTYPE_UPDATE);
					scanntechInterfaceOut.setAD_Table_ID(I_M_Product.Table_ID);
					scanntechInterfaceOut.setSeqNo(20);
					scanntechInterfaceOut.setRecord_ID(product.get_ID());
					scanntechInterfaceOut.setIsPriceChanged(true);
					scanntechInterfaceOut.setAD_OrgTrx_ID(adOrgTrxID);
					scanntechInterfaceOut.setWithOfferSO(withOffer);
					scanntechInterfaceOut.setPriceSO(newPriceSO);

					if (withOffer){
						scanntechInterfaceOut.setStartDate(this.getStartDate());
						scanntechInterfaceOut.setEndDate(this.getEndDate());
					}
					else{
						scanntechInterfaceOut.setM_PriceList_ID(mPriceListID);
						scanntechInterfaceOut.setStartDate(null);
						scanntechInterfaceOut.setEndDate(null);
					}
					scanntechInterfaceOut.saveEx();
				}
			}
		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}


	/***
	 * Procesa lineas de la oferta.
	 * Xpande. Created by Gabriel Vila on 4/4/18.
	 * @param ventaLinList
	 * @return
	 */
	private String processLines(List<MZOfertaVentaLin> ventaLinList) {

		String message = null;

		try{
			// Recorro y proceso lineas de la oferta
			for (MZOfertaVentaLin ventaLin: ventaLinList){

				MProduct product = (MProduct) ventaLin.getM_Product();

				// Valido que el producto de esta linea no tengo una oferta definida dentro del mismo rango de fechas de esta oferta.
				MZProductoOferta productoOferta = MZProductoOferta.getByProductDate(getCtx(), ventaLin.getM_Product_ID(), this.getStartDate(), this.getEndDate(), get_TrxName());
				if ((productoOferta != null) && (productoOferta.get_ID() > 0)){
					MZOfertaVenta ofertaVentaAux = (MZOfertaVenta) productoOferta.getZ_OfertaVenta();
					return "Ya existe una oferta para el producto " + product.getValue() + " - " + product.getName() + " (Oferta nro.: " + ofertaVentaAux.getDocumentNo() + ")";
				}

				// Si no estoy en correccion, o si estoy y ademas este producto fue dado de alta en esta oferta en esta corrección
				if ((!this.isModified()) || (this.isModified() && ventaLin.isNew())){

					// Genero nuevo registro en tabla de ofertas para el producto de esta linea.
					// No descrimino por organización ya que por definición la oferta de venta es para todas las organizaciones por igual.
					productoOferta = new MZProductoOferta(getCtx(), 0, get_TrxName());
					productoOferta.setZ_OfertaVenta_ID(this.get_ID());
					productoOferta.setZ_OfertaVentaLin_ID(ventaLin.get_ID());
					productoOferta.setM_Product_ID(ventaLin.getM_Product_ID());
					productoOferta.setStartDate(this.getStartDate());
					productoOferta.setEndDate(this.getEndDate());
					productoOferta.saveEx();
				}

				// Si tengo oferta para precio de venta
				if ((ventaLin.getNewPriceSO() != null) && (ventaLin.getNewPriceSO().compareTo(Env.ZERO) > 0)){

					// Para cada organización a la cual se aplica esta oferta:
					// 1. Se genera nuevo registro en evolución de precios para el producto de esta linea y organización
					// 3. Se enera una marca de actualización de precio de venta para este producto al POS, indicando que es oferta y su vigencia.
					for (MZOfertaVentaOrg ventaOrg: this.getOrgsSelected()){

						// Si no estoy en correccion, o si estoy y esta organización ahora se selecciono pero antes no estaba seleccionada
						if ((!this.isModified()) || (this.isModified() && ventaLin.isModified())){

							// Genero nuevo registro en evolucion de precios del producto para esta organizacion
							MZEvolPrecioVtaProdOrg evolPrecioVtaProdOrg = new MZEvolPrecioVtaProdOrg(getCtx(), 0, get_TrxName());
							evolPrecioVtaProdOrg.setM_Product_ID(product.getM_Product_ID());
							evolPrecioVtaProdOrg.setC_Currency_ID(this.getC_Currency_ID_SO());
							evolPrecioVtaProdOrg.setAD_OrgTrx_ID(ventaOrg.getAD_OrgTrx_ID());
							evolPrecioVtaProdOrg.setDateValidSO(this.getStartDate());
							evolPrecioVtaProdOrg.setPriceSO(ventaLin.getNewPriceSO());
							evolPrecioVtaProdOrg.setAD_User_ID(Env.getAD_User_ID(getCtx()));
							evolPrecioVtaProdOrg.setC_DocType_ID(this.getC_DocType_ID());
							evolPrecioVtaProdOrg.setDocumentNoRef(this.getDocumentNo());
							evolPrecioVtaProdOrg.setZ_OfertaVenta_ID(this.get_ID());
							evolPrecioVtaProdOrg.saveEx();
						}

						// Marco la organización como seleccionada en este proceso de completar la oferta.
						ventaOrg.setIsSelectedLast(true);
						ventaOrg.saveEx();

						// Obtengo proveedor de pos para esta organización (por ahora Sisteco y Scanntech)
						MZPosVendorOrg posVendorOrg = MZPosVendor.getByOrg(getCtx(), ventaOrg.getAD_OrgTrx_ID(), get_TrxName());
						MZPosVendor posVendor = (MZPosVendor) posVendorOrg.getZ_PosVendor();

						// Si no estoy en correccion, o si estoy y ademas este producto fue modificado
						if ((!this.isModified()) || (this.isModified() && ventaLin.isModified())){

							// Genero marca para comunicacion al pos.
							this.setInterfacePOS(posVendor, product, ventaOrg.getAD_OrgTrx_ID(), ventaLin.getNewPriceSO(), true, 0);

						}
					}
				}

				// Obtengo y recorro socios de negocio a procesar para este linea (producto)
				List<MZOfertaVentaLinBP> linBPList = ventaLin.getSelectedPartners();
				for (MZOfertaVentaLinBP linBP: linBPList){

					// Si se indica precio de compra de oferta para este producto - proveedor.
					if (linBP.getNewPricePO() != null){
						if (linBP.getNewPricePO().compareTo(Env.ZERO) > 0){

							// Por ahora solo en caso que no este en correccion de oferta
							if (!this.isModified()){
								// Agrego oferta al histórico de este producto-socio
								MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(getCtx(), linBP.getC_BPartner_ID(), ventaLin.getM_Product_ID(), get_TrxName());
								if ((productoSocio == null) || (productoSocio.get_ID() <= 0)){
									return "No se pudo obtener información de Producto - Socio de Negocio.";
								}
								MZProductoSocioOferta socioOferta = new MZProductoSocioOferta(getCtx(), 0, get_TrxName());
								socioOferta.setZ_OfertaVenta_ID(this.get_ID());
								socioOferta.setZ_OfertaVentaLin_ID(ventaLin.get_ID());
								socioOferta.setZ_OfertaVentaLinBP_ID(linBP.get_ID());
								socioOferta.setZ_ProductoSocio_ID(productoSocio.get_ID());
								socioOferta.setStartDate(this.getStartDate());
								socioOferta.setEndDate(this.getEndDate());
								socioOferta.saveEx();

							}
						}
					}
				}
			}

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return message;
	}


	/***
	 * Procesa productos que durante una corrección de oferta, fueron eliminados de la misma.
	 * En este caso se deben generar marcas de interface al POS con el precio de venta de lista de estos productos.
	 * Xpande. Created by Gabriel Vila on 4/4/18.
	 * @return
	 */
	private String processDeleted() {

		String message = null;
		String action = "";

		try{
			// Si no estoy en correccion, no hago nada
			if (!this.isModified()){
				return null;
			}

			// Obtengo y recorro productos elminados en la correción de esta oferta
			List<MZOfertaVentaLinDel> linDelList = this.getLinesDeleted();
			for (MZOfertaVentaLinDel linDel: linDelList){

				MProduct product = (MProduct) linDel.getM_Product();

				// Elimino registro de asociación de esta oferta para este producto
				action = " delete from z_productooferta where z_ofertaventa_id =" + this.get_ID() +
						" and m_product_id =" + product.get_ID();
				DB.executeUpdateEx(action, get_TrxName());

				// Elimino registro de asociación de esta oferta para este producto - socios de negocio
				action = " delete from z_productosociooferta where z_ofertaventa_id =" + this.get_ID() +
						" and z_ofertaventalin_id =" + linDel.getZ_OfertaVentaLin_ID();
				DB.executeUpdateEx(action, get_TrxName());


				// Para cada organización a la cual se aplica esta oferta:
				// 1. Se genera nuevo registro en evolución de precios para el producto de esta linea y organización
				// 3. Se enera una marca de actualización de precio de venta para este producto al POS, indicando que es oferta y su vigencia.
				for (MZOfertaVentaOrg ventaOrg: this.getOrgsSelected()) {

					// Obtengo el precio de lista actual del producto según lista de precios de venta asociada a la oferta
					BigDecimal newPriceSO = Env.ZERO;
					Timestamp validFrom = null;
					MPriceList priceList = PriceListUtils.getPriceListByOrg(getCtx(), this.getAD_Client_ID(), ventaOrg.getAD_OrgTrx_ID(),
							this.getC_Currency_ID_SO(), true, null);

					if (priceList != null){
						// Obtengo versión de lista vigente
						MPriceListVersion plv = priceList.getPriceListVersion(null);
						if (plv != null){
							MProductPrice productPrice = MProductPrice.get(getCtx(), plv.get_ID(), product.get_ID(), null);
							if (productPrice != null){
								newPriceSO = productPrice.getPriceList();
								validFrom = (Timestamp) productPrice.get_Value("ValidFrom");
								if (newPriceSO.compareTo(Env.ZERO) <= 0){
									return "No se pudo obtener precio de producto (" + product.getValue() + ") en Lista de Precios de Venta : " + priceList.getName();
								}
							}
							else{
								return "No se pudo obtener precio de producto (" + product.getValue() + ") en Lista de Precios de Venta : " + priceList.getName();
							}
						}
						else{
							return "No se pudo obtener Versión de Lista de Precios de Venta : " + priceList.getName();
						}
					}
					else{
						MOrg org = new MOrg(getCtx(), ventaOrg.getAD_OrgTrx_ID(), null);
						return "No se pudo obtener Lista de Precios de Venta para la Organización : " + org.getName();
					}

					// Guardo precio en linea deleteada para luego poder ser comunicado al local
					linDel.setNewPriceSO(newPriceSO);
					linDel.setValidFrom(validFrom);
					linDel.saveEx();

					// Genero nuevo registro en evolucion de precios del producto para esta organizacion
					MZEvolPrecioVtaProdOrg evolPrecioVtaProdOrg = new MZEvolPrecioVtaProdOrg(getCtx(), 0, get_TrxName());
					evolPrecioVtaProdOrg.setM_Product_ID(product.getM_Product_ID());
					evolPrecioVtaProdOrg.setC_Currency_ID(this.getC_Currency_ID_SO());
					evolPrecioVtaProdOrg.setAD_OrgTrx_ID(ventaOrg.getAD_OrgTrx_ID());
					evolPrecioVtaProdOrg.setDateValidSO(this.getStartDate());
					evolPrecioVtaProdOrg.setPriceSO(newPriceSO);
					evolPrecioVtaProdOrg.setAD_User_ID(Env.getAD_User_ID(getCtx()));
					evolPrecioVtaProdOrg.setC_DocType_ID(this.getC_DocType_ID());
					evolPrecioVtaProdOrg.setDocumentNoRef(this.getDocumentNo());
					evolPrecioVtaProdOrg.setZ_OfertaVenta_ID(this.get_ID());
					evolPrecioVtaProdOrg.saveEx();

					// Obtengo proveedor de pos para esta organización (por ahora Sisteco y Scanntech)
					MZPosVendorOrg posVendorOrg = MZPosVendor.getByOrg(getCtx(), ventaOrg.getAD_OrgTrx_ID(), get_TrxName());
					MZPosVendor posVendor = (MZPosVendor) posVendorOrg.getZ_PosVendor();

					// Genero marca para comunicacion al pos.
					this.setInterfacePOS(posVendor, product, ventaOrg.getAD_OrgTrx_ID(), newPriceSO, false, priceList.get_ID());
				}
			}

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return message;
	}


	/***
	 * Obtiene y retorna productos eliminados en una correccion de esta oferta.
	 * Xpande. Created by Gabriel Vila on 4/4/18.
	 * @return
	 */
	public List<MZOfertaVentaLinDel> getLinesDeleted(){

		String whereClause = X_Z_OfertaVentaLinDel.COLUMNNAME_Z_OfertaVenta_ID + " =" + this.get_ID();

		List<MZOfertaVentaLinDel> lines = new Query(getCtx(), I_Z_OfertaVentaLinDel.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}

}