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

/** Generated Model for Z_RemitoDifInv
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class MZRemitoDifInv extends X_Z_RemitoDifInv implements DocAction, DocOptions {

	/**
	 *
	 */
	private static final long serialVersionUID = 20180215L;

    /** Standard Constructor */
    public MZRemitoDifInv (Properties ctx, int Z_RemitoDifInv_ID, String trxName)
    {
      super (ctx, Z_RemitoDifInv_ID, trxName);
    }

    /** Load Constructor */
    public MZRemitoDifInv (Properties ctx, ResultSet rs, String trxName)
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
			options[newIndex++] = DocumentEngine.ACTION_Close;
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
		setDocStatus(DOCSTATUS_Closed);
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
      StringBuffer sb = new StringBuffer ("MZRemitoDifInv[")
        .append(getSummary()).append("]");
      return sb.toString();
    }


	/***
	 * Obtiene y retorna lineas abiertas de este Remito por Diferencia.
	 * Xpande. Created by Gabriel Vila on 2/28/18.
	 * @return
	 */
	public List<MZRemitoDifInvLin> getNotClosedLines(){

		String whereClause = X_Z_RemitoDifInvLin.COLUMNNAME_Z_RemitoDifInv_ID + " =" + this.get_ID() +
				" AND " + X_Z_RemitoDifInvLin.COLUMNNAME_IsClosed + " ='N'";

		List<MZRemitoDifInvLin> lines = new Query(getCtx(), I_Z_RemitoDifInvLin.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}


	/***
	 * Segun linea de factura, se verifica si la misma tiene diferencias de precio y cantidad.
	 * Si es asi genera nueva linea por este concepto.
	 * Xpande. Created by Gabriel Vila on 9/3/18.
	 * @param invoice
	 * @param invoiceLine
	 * @param precision
	 * @param soloDifCantidad
	 * @return
	 */
	public BigDecimal setRemitoDiferencia(MInvoice invoice, MInvoiceLine invoiceLine, int precision, boolean soloDifCantidad){

		BigDecimal amtRemito = Env.ZERO;

		try{

			if (invoiceLine.getM_Product_ID() <= 0){
				return Env.ZERO;
			}

			// Si corresponde genero documento de Remito por Diferencia
			boolean hayDiferenciaCantidad = false;
			boolean hayDiferenciaNeto = false;

			// Verifico diferencias entre cantidad recibida y cantidad facturada, si es que tengo recepcion asociada
			BigDecimal cantRecepcionada = invoiceLine.getQtyInvoiced();
			BigDecimal cantFacturada = invoiceLine.getQtyInvoiced();
			if (invoiceLine.getM_InOutLine_ID() > 0){
				MInOutLine inOutLine = (MInOutLine) invoiceLine.getM_InOutLine();
				cantRecepcionada = inOutLine.getMovementQty();
			}
			BigDecimal cantDiferencia = cantFacturada.subtract(cantRecepcionada);
			if (cantDiferencia.compareTo(Env.ZERO) > 0){
				hayDiferenciaCantidad = true;
			}

			BigDecimal pricePO = invoiceLine.getPriceEntered();
			BigDecimal priceDiferencia = Env.ZERO;
			BigDecimal netoFacturado = invoiceLine.getLineNetAmt();
			BigDecimal netoPO = netoFacturado;
			BigDecimal netoDiferencia = Env.ZERO;
			if (invoiceLine.get_Value("PricePO") != null){
				pricePO = (BigDecimal) invoiceLine.get_Value("PricePO");
				if (pricePO.compareTo(Env.ZERO) > 0){
					netoPO = pricePO.multiply(cantFacturada).setScale(precision, RoundingMode.HALF_UP);
					netoDiferencia = netoFacturado.subtract(netoPO);
					priceDiferencia = invoiceLine.getPriceEntered().subtract(pricePO);
					BigDecimal toleranciaNeto = new BigDecimal(1.50);
					if (netoDiferencia.compareTo(toleranciaNeto) > 0){
						hayDiferenciaNeto = true;
					}
				}
			}

			// Tengo diferencia de monto o cantidad
			if (hayDiferenciaCantidad || hayDiferenciaNeto){
				if (this.get_ID() <= 0){
					this.saveEx();
				}
				// Genero un linea de diferencia por cantidad y una linea de diferencia por monto (para el mismo producto, dos lineas).
				if (hayDiferenciaCantidad){
					MZRemitoDifInvLin remitoLin = new MZRemitoDifInvLin(invoice.getCtx(), 0, invoice.get_TrxName());
					remitoLin.setZ_RemitoDifInv_ID(this.get_ID());
					remitoLin.setC_InvoiceLine_ID(invoiceLine.get_ID());
					remitoLin.setC_Invoice_ID(invoice.get_ID());
					remitoLin.setM_InOutLine_ID(invoiceLine.getM_InOutLine_ID());
					remitoLin.setM_Product_ID(invoiceLine.getM_Product_ID());
					remitoLin.setC_UOM_ID(invoiceLine.getC_UOM_ID());
					remitoLin.setQtyDelivered(cantRecepcionada);
					remitoLin.setQtyInvoiced(cantFacturada);
					remitoLin.setDifferenceQty(cantDiferencia);
					remitoLin.setQtyOpen(cantDiferencia);
					remitoLin.setPricePO(pricePO);
					remitoLin.setPriceInvoiced(invoiceLine.getPriceEntered());
					remitoLin.setDifferencePrice(priceDiferencia);
					remitoLin.setAmtSubtotal(netoFacturado);
					remitoLin.setAmtSubtotalPO(netoPO);

					// Neto diferencia se calcula multiplicando la cantidad diferencia por el precio facturado
					remitoLin.setDifferenceAmt(remitoLin.getDifferenceQty().multiply(remitoLin.getPriceInvoiced()).setScale(precision, RoundingMode.HALF_UP));

					remitoLin.setAmtOpen(remitoLin.getDifferenceAmt());
					remitoLin.setIsDifferenceQty(true);
					remitoLin.setIsDifferenceAmt(false);
					remitoLin.saveEx();

					amtRemito = remitoLin.getDifferenceAmt();

				}

				if (hayDiferenciaNeto){
					if (!soloDifCantidad){
						MZRemitoDifInvLin remitoLin = new MZRemitoDifInvLin(invoice.getCtx(), 0, invoice.get_TrxName());
						remitoLin.setZ_RemitoDifInv_ID(this.get_ID());
						remitoLin.setC_InvoiceLine_ID(invoiceLine.get_ID());
						remitoLin.setC_Invoice_ID(invoice.get_ID());
						remitoLin.setM_InOutLine_ID(invoiceLine.getM_InOutLine_ID());
						remitoLin.setM_Product_ID(invoiceLine.getM_Product_ID());
						remitoLin.setC_UOM_ID(invoiceLine.getC_UOM_ID());
						remitoLin.setQtyDelivered(cantRecepcionada);
						remitoLin.setQtyInvoiced(cantFacturada);
						remitoLin.setDifferenceQty(cantDiferencia);
						remitoLin.setQtyOpen(cantDiferencia);
						remitoLin.setPricePO(pricePO);
						remitoLin.setPriceInvoiced(invoiceLine.getPriceEntered());
						remitoLin.setDifferencePrice(priceDiferencia);
						remitoLin.setAmtSubtotal(netoFacturado);
						remitoLin.setAmtSubtotalPO(netoPO);

						// Neto diferencia de la linea por diferencia de monto depende si ademas este producto tiene o no diferencia de cantidad
						if (hayDiferenciaCantidad){
							// Neto diferencia se calcula multiplicando la cantidad recepcionada por el precio diferencia
							remitoLin.setDifferenceAmt(remitoLin.getQtyDelivered().multiply(remitoLin.getDifferencePrice()).setScale(precision, RoundingMode.HALF_UP));
						}
						else{
							// El producto solo tiene diferencia por monto y ya esta calculada
							remitoLin.setDifferenceAmt(netoDiferencia);
						}

						remitoLin.setAmtOpen(remitoLin.getDifferenceAmt());
						remitoLin.setIsDifferenceQty(false);
						remitoLin.setIsDifferenceAmt(true);
						remitoLin.saveEx();

						amtRemito = remitoLin.getDifferenceAmt();
					}
				}
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

		return amtRemito;
	}

}