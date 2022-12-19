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

/** Generated Interface for Z_RegularOfferFile
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1
 */
public interface I_Z_RegularOfferFile 
{

    /** TableName=Z_RegularOfferFile */
    public static final String Table_Name = "Z_RegularOfferFile";

    /** AD_Table_ID=1000440 */
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

    /** Column name BreakValue */
    public static final String COLUMNNAME_BreakValue = "BreakValue";

	/** Set Break Value.
	  * Low Value of trade discount break level
	  */
	public void setBreakValue (BigDecimal BreakValue);

	/** Get Break Value.
	  * Low Value of trade discount break level
	  */
	public BigDecimal getBreakValue();

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

    /** Column name Discount */
    public static final String COLUMNNAME_Discount = "Discount";

	/** Set Discount %.
	  * Discount in percent
	  */
	public void setDiscount (BigDecimal Discount);

	/** Get Discount %.
	  * Discount in percent
	  */
	public BigDecimal getDiscount();

    /** Column name ErrorMsg */
    public static final String COLUMNNAME_ErrorMsg = "ErrorMsg";

	/** Set Error Msg	  */
	public void setErrorMsg (String ErrorMsg);

	/** Get Error Msg	  */
	public String getErrorMsg();

    /** Column name FileLineText */
    public static final String COLUMNNAME_FileLineText = "FileLineText";

	/** Set FileLineText	  */
	public void setFileLineText (String FileLineText);

	/** Get FileLineText	  */
	public String getFileLineText();

    /** Column name I_IsImported */
    public static final String COLUMNNAME_I_IsImported = "I_IsImported";

	/** Set Imported.
	  * Has this import been processed
	  */
	public void setI_IsImported (boolean I_IsImported);

	/** Get Imported.
	  * Has this import been processed
	  */
	public boolean isI_IsImported();

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

    /** Column name IsExclude */
    public static final String COLUMNNAME_IsExclude = "IsExclude";

	/** Set Exclude.
	  * Exclude access to the data - if not selected Include access to the data
	  */
	public void setIsExclude (boolean IsExclude);

	/** Get Exclude.
	  * Exclude access to the data - if not selected Include access to the data
	  */
	public boolean isExclude();

    /** Column name IsValid */
    public static final String COLUMNNAME_IsValid = "IsValid";

	/** Set Valid.
	  * Element is valid
	  */
	public void setIsValid (boolean IsValid);

	/** Get Valid.
	  * Element is valid
	  */
	public boolean isValid();

    /** Column name LineNo */
    public static final String COLUMNNAME_LineNo = "LineNo";

	/** Set Line.
	  * Line No
	  */
	public void setLineNo (int LineNo);

	/** Get Line.
	  * Line No
	  */
	public int getLineNo();

    /** Column name PricePO */
    public static final String COLUMNNAME_PricePO = "PricePO";

	/** Set PO Price.
	  * Price based on a purchase order
	  */
	public void setPricePO (BigDecimal PricePO);

	/** Get PO Price.
	  * Price based on a purchase order
	  */
	public BigDecimal getPricePO();

    /** Column name PriceSO */
    public static final String COLUMNNAME_PriceSO = "PriceSO";

	/** Set PriceSO.
	  * PriceSO
	  */
	public void setPriceSO (BigDecimal PriceSO);

	/** Get PriceSO.
	  * PriceSO
	  */
	public BigDecimal getPriceSO();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name ProdCode */
    public static final String COLUMNNAME_ProdCode = "ProdCode";

	/** Set ProdCode.
	  * ProdCode
	  */
	public void setProdCode (String ProdCode);

	/** Get ProdCode.
	  * ProdCode
	  */
	public String getProdCode();

    /** Column name ProdName */
    public static final String COLUMNNAME_ProdName = "ProdName";

	/** Set ProdName.
	  * ProdName
	  */
	public void setProdName (String ProdName);

	/** Get ProdName.
	  * ProdName
	  */
	public String getProdName();

    /** Column name PurchaseCurrency_ID */
    public static final String COLUMNNAME_PurchaseCurrency_ID = "PurchaseCurrency_ID";

	/** Set Moneda Compra.
	  * Moneda Compra
	  */
	public void setPurchaseCurrency_ID (int PurchaseCurrency_ID);

	/** Get Moneda Compra.
	  * Moneda Compra
	  */
	public int getPurchaseCurrency_ID();

	public I_C_Currency getPurchaseCurrency() throws RuntimeException;

    /** Column name QtyPromo */
    public static final String COLUMNNAME_QtyPromo = "QtyPromo";

	/** Set Cantidad Promoción.
	  * Cantidad Promoción
	  */
	public void setQtyPromo (BigDecimal QtyPromo);

	/** Get Cantidad Promoción.
	  * Cantidad Promoción
	  */
	public BigDecimal getQtyPromo();

    /** Column name SalesCurrency_ID */
    public static final String COLUMNNAME_SalesCurrency_ID = "SalesCurrency_ID";

	/** Set Moneda Venta.
	  * Moneda Venta
	  */
	public void setSalesCurrency_ID (int SalesCurrency_ID);

	/** Get Moneda Venta.
	  * Moneda Venta
	  */
	public int getSalesCurrency_ID();

	public I_C_Currency getSalesCurrency() throws RuntimeException;

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

    /** Column name Z_RegularOfferFile_ID */
    public static final String COLUMNNAME_Z_RegularOfferFile_ID = "Z_RegularOfferFile_ID";

	/** Set Z_RegularOfferFile ID	  */
	public void setZ_RegularOfferFile_ID (int Z_RegularOfferFile_ID);

	/** Get Z_RegularOfferFile ID	  */
	public int getZ_RegularOfferFile_ID();

    /** Column name Z_RegularOffer_ID */
    public static final String COLUMNNAME_Z_RegularOffer_ID = "Z_RegularOffer_ID";

	/** Set Z_RegularOffer ID	  */
	public void setZ_RegularOffer_ID (int Z_RegularOffer_ID);

	/** Get Z_RegularOffer ID	  */
	public int getZ_RegularOffer_ID();

	public I_Z_RegularOffer getZ_RegularOffer() throws RuntimeException;
}
