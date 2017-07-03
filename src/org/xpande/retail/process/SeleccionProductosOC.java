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
import org.compiere.Adempiere;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.util.Env;
import org.xpande.core.model.MZProductoUPC;
import org.xpande.retail.model.MProductPricing;
import org.xpande.retail.model.MZProductoSocio;

import java.math.BigDecimal;
import java.util.List;

/** Generated Process for (Z_SeleccionProductosOrdenCompra)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.0
 */
public class SeleccionProductosOC extends SeleccionProductosOCAbstract
{

	private MOrder order = null;

	@Override
	protected void prepare()
	{

		this.order = new MOrder(getCtx(), this.getRecord_ID(), get_TrxName());

		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{

		try{

			List<Integer> recordIds =  getSelectionKeys();

			//	Recorro filas de selección de productos que fueron seleccionadas por el usuario
			recordIds.stream().forEach( key -> {

				int mProductId = getSelectionAsInt(key, "PP_M_Product_ID");

				MProduct prod = new MProduct(getCtx(), mProductId, get_TrxName());

				// Inserto producto en nueva linea de orden de compra
				MOrderLine orderLine = new MOrderLine(order);
				orderLine.setM_Product_ID(mProductId);
				orderLine.setQtyEntered(Env.ONE);
				orderLine.setQtyOrdered(Env.ONE);
				orderLine.setC_UOM_ID(prod.getC_UOM_ID());

				MProductPricing productPricing = this.getProductPricing(orderLine);
				if (productPricing == null){
					throw new AdempiereException("No se pudo calcular precios y montos para el producto : " + prod.getValue() + " - " + prod.getName());
				}

				orderLine.setPriceActual(productPricing.getPriceStd());
				orderLine.setPriceList(productPricing.getPriceList());
				orderLine.setPriceLimit(productPricing.getPriceLimit());
				//
				if (orderLine.getQtyEntered().compareTo(orderLine.getQtyOrdered()) == 0)
					orderLine.setPriceEntered(orderLine.getPriceActual());
				else
					orderLine.setPriceEntered(orderLine.getPriceActual().multiply(orderLine.getQtyOrdered()
							.divide(orderLine.getQtyEntered(), 12, BigDecimal.ROUND_HALF_UP)));	//	recision

				//	Calculate Discount
				orderLine.setDiscount(productPricing.getDiscount());

				//	Set UOM
				if(orderLine.getC_UOM_ID() == 0 ){
					orderLine.setC_UOM_ID(productPricing.getC_UOM_ID());
				}

				// Si tengo codigo de barras del producto, lo inserto en la linea
				MZProductoUPC productoUPC = MZProductoUPC.getByProduct(getCtx(), mProductId, null);
				if ((productoUPC != null) && (productoUPC.get_ID() > 0)){
					orderLine.set_ValueOfColumn("UPC", productoUPC.getUPC());
				}

				// Si tengo codigo de producto del proveedor, lo inserto en la linea
				MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(getCtx(), this.order.getC_BPartner_ID(), mProductId, null);
				if ((productoSocio != null) && (productoSocio.get_ID() > 0)){
					if (productoSocio.getVendorProductNo() != null){
						orderLine.set_ValueOfColumn("VendorProductNo", productoSocio.getVendorProductNo());
					}
				}

				orderLine.saveEx();

			});

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return "";
	}

	/**
	 * 	Get and calculate Product Pricing
	 *	@param orderLine
	 *	@return product pricing
	 */
	private MProductPricing getProductPricing (MOrderLine orderLine)
	{
		MProductPricing productPricing = null;

		try{
			productPricing = new MProductPricing (orderLine.getM_Product_ID(), this.order.getC_BPartner_ID(), orderLine.getQtyOrdered(), false, null);
			productPricing.setM_PriceList_ID(this.order.getM_PriceList_ID());
			productPricing.setPriceDate(this.order.getDateOrdered());

			productPricing.calculatePrice(null);

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}
		return productPricing;
	}
}