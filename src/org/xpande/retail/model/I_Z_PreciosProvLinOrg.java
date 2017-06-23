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

/** Generated Interface for Z_PreciosProvLinOrg
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_PreciosProvLinOrg 
{

    /** TableName=Z_PreciosProvLinOrg */
    public static final String Table_Name = "Z_PreciosProvLinOrg";

    /** AD_Table_ID=1000056 */
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

    /** Column name Z_PreciosProvLin_ID */
    public static final String COLUMNNAME_Z_PreciosProvLin_ID = "Z_PreciosProvLin_ID";

	/** Set Z_PreciosProvLin ID	  */
	public void setZ_PreciosProvLin_ID(int Z_PreciosProvLin_ID);

	/** Get Z_PreciosProvLin ID	  */
	public int getZ_PreciosProvLin_ID();

	public I_Z_PreciosProvLin getZ_PreciosProvLin() throws RuntimeException;

    /** Column name Z_PreciosProvLinOrg_ID */
    public static final String COLUMNNAME_Z_PreciosProvLinOrg_ID = "Z_PreciosProvLinOrg_ID";

	/** Set Z_PreciosProvLinOrg ID	  */
	public void setZ_PreciosProvLinOrg_ID(int Z_PreciosProvLinOrg_ID);

	/** Get Z_PreciosProvLinOrg ID	  */
	public int getZ_PreciosProvLinOrg_ID();
}
