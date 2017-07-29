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
package org.xpande.retail.callout;

import org.compiere.model.*;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.xpande.core.model.MZProductoUPC;
import org.xpande.core.model.MZSocioListaPrecio;
import org.xpande.core.utils.TaxUtils;
import org.xpande.retail.model.MZProductoSocio;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

/**
 *	Invoice Callouts	
 *	
 *  @author Jorg Janke
 *  @version $Id: CalloutInvoice.java,v 1.4 2006/07/30 00:51:03 jjanke Exp $
 */
public class CalloutInvoice extends CalloutEngine
{

	/**
	 *	Invoice Header - DocType.
	 *		- PaymentRule
	 *		- temporary Document
	 *  Context:
	 *  	- DocSubTypeSO
	 *		- HasCharges
	 *	- (re-sets Business Partner info of required)
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String docType (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer C_DocType_ID = (Integer)value;
		if (C_DocType_ID == null || C_DocType_ID.intValue() == 0)
			return "";

		String sql = "SELECT d.HasCharges,'N',d.IsDocNoControlled," // 1..3
			+ "s.CurrentNext, d.DocBaseType, " // 4..5
			+ "s.StartNewYear, s.DateColumn, s.AD_Sequence_ID " //6..8
			+ "FROM C_DocType d, AD_Sequence s "
			+ "WHERE C_DocType_ID=?"		//	1
			+ " AND d.DocNoSequence_ID=s.AD_Sequence_ID(+)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_DocType_ID.intValue());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				//	Charges - Set Context
				Env.setContext(ctx, WindowNo, "HasCharges", rs.getString(1));
				//	DocumentNo
				if (rs.getString(3).equals("Y"))
				{
					if ("Y".equals(rs.getString(6)))
					{
						String dateColumn = rs.getString(7);
						mTab.setValue("DocumentNo", 
								"<" 
								+ MSequence.getPreliminaryNoByYear(mTab, rs.getInt(8), dateColumn, null)
								+ ">");
					}
					else
						mTab.setValue("DocumentNo", "<" + rs.getString(4) + ">");
				}
				//  DocBaseType - Set Context
				String s = rs.getString(5);
				Env.setContext(ctx, WindowNo, "DocBaseType", s);
				//  AP Check & AR Credit Memo
				if (s.startsWith("AP"))
					mTab.setValue("PaymentRule", "S");    //  Check
				else if (s.endsWith("C"))
					mTab.setValue("PaymentRule", "P");    //  OnCredit
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
			return e.getLocalizedMessage();
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		return "";
	}	//	docType

	/**
	 *	Invoice Header- BPartner.
	 *		- M_PriceList_ID (+ Context)
	 *		- C_BPartner_Location_ID
	 *		- AD_User_ID
	 *		- POReference
	 *		- SO_Description
	 *		- IsDiscountPrinted
	 *		- PaymentRule
	 *		- C_PaymentTerm_ID
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String bPartner (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer C_BPartner_ID = (Integer)value;
		if (C_BPartner_ID == null || C_BPartner_ID.intValue() == 0)
			return "";

		String sql = "SELECT p.AD_Language,p.C_PaymentTerm_ID,"
			+ " COALESCE(p.M_PriceList_ID,g.M_PriceList_ID) AS M_PriceList_ID, p.PaymentRule,p.POReference,"
			+ " p.SO_Description,p.IsDiscountPrinted,"
			+ " p.SO_CreditLimit, p.SO_CreditLimit-p.SO_CreditUsed AS CreditAvailable,"
			+ " l.C_BPartner_Location_ID,c.AD_User_ID,"
			+ " COALESCE(p.PO_PriceList_ID,g.PO_PriceList_ID) AS PO_PriceList_ID, p.PaymentRulePO,p.PO_PaymentTerm_ID " 
			+ "FROM C_BPartner p"
			+ " INNER JOIN C_BP_Group g ON (p.C_BP_Group_ID=g.C_BP_Group_ID)"			
			+ " LEFT OUTER JOIN C_BPartner_Location l ON (p.C_BPartner_ID=l.C_BPartner_ID AND l.IsBillTo='Y' AND l.IsActive='Y')"
			+ " LEFT OUTER JOIN AD_User c ON (p.C_BPartner_ID=c.C_BPartner_ID) "
			+ "WHERE p.C_BPartner_ID=? AND p.IsActive='Y'";		//	#1

		boolean IsSOTrx = Env.getContext(ctx, WindowNo, "IsSOTrx").equals("Y");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_BPartner_ID.intValue());
			rs = pstmt.executeQuery();
			//
			if (rs.next())
			{
				//	PriceList & IsTaxIncluded & Currency
				Integer ii = new Integer(rs.getInt(IsSOTrx ? "M_PriceList_ID" : "PO_PriceList_ID"));
				if (!rs.wasNull())
					mTab.setValue("M_PriceList_ID", ii);
				else
				{	//	get default PriceList
					int i = Env.getContextAsInt(ctx, "#M_PriceList_ID");
					if (i != 0)
						mTab.setValue("M_PriceList_ID", new Integer(i));
				}

				//	PaymentRule
				String s = rs.getString(IsSOTrx ? "PaymentRule" : "PaymentRulePO");
				if (s != null && s.length() != 0)
				{
					if (Env.getContext(ctx, WindowNo, "DocBaseType").endsWith("C"))	//	Credits are Payment Term
						s = "P";
					else if (IsSOTrx && (s.equals("S") || s.equals("U")))	//	No Check/Transfer for SO_Trx
						s = "P";											//  Payment Term
					mTab.setValue("PaymentRule", s);
				}
				//  Payment Term
				ii = new Integer(rs.getInt(IsSOTrx ? "C_PaymentTerm_ID" : "PO_PaymentTerm_ID"));
				if (!rs.wasNull())
					mTab.setValue("C_PaymentTerm_ID", ii);

				//	Location
				int locID = rs.getInt("C_BPartner_Location_ID");
				//	overwritten by InfoBP selection - works only if InfoWindow
				//	was used otherwise creates error (uses last value, may belong to differnt BP)
				if (C_BPartner_ID.toString().equals(Env.getContext(ctx, WindowNo, Env.TAB_INFO, "C_BPartner_ID")))
				{
					String loc = Env.getContext(ctx, WindowNo, Env.TAB_INFO, "C_BPartner_Location_ID");
					if (loc.length() > 0){
						if ((Integer.parseInt(loc)) > 0 ){
							locID = Integer.parseInt(loc);
						}
					}
				}
				if (locID == 0)
					mTab.setValue("C_BPartner_Location_ID", null);
				else
					mTab.setValue("C_BPartner_Location_ID", new Integer(locID));

				//	Contact - overwritten by InfoBP selection
				int contID = rs.getInt("AD_User_ID");
				if (C_BPartner_ID.toString().equals(Env.getContext(ctx, WindowNo, Env.TAB_INFO, "C_BPartner_ID")))
				{
					String cont = Env.getContext(ctx, WindowNo, Env.TAB_INFO, "AD_User_ID");
					if (cont.length() > 0)
						contID = Integer.parseInt(cont);
				}
				if (contID == 0)
					mTab.setValue("AD_User_ID", null);
				else
					mTab.setValue("AD_User_ID", new Integer(contID));

				//	CreditAvailable
				if (IsSOTrx)
				{
					double CreditLimit = rs.getDouble("SO_CreditLimit");
					if (CreditLimit != 0)
					{
						double CreditAvailable = rs.getDouble("CreditAvailable");
						if (!rs.wasNull() && CreditAvailable < 0)
							mTab.fireDataStatusEEvent("CreditLimitOver",
								DisplayType.getNumberFormat(DisplayType.Amount).format(CreditAvailable),
								false);
					}
				}
				
				//	PO Reference
				s = rs.getString("POReference");
				if (s != null && s.length() != 0)
					mTab.setValue("POReference", s);
				else
					mTab.setValue("POReference", null);
				//	SO Description
				s = rs.getString("SO_Description");
				if (s != null && s.trim().length() != 0)
					mTab.setValue("Description", s);
				//	IsDiscountPrinted
				s = rs.getString("IsDiscountPrinted");
				if (s != null && s.length() != 0)
					mTab.setValue("IsDiscountPrinted", s);
				else
					mTab.setValue("IsDiscountPrinted", "N");
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, "bPartner", e);
			return e.getLocalizedMessage();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return "";
	}	//	bPartner

	/**
	 *	Set Payment Term.
	 *	Payment Term has changed 
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String paymentTerm (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer C_PaymentTerm_ID = (Integer)value;
		int C_Invoice_ID = Env.getContextAsInt(ctx, WindowNo, "C_Invoice_ID");
		if (C_PaymentTerm_ID == null || C_PaymentTerm_ID.intValue() == 0
			|| C_Invoice_ID == 0)	//	not saved yet
			return "";
		//
		MPaymentTerm pt = new MPaymentTerm (ctx, C_PaymentTerm_ID.intValue(), null);
		if (pt.get_ID() == 0)
			return "PaymentTerm not found";
		
		boolean valid = pt.apply (C_Invoice_ID);
		mTab.setValue("IsPayScheduleValid", valid ? "Y" : "N");
		
		return "";
	}	//	paymentTerm

	
	/**************************************************************************
	 *	Invoice Line - Product.
	 *		- reset C_Charge_ID / M_AttributeSetInstance_ID
	 *		- PriceList, PriceStd, PriceLimit, C_Currency_ID, EnforcePriceLimit
	 *		- UOM
	 *	Calls Tax
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String product (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{

		// Xpande. Gabriel Vila.
		// Llamo a callout para setear los datos : codigo de barras - codigo prod.prov - producto
		String message = upcVendProdNoProduct(ctx, WindowNo, mTab, mField, value);
		if (!message.equalsIgnoreCase("")){
			return  message;
		}
		// Xpande.


		Integer M_Product_ID = (Integer)value;
		Integer M_AttributeSetInstance_ID = 0;

		if (M_Product_ID == null || M_Product_ID.intValue() == 0)
			return "";
		mTab.setValue("C_Charge_ID", null);

		int adOrgID = Env.getContextAsInt(ctx, WindowNo, "AD_Org_ID");

		int cInvoiceID = ((Integer)mTab.getValue("C_Invoice_ID")).intValue();
		if (cInvoiceID <= 0) {
			return "No se obtuvo ID interno de Orden";
		}

		MInvoice invoice = new MInvoice(ctx, cInvoiceID, null);
		int M_PriceList_ID = invoice.getM_PriceList_ID();
		if (M_PriceList_ID <= 0) {
			return "No se obtuvo ID interno de Lista de Precios de la Orden";
		}

		int M_PriceList_Version_ID = ((MPriceList)invoice.getM_PriceList()).getPriceListVersion(null).get_ID();
		int StdPrecision = MPriceList.getPricePrecision(ctx, M_PriceList_ID);


		//	Get Model and check the Attribute Set Instance from the context
		MProduct m_product = MProduct.get(Env.getCtx(), M_Product_ID);
		mTab.setValue("M_AttributeSetInstance_ID", m_product.getEnvAttributeSetInstance(ctx, WindowNo));

		/*****	Price Calculation see also qty	****/
		boolean IsSOTrx = Env.getContext(ctx, WindowNo, "IsSOTrx").equals("Y");
		int C_BPartner_ID = Env.getContextAsInt(ctx, WindowNo, WindowNo, "C_BPartner_ID");
		BigDecimal Qty = (BigDecimal)mTab.getValue("QtyInvoiced");
		org.xpande.retail.model.MProductPricing pp = new org.xpande.retail.model.MProductPricing (M_Product_ID.intValue(), C_BPartner_ID, adOrgID, Qty, IsSOTrx, null);
		//
		pp.setM_PriceList_ID(M_PriceList_ID);
		pp.setForcedPrecision(StdPrecision);

