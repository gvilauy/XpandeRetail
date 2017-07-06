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

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for Z_RecepcionProdFact
 *  @author Adempiere (generated) 
 *  @version Release 3.9.0 - $Id$ */
public class X_Z_RecepcionProdFact extends PO implements I_Z_RecepcionProdFact, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20170704L;

    /** Standard Constructor */
    public X_Z_RecepcionProdFact (Properties ctx, int Z_RecepcionProdFact_ID, String trxName)
    {
      super (ctx, Z_RecepcionProdFact_ID, trxName);
      /** if (Z_RecepcionProdFact_ID == 0)
        {
			setC_Currency_ID (0);
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
			setDocumentSerie (null);
			setManualDocumentNo (null);
			setM_InOut_ID (0);
			setSerieNumberDoc (null);
			setZ_RecepcionProdFact_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Z_RecepcionProdFact (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Z_RecepcionProdFact[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_Currency getC_Currency() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Invoice getC_Invoice() throws RuntimeException
    {
		return (I_C_Invoice)MTable.get(getCtx(), I_C_Invoice.Table_Name)
			.getPO(getC_Invoice_ID(), get_TrxName());	}

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Document Date.
		@param DateDoc 
		Date of the Document
	  */
	public void setDateDoc (Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Document Date.
		@return Date of the Document
	  */
	public Timestamp getDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/** Set DocumentSerie.
		@param DocumentSerie 
		Serie de un Documento
	  */
	public void setDocumentSerie (String DocumentSerie)
	{
		set_Value (COLUMNNAME_DocumentSerie, DocumentSerie);
	}

	/** Get DocumentSerie.
		@return Serie de un Documento
	  */
	public String getDocumentSerie () 
	{
		return (String)get_Value(COLUMNNAME_DocumentSerie);
	}

	/** Set ManualDocumentNo.
		@param ManualDocumentNo 
		Número de documento manual, sin secuencial automático del sistema
	  */
	public void setManualDocumentNo (String ManualDocumentNo)
	{
		set_Value (COLUMNNAME_ManualDocumentNo, ManualDocumentNo);
	}

	/** Get ManualDocumentNo.
		@return Número de documento manual, sin secuencial automático del sistema
	  */
	public String getManualDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_ManualDocumentNo);
	}

	public I_M_InOut getM_InOut() throws RuntimeException
    {
		return (I_M_InOut)MTable.get(getCtx(), I_M_InOut.Table_Name)
			.getPO(getM_InOut_ID(), get_TrxName());	}

	/** Set Shipment/Receipt.
		@param M_InOut_ID 
		Material Shipment Document
	  */
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Shipment/Receipt.
		@return Material Shipment Document
	  */
	public int getM_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SerieNumberDoc.
		@param SerieNumberDoc 
		Concatenación de serie + numero de documento
	  */
	public void setSerieNumberDoc (String SerieNumberDoc)
	{
		set_Value (COLUMNNAME_SerieNumberDoc, SerieNumberDoc);
	}

	/** Get SerieNumberDoc.
		@return Concatenación de serie + numero de documento
	  */
	public String getSerieNumberDoc () 
	{
		return (String)get_Value(COLUMNNAME_SerieNumberDoc);
	}

	/** Set Z_RecepcionProdFact ID.
		@param Z_RecepcionProdFact_ID Z_RecepcionProdFact ID	  */
	public void setZ_RecepcionProdFact_ID (int Z_RecepcionProdFact_ID)
	{
		if (Z_RecepcionProdFact_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Z_RecepcionProdFact_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Z_RecepcionProdFact_ID, Integer.valueOf(Z_RecepcionProdFact_ID));
	}

	/** Get Z_RecepcionProdFact ID.
		@return Z_RecepcionProdFact ID	  */
	public int getZ_RecepcionProdFact_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Z_RecepcionProdFact_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}