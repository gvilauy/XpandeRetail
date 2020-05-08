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

/** Generated Interface for Z_AstoVtaRecMPLinSC
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_AstoVtaRecMPLinSC 
{

    /** TableName=Z_AstoVtaRecMPLinSC */
    public static final String Table_Name = "Z_AstoVtaRecMPLinSC";

    /** AD_Table_ID=1000342 */
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

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public I_C_Currency getC_Currency() throws RuntimeException;

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
	public void setDateTrx (Timestamp DateTrx);

	/** Get Transaction Date.
	  * Transaction Date
	  */
	public Timestamp getDateTrx();

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

    /** Column name SC_CodigoCaja */
    public static final String COLUMNNAME_SC_CodigoCaja = "SC_CodigoCaja";

	/** Set SC_CodigoCaja	  */
	public void setSC_CodigoCaja (int SC_CodigoCaja);

	/** Get SC_CodigoCaja	  */
	public int getSC_CodigoCaja();

    /** Column name SC_CodigoMoneda */
    public static final String COLUMNNAME_SC_CodigoMoneda = "SC_CodigoMoneda";

	/** Set SC_CodigoMoneda	  */
	public void setSC_CodigoMoneda (String SC_CodigoMoneda);

	/** Get SC_CodigoMoneda	  */
	public String getSC_CodigoMoneda();

    /** Column name SC_CotizacionVenta */
    public static final String COLUMNNAME_SC_CotizacionVenta = "SC_CotizacionVenta";

	/** Set SC_CotizacionVenta	  */
	public void setSC_CotizacionVenta (BigDecimal SC_CotizacionVenta);

	/** Get SC_CotizacionVenta	  */
	public BigDecimal getSC_CotizacionVenta();

    /** Column name SC_CuponCancelado */
    public static final String COLUMNNAME_SC_CuponCancelado = "SC_CuponCancelado";

	/** Set SC_CuponCancelado	  */
	public void setSC_CuponCancelado (boolean SC_CuponCancelado);

	/** Get SC_CuponCancelado	  */
	public boolean isSC_CuponCancelado();

    /** Column name SC_DescripcionCFE */
    public static final String COLUMNNAME_SC_DescripcionCFE = "SC_DescripcionCFE";

	/** Set SC_DescripcionCFE.
	  * Descripcion CFE Scanntech
	  */
	public void setSC_DescripcionCFE (String SC_DescripcionCFE);

	/** Get SC_DescripcionCFE.
	  * Descripcion CFE Scanntech
	  */
	public String getSC_DescripcionCFE();

    /** Column name SC_Importe */
    public static final String COLUMNNAME_SC_Importe = "SC_Importe";

	/** Set SC_Importe	  */
	public void setSC_Importe (BigDecimal SC_Importe);

	/** Get SC_Importe	  */
	public BigDecimal getSC_Importe();

    /** Column name SC_NumeroMov */
    public static final String COLUMNNAME_SC_NumeroMov = "SC_NumeroMov";

	/** Set SC_NumeroMov	  */
	public void setSC_NumeroMov (String SC_NumeroMov);

	/** Get SC_NumeroMov	  */
	public String getSC_NumeroMov();

    /** Column name SC_NumeroOperacion */
    public static final String COLUMNNAME_SC_NumeroOperacion = "SC_NumeroOperacion";

	/** Set SC_NumeroOperacion	  */
	public void setSC_NumeroOperacion (String SC_NumeroOperacion);

	/** Get SC_NumeroOperacion	  */
	public String getSC_NumeroOperacion();

    /** Column name SC_NumeroTarjeta */
    public static final String COLUMNNAME_SC_NumeroTarjeta = "SC_NumeroTarjeta";

	/** Set SC_NumeroTarjeta	  */
	public void setSC_NumeroTarjeta (String SC_NumeroTarjeta);

	/** Get SC_NumeroTarjeta	  */
	public String getSC_NumeroTarjeta();

    /** Column name SC_SerieCfe */
    public static final String COLUMNNAME_SC_SerieCfe = "SC_SerieCfe";

	/** Set SC_SerieCfe	  */
	public void setSC_SerieCfe (String SC_SerieCfe);

	/** Get SC_SerieCfe	  */
	public String getSC_SerieCfe();

    /** Column name SC_TipoCfe */
    public static final String COLUMNNAME_SC_TipoCfe = "SC_TipoCfe";

	/** Set SC_TipoCfe	  */
	public void setSC_TipoCfe (int SC_TipoCfe);

	/** Get SC_TipoCfe	  */
	public int getSC_TipoCfe();

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
	public void setUUID (String UUID);

	/** Get Immutable Universally Unique Identifier.
	  * Immutable Universally Unique Identifier
	  */
	public String getUUID();

    /** Column name Z_AstoVtaRecMP_ID */
    public static final String COLUMNNAME_Z_AstoVtaRecMP_ID = "Z_AstoVtaRecMP_ID";

	/** Set Z_AstoVtaRecMP ID	  */
	public void setZ_AstoVtaRecMP_ID (int Z_AstoVtaRecMP_ID);

	/** Get Z_AstoVtaRecMP ID	  */
	public int getZ_AstoVtaRecMP_ID();

	public I_Z_AstoVtaRecMP getZ_AstoVtaRecMP() throws RuntimeException;

    /** Column name Z_AstoVtaRecMPLinSC_ID */
    public static final String COLUMNNAME_Z_AstoVtaRecMPLinSC_ID = "Z_AstoVtaRecMPLinSC_ID";

	/** Set Z_AstoVtaRecMPLinSC ID	  */
	public void setZ_AstoVtaRecMPLinSC_ID (int Z_AstoVtaRecMPLinSC_ID);

	/** Get Z_AstoVtaRecMPLinSC ID	  */
	public int getZ_AstoVtaRecMPLinSC_ID();

    /** Column name Z_GeneraAstoVtaDetMPSC_ID */
    public static final String COLUMNNAME_Z_GeneraAstoVtaDetMPSC_ID = "Z_GeneraAstoVtaDetMPSC_ID";

	/** Set Z_GeneraAstoVtaDetMPSC ID	  */
	public void setZ_GeneraAstoVtaDetMPSC_ID (int Z_GeneraAstoVtaDetMPSC_ID);

	/** Get Z_GeneraAstoVtaDetMPSC ID	  */
	public int getZ_GeneraAstoVtaDetMPSC_ID();

	public I_Z_GeneraAstoVtaDetMPSC getZ_GeneraAstoVtaDetMPSC() throws RuntimeException;

    /** Column name Z_StechCreditos_ID */
    public static final String COLUMNNAME_Z_StechCreditos_ID = "Z_StechCreditos_ID";

	/** Set Z_StechCreditos ID	  */
	public void setZ_StechCreditos_ID (int Z_StechCreditos_ID);

	/** Get Z_StechCreditos ID	  */
	public int getZ_StechCreditos_ID();

    /** Column name Z_StechMedioPago_ID */
    public static final String COLUMNNAME_Z_StechMedioPago_ID = "Z_StechMedioPago_ID";

	/** Set Z_StechMedioPago ID	  */
	public void setZ_StechMedioPago_ID (int Z_StechMedioPago_ID);

	/** Get Z_StechMedioPago ID	  */
	public int getZ_StechMedioPago_ID();
}
