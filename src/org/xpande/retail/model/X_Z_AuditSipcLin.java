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

/** Generated Model for Z_AuditSipcLin
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_AuditSipcLin extends PO implements I_Z_AuditSipcLin, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200727L;

    /** Standard Constructor */
    public X_Z_AuditSipcLin (Properties ctx, int Z_AuditSipcLin_ID, String trxName)
    {
      super (ctx, Z_AuditSipcLin_ID, trxName);
      /** if (Z_AuditSipcLin_ID == 0)
        {
			setWithOfferSO (false);
// N
			setZ_AuditSipc_ID (0);
			setZ_AuditSipcLin_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_AuditSipcLin (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_AuditSipcLin[")
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

	/** Set CodigoProducto.
		@param CodigoProducto 
		Código de Producto
	  */
	public void setCodigoProducto (String CodigoProducto)
	{
		set_Value (COLUMNNAME_CodigoProducto, CodigoProducto);
	}

	/** Get CodigoProducto.
		@return Código de Producto
	  */
	public String getCodigoProducto () 
	{
		return (String)get_Value(COLUMNNAME_CodigoProducto);
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

	/** Set FechaAuditoria.
		@param FechaAuditoria 
		Fecha auditoría genérica
	  */
	public void setFechaAuditoria (Timestamp FechaAuditoria)
	{
		set_Value (COLUMNNAME_FechaAuditoria, FechaAuditoria);
	}

	/** Get FechaAuditoria.
		@return Fecha auditoría genérica
	  */
	public Timestamp getFechaAuditoria () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FechaAuditoria);
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

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set PriceSO.
		@param PriceSO 
		PriceSO
	  */
	public void setPriceSO (BigDecimal PriceSO)
	{
		set_Value (COLUMNNAME_PriceSO, PriceSO);
	}

	/** Get PriceSO.
		@return PriceSO
	  */
	public BigDecimal getPriceSO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceSO);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UPC/EAN.
		@param UPC 
		Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public void setUPC (String UPC)
	{
		set_Value (COLUMNNAME_UPC, UPC);
	}

	/** Get UPC/EAN.
		@return Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public String getUPC () 
	{
		return (String)get_Value(COLUMNNAME_UPC);
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

	/** Set WithOfferSO.
		@param WithOfferSO 
		Si tiene o no oferta en precio de venta en Retail
	  */
	public void setWithOfferSO (boolean WithOfferSO)
	{
		set_Value (COLUMNNAME_WithOfferSO, Boolean.valueOf(WithOfferSO));
	}

	/** Get WithOfferSO.
		@return Si tiene o no oferta en precio de venta en Retail
	  */
	public boolean isWithOfferSO () 
	{
		Object oo = get_Value(COLUMNNAME_WithOfferSO);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_Z_AuditSipc getZ_AuditSipc() throws RuntimeException
    {
		return (I_Z_AuditSipc)MTable.get(getCtx(), I_Z_AuditSipc.Table_Name)
			.getPO(getZ_AuditSipc_ID(), get_TrxName());	}

	/** Set Z_AuditSipc ID.
		@param Z_AuditSipc_ID Z_AuditSipc ID	  */
	public void setZ_AuditSipc_ID (int Z_AuditSipc_ID)
	{
		if (Z_AuditSipc_ID < 1) 
			set_Value (COLUMNNAME_Z_AuditSipc_ID, null);
		else 
			set_Value (COLUMNNAME_Z_AuditSipc_ID, Integer.valueOf(Z_AuditSipc_ID));
	}

	/** Get Z_AuditSipc ID.
		@return Z_AuditSipc ID	  */
	public int getZ_AuditSipc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_AuditSipc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_AuditSipcLin ID.
		@param Z_AuditSipcLin_ID Z_AuditSipcLin ID	  */
	public void setZ_AuditSipcLin_ID (int Z_AuditSipcLin_ID)
	{
		if (Z_AuditSipcLin_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_AuditSipcLin_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_AuditSipcLin_ID, Integer.valueOf(Z_AuditSipcLin_ID));
	}

	/** Get Z_AuditSipcLin ID.
		@return Z_AuditSipcLin ID	  */
	public int getZ_AuditSipcLin_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_AuditSipcLin_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}