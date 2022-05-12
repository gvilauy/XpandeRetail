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

/** Generated Interface for Z_GeneraAstoVtaSumMP
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1
 */
public interface I_Z_GeneraAstoVtaSumMP 
{

    /** TableName=Z_GeneraAstoVtaSumMP */
    public static final String Table_Name = "Z_GeneraAstoVtaSumMP";

    /** AD_Table_ID=1000299 */
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

    /** Column name AmtTotal */
    public static final String COLUMNNAME_AmtTotal = "AmtTotal";

	/** Set AmtTotal.
	  * Monto total
	  */
	public void setAmtTotal (BigDecimal AmtTotal);

	/** Get AmtTotal.
	  * Monto total
	  */
	public BigDecimal getAmtTotal();

    /** Column name AmtTotal1 */
    public static final String COLUMNNAME_AmtTotal1 = "AmtTotal1";

	/** Set AmtTotal1.
	  * Monto total uno
	  */
	public void setAmtTotal1 (BigDecimal AmtTotal1);

	/** Get AmtTotal1.
	  * Monto total uno
	  */
	public BigDecimal getAmtTotal1();

    /** Column name AmtTotal2 */
    public static final String COLUMNNAME_AmtTotal2 = "AmtTotal2";

	/** Set AmtTotal2.
	  * Monto total dos
	  */
	public void setAmtTotal2 (BigDecimal AmtTotal2);

	/** Get AmtTotal2.
	  * Monto total dos
	  */
	public BigDecimal getAmtTotal2();

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

    /** Column name C_Currency_2_ID */
    public static final String COLUMNNAME_C_Currency_2_ID = "C_Currency_2_ID";

	/** Set C_Currency_2_ID.
	  * Moneda secundaria para procesos
	  */
	public void setC_Currency_2_ID (int C_Currency_2_ID);

	/** Get C_Currency_2_ID.
	  * Moneda secundaria para procesos
	  */
	public int getC_Currency_2_ID();

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

    /** Column name ChangeAmt */
    public static final String COLUMNNAME_ChangeAmt = "ChangeAmt";

	/** Set ChangeAmt	  */
	public void setChangeAmt (BigDecimal ChangeAmt);

	/** Get ChangeAmt	  */
	public BigDecimal getChangeAmt();

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

    /** Column name CodTipoLineaPOS */
    public static final String COLUMNNAME_CodTipoLineaPOS = "CodTipoLineaPOS";

	/** Set CodTipoLineaPOS.
	  * Código de tipo de linea para POS en Retail
	  */
	public void setCodTipoLineaPOS (String CodTipoLineaPOS);

	/** Get CodTipoLineaPOS.
	  * Código de tipo de linea para POS en Retail
	  */
	public String getCodTipoLineaPOS();

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

    /** Column name CurrencyRate */
    public static final String COLUMNNAME_CurrencyRate = "CurrencyRate";

	/** Set Rate.
	  * Currency Conversion Rate
	  */
	public void setCurrencyRate (BigDecimal CurrencyRate);

	/** Get Rate.
	  * Currency Conversion Rate
	  */
	public BigDecimal getCurrencyRate();

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

    /** Column name nomprodtarjeta */
    public static final String COLUMNNAME_nomprodtarjeta = "nomprodtarjeta";

	/** Set nomprodtarjeta	  */
	public void setnomprodtarjeta (String nomprodtarjeta);

	/** Get nomprodtarjeta	  */
	public String getnomprodtarjeta();

    /** Column name NomTipoLineaPOS */
    public static final String COLUMNNAME_NomTipoLineaPOS = "NomTipoLineaPOS";

	/** Set NomTipoLineaPOS.
	  * Nombre de tipo de linea POS para Retail
	  */
	public void setNomTipoLineaPOS (String NomTipoLineaPOS);

	/** Get NomTipoLineaPOS.
	  * Nombre de tipo de linea POS para Retail
	  */
	public String getNomTipoLineaPOS();

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

    /** Column name Z_GeneraAstoVta_ID */
    public static final String COLUMNNAME_Z_GeneraAstoVta_ID = "Z_GeneraAstoVta_ID";

	/** Set Z_GeneraAstoVta ID	  */
	public void setZ_GeneraAstoVta_ID (int Z_GeneraAstoVta_ID);

	/** Get Z_GeneraAstoVta ID	  */
	public int getZ_GeneraAstoVta_ID();

	public I_Z_GeneraAstoVta getZ_GeneraAstoVta() throws RuntimeException;

    /** Column name Z_GeneraAstoVtaSumMP_ID */
    public static final String COLUMNNAME_Z_GeneraAstoVtaSumMP_ID = "Z_GeneraAstoVtaSumMP_ID";

	/** Set Z_GeneraAstoVtaSumMP ID	  */
	public void setZ_GeneraAstoVtaSumMP_ID (int Z_GeneraAstoVtaSumMP_ID);

	/** Get Z_GeneraAstoVtaSumMP ID	  */
	public int getZ_GeneraAstoVtaSumMP_ID();

    /** Column name Z_MedioPago_ID */
    public static final String COLUMNNAME_Z_MedioPago_ID = "Z_MedioPago_ID";

	/** Set Z_MedioPago ID	  */
	public void setZ_MedioPago_ID (int Z_MedioPago_ID);

	/** Get Z_MedioPago ID	  */
	public int getZ_MedioPago_ID();

    /** Column name Z_MedioPagoIdent_ID */
    public static final String COLUMNNAME_Z_MedioPagoIdent_ID = "Z_MedioPagoIdent_ID";

	/** Set Z_MedioPagoIdent ID	  */
	public void setZ_MedioPagoIdent_ID (int Z_MedioPagoIdent_ID);

	/** Get Z_MedioPagoIdent ID	  */
	public int getZ_MedioPagoIdent_ID();
}
