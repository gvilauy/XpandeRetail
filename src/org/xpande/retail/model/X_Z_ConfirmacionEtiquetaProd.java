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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for Z_ConfirmacionEtiquetaProd
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_ConfirmacionEtiquetaProd extends PO implements I_Z_ConfirmacionEtiquetaProd, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20180117L;

    /** Standard Constructor */
    public X_Z_ConfirmacionEtiquetaProd (Properties ctx, int Z_ConfirmacionEtiquetaProd_ID, String trxName)
    {
      super (ctx, Z_ConfirmacionEtiquetaProd_ID, trxName);
      /** if (Z_ConfirmacionEtiquetaProd_ID == 0)
        {
			setC_Currency_ID_SO (0);
			setDateValidSO (new Timestamp( System.currentTimeMillis() ));
			setIsOmitted (false);
// N
			setIsPrinted (false);
// N
			setM_Product_ID (0);
			setPriceSO (Env.ZERO);
			setQtyCount (0);
			setWithOfferSO (false);
// N
			setZ_ConfirmacionEtiquetaDoc_ID (0);
			setZ_ConfirmacionEtiquetaProd_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_ConfirmacionEtiquetaProd (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_ConfirmacionEtiquetaProd[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set C_Currency_ID_SO.
		@param C_Currency_ID_SO 
		Moneda de Venta
	  */
	public void setC_Currency_ID_SO (int C_Currency_ID_SO)
	{
		set_Value (COLUMNNAME_C_Currency_ID_SO, Integer.valueOf(C_Currency_ID_SO));
	}

	/** Get C_Currency_ID_SO.
		@return Moneda de Venta
	  */
	public int getC_Currency_ID_SO () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID_SO);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DateValidSO.
		@param DateValidSO 
		Fecha Vigencia Venta
	  */
	public void setDateValidSO (Timestamp DateValidSO)
	{
		set_Value (COLUMNNAME_DateValidSO, DateValidSO);
	}

	/** Get DateValidSO.
		@return Fecha Vigencia Venta
	  */
	public Timestamp getDateValidSO () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateValidSO);
	}

	/** Set ID1.
		@param ID1 ID1	  */
	public void setID1 (int ID1)
	{
		set_Value (COLUMNNAME_ID1, Integer.valueOf(ID1));
	}

	/** Get ID1.
		@return ID1	  */
	public int getID1 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ID1);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ID2.
		@param ID2 ID2	  */
	public void setID2 (int ID2)
	{
		set_Value (COLUMNNAME_ID2, Integer.valueOf(ID2));
	}

	/** Get ID2.
		@return ID2	  */
	public int getID2 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ID2);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ID3.
		@param ID3 ID3	  */
	public void setID3 (int ID3)
	{
		set_Value (COLUMNNAME_ID3, Integer.valueOf(ID3));
	}

	/** Get ID3.
		@return ID3	  */
	public int getID3 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ID3);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ID4.
		@param ID4 ID4	  */
	public void setID4 (int ID4)
	{
		set_Value (COLUMNNAME_ID4, Integer.valueOf(ID4));
	}

	/** Get ID4.
		@return ID4	  */
	public int getID4 () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ID4);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set IsOmitted.
		@param IsOmitted 
		Omitida si o no
	  */
	public void setIsOmitted (boolean IsOmitted)
	{
		set_Value (COLUMNNAME_IsOmitted, Boolean.valueOf(IsOmitted));
	}

	/** Get IsOmitted.
		@return Omitida si o no
	  */
	public boolean isOmitted () 
	{
		Object oo = get_Value(COLUMNNAME_IsOmitted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Printed.
		@param IsPrinted 
		Indicates if this document / line is printed
	  */
	public void setIsPrinted (boolean IsPrinted)
	{
		set_Value (COLUMNNAME_IsPrinted, Boolean.valueOf(IsPrinted));
	}

	/** Get Printed.
		@return Indicates if this document / line is printed
	  */
	public boolean isPrinted () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrinted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Quantity count.
		@param QtyCount 
		Counted Quantity
	  */
	public void setQtyCount (int QtyCount)
	{
		set_Value (COLUMNNAME_QtyCount, Integer.valueOf(QtyCount));
	}

	/** Get Quantity count.
		@return Counted Quantity
	  */
	public int getQtyCount () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QtyCount);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set WithOfferSO.
		@param WithOfferSO 
		Si tiene o no oferta en precio de venta en Retail
	  */
	public void setWithOfferSO (boolean WithOfferSO)
	{
		set_Value (COLUMNNAME_WithOfferSO, Boolean.valueOf(WithOfferSO));
	}

	/** Get WithOfferSO.
		@return Si tiene o no oferta en precio de venta en Retail
	  */
	public boolean isWithOfferSO () 
	{
		Object oo = get_Value(COLUMNNAME_WithOfferSO);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_Z_ConfirmacionEtiquetaDoc getZ_ConfirmacionEtiquetaDoc() throws RuntimeException
    {
		return (I_Z_ConfirmacionEtiquetaDoc)MTable.get(getCtx(), I_Z_ConfirmacionEtiquetaDoc.Table_Name)
			.getPO(getZ_ConfirmacionEtiquetaDoc_ID(), get_TrxName());	}

	/** Set Z_ConfirmacionEtiquetaDoc ID.
		@param Z_ConfirmacionEtiquetaDoc_ID Z_ConfirmacionEtiquetaDoc ID	  */
	public void setZ_ConfirmacionEtiquetaDoc_ID (int Z_ConfirmacionEtiquetaDoc_ID)
	{
		if (Z_ConfirmacionEtiquetaDoc_ID < 1) 
			set_Value (COLUMNNAME_Z_ConfirmacionEtiquetaDoc_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ConfirmacionEtiquetaDoc_ID, Integer.valueOf(Z_ConfirmacionEtiquetaDoc_ID));
	}

	/** Get Z_ConfirmacionEtiquetaDoc ID.
		@return Z_ConfirmacionEtiquetaDoc ID	  */
	public int getZ_ConfirmacionEtiquetaDoc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ConfirmacionEtiquetaDoc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Z_ConfirmacionEtiquetaProd ID.
		@param Z_ConfirmacionEtiquetaProd_ID Z_ConfirmacionEtiquetaProd ID	  */
	public void setZ_ConfirmacionEtiquetaProd_ID (int Z_ConfirmacionEtiquetaProd_ID)
	{
		if (Z_ConfirmacionEtiquetaProd_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_ConfirmacionEtiquetaProd_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_ConfirmacionEtiquetaProd_ID, Integer.valueOf(Z_ConfirmacionEtiquetaProd_ID));
	}

	/** Get Z_ConfirmacionEtiquetaProd ID.
		@return Z_ConfirmacionEtiquetaProd ID	  */
	public int getZ_ConfirmacionEtiquetaProd_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ConfirmacionEtiquetaProd_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_ProductoFamilia getZ_ProductoFamilia() throws RuntimeException
    {
		return (I_Z_ProductoFamilia)MTable.get(getCtx(), I_Z_ProductoFamilia.Table_Name)
			.getPO(getZ_ProductoFamilia_ID(), get_TrxName());	}

	/** Set Z_ProductoFamilia ID.
		@param Z_ProductoFamilia_ID Z_ProductoFamilia ID	  */
	public void setZ_ProductoFamilia_ID (int Z_ProductoFamilia_ID)
	{
		if (Z_ProductoFamilia_ID < 1) 
			set_Value (COLUMNNAME_Z_ProductoFamilia_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ProductoFamilia_ID, Integer.valueOf(Z_ProductoFamilia_ID));
	}

	/** Get Z_ProductoFamilia ID.
		@return Z_ProductoFamilia ID	  */
	public int getZ_ProductoFamilia_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ProductoFamilia_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_ProductoRubro getZ_ProductoRubro() throws RuntimeException
    {
		return (I_Z_ProductoRubro)MTable.get(getCtx(), I_Z_ProductoRubro.Table_Name)
			.getPO(getZ_ProductoRubro_ID(), get_TrxName());	}

	/** Set Z_ProductoRubro ID.
		@param Z_ProductoRubro_ID Z_ProductoRubro ID	  */
	public void setZ_ProductoRubro_ID (int Z_ProductoRubro_ID)
	{
		if (Z_ProductoRubro_ID < 1) 
			set_Value (COLUMNNAME_Z_ProductoRubro_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ProductoRubro_ID, Integer.valueOf(Z_ProductoRubro_ID));
	}

	/** Get Z_ProductoRubro ID.
		@return Z_ProductoRubro ID	  */
	public int getZ_ProductoRubro_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ProductoRubro_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_ProductoSeccion getZ_ProductoSeccion() throws RuntimeException
    {
		return (I_Z_ProductoSeccion)MTable.get(getCtx(), I_Z_ProductoSeccion.Table_Name)
			.getPO(getZ_ProductoSeccion_ID(), get_TrxName());	}

	/** Set Z_ProductoSeccion ID.
		@param Z_ProductoSeccion_ID Z_ProductoSeccion ID	  */
	public void setZ_ProductoSeccion_ID (int Z_ProductoSeccion_ID)
	{
		if (Z_ProductoSeccion_ID < 1) 
			set_Value (COLUMNNAME_Z_ProductoSeccion_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ProductoSeccion_ID, Integer.valueOf(Z_ProductoSeccion_ID));
	}

	/** Get Z_ProductoSeccion ID.
		@return Z_ProductoSeccion ID	  */
	public int getZ_ProductoSeccion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ProductoSeccion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_Z_ProductoSubfamilia getZ_ProductoSubfamilia() throws RuntimeException
    {
		return (I_Z_ProductoSubfamilia)MTable.get(getCtx(), I_Z_ProductoSubfamilia.Table_Name)
			.getPO(getZ_ProductoSubfamilia_ID(), get_TrxName());	}

	/** Set Z_ProductoSubfamilia ID.
		@param Z_ProductoSubfamilia_ID Z_ProductoSubfamilia ID	  */
	public void setZ_ProductoSubfamilia_ID (int Z_ProductoSubfamilia_ID)
	{
		if (Z_ProductoSubfamilia_ID < 1) 
			set_Value (COLUMNNAME_Z_ProductoSubfamilia_ID, null);
		else 
			set_Value (COLUMNNAME_Z_ProductoSubfamilia_ID, Integer.valueOf(Z_ProductoSubfamilia_ID));
	}

	/** Get Z_ProductoSubfamilia ID.
		@return Z_ProductoSubfamilia ID	  */
	public int getZ_ProductoSubfamilia_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_ProductoSubfamilia_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}