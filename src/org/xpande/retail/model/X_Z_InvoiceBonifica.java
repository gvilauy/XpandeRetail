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

/** Generated Model for Z_InvoiceBonifica
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_InvoiceBonifica extends PO implements I_Z_InvoiceBonifica, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180521L;

    /** Standard Constructor */
    public X_Z_InvoiceBonifica (Properties ctx, int Z_InvoiceBonifica_ID, String trxName)
    {
      super (ctx, Z_InvoiceBonifica_ID, trxName);
      /** if (Z_InvoiceBonifica_ID == 0)
        {
			setC_Invoice_ID (0);
			setC_InvoiceLine_ID (0);
			setIsManual (true);
// Y
			setLine (0);
			setM_Product_ID (0);
			setQtyBase (Env.ZERO);
			setQtyCalculated (Env.ZERO);
			setQtyReward (Env.ZERO);
			setTipoBonificaQty (null);
// CRUZADA
			setZ_InvoiceBonifica_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_InvoiceBonifica (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_InvoiceBonifica[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (I_C_Invoice)MTable.get(getCtx(), I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException
    {
		return (I_C_InvoiceLine)MTable.get(getCtx(), I_C_InvoiceLine.Table_Name)
			.getPO(getC_InvoiceLine_ID(), get_TrxName());	}

	/** Set Invoice Line.
		@param C_InvoiceLine_ID 
		Invoice Detail Line
	  */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID)
	{
		if (C_InvoiceLine_ID < 1) 
			set_Value (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
	}

	/** Get Invoice Line.
		@return Invoice Detail Line
	  */
	public int getC_InvoiceLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Manual.
		@param IsManual 
		This is a manual process
	  */
	public void setIsManual (boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, Boolean.valueOf(IsManual));
	}

	/** Get Manual.
		@return This is a manual process
	  */
	public boolean isManual () 
	{
		Object oo = get_Value(COLUMNNAME_IsManual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Line No.
		@param Line 
		Unique line for this document
	  */
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Line No.
		@return Unique line for this document
	  */
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
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

	/** Set To Product.
		@param M_Product_To_ID 
		Product to be converted to (must have UOM Conversion defined to From Product)
	  */
	public void setM_Product_To_ID (int M_Product_To_ID)
	{
		if (M_Product_To_ID < 1) 
			set_Value (COLUMNNAME_M_Product_To_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_To_ID, Integer.valueOf(M_Product_To_ID));
	}

	/** Get To Product.
		@return Product to be converted to (must have UOM Conversion defined to From Product)
	  */
	public int getM_Product_To_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_To_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PO Price.
		@param PricePO 
		Price based on a purchase order
	  */
	public void setPricePO (BigDecimal PricePO)
	{
		set_Value (COLUMNNAME_PricePO, PricePO);
	}

	/** Get PO Price.
		@return Price based on a purchase order
	  */
	public BigDecimal getPricePO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PricePO);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyBase.
		@param QtyBase 
		Cantidad Base
	  */
	public void setQtyBase (BigDecimal QtyBase)
	{
		set_Value (COLUMNNAME_QtyBase, QtyBase);
	}

	/** Get QtyBase.
		@return Cantidad Base
	  */
	public BigDecimal getQtyBase () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyBase);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Calculated Quantity.
		@param QtyCalculated 
		Calculated Quantity
	  */
	public void setQtyCalculated (BigDecimal QtyCalculated)
	{
		set_Value (COLUMNNAME_QtyCalculated, QtyCalculated);
	}

	/** Get Calculated Quantity.
		@return Calculated Quantity
	  */
	public BigDecimal getQtyCalculated () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyCalculated);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Reward Quantity.
		@param QtyReward Reward Quantity	  */
	public void setQtyReward (BigDecimal QtyReward)
	{
		set_Value (COLUMNNAME_QtyReward, QtyReward);
	}

	/** Get Reward Quantity.
		@return Reward Quantity	  */
	public BigDecimal getQtyReward () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReward);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** TipoBonificaQty AD_Reference_ID=1000033 */
	public static final int TIPOBONIFICAQTY_AD_Reference_ID=1000033;
	/** MISMO PRODUCTO = SIMPLE */
	public static final String TIPOBONIFICAQTY_MISMOPRODUCTO = "SIMPLE";
	/** PRODUCTOS DEL SEGMENTO = SIMPLE_SET */
	public static final String TIPOBONIFICAQTY_PRODUCTOSDELSEGMENTO = "SIMPLE_SET";
	/** PRODUCTOS DEL SOCIO DE NEGOCIO = CRUZADA */
	public static final String TIPOBONIFICAQTY_PRODUCTOSDELSOCIODENEGOCIO = "CRUZADA";
	/** Set TipoBonificaQty.
		@param TipoBonificaQty 
		Lista de valores para Tipos de Bonificacion en Cantidadades
	  */
	public void setTipoBonificaQty (String TipoBonificaQty)
	{

		set_Value (COLUMNNAME_TipoBonificaQty, TipoBonificaQty);
	}

	/** Get TipoBonificaQty.
		@return Lista de valores para Tipos de Bonificacion en Cantidadades
	  */
	public String getTipoBonificaQty () 
	{
		return (String)get_Value(COLUMNNAME_TipoBonificaQty);
	}

	/** Set Z_InvoiceBonifica ID.
		@param Z_InvoiceBonifica_ID Z_InvoiceBonifica ID	  */
	public void setZ_InvoiceBonifica_ID (int Z_InvoiceBonifica_ID)
	{
		if (Z_InvoiceBonifica_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_InvoiceBonifica_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_InvoiceBonifica_ID, Integer.valueOf(Z_InvoiceBonifica_ID));
	}

	/** Get Z_InvoiceBonifica ID.
		@return Z_InvoiceBonifica ID	  */
	public int getZ_InvoiceBonifica_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_InvoiceBonifica_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_PautaComercialSet getZ_PautaComercialSet() throws RuntimeException
    {
		return (I_Z_PautaComercialSet)MTable.get(getCtx(), I_Z_PautaComercialSet.Table_Name)
			.getPO(getZ_PautaComercialSet_ID(), get_TrxName());	}

	/** Set Z_PautaComercialSet ID.
		@param Z_PautaComercialSet_ID Z_PautaComercialSet ID	  */
	public void setZ_PautaComercialSet_ID (int Z_PautaComercialSet_ID)
	{
		if (Z_PautaComercialSet_ID < 1) 
			set_Value (COLUMNNAME_Z_PautaComercialSet_ID, null);
		else 
			set_Value (COLUMNNAME_Z_PautaComercialSet_ID, Integer.valueOf(Z_PautaComercialSet_ID));
	}

	/** Get Z_PautaComercialSet ID.
		@return Z_PautaComercialSet ID	  */
	public int getZ_PautaComercialSet_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_PautaComercialSet_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}