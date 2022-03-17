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
package org.xpande.retail.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for Z_AstoVtaRecMPLinGeo
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1
 */
public interface I_Z_AstoVtaRecMPLinGeo 
{

    /** TableName=Z_AstoVtaRecMPLinGeo */
    public static final String Table_Name = "Z_AstoVtaRecMPLinGeo";

    /** AD_Table_ID=1000397 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name amtcashback */
    public static final String COLUMNNAME_amtcashback = "amtcashback";

	/** Set amtcashback	  */
	public void setamtcashback (BigDecimal amtcashback);

	/** Get amtcashback	  */
	public BigDecimal getamtcashback();

    /** Column name amtcashbackacct */
    public static final String COLUMNNAME_amtcashbackacct = "amtcashbackacct";

	/** Set amtcashbackacct	  */
	public void setamtcashbackacct (BigDecimal amtcashbackacct);

	/** Get amtcashbackacct	  */
	public BigDecimal getamtcashbackacct();

    /** Column name amtdiscount */
    public static final String COLUMNNAME_amtdiscount = "amtdiscount";

	/** Set amtdiscount	  */
	public void setamtdiscount (BigDecimal amtdiscount);

	/** Get amtdiscount	  */
	public BigDecimal getamtdiscount();

    /** Column name aplicadtoiva */
    public static final String COLUMNNAME_aplicadtoiva = "aplicadtoiva";

	/** Set aplicadtoiva	  */
	public void setaplicadtoiva (boolean aplicadtoiva);

	/** Get aplicadtoiva	  */
	public boolean isaplicadtoiva();

    /** Column name brandid */
    public static final String COLUMNNAME_brandid = "brandid";

	/** Set brandid	  */
	public void setbrandid (String brandid);

	/** Get brandid	  */
	public String getbrandid();

    /** Column name brandname */
    public static final String COLUMNNAME_brandname = "brandname";

	/** Set brandname	  */
	public void setbrandname (String brandname);

	/** Get brandname	  */
	public String getbrandname();

    /** Column name cantcuotas */
    public static final String COLUMNNAME_cantcuotas = "cantcuotas";

	/** Set cantcuotas	  */
	public void setcantcuotas (BigDecimal cantcuotas);

	/** Get cantcuotas	  */
	public BigDecimal getcantcuotas();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name checkbanknumber */
    public static final String COLUMNNAME_checkbanknumber = "checkbanknumber";

	/** Set checkbanknumber	  */
	public void setcheckbanknumber (String checkbanknumber);

	/** Get checkbanknumber	  */
	public String getcheckbanknumber();

    /** Column name checknumber */
    public static final String COLUMNNAME_checknumber = "checknumber";

	/** Set checknumber	  */
	public void setchecknumber (String checknumber);

	/** Get checknumber	  */
	public String getchecknumber();

    /** Column name checkvaliddate */
    public static final String COLUMNNAME_checkvaliddate = "checkvaliddate";

	/** Set checkvaliddate	  */
	public void setcheckvaliddate (Timestamp checkvaliddate);

	/** Get checkvaliddate	  */
	public Timestamp getcheckvaliddate();

    /** Column name codautorizacion */
    public static final String COLUMNNAME_codautorizacion = "codautorizacion";

	/** Set codautorizacion	  */
	public void setcodautorizacion (String codautorizacion);

	/** Get codautorizacion	  */
	public String getcodautorizacion();

    /** Column name codcaja */
    public static final String COLUMNNAME_codcaja = "codcaja";

	/** Set codcaja	  */
	public void setcodcaja (String codcaja);

	/** Get codcaja	  */
	public String getcodcaja();

    /** Column name codcajero */
    public static final String COLUMNNAME_codcajero = "codcajero";

	/** Set codcajero	  */
	public void setcodcajero (String codcajero);

	/** Get codcajero	  */
	public String getcodcajero();

    /** Column name codcomercio */
    public static final String COLUMNNAME_codcomercio = "codcomercio";

	/** Set codcomercio	  */
	public void setcodcomercio (String codcomercio);

	/** Get codcomercio	  */
	public String getcodcomercio();

    /** Column name codleyimpuesto */
    public static final String COLUMNNAME_codleyimpuesto = "codleyimpuesto";

