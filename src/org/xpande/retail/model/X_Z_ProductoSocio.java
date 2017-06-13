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
/** Generated Model - DO NOT CHANGE */
package org.xpande.retail.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for Z_ProductoSocio
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_ProductoSocio extends PO implements I_Z_ProductoSocio, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170613L;

    /** Standard Constructor */
    public X_Z_ProductoSocio (Properties ctx, int Z_ProductoSocio_ID, String trxName)
    {
      super (ctx, Z_ProductoSocio_ID, trxName);
      /** if (Z_ProductoSocio_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_Currency_ID (0);
			setDistinctPricePO (false);
// N
			setDistinctPriceSO (false);
// N
			setM_PriceList_ID (0);
			setM_Product_ID (0);
			setPriceFinal (Env.ZERO);
			setPriceList (Env.ZERO);
			setPricePO (Env.ZERO);
			setZ_ProductoSocio_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_ProductoSocio (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_Z_ProductoSocio[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Currency getC_Currency() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Date Invoiced.
		@param DateInvoiced 
		Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced)
	{
		set_Value (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	/** Get Date Invoiced.
		@return Date printed on Invoice
	  */
	public Timestamp getDateInvoiced () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateInvoiced);
	}

	/** Set DateValidPO.
		@param DateValidPO 
		Fecha Vigencia Compra
	  */
	public void setDateValidPO (Timestamp DateValidPO)
	{
		set_Value (COLUMNNAME_DateValidPO, DateValidPO);
	}

	/** Get DateValidPO.
		@return Fecha Vigencia Compra
	  */
	public Timestamp getDateValidPO () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateValidPO);
	}

	/** Set DateValidSO.
		@param DateValidSO 
		Fecha Vigencia Compra
	  */
	public void setDateValidSO (Timestamp DateValidSO)
	{
		set_Value (COLUMNNAME_DateValidSO, DateValidSO);
	}

	/** Get DateValidSO.
		@return Fecha Vigencia Compra
	  */
	public Timestamp getDateValidSO () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateValidSO);
	}

	/** Set DistinctPricePO.
		@param DistinctPricePO DistinctPricePO	  */
	public void setDistinctPricePO (boolean DistinctPricePO)
	{
		set_Value (COLUMNNAME_DistinctPricePO, Boolean.valueOf(DistinctPricePO));
	}

	/** Get DistinctPricePO.
		@return DistinctPricePO	  */
	public boolean isDistinctPricePO () 
	{
		Object oo = get_Value(COLUMNNAME_DistinctPricePO);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set DistinctPriceSO.
		@param DistinctPriceSO DistinctPriceSO	  */
	public void setDistinctPriceSO (boolean DistinctPriceSO)
	{
		set_Value (COLUMNNAME_DistinctPriceSO, Boolean.valueOf(DistinctPriceSO));
	}

	/** Get DistinctPriceSO.
		@return DistinctPriceSO	  */
	public boolean isDistinctPriceSO () 
	{
		Object oo = get_Value(COLUMNNAME_DistinctPriceSO);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set DtoFinFactura.
		@param DtoFinFactura 
		Descuento Financiero en Factura
	  */
	public void setDtoFinFactura (BigDecimal DtoFinFactura)
	{
		set_Value (COLUMNNAME_DtoFinFactura, DtoFinFactura);
	}

	/** Get DtoFinFactura.
		@return Descuento Financiero en Factura
	  */
	public BigDecimal getDtoFinFactura () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DtoFinFactura);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set DtoFinPago.
		@param DtoFinPago 
		Descuento financiero al Pago
	  */
	public void setDtoFinPago (BigDecimal DtoFinPago)
	{
		set_Value (COLUMNNAME_DtoFinPago, DtoFinPago);
	}

	/** Get DtoFinPago.
		@return Descuento financiero al Pago
	  */
	public BigDecimal getDtoFinPago () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DtoFinPago);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set DtoNCPago.
		@param DtoNCPago 
		Descuento nota de crédito al pago
	  */
	public void setDtoNCPago (BigDecimal DtoNCPago)
	{
		set_Value (COLUMNNAME_DtoNCPago, DtoNCPago);
	}

	/** Get DtoNCPago.
		@return Descuento nota de crédito al pago
	  */
	public BigDecimal getDtoNCPago () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DtoNCPago);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set DtoOpeFactura.
		@param DtoOpeFactura 
		Descuentos operativos en factura
	  */
	public void setDtoOpeFactura (BigDecimal DtoOpeFactura)
	{
		set_Value (COLUMNNAME_DtoOpeFactura, DtoOpeFactura);
	}

	/** Get DtoOpeFactura.
		@return Descuentos operativos en factura
	  */
	public BigDecimal getDtoOpeFactura () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DtoOpeFactura);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set DtoRetorno.
		@param DtoRetorno DtoRetorno	  */
	public void setDtoRetorno (BigDecimal DtoRetorno)
	{
		set_Value (COLUMNNAME_DtoRetorno, DtoRetorno);
	}

	/** Get DtoRetorno.
		@return DtoRetorno	  */
	public BigDecimal getDtoRetorno () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DtoRetorno);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set InvoiceNo.
		@param InvoiceNo InvoiceNo	  */
	public void setInvoiceNo (String InvoiceNo)
	{
		set_Value (COLUMNNAME_InvoiceNo, InvoiceNo);
	}

	/** Get InvoiceNo.
		@return InvoiceNo	  */
	public String getInvoiceNo () 
	{
		return (String)get_Value(COLUMNNAME_InvoiceNo);
	}

	public I_M_PriceList getM_PriceList() throws RuntimeException
    {
		return (I_M_PriceList)MTable.get(getCtx(), I_M_PriceList.Table_Name)
			.getPO(getM_PriceList_ID(), get_TrxName());	}

	/** Set Price List.
		@param M_PriceList_ID 
		Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
	}

	/** Get Price List.
		@return Unique identifier of a Price List
	  */
	public int getM_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_PriceList_ID_SO.
		@param M_PriceList_ID_SO 
		Lista de Precios de Venta
	  */
	public void setM_PriceList_ID_SO (int M_PriceList_ID_SO)
	{
		set_Value (COLUMNNAME_M_PriceList_ID_SO, Integer.valueOf(M_PriceList_ID_SO));
	}

	/** Get M_PriceList_ID_SO.
		@return Lista de Precios de Venta
	  */
	public int getM_PriceList_ID_SO () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID_SO);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_PriceList_Version getM_PriceList_Version() throws RuntimeException
    {
		return (I_M_PriceList_Version)MTable.get(getCtx(), I_M_PriceList_Version.Table_Name)
			.getPO(getM_PriceList_Version_ID(), get_TrxName());	}

	/** Set Price List Version.
		@param M_PriceList_Version_ID 
		Identifies a unique instance of a Price List
	  */
	public void setM_PriceList_Version_ID (int M_PriceList_Version_ID)
	{
		if (M_PriceList_Version_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_Version_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_Version_ID, Integer.valueOf(M_PriceList_Version_ID));
	}

	/** Get Price List Version.
		@return Identifies a unique instance of a Price List
	  */
	public int getM_PriceList_Version_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_Version_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_PriceList_Version_ID_SO.
		@param M_PriceList_Version_ID_SO 
		Version de Lista de Precios de Venta
	  */
	public void setM_PriceList_Version_ID_SO (int M_PriceList_Version_ID_SO)
	{
		set_Value (COLUMNNAME_M_PriceList_Version_ID_SO, Integer.valueOf(M_PriceList_Version_ID_SO));
	}

	/** Get M_PriceList_Version_ID_SO.
		@return Version de Lista de Precios de Venta
	  */
	public int getM_PriceList_Version_ID_SO () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_Version_ID_SO);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_Product getM_Product() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PriceFinal.
		@param PriceFinal PriceFinal	  */
	public void setPriceFinal (BigDecimal PriceFinal)
	{
		set_Value (COLUMNNAME_PriceFinal, PriceFinal);
	}

	/** Get PriceFinal.
		@return PriceFinal	  */
	public BigDecimal getPriceFinal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceFinal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PriceFinalMargin.
		@param PriceFinalMargin PriceFinalMargin	  */
	public void setPriceFinalMargin (BigDecimal PriceFinalMargin)
	{
		set_Value (COLUMNNAME_PriceFinalMargin, PriceFinalMargin);
	}

	/** Get PriceFinalMargin.
		@return PriceFinalMargin	  */
	public BigDecimal getPriceFinalMargin () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceFinalMargin);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Price Invoiced.
		@param PriceInvoiced 
		The priced invoiced to the customer (in the currency of the customer's AR price list) - 0 for default price
	  */
	public void setPriceInvoiced (BigDecimal PriceInvoiced)
	{
		set_Value (COLUMNNAME_PriceInvoiced, PriceInvoiced);
	}

	/** Get Price Invoiced.
		@return The priced invoiced to the customer (in the currency of the customer's AR price list) - 0 for default price
	  */
	public BigDecimal getPriceInvoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceInvoiced);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PriceInvoicedMargin.
		@param PriceInvoicedMargin PriceInvoicedMargin	  */
	public void setPriceInvoicedMargin (BigDecimal PriceInvoicedMargin)
	{
		set_Value (COLUMNNAME_PriceInvoicedMargin, PriceInvoicedMargin);
	}

	/** Get PriceInvoicedMargin.
		@return PriceInvoicedMargin	  */
	public BigDecimal getPriceInvoicedMargin () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceInvoicedMargin);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set List Price.
		@param PriceList 
		List Price
	  */
	public void setPriceList (BigDecimal PriceList)
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	/** Get List Price.
		@return List Price
	  */
	public BigDecimal getPriceList () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceList);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PO Price.
		@param PricePO 
		Price based on a purchase order
	  */
	public void setPricePO (BigDecimal PricePO)
	{
		set_Value (COLUMNNAME_PricePO, PricePO);
	}

	/** Get PO Price.
		@return Price based on a purchase order
	  */
	public BigDecimal getPricePO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PricePO);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PricePOMargin.
		@param PricePOMargin PricePOMargin	  */
	public void setPricePOMargin (BigDecimal PricePOMargin)
	{
		set_Value (COLUMNNAME_PricePOMargin, PricePOMargin);
	}

	/** Get PricePOMargin.
		@return PricePOMargin	  */
	public BigDecimal getPricePOMargin () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PricePOMargin);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PriceSO.
		@param PriceSO 
		PriceSO
	  */
	public void setPriceSO (BigDecimal PriceSO)
	{
		set_Value (COLUMNNAME_PriceSO, PriceSO);
	}

	/** Get PriceSO.
		@return PriceSO
	  */
	public BigDecimal getPriceSO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceSO);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Partner Product Key.
		@param VendorProductNo 
		Product Key of the Business Partner
	  */
	public void setVendorProductNo (String VendorProductNo)
	{
		set_Value (COLUMNNAME_VendorProductNo, VendorProductNo);
	}

	/** Get Partner Product Key.
		@return Product Key of the Business Partner
	  */
	public String getVendorProductNo () 
	{
		return (String)get_Value(COLUMNNAME_VendorProductNo);
	}

	/** Set Z_ProductoSocio ID.
		@param Z_ProductoSocio_ID Z_ProductoSocio ID	  */
	public void setZ_ProductoSocio_ID (int Z_ProductoSocio_ID)
	{
		if (Z_ProductoSocio_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_ProductoSocio_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_ProductoSocio_ID, Integer.valueOf(Z_ProductoSocio_ID));
	}

	/** Get Z_ProductoSocio ID.
		@return Z_ProductoSocio ID	  */
	public int getZ_ProductoSocio_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ProductoSocio_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}