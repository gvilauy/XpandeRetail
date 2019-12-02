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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for Z_GeneraAstoVtaDetMPSC
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_GeneraAstoVtaDetMPSC extends PO implements I_Z_GeneraAstoVtaDetMPSC, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20191202L;

    /** Standard Constructor */
    public X_Z_GeneraAstoVtaDetMPSC (Properties ctx, int Z_GeneraAstoVtaDetMPSC_ID, String trxName)
    {
      super (ctx, Z_GeneraAstoVtaDetMPSC_ID, trxName);
      /** if (Z_GeneraAstoVtaDetMPSC_ID == 0)
        {
			setCodMedioPagoPOS (null);
			setNomMedioPagoPOS (null);
			setSC_CuponCancelado (false);
// N
			setSC_Importe (Env.ZERO);
			setZ_GeneraAstoVtaDetMPSC_ID (0);
			setZ_GeneraAstoVta_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_GeneraAstoVtaDetMPSC (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_GeneraAstoVtaDetMPSC[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
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

	/** Set SC_CodigoCaja.
		@param SC_CodigoCaja SC_CodigoCaja	  */
	public void setSC_CodigoCaja (int SC_CodigoCaja)
	{
		set_Value (COLUMNNAME_SC_CodigoCaja, Integer.valueOf(SC_CodigoCaja));
	}

	/** Get SC_CodigoCaja.
		@return SC_CodigoCaja	  */
	public int getSC_CodigoCaja () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SC_CodigoCaja);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SC_CodigoMoneda.
		@param SC_CodigoMoneda SC_CodigoMoneda	  */
	public void setSC_CodigoMoneda (String SC_CodigoMoneda)
	{
		set_Value (COLUMNNAME_SC_CodigoMoneda, SC_CodigoMoneda);
	}

	/** Get SC_CodigoMoneda.
		@return SC_CodigoMoneda	  */
	public String getSC_CodigoMoneda () 
	{
		return (String)get_Value(COLUMNNAME_SC_CodigoMoneda);
	}

	/** Set SC_CotizacionVenta.
		@param SC_CotizacionVenta SC_CotizacionVenta	  */
	public void setSC_CotizacionVenta (BigDecimal SC_CotizacionVenta)
	{
		set_Value (COLUMNNAME_SC_CotizacionVenta, SC_CotizacionVenta);
	}

	/** Get SC_CotizacionVenta.
		@return SC_CotizacionVenta	  */
	public BigDecimal getSC_CotizacionVenta () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SC_CotizacionVenta);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SC_CuponCancelado.
		@param SC_CuponCancelado SC_CuponCancelado	  */
	public void setSC_CuponCancelado (boolean SC_CuponCancelado)
	{
		set_Value (COLUMNNAME_SC_CuponCancelado, Boolean.valueOf(SC_CuponCancelado));
	}

	/** Get SC_CuponCancelado.
		@return SC_CuponCancelado	  */
	public boolean isSC_CuponCancelado () 
	{
		Object oo = get_Value(COLUMNNAME_SC_CuponCancelado);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set SC_Importe.
		@param SC_Importe SC_Importe	  */
	public void setSC_Importe (BigDecimal SC_Importe)
	{
		set_Value (COLUMNNAME_SC_Importe, SC_Importe);
	}

	/** Get SC_Importe.
		@return SC_Importe	  */
	public BigDecimal getSC_Importe () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SC_Importe);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SC_NumeroMov.
		@param SC_NumeroMov SC_NumeroMov	  */
	public void setSC_NumeroMov (String SC_NumeroMov)
	{
		set_Value (COLUMNNAME_SC_NumeroMov, SC_NumeroMov);
	}

	/** Get SC_NumeroMov.
		@return SC_NumeroMov	  */
	public String getSC_NumeroMov () 
	{
		return (String)get_Value(COLUMNNAME_SC_NumeroMov);
	}

	/** Set SC_NumeroOperacion.
		@param SC_NumeroOperacion SC_NumeroOperacion	  */
	public void setSC_NumeroOperacion (String SC_NumeroOperacion)
	{
		set_Value (COLUMNNAME_SC_NumeroOperacion, SC_NumeroOperacion);
	}

	/** Get SC_NumeroOperacion.
		@return SC_NumeroOperacion	  */
	public String getSC_NumeroOperacion () 
	{
		return (String)get_Value(COLUMNNAME_SC_NumeroOperacion);
	}

	/** Set SC_NumeroTarjeta.
		@param SC_NumeroTarjeta SC_NumeroTarjeta	  */
	public void setSC_NumeroTarjeta (String SC_NumeroTarjeta)
	{
		set_Value (COLUMNNAME_SC_NumeroTarjeta, SC_NumeroTarjeta);
	}

	/** Get SC_NumeroTarjeta.
		@return SC_NumeroTarjeta	  */
	public String getSC_NumeroTarjeta () 
	{
		return (String)get_Value(COLUMNNAME_SC_NumeroTarjeta);
	}

	/** Set SC_SerieCfe.
		@param SC_SerieCfe SC_SerieCfe	  */
	public void setSC_SerieCfe (String SC_SerieCfe)
	{
		set_Value (COLUMNNAME_SC_SerieCfe, SC_SerieCfe);
	}

	/** Get SC_SerieCfe.
		@return SC_SerieCfe	  */
	public String getSC_SerieCfe () 
	{
		return (String)get_Value(COLUMNNAME_SC_SerieCfe);
	}

	/** Set SC_TipoCfe.
		@param SC_TipoCfe SC_TipoCfe	  */
	public void setSC_TipoCfe (int SC_TipoCfe)
	{
		set_Value (COLUMNNAME_SC_TipoCfe, Integer.valueOf(SC_TipoCfe));
	}

	/** Get SC_TipoCfe.
		@return SC_TipoCfe	  */
	public int getSC_TipoCfe () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SC_TipoCfe);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Z_GeneraAstoVtaDetMPSC ID.
		@param Z_GeneraAstoVtaDetMPSC_ID Z_GeneraAstoVtaDetMPSC ID	  */
	public void setZ_GeneraAstoVtaDetMPSC_ID (int Z_GeneraAstoVtaDetMPSC_ID)
	{
		if (Z_GeneraAstoVtaDetMPSC_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_GeneraAstoVtaDetMPSC_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_GeneraAstoVtaDetMPSC_ID, Integer.valueOf(Z_GeneraAstoVtaDetMPSC_ID));
	}

	/** Get Z_GeneraAstoVtaDetMPSC ID.
		@return Z_GeneraAstoVtaDetMPSC ID	  */
	public int getZ_GeneraAstoVtaDetMPSC_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_GeneraAstoVtaDetMPSC_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Z_MedioPago ID.
		@param Z_MedioPago_ID Z_MedioPago ID	  */
	public void setZ_MedioPago_ID (int Z_MedioPago_ID)
	{
		if (Z_MedioPago_ID < 1) 
			set_Value (COLUMNNAME_Z_MedioPago_ID, null);
		else 
			set_Value (COLUMNNAME_Z_MedioPago_ID, Integer.valueOf(Z_MedioPago_ID));
	}

	/** Get Z_MedioPago ID.
		@return Z_MedioPago ID	  */
	public int getZ_MedioPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MedioPago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_MedioPagoIdent ID.
		@param Z_MedioPagoIdent_ID Z_MedioPagoIdent ID	  */
	public void setZ_MedioPagoIdent_ID (int Z_MedioPagoIdent_ID)
	{
		if (Z_MedioPagoIdent_ID < 1) 
			set_Value (COLUMNNAME_Z_MedioPagoIdent_ID, null);
		else 
			set_Value (COLUMNNAME_Z_MedioPagoIdent_ID, Integer.valueOf(Z_MedioPagoIdent_ID));
	}

	/** Get Z_MedioPagoIdent ID.
		@return Z_MedioPagoIdent ID	  */
	public int getZ_MedioPagoIdent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MedioPagoIdent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_StechCreditos ID.
		@param Z_StechCreditos_ID Z_StechCreditos ID	  */
	public void setZ_StechCreditos_ID (int Z_StechCreditos_ID)
	{
		if (Z_StechCreditos_ID < 1) 
			set_Value (COLUMNNAME_Z_StechCreditos_ID, null);
		else 
			set_Value (COLUMNNAME_Z_StechCreditos_ID, Integer.valueOf(Z_StechCreditos_ID));
	}

	/** Get Z_StechCreditos ID.
		@return Z_StechCreditos ID	  */
	public int getZ_StechCreditos_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_StechCreditos_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_StechMedioPago ID.
		@param Z_StechMedioPago_ID Z_StechMedioPago ID	  */
	public void setZ_StechMedioPago_ID (int Z_StechMedioPago_ID)
	{
		if (Z_StechMedioPago_ID < 1) 
			set_Value (COLUMNNAME_Z_StechMedioPago_ID, null);
		else 
			set_Value (COLUMNNAME_Z_StechMedioPago_ID, Integer.valueOf(Z_StechMedioPago_ID));
	}

	/** Get Z_StechMedioPago ID.
		@return Z_StechMedioPago ID	  */
	public int getZ_StechMedioPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_StechMedioPago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}