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

/** Generated Interface for Z_ConfirmacionEtiquetaPrint
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_ConfirmacionEtiquetaPrint 
{

    /** TableName=Z_ConfirmacionEtiquetaPrint */
    public static final String Table_Name = "Z_ConfirmacionEtiquetaPrint";

    /** AD_Table_ID=1000076 */
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

    /** Column name C_Currency_ID_SO */
    public static final String COLUMNNAME_C_Currency_ID_SO = "C_Currency_ID_SO";

	/** Set C_Currency_ID_SO.
	  * Moneda de Venta
	  */
	public void setC_Currency_ID_SO(int C_Currency_ID_SO);

	/** Get C_Currency_ID_SO.
	  * Moneda de Venta
	  */
	public int getC_Currency_ID_SO();

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

    /** Column name DateValidSO */
    public static final String COLUMNNAME_DateValidSO = "DateValidSO";

	/** Set DateValidSO.
	  * Fecha Vigencia Venta
	  */
	public void setDateValidSO(Timestamp DateValidSO);

	/** Get DateValidSO.
	  * Fecha Vigencia Venta
	  */
	public Timestamp getDateValidSO();

    /** Column name Impresion_ID */
    public static final String COLUMNNAME_Impresion_ID = "Impresion_ID";

	/** Set Impresion_ID.
	  * ID general de impresión
	  */
	public void setImpresion_ID(int Impresion_ID);

	/** Get Impresion_ID.
	  * ID general de impresión
	  */
	public int getImpresion_ID();

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

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID(int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public I_M_Product getM_Product() throws RuntimeException;

    /** Column name PriceSO */
    public static final String COLUMNNAME_PriceSO = "PriceSO";

	/** Set PriceSO.
	  * PriceSO
	  */
	public void setPriceSO(BigDecimal PriceSO);

	/** Get PriceSO.
	  * PriceSO
	  */
	public BigDecimal getPriceSO();

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

    /** Column name Z_ConfirmacionEtiqueta_ID */
    public static final String COLUMNNAME_Z_ConfirmacionEtiqueta_ID = "Z_ConfirmacionEtiqueta_ID";

	/** Set Z_ConfirmacionEtiqueta ID	  */
	public void setZ_ConfirmacionEtiqueta_ID(int Z_ConfirmacionEtiqueta_ID);

	/** Get Z_ConfirmacionEtiqueta ID	  */
	public int getZ_ConfirmacionEtiqueta_ID();

	public I_Z_ConfirmacionEtiqueta getZ_ConfirmacionEtiqueta() throws RuntimeException;

    /** Column name Z_ConfirmacionEtiquetaPrint_ID */
    public static final String COLUMNNAME_Z_ConfirmacionEtiquetaPrint_ID = "Z_ConfirmacionEtiquetaPrint_ID";

	/** Set Z_ConfirmacionEtiquetaPrint ID	  */
	public void setZ_ConfirmacionEtiquetaPrint_ID(int Z_ConfirmacionEtiquetaPrint_ID);

	/** Get Z_ConfirmacionEtiquetaPrint ID	  */
	public int getZ_ConfirmacionEtiquetaPrint_ID();

    /** Column name Z_ImpresionEtiquetaSimple_ID */
    public static final String COLUMNNAME_Z_ImpresionEtiquetaSimple_ID = "Z_ImpresionEtiquetaSimple_ID";

	/** Set Z_ImpresionEtiquetaSimple ID	  */
	public void setZ_ImpresionEtiquetaSimple_ID(int Z_ImpresionEtiquetaSimple_ID);

	/** Get Z_ImpresionEtiquetaSimple ID	  */
	public int getZ_ImpresionEtiquetaSimple_ID();

	public I_Z_ImpresionEtiquetaSimple getZ_ImpresionEtiquetaSimple() throws RuntimeException;
}
