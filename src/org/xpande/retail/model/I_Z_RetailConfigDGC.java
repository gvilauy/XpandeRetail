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

/** Generated Interface for Z_RetailConfigDGC
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_RetailConfigDGC 
{

    /** TableName=Z_RetailConfigDGC */
    public static final String Table_Name = "Z_RetailConfigDGC";

    /** AD_Table_ID=1000164 */
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

    /** Column name AD_OrgTo_ID */
    public static final String COLUMNNAME_AD_OrgTo_ID = "AD_OrgTo_ID";

	/** Set Inter-Organization.
	  * Organization valid for intercompany documents
	  */
	public void setAD_OrgTo_ID(int AD_OrgTo_ID);

	/** Get Inter-Organization.
	  * Organization valid for intercompany documents
	  */
	public int getAD_OrgTo_ID();

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

    /** Column name DGC_Cod_Establecimiento */
    public static final String COLUMNNAME_DGC_Cod_Establecimiento = "DGC_Cod_Establecimiento";

	/** Set DGC_Cod_Establecimiento.
	  * Código de Establecimiento para DGC en módulo de Retail
	  */
	public void setDGC_Cod_Establecimiento(String DGC_Cod_Establecimiento);

	/** Get DGC_Cod_Establecimiento.
	  * Código de Establecimiento para DGC en módulo de Retail
	  */
	public String getDGC_Cod_Establecimiento();

    /** Column name DGC_ID_Establecimiento */
    public static final String COLUMNNAME_DGC_ID_Establecimiento = "DGC_ID_Establecimiento";

	/** Set DGC_ID_Establecimiento.
	  * Identificador de establecimiento para DBC en módulo de Retail
	  */
	public void setDGC_ID_Establecimiento(String DGC_ID_Establecimiento);

	/** Get DGC_ID_Establecimiento.
	  * Identificador de establecimiento para DBC en módulo de Retail
	  */
	public String getDGC_ID_Establecimiento();

    /** Column name DGC_MetodoPrecio */
    public static final String COLUMNNAME_DGC_MetodoPrecio = "DGC_MetodoPrecio";

	/** Set DGC_MetodoPrecio.
	  * Metodo para interface de precios de DGC en modulo de Retail
	  */
	public void setDGC_MetodoPrecio(String DGC_MetodoPrecio);

	/** Get DGC_MetodoPrecio.
	  * Metodo para interface de precios de DGC en modulo de Retail
	  */
	public String getDGC_MetodoPrecio();

    /** Column name DGC_MetodoProducto */
    public static final String COLUMNNAME_DGC_MetodoProducto = "DGC_MetodoProducto";

	/** Set DGC_MetodoProducto.
	  * Metodo para interface de productos de DGC en modulo de Retail
	  */
	public void setDGC_MetodoProducto(String DGC_MetodoProducto);

	/** Get DGC_MetodoProducto.
	  * Metodo para interface de productos de DGC en modulo de Retail
	  */
	public String getDGC_MetodoProducto();

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

    /** Column name Namespace */
    public static final String COLUMNNAME_Namespace = "Namespace";

	/** Set Namespace.
	  * Namespace en parametrizaciones de Web Services
	  */
	public void setNamespace(String Namespace);

	/** Get Namespace.
	  * Namespace en parametrizaciones de Web Services
	  */
	public String getNamespace();

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

    /** Column name WsdlURL */
    public static final String COLUMNNAME_WsdlURL = "WsdlURL";

	/** Set WsdlURL.
	  * URL para Wsdl en parametrizaciones de Web Services
	  */
	public void setWsdlURL(String WsdlURL);

	/** Get WsdlURL.
	  * URL para Wsdl en parametrizaciones de Web Services
	  */
	public String getWsdlURL();

    /** Column name Z_RetailConfigDGC_ID */
    public static final String COLUMNNAME_Z_RetailConfigDGC_ID = "Z_RetailConfigDGC_ID";

	/** Set Z_RetailConfigDGC ID	  */
	public void setZ_RetailConfigDGC_ID(int Z_RetailConfigDGC_ID);

	/** Get Z_RetailConfigDGC ID	  */
	public int getZ_RetailConfigDGC_ID();

    /** Column name Z_RetailConfig_ID */
    public static final String COLUMNNAME_Z_RetailConfig_ID = "Z_RetailConfig_ID";

	/** Set Z_RetailConfig ID	  */
	public void setZ_RetailConfig_ID(int Z_RetailConfig_ID);

	/** Get Z_RetailConfig ID	  */
	public int getZ_RetailConfig_ID();

	public org.xpande.retail.model.I_Z_RetailConfig getZ_RetailConfig() throws RuntimeException;
}
