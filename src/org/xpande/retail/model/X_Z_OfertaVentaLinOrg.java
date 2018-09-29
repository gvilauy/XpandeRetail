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

/** Generated Model for Z_OfertaVentaLinOrg
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_OfertaVentaLinOrg extends PO implements I_Z_OfertaVentaLinOrg, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180110L;

    /** Standard Constructor */
    public X_Z_OfertaVentaLinOrg (Properties ctx, int Z_OfertaVentaLinOrg_ID, String trxName)
    {
      super (ctx, Z_OfertaVentaLinOrg_ID, trxName);
      /** if (Z_OfertaVentaLinOrg_ID == 0)
        {
			setC_Currency_ID (0);
			setM_PriceList_ID (0);
			setM_PriceList_Version_ID (0);
			setZ_OfertaVentaLin_ID (0);
			setZ_OfertaVentaLinOrg_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_OfertaVentaLinOrg (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_OfertaVentaLinOrg[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Trx Organization.
		@param AD_OrgTrx_ID 
		Performing or initiating organization
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Trx Organization.
		@return Performing or initiating organization
	  */
	public int getAD_OrgTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
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

	/** Set Z_OfertaVentaLinOrg ID.
		@param Z_OfertaVentaLinOrg_ID Z_OfertaVentaLinOrg ID	  */
	public void setZ_OfertaVentaLinOrg_ID (int Z_OfertaVentaLinOrg_ID)
	{
		if (Z_OfertaVentaLinOrg_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_OfertaVentaLinOrg_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_OfertaVentaLinOrg_ID, Integer.valueOf(Z_OfertaVentaLinOrg_ID));
	}

	/** Get Z_OfertaVentaLinOrg ID.
		@return Z_OfertaVentaLinOrg ID	  */
	public int getZ_OfertaVentaLinOrg_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_OfertaVentaLinOrg_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}