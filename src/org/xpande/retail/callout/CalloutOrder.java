package org.xpande.retail.callout;

import org.compiere.model.*;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.xpande.core.model.MZProductoUPC;
import org.xpande.retail.model.MZProductoSocio;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Callouts para ordenes de compra en el módulo de retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 6/24/17.
 */
public class CalloutOrder extends CalloutEngine {

    /**	Debug Steps			*/
    private boolean steps = false;


    /*************************************************************************
     *	Order Line - Product.
     *		- reset C_Charge_ID / M_AttributeSetInstance_ID
     *		- PriceList, PriceStd, PriceLimit, C_Currency_ID, EnforcePriceLimit
     *		- UOM
     *	Calls Tax
     *
     *  @param ctx context
     *  @param WindowNo current Window No
     *  @param mTab Grid Tab
     *  @param mField Grid Field
     *  @param value New Value
     *  @return null or error message
     */
    public String product (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
    {

        // Xpande. Gabriel Vila.
        // Llamo a callout para setear los datos : codigo de barras - codigo prod.prov - producto
        String message = upcVendProdNoProduct(ctx, WindowNo, mTab, mField, value);
        if (!message.equalsIgnoreCase("")){
            return  message;
        }
        // Xpande.


        Integer M_Product_ID = (Integer)value;
        Integer M_AttributeSetInstance_ID = 0;
        //
        if (M_Product_ID == null || M_Product_ID.intValue() == 0)
        {
            //  If the product information is deleted, zero the other items as well
            mTab.setValue("M_AttributeSetInstance_ID", null);
            mTab.setValue("PriceList", new BigDecimal(0));
            mTab.setValue("PriceLimit", new BigDecimal(0));
            mTab.setValue("PriceActual", new BigDecimal(0));
            mTab.setValue("PriceEntered", new BigDecimal(0));
            mTab.setValue("C_Currency_ID", null);
            mTab.setValue("Discount", new BigDecimal(0));
            mTab.setValue("Discount2", new BigDecimal(0));
            mTab.setValue("C_UOM_ID", null);
            return "";
        }
        if (steps) log.warning("init");

        MProduct product = MProduct.get (ctx, M_Product_ID.intValue());
        I_M_AttributeSetInstance asi = product.getM_AttributeSetInstance();
        //
        mTab.setValue("C_Charge_ID", null);
        //	Set Attribute from context or, if null, from the Product
        //	Get Model and check the Attribute Set Instance from the context
        MProduct m_product = MProduct.get(Env.getCtx(), M_Product_ID);
        mTab.setValue("M_AttributeSetInstance_ID", m_product.getEnvAttributeSetInstance(ctx, WindowNo));
        if (Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "M_Product_ID") == M_Product_ID.intValue()
                && Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "M_AttributeSetInstance_ID") != 0)
            mTab.setValue("M_AttributeSetInstance_ID", Env.getContextAsInt(ctx, WindowNo, Env.TAB_INFO, "M_AttributeSetInstance_ID"));
        else {
            mTab.setValue("M_AttributeSetInstance_ID", asi.getM_AttributeSetInstance_ID());
        }
        /*****	Price Calculation see also qty	****/
        int C_BPartner_ID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_ID");
        BigDecimal Qty = (BigDecimal)mTab.getValue("QtyOrdered");
        boolean IsSOTrx = Env.getContext(ctx, WindowNo, "IsSOTrx").equals("Y");
        org.xpande.retail.model.MProductPricing pp = new org.xpande.retail.model.MProductPricing (M_Product_ID.intValue(), C_BPartner_ID, Qty, IsSOTrx, null);
        //
        int M_PriceList_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_ID");
        pp.setM_PriceList_ID(M_PriceList_ID);
        Timestamp orderDate = (Timestamp)mTab.getValue("DateOrdered");
        /** PLV is only accurate if PL selected in header */
        int M_PriceList_Version_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_Version_ID");
        if ( M_PriceList_Version_ID == 0 && M_PriceList_ID > 0)
        {
            String sql = "SELECT plv.M_PriceList_Version_ID "
                    + "FROM M_PriceList_Version plv "
                    + "WHERE plv.M_PriceList_ID=? "						//	1
                    + " AND plv.ValidFrom <= ? "
                    + "ORDER BY plv.ValidFrom DESC";
            //	Use newest price list - may not be future

            M_PriceList_Version_ID = DB.getSQLValueEx(null, sql, M_PriceList_ID, orderDate);
            if ( M_PriceList_Version_ID > 0 )
                Env.setContext(ctx, WindowNo, "M_PriceList_Version_ID", M_PriceList_Version_ID );
        }
        pp.setM_PriceList_Version_ID(M_PriceList_Version_ID);
        pp.setPriceDate(orderDate);
        //
        mTab.setValue("PriceList", pp.getPriceList());
        mTab.setValue("PriceLimit", pp.getPriceLimit());
        mTab.setValue("PriceActual", pp.getPriceStd());
        mTab.setValue("PriceEntered", pp.getPriceStd());
        mTab.setValue("C_Currency_ID", new Integer(pp.getC_Currency_ID()));
        mTab.setValue("Discount", pp.getDiscount());
        mTab.setValue("C_UOM_ID", new Integer(pp.getC_UOM_ID()));
        mTab.setValue("QtyOrdered", mTab.getValue("QtyEntered"));
        Env.setContext(ctx, WindowNo, "EnforcePriceLimit", pp.isEnforcePriceLimit() ? "Y" : "N");
        Env.setContext(ctx, WindowNo, "DiscountSchema", pp.isDiscountSchema() ? "Y" : "N");

