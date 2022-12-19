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

/** Generated Interface for Z_RegularOfferLine
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1
 */
public interface I_Z_RegularOfferLine 
{

    /** TableName=Z_RegularOfferLine */
    public static final String Table_Name = "Z_RegularOfferLine";

    /** AD_Table_ID=1000441 */
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

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public I_C_Currency getC_Currency() throws RuntimeException;

    /** Column name C_Currency_ID_To */
    public static final String COLUMNNAME_C_Currency_ID_To = "C_Currency_ID_To";

	/** Set Currency To.
	  * Target currency
	  */
	public void setC_Currency_ID_To (int C_Currency_ID_To);

	/** Get Currency To.
	  * Target currency
	  */
	public int getC_Currency_ID_To();

	public I_C_Currency getC_Currency_To() throws RuntimeException;

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

    /** Column name CurrencyRate */
    public static final String COLUMNNAME_CurrencyRate = "CurrencyRate";

	/** Set Rate.
	  * Currency Conversion Rate
	  */
	public void setCurrencyRate (BigDecimal CurrencyRate);

	/** Get Rate.
	  * Currency Conversion Rate
	  */
	public BigDecimal getCurrencyRate();

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

    /** Column name IsDifferPricePO */
    public static final String COLUMNNAME_IsDifferPricePO = "IsDifferPricePO";

	/** Set Precio Diferencial Compra.
	  * Precio Diferencial Compra
	  */
	public void setIsDifferPricePO (boolean IsDifferPricePO);

	/** Get Precio Diferencial Compra.
	  * Precio Diferencial Compra
	  */
	public boolean isDifferPricePO();

    /** Column name IsDifferPriceSO */
    public static final String COLUMNNAME_IsDifferPriceSO = "IsDifferPriceSO";

	/** Set Precio Diferencial Venta.
	  * Precio Diferencial Venta
	  */
	public void setIsDifferPriceSO (boolean IsDifferPriceSO);

	/** Get Precio Diferencial Venta.
	  * Precio Diferencial Venta
	  */
	public boolean isDifferPriceSO();

    /** Column name IsManual */
    public static final String COLUMNNAME_IsManual = "IsManual";

	/** Set Manual.
	  * This is a manual process
	  */
	public void setIsManual (boolean IsManual);

	/** Get Manual.
	  * This is a manual process
	  */
	public boolean isManual();

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

    /** Column name M_PriceList_Version_ID */
    public static final String COLUMNNAME_M_PriceList_Version_ID = "M_PriceList_Version_ID";

	/** Set Price List Version.
	  * Identifies a unique instance of a Price List
	  */
	public void setM_PriceList_Version_ID (int M_PriceList_Version_ID);

	/** Get Price List Version.
	  * Identifies a unique instance of a Price List
	  */
	public int getM_PriceList_Version_ID();

	public I_M_PriceList_Version getM_PriceList_Version() throws RuntimeException;

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

    /** Column name NewPricePO */
    public static final String COLUMNNAME_NewPricePO = "NewPricePO";

	/** Set NewPricePO.
	  * Nuevo precio de compra
	  */
	public void setNewPricePO (BigDecimal NewPricePO);

	/** Get NewPricePO.
	  * Nuevo precio de compra
	  */
	public BigDecimal getNewPricePO();

    /** Column name NewPriceSO */
    public static final String COLUMNNAME_NewPriceSO = "NewPriceSO";

	/** Set NewPriceSO.
	  * NewPriceSO
	  */
	public void setNewPriceSO (BigDecimal NewPriceSO);

	/** Get NewPriceSO.
	  * NewPriceSO
	  */
	public BigDecimal getNewPriceSO();

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

    /** Column name Z_RegularOffer_ID */
    public static final String COLUMNNAME_Z_RegularOffer_ID = "Z_RegularOffer_ID";

	/** Set Z_RegularOffer ID	  */
	public void setZ_RegularOffer_ID (int Z_RegularOffer_ID);

	/** Get Z_RegularOffer ID	  */
	public int getZ_RegularOffer_ID();

	public I_Z_RegularOffer getZ_RegularOffer() throws RuntimeException;

    /** Column name Z_RegularOfferLine_ID */
    public static final String COLUMNNAME_Z_RegularOfferLine_ID = "Z_RegularOfferLine_ID";

	/** Set Z_RegularOfferLine ID	  */
	public void setZ_RegularOfferLine_ID (int Z_RegularOfferLine_ID);

	/** Get Z_RegularOfferLine ID	  */
	public int getZ_RegularOfferLine_ID();
}
