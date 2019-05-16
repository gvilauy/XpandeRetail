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

/** Generated Model for Z_GeneraAstoVtaSumMP
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_GeneraAstoVtaSumMP extends PO implements I_Z_GeneraAstoVtaSumMP, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190514L;

    /** Standard Constructor */
    public X_Z_GeneraAstoVtaSumMP (Properties ctx, int Z_GeneraAstoVtaSumMP_ID, String trxName)
    {
      super (ctx, Z_GeneraAstoVtaSumMP_ID, trxName);
      /** if (Z_GeneraAstoVtaSumMP_ID == 0)
        {
			setAmtTotal (Env.ZERO);
			setC_Currency_ID (0);
			setCodMedioPagoPOS (null);
			setNomMedioPagoPOS (null);
			setZ_GeneraAstoVta_ID (0);
			setZ_GeneraAstoVtaSumMP_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_GeneraAstoVtaSumMP (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_GeneraAstoVtaSumMP[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AmtTotal.
		@param AmtTotal 
		Monto total
	  */
	public void setAmtTotal (BigDecimal AmtTotal)
	{
		set_Value (COLUMNNAME_AmtTotal, AmtTotal);
	}

	/** Get AmtTotal.
		@return Monto total
	  */
	public BigDecimal getAmtTotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtTotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtTotal1.
		@param AmtTotal1 
		Monto total uno
	  */
	public void setAmtTotal1 (BigDecimal AmtTotal1)
	{
		set_Value (COLUMNNAME_AmtTotal1, AmtTotal1);
	}

	/** Get AmtTotal1.
		@return Monto total uno
	  */
	public BigDecimal getAmtTotal1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtTotal1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtTotal2.
		@param AmtTotal2 
		Monto total dos
	  */
	public void setAmtTotal2 (BigDecimal AmtTotal2)
	{
		set_Value (COLUMNNAME_AmtTotal2, AmtTotal2);
	}

	/** Get AmtTotal2.
		@return Monto total dos
	  */
	public BigDecimal getAmtTotal2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtTotal2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set C_Currency_2_ID.
		@param C_Currency_2_ID 
		Moneda secundaria para procesos
	  */
	public void setC_Currency_2_ID (int C_Currency_2_ID)
	{
		if (C_Currency_2_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_2_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_2_ID, Integer.valueOf(C_Currency_2_ID));
	}

	/** Get C_Currency_2_ID.
		@return Moneda secundaria para procesos
	  */
	public int getC_Currency_2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Currency getC_Currency() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ChangeAmt.
		@param ChangeAmt ChangeAmt	  */
	public void setChangeAmt (BigDecimal ChangeAmt)
	{
		set_Value (COLUMNNAME_ChangeAmt, ChangeAmt);
	}

	/** Get ChangeAmt.
		@return ChangeAmt	  */
	public BigDecimal getChangeAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ChangeAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set CodMedioPagoPOS.
		@param CodMedioPagoPOS 
		Código de Medio de Pago del POS en Retail
	  */
	public void setCodMedioPagoPOS (String CodMedioPagoPOS)
	{
		set_Value (COLUMNNAME_CodMedioPagoPOS, CodMedioPagoPOS);
	}

	/** Get CodMedioPagoPOS.
		@return Código de Medio de Pago del POS en Retail
	  */
	public String getCodMedioPagoPOS () 
	{
		return (String)get_Value(COLUMNNAME_CodMedioPagoPOS);
	}

	/** Set CodTipoLineaPOS.
		@param CodTipoLineaPOS 
		Código de tipo de linea para POS en Retail
	  */
	public void setCodTipoLineaPOS (String CodTipoLineaPOS)
	{
		set_Value (COLUMNNAME_CodTipoLineaPOS, CodTipoLineaPOS);
	}

	/** Get CodTipoLineaPOS.
		@return Código de tipo de linea para POS en Retail
	  */
	public String getCodTipoLineaPOS () 
	{
		return (String)get_Value(COLUMNNAME_CodTipoLineaPOS);
	}

	/** Set Rate.
		@param CurrencyRate 
		Currency Conversion Rate
	  */
	public void setCurrencyRate (BigDecimal CurrencyRate)
	{
		set_Value (COLUMNNAME_CurrencyRate, CurrencyRate);
	}

	/** Get Rate.
		@return Currency Conversion Rate
	  */
	public BigDecimal getCurrencyRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrencyRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set NomMedioPagoPOS.
		@param NomMedioPagoPOS 
		Nombre de Medio de Pago del POS en Retail
	  */
	public void setNomMedioPagoPOS (String NomMedioPagoPOS)
	{
		set_Value (COLUMNNAME_NomMedioPagoPOS, NomMedioPagoPOS);
	}

	/** Get NomMedioPagoPOS.
		@return Nombre de Medio de Pago del POS en Retail
	  */
	public String getNomMedioPagoPOS () 
	{
		return (String)get_Value(COLUMNNAME_NomMedioPagoPOS);
	}

	/** Set NomTipoLineaPOS.
		@param NomTipoLineaPOS 
		Nombre de tipo de linea POS para Retail
	  */
	public void setNomTipoLineaPOS (String NomTipoLineaPOS)
	{
		set_Value (COLUMNNAME_NomTipoLineaPOS, NomTipoLineaPOS);
	}

	/** Get NomTipoLineaPOS.
		@return Nombre de tipo de linea POS para Retail
	  */
	public String getNomTipoLineaPOS () 
	{
		return (String)get_Value(COLUMNNAME_NomTipoLineaPOS);
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

	public I_Z_GeneraAstoVta getZ_GeneraAstoVta() throws RuntimeException
    {
		return (I_Z_GeneraAstoVta)MTable.get(getCtx(), I_Z_GeneraAstoVta.Table_Name)
			.getPO(getZ_GeneraAstoVta_ID(), get_TrxName());	}

	/** Set Z_GeneraAstoVta ID.
		@param Z_GeneraAstoVta_ID Z_GeneraAstoVta ID	  */
	public void setZ_GeneraAstoVta_ID (int Z_GeneraAstoVta_ID)
	{
		if (Z_GeneraAstoVta_ID < 1) 
			set_Value (COLUMNNAME_Z_GeneraAstoVta_ID, null);
		else 
			set_Value (COLUMNNAME_Z_GeneraAstoVta_ID, Integer.valueOf(Z_GeneraAstoVta_ID));
	}

	/** Get Z_GeneraAstoVta ID.
		@return Z_GeneraAstoVta ID	  */
	public int getZ_GeneraAstoVta_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_GeneraAstoVta_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_GeneraAstoVtaSumMP ID.
		@param Z_GeneraAstoVtaSumMP_ID Z_GeneraAstoVtaSumMP ID	  */
	public void setZ_GeneraAstoVtaSumMP_ID (int Z_GeneraAstoVtaSumMP_ID)
	{
		if (Z_GeneraAstoVtaSumMP_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_GeneraAstoVtaSumMP_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_GeneraAstoVtaSumMP_ID, Integer.valueOf(Z_GeneraAstoVtaSumMP_ID));
	}

	/** Get Z_GeneraAstoVtaSumMP ID.
		@return Z_GeneraAstoVtaSumMP ID	  */
	public int getZ_GeneraAstoVtaSumMP_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_GeneraAstoVtaSumMP_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}