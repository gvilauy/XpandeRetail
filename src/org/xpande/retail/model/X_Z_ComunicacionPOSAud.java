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
/** Generated Model - DO NOT CHANGE */
package org.xpande.retail.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for Z_ComunicacionPOSAud
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_ComunicacionPOSAud extends PO implements I_Z_ComunicacionPOSAud, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20171013L;

    /** Standard Constructor */
    public X_Z_ComunicacionPOSAud (Properties ctx, int Z_ComunicacionPOSAud_ID, String trxName)
    {
      super (ctx, Z_ComunicacionPOSAud_ID, trxName);
      /** if (Z_ComunicacionPOSAud_ID == 0)
        {
			setTipoComunicaPOS (null);
			setZ_ComunicacionPOSAud_ID (0);
			setZ_ComunicacionPOS_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_ComunicacionPOSAud (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_Z_ComunicacionPOSAud[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Table getAD_Table() throws RuntimeException
    {
		return (I_AD_Table)MTable.get(getCtx(), I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** CRUDType AD_Reference_ID=1000007 */
	public static final int CRUDTYPE_AD_Reference_ID=1000007;
	/** CREATE = C */
	public static final String CRUDTYPE_CREATE = "C";
	/** UPDATE = U */
	public static final String CRUDTYPE_UPDATE = "U";
	/** DELETE = D */
	public static final String CRUDTYPE_DELETE = "D";
	/** READ = R */
	public static final String CRUDTYPE_READ = "R";
	/** Set CRUDType.
		@param CRUDType 
		Tipo de acción de alta, baja, modificacion o lectura de registro en Base de Datos
	  */
	public void setCRUDType (String CRUDType)
	{

		set_Value (COLUMNNAME_CRUDType, CRUDType);
	}

	/** Get CRUDType.
		@return Tipo de acción de alta, baja, modificacion o lectura de registro en Base de Datos
	  */
	public String getCRUDType () 
	{
		return (String)get_Value(COLUMNNAME_CRUDType);
	}

	public I_M_Product getM_Product() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PriceSO.
		@param PriceSO 
		PriceSO
	  */
	public void setPriceSO (BigDecimal PriceSO)
	{
		set_Value (COLUMNNAME_PriceSO, PriceSO);
	}

	/** Get PriceSO.
		@return PriceSO
	  */
	public BigDecimal getPriceSO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceSO);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** TipoComunicaPOS AD_Reference_ID=1000026 */
	public static final int TIPOCOMUNICAPOS_AD_Reference_ID=1000026;
	/** PRODUCTO = PRODUCTO */
	public static final String TIPOCOMUNICAPOS_PRODUCTO = "PRODUCTO";
	/** CODIGO DE BARRAS = UPC */
	public static final String TIPOCOMUNICAPOS_CODIGODEBARRAS = "UPC";
	/** SOCIO DE NEGOCIO = PARTNER */
	public static final String TIPOCOMUNICAPOS_SOCIODENEGOCIO = "PARTNER";
	/** Set TipoComunicaPOS.
		@param TipoComunicaPOS 
		Tipo de entidad comunicada al POS en modulo de Retail
	  */
	public void setTipoComunicaPOS (String TipoComunicaPOS)
	{

		set_Value (COLUMNNAME_TipoComunicaPOS, TipoComunicaPOS);
	}

	/** Get TipoComunicaPOS.
		@return Tipo de entidad comunicada al POS en modulo de Retail
	  */
	public String getTipoComunicaPOS () 
	{
		return (String)get_Value(COLUMNNAME_TipoComunicaPOS);
	}

	/** Set UPC/EAN.
		@param UPC 
		Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public void setUPC (String UPC)
	{
		set_Value (COLUMNNAME_UPC, UPC);
	}

	/** Get UPC/EAN.
		@return Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public String getUPC () 
	{
		return (String)get_Value(COLUMNNAME_UPC);
	}

	/** Set Z_ComunicacionPOSAud ID.
		@param Z_ComunicacionPOSAud_ID Z_ComunicacionPOSAud ID	  */
	public void setZ_ComunicacionPOSAud_ID (int Z_ComunicacionPOSAud_ID)
	{
		if (Z_ComunicacionPOSAud_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_ComunicacionPOSAud_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_ComunicacionPOSAud_ID, Integer.valueOf(Z_ComunicacionPOSAud_ID));
	}

	/** Get Z_ComunicacionPOSAud ID.
		@return Z_ComunicacionPOSAud ID	  */
	public int getZ_ComunicacionPOSAud_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ComunicacionPOSAud_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_ComunicacionPOS getZ_ComunicacionPOS() throws RuntimeException
    {
		return (I_Z_ComunicacionPOS)MTable.get(getCtx(), I_Z_ComunicacionPOS.Table_Name)
			.getPO(getZ_ComunicacionPOS_ID(), get_TrxName());	}

	/** Set Z_ComunicacionPOS ID.
		@param Z_ComunicacionPOS_ID Z_ComunicacionPOS ID	  */
	public void setZ_ComunicacionPOS_ID (int Z_ComunicacionPOS_ID)
	{
		if (Z_ComunicacionPOS_ID < 1) 
			set_Value (COLUMNNAME_Z_ComunicacionPOS_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ComunicacionPOS_ID, Integer.valueOf(Z_ComunicacionPOS_ID));
	}

	/** Get Z_ComunicacionPOS ID.
		@return Z_ComunicacionPOS ID	  */
	public int getZ_ComunicacionPOS_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ComunicacionPOS_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}