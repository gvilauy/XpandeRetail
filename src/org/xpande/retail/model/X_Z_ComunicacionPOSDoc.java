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

/** Generated Model for Z_ComunicacionPOSDoc
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_ComunicacionPOSDoc extends PO implements I_Z_ComunicacionPOSDoc, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170722L;

    /** Standard Constructor */
    public X_Z_ComunicacionPOSDoc (Properties ctx, int Z_ComunicacionPOSDoc_ID, String trxName)
    {
      super (ctx, Z_ComunicacionPOSDoc_ID, trxName);
      /** if (Z_ComunicacionPOSDoc_ID == 0)
        {
			setAD_Table_ID (0);
			setAD_User_ID (0);
			setC_DocTypeTarget_ID (0);
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
			setDocumentNoRef (null);
			setIsConfirmed (false);
// N
			setIsSelected (false);
// N
			setRecord_ID (0);
			setZ_ComunicacionPOSDoc_ID (0);
			setZ_ComunicacionPOS_ID (0);
			setZ_ConfirmacionEtiquetaDoc_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_ComunicacionPOSDoc (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_ComunicacionPOSDoc[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Table getAD_Table() throws RuntimeException
    {
		return (I_AD_Table)MTable.get(getCtx(), I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_C_DocType getC_DocTypeTarget() throws RuntimeException
    {
		return (I_C_DocType)MTable.get(getCtx(), I_C_DocType.Table_Name)
			.getPO(getC_DocTypeTarget_ID(), get_TrxName());	}

	/** Set Target Document Type.
		@param C_DocTypeTarget_ID 
		Target document type for conversing documents
	  */
	public void setC_DocTypeTarget_ID (int C_DocTypeTarget_ID)
	{
		if (C_DocTypeTarget_ID < 1) 
			set_Value (COLUMNNAME_C_DocTypeTarget_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocTypeTarget_ID, Integer.valueOf(C_DocTypeTarget_ID));
	}

	/** Get Target Document Type.
		@return Target document type for conversing documents
	  */
	public int getC_DocTypeTarget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocTypeTarget_ID);
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

	/** Set DocumentNoRef.
		@param DocumentNoRef 
		Numero de documento referenciado
	  */
	public void setDocumentNoRef (String DocumentNoRef)
	{
		set_Value (COLUMNNAME_DocumentNoRef, DocumentNoRef);
	}

	/** Get DocumentNoRef.
		@return Numero de documento referenciado
	  */
	public String getDocumentNoRef () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNoRef);
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

	/** Set Selected.
		@param IsSelected Selected	  */
	public void setIsSelected (boolean IsSelected)
	{
		set_Value (COLUMNNAME_IsSelected, Boolean.valueOf(IsSelected));
	}

	/** Get Selected.
		@return Selected	  */
	public boolean isSelected () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_ComunicacionPOSDoc ID.
		@param Z_ComunicacionPOSDoc_ID Z_ComunicacionPOSDoc ID	  */
	public void setZ_ComunicacionPOSDoc_ID (int Z_ComunicacionPOSDoc_ID)
	{
		if (Z_ComunicacionPOSDoc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_ComunicacionPOSDoc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_ComunicacionPOSDoc_ID, Integer.valueOf(Z_ComunicacionPOSDoc_ID));
	}

	/** Get Z_ComunicacionPOSDoc ID.
		@return Z_ComunicacionPOSDoc ID	  */
	public int getZ_ComunicacionPOSDoc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ComunicacionPOSDoc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.xpande.retail.model.I_Z_ComunicacionPOS getZ_ComunicacionPOS() throws RuntimeException
    {
		return (org.xpande.retail.model.I_Z_ComunicacionPOS)MTable.get(getCtx(), org.xpande.retail.model.I_Z_ComunicacionPOS.Table_Name)
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

	public I_Z_ConfirmacionEtiquetaDoc getZ_ConfirmacionEtiquetaDoc() throws RuntimeException
    {
		return (I_Z_ConfirmacionEtiquetaDoc)MTable.get(getCtx(), I_Z_ConfirmacionEtiquetaDoc.Table_Name)
			.getPO(getZ_ConfirmacionEtiquetaDoc_ID(), get_TrxName());	}

	/** Set Z_ConfirmacionEtiquetaDoc ID.
		@param Z_ConfirmacionEtiquetaDoc_ID Z_ConfirmacionEtiquetaDoc ID	  */
	public void setZ_ConfirmacionEtiquetaDoc_ID (int Z_ConfirmacionEtiquetaDoc_ID)
	{
		if (Z_ConfirmacionEtiquetaDoc_ID < 1) 
			set_Value (COLUMNNAME_Z_ConfirmacionEtiquetaDoc_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ConfirmacionEtiquetaDoc_ID, Integer.valueOf(Z_ConfirmacionEtiquetaDoc_ID));
	}

	/** Get Z_ConfirmacionEtiquetaDoc ID.
		@return Z_ConfirmacionEtiquetaDoc ID	  */
	public int getZ_ConfirmacionEtiquetaDoc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ConfirmacionEtiquetaDoc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
}