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

/** Generated Interface for Z_FormEfectivoCaja
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_FormEfectivoCaja 
{

    /** TableName=Z_FormEfectivoCaja */
    public static final String Table_Name = "Z_FormEfectivoCaja";

    /** AD_Table_ID=1000318 */
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

    /** Column name AmtSubtotal1 */
    public static final String COLUMNNAME_AmtSubtotal1 = "AmtSubtotal1";

	/** Set AmtSubtotal1.
	  * Monto subtotal uno
	  */
	public void setAmtSubtotal1(BigDecimal AmtSubtotal1);

	/** Get AmtSubtotal1.
	  * Monto subtotal uno
	  */
	public BigDecimal getAmtSubtotal1();

    /** Column name AmtSubtotal2 */
    public static final String COLUMNNAME_AmtSubtotal2 = "AmtSubtotal2";

	/** Set AmtSubtotal2.
	  * Monto subtotal dos
	  */
	public void setAmtSubtotal2(BigDecimal AmtSubtotal2);

	/** Get AmtSubtotal2.
	  * Monto subtotal dos
	  */
	public BigDecimal getAmtSubtotal2();

    /** Column name C_Currency_2_ID */
    public static final String COLUMNNAME_C_Currency_2_ID = "C_Currency_2_ID";

	/** Set C_Currency_2_ID.
	  * Moneda secundaria para procesos
	  */
	public void setC_Currency_2_ID(int C_Currency_2_ID);

	/** Get C_Currency_2_ID.
	  * Moneda secundaria para procesos
	  */
	public int getC_Currency_2_ID();

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID(int C_Currency_ID);

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

    /** Column name Z_FormEfectivoCaja_ID */
    public static final String COLUMNNAME_Z_FormEfectivoCaja_ID = "Z_FormEfectivoCaja_ID";

	/** Set Z_FormEfectivoCaja ID	  */
	public void setZ_FormEfectivoCaja_ID(int Z_FormEfectivoCaja_ID);

	/** Get Z_FormEfectivoCaja ID	  */
	public int getZ_FormEfectivoCaja_ID();

    /** Column name Z_FormEfectivoLin_ID */
    public static final String COLUMNNAME_Z_FormEfectivoLin_ID = "Z_FormEfectivoLin_ID";

	/** Set Z_FormEfectivoLin ID	  */
	public void setZ_FormEfectivoLin_ID(int Z_FormEfectivoLin_ID);

	/** Get Z_FormEfectivoLin ID	  */
	public int getZ_FormEfectivoLin_ID();

	public I_Z_FormEfectivoLin getZ_FormEfectivoLin() throws RuntimeException;
}
