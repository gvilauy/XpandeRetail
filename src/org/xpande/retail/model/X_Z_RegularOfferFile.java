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

/** Generated Model for Z_RegularOfferFile
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1 - $Id$ */
public class X_Z_RegularOfferFile extends PO implements I_Z_RegularOfferFile, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20221218L;

    /** Standard Constructor */
    public X_Z_RegularOfferFile (Properties ctx, int Z_RegularOfferFile_ID, String trxName)
    {
      super (ctx, Z_RegularOfferFile_ID, trxName);
      /** if (Z_RegularOfferFile_ID == 0)
        {
			setI_IsImported (false);
// N
			setIsExclude (false);
// N
			setIsValid (false);
// N
			setProcessed (false);
// N
			setZ_RegularOfferFile_ID (0);
			setZ_RegularOffer_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_RegularOfferFile (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_RegularOfferFile[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Error Msg.
		@param ErrorMsg Error Msg	  */
	public void setErrorMsg (String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	/** Get Error Msg.
		@return Error Msg	  */
	public String getErrorMsg () 
	{
		return (String)get_Value(COLUMNNAME_ErrorMsg);
	}

	/** Set FileLineText.
		@param FileLineText FileLineText	  */
	public void setFileLineText (String FileLineText)
	{
		set_Value (COLUMNNAME_FileLineText, FileLineText);
	}

	/** Get FileLineText.
		@return FileLineText	  */
	public String getFileLineText () 
	{
		return (String)get_Value(COLUMNNAME_FileLineText);
	}

	/** Set Imported.
		@param I_IsImported 
		Has this import been processed
	  */
	public void setI_IsImported (boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
	}

	/** Get Imported.
		@return Has this import been processed
	  */
	public boolean isI_IsImported () 
	{
		Object oo = get_Value(COLUMNNAME_I_IsImported);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Exclude.
		@param IsExclude 
		Exclude access to the data - if not selected Include access to the data
	  */
	public void setIsExclude (boolean IsExclude)
	{
		set_Value (COLUMNNAME_IsExclude, Boolean.valueOf(IsExclude));
	}

	/** Get Exclude.
		@return Exclude access to the data - if not selected Include access to the data
	  */
	public boolean isExclude () 
	{
		Object oo = get_Value(COLUMNNAME_IsExclude);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Valid.
		@param IsValid 
		Element is valid
	  */
	public void setIsValid (boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, Boolean.valueOf(IsValid));
	}

	/** Get Valid.
		@return Element is valid
	  */
	public boolean isValid () 
	{
		Object oo = get_Value(COLUMNNAME_IsValid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Line.
		@param LineNo 
		Line No
	  */
	public void setLineNo (int LineNo)
	{
		set_Value (COLUMNNAME_LineNo, Integer.valueOf(LineNo));
	}

	/** Get Line.
		@return Line No
	  */
	public int getLineNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LineNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ProdCode.
		@param ProdCode 
		ProdCode
	  */
	public void setProdCode (String ProdCode)
	{
		set_Value (COLUMNNAME_ProdCode, ProdCode);
	}

	/** Get ProdCode.
		@return ProdCode
	  */
	public String getProdCode () 
	{
		return (String)get_Value(COLUMNNAME_ProdCode);
	}

	/** Set ProdName.
		@param ProdName 
		ProdName
	  */
	public void setProdName (String ProdName)
	{
		set_Value (COLUMNNAME_ProdName, ProdName);
	}

	/** Get ProdName.
		@return ProdName
	  */
	public String getProdName () 
	{
		return (String)get_Value(COLUMNNAME_ProdName);
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

	/** Set UPC/EAN.
		@param UPC 
		Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public void setUPC (String UPC)
	{
		set_Value (COLUMNNAME_UPC, UPC);
	}

	/** Get UPC/EAN.
		@return Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public String getUPC () 
	{
		return (String)get_Value(COLUMNNAME_UPC);
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

	/** Set Z_RegularOfferFile ID.
		@param Z_RegularOfferFile_ID Z_RegularOfferFile ID	  */
	public void setZ_RegularOfferFile_ID (int Z_RegularOfferFile_ID)
	{
		if (Z_RegularOfferFile_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_RegularOfferFile_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_RegularOfferFile_ID, Integer.valueOf(Z_RegularOfferFile_ID));
	}

	/** Get Z_RegularOfferFile ID.
		@return Z_RegularOfferFile ID	  */
	public int getZ_RegularOfferFile_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_RegularOfferFile_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_RegularOffer getZ_RegularOffer() throws RuntimeException
    {
		return (I_Z_RegularOffer)MTable.get(getCtx(), I_Z_RegularOffer.Table_Name)
			.getPO(getZ_RegularOffer_ID(), get_TrxName());	}

	/** Set Z_RegularOffer ID.
		@param Z_RegularOffer_ID Z_RegularOffer ID	  */
	public void setZ_RegularOffer_ID (int Z_RegularOffer_ID)
	{
		if (Z_RegularOffer_ID < 1) 
			set_Value (COLUMNNAME_Z_RegularOffer_ID, null);
		else 
			set_Value (COLUMNNAME_Z_RegularOffer_ID, Integer.valueOf(Z_RegularOffer_ID));
	}

	/** Get Z_RegularOffer ID.
		@return Z_RegularOffer ID	  */
	public int getZ_RegularOffer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_RegularOffer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}