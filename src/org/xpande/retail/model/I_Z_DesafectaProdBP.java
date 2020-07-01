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

/** Generated Interface for Z_DesafectaProdBP
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_DesafectaProdBP 
{

    /** TableName=Z_DesafectaProdBP */
    public static final String Table_Name = "Z_DesafectaProdBP";

    /** AD_Table_ID=1000357 */
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

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Document Type.
	  * Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID);

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
	public void setDateDoc (Timestamp DateDoc);

	/** Get Document Date.
	  * Date of the Document
	  */
	public Timestamp getDateDoc();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Document Action.
	  * The targeted status of the document
	  */
	public void setDocAction (String DocAction);

	/** Get Document Action.
	  * The targeted status of the document
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

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

    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/** Set Approved.
	  * Indicates if this document requires approval
	  */
	public void setIsApproved (boolean IsApproved);

	/** Get Approved.
	  * Indicates if this document requires approval
	  */
	public boolean isApproved();

    /** Column name LineaProdSocio_ID_To */
    public static final String COLUMNNAME_LineaProdSocio_ID_To = "LineaProdSocio_ID_To";

	/** Set LineaProdSocio_ID_To.
	  * Linea de productos destino de socios de negocio en Retail 
	  */
	public void setLineaProdSocio_ID_To (int LineaProdSocio_ID_To);

	/** Get LineaProdSocio_ID_To.
	  * Linea de productos destino de socios de negocio en Retail 
	  */
	public int getLineaProdSocio_ID_To();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name ProcessButton */
    public static final String COLUMNNAME_ProcessButton = "ProcessButton";

	/** Set ProcessButton	  */
	public void setProcessButton (String ProcessButton);

	/** Get ProcessButton	  */
	public String getProcessButton();

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

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name TipoDesafProdBP */
    public static final String COLUMNNAME_TipoDesafProdBP = "TipoDesafProdBP";

	/** Set TipoDesafProdBP.
	  * Tipos de operaciones posibles para el proceso de desafectación de productos a socios de negocio en Retail
	  */
	public void setTipoDesafProdBP (String TipoDesafProdBP);

	/** Get TipoDesafProdBP.
	  * Tipos de operaciones posibles para el proceso de desafectación de productos a socios de negocio en Retail
	  */
	public String getTipoDesafProdBP();

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

    /** Column name Z_DesafectaProdBP_ID */
    public static final String COLUMNNAME_Z_DesafectaProdBP_ID = "Z_DesafectaProdBP_ID";

	/** Set Z_DesafectaProdBP ID	  */
	public void setZ_DesafectaProdBP_ID (int Z_DesafectaProdBP_ID);

	/** Get Z_DesafectaProdBP ID	  */
	public int getZ_DesafectaProdBP_ID();

    /** Column name Z_LineaProductoSocio_ID */
    public static final String COLUMNNAME_Z_LineaProductoSocio_ID = "Z_LineaProductoSocio_ID";

	/** Set Z_LineaProductoSocio ID	  */
	public void setZ_LineaProductoSocio_ID (int Z_LineaProductoSocio_ID);

	/** Get Z_LineaProductoSocio ID	  */
	public int getZ_LineaProductoSocio_ID();

	public I_Z_LineaProductoSocio getZ_LineaProductoSocio() throws RuntimeException;

    /** Column name Z_ProductoFamilia_ID */
    public static final String COLUMNNAME_Z_ProductoFamilia_ID = "Z_ProductoFamilia_ID";

	/** Set Z_ProductoFamilia ID	  */
	public void setZ_ProductoFamilia_ID (int Z_ProductoFamilia_ID);

	/** Get Z_ProductoFamilia ID	  */
	public int getZ_ProductoFamilia_ID();

	public I_Z_ProductoFamilia getZ_ProductoFamilia() throws RuntimeException;

    /** Column name Z_ProductoRubro_ID */
    public static final String COLUMNNAME_Z_ProductoRubro_ID = "Z_ProductoRubro_ID";

	/** Set Z_ProductoRubro ID	  */
	public void setZ_ProductoRubro_ID (int Z_ProductoRubro_ID);

	/** Get Z_ProductoRubro ID	  */
	public int getZ_ProductoRubro_ID();

	public I_Z_ProductoRubro getZ_ProductoRubro() throws RuntimeException;

    /** Column name Z_ProductoSeccion_ID */
    public static final String COLUMNNAME_Z_ProductoSeccion_ID = "Z_ProductoSeccion_ID";

	/** Set Z_ProductoSeccion ID	  */
	public void setZ_ProductoSeccion_ID (int Z_ProductoSeccion_ID);

	/** Get Z_ProductoSeccion ID	  */
	public int getZ_ProductoSeccion_ID();

	public I_Z_ProductoSeccion getZ_ProductoSeccion() throws RuntimeException;

    /** Column name Z_ProductoSubfamilia_ID */
    public static final String COLUMNNAME_Z_ProductoSubfamilia_ID = "Z_ProductoSubfamilia_ID";

	/** Set Z_ProductoSubfamilia ID	  */
	public void setZ_ProductoSubfamilia_ID (int Z_ProductoSubfamilia_ID);

	/** Get Z_ProductoSubfamilia ID	  */
	public int getZ_ProductoSubfamilia_ID();

	public I_Z_ProductoSubfamilia getZ_ProductoSubfamilia() throws RuntimeException;
}
