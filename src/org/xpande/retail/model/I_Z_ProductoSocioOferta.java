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

/** Generated Interface for Z_ProductoSocioOferta
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0
 */
public interface I_Z_ProductoSocioOferta 
{

    /** TableName=Z_ProductoSocioOferta */
    public static final String Table_Name = "Z_ProductoSocioOferta";

    /** AD_Table_ID=1000184 */
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

    /** Column name EndDate */
    public static final String COLUMNNAME_EndDate = "EndDate";

	/** Set End Date.
	  * Last effective date (inclusive)
	  */
	public void setEndDate(Timestamp EndDate);

	/** Get End Date.
	  * Last effective date (inclusive)
	  */
	public Timestamp getEndDate();

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

    /** Column name StartDate */
    public static final String COLUMNNAME_StartDate = "StartDate";

	/** Set Start Date.
	  * First effective day (inclusive)
	  */
	public void setStartDate(Timestamp StartDate);

	/** Get Start Date.
	  * First effective day (inclusive)
	  */
	public Timestamp getStartDate();

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

    /** Column name Z_OfertaVenta_ID */
    public static final String COLUMNNAME_Z_OfertaVenta_ID = "Z_OfertaVenta_ID";

	/** Set Z_OfertaVenta ID	  */
	public void setZ_OfertaVenta_ID(int Z_OfertaVenta_ID);

	/** Get Z_OfertaVenta ID	  */
	public int getZ_OfertaVenta_ID();

	public I_Z_OfertaVenta getZ_OfertaVenta() throws RuntimeException;

    /** Column name Z_OfertaVentaLinBP_ID */
    public static final String COLUMNNAME_Z_OfertaVentaLinBP_ID = "Z_OfertaVentaLinBP_ID";

	/** Set Z_OfertaVentaLinBP ID	  */
	public void setZ_OfertaVentaLinBP_ID(int Z_OfertaVentaLinBP_ID);

	/** Get Z_OfertaVentaLinBP ID	  */
	public int getZ_OfertaVentaLinBP_ID();

	public I_Z_OfertaVentaLinBP getZ_OfertaVentaLinBP() throws RuntimeException;

    /** Column name Z_OfertaVentaLin_ID */
    public static final String COLUMNNAME_Z_OfertaVentaLin_ID = "Z_OfertaVentaLin_ID";

	/** Set Z_OfertaVentaLin ID	  */
	public void setZ_OfertaVentaLin_ID(int Z_OfertaVentaLin_ID);

	/** Get Z_OfertaVentaLin ID	  */
	public int getZ_OfertaVentaLin_ID();

	public I_Z_OfertaVentaLin getZ_OfertaVentaLin() throws RuntimeException;

    /** Column name Z_ProductoSocio_ID */
    public static final String COLUMNNAME_Z_ProductoSocio_ID = "Z_ProductoSocio_ID";

	/** Set Z_ProductoSocio ID	  */
	public void setZ_ProductoSocio_ID(int Z_ProductoSocio_ID);

	/** Get Z_ProductoSocio ID	  */
	public int getZ_ProductoSocio_ID();

	public I_Z_ProductoSocio getZ_ProductoSocio() throws RuntimeException;

    /** Column name Z_ProductoSocioOferta_ID */
    public static final String COLUMNNAME_Z_ProductoSocioOferta_ID = "Z_ProductoSocioOferta_ID";

	/** Set Z_ProductoSocioOferta ID	  */
	public void setZ_ProductoSocioOferta_ID(int Z_ProductoSocioOferta_ID);

	/** Get Z_ProductoSocioOferta ID	  */
	public int getZ_ProductoSocioOferta_ID();
}