	/** Set codleyimpuesto	  */
	public void setcodleyimpuesto (String codleyimpuesto);

	/** Get codleyimpuesto	  */
	public String getcodleyimpuesto();

    /** Column name codmediopago */
    public static final String COLUMNNAME_codmediopago = "codmediopago";

	/** Set codmediopago	  */
	public void setcodmediopago (String codmediopago);

	/** Get codmediopago	  */
	public String getcodmediopago();

    /** Column name CodMedioPagoPOS */
    public static final String COLUMNNAME_CodMedioPagoPOS = "CodMedioPagoPOS";

	/** Set CodMedioPagoPOS.
	  * Código de Medio de Pago del POS en Retail
	  */
	public void setCodMedioPagoPOS (String CodMedioPagoPOS);

	/** Get CodMedioPagoPOS.
	  * Código de Medio de Pago del POS en Retail
	  */
	public String getCodMedioPagoPOS();

    /** Column name codprodtarjeta */
    public static final String COLUMNNAME_codprodtarjeta = "codprodtarjeta";

	/** Set codprodtarjeta	  */
	public void setcodprodtarjeta (String codprodtarjeta);

	/** Get codprodtarjeta	  */
	public String getcodprodtarjeta();

    /** Column name codterminal */
    public static final String COLUMNNAME_codterminal = "codterminal";

	/** Set codterminal	  */
	public void setcodterminal (String codterminal);

	/** Get codterminal	  */
	public String getcodterminal();

    /** Column name codtipotarjeta */
    public static final String COLUMNNAME_codtipotarjeta = "codtipotarjeta";

	/** Set codtipotarjeta	  */
	public void setcodtipotarjeta (String codtipotarjeta);

	/** Get codtipotarjeta	  */
	public String getcodtipotarjeta();

    /** Column name conversionrate */
    public static final String COLUMNNAME_conversionrate = "conversionrate";

	/** Set conversionrate	  */
	public void setconversionrate (BigDecimal conversionrate);

	/** Get conversionrate	  */
	public BigDecimal getconversionrate();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name C_TaxGroup_ID */
    public static final String COLUMNNAME_C_TaxGroup_ID = "C_TaxGroup_ID";

	/** Set Tax Group	  */
	public void setC_TaxGroup_ID (int C_TaxGroup_ID);

	/** Get Tax Group	  */
	public int getC_TaxGroup_ID();

	public org.eevolution.model.I_C_TaxGroup getC_TaxGroup() throws RuntimeException;

    /** Column name datevoucherorig */
    public static final String COLUMNNAME_datevoucherorig = "datevoucherorig";

	/** Set datevoucherorig	  */
	public void setdatevoucherorig (Timestamp datevoucherorig);

	/** Get datevoucherorig	  */
	public Timestamp getdatevoucherorig();

    /** Column name docdginame */
    public static final String COLUMNNAME_docdginame = "docdginame";

	/** Set docdginame	  */
	public void setdocdginame (String docdginame);

	/** Get docdginame	  */
	public String getdocdginame();

    /** Column name escambio */
    public static final String COLUMNNAME_escambio = "escambio";

	/** Set escambio	  */
	public void setescambio (boolean escambio);

	/** Get escambio	  */
	public boolean isescambio();

    /** Column name fechaticket */
    public static final String COLUMNNAME_fechaticket = "fechaticket";

	/** Set fechaticket	  */
	public void setfechaticket (Timestamp fechaticket);

	/** Get fechaticket	  */
	public Timestamp getfechaticket();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name ISO_Code */
    public static final String COLUMNNAME_ISO_Code = "ISO_Code";

	/** Set ISO Currency Code.
	  * Three letter ISO 4217 Code of the Currency
	  */
	public void setISO_Code (String ISO_Code);

	/** Get ISO Currency Code.
	  * Three letter ISO 4217 Code of the Currency
	  */
	public String getISO_Code();

    /** Column name IsOnline */
    public static final String COLUMNNAME_IsOnline = "IsOnline";

	/** Set Online Access.
	  * Can be accessed online 
	  */
	public void setIsOnline (boolean IsOnline);

	/** Get Online Access.
	  * Can be accessed online 
	  */
	public boolean isOnline();

