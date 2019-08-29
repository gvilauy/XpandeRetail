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

/** Generated Model for Z_FormEfectivoLin
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_FormEfectivoLin extends PO implements I_Z_FormEfectivoLin, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20190829L;

    /** Standard Constructor */
    public X_Z_FormEfectivoLin (Properties ctx, int Z_FormEfectivoLin_ID, String trxName)
    {
      super (ctx, Z_FormEfectivoLin_ID, trxName);
      /** if (Z_FormEfectivoLin_ID == 0)
        {
			setC_Currency_2_ID (0);
			setC_Currency_ID (0);
			setName (null);
			setTipoConceptoForEfe (null);
			setZ_FormEfectivo_ID (0);
			setZ_FormEfectivoLin_ID (0);
			setZ_RetailConfigForEfe_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_FormEfectivoLin (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_FormEfectivoLin[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AmtSubtotal1.
		@param AmtSubtotal1 
		Monto subtotal uno
	  */
	public void setAmtSubtotal1 (BigDecimal AmtSubtotal1)
	{
		set_Value (COLUMNNAME_AmtSubtotal1, AmtSubtotal1);
	}

	/** Get AmtSubtotal1.
		@return Monto subtotal uno
	  */
	public BigDecimal getAmtSubtotal1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtSubtotal1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set AmtSubtotal2.
		@param AmtSubtotal2 
		Monto subtotal dos
	  */
	public void setAmtSubtotal2 (BigDecimal AmtSubtotal2)
	{
		set_Value (COLUMNNAME_AmtSubtotal2, AmtSubtotal2);
	}

	/** Get AmtSubtotal2.
		@return Monto subtotal dos
	  */
	public BigDecimal getAmtSubtotal2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtSubtotal2);
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

	/** TipoConceptoForEfe AD_Reference_ID=1000058 */
	public static final int TIPOCONCEPTOFOREFE_AD_Reference_ID=1000058;
	/** SALDO INICIAL = SALDO_INICIAL */
	public static final String TIPOCONCEPTOFOREFE_SALDOINICIAL = "SALDO_INICIAL";
	/** ENTRADAS DE EFECTIVO = ENTRADA_EFECTIVO */
	public static final String TIPOCONCEPTOFOREFE_ENTRADASDEEFECTIVO = "ENTRADA_EFECTIVO";
	/** SALIDAS DE EFECTIVO = SALIDA_EFECTIVO */
	public static final String TIPOCONCEPTOFOREFE_SALIDASDEEFECTIVO = "SALIDA_EFECTIVO";
	/** RESULTADO DEL ARQUEO = RESULTADO_ARQUEO */
	public static final String TIPOCONCEPTOFOREFE_RESULTADODELARQUEO = "RESULTADO_ARQUEO";
	/** Set TipoConceptoForEfe.
		@param TipoConceptoForEfe 
		Tipo de concepto de formulario de movimientos de efectivo en Retail
	  */
	public void setTipoConceptoForEfe (String TipoConceptoForEfe)
	{

		set_Value (COLUMNNAME_TipoConceptoForEfe, TipoConceptoForEfe);
	}

	/** Get TipoConceptoForEfe.
		@return Tipo de concepto de formulario de movimientos de efectivo en Retail
	  */
	public String getTipoConceptoForEfe () 
	{
		return (String)get_Value(COLUMNNAME_TipoConceptoForEfe);
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

	public I_Z_FormEfectivo getZ_FormEfectivo() throws RuntimeException
    {
		return (I_Z_FormEfectivo)MTable.get(getCtx(), I_Z_FormEfectivo.Table_Name)
			.getPO(getZ_FormEfectivo_ID(), get_TrxName());	}

	/** Set Z_FormEfectivo ID.
		@param Z_FormEfectivo_ID Z_FormEfectivo ID	  */
	public void setZ_FormEfectivo_ID (int Z_FormEfectivo_ID)
	{
		if (Z_FormEfectivo_ID < 1) 
			set_Value (COLUMNNAME_Z_FormEfectivo_ID, null);
		else 
			set_Value (COLUMNNAME_Z_FormEfectivo_ID, Integer.valueOf(Z_FormEfectivo_ID));
	}

	/** Get Z_FormEfectivo ID.
		@return Z_FormEfectivo ID	  */
	public int getZ_FormEfectivo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_FormEfectivo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_FormEfectivoLin ID.
		@param Z_FormEfectivoLin_ID Z_FormEfectivoLin ID	  */
	public void setZ_FormEfectivoLin_ID (int Z_FormEfectivoLin_ID)
	{
		if (Z_FormEfectivoLin_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_FormEfectivoLin_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_FormEfectivoLin_ID, Integer.valueOf(Z_FormEfectivoLin_ID));
	}

	/** Get Z_FormEfectivoLin ID.
		@return Z_FormEfectivoLin ID	  */
	public int getZ_FormEfectivoLin_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_FormEfectivoLin_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.xpande.retail.model.I_Z_RetailConfigForEfe getZ_RetailConfigForEfe() throws RuntimeException
    {
		return (org.xpande.retail.model.I_Z_RetailConfigForEfe)MTable.get(getCtx(), org.xpande.retail.model.I_Z_RetailConfigForEfe.Table_Name)
			.getPO(getZ_RetailConfigForEfe_ID(), get_TrxName());	}

	/** Set Z_RetailConfigForEfe ID.
		@param Z_RetailConfigForEfe_ID Z_RetailConfigForEfe ID	  */
	public void setZ_RetailConfigForEfe_ID (int Z_RetailConfigForEfe_ID)
	{
		if (Z_RetailConfigForEfe_ID < 1) 
			set_Value (COLUMNNAME_Z_RetailConfigForEfe_ID, null);
		else 
			set_Value (COLUMNNAME_Z_RetailConfigForEfe_ID, Integer.valueOf(Z_RetailConfigForEfe_ID));
	}

	/** Get Z_RetailConfigForEfe ID.
		@return Z_RetailConfigForEfe ID	  */
	public int getZ_RetailConfigForEfe_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_RetailConfigForEfe_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}