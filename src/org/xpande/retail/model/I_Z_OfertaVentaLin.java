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

/** Generated Interface for Z_OfertaVentaLin
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_OfertaVentaLin 
{

    /** TableName=Z_OfertaVentaLin */
    public static final String Table_Name = "Z_OfertaVentaLin";

    /** AD_Table_ID=1000180 */
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

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription(String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

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

    /** Column name IsModified */
    public static final String COLUMNNAME_IsModified = "IsModified";

	/** Set Modified.
	  * The record is modified
	  */
	public void setIsModified(boolean IsModified);

	/** Get Modified.
	  * The record is modified
	  */
	public boolean isModified();

    /** Column name IsNew */
    public static final String COLUMNNAME_IsNew = "IsNew";

	/** Set IsNew	  */
	public void setIsNew(boolean IsNew);

	/** Get IsNew	  */
	public boolean isNew();

    /** Column name LastPriceSO */
    public static final String COLUMNNAME_LastPriceSO = "LastPriceSO";

	/** Set LastPriceSO.
	  * Ultimo precio de venta
	  */
	public void setLastPriceSO(BigDecimal LastPriceSO);

	/** Get LastPriceSO.
	  * Ultimo precio de venta
	  */
	public BigDecimal getLastPriceSO();

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

    /** Column name NewPriceSO */
    public static final String COLUMNNAME_NewPriceSO = "NewPriceSO";

	/** Set NewPriceSO.
	  * NewPriceSO
	  */
	public void setNewPriceSO(BigDecimal NewPriceSO);

	/** Get NewPriceSO.
	  * NewPriceSO
	  */
	public BigDecimal getNewPriceSO();

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

    /** Column name UPC */
    public static final String COLUMNNAME_UPC = "UPC";

	/** Set UPC/EAN.
	  * Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public void setUPC(String UPC);

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

    /** Column name Z_OfertaVenta_ID */
    public static final String COLUMNNAME_Z_OfertaVenta_ID = "Z_OfertaVenta_ID";

	/** Set Z_OfertaVenta ID	  */
	public void setZ_OfertaVenta_ID(int Z_OfertaVenta_ID);

	/** Get Z_OfertaVenta ID	  */
	public int getZ_OfertaVenta_ID();

	public I_Z_OfertaVenta getZ_OfertaVenta() throws RuntimeException;

    /** Column name Z_OfertaVentaLin_ID */
    public static final String COLUMNNAME_Z_OfertaVentaLin_ID = "Z_OfertaVentaLin_ID";

	/** Set Z_OfertaVentaLin ID	  */
	public void setZ_OfertaVentaLin_ID(int Z_OfertaVentaLin_ID);

	/** Get Z_OfertaVentaLin ID	  */
	public int getZ_OfertaVentaLin_ID();
}
