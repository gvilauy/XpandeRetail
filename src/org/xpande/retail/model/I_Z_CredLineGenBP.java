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

/** Generated Interface for Z_CredLineGenBP
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1
 */
public interface I_Z_CredLineGenBP 
{

    /** TableName=Z_CredLineGenBP */
    public static final String Table_Name = "Z_CredLineGenBP";

    /** AD_Table_ID=1000381 */
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

    /** Column name AmtBase */
    public static final String COLUMNNAME_AmtBase = "AmtBase";

	/** Set AmtBase.
	  * Monto base
	  */
	public void setAmtBase (BigDecimal AmtBase);

	/** Get AmtBase.
	  * Monto base
	  */
	public BigDecimal getAmtBase();

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

    /** Column name CreditLimit */
    public static final String COLUMNNAME_CreditLimit = "CreditLimit";

	/** Set Credit limit.
	  * Amount of Credit allowed
	  */
	public void setCreditLimit (BigDecimal CreditLimit);

	/** Get Credit limit.
	  * Amount of Credit allowed
	  */
	public BigDecimal getCreditLimit();

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

    /** Column name Z_CreditLineCategory_ID */
    public static final String COLUMNNAME_Z_CreditLineCategory_ID = "Z_CreditLineCategory_ID";

	/** Set Categoría Linea Crédito.
	  * Categoría Linea Crédito
	  */
	public void setZ_CreditLineCategory_ID (int Z_CreditLineCategory_ID);

	/** Get Categoría Linea Crédito.
	  * Categoría Linea Crédito
	  */
	public int getZ_CreditLineCategory_ID();

	public I_Z_CreditLineCategory getZ_CreditLineCategory() throws RuntimeException;

    /** Column name Z_CredLineGenBP_ID */
    public static final String COLUMNNAME_Z_CredLineGenBP_ID = "Z_CredLineGenBP_ID";

	/** Set Z_CredLineGenBP ID	  */
	public void setZ_CredLineGenBP_ID (int Z_CredLineGenBP_ID);

	/** Get Z_CredLineGenBP ID	  */
	public int getZ_CredLineGenBP_ID();

    /** Column name Z_CredLineGen_ID */
    public static final String COLUMNNAME_Z_CredLineGen_ID = "Z_CredLineGen_ID";

	/** Set Generador Lineas Credito.
	  * Generador Lineas Credito
	  */
	public void setZ_CredLineGen_ID (int Z_CredLineGen_ID);

	/** Get Generador Lineas Credito.
	  * Generador Lineas Credito
	  */
	public int getZ_CredLineGen_ID();

	public I_Z_CredLineGen getZ_CredLineGen() throws RuntimeException;
}
