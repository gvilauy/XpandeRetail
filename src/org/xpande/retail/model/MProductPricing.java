/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.xpande.retail.model;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MDiscountSchema;
import org.compiere.model.MPriceList;
import org.compiere.model.MProduct;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trace;
import org.xpande.retail.utils.ProductPricesInfo;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.logging.Level;

/**
 *  Product Price Calculations
 *
 *  @author Jorg Janke
 *  @version $Id: MProductPricing.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 *  eEvolution author Victor Perez <victor.perez@e-evolution.com>, Created by e-Evolution on 26/02/16.
 */
public class MProductPricing
{

	/**
	 * 	Constructor
	 * @param productId product
	 * 	@param partnerId partner
	 * @param quantity quantity
	 * @param isSOTrx SO or PO
	 */
	
	@Deprecated
	public MProductPricing(int productId, int partnerId, int adOrgTrxID, Timestamp dateDocument,
                           BigDecimal quantity, boolean isSOTrx)
	{
		 this(productId, partnerId, adOrgTrxID, dateDocument, quantity, isSOTrx, null);
	}
	
	
	public MProductPricing(int productId, int partnerId, int adOrgTrxID, Timestamp dateDocument,
                           BigDecimal quantity, boolean isSOTrx, String trxName)
	{
		this.productId = productId;
		this.partnerId = partnerId;
		this.adOrgTrxID = adOrgTrxID;
		this.dateDocument = dateDocument;
		this.trxName = trxName;

		if (quantity != null && Env.ZERO.compareTo(quantity) != 0)
			this.quantity = quantity;
		this.isSOTrx = isSOTrx;
		int thereAreVendorBreakRecords = DB.getSQLValue(trxName,
				"SELECT count(M_Product_ID) FROM M_ProductPriceVendorBreak WHERE M_Product_ID=? AND (BreakValue > 0 OR C_BPartner_ID=?)",
				this.productId, this.partnerId);
		useVendorBreak = thereAreVendorBreakRecords > 0;
	}	//	MProductPricing

	private int 		productId;
	private int 		partnerId;
	private Timestamp	dateDocument;
	private int	adOrgTrxID;
	private BigDecimal 	quantity = Env.ONE;
	private boolean 	isSOTrx = true;
	//
	private int 		priceListId = 0;
	private int 		priceListVersionId = 0;
	private Timestamp 	priceDate;
	/** Precision -1 = no rounding		*/
	private int 		precision = -1;
	
	
	private boolean 	calculated = false;
	private boolean 	vendorBreak = false;
	private boolean 	useVendorBreak;
	private Boolean 	found = null;
	
	private BigDecimal 	priceList = Env.ZERO;
	private BigDecimal 	priceStd = Env.ZERO;
	private BigDecimal 	priceLimit = Env.ZERO;

	private boolean		isCostoHistorico = false;
	private BigDecimal	pricePO = Env.ZERO;
	private BigDecimal	priceFinal = Env.ZERO;

	private int 		currencyId = 0;
	private boolean 	isEnforcePriceLimit = false;
	private int 		uomId = 0;
	private int 		productCategoryId;
	private boolean 	discountSchema = false;
	private boolean 	isTaxIncluded = true;
	private String		trxName = null;

	private boolean tieneOfertaComercial = false;
	private boolean ofertaAplicaDtosPago = true;

	private int forcedPrecision = -1;

	/**	Logger			*/
	protected CLogger	log = CLogger.getCLogger(getClass());


	/***
	 * Fuerzo precision decimal por encima de la precision decimal de la lista de precios que recibe esta clase.
	 * @param forcedPrecision
	 */
	public void setForcedPrecision(int forcedPrecision) {
		this.forcedPrecision = forcedPrecision;
	}


	/**
	 * 	Calculate Price
	 * 	@return true if calculated
	 */
	public boolean calculatePrice ()
	{
		if (productId == 0
			|| (found != null && !found.booleanValue()))	//	previously not found
			return false;

		if (useVendorBreak) {
			//	Price List Version known - vendor break
			if (!calculated) {
				calculated = calculatePriceBasedOnPriceListVersionAndProductBreak();
				if (calculated)
					vendorBreak = true;
			}
			//	Price List known - vendor break
			if (!calculated) {
				calculated = calculatePriceBasedOnPriceListAndProductBreak();
				if (calculated)
					vendorBreak = true;
			}
			if (!calculated) {
				calculated = calculatePriceBasedOnBasedPriceListAndProductBreak();
				if (calculated)
					vendorBreak = true;
			}
		}

		// Xpande. Gabriel Vila. 15/06/2017.
		// Primero busco precio por organización-producto-socio

		if (!calculated)
			calculated = calculatePriceBasedOnOrg();

		// Fin Xpande

		//	Price List Version known
		if (!calculated)
			calculated = calculatePriceBasedOnPriceListVersion();
		//	Price List known
		if (!calculated)
			calculated = calculatePriceBasedOnPriceList();
		//	Base Price List used
		if (!calculated)
			calculated = calculatePriceBasedOnBasePriceList();
		//	Set UOM, Prod.Category
		if (!calculated)
			setBaseInfo();
		//	User based Discount
		if (calculated && !vendorBreak)
			calculateDiscount();
		setPrecision();		//	from Price List
		//
		found = new Boolean (calculated);
		return calculated;
	}	//	calculatePrice

