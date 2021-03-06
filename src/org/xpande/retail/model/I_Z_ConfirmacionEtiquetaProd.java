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

/** Generated Interface for Z_ConfirmacionEtiquetaProd
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_ConfirmacionEtiquetaProd 
{

    /** TableName=Z_ConfirmacionEtiquetaProd */
    public static final String Table_Name = "Z_ConfirmacionEtiquetaProd";

    /** AD_Table_ID=1000075 */
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

    /** Column name ID1 */
    public static final String COLUMNNAME_ID1 = "ID1";

	/** Set ID1	  */
	public void setID1(int ID1);

	/** Get ID1	  */
	public int getID1();

    /** Column name ID2 */
    public static final String COLUMNNAME_ID2 = "ID2";

	/** Set ID2	  */
	public void setID2(int ID2);

	/** Get ID2	  */
	public int getID2();

    /** Column name ID3 */
    public static final String COLUMNNAME_ID3 = "ID3";

	/** Set ID3	  */
	public void setID3(int ID3);

	/** Get ID3	  */
	public int getID3();

    /** Column name ID4 */
    public static final String COLUMNNAME_ID4 = "ID4";

	/** Set ID4	  */
	public void setID4(int ID4);

	/** Get ID4	  */
	public int getID4();

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

    /** Column name IsOmitted */
    public static final String COLUMNNAME_IsOmitted = "IsOmitted";

	/** Set IsOmitted.
	  * Omitida si o no
	  */
	public void setIsOmitted(boolean IsOmitted);

	/** Get IsOmitted.
	  * Omitida si o no
	  */
	public boolean isOmitted();

    /** Column name IsPrinted */
    public static final String COLUMNNAME_IsPrinted = "IsPrinted";

	/** Set Printed.
	  * Indicates if this document / line is printed
	  */
	public void setIsPrinted(boolean IsPrinted);

	/** Get Printed.
	  * Indicates if this document / line is printed
	  */
	public boolean isPrinted();

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

    /** Column name QtyCount */
    public static final String COLUMNNAME_QtyCount = "QtyCount";

	/** Set Quantity count.
	  * Counted Quantity
	  */
	public void setQtyCount(int QtyCount);

	/** Get Quantity count.
	  * Counted Quantity
	  */
	public int getQtyCount();

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

    /** Column name WithOfferSO */
    public static final String COLUMNNAME_WithOfferSO = "WithOfferSO";

	/** Set WithOfferSO.
	  * Si tiene o no oferta en precio de venta en Retail
	  */
	public void setWithOfferSO(boolean WithOfferSO);

	/** Get WithOfferSO.
	  * Si tiene o no oferta en precio de venta en Retail
	  */
	public boolean isWithOfferSO();

    /** Column name Z_ConfirmacionEtiquetaDoc_ID */
    public static final String COLUMNNAME_Z_ConfirmacionEtiquetaDoc_ID = "Z_ConfirmacionEtiquetaDoc_ID";

	/** Set Z_ConfirmacionEtiquetaDoc ID	  */
	public void setZ_ConfirmacionEtiquetaDoc_ID(int Z_ConfirmacionEtiquetaDoc_ID);

	/** Get Z_ConfirmacionEtiquetaDoc ID	  */
	public int getZ_ConfirmacionEtiquetaDoc_ID();

	public I_Z_ConfirmacionEtiquetaDoc getZ_ConfirmacionEtiquetaDoc() throws RuntimeException;

    /** Column name Z_ConfirmacionEtiquetaProd_ID */
    public static final String COLUMNNAME_Z_ConfirmacionEtiquetaProd_ID = "Z_ConfirmacionEtiquetaProd_ID";

	/** Set Z_ConfirmacionEtiquetaProd ID	  */
	public void setZ_ConfirmacionEtiquetaProd_ID(int Z_ConfirmacionEtiquetaProd_ID);

	/** Get Z_ConfirmacionEtiquetaProd ID	  */
	public int getZ_ConfirmacionEtiquetaProd_ID();

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