		Timestamp invoiceDate = Env.getContextAsDate(ctx, WindowNo, "DateInvoiced");
		/** PLV is only accurate if PL selected in header */
		if ( M_PriceList_Version_ID == 0 && M_PriceList_ID > 0)
		{
			String sql = "SELECT plv.M_PriceList_Version_ID "
				+ "FROM M_PriceList_Version plv "
				+ "WHERE plv.M_PriceList_ID=? "						//	1
				+ " AND plv.ValidFrom <= ? "
				+ "ORDER BY plv.ValidFrom DESC";
			//	Use newest price list - may not be future
			
			M_PriceList_Version_ID = DB.getSQLValueEx(null, sql, M_PriceList_ID, invoiceDate);
			if ( M_PriceList_Version_ID > 0 )
				Env.setContext(ctx, WindowNo, "M_PriceList_Version_ID", M_PriceList_Version_ID );
		}
		
		pp.setM_PriceList_Version_ID(M_PriceList_Version_ID);
		Timestamp date = Env.getContextAsDate(ctx, WindowNo, "DateInvoiced");
		pp.setPriceDate(date);
		//		
		mTab.setValue("PriceList", pp.getPriceList());
		mTab.setValue("PriceLimit", pp.getPriceLimit());
		mTab.setValue("PriceActual", pp.getPriceStd());
		mTab.setValue("PriceEntered", pp.getPriceStd());

