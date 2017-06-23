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

/** Generated Interface for Z_PreciosProvLin
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_PreciosProvLin 
{

    /** TableName=Z_PreciosProvLin */
    public static final String Table_Name = "Z_PreciosProvLin";

    /** AD_Table_ID=1000044 */
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

    /** Column name C_TaxCategory_ID */
    public static final String COLUMNNAME_C_TaxCategory_ID = "C_TaxCategory_ID";

	/** Set Tax Category.
	  * Tax Category
	  */
	public void setC_TaxCategory_ID(int C_TaxCategory_ID);

	/** Get Tax Category.
	  * Tax Category
	  */
	public int getC_TaxCategory_ID();

	public I_C_TaxCategory getC_TaxCategory() throws RuntimeException;

    /** Column name C_TaxCategory_ID_2 */
    public static final String COLUMNNAME_C_TaxCategory_ID_2 = "C_TaxCategory_ID_2";

	/** Set C_TaxCategory_ID_2	  */
	public void setC_TaxCategory_ID_2(int C_TaxCategory_ID_2);

	/** Get C_TaxCategory_ID_2	  */
	public int getC_TaxCategory_ID_2();

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/** Set UOM.
	  * Unit of Measure
	  */
	public void setC_UOM_ID(int C_UOM_ID);

	/** Get UOM.
	  * Unit of Measure
	  */
	public int getC_UOM_ID();

	public I_C_UOM getC_UOM() throws RuntimeException;

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

    /** Column name DistinctPricePO */
    public static final String COLUMNNAME_DistinctPricePO = "DistinctPricePO";

	/** Set DistinctPricePO	  */
	public void setDistinctPricePO(boolean DistinctPricePO);

	/** Get DistinctPricePO	  */
	public boolean isDistinctPricePO();

    /** Column name DistinctPriceSO */
    public static final String COLUMNNAME_DistinctPriceSO = "DistinctPriceSO";

	/** Set DistinctPriceSO	  */
	public void setDistinctPriceSO(boolean DistinctPriceSO);

	/** Get DistinctPriceSO	  */
	public boolean isDistinctPriceSO();

    /** Column name ErrorMsg */
    public static final String COLUMNNAME_ErrorMsg = "ErrorMsg";

	/** Set Error Msg	  */
	public void setErrorMsg(String ErrorMsg);

	/** Get Error Msg	  */
	public String getErrorMsg();

    /** Column name InternalCode */
    public static final String COLUMNNAME_InternalCode = "InternalCode";

	/** Set InternalCode.
	  * Código Interno
	  */
	public void setInternalCode(String InternalCode);

	/** Get InternalCode.
	  * Código Interno
	  */
	public String getInternalCode();

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

    /** Column name IsClassified */
    public static final String COLUMNNAME_IsClassified = "IsClassified";

	/** Set IsClassified	  */
	public void setIsClassified(boolean IsClassified);

	/** Get IsClassified	  */
	public boolean isClassified();

    /** Column name IsConfirmed */
    public static final String COLUMNNAME_IsConfirmed = "IsConfirmed";

	/** Set Confirmed.
	  * Assignment is confirmed
	  */
	public void setIsConfirmed(boolean IsConfirmed);

	/** Get Confirmed.
	  * Assignment is confirmed
	  */
	public boolean isConfirmed();

    /** Column name IsNew */
    public static final String COLUMNNAME_IsNew = "IsNew";

	/** Set IsNew	  */
	public void setIsNew(boolean IsNew);

	/** Get IsNew	  */
	public boolean isNew();

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

    /** Column name OrgDifferentPricePO */
    public static final String COLUMNNAME_OrgDifferentPricePO = "OrgDifferentPricePO";

	/** Set OrgDifferentPricePO.
	  * Si se manejan precios de compra distintos por organización en modulo de retail
	  */
	public void setOrgDifferentPricePO(boolean OrgDifferentPricePO);

	/** Get OrgDifferentPricePO.
	  * Si se manejan precios de compra distintos por organización en modulo de retail
	  */
	public boolean isOrgDifferentPricePO();

    /** Column name OrgDifferentPriceSO */
    public static final String COLUMNNAME_OrgDifferentPriceSO = "OrgDifferentPriceSO";

	/** Set OrgDifferentPriceSO.
	  * Si se manejan precios de venta distintos por organización en modulo de retail
	  */
	public void setOrgDifferentPriceSO(boolean OrgDifferentPriceSO);

	/** Get OrgDifferentPriceSO.
	  * Si se manejan precios de venta distintos por organización en modulo de retail
	  */
	public boolean isOrgDifferentPriceSO();

    /** Column name PriceFinal */
    public static final String COLUMNNAME_PriceFinal = "PriceFinal";

	/** Set PriceFinal	  */
	public void setPriceFinal(BigDecimal PriceFinal);

	/** Get PriceFinal	  */
	public BigDecimal getPriceFinal();

    /** Column name PriceFinalMargin */
    public static final String COLUMNNAME_PriceFinalMargin = "PriceFinalMargin";

	/** Set PriceFinalMargin	  */
	public void setPriceFinalMargin(BigDecimal PriceFinalMargin);

	/** Get PriceFinalMargin	  */
	public BigDecimal getPriceFinalMargin();

    /** Column name PriceInvoiced */
    public static final String COLUMNNAME_PriceInvoiced = "PriceInvoiced";

	/** Set Price Invoiced.
	  * The priced invoiced to the customer (in the currency of the customer's AR price list) - 0 for default price
	  */
	public void setPriceInvoiced(BigDecimal PriceInvoiced);

	/** Get Price Invoiced.
	  * The priced invoiced to the customer (in the currency of the customer's AR price list) - 0 for default price
	  */
	public BigDecimal getPriceInvoiced();

    /** Column name PriceInvoicedMargin */
    public static final String COLUMNNAME_PriceInvoicedMargin = "PriceInvoicedMargin";

	/** Set PriceInvoicedMargin	  */
	public void setPriceInvoicedMargin(BigDecimal PriceInvoicedMargin);

	/** Get PriceInvoicedMargin	  */
	public BigDecimal getPriceInvoicedMargin();

    /** Column name PriceList */
    public static final String COLUMNNAME_PriceList = "PriceList";

	/** Set List Price.
	  * List Price
	  */
	public void setPriceList(BigDecimal PriceList);

	/** Get List Price.
	  * List Price
	  */
	public BigDecimal getPriceList();

    /** Column name PricePO */
    public static final String COLUMNNAME_PricePO = "PricePO";

	/** Set PO Price.
	  * Price based on a purchase order
	  */
	public void setPricePO(BigDecimal PricePO);

	/** Get PO Price.
	  * Price based on a purchase order
	  */
	public BigDecimal getPricePO();

    /** Column name PricePOMargin */
    public static final String COLUMNNAME_PricePOMargin = "PricePOMargin";

	/** Set PricePOMargin	  */
	public void setPricePOMargin(BigDecimal PricePOMargin);

	/** Get PricePOMargin	  */
	public BigDecimal getPricePOMargin();

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

    /** Column name TotalDiscountsFinal */
    public static final String COLUMNNAME_TotalDiscountsFinal = "TotalDiscountsFinal";

	/** Set TotalDiscountsFinal.
	  * Suma total de los porcentajes de descuentos de productos para pagos
	  */
	public void setTotalDiscountsFinal(BigDecimal TotalDiscountsFinal);

	/** Get TotalDiscountsFinal.
	  * Suma total de los porcentajes de descuentos de productos para pagos
	  */
	public BigDecimal getTotalDiscountsFinal();

    /** Column name TotalDiscountsPO */
    public static final String COLUMNNAME_TotalDiscountsPO = "TotalDiscountsPO";

	/** Set TotalDiscountsPO.
	  * Suma total de los porcentajes de descuentos para ordenes de compra
	  */
	public void setTotalDiscountsPO(BigDecimal TotalDiscountsPO);

	/** Get TotalDiscountsPO.
	  * Suma total de los porcentajes de descuentos para ordenes de compra
	  */
	public BigDecimal getTotalDiscountsPO();

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

    /** Column name VendorProductNo */
    public static final String COLUMNNAME_VendorProductNo = "VendorProductNo";

	/** Set Partner Product Key.
	  * Product Key of the Business Partner
	  */
	public void setVendorProductNo(String VendorProductNo);

	/** Get Partner Product Key.
	  * Product Key of the Business Partner
	  */
	public String getVendorProductNo();

    /** Column name Z_PautaComercialSet_ID_1 */
    public static final String COLUMNNAME_Z_PautaComercialSet_ID_1 = "Z_PautaComercialSet_ID_1";

	/** Set Z_PautaComercialSet_ID_1	  */
	public void setZ_PautaComercialSet_ID_1(int Z_PautaComercialSet_ID_1);

	/** Get Z_PautaComercialSet_ID_1	  */
	public int getZ_PautaComercialSet_ID_1();

    /** Column name Z_PautaComercialSet_ID_2 */
    public static final String COLUMNNAME_Z_PautaComercialSet_ID_2 = "Z_PautaComercialSet_ID_2";

	/** Set Z_PautaComercialSet_ID_2	  */
	public void setZ_PautaComercialSet_ID_2(int Z_PautaComercialSet_ID_2);

	/** Get Z_PautaComercialSet_ID_2	  */
	public int getZ_PautaComercialSet_ID_2();

    /** Column name Z_PautaComercialSet_ID_Gen */
    public static final String COLUMNNAME_Z_PautaComercialSet_ID_Gen = "Z_PautaComercialSet_ID_Gen";

	/** Set Z_PautaComercialSet_ID_Gen.
	  * Set General de Pauta Comercial 
	  */
	public void setZ_PautaComercialSet_ID_Gen(int Z_PautaComercialSet_ID_Gen);

	/** Get Z_PautaComercialSet_ID_Gen.
	  * Set General de Pauta Comercial 
	  */
	public int getZ_PautaComercialSet_ID_Gen();

    /** Column name Z_PreciosProvCab_ID */
    public static final String COLUMNNAME_Z_PreciosProvCab_ID = "Z_PreciosProvCab_ID";

	/** Set Z_PreciosProvCab ID	  */
	public void setZ_PreciosProvCab_ID(int Z_PreciosProvCab_ID);

	/** Get Z_PreciosProvCab ID	  */
	public int getZ_PreciosProvCab_ID();

	public I_Z_PreciosProvCab getZ_PreciosProvCab() throws RuntimeException;

    /** Column name Z_PreciosProvLin_ID */
    public static final String COLUMNNAME_Z_PreciosProvLin_ID = "Z_PreciosProvLin_ID";

	/** Set Z_PreciosProvLin ID	  */
	public void setZ_PreciosProvLin_ID(int Z_PreciosProvLin_ID);

	/** Get Z_PreciosProvLin ID	  */
	public int getZ_PreciosProvLin_ID();

    /** Column name Z_ProductoFamilia_ID */
    public static final String COLUMNNAME_Z_ProductoFamilia_ID = "Z_ProductoFamilia_ID";

	/** Set Z_ProductoFamilia ID	  */
	public void setZ_ProductoFamilia_ID(int Z_ProductoFamilia_ID);

	/** Get Z_ProductoFamilia ID	  */
	public int getZ_ProductoFamilia_ID();

	public I_Z_ProductoFamilia getZ_ProductoFamilia() throws RuntimeException;

    /** Column name Z_ProductoRubro_ID */
    public static final String COLUMNNAME_Z_ProductoRubro_ID = "Z_ProductoRubro_ID";

	/** Set Z_ProductoRubro ID	  */
	public void setZ_ProductoRubro_ID(int Z_ProductoRubro_ID);

	/** Get Z_ProductoRubro ID	  */
	public int getZ_ProductoRubro_ID();

	public I_Z_ProductoRubro getZ_ProductoRubro() throws RuntimeException;

    /** Column name Z_ProductoSeccion_ID */
    public static final String COLUMNNAME_Z_ProductoSeccion_ID = "Z_ProductoSeccion_ID";

	/** Set Z_ProductoSeccion ID	  */
	public void setZ_ProductoSeccion_ID(int Z_ProductoSeccion_ID);

	/** Get Z_ProductoSeccion ID	  */
	public int getZ_ProductoSeccion_ID();

	public I_Z_ProductoSeccion getZ_ProductoSeccion() throws RuntimeException;

    /** Column name Z_ProductoSubfamilia_ID */
    public static final String COLUMNNAME_Z_ProductoSubfamilia_ID = "Z_ProductoSubfamilia_ID";

	/** Set Z_ProductoSubfamilia ID	  */
	public void setZ_ProductoSubfamilia_ID(int Z_ProductoSubfamilia_ID);

	/** Get Z_ProductoSubfamilia ID	  */
	public int getZ_ProductoSubfamilia_ID();

	public I_Z_ProductoSubfamilia getZ_ProductoSubfamilia() throws RuntimeException;
}
