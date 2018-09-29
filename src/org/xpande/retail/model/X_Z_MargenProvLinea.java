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

/** Generated Model for Z_MargenProvLinea
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_MargenProvLinea extends PO implements I_Z_MargenProvLinea, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20171128L;

    /** Standard Constructor */
    public X_Z_MargenProvLinea (Properties ctx, int Z_MargenProvLinea_ID, String trxName)
    {
      super (ctx, Z_MargenProvLinea_ID, trxName);
      /** if (Z_MargenProvLinea_ID == 0)
        {
			setZ_LineaProductoSocio_ID (0);
			setZ_MargenProv_ID (0);
			setZ_MargenProvLinea_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_MargenProvLinea (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_MargenProvLinea[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set MarginTolerance.
		@param MarginTolerance 
		Porcentaje de tolerancia para márgenes
	  */
	public void setMarginTolerance (BigDecimal MarginTolerance)
	{
		set_Value (COLUMNNAME_MarginTolerance, MarginTolerance);
	}

	/** Get MarginTolerance.
		@return Porcentaje de tolerancia para márgenes
	  */
	public BigDecimal getMarginTolerance () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MarginTolerance);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public I_Z_MargenProv getZ_MargenProv() throws RuntimeException
    {
		return (I_Z_MargenProv)MTable.get(getCtx(), I_Z_MargenProv.Table_Name)
			.getPO(getZ_MargenProv_ID(), get_TrxName());	}

	/** Set Z_MargenProv ID.
		@param Z_MargenProv_ID Z_MargenProv ID	  */
	public void setZ_MargenProv_ID (int Z_MargenProv_ID)
	{
		if (Z_MargenProv_ID < 1) 
			set_Value (COLUMNNAME_Z_MargenProv_ID, null);
		else 
			set_Value (COLUMNNAME_Z_MargenProv_ID, Integer.valueOf(Z_MargenProv_ID));
	}

	/** Get Z_MargenProv ID.
		@return Z_MargenProv ID	  */
	public int getZ_MargenProv_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MargenProv_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_MargenProvLinea ID.
		@param Z_MargenProvLinea_ID Z_MargenProvLinea ID	  */
	public void setZ_MargenProvLinea_ID (int Z_MargenProvLinea_ID)
	{
		if (Z_MargenProvLinea_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_MargenProvLinea_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_MargenProvLinea_ID, Integer.valueOf(Z_MargenProvLinea_ID));
	}

	/** Get Z_MargenProvLinea ID.
		@return Z_MargenProvLinea ID	  */
	public int getZ_MargenProvLinea_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MargenProvLinea_ID);
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
}