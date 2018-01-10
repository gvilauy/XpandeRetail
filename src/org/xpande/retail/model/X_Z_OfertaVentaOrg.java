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

/** Generated Model for Z_OfertaVentaOrg
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_OfertaVentaOrg extends PO implements I_Z_OfertaVentaOrg, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180110L;

    /** Standard Constructor */
    public X_Z_OfertaVentaOrg (Properties ctx, int Z_OfertaVentaOrg_ID, String trxName)
    {
      super (ctx, Z_OfertaVentaOrg_ID, trxName);
      /** if (Z_OfertaVentaOrg_ID == 0)
        {
			setAD_OrgTrx_ID (0);
			setIsSelected (true);
// Y
			setZ_OfertaVenta_ID (0);
			setZ_OfertaVentaOrg_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_OfertaVentaOrg (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_OfertaVentaOrg[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Trx Organization.
		@param AD_OrgTrx_ID 
		Performing or initiating organization
	  */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Trx Organization.
		@return Performing or initiating organization
	  */
	public int getAD_OrgTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Selected.
		@param IsSelected Selected	  */
	public void setIsSelected (boolean IsSelected)
	{
		set_Value (COLUMNNAME_IsSelected, Boolean.valueOf(IsSelected));
	}

	/** Get Selected.
		@return Selected	  */
	public boolean isSelected () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.xpande.retail.model.I_Z_OfertaVenta getZ_OfertaVenta() throws RuntimeException
    {
		return (org.xpande.retail.model.I_Z_OfertaVenta)MTable.get(getCtx(), org.xpande.retail.model.I_Z_OfertaVenta.Table_Name)
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

	/** Set Z_OfertaVentaOrg ID.
		@param Z_OfertaVentaOrg_ID Z_OfertaVentaOrg ID	  */
	public void setZ_OfertaVentaOrg_ID (int Z_OfertaVentaOrg_ID)
	{
		if (Z_OfertaVentaOrg_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_OfertaVentaOrg_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_OfertaVentaOrg_ID, Integer.valueOf(Z_OfertaVentaOrg_ID));
	}

	/** Get Z_OfertaVentaOrg ID.
		@return Z_OfertaVentaOrg ID	  */
	public int getZ_OfertaVentaOrg_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_OfertaVentaOrg_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}