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

/** Generated Model for Z_RemitoDifInvLin
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_RemitoDifInvLin extends PO implements I_Z_RemitoDifInvLin, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180226L;

    /** Standard Constructor */
    public X_Z_RemitoDifInvLin (Properties ctx, int Z_RemitoDifInvLin_ID, String trxName)
    {
      super (ctx, Z_RemitoDifInvLin_ID, trxName);
      /** if (Z_RemitoDifInvLin_ID == 0)
        {
			setC_InvoiceLine_ID (0);
			setC_UOM_ID (0);
			setIsDifferenceAmt (false);
// N
			setIsDifferenceQty (false);
// N
			setZ_RemitoDifInv_ID (0);
			setZ_RemitoDifInvLin_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_RemitoDifInvLin (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_RemitoDifInvLin[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AmtSubtotal.
		@param AmtSubtotal 
		Subtotales para no mostrar impuestos incluídos
	  */
	public void setAmtSubtotal (BigDecimal AmtSubtotal)
	{
		set_Value (COLUMNNAME_AmtSubtotal, AmtSubtotal);
	}

	/** Get AmtSubtotal.
		@return Subtotales para no mostrar impuestos incluídos
	  */
	public BigDecimal getAmtSubtotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtSubtotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtSubtotalPO.
		@param AmtSubtotalPO 
		Subtotal considerando precio pactado para orden de compra
	  */
	public void setAmtSubtotalPO (BigDecimal AmtSubtotalPO)
	{
		set_Value (COLUMNNAME_AmtSubtotalPO, AmtSubtotalPO);
	}

	/** Get AmtSubtotalPO.
		@return Subtotal considerando precio pactado para orden de compra
	  */
	public BigDecimal getAmtSubtotalPO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtSubtotalPO);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public I_C_UOM getC_UOM() throws RuntimeException
    {
		return (I_C_UOM)MTable.get(getCtx(), I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Difference.
		@param DifferenceAmt 
		Difference Amount
	  */
	public void setDifferenceAmt (BigDecimal DifferenceAmt)
	{
		set_Value (COLUMNNAME_DifferenceAmt, DifferenceAmt);
	}

	/** Get Difference.
		@return Difference Amount
	  */
	public BigDecimal getDifferenceAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DifferenceAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set DifferencePrice.
		@param DifferencePrice 
		Diferencia entre precios
	  */
	public void setDifferencePrice (BigDecimal DifferencePrice)
	{
		set_Value (COLUMNNAME_DifferencePrice, DifferencePrice);
	}

	/** Get DifferencePrice.
		@return Diferencia entre precios
	  */
	public BigDecimal getDifferencePrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DifferencePrice);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Difference.
		@param DifferenceQty 
		Difference Quantity
	  */
	public void setDifferenceQty (BigDecimal DifferenceQty)
	{
		set_Value (COLUMNNAME_DifferenceQty, DifferenceQty);
	}

	/** Get Difference.
		@return Difference Quantity
	  */
	public BigDecimal getDifferenceQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DifferenceQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set IsDifferenceAmt.
		@param IsDifferenceAmt 
		Si tiene o no diferencia por montos
	  */
	public void setIsDifferenceAmt (boolean IsDifferenceAmt)
	{
		set_Value (COLUMNNAME_IsDifferenceAmt, Boolean.valueOf(IsDifferenceAmt));
	}

	/** Get IsDifferenceAmt.
		@return Si tiene o no diferencia por montos
	  */
	public boolean isDifferenceAmt () 
	{
		Object oo = get_Value(COLUMNNAME_IsDifferenceAmt);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDifferenceQty.
		@param IsDifferenceQty 
		Si tiene o no diferencia de cantidades
	  */
	public void setIsDifferenceQty (boolean IsDifferenceQty)
	{
		set_Value (COLUMNNAME_IsDifferenceQty, Boolean.valueOf(IsDifferenceQty));
	}

	/** Get IsDifferenceQty.
		@return Si tiene o no diferencia de cantidades
	  */
	public boolean isDifferenceQty () 
	{
		Object oo = get_Value(COLUMNNAME_IsDifferenceQty);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_M_InOutLine getM_InOutLine() throws RuntimeException
    {
		return (I_M_InOutLine)MTable.get(getCtx(), I_M_InOutLine.Table_Name)
			.getPO(getM_InOutLine_ID(), get_TrxName());	}

	/** Set Shipment/Receipt Line.
		@param M_InOutLine_ID 
		Line on Shipment or Receipt document
	  */
	public void setM_InOutLine_ID (int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_Value (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
	}

	/** Get Shipment/Receipt Line.
		@return Line on Shipment or Receipt document
	  */
	public int getM_InOutLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOutLine_ID);
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

	/** Set Price Invoiced.
		@param PriceInvoiced 
		The priced invoiced to the customer (in the currency of the customer's AR price list) - 0 for default price
	  */
	public void setPriceInvoiced (BigDecimal PriceInvoiced)
	{
		set_Value (COLUMNNAME_PriceInvoiced, PriceInvoiced);
	}

	/** Get Price Invoiced.
		@return The priced invoiced to the customer (in the currency of the customer's AR price list) - 0 for default price
	  */
	public BigDecimal getPriceInvoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceInvoiced);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Delivered Quantity.
		@param QtyDelivered 
		Delivered Quantity
	  */
	public void setQtyDelivered (BigDecimal QtyDelivered)
	{
		set_Value (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	/** Get Delivered Quantity.
		@return Delivered Quantity
	  */
	public BigDecimal getQtyDelivered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDelivered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity Invoiced.
		@param QtyInvoiced 
		Invoiced Quantity
	  */
	public void setQtyInvoiced (BigDecimal QtyInvoiced)
	{
		set_Value (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	/** Get Quantity Invoiced.
		@return Invoiced Quantity
	  */
	public BigDecimal getQtyInvoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyInvoiced);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_Z_RemitoDifInv getZ_RemitoDifInv() throws RuntimeException
    {
		return (I_Z_RemitoDifInv)MTable.get(getCtx(), I_Z_RemitoDifInv.Table_Name)
			.getPO(getZ_RemitoDifInv_ID(), get_TrxName());	}

	/** Set Z_RemitoDifInv ID.
		@param Z_RemitoDifInv_ID Z_RemitoDifInv ID	  */
	public void setZ_RemitoDifInv_ID (int Z_RemitoDifInv_ID)
	{
		if (Z_RemitoDifInv_ID < 1) 
			set_Value (COLUMNNAME_Z_RemitoDifInv_ID, null);
		else 
			set_Value (COLUMNNAME_Z_RemitoDifInv_ID, Integer.valueOf(Z_RemitoDifInv_ID));
	}

	/** Get Z_RemitoDifInv ID.
		@return Z_RemitoDifInv ID	  */
	public int getZ_RemitoDifInv_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_RemitoDifInv_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_RemitoDifInvLin ID.
		@param Z_RemitoDifInvLin_ID Z_RemitoDifInvLin ID	  */
	public void setZ_RemitoDifInvLin_ID (int Z_RemitoDifInvLin_ID)
	{
		if (Z_RemitoDifInvLin_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_RemitoDifInvLin_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_RemitoDifInvLin_ID, Integer.valueOf(Z_RemitoDifInvLin_ID));
	}

	/** Get Z_RemitoDifInvLin ID.
		@return Z_RemitoDifInvLin ID	  */
	public int getZ_RemitoDifInvLin_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_RemitoDifInvLin_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}