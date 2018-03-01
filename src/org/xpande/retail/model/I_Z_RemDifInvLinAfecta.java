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

/** Generated Interface for Z_RemDifInvLinAfecta
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_RemDifInvLinAfecta 
{

    /** TableName=Z_RemDifInvLinAfecta */
    public static final String Table_Name = "Z_RemDifInvLinAfecta";

    /** AD_Table_ID=1000200 */
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

    /** Column name AmtDocument */
    public static final String COLUMNNAME_AmtDocument = "AmtDocument";

	/** Set AmtDocument.
	  * Monto documento
	  */
	public void setAmtDocument(BigDecimal AmtDocument);

	/** Get AmtDocument.
	  * Monto documento
	  */
	public BigDecimal getAmtDocument();

    /** Column name AmtOpen */
    public static final String COLUMNNAME_AmtOpen = "AmtOpen";

	/** Set AmtOpen.
	  * Monto pendiente
	  */
	public void setAmtOpen(BigDecimal AmtOpen);

	/** Get AmtOpen.
	  * Monto pendiente
	  */
	public BigDecimal getAmtOpen();

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

    /** Column name DocumentNoRef */
    public static final String COLUMNNAME_DocumentNoRef = "DocumentNoRef";

	/** Set DocumentNoRef.
	  * Numero de documento referenciado
	  */
	public void setDocumentNoRef(String DocumentNoRef);

	/** Get DocumentNoRef.
	  * Numero de documento referenciado
	  */
	public String getDocumentNoRef();

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

    /** Column name IsDifferenceAmt */
    public static final String COLUMNNAME_IsDifferenceAmt = "IsDifferenceAmt";

	/** Set IsDifferenceAmt.
	  * Si tiene o no diferencia por montos
	  */
	public void setIsDifferenceAmt(boolean IsDifferenceAmt);

	/** Get IsDifferenceAmt.
	  * Si tiene o no diferencia por montos
	  */
	public boolean isDifferenceAmt();

    /** Column name IsDifferenceQty */
    public static final String COLUMNNAME_IsDifferenceQty = "IsDifferenceQty";

	/** Set IsDifferenceQty.
	  * Si tiene o no diferencia de cantidades
	  */
	public void setIsDifferenceQty(boolean IsDifferenceQty);

	/** Get IsDifferenceQty.
	  * Si tiene o no diferencia de cantidades
	  */
	public boolean isDifferenceQty();

    /** Column name LineTotalAmt */
    public static final String COLUMNNAME_LineTotalAmt = "LineTotalAmt";

	/** Set Line Total.
	  * Total line amount incl. Tax
	  */
	public void setLineTotalAmt(BigDecimal LineTotalAmt);

	/** Get Line Total.
	  * Total line amount incl. Tax
	  */
	public BigDecimal getLineTotalAmt();

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

    /** Column name PriceDoc */
    public static final String COLUMNNAME_PriceDoc = "PriceDoc";

	/** Set PriceDoc.
	  * Precio del documento o linea
	  */
	public void setPriceDoc(BigDecimal PriceDoc);

	/** Get PriceDoc.
	  * Precio del documento o linea
	  */
	public BigDecimal getPriceDoc();

    /** Column name PriceEntered */
    public static final String COLUMNNAME_PriceEntered = "PriceEntered";

	/** Set Price.
	  * Price Entered - the price based on the selected/base UoM
	  */
	public void setPriceEntered(BigDecimal PriceEntered);

	/** Get Price.
	  * Price Entered - the price based on the selected/base UoM
	  */
	public BigDecimal getPriceEntered();

    /** Column name QtyDocument */
    public static final String COLUMNNAME_QtyDocument = "QtyDocument";

	/** Set QtyDocument.
	  * Cantidad original del documento o linea
	  */
	public void setQtyDocument(BigDecimal QtyDocument);

	/** Get QtyDocument.
	  * Cantidad original del documento o linea
	  */
	public BigDecimal getQtyDocument();

    /** Column name QtyEntered */
    public static final String COLUMNNAME_QtyEntered = "QtyEntered";

	/** Set Quantity.
	  * The Quantity Entered is based on the selected UoM
	  */
	public void setQtyEntered(BigDecimal QtyEntered);

	/** Get Quantity.
	  * The Quantity Entered is based on the selected UoM
	  */
	public BigDecimal getQtyEntered();

    /** Column name QtyOpen */
    public static final String COLUMNNAME_QtyOpen = "QtyOpen";

	/** Set QtyOpen.
	  * Cantidad pendiente del documento o linea
	  */
	public void setQtyOpen(BigDecimal QtyOpen);

	/** Get QtyOpen.
	  * Cantidad pendiente del documento o linea
	  */
	public BigDecimal getQtyOpen();

    /** Column name Ref_Invoice_ID */
    public static final String COLUMNNAME_Ref_Invoice_ID = "Ref_Invoice_ID";

	/** Set Referenced Invoice	  */
	public void setRef_Invoice_ID(int Ref_Invoice_ID);

	/** Get Referenced Invoice	  */
	public int getRef_Invoice_ID();

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

    /** Column name Z_RemDifInvLinAfecta_ID */
    public static final String COLUMNNAME_Z_RemDifInvLinAfecta_ID = "Z_RemDifInvLinAfecta_ID";

	/** Set Z_RemDifInvLinAfecta ID	  */
	public void setZ_RemDifInvLinAfecta_ID(int Z_RemDifInvLinAfecta_ID);

	/** Get Z_RemDifInvLinAfecta ID	  */
	public int getZ_RemDifInvLinAfecta_ID();

    /** Column name Z_RemitoDifInv_ID */
    public static final String COLUMNNAME_Z_RemitoDifInv_ID = "Z_RemitoDifInv_ID";

	/** Set Z_RemitoDifInv ID	  */
	public void setZ_RemitoDifInv_ID(int Z_RemitoDifInv_ID);

	/** Get Z_RemitoDifInv ID	  */
	public int getZ_RemitoDifInv_ID();

	public I_Z_RemitoDifInv getZ_RemitoDifInv() throws RuntimeException;

    /** Column name Z_RemitoDifInvLin_ID */
    public static final String COLUMNNAME_Z_RemitoDifInvLin_ID = "Z_RemitoDifInvLin_ID";

	/** Set Z_RemitoDifInvLin ID	  */
	public void setZ_RemitoDifInvLin_ID(int Z_RemitoDifInvLin_ID);

	/** Get Z_RemitoDifInvLin ID	  */
	public int getZ_RemitoDifInvLin_ID();

	public I_Z_RemitoDifInvLin getZ_RemitoDifInvLin() throws RuntimeException;
}
