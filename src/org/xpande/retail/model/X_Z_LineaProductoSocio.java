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
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for Z_LineaProductoSocio
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_LineaProductoSocio extends PO implements I_Z_LineaProductoSocio, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170714L;

    /** Standard Constructor */
    public X_Z_LineaProductoSocio (Properties ctx, int Z_LineaProductoSocio_ID, String trxName)
    {
      super (ctx, Z_LineaProductoSocio_ID, trxName);
      /** if (Z_LineaProductoSocio_ID == 0)
        {
			setC_BPartner_ID (0);
			setIsLockedPO (false);
// N
			setIsOwn (true);
// Y
			setM_PriceList_ID (0);
			setZ_LineaProductoGral_ID (0);
			setZ_LineaProductoSocio_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_LineaProductoSocio (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_LineaProductoSocio[")
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

	public I_C_BPartner getC_BPartnerRelation() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_BPartnerRelation_ID(), get_TrxName());	}

	/** Set Related Partner.
		@param C_BPartnerRelation_ID 
		Related Business Partner
	  */
	public void setC_BPartnerRelation_ID (int C_BPartnerRelation_ID)
	{
		if (C_BPartnerRelation_ID < 1) 
			set_Value (COLUMNNAME_C_BPartnerRelation_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartnerRelation_ID, Integer.valueOf(C_BPartnerRelation_ID));
	}

	/** Get Related Partner.
		@return Related Business Partner
	  */
	public int getC_BPartnerRelation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartnerRelation_ID);
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
		throw new IllegalArgumentException ("C_Currency_ID is virtual column");	}

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

	/** Set IsOwn.
		@param IsOwn 
		Si le pertenece o no
	  */
	public void setIsOwn (boolean IsOwn)
	{
		set_Value (COLUMNNAME_IsOwn, Boolean.valueOf(IsOwn));
	}

	/** Get IsOwn.
		@return Si le pertenece o no
	  */
	public boolean isOwn () 
	{
		Object oo = get_Value(COLUMNNAME_IsOwn);
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

	public I_Z_LineaProductoGral getZ_LineaProductoGral() throws RuntimeException
    {
		return (I_Z_LineaProductoGral)MTable.get(getCtx(), I_Z_LineaProductoGral.Table_Name)
			.getPO(getZ_LineaProductoGral_ID(), get_TrxName());	}

	/** Set Z_LineaProductoGral ID.
		@param Z_LineaProductoGral_ID Z_LineaProductoGral ID	  */
	public void setZ_LineaProductoGral_ID (int Z_LineaProductoGral_ID)
	{
		if (Z_LineaProductoGral_ID < 1) 
			set_Value (COLUMNNAME_Z_LineaProductoGral_ID, null);
		else 
			set_Value (COLUMNNAME_Z_LineaProductoGral_ID, Integer.valueOf(Z_LineaProductoGral_ID));
	}

	/** Get Z_LineaProductoGral ID.
		@return Z_LineaProductoGral ID	  */
	public int getZ_LineaProductoGral_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_LineaProductoGral_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_LineaProductoSocio ID.
		@param Z_LineaProductoSocio_ID Z_LineaProductoSocio ID	  */
	public void setZ_LineaProductoSocio_ID (int Z_LineaProductoSocio_ID)
	{
		if (Z_LineaProductoSocio_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_LineaProductoSocio_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_LineaProductoSocio_ID, Integer.valueOf(Z_LineaProductoSocio_ID));
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

	public I_Z_PautaComercial getZ_PautaComercial() throws RuntimeException
    {
		return (I_Z_PautaComercial)MTable.get(getCtx(), I_Z_PautaComercial.Table_Name)
			.getPO(getZ_PautaComercial_ID(), get_TrxName());	}

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