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

/** Generated Model for Z_RetailConfForEfe_Acct
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_RetailConfForEfe_Acct extends PO implements I_Z_RetailConfForEfe_Acct, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20191009L;

    /** Standard Constructor */
    public X_Z_RetailConfForEfe_Acct (Properties ctx, int Z_RetailConfForEfe_Acct_ID, String trxName)
    {
      super (ctx, Z_RetailConfForEfe_Acct_ID, trxName);
      /** if (Z_RetailConfForEfe_Acct_ID == 0)
        {
			setAccount_Acct (0);
			setC_AcctSchema_ID (0);
			setC_Currency_ID (0);
			setIsDebito (false);
// N
			setZ_RetailConfForEfe_Acct_ID (0);
			setZ_RetailConfigForEfe_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_RetailConfForEfe_Acct (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_RetailConfForEfe_Acct[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_ValidCombination getAccount_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getAccount_Acct(), get_TrxName());	}

	/** Set Account_Acct.
		@param Account_Acct Account_Acct	  */
	public void setAccount_Acct (int Account_Acct)
	{
		set_Value (COLUMNNAME_Account_Acct, Integer.valueOf(Account_Acct));
	}

	/** Get Account_Acct.
		@return Account_Acct	  */
	public int getAccount_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Account_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set IsDebito.
		@param IsDebito 
		Si se comporta como un débito para la contabilidad o no
	  */
	public void setIsDebito (boolean IsDebito)
	{
		set_Value (COLUMNNAME_IsDebito, Boolean.valueOf(IsDebito));
	}

	/** Get IsDebito.
		@return Si se comporta como un débito para la contabilidad o no
	  */
	public boolean isDebito () 
	{
		Object oo = get_Value(COLUMNNAME_IsDebito);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Z_RetailConfForEfe_Acct ID.
		@param Z_RetailConfForEfe_Acct_ID Z_RetailConfForEfe_Acct ID	  */
	public void setZ_RetailConfForEfe_Acct_ID (int Z_RetailConfForEfe_Acct_ID)
	{
		if (Z_RetailConfForEfe_Acct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_RetailConfForEfe_Acct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_RetailConfForEfe_Acct_ID, Integer.valueOf(Z_RetailConfForEfe_Acct_ID));
	}

	/** Get Z_RetailConfForEfe_Acct ID.
		@return Z_RetailConfForEfe_Acct ID	  */
	public int getZ_RetailConfForEfe_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_RetailConfForEfe_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_RetailConfigForEfe getZ_RetailConfigForEfe() throws RuntimeException
    {
		return (I_Z_RetailConfigForEfe)MTable.get(getCtx(), I_Z_RetailConfigForEfe.Table_Name)
			.getPO(getZ_RetailConfigForEfe_ID(), get_TrxName());	}

	/** Set Z_RetailConfigForEfe ID.
		@param Z_RetailConfigForEfe_ID Z_RetailConfigForEfe ID	  */
	public void setZ_RetailConfigForEfe_ID (int Z_RetailConfigForEfe_ID)
	{
		if (Z_RetailConfigForEfe_ID < 1) 
			set_Value (COLUMNNAME_Z_RetailConfigForEfe_ID, null);
		else 
			set_Value (COLUMNNAME_Z_RetailConfigForEfe_ID, Integer.valueOf(Z_RetailConfigForEfe_ID));
	}

	/** Get Z_RetailConfigForEfe ID.
		@return Z_RetailConfigForEfe ID	  */
	public int getZ_RetailConfigForEfe_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_RetailConfigForEfe_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}