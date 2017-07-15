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

/** Generated Model for Z_PreciosProvCab
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_PreciosProvCab extends PO implements I_Z_PreciosProvCab, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170715L;

    /** Standard Constructor */
    public X_Z_PreciosProvCab (Properties ctx, int Z_PreciosProvCab_ID, String trxName)
    {
      super (ctx, Z_PreciosProvCab_ID, trxName);
      /** if (Z_PreciosProvCab_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_Currency_ID (0);
			setC_Currency_ID_SO (0);
			setC_DocType_ID (0);
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDateValidPO (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setHaveErrors (false);
// N
			setIsApproved (false);
// N
			setIsExecuted (false);
// N
			setIsNewLineaProducto (false);
// N
			setIsTaxIncluded (true);
// Y
			setModalidadPreciosProv (null);
// ARCHIVO
			setOnlyOneOrg (true);
// Y
			setProcessed (false);
// N
			setZ_PreciosProvCab_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_PreciosProvCab (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_PreciosProvCab[")
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

	/** Set DateValidPO.
		@param DateValidPO 
		Fecha Vigencia Compra
	  */
	public void setDateValidPO (Timestamp DateValidPO)
	{
		set_Value (COLUMNNAME_DateValidPO, DateValidPO);
	}

	/** Get DateValidPO.
		@return Fecha Vigencia Compra
	  */
	public Timestamp getDateValidPO () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateValidPO);
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

	/** Set File Name.
		@param FileName 
		Name of the local file or URL
	  */
	public void setFileName (String FileName)
	{
		set_Value (COLUMNNAME_FileName, FileName);
	}

	/** Get File Name.
		@return Name of the local file or URL
	  */
	public String getFileName () 
	{
		return (String)get_Value(COLUMNNAME_FileName);
	}

	/** Set HaveErrors.
		@param HaveErrors HaveErrors	  */
	public void setHaveErrors (boolean HaveErrors)
	{
		set_Value (COLUMNNAME_HaveErrors, Boolean.valueOf(HaveErrors));
	}

	/** Get HaveErrors.
		@return HaveErrors	  */
	public boolean isHaveErrors () 
	{
		Object oo = get_Value(COLUMNNAME_HaveErrors);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set IsNewLineaProducto.
		@param IsNewLineaProducto 
		Es una nueva linea de producto en modulo de Retail
	  */
	public void setIsNewLineaProducto (boolean IsNewLineaProducto)
	{
		set_Value (COLUMNNAME_IsNewLineaProducto, Boolean.valueOf(IsNewLineaProducto));
	}

	/** Get IsNewLineaProducto.
		@return Es una nueva linea de producto en modulo de Retail
	  */
	public boolean isNewLineaProducto () 
	{
		Object oo = get_Value(COLUMNNAME_IsNewLineaProducto);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Price includes Tax.
		@param IsTaxIncluded 
		Tax is included in the price 
	  */
	public void setIsTaxIncluded (boolean IsTaxIncluded)
	{
		set_Value (COLUMNNAME_IsTaxIncluded, Boolean.valueOf(IsTaxIncluded));
	}

	/** Get Price includes Tax.
		@return Tax is included in the price 
	  */
	public boolean isTaxIncluded () 
	{
		Object oo = get_Value(COLUMNNAME_IsTaxIncluded);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** ModalidadPreciosProv AD_Reference_ID=1000001 */
	public static final int MODALIDADPRECIOSPROV_AD_Reference_ID=1000001;
	/** ARCHIVO DE CARGA = ARCHIVO */
	public static final String MODALIDADPRECIOSPROV_ARCHIVODECARGA = "ARCHIVO";
	/** LINEA DE PRODUCTOS = LINEA */
	public static final String MODALIDADPRECIOSPROV_LINEADEPRODUCTOS = "LINEA";
	/** INGRESO MANUAL = MANUAL */
	public static final String MODALIDADPRECIOSPROV_INGRESOMANUAL = "MANUAL";
	/** Set ModalidadPreciosProv.
		@param ModalidadPreciosProv 
		Modalidad de gestión para el mantenimiento de precios de proveedor
	  */
	public void setModalidadPreciosProv (String ModalidadPreciosProv)
	{

		set_Value (COLUMNNAME_ModalidadPreciosProv, ModalidadPreciosProv);
	}

	/** Get ModalidadPreciosProv.
		@return Modalidad de gestión para el mantenimiento de precios de proveedor
	  */
	public String getModalidadPreciosProv () 
	{
		return (String)get_Value(COLUMNNAME_ModalidadPreciosProv);
	}

	public I_M_PriceList getM_PriceList() throws RuntimeException
    {
		return (I_M_PriceList)MTable.get(getCtx(), I_M_PriceList.Table_Name)
			.getPO(getM_PriceList_ID(), get_TrxName());	}

	/** Set Price List.
		@param M_PriceList_ID 
		Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID)
	{
		if (M_PriceList_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_ID, Integer.valueOf(M_PriceList_ID));
	}

	/** Get Price List.
		@return Unique identifier of a Price List
	  */
	public int getM_PriceList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_M_PriceList_Version getM_PriceList_Version() throws RuntimeException
    {
		return (I_M_PriceList_Version)MTable.get(getCtx(), I_M_PriceList_Version.Table_Name)
			.getPO(getM_PriceList_Version_ID(), get_TrxName());	}

	/** Set Price List Version.
		@param M_PriceList_Version_ID 
		Identifies a unique instance of a Price List
	  */
	public void setM_PriceList_Version_ID (int M_PriceList_Version_ID)
	{
		if (M_PriceList_Version_ID < 1) 
			set_Value (COLUMNNAME_M_PriceList_Version_ID, null);
		else 
			set_Value (COLUMNNAME_M_PriceList_Version_ID, Integer.valueOf(M_PriceList_Version_ID));
	}

	/** Get Price List Version.
		@return Identifies a unique instance of a Price List
	  */
	public int getM_PriceList_Version_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_Version_ID);
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

	/** Set PrecisionPO.
		@param PrecisionPO 
		Precisión decimal para precios de compra
	  */
	public void setPrecisionPO (int PrecisionPO)
	{
		set_Value (COLUMNNAME_PrecisionPO, Integer.valueOf(PrecisionPO));
	}

	/** Get PrecisionPO.
		@return Precisión decimal para precios de compra
	  */
	public int getPrecisionPO () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PrecisionPO);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set ProcessButton3.
		@param ProcessButton3 
		Botón para proceso
	  */
	public void setProcessButton3 (String ProcessButton3)
	{
		set_Value (COLUMNNAME_ProcessButton3, ProcessButton3);
	}

	/** Get ProcessButton3.
		@return Botón para proceso
	  */
	public String getProcessButton3 () 
	{
		return (String)get_Value(COLUMNNAME_ProcessButton3);
	}

	/** Set ProcessButton4.
		@param ProcessButton4 
		Botón de Proceso
	  */
	public void setProcessButton4 (String ProcessButton4)
	{
		set_Value (COLUMNNAME_ProcessButton4, ProcessButton4);
	}

	/** Get ProcessButton4.
		@return Botón de Proceso
	  */
	public String getProcessButton4 () 
	{
		return (String)get_Value(COLUMNNAME_ProcessButton4);
	}

	/** Set ProcessButton5.
		@param ProcessButton5 
		Botón de Proceso
	  */
	public void setProcessButton5 (String ProcessButton5)
	{
		set_Value (COLUMNNAME_ProcessButton5, ProcessButton5);
	}

	/** Get ProcessButton5.
		@return Botón de Proceso
	  */
	public String getProcessButton5 () 
	{
		return (String)get_Value(COLUMNNAME_ProcessButton5);
	}

	/** Set ProcessButton6.
		@param ProcessButton6 
		Botón de Proceso
	  */
	public void setProcessButton6 (String ProcessButton6)
	{
		set_Value (COLUMNNAME_ProcessButton6, ProcessButton6);
	}

	/** Get ProcessButton6.
		@return Botón de Proceso
	  */
	public String getProcessButton6 () 
	{
		return (String)get_Value(COLUMNNAME_ProcessButton6);
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

	/** Set Rate.
		@param Rate 
		Rate or Tax or Exchange
	  */
	public void setRate (BigDecimal Rate)
	{
		set_Value (COLUMNNAME_Rate, Rate);
	}

	/** Get Rate.
		@return Rate or Tax or Exchange
	  */
	public BigDecimal getRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Rate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_Z_LineaProductoSocio getZ_LineaProductoSocio() throws RuntimeException
    {
		return (I_Z_LineaProductoSocio)MTable.get(getCtx(), I_Z_LineaProductoSocio.Table_Name)
			.getPO(getZ_LineaProductoSocio_ID(), get_TrxName());	}

	/** Set Z_LineaProductoSocio ID.
		@param Z_LineaProductoSocio_ID Z_LineaProductoSocio ID	  */
	public void setZ_LineaProductoSocio_ID (int Z_LineaProductoSocio_ID)
	{
		if (Z_LineaProductoSocio_ID < 1) 
			set_Value (COLUMNNAME_Z_LineaProductoSocio_ID, null);
		else 
			set_Value (COLUMNNAME_Z_LineaProductoSocio_ID, Integer.valueOf(Z_LineaProductoSocio_ID));
	}

	/** Get Z_LineaProductoSocio ID.
		@return Z_LineaProductoSocio ID	  */
	public int getZ_LineaProductoSocio_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_LineaProductoSocio_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_PautaComercial getZ_PautaComercial() throws RuntimeException
    {
		return (I_Z_PautaComercial)MTable.get(getCtx(), I_Z_PautaComercial.Table_Name)
			.getPO(getZ_PautaComercial_ID(), get_TrxName());	}

	/** Set Z_PautaComercial ID.
		@param Z_PautaComercial_ID Z_PautaComercial ID	  */
	public void setZ_PautaComercial_ID (int Z_PautaComercial_ID)
	{
		if (Z_PautaComercial_ID < 1) 
			set_Value (COLUMNNAME_Z_PautaComercial_ID, null);
		else 
			set_Value (COLUMNNAME_Z_PautaComercial_ID, Integer.valueOf(Z_PautaComercial_ID));
	}

	/** Get Z_PautaComercial ID.
		@return Z_PautaComercial ID	  */
	public int getZ_PautaComercial_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_PautaComercial_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_PreciosProvCab ID.
		@param Z_PreciosProvCab_ID Z_PreciosProvCab ID	  */
	public void setZ_PreciosProvCab_ID (int Z_PreciosProvCab_ID)
	{
		if (Z_PreciosProvCab_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_PreciosProvCab_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_PreciosProvCab_ID, Integer.valueOf(Z_PreciosProvCab_ID));
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
}