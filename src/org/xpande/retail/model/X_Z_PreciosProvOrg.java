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

/** Generated Model for Z_PreciosProvOrg
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_PreciosProvOrg extends PO implements I_Z_PreciosProvOrg, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170615L;

    /** Standard Constructor */
    public X_Z_PreciosProvOrg (Properties ctx, int Z_PreciosProvOrg_ID, String trxName)
    {
      super (ctx, Z_PreciosProvOrg_ID, trxName);
      /** if (Z_PreciosProvOrg_ID == 0)
        {
			setAD_OrgTrx_ID (0);
			setIsSelected (true);
// Y
			setZ_PreciosProvCab_ID (0);
			setZ_PreciosProvOrg_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_PreciosProvOrg (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_PreciosProvOrg[")
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

	/** Set Selected.
		@param IsSelected Selected	  */
	public void setIsSelected (boolean IsSelected)
	{
		set_Value (COLUMNNAME_IsSelected, Boolean.valueOf(IsSelected));
	}

	/** Get Selected.
		@return Selected	  */
	public boolean isSelected () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_Z_PreciosProvCab getZ_PreciosProvCab() throws RuntimeException
    {
		return (I_Z_PreciosProvCab)MTable.get(getCtx(), I_Z_PreciosProvCab.Table_Name)
			.getPO(getZ_PreciosProvCab_ID(), get_TrxName());	}

	/** Set Z_PreciosProvCab ID.
		@param Z_PreciosProvCab_ID Z_PreciosProvCab ID	  */
	public void setZ_PreciosProvCab_ID (int Z_PreciosProvCab_ID)
	{
		if (Z_PreciosProvCab_ID < 1) 
			set_Value (COLUMNNAME_Z_PreciosProvCab_ID, null);
		else 
			set_Value (COLUMNNAME_Z_PreciosProvCab_ID, Integer.valueOf(Z_PreciosProvCab_ID));
	}

	/** Get Z_PreciosProvCab ID.
		@return Z_PreciosProvCab ID	  */
	public int getZ_PreciosProvCab_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_PreciosProvCab_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_PreciosProvOrg ID.
		@param Z_PreciosProvOrg_ID Z_PreciosProvOrg ID	  */
	public void setZ_PreciosProvOrg_ID (int Z_PreciosProvOrg_ID)
	{
		if (Z_PreciosProvOrg_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_PreciosProvOrg_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_PreciosProvOrg_ID, Integer.valueOf(Z_PreciosProvOrg_ID));
	}

	/** Get Z_PreciosProvOrg ID.
		@return Z_PreciosProvOrg ID	  */
	public int getZ_PreciosProvOrg_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_PreciosProvOrg_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}