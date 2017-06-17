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

/** Generated Model for Z_PautaComercialSet
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_PautaComercialSet extends PO implements I_Z_PautaComercialSet, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170616L;

    /** Standard Constructor */
    public X_Z_PautaComercialSet (Properties ctx, int Z_PautaComercialSet_ID, String trxName)
    {
      super (ctx, Z_PautaComercialSet_ID, trxName);
      /** if (Z_PautaComercialSet_ID == 0)
        {
			setDateValidFrom (new Timestamp( System.currentTimeMillis() ));
			setName (null);
			setZ_PautaComercial_ID (0);
			setZ_PautaComercialSet_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_PautaComercialSet (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_PautaComercialSet[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public I_Z_PautaComercial getZ_PautaComercial() throws RuntimeException
    {
		return (I_Z_PautaComercial)MTable.get(getCtx(), I_Z_PautaComercial.Table_Name)
			.getPO(getZ_PautaComercial_ID(), get_TrxName());	}

	/** Set Z_PautaComercial ID.
		@param Z_PautaComercial_ID Z_PautaComercial ID	  */
	public void setZ_PautaComercial_ID (int Z_PautaComercial_ID)
	{
		if (Z_PautaComercial_ID < 1) 
			set_Value (COLUMNNAME_Z_PautaComercial_ID, null);
		else 
			set_Value (COLUMNNAME_Z_PautaComercial_ID, Integer.valueOf(Z_PautaComercial_ID));
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

	/** Set Z_PautaComercialSet ID.
		@param Z_PautaComercialSet_ID Z_PautaComercialSet ID	  */
	public void setZ_PautaComercialSet_ID (int Z_PautaComercialSet_ID)
	{
		if (Z_PautaComercialSet_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_PautaComercialSet_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_PautaComercialSet_ID, Integer.valueOf(Z_PautaComercialSet_ID));
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