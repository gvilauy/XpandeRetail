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

/** Generated Interface for Z_ActualizacionPVPLinOrg
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_ActualizacionPVPLinOrg 
{

    /** TableName=Z_ActualizacionPVPLinOrg */
    public static final String Table_Name = "Z_ActualizacionPVPLinOrg";

    /** AD_Table_ID=1000081 */
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

    /** Column name AD_OrgTrx_ID */
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/** Set Trx Organization.
	  * Performing or initiating organization
	  */
	public void setAD_OrgTrx_ID(int AD_OrgTrx_ID);

	/** Get Trx Organization.
	  * Performing or initiating organization
	  */
	public int getAD_OrgTrx_ID();

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

    /** Column name Z_ActualizacionPVPLin_ID */
    public static final String COLUMNNAME_Z_ActualizacionPVPLin_ID = "Z_ActualizacionPVPLin_ID";

	/** Set Z_ActualizacionPVPLin ID	  */
	public void setZ_ActualizacionPVPLin_ID(int Z_ActualizacionPVPLin_ID);

	/** Get Z_ActualizacionPVPLin ID	  */
	public int getZ_ActualizacionPVPLin_ID();

	public org.xpande.retail.model.I_Z_ActualizacionPVPLin getZ_ActualizacionPVPLin() throws RuntimeException;

    /** Column name Z_ActualizacionPVPLinOrg_ID */
    public static final String COLUMNNAME_Z_ActualizacionPVPLinOrg_ID = "Z_ActualizacionPVPLinOrg_ID";

	/** Set Z_ActualizacionPVPLinOrg ID	  */
	public void setZ_ActualizacionPVPLinOrg_ID(int Z_ActualizacionPVPLinOrg_ID);

	/** Get Z_ActualizacionPVPLinOrg ID	  */
	public int getZ_ActualizacionPVPLinOrg_ID();
}
