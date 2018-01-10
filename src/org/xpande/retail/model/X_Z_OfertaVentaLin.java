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

/** Generated Model for Z_OfertaVentaLin
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_OfertaVentaLin extends PO implements I_Z_OfertaVentaLin, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180110L;

    /** Standard Constructor */
    public X_Z_OfertaVentaLin (Properties ctx, int Z_OfertaVentaLin_ID, String trxName)
    {
      super (ctx, Z_OfertaVentaLin_ID, trxName);
      /** if (Z_OfertaVentaLin_ID == 0)
        {
			setC_Currency_ID_SO (0);
			setM_Product_ID (0);
			setNewPriceSO (Env.ZERO);
			setZ_OfertaVenta_ID (0);
			setZ_OfertaVentaLin_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_OfertaVentaLin (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_OfertaVentaLin[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set C_Currency_ID_SO.
		@param C_Currency_ID_SO 
		Moneda de Venta
	  */
	public void setC_Currency_ID_SO (int C_Currency_ID_SO)
	{
		set_Value (COLUMNNAME_C_Currency_ID_SO, Integer.valueOf(C_Currency_ID_SO));
	}

	/** Get C_Currency_ID_SO.
		@return Moneda de Venta
	  */
	public int getC_Currency_ID_SO () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID_SO);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_Z_OfertaVenta getZ_OfertaVenta() throws RuntimeException
    {
		return (I_Z_OfertaVenta)MTable.get(getCtx(), I_Z_OfertaVenta.Table_Name)
			.getPO(getZ_OfertaVenta_ID(), get_TrxName());	}

	/** Set Z_OfertaVenta ID.
		@param Z_OfertaVenta_ID Z_OfertaVenta ID	  */
	public void setZ_OfertaVenta_ID (int Z_OfertaVenta_ID)
	{
		if (Z_OfertaVenta_ID < 1) 
			set_Value (COLUMNNAME_Z_OfertaVenta_ID, null);
		else 
			set_Value (COLUMNNAME_Z_OfertaVenta_ID, Integer.valueOf(Z_OfertaVenta_ID));
	}

	/** Get Z_OfertaVenta ID.
		@return Z_OfertaVenta ID	  */
	public int getZ_OfertaVenta_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_OfertaVenta_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_OfertaVentaLin ID.
		@param Z_OfertaVentaLin_ID Z_OfertaVentaLin ID	  */
	public void setZ_OfertaVentaLin_ID (int Z_OfertaVentaLin_ID)
	{
		if (Z_OfertaVentaLin_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_OfertaVentaLin_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_OfertaVentaLin_ID, Integer.valueOf(Z_OfertaVentaLin_ID));
	}

	/** Get Z_OfertaVentaLin ID.
		@return Z_OfertaVentaLin ID	  */
	public int getZ_OfertaVentaLin_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_OfertaVentaLin_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}