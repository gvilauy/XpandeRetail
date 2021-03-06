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

/** Generated Interface for Z_LineaProductoSocio
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_LineaProductoSocio 
{

    /** TableName=Z_LineaProductoSocio */
    public static final String Table_Name = "Z_LineaProductoSocio";

    /** AD_Table_ID=1000046 */
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

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID(int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_BPartnerRelation_ID */
    public static final String COLUMNNAME_C_BPartnerRelation_ID = "C_BPartnerRelation_ID";

	/** Set Related Partner.
	  * Related Business Partner
	  */
	public void setC_BPartnerRelation_ID(int C_BPartnerRelation_ID);

	/** Get Related Partner.
	  * Related Business Partner
	  */
	public int getC_BPartnerRelation_ID();

	public I_C_BPartner getC_BPartnerRelation() throws RuntimeException;

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

    /** Column name IsLockedPO */
    public static final String COLUMNNAME_IsLockedPO = "IsLockedPO";

	/** Set IsLockedPO.
	  * Si esta bloqueado para compras o no
	  */
	public void setIsLockedPO(boolean IsLockedPO);

	/** Get IsLockedPO.
	  * Si esta bloqueado para compras o no
	  */
	public boolean isLockedPO();

    /** Column name IsOwn */
    public static final String COLUMNNAME_IsOwn = "IsOwn";

	/** Set IsOwn.
	  * Si le pertenece o no
	  */
	public void setIsOwn(boolean IsOwn);

	/** Get IsOwn.
	  * Si le pertenece o no
	  */
	public boolean isOwn();

    /** Column name M_PriceList_ID */
    public static final String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/** Set Price List.
	  * Unique identifier of a Price List
	  */
	public void setM_PriceList_ID(int M_PriceList_ID);

	/** Get Price List.
	  * Unique identifier of a Price List
	  */
	public int getM_PriceList_ID();

	public I_M_PriceList getM_PriceList() throws RuntimeException;

    /** Column name ProcessButton */
    public static final String COLUMNNAME_ProcessButton = "ProcessButton";

	/** Set ProcessButton	  */
	public void setProcessButton(String ProcessButton);

	/** Get ProcessButton	  */
	public String getProcessButton();

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

    /** Column name Z_LineaProductoGral_ID */
    public static final String COLUMNNAME_Z_LineaProductoGral_ID = "Z_LineaProductoGral_ID";

	/** Set Z_LineaProductoGral ID	  */
	public void setZ_LineaProductoGral_ID(int Z_LineaProductoGral_ID);

	/** Get Z_LineaProductoGral ID	  */
	public int getZ_LineaProductoGral_ID();

	public I_Z_LineaProductoGral getZ_LineaProductoGral() throws RuntimeException;

    /** Column name Z_LineaProductoSocio_ID */
    public static final String COLUMNNAME_Z_LineaProductoSocio_ID = "Z_LineaProductoSocio_ID";

	/** Set Z_LineaProductoSocio ID	  */
	public void setZ_LineaProductoSocio_ID(int Z_LineaProductoSocio_ID);

	/** Get Z_LineaProductoSocio ID	  */
	public int getZ_LineaProductoSocio_ID();

    /** Column name Z_PautaComercial_ID */
    public static final String COLUMNNAME_Z_PautaComercial_ID = "Z_PautaComercial_ID";

	/** Set Z_PautaComercial ID	  */
	public void setZ_PautaComercial_ID(int Z_PautaComercial_ID);

	/** Get Z_PautaComercial ID	  */
	public int getZ_PautaComercial_ID();

	public I_Z_PautaComercial getZ_PautaComercial() throws RuntimeException;
}
