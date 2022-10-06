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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.process.DocAction;
import org.compiere.process.DocOptions;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xpande.core.utils.DateUtils;
import org.xpande.financial.model.*;
import org.xpande.stech.model.MZStechCreditos;
import org.xpande.stech.model.MZStechMedioPago;

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

		MZPosVendor posVendor = (MZPosVendor) this.getZ_PosVendor();
		if (posVendor.getValue().equalsIgnoreCase("SISTECO")){

			// Obtengo reclasficacion de medios de pago segun proveedor de POS, si es que tengo alguna.
			List<MZGeneraAstoVtaDetMPST> vtaDetMPSTList = this.getMediosPagoModifSisteco();

			// Si tengo medios de pago reclasificados
			if (vtaDetMPSTList.size() > 0){

				// Instancio y completo nuevo documento para asiento de reclasificación de medios de pago de sisteco
				MDocType[] docTypeRecMPList = MDocType.getOfDocBaseType(getCtx(), "AVR");
				if (docTypeRecMPList.length <= 0){
					this.m_processMsg = "Falta definr Tipo de Documento para Asiento de Reclasificación de Medios de Pago.";
					return DocAction.STATUS_Invalid;
				}

				MZAstoVtaRecMP astoVtaRecMP = new MZAstoVtaRecMP(getCtx(), 0, get_TrxName());
				astoVtaRecMP.setAD_Org_ID(this.getAD_Org_ID());
				astoVtaRecMP.setZ_GeneraAstoVta_ID(this.get_ID());
				astoVtaRecMP.setC_DocType_ID(docTypeRecMPList[0].get_ID());
				astoVtaRecMP.setDateDoc(this.getDateDoc());
				astoVtaRecMP.setDateAcct(this.getDateAcct());
				astoVtaRecMP.setZ_PosVendor_ID(this.getZ_PosVendor_ID());
				astoVtaRecMP.setDescription("Generado Automáticamente desde Generador de Asientos de Venta Nro. : " + this.getDocumentNo());

				astoVtaRecMP.saveEx();

				for (MZGeneraAstoVtaDetMPST vtaDetMPST: vtaDetMPSTList){
					MZAstoVtaRecMPLinST astoVtaRecMPLinST = new MZAstoVtaRecMPLinST(getCtx(), 0, get_TrxName());
					astoVtaRecMPLinST.setAD_Org_ID(this.getAD_Org_ID());
					astoVtaRecMPLinST.setZ_AstoVtaRecMP_ID(astoVtaRecMP.get_ID());
					astoVtaRecMPLinST.setZ_GeneraAstoVtaDetMPST_ID(vtaDetMPST.get_ID());
					astoVtaRecMPLinST.setDateTrx(vtaDetMPST.getDateTrx());
					astoVtaRecMPLinST.setName(vtaDetMPST.getName());
					astoVtaRecMPLinST.setST_Cambio(vtaDetMPST.getST_Cambio());
					astoVtaRecMPLinST.setST_CodigoCaja(vtaDetMPST.getST_CodigoCaja());
					astoVtaRecMPLinST.setST_CodigoCajera(vtaDetMPST.getST_CodigoCajera());
					astoVtaRecMPLinST.setST_CodigoCC(vtaDetMPST.getST_CodigoCC());
					astoVtaRecMPLinST.setST_CodigoMedioPago(vtaDetMPST.getST_CodigoMedioPago());
					astoVtaRecMPLinST.setST_CodigoMoneda(vtaDetMPST.getST_CodigoMoneda());
					astoVtaRecMPLinST.setST_DescripcionCFE(vtaDetMPST.getST_DescripcionCFE());
					astoVtaRecMPLinST.setST_DescuentoAfam(vtaDetMPST.getST_DescuentoAfam());
					astoVtaRecMPLinST.setST_MontoDescuentoLeyIVA(vtaDetMPST.getST_MontoDescuentoLeyIVA());
					astoVtaRecMPLinST.setST_NombreCC(vtaDetMPST.getST_NombreCC());
					astoVtaRecMPLinST.setST_NombreMedioPago(vtaDetMPST.getST_NombreMedioPago());
					astoVtaRecMPLinST.setST_NombreTarjeta(vtaDetMPST.getST_NombreTarjeta());
					astoVtaRecMPLinST.setST_NumeroTarjeta(vtaDetMPST.getST_NumeroTarjeta());
					astoVtaRecMPLinST.setST_NumeroTicket(vtaDetMPST.getST_NumeroTicket());
					astoVtaRecMPLinST.setST_TextoLey(vtaDetMPST.getST_TextoLey());
					astoVtaRecMPLinST.setST_TimestampTicket(vtaDetMPST.getST_TimestampTicket());
					astoVtaRecMPLinST.setST_TipoLinea(vtaDetMPST.getST_TipoLinea());
					astoVtaRecMPLinST.setST_TipoTarjetaCredito(vtaDetMPST.getST_TipoTarjetaCredito());
					astoVtaRecMPLinST.setST_TotalEntregado(vtaDetMPST.getST_TotalEntregado());
					astoVtaRecMPLinST.setST_TotalEntregadoMonedaRef(vtaDetMPST.getST_TotalEntregadoMonedaRef());
					astoVtaRecMPLinST.setST_TotalMPPagoMoneda(vtaDetMPST.getST_TotalMPPagoMoneda());
					astoVtaRecMPLinST.setST_TotalMPPagoMonedaRef(vtaDetMPST.getST_TotalMPPagoMonedaRef());
					astoVtaRecMPLinST.setTotalAmt(vtaDetMPST.getTotalAmt());
					astoVtaRecMPLinST.setZ_SistecoMedioPago_ID(vtaDetMPST.getZ_SistecoMedioPago_ID());
					astoVtaRecMPLinST.setZ_SistecoTipoTarjeta_ID(vtaDetMPST.getZ_SistecoTipoTarjeta_ID());
					astoVtaRecMPLinST.saveEx();
				}

				if (!astoVtaRecMP.processIt(DocAction.ACTION_Complete)){
					if (astoVtaRecMP.getProcessMsg() != null){
						this.m_processMsg = astoVtaRecMP.getProcessMsg();
					}
					else {
						this.m_processMsg = "No se pudo generar y completar el documento de Asiento de Reclasificación de Medios de Pago.";
					}
					return DocAction.STATUS_Invalid;
				}
				astoVtaRecMP.saveEx();

				this.setZ_AstoVtaRecMP_ID(astoVtaRecMP.get_ID());
			}
		}

		else if (posVendor.getValue().equalsIgnoreCase("SCANNTECH")){

			// Obtengo reclasficacion de medios de pago segun proveedor de POS, si es que tengo alguna.
			List<MZGeneraAstoVtaDetMPSC> vtaDetMPSCList = this.getMediosPagoModifScanntech();

			// Si tengo medios de pago reclasificados
			if (vtaDetMPSCList.size() > 0){

				// Instancio y completo nuevo documento para asiento de reclasificación de medios de pago de sisteco
				MDocType[] docTypeRecMPList = MDocType.getOfDocBaseType(getCtx(), "AVR");
				if (docTypeRecMPList.length <= 0){
					this.m_processMsg = "Falta definr Tipo de Documento para Asiento de Reclasificación de Medios de Pago.";
					return DocAction.STATUS_Invalid;
				}

				MZAstoVtaRecMP astoVtaRecMP = new MZAstoVtaRecMP(getCtx(), 0, get_TrxName());
				astoVtaRecMP.setAD_Org_ID(this.getAD_Org_ID());
				astoVtaRecMP.setZ_GeneraAstoVta_ID(this.get_ID());
				astoVtaRecMP.setC_DocType_ID(docTypeRecMPList[0].get_ID());
				astoVtaRecMP.setDateDoc(this.getDateDoc());
				astoVtaRecMP.setDateAcct(this.getDateAcct());
				astoVtaRecMP.setZ_PosVendor_ID(this.getZ_PosVendor_ID());
				astoVtaRecMP.setDescription("Generado Automáticamente desde Generador de Asientos de Venta Nro. : " + this.getDocumentNo());

				astoVtaRecMP.saveEx();

				for (MZGeneraAstoVtaDetMPSC vtaDetMPSC: vtaDetMPSCList){
					MZAstoVtaRecMPLinSC astoVtaRecMPLinSC = new MZAstoVtaRecMPLinSC(getCtx(), 0, get_TrxName());
					astoVtaRecMPLinSC.setAD_Org_ID(this.getAD_Org_ID());
					astoVtaRecMPLinSC.setZ_AstoVtaRecMP_ID(astoVtaRecMP.get_ID());
					astoVtaRecMPLinSC.setZ_GeneraAstoVtaDetMPSC_ID(vtaDetMPSC.get_ID());
					astoVtaRecMPLinSC.setDateTrx(vtaDetMPSC.getDateTrx());
					astoVtaRecMPLinSC.setC_Currency_ID(vtaDetMPSC.getC_Currency_ID());
					astoVtaRecMPLinSC.setCodMedioPagoPOS(vtaDetMPSC.getCodMedioPagoPOS());
					astoVtaRecMPLinSC.setNomMedioPagoPOS(vtaDetMPSC.getNomMedioPagoPOS());
					astoVtaRecMPLinSC.setSC_CodigoCaja(vtaDetMPSC.getSC_CodigoCaja());
					astoVtaRecMPLinSC.setSC_CodigoMoneda(vtaDetMPSC.getSC_CodigoMoneda());
					astoVtaRecMPLinSC.setSC_CotizacionVenta(vtaDetMPSC.getSC_CotizacionVenta());
					astoVtaRecMPLinSC.setSC_CuponCancelado(vtaDetMPSC.isSC_CuponCancelado());
					astoVtaRecMPLinSC.setSC_Importe(vtaDetMPSC.getSC_Importe());
					astoVtaRecMPLinSC.setSC_NumeroMov(vtaDetMPSC.getSC_NumeroMov());
					astoVtaRecMPLinSC.setSC_NumeroOperacion(vtaDetMPSC.getSC_NumeroOperacion());
					astoVtaRecMPLinSC.setSC_NumeroTarjeta(vtaDetMPSC.getSC_NumeroTarjeta());
					astoVtaRecMPLinSC.setSC_DescripcionCFE(vtaDetMPSC.getSC_DescripcionCFE());
					astoVtaRecMPLinSC.setSC_DescripcionCFE(vtaDetMPSC.getSC_SerieCfe());
					astoVtaRecMPLinSC.setSC_SerieCfe(vtaDetMPSC.getSC_SerieCfe());
					astoVtaRecMPLinSC.setSC_TipoCfe(vtaDetMPSC.getSC_TipoCfe());
					astoVtaRecMPLinSC.setZ_StechMedioPago_ID(vtaDetMPSC.getZ_StechMedioPago_ID());
					astoVtaRecMPLinSC.setZ_StechCreditos_ID(vtaDetMPSC.getZ_StechCreditos_ID());

					astoVtaRecMPLinSC.saveEx();
				}

				if (!astoVtaRecMP.processIt(DocAction.ACTION_Complete)){
					if (astoVtaRecMP.getProcessMsg() != null){
						this.m_processMsg = astoVtaRecMP.getProcessMsg();
					}
					else {
						this.m_processMsg = "No se pudo generar y completar el documento de Asiento de Reclasificación de Medios de Pago.";
					}
					return DocAction.STATUS_Invalid;
				}
				astoVtaRecMP.saveEx();

				this.setZ_AstoVtaRecMP_ID(astoVtaRecMP.get_ID());
			}
		}
		else if (posVendor.getValue().equalsIgnoreCase("GEOCOM")){
			// Obtengo reclasficacion de medios de pago segun proveedor de POS, si es que tengo alguna.
			List<MZGenAstoVtaDetMPGeo> vtaDetMPGeoList = this.getMediosPagoModifGeocom();

			// Si tengo medios de pago reclasificados
			if (vtaDetMPGeoList.size() > 0){

				// Instancio y completo nuevo documento para asiento de reclasificación de medios de pago de sisteco
				MDocType[] docTypeRecMPList = MDocType.getOfDocBaseType(getCtx(), "AVR");
				if (docTypeRecMPList.length <= 0){
					this.m_processMsg = "Falta definr Tipo de Documento para Asiento de Reclasificación de Medios de Pago.";
					return DocAction.STATUS_Invalid;
				}

				MZAstoVtaRecMP astoVtaRecMP = new MZAstoVtaRecMP(getCtx(), 0, get_TrxName());
				astoVtaRecMP.setAD_Org_ID(this.getAD_Org_ID());
				astoVtaRecMP.setZ_GeneraAstoVta_ID(this.get_ID());
				astoVtaRecMP.setC_DocType_ID(docTypeRecMPList[0].get_ID());
				astoVtaRecMP.setDateDoc(this.getDateDoc());
				astoVtaRecMP.setDateAcct(this.getDateAcct());
				astoVtaRecMP.setZ_PosVendor_ID(this.getZ_PosVendor_ID());
				astoVtaRecMP.setDescription("Generado Automáticamente desde Generador de Asientos de Venta Nro. : " + this.getDocumentNo());

				astoVtaRecMP.saveEx();

				for (MZGenAstoVtaDetMPGeo vtaDetMPGeo: vtaDetMPGeoList){
					MZAstoVtaRecMPLinGeo astoVtaRecMPLin = new MZAstoVtaRecMPLinGeo(getCtx(), 0, get_TrxName());
					astoVtaRecMPLin.setAD_Org_ID(this.getAD_Org_ID());
					astoVtaRecMPLin.setZ_AstoVtaRecMP_ID(astoVtaRecMP.get_ID());
					astoVtaRecMPLin.setZ_GenAstoVtaDetMPGeo_ID(vtaDetMPGeo.get_ID());
					astoVtaRecMPLin.setamtcashback(vtaDetMPGeo.getamtcashback());
					astoVtaRecMPLin.setamtcashbackacct(vtaDetMPGeo.getamtcashbackacct());
					astoVtaRecMPLin.setamtdiscount(vtaDetMPGeo.getamtdiscount());
					astoVtaRecMPLin.setaplicadtoiva(vtaDetMPGeo.isaplicadtoiva());
					astoVtaRecMPLin.setbrandid(vtaDetMPGeo.getbrandid());
					astoVtaRecMPLin.setbrandname(vtaDetMPGeo.getbrandname());
					astoVtaRecMPLin.setcantcuotas(vtaDetMPGeo.getcantcuotas());
					astoVtaRecMPLin.setC_BPartner_ID(vtaDetMPGeo.getC_BPartner_ID());
					astoVtaRecMPLin.setcheckbanknumber(vtaDetMPGeo.getcheckbanknumber());
					astoVtaRecMPLin.setchecknumber(vtaDetMPGeo.getchecknumber());
					astoVtaRecMPLin.setcheckvaliddate(vtaDetMPGeo.getcheckvaliddate());
					astoVtaRecMPLin.setcodautorizacion(vtaDetMPGeo.getcodautorizacion());
					astoVtaRecMPLin.setcodcaja(vtaDetMPGeo.getcodcaja());
					astoVtaRecMPLin.setcodcajero(vtaDetMPGeo.getcodcajero());
					astoVtaRecMPLin.setcodcomercio(vtaDetMPGeo.getcodcomercio());
					astoVtaRecMPLin.setcodleyimpuesto(vtaDetMPGeo.getcodleyimpuesto());
					astoVtaRecMPLin.setcodmediopago(vtaDetMPGeo.getcodmediopago());
					astoVtaRecMPLin.setCodMedioPagoPOS(vtaDetMPGeo.getCodMedioPagoPOS());
					astoVtaRecMPLin.setcodprodtarjeta(vtaDetMPGeo.getcodprodtarjeta());
					astoVtaRecMPLin.setcodterminal(vtaDetMPGeo.getcodterminal());
					astoVtaRecMPLin.setcodtipotarjeta(vtaDetMPGeo.getcodtipotarjeta());
					astoVtaRecMPLin.setconversionrate(vtaDetMPGeo.getconversionrate());
					astoVtaRecMPLin.setC_TaxGroup_ID(vtaDetMPGeo.getC_TaxGroup_ID());
					astoVtaRecMPLin.setdatevoucherorig(vtaDetMPGeo.getdatevoucherorig());
					astoVtaRecMPLin.setdocdginame(vtaDetMPGeo.getdocdginame());
					astoVtaRecMPLin.setescambio(vtaDetMPGeo.isescambio());
					astoVtaRecMPLin.setfechaticket(vtaDetMPGeo.getfechaticket());
					astoVtaRecMPLin.setISO_Code(vtaDetMPGeo.getISO_Code());
					astoVtaRecMPLin.setIsOnline(vtaDetMPGeo.isOnline());
					astoVtaRecMPLin.setmodoingtarjeta(vtaDetMPGeo.getmodoingtarjeta());
					astoVtaRecMPLin.setnommediopago(vtaDetMPGeo.getnommediopago());
					astoVtaRecMPLin.setNomMedioPagoPOS(vtaDetMPGeo.getNomMedioPagoPOS());
					astoVtaRecMPLin.setnomprodtarjeta(vtaDetMPGeo.getnomprodtarjeta());
					astoVtaRecMPLin.setnrotarjeta(vtaDetMPGeo.getnrotarjeta());
					astoVtaRecMPLin.setNroTicket(vtaDetMPGeo.getNroTicket());
					astoVtaRecMPLin.setnrovoucher(vtaDetMPGeo.getnrovoucher());
					astoVtaRecMPLin.setnrovoucherorigl(vtaDetMPGeo.getnrovoucherorigl());
					astoVtaRecMPLin.setNumeroCFE(vtaDetMPGeo.getNumeroCFE());
					astoVtaRecMPLin.setorigenvta(vtaDetMPGeo.getorigenvta());
					astoVtaRecMPLin.setplanid(vtaDetMPGeo.getplanid());
					astoVtaRecMPLin.setposbatchid(vtaDetMPGeo.getposbatchid());
					astoVtaRecMPLin.setSerieCFE(vtaDetMPGeo.getSerieCFE());
					astoVtaRecMPLin.setTaxID(vtaDetMPGeo.getTaxID());
					astoVtaRecMPLin.setTipoCFE(vtaDetMPGeo.getTipoCFE());
					astoVtaRecMPLin.settipotarjeta(vtaDetMPGeo.gettipotarjeta());
					astoVtaRecMPLin.setTotalAmt(vtaDetMPGeo.getTotalAmt());
					astoVtaRecMPLin.settotalentregado(vtaDetMPGeo.gettotalentregado());
					astoVtaRecMPLin.settotalentregadomonref(vtaDetMPGeo.gettotalentregadomonref());
					astoVtaRecMPLin.setZ_MedioPagoPos_ID(vtaDetMPGeo.getZ_MedioPagoPos_ID());
					astoVtaRecMPLin.setZ_MPagoIdentPos_ID(vtaDetMPGeo.getZ_MPagoIdentPos_ID());
					astoVtaRecMPLin.saveEx();
				}

				if (!astoVtaRecMP.processIt(DocAction.ACTION_Complete)){
					if (astoVtaRecMP.getProcessMsg() != null){
						this.m_processMsg = astoVtaRecMP.getProcessMsg();
					}
					else {
						this.m_processMsg = "No se pudo generar y completar el documento de Asiento de Reclasificación de Medios de Pago.";
					}
					return DocAction.STATUS_Invalid;
				}
				astoVtaRecMP.saveEx();
				this.setZ_AstoVtaRecMP_ID(astoVtaRecMP.get_ID());
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
		String action = "";

		log.info("reActivateIt - " + toString());

		// Before reActivate
		m_processMsg = ModelValidationEngine.get().fireDocValidate(this,ModelValidator.TIMING_BEFORE_REACTIVATE);
		if (m_processMsg != null)
			return false;

		// Control de período contable
		MPeriod.testPeriodOpen(getCtx(), this.getDateAcct(), this.getC_DocType_ID(), this.getAD_Org_ID());

		// Elimino asientos contables
		MFactAcct.deleteEx(this.get_Table_ID(), this.get_ID(), get_TrxName());

		// Si tengo asiento de reclasificación de medios de pago asociado a este asiento de venta
		if (this.getZ_AstoVtaRecMP_ID() > 0){
			// Elimino asiento de reclasificación
			MZAstoVtaRecMP astoVtaRecMP = (MZAstoVtaRecMP) this.getZ_AstoVtaRecMP();
			if (!astoVtaRecMP.processIt(DocumentEngine.ACTION_ReActivate)){
				if (astoVtaRecMP.getProcessMsg() != null){
					m_processMsg = astoVtaRecMP.getProcessMsg();
				}
				else {
					m_processMsg = "No se pudo reactivar el asiento de reclasificación asociado a este asiento de venta.";
				}
				return false;
			}
			astoVtaRecMP.deleteEx(true);

			action = " update z_generaastovta set z_astovtarecmp_id = null where z_generaastovta_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
		}

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

		if ((newRecord) || (is_ValueChanged(X_Z_GeneraAstoVta.COLUMNNAME_C_AcctSchema_ID))){
			MAcctSchema acctSchema = (MAcctSchema) this.getC_AcctSchema();
			this.setC_Currency_ID(acctSchema.getC_Currency_ID());
		}

		if (this.getDateAcct() == null){
			this.setDateAcct(this.getDateTo());
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

			action = " delete from " + X_Z_GeneraAstoVtaDetMPST.Table_Name +
					" where " + X_Z_GeneraAstoVtaDetMPST.COLUMNNAME_Z_GeneraAstoVta_ID + " =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from " + X_Z_GeneraAstoVtaDetMPSC.Table_Name +
					" where " + X_Z_GeneraAstoVtaDetMPSC.COLUMNNAME_Z_GeneraAstoVta_ID + " =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from " + X_Z_GenAstoVtaDetMPGeo.Table_Name +
					" where " + X_Z_GenAstoVtaDetMPGeo.COLUMNNAME_Z_GeneraAstoVta_ID + " =" + this.get_ID();
			//DB.executeUpdateEx(action, get_TrxName());

			if (this.getZ_PosVendor_ID() <= 0){
				return "Falta indicar Proveedor de POS para la Organización seleccionada";
			}

			MZPosVendor posVendor = (MZPosVendor) this.getZ_PosVendor();
			if (posVendor.getValue().equalsIgnoreCase("SISTECO")){
				message = this.getVentasMedioPagoSisteco();
				if (message == null){
					message = this.getVentasDetMedioPagoSisteco();
				}
			}
			else if (posVendor.getValue().equalsIgnoreCase("SCANNTECH")){
				message = this.getVentasMedioPagoScanntech();
				if (message == null){
					message = this.getVentasDetMedioPagoScanntech();
				}
			}
			else if (posVendor.getValue().equalsIgnoreCase("GEOCOM")){
				message = this.getVentasMedioPagoGeo();
				if (message == null){
					message = this.getVentasDetMedioPagoGeo();
				}
			}

			// Actualizo monto redondeo
			//this.setRedondeo();

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return message;
	}


	/***
	 * Obtiene y carga información de ventas SUMARIZADAS por medios de pago desde SISTECO.
	 * Xpande. Created by Gabriel Vila on 4/29/19.
	 * @return
	 */
	private String getVentasMedioPagoSisteco(){

		String message = null;

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
		    sql = " select a.Z_SistecoInterfacePazos_ID, a.st_codigomediopago, a.st_nombremediopago, a.st_tipolinea, b.name as nomtipolinea, a.st_tipotarjetacredito, " +
					" a.st_nombretarjeta, sttar.z_mediopago_id as mptar, sttar.z_mediopagoident_id, stmp.z_mediopago_id as mpsis, a.st_codigomoneda, " +
					" sum(a.st_totalentregado) as st_totalentregado, sum(a.st_totalmppagomoneda) as st_totalmppagomoneda, " +
					" sum(a.st_totalentregadomonedaref) as st_totalentregadomonedaref, " +
					" sum(a.st_totalmppagomonedaref) as st_totalmppagomonedaref, " +
					" sum(coalesce(a.st_cambio,0)) as st_cambio, sum(a.totalamt) as totalamt " +
					" from zv_sisteco_vtasmpagodet a " +
					" left outer join z_sistecotipolineapazos b on a.st_tipolinea = b.value " +
					" left outer join z_sistecotipotarjeta sttar on a.st_tipotarjetacredito = sttar.value " +
					" left outer join z_sistecomediopago stmp on a.st_codigomediopago = stmp.value " +
					" where a.ad_org_id =" + this.getAD_Org_ID() +
					" and a.datetrx ='" + this.getDateTo() + "' " +
					" and b.IsAsientoVtaPOS ='Y' " +
					" group by a.Z_SistecoInterfacePazos_ID, a.st_codigomediopago, a.st_nombremediopago, a.st_tipolinea, b.name, a.st_tipotarjetacredito, a.st_nombretarjeta, " +
					" sttar.z_mediopago_id, sttar.z_mediopagoident_id, stmp.z_mediopago_id, a.st_codigomoneda " +
					" order by a.Z_SistecoInterfacePazos_ID, a.st_codigomediopago, a.st_nombremediopago, a.st_tipolinea, b.name, a.st_tipotarjetacredito, a.st_nombretarjeta, a.st_codigomoneda ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			MZGeneraAstoVtaSumMP astoVtaSumMP = null;
			boolean firstRow = true;

			while(rs.next()){

				if (firstRow){
					this.setZ_SistecoInterfacePazos_ID(rs.getInt("Z_SistecoInterfacePazos_ID"));
					firstRow = false;
				}

				String codigoMP = rs.getString("st_tipotarjetacredito");
				String nombreMP = rs.getString("st_nombretarjeta");

				if ((codigoMP == null) || (codigoMP.trim().equalsIgnoreCase(""))){
					codigoMP = rs.getString("st_codigomediopago");
				}
				if ((nombreMP == null) || (nombreMP.trim().equalsIgnoreCase(""))){
					nombreMP = rs.getString("st_nombremediopago");
				}

				astoVtaSumMP = new MZGeneraAstoVtaSumMP(getCtx(), 0, get_TrxName());
				astoVtaSumMP.set_ValueOfColumn("AD_Client_ID", this.getAD_Client_ID());
				astoVtaSumMP.setZ_GeneraAstoVta_ID(this.get_ID());
				astoVtaSumMP.setAD_Org_ID(this.getAD_Org_ID());
				astoVtaSumMP.setCodMedioPagoPOS(codigoMP);
				astoVtaSumMP.setNomMedioPagoPOS(nombreMP);
				astoVtaSumMP.setC_Currency_ID(142);
				astoVtaSumMP.setC_Currency_2_ID(100);
				astoVtaSumMP.setCodTipoLineaPOS(rs.getString("st_tipolinea"));
				astoVtaSumMP.setNomTipoLineaPOS(rs.getString("nomtipolinea"));

				String codMoneda = rs.getString("st_codigomoneda");
				if ((codMoneda == null) || (codMoneda.trim().equalsIgnoreCase(""))){
					return "Hay información de ventas por medios de pago que no tiene MONEDA.";
				}

				BigDecimal amt1 = Env.ZERO, amt2 = Env.ZERO;
				BigDecimal currencyRate = Env.ONE;

				if (codMoneda.trim().equalsIgnoreCase("PESOS")){

					amt1 = rs.getBigDecimal("st_totalentregadomonedaref");
					if (amt1 == null) amt1 = Env.ZERO;

					// Resto cambio
					if (rs.getBigDecimal("st_cambio") != null){
						amt1 = amt1.subtract(rs.getBigDecimal("st_cambio"));
					}

				}
				else if (codMoneda.trim().equalsIgnoreCase("DOLARES")){

					amt2 = rs.getBigDecimal("st_totalentregado");
					amt1 = rs.getBigDecimal("st_totalentregadomonedaref");

					if (amt2 == null) amt2 = Env.ZERO;
					if (amt1 == null) amt1 = Env.ZERO;

					if (rs.getBigDecimal("st_totalmppagomoneda").compareTo(Env.ZERO) > 0){
						currencyRate = rs.getBigDecimal("st_totalmppagomonedaref").divide(rs.getBigDecimal("st_totalmppagomoneda"), 3, RoundingMode.HALF_UP);
					}
				}
				else {
					return "Se encontró una Moneda disinta a PESOS o DOLARES y no es posible procesarla : " + codMoneda;
				}

				astoVtaSumMP.setAmtTotal1(amt1);
				astoVtaSumMP.setAmtTotal2(amt2);
				astoVtaSumMP.setCurrencyRate(currencyRate);
				astoVtaSumMP.setAmtTotal(amt1);

				if (rs.getBigDecimal("st_cambio") != null){
					astoVtaSumMP.setChangeAmt(rs.getBigDecimal("st_cambio"));
				}

				if (rs.getInt("mptar") > 0){
					astoVtaSumMP.setZ_MedioPago_ID(rs.getInt("mptar"));
				}
				else{
					if (rs.getInt("mpsis") > 0){
						astoVtaSumMP.setZ_MedioPago_ID(rs.getInt("mpsis"));
					}
				}
				if (rs.getInt("z_mediopagoident_id") > 0){
					astoVtaSumMP.setZ_MedioPagoIdent_ID(rs.getInt("z_mediopagoident_id"));
				}

				astoVtaSumMP.saveEx();
			}

			// Refresco montos en pesos, cuando para el mismo medio de pago pero en dolares, hubo cambio en pesos.
			this.updateMontoCambios();

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
	 * Obtiene y carga información de ventas SUMARIZADAS por medios de pago desde GEOCOM.
	 * Xpande. Created by Gabriel Vila on 4/29/19.
	 * @return
	 */
	private String getVentasMedioPagoGeo(){

		String message = null;
		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
			MAcctSchema schema = MClient.get(getCtx(), this.getAD_Client_ID()).getAcctSchema();
			Date dateFechaAux = new Date(this.getDateTo().getTime());
			dateFechaAux =  DateUtils.addDays(dateFechaAux, 1);
			Timestamp dateTo2 = new Timestamp(dateFechaAux.getTime());

			sql = " select a.codmediopagopos, a.codtipotarjeta, a.brandid, a.brandname, a.codprodtarjeta, a.nomprodtarjeta, " +
					" a.iso_code, coalesce(a.conversionrate,1) as currencyrate, " +
					" sum(a.totalamt) as totalamt, sum(totalentregadomonref) as totalentregadomonref, coalesce(sum(amtdiscount),0) as amtdiscount " +
					//" sum(a.totalamt + coalesce(amtdiscount,0)) as totalamt, sum(totalentregadomonref) as totalentregadomonref " +
					" from zv_detvtamediopago a " +
					" where a.ad_org_id =" + this.getAD_Org_ID() +
					" and a.fechaticket between '" + this.getDateTo() + "' and '" + dateTo2 + "' " +
					" and a.codmediopagopos is not null " +
					" group by 1,2,3,4,5,6,7,8 ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			MZGeneraAstoVtaSumMP astoVtaSumMP = null;

			while(rs.next()){

				String isoCode = "UYU";
				if ((!rs.getString("iso_code").equalsIgnoreCase("858"))
						&& (!rs.getString("iso_code").equalsIgnoreCase("UYU"))) {
					isoCode = "USD";
				}
				MCurrency currency = MCurrency.get(getCtx(), isoCode);

				String codigoMP = null;
				String nombreMP = null;
				int zMedioPagoIdentID = -1;

				String codMedioPagoPOS = rs.getString("codmediopagopos");
				String codTarjetaPOS = rs.getString("codtipotarjeta");

				MZMedioPago medioPago = MZMedioPago.getByPosValue(getCtx(), codMedioPagoPOS, get_TrxName());
				if ((medioPago == null) || (medioPago.get_ID() <= 0)){
					return "No se pudo obtener medio de pago para el código : " + codMedioPagoPOS;
				}
				codigoMP = codMedioPagoPOS;
				nombreMP = medioPago.getName();

				if ((codTarjetaPOS != null) && (!codTarjetaPOS.trim().equalsIgnoreCase(""))){
					MZMedioPagoIdent pagoIdent = medioPago.getMedioPagoIdentPOS(codTarjetaPOS);
					if ((pagoIdent != null) && (pagoIdent.get_ID() > 0)){
						codigoMP = codTarjetaPOS;
						nombreMP = pagoIdent.getName();
						zMedioPagoIdentID = pagoIdent.get_ID();
					}
				}

				astoVtaSumMP = new MZGeneraAstoVtaSumMP(getCtx(), 0, get_TrxName());
				astoVtaSumMP.setZ_GeneraAstoVta_ID(this.get_ID());
				astoVtaSumMP.set_ValueOfColumn("AD_Client_ID", this.getAD_Client_ID());
				astoVtaSumMP.setAD_Org_ID(this.getAD_Org_ID());
				astoVtaSumMP.setCodMedioPagoPOS(codigoMP);
				astoVtaSumMP.setNomMedioPagoPOS(nombreMP);
				astoVtaSumMP.setbrandid(rs.getString("brandid"));
				astoVtaSumMP.setbrandname(rs.getString("brandname"));
				astoVtaSumMP.setcodprodtarjeta(rs.getString("codprodtarjeta"));
				astoVtaSumMP.setnomprodtarjeta(rs.getString("nomprodtarjeta"));
				astoVtaSumMP.setC_Currency_ID(schema.getC_Currency_ID());
				astoVtaSumMP.setC_Currency_2_ID(currency.get_ID());
				astoVtaSumMP.setZ_MedioPago_ID(medioPago.get_ID());
				if (zMedioPagoIdentID > 0){
					astoVtaSumMP.setZ_MedioPagoIdent_ID(zMedioPagoIdentID);
				}
				BigDecimal amt1 = rs.getBigDecimal("totalentregadomonref");
				BigDecimal amt2 = rs.getBigDecimal("totalamt");
				BigDecimal currencyRate = rs.getBigDecimal("currencyrate");

				if (amt1 == null) amt1 = Env.ZERO;
				if (amt2 == null) amt2 = Env.ZERO;
				if (currencyRate == null) currencyRate = Env.ONE;

				if (currency.get_ID() == schema.getC_Currency_ID()){
					amt2 = amt1;
				}
				astoVtaSumMP.setAmtTotal1(amt1);
				astoVtaSumMP.setAmtTotal2(amt2);
				astoVtaSumMP.setCurrencyRate(currencyRate);
				astoVtaSumMP.setAmtTotal(amt1);
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
		return null;
	}

	/***
	 * Obtiene y carga información de detalle de ventas por medios de pago desde SISTECO.
	 * Xpande. Created by Gabriel Vila on 8/2/19.
	 * @return
	 */
	private String getVentasDetMedioPagoSisteco(){

		String message = null;

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
			sql = " select a.st_numeroticket, a.st_timestampticket, a.st_codigocaja, a.st_codigocajera, a.st_codigomediopago, " +
					" a.st_nombremediopago, a.st_tipotarjetacredito, a.name, a.st_descripcioncfe, a.st_codigomoneda, a.st_totalentregado, " +
					" a.st_totalmppagomoneda, a.st_totalentregadomonedaref, a.st_totalmppagomonedaref, a.st_cambio, a.totalamt, " +
					" a.st_montodescuentoleyiva, a.st_descuentoafam, a.st_tipolinea, a.st_numerotarjeta, a.st_textoley, a.st_codigocc, " +
					" a.st_nombrecc, sttar.z_mediopago_id as mptar, sttar.z_mediopagoident_id, stmp.z_mediopago_id as mpsis " +
					" from zv_sisteco_vtasmpagodet a " +
					" left outer join z_sistecotipolineapazos b on a.st_tipolinea = b.value " +
					" left outer join z_sistecotipotarjeta sttar on a.st_tipotarjetacredito = sttar.value " +
					" left outer join z_sistecomediopago stmp on a.st_codigomediopago = stmp.value " +
					" where a.ad_org_id =" + this.getAD_Org_ID() +
					" and a.datetrx ='" + this.getDateTo() + "' " +
					" and b.IsAsientoVtaPOS ='Y' " +
					" order by a.st_codigomediopago, a.st_nombremediopago, a.st_tipolinea, b.name, a.st_tipotarjetacredito, a.st_nombretarjeta, a.st_codigomoneda, a.datetrx ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				MZGeneraAstoVtaDetMPST vtaDetMPST = new MZGeneraAstoVtaDetMPST(getCtx(), 0, get_TrxName());
				vtaDetMPST.setZ_GeneraAstoVta_ID(this.get_ID());
				vtaDetMPST.set_ValueOfColumn("AD_Client_ID", this.getAD_Client_ID());
				vtaDetMPST.setAD_Org_ID(this.getAD_Org_ID());
				vtaDetMPST.setDateTrx(this.getDateTo());
				vtaDetMPST.setST_NumeroTicket(rs.getString("st_numeroticket"));
				vtaDetMPST.setST_TimestampTicket(rs.getTimestamp("st_timestampticket"));
				vtaDetMPST.setST_CodigoCaja(rs.getString("st_codigocaja"));
				vtaDetMPST.setST_CodigoCajera(rs.getString("st_codigocajera"));
				vtaDetMPST.setST_CodigoMedioPago(rs.getString("st_codigomediopago"));
				vtaDetMPST.setST_NombreMedioPago(rs.getString("st_nombremediopago"));
				vtaDetMPST.setST_TipoTarjetaCredito(rs.getString("st_tipotarjetacredito"));
				vtaDetMPST.setName(rs.getString("name"));
				vtaDetMPST.setST_DescripcionCFE(rs.getString("st_descripcioncfe"));
				vtaDetMPST.setST_CodigoMoneda(rs.getString("st_codigomoneda"));
				vtaDetMPST.setST_TotalEntregado(rs.getBigDecimal("st_totalentregado"));
				vtaDetMPST.setST_TotalMPPagoMoneda(rs.getBigDecimal("st_totalmppagomoneda"));
				vtaDetMPST.setST_TotalEntregadoMonedaRef(rs.getBigDecimal("st_totalentregadomonedaref"));
				vtaDetMPST.setST_TotalMPPagoMonedaRef(rs.getBigDecimal("st_totalmppagomonedaref"));
				vtaDetMPST.setST_Cambio(rs.getBigDecimal("st_cambio"));
				vtaDetMPST.setTotalAmt(rs.getBigDecimal("totalamt"));
				vtaDetMPST.setST_MontoDescuentoLeyIVA(rs.getBigDecimal("st_montodescuentoleyiva"));
				vtaDetMPST.setST_DescuentoAfam(rs.getBigDecimal("st_descuentoafam"));
				vtaDetMPST.setST_TipoLinea(rs.getString("st_tipolinea"));
				vtaDetMPST.setST_NumeroTarjeta(rs.getString("st_numerotarjeta"));
				vtaDetMPST.setST_TextoLey(rs.getString("st_textoley"));
				vtaDetMPST.setST_CodigoCC(rs.getString("st_codigocc"));
				vtaDetMPST.setST_NombreCC(rs.getString("st_nombrecc"));

				if (rs.getInt("mptar") > 0){
					vtaDetMPST.setZ_MedioPago_ID(rs.getInt("mptar"));
				}
				else{
					if (rs.getInt("mpsis") > 0){
						vtaDetMPST.setZ_MedioPago_ID(rs.getInt("mpsis"));
					}
				}
				if (vtaDetMPST.getZ_MedioPago_ID() <= 0){
					return "No se pudo obtener información en la configuración de Sisteco para el codigo de medio de pago : " +
							vtaDetMPST.getST_CodigoMedioPago();
				}
				if (rs.getInt("z_mediopagoident_id") > 0){
					vtaDetMPST.setZ_MedioPagoIdent_ID(rs.getInt("z_mediopagoident_id"));
				}
				vtaDetMPST.saveEx();
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
	 * Obtiene y carga información de detalle de ventas por medios de pago desde SCANNTECH.
	 * Xpande. Created by Gabriel Vila on 10/21/19.
	 * @return
	 */
	private String getVentasDetMedioPagoScanntech(){

		String message = null;

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
			sql = " select a.sc_codigotipopago, a.sc_codigocredito, a.sc_codigovale, a.sc_codigomoneda, a.sc_cotizacioncompra, " +
					" case when b.sc_cuponcancelado ='N' then " +
					" (coalesce(a.sc_importepago,0) + coalesce(a.sc_descuentoafam,0) + coalesce(a.sc_descuentoincfin,0)) else " +
					" ((coalesce(a.sc_importepago,0) + coalesce(a.sc_descuentoafam,0) + coalesce(a.sc_descuentoincfin,0)) * -1) end as sc_importe, " +
					" b.sc_fechaoperacion, b.sc_numeromov, b.sc_codigocaja, b.sc_tipocfe, b.sc_seriecfe, b.sc_numerooperacion, " +
					" case when a.sc_codigomoneda = '858' then 142 else 100 end as c_currency_id, " +
					" b.sc_cuponcancelado, a.sc_numerotarjeta, " +
					" coalesce(cc.z_mediopago_id, mp.z_mediopago_id) as z_mediopago_id, cc.z_mediopagoident_id " +
					" from z_stech_tk_movpago a " +
					" inner join z_stech_tk_mov b on a.z_stech_tk_mov_id = b.z_stech_tk_mov_id " +
					" left outer join z_stechmediopago mp on a.z_stechmediopago_id = mp.z_stechmediopago_id " +
					" left outer join z_stechcreditos cc on a.z_stechcreditos_id = cc.z_stechcreditos_id " +
					" where a.ad_org_id =" + this.getAD_Org_ID() +
					" and a.datetrx='" + this.getDateTo() + "' " +
					" and mp.IsAsientoVtaPOS ='Y' " +
					" order by 1,2,3,4 ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				String codigoMP = null;
				String nombreMP = null;

				String codTipoPago = rs.getString("sc_codigotipopago");
				String codCredito = rs.getString("sc_codigocredito");
				String codVale = rs.getString("sc_codigovale");

				// Obtengo codigo de medio de pago y nombre segun sea vale, credito o medio de pago
				if ((codVale != null) && (!codVale.trim().equalsIgnoreCase(""))){

					MZStechCreditos stechCreditos = MZStechCreditos.getByValue(getCtx(), codVale, null);
					if ((stechCreditos == null) || (stechCreditos.get_ID() <= 0)){
						return "No se pudo obtener información en la configuración de Scanntech para el codigo de vale : " + codVale;
					}

					codigoMP = codVale;
					nombreMP = stechCreditos.getName();
				}
				else {
					if ((codCredito != null) && (!codCredito.trim().equalsIgnoreCase(""))){
						MZStechCreditos stechCreditos = MZStechCreditos.getByValue(getCtx(), codCredito, null);
						if ((stechCreditos == null) || (stechCreditos.get_ID() <= 0)){
							return "No se pudo obtener información en la configuración de Scanntech para el codigo de crédito : " + codCredito;
						}

						codigoMP = codCredito;
						nombreMP = stechCreditos.getName();
					}
					else{
						if ((codTipoPago != null) && (!codTipoPago.trim().equalsIgnoreCase(""))){
							MZStechMedioPago stechMedioPago = MZStechMedioPago.getByValue(getCtx(), codTipoPago, null);
							if ((stechMedioPago == null) || (stechMedioPago.get_ID() <= 0)){
								return "No se pudo obtener información en la configuración de Scanntech para el codigo de medio de pago : " + codTipoPago;
							}

							codigoMP = codTipoPago;
							nombreMP = stechMedioPago.getName();
						}
						else {
							return "No se pudo obtener información de medio de pago, ya que no se recibe código";
						}
					}
				}


				MZGeneraAstoVtaDetMPSC vtaDetMPSC = new MZGeneraAstoVtaDetMPSC(getCtx(), 0, get_TrxName());
				vtaDetMPSC.setZ_GeneraAstoVta_ID(this.get_ID());
				vtaDetMPSC.set_ValueOfColumn("AD_Client_ID", this.getAD_Client_ID());
				vtaDetMPSC.setAD_Org_ID(this.getAD_Org_ID());
				vtaDetMPSC.setDateTrx(rs.getTimestamp("sc_fechaoperacion"));
				vtaDetMPSC.setCodMedioPagoPOS(codigoMP);
				vtaDetMPSC.setNomMedioPagoPOS(nombreMP);
				vtaDetMPSC.setSC_CodigoMoneda(rs.getString("sc_codigomoneda"));
				vtaDetMPSC.setSC_CotizacionVenta(rs.getBigDecimal("sc_cotizacioncompra"));
				vtaDetMPSC.setSC_Importe(rs.getBigDecimal("sc_importe"));
				vtaDetMPSC.setSC_NumeroMov(rs.getString("sc_numeromov"));
				vtaDetMPSC.setSC_CodigoCaja(rs.getInt("sc_codigocaja"));

				String descripcionCFE = "e-Factura";
				int tipoCFE = rs.getInt("sc_tipocfe");
				if (tipoCFE == 101){
					descripcionCFE = "e-Ticket";
				}
				else if (tipoCFE == 102){
					descripcionCFE = "Nota de Credito e-Ticket";
				}
				else if (tipoCFE == 111){
					descripcionCFE = "e-Factura";
				}
				else if (tipoCFE == 112){
					descripcionCFE = "Nota de Credito e-Factura";
				}

				vtaDetMPSC.set_ValueOfColumn("SC_DescripcionCFE", descripcionCFE);

				vtaDetMPSC.setSC_TipoCfe(rs.getInt("sc_tipocfe"));
				vtaDetMPSC.setSC_SerieCfe(rs.getString("sc_seriecfe"));
				vtaDetMPSC.setSC_NumeroOperacion(rs.getString("sc_numerooperacion"));
				vtaDetMPSC.setC_Currency_ID(rs.getInt("c_currency_id"));
				vtaDetMPSC.setSC_CuponCancelado( (rs.getString("sc_cuponcancelado").equalsIgnoreCase("N")) ? false : true);
				vtaDetMPSC.setSC_NumeroTarjeta(rs.getString("sc_numerotarjeta"));

				if (rs.getInt("z_mediopago_id") > 0){
					vtaDetMPSC.setZ_MedioPago_ID(rs.getInt("z_mediopago_id"));
				}

				if (rs.getInt("z_mediopagoident_id") > 0){
					vtaDetMPSC.setZ_MedioPagoIdent_ID(rs.getInt("z_mediopagoident_id"));
				}

				vtaDetMPSC.saveEx();
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
	 * Obtiene y carga información de detalle de ventas por medios de pago desde GEOCOM.
	 * Xpande. Created by Gabriel Vila on 10/21/19.
	 * @return
	 */
	private String getVentasDetMedioPagoGeo(){

		String message = null;

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
			Date dateFechaAux = new Date(this.getDateTo().getTime());
			dateFechaAux =  DateUtils.addDays(dateFechaAux, 1);
			Timestamp dateTo2 = new Timestamp(dateFechaAux.getTime());

			sql = " select a.* " +
					" from ZV_DetVtaMedioPago a " +
					" where a.ad_org_id =" + this.getAD_Org_ID() +
					" and a.fechaticket between '" + this.getDateTo() + "' and '" + dateTo2 + "' " +
					" and a.codmediopagopos is not null " +
					" order by a.fechaticket ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){
				MZGenAstoVtaDetMPGeo detMPGeo = new MZGenAstoVtaDetMPGeo(getCtx(), 0, get_TrxName());
				detMPGeo.setAD_Org_ID(this.getAD_Org_ID());
				detMPGeo.set_ValueOfColumn("AD_Client_ID", this.getAD_Client_ID());
				detMPGeo.setZ_GeneraAstoVta_ID(this.get_ID());
				detMPGeo.setamtcashback(rs.getBigDecimal("amtcashback"));
				detMPGeo.setamtcashbackacct(rs.getBigDecimal("amtcashbackacct"));
				detMPGeo.setamtdiscount(rs.getBigDecimal("amtdiscount"));
				String aplicadtoiva = rs.getString("aplicadtoiva");
				if ((aplicadtoiva == null) || (aplicadtoiva.trim().equalsIgnoreCase(""))) {
					aplicadtoiva = "N";
				}
				detMPGeo.setaplicadtoiva((aplicadtoiva.equalsIgnoreCase("Y")) ? true:false);
				detMPGeo.setbrandid(rs.getString("brandid"));
				detMPGeo.setbrandname(rs.getString("brandname"));
				detMPGeo.setcantcuotas(rs.getBigDecimal("cantcuotas"));
				detMPGeo.setC_BPartner_ID(rs.getInt("c_bpartner_id"));
				detMPGeo.setcheckbanknumber(rs.getString("checkbanknumber"));
				detMPGeo.setchecknumber(rs.getString("checknumber"));
				detMPGeo.setcheckvaliddate(rs.getTimestamp("checkvaliddate"));
				detMPGeo.setcodautorizacion(rs.getString("codautorizacion"));
				detMPGeo.setcodcaja(rs.getString("codcaja"));
				detMPGeo.setcodcajero(rs.getString("codcajero"));
				detMPGeo.setcodcomercio(rs.getString("codcomercio"));
				detMPGeo.setcodleyimpuesto(rs.getString("codleyimpuesto"));
				detMPGeo.setcodmediopago(rs.getString("codmediopago"));
				detMPGeo.setCodMedioPagoPOS(rs.getString("CodMedioPagoPOS"));
				detMPGeo.setcodprodtarjeta(rs.getString("codprodtarjeta"));
				detMPGeo.setcodterminal(rs.getString("codterminal"));
				detMPGeo.setcodtipotarjeta(rs.getString("codtipotarjeta"));
				detMPGeo.setconversionrate(rs.getBigDecimal("conversionrate"));
				if (rs.getInt("c_taxgroup_id") > 0) {
					detMPGeo.setC_TaxGroup_ID(rs.getInt("c_taxgroup_id"));
				}
				detMPGeo.setdatevoucherorig(rs.getTimestamp("datevoucherorig"));
				detMPGeo.setdocdginame(rs.getString("docdginame"));

				String escambio = rs.getString("escambio");
				if ((escambio == null) || (escambio.trim().equalsIgnoreCase(""))) {
					escambio = "N";
				}
				detMPGeo.setescambio((escambio.equalsIgnoreCase("Y")) ? true:false);
				detMPGeo.setfechaticket(rs.getTimestamp("fechaticket"));
				detMPGeo.setISO_Code(rs.getString("ISO_Code"));

				String isonline = rs.getString("isonline");
				if ((isonline == null) || (isonline.trim().equalsIgnoreCase(""))) {
					isonline = "N";
				}
				detMPGeo.setIsOnline((isonline.equalsIgnoreCase("Y")) ? true:false);

				detMPGeo.setmodoingtarjeta(rs.getString("modoingtarjeta"));
				detMPGeo.setnommediopago(rs.getString("nommediopago"));
				detMPGeo.setNomMedioPagoPOS(rs.getString("NomMedioPagoPOS"));
				detMPGeo.setnomprodtarjeta(rs.getString("nomprodtarjeta"));
				detMPGeo.setnrotarjeta(rs.getString("nrotarjeta"));
				detMPGeo.setNroTicket(rs.getString("nroticket"));
				detMPGeo.setnrovoucher(rs.getString("nrovoucher"));
				detMPGeo.setnrovoucherorigl(rs.getString("nrovoucherorigl"));
				detMPGeo.setNumeroCFE(rs.getString("NumeroCFE"));
				detMPGeo.setorigenvta(rs.getString("origenvta"));
				detMPGeo.setplanid(rs.getString("planid"));
				detMPGeo.setposbatchid(rs.getInt("posbatchid"));
				detMPGeo.setSerieCFE(rs.getString("seriecfe"));
				detMPGeo.setTaxID(rs.getString("taxid"));
				detMPGeo.setTipoCFE(rs.getString("tipocfe"));
				detMPGeo.settipotarjeta(rs.getString("tipotarjeta"));
				detMPGeo.setTotalAmt(rs.getBigDecimal("totalamt"));
				detMPGeo.settotalentregado(rs.getBigDecimal("totalentregado"));
				detMPGeo.settotalentregadomonref(rs.getBigDecimal("totalentregadomonref"));
				detMPGeo.saveEx();
			}
		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return null;
	}

	/***
	 * Obtiene y carga información de ventas por medios de pago desde SCANNTECH.
	 * Xpande. Created by Gabriel Vila on 6/3/19.
	 * @return
	 */
	private String getVentasMedioPagoScanntech(){

		String message = null;

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{

			sql = " select a.Z_StechInterfaceVta_ID, a.sc_codigotipopago,  a.sc_codigocredito, a.sc_codigovale, a.sc_codigomoneda, a.sc_cotizacioncompra, " +
					" sum(coalesce(a.sc_importepago,0) + coalesce(a.sc_descuentoafam,0) + coalesce(a.sc_descuentoincfin,0)) as sc_importe " +
					" from z_stech_tk_movpago a " +
					" left outer join z_stechmediopago mp on a.z_stechmediopago_id = mp.z_stechmediopago_id " +
					" where a.ad_org_id =" + this.getAD_Org_ID() +
					" and a.datetrx='" + this.getDateTo() + "' " +
					" and mp.IsAsientoVtaPOS ='Y' " +
					" group by 1,2,3,4,5,6 " +
					" order by 1,2,3,4,5 ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			MZGeneraAstoVtaSumMP astoVtaSumMP = null;
			boolean firstRow = true;

			while(rs.next()){

				if (firstRow){
					this.setZ_StechInterfaceVta_ID(rs.getInt("Z_StechInterfaceVta_ID"));
					firstRow = false;
				}

				String codigoMP = null;
				String nombreMP = null;
				int zMedioPagoID = -1, zMedioPagoIdentID = -1;

				String codTipoPago = rs.getString("sc_codigotipopago");
				String codCredito = rs.getString("sc_codigocredito");
				String codVale = rs.getString("sc_codigovale");

				// Obtengo codigo de medio de pago y nombre segun sea vale, credito o medio de pago
				if ((codVale != null) && (!codVale.trim().equalsIgnoreCase(""))){

					MZStechCreditos stechCreditos = MZStechCreditos.getByValue(getCtx(), codVale, null);
					if ((stechCreditos == null) || (stechCreditos.get_ID() <= 0)){
						return "No se pudo obtener información en la configuración de Scanntech para el codigo de vale : " + codVale;
					}

					codigoMP = codVale;
					nombreMP = stechCreditos.getName();
					if (stechCreditos.getZ_MedioPago_ID() > 0){
						zMedioPagoID = stechCreditos.getZ_MedioPago_ID();
					}
					if (stechCreditos.getZ_MedioPagoIdent_ID() > 0){
						zMedioPagoIdentID = stechCreditos.getZ_MedioPagoIdent_ID();
					}
				}
				else {
					if ((codCredito != null) && (!codCredito.trim().equalsIgnoreCase(""))){
						MZStechCreditos stechCreditos = MZStechCreditos.getByValue(getCtx(), codCredito, null);
						if ((stechCreditos == null) || (stechCreditos.get_ID() <= 0)){
							return "No se pudo obtener información en la configuración de Scanntech para el codigo de crédito : " + codCredito;
						}

						codigoMP = codCredito;
						nombreMP = stechCreditos.getName();
						if (stechCreditos.getZ_MedioPago_ID() > 0){
							zMedioPagoID = stechCreditos.getZ_MedioPago_ID();
						}
						if (stechCreditos.getZ_MedioPagoIdent_ID() > 0){
							zMedioPagoIdentID = stechCreditos.getZ_MedioPagoIdent_ID();
						}
					}
					else{
						if ((codTipoPago != null) && (!codTipoPago.trim().equalsIgnoreCase(""))){
							MZStechMedioPago stechMedioPago = MZStechMedioPago.getByValue(getCtx(), codTipoPago, null);
							if ((stechMedioPago == null) || (stechMedioPago.get_ID() <= 0)){
								return "No se pudo obtener información en la configuración de Scanntech para el codigo de medio de pago : " + codTipoPago;
							}

							codigoMP = codTipoPago;
							nombreMP = stechMedioPago.getName();
							if (stechMedioPago.getZ_MedioPago_ID() > 0){
								zMedioPagoID = stechMedioPago.getZ_MedioPago_ID();
							}
						}
						else {
							return "No se pudo obtener información de medio de pago, ya que no se recibe código";
						}
					}
				}

				astoVtaSumMP = new MZGeneraAstoVtaSumMP(getCtx(), 0, get_TrxName());
				astoVtaSumMP.setZ_GeneraAstoVta_ID(this.get_ID());
				astoVtaSumMP.set_ValueOfColumn("AD_Client_ID", this.getAD_Client_ID());
				astoVtaSumMP.setAD_Org_ID(this.getAD_Org_ID());
				astoVtaSumMP.setCodMedioPagoPOS(codigoMP);
				astoVtaSumMP.setNomMedioPagoPOS(nombreMP);
				astoVtaSumMP.setC_Currency_ID(142);
				astoVtaSumMP.setC_Currency_2_ID(100);

				if (zMedioPagoID > 0){
					astoVtaSumMP.setZ_MedioPago_ID(zMedioPagoID);
				}
				if (zMedioPagoIdentID > 0){
					astoVtaSumMP.setZ_MedioPagoIdent_ID(zMedioPagoIdentID);
				}

				String codMoneda = rs.getString("sc_codigomoneda");
				if ((codMoneda == null) || (codMoneda.trim().equalsIgnoreCase(""))){
					return "Hay información de ventas por medios de pago que no tiene MONEDA.";
				}

				BigDecimal amt1 = Env.ZERO, amt2 = Env.ZERO;
				BigDecimal currencyRate = Env.ONE;

				if (codMoneda.trim().equalsIgnoreCase("858")){

					amt1 = rs.getBigDecimal("sc_importe");
					if (amt1 == null) amt1 = Env.ZERO;

				}
				else if (codMoneda.trim().equalsIgnoreCase("840")){

					amt2 = rs.getBigDecimal("sc_importe");
					if (amt2 == null) amt2 = Env.ZERO;

					currencyRate = rs.getBigDecimal("sc_cotizacioncompra");
					if (currencyRate == null) currencyRate = Env.ONE;

					amt1 = amt2.multiply(currencyRate).setScale(2, RoundingMode.HALF_UP);
					if (amt1 == null) amt1 = Env.ZERO;
				}
				else {
					return "Se encontró una Moneda disinta a PESOS o DOLARES y no es posible procesarla : " + codMoneda;
				}

				astoVtaSumMP.setAmtTotal1(amt1);
				astoVtaSumMP.setAmtTotal2(amt2);
				astoVtaSumMP.setCurrencyRate(currencyRate);
				astoVtaSumMP.setAmtTotal(amt1);

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
	 * Cuando para un medio de pago, como por ejemplo EFECTIVO, hubo ventas en pesos y en dolares, debo considerar un posible
	 * cambio dado en pesos para las ventas en dolares. Considerarlo significa que debo restarlo a las ventas en pesos de ese
	 * medio de pago.
	 * Xpande. Created by Gabriel Vila on 4/30/19.
	 */
	private void updateMontoCambios() {

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String action = "";

		try{
		    sql = " select codmediopagopos, coalesce(changeamt,0) as changeamt  " +
					" from z_generaastovtasummp " +
					" where z_generaastovta_id =" + this.get_ID() +
					" and amttotal2 > 0 and changeamt > 0 ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){
				action = " update z_generaastovtasummp " +
						 " set amttotal1 = amttotal1 - " + rs.getBigDecimal("changeamt") + ", " +
						 " amttotal = amttotal - " + rs.getBigDecimal("changeamt") +
						 " where z_generaastovta_id =" + this.get_ID() +
						 " and codmediopagopos ='" + rs.getString("codmediopagopos") + "' " +
						 " and amttotal2 <= 0 ";
				DB.executeUpdateEx(action, get_TrxName());
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
	 * Obtiene información de medios de pago e impuestos.
	 * Xpande. Created by Gabriel Vila on 5/22/20.
	 * @return
	 */
	public String getInfo(){

		String message = null;

		try{

			// Cargo información de medios de pago
			message = this.getVentasMedioPago();

			// Cargo información de impuestos
			if (message == null){
				message = this.getVentasImpuesto();
			}

			// Calcula diferencia como redondeo
			this.setTotalesYRedondeo();

		}
		catch (Exception e){
		    throw new AdempiereException(e);
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
				message = this.getVentasImpuestoScanntech();
			}
			else if (posVendor.getValue().equalsIgnoreCase("GEOCOM")){
				message = this.getVentasImpuestoGeocom();
			}

			// Actualizo monto redondeo
			//this.setRedondeo();

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

		return message;
	}

	/***
	 * Setea importe de Redondeo, producto de total medios de pago menos total impuestos.
	 * Xpande. Created by Gabriel Vila on 5/16/19.
	 */
	public void setTotalesYRedondeo() {

		String sql = "";

		try{
			// Obtengo total de medios de pago

			// Sin identificadores
			sql = " select round(sum(a.amttotal),2) as total " +
					" from z_generaastovtasummp a " +
					" inner join z_mediopago b on a.z_mediopago_id = b.z_mediopago_id " +
					" where a.z_generaastovta_id =" + this.get_ID() +
					" and a.z_mediopagoident_id is null " +
					" and b.ContabVtaPos='Y' ";

			BigDecimal amtMediosPago1 = DB.getSQLValueBDEx(get_TrxName(), sql);
			if (amtMediosPago1 == null) amtMediosPago1 = Env.ZERO;

			// Con identificadores que se contabilizan
			sql = " select round(sum(a.amttotal),2) as total " +
					" from z_generaastovtasummp a " +
					" inner join z_mediopagoident b on a.z_mediopagoident_id = b.z_mediopagoident_id " +
					" where a.z_generaastovta_id =" + this.get_ID() +
					" and b.Contabilizar='Y' ";

			BigDecimal amtMediosPago2 = DB.getSQLValueBDEx(get_TrxName(), sql);
			if (amtMediosPago2 == null) amtMediosPago2 = Env.ZERO;

			BigDecimal amtMediosPago = amtMediosPago1.add(amtMediosPago2);

			// Obtengo total de impuestos
			sql = " select round(sum(taxamt),2) as total " +
					" from " + X_Z_GeneraAstoVtaSumTax.Table_Name +
					" where " + X_Z_GeneraAstoVtaSumTax.COLUMNNAME_Z_GeneraAstoVta_ID + " =" + this.get_ID();

			BigDecimal amtImpuestos = DB.getSQLValueBDEx(get_TrxName(), sql);
			if (amtImpuestos == null) amtImpuestos = Env.ZERO;

			// Obtengo total base de impuestos
			sql = " select round(sum(taxbaseamt),2) as total " +
					" from " + X_Z_GeneraAstoVtaSumTax.Table_Name +
					" where " + X_Z_GeneraAstoVtaSumTax.COLUMNNAME_Z_GeneraAstoVta_ID + " =" + this.get_ID();

			BigDecimal amtBaseImp = DB.getSQLValueBDEx(get_TrxName(), sql);
			if (amtBaseImp == null) amtBaseImp = Env.ZERO;

			this.setAmtAcctDr(amtMediosPago);
			this.setAmtAcctCr(amtBaseImp.add(amtImpuestos));

			MZPosVendor posVendor = (MZPosVendor) this.getZ_PosVendor();
			if (posVendor.getValue().equalsIgnoreCase("GEOCOM")){
				BigDecimal amtRounding = this.setRedondeoGeocom();
				this.setAmtRounding(amtRounding);
			}
			else{
				this.setAmtRounding(amtMediosPago.subtract(amtBaseImp).subtract(amtImpuestos));
			}
			this.saveEx();

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}

	private BigDecimal setRedondeoGeocom() {

		BigDecimal amtRounding = Env.ZERO;

		try{
			Date dateFechaAux = new Date(this.getDateTo().getTime());
			dateFechaAux =  DateUtils.addDays(dateFechaAux, 1);
			Timestamp dateTo2 = new Timestamp(dateFechaAux.getTime());

			String sql = " select sum(amtrounding) as amtrounding " +
						"from z_gc_interfacevta " +
						" where ad_org_id =" + this.getAD_Org_ID() +
						" and fechaticket between '" + this.getDateTo() + "' and '" + dateTo2 + "' " +
						"and tipocfe in ('101', '111')";
			BigDecimal amtRoundARI = DB.getSQLValueBDEx(get_TrxName(), sql);
			if  (amtRoundARI == null) amtRoundARI = Env.ZERO;

			sql = " select sum(amtrounding) as amtrounding " +
					"from z_gc_interfacevta " +
					" where ad_org_id =" + this.getAD_Org_ID() +
					" and fechaticket between '" + this.getDateTo() + "' and '" + dateTo2 + "' " +
					"and tipocfe in ('102', '112')";
			BigDecimal amtRoundARC = DB.getSQLValueBDEx(get_TrxName(), sql);
			if  (amtRoundARC == null) amtRoundARC = Env.ZERO;

			amtRounding = amtRoundARI.subtract(amtRoundARC);
		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
		return amtRounding;
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
				astoVtaSumTax.set_ValueOfColumn("AD_Client_ID", this.getAD_Client_ID());
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
	 * Obtiene y carga información de ventas por impuesto desde SCANNTECH
	 * Xpande. Created by Gabriel Vila on 6/4/19.
	 * @return
	 */
	private String getVentasImpuestoScanntech(){

		String message = null;

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
			sql = " select a.c_taxcategory_id, b.name, a.sc_porcentajeiva, round(sum(a.sc_montoiva * coalesce(a.sc_cotizacioncompra,1)),2) as taxamt, " +
					" case when sc_porcentajeiva > 0 then " +
					" sum(round((a.sc_importe * coalesce(a.sc_cotizacioncompra,1) *100)/(100 + sc_porcentajeiva),2)) else sum(a.sc_importe * coalesce(a.sc_cotizacioncompra,1)) end as taxbaseamt " +
					" from zv_scanntech_detvtas a " +
					" left outer join c_taxcategory b on a.c_taxcategory_id = b.c_taxcategory_id  " +
					" where a.ad_org_id =" + this.getAD_Org_ID() +
					" and a.datetrx ='" + this.getDateTo() + "' " +
					" group by a.c_taxcategory_id, b.name, a.sc_porcentajeiva ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				MZGeneraAstoVtaSumTax astoVtaSumTax = new MZGeneraAstoVtaSumTax(getCtx(), 0, get_TrxName());
				astoVtaSumTax.setZ_GeneraAstoVta_ID(this.get_ID());
				astoVtaSumTax.set_ValueOfColumn("AD_Client_ID", this.getAD_Client_ID());
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
	 * Obtiene y carga información de ventas por impuesto desde GEOCOM
	 * Xpande. Created by Gabriel Vila on 6/4/19.
	 * @return
	 */
	private String getVentasImpuestoGeocom(){

		String message = null;

		String sql = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
			sql = " select a.c_taxcategory_id, b.name, a.c_tax_id, cur.c_currency_id, " +
					" round(sum(a.taxamt),2) as taxamt, " +
					" round(sum(a.amtsubtotal),2) as taxbaseamt " +
					" from zv_geocom_detvtas a " +
					" inner join c_currency cur on (a.iso_code = cur.iso_code) " +
					" left outer join c_taxcategory b on a.c_taxcategory_id = b.c_taxcategory_id  " +
					" where a.ad_org_id =" + this.getAD_Org_ID() +
					" and a.datetrx ='" + this.getDateTo() + "' " +
					" group by a.c_taxcategory_id, b.name, a.c_tax_id, cur.c_currency_id ";

			pstmt = DB.prepareStatement(sql, get_TrxName());
			rs = pstmt.executeQuery();

			while(rs.next()){

				MZGeneraAstoVtaSumTax astoVtaSumTax = new MZGeneraAstoVtaSumTax(getCtx(), 0, get_TrxName());
				astoVtaSumTax.setZ_GeneraAstoVta_ID(this.get_ID());
				astoVtaSumTax.set_ValueOfColumn("AD_Client_ID", this.getAD_Client_ID());
				astoVtaSumTax.setAD_Org_ID(this.getAD_Org_ID());
				astoVtaSumTax.setC_TaxCategory_ID(rs.getInt("c_taxcategory_id"));
				astoVtaSumTax.setC_Currency_ID(rs.getInt("c_currency_id"));
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


	/***
	 * Obtiene y retorna lista de medios de pago sumarizados asociados a este documento.
	 * Xpande. Created by Gabriel Vila on 5/14/19.
	 * @return
	 */
	public List<MZGeneraAstoVtaSumMP> getLineasMediosPago(){

		String whereClause = X_Z_GeneraAstoVtaSumMP.COLUMNNAME_Z_GeneraAstoVta_ID + " =" + this.get_ID();

		List<MZGeneraAstoVtaSumMP> lines = new Query(getCtx(), I_Z_GeneraAstoVtaSumMP.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}

	/***
	 * Obtiene y retorna lista de impuestos sumarizados asociados a este documento.
	 * Xpande. Created by Gabriel Vila on 5/14/19.
	 * @return
	 */
	public List<MZGeneraAstoVtaSumTax> getLineasImpuestos(){

		String whereClause = X_Z_GeneraAstoVtaSumTax.COLUMNNAME_Z_GeneraAstoVta_ID + " =" + this.get_ID();

		List<MZGeneraAstoVtaSumTax> lines = new Query(getCtx(), I_Z_GeneraAstoVtaSumTax.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}

	/***
	 * Obtiene y retorna lineas de detalle de medios de pago de sisteco, que tuvieron modificaciones en medio de pago o tarjeta.
	 * Xpande. Created by Gabriel Vila on 8/9/19.
 	 * @return
	 */
	public List<MZGeneraAstoVtaDetMPST> getMediosPagoModifSisteco(){

		String whereClause = X_Z_GeneraAstoVtaDetMPST.COLUMNNAME_Z_GeneraAstoVta_ID + " =" + this.get_ID() +
				" AND (" + X_Z_GeneraAstoVtaDetMPST.COLUMNNAME_Z_SistecoMedioPago_ID + " is not null OR " +
				X_Z_GeneraAstoVtaDetMPST.COLUMNNAME_Z_SistecoTipoTarjeta_ID + " is not null) ";

		List<MZGeneraAstoVtaDetMPST> lines = new Query(getCtx(), I_Z_GeneraAstoVtaDetMPST.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}

	/***
	 * Obtiene y retorna lineas de detalle de medios de pago de Scanntech, que tuvieron modificaciones en medio de pago o tarjeta.
	 * Xpande. Created by Gabriel Vila on 5/8/20.
	 * @return
	 */
	public List<MZGeneraAstoVtaDetMPSC> getMediosPagoModifScanntech(){

		String whereClause = X_Z_GeneraAstoVtaDetMPSC.COLUMNNAME_Z_GeneraAstoVta_ID + " =" + this.get_ID() +
				" AND (" + X_Z_GeneraAstoVtaDetMPSC.COLUMNNAME_Z_StechMedioPago_ID + " is not null OR " +
				X_Z_GeneraAstoVtaDetMPSC.COLUMNNAME_Z_StechCreditos_ID + " is not null) ";

		List<MZGeneraAstoVtaDetMPSC> lines = new Query(getCtx(), I_Z_GeneraAstoVtaDetMPSC.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}

	/***
	 * Obtiene y retorna lineas de detalle de medios de pago de Geocom, que tuvieron modificaciones en medio de pago o tarjeta.
	 * Xpande. Created by Gabriel Vila on 5/8/20.
	 * @return
	 */
	public List<MZGenAstoVtaDetMPGeo> getMediosPagoModifGeocom(){

		String whereClause = X_Z_GenAstoVtaDetMPGeo.COLUMNNAME_Z_GeneraAstoVta_ID + " =" + this.get_ID() +
				" AND (" + X_Z_GenAstoVtaDetMPGeo.COLUMNNAME_Z_MedioPagoPos_ID + " is not null OR " +
				X_Z_GenAstoVtaDetMPGeo.COLUMNNAME_Z_MPagoIdentPos_ID + " is not null) ";

		List<MZGenAstoVtaDetMPGeo> lines = new Query(getCtx(), I_Z_GenAstoVtaDetMPGeo.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}
}