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

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for Z_PautaComercial
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_PautaComercial extends PO implements I_Z_PautaComercial, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170617L;

    /** Standard Constructor */
    public X_Z_PautaComercial (Properties ctx, int Z_PautaComercial_ID, String trxName)
    {
      super (ctx, Z_PautaComercial_ID, trxName);
      /** if (Z_PautaComercial_ID == 0)
        {
			setC_BPartner_ID (0);
			setName (null);
			setZ_PautaComercial_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_PautaComercial (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_PautaComercial[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set DateValidFrom.
		@param DateValidFrom 
		Fecha Vigencia Desde
	  */
	public void setDateValidFrom (Timestamp DateValidFrom)
	{
		set_Value (COLUMNNAME_DateValidFrom, DateValidFrom);
	}

	/** Get DateValidFrom.
		@return Fecha Vigencia Desde
	  */
	public Timestamp getDateValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateValidFrom);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
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

	/** Set ProcessButton.
		@param ProcessButton ProcessButton	  */
	public void setProcessButton (String ProcessButton)
	{
		set_Value (COLUMNNAME_ProcessButton, ProcessButton);
	}

	/** Get ProcessButton.
		@return ProcessButton	  */
	public String getProcessButton () 
	{
		return (String)get_Value(COLUMNNAME_ProcessButton);
	}

	public I_Z_LineaProductoSocio getZ_LineaProductoSocio() throws RuntimeException
    {
		return (I_Z_LineaProductoSocio)MTable.get(getCtx(), I_Z_LineaProductoSocio.Table_Name)
			.getPO(getZ_LineaProductoSocio_ID(), get_TrxName());	}

	/** Set Z_LineaProductoSocio ID.
		@param Z_LineaProductoSocio_ID Z_LineaProductoSocio ID	  */
	public void setZ_LineaProductoSocio_ID (int Z_LineaProductoSocio_ID)
	{
		if (Z_LineaProductoSocio_ID < 1) 
			set_Value (COLUMNNAME_Z_LineaProductoSocio_ID, null);
		else 
			set_Value (COLUMNNAME_Z_LineaProductoSocio_ID, Integer.valueOf(Z_LineaProductoSocio_ID));
	}

	/** Get Z_LineaProductoSocio ID.
		@return Z_LineaProductoSocio ID	  */
	public int getZ_LineaProductoSocio_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_LineaProductoSocio_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_PautaComercial ID.
		@param Z_PautaComercial_ID Z_PautaComercial ID	  */
	public void setZ_PautaComercial_ID (int Z_PautaComercial_ID)
	{
		if (Z_PautaComercial_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_PautaComercial_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_PautaComercial_ID, Integer.valueOf(Z_PautaComercial_ID));
	}

	/** Get Z_PautaComercial ID.
		@return Z_PautaComercial ID	  */
	public int getZ_PautaComercial_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_PautaComercial_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}