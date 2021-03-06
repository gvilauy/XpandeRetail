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

/** Generated Interface for Z_RetailConfig
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_RetailConfig 
{

    /** TableName=Z_RetailConfig */
    public static final String Table_Name = "Z_RetailConfig";

    /** AD_Table_ID=1000134 */
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

    /** Column name CompletaRecepcion */
    public static final String COLUMNNAME_CompletaRecepcion = "CompletaRecepcion";

	/** Set CompletaRecepcion.
	  * Si completa o no recepcion de mercadería de manera automatica
	  */
	public void setCompletaRecepcion(boolean CompletaRecepcion);

	/** Get CompletaRecepcion.
	  * Si completa o no recepcion de mercadería de manera automatica
	  */
	public boolean isCompletaRecepcion();

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

    /** Column name C_Tax_ID */
    public static final String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/** Set Tax.
	  * Tax identifier
	  */
	public void setC_Tax_ID(int C_Tax_ID);

	/** Get Tax.
	  * Tax identifier
	  */
	public int getC_Tax_ID();

	public I_C_Tax getC_Tax() throws RuntimeException;

    /** Column name DefDocRemDifCant_ID */
    public static final String COLUMNNAME_DefDocRemDifCant_ID = "DefDocRemDifCant_ID";

	/** Set DefDocRemDifCant_ID.
	  * ID por defecto para documento de remito de diferencia de cantidad en módulo Retail
	  */
	public void setDefDocRemDifCant_ID(int DefDocRemDifCant_ID);

	/** Get DefDocRemDifCant_ID.
	  * ID por defecto para documento de remito de diferencia de cantidad en módulo Retail
	  */
	public int getDefDocRemDifCant_ID();

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

    /** Column name ToleraRemDifLin */
    public static final String COLUMNNAME_ToleraRemDifLin = "ToleraRemDifLin";

	/** Set ToleraRemDifLin.
	  * Porcentaje de tolerancia para lineas en la generación de Remitos por Diferencia
	  */
	public void setToleraRemDifLin(BigDecimal ToleraRemDifLin);

	/** Get ToleraRemDifLin.
	  * Porcentaje de tolerancia para lineas en la generación de Remitos por Diferencia
	  */
	public BigDecimal getToleraRemDifLin();

    /** Column name ToleraRemDifTot */
    public static final String COLUMNNAME_ToleraRemDifTot = "ToleraRemDifTot";

	/** Set ToleraRemDifTot.
	  * Porcentaje de tolerancia para Total en la generación de Remitos por Diferencia
	  */
	public void setToleraRemDifTot(BigDecimal ToleraRemDifTot);

	/** Get ToleraRemDifTot.
	  * Porcentaje de tolerancia para Total en la generación de Remitos por Diferencia
	  */
	public BigDecimal getToleraRemDifTot();

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

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public void setValue(String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	public String getValue();

    /** Column name Z_RetailConfig_ID */
    public static final String COLUMNNAME_Z_RetailConfig_ID = "Z_RetailConfig_ID";

	/** Set Z_RetailConfig ID	  */
	public void setZ_RetailConfig_ID(int Z_RetailConfig_ID);

	/** Get Z_RetailConfig ID	  */
	public int getZ_RetailConfig_ID();
}
