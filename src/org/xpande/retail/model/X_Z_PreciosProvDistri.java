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

/** Generated Model for Z_PreciosProvDistri
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_PreciosProvDistri extends PO implements I_Z_PreciosProvDistri, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170619L;

    /** Standard Constructor */
    public X_Z_PreciosProvDistri (Properties ctx, int Z_PreciosProvDistri_ID, String trxName)
    {
      super (ctx, Z_PreciosProvDistri_ID, trxName);
      /** if (Z_PreciosProvDistri_ID == 0)
        {
			setC_BPartner_ID (0);
			setIsManualRecord (false);
			setZ_PreciosProvCab_ID (0);
			setZ_PreciosProvDistri_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_PreciosProvDistri (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_PreciosProvDistri[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set IsManualRecord.
		@param IsManualRecord 
		Registro ingresado manualmente por el usuario
	  */
	public void setIsManualRecord (boolean IsManualRecord)
	{
		set_Value (COLUMNNAME_IsManualRecord, Boolean.valueOf(IsManualRecord));
	}

	/** Get IsManualRecord.
		@return Registro ingresado manualmente por el usuario
	  */
	public boolean isManualRecord () 
	{
		Object oo = get_Value(COLUMNNAME_IsManualRecord);
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

	/** Set Z_PreciosProvDistri ID.
		@param Z_PreciosProvDistri_ID Z_PreciosProvDistri ID	  */
	public void setZ_PreciosProvDistri_ID (int Z_PreciosProvDistri_ID)
	{
		if (Z_PreciosProvDistri_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_PreciosProvDistri_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_PreciosProvDistri_ID, Integer.valueOf(Z_PreciosProvDistri_ID));
	}

	/** Get Z_PreciosProvDistri ID.
		@return Z_PreciosProvDistri ID	  */
	public int getZ_PreciosProvDistri_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_PreciosProvDistri_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}