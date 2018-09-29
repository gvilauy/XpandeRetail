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

/** Generated Interface for Z_InvoiceBonifica
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_InvoiceBonifica 
{

    /** TableName=Z_InvoiceBonifica */
    public static final String Table_Name = "Z_InvoiceBonifica";

    /** AD_Table_ID=1000210 */
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

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID(int C_Invoice_ID);

	/** Get Invoice.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

	public I_C_Invoice getC_Invoice() throws RuntimeException;

    /** Column name C_InvoiceLine_ID */
    public static final String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/** Set Invoice Line.
	  * Invoice Detail Line
	  */
	public void setC_InvoiceLine_ID(int C_InvoiceLine_ID);

	/** Get Invoice Line.
	  * Invoice Detail Line
	  */
	public int getC_InvoiceLine_ID();

	public I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException;

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

    /** Column name IsManual */
    public static final String COLUMNNAME_IsManual = "IsManual";

	/** Set Manual.
	  * This is a manual process
	  */
	public void setIsManual(boolean IsManual);

	/** Get Manual.
	  * This is a manual process
	  */
	public boolean isManual();

    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/** Set Line No.
	  * Unique line for this document
	  */
	public void setLine(int Line);

	/** Get Line No.
	  * Unique line for this document
	  */
	public int getLine();

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

    /** Column name M_Product_To_ID */
    public static final String COLUMNNAME_M_Product_To_ID = "M_Product_To_ID";

	/** Set To Product.
	  * Product to be converted to (must have UOM Conversion defined to From Product)
	  */
	public void setM_Product_To_ID(int M_Product_To_ID);

	/** Get To Product.
	  * Product to be converted to (must have UOM Conversion defined to From Product)
	  */
	public int getM_Product_To_ID();

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

    /** Column name QtyBase */
    public static final String COLUMNNAME_QtyBase = "QtyBase";

	/** Set QtyBase.
	  * Cantidad Base
	  */
	public void setQtyBase(BigDecimal QtyBase);

	/** Get QtyBase.
	  * Cantidad Base
	  */
	public BigDecimal getQtyBase();

    /** Column name QtyCalculated */
    public static final String COLUMNNAME_QtyCalculated = "QtyCalculated";

	/** Set Calculated Quantity.
	  * Calculated Quantity
	  */
	public void setQtyCalculated(BigDecimal QtyCalculated);

	/** Get Calculated Quantity.
	  * Calculated Quantity
	  */
	public BigDecimal getQtyCalculated();

    /** Column name QtyReward */
    public static final String COLUMNNAME_QtyReward = "QtyReward";

	/** Set Reward Quantity	  */
	public void setQtyReward(BigDecimal QtyReward);

	/** Get Reward Quantity	  */
	public BigDecimal getQtyReward();

    /** Column name TipoBonificaQty */
    public static final String COLUMNNAME_TipoBonificaQty = "TipoBonificaQty";

	/** Set TipoBonificaQty.
	  * Lista de valores para Tipos de Bonificacion en Cantidadades
	  */
	public void setTipoBonificaQty(String TipoBonificaQty);

	/** Get TipoBonificaQty.
	  * Lista de valores para Tipos de Bonificacion en Cantidadades
	  */
	public String getTipoBonificaQty();

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

    /** Column name Z_InvoiceBonifica_ID */
    public static final String COLUMNNAME_Z_InvoiceBonifica_ID = "Z_InvoiceBonifica_ID";

	/** Set Z_InvoiceBonifica ID	  */
	public void setZ_InvoiceBonifica_ID(int Z_InvoiceBonifica_ID);

	/** Get Z_InvoiceBonifica ID	  */
	public int getZ_InvoiceBonifica_ID();

    /** Column name Z_PautaComercialSet_ID */
    public static final String COLUMNNAME_Z_PautaComercialSet_ID = "Z_PautaComercialSet_ID";

	/** Set Z_PautaComercialSet ID	  */
	public void setZ_PautaComercialSet_ID(int Z_PautaComercialSet_ID);

	/** Get Z_PautaComercialSet ID	  */
	public int getZ_PautaComercialSet_ID();

	public I_Z_PautaComercialSet getZ_PautaComercialSet() throws RuntimeException;
}
