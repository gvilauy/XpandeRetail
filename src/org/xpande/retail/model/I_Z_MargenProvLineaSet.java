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

/** Generated Interface for Z_MargenProvLineaSet
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_MargenProvLineaSet 
{

    /** TableName=Z_MargenProvLineaSet */
    public static final String Table_Name = "Z_MargenProvLineaSet";

    /** AD_Table_ID=1000167 */
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

    /** Column name Margin */
    public static final String COLUMNNAME_Margin = "Margin";

	/** Set Margin %.
	  * Margin for a product as a percentage
	  */
	public void setMargin(BigDecimal Margin);

	/** Get Margin %.
	  * Margin for a product as a percentage
	  */
	public BigDecimal getMargin();

    /** Column name MarginTolerance */
    public static final String COLUMNNAME_MarginTolerance = "MarginTolerance";

	/** Set MarginTolerance.
	  * Porcentaje de tolerancia para márgenes
	  */
	public void setMarginTolerance(BigDecimal MarginTolerance);

	/** Get MarginTolerance.
	  * Porcentaje de tolerancia para márgenes
	  */
	public BigDecimal getMarginTolerance();

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

    /** Column name ProcessButton */
    public static final String COLUMNNAME_ProcessButton = "ProcessButton";

	/** Set ProcessButton	  */
	public void setProcessButton(String ProcessButton);

	/** Get ProcessButton	  */
	public String getProcessButton();

    /** Column name ProcessButton2 */
    public static final String COLUMNNAME_ProcessButton2 = "ProcessButton2";

	/** Set ProcessButton2.
	  * Botón de Proceso
	  */
	public void setProcessButton2(String ProcessButton2);

	/** Get ProcessButton2.
	  * Botón de Proceso
	  */
	public String getProcessButton2();

    /** Column name ProcessButton3 */
    public static final String COLUMNNAME_ProcessButton3 = "ProcessButton3";

	/** Set ProcessButton3.
	  * Botón para proceso
	  */
	public void setProcessButton3(String ProcessButton3);

	/** Get ProcessButton3.
	  * Botón para proceso
	  */
	public String getProcessButton3();

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

    /** Column name Z_MargenProvLinea_ID */
    public static final String COLUMNNAME_Z_MargenProvLinea_ID = "Z_MargenProvLinea_ID";

	/** Set Z_MargenProvLinea ID	  */
	public void setZ_MargenProvLinea_ID(int Z_MargenProvLinea_ID);

	/** Get Z_MargenProvLinea ID	  */
	public int getZ_MargenProvLinea_ID();

	public I_Z_MargenProvLinea getZ_MargenProvLinea() throws RuntimeException;

    /** Column name Z_MargenProvLineaSet_ID */
    public static final String COLUMNNAME_Z_MargenProvLineaSet_ID = "Z_MargenProvLineaSet_ID";

	/** Set Z_MargenProvLineaSet ID	  */
	public void setZ_MargenProvLineaSet_ID(int Z_MargenProvLineaSet_ID);

	/** Get Z_MargenProvLineaSet ID	  */
	public int getZ_MargenProvLineaSet_ID();

    /** Column name Z_PautaComercialSet_ID */
    public static final String COLUMNNAME_Z_PautaComercialSet_ID = "Z_PautaComercialSet_ID";

	/** Set Z_PautaComercialSet ID	  */
	public void setZ_PautaComercialSet_ID(int Z_PautaComercialSet_ID);

	/** Get Z_PautaComercialSet ID	  */
	public int getZ_PautaComercialSet_ID();

	public I_Z_PautaComercialSet getZ_PautaComercialSet() throws RuntimeException;
}
