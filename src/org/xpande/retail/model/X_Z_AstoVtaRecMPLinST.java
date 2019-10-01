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
import org.compiere.util.KeyNamePair;

/** Generated Model for Z_AstoVtaRecMPLinST
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_AstoVtaRecMPLinST extends PO implements I_Z_AstoVtaRecMPLinST, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20191001L;

    /** Standard Constructor */
    public X_Z_AstoVtaRecMPLinST (Properties ctx, int Z_AstoVtaRecMPLinST_ID, String trxName)
    {
      super (ctx, Z_AstoVtaRecMPLinST_ID, trxName);
      /** if (Z_AstoVtaRecMPLinST_ID == 0)
        {
			setZ_AstoVtaRecMP_ID (0);
			setZ_AstoVtaRecMPLinST_ID (0);
			setZ_GeneraAstoVtaDetMPST_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_AstoVtaRecMPLinST (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_AstoVtaRecMPLinST[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Transaction Date.
		@param DateTrx 
		Transaction Date
	  */
	public void setDateTrx (Timestamp DateTrx)
	{
		set_ValueNoCheck (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Transaction Date.
		@return Transaction Date
	  */
	public Timestamp getDateTrx () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_ValueNoCheck (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set ST_Cambio.
		@param ST_Cambio ST_Cambio	  */
	public void setST_Cambio (BigDecimal ST_Cambio)
	{
		set_ValueNoCheck (COLUMNNAME_ST_Cambio, ST_Cambio);
	}

	/** Get ST_Cambio.
		@return ST_Cambio	  */
	public BigDecimal getST_Cambio () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ST_Cambio);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ST_CodigoCaja.
		@param ST_CodigoCaja ST_CodigoCaja	  */
	public void setST_CodigoCaja (String ST_CodigoCaja)
	{
		set_ValueNoCheck (COLUMNNAME_ST_CodigoCaja, ST_CodigoCaja);
	}

	/** Get ST_CodigoCaja.
		@return ST_CodigoCaja	  */
	public String getST_CodigoCaja () 
	{
		return (String)get_Value(COLUMNNAME_ST_CodigoCaja);
	}

	/** Set ST_CodigoCajera.
		@param ST_CodigoCajera ST_CodigoCajera	  */
	public void setST_CodigoCajera (String ST_CodigoCajera)
	{
		set_ValueNoCheck (COLUMNNAME_ST_CodigoCajera, ST_CodigoCajera);
	}

	/** Get ST_CodigoCajera.
		@return ST_CodigoCajera	  */
	public String getST_CodigoCajera () 
	{
		return (String)get_Value(COLUMNNAME_ST_CodigoCajera);
	}

	/** Set ST_CodigoCC.
		@param ST_CodigoCC ST_CodigoCC	  */
	public void setST_CodigoCC (String ST_CodigoCC)
	{
		set_ValueNoCheck (COLUMNNAME_ST_CodigoCC, ST_CodigoCC);
	}

	/** Get ST_CodigoCC.
		@return ST_CodigoCC	  */
	public String getST_CodigoCC () 
	{
		return (String)get_Value(COLUMNNAME_ST_CodigoCC);
	}

	/** Set ST_CodigoMedioPago.
		@param ST_CodigoMedioPago ST_CodigoMedioPago	  */
	public void setST_CodigoMedioPago (String ST_CodigoMedioPago)
	{
		set_ValueNoCheck (COLUMNNAME_ST_CodigoMedioPago, ST_CodigoMedioPago);
	}

	/** Get ST_CodigoMedioPago.
		@return ST_CodigoMedioPago	  */
	public String getST_CodigoMedioPago () 
	{
		return (String)get_Value(COLUMNNAME_ST_CodigoMedioPago);
	}

	/** Set ST_CodigoMoneda.
		@param ST_CodigoMoneda ST_CodigoMoneda	  */
	public void setST_CodigoMoneda (String ST_CodigoMoneda)
	{
		set_ValueNoCheck (COLUMNNAME_ST_CodigoMoneda, ST_CodigoMoneda);
	}

	/** Get ST_CodigoMoneda.
		@return ST_CodigoMoneda	  */
	public String getST_CodigoMoneda () 
	{
		return (String)get_Value(COLUMNNAME_ST_CodigoMoneda);
	}

	/** Set ST_DescripcionCFE.
		@param ST_DescripcionCFE ST_DescripcionCFE	  */
	public void setST_DescripcionCFE (String ST_DescripcionCFE)
	{
		set_ValueNoCheck (COLUMNNAME_ST_DescripcionCFE, ST_DescripcionCFE);
	}

	/** Get ST_DescripcionCFE.
		@return ST_DescripcionCFE	  */
	public String getST_DescripcionCFE () 
	{
		return (String)get_Value(COLUMNNAME_ST_DescripcionCFE);
	}

	/** Set ST_DescuentoAfam.
		@param ST_DescuentoAfam 
		Atributo para Sisteco
	  */
	public void setST_DescuentoAfam (BigDecimal ST_DescuentoAfam)
	{
		set_ValueNoCheck (COLUMNNAME_ST_DescuentoAfam, ST_DescuentoAfam);
	}

	/** Get ST_DescuentoAfam.
		@return Atributo para Sisteco
	  */
	public BigDecimal getST_DescuentoAfam () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ST_DescuentoAfam);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ST_MontoDescuentoLeyIVA.
		@param ST_MontoDescuentoLeyIVA ST_MontoDescuentoLeyIVA	  */
	public void setST_MontoDescuentoLeyIVA (BigDecimal ST_MontoDescuentoLeyIVA)
	{
		set_ValueNoCheck (COLUMNNAME_ST_MontoDescuentoLeyIVA, ST_MontoDescuentoLeyIVA);
	}

	/** Get ST_MontoDescuentoLeyIVA.
		@return ST_MontoDescuentoLeyIVA	  */
	public BigDecimal getST_MontoDescuentoLeyIVA () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ST_MontoDescuentoLeyIVA);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ST_NombreCC.
		@param ST_NombreCC ST_NombreCC	  */
	public void setST_NombreCC (String ST_NombreCC)
	{
		set_ValueNoCheck (COLUMNNAME_ST_NombreCC, ST_NombreCC);
	}

	/** Get ST_NombreCC.
		@return ST_NombreCC	  */
	public String getST_NombreCC () 
	{
		return (String)get_Value(COLUMNNAME_ST_NombreCC);
	}

	/** Set ST_NombreMedioPago.
		@param ST_NombreMedioPago 
		Atributo para interface con Sisteco
	  */
	public void setST_NombreMedioPago (String ST_NombreMedioPago)
	{
		set_ValueNoCheck (COLUMNNAME_ST_NombreMedioPago, ST_NombreMedioPago);
	}

	/** Get ST_NombreMedioPago.
		@return Atributo para interface con Sisteco
	  */
	public String getST_NombreMedioPago () 
	{
		return (String)get_Value(COLUMNNAME_ST_NombreMedioPago);
	}

	/** Set ST_NombreTarjeta.
		@param ST_NombreTarjeta ST_NombreTarjeta	  */
	public void setST_NombreTarjeta (String ST_NombreTarjeta)
	{
		set_ValueNoCheck (COLUMNNAME_ST_NombreTarjeta, ST_NombreTarjeta);
	}

	/** Get ST_NombreTarjeta.
		@return ST_NombreTarjeta	  */
	public String getST_NombreTarjeta () 
	{
		return (String)get_Value(COLUMNNAME_ST_NombreTarjeta);
	}

	/** Set ST_NumeroTarjeta.
		@param ST_NumeroTarjeta ST_NumeroTarjeta	  */
	public void setST_NumeroTarjeta (String ST_NumeroTarjeta)
	{
		set_ValueNoCheck (COLUMNNAME_ST_NumeroTarjeta, ST_NumeroTarjeta);
	}

	/** Get ST_NumeroTarjeta.
		@return ST_NumeroTarjeta	  */
	public String getST_NumeroTarjeta () 
	{
		return (String)get_Value(COLUMNNAME_ST_NumeroTarjeta);
	}

	/** Set ST_NumeroTicket.
		@param ST_NumeroTicket ST_NumeroTicket	  */
	public void setST_NumeroTicket (String ST_NumeroTicket)
	{
		set_ValueNoCheck (COLUMNNAME_ST_NumeroTicket, ST_NumeroTicket);
	}

	/** Get ST_NumeroTicket.
		@return ST_NumeroTicket	  */
	public String getST_NumeroTicket () 
	{
		return (String)get_Value(COLUMNNAME_ST_NumeroTicket);
	}

	/** Set ST_TextoLey.
		@param ST_TextoLey ST_TextoLey	  */
	public void setST_TextoLey (String ST_TextoLey)
	{
		set_ValueNoCheck (COLUMNNAME_ST_TextoLey, ST_TextoLey);
	}

	/** Get ST_TextoLey.
		@return ST_TextoLey	  */
	public String getST_TextoLey () 
	{
		return (String)get_Value(COLUMNNAME_ST_TextoLey);
	}

	/** Set ST_TimestampTicket.
		@param ST_TimestampTicket ST_TimestampTicket	  */
	public void setST_TimestampTicket (Timestamp ST_TimestampTicket)
	{
		set_ValueNoCheck (COLUMNNAME_ST_TimestampTicket, ST_TimestampTicket);
	}

	/** Get ST_TimestampTicket.
		@return ST_TimestampTicket	  */
	public Timestamp getST_TimestampTicket () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ST_TimestampTicket);
	}

	/** Set ST_TipoLinea.
		@param ST_TipoLinea ST_TipoLinea	  */
	public void setST_TipoLinea (String ST_TipoLinea)
	{
		set_ValueNoCheck (COLUMNNAME_ST_TipoLinea, ST_TipoLinea);
	}

	/** Get ST_TipoLinea.
		@return ST_TipoLinea	  */
	public String getST_TipoLinea () 
	{
		return (String)get_Value(COLUMNNAME_ST_TipoLinea);
	}

	/** Set ST_TipoTarjetaCredito.
		@param ST_TipoTarjetaCredito ST_TipoTarjetaCredito	  */
	public void setST_TipoTarjetaCredito (String ST_TipoTarjetaCredito)
	{
		set_ValueNoCheck (COLUMNNAME_ST_TipoTarjetaCredito, ST_TipoTarjetaCredito);
	}

	/** Get ST_TipoTarjetaCredito.
		@return ST_TipoTarjetaCredito	  */
	public String getST_TipoTarjetaCredito () 
	{
		return (String)get_Value(COLUMNNAME_ST_TipoTarjetaCredito);
	}

	/** Set ST_TotalEntregado.
		@param ST_TotalEntregado ST_TotalEntregado	  */
	public void setST_TotalEntregado (BigDecimal ST_TotalEntregado)
	{
		set_ValueNoCheck (COLUMNNAME_ST_TotalEntregado, ST_TotalEntregado);
	}

	/** Get ST_TotalEntregado.
		@return ST_TotalEntregado	  */
	public BigDecimal getST_TotalEntregado () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ST_TotalEntregado);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ST_TotalEntregadoMonedaRef.
		@param ST_TotalEntregadoMonedaRef ST_TotalEntregadoMonedaRef	  */
	public void setST_TotalEntregadoMonedaRef (BigDecimal ST_TotalEntregadoMonedaRef)
	{
		set_ValueNoCheck (COLUMNNAME_ST_TotalEntregadoMonedaRef, ST_TotalEntregadoMonedaRef);
	}

	/** Get ST_TotalEntregadoMonedaRef.
		@return ST_TotalEntregadoMonedaRef	  */
	public BigDecimal getST_TotalEntregadoMonedaRef () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ST_TotalEntregadoMonedaRef);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ST_TotalMPPagoMoneda.
		@param ST_TotalMPPagoMoneda ST_TotalMPPagoMoneda	  */
	public void setST_TotalMPPagoMoneda (BigDecimal ST_TotalMPPagoMoneda)
	{
		set_ValueNoCheck (COLUMNNAME_ST_TotalMPPagoMoneda, ST_TotalMPPagoMoneda);
	}

	/** Get ST_TotalMPPagoMoneda.
		@return ST_TotalMPPagoMoneda	  */
	public BigDecimal getST_TotalMPPagoMoneda () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ST_TotalMPPagoMoneda);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ST_TotalMPPagoMonedaRef.
		@param ST_TotalMPPagoMonedaRef ST_TotalMPPagoMonedaRef	  */
	public void setST_TotalMPPagoMonedaRef (BigDecimal ST_TotalMPPagoMonedaRef)
	{
		set_ValueNoCheck (COLUMNNAME_ST_TotalMPPagoMonedaRef, ST_TotalMPPagoMonedaRef);
	}

	/** Get ST_TotalMPPagoMonedaRef.
		@return ST_TotalMPPagoMonedaRef	  */
	public BigDecimal getST_TotalMPPagoMonedaRef () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ST_TotalMPPagoMonedaRef);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Total Amount.
		@param TotalAmt 
		Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt)
	{
		set_ValueNoCheck (COLUMNNAME_TotalAmt, TotalAmt);
	}

	/** Get Total Amount.
		@return Total Amount
	  */
	public BigDecimal getTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	public I_Z_AstoVtaRecMP getZ_AstoVtaRecMP() throws RuntimeException
    {
		return (I_Z_AstoVtaRecMP)MTable.get(getCtx(), I_Z_AstoVtaRecMP.Table_Name)
			.getPO(getZ_AstoVtaRecMP_ID(), get_TrxName());	}

	/** Set Z_AstoVtaRecMP ID.
		@param Z_AstoVtaRecMP_ID Z_AstoVtaRecMP ID	  */
	public void setZ_AstoVtaRecMP_ID (int Z_AstoVtaRecMP_ID)
	{
		if (Z_AstoVtaRecMP_ID < 1) 
			set_Value (COLUMNNAME_Z_AstoVtaRecMP_ID, null);
		else 
			set_Value (COLUMNNAME_Z_AstoVtaRecMP_ID, Integer.valueOf(Z_AstoVtaRecMP_ID));
	}

	/** Get Z_AstoVtaRecMP ID.
		@return Z_AstoVtaRecMP ID	  */
	public int getZ_AstoVtaRecMP_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_AstoVtaRecMP_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_AstoVtaRecMPLinST ID.
		@param Z_AstoVtaRecMPLinST_ID Z_AstoVtaRecMPLinST ID	  */
	public void setZ_AstoVtaRecMPLinST_ID (int Z_AstoVtaRecMPLinST_ID)
	{
		if (Z_AstoVtaRecMPLinST_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_AstoVtaRecMPLinST_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_AstoVtaRecMPLinST_ID, Integer.valueOf(Z_AstoVtaRecMPLinST_ID));
	}

	/** Get Z_AstoVtaRecMPLinST ID.
		@return Z_AstoVtaRecMPLinST ID	  */
	public int getZ_AstoVtaRecMPLinST_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_AstoVtaRecMPLinST_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_GeneraAstoVtaDetMPST getZ_GeneraAstoVtaDetMPST() throws RuntimeException
    {
		return (I_Z_GeneraAstoVtaDetMPST)MTable.get(getCtx(), I_Z_GeneraAstoVtaDetMPST.Table_Name)
			.getPO(getZ_GeneraAstoVtaDetMPST_ID(), get_TrxName());	}

	/** Set Z_GeneraAstoVtaDetMPST ID.
		@param Z_GeneraAstoVtaDetMPST_ID Z_GeneraAstoVtaDetMPST ID	  */
	public void setZ_GeneraAstoVtaDetMPST_ID (int Z_GeneraAstoVtaDetMPST_ID)
	{
		if (Z_GeneraAstoVtaDetMPST_ID < 1) 
			set_Value (COLUMNNAME_Z_GeneraAstoVtaDetMPST_ID, null);
		else 
			set_Value (COLUMNNAME_Z_GeneraAstoVtaDetMPST_ID, Integer.valueOf(Z_GeneraAstoVtaDetMPST_ID));
	}

	/** Get Z_GeneraAstoVtaDetMPST ID.
		@return Z_GeneraAstoVtaDetMPST ID	  */
	public int getZ_GeneraAstoVtaDetMPST_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_GeneraAstoVtaDetMPST_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_SistecoMedioPago ID.
		@param Z_SistecoMedioPago_ID Z_SistecoMedioPago ID	  */
	public void setZ_SistecoMedioPago_ID (int Z_SistecoMedioPago_ID)
	{
		if (Z_SistecoMedioPago_ID < 1) 
			set_Value (COLUMNNAME_Z_SistecoMedioPago_ID, null);
		else 
			set_Value (COLUMNNAME_Z_SistecoMedioPago_ID, Integer.valueOf(Z_SistecoMedioPago_ID));
	}

	/** Get Z_SistecoMedioPago ID.
		@return Z_SistecoMedioPago ID	  */
	public int getZ_SistecoMedioPago_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_SistecoMedioPago_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_SistecoTipoTarjeta ID.
		@param Z_SistecoTipoTarjeta_ID Z_SistecoTipoTarjeta ID	  */
	public void setZ_SistecoTipoTarjeta_ID (int Z_SistecoTipoTarjeta_ID)
	{
		if (Z_SistecoTipoTarjeta_ID < 1) 
			set_Value (COLUMNNAME_Z_SistecoTipoTarjeta_ID, null);
		else 
			set_Value (COLUMNNAME_Z_SistecoTipoTarjeta_ID, Integer.valueOf(Z_SistecoTipoTarjeta_ID));
	}

	/** Get Z_SistecoTipoTarjeta ID.
		@return Z_SistecoTipoTarjeta ID	  */
	public int getZ_SistecoTipoTarjeta_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_SistecoTipoTarjeta_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}