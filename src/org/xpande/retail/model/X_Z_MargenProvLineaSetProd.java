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

/** Generated Model for Z_MargenProvLineaSetProd
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_MargenProvLineaSetProd extends PO implements I_Z_MargenProvLineaSetProd, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20171122L;

    /** Standard Constructor */
    public X_Z_MargenProvLineaSetProd (Properties ctx, int Z_MargenProvLineaSetProd_ID, String trxName)
    {
      super (ctx, Z_MargenProvLineaSetProd_ID, trxName);
      /** if (Z_MargenProvLineaSetProd_ID == 0)
        {
			setM_Product_ID (0);
			setZ_MargenProvLineaSet_ID (0);
			setZ_MargenProvLineaSetProd_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_MargenProvLineaSetProd (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_MargenProvLineaSetProd[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Margin %.
		@param Margin 
		Margin for a product as a percentage
	  */
	public void setMargin (BigDecimal Margin)
	{
		set_Value (COLUMNNAME_Margin, Margin);
	}

	/** Get Margin %.
		@return Margin for a product as a percentage
	  */
	public BigDecimal getMargin () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Margin);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public org.xpande.retail.model.I_Z_MargenProvLineaSet getZ_MargenProvLineaSet() throws RuntimeException
    {
		return (org.xpande.retail.model.I_Z_MargenProvLineaSet)MTable.get(getCtx(), org.xpande.retail.model.I_Z_MargenProvLineaSet.Table_Name)
			.getPO(getZ_MargenProvLineaSet_ID(), get_TrxName());	}

	/** Set Z_MargenProvLineaSet ID.
		@param Z_MargenProvLineaSet_ID Z_MargenProvLineaSet ID	  */
	public void setZ_MargenProvLineaSet_ID (int Z_MargenProvLineaSet_ID)
	{
		if (Z_MargenProvLineaSet_ID < 1) 
			set_Value (COLUMNNAME_Z_MargenProvLineaSet_ID, null);
		else 
			set_Value (COLUMNNAME_Z_MargenProvLineaSet_ID, Integer.valueOf(Z_MargenProvLineaSet_ID));
	}

	/** Get Z_MargenProvLineaSet ID.
		@return Z_MargenProvLineaSet ID	  */
	public int getZ_MargenProvLineaSet_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MargenProvLineaSet_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_MargenProvLineaSetProd ID.
		@param Z_MargenProvLineaSetProd_ID Z_MargenProvLineaSetProd ID	  */
	public void setZ_MargenProvLineaSetProd_ID (int Z_MargenProvLineaSetProd_ID)
	{
		if (Z_MargenProvLineaSetProd_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_MargenProvLineaSetProd_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_MargenProvLineaSetProd_ID, Integer.valueOf(Z_MargenProvLineaSetProd_ID));
	}

	/** Get Z_MargenProvLineaSetProd ID.
		@return Z_MargenProvLineaSetProd ID	  */
	public int getZ_MargenProvLineaSetProd_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MargenProvLineaSetProd_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}