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

/** Generated Model for Z_AuditSipcLin
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_AuditSipcLin extends PO implements I_Z_AuditSipcLin, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200707L;

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