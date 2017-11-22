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

/** Generated Model for Z_MargenProvLineaSet
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_MargenProvLineaSet extends PO implements I_Z_MargenProvLineaSet, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20171122L;

    /** Standard Constructor */
    public X_Z_MargenProvLineaSet (Properties ctx, int Z_MargenProvLineaSet_ID, String trxName)
    {
      super (ctx, Z_MargenProvLineaSet_ID, trxName);
      /** if (Z_MargenProvLineaSet_ID == 0)
        {
			setName (null);
			setZ_MargenProvLinea_ID (0);
			setZ_MargenProvLineaSet_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_MargenProvLineaSet (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_MargenProvLineaSet[")
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

	/** Set ProcessButton2.
		@param ProcessButton2 
		Botón de Proceso
	  */
	public void setProcessButton2 (String ProcessButton2)
	{
		set_Value (COLUMNNAME_ProcessButton2, ProcessButton2);
	}

	/** Get ProcessButton2.
		@return Botón de Proceso
	  */
	public String getProcessButton2 () 
	{
		return (String)get_Value(COLUMNNAME_ProcessButton2);
	}

	public org.xpande.retail.model.I_Z_MargenProvLinea getZ_MargenProvLinea() throws RuntimeException
    {
		return (org.xpande.retail.model.I_Z_MargenProvLinea)MTable.get(getCtx(), org.xpande.retail.model.I_Z_MargenProvLinea.Table_Name)
			.getPO(getZ_MargenProvLinea_ID(), get_TrxName());	}

	/** Set Z_MargenProvLinea ID.
		@param Z_MargenProvLinea_ID Z_MargenProvLinea ID	  */
	public void setZ_MargenProvLinea_ID (int Z_MargenProvLinea_ID)
	{
		if (Z_MargenProvLinea_ID < 1) 
			set_Value (COLUMNNAME_Z_MargenProvLinea_ID, null);
		else 
			set_Value (COLUMNNAME_Z_MargenProvLinea_ID, Integer.valueOf(Z_MargenProvLinea_ID));
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

	/** Set Z_MargenProvLineaSet ID.
		@param Z_MargenProvLineaSet_ID Z_MargenProvLineaSet ID	  */
	public void setZ_MargenProvLineaSet_ID (int Z_MargenProvLineaSet_ID)
	{
		if (Z_MargenProvLineaSet_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_MargenProvLineaSet_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_MargenProvLineaSet_ID, Integer.valueOf(Z_MargenProvLineaSet_ID));
	}

	/** Get Z_MargenProvLineaSet ID.
		@return Z_MargenProvLineaSet ID	  */
	public int getZ_MargenProvLineaSet_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MargenProvLineaSet_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_PautaComercialSet getZ_PautaComercialSet() throws RuntimeException
    {
		return (I_Z_PautaComercialSet)MTable.get(getCtx(), I_Z_PautaComercialSet.Table_Name)
			.getPO(getZ_PautaComercialSet_ID(), get_TrxName());	}

	/** Set Z_PautaComercialSet ID.
		@param Z_PautaComercialSet_ID Z_PautaComercialSet ID	  */
	public void setZ_PautaComercialSet_ID (int Z_PautaComercialSet_ID)
	{
		if (Z_PautaComercialSet_ID < 1) 
			set_Value (COLUMNNAME_Z_PautaComercialSet_ID, null);
		else 
			set_Value (COLUMNNAME_Z_PautaComercialSet_ID, Integer.valueOf(Z_PautaComercialSet_ID));
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