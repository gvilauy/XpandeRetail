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

/** Generated Model for Z_ActualizacionPVPLin
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_ActualizacionPVPLin extends PO implements I_Z_ActualizacionPVPLin, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180515L;

    /** Standard Constructor */
    public X_Z_ActualizacionPVPLin (Properties ctx, int Z_ActualizacionPVPLin_ID, String trxName)
    {
      super (ctx, Z_ActualizacionPVPLin_ID, trxName);
      /** if (Z_ActualizacionPVPLin_ID == 0)
        {
			setC_Currency_ID (0);
			setDistinctPriceSO (false);
// N
			setIsConfirmed (true);
// Y
			setM_Product_ID (0);
			setZ_ActualizacionPVP_ID (0);
			setZ_ActualizacionPVPLin_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_ActualizacionPVPLin (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_ActualizacionPVPLin[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public I_C_TaxCategory getC_TaxCategory() throws RuntimeException
    {
		return (I_C_TaxCategory)MTable.get(getCtx(), I_C_TaxCategory.Table_Name)
			.getPO(getC_TaxCategory_ID(), get_TrxName());	}

	/** Set Tax Category.
		@param C_TaxCategory_ID 
		Tax Category
	  */
	public void setC_TaxCategory_ID (int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID < 1) 
			set_Value (COLUMNNAME_C_TaxCategory_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxCategory_ID, Integer.valueOf(C_TaxCategory_ID));
	}

	/** Get Tax Category.
		@return Tax Category
	  */
	public int getC_TaxCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_UOM getC_UOM() throws RuntimeException
    {
		return (I_C_UOM)MTable.get(getCtx(), I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DateValidSO.
		@param DateValidSO 
		Fecha Vigencia Venta
	  */
	public void setDateValidSO (Timestamp DateValidSO)
	{
		set_Value (COLUMNNAME_DateValidSO, DateValidSO);
	}

	/** Get DateValidSO.
		@return Fecha Vigencia Venta
	  */
	public Timestamp getDateValidSO () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateValidSO);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
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

	/** Set InternalCode.
		@param InternalCode 
		Código Interno
	  */
	public void setInternalCode (String InternalCode)
	{
		set_Value (COLUMNNAME_InternalCode, InternalCode);
	}

	/** Get InternalCode.
		@return Código Interno
	  */
	public String getInternalCode () 
	{
		return (String)get_Value(COLUMNNAME_InternalCode);
	}

	/** Set Confirmed.
		@param IsConfirmed 
		Assignment is confirmed
	  */
	public void setIsConfirmed (boolean IsConfirmed)
	{
		set_Value (COLUMNNAME_IsConfirmed, Boolean.valueOf(IsConfirmed));
	}

	/** Get Confirmed.
		@return Assignment is confirmed
	  */
	public boolean isConfirmed () 
	{
		Object oo = get_Value(COLUMNNAME_IsConfirmed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
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

	public I_Z_ActualizacionPVP getZ_ActualizacionPVP() throws RuntimeException
    {
		return (I_Z_ActualizacionPVP)MTable.get(getCtx(), I_Z_ActualizacionPVP.Table_Name)
			.getPO(getZ_ActualizacionPVP_ID(), get_TrxName());	}

	/** Set Z_ActualizacionPVP ID.
		@param Z_ActualizacionPVP_ID Z_ActualizacionPVP ID	  */
	public void setZ_ActualizacionPVP_ID (int Z_ActualizacionPVP_ID)
	{
		if (Z_ActualizacionPVP_ID < 1) 
			set_Value (COLUMNNAME_Z_ActualizacionPVP_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ActualizacionPVP_ID, Integer.valueOf(Z_ActualizacionPVP_ID));
	}

	/** Get Z_ActualizacionPVP ID.
		@return Z_ActualizacionPVP ID	  */
	public int getZ_ActualizacionPVP_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ActualizacionPVP_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_ActualizacionPVPLin ID.
		@param Z_ActualizacionPVPLin_ID Z_ActualizacionPVPLin ID	  */
	public void setZ_ActualizacionPVPLin_ID (int Z_ActualizacionPVPLin_ID)
	{
		if (Z_ActualizacionPVPLin_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_ActualizacionPVPLin_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_ActualizacionPVPLin_ID, Integer.valueOf(Z_ActualizacionPVPLin_ID));
	}

	/** Get Z_ActualizacionPVPLin ID.
		@return Z_ActualizacionPVPLin ID	  */
	public int getZ_ActualizacionPVPLin_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ActualizacionPVPLin_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_ProductoFamilia getZ_ProductoFamilia() throws RuntimeException
    {
		return (I_Z_ProductoFamilia)MTable.get(getCtx(), I_Z_ProductoFamilia.Table_Name)
			.getPO(getZ_ProductoFamilia_ID(), get_TrxName());	}

	/** Set Z_ProductoFamilia ID.
		@param Z_ProductoFamilia_ID Z_ProductoFamilia ID	  */
	public void setZ_ProductoFamilia_ID (int Z_ProductoFamilia_ID)
	{
		if (Z_ProductoFamilia_ID < 1) 
			set_Value (COLUMNNAME_Z_ProductoFamilia_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ProductoFamilia_ID, Integer.valueOf(Z_ProductoFamilia_ID));
	}

	/** Get Z_ProductoFamilia ID.
		@return Z_ProductoFamilia ID	  */
	public int getZ_ProductoFamilia_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ProductoFamilia_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_ProductoRubro getZ_ProductoRubro() throws RuntimeException
    {
		return (I_Z_ProductoRubro)MTable.get(getCtx(), I_Z_ProductoRubro.Table_Name)
			.getPO(getZ_ProductoRubro_ID(), get_TrxName());	}

	/** Set Z_ProductoRubro ID.
		@param Z_ProductoRubro_ID Z_ProductoRubro ID	  */
	public void setZ_ProductoRubro_ID (int Z_ProductoRubro_ID)
	{
		if (Z_ProductoRubro_ID < 1) 
			set_Value (COLUMNNAME_Z_ProductoRubro_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ProductoRubro_ID, Integer.valueOf(Z_ProductoRubro_ID));
	}

	/** Get Z_ProductoRubro ID.
		@return Z_ProductoRubro ID	  */
	public int getZ_ProductoRubro_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ProductoRubro_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_ProductoSeccion getZ_ProductoSeccion() throws RuntimeException
    {
		return (I_Z_ProductoSeccion)MTable.get(getCtx(), I_Z_ProductoSeccion.Table_Name)
			.getPO(getZ_ProductoSeccion_ID(), get_TrxName());	}

	/** Set Z_ProductoSeccion ID.
		@param Z_ProductoSeccion_ID Z_ProductoSeccion ID	  */
	public void setZ_ProductoSeccion_ID (int Z_ProductoSeccion_ID)
	{
		if (Z_ProductoSeccion_ID < 1) 
			set_Value (COLUMNNAME_Z_ProductoSeccion_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ProductoSeccion_ID, Integer.valueOf(Z_ProductoSeccion_ID));
	}

	/** Get Z_ProductoSeccion ID.
		@return Z_ProductoSeccion ID	  */
	public int getZ_ProductoSeccion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ProductoSeccion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_ProductoSubfamilia getZ_ProductoSubfamilia() throws RuntimeException
    {
		return (I_Z_ProductoSubfamilia)MTable.get(getCtx(), I_Z_ProductoSubfamilia.Table_Name)
			.getPO(getZ_ProductoSubfamilia_ID(), get_TrxName());	}

	/** Set Z_ProductoSubfamilia ID.
		@param Z_ProductoSubfamilia_ID Z_ProductoSubfamilia ID	  */
	public void setZ_ProductoSubfamilia_ID (int Z_ProductoSubfamilia_ID)
	{
		if (Z_ProductoSubfamilia_ID < 1) 
			set_Value (COLUMNNAME_Z_ProductoSubfamilia_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ProductoSubfamilia_ID, Integer.valueOf(Z_ProductoSubfamilia_ID));
	}

	/** Get Z_ProductoSubfamilia ID.
		@return Z_ProductoSubfamilia ID	  */
	public int getZ_ProductoSubfamilia_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ProductoSubfamilia_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}