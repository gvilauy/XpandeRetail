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

/** Generated Model for Z_ProductoTaxOrg
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_ProductoTaxOrg extends PO implements I_Z_ProductoTaxOrg, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20181211L;

    /** Standard Constructor */
    public X_Z_ProductoTaxOrg (Properties ctx, int Z_ProductoTaxOrg_ID, String trxName)
    {
      super (ctx, Z_ProductoTaxOrg_ID, trxName);
      /** if (Z_ProductoTaxOrg_ID == 0)
        {
			setAD_OrgTrx_ID (0);
			setM_Product_ID (0);
			setZ_ProductoTaxOrg_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_ProductoTaxOrg (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_ProductoTaxOrg[")
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

	/** Set C_TaxCategory_ID_2.
		@param C_TaxCategory_ID_2 
		Categoria de impuesto secundaria
	  */
	public void setC_TaxCategory_ID_2 (int C_TaxCategory_ID_2)
	{
		set_Value (COLUMNNAME_C_TaxCategory_ID_2, Integer.valueOf(C_TaxCategory_ID_2));
	}

	/** Get C_TaxCategory_ID_2.
		@return Categoria de impuesto secundaria
	  */
	public int getC_TaxCategory_ID_2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxCategory_ID_2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_TaxCategory_ID_3.
		@param C_TaxCategory_ID_3 
		Categoría de impuesto terciaria
	  */
	public void setC_TaxCategory_ID_3 (int C_TaxCategory_ID_3)
	{
		set_Value (COLUMNNAME_C_TaxCategory_ID_3, Integer.valueOf(C_TaxCategory_ID_3));
	}

	/** Get C_TaxCategory_ID_3.
		@return Categoría de impuesto terciaria
	  */
	public int getC_TaxCategory_ID_3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxCategory_ID_3);
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

	/** Set Z_ProductoTaxOrg ID.
		@param Z_ProductoTaxOrg_ID Z_ProductoTaxOrg ID	  */
	public void setZ_ProductoTaxOrg_ID (int Z_ProductoTaxOrg_ID)
	{
		if (Z_ProductoTaxOrg_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_ProductoTaxOrg_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_ProductoTaxOrg_ID, Integer.valueOf(Z_ProductoTaxOrg_ID));
	}

	/** Get Z_ProductoTaxOrg ID.
		@return Z_ProductoTaxOrg ID	  */
	public int getZ_ProductoTaxOrg_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ProductoTaxOrg_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}