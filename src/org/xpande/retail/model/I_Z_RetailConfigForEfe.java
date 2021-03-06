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

/** Generated Interface for Z_RetailConfigForEfe
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_RetailConfigForEfe 
{

    /** TableName=Z_RetailConfigForEfe */
    public static final String Table_Name = "Z_RetailConfigForEfe";

    /** AD_Table_ID=1000308 */
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

    /** Column name AfectaSaldo */
    public static final String COLUMNNAME_AfectaSaldo = "AfectaSaldo";

	/** Set AfectaSaldo.
	  * Si afecta o no saldo
	  */
	public void setAfectaSaldo (boolean AfectaSaldo);

	/** Get AfectaSaldo.
	  * Si afecta o no saldo
	  */
	public boolean isAfectaSaldo();

    /** Column name AplicaF01 */
    public static final String COLUMNNAME_AplicaF01 = "AplicaF01";

	/** Set AplicaF01.
	  * Si aplica o no al Formulario F01 en Retail
	  */
	public void setAplicaF01 (boolean AplicaF01);

	/** Get AplicaF01.
	  * Si aplica o no al Formulario F01 en Retail
	  */
	public boolean isAplicaF01();

    /** Column name AplicaF02 */
    public static final String COLUMNNAME_AplicaF02 = "AplicaF02";

	/** Set AplicaF02.
	  * Si aplica o no al Formulario F02 en Retail
	  */
	public void setAplicaF02 (boolean AplicaF02);

	/** Get AplicaF02.
	  * Si aplica o no al Formulario F02 en Retail
	  */
	public boolean isAplicaF02();

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

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

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

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public void setSeqNo (int SeqNo);

	/** Get Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public int getSeqNo();

    /** Column name TieneCaja */
    public static final String COLUMNNAME_TieneCaja = "TieneCaja";

	/** Set TieneCaja.
	  * Si requiere o no una caja asociada
	  */
	public void setTieneCaja (boolean TieneCaja);

	/** Get TieneCaja.
	  * Si requiere o no una caja asociada
	  */
	public boolean isTieneCaja();

    /** Column name TipoConceptoForEfe */
    public static final String COLUMNNAME_TipoConceptoForEfe = "TipoConceptoForEfe";

	/** Set TipoConceptoForEfe.
	  * Tipo de concepto de formulario de movimientos de efectivo en Retail
	  */
	public void setTipoConceptoForEfe (String TipoConceptoForEfe);

	/** Get TipoConceptoForEfe.
	  * Tipo de concepto de formulario de movimientos de efectivo en Retail
	  */
	public String getTipoConceptoForEfe();

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

    /** Column name Z_RetailConfigForEfe_ID */
    public static final String COLUMNNAME_Z_RetailConfigForEfe_ID = "Z_RetailConfigForEfe_ID";

	/** Set Z_RetailConfigForEfe ID	  */
	public void setZ_RetailConfigForEfe_ID (int Z_RetailConfigForEfe_ID);

	/** Get Z_RetailConfigForEfe ID	  */
	public int getZ_RetailConfigForEfe_ID();

    /** Column name Z_RetailConfig_ID */
    public static final String COLUMNNAME_Z_RetailConfig_ID = "Z_RetailConfig_ID";

	/** Set Z_RetailConfig ID	  */
	public void setZ_RetailConfig_ID (int Z_RetailConfig_ID);

	/** Get Z_RetailConfig ID	  */
	public int getZ_RetailConfig_ID();

	public I_Z_RetailConfig getZ_RetailConfig() throws RuntimeException;
}
