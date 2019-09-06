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

/** Generated Model for Z_RetailForEfe_Acct
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_RetailForEfe_Acct extends PO implements I_Z_RetailForEfe_Acct, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190906L;

    /** Standard Constructor */
    public X_Z_RetailForEfe_Acct (Properties ctx, int Z_RetailForEfe_Acct_ID, String trxName)
    {
      super (ctx, Z_RetailForEfe_Acct_ID, trxName);
      /** if (Z_RetailForEfe_Acct_ID == 0)
        {
			setC_AcctSchema_ID (0);
			setC_Currency_ID (0);
			setP_Revenue_Acct (0);
			setZ_RetailConfig_ID (0);
			setZ_RetailForEfe_Acct_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_RetailForEfe_Acct (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_RetailForEfe_Acct[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_AcctSchema getC_AcctSchema() throws RuntimeException
    {
		return (I_C_AcctSchema)MTable.get(getCtx(), I_C_AcctSchema.Table_Name)
			.getPO(getC_AcctSchema_ID(), get_TrxName());	}

	/** Set Accounting Schema.
		@param C_AcctSchema_ID 
		Rules for accounting
	  */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_Value (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_Value (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
	}

	/** Get Accounting Schema.
		@return Rules for accounting
	  */
	public int getC_AcctSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Currency getC_Currency() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_Expense_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_Expense_Acct(), get_TrxName());	}

	/** Set Product Expense.
		@param P_Expense_Acct 
		Account for Product Expense
	  */
	public void setP_Expense_Acct (int P_Expense_Acct)
	{
		set_Value (COLUMNNAME_P_Expense_Acct, Integer.valueOf(P_Expense_Acct));
	}

	/** Get Product Expense.
		@return Account for Product Expense
	  */
	public int getP_Expense_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Expense_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getP_Revenue_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getP_Revenue_Acct(), get_TrxName());	}

	/** Set Product Revenue.
		@param P_Revenue_Acct 
		Account for Product Revenue (Sales Account)
	  */
	public void setP_Revenue_Acct (int P_Revenue_Acct)
	{
		set_Value (COLUMNNAME_P_Revenue_Acct, Integer.valueOf(P_Revenue_Acct));
	}

	/** Get Product Revenue.
		@return Account for Product Revenue (Sales Account)
	  */
	public int getP_Revenue_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_P_Revenue_Acct);
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

	public I_Z_RetailConfig getZ_RetailConfig() throws RuntimeException
    {
		return (I_Z_RetailConfig)MTable.get(getCtx(), I_Z_RetailConfig.Table_Name)
			.getPO(getZ_RetailConfig_ID(), get_TrxName());	}

	/** Set Z_RetailConfig ID.
		@param Z_RetailConfig_ID Z_RetailConfig ID	  */
	public void setZ_RetailConfig_ID (int Z_RetailConfig_ID)
	{
		if (Z_RetailConfig_ID < 1) 
			set_Value (COLUMNNAME_Z_RetailConfig_ID, null);
		else 
			set_Value (COLUMNNAME_Z_RetailConfig_ID, Integer.valueOf(Z_RetailConfig_ID));
	}

	/** Get Z_RetailConfig ID.
		@return Z_RetailConfig ID	  */
	public int getZ_RetailConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_RetailConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_RetailForEfe_Acct ID.
		@param Z_RetailForEfe_Acct_ID Z_RetailForEfe_Acct ID	  */
	public void setZ_RetailForEfe_Acct_ID (int Z_RetailForEfe_Acct_ID)
	{
		if (Z_RetailForEfe_Acct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_RetailForEfe_Acct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_RetailForEfe_Acct_ID, Integer.valueOf(Z_RetailForEfe_Acct_ID));
	}

	/** Get Z_RetailForEfe_Acct ID.
		@return Z_RetailForEfe_Acct ID	  */
	public int getZ_RetailForEfe_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_RetailForEfe_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}