		// Xpande. Gabriel Vila
		// Seteo precio de orden de compra para retail solo si es nulo
		BigDecimal pricePO = (BigDecimal) mTab.getValue("PricePO");
		if ((pricePO == null) || (pricePO.compareTo(Env.ZERO) <= 0)) {
			mTab.setValue("PricePO", mTab.getValue("PriceEntered"));
		}
		// Xpande.

		mTab.setValue("C_Currency_ID", new Integer(pp.getC_Currency_ID()));
	//	mTab.setValue("Discount", pp.getDiscount());
		mTab.setValue("C_UOM_ID", new Integer(pp.getC_UOM_ID()));
		Env.setContext(ctx, WindowNo, "EnforcePriceLimit", pp.isEnforcePriceLimit() ? "Y" : "N");
		Env.setContext(ctx, WindowNo, "DiscountSchema", pp.isDiscountSchema() ? "Y" : "N");
		//
		return tax (ctx, WindowNo, mTab, mField, value);
	}	//	product

	/**
	 *	Invoice Line - Charge.
	 * 		- updates PriceActual from Charge
	 * 		- sets PriceLimit, PriceList to zero
	 * 	Calles tax
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String charge (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer C_Charge_ID = (Integer)value;
		if (C_Charge_ID == null || C_Charge_ID.intValue() == 0)
			return "";
			
		//	No Product defined
		if (mTab.getValue("M_Product_ID") != null)
		{
			mTab.setValue("C_Charge_ID", null);
			return "ChargeExclusively";
		}
		mTab.setValue("M_AttributeSetInstance_ID", null);
		mTab.setValue("S_ResourceAssignment_ID", null);
		mTab.setValue("C_UOM_ID", new Integer(100));	//	EA

		Env.setContext(ctx, WindowNo, "DiscountSchema", "N");
		Env.setContext(Env.getCtx(), WindowNo, "IsTaxIncluded", "Y");

		String sql = "SELECT ChargeAmt FROM C_Charge WHERE C_Charge_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_Charge_ID.intValue());
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				mTab.setValue ("PriceEntered", rs.getBigDecimal (1));
				mTab.setValue ("PriceActual", rs.getBigDecimal (1));
				mTab.setValue ("PriceLimit", Env.ZERO);
				mTab.setValue ("PriceList", Env.ZERO);
				mTab.setValue ("Discount", Env.ZERO);
			}
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql + e);
			return e.getLocalizedMessage();
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//
		return tax (ctx, WindowNo, mTab, mField, value);
	}	//	charge


	/**
	 *	Invoice Line - Tax.
	 *		- basis: Product, Charge, BPartner Location
	 *		- sets C_Tax_ID
	 *  Calls Amount
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String tax (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		String column = mField.getColumnName();
		if (value == null)
			return "";

		//	Check Product
		int M_Product_ID = 0;
		if (column.equals("M_Product_ID"))
			M_Product_ID = ((Integer)value).intValue();
		else
			M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
		int C_Charge_ID = 0;
		if (column.equals("C_Charge_ID"))
			C_Charge_ID = ((Integer)value).intValue();
		else
			C_Charge_ID = Env.getContextAsInt(ctx, WindowNo, "C_Charge_ID");
		log.fine("Product=" + M_Product_ID + ", C_Charge_ID=" + C_Charge_ID);
		if (M_Product_ID == 0 && C_Charge_ID == 0)
			return amt (ctx, WindowNo, mTab, mField, value);	//

		//	Check Partner Location
		int shipC_BPartner_Location_ID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_Location_ID");
		if (shipC_BPartner_Location_ID == 0)
			return amt (ctx, WindowNo, mTab, mField, value);	//
		log.fine("Ship BP_Location=" + shipC_BPartner_Location_ID);
		int billC_BPartner_Location_ID = shipC_BPartner_Location_ID;
		log.fine("Bill BP_Location=" + billC_BPartner_Location_ID);

		//	Dates
		Timestamp billDate = Env.getContextAsDate(ctx, WindowNo, "DateInvoiced");
		log.fine("Bill Date=" + billDate);
		Timestamp shipDate = billDate;
		log.fine("Ship Date=" + shipDate);

		int AD_Org_ID = Env.getContextAsInt(ctx, WindowNo, "AD_Org_ID");
		log.fine("Org=" + AD_Org_ID);

		int M_Warehouse_ID = Env.getContextAsInt(ctx, "#M_Warehouse_ID");
		log.fine("Warehouse=" + M_Warehouse_ID);

		//
		int C_Tax_ID = Tax.get(ctx, M_Product_ID, C_Charge_ID, billDate, shipDate,
			AD_Org_ID, M_Warehouse_ID, billC_BPartner_Location_ID, shipC_BPartner_Location_ID,
			Env.getContext(ctx, WindowNo, "IsSOTrx").equals("Y"));

		// Xpande. Gabriel Vila.
		// Para ordenes de compra en Retail, puede suceder que el producto tenga un impuesto especial de compra.
		// Por lo tanto aca considero esta posibilidad.
		if ("N".equals(Env.getContext(ctx, WindowNo, "IsSOTrx"))){
			MProduct product = new MProduct(ctx, M_Product_ID, null);
			if (product.get_ValueAsInt("C_TaxCategory_ID_2") > 0){
				MTax taxAux = TaxUtils.getLastTaxByCategory(ctx, product.get_ValueAsInt("C_TaxCategory_ID_2"), null);
				if ((taxAux != null) && (taxAux.get_ID() > 0)){
					C_Tax_ID = taxAux.get_ID();
				}
			}
		}
		// Xpande

		log.info("Tax ID=" + C_Tax_ID);
		//
		if (C_Tax_ID == 0)
			mTab.fireDataStatusEEvent(CLogger.retrieveError());
		else
			mTab.setValue("C_Tax_ID", new Integer(C_Tax_ID));
		//
		return amt (ctx, WindowNo, mTab, mField, value);
	}	//	tax


	/**
	 *	Invoice - Amount.
	 *		- called from QtyInvoiced, PriceActual
	 *		- calculates LineNetAmt
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String amt (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive() || value == null)
			return "";

	//	log.log(Level.WARNING,"amt - init");
		int C_UOM_To_ID = Env.getContextAsInt(ctx, WindowNo, "C_UOM_ID");
		int M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
		int adOrgID = Env.getContextAsInt(ctx, WindowNo, "AD_Org_ID");

		int cInvoiceID = ((Integer)mTab.getValue("C_Invoice_ID")).intValue();
		if (cInvoiceID <= 0) {
			return "No se obtuvo ID interno de Comprobante";
		}
		MInvoice invoice = new MInvoice(ctx, cInvoiceID, null);
		int M_PriceList_ID = invoice.getM_PriceList_ID();
		int StdPrecision = MPriceList.getPricePrecision(ctx, M_PriceList_ID);

		BigDecimal QtyEntered, QtyInvoiced, PriceEntered, PriceActual, PriceLimit, Discount, PriceList;
		//	get values
		QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
		QtyInvoiced = (BigDecimal)mTab.getValue("QtyInvoiced");
		log.fine("QtyEntered=" + QtyEntered + ", Invoiced=" + QtyInvoiced + ", UOM=" + C_UOM_To_ID);
		//
		PriceEntered = (BigDecimal)mTab.getValue("PriceEntered");
		PriceActual = (BigDecimal)mTab.getValue("PriceActual");
	//	Discount = (BigDecimal)mTab.getValue("Discount");
		PriceLimit = (BigDecimal)mTab.getValue("PriceLimit");
		PriceList = (BigDecimal)mTab.getValue("PriceList");
		log.fine("PriceList=" + PriceList + ", Limit=" + PriceLimit + ", Precision=" + StdPrecision);
		log.fine("PriceEntered=" + PriceEntered + ", Actual=" + PriceActual);// + ", Discount=" + Discount);

		//		No Product
		if ( M_Product_ID == 0 )
		{
			// if price change sync price actual and entered
			// else ignore
			if (mField.getColumnName().equals("PriceActual"))
			{
				PriceEntered = (BigDecimal) value;
				mTab.setValue("PriceEntered", value);
			}
			else if (mField.getColumnName().equals("PriceEntered"))
			{
				PriceActual = (BigDecimal) value;
				mTab.setValue("PriceActual", value);
			}
		}
		//	Product Qty changed - recalc price
		else if ((mField.getColumnName().equals("QtyInvoiced") 
			|| mField.getColumnName().equals("QtyEntered")
			|| mField.getColumnName().equals("C_UOM_ID")
			|| mField.getColumnName().equals("M_Product_ID")) 
			&& !"N".equals(Env.getContext(ctx, WindowNo, "DiscountSchema")))
		{
			int C_BPartner_ID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_ID");
			if (mField.getColumnName().equals("QtyEntered"))
				QtyInvoiced = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
					C_UOM_To_ID, QtyEntered);
			if (QtyInvoiced == null)
				QtyInvoiced = QtyEntered;
			boolean IsSOTrx = Env.getContext(ctx, WindowNo, "IsSOTrx").equals("Y");
			org.xpande.retail.model.MProductPricing pp = new org.xpande.retail.model.MProductPricing (M_Product_ID, C_BPartner_ID, adOrgID, QtyInvoiced, IsSOTrx, null);
			pp.setM_PriceList_ID(M_PriceList_ID);
			pp.setForcedPrecision(StdPrecision);
			int	M_PriceList_Version_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_Version_ID");
			pp.setM_PriceList_Version_ID(M_PriceList_Version_ID);
			Timestamp date = (Timestamp)mTab.getValue("DateInvoiced");
			pp.setPriceDate(date);
			//
			PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
				C_UOM_To_ID, pp.getPriceStd());
			if (PriceEntered == null)
				PriceEntered = pp.getPriceStd();
			//
			log.fine("amt - QtyChanged -> PriceActual=" + pp.getPriceStd()
				+ ", PriceEntered=" + PriceEntered + ", Discount=" + pp.getDiscount());
			
			PriceActual = pp.getPriceStd();
			mTab.setValue("PriceActual", pp.getPriceStd());
		//	mTab.setValue("Discount", pp.getDiscount());
			mTab.setValue("PriceEntered", PriceEntered);

			// Xpande. Gabriel Vila
			// Seteo precio de orden de compra para retail solo si es nulo
			BigDecimal pricePO = (BigDecimal) mTab.getValue("PricePO");
			if ((pricePO == null) || (pricePO.compareTo(Env.ZERO) <= 0)) {
				mTab.setValue("PricePO", mTab.getValue("PriceEntered"));
			}
			// Xpande.

			Env.setContext(ctx, WindowNo, "DiscountSchema", pp.isDiscountSchema() ? "Y" : "N");
		}
		else if (mField.getColumnName().equals("PriceActual"))
		{
			PriceActual = (BigDecimal)value;
			PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
				C_UOM_To_ID, PriceActual);
			if (PriceEntered == null)
				PriceEntered = PriceActual;
			//
			log.fine("amt - PriceActual=" + PriceActual 
				+ " -> PriceEntered=" + PriceEntered);
			mTab.setValue("PriceEntered", PriceEntered);
		}
		else if (mField.getColumnName().equals("PriceEntered"))
		{
			PriceEntered = (BigDecimal)value;
			PriceActual = MUOMConversion.convertProductTo (ctx, M_Product_ID, 
				C_UOM_To_ID, PriceEntered);
			if (PriceActual == null)
				PriceActual = PriceEntered;
			//
			log.fine("amt - PriceEntered=" + PriceEntered 
				+ " -> PriceActual=" + PriceActual);
			mTab.setValue("PriceActual", PriceActual);
		}
		
		/**  Discount entered - Calculate Actual/Entered
		if (mField.getColumnName().equals("Discount"))
		{
			PriceActual = new BigDecimal ((100.0 - Discount.doubleValue()) / 100.0 * PriceList.doubleValue());
			if (PriceActual.scale() > StdPrecision)
				PriceActual = PriceActual.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
			PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
				C_UOM_To_ID, PriceActual);
			if (PriceEntered == null)
				PriceEntered = PriceActual;
			mTab.setValue("PriceActual", PriceActual);
			mTab.setValue("PriceEntered", PriceEntered);
		}
		//	calculate Discount
		else
		{
			if (PriceList.intValue() == 0)
				Discount = Env.ZERO;
			else
				Discount = new BigDecimal ((PriceList.doubleValue() - PriceActual.doubleValue()) / PriceList.doubleValue() * 100.0);
			if (Discount.scale() > 2)
				Discount = Discount.setScale(2, BigDecimal.ROUND_HALF_UP);
			mTab.setValue("Discount", Discount);
		}
		log.fine("amt = PriceEntered=" + PriceEntered + ", Actual" + PriceActual + ", Discount=" + Discount);
		/* */

		//	Check PriceLimit
		String epl = Env.getContext(ctx, WindowNo, "EnforcePriceLimit");
		boolean enforce = Env.isSOTrx(ctx, WindowNo) && epl != null && epl.equals("Y");
		if (enforce && MRole.getDefault().isOverwritePriceLimit())
			enforce = false;
		//	Check Price Limit?
		if (enforce && PriceLimit.doubleValue() != 0.0
		  && PriceActual.compareTo(PriceLimit) < 0)
		{
			PriceActual = PriceLimit;
			PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
				C_UOM_To_ID, PriceLimit);
			if (PriceEntered == null)
				PriceEntered = PriceLimit;
			log.fine("amt =(under) PriceEntered=" + PriceEntered + ", Actual" + PriceLimit);
			mTab.setValue ("PriceActual", PriceLimit);
			mTab.setValue ("PriceEntered", PriceEntered);
			mTab.fireDataStatusEEvent ("UnderLimitPrice", "", false);
			//	Repeat Discount calc
			if (PriceList.intValue() != 0)
			{
				Discount = new BigDecimal ((PriceList.doubleValue () - PriceActual.doubleValue ()) / PriceList.doubleValue () * 100.0);
				if (Discount.scale () > 2)
					Discount = Discount.setScale (2, BigDecimal.ROUND_HALF_UP);
			//	mTab.setValue ("Discount", Discount);
			}
		}

		//	Line Net Amt
		BigDecimal LineNetAmt = QtyInvoiced.multiply(PriceActual);
		if (LineNetAmt.scale() > StdPrecision)
			LineNetAmt = LineNetAmt.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
		log.info("amt = LineNetAmt=" + LineNetAmt);
		mTab.setValue("LineNetAmt", LineNetAmt);


		//	Calculate Tax Amount for order
		//boolean IsSOTrx = "Y".equals(Env.getContext(Env.getCtx(), WindowNo, "IsSOTrx"));
		//if (!IsSOTrx)
		//{
			BigDecimal TaxAmt = Env.ZERO; // teo_sarca: [ 1656829 ] Problem when there is not tax selected in vendor invoice
			if (mField.getColumnName().equals("TaxAmt"))
			{
				TaxAmt = (BigDecimal)mTab.getValue("TaxAmt");
			}
			else
			{
				Integer taxID = (Integer)mTab.getValue("C_Tax_ID");
				if (taxID != null)
				{
					int C_Tax_ID = taxID.intValue();
					MTax tax = new MTax (ctx, C_Tax_ID, null);
					TaxAmt = tax.calculateTax(LineNetAmt, isTaxIncluded(WindowNo), StdPrecision);
					mTab.setValue("TaxAmt", TaxAmt);
				}
			}

			// Xpande. Gabriel Vila. Comento codigo por problemas de impuestos incluídos o no.
			// Agrego un if donde solo se suma el impuesto si no es impuesto incluído y si no esta incluido se setea total=neto
			if (!isTaxIncluded(WindowNo)){
				//	Add it up
				mTab.setValue("LineTotalAmt", LineNetAmt.add(TaxAmt));
				mTab.setValue("AmtSubtotal", LineNetAmt);
			}
			else{
				mTab.setValue("LineTotalAmt", LineNetAmt);
				mTab.setValue("AmtSubtotal", LineNetAmt.subtract(TaxAmt));
			}
			// Xpande

		//}



		return "";
	}	//	amt

	/**
	 * 	Is Tax Included
	 *	@param WindowNo window no
	 *	@return tax included (default: false)
	 */
	private boolean isTaxIncluded (int WindowNo)
	{
		String ss = Env.getContext(Env.getCtx(), WindowNo, "IsTaxIncluded");
		//	Not Set Yet
		if (ss.length() == 0)
		{
			int M_PriceList_ID = Env.getContextAsInt(Env.getCtx(), WindowNo, "M_PriceList_ID");
			if (M_PriceList_ID == 0)
				return false;
			ss = DB.getSQLValueString(null,
				"SELECT IsTaxIncluded FROM M_PriceList WHERE M_PriceList_ID=?", 
				M_PriceList_ID);
			if (ss == null)
				ss = "N";
			Env.setContext(Env.getCtx(), WindowNo, "IsTaxIncluded", ss);
		}
		return "Y".equals(ss);
	}	//	isTaxIncluded

	/**
	 *	Invoice Line - Quantity.
	 *		- called from C_UOM_ID, QtyEntered, QtyInvoiced
	 *		- enforces qty UOM relationship
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String qty (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive() || value == null)
			return "";

		int M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
	//	log.log(Level.WARNING,"qty - init - M_Product_ID=" + M_Product_ID);
		BigDecimal QtyInvoiced, QtyEntered, PriceActual, PriceEntered;
		
		//	No Product
		if (M_Product_ID == 0)
		{
			QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
			mTab.setValue("QtyInvoiced", QtyEntered);
		}
		//	UOM Changed - convert from Entered -> Product
		else if (mField.getColumnName().equals("C_UOM_ID"))
		{
			int C_UOM_To_ID = ((Integer)value).intValue();
			QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
			BigDecimal QtyEntered1 = QtyEntered.setScale(MUOM.getPrecision(ctx, C_UOM_To_ID), BigDecimal.ROUND_HALF_UP);
			if (QtyEntered.compareTo(QtyEntered1) != 0)
			{
				log.fine("Corrected QtyEntered Scale UOM=" + C_UOM_To_ID 
					+ "; QtyEntered=" + QtyEntered + "->" + QtyEntered1);  
				QtyEntered = QtyEntered1;
				mTab.setValue("QtyEntered", QtyEntered);
			}
			QtyInvoiced = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
				C_UOM_To_ID, QtyEntered);
			if (QtyInvoiced == null)
				QtyInvoiced = QtyEntered;
			boolean conversion = QtyEntered.compareTo(QtyInvoiced) != 0;
			PriceActual = (BigDecimal)mTab.getValue("PriceActual");
			PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
				C_UOM_To_ID, PriceActual);
			if (PriceEntered == null)
				PriceEntered = PriceActual; 
			log.fine("qty - UOM=" + C_UOM_To_ID 
				+ ", QtyEntered/PriceActual=" + QtyEntered + "/" + PriceActual
				+ " -> " + conversion 
				+ " QtyInvoiced/PriceEntered=" + QtyInvoiced + "/" + PriceEntered);
			Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y" : "N");
			mTab.setValue("QtyInvoiced", QtyInvoiced);
			mTab.setValue("PriceEntered", PriceEntered);
		}
		//	QtyEntered changed - calculate QtyInvoiced
		else if (mField.getColumnName().equals("QtyEntered"))
		{
			int C_UOM_To_ID = Env.getContextAsInt(ctx, WindowNo, "C_UOM_ID");
			QtyEntered = (BigDecimal)value;
			BigDecimal QtyEntered1 = QtyEntered.setScale(MUOM.getPrecision(ctx, C_UOM_To_ID), BigDecimal.ROUND_HALF_UP);
			if (QtyEntered.compareTo(QtyEntered1) != 0)
			{
				log.fine("Corrected QtyEntered Scale UOM=" + C_UOM_To_ID 
					+ "; QtyEntered=" + QtyEntered + "->" + QtyEntered1);  
				QtyEntered = QtyEntered1;
				mTab.setValue("QtyEntered", QtyEntered);
			}
			QtyInvoiced = MUOMConversion.convertProductFrom (ctx, M_Product_ID, 
				C_UOM_To_ID, QtyEntered);
			if (QtyInvoiced == null)
				QtyInvoiced = QtyEntered;
			boolean conversion = QtyEntered.compareTo(QtyInvoiced) != 0;
			log.fine("qty - UOM=" + C_UOM_To_ID 
				+ ", QtyEntered=" + QtyEntered
				+ " -> " + conversion 
				+ " QtyInvoiced=" + QtyInvoiced);
			Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y" : "N");
			mTab.setValue("QtyInvoiced", QtyInvoiced);
		}
		//	QtyInvoiced changed - calculate QtyEntered (should not happen)
		else if (mField.getColumnName().equals("QtyInvoiced"))
		{
			int C_UOM_To_ID = Env.getContextAsInt(ctx, WindowNo, "C_UOM_ID");
			QtyInvoiced = (BigDecimal)value;
			int precision = MProduct.get(ctx, M_Product_ID).getUOMPrecision(); 
			BigDecimal QtyInvoiced1 = QtyInvoiced.setScale(precision, BigDecimal.ROUND_HALF_UP);
			if (QtyInvoiced.compareTo(QtyInvoiced1) != 0)
			{
				log.fine("Corrected QtyInvoiced Scale "
					+ QtyInvoiced + "->" + QtyInvoiced1);  
				QtyInvoiced = QtyInvoiced1;
				mTab.setValue("QtyInvoiced", QtyInvoiced);
			}
			QtyEntered = MUOMConversion.convertProductTo (ctx, M_Product_ID, 
				C_UOM_To_ID, QtyInvoiced);
			if (QtyEntered == null)
				QtyEntered = QtyInvoiced;
			boolean conversion = QtyInvoiced.compareTo(QtyEntered) != 0;
			log.fine("qty - UOM=" + C_UOM_To_ID 
				+ ", QtyInvoiced=" + QtyInvoiced
				+ " -> " + conversion 
				+ " QtyEntered=" + QtyEntered);
			Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y" : "N");
			mTab.setValue("QtyEntered", QtyEntered);
		}
		//
		return "";
	}	//	qty

	/***
	 * Al ingresar código de barras, o codigo de producto del proveedor, o el producto directamente,
	 * se deben setear los otros dos valores asociados.
	 * Xpande. Created by Gabriel Vila on 6/25/17.
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String upcVendProdNoProduct(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

		if (isCalloutActive()) return "";

		if ((value == null) || (value.toString().trim().equalsIgnoreCase(""))){
			mTab.setValue("UPC", null);
			mTab.setValue("VendorProductNo", null);
			mTab.setValue("M_Product_ID", null);
			return "";
		}

		int cBPartnerID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_ID");

		String column = mField.getColumnName();

		if (column.equalsIgnoreCase("UPC")){
			MZProductoUPC pupc = MZProductoUPC.getByUPC(ctx, value.toString().trim(), null);
			if ((pupc != null) && (pupc.get_ID() > 0)){
				MProduct prod = (MProduct) pupc.getM_Product();
				MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(ctx, cBPartnerID, prod.get_ID(), null);
				if ((productoSocio == null) || (productoSocio.get_ID() <= 0)){
					mTab.setValue("VendorProductNo", null);
					mTab.setValue("M_Product_ID", null);
					mTab.fireDataStatusEEvent ("Error", "El código de barras ingresado no pertenece a un Producto de este Socio de Negocio.", true);
				}
				else{
					if (productoSocio.getVendorProductNo() != null){
						if (!productoSocio.getVendorProductNo().trim().equalsIgnoreCase("")){
							mTab.setValue("VendorProductNo", productoSocio.getVendorProductNo().trim());
						}
					}
					mTab.setValue("M_Product_ID", prod.get_ID());
				}
			}
			else{
				mTab.setValue("VendorProductNo", null);
				mTab.setValue("M_Product_ID", null);
				mTab.fireDataStatusEEvent ("Error", "No existe Producto con código de barras ingresado", true);
			}
		}
		else if (column.equalsIgnoreCase("VendorProductNo")){
			MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerVendorProdNo(ctx, cBPartnerID, value.toString().trim(), null);
			if ((productoSocio != null) && (productoSocio.get_ID() > 0)){
				MProduct prod = (MProduct) productoSocio.getM_Product();
				MZProductoUPC pupc = MZProductoUPC.getByProduct(ctx, prod.get_ID(), null);
				if ((pupc != null) && (pupc.get_ID() > 0)){
					mTab.setValue("UPC", pupc.getUPC());
				}
				mTab.setValue("M_Product_ID", prod.get_ID());
			}
			else{
				mTab.setValue("UPC", null);
				mTab.setValue("M_Product_ID", null);
				mTab.fireDataStatusEEvent ("Error", "No existe Producto para el código ingresado de producto del proveedor", true);
			}
		}
		else if (column.equalsIgnoreCase("M_Product_ID")){
			int mProductID = ((Integer) value).intValue();
			MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(ctx, cBPartnerID, mProductID, null);
			if ((productoSocio != null) && (productoSocio.get_ID() > 0)){
				if (productoSocio.getVendorProductNo() != null){
					if (!productoSocio.getVendorProductNo().trim().equalsIgnoreCase("")){
						mTab.setValue("VendorProductNo", productoSocio.getVendorProductNo().trim());
					}
					else{
						mTab.setValue("VendorProductNo", null);
					}
				}
				else{
					mTab.setValue("VendorProductNo", null);
				}
			}
			MZProductoUPC pupc = MZProductoUPC.getByProduct(ctx, mProductID, null);
			if ((pupc != null) && (pupc.get_ID() > 0)){
				mTab.setValue("UPC", pupc.getUPC());
			}
			else{
				mTab.setValue("UPC", null);
			}
		}

		return "";
	}

	/***
	 * Dado un socio de negocio y una moneda, obtengo lista de precios de compra y atributos de la misma.
	 * Xpande. Created by Gabriel Vila on 7/15/17.
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String priceListPOByPartnerCurrency(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

		if (isCalloutActive()) return "";

		if ((value == null) || (((Integer)value).intValue()) <= 0) return "";

		// Solo aplica a documentos de compra
		boolean isSOTrx = Env.getContext(ctx, WindowNo, "IsSOTrx").equals("Y");
		if (isSOTrx) return "";

		int cCurrencyID = 0;
		int cBPartnerID = 0;

		String column = mField.getColumnName();

		if (column.equalsIgnoreCase("C_BPartner_ID")){
			cBPartnerID = ((Integer)value).intValue();
			if (mTab.getValue("C_Currency_ID") != null){
				cCurrencyID = (Integer)mTab.getValue("C_Currency_ID");
			}
		}
		else if (column.equalsIgnoreCase("C_Currency_ID")){
			cCurrencyID = ((Integer)value).intValue();
			if (mTab.getValue("C_BPartner_ID") != null){
				cBPartnerID = (Integer)mTab.getValue("C_BPartner_ID");
			}
		}

		// Si no tengo partner o moneda, no hago nada
		if ((cCurrencyID <= 0) || (cBPartnerID <= 0)){
			mTab.setValue("M_PriceList_ID", null);
			return "";
		}

		// Obtengo lista de precios de compra para socio de negocio y moneda
		MZSocioListaPrecio socioListaPrecio = MZSocioListaPrecio.getByPartnerCurrency(ctx, cBPartnerID, cCurrencyID, null);
		if ((socioListaPrecio == null) || (socioListaPrecio.get_ID() <= 0)){
			/*
			// Si no tengo, obtengo lista por defecto
			int i = Env.getContextAsInt(ctx, "#M_PriceList_ID");
			if (i != 0){
				mTab.setValue("M_PriceList_ID", new Integer(i));
			}
			else{
				mTab.setValue("M_PriceList_ID", null);
			}
			*/

			mTab.setValue("M_PriceList_ID", null);

			return "";
		}

		MPriceList priceList = (MPriceList) socioListaPrecio.getM_PriceList();
		MPriceListVersion priceListVersion = priceList.getPriceListVersion(null);

		// Seteo datos con datos de la lista

		// PriceList
		mTab.setValue("M_PriceList_ID", priceList.get_ID());

		//	Tax Included
		mTab.setValue("IsTaxIncluded", priceList.isTaxIncluded());

		//	Price Limit Enforce
		Env.setContext(ctx, WindowNo, "EnforcePriceLimit", priceList.isEnforcePriceLimit());

		//	PriceList Version
		Env.setContext(ctx, WindowNo, "M_PriceList_Version_ID", priceListVersion.get_ID());

		return "";

	}

}	//	CalloutInvoice
