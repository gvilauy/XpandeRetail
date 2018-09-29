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

/** Generated Model for Z_OfertaVenta
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_OfertaVenta extends PO implements I_Z_OfertaVenta, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180404L;

    /** Standard Constructor */
    public X_Z_OfertaVenta (Properties ctx, int Z_OfertaVenta_ID, String trxName)
    {
      super (ctx, Z_OfertaVenta_ID, trxName);
      /** if (Z_OfertaVenta_ID == 0)
        {
			setC_Currency_ID_SO (0);
			setC_DocType_ID (0);
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setEndDate (new Timestamp( System.currentTimeMillis() ));
			setIsApproved (false);
// N
			setIsModified (false);
// N
			setM_PriceList_ID_SO (0);
			setM_PriceList_Version_ID_SO (0);
			setOnlyOneOrg (true);
// Y
			setProcessed (false);
// N
			setProcessing (false);
// N
			setStartDate (new Timestamp( System.currentTimeMillis() ));
			setZ_OfertaVenta_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_OfertaVenta (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_OfertaVenta[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set C_Currency_ID_SO.
		@param C_Currency_ID_SO 
		Moneda de Venta
	  */
	public void setC_Currency_ID_SO (int C_Currency_ID_SO)
	{
		set_Value (COLUMNNAME_C_Currency_ID_SO, Integer.valueOf(C_Currency_ID_SO));
	}

	/** Get C_Currency_ID_SO.
		@return Moneda de Venta
	  */
	public int getC_Currency_ID_SO () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID_SO);
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

	/** Set Modified.
		@param IsModified 
		The record is modified
	  */
	public void setIsModified (boolean IsModified)
	{
		set_Value (COLUMNNAME_IsModified, Boolean.valueOf(IsModified));
	}

	/** Get Modified.
		@return The record is modified
	  */
	public boolean isModified () 
	{
		Object oo = get_Value(COLUMNNAME_IsModified);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set M_PriceList_ID_SO.
		@param M_PriceList_ID_SO 
		Lista de Precios de Venta
	  */
	public void setM_PriceList_ID_SO (int M_PriceList_ID_SO)
	{
		set_Value (COLUMNNAME_M_PriceList_ID_SO, Integer.valueOf(M_PriceList_ID_SO));
	}

	/** Get M_PriceList_ID_SO.
		@return Lista de Precios de Venta
	  */
	public int getM_PriceList_ID_SO () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID_SO);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_PriceList_Version_ID_SO.
		@param M_PriceList_Version_ID_SO 
		Version de Lista de Precios de Venta
	  */
	public void setM_PriceList_Version_ID_SO (int M_PriceList_Version_ID_SO)
	{
		set_Value (COLUMNNAME_M_PriceList_Version_ID_SO, Integer.valueOf(M_PriceList_Version_ID_SO));
	}

	/** Get M_PriceList_Version_ID_SO.
		@return Version de Lista de Precios de Venta
	  */
	public int getM_PriceList_Version_ID_SO () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_Version_ID_SO);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set OnlyOneOrg.
		@param OnlyOneOrg 
		Flag para indicar si estoy procesando una sola una organización
	  */
	public void setOnlyOneOrg (boolean OnlyOneOrg)
	{
		set_Value (COLUMNNAME_OnlyOneOrg, Boolean.valueOf(OnlyOneOrg));
	}

	/** Get OnlyOneOrg.
		@return Flag para indicar si estoy procesando una sola una organización
	  */
	public boolean isOnlyOneOrg () 
	{
		Object oo = get_Value(COLUMNNAME_OnlyOneOrg);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set PrecisionSO.
		@param PrecisionSO 
		Precisión decimal para precios de venta
	  */
	public void setPrecisionSO (int PrecisionSO)
	{
		set_Value (COLUMNNAME_PrecisionSO, Integer.valueOf(PrecisionSO));
	}

	/** Get PrecisionSO.
		@return Precisión decimal para precios de venta
	  */
	public int getPrecisionSO () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PrecisionSO);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Z_OfertaVenta ID.
		@param Z_OfertaVenta_ID Z_OfertaVenta ID	  */
	public void setZ_OfertaVenta_ID (int Z_OfertaVenta_ID)
	{
		if (Z_OfertaVenta_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_OfertaVenta_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_OfertaVenta_ID, Integer.valueOf(Z_OfertaVenta_ID));
	}

	/** Get Z_OfertaVenta ID.
		@return Z_OfertaVenta ID	  */
	public int getZ_OfertaVenta_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_OfertaVenta_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}