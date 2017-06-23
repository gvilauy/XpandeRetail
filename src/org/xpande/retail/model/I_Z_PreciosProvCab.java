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

/** Generated Interface for Z_PreciosProvCab
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_PreciosProvCab 
{

    /** TableName=Z_PreciosProvCab */
    public static final String Table_Name = "Z_PreciosProvCab";

    /** AD_Table_ID=1000042 */
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

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID(int C_BPartner_ID);

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

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Document Type.
	  * Document type or rules
	  */
	public void setC_DocType_ID(int C_DocType_ID);

	/** Get Document Type.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public I_C_DocType getC_DocType() throws RuntimeException;

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

    /** Column name DateDoc */
    public static final String COLUMNNAME_DateDoc = "DateDoc";

	/** Set Document Date.
	  * Date of the Document
	  */
	public void setDateDoc(Timestamp DateDoc);

	/** Get Document Date.
	  * Date of the Document
	  */
	public Timestamp getDateDoc();

    /** Column name DateValidPO */
    public static final String COLUMNNAME_DateValidPO = "DateValidPO";

	/** Set DateValidPO.
	  * Fecha Vigencia Compra
	  */
	public void setDateValidPO(Timestamp DateValidPO);

	/** Get DateValidPO.
	  * Fecha Vigencia Compra
	  */
	public Timestamp getDateValidPO();

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

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Document Action.
	  * The targeted status of the document
	  */
	public void setDocAction(String DocAction);

	/** Get Document Action.
	  * The targeted status of the document
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus(String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo(String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name FileName */
    public static final String COLUMNNAME_FileName = "FileName";

	/** Set File Name.
	  * Name of the local file or URL
	  */
	public void setFileName(String FileName);

	/** Get File Name.
	  * Name of the local file or URL
	  */
	public String getFileName();

    /** Column name HaveErrors */
    public static final String COLUMNNAME_HaveErrors = "HaveErrors";

	/** Set HaveErrors	  */
	public void setHaveErrors(boolean HaveErrors);

	/** Get HaveErrors	  */
	public boolean isHaveErrors();

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

    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/** Set Approved.
	  * Indicates if this document requires approval
	  */
	public void setIsApproved(boolean IsApproved);

	/** Get Approved.
	  * Indicates if this document requires approval
	  */
	public boolean isApproved();

    /** Column name IsExecuted */
    public static final String COLUMNNAME_IsExecuted = "IsExecuted";

	/** Set IsExecuted	  */
	public void setIsExecuted(boolean IsExecuted);

	/** Get IsExecuted	  */
	public boolean isExecuted();

    /** Column name IsTaxIncluded */
    public static final String COLUMNNAME_IsTaxIncluded = "IsTaxIncluded";

	/** Set Price includes Tax.
	  * Tax is included in the price 
	  */
	public void setIsTaxIncluded(boolean IsTaxIncluded);

	/** Get Price includes Tax.
	  * Tax is included in the price 
	  */
	public boolean isTaxIncluded();

    /** Column name ModalidadPreciosProv */
    public static final String COLUMNNAME_ModalidadPreciosProv = "ModalidadPreciosProv";

	/** Set ModalidadPreciosProv.
	  * Modalidad de gestión para el mantenimiento de precios de proveedor
	  */
	public void setModalidadPreciosProv(String ModalidadPreciosProv);

	/** Get ModalidadPreciosProv.
	  * Modalidad de gestión para el mantenimiento de precios de proveedor
	  */
	public String getModalidadPreciosProv();

    /** Column name M_PriceList_ID */
    public static final String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/** Set Price List.
	  * Unique identifier of a Price List
	  */
	public void setM_PriceList_ID(int M_PriceList_ID);

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
	public void setM_PriceList_ID_SO(int M_PriceList_ID_SO);

	/** Get M_PriceList_ID_SO.
	  * Lista de Precios de Venta
	  */
	public int getM_PriceList_ID_SO();

    /** Column name M_PriceList_Version_ID */
    public static final String COLUMNNAME_M_PriceList_Version_ID = "M_PriceList_Version_ID";

	/** Set Price List Version.
	  * Identifies a unique instance of a Price List
	  */
	public void setM_PriceList_Version_ID(int M_PriceList_Version_ID);

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
	public void setM_PriceList_Version_ID_SO(int M_PriceList_Version_ID_SO);

	/** Get M_PriceList_Version_ID_SO.
	  * Version de Lista de Precios de Venta
	  */
	public int getM_PriceList_Version_ID_SO();

    /** Column name NombreLineaManual */
    public static final String COLUMNNAME_NombreLineaManual = "NombreLineaManual";

	/** Set NombreLineaManual	  */
	public void setNombreLineaManual(String NombreLineaManual);

	/** Get NombreLineaManual	  */
	public String getNombreLineaManual();

    /** Column name OnlyOneOrg */
    public static final String COLUMNNAME_OnlyOneOrg = "OnlyOneOrg";

	/** Set OnlyOneOrg.
	  * Flag para indicar si estoy procesando una sola una organización
	  */
	public void setOnlyOneOrg(boolean OnlyOneOrg);

	/** Get OnlyOneOrg.
	  * Flag para indicar si estoy procesando una sola una organización
	  */
	public boolean isOnlyOneOrg();

    /** Column name PrecisionPO */
    public static final String COLUMNNAME_PrecisionPO = "PrecisionPO";

	/** Set PrecisionPO.
	  * Precisión decimal para precios de compra
	  */
	public void setPrecisionPO(int PrecisionPO);

	/** Get PrecisionPO.
	  * Precisión decimal para precios de compra
	  */
	public int getPrecisionPO();

    /** Column name PrecisionSO */
    public static final String COLUMNNAME_PrecisionSO = "PrecisionSO";

	/** Set PrecisionSO.
	  * Precisión decimal para precios de venta
	  */
	public void setPrecisionSO(int PrecisionSO);

	/** Get PrecisionSO.
	  * Precisión decimal para precios de venta
	  */
	public int getPrecisionSO();

    /** Column name ProcessButton */
    public static final String COLUMNNAME_ProcessButton = "ProcessButton";

	/** Set ProcessButton	  */
	public void setProcessButton(String ProcessButton);

	/** Get ProcessButton	  */
	public String getProcessButton();

    /** Column name ProcessButton2 */
    public static final String COLUMNNAME_ProcessButton2 = "ProcessButton2";

	/** Set ProcessButton2.
	  * Botón de Proceso
	  */
	public void setProcessButton2(String ProcessButton2);

	/** Get ProcessButton2.
	  * Botón de Proceso
	  */
	public String getProcessButton2();

    /** Column name ProcessButton3 */
    public static final String COLUMNNAME_ProcessButton3 = "ProcessButton3";

	/** Set ProcessButton3.
	  * Botón para proceso
	  */
	public void setProcessButton3(String ProcessButton3);

	/** Get ProcessButton3.
	  * Botón para proceso
	  */
	public String getProcessButton3();

    /** Column name ProcessButton4 */
    public static final String COLUMNNAME_ProcessButton4 = "ProcessButton4";

	/** Set ProcessButton4.
	  * Botón de Proceso
	  */
	public void setProcessButton4(String ProcessButton4);

	/** Get ProcessButton4.
	  * Botón de Proceso
	  */
	public String getProcessButton4();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed(boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing(boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

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

    /** Column name Z_LineaProductoSocio_ID */
    public static final String COLUMNNAME_Z_LineaProductoSocio_ID = "Z_LineaProductoSocio_ID";

	/** Set Z_LineaProductoSocio ID	  */
	public void setZ_LineaProductoSocio_ID(int Z_LineaProductoSocio_ID);

	/** Get Z_LineaProductoSocio ID	  */
	public int getZ_LineaProductoSocio_ID();

	public I_Z_LineaProductoSocio getZ_LineaProductoSocio() throws RuntimeException;

    /** Column name Z_PautaComercial_ID */
    public static final String COLUMNNAME_Z_PautaComercial_ID = "Z_PautaComercial_ID";

	/** Set Z_PautaComercial ID	  */
	public void setZ_PautaComercial_ID(int Z_PautaComercial_ID);

	/** Get Z_PautaComercial ID	  */
	public int getZ_PautaComercial_ID();

	public I_Z_PautaComercial getZ_PautaComercial() throws RuntimeException;

    /** Column name Z_PreciosProvCab_ID */
    public static final String COLUMNNAME_Z_PreciosProvCab_ID = "Z_PreciosProvCab_ID";

	/** Set Z_PreciosProvCab ID	  */
	public void setZ_PreciosProvCab_ID(int Z_PreciosProvCab_ID);

	/** Get Z_PreciosProvCab ID	  */
	public int getZ_PreciosProvCab_ID();
}
