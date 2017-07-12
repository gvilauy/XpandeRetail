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

/** Generated Model for Z_ProductoRubro
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_ProductoRubro extends PO implements I_Z_ProductoRubro, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170711L;

    /** Standard Constructor */
    public X_Z_ProductoRubro (Properties ctx, int Z_ProductoRubro_ID, String trxName)
    {
      super (ctx, Z_ProductoRubro_ID, trxName);
      /** if (Z_ProductoRubro_ID == 0)
        {
			setName (null);
			setZ_ProductoRubro_ID (0);
			setZ_ProductoSeccion_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_ProductoRubro (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_ProductoRubro[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set CodigoRubroPos.
		@param CodigoRubroPos 
		Código Rubro para interface POS
	  */
	public void setCodigoRubroPos (String CodigoRubroPos)
	{
		set_Value (COLUMNNAME_CodigoRubroPos, CodigoRubroPos);
	}

	/** Get CodigoRubroPos.
		@return Código Rubro para interface POS
	  */
	public String getCodigoRubroPos () 
	{
		return (String)get_Value(COLUMNNAME_CodigoRubroPos);
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

	/** Set Margin %.
		@param Margin 
		Margin for a product as a percentage
	  */
	public void setMargin (BigDecimal Margin)
	{
		set_Value (COLUMNNAME_Margin, Margin);
	}

	/** Get Margin %.
		@return Margin for a product as a percentage
	  */
	public BigDecimal getMargin () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Margin);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Z_ProductoRubro ID.
		@param Z_ProductoRubro_ID Z_ProductoRubro ID	  */
	public void setZ_ProductoRubro_ID (int Z_ProductoRubro_ID)
	{
		if (Z_ProductoRubro_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_ProductoRubro_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_ProductoRubro_ID, Integer.valueOf(Z_ProductoRubro_ID));
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
}