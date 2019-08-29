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

/** Generated Model for Z_FormEfectivo
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_FormEfectivo extends PO implements I_Z_FormEfectivo, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190829L;

    /** Standard Constructor */
    public X_Z_FormEfectivo (Properties ctx, int Z_FormEfectivo_ID, String trxName)
    {
      super (ctx, Z_FormEfectivo_ID, trxName);
      /** if (Z_FormEfectivo_ID == 0)
        {
			setC_DocType_ID (0);
			setDateAcct (new Timestamp( System.currentTimeMillis() ));
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setIsApproved (false);
// N
			setPosted (false);
// N
			setProcessed (false);
// N
			setProcessing (false);
// N
			setZ_FormEfectivo_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_FormEfectivo (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_FormEfectivo[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AmtTotal1.
		@param AmtTotal1 
		Monto total uno
	  */
	public void setAmtTotal1 (BigDecimal AmtTotal1)
	{
		set_Value (COLUMNNAME_AmtTotal1, AmtTotal1);
	}

	/** Get AmtTotal1.
		@return Monto total uno
	  */
	public BigDecimal getAmtTotal1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtTotal1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtTotal2.
		@param AmtTotal2 
		Monto total dos
	  */
	public void setAmtTotal2 (BigDecimal AmtTotal2)
	{
		set_Value (COLUMNNAME_AmtTotal2, AmtTotal2);
	}

	/** Get AmtTotal2.
		@return Monto total dos
	  */
	public BigDecimal getAmtTotal2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtTotal2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtTotal3.
		@param AmtTotal3 
		Monto total 3
	  */
	public void setAmtTotal3 (BigDecimal AmtTotal3)
	{
		set_Value (COLUMNNAME_AmtTotal3, AmtTotal3);
	}

	/** Get AmtTotal3.
		@return Monto total 3
	  */
	public BigDecimal getAmtTotal3 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtTotal3);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtTotal4.
		@param AmtTotal4 
		Monto total 4
	  */
	public void setAmtTotal4 (BigDecimal AmtTotal4)
	{
		set_Value (COLUMNNAME_AmtTotal4, AmtTotal4);
	}

	/** Get AmtTotal4.
		@return Monto total 4
	  */
	public BigDecimal getAmtTotal4 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtTotal4);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtTotal5.
		@param AmtTotal5 
		Monto total 5
	  */
	public void setAmtTotal5 (BigDecimal AmtTotal5)
	{
		set_Value (COLUMNNAME_AmtTotal5, AmtTotal5);
	}

	/** Get AmtTotal5.
		@return Monto total 5
	  */
	public BigDecimal getAmtTotal5 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtTotal5);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtTotal6.
		@param AmtTotal6 
		Monto total 6
	  */
	public void setAmtTotal6 (BigDecimal AmtTotal6)
	{
		set_Value (COLUMNNAME_AmtTotal6, AmtTotal6);
	}

	/** Get AmtTotal6.
		@return Monto total 6
	  */
	public BigDecimal getAmtTotal6 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtTotal6);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtTotal7.
		@param AmtTotal7 
		Monto total 7
	  */
	public void setAmtTotal7 (BigDecimal AmtTotal7)
	{
		set_Value (COLUMNNAME_AmtTotal7, AmtTotal7);
	}

	/** Get AmtTotal7.
		@return Monto total 7
	  */
	public BigDecimal getAmtTotal7 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtTotal7);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtTotal8.
		@param AmtTotal8 
		Monto total 8
	  */
	public void setAmtTotal8 (BigDecimal AmtTotal8)
	{
		set_Value (COLUMNNAME_AmtTotal8, AmtTotal8);
	}

	/** Get AmtTotal8.
		@return Monto total 8
	  */
	public BigDecimal getAmtTotal8 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtTotal8);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Account Date.
		@param DateAcct 
		Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Account Date.
		@return Accounting Date
	  */
	public Timestamp getDateAcct () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAcct);
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

	/** DocAction AD_Reference_ID=135 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse - Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse - Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re-activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** <None> = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** Wait Complete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** Set Document Action.
		@param DocAction 
		The targeted status of the document
	  */
	public void setDocAction (String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Document Action.
		@return The targeted status of the document
	  */
	public String getDocAction () 
	{
		return (String)get_Value(COLUMNNAME_DocAction);
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

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Approved.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Approved.
		@return Indicates if this document requires approval
	  */
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Posted.
		@param Posted 
		Posting status
	  */
	public void setPosted (boolean Posted)
	{
		set_Value (COLUMNNAME_Posted, Boolean.valueOf(Posted));
	}

	/** Get Posted.
		@return Posting status
	  */
	public boolean isPosted () 
	{
		Object oo = get_Value(COLUMNNAME_Posted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set ProcessButton.
		@param ProcessButton ProcessButton	  */
	public void setProcessButton (String ProcessButton)
	{
		set_Value (COLUMNNAME_ProcessButton, ProcessButton);
	}

	/** Get ProcessButton.
		@return ProcessButton	  */
	public String getProcessButton () 
	{
		return (String)get_Value(COLUMNNAME_ProcessButton);
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

	/** Set Processed On.
		@param ProcessedOn 
		The date+time (expressed in decimal format) when the document has been processed
	  */
	public void setProcessedOn (BigDecimal ProcessedOn)
	{
		set_Value (COLUMNNAME_ProcessedOn, ProcessedOn);
	}

	/** Get Processed On.
		@return The date+time (expressed in decimal format) when the document has been processed
	  */
	public BigDecimal getProcessedOn () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ProcessedOn);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
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

	/** Set Z_FormEfectivo ID.
		@param Z_FormEfectivo_ID Z_FormEfectivo ID	  */
	public void setZ_FormEfectivo_ID (int Z_FormEfectivo_ID)
	{
		if (Z_FormEfectivo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_FormEfectivo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_FormEfectivo_ID, Integer.valueOf(Z_FormEfectivo_ID));
	}

	/** Get Z_FormEfectivo ID.
		@return Z_FormEfectivo ID	  */
	public int getZ_FormEfectivo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_FormEfectivo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}