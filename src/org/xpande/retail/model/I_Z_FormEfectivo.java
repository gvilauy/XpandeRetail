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

/** Generated Interface for Z_FormEfectivo
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_FormEfectivo 
{

    /** TableName=Z_FormEfectivo */
    public static final String Table_Name = "Z_FormEfectivo";

    /** AD_Table_ID=1000309 */
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

    /** Column name AmtBalanceo */
    public static final String COLUMNNAME_AmtBalanceo = "AmtBalanceo";

	/** Set AmtBalanceo.
	  * Monto para balanceo contable
	  */
	public void setAmtBalanceo(BigDecimal AmtBalanceo);

	/** Get AmtBalanceo.
	  * Monto para balanceo contable
	  */
	public BigDecimal getAmtBalanceo();

    /** Column name AmtBalanceo2 */
    public static final String COLUMNNAME_AmtBalanceo2 = "AmtBalanceo2";

	/** Set AmtBalanceo2.
	  * Monto para balanceo contable 2
	  */
	public void setAmtBalanceo2(BigDecimal AmtBalanceo2);

	/** Get AmtBalanceo2.
	  * Monto para balanceo contable 2
	  */
	public BigDecimal getAmtBalanceo2();

    /** Column name AmtTotal1 */
    public static final String COLUMNNAME_AmtTotal1 = "AmtTotal1";

	/** Set AmtTotal1.
	  * Monto total uno
	  */
	public void setAmtTotal1(BigDecimal AmtTotal1);

	/** Get AmtTotal1.
	  * Monto total uno
	  */
	public BigDecimal getAmtTotal1();

    /** Column name AmtTotal2 */
    public static final String COLUMNNAME_AmtTotal2 = "AmtTotal2";

	/** Set AmtTotal2.
	  * Monto total dos
	  */
	public void setAmtTotal2(BigDecimal AmtTotal2);

	/** Get AmtTotal2.
	  * Monto total dos
	  */
	public BigDecimal getAmtTotal2();

    /** Column name AmtTotal3 */
    public static final String COLUMNNAME_AmtTotal3 = "AmtTotal3";

	/** Set AmtTotal3.
	  * Monto total 3
	  */
	public void setAmtTotal3(BigDecimal AmtTotal3);

	/** Get AmtTotal3.
	  * Monto total 3
	  */
	public BigDecimal getAmtTotal3();

    /** Column name AmtTotal4 */
    public static final String COLUMNNAME_AmtTotal4 = "AmtTotal4";

	/** Set AmtTotal4.
	  * Monto total 4
	  */
	public void setAmtTotal4(BigDecimal AmtTotal4);

	/** Get AmtTotal4.
	  * Monto total 4
	  */
	public BigDecimal getAmtTotal4();

    /** Column name AmtTotal5 */
    public static final String COLUMNNAME_AmtTotal5 = "AmtTotal5";

	/** Set AmtTotal5.
	  * Monto total 5
	  */
	public void setAmtTotal5(BigDecimal AmtTotal5);

	/** Get AmtTotal5.
	  * Monto total 5
	  */
	public BigDecimal getAmtTotal5();

    /** Column name AmtTotal6 */
    public static final String COLUMNNAME_AmtTotal6 = "AmtTotal6";

	/** Set AmtTotal6.
	  * Monto total 6
	  */
	public void setAmtTotal6(BigDecimal AmtTotal6);

	/** Get AmtTotal6.
	  * Monto total 6
	  */
	public BigDecimal getAmtTotal6();

    /** Column name AmtTotal7 */
    public static final String COLUMNNAME_AmtTotal7 = "AmtTotal7";

	/** Set AmtTotal7.
	  * Monto total 7
	  */
	public void setAmtTotal7(BigDecimal AmtTotal7);

	/** Get AmtTotal7.
	  * Monto total 7
	  */
	public BigDecimal getAmtTotal7();

    /** Column name AmtTotal8 */
    public static final String COLUMNNAME_AmtTotal8 = "AmtTotal8";

	/** Set AmtTotal8.
	  * Monto total 8
	  */
	public void setAmtTotal8(BigDecimal AmtTotal8);

	/** Get AmtTotal8.
	  * Monto total 8
	  */
	public BigDecimal getAmtTotal8();

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

    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/** Set Account Date.
	  * Accounting Date
	  */
	public void setDateAcct(Timestamp DateAcct);

	/** Get Account Date.
	  * Accounting Date
	  */
	public Timestamp getDateAcct();

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

    /** Column name DifferenceAmt */
    public static final String COLUMNNAME_DifferenceAmt = "DifferenceAmt";

	/** Set Difference.
	  * Difference Amount
	  */
	public void setDifferenceAmt(BigDecimal DifferenceAmt);

	/** Get Difference.
	  * Difference Amount
	  */
	public BigDecimal getDifferenceAmt();

    /** Column name DifferenceAmt2 */
    public static final String COLUMNNAME_DifferenceAmt2 = "DifferenceAmt2";

	/** Set DifferenceAmt2.
	  * Monto diferencia 2
	  */
	public void setDifferenceAmt2(BigDecimal DifferenceAmt2);

	/** Get DifferenceAmt2.
	  * Monto diferencia 2
	  */
	public BigDecimal getDifferenceAmt2();

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

    /** Column name Posted */
    public static final String COLUMNNAME_Posted = "Posted";

	/** Set Posted.
	  * Posting status
	  */
	public void setPosted(boolean Posted);

	/** Get Posted.
	  * Posting status
	  */
	public boolean isPosted();

    /** Column name ProcessButton */
    public static final String COLUMNNAME_ProcessButton = "ProcessButton";

	/** Set ProcessButton	  */
	public void setProcessButton(String ProcessButton);

	/** Get ProcessButton	  */
	public String getProcessButton();

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

    /** Column name ProcessedOn */
    public static final String COLUMNNAME_ProcessedOn = "ProcessedOn";

	/** Set Processed On.
	  * The date+time (expressed in decimal format) when the document has been processed
	  */
	public void setProcessedOn(BigDecimal ProcessedOn);

	/** Get Processed On.
	  * The date+time (expressed in decimal format) when the document has been processed
	  */
	public BigDecimal getProcessedOn();

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

    /** Column name UUID */
    public static final String COLUMNNAME_UUID = "UUID";

	/** Set Immutable Universally Unique Identifier.
	  * Immutable Universally Unique Identifier
	  */
	public void setUUID(String UUID);

	/** Get Immutable Universally Unique Identifier.
	  * Immutable Universally Unique Identifier
	  */
	public String getUUID();

    /** Column name Z_FormEfectivo_ID */
    public static final String COLUMNNAME_Z_FormEfectivo_ID = "Z_FormEfectivo_ID";

	/** Set Z_FormEfectivo ID	  */
	public void setZ_FormEfectivo_ID(int Z_FormEfectivo_ID);

	/** Get Z_FormEfectivo ID	  */
	public int getZ_FormEfectivo_ID();
}
