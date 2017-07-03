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
import java.util.HashMap;
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
import org.xpande.core.model.MZProductoUPC;
import org.xpande.core.model.MZSocioListaPrecio;
import sun.misc.MessageUtils;

/** Generated Model for Z_PreciosProvCab
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class MZPreciosProvCab extends X_Z_PreciosProvCab implements DocAction, DocOptions {

	/**
	 *
	 */
	private static final long serialVersionUID = 20170613L;

    /** Standard Constructor */
    public MZPreciosProvCab (Properties ctx, int Z_PreciosProvCab_ID, String trxName)
    {
      super (ctx, Z_PreciosProvCab_ID, trxName);
    }

    /** Load Constructor */
    public MZPreciosProvCab (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
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
		

		/*
		MDocType dt = MDocType.get(getCtx(), getC_DocType_ID());

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

		// Obtengo lineas del documento
		List<MZPreciosProvLin> lines = this.getLines();

		// Valido condiciones para completar este documento
		m_processMsg = this.validateDocument(lines);
		if (m_processMsg != null){
			return DocAction.STATUS_Invalid;
		}

		Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);

		// Obtengo lista de precios de compra y versión de la misma a procesar
		this.setPriceListPO();
		MPriceList plCompra = (MPriceList) this.getM_PriceList();
		MPriceListVersion plVersionCompra = (MPriceListVersion) this.getM_PriceList_Version();

		// Obtengo lista de precios de venta y versión de la misma a procesar
		MPriceList plVenta = new MPriceList(getCtx(), this.getM_PriceList_ID_SO(), get_TrxName());
		MPriceListVersion plVersionVenta = new MPriceListVersion(getCtx(), this.getM_PriceList_Version_ID_SO(), get_TrxName());

		// Obtengo modelo para linea de productos del socio, si es nueva la creo en este momento y la asocio al socio del documento.
		MZLineaProductoSocio lineaProductoSocio = null;
		if (this.getZ_LineaProductoSocio_ID() > 0){
			lineaProductoSocio = (MZLineaProductoSocio) this.getZ_LineaProductoSocio();
		}
		else{
			// Nueva linea, la creo y la asocio al socio de negocios de este documento
			lineaProductoSocio = new MZLineaProductoSocio(getCtx(), 0, get_TrxName());
			lineaProductoSocio.setC_BPartner_ID(this.getC_BPartner_ID());
			lineaProductoSocio.setName(this.getNombreLineaManual());
			lineaProductoSocio.setIsOwn(true);
			lineaProductoSocio.setIsLockedPO(false);
			lineaProductoSocio.setM_PriceList_ID(plCompra.get_ID());
			lineaProductoSocio.saveEx();
			this.setZ_LineaProductoSocio_ID(lineaProductoSocio.get_ID());

			// Si tengo pauta comercial, le asocio la nueva linea de productos en este momento y aplico
			if (this.getZ_PautaComercial_ID() > 0){
				MZPautaComercial pautaComercial = (MZPautaComercial)this.getZ_PautaComercial();
				pautaComercial.updateLineaProducto(this.getZ_LineaProductoSocio_ID());
				m_processMsg = pautaComercial.applyPauta(false);
				if (m_processMsg != null){
					return DocAction.STATUS_Invalid;
				}
			}
		}

		// Recorro y proceso lineas (ya fueron validadas)
		for (MZPreciosProvLin line: lines){

			// Sete de producto de la linea, en caso de ser un nuevo producto se crea en este momento.
			line.setProduct();

			// Actualizo lista de precios de compra del documento para el producto de esta linea
			this.updateProductPriceListPO(plCompra, plVersionCompra, line);

 			// Actualizo lista de precios de venta del documento para el producto de esta linea
			this.updateProductPriceListSO(plVenta, plVersionVenta, line);

			// Actualizo segmentos especiales en pauta comercial para el producto de esta linea
			this.updateProductPautaComercial(line);

			// Proceso información para asociaciones de producto, socio de negocio, distribuidores y organizaciones
			// Primero para el socio de negocio del documento
			this.setProductSocioOrgs(this.getC_BPartner_ID(), this.getZ_LineaProductoSocio_ID(), line, fechaHoy, plCompra, plVersionCompra, plVenta, plVersionVenta);

			// Luego para los ditribuidores del socio de negocio del documento
			List<MZPreciosProvDistri> distris = this.getDistribuidores();
			for (MZPreciosProvDistri preciosProvDistri: distris){

				// Obtengo o creo distribuidor del socio de negocio del documento
				MZLineaProductoDistri lineaProductoDistri = null;
				if (preciosProvDistri.getZ_LineaProductoDistri_ID() > 0){
					lineaProductoDistri = (MZLineaProductoDistri) preciosProvDistri.getZ_LineaProductoDistri();
				}
				else{
					lineaProductoDistri = new MZLineaProductoDistri(getCtx(), 0, get_TrxName());
					lineaProductoDistri.setZ_LineaProductoSocio_ID(this.getZ_LineaProductoSocio_ID());
					lineaProductoDistri.setC_BPartner_ID(preciosProvDistri.getC_BPartner_ID());
					lineaProductoDistri.setIsLockedPO(false);
					lineaProductoDistri.setLineaProductoDistribuidor();
					lineaProductoDistri.saveEx();
					preciosProvDistri.setZ_LineaProductoDistri_ID(lineaProductoDistri.get_ID());
					preciosProvDistri.saveEx();
				}

				// Actualizo precios de compra de este producto en lista de precios de compra del distribuidor
				lineaProductoDistri.updateProductPriceListPO(this.getC_Currency_ID(), line.getM_Product_ID(), line.getPriceList());

				// Asocio producto a distribuidor y organizaciones
				this.setProductSocioOrgs(lineaProductoDistri.getC_BPartner_ID(), lineaProductoDistri.getZ_LineaProductoSocioRelated_ID(), line, fechaHoy,
						lineaProductoDistri.getPlCompra(), lineaProductoDistri.getPlVersionCompra(), plVenta, plVersionVenta);
			}

			line.saveEx();
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


	/***
	 * Actualiza pauta comercial, asociando el producto de la linea recibida con los segmentos especiales correspondientes.
	 * Xpande. Created by Gabriel Vila on 6/27/17.
	 * @param line
	 */
	private void updateProductPautaComercial(MZPreciosProvLin line) {

		try{

			// No hago nada si no tengo pauta comercial en este documento y si no tengo producto en la linea
			if (this.getZ_PautaComercial_ID() <= 0) return;
			if (line.getM_Product_ID() <= 0) return;

			// Al producto de esta linea, le quito los segmentos espciales actualmente asociados
			String action = " delete from " + I_Z_PautaComercialSetProd.Table_Name +
					" where " + X_Z_PautaComercialSetProd.COLUMNNAME_M_Product_ID + " =" + line.getM_Product_ID() +
					" and " + X_Z_PautaComercialSetProd.COLUMNNAME_Z_PautaComercialSet_ID +
					" in (select z_pautacomercialset_id from z_pautacomercialset where z_pautacomercial_id =" + this.getZ_PautaComercial_ID() + ")";
			DB.executeUpdateEx(action, get_TrxName());

			// Asocio el producto de esta linea en los segmentos especiales 1 y 2 seleccionados
			// Segmento especial 1
			if (line.getZ_PautaComercialSet_ID_1() > 0){
				MZPautaComercialSetProd pautaComercialSetProd = new MZPautaComercialSetProd(getCtx(), 0, get_TrxName());
				pautaComercialSetProd = new MZPautaComercialSetProd(getCtx(), 0, get_TrxName());
				pautaComercialSetProd.setZ_PautaComercialSet_ID(line.getZ_PautaComercialSet_ID_1());
				pautaComercialSetProd.setM_Product_ID(line.getM_Product_ID());
				pautaComercialSetProd.setUPC(line.getUPC());
				pautaComercialSetProd.setVendorProductNo(line.getVendorProductNo());
				pautaComercialSetProd.saveEx();
			}
			// Segmento especial 2
			if (line.getZ_PautaComercialSet_ID_2() > 0){
				MZPautaComercialSetProd pautaComercialSetProd = new MZPautaComercialSetProd(getCtx(), 0, get_TrxName());
				pautaComercialSetProd = new MZPautaComercialSetProd(getCtx(), 0, get_TrxName());
				pautaComercialSetProd.setZ_PautaComercialSet_ID(line.getZ_PautaComercialSet_ID_2());
				pautaComercialSetProd.setM_Product_ID(line.getM_Product_ID());
				pautaComercialSetProd.setUPC(line.getUPC());
				pautaComercialSetProd.setVendorProductNo(line.getVendorProductNo());
				pautaComercialSetProd.saveEx();
			}
		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}


	/***
	 * Actualiza lista de precio de compra para el producto de la linea recibida.
	 * Xpande. Created by Gabriel Vila on 6/21/17.
 	 * @param plCompra
	 * @param plVersionCompra
	 * @param line
	 */
	private void updateProductPriceListPO(MPriceList plCompra, MPriceListVersion plVersionCompra, MZPreciosProvLin line) {

		try{
			// Intento obtener precio de lista actual para el producto de esta linea, en la versión de lista
			// de precios de compra recibida.
			MProductPrice pprice = MProductPrice.get(getCtx(), plVersionCompra.get_ID(), line.getM_Product_ID(), get_TrxName());

			// Si no tengo precio para este producto, lo creo.
			if ((pprice == null) || (pprice.getM_Product_ID() <= 0)){
				pprice = new MProductPrice(plVersionCompra, line.getM_Product_ID(), line.getPriceList(), line.getPriceList(), line.getPriceList());
			}
			else{
				// Actualizo precios
				pprice.setPriceList(line.getPriceList());
				pprice.setPriceStd(line.getPriceList());
				pprice.setPriceLimit(line.getPriceList());
			}
			pprice.saveEx();

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}

	/***
	 * Actualiza lista de precios de venta para el producto de la linea recibida.
	 * Xpande. Created by Gabriel Vila on 6/21/17.
	 * @param plVenta
	 * @param plVersionVenta
	 * @param line
	 */
	private void updateProductPriceListSO(MPriceList plVenta, MPriceListVersion plVersionVenta, MZPreciosProvLin line) {

		try{
			// Intento obtener precio de lista actual para el producto de esta linea, en la versión de lista
			// de precios de venta recibida.
			MProductPrice pprice = MProductPrice.get(getCtx(), plVersionVenta.get_ID(), line.getM_Product_ID(), get_TrxName());

			// Si no tengo precio para este producto, lo creo.
			if ((pprice == null) || (pprice.getM_Product_ID() <= 0)){
				pprice = new MProductPrice(plVersionVenta, line.getM_Product_ID(), line.getNewPriceSO(), line.getNewPriceSO(), line.getNewPriceSO());
			}
			else{
				// Actualizo precios
				pprice.setPriceList(line.getNewPriceSO());
				pprice.setPriceStd(line.getNewPriceSO());
				pprice.setPriceLimit(line.getNewPriceSO());
			}
			pprice.saveEx();

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

	}

	/***
	 * Setea lista de precios de compra a considerar.
	 * Procedimiento:
	 * 1. Si tengo lista seleccionada en el documento, utilizo eso.
	 * 2. Si no tengo lista seleccionada, verifico si no hay una lista existente para la moneda de compra indicada,
	 * si hay la asocio a este documento y la utilizo.
	 * 3. Si no tengo lista seleccionada y tampoco existe una para la moneda de compra, tengo que crear una nueva,
	 * asociarla a este documento y utilizarla.
	 * Xpande. Created by Gabriel Vila on 6/19/17.
	 * @return
	 */
	private void setPriceListPO() {

		MPriceList pl = null;

		try{

			// Si tengo lista seleccionada, no hago nada.
			if (this.getM_PriceList_ID() > 0){
				return;
			}

			// Veo si hay una lista existente para socio de negocio y moneda de compra
			MZSocioListaPrecio bpl = MZSocioListaPrecio.getByPartnerCurrency(getCtx(), this.getC_BPartner_ID(), this.getC_Currency_ID(), get_TrxName());
			if ((bpl != null) && (bpl.get_ID() > 0)){
				this.setM_PriceList_ID(bpl.getM_PriceList_ID());
				this.setM_PriceList_Version_ID(((MPriceList)bpl.getM_PriceList()).getPriceListVersion(null).get_ID());
			}
			else{
				// No existe lista para este socio de negocio y moneda de compra. La creo y seteo al socio de negocio.
				MBPartner bp = (MBPartner) this.getC_BPartner();
				MCurrency cur = (MCurrency) this.getC_Currency();
				pl = new MPriceList(getCtx(), 0, get_TrxName());
				pl.setName("LISTA " + bp.getName().toUpperCase() + " " + cur.getISO_Code());
				pl.setC_Currency_ID(this.getC_Currency_ID());
				pl.setIsSOPriceList(false);
				pl.setIsTaxIncluded(true);
				pl.setIsNetPrice(false);
				pl.setPricePrecision(cur.getStdPrecision());
				pl.setAD_Org_ID(0);
				pl.saveEx();

				MPriceListVersion plv = new MPriceListVersion(pl);
				plv.setName("VIGENTE");
				plv.setM_DiscountSchema_ID(1000000);
				plv.saveEx();

				bpl =  new MZSocioListaPrecio(getCtx(), 0, get_TrxName());
				bpl.setC_BPartner_ID(this.getC_BPartner_ID());
				bpl.setC_Currency_ID(this.getC_Currency_ID());
				bpl.setM_PriceList_ID(pl.get_ID());
				bpl.saveEx();

				this.setM_PriceList_ID(pl.get_ID());
				this.setM_PriceList_Version_ID(plv.get_ID());
			}
		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}


	/***
	 * Realiza validaciones a nivel del documento.
	 * @param lines
	 * @return null si esta ok, o mensaje de invalidez
	 */
	private String validateDocument(List<MZPreciosProvLin> lines) {

		String message = null;

		if (this.getZ_LineaProductoSocio_ID() <= 0){
			if ((this.getNombreLineaManual() == null) || (this.getNombreLineaManual().trim().equalsIgnoreCase(""))){
				return "Debe indicar Linea de Producto del Socio de Negocio a considerar, o ingresar Nombre para crear una Nueva.";
			}
		}

		if (lines.size() <= 0){
			return "El documento no tiene productos para procesar.";
		}

		Timestamp today = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
		Timestamp validFrom = TimeUtil.trunc(this.getDateValidPO(), TimeUtil.TRUNC_DAY);

		if (validFrom.compareTo(today) != 0){
			return "La fecha de Vigencia tiene que ser igual a hoy";
		}

		// Si tengo modalidad de de proceso por medio de archivo de interface
		if (this.getModalidadPreciosProv().equalsIgnoreCase(X_Z_PreciosProvCab.MODALIDADPRECIOSPROV_ARCHIVODECARGA)){
			// Valido que no haya lineas de inconsistencias en el archivo que NO fueron marcadas como omitidas
			List<MZPreciosProvArchivo> linesFile = this.getLineasArchivoNoConfNoOmitir();
			if (linesFile.size() > 0){
				return "Hay lineas del archivo que aún tienen inconsistencias.\nDebe omitir o solucionar las mismas.";
			}
		}

		// Recorro y valido lineas del documento
		boolean hayInconcistencias = false;
		for (MZPreciosProvLin line: lines){

			// En caso de haber alguna inconsistencia, marco linea como no confirmada y guardo el mensaje
			String messageLine = line.validate();
			if (messageLine != null){
				hayInconcistencias = true;
				line.setIsConfirmed(false);
				line.setErrorMsg(messageLine);
			}
			else {
				// Linea validada, proceso.
				line.setIsConfirmed(true);
				line.setErrorMsg(null);
			}
			line.saveEx();
		}

		if (hayInconcistencias){
			return "Hay inconsistencias en algunos de los productos.\nDebe solucionarlas antes de poder completar el documento.";
		}

		return message;
	}


	/***
	 * Setea información para la asociación de producto, socio de negocio, y organizaciones.
	 * Xpande. Created by Gabriel Vila on 6/21/17.
	 * @param cBPartnerID
	 * @param zLineaProductoSocioID
	 * @param line
	 * @param fechaHoy
	 * @param plCompra
	 * @param plVersionCompra
	 * @param plVenta
	 * @param plVersionVenta
	 */
	private void setProductSocioOrgs(int cBPartnerID, int zLineaProductoSocioID, MZPreciosProvLin line, Timestamp fechaHoy,
									 MPriceList plCompra, MPriceListVersion plVersionCompra,
									 MPriceList plVenta, MPriceListVersion plVersionVenta) {

		try{

			String action = "";

			// Si es la primera vez que proceso precios de este proveedor para este producto, creo la asociación.
			// Si ya ha tenido compras, entonces obtengo modelo de la asociación
			MZProductoSocio prodbp = MZProductoSocio.getByBPartnerProduct(getCtx(), cBPartnerID, line.getM_Product_ID(), get_TrxName());
			if (prodbp == null){
				prodbp = new MZProductoSocio(getCtx(), 0, get_TrxName());
				prodbp.setM_Product_ID(line.getM_Product_ID());
				prodbp.setC_BPartner_ID(cBPartnerID);
				prodbp.setDateValidPO(fechaHoy);
				prodbp.setDateValidSO(fechaHoy);
				prodbp.setZ_LineaProductoSocio_ID(zLineaProductoSocioID);
				prodbp.setPriceList(line.getPriceList());
				prodbp.setPriceSO(line.getNewPriceSO());
			}
			else{
				// Si precio de lista compra cambia
				if (prodbp.getPriceList().compareTo(line.getPriceList()) != 0){
					// Actualizo info precios compra para este producto-socio
					prodbp.setDateValidPO(fechaHoy);
					prodbp.setPriceList(line.getPriceList());
				}

				// Si precio de lista venta cambia
				if (prodbp.getPriceSO().compareTo(line.getNewPriceSO()) != 0){
					prodbp.setDateValidSO(fechaHoy);
					prodbp.setPriceSO(line.getNewPriceSO());
				}
			}

			// Precios OC, final y descuentos se actualizan siempre porque pueden haber cambiado las pautas comerciales
			prodbp.setM_PriceList_ID(plCompra.get_ID());
			prodbp.setM_PriceList_Version_ID(plVersionCompra.get_ID());
			prodbp.setM_PriceList_ID_SO(plVenta.get_ID());
			prodbp.setM_PriceList_Version_ID_SO(plVersionVenta.get_ID());
			prodbp.setC_Currency_ID(plCompra.getC_Currency_ID());
			prodbp.setC_Currency_ID_SO(this.getC_Currency_ID_SO());
			prodbp.setPricePO(line.getPricePO());
			prodbp.setPricePOMargin(line.getPricePOMargin());
			prodbp.setPriceFinal(line.getPriceFinal());
			prodbp.setPriceFinalMargin(line.getPriceFinalMargin());
			if (this.getZ_PautaComercial_ID() > 0){
				prodbp.setZ_PautaComercial_ID(this.getZ_PautaComercial_ID());
				prodbp.setTotalDiscountsPO(line.getTotalDiscountsPO());
				prodbp.setTotalDiscountsFinal(line.getTotalDiscountsFinal());

				if (line.getZ_PautaComercialSet_ID_Gen() > 0){
					prodbp.setZ_PautaComercialSet_ID_Gen(line.getZ_PautaComercialSet_ID_Gen());
				}
				else{
					prodbp.setZ_PautaComercialSet_ID_Gen(0);
				}
				if (line.getZ_PautaComercialSet_ID_1() > 0){
					prodbp.setZ_PautaComercialSet_ID_1(line.getZ_PautaComercialSet_ID_1());
				}
				else{
					prodbp.setZ_PautaComercialSet_ID_1(0);
				}
				if (line.getZ_PautaComercialSet_ID_2() > 0){
					prodbp.setZ_PautaComercialSet_ID_2(line.getZ_PautaComercialSet_ID_2());
				}
				else{
					prodbp.setZ_PautaComercialSet_ID_2(0);
				}
			}
			else{
				prodbp.setZ_PautaComercial_ID(0);
				prodbp.setTotalDiscountsPO(null);
				prodbp.setTotalDiscountsFinal(null);
				prodbp.setZ_PautaComercialSet_ID_Gen(0);
				prodbp.setZ_PautaComercialSet_ID_1(0);
				prodbp.setZ_PautaComercialSet_ID_2(0);
			}

			prodbp.saveEx();

			// No dejo ids de pauta en cero en el producto-socio
			if (prodbp.getZ_PautaComercial_ID() == 0){
				action = " update z_productosocio set z_pautacomercial_id = null, z_pautacomercialset_id_gen = null, " +
						 " z_pautacomercialset_id_1 = null, z_pautacomercialset_id_2 = null " +
						 " where z_productosocio_id =" + prodbp.get_ID();
				DB.executeUpdateEx(action, get_TrxName());
			}
			else{
				boolean infoPautaCero = false;
				String setInfo = "";
				if (prodbp.getZ_PautaComercialSet_ID_Gen() == 0){
					infoPautaCero = true;
					setInfo = " z_pautacomercialset_id_gen = null ";
				}
				if (prodbp.getZ_PautaComercialSet_ID_1() == 0){
					infoPautaCero = true;
					if (!setInfo.equalsIgnoreCase("")) setInfo = setInfo + ", ";
					setInfo = setInfo + " z_pautacomercialset_id_1 = null ";
				}
				if (prodbp.getZ_PautaComercialSet_ID_2() == 0){
					infoPautaCero = true;
					if (!setInfo.equalsIgnoreCase("")) setInfo = setInfo + ", ";
					setInfo = setInfo + " z_pautacomercialset_id_2 = null ";
				}
				if (infoPautaCero){
					action = " update z_productosocio set " + setInfo +
							" where z_productosocio_id =" + prodbp.get_ID();
					DB.executeUpdateEx(action, get_TrxName());
				}
			}

			// Proceso cada organización seleccionada para procesar
			List<MZPreciosProvOrg> pporgs = this.getOrgsSelected();
			for (MZPreciosProvOrg pporg: pporgs){
				// Si no tengo precio para esta organización dentro del producto-socio, la creo en este momento
				MZProductoSocioOrg prodbpOrg = prodbp.getOrg(pporg.getAD_OrgTrx_ID());
				if ((prodbpOrg == null) || (prodbpOrg.get_ID() <= 0)){
					prodbpOrg = new MZProductoSocioOrg(getCtx(), 0, get_TrxName());
					prodbpOrg.setZ_ProductoSocio_ID(prodbp.get_ID());
					prodbpOrg.setAD_OrgTrx_ID(pporg.getAD_OrgTrx_ID());
					prodbpOrg.setDateValidPO(fechaHoy);
					prodbpOrg.setDateValidSO(fechaHoy);
					prodbpOrg.setPriceList(line.getPriceList());
					prodbpOrg.setPriceSO(line.getNewPriceSO());
				}
				else{
					// Si precio de lista compra cambia
					if (prodbpOrg.getPriceList().compareTo(line.getPriceList()) != 0){
						// Actualizo info precios compra para este producto-socio-organizacion
						prodbpOrg.setDateValidPO(fechaHoy);
						prodbpOrg.setPriceList(line.getPriceList());
					}

					// Si precio de lista venta cambia
					if (prodbpOrg.getPriceSO().compareTo(line.getNewPriceSO()) != 0){
						prodbpOrg.setDateValidSO(fechaHoy);
						prodbpOrg.setPriceSO(line.getNewPriceSO());
					}
				}
				prodbpOrg.setM_PriceList_ID(plCompra.get_ID());
				prodbpOrg.setM_PriceList_Version_ID(plVersionCompra.get_ID());
				prodbpOrg.setM_PriceList_ID_SO(plVenta.get_ID());
				prodbpOrg.setM_PriceList_Version_ID_SO(plVersionVenta.get_ID());
				prodbpOrg.setC_Currency_ID(plCompra.getC_Currency_ID());
				prodbpOrg.setC_Currency_ID_SO(this.getC_Currency_ID_SO());
				prodbpOrg.setPricePO(line.getPricePO());
				prodbpOrg.setPricePOMargin(line.getPricePOMargin());
				prodbpOrg.setPriceFinal(line.getPriceFinal());
				prodbpOrg.setPriceFinalMargin(line.getPriceFinalMargin());
				prodbpOrg.saveEx();
			}

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}


	/***
	 * Obtiene y retorna lineas del documento.
	 * Xpande. Created by Gabriel Vila on 6/19/17.
	 * @return
	 */
	public List<MZPreciosProvLin> getLines() {

		String whereClause = X_Z_PreciosProvLin.COLUMNNAME_Z_PreciosProvCab_ID + " =" + this.get_ID();

		List<MZPreciosProvLin> lines = new Query(getCtx(), I_Z_PreciosProvLin.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}


	/***
	 * Obtiene y retorna lineas a clasificar sus productos, según parametros recibidos.
	 * Xpande. Created by Gabriel Vila on 6/23/17.
	 * @param newProducts
	 * @param selectAll
	 * @return
	 */
	public List<MZPreciosProvLin> getLinesToClassified(boolean newProducts, boolean selectAll) {

		String whereClause = X_Z_PreciosProvLin.COLUMNNAME_Z_PreciosProvCab_ID + " =" + this.get_ID();

		if (newProducts){
			whereClause += " AND " + X_Z_PreciosProvLin.COLUMNNAME_IsNew + " ='Y' ";
		}
		else{
			whereClause += " AND " + X_Z_PreciosProvLin.COLUMNNAME_IsNew + " ='N' ";
		}

		if (!selectAll){
			whereClause += " AND " + X_Z_PreciosProvLin.COLUMNNAME_IsClassified + " ='Y' ";
		}

		List<MZPreciosProvLin> lines = new Query(getCtx(), I_Z_PreciosProvLin.Table_Name, whereClause, get_TrxName()).list();

		return lines;
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
      StringBuffer sb = new StringBuffer ("MZPreciosProvCab[")
        .append(getSummary()).append("]");
      return sb.toString();
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


	/***
	 * Ejecuta proceso de carga de productos según modalidad seleccionada de proceso: archivo, linea o manual.
	 * Xpande. Created by Gabriel Vila on 6/13/17.
	 */
	public void execute() {

		try{

			// Elimino información previa que pudiera existir, en caso que el documento no haya sido ejecutado
			if (!this.isExecuted()){
				this.deleteData();
			}

			// Si la modalidad de proceso es mediante Archivo de Interface
			if (this.getModalidadPreciosProv().equalsIgnoreCase(X_Z_PreciosProvCab.MODALIDADPRECIOSPROV_ARCHIVODECARGA)){

				// Si documento ya fue ejecutado
				if (this.isExecuted()){

					// Si tengo inconsistencias en líneas de archivo ya leídas
					if (this.isHaveErrors()){
						// Reproceso lineas incosistentes y no omitidas
						this.setDataFromFile(false);
					}
				}
				else{   // Documento no ejecutado aún
					// Leo archivo con formato de importación
					this.getDataFromFile();

					// Seteo tabla de lineas de este documento (productos) a partir de las lineas leídas del arhivo
					this.setDataFromFile(true);
				}

			}

			// Si la modalidad de proceso es mediante la carga de productos de una linea del proveedor
			else if (this.getModalidadPreciosProv().equalsIgnoreCase(X_Z_PreciosProvCab.MODALIDADPRECIOSPROV_LINEADEPRODUCTOS)){

				// Seteo tabla de lineas de este documento (productos existentes) con los productos contenidos en la
				// linea de productos seleccionada.
				this.setDateFromLineaProductoSocio();
			}

			// Marco este documento como ejecutado, para que no se permita modificar información en la ventana
			this.setIsExecuted(true);
			this.saveEx();

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}


	/***
	 * Actualiza información a partir de datos leídos desde linea de productos existente.
	 * Xpande. Created by Gabriel Vila on 6/23/17.
	 */
	private void setDateFromLineaProductoSocio() {

		try{

			// Otengo lista de modelos de asociación productos-socio para linea y socio seleccionados.
			List<MZProductoSocio> productoSocios = MZProductoSocio.getByBPartnerLineaPriceList(getCtx(), this.getC_BPartner_ID(),
					this.getZ_LineaProductoSocio_ID(), this.getM_PriceList_ID(), get_TrxName());
			if ((productoSocios == null) || (productoSocios.size() <= 0)){
				throw new AdempiereException("No se obtuvieron Productos para la Linea, Socio de Negocios y Lista de Precios de Compra seleccionados.");
			}

			// Instancio modelo de lista de precios de compra y versión vigente de la misma
			MPriceList plCompra = (MPriceList) this.getM_PriceList();
			MPriceListVersion plVersionCompra = (MPriceListVersion) this.getM_PriceList_Version();

			// Instancio modelo  lista de precios de venta y versión de la misma a procesar
			MPriceList plVenta = new MPriceList(getCtx(), this.getM_PriceList_ID_SO(), get_TrxName());
			MPriceListVersion plVersionVenta = new MPriceListVersion(getCtx(), this.getM_PriceList_Version_ID_SO(), get_TrxName());

			// Recorro y proceso lista, para obtener productos a cargar
			for (MZProductoSocio productoSocio: productoSocios){

				// Producto a considerar
				MProduct prod = (MProduct) productoSocio.getM_Product();

				// Precio de lista compra actual tomado desde la propia lista
				BigDecimal priceListPO = null;
				MProductPrice productPrice = MProductPrice.get(getCtx(), plVersionCompra.get_ID(), prod.get_ID(), get_TrxName());

				// Si no encontre producto en la lista, lo tomo del modelo producto-socio
				if (productPrice != null){
					priceListPO = productPrice.getPriceList();
				}
				else{
					priceListPO = productoSocio.getPriceList();
				}

				if ((priceListPO == null) || (priceListPO.compareTo(Env.ZERO) <= 0)){
					throw new AdempiereException("No se obtuvo Precio de Lista Compra para el producto: " + prod.getValue() + " - " + prod.getName());
				}

				// Precio de lista venta actual tomado desde la propia lista
				BigDecimal priceListSO = null;
				MProductPrice productPriceSO = MProductPrice.get(getCtx(), plVersionVenta.get_ID(), prod.get_ID(), get_TrxName());

				// Si no encontre producto en la lista, lo tomo del modelo producto-socio
				if (productPriceSO != null){
					priceListSO = productPriceSO.getPriceList();
				}
				else{
					priceListSO = productoSocio.getPriceSO();
				}

				if ((priceListSO == null) || (priceListSO.compareTo(Env.ZERO) <= 0)){
					throw new AdempiereException("No se obtuvo Precio de Lista Venta para el producto: " + prod.getValue() + " - " + prod.getName());
				}

				MZPreciosProvLin plinea = new MZPreciosProvLin(getCtx(), 0, get_TrxName());
				plinea.setZ_PreciosProvCab_ID(this.get_ID());
				plinea.setC_Currency_ID(this.getC_Currency_ID());
				plinea.setC_Currency_ID_SO(this.getC_Currency_ID_SO());
				plinea.setM_Product_ID(prod.get_ID());
				plinea.setInternalCode(prod.getValue());
				plinea.setName(prod.getName());
				plinea.setDescription(prod.getDescription());
				plinea.setIsNew(false);

				// Ultimo código de barras asociado al producto (en caso de tenerlo)
				MZProductoUPC pupc = MZProductoUPC.getByProduct(getCtx(), prod.get_ID(), get_TrxName());
				if ((pupc != null) && (pupc.get_ID() > 0)){
					plinea.setUPC(pupc.getUPC());
				}
				if (productoSocio.getVendorProductNo() != null){
					plinea.setVendorProductNo(productoSocio.getVendorProductNo());
				}

				plinea.setZ_ProductoSeccion_ID(prod.get_ValueAsInt(X_Z_ProductoSeccion.COLUMNNAME_Z_ProductoSeccion_ID));
				plinea.setZ_ProductoRubro_ID(prod.get_ValueAsInt(X_Z_ProductoRubro.COLUMNNAME_Z_ProductoRubro_ID));
				if (prod.get_ValueAsInt(X_Z_ProductoFamilia.COLUMNNAME_Z_ProductoFamilia_ID) > 0){
					plinea.setZ_ProductoFamilia_ID(prod.get_ValueAsInt(X_Z_ProductoFamilia.COLUMNNAME_Z_ProductoFamilia_ID));
				}
				if (prod.get_ValueAsInt(X_Z_ProductoSubfamilia.COLUMNNAME_Z_ProductoSubfamilia_ID) > 0){
					plinea.setZ_ProductoSubfamilia_ID(prod.get_ValueAsInt(X_Z_ProductoSubfamilia.COLUMNNAME_Z_ProductoSubfamilia_ID));
				}
				plinea.setC_TaxCategory_ID(prod.getC_TaxCategory_ID());
				if (prod.get_ValueAsInt("C_TaxCategory_ID_2") > 0){
					plinea.setC_TaxCategory_ID_2(prod.get_ValueAsInt("C_TaxCategory_ID_2"));
				}
				if (prod.getC_UOM_ID() > 0){
					plinea.setC_UOM_ID(prod.getC_UOM_ID());
				}

				plinea.setOrgDifferentPricePO(productoSocio.isDistinctPricePO());

				// Precios de compra
				plinea.calculatePricesPO(priceListPO, this.getPrecisionPO(), (MZPautaComercial) this.getZ_PautaComercial(), false);

				// Precios de venta
				plinea.calculatePricesSO(priceListSO, this.getPrecisionSO());

				// Calculo y seteo márgenes de linea
				plinea.calculateMargins(this.getRate());

				plinea.saveEx();
			}

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}

	/***
	 * Elimina datos anteriores de tablas resultado del proceso de obtener datos.
	 * Xpande. Created by Gabriel Vila on 6/13/17.
	 */
	private void deleteData() {

		String action = "";

		try{

			action = " delete from z_preciosprovlin cascade where z_preciosprovcab_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());

			action = " delete from z_preciosprovarchivo cascade where z_preciosprovcab_id =" + this.get_ID();
			DB.executeUpdateEx(action, get_TrxName());
		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}

	/***
	 * Obtiene información desde archivo de interface.
	 * Xpande. Created by Gabriel Vila on 6/13/17.
	 */
	private void getDataFromFile() {

		FileReader fReader = null;
		BufferedReader bReader = null;

		String lineaArchivo = null;
		String mensaje = "";
		String action = "";

		try{

			// Formato de importación de archivo de interface de precios de proveedor
			ImpFormat formatoImpArchivo = ImpFormat.load("Retail_Precios_Proveedor");

			// Abro archivo
			File archivoPazos = new File(this.getFileName());
			fReader = new FileReader(archivoPazos);
			bReader = new BufferedReader(fReader);

			int contLineas = 0;
			int zPreciosProvArchivoID = 0;

			// Leo lineas del archivo
			lineaArchivo = bReader.readLine();

			while (lineaArchivo != null) {

				lineaArchivo = lineaArchivo.replace("'", "");
				contLineas++;

				zPreciosProvArchivoID = formatoImpArchivo.updateDB(lineaArchivo, getCtx(), get_TrxName());

				if (zPreciosProvArchivoID <= 0){
					mensaje = "Error al procesar linea " + contLineas + " : " + lineaArchivo;
					throw new AdempiereException(mensaje);
				}
				else{
					// Seteo atributos de linea procesada en tabla
					action = " update " + I_Z_PreciosProvArchivo.Table_Name +
							" set " + X_Z_PreciosProvArchivo.COLUMNNAME_Z_PreciosProvCab_ID + " = " + this.get_ID() + ", " +
							" LineNumber =" + contLineas + ", " +
							" FileLineText ='" + lineaArchivo + "' " +
							" where " + X_Z_PreciosProvArchivo.COLUMNNAME_Z_PreciosProvArchivo_ID + " = " + zPreciosProvArchivoID;
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

	/***
	 * Actualiza información a partir de datos leídos desde archivo de interface
	 * Xpande. Created by Gabriel Vila on 6/13/17.
	 * @param allLines : true si considera todas las lineas, false si considera solamente aquellas lineas con inconsistencias y
	 *                 que no estan marcadas para omitir.
	 */
	private void setDataFromFile(boolean allLines) {

		HashMap<String, Integer> hashUPC = new HashMap<String, Integer>();
		HashMap<String, Integer> hashValue = new HashMap<String, Integer>();
		String whereClause ="";

		try{

			// Instancio modelo de lista de precios de compra y versión vigente de la misma, en caso de tener una.
			MPriceList plCompra = null;
			MPriceListVersion plVersionCompra = null;
			if (this.getM_PriceList_ID() > 0){
				plCompra = (MPriceList) this.getM_PriceList();
				plVersionCompra = (MPriceListVersion) this.getM_PriceList_Version();
			}

			// Instancio modelo  lista de precios de venta y versión de la misma a procesar
			MPriceList plVenta = new MPriceList(getCtx(), this.getM_PriceList_ID_SO(), get_TrxName());
			MPriceListVersion plVersionVenta = new MPriceListVersion(getCtx(), this.getM_PriceList_Version_ID_SO(), get_TrxName());

			// Obtengo lineas leídas del archivo de interface segun flag recibido
			List<MZPreciosProvArchivo> lineasArchivo = null;
			if (allLines){
				lineasArchivo = this.getLineasArchivo();
			}
			else{
				lineasArchivo = this.getLineasArchivoNoConfNoOmitir();
			}

			boolean hayIncosistencias = false;

			// Recorro y proceso lineas
			for (MZPreciosProvArchivo lineaArchivo: lineasArchivo) {

				MZPreciosProvLin plinea = new MZPreciosProvLin(getCtx(), 0, get_TrxName());
				plinea.setZ_PreciosProvCab_ID(this.get_ID());
				plinea.setC_Currency_ID(this.getC_Currency_ID());
				plinea.setC_Currency_ID_SO(this.getC_Currency_ID_SO());
				lineaArchivo.setIsConfirmed(false);

				MProduct prod = null;

				// Verifico si producto de esta linea, es nuevo o ya existe en el sistema.
				// Primero busco producto por código de barras
				if (lineaArchivo.getUPC() != null){
					if (!lineaArchivo.getUPC().trim().equalsIgnoreCase("")){

						// Codigo de barras solo con carácteres numéricos
						String upc = lineaArchivo.getUPC().trim().replaceAll("[^0-9]", "");

						// Controlo código de barra repetido en otra linea del mismo archivo
						if (hashUPC.containsKey(upc)){
							hayIncosistencias = true;
							lineaArchivo.setIsConfirmed(false);
							lineaArchivo.setErrorMsg("Código de barras repetido en lineas de archivo : " +
									lineaArchivo.getLineNumber() + " y " + hashUPC.get(upc).intValue());
							lineaArchivo.saveEx();
							continue;
						}

						// Si estoy reprocesando lineas inconsistentes, controlo código de barras duplicado
						// contra lineas ya procesadas
						if (!allLines){
							MZPreciosProvArchivo lineaConfirmada = MZPreciosProvArchivo.getConfirmedByUPC(getCtx(), this.get_ID(), upc, get_TrxName());
							if ((lineaConfirmada != null) && (lineaConfirmada.get_ID() > 0)){
								hayIncosistencias = true;
								lineaArchivo.setIsConfirmed(false);
								lineaArchivo.setErrorMsg("Código de barras repetido en lineas de archivo : " +
										lineaArchivo.getLineNumber() + " y " + lineaConfirmada.getLineNumber());
								lineaArchivo.saveEx();
								continue;
							}
						}

						hashUPC.put(upc, lineaArchivo.getLineNumber());
						plinea.setUPC(upc);

						MZProductoUPC pupc = MZProductoUPC.getByUPC(getCtx(), lineaArchivo.getUPC().trim(), get_TrxName());
						if ((pupc != null) && (pupc.get_ID() > 0)){
							// Tengo producto por codigo de barras
							prod = (MProduct)pupc.getM_Product();
						}
					}
				}

				// Si no tengo producto, busco por codigo interno
				if (prod == null){
					// Codigo interno
					if (lineaArchivo.getValue() != null){
						if (!lineaArchivo.getValue().trim().equalsIgnoreCase("")){

							String valueProd = lineaArchivo.getValue().trim();

							// Controlo código interno repetido en otra linea del mismo archivo
							if (hashValue.containsKey(valueProd)){
								hayIncosistencias = true;
								lineaArchivo.setIsConfirmed(false);
								lineaArchivo.setErrorMsg("Código Interno repetido en lineas de archivo : " +
										lineaArchivo.getLineNumber() + " y " + hashValue.get(valueProd).intValue());
								lineaArchivo.saveEx();
								continue;
							}

							hashValue.put(valueProd, lineaArchivo.getLineNumber());
							plinea.setInternalCode(valueProd);

							whereClause = X_M_Product.COLUMNNAME_Value + " ='" + valueProd + "' ";
							MProduct[] prods = MProduct.get(getCtx(), whereClause, get_TrxName());
							if (prods != null){
								if (prods.length > 0){
									// Tengo producto por codigo interno
									prod = prods[0];
								}
							}
						}
					}
				}

				// Si tengo producto, lo marco en la tabla de lineas
				if ((prod != null) && (prod.get_ID() > 0)){
					plinea.setM_Product_ID(prod.get_ID());
					plinea.setName(prod.getName());
					plinea.setDescription(prod.getDescription());
					plinea.setIsNew(false);
					plinea.setZ_ProductoSeccion_ID(prod.get_ValueAsInt(X_Z_ProductoSeccion.COLUMNNAME_Z_ProductoSeccion_ID));
					plinea.setZ_ProductoRubro_ID(prod.get_ValueAsInt(X_Z_ProductoRubro.COLUMNNAME_Z_ProductoRubro_ID));
					if (prod.get_ValueAsInt(X_Z_ProductoFamilia.COLUMNNAME_Z_ProductoFamilia_ID) > 0){
						plinea.setZ_ProductoFamilia_ID(prod.get_ValueAsInt(X_Z_ProductoFamilia.COLUMNNAME_Z_ProductoFamilia_ID));
					}
					if (prod.get_ValueAsInt(X_Z_ProductoSubfamilia.COLUMNNAME_Z_ProductoSubfamilia_ID) > 0){
						plinea.setZ_ProductoSubfamilia_ID(prod.get_ValueAsInt(X_Z_ProductoSubfamilia.COLUMNNAME_Z_ProductoSubfamilia_ID));
					}
					plinea.setC_TaxCategory_ID(prod.getC_TaxCategory_ID());
					if (prod.get_ValueAsInt("C_TaxCategory_ID_2") > 0){
						plinea.setC_TaxCategory_ID_2(prod.get_ValueAsInt("C_TaxCategory_ID_2"));
					}
					if (prod.getC_UOM_ID() > 0){
						plinea.setC_UOM_ID(prod.getC_UOM_ID());
					}
				}
				else{
					plinea.setIsNew(true);
				}

				// Codigo de producto del proveedor
				if (lineaArchivo.getVendorProductNo() != null){
					if (!lineaArchivo.getVendorProductNo().trim().equalsIgnoreCase("")){
						plinea.setVendorProductNo(lineaArchivo.getVendorProductNo().trim());
					}
				}

				// Valido que se haya ingresado precio de lista
				if ((lineaArchivo.getPriceList() == null) || (lineaArchivo.getPriceList().compareTo(Env.ZERO) <= 0)){
					hayIncosistencias = true;
					lineaArchivo.setIsConfirmed(false);
					lineaArchivo.setErrorMsg("No se indica Precio de Lista en linea de archivo : " + lineaArchivo.getLineNumber());
					lineaArchivo.saveEx();
					continue;
				}

				// Si esta línea se corresponde a un nuevo producto, guardos precios sin buscar en el sistema
				if (plinea.isNew()){

					lineaArchivo.setIsNew(true);

					// Valido que se haya ingresado nombre para el nuevo producto
					if ((lineaArchivo.getName() == null) || (lineaArchivo.getName().trim().equalsIgnoreCase(""))){
						hayIncosistencias = true;
						lineaArchivo.setIsConfirmed(false);
						lineaArchivo.setErrorMsg("No se indica Nombre de Producto en linea de archivo : " + lineaArchivo.getLineNumber());
						lineaArchivo.saveEx();
						continue;
					}
					plinea.setName(lineaArchivo.getName().toUpperCase().trim());
					plinea.setDescription(lineaArchivo.getName().toUpperCase().trim());

					// Precios de compra en nuevo producto
					plinea.setOrgDifferentPricePO(false);
					plinea.calculatePricesPO(lineaArchivo.getPriceList(), this.getPrecisionPO(), (MZPautaComercial) this.getZ_PautaComercial(), false);

					// Precios de venta en nuevo producto
					plinea.setOrgDifferentPriceSO(false);
					plinea.setPriceSO(null);
					plinea.setNewPriceSO(null);
					if (lineaArchivo.getPriceSO() != null){
						plinea.setNewPriceSO(lineaArchivo.getPriceSO().setScale(this.getPrecisionSO(), BigDecimal.ROUND_HALF_UP));
					}

				}
				else{  // El producto ya estaba definido en el sistema

					lineaArchivo.setIsNew(false);

					// Estoy seteando nuevo precios de compra, por lo tanto todas las orgs, seleccionadas
					// en este documento, van a tener los mismos precios de compra para este producto.
					plinea.setOrgDifferentPricePO(false);

					// Precios de compra en producto existente
					plinea.calculatePricesPO(lineaArchivo.getPriceList(), this.getPrecisionPO(), (MZPautaComercial) this.getZ_PautaComercial(), false);

					// Precios de venta en producto existente
					plinea.calculatePricesSO(lineaArchivo.getPriceSO(), this.getPrecisionSO());

					/*
					// Valido que tenga cambios en precio de lista, precio OC y precio de venta.
					// Si estos valores son iguales, no debo considerar esta linea, entonces la marco como inváida,
					// indico motivo, pero la marco para Omitir.
					if ((plCompra != null) && (plCompra.get_ID() > 0)){
						if ((plVersionCompra != null) && (plVersionCompra.get_ID() > 0)){
							MProductPrice productPrice = MProductPrice.get(getCtx(), plVersionCompra.get_ID(), plinea.getM_Product_ID(), get_TrxName());
							if (productPrice != null){
								if (productPrice.getPriceList() != null){
									if (productPrice.getPriceList().compareTo(plinea.getPriceList()) == 0){

									}
								}
							}
						}
					}
					*/

				}

				// Calculo y seteo márgenes de linea
				plinea.calculateMargins(this.getRate());

				plinea.saveEx();
				lineaArchivo.setIsConfirmed(true);
				lineaArchivo.setErrorMsg(null);
				lineaArchivo.saveEx();
			}

			// Marco cabezal con inconsistencias en caso que hubiera
			if (hayIncosistencias){
				this.setHaveErrors(true);
				this.saveEx();
			}
		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}

	/***
	 * Metodo que obtiene y retorna lista de modelos de lineas leídas de archivo de interface de precios de proveedor.
	 * Solo se consideran aquellas lineas con inconsistencias y no marcadas para omitir.
	 * Xpande. Created by Gabriel Vila on 6/13/17.
	 * @return
	 */
	private List<MZPreciosProvArchivo> getLineasArchivoNoConfNoOmitir() {

		String whereClause = X_Z_PreciosProvArchivo.COLUMNNAME_Z_PreciosProvCab_ID + "=" + this.get_ID() +
				" AND " + X_Z_PreciosProvArchivo.COLUMNNAME_IsConfirmed + " ='N'" +
				" AND " + X_Z_PreciosProvArchivo.COLUMNNAME_IsOmitted + " ='N'";

		List<MZPreciosProvArchivo> lines = new Query(getCtx(), I_Z_PreciosProvArchivo.Table_Name, whereClause, get_TrxName()).list();

		return lines;

	}

	/***
	 * Metodo que obtiene y retorna lista de modelos de lineas leídas de archivo de interface de precios de proveedor.
	 * Xpande. Created by Gabriel Vila on 6/13/17.
	 * @return
	 */
	public List<MZPreciosProvArchivo> getLineasArchivo(){

		String whereClause = X_Z_PreciosProvArchivo.COLUMNNAME_Z_PreciosProvCab_ID + "=" + this.get_ID();

		List<MZPreciosProvArchivo> lines = new Query(getCtx(), I_Z_PreciosProvArchivo.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success) {

		if (!success) return false;

		// Si es nuevo registro o modifico linea de productos del socio
		if (newRecord || is_ValueChanged(X_Z_PreciosProvCab.COLUMNNAME_Z_LineaProductoSocio_ID)){
			// Cargo distribuidores del socio de negocio de este documento
			this.setDistribuidores();
		}

		// En caso de nuevo registro
		if (newRecord){

			// Seteo precisión decimal para precios de compra y venta
			this.setPrecisionDecimal();


			// Obtengo tasa de cambio de hoy para compra-venta multimoneda
			if (this.getC_Currency_ID() != this.getC_Currency_ID_SO()){
				Timestamp fechaHoy = TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY);
				BigDecimal rate = MConversionRate.getRate(this.getC_Currency_ID(), this.getC_Currency_ID_SO(), fechaHoy, 0, this.getAD_Client_ID(), 0);
				this.setRate(rate.setScale(3, BigDecimal.ROUND_HALF_UP));
			}

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
					MZPreciosProvOrg pOrg = new MZPreciosProvOrg(getCtx(), 0, get_TrxName());
					pOrg.setZ_PreciosProvCab_ID(this.get_ID());
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
				MZPreciosProvOrg pOrg = new MZPreciosProvOrg(getCtx(), 0, get_TrxName());
				pOrg.setZ_PreciosProvCab_ID(this.get_ID());
				pOrg.setAD_OrgTrx_ID(this.getAD_Org_ID());
				pOrg.saveEx();

				// Marco flag que indica que solo tengo una organización para procesar
				this.setOnlyOneOrg(true);
			}

			this.saveEx();
		}

		return true;
	}


	/***
	 * Setea distribuidores del socio de negocio de este documento.
	 * Xpande. Created by Gabriel Vila on 7/2/17.
	 */
	private void setDistribuidores() {

		try{
			// Si tengo linea de productos (no estoy creando una nueva)
			if (this.getZ_LineaProductoSocio_ID() > 0){
				// Obtengo distribuidores de esta linea de negocio y los seteo en este documento para procesarlos
				MZLineaProductoSocio lineaProductoSocio = (MZLineaProductoSocio) this.getZ_LineaProductoSocio();
				List<MZLineaProductoDistri> distris = lineaProductoSocio.getDistribuidores();
				for (MZLineaProductoDistri lineaProductoDistri: distris){
					MZPreciosProvDistri preciosProvDistri = new MZPreciosProvDistri(getCtx(), 0, get_TrxName());
					preciosProvDistri.setZ_PreciosProvCab_ID(this.get_ID());
					preciosProvDistri.setZ_LineaProductoDistri_ID(lineaProductoDistri.get_ID());
					preciosProvDistri.setC_BPartner_ID(lineaProductoDistri.getC_BPartner_ID());
					preciosProvDistri.setIsManualRecord(false);
					preciosProvDistri.saveEx();
				}
			}
		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
	}


	/***
	 * Setea precisión decimal para precios de compra y venta.
	 * Xpande. Created by Gabriel Vila on 6/17/17.
	 */
	private void setPrecisionDecimal(){

		try{

			// Obtengo precisión decimal para precios según precisión de la lista, o en caso de no tener lista,
			// tomo la precisión de la moneda directamente

			// Precision decimal para precios de compra
			if (this.getM_PriceList_ID() > 0){
				MPriceList pl = (MPriceList)this.getM_PriceList();
				this.setPrecisionPO(pl.getPricePrecision());
			}
			else{
				// Tomo de la moneda
				MCurrency cur = (MCurrency)this.getC_Currency();
				this.setPrecisionPO(cur.getStdPrecision());
			}

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
	 * Obtiene y retorna organizaciones seleccionadas para considerarse en este proceso.
	 * Xpande. Created by Gabriel Vila on 6/20/17.
	 * @return
	 */
	public List<MZPreciosProvOrg> getOrgsSelected() {

		String whereClause = X_Z_PreciosProvOrg.COLUMNNAME_Z_PreciosProvCab_ID + " =" + this.get_ID() +
				" AND " + X_Z_PreciosProvOrg.COLUMNNAME_IsSelected + " ='Y'";
		List<MZPreciosProvOrg> lines = new Query(getCtx(), I_Z_PreciosProvOrg.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}

	/***
	 * Obtiene y retorna distribuidores a considerarse en este proceso.
	 * Xpande. Created by Gabriel Vila on 6/20/17.
	 * @return
	 */
	public List<MZPreciosProvDistri> getDistribuidores() {

		String whereClause = X_Z_PreciosProvDistri.COLUMNNAME_Z_PreciosProvCab_ID + " =" + this.get_ID();

		List<MZPreciosProvDistri> lines = new Query(getCtx(), I_Z_PreciosProvDistri.Table_Name, whereClause, get_TrxName()).list();

		return lines;
	}


	/***
	 * Refresca información de precios de compra de las lineas del documento.
	 * Xpande. Created by Gabriel Vila on 6/26/17.
	 * @return
	 */
	public String refrescarPrecios(){

		String message = null;

		try{

			// Obtengo y recorro lineas de este documento
			List<MZPreciosProvLin> preciosProvLins = this.getLines();
			for (MZPreciosProvLin preciosProvLin: preciosProvLins){

				// Refresco valores de precio de compra de este linea
				preciosProvLin.calculatePricesPO(preciosProvLin.getPriceList(), this.getPrecisionPO(), (MZPautaComercial) this.getZ_PautaComercial(), false);

				// Calculo y seteo márgenes de linea
				preciosProvLin.calculateMargins(this.getRate());

				preciosProvLin.saveEx();
			}

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return message;
	}

}