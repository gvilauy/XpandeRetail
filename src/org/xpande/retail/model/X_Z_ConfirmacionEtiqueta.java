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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for Z_ConfirmacionEtiqueta
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_ConfirmacionEtiqueta extends PO implements I_Z_ConfirmacionEtiqueta, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170921L;

    /** Standard Constructor */
    public X_Z_ConfirmacionEtiqueta (Properties ctx, int Z_ConfirmacionEtiqueta_ID, String trxName)
    {
      super (ctx, Z_ConfirmacionEtiqueta_ID, trxName);
      /** if (Z_ConfirmacionEtiqueta_ID == 0)
        {
			setC_DocType_ID (0);
			setComunicadoPOS (false);
// N
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setImpresion_ID (0);
			setIsApproved (false);
// N
			setIsExecuted (false);
// N
			setProcessed (false);
// N
			setZ_ConfirmacionEtiqueta_ID (0);
			setZ_FormatoEtiqueta_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_ConfirmacionEtiqueta (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_ConfirmacionEtiqueta[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set ComunicadoPOS.
		@param ComunicadoPOS 
		Si fue o no comunicado al POS
	  */
	public void setComunicadoPOS (boolean ComunicadoPOS)
	{
		set_Value (COLUMNNAME_ComunicadoPOS, Boolean.valueOf(ComunicadoPOS));
	}

	/** Get ComunicadoPOS.
		@return Si fue o no comunicado al POS
	  */
	public boolean isComunicadoPOS () 
	{
		Object oo = get_Value(COLUMNNAME_ComunicadoPOS);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Impresion_ID.
		@param Impresion_ID 
		ID general de impresión
	  */
	public void setImpresion_ID (int Impresion_ID)
	{
		if (Impresion_ID < 1) 
			set_Value (COLUMNNAME_Impresion_ID, null);
		else 
			set_Value (COLUMNNAME_Impresion_ID, Integer.valueOf(Impresion_ID));
	}

	/** Get Impresion_ID.
		@return ID general de impresión
	  */
	public int getImpresion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Impresion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set ProcessButton2.
		@param ProcessButton2 
		Botón de Proceso
	  */
	public void setProcessButton2 (String ProcessButton2)
	{
		set_Value (COLUMNNAME_ProcessButton2, ProcessButton2);
	}

	/** Get ProcessButton2.
		@return Botón de Proceso
	  */
	public String getProcessButton2 () 
	{
		return (String)get_Value(COLUMNNAME_ProcessButton2);
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

	public I_Z_ComunicacionPOS getZ_ComunicacionPOS() throws RuntimeException
    {
		return (I_Z_ComunicacionPOS)MTable.get(getCtx(), I_Z_ComunicacionPOS.Table_Name)
			.getPO(getZ_ComunicacionPOS_ID(), get_TrxName());	}

	/** Set Z_ComunicacionPOS ID.
		@param Z_ComunicacionPOS_ID Z_ComunicacionPOS ID	  */
	public void setZ_ComunicacionPOS_ID (int Z_ComunicacionPOS_ID)
	{
		if (Z_ComunicacionPOS_ID < 1) 
			set_Value (COLUMNNAME_Z_ComunicacionPOS_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ComunicacionPOS_ID, Integer.valueOf(Z_ComunicacionPOS_ID));
	}

	/** Get Z_ComunicacionPOS ID.
		@return Z_ComunicacionPOS ID	  */
	public int getZ_ComunicacionPOS_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ComunicacionPOS_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_ConfirmacionEtiqueta ID.
		@param Z_ConfirmacionEtiqueta_ID Z_ConfirmacionEtiqueta ID	  */
	public void setZ_ConfirmacionEtiqueta_ID (int Z_ConfirmacionEtiqueta_ID)
	{
		if (Z_ConfirmacionEtiqueta_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_ConfirmacionEtiqueta_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_ConfirmacionEtiqueta_ID, Integer.valueOf(Z_ConfirmacionEtiqueta_ID));
	}

	/** Get Z_ConfirmacionEtiqueta ID.
		@return Z_ConfirmacionEtiqueta ID	  */
	public int getZ_ConfirmacionEtiqueta_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ConfirmacionEtiqueta_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_FormatoEtiqueta ID.
		@param Z_FormatoEtiqueta_ID Z_FormatoEtiqueta ID	  */
	public void setZ_FormatoEtiqueta_ID (int Z_FormatoEtiqueta_ID)
	{
		if (Z_FormatoEtiqueta_ID < 1) 
			set_Value (COLUMNNAME_Z_FormatoEtiqueta_ID, null);
		else 
			set_Value (COLUMNNAME_Z_FormatoEtiqueta_ID, Integer.valueOf(Z_FormatoEtiqueta_ID));
	}

	/** Get Z_FormatoEtiqueta ID.
		@return Z_FormatoEtiqueta ID	  */
	public int getZ_FormatoEtiqueta_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_FormatoEtiqueta_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}