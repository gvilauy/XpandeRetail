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

/** Generated Interface for Z_ProductoSocio
 *  @author Adempiere (generated) 
 *  @version Release 3.9.1
 */
public interface I_Z_ProductoSocio 
{

    /** TableName=Z_ProductoSocio */
    public static final String Table_Name = "Z_ProductoSocio";

    /** AD_Table_ID=1000045 */
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

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public I_C_BPartner getC_BPartner() throws RuntimeException;

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

    /** Column name C_Currency_ID_SO */
    public static final String COLUMNNAME_C_Currency_ID_SO = "C_Currency_ID_SO";

	/** Set C_Currency_ID_SO.
	  * Moneda de Venta
	  */
	public void setC_Currency_ID_SO (int C_Currency_ID_SO);

	/** Get C_Currency_ID_SO.
	  * Moneda de Venta
	  */
	public int getC_Currency_ID_SO();

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/** Get Invoice.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

	public I_C_Invoice getC_Invoice() throws RuntimeException;

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

    /** Column name DateInvoiced */
    public static final String COLUMNNAME_DateInvoiced = "DateInvoiced";

	/** Set Date Invoiced.
	  * Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced);

	/** Get Date Invoiced.
	  * Date printed on Invoice
	  */
	public Timestamp getDateInvoiced();

    /** Column name DateValidPO */
    public static final String COLUMNNAME_DateValidPO = "DateValidPO";

	/** Set DateValidPO.
	  * Fecha Vigencia Compra
	  */
	public void setDateValidPO (Timestamp DateValidPO);

	/** Get DateValidPO.
	  * Fecha Vigencia Compra
	  */
	public Timestamp getDateValidPO();

    /** Column name DateValidSO */
    public static final String COLUMNNAME_DateValidSO = "DateValidSO";

	/** Set DateValidSO.
	  * Fecha Vigencia Venta
	  */
	public void setDateValidSO (Timestamp DateValidSO);

	/** Get DateValidSO.
	  * Fecha Vigencia Venta
	  */
	public Timestamp getDateValidSO();

    /** Column name DistinctPricePO */
    public static final String COLUMNNAME_DistinctPricePO = "DistinctPricePO";

	/** Set DistinctPricePO	  */
	public void setDistinctPricePO (boolean DistinctPricePO);

	/** Get DistinctPricePO	  */
	public boolean isDistinctPricePO();

    /** Column name DistinctPriceSO */
    public static final String COLUMNNAME_DistinctPriceSO = "DistinctPriceSO";

	/** Set DistinctPriceSO	  */
	public void setDistinctPriceSO (boolean DistinctPriceSO);

	/** Get DistinctPriceSO	  */
	public boolean isDistinctPriceSO();

    /** Column name DtoFinFactura */
    public static final String COLUMNNAME_DtoFinFactura = "DtoFinFactura";

	/** Set DtoFinFactura.
	  * Descuento Financiero en Factura
	  */
	public void setDtoFinFactura (BigDecimal DtoFinFactura);

	/** Get DtoFinFactura.
	  * Descuento Financiero en Factura
	  */
	public BigDecimal getDtoFinFactura();

    /** Column name DtoFinPago */
    public static final String COLUMNNAME_DtoFinPago = "DtoFinPago";

	/** Set DtoFinPago.
	  * Descuento financiero al Pago
	  */
	public void setDtoFinPago (BigDecimal DtoFinPago);

	/** Get DtoFinPago.
	  * Descuento financiero al Pago
	  */
	public BigDecimal getDtoFinPago();

    /** Column name DtoNCPago */
    public static final String COLUMNNAME_DtoNCPago = "DtoNCPago";

	/** Set DtoNCPago.
	  * Descuento nota de crédito al pago
	  */
	public void setDtoNCPago (BigDecimal DtoNCPago);

	/** Get DtoNCPago.
	  * Descuento nota de crédito al pago
	  */
	public BigDecimal getDtoNCPago();

    /** Column name DtoOpeFactura */
    public static final String COLUMNNAME_DtoOpeFactura = "DtoOpeFactura";

	/** Set DtoOpeFactura.
	  * Descuentos operativos en factura
	  */
	public void setDtoOpeFactura (BigDecimal DtoOpeFactura);

	/** Get DtoOpeFactura.
	  * Descuentos operativos en factura
	  */
	public BigDecimal getDtoOpeFactura();

