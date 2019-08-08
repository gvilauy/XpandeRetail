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

/** Generated Model for Z_RetailConfig
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_RetailConfig extends PO implements I_Z_RetailConfig, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190808L;

    /** Standard Constructor */
    public X_Z_RetailConfig (Properties ctx, int Z_RetailConfig_ID, String trxName)
    {
      super (ctx, Z_RetailConfig_ID, trxName);
      /** if (Z_RetailConfig_ID == 0)
        {
			setCompletaRecepcion (false);
// N
			setValue (null);
			setZ_RetailConfig_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_RetailConfig (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_RetailConfig[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set CompletaRecepcion.
		@param CompletaRecepcion 
		Si completa o no recepcion de mercadería de manera automatica
	  */
	public void setCompletaRecepcion (boolean CompletaRecepcion)
	{
		set_Value (COLUMNNAME_CompletaRecepcion, Boolean.valueOf(CompletaRecepcion));
	}

	/** Get CompletaRecepcion.
		@return Si completa o no recepcion de mercadería de manera automatica
	  */
	public boolean isCompletaRecepcion () 
	{
		Object oo = get_Value(COLUMNNAME_CompletaRecepcion);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_C_Tax getC_Tax() throws RuntimeException
    {
		return (I_C_Tax)MTable.get(getCtx(), I_C_Tax.Table_Name)
			.getPO(getC_Tax_ID(), get_TrxName());	}

	/** Set Tax.
		@param C_Tax_ID 
		Tax identifier
	  */
	public void setC_Tax_ID (int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_Value (COLUMNNAME_C_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
	}

	/** Get Tax.
		@return Tax identifier
	  */
	public int getC_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DefDocRemDifCant_ID.
		@param DefDocRemDifCant_ID 
		ID por defecto para documento de remito de diferencia de cantidad en módulo Retail
	  */
	public void setDefDocRemDifCant_ID (int DefDocRemDifCant_ID)
	{
		if (DefDocRemDifCant_ID < 1) 
			set_Value (COLUMNNAME_DefDocRemDifCant_ID, null);
		else 
			set_Value (COLUMNNAME_DefDocRemDifCant_ID, Integer.valueOf(DefDocRemDifCant_ID));
	}

	/** Get DefDocRemDifCant_ID.
		@return ID por defecto para documento de remito de diferencia de cantidad en módulo Retail
	  */
	public int getDefDocRemDifCant_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DefDocRemDifCant_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ToleraRemDifLin.
		@param ToleraRemDifLin 
		Porcentaje de tolerancia para lineas en la generación de Remitos por Diferencia
	  */
	public void setToleraRemDifLin (BigDecimal ToleraRemDifLin)
	{
		set_Value (COLUMNNAME_ToleraRemDifLin, ToleraRemDifLin);
	}

	/** Get ToleraRemDifLin.
		@return Porcentaje de tolerancia para lineas en la generación de Remitos por Diferencia
	  */
	public BigDecimal getToleraRemDifLin () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ToleraRemDifLin);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ToleraRemDifTot.
		@param ToleraRemDifTot 
		Porcentaje de tolerancia para Total en la generación de Remitos por Diferencia
	  */
	public void setToleraRemDifTot (BigDecimal ToleraRemDifTot)
	{
		set_Value (COLUMNNAME_ToleraRemDifTot, ToleraRemDifTot);
	}

	/** Get ToleraRemDifTot.
		@return Porcentaje de tolerancia para Total en la generación de Remitos por Diferencia
	  */
	public BigDecimal getToleraRemDifTot () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ToleraRemDifTot);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

	/** Set Z_RetailConfig ID.
		@param Z_RetailConfig_ID Z_RetailConfig ID	  */
	public void setZ_RetailConfig_ID (int Z_RetailConfig_ID)
	{
		if (Z_RetailConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_RetailConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_RetailConfig_ID, Integer.valueOf(Z_RetailConfig_ID));
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