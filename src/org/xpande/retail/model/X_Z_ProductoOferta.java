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

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for Z_ProductoOferta
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_ProductoOferta extends PO implements I_Z_ProductoOferta, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180110L;

    /** Standard Constructor */
    public X_Z_ProductoOferta (Properties ctx, int Z_ProductoOferta_ID, String trxName)
    {
      super (ctx, Z_ProductoOferta_ID, trxName);
      /** if (Z_ProductoOferta_ID == 0)
        {
			setEndDate (new Timestamp( System.currentTimeMillis() ));
			setM_Product_ID (0);
			setStartDate (new Timestamp( System.currentTimeMillis() ));
			setZ_OfertaVenta_ID (0);
			setZ_ProductoOferta_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_ProductoOferta (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_ProductoOferta[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set End Date.
		@param EndDate 
		Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Timestamp getEndDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndDate);
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

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
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

	/** Set Z_ProductoOferta ID.
		@param Z_ProductoOferta_ID Z_ProductoOferta ID	  */
	public void setZ_ProductoOferta_ID (int Z_ProductoOferta_ID)
	{
		if (Z_ProductoOferta_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_ProductoOferta_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_ProductoOferta_ID, Integer.valueOf(Z_ProductoOferta_ID));
	}

	/** Get Z_ProductoOferta ID.
		@return Z_ProductoOferta ID	  */
	public int getZ_ProductoOferta_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ProductoOferta_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}