    /** Column name DtoRetorno */
    public static final String COLUMNNAME_DtoRetorno = "DtoRetorno";

	/** Set DtoRetorno	  */
	public void setDtoRetorno (BigDecimal DtoRetorno);

	/** Get DtoRetorno	  */
	public BigDecimal getDtoRetorno();

    /** Column name InvoiceNo */
    public static final String COLUMNNAME_InvoiceNo = "InvoiceNo";

	/** Set InvoiceNo	  */
	public void setInvoiceNo (String InvoiceNo);

	/** Get InvoiceNo	  */
	public String getInvoiceNo();

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

    /** Column name IsBonificable */
    public static final String COLUMNNAME_IsBonificable = "IsBonificable";

	/** Set IsBonificable.
	  * Es bonificable o no
	  */
	public void setIsBonificable (boolean IsBonificable);

	/** Get IsBonificable.
	  * Es bonificable o no
	  */
	public boolean isBonificable();

    /** Column name M_PriceList_ID */
    public static final String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/** Set Price List.
	  * Unique identifier of a Price List
	  */
	public void setM_PriceList_ID (int M_PriceList_ID);

	/** Get Price List.
	  * Unique identifier of a Price List
	  */
	public int getM_PriceList_ID();

	public I_M_PriceList getM_PriceList() throws RuntimeException;

    /** Column name M_PriceList_ID_SO */
    public static final String COLUMNNAME_M_PriceList_ID_SO = "M_PriceList_ID_SO";

	/** Set M_PriceList_ID_SO.
	  * Lista de Precios de Venta
	  */
	public void setM_PriceList_ID_SO (int M_PriceList_ID_SO);

	/** Get M_PriceList_ID_SO.
	  * Lista de Precios de Venta
	  */
	public int getM_PriceList_ID_SO();

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

    /** Column name M_PriceList_Version_ID_SO */
    public static final String COLUMNNAME_M_PriceList_Version_ID_SO = "M_PriceList_Version_ID_SO";

	/** Set M_PriceList_Version_ID_SO.
	  * Version de Lista de Precios de Venta
	  */
	public void setM_PriceList_Version_ID_SO (int M_PriceList_Version_ID_SO);

	/** Get M_PriceList_Version_ID_SO.
	  * Version de Lista de Precios de Venta
	  */
	public int getM_PriceList_Version_ID_SO();

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

    /** Column name PriceFinal */
    public static final String COLUMNNAME_PriceFinal = "PriceFinal";

	/** Set PriceFinal	  */
	public void setPriceFinal (BigDecimal PriceFinal);

	/** Get PriceFinal	  */
	public BigDecimal getPriceFinal();

    /** Column name PriceFinalMargin */
    public static final String COLUMNNAME_PriceFinalMargin = "PriceFinalMargin";

	/** Set PriceFinalMargin	  */
	public void setPriceFinalMargin (BigDecimal PriceFinalMargin);

	/** Get PriceFinalMargin	  */
	public BigDecimal getPriceFinalMargin();

    /** Column name PriceInvoiced */
    public static final String COLUMNNAME_PriceInvoiced = "PriceInvoiced";

	/** Set Price Invoiced.
	  * The priced invoiced to the customer (in the currency of the customer's AR price list) - 0 for default price
	  */
	public void setPriceInvoiced (BigDecimal PriceInvoiced);

	/** Get Price Invoiced.
	  * The priced invoiced to the customer (in the currency of the customer's AR price list) - 0 for default price
	  */
	public BigDecimal getPriceInvoiced();

    /** Column name PriceInvoicedMargin */
    public static final String COLUMNNAME_PriceInvoicedMargin = "PriceInvoicedMargin";

	/** Set PriceInvoicedMargin	  */
	public void setPriceInvoicedMargin (BigDecimal PriceInvoicedMargin);

	/** Get PriceInvoicedMargin	  */
	public BigDecimal getPriceInvoicedMargin();

    /** Column name PriceList */
    public static final String COLUMNNAME_PriceList = "PriceList";

	/** Set List Price.
	  * List Price
	  */
	public void setPriceList (BigDecimal PriceList);

	/** Get List Price.
	  * List Price
	  */
	public BigDecimal getPriceList();

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

