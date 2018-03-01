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
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.util.Env;
import org.xpande.core.model.MZProductoUPC;
import org.xpande.retail.model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/** Generated Process for (Z_SeleccionRemitosDif)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.0
 */
public class SeleccionRemitosDif extends SeleccionRemitosDifAbstract
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
		try {

			List<Integer> recordIds = getSelectionKeys();

			//	Recorro filas de selección de productos que fueron seleccionadas por el usuario
			recordIds.stream().forEach(key -> {

				MZRemitoDifInv remitoDif = new MZRemitoDifInv(getCtx(), key.intValue(), get_TrxName());
				MInvoice invoiceRemito = (MInvoice) remitoDif.getC_Invoice();

				String documentNoRef = invoiceRemito.get_ValueAsString("DocumentSerie") + invoiceRemito.getDocumentNo();

				// Obtengo lineas del remito que estan abiertas, es decir aún tienen saldo pendiente
				List<MZRemitoDifInvLin> difInvLinList = remitoDif.getNotClosedLines();

				// Reorro y cargao información de estas lineas
				for (MZRemitoDifInvLin difInvLin: difInvLinList){

					MZRemDifInvLinAfecta linAfecta = new MZRemDifInvLinAfecta(getCtx(), 0, get_TrxName());
					linAfecta.setZ_RemitoDifInv_ID(remitoDif.get_ID());
					linAfecta.setZ_RemitoDifInvLin_ID(difInvLin.get_ID());
					linAfecta.setC_Invoice_ID(this.invoice.get_ID());
					linAfecta.setC_UOM_ID(difInvLin.getC_UOM_ID());
					linAfecta.setDocumentNoRef(documentNoRef);
					linAfecta.setIsDifferenceAmt(difInvLin.isDifferenceAmt());
					linAfecta.setIsDifferenceQty(difInvLin.isDifferenceQty());
					linAfecta.setM_Product_ID(difInvLin.getM_Product_ID());
					linAfecta.setRef_Invoice_ID(difInvLin.getC_Invoice_ID());

					// Una linea de remito es por diferencia de cantidad o por diferencia de precio, pero nunca ambas a la vez.
					if (difInvLin.isDifferenceQty()){
						linAfecta.setQtyDocument(difInvLin.getDifferenceQty());
						linAfecta.setQtyOpen(difInvLin.getQtyOpen());
						linAfecta.setQtyEntered(linAfecta.getQtyOpen());
						linAfecta.setPriceDoc(difInvLin.getPriceInvoiced());
					}

					if (difInvLin.isDifferenceAmt()){
						linAfecta.setQtyDocument(difInvLin.getQtyDelivered());
						linAfecta.setQtyOpen(Env.ZERO);
						linAfecta.setQtyEntered(linAfecta.getQtyDocument());
						linAfecta.setPriceDoc(difInvLin.getDifferencePrice());
					}

					linAfecta.setPriceEntered(linAfecta.getPriceDoc());
					linAfecta.setAmtDocument(difInvLin.getDifferenceAmt());
					linAfecta.setAmtOpen(difInvLin.getAmtOpen());
					linAfecta.setLineTotalAmt(linAfecta.getQtyEntered().multiply(linAfecta.getPriceEntered()).setScale(2, RoundingMode.HALF_UP));

					// Genera nueva linea de nota de credito asociada a esta linea del remito por diferencia
					MInvoiceLine invoiceLine = new MInvoiceLine(this.invoice);
					invoiceLine.setM_Product_ID(linAfecta.getM_Product_ID());
					invoiceLine.setQtyEntered(linAfecta.getQtyEntered());
					invoiceLine.setQtyInvoiced(linAfecta.getQtyEntered());
					invoiceLine.setC_UOM_ID(linAfecta.getC_UOM_ID());
					invoiceLine.setPriceActual(linAfecta.getPriceEntered());
					invoiceLine.setPriceList(linAfecta.getPriceEntered());
					invoiceLine.setPriceLimit(linAfecta.getPriceEntered());
					invoiceLine.set_ValueOfColumn("PricePO", invoiceLine.getPriceEntered());
					MZProductoUPC productoUPC = MZProductoUPC.getByProduct(getCtx(), linAfecta.getM_Product_ID(), null);
					if ((productoUPC != null) && (productoUPC.get_ID() > 0)){
						invoiceLine.set_ValueOfColumn("UPC", productoUPC.getUPC());
					}
					MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(getCtx(), this.invoice.getC_BPartner_ID(), linAfecta.getM_Product_ID(), null);
					if ((productoSocio != null) && (productoSocio.get_ID() > 0)){
						invoiceLine.set_ValueOfColumn("VendorProductNo", productoSocio.getVendorProductNo());
					}

					invoiceLine.saveEx();

					// Guardo linea de afectación de remito
					linAfecta.setC_InvoiceLine_ID(invoiceLine.get_ID());
					linAfecta.saveEx();
				}

				/*

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
				invoiceLine.set_ValueOfColumn("PricePO", invoiceLine.getPriceEntered());

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

				*/

			});

		}
		catch (Exception e){
		    throw new AdempiereException(e);
		}

		return "";
	}
}