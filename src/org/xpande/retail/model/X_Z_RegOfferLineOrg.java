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
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for Z_RegOfferLineOrg
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1 - $Id$ */
public class X_Z_RegOfferLineOrg extends PO implements I_Z_RegOfferLineOrg, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20221218L;

    /** Standard Constructor */
    public X_Z_RegOfferLineOrg (Properties ctx, int Z_RegOfferLineOrg_ID, String trxName)
    {
      super (ctx, Z_RegOfferLineOrg_ID, trxName);
      /** if (Z_RegOfferLineOrg_ID == 0)
        {
			setAD_OrgTrx_ID (0);
			setZ_RegOfferLineOrg_ID (0);
			setZ_RegularOfferLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_RegOfferLineOrg (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_RegOfferLineOrg[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Trx Organization.
		@param AD_OrgTrx_ID 
		Performing or initiating organization
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Trx Organization.
		@return Performing or initiating organization
	  */
	public int getAD_OrgTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Break Value.
		@param BreakValue 
		Low Value of trade discount break level
	  */
	public void setBreakValue (BigDecimal BreakValue)
	{
		set_Value (COLUMNNAME_BreakValue, BreakValue);
	}

	/** Get Break Value.
		@return Low Value of trade discount break level
	  */
	public BigDecimal getBreakValue () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BreakValue);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public I_C_Currency getC_Currency_To() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID_To(), get_TrxName());	}

	/** Set Currency To.
		@param C_Currency_ID_To 
		Target currency
	  */
	public void setC_Currency_ID_To (int C_Currency_ID_To)
	{
		set_Value (COLUMNNAME_C_Currency_ID_To, Integer.valueOf(C_Currency_ID_To));
	}

	/** Get Currency To.
		@return Target currency
	  */
	public int getC_Currency_ID_To () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID_To);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Rate.
		@param CurrencyRate 
		Currency Conversion Rate
	  */
	public void setCurrencyRate (BigDecimal CurrencyRate)
	{
		set_Value (COLUMNNAME_CurrencyRate, CurrencyRate);
	}

	/** Get Rate.
		@return Currency Conversion Rate
	  */
	public BigDecimal getCurrencyRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrencyRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Discount %.
		@param Discount 
		Discount in percent
	  */
	public void setDiscount (BigDecimal Discount)
	{
		set_Value (COLUMNNAME_Discount, Discount);
	}

	/** Get Discount %.
		@return Discount in percent
	  */
	public BigDecimal getDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Discount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set NewPricePO.
		@param NewPricePO 
		Nuevo precio de compra
	  */
	public void setNewPricePO (BigDecimal NewPricePO)
	{
		set_Value (COLUMNNAME_NewPricePO, NewPricePO);
	}

	/** Get NewPricePO.
		@return Nuevo precio de compra
	  */
	public BigDecimal getNewPricePO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NewPricePO);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set NewPriceSO.
		@param NewPriceSO 
		NewPriceSO
	  */
	public void setNewPriceSO (BigDecimal NewPriceSO)
	{
		set_Value (COLUMNNAME_NewPriceSO, NewPriceSO);
	}

	/** Get NewPriceSO.
		@return NewPriceSO
	  */
	public BigDecimal getNewPriceSO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NewPriceSO);
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

	public I_C_Currency getPurchaseCurrency() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getPurchaseCurrency_ID(), get_TrxName());	}

	/** Set Moneda Compra.
		@param PurchaseCurrency_ID 
		Moneda Compra
	  */
	public void setPurchaseCurrency_ID (int PurchaseCurrency_ID)
	{
		if (PurchaseCurrency_ID < 1) 
			set_Value (COLUMNNAME_PurchaseCurrency_ID, null);
		else 
			set_Value (COLUMNNAME_PurchaseCurrency_ID, Integer.valueOf(PurchaseCurrency_ID));
	}

	/** Get Moneda Compra.
		@return Moneda Compra
	  */
	public int getPurchaseCurrency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PurchaseCurrency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cantidad Promoción.
		@param QtyPromo 
		Cantidad Promoción
	  */
	public void setQtyPromo (BigDecimal QtyPromo)
	{
		set_Value (COLUMNNAME_QtyPromo, QtyPromo);
	}

	/** Get Cantidad Promoción.
		@return Cantidad Promoción
	  */
	public BigDecimal getQtyPromo () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyPromo);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_C_Currency getSalesCurrency() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getSalesCurrency_ID(), get_TrxName());	}

	/** Set Moneda Venta.
		@param SalesCurrency_ID 
		Moneda Venta
	  */
	public void setSalesCurrency_ID (int SalesCurrency_ID)
	{
		if (SalesCurrency_ID < 1) 
			set_Value (COLUMNNAME_SalesCurrency_ID, null);
		else 
			set_Value (COLUMNNAME_SalesCurrency_ID, Integer.valueOf(SalesCurrency_ID));
	}

	/** Get Moneda Venta.
		@return Moneda Venta
	  */
	public int getSalesCurrency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesCurrency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Immutable Universally Unique Identifier.
		@param UUID 
		Immutable Universally Unique Identifier
	  */
	public void setUUID (String UUID)
	{
		set_Value (COLUMNNAME_UUID, UUID);
	}

	/** Get Immutable Universally Unique Identifier.
		@return Immutable Universally Unique Identifier
	  */
	public String getUUID () 
	{
		return (String)get_Value(COLUMNNAME_UUID);
	}

	/** Set Z_RegOfferLineOrg ID.
		@param Z_RegOfferLineOrg_ID Z_RegOfferLineOrg ID	  */
	public void setZ_RegOfferLineOrg_ID (int Z_RegOfferLineOrg_ID)
	{
		if (Z_RegOfferLineOrg_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_RegOfferLineOrg_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_RegOfferLineOrg_ID, Integer.valueOf(Z_RegOfferLineOrg_ID));
	}

	/** Get Z_RegOfferLineOrg ID.
		@return Z_RegOfferLineOrg ID	  */
	public int getZ_RegOfferLineOrg_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_RegOfferLineOrg_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_RegularOfferLine getZ_RegularOfferLine() throws RuntimeException
    {
		return (I_Z_RegularOfferLine)MTable.get(getCtx(), I_Z_RegularOfferLine.Table_Name)
			.getPO(getZ_RegularOfferLine_ID(), get_TrxName());	}

	/** Set Z_RegularOfferLine ID.
		@param Z_RegularOfferLine_ID Z_RegularOfferLine ID	  */
	public void setZ_RegularOfferLine_ID (int Z_RegularOfferLine_ID)
	{
		if (Z_RegularOfferLine_ID < 1) 
			set_Value (COLUMNNAME_Z_RegularOfferLine_ID, null);
		else 
			set_Value (COLUMNNAME_Z_RegularOfferLine_ID, Integer.valueOf(Z_RegularOfferLine_ID));
	}

	/** Get Z_RegularOfferLine ID.
		@return Z_RegularOfferLine ID	  */
	public int getZ_RegularOfferLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_RegularOfferLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}