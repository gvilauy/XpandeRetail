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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for Z_CreditLineAudit
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1 - $Id$ */
public class X_Z_CreditLineAudit extends PO implements I_Z_CreditLineAudit, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20221116L;

    /** Standard Constructor */
    public X_Z_CreditLineAudit (Properties ctx, int Z_CreditLineAudit_ID, String trxName)
    {
      super (ctx, Z_CreditLineAudit_ID, trxName);
      /** if (Z_CreditLineAudit_ID == 0)
        {
			setAD_User_ID (0);
			setC_BPartner_ID (0);
			setC_Currency_ID (0);
			setC_DocType_ID (0);
			setColumnName (null);
			setCreditLimit (Env.ZERO);
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
			setDateTrx (new Timestamp( System.currentTimeMillis() ));
			setDocStatus (null);
			setEndDate (new Timestamp( System.currentTimeMillis() ));
			setIsAuthSupervisor (false);
// N
			setStartDate (new Timestamp( System.currentTimeMillis() ));
			setZ_CreditLineAudit_ID (0);
			setZ_CreditLineCategory_ID (0);
			setZ_CreditLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_CreditLineAudit (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_CreditLineAudit[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_User getAD_User() throws RuntimeException
    {
		return (I_AD_User)MTable.get(getCtx(), I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_C_DocType getC_DocType() throws RuntimeException
    {
		return (I_C_DocType)MTable.get(getCtx(), I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DB Column Name.
		@param ColumnName 
		Name of the column in the database
	  */
	public void setColumnName (String ColumnName)
	{
		set_Value (COLUMNNAME_ColumnName, ColumnName);
	}

	/** Get DB Column Name.
		@return Name of the column in the database
	  */
	public String getColumnName () 
	{
		return (String)get_Value(COLUMNNAME_ColumnName);
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

	/** Set Document Date.
		@param DateDoc 
		Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Timestamp getDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set End Date.
		@param EndDate 
		Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Timestamp getEndDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndDate);
	}

	/** Set Autoriza Sobregiro Supervisor.
		@param IsAuthSupervisor 
		Autoriza Sobregiro Supervisor
	  */
	public void setIsAuthSupervisor (boolean IsAuthSupervisor)
	{
		set_Value (COLUMNNAME_IsAuthSupervisor, Boolean.valueOf(IsAuthSupervisor));
	}

	/** Get Autoriza Sobregiro Supervisor.
		@return Autoriza Sobregiro Supervisor
	  */
	public boolean isAuthSupervisor () 
	{
		Object oo = get_Value(COLUMNNAME_IsAuthSupervisor);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set New Value.
		@param NewValue 
		New field value
	  */
	public void setNewValue (String NewValue)
	{
		set_Value (COLUMNNAME_NewValue, NewValue);
	}

	/** Get New Value.
		@return New field value
	  */
	public String getNewValue () 
	{
		return (String)get_Value(COLUMNNAME_NewValue);
	}

	/** Set Old Value.
		@param OldValue 
		The old file data
	  */
	public void setOldValue (String OldValue)
	{
		set_Value (COLUMNNAME_OldValue, OldValue);
	}

	/** Get Old Value.
		@return The old file data
	  */
	public String getOldValue () 
	{
		return (String)get_Value(COLUMNNAME_OldValue);
	}

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
	}

	/** Set Z_CreditLineAudit ID.
		@param Z_CreditLineAudit_ID Z_CreditLineAudit ID	  */
	public void setZ_CreditLineAudit_ID (int Z_CreditLineAudit_ID)
	{
		if (Z_CreditLineAudit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_CreditLineAudit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_CreditLineAudit_ID, Integer.valueOf(Z_CreditLineAudit_ID));
	}

	/** Get Z_CreditLineAudit ID.
		@return Z_CreditLineAudit ID	  */
	public int getZ_CreditLineAudit_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_CreditLineAudit_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_CreditLineCategory getZ_CreditLineCategory() throws RuntimeException
    {
		return (I_Z_CreditLineCategory)MTable.get(getCtx(), I_Z_CreditLineCategory.Table_Name)
			.getPO(getZ_CreditLineCategory_ID(), get_TrxName());	}

	/** Set Categoría Linea Crédito.
		@param Z_CreditLineCategory_ID Categoría Linea Crédito	  */
	public void setZ_CreditLineCategory_ID (int Z_CreditLineCategory_ID)
	{
		if (Z_CreditLineCategory_ID < 1) 
			set_Value (COLUMNNAME_Z_CreditLineCategory_ID, null);
		else 
			set_Value (COLUMNNAME_Z_CreditLineCategory_ID, Integer.valueOf(Z_CreditLineCategory_ID));
	}

	/** Get Categoría Linea Crédito.
		@return Categoría Linea Crédito	  */
	public int getZ_CreditLineCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_CreditLineCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_CreditLine getZ_CreditLine() throws RuntimeException
    {
		return (I_Z_CreditLine)MTable.get(getCtx(), I_Z_CreditLine.Table_Name)
			.getPO(getZ_CreditLine_ID(), get_TrxName());	}

	/** Set Linea de Credito.
		@param Z_CreditLine_ID Linea de Credito	  */
	public void setZ_CreditLine_ID (int Z_CreditLine_ID)
	{
		if (Z_CreditLine_ID < 1) 
			set_Value (COLUMNNAME_Z_CreditLine_ID, null);
		else 
			set_Value (COLUMNNAME_Z_CreditLine_ID, Integer.valueOf(Z_CreditLine_ID));
	}

	/** Get Linea de Credito.
		@return Linea de Credito	  */
	public int getZ_CreditLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_CreditLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}