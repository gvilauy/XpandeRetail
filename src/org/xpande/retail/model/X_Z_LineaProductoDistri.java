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

/** Generated Model for Z_LineaProductoDistri
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_LineaProductoDistri extends PO implements I_Z_LineaProductoDistri, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170621L;

    /** Standard Constructor */
    public X_Z_LineaProductoDistri (Properties ctx, int Z_LineaProductoDistri_ID, String trxName)
    {
      super (ctx, Z_LineaProductoDistri_ID, trxName);
      /** if (Z_LineaProductoDistri_ID == 0)
        {
			setC_BPartner_ID (0);
			setZ_LineaProductoDistri_ID (0);
			setZ_LineaProductoSocio_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_LineaProductoDistri (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_LineaProductoDistri[")
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

	/** Set Z_LineaProductoDistri ID.
		@param Z_LineaProductoDistri_ID Z_LineaProductoDistri ID	  */
	public void setZ_LineaProductoDistri_ID (int Z_LineaProductoDistri_ID)
	{
		if (Z_LineaProductoDistri_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_LineaProductoDistri_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_LineaProductoDistri_ID, Integer.valueOf(Z_LineaProductoDistri_ID));
	}

	/** Get Z_LineaProductoDistri ID.
		@return Z_LineaProductoDistri ID	  */
	public int getZ_LineaProductoDistri_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_LineaProductoDistri_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
}