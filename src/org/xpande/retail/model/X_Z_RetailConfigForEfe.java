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

/** Generated Model for Z_RetailConfigForEfe
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_RetailConfigForEfe extends PO implements I_Z_RetailConfigForEfe, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200910L;

    /** Standard Constructor */
    public X_Z_RetailConfigForEfe (Properties ctx, int Z_RetailConfigForEfe_ID, String trxName)
    {
      super (ctx, Z_RetailConfigForEfe_ID, trxName);
      /** if (Z_RetailConfigForEfe_ID == 0)
        {
			setAfectaSaldo (true);
// Y
			setAplicaF01 (false);
// N
			setAplicaF02 (false);
// N
			setName (null);
			setTieneCaja (false);
// N
			setTipoConceptoForEfe (null);
			setZ_RetailConfigForEfe_ID (0);
			setZ_RetailConfig_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_RetailConfigForEfe (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_RetailConfigForEfe[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set AfectaSaldo.
		@param AfectaSaldo 
		Si afecta o no saldo
	  */
	public void setAfectaSaldo (boolean AfectaSaldo)
	{
		set_Value (COLUMNNAME_AfectaSaldo, Boolean.valueOf(AfectaSaldo));
	}

	/** Get AfectaSaldo.
		@return Si afecta o no saldo
	  */
	public boolean isAfectaSaldo () 
	{
		Object oo = get_Value(COLUMNNAME_AfectaSaldo);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set AplicaF01.
		@param AplicaF01 
		Si aplica o no al Formulario F01 en Retail
	  */
	public void setAplicaF01 (boolean AplicaF01)
	{
		set_Value (COLUMNNAME_AplicaF01, Boolean.valueOf(AplicaF01));
	}

	/** Get AplicaF01.
		@return Si aplica o no al Formulario F01 en Retail
	  */
	public boolean isAplicaF01 () 
	{
		Object oo = get_Value(COLUMNNAME_AplicaF01);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set AplicaF02.
		@param AplicaF02 
		Si aplica o no al Formulario F02 en Retail
	  */
	public void setAplicaF02 (boolean AplicaF02)
	{
		set_Value (COLUMNNAME_AplicaF02, Boolean.valueOf(AplicaF02));
	}

	/** Get AplicaF02.
		@return Si aplica o no al Formulario F02 en Retail
	  */
	public boolean isAplicaF02 () 
	{
		Object oo = get_Value(COLUMNNAME_AplicaF02);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set TieneCaja.
		@param TieneCaja 
		Si requiere o no una caja asociada
	  */
	public void setTieneCaja (boolean TieneCaja)
	{
		set_Value (COLUMNNAME_TieneCaja, Boolean.valueOf(TieneCaja));
	}

	/** Get TieneCaja.
		@return Si requiere o no una caja asociada
	  */
	public boolean isTieneCaja () 
	{
		Object oo = get_Value(COLUMNNAME_TieneCaja);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Z_RetailConfigForEfe ID.
		@param Z_RetailConfigForEfe_ID Z_RetailConfigForEfe ID	  */
	public void setZ_RetailConfigForEfe_ID (int Z_RetailConfigForEfe_ID)
	{
		if (Z_RetailConfigForEfe_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_RetailConfigForEfe_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_RetailConfigForEfe_ID, Integer.valueOf(Z_RetailConfigForEfe_ID));
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

	public I_Z_RetailConfig getZ_RetailConfig() throws RuntimeException
    {
		return (I_Z_RetailConfig)MTable.get(getCtx(), I_Z_RetailConfig.Table_Name)
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