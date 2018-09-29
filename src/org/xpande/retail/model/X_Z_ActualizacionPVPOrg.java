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

/** Generated Model for Z_ActualizacionPVPOrg
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_ActualizacionPVPOrg extends PO implements I_Z_ActualizacionPVPOrg, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170719L;

    /** Standard Constructor */
    public X_Z_ActualizacionPVPOrg (Properties ctx, int Z_ActualizacionPVPOrg_ID, String trxName)
    {
      super (ctx, Z_ActualizacionPVPOrg_ID, trxName);
      /** if (Z_ActualizacionPVPOrg_ID == 0)
        {
			setAD_OrgTrx_ID (0);
			setIsSelected (true);
// Y
			setM_PriceList_ID (0);
			setM_PriceList_Version_ID (0);
			setZ_ActualizacionPVP_ID (0);
			setZ_ActualizacionPVPOrg_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_ActualizacionPVPOrg (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_ActualizacionPVPOrg[")
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

	public I_M_PriceList getM_PriceList() throws RuntimeException
    {
		return (I_M_PriceList)MTable.get(getCtx(), I_M_PriceList.Table_Name)
			.getPO(getM_PriceList_ID(), get_TrxName());	}

	/** Set Price List.
		@param M_PriceList_ID 
		Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
	}

	/** Get Price List.
		@return Unique identifier of a Price List
	  */
	public int getM_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public org.xpande.retail.model.I_Z_ActualizacionPVP getZ_ActualizacionPVP() throws RuntimeException
    {
		return (org.xpande.retail.model.I_Z_ActualizacionPVP)MTable.get(getCtx(), org.xpande.retail.model.I_Z_ActualizacionPVP.Table_Name)
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

	/** Set Z_ActualizacionPVPOrg ID.
		@param Z_ActualizacionPVPOrg_ID Z_ActualizacionPVPOrg ID	  */
	public void setZ_ActualizacionPVPOrg_ID (int Z_ActualizacionPVPOrg_ID)
	{
		if (Z_ActualizacionPVPOrg_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_ActualizacionPVPOrg_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_ActualizacionPVPOrg_ID, Integer.valueOf(Z_ActualizacionPVPOrg_ID));
	}

	/** Get Z_ActualizacionPVPOrg ID.
		@return Z_ActualizacionPVPOrg ID	  */
	public int getZ_ActualizacionPVPOrg_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ActualizacionPVPOrg_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}