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

/** Generated Model for Z_GenAstoVtaDetMPGeo
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1 - $Id$ */
public class X_Z_GenAstoVtaDetMPGeo extends PO implements I_Z_GenAstoVtaDetMPGeo, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220314L;

    /** Standard Constructor */
    public X_Z_GenAstoVtaDetMPGeo (Properties ctx, int Z_GenAstoVtaDetMPGeo_ID, String trxName)
    {
      super (ctx, Z_GenAstoVtaDetMPGeo_ID, trxName);
      /** if (Z_GenAstoVtaDetMPGeo_ID == 0)
        {
			setaplicadtoiva (false);
// N
			setescambio (false);
// N
			setIsOnline (true);
// Y
			setZ_GenAstoVtaDetMPGeo_ID (0);
			setZ_GeneraAstoVta_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_GenAstoVtaDetMPGeo (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_GenAstoVtaDetMPGeo[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set amtcashback.
		@param amtcashback amtcashback	  */
	public void setamtcashback (BigDecimal amtcashback)
	{
		set_ValueNoCheck (COLUMNNAME_amtcashback, amtcashback);
	}

	/** Get amtcashback.
		@return amtcashback	  */
	public BigDecimal getamtcashback () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtcashback);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtcashbackacct.
		@param amtcashbackacct amtcashbackacct	  */
	public void setamtcashbackacct (BigDecimal amtcashbackacct)
	{
		set_ValueNoCheck (COLUMNNAME_amtcashbackacct, amtcashbackacct);
	}

	/** Get amtcashbackacct.
		@return amtcashbackacct	  */
	public BigDecimal getamtcashbackacct () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtcashbackacct);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set amtdiscount.
		@param amtdiscount amtdiscount	  */
	public void setamtdiscount (BigDecimal amtdiscount)
	{
		set_ValueNoCheck (COLUMNNAME_amtdiscount, amtdiscount);
	}

	/** Get amtdiscount.
		@return amtdiscount	  */
	public BigDecimal getamtdiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_amtdiscount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set aplicadtoiva.
		@param aplicadtoiva aplicadtoiva	  */
	public void setaplicadtoiva (boolean aplicadtoiva)
	{
		set_ValueNoCheck (COLUMNNAME_aplicadtoiva, Boolean.valueOf(aplicadtoiva));
	}

	/** Get aplicadtoiva.
		@return aplicadtoiva	  */
	public boolean isaplicadtoiva () 
	{
		Object oo = get_Value(COLUMNNAME_aplicadtoiva);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set brandid.
		@param brandid brandid	  */
	public void setbrandid (String brandid)
	{
		set_ValueNoCheck (COLUMNNAME_brandid, brandid);
	}

	/** Get brandid.
		@return brandid	  */
	public String getbrandid () 
	{
		return (String)get_Value(COLUMNNAME_brandid);
	}

	/** Set brandname.
		@param brandname brandname	  */
	public void setbrandname (String brandname)
	{
		set_ValueNoCheck (COLUMNNAME_brandname, brandname);
	}

	/** Get brandname.
		@return brandname	  */
	public String getbrandname () 
	{
		return (String)get_Value(COLUMNNAME_brandname);
	}

	/** Set cantcuotas.
		@param cantcuotas cantcuotas	  */
	public void setcantcuotas (BigDecimal cantcuotas)
	{
		set_ValueNoCheck (COLUMNNAME_cantcuotas, cantcuotas);
	}

	/** Get cantcuotas.
		@return cantcuotas	  */
	public BigDecimal getcantcuotas () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_cantcuotas);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	/** Set checkbanknumber.
		@param checkbanknumber checkbanknumber	  */
	public void setcheckbanknumber (String checkbanknumber)
	{
		set_ValueNoCheck (COLUMNNAME_checkbanknumber, checkbanknumber);
	}

	/** Get checkbanknumber.
		@return checkbanknumber	  */
	public String getcheckbanknumber () 
	{
		return (String)get_Value(COLUMNNAME_checkbanknumber);
	}

	/** Set checknumber.
		@param checknumber checknumber	  */
	public void setchecknumber (String checknumber)
	{
		set_ValueNoCheck (COLUMNNAME_checknumber, checknumber);
	}

	/** Get checknumber.
		@return checknumber	  */
	public String getchecknumber () 
	{
		return (String)get_Value(COLUMNNAME_checknumber);
	}

	/** Set checkvaliddate.
		@param checkvaliddate checkvaliddate	  */
	public void setcheckvaliddate (Timestamp checkvaliddate)
	{
		set_ValueNoCheck (COLUMNNAME_checkvaliddate, checkvaliddate);
	}

	/** Get checkvaliddate.
		@return checkvaliddate	  */
	public Timestamp getcheckvaliddate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_checkvaliddate);
	}

	/** Set codautorizacion.
		@param codautorizacion codautorizacion	  */
	public void setcodautorizacion (String codautorizacion)
	{
		set_ValueNoCheck (COLUMNNAME_codautorizacion, codautorizacion);
	}

	/** Get codautorizacion.
		@return codautorizacion	  */
	public String getcodautorizacion () 
	{
		return (String)get_Value(COLUMNNAME_codautorizacion);
	}

	/** Set codcaja.
		@param codcaja codcaja	  */
	public void setcodcaja (String codcaja)
	{
		set_ValueNoCheck (COLUMNNAME_codcaja, codcaja);
	}

	/** Get codcaja.
		@return codcaja	  */
	public String getcodcaja () 
	{
		return (String)get_Value(COLUMNNAME_codcaja);
	}

	/** Set codcajero.
		@param codcajero codcajero	  */
	public void setcodcajero (String codcajero)
	{
		set_ValueNoCheck (COLUMNNAME_codcajero, codcajero);
	}

	/** Get codcajero.
		@return codcajero	  */
	public String getcodcajero () 
	{
		return (String)get_Value(COLUMNNAME_codcajero);
	}

	/** Set codcomercio.
		@param codcomercio codcomercio	  */
	public void setcodcomercio (String codcomercio)
	{
		set_ValueNoCheck (COLUMNNAME_codcomercio, codcomercio);
	}

	/** Get codcomercio.
		@return codcomercio	  */
	public String getcodcomercio () 
	{
		return (String)get_Value(COLUMNNAME_codcomercio);
	}

	/** Set codleyimpuesto.
		@param codleyimpuesto codleyimpuesto	  */
	public void setcodleyimpuesto (String codleyimpuesto)
	{
		set_ValueNoCheck (COLUMNNAME_codleyimpuesto, codleyimpuesto);
	}

	/** Get codleyimpuesto.
		@return codleyimpuesto	  */
	public String getcodleyimpuesto () 
	{
		return (String)get_Value(COLUMNNAME_codleyimpuesto);
	}

	/** Set codmediopago.
		@param codmediopago codmediopago	  */
	public void setcodmediopago (String codmediopago)
	{
		set_ValueNoCheck (COLUMNNAME_codmediopago, codmediopago);
	}

	/** Get codmediopago.
		@return codmediopago	  */
	public String getcodmediopago () 
	{
		return (String)get_Value(COLUMNNAME_codmediopago);
	}

	/** Set CodMedioPagoPOS.
		@param CodMedioPagoPOS 
		Código de Medio de Pago del POS en Retail
	  */
	public void setCodMedioPagoPOS (String CodMedioPagoPOS)
	{
		set_ValueNoCheck (COLUMNNAME_CodMedioPagoPOS, CodMedioPagoPOS);
	}

	/** Get CodMedioPagoPOS.
		@return Código de Medio de Pago del POS en Retail
	  */
	public String getCodMedioPagoPOS () 
	{
		return (String)get_Value(COLUMNNAME_CodMedioPagoPOS);
	}

	/** Set codprodtarjeta.
		@param codprodtarjeta codprodtarjeta	  */
	public void setcodprodtarjeta (String codprodtarjeta)
	{
		set_ValueNoCheck (COLUMNNAME_codprodtarjeta, codprodtarjeta);
	}

	/** Get codprodtarjeta.
		@return codprodtarjeta	  */
	public String getcodprodtarjeta () 
	{
		return (String)get_Value(COLUMNNAME_codprodtarjeta);
	}

	/** Set codterminal.
		@param codterminal codterminal	  */
	public void setcodterminal (String codterminal)
	{
		set_ValueNoCheck (COLUMNNAME_codterminal, codterminal);
	}

	/** Get codterminal.
		@return codterminal	  */
	public String getcodterminal () 
	{
		return (String)get_Value(COLUMNNAME_codterminal);
	}

	/** Set codtipotarjeta.
		@param codtipotarjeta codtipotarjeta	  */
	public void setcodtipotarjeta (String codtipotarjeta)
	{
		set_ValueNoCheck (COLUMNNAME_codtipotarjeta, codtipotarjeta);
	}

	/** Get codtipotarjeta.
		@return codtipotarjeta	  */
	public String getcodtipotarjeta () 
	{
		return (String)get_Value(COLUMNNAME_codtipotarjeta);
	}

	/** Set conversionrate.
		@param conversionrate conversionrate	  */
	public void setconversionrate (BigDecimal conversionrate)
	{
		set_ValueNoCheck (COLUMNNAME_conversionrate, conversionrate);
	}

	/** Get conversionrate.
		@return conversionrate	  */
	public BigDecimal getconversionrate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_conversionrate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.eevolution.model.I_C_TaxGroup getC_TaxGroup() throws RuntimeException
    {
		return (org.eevolution.model.I_C_TaxGroup)MTable.get(getCtx(), org.eevolution.model.I_C_TaxGroup.Table_Name)
			.getPO(getC_TaxGroup_ID(), get_TrxName());	}

	/** Set Tax Group.
		@param C_TaxGroup_ID Tax Group	  */
	public void setC_TaxGroup_ID (int C_TaxGroup_ID)
	{
		if (C_TaxGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_TaxGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_TaxGroup_ID, Integer.valueOf(C_TaxGroup_ID));
	}

	/** Get Tax Group.
		@return Tax Group	  */
	public int getC_TaxGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set datevoucherorig.
		@param datevoucherorig datevoucherorig	  */
	public void setdatevoucherorig (Timestamp datevoucherorig)
	{
		set_ValueNoCheck (COLUMNNAME_datevoucherorig, datevoucherorig);
	}

	/** Get datevoucherorig.
		@return datevoucherorig	  */
	public Timestamp getdatevoucherorig () 
	{
		return (Timestamp)get_Value(COLUMNNAME_datevoucherorig);
	}

	/** Set docdginame.
		@param docdginame docdginame	  */
	public void setdocdginame (String docdginame)
	{
		set_ValueNoCheck (COLUMNNAME_docdginame, docdginame);
	}

	/** Get docdginame.
		@return docdginame	  */
	public String getdocdginame () 
	{
		return (String)get_Value(COLUMNNAME_docdginame);
	}

	/** Set escambio.
		@param escambio escambio	  */
	public void setescambio (boolean escambio)
	{
		set_ValueNoCheck (COLUMNNAME_escambio, Boolean.valueOf(escambio));
	}

	/** Get escambio.
		@return escambio	  */
	public boolean isescambio () 
	{
		Object oo = get_Value(COLUMNNAME_escambio);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set fechaticket.
		@param fechaticket fechaticket	  */
	public void setfechaticket (Timestamp fechaticket)
	{
		set_ValueNoCheck (COLUMNNAME_fechaticket, fechaticket);
	}

	/** Get fechaticket.
		@return fechaticket	  */
	public Timestamp getfechaticket () 
	{
		return (Timestamp)get_Value(COLUMNNAME_fechaticket);
	}

	/** Set ISO Currency Code.
		@param ISO_Code 
		Three letter ISO 4217 Code of the Currency
	  */
	public void setISO_Code (String ISO_Code)
	{
		set_ValueNoCheck (COLUMNNAME_ISO_Code, ISO_Code);
	}

	/** Get ISO Currency Code.
		@return Three letter ISO 4217 Code of the Currency
	  */
	public String getISO_Code () 
	{
		return (String)get_Value(COLUMNNAME_ISO_Code);
	}

	/** Set Online Access.
		@param IsOnline 
		Can be accessed online 
	  */
	public void setIsOnline (boolean IsOnline)
	{
		set_ValueNoCheck (COLUMNNAME_IsOnline, Boolean.valueOf(IsOnline));
	}

	/** Get Online Access.
		@return Can be accessed online 
	  */
	public boolean isOnline () 
	{
		Object oo = get_Value(COLUMNNAME_IsOnline);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set modoingtarjeta.
		@param modoingtarjeta modoingtarjeta	  */
	public void setmodoingtarjeta (String modoingtarjeta)
	{
		set_ValueNoCheck (COLUMNNAME_modoingtarjeta, modoingtarjeta);
	}

	/** Get modoingtarjeta.
		@return modoingtarjeta	  */
	public String getmodoingtarjeta () 
	{
		return (String)get_Value(COLUMNNAME_modoingtarjeta);
	}

	/** Set nommediopago.
		@param nommediopago nommediopago	  */
	public void setnommediopago (String nommediopago)
	{
		set_ValueNoCheck (COLUMNNAME_nommediopago, nommediopago);
	}

	/** Get nommediopago.
		@return nommediopago	  */
	public String getnommediopago () 
	{
		return (String)get_Value(COLUMNNAME_nommediopago);
	}

	/** Set NomMedioPagoPOS.
		@param NomMedioPagoPOS 
		Nombre de Medio de Pago del POS en Retail
	  */
	public void setNomMedioPagoPOS (String NomMedioPagoPOS)
	{
		set_ValueNoCheck (COLUMNNAME_NomMedioPagoPOS, NomMedioPagoPOS);
	}

	/** Get NomMedioPagoPOS.
		@return Nombre de Medio de Pago del POS en Retail
	  */
	public String getNomMedioPagoPOS () 
	{
		return (String)get_Value(COLUMNNAME_NomMedioPagoPOS);
	}

	/** Set nomprodtarjeta.
		@param nomprodtarjeta nomprodtarjeta	  */
	public void setnomprodtarjeta (String nomprodtarjeta)
	{
		set_ValueNoCheck (COLUMNNAME_nomprodtarjeta, nomprodtarjeta);
	}

	/** Get nomprodtarjeta.
		@return nomprodtarjeta	  */
	public String getnomprodtarjeta () 
	{
		return (String)get_Value(COLUMNNAME_nomprodtarjeta);
	}

	/** Set nrotarjeta.
		@param nrotarjeta nrotarjeta	  */
	public void setnrotarjeta (String nrotarjeta)
	{
		set_ValueNoCheck (COLUMNNAME_nrotarjeta, nrotarjeta);
	}

	/** Get nrotarjeta.
		@return nrotarjeta	  */
	public String getnrotarjeta () 
	{
		return (String)get_Value(COLUMNNAME_nrotarjeta);
	}

	/** Set NroTicket.
		@param NroTicket 
		Número de Ticket
	  */
	public void setNroTicket (String NroTicket)
	{
		set_ValueNoCheck (COLUMNNAME_NroTicket, NroTicket);
	}

	/** Get NroTicket.
		@return Número de Ticket
	  */
	public String getNroTicket () 
	{
		return (String)get_Value(COLUMNNAME_NroTicket);
	}

	/** Set nrovoucher.
		@param nrovoucher nrovoucher	  */
	public void setnrovoucher (String nrovoucher)
	{
		set_ValueNoCheck (COLUMNNAME_nrovoucher, nrovoucher);
	}

	/** Get nrovoucher.
		@return nrovoucher	  */
	public String getnrovoucher () 
	{
		return (String)get_Value(COLUMNNAME_nrovoucher);
	}

	/** Set nrovoucherorigl.
		@param nrovoucherorigl nrovoucherorigl	  */
	public void setnrovoucherorigl (String nrovoucherorigl)
	{
		set_ValueNoCheck (COLUMNNAME_nrovoucherorigl, nrovoucherorigl);
	}

	/** Get nrovoucherorigl.
		@return nrovoucherorigl	  */
	public String getnrovoucherorigl () 
	{
		return (String)get_Value(COLUMNNAME_nrovoucherorigl);
	}

	/** Set NumeroCFE.
		@param NumeroCFE 
		Número de CFE
	  */
	public void setNumeroCFE (String NumeroCFE)
	{
		set_ValueNoCheck (COLUMNNAME_NumeroCFE, NumeroCFE);
	}

	/** Get NumeroCFE.
		@return Número de CFE
	  */
	public String getNumeroCFE () 
	{
		return (String)get_Value(COLUMNNAME_NumeroCFE);
	}

	/** Set origenvta.
		@param origenvta origenvta	  */
	public void setorigenvta (String origenvta)
	{
		set_ValueNoCheck (COLUMNNAME_origenvta, origenvta);
	}

	/** Get origenvta.
		@return origenvta	  */
	public String getorigenvta () 
	{
		return (String)get_Value(COLUMNNAME_origenvta);
	}

	/** Set planid.
		@param planid planid	  */
	public void setplanid (String planid)
	{
		set_ValueNoCheck (COLUMNNAME_planid, planid);
	}

	/** Get planid.
		@return planid	  */
	public String getplanid () 
	{
		return (String)get_Value(COLUMNNAME_planid);
	}

	/** Set posbatchid.
		@param posbatchid posbatchid	  */
	public void setposbatchid (int posbatchid)
	{
		set_ValueNoCheck (COLUMNNAME_posbatchid, Integer.valueOf(posbatchid));
	}

	/** Get posbatchid.
		@return posbatchid	  */
	public int getposbatchid () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_posbatchid);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SerieCFE.
		@param SerieCFE 
		Serie para CFE
	  */
	public void setSerieCFE (String SerieCFE)
	{
		set_ValueNoCheck (COLUMNNAME_SerieCFE, SerieCFE);
	}

	/** Get SerieCFE.
		@return Serie para CFE
	  */
	public String getSerieCFE () 
	{
		return (String)get_Value(COLUMNNAME_SerieCFE);
	}

	/** Set Tax ID.
		@param TaxID 
		Tax Identification
	  */
	public void setTaxID (String TaxID)
	{
		set_ValueNoCheck (COLUMNNAME_TaxID, TaxID);
	}

	/** Get Tax ID.
		@return Tax Identification
	  */
	public String getTaxID () 
	{
		return (String)get_Value(COLUMNNAME_TaxID);
	}

	/** Set TipoCFE.
		@param TipoCFE 
		Tipo CFE para CFE
	  */
	public void setTipoCFE (String TipoCFE)
	{
		set_ValueNoCheck (COLUMNNAME_TipoCFE, TipoCFE);
	}

	/** Get TipoCFE.
		@return Tipo CFE para CFE
	  */
	public String getTipoCFE () 
	{
		return (String)get_Value(COLUMNNAME_TipoCFE);
	}

	/** Set tipotarjeta.
		@param tipotarjeta tipotarjeta	  */
	public void settipotarjeta (String tipotarjeta)
	{
		set_ValueNoCheck (COLUMNNAME_tipotarjeta, tipotarjeta);
	}

	/** Get tipotarjeta.
		@return tipotarjeta	  */
	public String gettipotarjeta () 
	{
		return (String)get_Value(COLUMNNAME_tipotarjeta);
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

	/** Set totalentregado.
		@param totalentregado totalentregado	  */
	public void settotalentregado (BigDecimal totalentregado)
	{
		set_ValueNoCheck (COLUMNNAME_totalentregado, totalentregado);
	}

	/** Get totalentregado.
		@return totalentregado	  */
	public BigDecimal gettotalentregado () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totalentregado);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set totalentregadomonref.
		@param totalentregadomonref totalentregadomonref	  */
	public void settotalentregadomonref (BigDecimal totalentregadomonref)
	{
		set_ValueNoCheck (COLUMNNAME_totalentregadomonref, totalentregadomonref);
	}

	/** Get totalentregadomonref.
		@return totalentregadomonref	  */
	public BigDecimal gettotalentregadomonref () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totalentregadomonref);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Z_GenAstoVtaDetMPGeo ID.
		@param Z_GenAstoVtaDetMPGeo_ID Z_GenAstoVtaDetMPGeo ID	  */
	public void setZ_GenAstoVtaDetMPGeo_ID (int Z_GenAstoVtaDetMPGeo_ID)
	{
		if (Z_GenAstoVtaDetMPGeo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_GenAstoVtaDetMPGeo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_GenAstoVtaDetMPGeo_ID, Integer.valueOf(Z_GenAstoVtaDetMPGeo_ID));
	}

	/** Get Z_GenAstoVtaDetMPGeo ID.
		@return Z_GenAstoVtaDetMPGeo ID	  */
	public int getZ_GenAstoVtaDetMPGeo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_GenAstoVtaDetMPGeo_ID);
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
			set_ValueNoCheck (COLUMNNAME_Z_GeneraAstoVta_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_GeneraAstoVta_ID, Integer.valueOf(Z_GeneraAstoVta_ID));
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

	/** Set Z_MedioPagoPos ID.
		@param Z_MedioPagoPos_ID Z_MedioPagoPos ID	  */
	public void setZ_MedioPagoPos_ID (int Z_MedioPagoPos_ID)
	{
		if (Z_MedioPagoPos_ID < 1) 
			set_Value (COLUMNNAME_Z_MedioPagoPos_ID, null);
		else 
			set_Value (COLUMNNAME_Z_MedioPagoPos_ID, Integer.valueOf(Z_MedioPagoPos_ID));
	}

	/** Get Z_MedioPagoPos ID.
		@return Z_MedioPagoPos ID	  */
	public int getZ_MedioPagoPos_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MedioPagoPos_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_MPagoIdentPos ID.
		@param Z_MPagoIdentPos_ID Z_MPagoIdentPos ID	  */
	public void setZ_MPagoIdentPos_ID (int Z_MPagoIdentPos_ID)
	{
		if (Z_MPagoIdentPos_ID < 1) 
			set_Value (COLUMNNAME_Z_MPagoIdentPos_ID, null);
		else 
			set_Value (COLUMNNAME_Z_MPagoIdentPos_ID, Integer.valueOf(Z_MPagoIdentPos_ID));
	}

	/** Get Z_MPagoIdentPos ID.
		@return Z_MPagoIdentPos ID	  */
	public int getZ_MPagoIdentPos_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_MPagoIdentPos_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}