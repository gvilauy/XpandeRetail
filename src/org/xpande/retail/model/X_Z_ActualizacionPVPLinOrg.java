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

/** Generated Model for Z_ActualizacionPVPLinOrg
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_ActualizacionPVPLinOrg extends PO implements I_Z_ActualizacionPVPLinOrg, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170719L;

    /** Standard Constructor */
    public X_Z_ActualizacionPVPLinOrg (Properties ctx, int Z_ActualizacionPVPLinOrg_ID, String trxName)
    {
      super (ctx, Z_ActualizacionPVPLinOrg_ID, trxName);
      /** if (Z_ActualizacionPVPLinOrg_ID == 0)
        {
			setAD_OrgTrx_ID (0);
			setC_Currency_ID (0);
			setNewPriceSO (Env.ZERO);
			setPriceFinal (Env.ZERO);
			setPriceFinalMargin (Env.ZERO);
			setPricePO (Env.ZERO);
			setPricePOMargin (Env.ZERO);
			setPriceSO (Env.ZERO);
			setZ_ActualizacionPVPLin_ID (0);
			setZ_ActualizacionPVPLinOrg_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_ActualizacionPVPLinOrg (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_ActualizacionPVPLinOrg[")
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

	/** Set NewPriceSO.
		@param NewPriceSO 
		NewPriceSO
	  */
	public void setNewPriceSO (BigDecimal NewPriceSO)
	{
		set_Value (COLUMNNAME_NewPriceSO, NewPriceSO);
	}

	/** Get NewPriceSO.
		@return NewPriceSO
	  */
	public BigDecimal getNewPriceSO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NewPriceSO);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PriceFinal.
		@param PriceFinal PriceFinal	  */
	public void setPriceFinal (BigDecimal PriceFinal)
	{
		set_Value (COLUMNNAME_PriceFinal, PriceFinal);
	}

	/** Get PriceFinal.
		@return PriceFinal	  */
	public BigDecimal getPriceFinal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceFinal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PriceFinalMargin.
		@param PriceFinalMargin PriceFinalMargin	  */
	public void setPriceFinalMargin (BigDecimal PriceFinalMargin)
	{
		set_Value (COLUMNNAME_PriceFinalMargin, PriceFinalMargin);
	}

	/** Get PriceFinalMargin.
		@return PriceFinalMargin	  */
	public BigDecimal getPriceFinalMargin () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceFinalMargin);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set PriceInvoicedMargin.
		@param PriceInvoicedMargin PriceInvoicedMargin	  */
	public void setPriceInvoicedMargin (BigDecimal PriceInvoicedMargin)
	{
		set_Value (COLUMNNAME_PriceInvoicedMargin, PriceInvoicedMargin);
	}

	/** Get PriceInvoicedMargin.
		@return PriceInvoicedMargin	  */
	public BigDecimal getPriceInvoicedMargin () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceInvoicedMargin);
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

	public org.xpande.retail.model.I_Z_ActualizacionPVPLin getZ_ActualizacionPVPLin() throws RuntimeException
    {
		return (org.xpande.retail.model.I_Z_ActualizacionPVPLin)MTable.get(getCtx(), org.xpande.retail.model.I_Z_ActualizacionPVPLin.Table_Name)
			.getPO(getZ_ActualizacionPVPLin_ID(), get_TrxName());	}

	/** Set Z_ActualizacionPVPLin ID.
		@param Z_ActualizacionPVPLin_ID Z_ActualizacionPVPLin ID	  */
	public void setZ_ActualizacionPVPLin_ID (int Z_ActualizacionPVPLin_ID)
	{
		if (Z_ActualizacionPVPLin_ID < 1) 
			set_Value (COLUMNNAME_Z_ActualizacionPVPLin_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ActualizacionPVPLin_ID, Integer.valueOf(Z_ActualizacionPVPLin_ID));
	}

	/** Get Z_ActualizacionPVPLin ID.
		@return Z_ActualizacionPVPLin ID	  */
	public int getZ_ActualizacionPVPLin_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ActualizacionPVPLin_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_ActualizacionPVPLinOrg ID.
		@param Z_ActualizacionPVPLinOrg_ID Z_ActualizacionPVPLinOrg ID	  */
	public void setZ_ActualizacionPVPLinOrg_ID (int Z_ActualizacionPVPLinOrg_ID)
	{
		if (Z_ActualizacionPVPLinOrg_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_ActualizacionPVPLinOrg_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_ActualizacionPVPLinOrg_ID, Integer.valueOf(Z_ActualizacionPVPLinOrg_ID));
	}

	/** Get Z_ActualizacionPVPLinOrg ID.
		@return Z_ActualizacionPVPLinOrg ID	  */
	public int getZ_ActualizacionPVPLinOrg_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ActualizacionPVPLinOrg_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}