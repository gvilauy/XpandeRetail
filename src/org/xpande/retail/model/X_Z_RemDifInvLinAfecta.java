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

/** Generated Model for Z_RemDifInvLinAfecta
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_RemDifInvLinAfecta extends PO implements I_Z_RemDifInvLinAfecta, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180227L;

    /** Standard Constructor */
    public X_Z_RemDifInvLinAfecta (Properties ctx, int Z_RemDifInvLinAfecta_ID, String trxName)
    {
      super (ctx, Z_RemDifInvLinAfecta_ID, trxName);
      /** if (Z_RemDifInvLinAfecta_ID == 0)
        {
			setAmtDocument (Env.ZERO);
			setAmtOpen (Env.ZERO);
			setC_InvoiceLine_ID (0);
			setC_UOM_ID (0);
			setIsDifferenceAmt (false);
// N
			setIsDifferenceQty (false);
// N
			setPriceDoc (Env.ZERO);
			setPriceEntered (Env.ZERO);
			setQtyDocument (Env.ZERO);
			setQtyEntered (Env.ZERO);
			setQtyOpen (Env.ZERO);
			setZ_RemDifInvLinAfecta_ID (0);
			setZ_RemitoDifInv_ID (0);
			setZ_RemitoDifInvLin_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_RemDifInvLinAfecta (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_RemDifInvLinAfecta[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AmtDocument.
		@param AmtDocument 
		Monto documento
	  */
	public void setAmtDocument (BigDecimal AmtDocument)
	{
		set_Value (COLUMNNAME_AmtDocument, AmtDocument);
	}

	/** Get AmtDocument.
		@return Monto documento
	  */
	public BigDecimal getAmtDocument () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtDocument);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtOpen.
		@param AmtOpen 
		Monto pendiente
	  */
	public void setAmtOpen (BigDecimal AmtOpen)
	{
		set_Value (COLUMNNAME_AmtOpen, AmtOpen);
	}

	/** Get AmtOpen.
		@return Monto pendiente
	  */
	public BigDecimal getAmtOpen () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtOpen);
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

	/** Set Line Total.
		@param LineTotalAmt 
		Total line amount incl. Tax
	  */
	public void setLineTotalAmt (BigDecimal LineTotalAmt)
	{
		set_Value (COLUMNNAME_LineTotalAmt, LineTotalAmt);
	}

	/** Get Line Total.
		@return Total line amount incl. Tax
	  */
	public BigDecimal getLineTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineTotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set PriceDoc.
		@param PriceDoc 
		Precio del documento o linea
	  */
	public void setPriceDoc (BigDecimal PriceDoc)
	{
		set_Value (COLUMNNAME_PriceDoc, PriceDoc);
	}

	/** Get PriceDoc.
		@return Precio del documento o linea
	  */
	public BigDecimal getPriceDoc () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceDoc);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Price.
		@param PriceEntered 
		Price Entered - the price based on the selected/base UoM
	  */
	public void setPriceEntered (BigDecimal PriceEntered)
	{
		set_Value (COLUMNNAME_PriceEntered, PriceEntered);
	}

	/** Get Price.
		@return Price Entered - the price based on the selected/base UoM
	  */
	public BigDecimal getPriceEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceEntered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyDocument.
		@param QtyDocument 
		Cantidad original del documento o linea
	  */
	public void setQtyDocument (BigDecimal QtyDocument)
	{
		set_Value (COLUMNNAME_QtyDocument, QtyDocument);
	}

	/** Get QtyDocument.
		@return Cantidad original del documento o linea
	  */
	public BigDecimal getQtyDocument () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDocument);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity.
		@param QtyEntered 
		The Quantity Entered is based on the selected UoM
	  */
	public void setQtyEntered (BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	/** Get Quantity.
		@return The Quantity Entered is based on the selected UoM
	  */
	public BigDecimal getQtyEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyEntered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QtyOpen.
		@param QtyOpen 
		Cantidad pendiente del documento o linea
	  */
	public void setQtyOpen (BigDecimal QtyOpen)
	{
		set_Value (COLUMNNAME_QtyOpen, QtyOpen);
	}

	/** Get QtyOpen.
		@return Cantidad pendiente del documento o linea
	  */
	public BigDecimal getQtyOpen () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOpen);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Referenced Invoice.
		@param Ref_Invoice_ID Referenced Invoice	  */
	public void setRef_Invoice_ID (int Ref_Invoice_ID)
	{
		if (Ref_Invoice_ID < 1) 
			set_Value (COLUMNNAME_Ref_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_Ref_Invoice_ID, Integer.valueOf(Ref_Invoice_ID));
	}

	/** Get Referenced Invoice.
		@return Referenced Invoice	  */
	public int getRef_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ref_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_RemDifInvLinAfecta ID.
		@param Z_RemDifInvLinAfecta_ID Z_RemDifInvLinAfecta ID	  */
	public void setZ_RemDifInvLinAfecta_ID (int Z_RemDifInvLinAfecta_ID)
	{
		if (Z_RemDifInvLinAfecta_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_RemDifInvLinAfecta_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_RemDifInvLinAfecta_ID, Integer.valueOf(Z_RemDifInvLinAfecta_ID));
	}

	/** Get Z_RemDifInvLinAfecta ID.
		@return Z_RemDifInvLinAfecta ID	  */
	public int getZ_RemDifInvLinAfecta_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_RemDifInvLinAfecta_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_Z_RemitoDifInvLin getZ_RemitoDifInvLin() throws RuntimeException
    {
		return (I_Z_RemitoDifInvLin)MTable.get(getCtx(), I_Z_RemitoDifInvLin.Table_Name)
			.getPO(getZ_RemitoDifInvLin_ID(), get_TrxName());	}

	/** Set Z_RemitoDifInvLin ID.
		@param Z_RemitoDifInvLin_ID Z_RemitoDifInvLin ID	  */
	public void setZ_RemitoDifInvLin_ID (int Z_RemitoDifInvLin_ID)
	{
		if (Z_RemitoDifInvLin_ID < 1) 
			set_Value (COLUMNNAME_Z_RemitoDifInvLin_ID, null);
		else 
			set_Value (COLUMNNAME_Z_RemitoDifInvLin_ID, Integer.valueOf(Z_RemitoDifInvLin_ID));
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