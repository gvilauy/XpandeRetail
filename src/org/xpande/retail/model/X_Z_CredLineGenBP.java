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

/** Generated Model for Z_CredLineGenBP
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1 - $Id$ */
public class X_Z_CredLineGenBP extends PO implements I_Z_CredLineGenBP, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20211029L;

    /** Standard Constructor */
    public X_Z_CredLineGenBP (Properties ctx, int Z_CredLineGenBP_ID, String trxName)
    {
      super (ctx, Z_CredLineGenBP_ID, trxName);
      /** if (Z_CredLineGenBP_ID == 0)
        {
			setC_BPartner_ID (0);
			setCreditLimit (Env.ZERO);
			setZ_CredLineGenBP_ID (0);
			setZ_CredLineGen_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_CredLineGenBP (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_CredLineGenBP[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AmtBase.
		@param AmtBase 
		Monto base
	  */
	public void setAmtBase (BigDecimal AmtBase)
	{
		set_Value (COLUMNNAME_AmtBase, AmtBase);
	}

	/** Get AmtBase.
		@return Monto base
	  */
	public BigDecimal getAmtBase () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtBase);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Credit limit.
		@param CreditLimit 
		Amount of Credit allowed
	  */
	public void setCreditLimit (BigDecimal CreditLimit)
	{
		set_Value (COLUMNNAME_CreditLimit, CreditLimit);
	}

	/** Get Credit limit.
		@return Amount of Credit allowed
	  */
	public BigDecimal getCreditLimit () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CreditLimit);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Tax ID.
		@param TaxID 
		Tax Identification
	  */
	public void setTaxID (String TaxID)
	{
		set_Value (COLUMNNAME_TaxID, TaxID);
	}

	/** Get Tax ID.
		@return Tax Identification
	  */
	public String getTaxID () 
	{
		return (String)get_Value(COLUMNNAME_TaxID);
	}

	public I_Z_CreditLineCategory getZ_CreditLineCategory() throws RuntimeException
    {
		return (I_Z_CreditLineCategory)MTable.get(getCtx(), I_Z_CreditLineCategory.Table_Name)
			.getPO(getZ_CreditLineCategory_ID(), get_TrxName());	}

	/** Set Categoría Linea Crédito.
		@param Z_CreditLineCategory_ID 
		Categoría Linea Crédito
	  */
	public void setZ_CreditLineCategory_ID (int Z_CreditLineCategory_ID)
	{
		if (Z_CreditLineCategory_ID < 1) 
			set_Value (COLUMNNAME_Z_CreditLineCategory_ID, null);
		else 
			set_Value (COLUMNNAME_Z_CreditLineCategory_ID, Integer.valueOf(Z_CreditLineCategory_ID));
	}

	/** Get Categoría Linea Crédito.
		@return Categoría Linea Crédito
	  */
	public int getZ_CreditLineCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_CreditLineCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_CredLineGenBP ID.
		@param Z_CredLineGenBP_ID Z_CredLineGenBP ID	  */
	public void setZ_CredLineGenBP_ID (int Z_CredLineGenBP_ID)
	{
		if (Z_CredLineGenBP_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_CredLineGenBP_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_CredLineGenBP_ID, Integer.valueOf(Z_CredLineGenBP_ID));
	}

	/** Get Z_CredLineGenBP ID.
		@return Z_CredLineGenBP ID	  */
	public int getZ_CredLineGenBP_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_CredLineGenBP_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_CredLineGen getZ_CredLineGen() throws RuntimeException
    {
		return (I_Z_CredLineGen)MTable.get(getCtx(), I_Z_CredLineGen.Table_Name)
			.getPO(getZ_CredLineGen_ID(), get_TrxName());	}

	/** Set Generador Lineas Credito.
		@param Z_CredLineGen_ID 
		Generador Lineas Credito
	  */
	public void setZ_CredLineGen_ID (int Z_CredLineGen_ID)
	{
		if (Z_CredLineGen_ID < 1) 
			set_Value (COLUMNNAME_Z_CredLineGen_ID, null);
		else 
			set_Value (COLUMNNAME_Z_CredLineGen_ID, Integer.valueOf(Z_CredLineGen_ID));
	}

	/** Get Generador Lineas Credito.
		@return Generador Lineas Credito
	  */
	public int getZ_CredLineGen_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_CredLineGen_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}