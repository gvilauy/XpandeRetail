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

/** Generated Model for Z_ConfirmacionEtiquetaDoc
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_ConfirmacionEtiquetaDoc extends PO implements I_Z_ConfirmacionEtiquetaDoc, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170921L;

    /** Standard Constructor */
    public X_Z_ConfirmacionEtiquetaDoc (Properties ctx, int Z_ConfirmacionEtiquetaDoc_ID, String trxName)
    {
      super (ctx, Z_ConfirmacionEtiquetaDoc_ID, trxName);
      /** if (Z_ConfirmacionEtiquetaDoc_ID == 0)
        {
			setAD_Table_ID (0);
			setAD_User_ID (0);
			setC_DocTypeTarget_ID (0);
			setComunicadoPOS (false);
// N
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
			setDocumentNoRef (null);
			setIsConfirmed (false);
// N
			setIsOmitted (false);
// N
			setIsSelected (false);
// N
			setRecord_ID (0);
			setZ_ConfirmacionEtiquetaDoc_ID (0);
			setZ_ConfirmacionEtiqueta_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_ConfirmacionEtiquetaDoc (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_ConfirmacionEtiquetaDoc[")
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

	/** Set Z_ConfirmacionEtiquetaDoc ID.
		@param Z_ConfirmacionEtiquetaDoc_ID Z_ConfirmacionEtiquetaDoc ID	  */
	public void setZ_ConfirmacionEtiquetaDoc_ID (int Z_ConfirmacionEtiquetaDoc_ID)
	{
		if (Z_ConfirmacionEtiquetaDoc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_ConfirmacionEtiquetaDoc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_ConfirmacionEtiquetaDoc_ID, Integer.valueOf(Z_ConfirmacionEtiquetaDoc_ID));
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

	public I_Z_ConfirmacionEtiqueta getZ_ConfirmacionEtiqueta() throws RuntimeException
    {
		return (I_Z_ConfirmacionEtiqueta)MTable.get(getCtx(), I_Z_ConfirmacionEtiqueta.Table_Name)
			.getPO(getZ_ConfirmacionEtiqueta_ID(), get_TrxName());	}

	/** Set Z_ConfirmacionEtiqueta ID.
		@param Z_ConfirmacionEtiqueta_ID Z_ConfirmacionEtiqueta ID	  */
	public void setZ_ConfirmacionEtiqueta_ID (int Z_ConfirmacionEtiqueta_ID)
	{
		if (Z_ConfirmacionEtiqueta_ID < 1) 
			set_Value (COLUMNNAME_Z_ConfirmacionEtiqueta_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ConfirmacionEtiqueta_ID, Integer.valueOf(Z_ConfirmacionEtiqueta_ID));
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

	public I_Z_MotivoOmisionPOS getZ_MotivoOmisionPOS() throws RuntimeException
    {
		return (I_Z_MotivoOmisionPOS)MTable.get(getCtx(), I_Z_MotivoOmisionPOS.Table_Name)
			.getPO(getZ_MotivoOmisionPOS_ID(), get_TrxName());	}

	/** Set Z_MotivoOmisionPOS ID.
		@param Z_MotivoOmisionPOS_ID Z_MotivoOmisionPOS ID	  */
	public void setZ_MotivoOmisionPOS_ID (int Z_MotivoOmisionPOS_ID)
	{
		if (Z_MotivoOmisionPOS_ID < 1) 
			set_Value (COLUMNNAME_Z_MotivoOmisionPOS_ID, null);
		else 
			set_Value (COLUMNNAME_Z_MotivoOmisionPOS_ID, Integer.valueOf(Z_MotivoOmisionPOS_ID));
	}

	/** Get Z_MotivoOmisionPOS ID.
		@return Z_MotivoOmisionPOS ID	  */
	public int getZ_MotivoOmisionPOS_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MotivoOmisionPOS_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}