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

package org.xpande.retail.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.xpande.core.model.MZProductoUPC;
import org.xpande.retail.model.MProductPricing;
import org.xpande.retail.model.MZProductoSocio;

import java.math.BigDecimal;
import java.util.List;

/** Generated Process for (Z_SeleccionProductosCompProv)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.0
 */
public class SeleccionProductosCompProv extends SeleccionProductosCompProvAbstract
{

	private MInvoice invoice = null;

	@Override
	protected void prepare()
	{
		this.invoice = new MInvoice(getCtx(), this.getRecord_ID(), get_TrxName());

		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{
		try{

			List<Integer> recordIds =  getSelectionKeys();

			//	Recorro filas de selección de productos que fueron seleccionadas por el usuario
			recordIds.stream().forEach( key -> {

				MZProductoSocio productoSocio = new MZProductoSocio(getCtx(), key.intValue(), get_TrxName());
				MProduct prod = (MProduct) productoSocio.getM_Product();
				MPriceList priceList = (MPriceList) productoSocio.getM_PriceList();

				// Inserto producto en nueva linea de comprobante
				MInvoiceLine invoiceLine = new MInvoiceLine(this.invoice);
				invoiceLine.setM_Product_ID(prod.get_ID());
				invoiceLine.setQtyEntered(Env.ONE);
				invoiceLine.setQtyInvoiced(Env.ONE);
				invoiceLine.setC_UOM_ID(prod.getC_UOM_ID());

				MProductPricing productPricing = this.getProductPricing(invoiceLine, priceList);
				if (productPricing == null){
					throw new AdempiereException("No se pudo calcular precios y montos para el producto : " + prod.getValue() + " - " + prod.getName());
				}

				invoiceLine.setPriceActual(productPricing.getPriceStd());
				invoiceLine.setPriceList(productPricing.getPriceList());
				invoiceLine.setPriceLimit(productPricing.getPriceLimit());

				if (productPricing.isCostoHistorico()){
					invoiceLine.set_ValueOfColumn("PricePO", productPricing.getPricePO());
					invoiceLine.set_ValueOfColumn("PricePONoDto", productPricing.getPricePO());
				}
				else{
					invoiceLine.set_ValueOfColumn("PricePO", invoiceLine.getPriceEntered());
					invoiceLine.set_ValueOfColumn("PricePONoDto", invoiceLine.getPriceEntered());
				}

				//
				if (invoiceLine.getQtyEntered().compareTo(invoiceLine.getQtyInvoiced()) == 0)
					invoiceLine.setPriceEntered(invoiceLine.getPriceActual());
				else
					invoiceLine.setPriceEntered(invoiceLine.getPriceActual().multiply(invoiceLine.getQtyInvoiced()
							.divide(invoiceLine.getQtyEntered(), 12, BigDecimal.ROUND_HALF_UP)));	//	recision

				//	Calculate Discount
				//invoiceLine.setDiscount(productPricing.getDiscount());

				//	Set UOM
				if(invoiceLine.getC_UOM_ID() == 0 ){
					invoiceLine.setC_UOM_ID(productPricing.getC_UOM_ID());
				}

				// Si tengo codigo de barras del producto, lo inserto en la linea
				MZProductoUPC productoUPC = MZProductoUPC.getByProduct(getCtx(), prod.get_ID(), null);
				if ((productoUPC != null) && (productoUPC.get_ID() > 0)){
					invoiceLine.set_ValueOfColumn("UPC", productoUPC.getUPC());
				}

				// Si tengo codigo de producto del proveedor, lo inserto en la linea
				if (productoSocio.getVendorProductNo() != null){
					invoiceLine.set_ValueOfColumn("VendorProductNo", productoSocio.getVendorProductNo());
				}

				invoiceLine.saveEx();

			});

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}

		return "";

	}

	/**
	 * 	Get and calculate Product Pricing
	 *	@param invoiceLine
	 *  @param priceList
	 *	@return product pricing
	 */
	private MProductPricing getProductPricing (MInvoiceLine invoiceLine, MPriceList priceList)
	{
		MProductPricing productPricing = null;

		try{
			productPricing = new MProductPricing (invoiceLine.getM_Product_ID(), this.invoice.getC_BPartner_ID(), this.invoice.getAD_Org_ID(),
					this.invoice.getDateInvoiced(), invoiceLine.getQtyInvoiced(), false, null);
			productPricing.setM_PriceList_ID(priceList.get_ID());
			productPricing.setPriceDate(this.invoice.getDateInvoiced());

			productPricing.calculatePrice();

		}
		catch (Exception e){
			throw new AdempiereException(e);
		}
		return productPricing;
	}
}