        //	Check/Update Warehouse Setting
        //	int M_Warehouse_ID = Env.getContextAsInt(ctx, WindowNo, "M_Warehouse_ID");
        //	Integer wh = (Integer)mTab.getValue("M_Warehouse_ID");
        //	if (wh.intValue() != M_Warehouse_ID)
        //	{
        //		mTab.setValue("M_Warehouse_ID", new Integer(M_Warehouse_ID));
        //		ADialog.warn(,WindowNo, "WarehouseChanged");
        //	}


        if (Env.isSOTrx(ctx, WindowNo))
        {
            if (product.isStocked())
            {
                BigDecimal QtyOrdered = (BigDecimal)mTab.getValue("QtyOrdered");
                int M_Warehouse_ID = Env.getContextAsInt(ctx, WindowNo, "M_Warehouse_ID");
                M_AttributeSetInstance_ID = Env.getContextAsInt(ctx, WindowNo, "M_AttributeSetInstance_ID");
                BigDecimal available = MStorage.getQtyAvailable
                        (M_Warehouse_ID, M_Product_ID.intValue(), M_AttributeSetInstance_ID, null);
                if (available == null)
                    available = Env.ZERO;
                if (available.signum() == 0)
                    mTab.fireDataStatusEEvent ("NoQtyAvailable", "0", false);
                else if (available.compareTo(QtyOrdered) < 0)
                    mTab.fireDataStatusEEvent ("InsufficientQtyAvailable", available.toString(), false);
                else
                {
                    Integer C_OrderLine_ID = (Integer)mTab.getValue("C_OrderLine_ID");
                    if (C_OrderLine_ID == null)
                        C_OrderLine_ID = new Integer(0);
                    BigDecimal notReserved = MOrderLine.getNotReserved(ctx,
                            M_Warehouse_ID, M_Product_ID, M_AttributeSetInstance_ID,
                            C_OrderLine_ID.intValue());
                    if (notReserved == null)
                        notReserved = Env.ZERO;
                    BigDecimal total = available.subtract(notReserved);
                    if (total.compareTo(QtyOrdered) < 0)
                    {
                        String info = Msg.parseTranslation(ctx, "@QtyAvailable@=" + available
                                + " - @QtyNotReserved@=" + notReserved + " = " + total);
                        mTab.fireDataStatusEEvent ("InsufficientQtyAvailable",
                                info, false);
                    }
                }
            }
        }
        //
        if (steps) log.warning("fini");
        return tax (ctx, WindowNo, mTab, mField, value);
    }	//	product


    /**
     *	Order Line - Charge.
     * 		- updates PriceActual from Charge
     * 		- sets PriceLimit, PriceList to zero
     * 	Calls tax
     *  @param ctx context
     *  @param WindowNo current Window No
     *  @param mTab Grid Tab
     *  @param mField Grid Field
     *  @param value New Value
     *  @return null or error message
     */
    public String charge (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
    {
        Integer C_Charge_ID = (Integer)value;
        if (C_Charge_ID == null || C_Charge_ID.intValue() == 0)
            return "";
        //	No Product defined
        if (mTab.getValue("M_Product_ID") != null)
        {
            mTab.setValue("C_Charge_ID", null);
            return "ChargeExclusively";
        }
        mTab.setValue("M_AttributeSetInstance_ID", null);
        mTab.setValue("S_ResourceAssignment_ID", null);
        mTab.setValue("C_UOM_ID", new Integer(100));	//	EA

        Env.setContext(ctx, WindowNo, "DiscountSchema", "N");
        String sql = "SELECT ChargeAmt FROM C_Charge WHERE C_Charge_ID=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            pstmt = DB.prepareStatement(sql, null);
            pstmt.setInt(1, C_Charge_ID.intValue());
            rs = pstmt.executeQuery();
            if (rs.next())
            {
                mTab.setValue ("PriceEntered", rs.getBigDecimal (1));
                mTab.setValue ("PriceActual", rs.getBigDecimal (1));
                mTab.setValue ("PriceLimit", Env.ZERO);
                mTab.setValue ("PriceList", Env.ZERO);
                mTab.setValue ("Discount", Env.ZERO);
                mTab.setValue ("Discount2", Env.ZERO);
            }
        }
        catch (SQLException e)
        {
            log.log(Level.SEVERE, sql, e);
            return e.getLocalizedMessage();
        }
        finally
        {
            DB.close(rs, pstmt);
            rs = null; pstmt = null;
        }

        //
        return tax (ctx, WindowNo, mTab, mField, value);
    }	//	charge


    /**
     *	Order Line - Tax.
     *		- basis: Product, Charge, BPartner Location
     *		- sets C_Tax_ID
     *  Calls Amount
     *  @param ctx context
     *  @param WindowNo current Window No
     *  @param mTab Grid Tab
     *  @param mField Grid Field
     *  @param value New Value
     *  @return null or error message
     */
    public String tax (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
    {
        String column = mField.getColumnName();
        if (value == null)
            return "";
        if (steps) log.warning("init");

        //	Check Product
        int M_Product_ID = 0;
        if (column.equals("M_Product_ID"))
            M_Product_ID = ((Integer)value).intValue();
        else
            M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
        int C_Charge_ID = 0;
        if (column.equals("C_Charge_ID"))
            C_Charge_ID = ((Integer)value).intValue();
        else
            C_Charge_ID = Env.getContextAsInt(ctx, WindowNo, "C_Charge_ID");
        log.fine("Product=" + M_Product_ID + ", C_Charge_ID=" + C_Charge_ID);
        if (M_Product_ID == 0 && C_Charge_ID == 0)
            return amt(ctx, WindowNo, mTab, mField, value);		//

        //	Check Partner Location
        int shipC_BPartner_Location_ID = 0;
        if (column.equals("C_BPartner_Location_ID"))
            shipC_BPartner_Location_ID = ((Integer)value).intValue();
        else
            shipC_BPartner_Location_ID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_Location_ID");
        if (shipC_BPartner_Location_ID == 0)
            return amt(ctx, WindowNo, mTab, mField, value);		//
        log.fine("Ship BP_Location=" + shipC_BPartner_Location_ID);

        //
        Timestamp billDate = Env.getContextAsDate(ctx, WindowNo, "DateOrdered");
        log.fine("Bill Date=" + billDate);

        Timestamp shipDate = Env.getContextAsDate(ctx, WindowNo, "DatePromised");
        log.fine("Ship Date=" + shipDate);

        int AD_Org_ID = Env.getContextAsInt(ctx, WindowNo, "AD_Org_ID");
        log.fine("Org=" + AD_Org_ID);

        int M_Warehouse_ID = Env.getContextAsInt(ctx, WindowNo, "M_Warehouse_ID");
        log.fine("Warehouse=" + M_Warehouse_ID);

        int billC_BPartner_Location_ID = Env.getContextAsInt(ctx, WindowNo, "Bill_Location_ID");
        if (billC_BPartner_Location_ID == 0)
            billC_BPartner_Location_ID = shipC_BPartner_Location_ID;
        log.fine("Bill BP_Location=" + billC_BPartner_Location_ID);

        //
        int C_Tax_ID = Tax.get (ctx, M_Product_ID, C_Charge_ID, billDate, shipDate,
                AD_Org_ID, M_Warehouse_ID, billC_BPartner_Location_ID, shipC_BPartner_Location_ID,
                "Y".equals(Env.getContext(ctx, WindowNo, "IsSOTrx")));
        log.info("Tax ID=" + C_Tax_ID);
        //
        if (C_Tax_ID == 0)
            mTab.fireDataStatusEEvent(CLogger.retrieveError());
        else
            mTab.setValue("C_Tax_ID", new Integer(C_Tax_ID));
        //
        if (steps) log.warning("fini");
        return amt(ctx, WindowNo, mTab, mField, value);
    }	//	tax


    /**
     *	Order Line - Amount.
     *		- called from QtyOrdered, Discount and PriceActual
     *		- calculates Discount or Actual Amount
     *		- calculates LineNetAmt
     *		- enforces PriceLimit
     *  @param ctx context
     *  @param WindowNo current Window No
     *  @param mTab Grid Tab
     *  @param mField Grid Field
     *  @param value New Value
     *  @return null or error message
     */
    public String amt (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
    {
        if (isCalloutActive() || value == null)
            return "";

        if (steps) log.warning("init");
        int C_UOM_To_ID = Env.getContextAsInt(ctx, WindowNo, "C_UOM_ID");
        int M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
        int M_PriceList_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_ID");
        int StdPrecision = MPriceList.getPricePrecision(ctx, M_PriceList_ID);
        BigDecimal QtyEntered, QtyOrdered, PriceEntered, PriceActual, PriceLimit, Discount, PriceList;
        //	get values
        QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
        QtyOrdered = (BigDecimal)mTab.getValue("QtyOrdered");
        log.fine("QtyEntered=" + QtyEntered + ", Ordered=" + QtyOrdered + ", UOM=" + C_UOM_To_ID);
        //
        PriceEntered = (BigDecimal)mTab.getValue("PriceEntered");
        PriceActual = (BigDecimal)mTab.getValue("PriceActual");
        Discount = (BigDecimal)mTab.getValue("Discount");
        PriceLimit = (BigDecimal)mTab.getValue("PriceLimit");
        PriceList = (BigDecimal)mTab.getValue("PriceList");
        log.fine("PriceList=" + PriceList + ", Limit=" + PriceLimit + ", Precision=" + StdPrecision);
        log.fine("PriceEntered=" + PriceEntered + ", Actual=" + PriceActual + ", Discount=" + Discount);

        //		No Product
        if (M_Product_ID == 0)
        {
            // if price change sync price actual and entered
            // else ignore
            if (mField.getColumnName().equals("PriceActual"))
            {
                PriceEntered = (BigDecimal) value;
                mTab.setValue("PriceEntered", value);
            }
            else if (mField.getColumnName().equals("PriceEntered"))
            {
                PriceActual = (BigDecimal) value;
                mTab.setValue("PriceActual", value);
            }
        }
        //	Product Qty changed - recalc price
        else if ((mField.getColumnName().equals("QtyOrdered")
                || mField.getColumnName().equals("QtyEntered")
                || mField.getColumnName().equals("C_UOM_ID")
                || mField.getColumnName().equals("M_Product_ID"))
                && !"N".equals(Env.getContext(ctx, WindowNo, "DiscountSchema")))
        {
            int C_BPartner_ID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_ID");
            if (mField.getColumnName().equals("QtyEntered"))
                QtyOrdered = MUOMConversion.convertProductFrom (ctx, M_Product_ID,
                        C_UOM_To_ID, QtyEntered);
            if (QtyOrdered == null)
                QtyOrdered = QtyEntered;
            boolean IsSOTrx = Env.getContext(ctx, WindowNo, "IsSOTrx").equals("Y");
            org.xpande.retail.model.MProductPricing pp = new org.xpande.retail.model.MProductPricing (M_Product_ID, C_BPartner_ID, QtyOrdered, IsSOTrx, null);
            pp.setM_PriceList_ID(M_PriceList_ID);
            int M_PriceList_Version_ID = Env.getContextAsInt(ctx, WindowNo, "M_PriceList_Version_ID");
            pp.setM_PriceList_Version_ID(M_PriceList_Version_ID);
            Timestamp date = (Timestamp)mTab.getValue("DateOrdered");
            pp.setPriceDate(date);
            //
            PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID,
                    C_UOM_To_ID, pp.getPriceStd());
            if (PriceEntered == null)
                PriceEntered = pp.getPriceStd();
            //
            log.fine("QtyChanged -> PriceActual=" + pp.getPriceStd()
                    + ", PriceEntered=" + PriceEntered + ", Discount=" + pp.getDiscount());
            PriceActual = pp.getPriceStd();
            mTab.setValue("PriceActual", pp.getPriceStd());
            mTab.setValue("Discount", pp.getDiscount());
            mTab.setValue("PriceEntered", PriceEntered);
            Env.setContext(ctx, WindowNo, "DiscountSchema", pp.isDiscountSchema() ? "Y" : "N");
        }
        else if (mField.getColumnName().equals("PriceActual"))
        {
            PriceActual = (BigDecimal)value;
            PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID,
                    C_UOM_To_ID, PriceActual);
            if (PriceEntered == null)
                PriceEntered = PriceActual;
            //
            log.fine("PriceActual=" + PriceActual
                    + " -> PriceEntered=" + PriceEntered);
            mTab.setValue("PriceEntered", PriceEntered);
        }
        else if (mField.getColumnName().equals("PriceEntered"))
        {
            PriceEntered = (BigDecimal)value;
            PriceActual = MUOMConversion.convertProductTo (ctx, M_Product_ID,
                    C_UOM_To_ID, PriceEntered);
            if (PriceActual == null)
                PriceActual = PriceEntered;
            //
            log.fine("PriceEntered=" + PriceEntered
                    + " -> PriceActual=" + PriceActual);
            mTab.setValue("PriceActual", PriceActual);
        }

        //  Discount entered - Calculate Actual/Entered
        if (mField.getColumnName().equals("Discount"))
        {
            if ( PriceList.doubleValue() != 0 )
                PriceActual = new BigDecimal ((100.0 - Discount.doubleValue()) / 100.0 * PriceList.doubleValue());
            if (PriceActual.scale() > StdPrecision)
                PriceActual = PriceActual.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
            PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID,
                    C_UOM_To_ID, PriceActual);
            if (PriceEntered == null)
                PriceEntered = PriceActual;
            mTab.setValue("PriceActual", PriceActual);
            mTab.setValue("PriceEntered", PriceEntered);
        }
        //	calculate Discount
        else
        {
            if (PriceList.intValue() == 0)
                Discount = Env.ZERO;
            else
                Discount = new BigDecimal ((PriceList.doubleValue() - PriceActual.doubleValue()) / PriceList.doubleValue() * 100.0);
            if (Discount.scale() > 2)
                Discount = Discount.setScale(2, BigDecimal.ROUND_HALF_UP);
            mTab.setValue("Discount", Discount);
        }
        log.fine("PriceEntered=" + PriceEntered + ", Actual=" + PriceActual + ", Discount=" + Discount);

        //	Check PriceLimit
        String epl = Env.getContext(ctx, WindowNo, "EnforcePriceLimit");
        boolean enforce = Env.isSOTrx(ctx, WindowNo) && epl != null && epl.equals("Y");
        if (enforce && MRole.getDefault().isOverwritePriceLimit())
            enforce = false;
        //	Check Price Limit?
        if (enforce && PriceLimit.doubleValue() != 0.0
                && PriceActual.compareTo(PriceLimit) < 0)
        {
            PriceActual = PriceLimit;
            PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID,
                    C_UOM_To_ID, PriceLimit);
            if (PriceEntered == null)
                PriceEntered = PriceLimit;
            log.fine("(under) PriceEntered=" + PriceEntered + ", Actual" + PriceLimit);
            mTab.setValue ("PriceActual", PriceLimit);
            mTab.setValue ("PriceEntered", PriceEntered);
            mTab.fireDataStatusEEvent ("UnderLimitPrice", "", false);
            //	Repeat Discount calc
            if (PriceList.intValue() != 0)
            {
                Discount = new BigDecimal ((PriceList.doubleValue () - PriceActual.doubleValue ()) / PriceList.doubleValue () * 100.0);
                if (Discount.scale () > 2)
                    Discount = Discount.setScale (2, BigDecimal.ROUND_HALF_UP);
                mTab.setValue ("Discount", Discount);
            }
        }

        //	Line Net Amt
        BigDecimal LineNetAmt = QtyOrdered.multiply(PriceActual);
        if (LineNetAmt.scale() > StdPrecision)
            LineNetAmt = LineNetAmt.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
        log.info("LineNetAmt=" + LineNetAmt);
        mTab.setValue("LineNetAmt", LineNetAmt);
        //
        return "";
    }	//	amt

    /**
     *	Order Line - Quantity.
     *		- called from C_UOM_ID, QtyEntered, QtyOrdered
     *		- enforces qty UOM relationship
     *  @param ctx context
     *  @param WindowNo current Window No
     *  @param mTab Grid Tab
     *  @param mField Grid Field
     *  @param value New Value
     *  @return null or error message
     */
    public String qty (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
    {
        if (isCalloutActive() || value == null)
            return "";
        int M_Product_ID = Env.getContextAsInt(ctx, WindowNo, "M_Product_ID");
        if (steps) log.warning("init - M_Product_ID=" + M_Product_ID + " - " );
        BigDecimal QtyOrdered = Env.ZERO;
        BigDecimal QtyEntered, PriceActual, PriceEntered;

        //	No Product
        if (M_Product_ID == 0)
        {
            QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
            QtyOrdered = QtyEntered;
            mTab.setValue("QtyOrdered", QtyOrdered);
        }
        //	UOM Changed - convert from Entered -> Product
        else if (mField.getColumnName().equals("C_UOM_ID"))
        {
            int C_UOM_To_ID = ((Integer)value).intValue();
            QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
            BigDecimal QtyEntered1 = QtyEntered.setScale(MUOM.getPrecision(ctx, C_UOM_To_ID), BigDecimal.ROUND_HALF_UP);
            if (QtyEntered.compareTo(QtyEntered1) != 0)
            {
                log.fine("Corrected QtyEntered Scale UOM=" + C_UOM_To_ID
                        + "; QtyEntered=" + QtyEntered + "->" + QtyEntered1);
                QtyEntered = QtyEntered1;
                mTab.setValue("QtyEntered", QtyEntered);
            }
            QtyOrdered = MUOMConversion.convertProductFrom (ctx, M_Product_ID,
                    C_UOM_To_ID, QtyEntered);
            if (QtyOrdered == null)
                QtyOrdered = QtyEntered;
            boolean conversion = QtyEntered.compareTo(QtyOrdered) != 0;
            PriceActual = (BigDecimal)mTab.getValue("PriceActual");
            PriceEntered = MUOMConversion.convertProductFrom (ctx, M_Product_ID,
                    C_UOM_To_ID, PriceActual);
            if (PriceEntered == null)
                PriceEntered = PriceActual;
            log.fine("UOM=" + C_UOM_To_ID
                    + ", QtyEntered/PriceActual=" + QtyEntered + "/" + PriceActual
                    + " -> " + conversion
                    + " QtyOrdered/PriceEntered=" + QtyOrdered + "/" + PriceEntered);
            Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y" : "N");
            mTab.setValue("QtyOrdered", QtyOrdered);
            mTab.setValue("PriceEntered", PriceEntered);
        }
        //	QtyEntered changed - calculate QtyOrdered
        else if (mField.getColumnName().equals("QtyEntered"))
        {
            int C_UOM_To_ID = Env.getContextAsInt(ctx, WindowNo, "C_UOM_ID");
            QtyEntered = (BigDecimal)value;
            BigDecimal QtyEntered1 = QtyEntered.setScale(MUOM.getPrecision(ctx, C_UOM_To_ID), BigDecimal.ROUND_HALF_UP);
            if (QtyEntered.compareTo(QtyEntered1) != 0)
            {
                log.fine("Corrected QtyEntered Scale UOM=" + C_UOM_To_ID
                        + "; QtyEntered=" + QtyEntered + "->" + QtyEntered1);
                QtyEntered = QtyEntered1;
                mTab.setValue("QtyEntered", QtyEntered);
            }
            QtyOrdered = MUOMConversion.convertProductFrom (ctx, M_Product_ID,
                    C_UOM_To_ID, QtyEntered);
            if (QtyOrdered == null)
                QtyOrdered = QtyEntered;
            boolean conversion = QtyEntered.compareTo(QtyOrdered) != 0;
            log.fine("UOM=" + C_UOM_To_ID
                    + ", QtyEntered=" + QtyEntered
                    + " -> " + conversion
                    + " QtyOrdered=" + QtyOrdered);
            Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y" : "N");
            mTab.setValue("QtyOrdered", QtyOrdered);
        }
        //	QtyOrdered changed - calculate QtyEntered (should not happen)
        else if (mField.getColumnName().equals("QtyOrdered"))
        {
            int C_UOM_To_ID = Env.getContextAsInt(ctx, WindowNo, "C_UOM_ID");
            QtyOrdered = (BigDecimal)value;
            int precision = MProduct.get(ctx, M_Product_ID).getUOMPrecision();
            BigDecimal QtyOrdered1 = QtyOrdered.setScale(precision, BigDecimal.ROUND_HALF_UP);
            if (QtyOrdered.compareTo(QtyOrdered1) != 0)
            {
                log.fine("Corrected QtyOrdered Scale "
                        + QtyOrdered + "->" + QtyOrdered1);
                QtyOrdered = QtyOrdered1;
                mTab.setValue("QtyOrdered", QtyOrdered);
            }
            QtyEntered = MUOMConversion.convertProductTo (ctx, M_Product_ID,
                    C_UOM_To_ID, QtyOrdered);
            if (QtyEntered == null)
                QtyEntered = QtyOrdered;
            boolean conversion = QtyOrdered.compareTo(QtyEntered) != 0;
            log.fine("UOM=" + C_UOM_To_ID
                    + ", QtyOrdered=" + QtyOrdered
                    + " -> " + conversion
                    + " QtyEntered=" + QtyEntered);
            Env.setContext(ctx, WindowNo, "UOMConversion", conversion ? "Y" : "N");
            mTab.setValue("QtyEntered", QtyEntered);
        }
        else
        {
            //	QtyEntered = (BigDecimal)mTab.getValue("QtyEntered");
            QtyOrdered = (BigDecimal)mTab.getValue("QtyOrdered");
        }

        //	Storage
        if (M_Product_ID != 0
                && Env.isSOTrx(ctx, WindowNo)
                && QtyOrdered.signum() > 0)		//	no negative (returns)
        {
            MProduct product = MProduct.get (ctx, M_Product_ID);
            if (product.isStocked())
            {
                int M_Warehouse_ID = Env.getContextAsInt(ctx, WindowNo, "M_Warehouse_ID");
                int M_AttributeSetInstance_ID = Env.getContextAsInt(ctx, WindowNo, "M_AttributeSetInstance_ID");
                BigDecimal available = MStorage.getQtyAvailable
                        (M_Warehouse_ID, M_Product_ID, M_AttributeSetInstance_ID, null);
                if (available == null)
                    available = Env.ZERO;
                if (available.signum() == 0)
                    mTab.fireDataStatusEEvent ("NoQtyAvailable", "0", false);
                else if (available.compareTo(QtyOrdered) < 0)
                    mTab.fireDataStatusEEvent ("InsufficientQtyAvailable", available.toString(), false);
                else
                {
                    Integer C_OrderLine_ID = (Integer)mTab.getValue("C_OrderLine_ID");
                    if (C_OrderLine_ID == null)
                        C_OrderLine_ID = new Integer(0);
                    BigDecimal notReserved = MOrderLine.getNotReserved(ctx,
                            M_Warehouse_ID, M_Product_ID, M_AttributeSetInstance_ID,
                            C_OrderLine_ID.intValue());
                    if (notReserved == null)
                        notReserved = Env.ZERO;
                    BigDecimal total = available.subtract(notReserved);
                    if (total.compareTo(QtyOrdered) < 0)
                    {
                        String info = Msg.parseTranslation(ctx, "@QtyAvailable@=" + available
                                + "  -  @QtyNotReserved@=" + notReserved + "  =  " + total);
                        mTab.fireDataStatusEEvent ("InsufficientQtyAvailable",
                                info, false);
                    }
                }
            }
        }
        //
        return "";
    }	//	qty


    /***
     * Al ingresar código de barras, o codigo de producto del proveedor, o el producto directamente,
     * se deben setear los otros dos valores asociados.
     * Xpande. Created by Gabriel Vila on 6/25/17.
     * @param ctx
     * @param WindowNo
     * @param mTab
     * @param mField
     * @param value
     * @return
     */
    public String upcVendProdNoProduct(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value) {

        if (isCalloutActive()) return "";

        if ((value == null) || (value.toString().trim().equalsIgnoreCase(""))){
            mTab.setValue("UPC", null);
            mTab.setValue("VendorProductNo", null);
            mTab.setValue("M_Product_ID", null);
            return "";
        }

        int cBPartnerID = Env.getContextAsInt(ctx, WindowNo, "C_BPartner_ID");

        String column = mField.getColumnName();

        if (column.equalsIgnoreCase("UPC")){
            MZProductoUPC pupc = MZProductoUPC.getByUPC(ctx, value.toString().trim(), null);
            if ((pupc != null) && (pupc.get_ID() > 0)){
                MProduct prod = (MProduct) pupc.getM_Product();
                MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(ctx, cBPartnerID, prod.get_ID(), null);
                if ((productoSocio == null) || (productoSocio.get_ID() <= 0)){
                    mTab.setValue("VendorProductNo", null);
                    mTab.setValue("M_Product_ID", null);
                    mTab.fireDataStatusEEvent ("Error", "El código de barras ingresado no pertenece a un Producto de este Socio de Negocio.", true);
                }
                else{
                    if (productoSocio.getVendorProductNo() != null){
                        if (!productoSocio.getVendorProductNo().trim().equalsIgnoreCase("")){
                            mTab.setValue("VendorProductNo", productoSocio.getVendorProductNo().trim());
                        }
                    }
                    mTab.setValue("M_Product_ID", prod.get_ID());
                }
            }
            else{
                mTab.setValue("VendorProductNo", null);
                mTab.setValue("M_Product_ID", null);
                mTab.fireDataStatusEEvent ("Error", "No existe Producto con código de barras ingresado", true);
            }
        }
        else if (column.equalsIgnoreCase("VendorProductNo")){
            MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerVendorProdNo(ctx, cBPartnerID, value.toString().trim(), null);
            if ((productoSocio != null) && (productoSocio.get_ID() > 0)){
                MProduct prod = (MProduct) productoSocio.getM_Product();
                MZProductoUPC pupc = MZProductoUPC.getByProduct(ctx, prod.get_ID(), null);
                if ((pupc != null) & (pupc.get_ID() > 0)){
                    mTab.setValue("UPC", pupc.getUPC());
                }
                mTab.setValue("M_Product_ID", prod.get_ID());
            }
            else{
                mTab.setValue("UPC", null);
                mTab.setValue("M_Product_ID", null);
                mTab.fireDataStatusEEvent ("Error", "No existe Producto para el código ingresado de producto del proveedor", true);
            }
        }
        else if (column.equalsIgnoreCase("M_Product_ID")){
            int mProductID = ((Integer) value).intValue();
            MZProductoSocio productoSocio = MZProductoSocio.getByBPartnerProduct(ctx, cBPartnerID, mProductID, null);
            if ((productoSocio != null) || (productoSocio.get_ID() > 0)){
                if (productoSocio.getVendorProductNo() != null){
                    if (!productoSocio.getVendorProductNo().trim().equalsIgnoreCase("")){
                        mTab.setValue("VendorProductNo", productoSocio.getVendorProductNo().trim());
                    }
                    else{
                        mTab.setValue("VendorProductNo", null);
                    }
                }
                else{
                    mTab.setValue("VendorProductNo", null);
                }
            }
            MZProductoUPC pupc = MZProductoUPC.getByProduct(ctx, mProductID, null);
            if ((pupc != null) & (pupc.get_ID() > 0)){
                mTab.setValue("UPC", pupc.getUPC());
            }
            else{
                mTab.setValue("UPC", null);
            }
        }

        return "";
    }
}