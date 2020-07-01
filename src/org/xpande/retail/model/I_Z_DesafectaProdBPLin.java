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

/** Generated Interface for Z_DesafectaProdBPLin
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_DesafectaProdBPLin 
{

    /** TableName=Z_DesafectaProdBPLin */
    public static final String Table_Name = "Z_DesafectaProdBPLin";

    /** AD_Table_ID=1000358 */
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
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name LineaProdSocio_ID_To */
    public static final String COLUMNNAME_LineaProdSocio_ID_To = "LineaProdSocio_ID_To";

	/** Set LineaProdSocio_ID_To.
	  * Linea de productos destino de socios de negocio en Retail 
	  */
	public void setLineaProdSocio_ID_To (int LineaProdSocio_ID_To);

	/** Get LineaProdSocio_ID_To.
	  * Linea de productos destino de socios de negocio en Retail 
	  */
	public int getLineaProdSocio_ID_To();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public I_M_Product getM_Product() throws RuntimeException;

    /** Column name UPC */
    public static final String COLUMNNAME_UPC = "UPC";

	/** Set UPC/EAN.
	  * Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public void setUPC (String UPC);

	/** Get UPC/EAN.
	  * Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public String getUPC();

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

    /** Column name VendorProductNo */
    public static final String COLUMNNAME_VendorProductNo = "VendorProductNo";

	/** Set Partner Product Key.
	  * Product Key of the Business Partner
	  */
	public void setVendorProductNo (String VendorProductNo);

	/** Get Partner Product Key.
	  * Product Key of the Business Partner
	  */
	public String getVendorProductNo();

    /** Column name Z_DesafectaProdBP_ID */
    public static final String COLUMNNAME_Z_DesafectaProdBP_ID = "Z_DesafectaProdBP_ID";

	/** Set Z_DesafectaProdBP ID	  */
	public void setZ_DesafectaProdBP_ID (int Z_DesafectaProdBP_ID);

	/** Get Z_DesafectaProdBP ID	  */
	public int getZ_DesafectaProdBP_ID();

	public I_Z_DesafectaProdBP getZ_DesafectaProdBP() throws RuntimeException;

    /** Column name Z_DesafectaProdBPLin_ID */
    public static final String COLUMNNAME_Z_DesafectaProdBPLin_ID = "Z_DesafectaProdBPLin_ID";

	/** Set Z_DesafectaProdBPLin ID	  */
	public void setZ_DesafectaProdBPLin_ID (int Z_DesafectaProdBPLin_ID);

	/** Get Z_DesafectaProdBPLin ID	  */
	public int getZ_DesafectaProdBPLin_ID();

    /** Column name Z_ProductoFamilia_ID */
    public static final String COLUMNNAME_Z_ProductoFamilia_ID = "Z_ProductoFamilia_ID";

	/** Set Z_ProductoFamilia ID	  */
	public void setZ_ProductoFamilia_ID (int Z_ProductoFamilia_ID);

	/** Get Z_ProductoFamilia ID	  */
	public int getZ_ProductoFamilia_ID();

	public I_Z_ProductoFamilia getZ_ProductoFamilia() throws RuntimeException;

    /** Column name Z_ProductoRubro_ID */
    public static final String COLUMNNAME_Z_ProductoRubro_ID = "Z_ProductoRubro_ID";

	/** Set Z_ProductoRubro ID	  */
	public void setZ_ProductoRubro_ID (int Z_ProductoRubro_ID);

	/** Get Z_ProductoRubro ID	  */
	public int getZ_ProductoRubro_ID();

	public I_Z_ProductoRubro getZ_ProductoRubro() throws RuntimeException;

    /** Column name Z_ProductoSeccion_ID */
    public static final String COLUMNNAME_Z_ProductoSeccion_ID = "Z_ProductoSeccion_ID";

	/** Set Z_ProductoSeccion ID	  */
	public void setZ_ProductoSeccion_ID (int Z_ProductoSeccion_ID);

	/** Get Z_ProductoSeccion ID	  */
	public int getZ_ProductoSeccion_ID();

	public I_Z_ProductoSeccion getZ_ProductoSeccion() throws RuntimeException;

    /** Column name Z_ProductoSocio_ID */
    public static final String COLUMNNAME_Z_ProductoSocio_ID = "Z_ProductoSocio_ID";

	/** Set Z_ProductoSocio ID	  */
	public void setZ_ProductoSocio_ID (int Z_ProductoSocio_ID);

	/** Get Z_ProductoSocio ID	  */
	public int getZ_ProductoSocio_ID();

    /** Column name Z_ProductoSubfamilia_ID */
    public static final String COLUMNNAME_Z_ProductoSubfamilia_ID = "Z_ProductoSubfamilia_ID";

	/** Set Z_ProductoSubfamilia ID	  */
	public void setZ_ProductoSubfamilia_ID (int Z_ProductoSubfamilia_ID);

	/** Get Z_ProductoSubfamilia ID	  */
	public int getZ_ProductoSubfamilia_ID();

	public I_Z_ProductoSubfamilia getZ_ProductoSubfamilia() throws RuntimeException;
}
