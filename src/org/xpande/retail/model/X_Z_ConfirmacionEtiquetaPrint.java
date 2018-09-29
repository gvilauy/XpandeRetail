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

/** Generated Model for Z_ConfirmacionEtiquetaPrint
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_ConfirmacionEtiquetaPrint extends PO implements I_Z_ConfirmacionEtiquetaPrint, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170731L;

    /** Standard Constructor */
    public X_Z_ConfirmacionEtiquetaPrint (Properties ctx, int Z_ConfirmacionEtiquetaPrint_ID, String trxName)
    {
      super (ctx, Z_ConfirmacionEtiquetaPrint_ID, trxName);
      /** if (Z_ConfirmacionEtiquetaPrint_ID == 0)
        {
			setC_Currency_ID_SO (0);
			setDateValidSO (new Timestamp( System.currentTimeMillis() ));
			setImpresion_ID (0);
			setM_Product_ID (0);
			setPriceSO (Env.ZERO);
			setZ_ConfirmacionEtiquetaPrint_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_ConfirmacionEtiquetaPrint (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_ConfirmacionEtiquetaPrint[")
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
		Fecha Vigencia Venta
	  */
	public void setDateValidSO (Timestamp DateValidSO)
	{
		set_Value (COLUMNNAME_DateValidSO, DateValidSO);
	}

	/** Get DateValidSO.
		@return Fecha Vigencia Venta
	  */
	public Timestamp getDateValidSO () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateValidSO);
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

	/** Set Z_ConfirmacionEtiquetaPrint ID.
		@param Z_ConfirmacionEtiquetaPrint_ID Z_ConfirmacionEtiquetaPrint ID	  */
	public void setZ_ConfirmacionEtiquetaPrint_ID (int Z_ConfirmacionEtiquetaPrint_ID)
	{
		if (Z_ConfirmacionEtiquetaPrint_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_ConfirmacionEtiquetaPrint_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_ConfirmacionEtiquetaPrint_ID, Integer.valueOf(Z_ConfirmacionEtiquetaPrint_ID));
	}

	/** Get Z_ConfirmacionEtiquetaPrint ID.
		@return Z_ConfirmacionEtiquetaPrint ID	  */
	public int getZ_ConfirmacionEtiquetaPrint_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ConfirmacionEtiquetaPrint_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_ImpresionEtiquetaSimple getZ_ImpresionEtiquetaSimple() throws RuntimeException
    {
		return (I_Z_ImpresionEtiquetaSimple)MTable.get(getCtx(), I_Z_ImpresionEtiquetaSimple.Table_Name)
			.getPO(getZ_ImpresionEtiquetaSimple_ID(), get_TrxName());	}

	/** Set Z_ImpresionEtiquetaSimple ID.
		@param Z_ImpresionEtiquetaSimple_ID Z_ImpresionEtiquetaSimple ID	  */
	public void setZ_ImpresionEtiquetaSimple_ID (int Z_ImpresionEtiquetaSimple_ID)
	{
		if (Z_ImpresionEtiquetaSimple_ID < 1) 
			set_Value (COLUMNNAME_Z_ImpresionEtiquetaSimple_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ImpresionEtiquetaSimple_ID, Integer.valueOf(Z_ImpresionEtiquetaSimple_ID));
	}

	/** Get Z_ImpresionEtiquetaSimple ID.
		@return Z_ImpresionEtiquetaSimple ID	  */
	public int getZ_ImpresionEtiquetaSimple_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ImpresionEtiquetaSimple_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}