    /** Column name PricePOMargin */
    public static final String COLUMNNAME_PricePOMargin = "PricePOMargin";

	/** Set PricePOMargin	  */
	public void setPricePOMargin (BigDecimal PricePOMargin);

	/** Get PricePOMargin	  */
	public BigDecimal getPricePOMargin();

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

    /** Column name TotalDiscountsFinal */
    public static final String COLUMNNAME_TotalDiscountsFinal = "TotalDiscountsFinal";

	/** Set TotalDiscountsFinal.
	  * Suma total de los porcentajes de descuentos de productos para pagos
	  */
	public void setTotalDiscountsFinal (BigDecimal TotalDiscountsFinal);

	/** Get TotalDiscountsFinal.
	  * Suma total de los porcentajes de descuentos de productos para pagos
	  */
	public BigDecimal getTotalDiscountsFinal();

    /** Column name TotalDiscountsPO */
    public static final String COLUMNNAME_TotalDiscountsPO = "TotalDiscountsPO";

	/** Set TotalDiscountsPO.
	  * Suma total de los porcentajes de descuentos para ordenes de compra
	  */
	public void setTotalDiscountsPO (BigDecimal TotalDiscountsPO);

	/** Get TotalDiscountsPO.
	  * Suma total de los porcentajes de descuentos para ordenes de compra
	  */
	public BigDecimal getTotalDiscountsPO();

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

    /** Column name Z_LineaProductoSocio_ID */
    public static final String COLUMNNAME_Z_LineaProductoSocio_ID = "Z_LineaProductoSocio_ID";

	/** Set Z_LineaProductoSocio ID	  */
	public void setZ_LineaProductoSocio_ID (int Z_LineaProductoSocio_ID);

	/** Get Z_LineaProductoSocio ID	  */
	public int getZ_LineaProductoSocio_ID();

	public I_Z_LineaProductoSocio getZ_LineaProductoSocio() throws RuntimeException;

    /** Column name Z_PautaComercial_ID */
    public static final String COLUMNNAME_Z_PautaComercial_ID = "Z_PautaComercial_ID";

	/** Set Z_PautaComercial ID	  */
	public void setZ_PautaComercial_ID (int Z_PautaComercial_ID);

	/** Get Z_PautaComercial ID	  */
	public int getZ_PautaComercial_ID();

	public I_Z_PautaComercial getZ_PautaComercial() throws RuntimeException;

    /** Column name Z_PautaComercialSet_ID_1 */
    public static final String COLUMNNAME_Z_PautaComercialSet_ID_1 = "Z_PautaComercialSet_ID_1";

	/** Set Z_PautaComercialSet_ID_1	  */
	public void setZ_PautaComercialSet_ID_1 (int Z_PautaComercialSet_ID_1);

	/** Get Z_PautaComercialSet_ID_1	  */
	public int getZ_PautaComercialSet_ID_1();

    /** Column name Z_PautaComercialSet_ID_2 */
    public static final String COLUMNNAME_Z_PautaComercialSet_ID_2 = "Z_PautaComercialSet_ID_2";

	/** Set Z_PautaComercialSet_ID_2	  */
	public void setZ_PautaComercialSet_ID_2 (int Z_PautaComercialSet_ID_2);

	/** Get Z_PautaComercialSet_ID_2	  */
	public int getZ_PautaComercialSet_ID_2();

    /** Column name Z_PautaComercialSet_ID_Gen */
    public static final String COLUMNNAME_Z_PautaComercialSet_ID_Gen = "Z_PautaComercialSet_ID_Gen";

	/** Set Z_PautaComercialSet_ID_Gen.
	  * Set General de Pauta Comercial 
	  */
	public void setZ_PautaComercialSet_ID_Gen (int Z_PautaComercialSet_ID_Gen);

	/** Get Z_PautaComercialSet_ID_Gen.
	  * Set General de Pauta Comercial 
	  */
	public int getZ_PautaComercialSet_ID_Gen();

    /** Column name Z_ProductoSocio_ID */
    public static final String COLUMNNAME_Z_ProductoSocio_ID = "Z_ProductoSocio_ID";

	/** Set Z_ProductoSocio ID	  */
	public void setZ_ProductoSocio_ID (int Z_ProductoSocio_ID);

	/** Get Z_ProductoSocio ID	  */
	public int getZ_ProductoSocio_ID();
}