	private boolean calculatePriceBasedOnOrg() {

		boolean calculated = false;

		try{

			this.isCostoHistorico = false;

			if (this.adOrgTrxID <= 0) return false;

			MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(Env.getCtx(), partnerId, productId, null);
			if ((productoSocio != null) && (productoSocio.get_ID() > 0)){



				MProduct product = (MProduct)productoSocio.getM_Product();

				this.currencyId = productoSocio.getC_Currency_ID();
				this.productCategoryId = product.getM_Product_Category_ID();

				// Si la fecha del documento es menor a la fecha de vigencia de costos del modelo producto-socio
				if (this.dateDocument.before(productoSocio.getDateValidPO())){

					// Veo si tengo precios en histórico de costos para esta fecha-organización-socio-producto-moneda
					MZHistCostoProd histCostoProd = MZHistCostoProd.getByDateOrgProdPartner(Env.getCtx(), this.dateDocument, this.adOrgTrxID,
																				this.partnerId, this.productId, this.currencyId, this.trxName);
					if ((histCostoProd != null) && (histCostoProd.get_ID() > 0)){

						this.tieneOfertaComercial = false;
						this.pricePO = histCostoProd.getPriceList();
						this.priceStd = histCostoProd.getPriceList();
						this.priceList = histCostoProd.getPriceList();
						this.priceLimit = histCostoProd.getPriceList();
						this.uomId = product.getC_UOM_ID();
						this.isEnforcePriceLimit = false;
						this.isTaxIncluded = true;

						this.isCostoHistorico = true;

						return true;
					}
				}

				// Verifico si tengo oferta comercial, en cuyo caso dejo constancia y obtengo precio de oferta y su correspondiente moneda
				MZProductoSocioOferta socioOferta = MZProductoSocioOferta.getByProductBPDate(Env.getCtx(), productoSocio.get_ID(), this.dateDocument, null);
				if ((socioOferta != null) && (socioOferta.get_ID() > 0)){
					// Tengo oferta, tomo el precio de la oferta
					this.tieneOfertaComercial = true;
					MZOfertaVentaLinBP ofertaVentaLinBP = (MZOfertaVentaLinBP) socioOferta.getZ_OfertaVentaLinBP();
					if ((ofertaVentaLinBP != null) && (ofertaVentaLinBP.get_ID() > 0)){
						this.ofertaAplicaDtosPago = ofertaVentaLinBP.isAplicanDtosPago();
						priceStd = ofertaVentaLinBP.getNewPricePO();
						priceList = ofertaVentaLinBP.getNewPricePO();
						priceLimit = ofertaVentaLinBP.getNewPricePO();
						uomId = product.getC_UOM_ID();
						currencyId = ofertaVentaLinBP.getC_Currency_ID();
						productCategoryId = product.getM_Product_Category_ID();
						isEnforcePriceLimit = false;
						isTaxIncluded = true;
						calculated = true;
					}
				}
				else{
					// No tengo oferta, tomo el precio desde organizacion
					this.tieneOfertaComercial = false;

					MZProductoSocioOrg productoSocioOrg = productoSocio.getOrg(this.adOrgTrxID);
					if ((productoSocioOrg != null) && (productoSocioOrg.get_ID() > 0)){
						priceStd = productoSocioOrg.getPriceList();
						priceList = productoSocioOrg.getPriceList();
						priceLimit = productoSocioOrg.getPriceList();
						uomId = product.getC_UOM_ID();
						currencyId = productoSocio.getC_Currency_ID();
						productCategoryId = product.getM_Product_Category_ID();
						isEnforcePriceLimit = false;
						isTaxIncluded = true;
						calculated = true;
					}
				}
			}

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return calculated;
	}

	/**
	 * 	Calculate Price based on Price List Version
	 * 	@return true if calculated
	 */
	private boolean calculatePriceBasedOnPriceListVersion()
	{
		if (productId == 0 || priceListVersionId == 0)
			return false;
		//
		String sql = "SELECT bomPriceStd(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceStd,"	//	1
			+ " bomPriceList(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceList,"		//	2
			+ " bomPriceLimit(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceLimit,"	//	3
			+ " p.C_UOM_ID,pv.ValidFrom,pl.C_Currency_ID,p.M_Product_Category_ID,"	//	4..7
			+ " pl.EnforcePriceLimit, pl.IsTaxIncluded "	// 8..9
			+ "FROM M_Product p"
			+ " INNER JOIN M_ProductPrice pp ON (p.M_Product_ID=pp.M_Product_ID)"
			+ " INNER JOIN  M_PriceList_Version pv ON (pp.M_PriceList_Version_ID=pv.M_PriceList_Version_ID)"
			+ " INNER JOIN M_Pricelist pl ON (pv.M_PriceList_ID=pl.M_PriceList_ID) "
			+ "WHERE pv.IsActive='Y'"
			+ " AND pp.IsActive='Y'"
			+ " AND p.M_Product_ID=?"				//	#1
			+ " AND pv.M_PriceList_Version_ID=?";	//	#2
		calculated = false;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try
		{
			statement = DB.prepareStatement(sql, trxName);
			statement.setInt(1, productId);
			statement.setInt(2, priceListVersionId);
			rs = statement.executeQuery();
			if (rs.next())
			{
				//	Prices
				priceStd = rs.getBigDecimal(1);
				if (rs.wasNull())
					priceStd = Env.ZERO;
				priceList = rs.getBigDecimal(2);
				if (rs.wasNull())
					priceList = Env.ZERO;
				priceLimit = rs.getBigDecimal(3);
				if (rs.wasNull())
					priceLimit = Env.ZERO;
				//
				uomId = rs.getInt(4);
				currencyId = rs.getInt(6);
				productCategoryId = rs.getInt(7);
				isEnforcePriceLimit = "Y".equals(rs.getString(8));
				isTaxIncluded = "Y".equals(rs.getString(9));
				//
				log.fine("M_PriceList_Version_ID=" + priceListVersionId + " - " + priceStd);
				calculated = true;
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			calculated = false;
		}
		finally
		{
			DB.close(rs, statement);
			rs = null;
			statement = null;
		}
		return calculated;
	}	//	calculatePriceBasedOnPriceListVersion

	/**
	 * 	Calculate Price based on Price List
	 * 	@return true if calculated
	 */
	private boolean calculatePriceBasedOnPriceList()
	{
		if (productId == 0)
			return false;
		/** **/
		if (priceListId == 0)
		{
			log.log(Level.SEVERE, "No PriceList");
			Trace.printStack();
			return false;
		}

		//	Get Prices for Price List
		String sql = "SELECT bomPriceStd(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceStd,"	//	1
			+ " bomPriceList(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceList,"		//	2
			+ " bomPriceLimit(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceLimit,"	//	3
			+ " p.C_UOM_ID,pv.ValidFrom,pl.C_Currency_ID,p.M_Product_Category_ID,pl.EnforcePriceLimit "	// 4..8
			+ "FROM M_Product p"
			+ " INNER JOIN M_ProductPrice pp ON (p.M_Product_ID=pp.M_Product_ID)"
			+ " INNER JOIN  M_PriceList_Version pv ON (pp.M_PriceList_Version_ID=pv.M_PriceList_Version_ID)"
			+ " INNER JOIN M_Pricelist pl ON (pv.M_PriceList_ID=pl.M_PriceList_ID) "
			+ "WHERE pv.IsActive='Y'"
			+ " AND pp.IsActive='Y'"
			+ " AND p.M_Product_ID=?"				//	#1
			+ " AND pv.M_PriceList_ID=?"			//	#2
			+ " ORDER BY pv.ValidFrom DESC";
		calculated = false;
		if (priceDate == null)
			priceDate = new Timestamp (System.currentTimeMillis());
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = DB.prepareStatement(sql, trxName);
			statement.setInt(1, productId);
			statement.setInt(2, priceListId);
			resultSet = statement.executeQuery();
			while (!calculated && resultSet.next())
			{
				Timestamp priceListDate = resultSet.getTimestamp(5);
				//	we have the price list
				//	if order date is after or equal PriceList validFrom
				if (priceListDate == null || !priceDate.before(priceListDate))
				{
					//	Prices
					priceStd = resultSet.getBigDecimal (1);
					if (resultSet.wasNull ())
						priceStd = Env.ZERO;
					priceList = resultSet.getBigDecimal (2);
					if (resultSet.wasNull ())
						priceList = Env.ZERO;
					priceLimit = resultSet.getBigDecimal (3);
					if (resultSet.wasNull ())
						priceLimit = Env.ZERO;

					uomId = resultSet.getInt (4);
					currencyId = resultSet.getInt (6);
					productCategoryId = resultSet.getInt(7);
					isEnforcePriceLimit = "Y".equals(resultSet.getString(8));

					log.fine("M_PriceList_ID=" + priceListId
						+ "(" + priceListDate + ")" + " - " + priceStd);
					calculated = true;
					break;
				}
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			calculated = false;
		}
		finally
		{
			DB.close(resultSet, statement);
			resultSet = null;
			statement = null;
		}
		if (!calculated)
			log.finer("Not found (PL)");
		return calculated;
	}	//	calculatePriceBasedOnPriceList

	/**
	 * 	Calculate Price based on Base Price List
	 * 	@return true if calculated
	 */
	private boolean calculatePriceBasedOnBasePriceList()
	{
		if (productId == 0 || priceListId == 0)
			return false;
		//
		String sql = "SELECT bomPriceStd(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceStd,"	//	1
			+ " bomPriceList(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceList,"		//	2
			+ " bomPriceLimit(p.M_Product_ID,pv.M_PriceList_Version_ID) AS PriceLimit,"	//	3
			+ " p.C_UOM_ID,pv.ValidFrom,pl.C_Currency_ID,p.M_Product_Category_ID,"	//	4..7
			+ " pl.EnforcePriceLimit, pl.IsTaxIncluded "	// 8..9
			+ "FROM M_Product p"
			+ " INNER JOIN M_ProductPrice pp ON (p.M_Product_ID=pp.M_Product_ID)"
			+ " INNER JOIN M_PriceList_Version pv ON (pp.M_PriceList_Version_ID=pv.M_PriceList_Version_ID)"
			+ " INNER JOIN M_PriceList pl ON (pv.M_PriceList_ID=pl.M_PriceList_ID)"
			+ "WHERE pv.IsActive='Y'"
			+ " AND pp.IsActive='Y'"
			+ " AND p.M_Product_ID=?"				//	#1
			+ " AND EXISTS (SELECT 1 FROM M_PriceList_Version plv WHERE plv.IsActive='Y' "
			+ " AND plv.M_PriceList_ID=? AND pp.M_PriceList_Version_ID=plv.M_PriceList_Version_Base_ID)"
			+ " ORDER BY pv.ValidFrom DESC";
		calculated = false;
		if (priceDate == null)
			priceDate = new Timestamp (System.currentTimeMillis());
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = DB.prepareStatement(sql, trxName);
			statement.setInt(1, productId);
			statement.setInt(2, priceListId);
			resultSet = statement.executeQuery();
			while (!calculated && resultSet.next())
			{
				Timestamp plDate = resultSet.getTimestamp(5);
				//	we have the price list
				//	if order date is after or equal PriceList validFrom
				if (plDate == null || !priceDate.before(plDate))
				{
					//	Prices
					priceStd = resultSet.getBigDecimal (1);
					if (resultSet.wasNull ())
						priceStd = Env.ZERO;
					priceList = resultSet.getBigDecimal (2);
					if (resultSet.wasNull ())
						priceList = Env.ZERO;
					priceLimit = resultSet.getBigDecimal (3);
					if (resultSet.wasNull ())
						priceLimit = Env.ZERO;
						//
					uomId = resultSet.getInt (4);
					currencyId = resultSet.getInt (6);
					productCategoryId = resultSet.getInt(7);
					isEnforcePriceLimit = "Y".equals(resultSet.getString(8));
					isTaxIncluded = "Y".equals(resultSet.getString(9));
					//
					log.fine("M_PriceList_ID=" + priceListId
						+ "(" + plDate + ")" + " - " + priceStd);
					calculated = true;
					break;
				}
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
			calculated = false;
		}
		finally
		{
			DB.close(resultSet, statement);
			resultSet = null;
			statement = null;
		}
		if (!calculated)
			log.finer("Not found (BPL)");
		return calculated;
	}	//	calculatePriceBasedOnBasePriceList

	/**
	 * 	Calculate Price based on Price List Version and Vendor Break
	 * 	@return true if calculated
	 */
	private boolean calculatePriceBasedOnPriceListVersionAndProductBreak()
	{
		if (productId == 0 || priceListVersionId == 0 || !useVendorBreak)
			return false;
		//
		StringBuilder sql = new StringBuilder("SELECT pp.PriceStd,");	//	1
			sql.append(" pp.PriceList,")		//	2
			.append(" pp.PriceLimit,")	//	3
			.append( " p.C_UOM_ID,pv.ValidFrom,pl.C_Currency_ID,p.M_Product_Category_ID,")	//	4..7
			.append( " pl.EnforcePriceLimit, pl.IsTaxIncluded ")	// 8..9
			.append( "FROM M_Product p")
			.append( " INNER JOIN M_ProductPriceVendorBreak pp ON (p.M_Product_ID=pp.M_Product_ID)")
			.append( " INNER JOIN  M_PriceList_Version pv ON (pp.M_PriceList_Version_ID=pv.M_PriceList_Version_ID)")
			.append( " INNER JOIN M_PriceList pl ON (pv.M_PriceList_ID=pl.M_PriceList_ID) ")
			.append( "WHERE pv.IsActive='Y'")
			.append( " AND pp.IsActive='Y'")
			.append( " AND p.M_Product_ID=?")		//	#1
			.append( " AND pv.M_PriceList_Version_ID=? AND (pp.C_BPartner_ID=? OR pp.C_BPartner_ID IS NULL) ") //	#2
			.append(" AND ?>=pp.BreakValue")				//  #4
			.append(" ORDER BY pp.BreakValue DESC, pp.C_BPartner_ID ASC");
		calculated = false;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = DB.prepareStatement(sql.toString(), trxName);
			int count = 0;
			count ++;
			statement.setInt(count, productId);
			count ++;
			statement.setInt(count, priceListVersionId);
			count ++;
			statement.setInt(count, partnerId);
			count ++;
			statement.setBigDecimal(count , quantity);
			resultSet = statement.executeQuery();
			if (resultSet.next())
			{
				//	Prices
				priceStd = resultSet.getBigDecimal(1);
				if (resultSet.wasNull())
					priceStd = Env.ZERO;
				priceList = resultSet.getBigDecimal(2);
				if (resultSet.wasNull())
					priceList = Env.ZERO;
				priceLimit = resultSet.getBigDecimal(3);
				if (resultSet.wasNull())
					priceLimit = Env.ZERO;
				//
				uomId = resultSet.getInt(4);
				currencyId = resultSet.getInt(6);
				productCategoryId = resultSet.getInt(7);
				isEnforcePriceLimit = "Y".equals(resultSet.getString(8));
				isTaxIncluded = "Y".equals(resultSet.getString(9));
				//
				log.fine("M_PriceList_Version_ID=" + priceListVersionId + " - " + priceStd);
				calculated = true;
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
			calculated = false;
		}
		finally
		{
			DB.close(resultSet, statement);
			resultSet = null;
			statement = null;
		}
		return calculated;
	}	//	calculatePriceBasedOnPriceListVersionAndVendorBreak

	/**
	 * 	Calculate Price based on Price List and Vendor break
	 * 	@return true if calculated
	 */
	private boolean calculatePriceBasedOnPriceListAndProductBreak()
	{
		if (productId == 0 || !useVendorBreak)
			return false;

		/** **/
		if (priceListId == 0)
		{
			log.log(Level.SEVERE, "No PriceList");
			Trace.printStack();
			return false;
		}

		//	Get Prices for Price List
		StringBuilder sql = new StringBuilder("SELECT pp.PriceStd,");	//	1
		sql.append(" pp.PriceList,")		//	2
		.append(" pp.PriceLimit,")	//	3
		.append(" p.C_UOM_ID,pv.ValidFrom,pl.C_Currency_ID,p.M_Product_Category_ID,pl.EnforcePriceLimit ")	// 4..8
		.append(" FROM M_Product p")
		.append(" INNER JOIN M_ProductPriceVendorBreak pp ON (p.M_Product_ID=pp.M_Product_ID)")
		.append(" INNER JOIN  M_PriceList_Version pv ON (pp.M_PriceList_Version_ID=pv.M_PriceList_Version_ID)")
		.append(" INNER JOIN M_PriceList pl ON (pv.M_PriceList_ID=pl.M_PriceList_ID) ")
		.append(" WHERE pv.IsActive='Y'")
		.append(" AND pp.IsActive='Y'")
		.append(" AND p.M_Product_ID=?")				//	#1
		.append(" AND pv.M_PriceList_ID=? AND (pp.C_BPartner_ID=? OR pp.C_BPartner_ID IS NULL) ")			//	#2
		.append(" AND ?>=pp.BreakValue")				//  #4
		.append(" ORDER BY pv.ValidFrom DESC, pp.BreakValue DESC, pp.C_BPartner_ID ASC");
		calculated = false;
		if (priceDate == null)
			priceDate = new Timestamp (System.currentTimeMillis());
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = DB.prepareStatement(sql.toString(), trxName);
			int count = 0;
			count ++;
			statement.setInt(count, productId);
			count ++;
			statement.setInt(count, priceListId);
			count++;
			statement.setInt( count , partnerId);
			count++;
			statement.setBigDecimal(count, quantity);
			resultSet = statement.executeQuery();
			while (!calculated && resultSet.next())
			{
				Timestamp plDate = resultSet.getTimestamp(5);
				//	we have the price list
				//	if order date is after or equal PriceList validFrom
				if (plDate == null || !priceDate.before(plDate))
				{
					//	Prices
					priceStd = resultSet.getBigDecimal (1);
					if (resultSet.wasNull ())
						priceStd = Env.ZERO;
					priceList = resultSet.getBigDecimal (2);
					if (resultSet.wasNull ())
						priceList = Env.ZERO;
					priceLimit = resultSet.getBigDecimal (3);
					if (resultSet.wasNull ())
						priceLimit = Env.ZERO;
						//
					uomId = resultSet.getInt (4);
					currencyId = resultSet.getInt (6);
					productCategoryId = resultSet.getInt(7);
					isEnforcePriceLimit = "Y".equals(resultSet.getString(8));
					//
					log.fine("M_PriceList_ID=" + priceListId
						+ "(" + plDate + ")" + " - " + priceStd);
					calculated = true;
					break;
				}
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
			calculated = false;
		}
		finally
		{
			DB.close(resultSet, statement);
			resultSet = null;
			statement = null;
		}
		if (!calculated)
			log.finer("Not found (PL)");
		return calculated;
	}	//	calculatePriceBasedOnPriceListAndVendorBreak

	/**
	* 	Calculate Price based on Base Price List and Vendor Break
	* 	@return true if calculated
	*/
	private boolean calculatePriceBasedOnBasedPriceListAndProductBreak()
	{
		if (productId == 0 || priceListId == 0 || !useVendorBreak)
			return false;
		//
		StringBuilder sql = new StringBuilder("SELECT pp.PriceStd,");	//	1
				sql.append(" pp.PriceList,")		//	2
				.append( " pp.PriceLimit,")	//	3
				.append( " p.C_UOM_ID,pv.ValidFrom,pl.C_Currency_ID,p.M_Product_Category_ID,")	//	4..7
				.append( " pl.EnforcePriceLimit, pl.IsTaxIncluded ")	// 8..9
				.append( " FROM M_Product p")
				.append( " INNER JOIN M_ProductPriceVendorBreak pp ON (p.M_Product_ID=pp.M_Product_ID)")
				.append( " INNER JOIN M_PriceList_Version pv ON (pp.M_PriceList_Version_ID=pv.M_PriceList_Version_ID)")
				.append( " INNER JOIN M_PriceList pl ON (pv.M_PriceList_ID=pl.M_PriceList_ID)")
				.append( " WHERE pv.IsActive='Y'")
				.append( " AND pp.IsActive='Y'")
				.append( " AND p.M_Product_ID=?")				//	#1
				.append( " AND EXISTS (SELECT 1 FROM M_PriceList_Version plv WHERE plv.IsActive='Y' ")
				.append( " AND plv.M_PriceList_ID=? AND pp.M_PriceList_Version_ID=plv.M_PriceList_Version_Base_ID) AND (pp.C_BPartner_ID=? OR pp.C_BPartner_ID IS NULL)")
				.append( " AND ?>=pp.BreakValue")				//  #4
				.append( " ORDER BY pv.ValidFrom DESC, BreakValue DESC");

		calculated = false;
		if (priceDate == null)
			priceDate = new Timestamp (System.currentTimeMillis());
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try
		{
			statement = DB.prepareStatement(sql.toString(), trxName);
			int count = 0;
			count++;
			statement.setInt(count, productId);
			count++;
			statement.setInt(count, priceListId);
			count++;
			statement.setInt(count, partnerId);
			count++;
			statement.setBigDecimal(count, quantity);
			resultSet = statement.executeQuery();
			while (!calculated && resultSet.next())
			{
				Timestamp plDate = resultSet.getTimestamp(5);
				//	we have the price list
				//	if order date is after or equal PriceList validFrom
				if (plDate == null || !priceDate.before(plDate))
				{
					//	Prices
					priceStd = resultSet.getBigDecimal (1);
					if (resultSet.wasNull ())
						priceStd = Env.ZERO;
					priceList = resultSet.getBigDecimal (2);
					if (resultSet.wasNull ())
						priceList = Env.ZERO;
					priceLimit = resultSet.getBigDecimal (3);
					if (resultSet.wasNull ())
						priceLimit = Env.ZERO;
					//
					uomId = resultSet.getInt (4);
					currencyId = resultSet.getInt (6);
					productCategoryId = resultSet.getInt(7);
					isEnforcePriceLimit = "Y".equals(resultSet.getString(8));
					isTaxIncluded = "Y".equals(resultSet.getString(9));
					//
					log.fine("M_PriceList_ID=" + priceListId
							+ "(" + plDate + ")" + " - " + priceStd);
					calculated = true;
					break;
				}
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
			calculated = false;
		}
		finally
		{
			DB.close(resultSet, statement);
			resultSet = null;
			statement = null;
		}
		if (!calculated)
			log.finer("Not found (BPL)");
		return calculated;
	}	//	calculatePriceBasedOnBasePriceListAndVendorBreak

	/**
	 * 	Set Base Info (UOM)
	 */
	private void setBaseInfo()
	{
		if (productId == 0)
			return;
		
		MProduct product = new MProduct(Env.getCtx(), productId , trxName);
		if (product != null) {
			 uomId = product.getC_UOM_ID();
			 productCategoryId = product.getM_Product_Category_ID();
		}
	}	//	setBaseInfo

	/**
	 * 	Is Tax Included
	 *	@return tax included
	 */
	public boolean isTaxIncluded()
	{
		return isTaxIncluded;
	}	//	isTaxIncluded
	
	
	/**************************************************************************
	 * 	Calculate (Business Partner) Discount
	 */
	private void calculateDiscount()
	{
		discountSchema = false;
		if (partnerId == 0 || productId == 0)
			return;
		
		int discountSchemaId = 0;
		BigDecimal flatDiscount = null;
		String sql = "SELECT COALESCE(p.M_DiscountSchema_ID,g.M_DiscountSchema_ID),"
			+ " COALESCE(p.PO_DiscountSchema_ID,g.PO_DiscountSchema_ID), p.FlatDiscount "
			+ "FROM C_BPartner p"
			+ " INNER JOIN C_BP_Group g ON (p.C_BP_Group_ID=g.C_BP_Group_ID) "
			+ "WHERE p.C_BPartner_ID=?";
		PreparedStatement statement = null;
		ResultSet rs = null;
		try
		{
			statement = DB.prepareStatement (sql, trxName);
			statement.setInt (1, partnerId);
			rs = statement.executeQuery ();
			if (rs.next ())
			{
				discountSchemaId = rs.getInt(isSOTrx ? 1 : 2);
				flatDiscount = rs.getBigDecimal(3);
				if (flatDiscount == null)
					flatDiscount = Env.ZERO;
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, statement);
			rs = null;
			statement = null;
		}


		// Xpande. Gabriel Vila.
		// Modifico manera de calcular descuentos.
		// En retail se utilizan las pautas comerciales en vez de los Esquemas de Descuentos de Adempiere.

		if (discountSchemaId > 0){
			MDiscountSchema discountSchema = new MDiscountSchema(Env.getCtx(), discountSchemaId , trxName);	//	not correct
			if (discountSchema.get_ID() == 0)
				return;
			//
			this.discountSchema = true;
			priceStd = discountSchema.calculatePrice(quantity, priceStd, productId, productCategoryId, flatDiscount);
		}
		else{

			// Si no tengo precio de oferta
			if (!this.tieneOfertaComercial){
				// Es aquí donde se utiliza el concepto de Pauta Comercial en Retail, para el manejo de descuentos.
				// Instancio modelo producto-socio, y si este modelo tiene pauta comercial asociada, calculo descuentos.
				MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(Env.getCtx(), partnerId, productId, null);
				if ((productoSocio != null) && (productoSocio.get_ID() > 0)){
					if (productoSocio.getZ_PautaComercial_ID() > 0){
						this.setPrecision();
						MZPautaComercial pautaComercial = (MZPautaComercial) productoSocio.getZ_PautaComercial();
						ProductPricesInfo ppi = pautaComercial.calculatePrices(productId, priceStd, precision);
						if (ppi != null){
							priceStd = ppi.getPricePO();
						}
					}
				}
			}
		}
		// Xpande.
	}

	
	/**************************************************************************
	 * 	Calculate Discount Percentage based on Standard/List Price
	 * 	@return Discount
	 */
	public BigDecimal getDiscount()
	{
		BigDecimal discount = Env.ZERO;
		if (priceList.intValue() != 0)
			discount = new BigDecimal ((priceList.doubleValue() - priceStd.doubleValue())
				/ priceList.doubleValue() * 100.0);
		if (discount.scale() > 2)
			discount = discount.setScale(2, BigDecimal.ROUND_HALF_UP);
		return discount;
	}	//	getDiscount


	

	/**************************************************************************
	 * 	Get Product ID
	 *	@return id
	 */
	public int getM_Product_ID()
	{
		return productId;
	}
	
	/**
	 * 	Get PriceList ID
	 *	@return pl
	 */
	public int getM_PriceList_ID()
	{
		return priceListId;
	}	//	getM_PriceList_ID
	
	/**
	 * 	Set PriceList
	 *	@param M_PriceList_ID pl
	 */
	public void setM_PriceList_ID( int M_PriceList_ID)
	{
		priceListId = M_PriceList_ID;
		calculated = false;
	}	//	setM_PriceList_ID
	
	/**
	 * 	Get PriceList Version
	 *	@return plv
	 */
	public int getM_PriceList_Version_ID()
	{
		return priceListVersionId;
	}	//	getM_PriceList_Version_ID
	
	/**
	 * 	Set PriceList Version
	 *	@param M_PriceList_Version_ID plv
	 */
	public void setM_PriceList_Version_ID (int M_PriceList_Version_ID)
	{
		priceListVersionId = M_PriceList_Version_ID;
		calculated = false;
	}	//	setM_PriceList_Version_ID
	
	/**
	 * 	Get Price Date
	 *	@return date
	 */
	public Timestamp getPriceDate()
	{
		return priceDate;
	}	//	getPriceDate
	
	/**
	 * 	Set Price Date
	 *	@param priceDate date
	 */
	public void setPriceDate(Timestamp priceDate)
	{
		this.priceDate = priceDate;
		calculated = false;
	}	//	setPriceDate
	
	/**
	 * 	Set Precision.
	 */
	private void setPrecision ()
	{
		if (this.forcedPrecision >=0){
			precision = this.forcedPrecision;
		}
		else{
			if (priceListId != 0){
				precision = MPriceList.getPricePrecision(Env.getCtx(), getM_PriceList_ID());
			}
		}

	}	//	setPrecision
	
	/**
	 * 	Get Precision
	 *	@return precision - -1 = no rounding
	 */
	public int getPrecision()
	{
		return precision;
	}	//	getPrecision
	
	/**
	 * 	Round
	 *	@param price number
	 *	@return rounded number
	 */
	private BigDecimal round (BigDecimal price)
	{
		if (precision >= 0	//	-1 = no rounding
			&& price.scale() > precision)
			return price.setScale(precision, BigDecimal.ROUND_HALF_UP);
		return price;
	}	//	round
	
	/**************************************************************************
	 * 	Get C_UOM_ID
	 *	@return uom
	 */
	public int getC_UOM_ID()
	{
		if (!calculated)
			calculatePrice();
		return uomId;
	}
	
	/**
	 * 	Get Price List
	 *	@return list
	 */
	public BigDecimal getPriceList()
	{
		if (!calculated)
			calculatePrice();
		return round(priceList);
	}
	/**
	 * 	Get Price Std
	 *	@return std
	 */
	public BigDecimal getPriceStd()
	{
		if (!calculated)
			calculatePrice();
		return round(priceStd);
	}
	/**
	 * 	Get Price Limit
	 *	@return limit
	 */
	public BigDecimal getPriceLimit()
	{
		if (!calculated)
			calculatePrice();
		return round(priceLimit);
	}
	/**
	 * 	Get Price List Currency
	 *	@return currency
	 */
	public int getC_Currency_ID()
	{
		if (!calculated)
			calculatePrice();
		return currencyId;
	}
	/**
	 * 	Is Price List enforded?
	 *	@return enforce limit
	 */
	public boolean isEnforcePriceLimit()
	{
		if (!calculated)
			calculatePrice();
		return isEnforcePriceLimit;
	}	//	isEnforcePriceLimit

	/**
	 * 	Is a DiscountSchema active?
	 *	@return active Discount Schema
	 */
	public boolean isDiscountSchema()
	{
		return discountSchema || useVendorBreak;
	}	//	isDiscountSchema
	
	/**
	 * 	Is the Price Calculated (i.e. found)?
	 *	@return calculated
	 */
	public boolean isCalculated()
	{
		return calculated;
	}	//	isCalculated

	public boolean isCostoHistorico() {
		return isCostoHistorico;
	}

	public BigDecimal getPricePO() {
		return pricePO;
	}

	public BigDecimal getPriceFinal() {
		return priceFinal;
	}

}	//	MProductPrice