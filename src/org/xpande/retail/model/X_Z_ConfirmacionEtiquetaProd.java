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

/** Generated Model for Z_ConfirmacionEtiquetaProd
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_ConfirmacionEtiquetaProd extends PO implements I_Z_ConfirmacionEtiquetaProd, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170712L;

    /** Standard Constructor */
    public X_Z_ConfirmacionEtiquetaProd (Properties ctx, int Z_ConfirmacionEtiquetaProd_ID, String trxName)
    {
      super (ctx, Z_ConfirmacionEtiquetaProd_ID, trxName);
      /** if (Z_ConfirmacionEtiquetaProd_ID == 0)
        {
			setC_Currency_ID_SO (0);
			setDateValidSO (new Timestamp( System.currentTimeMillis() ));
			setIsOmitted (false);
// N
			setIsPrinted (false);
// N
			setM_Product_ID (0);
			setPriceSO (Env.ZERO);
			setQtyCount (0);
			setZ_ConfirmacionEtiquetaDoc_ID (0);
			setZ_ConfirmacionEtiquetaProd_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_ConfirmacionEtiquetaProd (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_ConfirmacionEtiquetaProd[")
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

	/** Set DateValidSO.
		@param DateValidSO 
		Fecha Vigencia Compra
	  */
	public void setDateValidSO (Timestamp DateValidSO)
	{
		set_Value (COLUMNNAME_DateValidSO, DateValidSO);
	}

	/** Get DateValidSO.
		@return Fecha Vigencia Compra
	  */
	public Timestamp getDateValidSO () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateValidSO);
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

	/** Set Printed.
		@param IsPrinted 
		Indicates if this document / line is printed
	  */
	public void setIsPrinted (boolean IsPrinted)
	{
		set_Value (COLUMNNAME_IsPrinted, Boolean.valueOf(IsPrinted));
	}

	/** Get Printed.
		@return Indicates if this document / line is printed
	  */
	public boolean isPrinted () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrinted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Quantity count.
		@param QtyCount 
		Counted Quantity
	  */
	public void setQtyCount (int QtyCount)
	{
		set_Value (COLUMNNAME_QtyCount, Integer.valueOf(QtyCount));
	}

	/** Get Quantity count.
		@return Counted Quantity
	  */
	public int getQtyCount () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyCount);
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

	/** Set Z_ConfirmacionEtiquetaProd ID.
		@param Z_ConfirmacionEtiquetaProd_ID Z_ConfirmacionEtiquetaProd ID	  */
	public void setZ_ConfirmacionEtiquetaProd_ID (int Z_ConfirmacionEtiquetaProd_ID)
	{
		if (Z_ConfirmacionEtiquetaProd_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_ConfirmacionEtiquetaProd_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_ConfirmacionEtiquetaProd_ID, Integer.valueOf(Z_ConfirmacionEtiquetaProd_ID));
	}

	/** Get Z_ConfirmacionEtiquetaProd ID.
		@return Z_ConfirmacionEtiquetaProd ID	  */
	public int getZ_ConfirmacionEtiquetaProd_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ConfirmacionEtiquetaProd_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}