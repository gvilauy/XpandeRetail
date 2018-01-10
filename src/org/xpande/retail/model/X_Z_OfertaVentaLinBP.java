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

/** Generated Model for Z_OfertaVentaLinBP
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_OfertaVentaLinBP extends PO implements I_Z_OfertaVentaLinBP, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180110L;

    /** Standard Constructor */
    public X_Z_OfertaVentaLinBP (Properties ctx, int Z_OfertaVentaLinBP_ID, String trxName)
    {
      super (ctx, Z_OfertaVentaLinBP_ID, trxName);
      /** if (Z_OfertaVentaLinBP_ID == 0)
        {
			setAplicanDtosPago (true);
// Y
			setC_BPartner_ID (0);
			setC_Currency_ID (0);
			setC_Currency_ID_To (0);
			setIsLockedPO (true);
// Y
			setM_PriceList_ID (0);
			setM_PriceList_Version_ID (0);
			setPricePO (Env.ZERO);
			setZ_OfertaVentaLinBP_ID (0);
			setZ_OfertaVentaLin_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_OfertaVentaLinBP (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_OfertaVentaLinBP[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AplicanDtosPago.
		@param AplicanDtosPago 
		Si aplican o no descuentos al pago
	  */
	public void setAplicanDtosPago (boolean AplicanDtosPago)
	{
		set_Value (COLUMNNAME_AplicanDtosPago, Boolean.valueOf(AplicanDtosPago));
	}

	/** Get AplicanDtosPago.
		@return Si aplican o no descuentos al pago
	  */
	public boolean isAplicanDtosPago () 
	{
		Object oo = get_Value(COLUMNNAME_AplicanDtosPago);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	public I_C_Currency getC_Currency_To() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID_To(), get_TrxName());	}

	/** Set Currency To.
		@param C_Currency_ID_To 
		Target currency
	  */
	public void setC_Currency_ID_To (int C_Currency_ID_To)
	{
		set_Value (COLUMNNAME_C_Currency_ID_To, Integer.valueOf(C_Currency_ID_To));
	}

	/** Get Currency To.
		@return Target currency
	  */
	public int getC_Currency_ID_To () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID_To);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set IsLockedPO.
		@param IsLockedPO 
		Si esta bloqueado para compras o no
	  */
	public void setIsLockedPO (boolean IsLockedPO)
	{
		set_Value (COLUMNNAME_IsLockedPO, Boolean.valueOf(IsLockedPO));
	}

	/** Get IsLockedPO.
		@return Si esta bloqueado para compras o no
	  */
	public boolean isLockedPO () 
	{
		Object oo = get_Value(COLUMNNAME_IsLockedPO);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set NewPricePO.
		@param NewPricePO 
		Nuevo precio de compra
	  */
	public void setNewPricePO (BigDecimal NewPricePO)
	{
		set_Value (COLUMNNAME_NewPricePO, NewPricePO);
	}

	/** Get NewPricePO.
		@return Nuevo precio de compra
	  */
	public BigDecimal getNewPricePO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NewPricePO);
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

	/** Set PricePOMargin.
		@param PricePOMargin PricePOMargin	  */
	public void setPricePOMargin (BigDecimal PricePOMargin)
	{
		set_Value (COLUMNNAME_PricePOMargin, PricePOMargin);
	}

	/** Get PricePOMargin.
		@return PricePOMargin	  */
	public BigDecimal getPricePOMargin () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PricePOMargin);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Z_OfertaVentaLinBP ID.
		@param Z_OfertaVentaLinBP_ID Z_OfertaVentaLinBP ID	  */
	public void setZ_OfertaVentaLinBP_ID (int Z_OfertaVentaLinBP_ID)
	{
		if (Z_OfertaVentaLinBP_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_OfertaVentaLinBP_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_OfertaVentaLinBP_ID, Integer.valueOf(Z_OfertaVentaLinBP_ID));
	}

	/** Get Z_OfertaVentaLinBP ID.
		@return Z_OfertaVentaLinBP ID	  */
	public int getZ_OfertaVentaLinBP_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_OfertaVentaLinBP_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_OfertaVentaLin getZ_OfertaVentaLin() throws RuntimeException
    {
		return (I_Z_OfertaVentaLin)MTable.get(getCtx(), I_Z_OfertaVentaLin.Table_Name)
			.getPO(getZ_OfertaVentaLin_ID(), get_TrxName());	}

	/** Set Z_OfertaVentaLin ID.
		@param Z_OfertaVentaLin_ID Z_OfertaVentaLin ID	  */
	public void setZ_OfertaVentaLin_ID (int Z_OfertaVentaLin_ID)
	{
		if (Z_OfertaVentaLin_ID < 1) 
			set_Value (COLUMNNAME_Z_OfertaVentaLin_ID, null);
		else 
			set_Value (COLUMNNAME_Z_OfertaVentaLin_ID, Integer.valueOf(Z_OfertaVentaLin_ID));
	}

	/** Get Z_OfertaVentaLin ID.
		@return Z_OfertaVentaLin ID	  */
	public int getZ_OfertaVentaLin_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_OfertaVentaLin_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}