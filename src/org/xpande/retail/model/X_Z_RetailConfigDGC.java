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

/** Generated Model for Z_RetailConfigDGC
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_RetailConfigDGC extends PO implements I_Z_RetailConfigDGC, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20171106L;

    /** Standard Constructor */
    public X_Z_RetailConfigDGC (Properties ctx, int Z_RetailConfigDGC_ID, String trxName)
    {
      super (ctx, Z_RetailConfigDGC_ID, trxName);
      /** if (Z_RetailConfigDGC_ID == 0)
        {
			setAD_OrgTo_ID (0);
			setDGC_Cod_Establecimiento (null);
			setDGC_ID_Establecimiento (null);
			setDGC_MetodoPrecio (null);
			setDGC_MetodoProducto (null);
			setNamespace (null);
			setWsdlURL (null);
			setZ_RetailConfigDGC_ID (0);
			setZ_RetailConfig_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_RetailConfigDGC (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_RetailConfigDGC[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Inter-Organization.
		@param AD_OrgTo_ID 
		Organization valid for intercompany documents
	  */
	public void setAD_OrgTo_ID (int AD_OrgTo_ID)
	{
		if (AD_OrgTo_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTo_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTo_ID, Integer.valueOf(AD_OrgTo_ID));
	}

	/** Get Inter-Organization.
		@return Organization valid for intercompany documents
	  */
	public int getAD_OrgTo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DGC_Cod_Establecimiento.
		@param DGC_Cod_Establecimiento 
		Código de Establecimiento para DGC en módulo de Retail
	  */
	public void setDGC_Cod_Establecimiento (String DGC_Cod_Establecimiento)
	{
		set_Value (COLUMNNAME_DGC_Cod_Establecimiento, DGC_Cod_Establecimiento);
	}

	/** Get DGC_Cod_Establecimiento.
		@return Código de Establecimiento para DGC en módulo de Retail
	  */
	public String getDGC_Cod_Establecimiento () 
	{
		return (String)get_Value(COLUMNNAME_DGC_Cod_Establecimiento);
	}

	/** Set DGC_ID_Establecimiento.
		@param DGC_ID_Establecimiento 
		Identificador de establecimiento para DBC en módulo de Retail
	  */
	public void setDGC_ID_Establecimiento (String DGC_ID_Establecimiento)
	{
		set_Value (COLUMNNAME_DGC_ID_Establecimiento, DGC_ID_Establecimiento);
	}

	/** Get DGC_ID_Establecimiento.
		@return Identificador de establecimiento para DBC en módulo de Retail
	  */
	public String getDGC_ID_Establecimiento () 
	{
		return (String)get_Value(COLUMNNAME_DGC_ID_Establecimiento);
	}

	/** Set DGC_MetodoPrecio.
		@param DGC_MetodoPrecio 
		Metodo para interface de precios de DGC en modulo de Retail
	  */
	public void setDGC_MetodoPrecio (String DGC_MetodoPrecio)
	{
		set_Value (COLUMNNAME_DGC_MetodoPrecio, DGC_MetodoPrecio);
	}

	/** Get DGC_MetodoPrecio.
		@return Metodo para interface de precios de DGC en modulo de Retail
	  */
	public String getDGC_MetodoPrecio () 
	{
		return (String)get_Value(COLUMNNAME_DGC_MetodoPrecio);
	}

	/** Set DGC_MetodoProducto.
		@param DGC_MetodoProducto 
		Metodo para interface de productos de DGC en modulo de Retail
	  */
	public void setDGC_MetodoProducto (String DGC_MetodoProducto)
	{
		set_Value (COLUMNNAME_DGC_MetodoProducto, DGC_MetodoProducto);
	}

	/** Get DGC_MetodoProducto.
		@return Metodo para interface de productos de DGC en modulo de Retail
	  */
	public String getDGC_MetodoProducto () 
	{
		return (String)get_Value(COLUMNNAME_DGC_MetodoProducto);
	}

	/** Set Namespace.
		@param Namespace 
		Namespace en parametrizaciones de Web Services
	  */
	public void setNamespace (String Namespace)
	{
		set_Value (COLUMNNAME_Namespace, Namespace);
	}

	/** Get Namespace.
		@return Namespace en parametrizaciones de Web Services
	  */
	public String getNamespace () 
	{
		return (String)get_Value(COLUMNNAME_Namespace);
	}

	/** Set WsdlURL.
		@param WsdlURL 
		URL para Wsdl en parametrizaciones de Web Services
	  */
	public void setWsdlURL (String WsdlURL)
	{
		set_Value (COLUMNNAME_WsdlURL, WsdlURL);
	}

	/** Get WsdlURL.
		@return URL para Wsdl en parametrizaciones de Web Services
	  */
	public String getWsdlURL () 
	{
		return (String)get_Value(COLUMNNAME_WsdlURL);
	}

	/** Set Z_RetailConfigDGC ID.
		@param Z_RetailConfigDGC_ID Z_RetailConfigDGC ID	  */
	public void setZ_RetailConfigDGC_ID (int Z_RetailConfigDGC_ID)
	{
		if (Z_RetailConfigDGC_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_RetailConfigDGC_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_RetailConfigDGC_ID, Integer.valueOf(Z_RetailConfigDGC_ID));
	}

	/** Get Z_RetailConfigDGC ID.
		@return Z_RetailConfigDGC ID	  */
	public int getZ_RetailConfigDGC_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_RetailConfigDGC_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.xpande.retail.model.I_Z_RetailConfig getZ_RetailConfig() throws RuntimeException
    {
		return (org.xpande.retail.model.I_Z_RetailConfig)MTable.get(getCtx(), org.xpande.retail.model.I_Z_RetailConfig.Table_Name)
			.getPO(getZ_RetailConfig_ID(), get_TrxName());	}

	/** Set Z_RetailConfig ID.
		@param Z_RetailConfig_ID Z_RetailConfig ID	  */
	public void setZ_RetailConfig_ID (int Z_RetailConfig_ID)
	{
		if (Z_RetailConfig_ID < 1) 
			set_Value (COLUMNNAME_Z_RetailConfig_ID, null);
		else 
			set_Value (COLUMNNAME_Z_RetailConfig_ID, Integer.valueOf(Z_RetailConfig_ID));
	}

	/** Get Z_RetailConfig ID.
		@return Z_RetailConfig ID	  */
	public int getZ_RetailConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_RetailConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}