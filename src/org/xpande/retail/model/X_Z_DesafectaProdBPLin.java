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

/** Generated Model for Z_DesafectaProdBPLin
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_DesafectaProdBPLin extends PO implements I_Z_DesafectaProdBPLin, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200701L;

    /** Standard Constructor */
    public X_Z_DesafectaProdBPLin (Properties ctx, int Z_DesafectaProdBPLin_ID, String trxName)
    {
      super (ctx, Z_DesafectaProdBPLin_ID, trxName);
      /** if (Z_DesafectaProdBPLin_ID == 0)
        {
			setM_Product_ID (0);
			setZ_DesafectaProdBP_ID (0);
			setZ_DesafectaProdBPLin_ID (0);
			setZ_ProductoSocio_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_DesafectaProdBPLin (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_DesafectaProdBPLin[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set LineaProdSocio_ID_To.
		@param LineaProdSocio_ID_To 
		Linea de productos destino de socios de negocio en Retail 
	  */
	public void setLineaProdSocio_ID_To (int LineaProdSocio_ID_To)
	{
		set_Value (COLUMNNAME_LineaProdSocio_ID_To, Integer.valueOf(LineaProdSocio_ID_To));
	}

	/** Get LineaProdSocio_ID_To.
		@return Linea de productos destino de socios de negocio en Retail 
	  */
	public int getLineaProdSocio_ID_To () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LineaProdSocio_ID_To);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_Product getM_Product() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UPC/EAN.
		@param UPC 
		Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public void setUPC (String UPC)
	{
		set_Value (COLUMNNAME_UPC, UPC);
	}

	/** Get UPC/EAN.
		@return Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public String getUPC () 
	{
		return (String)get_Value(COLUMNNAME_UPC);
	}

	/** Set Immutable Universally Unique Identifier.
		@param UUID 
		Immutable Universally Unique Identifier
	  */
	public void setUUID (String UUID)
	{
		set_Value (COLUMNNAME_UUID, UUID);
	}

	/** Get Immutable Universally Unique Identifier.
		@return Immutable Universally Unique Identifier
	  */
	public String getUUID () 
	{
		return (String)get_Value(COLUMNNAME_UUID);
	}

	/** Set Partner Product Key.
		@param VendorProductNo 
		Product Key of the Business Partner
	  */
	public void setVendorProductNo (String VendorProductNo)
	{
		set_Value (COLUMNNAME_VendorProductNo, VendorProductNo);
	}

	/** Get Partner Product Key.
		@return Product Key of the Business Partner
	  */
	public String getVendorProductNo () 
	{
		return (String)get_Value(COLUMNNAME_VendorProductNo);
	}

	public I_Z_DesafectaProdBP getZ_DesafectaProdBP() throws RuntimeException
    {
		return (I_Z_DesafectaProdBP)MTable.get(getCtx(), I_Z_DesafectaProdBP.Table_Name)
			.getPO(getZ_DesafectaProdBP_ID(), get_TrxName());	}

	/** Set Z_DesafectaProdBP ID.
		@param Z_DesafectaProdBP_ID Z_DesafectaProdBP ID	  */
	public void setZ_DesafectaProdBP_ID (int Z_DesafectaProdBP_ID)
	{
		if (Z_DesafectaProdBP_ID < 1) 
			set_Value (COLUMNNAME_Z_DesafectaProdBP_ID, null);
		else 
			set_Value (COLUMNNAME_Z_DesafectaProdBP_ID, Integer.valueOf(Z_DesafectaProdBP_ID));
	}

	/** Get Z_DesafectaProdBP ID.
		@return Z_DesafectaProdBP ID	  */
	public int getZ_DesafectaProdBP_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_DesafectaProdBP_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_DesafectaProdBPLin ID.
		@param Z_DesafectaProdBPLin_ID Z_DesafectaProdBPLin ID	  */
	public void setZ_DesafectaProdBPLin_ID (int Z_DesafectaProdBPLin_ID)
	{
		if (Z_DesafectaProdBPLin_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_DesafectaProdBPLin_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_DesafectaProdBPLin_ID, Integer.valueOf(Z_DesafectaProdBPLin_ID));
	}

	/** Get Z_DesafectaProdBPLin ID.
		@return Z_DesafectaProdBPLin ID	  */
	public int getZ_DesafectaProdBPLin_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_DesafectaProdBPLin_ID);
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