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

import org.xpande.retail.model.MZPautaComercialSet;
import org.xpande.retail.model.MZProductoSocio;

import java.util.List;

/** Generated Process for (Z_SeleccionProductosPauta)
 *  @author ADempiere (generated) 
 *  @version Release 3.9.0
 */
public class SeleccionProductosPauta extends SeleccionProductosPautaAbstract
{
	private MZPautaComercialSet pautaComercialSet = null;

	@Override
	protected void prepare()
	{
		this.pautaComercialSet = new MZPautaComercialSet(getCtx(), this.getRecord_ID(), get_TrxName());

		super.prepare();
	}

	@Override
	protected String doIt() throws Exception
	{

		List<Integer> recordIds =  getSelectionKeys();

		//	Recorro filas de selección de productos que fueron seleccionadas por el usuario
		recordIds.stream().forEach( key -> {

			MZProductoSocio productoSocio = new MZProductoSocio(getCtx(), key.intValue(), get_TrxName());

			// Inserto producto en este segmento.
			this.pautaComercialSet.insertProduct(productoSocio.getM_Product_ID());

		});

		return "";
	}
}