    /** Column name modoingtarjeta */
    public static final String COLUMNNAME_modoingtarjeta = "modoingtarjeta";

	/** Set modoingtarjeta	  */
	public void setmodoingtarjeta (String modoingtarjeta);

	/** Get modoingtarjeta	  */
	public String getmodoingtarjeta();

    /** Column name nommediopago */
    public static final String COLUMNNAME_nommediopago = "nommediopago";

	/** Set nommediopago	  */
	public void setnommediopago (String nommediopago);

	/** Get nommediopago	  */
	public String getnommediopago();

    /** Column name NomMedioPagoPOS */
    public static final String COLUMNNAME_NomMedioPagoPOS = "NomMedioPagoPOS";

	/** Set NomMedioPagoPOS.
	  * Nombre de Medio de Pago del POS en Retail
	  */
	public void setNomMedioPagoPOS (String NomMedioPagoPOS);

	/** Get NomMedioPagoPOS.
	  * Nombre de Medio de Pago del POS en Retail
	  */
	public String getNomMedioPagoPOS();

    /** Column name nomprodtarjeta */
    public static final String COLUMNNAME_nomprodtarjeta = "nomprodtarjeta";

	/** Set nomprodtarjeta	  */
	public void setnomprodtarjeta (String nomprodtarjeta);

	/** Get nomprodtarjeta	  */
	public String getnomprodtarjeta();

    /** Column name nrotarjeta */
    public static final String COLUMNNAME_nrotarjeta = "nrotarjeta";

	/** Set nrotarjeta	  */
	public void setnrotarjeta (String nrotarjeta);

	/** Get nrotarjeta	  */
	public String getnrotarjeta();

    /** Column name NroTicket */
    public static final String COLUMNNAME_NroTicket = "NroTicket";

	/** Set NroTicket.
	  * Número de Ticket
	  */
	public void setNroTicket (String NroTicket);

	/** Get NroTicket.
	  * Número de Ticket
	  */
	public String getNroTicket();

    /** Column name nrovoucher */
    public static final String COLUMNNAME_nrovoucher = "nrovoucher";

	/** Set nrovoucher	  */
	public void setnrovoucher (String nrovoucher);

	/** Get nrovoucher	  */
	public String getnrovoucher();

    /** Column name nrovoucherorigl */
    public static final String COLUMNNAME_nrovoucherorigl = "nrovoucherorigl";

	/** Set nrovoucherorigl	  */
	public void setnrovoucherorigl (String nrovoucherorigl);

	/** Get nrovoucherorigl	  */
	public String getnrovoucherorigl();

    /** Column name NumeroCFE */
    public static final String COLUMNNAME_NumeroCFE = "NumeroCFE";

	/** Set NumeroCFE.
	  * Número de CFE
	  */
	public void setNumeroCFE (String NumeroCFE);

	/** Get NumeroCFE.
	  * Número de CFE
	  */
	public String getNumeroCFE();

    /** Column name origenvta */
    public static final String COLUMNNAME_origenvta = "origenvta";

	/** Set origenvta	  */
	public void setorigenvta (String origenvta);

	/** Get origenvta	  */
	public String getorigenvta();

    /** Column name planid */
    public static final String COLUMNNAME_planid = "planid";

	/** Set planid	  */
	public void setplanid (String planid);

	/** Get planid	  */
	public String getplanid();

    /** Column name posbatchid */
    public static final String COLUMNNAME_posbatchid = "posbatchid";

	/** Set posbatchid	  */
	public void setposbatchid (int posbatchid);

	/** Get posbatchid	  */
	public int getposbatchid();

    /** Column name SerieCFE */
    public static final String COLUMNNAME_SerieCFE = "SerieCFE";

	/** Set SerieCFE.
	  * Serie para CFE
	  */
	public void setSerieCFE (String SerieCFE);

	/** Get SerieCFE.
	  * Serie para CFE
	  */
	public String getSerieCFE();

    /** Column name TaxID */
    public static final String COLUMNNAME_TaxID = "TaxID";

	/** Set Tax ID.
	  * Tax Identification
	  */
	public void setTaxID (String TaxID);

	/** Get Tax ID.
	  * Tax Identification
	  */
	public String getTaxID();

