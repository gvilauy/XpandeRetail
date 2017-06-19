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
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for Z_PautaComercialSetProd
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_PautaComercialSetProd extends PO implements I_Z_PautaComercialSetProd, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170618L;

    /** Standard Constructor */
    public X_Z_PautaComercialSetProd (Properties ctx, int Z_PautaComercialSetProd_ID, String trxName)
    {
      super (ctx, Z_PautaComercialSetProd_ID, trxName);
      /** if (Z_PautaComercialSetProd_ID == 0)
        {
			setM_Product_ID (0);
			setZ_PautaComercialSet_ID (0);
			setZ_PautaComercialSetProd_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_PautaComercialSetProd (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_PautaComercialSetProd[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public I_Z_PautaComercialSet getZ_PautaComercialSet() throws RuntimeException
    {
		return (I_Z_PautaComercialSet)MTable.get(getCtx(), I_Z_PautaComercialSet.Table_Name)
			.getPO(getZ_PautaComercialSet_ID(), get_TrxName());	}

	/** Set Z_PautaComercialSet ID.
		@param Z_PautaComercialSet_ID Z_PautaComercialSet ID	  */
	public void setZ_PautaComercialSet_ID (int Z_PautaComercialSet_ID)
	{
		if (Z_PautaComercialSet_ID < 1) 
			set_Value (COLUMNNAME_Z_PautaComercialSet_ID, null);
		else 
			set_Value (COLUMNNAME_Z_PautaComercialSet_ID, Integer.valueOf(Z_PautaComercialSet_ID));
	}

	/** Get Z_PautaComercialSet ID.
		@return Z_PautaComercialSet ID	  */
	public int getZ_PautaComercialSet_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_PautaComercialSet_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_PautaComercialSetProd ID.
		@param Z_PautaComercialSetProd_ID Z_PautaComercialSetProd ID	  */
	public void setZ_PautaComercialSetProd_ID (int Z_PautaComercialSetProd_ID)
	{
		if (Z_PautaComercialSetProd_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_PautaComercialSetProd_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_PautaComercialSetProd_ID, Integer.valueOf(Z_PautaComercialSetProd_ID));
	}

	/** Get Z_PautaComercialSetProd ID.
		@return Z_PautaComercialSetProd ID	  */
	public int getZ_PautaComercialSetProd_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_PautaComercialSetProd_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}