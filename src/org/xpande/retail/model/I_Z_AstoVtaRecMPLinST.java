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

/** Generated Interface for Z_AstoVtaRecMPLinST
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_AstoVtaRecMPLinST 
{

    /** TableName=Z_AstoVtaRecMPLinST */
    public static final String Table_Name = "Z_AstoVtaRecMPLinST";

    /** AD_Table_ID=1000326 */
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
	public void setAD_Org_ID(int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

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

    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/** Set Transaction Date.
	  * Transaction Date
	  */
	public void setDateTrx(Timestamp DateTrx);

	/** Get Transaction Date.
	  * Transaction Date
	  */
	public Timestamp getDateTrx();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive(boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName(String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name ST_Cambio */
    public static final String COLUMNNAME_ST_Cambio = "ST_Cambio";

	/** Set ST_Cambio	  */
	public void setST_Cambio(BigDecimal ST_Cambio);

	/** Get ST_Cambio	  */
	public BigDecimal getST_Cambio();

    /** Column name ST_CodigoCaja */
    public static final String COLUMNNAME_ST_CodigoCaja = "ST_CodigoCaja";

	/** Set ST_CodigoCaja	  */
	public void setST_CodigoCaja(String ST_CodigoCaja);

	/** Get ST_CodigoCaja	  */
	public String getST_CodigoCaja();

    /** Column name ST_CodigoCajera */
    public static final String COLUMNNAME_ST_CodigoCajera = "ST_CodigoCajera";

	/** Set ST_CodigoCajera	  */
	public void setST_CodigoCajera(String ST_CodigoCajera);

	/** Get ST_CodigoCajera	  */
	public String getST_CodigoCajera();

    /** Column name ST_CodigoCC */
    public static final String COLUMNNAME_ST_CodigoCC = "ST_CodigoCC";

	/** Set ST_CodigoCC	  */
	public void setST_CodigoCC(String ST_CodigoCC);

	/** Get ST_CodigoCC	  */
	public String getST_CodigoCC();

    /** Column name ST_CodigoMedioPago */
    public static final String COLUMNNAME_ST_CodigoMedioPago = "ST_CodigoMedioPago";

	/** Set ST_CodigoMedioPago	  */
	public void setST_CodigoMedioPago(String ST_CodigoMedioPago);

	/** Get ST_CodigoMedioPago	  */
	public String getST_CodigoMedioPago();

    /** Column name ST_CodigoMoneda */
    public static final String COLUMNNAME_ST_CodigoMoneda = "ST_CodigoMoneda";

	/** Set ST_CodigoMoneda	  */
	public void setST_CodigoMoneda(String ST_CodigoMoneda);

	/** Get ST_CodigoMoneda	  */
	public String getST_CodigoMoneda();

    /** Column name ST_DescripcionCFE */
    public static final String COLUMNNAME_ST_DescripcionCFE = "ST_DescripcionCFE";

	/** Set ST_DescripcionCFE	  */
	public void setST_DescripcionCFE(String ST_DescripcionCFE);

	/** Get ST_DescripcionCFE	  */
	public String getST_DescripcionCFE();

    /** Column name ST_DescuentoAfam */
    public static final String COLUMNNAME_ST_DescuentoAfam = "ST_DescuentoAfam";

	/** Set ST_DescuentoAfam.
	  * Atributo para Sisteco
	  */
	public void setST_DescuentoAfam(BigDecimal ST_DescuentoAfam);

	/** Get ST_DescuentoAfam.
	  * Atributo para Sisteco
	  */
	public BigDecimal getST_DescuentoAfam();

    /** Column name ST_MontoDescuentoLeyIVA */
    public static final String COLUMNNAME_ST_MontoDescuentoLeyIVA = "ST_MontoDescuentoLeyIVA";

	/** Set ST_MontoDescuentoLeyIVA	  */
	public void setST_MontoDescuentoLeyIVA(BigDecimal ST_MontoDescuentoLeyIVA);

	/** Get ST_MontoDescuentoLeyIVA	  */
	public BigDecimal getST_MontoDescuentoLeyIVA();

    /** Column name ST_NombreCC */
    public static final String COLUMNNAME_ST_NombreCC = "ST_NombreCC";

	/** Set ST_NombreCC	  */
	public void setST_NombreCC(String ST_NombreCC);

	/** Get ST_NombreCC	  */
	public String getST_NombreCC();

    /** Column name ST_NombreMedioPago */
    public static final String COLUMNNAME_ST_NombreMedioPago = "ST_NombreMedioPago";

	/** Set ST_NombreMedioPago.
	  * Atributo para interface con Sisteco
	  */
	public void setST_NombreMedioPago(String ST_NombreMedioPago);

	/** Get ST_NombreMedioPago.
	  * Atributo para interface con Sisteco
	  */
	public String getST_NombreMedioPago();

    /** Column name ST_NombreTarjeta */
    public static final String COLUMNNAME_ST_NombreTarjeta = "ST_NombreTarjeta";

	/** Set ST_NombreTarjeta	  */
	public void setST_NombreTarjeta(String ST_NombreTarjeta);

	/** Get ST_NombreTarjeta	  */
	public String getST_NombreTarjeta();

    /** Column name ST_NumeroTarjeta */
    public static final String COLUMNNAME_ST_NumeroTarjeta = "ST_NumeroTarjeta";

	/** Set ST_NumeroTarjeta	  */
	public void setST_NumeroTarjeta(String ST_NumeroTarjeta);

	/** Get ST_NumeroTarjeta	  */
	public String getST_NumeroTarjeta();

    /** Column name ST_NumeroTicket */
    public static final String COLUMNNAME_ST_NumeroTicket = "ST_NumeroTicket";

	/** Set ST_NumeroTicket	  */
	public void setST_NumeroTicket(String ST_NumeroTicket);

	/** Get ST_NumeroTicket	  */
	public String getST_NumeroTicket();

    /** Column name ST_TextoLey */
    public static final String COLUMNNAME_ST_TextoLey = "ST_TextoLey";

	/** Set ST_TextoLey	  */
	public void setST_TextoLey(String ST_TextoLey);

	/** Get ST_TextoLey	  */
	public String getST_TextoLey();

    /** Column name ST_TimestampTicket */
    public static final String COLUMNNAME_ST_TimestampTicket = "ST_TimestampTicket";

	/** Set ST_TimestampTicket	  */
	public void setST_TimestampTicket(Timestamp ST_TimestampTicket);

	/** Get ST_TimestampTicket	  */
	public Timestamp getST_TimestampTicket();

    /** Column name ST_TipoLinea */
    public static final String COLUMNNAME_ST_TipoLinea = "ST_TipoLinea";

	/** Set ST_TipoLinea	  */
	public void setST_TipoLinea(String ST_TipoLinea);

	/** Get ST_TipoLinea	  */
	public String getST_TipoLinea();

    /** Column name ST_TipoTarjetaCredito */
    public static final String COLUMNNAME_ST_TipoTarjetaCredito = "ST_TipoTarjetaCredito";

	/** Set ST_TipoTarjetaCredito	  */
	public void setST_TipoTarjetaCredito(String ST_TipoTarjetaCredito);

	/** Get ST_TipoTarjetaCredito	  */
	public String getST_TipoTarjetaCredito();

    /** Column name ST_TotalEntregado */
    public static final String COLUMNNAME_ST_TotalEntregado = "ST_TotalEntregado";

	/** Set ST_TotalEntregado	  */
	public void setST_TotalEntregado(BigDecimal ST_TotalEntregado);

	/** Get ST_TotalEntregado	  */
	public BigDecimal getST_TotalEntregado();

    /** Column name ST_TotalEntregadoMonedaRef */
    public static final String COLUMNNAME_ST_TotalEntregadoMonedaRef = "ST_TotalEntregadoMonedaRef";

	/** Set ST_TotalEntregadoMonedaRef	  */
	public void setST_TotalEntregadoMonedaRef(BigDecimal ST_TotalEntregadoMonedaRef);

	/** Get ST_TotalEntregadoMonedaRef	  */
	public BigDecimal getST_TotalEntregadoMonedaRef();

    /** Column name ST_TotalMPPagoMoneda */
    public static final String COLUMNNAME_ST_TotalMPPagoMoneda = "ST_TotalMPPagoMoneda";

	/** Set ST_TotalMPPagoMoneda	  */
	public void setST_TotalMPPagoMoneda(BigDecimal ST_TotalMPPagoMoneda);

	/** Get ST_TotalMPPagoMoneda	  */
	public BigDecimal getST_TotalMPPagoMoneda();

    /** Column name ST_TotalMPPagoMonedaRef */
    public static final String COLUMNNAME_ST_TotalMPPagoMonedaRef = "ST_TotalMPPagoMonedaRef";

	/** Set ST_TotalMPPagoMonedaRef	  */
	public void setST_TotalMPPagoMonedaRef(BigDecimal ST_TotalMPPagoMonedaRef);

	/** Get ST_TotalMPPagoMonedaRef	  */
	public BigDecimal getST_TotalMPPagoMonedaRef();

    /** Column name TotalAmt */
    public static final String COLUMNNAME_TotalAmt = "TotalAmt";

	/** Set Total Amount.
	  * Total Amount
	  */
	public void setTotalAmt(BigDecimal TotalAmt);

	/** Get Total Amount.
	  * Total Amount
	  */
	public BigDecimal getTotalAmt();

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

    /** Column name UUID */
    public static final String COLUMNNAME_UUID = "UUID";

	/** Set Immutable Universally Unique Identifier.
	  * Immutable Universally Unique Identifier
	  */
	public void setUUID(String UUID);

	/** Get Immutable Universally Unique Identifier.
	  * Immutable Universally Unique Identifier
	  */
	public String getUUID();

    /** Column name Z_AstoVtaRecMP_ID */
    public static final String COLUMNNAME_Z_AstoVtaRecMP_ID = "Z_AstoVtaRecMP_ID";

	/** Set Z_AstoVtaRecMP ID	  */
	public void setZ_AstoVtaRecMP_ID(int Z_AstoVtaRecMP_ID);

	/** Get Z_AstoVtaRecMP ID	  */
	public int getZ_AstoVtaRecMP_ID();

	public I_Z_AstoVtaRecMP getZ_AstoVtaRecMP() throws RuntimeException;

    /** Column name Z_AstoVtaRecMPLinST_ID */
    public static final String COLUMNNAME_Z_AstoVtaRecMPLinST_ID = "Z_AstoVtaRecMPLinST_ID";

	/** Set Z_AstoVtaRecMPLinST ID	  */
	public void setZ_AstoVtaRecMPLinST_ID(int Z_AstoVtaRecMPLinST_ID);

	/** Get Z_AstoVtaRecMPLinST ID	  */
	public int getZ_AstoVtaRecMPLinST_ID();

    /** Column name Z_GeneraAstoVtaDetMPST_ID */
    public static final String COLUMNNAME_Z_GeneraAstoVtaDetMPST_ID = "Z_GeneraAstoVtaDetMPST_ID";

	/** Set Z_GeneraAstoVtaDetMPST ID	  */
	public void setZ_GeneraAstoVtaDetMPST_ID(int Z_GeneraAstoVtaDetMPST_ID);

	/** Get Z_GeneraAstoVtaDetMPST ID	  */
	public int getZ_GeneraAstoVtaDetMPST_ID();

	public I_Z_GeneraAstoVtaDetMPST getZ_GeneraAstoVtaDetMPST() throws RuntimeException;

    /** Column name Z_SistecoMedioPago_ID */
    public static final String COLUMNNAME_Z_SistecoMedioPago_ID = "Z_SistecoMedioPago_ID";

	/** Set Z_SistecoMedioPago ID	  */
	public void setZ_SistecoMedioPago_ID(int Z_SistecoMedioPago_ID);

	/** Get Z_SistecoMedioPago ID	  */
	public int getZ_SistecoMedioPago_ID();

    /** Column name Z_SistecoTipoTarjeta_ID */
    public static final String COLUMNNAME_Z_SistecoTipoTarjeta_ID = "Z_SistecoTipoTarjeta_ID";

	/** Set Z_SistecoTipoTarjeta ID	  */
	public void setZ_SistecoTipoTarjeta_ID(int Z_SistecoTipoTarjeta_ID);

	/** Get Z_SistecoTipoTarjeta ID	  */
	public int getZ_SistecoTipoTarjeta_ID();
}
