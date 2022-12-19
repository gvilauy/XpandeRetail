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
import org.compiere.util.KeyNamePair;

/** Generated Model for Z_RegularOfferOrg
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1 - $Id$ */
public class X_Z_RegularOfferOrg extends PO implements I_Z_RegularOfferOrg, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20221218L;

    /** Standard Constructor */
    public X_Z_RegularOfferOrg (Properties ctx, int Z_RegularOfferOrg_ID, String trxName)
    {
      super (ctx, Z_RegularOfferOrg_ID, trxName);
      /** if (Z_RegularOfferOrg_ID == 0)
        {
			setAD_OrgTrx_ID (0);
			setIsExecuted (false);
// N
			setZ_RegularOffer_ID (0);
			setZ_RegularOfferOrg_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_RegularOfferOrg (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_RegularOfferOrg[")
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getAD_OrgTrx_ID()));
    }

	/** Set IsExecuted.
		@param IsExecuted IsExecuted	  */
	public void setIsExecuted (boolean IsExecuted)
	{
		set_Value (COLUMNNAME_IsExecuted, Boolean.valueOf(IsExecuted));
	}

	/** Get IsExecuted.
		@return IsExecuted	  */
	public boolean isExecuted () 
	{
		Object oo = get_Value(COLUMNNAME_IsExecuted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Z_RegularOfferOrg ID.
		@param Z_RegularOfferOrg_ID Z_RegularOfferOrg ID	  */
	public void setZ_RegularOfferOrg_ID (int Z_RegularOfferOrg_ID)
	{
		if (Z_RegularOfferOrg_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_RegularOfferOrg_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_RegularOfferOrg_ID, Integer.valueOf(Z_RegularOfferOrg_ID));
	}

	/** Get Z_RegularOfferOrg ID.
		@return Z_RegularOfferOrg ID	  */
	public int getZ_RegularOfferOrg_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_RegularOfferOrg_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}