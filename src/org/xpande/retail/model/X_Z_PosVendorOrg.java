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

/** Generated Model for Z_PosVendorOrg
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_PosVendorOrg extends PO implements I_Z_PosVendorOrg, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180209L;

    /** Standard Constructor */
    public X_Z_PosVendorOrg (Properties ctx, int Z_PosVendorOrg_ID, String trxName)
    {
      super (ctx, Z_PosVendorOrg_ID, trxName);
      /** if (Z_PosVendorOrg_ID == 0)
        {
			setAD_OrgTrx_ID (0);
			setZ_PosVendor_ID (0);
			setZ_PosVendorOrg_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_PosVendorOrg (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_PosVendorOrg[")
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

	public I_Z_PosVendor getZ_PosVendor() throws RuntimeException
    {
		return (I_Z_PosVendor)MTable.get(getCtx(), I_Z_PosVendor.Table_Name)
			.getPO(getZ_PosVendor_ID(), get_TrxName());	}

	/** Set Z_PosVendor ID.
		@param Z_PosVendor_ID Z_PosVendor ID	  */
	public void setZ_PosVendor_ID (int Z_PosVendor_ID)
	{
		if (Z_PosVendor_ID < 1) 
			set_Value (COLUMNNAME_Z_PosVendor_ID, null);
		else 
			set_Value (COLUMNNAME_Z_PosVendor_ID, Integer.valueOf(Z_PosVendor_ID));
	}

	/** Get Z_PosVendor ID.
		@return Z_PosVendor ID	  */
	public int getZ_PosVendor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_PosVendor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_PosVendorOrg ID.
		@param Z_PosVendorOrg_ID Z_PosVendorOrg ID	  */
	public void setZ_PosVendorOrg_ID (int Z_PosVendorOrg_ID)
	{
		if (Z_PosVendorOrg_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_PosVendorOrg_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_PosVendorOrg_ID, Integer.valueOf(Z_PosVendorOrg_ID));
	}

	/** Get Z_PosVendorOrg ID.
		@return Z_PosVendorOrg ID	  */
	public int getZ_PosVendorOrg_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_PosVendorOrg_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}