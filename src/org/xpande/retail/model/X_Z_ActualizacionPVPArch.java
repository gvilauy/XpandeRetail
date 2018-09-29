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

/** Generated Model for Z_ActualizacionPVPArch
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_ActualizacionPVPArch extends PO implements I_Z_ActualizacionPVPArch, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180515L;

    /** Standard Constructor */
    public X_Z_ActualizacionPVPArch (Properties ctx, int Z_ActualizacionPVPArch_ID, String trxName)
    {
      super (ctx, Z_ActualizacionPVPArch_ID, trxName);
      /** if (Z_ActualizacionPVPArch_ID == 0)
        {
			setI_IsImported (false);
// N
			setIsConfirmed (false);
// N
			setIsNew (false);
// N
			setIsOmitted (false);
// N
			setProcessed (false);
// N
			setZ_ActualizacionPVPArch_ID (0);
			setZ_ActualizacionPVP_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_ActualizacionPVPArch (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_ActualizacionPVPArch[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Error Msg.
		@param ErrorMsg Error Msg	  */
	public void setErrorMsg (String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	/** Get Error Msg.
		@return Error Msg	  */
	public String getErrorMsg () 
	{
		return (String)get_Value(COLUMNNAME_ErrorMsg);
	}

	/** Set FileLineText.
		@param FileLineText FileLineText	  */
	public void setFileLineText (String FileLineText)
	{
		set_Value (COLUMNNAME_FileLineText, FileLineText);
	}

	/** Get FileLineText.
		@return FileLineText	  */
	public String getFileLineText () 
	{
		return (String)get_Value(COLUMNNAME_FileLineText);
	}

	/** Set Imported.
		@param I_IsImported 
		Has this import been processed
	  */
	public void setI_IsImported (boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
	}

	/** Get Imported.
		@return Has this import been processed
	  */
	public boolean isI_IsImported () 
	{
		Object oo = get_Value(COLUMNNAME_I_IsImported);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Confirmed.
		@param IsConfirmed 
		Assignment is confirmed
	  */
	public void setIsConfirmed (boolean IsConfirmed)
	{
		set_Value (COLUMNNAME_IsConfirmed, Boolean.valueOf(IsConfirmed));
	}

	/** Get Confirmed.
		@return Assignment is confirmed
	  */
	public boolean isConfirmed () 
	{
		Object oo = get_Value(COLUMNNAME_IsConfirmed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsNew.
		@param IsNew IsNew	  */
	public void setIsNew (boolean IsNew)
	{
		set_Value (COLUMNNAME_IsNew, Boolean.valueOf(IsNew));
	}

	/** Get IsNew.
		@return IsNew	  */
	public boolean isNew () 
	{
		Object oo = get_Value(COLUMNNAME_IsNew);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsOmitted.
		@param IsOmitted 
		Omitida si o no
	  */
	public void setIsOmitted (boolean IsOmitted)
	{
		set_Value (COLUMNNAME_IsOmitted, Boolean.valueOf(IsOmitted));
	}

	/** Get IsOmitted.
		@return Omitida si o no
	  */
	public boolean isOmitted () 
	{
		Object oo = get_Value(COLUMNNAME_IsOmitted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set LineNumber.
		@param LineNumber LineNumber	  */
	public void setLineNumber (int LineNumber)
	{
		set_Value (COLUMNNAME_LineNumber, Integer.valueOf(LineNumber));
	}

	/** Get LineNumber.
		@return LineNumber	  */
	public int getLineNumber () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LineNumber);
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

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

	/** Set Z_ActualizacionPVPArch ID.
		@param Z_ActualizacionPVPArch_ID Z_ActualizacionPVPArch ID	  */
	public void setZ_ActualizacionPVPArch_ID (int Z_ActualizacionPVPArch_ID)
	{
		if (Z_ActualizacionPVPArch_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_ActualizacionPVPArch_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_ActualizacionPVPArch_ID, Integer.valueOf(Z_ActualizacionPVPArch_ID));
	}

	/** Get Z_ActualizacionPVPArch ID.
		@return Z_ActualizacionPVPArch ID	  */
	public int getZ_ActualizacionPVPArch_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ActualizacionPVPArch_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_ActualizacionPVP getZ_ActualizacionPVP() throws RuntimeException
    {
		return (I_Z_ActualizacionPVP)MTable.get(getCtx(), I_Z_ActualizacionPVP.Table_Name)
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
}