    /** Column name TipoCFE */
    public static final String COLUMNNAME_TipoCFE = "TipoCFE";

	/** Set TipoCFE.
	  * Tipo CFE para CFE
	  */
	public void setTipoCFE (String TipoCFE);

	/** Get TipoCFE.
	  * Tipo CFE para CFE
	  */
	public String getTipoCFE();

    /** Column name tipotarjeta */
    public static final String COLUMNNAME_tipotarjeta = "tipotarjeta";

	/** Set tipotarjeta	  */
	public void settipotarjeta (String tipotarjeta);

	/** Get tipotarjeta	  */
	public String gettipotarjeta();

    /** Column name TotalAmt */
    public static final String COLUMNNAME_TotalAmt = "TotalAmt";

	/** Set Total Amount.
	  * Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt);

	/** Get Total Amount.
	  * Total Amount
	  */
	public BigDecimal getTotalAmt();

    /** Column name totalentregado */
    public static final String COLUMNNAME_totalentregado = "totalentregado";

	/** Set totalentregado	  */
	public void settotalentregado (BigDecimal totalentregado);

	/** Get totalentregado	  */
	public BigDecimal gettotalentregado();

    /** Column name totalentregadomonref */
    public static final String COLUMNNAME_totalentregadomonref = "totalentregadomonref";

	/** Set totalentregadomonref	  */
	public void settotalentregadomonref (BigDecimal totalentregadomonref);

	/** Get totalentregadomonref	  */
	public BigDecimal gettotalentregadomonref();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name Z_AstoVtaRecMP_ID */
    public static final String COLUMNNAME_Z_AstoVtaRecMP_ID = "Z_AstoVtaRecMP_ID";

	/** Set Z_AstoVtaRecMP ID	  */
	public void setZ_AstoVtaRecMP_ID (int Z_AstoVtaRecMP_ID);

	/** Get Z_AstoVtaRecMP ID	  */
	public int getZ_AstoVtaRecMP_ID();

	public I_Z_AstoVtaRecMP getZ_AstoVtaRecMP() throws RuntimeException;

    /** Column name Z_AstoVtaRecMPLinGeo_ID */
    public static final String COLUMNNAME_Z_AstoVtaRecMPLinGeo_ID = "Z_AstoVtaRecMPLinGeo_ID";

	/** Set Z_AstoVtaRecMPLinGeo ID	  */
	public void setZ_AstoVtaRecMPLinGeo_ID (int Z_AstoVtaRecMPLinGeo_ID);

	/** Get Z_AstoVtaRecMPLinGeo ID	  */
	public int getZ_AstoVtaRecMPLinGeo_ID();

    /** Column name Z_GenAstoVtaDetMPGeo_ID */
    public static final String COLUMNNAME_Z_GenAstoVtaDetMPGeo_ID = "Z_GenAstoVtaDetMPGeo_ID";

	/** Set Z_GenAstoVtaDetMPGeo ID	  */
	public void setZ_GenAstoVtaDetMPGeo_ID (int Z_GenAstoVtaDetMPGeo_ID);

	/** Get Z_GenAstoVtaDetMPGeo ID	  */
	public int getZ_GenAstoVtaDetMPGeo_ID();

	public I_Z_GenAstoVtaDetMPGeo getZ_GenAstoVtaDetMPGeo() throws RuntimeException;

    /** Column name Z_MedioPagoPos_ID */
    public static final String COLUMNNAME_Z_MedioPagoPos_ID = "Z_MedioPagoPos_ID";

	/** Set Z_MedioPagoPos ID	  */
	public void setZ_MedioPagoPos_ID (int Z_MedioPagoPos_ID);

	/** Get Z_MedioPagoPos ID	  */
	public int getZ_MedioPagoPos_ID();

    /** Column name Z_MPagoIdentPos_ID */
    public static final String COLUMNNAME_Z_MPagoIdentPos_ID = "Z_MPagoIdentPos_ID";

	/** Set Z_MPagoIdentPos ID	  */
	public void setZ_MPagoIdentPos_ID (int Z_MPagoIdentPos_ID);

	/** Get Z_MPagoIdentPos ID	  */
	public int getZ_MPagoIdentPos_ID();

}
