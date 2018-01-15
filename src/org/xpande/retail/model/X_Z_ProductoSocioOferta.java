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

/** Generated Model for Z_ProductoSocioOferta
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_ProductoSocioOferta extends PO implements I_Z_ProductoSocioOferta, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180115L;

    /** Standard Constructor */
    public X_Z_ProductoSocioOferta (Properties ctx, int Z_ProductoSocioOferta_ID, String trxName)
    {
      super (ctx, Z_ProductoSocioOferta_ID, trxName);
      /** if (Z_ProductoSocioOferta_ID == 0)
        {
			setEndDate (new Timestamp( System.currentTimeMillis() ));
			setStartDate (new Timestamp( System.currentTimeMillis() ));
			setZ_OfertaVenta_ID (0);
			setZ_OfertaVentaLinBP_ID (0);
			setZ_OfertaVentaLin_ID (0);
			setZ_ProductoSocio_ID (0);
			setZ_ProductoSocioOferta_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_ProductoSocioOferta (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_ProductoSocioOferta[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set End Date.
		@param EndDate 
		Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Timestamp getEndDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndDate);
	}

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
	}

	public I_Z_OfertaVenta getZ_OfertaVenta() throws RuntimeException
    {
		return (I_Z_OfertaVenta)MTable.get(getCtx(), I_Z_OfertaVenta.Table_Name)
			.getPO(getZ_OfertaVenta_ID(), get_TrxName());	}

	/** Set Z_OfertaVenta ID.
		@param Z_OfertaVenta_ID Z_OfertaVenta ID	  */
	public void setZ_OfertaVenta_ID (int Z_OfertaVenta_ID)
	{
		if (Z_OfertaVenta_ID < 1) 
			set_Value (COLUMNNAME_Z_OfertaVenta_ID, null);
		else 
			set_Value (COLUMNNAME_Z_OfertaVenta_ID, Integer.valueOf(Z_OfertaVenta_ID));
	}

	/** Get Z_OfertaVenta ID.
		@return Z_OfertaVenta ID	  */
	public int getZ_OfertaVenta_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_OfertaVenta_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_OfertaVentaLinBP getZ_OfertaVentaLinBP() throws RuntimeException
    {
		return (I_Z_OfertaVentaLinBP)MTable.get(getCtx(), I_Z_OfertaVentaLinBP.Table_Name)
			.getPO(getZ_OfertaVentaLinBP_ID(), get_TrxName());	}

	/** Set Z_OfertaVentaLinBP ID.
		@param Z_OfertaVentaLinBP_ID Z_OfertaVentaLinBP ID	  */
	public void setZ_OfertaVentaLinBP_ID (int Z_OfertaVentaLinBP_ID)
	{
		if (Z_OfertaVentaLinBP_ID < 1) 
			set_Value (COLUMNNAME_Z_OfertaVentaLinBP_ID, null);
		else 
			set_Value (COLUMNNAME_Z_OfertaVentaLinBP_ID, Integer.valueOf(Z_OfertaVentaLinBP_ID));
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

	public I_Z_ProductoSocio getZ_ProductoSocio() throws RuntimeException
    {
		return (I_Z_ProductoSocio)MTable.get(getCtx(), I_Z_ProductoSocio.Table_Name)
			.getPO(getZ_ProductoSocio_ID(), get_TrxName());	}

	/** Set Z_ProductoSocio ID.
		@param Z_ProductoSocio_ID Z_ProductoSocio ID	  */
	public void setZ_ProductoSocio_ID (int Z_ProductoSocio_ID)
	{
		if (Z_ProductoSocio_ID < 1) 
			set_Value (COLUMNNAME_Z_ProductoSocio_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ProductoSocio_ID, Integer.valueOf(Z_ProductoSocio_ID));
	}

	/** Get Z_ProductoSocio ID.
		@return Z_ProductoSocio ID	  */
	public int getZ_ProductoSocio_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ProductoSocio_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_ProductoSocioOferta ID.
		@param Z_ProductoSocioOferta_ID Z_ProductoSocioOferta ID	  */
	public void setZ_ProductoSocioOferta_ID (int Z_ProductoSocioOferta_ID)
	{
		if (Z_ProductoSocioOferta_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_ProductoSocioOferta_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_ProductoSocioOferta_ID, Integer.valueOf(Z_ProductoSocioOferta_ID));
	}

	/** Get Z_ProductoSocioOferta ID.
		@return Z_ProductoSocioOferta ID	  */
	public int getZ_ProductoSocioOferta_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ProductoSocioOferta_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}