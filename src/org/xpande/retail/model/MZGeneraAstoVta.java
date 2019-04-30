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

			}
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
		    sql = " select st_codigomediopago, st_nombremediopago, st_tipotarjetacredito, st_nombretarjeta, st_codigomoneda, " +
					" sum(st_totalentregado) as st_totalentregado, sum(st_totalmppagomoneda) as st_totalmppagomoneda, " +
					" sum(st_totalentregadomonedaref) as st_totalentregadomonedaref, sum(st_totalmppagomonedaref) as st_totalmppagomonedaref, " +
					" sum(st_cambio) as st_cambio, sum(totalamt) as totalamt " +
					" from zv_sisteco_vtasmpagodet " +
					" where ad_org_id =" + this.getAD_Org_ID() +
					" and datetrx ='" + this.getDateTo() + "' " +
					" group by st_codigomediopago, st_nombremediopago, st_tipotarjetacredito, st_nombretarjeta, st_codigomoneda " +
					" order by st_codigomediopago, st_nombremediopago, st_tipotarjetacredito, st_nombretarjeta, st_codigomoneda ";

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

				// Si ya tengo registro para este medio de pago, lo obtengo, sino lo instancio.
				astoVtaSumMP = this.getAstoVtaSumMPByCod(codigoMP);
				if ((astoVtaSumMP == null) || (astoVtaSumMP.get_ID() <= 0)){
					astoVtaSumMP = new MZGeneraAstoVtaSumMP(getCtx(), 0, get_TrxName());
					astoVtaSumMP.setZ_GeneraAstoVta_ID(this.get_ID());
					astoVtaSumMP.setAD_Org_ID(this.getAD_Org_ID());
					astoVtaSumMP.setCodMedioPagoPOS(codigoMP);
					astoVtaSumMP.setNomMedioPagoPOS(nombreMP);
					astoVtaSumMP.setC_Currency_ID(142);
					astoVtaSumMP.setC_Currency_2_ID(100);
				}

				int cCurrencyID = 142, cCurrencyID2 = 100;
				BigDecimal currencyRate = Env.ONE;

				String codMoneda = rs.getString("st_codigomoneda");
				if ((codMoneda == null) || (codMoneda.trim().equalsIgnoreCase(""))){
					return "Hay información de ventas por medios de pago que no tiene MONEDA.";
				}

				if (codMoneda.trim().equalsIgnoreCase("PESOS")){
					if (rs.getBigDecimal("totalamt") != null){
						astoVtaSumMP.setAmtTotal1(rs.getBigDecimal("totalamt"));
					}
					else{
						astoVtaSumMP.setAmtTotal1(rs.getBigDecimal("st_totalmppagomonedaref"));
					}

					if (astoVtaSumMP.getCurrencyRate() == null){
						astoVtaSumMP.setCurrencyRate(Env.ONE);
					}
					if (astoVtaSumMP.getAmtTotal2() == null){
						astoVtaSumMP.setAmtTotal2(Env.ZERO);
					}
				}
				else if (codMoneda.trim().equalsIgnoreCase("DOLARES")){
					astoVtaSumMP.setAmtTotal2(rs.getBigDecimal("st_totalmppagomoneda"));
					if (rs.getBigDecimal("st_totalmppagomoneda").compareTo(Env.ZERO) > 0){
						currencyRate = rs.getBigDecimal("st_totalmppagomonedaref").divide(rs.getBigDecimal("st_totalmppagomoneda"), 3, RoundingMode.HALF_UP);
					}
					astoVtaSumMP.setCurrencyRate(currencyRate);
					if (astoVtaSumMP.getAmtTotal1() == null){
						astoVtaSumMP.setAmtTotal1(Env.ZERO);
					}

				}
				else {
					return "Se encontró una Moneda disinta a PESOS o DOLARES y no es posible procesarla : " + codMoneda;
				}

				if (astoVtaSumMP.getAmtTotal() == null){
					astoVtaSumMP.setAmtTotal(Env.ZERO);
				}
				BigDecimal amtTotal = astoVtaSumMP.getAmtTotal();
				if (rs.getBigDecimal("totalamt") != null){
					amtTotal = amtTotal.add(rs.getBigDecimal("totalamt"));
				}
				astoVtaSumMP.setAmtTotal(amtTotal);

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

			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

		return message;
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


}