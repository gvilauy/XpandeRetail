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
	private static final long serialVersionUID = 20170618L;

    /** Standard Constructor */
    public X_Z_PautaComercialSet (Properties ctx, int Z_PautaComercialSet_ID, String trxName)
    {
      super (ctx, Z_PautaComercialSet_ID, trxName);
      /** if (Z_PautaComercialSet_ID == 0)
        {
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

	/** Set IsGeneral.
		@param IsGeneral IsGeneral	  */
	public void setIsGeneral (boolean IsGeneral)
	{
		set_Value (COLUMNNAME_IsGeneral, Boolean.valueOf(IsGeneral));
	}

	/** Get IsGeneral.
		@return IsGeneral	  */
	public boolean isGeneral () 
	{
		Object oo = get_Value(COLUMNNAME_IsGeneral);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	public I_Z_ProductoFamilia getZ_ProductoFamilia() throws RuntimeException
    {
		return (I_Z_ProductoFamilia)MTable.get(getCtx(), I_Z_ProductoFamilia.Table_Name)
			.getPO(getZ_ProductoFamilia_ID(), get_TrxName());	}

	/** Set Z_ProductoFamilia ID.
		@param Z_ProductoFamilia_ID Z_ProductoFamilia ID	  */
	public void setZ_ProductoFamilia_ID (int Z_ProductoFamilia_ID)
	{
		if (Z_ProductoFamilia_ID < 1) 
			set_Value (COLUMNNAME_Z_ProductoFamilia_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ProductoFamilia_ID, Integer.valueOf(Z_ProductoFamilia_ID));
	}

	/** Get Z_ProductoFamilia ID.
		@return Z_ProductoFamilia ID	  */
	public int getZ_ProductoFamilia_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ProductoFamilia_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_ProductoRubro getZ_ProductoRubro() throws RuntimeException
    {
		return (I_Z_ProductoRubro)MTable.get(getCtx(), I_Z_ProductoRubro.Table_Name)
			.getPO(getZ_ProductoRubro_ID(), get_TrxName());	}

	/** Set Z_ProductoRubro ID.
		@param Z_ProductoRubro_ID Z_ProductoRubro ID	  */
	public void setZ_ProductoRubro_ID (int Z_ProductoRubro_ID)
	{
		if (Z_ProductoRubro_ID < 1) 
			set_Value (COLUMNNAME_Z_ProductoRubro_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ProductoRubro_ID, Integer.valueOf(Z_ProductoRubro_ID));
	}

	/** Get Z_ProductoRubro ID.
		@return Z_ProductoRubro ID	  */
	public int getZ_ProductoRubro_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ProductoRubro_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_ProductoSeccion getZ_ProductoSeccion() throws RuntimeException
    {
		return (I_Z_ProductoSeccion)MTable.get(getCtx(), I_Z_ProductoSeccion.Table_Name)
			.getPO(getZ_ProductoSeccion_ID(), get_TrxName());	}

	/** Set Z_ProductoSeccion ID.
		@param Z_ProductoSeccion_ID Z_ProductoSeccion ID	  */
	public void setZ_ProductoSeccion_ID (int Z_ProductoSeccion_ID)
	{
		if (Z_ProductoSeccion_ID < 1) 
			set_Value (COLUMNNAME_Z_ProductoSeccion_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ProductoSeccion_ID, Integer.valueOf(Z_ProductoSeccion_ID));
	}

	/** Get Z_ProductoSeccion ID.
		@return Z_ProductoSeccion ID	  */
	public int getZ_ProductoSeccion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ProductoSeccion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_ProductoSubfamilia getZ_ProductoSubfamilia() throws RuntimeException
    {
		return (I_Z_ProductoSubfamilia)MTable.get(getCtx(), I_Z_ProductoSubfamilia.Table_Name)
			.getPO(getZ_ProductoSubfamilia_ID(), get_TrxName());	}

	/** Set Z_ProductoSubfamilia ID.
		@param Z_ProductoSubfamilia_ID Z_ProductoSubfamilia ID	  */
	public void setZ_ProductoSubfamilia_ID (int Z_ProductoSubfamilia_ID)
	{
		if (Z_ProductoSubfamilia_ID < 1) 
			set_Value (COLUMNNAME_Z_ProductoSubfamilia_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ProductoSubfamilia_ID, Integer.valueOf(Z_ProductoSubfamilia_ID));
	}

	/** Get Z_ProductoSubfamilia ID.
		@return Z_ProductoSubfamilia ID	  */
	public int getZ_ProductoSubfamilia_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ProductoSubfamilia_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}