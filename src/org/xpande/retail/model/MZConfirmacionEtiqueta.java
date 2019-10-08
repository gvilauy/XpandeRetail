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
import java.util.Date;
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
import org.xpande.core.model.MZActividadDocumento;
import org.xpande.core.utils.DateUtils;
import org.xpande.core.utils.SequenceUtils;

/** Generated Model for Z_ConfirmacionEtiqueta
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class MZConfirmacionEtiqueta extends X_Z_ConfirmacionEtiqueta implements DocAction, DocOptions {

	/**
	 *
	 */
	private static final long serialVersionUID = 20170712L;

    /** Standard Constructor */
    public MZConfirmacionEtiqueta (Properties ctx, int Z_ConfirmacionEtiqueta_ID, String trxName)
    {
      super (ctx, Z_ConfirmacionEtiqueta_ID, trxName);
    }

    /** Load Constructor */
    public MZConfirmacionEtiqueta (Properties ctx, ResultSet rs, String trxName)
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

		// Recorro documentos y productos, y confirmo documentos.
		// Para los documentos marcados para procesar, recorro sus productos.
		// Si todos los productos tienen una accion seleccionada entonces el documento esta confirmado y asi lo marco.
		// Si alguno de sus productos no tiene una accion seleccionada, entonces el documento queda como NO confirmado.
		// Esta situacion controla que el usuario siempre indique una acción en los productos de los documentos marcados a procesar.
		this.confirmarDocumentos();

		// Obtengo documentos confirmados para procesar
		List<MZConfirmacionEtiquetaDoc> etiquetaDocs = this.getConfirmedEtiquetaDocs();

		// Valido condiciones para completar este documento
		m_processMsg = this.validateDocument(etiquetaDocs);
		if (m_processMsg != null){
			return DocAction.STATUS_Invalid;
		}

		// Seteo información de documentos procesados
		for (MZConfirmacionEtiquetaDoc etiquetaDoc: etiquetaDocs){

			// Si este documento es una oferta periódica
			if (etiquetaDoc.getAD_Table_ID() == X_Z_OfertaVenta.Table_ID){

				// Le indico que se comunico al local con este ID
				MZOfertaVenta ofertaVenta = new MZOfertaVenta(getCtx(), etiquetaDoc.getRecord_ID(), get_TrxName());
				ofertaVenta.setComunicadaLocal(this.get_ID(), this.getAD_Org_ID());
			}
		}

		// Genero registros para impresión de etiquetas
		this.generatePrintRecords();

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
		if (etiquetaDocs != null){
			actividadDocumento.setLineNo(etiquetaDocs.size());
		}
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
	 * Recorro documentos y productos, y confirmo documentos.
	 * Para los documentos marcados para procesar, recorro sus productos.
	 * Si todos los productos tienen una accion seleccionada entonces el documento esta confirmado y asi lo marco.
	 * Si alguno de sus productos no tiene una accion seleccionada, entonces el documento queda como NO confirmado.
	 * Esta situacion controla que el usuario siempre indique una acción en los productos de los documentos marcados a procesar.
	 * Xpande. Created by Gabriel Vila on 9/13/17.
	 */
	private void confirmarDocumentos() {

		String sql = "";

		try{
			// Obtengo y recorro documentos marcados para procesar
			List<MZConfirmacionEtiquetaDoc> etiquetaDocs = this.getSelectedEtiquetaDocs();
			for (MZConfirmacionEtiquetaDoc etiquetaDoc: etiquetaDocs){

				// Obtengo cantidad de productos de este documento que no tienen accion seleccionada (omitir o imprimir)
				sql = " select count(*) from Z_ConfirmacionEtiquetaProd " +
						" where Z_ConfirmacionEtiquetaDoc_ID =" + etiquetaDoc.get_ID() +
						" and isprinted='N' and isomitted='N'";
				int contador = DB.getSQLValueEx(get_TrxName(), sql);

				// Si hay productos de este documento que no tienen accion seleccionada, este documento queda como no confirmado
				if (contador > 0){
					etiquetaDoc.setIsConfirmed(false);
				}
				else {
					// Todos los productos de este documentos tienen accion seleccionada, este documento queda como confirmado
					etiquetaDoc.setIsConfirmed(true);
				}
				etiquetaDoc.saveEx();
			}

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}


	/***
	 * Genera registros para impresión de etiquetas.
	 * Xpande. Created by Gabriel Vila on 7/12/17.
	 */
	private void generatePrintRecords() {

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
		    sql = " select cprod.z_confirmacionetiquetaprod_id " +
					" from z_confirmacionetiquetaprod cprod " +
					" inner join z_confirmacionetiquetadoc cdoc on cprod.z_confirmacionetiquetadoc_id = cdoc.z_confirmacionetiquetadoc_id " +
					" inner join z_confirmacionetiqueta cab on cdoc.z_confirmacionetiqueta_id = cab.z_confirmacionetiqueta_id " +
					" inner join m_product prod on cprod.m_product_id = prod.m_product_id " +
					" where cab.z_confirmacionetiqueta_id =" + this.get_ID() +
					//" and cdoc.isconfirmed='Y' " +
					" and cprod.isprinted='Y' " +
					" and cprod.WithOfferSO='N' " +   // No imprimo etiquetas de productos que estan actualmente en una oferta vigente
					" order by prod.z_productoseccion_id, prod.z_productorubro_id, prod.z_productofamilia_id, prod.z_productosubfamilia_id, " +
					" cdoc.c_bpartner_id, cdoc.z_lineaproductosocio_id ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				// Para este producto debo agregar tantos registros de impresión como cantidad de etiquetas a imprimir
				MZConfirmacionEtiquetaProd etiquetaProd = new MZConfirmacionEtiquetaProd(getCtx(), rs.getInt("z_confirmacionetiquetaprod_id"), get_TrxName());
				for (int i = 1; i <= etiquetaProd.getQtyCount(); i++){
					MZConfirmacionEtiquetaPrint etiquetaPrint = new MZConfirmacionEtiquetaPrint(getCtx(), 0, get_TrxName());
					etiquetaPrint.setZ_ConfirmacionEtiqueta_ID(this.get_ID());
					etiquetaPrint.setImpresion_ID(this.getImpresion_ID());
					etiquetaPrint.setM_Product_ID(etiquetaProd.getM_Product_ID());
					etiquetaPrint.setPriceSO(etiquetaProd.getPriceSO().setScale(2, BigDecimal.ROUND_HALF_UP));
					etiquetaPrint.setDateValidSO(etiquetaProd.getDateValidSO());
					etiquetaPrint.setC_Currency_ID_SO(etiquetaProd.getC_Currency_ID_SO());
					etiquetaPrint.saveEx();
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

	}


	/***
	 * Validaciones al momento de completar este documento.
	 * Xpande. Created by Gabriel Vila on 7/12/17.
	 * @param etiquetaDocs
	 * @return
	 */
	private String validateDocument(List<MZConfirmacionEtiquetaDoc> etiquetaDocs) {

		String message = null;

		if (etiquetaDocs.size() <= 0){
			return "No hay Documentos confirmados para procesar";
		}

		// Valido que no hayan documentos marcados para procesar pero que no hayan quedado confirmados
		List<MZConfirmacionEtiquetaDoc> notConfirmedDocs = this.getNotConfirmedEtiquetaDocs();
		if ((notConfirmedDocs != null) && (notConfirmedDocs.size() > 0)){
			return "Hay documentos seleccionados para Procesar, pero que no fueron Confirmados en su totalidad de Productos";
		}

		return message;
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
      StringBuffer sb = new StringBuffer ("MZConfirmacionEtiqueta[")
        .append(getSummary()).append("]");
      return sb.toString();
    }

	/***
	 * Obtiene y carga documentos y cambios de precios para el proceso de confirmación de etiquetas.
	 * Xpande. Created by Gabriel Vila on 7/12/17.
	 * @return
	 */
	public String getDocuments(){

		String message = null;

		try{

			// Elimina documentos anteriores antes de cargar
			this.deleteDocuments();

			// Obtiene documentos de gestión de precios
			this.getDocPrecios();

			// Obtiene documentos de actualizacion pvp
			this.getDocActualizacionPVP();

			// Obtiene documentos de ofertas periódicas
			this.getDocOfertas();

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
		return message;
	}

	/***
	 * Obtiene documentos de Actualización de PVP a considerarse en este proceso.
	 * Xpande. Created by Gabriel Vila on 7/22/17.
	 */
	private void getDocActualizacionPVP() {

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{

			Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

			int zActualizacionPVPID = 0;
			MZConfirmacionEtiquetaDoc etiquetaDoc = null;
			int adTableID = MTable.getTable_ID(I_Z_ActualizacionPVP.Table_Name);

			sql = " select cab.z_actualizacionpvp_id, cab.c_doctype_id, cab.documentno, cab.datedoc, cab.updated, cab.updatedby, cab.datetopos, " +
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
					" where confdoc.isselected ='Y' and confdoc.ad_table_id =" + adTableID + " and conf.ad_org_id =" + this.getAD_Org_ID() + ") " +
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
					etiquetaDoc.setDateToPos(rs.getTimestamp("datetopos"));
					etiquetaDoc.save();

					zActualizacionPVPID = rs.getInt("z_actualizacionpvp_id");
				}

				// Verifico si este producto esta en oferta en este momento y marco flag de esa situación.
				// En caso de estar en oferta actualmente, este producto no debe imprimir etiqueta con el nuevo precio de venta.
				// Tampoco será enviado al POS.
				boolean tieneOfertaSO = false;
				MZProductoOferta productoOferta = MZProductoOferta.getByProductDateOrg(getCtx(), rs.getInt("m_product_id"), fechaHoy, fechaHoy, this.getAD_Org_ID(), get_TrxName());
				if ((productoOferta != null) && (productoOferta.get_ID() > 0)){
					tieneOfertaSO = true;
				}

				// Nuevo producto
				MZConfirmacionEtiquetaProd etiquetaProd = new MZConfirmacionEtiquetaProd(getCtx(), 0, get_TrxName());
				etiquetaProd.setZ_ConfirmacionEtiquetaDoc_ID(etiquetaDoc.get_ID());
				etiquetaProd.setM_Product_ID(rs.getInt("m_product_id"));
				etiquetaProd.setPriceSO(rs.getBigDecimal("newpriceso"));
				etiquetaProd.setDateValidSO(rs.getTimestamp("datedoc"));
				etiquetaProd.setC_Currency_ID_SO(rs.getInt("c_currency_id"));
				etiquetaProd.setWithOfferSO(tieneOfertaSO);
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

	/***
	 * Obtiene y carga documentos y productos con cambios de precios, para gestiones de precios de proveedor.
	 * Xpande. Created by Gabriel Vila on 7/12/17.
	 */
    private void getDocPrecios(){

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{

			Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

			int zPreciosProvCabID = 0;
			MZConfirmacionEtiquetaDoc etiquetaDoc = null;
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
					" where confdoc.isselected ='Y' and confdoc.ad_table_id =" + adTableID + " and conf.ad_org_id =" + this.getAD_Org_ID() + ") " +
					" order by cab.updated, cab.z_preciosprovcab_id";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				// Corte por id de documento de gestión de precios
				if (rs.getInt("z_preciosprovcab_id") != zPreciosProvCabID){
					// Nuevo documento
					etiquetaDoc = new MZConfirmacionEtiquetaDoc(getCtx(), 0, get_TrxName());
					etiquetaDoc.setAD_Org_ID(this.getAD_Org_ID());
					etiquetaDoc.setZ_ConfirmacionEtiqueta_ID(this.get_ID());
					etiquetaDoc.setAD_Table_ID(adTableID);
					etiquetaDoc.setRecord_ID(rs.getInt("z_preciosprovcab_id"));
					etiquetaDoc.setAD_User_ID(rs.getInt("updatedby"));
					etiquetaDoc.setC_DocTypeTarget_ID(rs.getInt("c_doctype_id"));
					etiquetaDoc.setDateDoc(rs.getTimestamp("updated"));
					etiquetaDoc.setDocumentNoRef(rs.getString("documentno"));
					etiquetaDoc.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
					etiquetaDoc.setZ_LineaProductoSocio_ID(rs.getInt("z_lineaproductosocio_id"));
					etiquetaDoc.saveEx();

					zPreciosProvCabID = rs.getInt("z_preciosprovcab_id");
				}

				// Verifico si este producto esta en oferta en este momento y marco flag de esa situación.
				// En caso de estar en oferta actualmente, este producto no debe imprimir etiqueta con el nuevo precio de venta.
				// Tampoco será enviado al POS.
				boolean tieneOfertaSO = false;
				MZProductoOferta productoOferta = MZProductoOferta.getByProductDateOrg(getCtx(), rs.getInt("m_product_id"), fechaHoy, fechaHoy, this.getAD_Org_ID(), get_TrxName());
				if ((productoOferta != null) && (productoOferta.get_ID() > 0)){
					tieneOfertaSO = true;
				}

				// Nuevo producto
				MZConfirmacionEtiquetaProd etiquetaProd = new MZConfirmacionEtiquetaProd(getCtx(), 0, get_TrxName());
				etiquetaProd.setZ_ConfirmacionEtiquetaDoc_ID(etiquetaDoc.get_ID());
				etiquetaProd.setAD_Org_ID(this.getAD_Org_ID());
				etiquetaProd.setM_Product_ID(rs.getInt("m_product_id"));
				etiquetaProd.setPriceSO(rs.getBigDecimal("newpriceso"));
				etiquetaProd.setDateValidSO(rs.getTimestamp("datevalidpo"));
				etiquetaProd.setC_Currency_ID_SO(rs.getInt("c_currency_id_so"));
				etiquetaProd.setWithOfferSO(tieneOfertaSO);
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


	/***
	 * Obtiene documentos de Ofertas Periódicas a considerarse en este proceso.
	 * Xpande. Created by Gabriel Vila on 1/15/18.
	 */
	private void getDocOfertas() {

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{

			Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
            Date dateFechaAux = new Date(fechaHoy.getTime());
            dateFechaAux =  DateUtils.addDays(dateFechaAux, 3);
            Timestamp fechaOferta = new Timestamp(dateFechaAux.getTime());

			int zOfertaVentaID = 0;
			MZConfirmacionEtiquetaDoc etiquetaDoc = null;
			int adTableID = MTable.getTable_ID(I_Z_OfertaVenta.Table_Name);

			sql = " select cab.z_ofertaventa_id, cab.c_doctype_id, cab.documentno, cab.startdate, cab.updated, cab.updatedby, " +
					" lin.c_currency_id_so as c_currency_id, lin.m_product_id, lin.newpriceso, linorg.ad_orgtrx_id " +
					" from z_ofertaventalinorg linorg " +
					" inner join z_ofertaventalin lin on linorg.z_ofertaventalin_id = lin.z_ofertaventalin_id " +
					" inner join z_ofertaventa cab on lin.z_ofertaventa_id = cab.z_ofertaventa_id " +
					" inner join z_ofertaventaorg caborg on (cab.z_ofertaventa_id = caborg.z_ofertaventa_id " +
					" and caborg.ad_orgtrx_id =" + this.getAD_Org_ID() + ") " +
					" where linorg.ad_orgtrx_id =" + this.getAD_Org_ID() +
					" and lin.newpriceso > 0 " +
					" and cab.docstatus='CO' " +
					" and cab.startdate <='" + fechaOferta + "' "  +
					//"' and cab.enddate >='" + fechaOferta + "') " +
					" and ((cab.z_ofertaventa_id not in " +
					" (select confdoc.record_id from z_confirmacionetiquetadoc confdoc " +
					" inner join z_confirmacionetiqueta conf on confdoc.z_confirmacionetiqueta_id = conf.z_confirmacionetiqueta_id " +
					" where confdoc.isselected ='Y' and confdoc.ad_table_id =" + adTableID +
					" and conf.ad_org_id =" + this.getAD_Org_ID() + ")) OR (cab.ismodified='Y' AND caborg.ismodified='Y')) " +
					" order by cab.updated, cab.z_ofertaventa_id ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				// Corte por id de documento de gestión de precios
				if (rs.getInt("z_ofertaventa_id") != zOfertaVentaID){
					// Nuevo documento
					etiquetaDoc = new MZConfirmacionEtiquetaDoc(getCtx(), 0, get_TrxName());
					etiquetaDoc.setZ_ConfirmacionEtiqueta_ID(this.get_ID());
					etiquetaDoc.setAD_Table_ID(adTableID);
					etiquetaDoc.setRecord_ID(rs.getInt("z_ofertaventa_id"));
					etiquetaDoc.setAD_User_ID(rs.getInt("updatedby"));
					etiquetaDoc.setC_DocTypeTarget_ID(rs.getInt("c_doctype_id"));
					etiquetaDoc.setDateDoc(rs.getTimestamp("updated"));
					etiquetaDoc.setDocumentNoRef(rs.getString("documentno"));
					etiquetaDoc.setDateToPos(rs.getTimestamp("startdate"));
					etiquetaDoc.save();

					zOfertaVentaID = rs.getInt("z_ofertaventa_id");

					// Si es una corrección de oferta, tengo que considerar los productos eliminados de la misma
					MZOfertaVenta ofertaVenta = new MZOfertaVenta(getCtx(), zOfertaVentaID, get_TrxName());
					if (ofertaVenta.isModified()){
						List<MZOfertaVentaLinDel> linDelList = ofertaVenta.getLinesDeleted();
						for (MZOfertaVentaLinDel linDel: linDelList){
							// Agrego producto eliminado en correccion de oferta
							MZConfirmacionEtiquetaProd etiquetaProd = new MZConfirmacionEtiquetaProd(getCtx(), 0, get_TrxName());
							etiquetaProd.setZ_ConfirmacionEtiquetaDoc_ID(etiquetaDoc.get_ID());
							etiquetaProd.setM_Product_ID(linDel.getM_Product_ID());
							etiquetaProd.setPriceSO(linDel.getNewPriceSO());
							etiquetaProd.setDateValidSO(linDel.getValidFrom());
							etiquetaProd.setC_Currency_ID_SO(ofertaVenta.getC_Currency_ID_SO());
							etiquetaProd.setWithOfferSO(false);  // Este producto en si mismo esta entrando a comuniacion al local para imprimirse por una cercana oferta.
							etiquetaProd.setQtyCount(1);
							etiquetaProd.saveEx();
						}
					}
				}

				// Nuevo producto
				MZConfirmacionEtiquetaProd etiquetaProd = new MZConfirmacionEtiquetaProd(getCtx(), 0, get_TrxName());
				etiquetaProd.setZ_ConfirmacionEtiquetaDoc_ID(etiquetaDoc.get_ID());
				etiquetaProd.setM_Product_ID(rs.getInt("m_product_id"));
				etiquetaProd.setPriceSO(rs.getBigDecimal("newpriceso"));
				etiquetaProd.setDateValidSO(rs.getTimestamp("startdate"));
				etiquetaProd.setC_Currency_ID_SO(rs.getInt("c_currency_id"));
				etiquetaProd.setWithOfferSO(false);  // Este producto en si mismo esta entrando a comuniacion al local para imprimirse por una cercana oferta.
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


	/***
	 * Obtiene y retorna lineas de documentos marcados para procesar y que estan confirmados para este modelo.
	 * Xpande. Created by Gabriel Vila on 7/12/17.
	 * @return
	 */
	public List<MZConfirmacionEtiquetaDoc> getConfirmedEtiquetaDocs(){

    	String whereClause = X_Z_ConfirmacionEtiquetaDoc.COLUMNNAME_Z_ConfirmacionEtiqueta_ID + " =" + this.get_ID() +
				" AND " + X_Z_ConfirmacionEtiquetaDoc.COLUMNNAME_IsSelected + " ='Y'" +
				" AND " + X_Z_ConfirmacionEtiquetaDoc.COLUMNNAME_IsConfirmed + " ='Y'";

    	List<MZConfirmacionEtiquetaDoc> lines = new Query(getCtx(), I_Z_ConfirmacionEtiquetaDoc.Table_Name, whereClause, get_TrxName()).list();

    	return lines;
	}


	/***
	 * Obtiene y retorna lineas de documentos marcados para procesar y que no estan confirmados para este modelo.
	 * Xpande. Created by Gabriel Vila on 7/12/17.
	 * @return
	 */
	public List<MZConfirmacionEtiquetaDoc> getNotConfirmedEtiquetaDocs(){

		String whereClause = X_Z_ConfirmacionEtiquetaDoc.COLUMNNAME_Z_ConfirmacionEtiqueta_ID + " =" + this.get_ID() +
				" AND " + X_Z_ConfirmacionEtiquetaDoc.COLUMNNAME_IsSelected + " ='Y'" +
				" AND " + X_Z_ConfirmacionEtiquetaDoc.COLUMNNAME_IsConfirmed + " ='N'";

		List<MZConfirmacionEtiquetaDoc> lines = new Query(getCtx(), I_Z_ConfirmacionEtiquetaDoc.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}


	/***
	 * Obtiene y retorna lineas de documentos marcados para procesar de este modelo.
	 * Xpande. Created by Gabriel Vila on 7/12/17.
	 * @return
	 */
	public List<MZConfirmacionEtiquetaDoc> getSelectedEtiquetaDocs(){

		String whereClause = X_Z_ConfirmacionEtiquetaDoc.COLUMNNAME_Z_ConfirmacionEtiqueta_ID + " =" + this.get_ID() +
				" AND " + X_Z_ConfirmacionEtiquetaDoc.COLUMNNAME_IsSelected + " ='Y'";

		List<MZConfirmacionEtiquetaDoc> lines = new Query(getCtx(), I_Z_ConfirmacionEtiquetaDoc.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}


	@Override
	protected boolean beforeSave(boolean newRecord) {

		try{

			// No acepto organización *
			if (this.getAD_Org_ID() <= 0){
				log.saveError("ATENCIÓN", "Debe Indicar Organización a considerar (no se acepta organización = * )");
				return false;

			}

			if (newRecord){
				// Genera ID para impresión de etiquetas. Este ID es el que se pasa por parametro al reporte.
				int impresionID = SequenceUtils.getNextID_NoTable(get_TrxName(), "impresion_id");
				this.setImpresion_ID(impresionID);
			}

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return true;
	}

	/***
	 * Elimina información de documentos asociados a este modelo.
	 * Xpande. Created by Gabriel Vila on 9/13/17.
	 */
	private void deleteDocuments(){

		try{
			String action = " delete from z_confirmacionetiquetadoc where z_confirmacionetiqueta_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}

}