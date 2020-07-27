package org.xpande.retail.process;

import org.adempiere.exceptions.AdempiereException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.xpande.retail.model.*;
import uy.com.bullseye.sipc.ws.interfaces.Datos;
import uy.com.bullseye.sipc.ws.interfaces.Productos;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Proceso para declaración de precios al organismo DGC en Retail.
 * Product: Adempiere ERP & CRM Smart Business Solution. Localization : Uruguay - Xpande
 * Xpande. Created by Gabriel Vila on 11/6/17.
 */
public class InterfacePreciosDGC extends SvrProcess {

    private int adOrgID = -1;
    private MZRetailConfigDGC configDGC = null;
    private MZAuditSipc auditSipc = null;

    @Override
    protected void prepare() {

        ProcessInfoParameter[] para = getParameter();

        for (int i = 0; i < para.length; i++){

            String name = para[i].getParameterName();

            if (name != null){
                if (para[i].getParameter() != null){
                    // Organización a procesar
                    if (name.trim().equalsIgnoreCase("AD_Org_ID")){
                        this.adOrgID = ((BigDecimal)para[i].getParameter()).intValueExact();
                    }
                }
            }
        }

    }

    @Override
    protected String doIt() throws Exception {

        String message = "OK";

        try{

            // Obtengo configuraciones para el proceso
            MZRetailConfig retailConfig = MZRetailConfig.getDefault(getCtx(), get_TrxName());
            if (retailConfig == null){
                return "@Error@ " + "No se obtuvieron datos de configuración general de Retail";
            }
            this.configDGC = retailConfig.getConfigDGCByOrg(this.adOrgID);
            if (this.configDGC == null){
                return "@Error@ " + "No se obtuvieron datos de configuración para interface DGC y organización seleccionada.";
            }

            // Insntancio modelo de cabezal de autitoria para este proceso.
            this.auditSipc = new MZAuditSipc(getCtx(), 0, get_TrxName());
            this.auditSipc.setAD_Org_ID(this.adOrgID);
            this.auditSipc.setDateDoc(TimeUtil.trunc(new Timestamp(System.currentTimeMillis()), TimeUtil.TRUNC_DAY));
            this.auditSipc.saveEx();

            ArrayList<Datos> precios = new ArrayList<Datos>();

            String whereClause = this.getSPICProductsWhere();

            precios = this.getPrecios(whereClause);

            message = this.sendPrices(precios);

        }
        catch (Exception e){
            throw new AdempiereException(e);
        }

        return message;
    }


    private String getSPICProductsWhere(){
        String res = "";
        String resAux1 = "";
        String resAux2 = "";

        String select1 = "SELECT pUPC.UPC as UPC, pp.priceList, 'N' as isMailing, p.isactive, p.m_product_id, " +
                        " pp.c_doctype_id, pp.documentnoref, pp.updated, pp.updatedby " +
                        " FROM M_ProductPrice pp JOIN M_Product p";
        String select2 = " UNION SELECT p.codigodgc as UPC, pp.priceList, 'N' as isMailing, p.isactive, p.m_product_id, " +
                        " pp.c_doctype_id, pp.documentnoref, pp.updated, pp.updatedby " +
                        " FROM M_ProductPrice pp JOIN M_Product p";

        String joinWhere = " ON pp.M_Product_ID = p.M_Product_ID"
                +" JOIN z_productoupc pUPC"
                +" ON pp.M_Product_ID = pUPC.M_Product_ID"
                +" WHERE M_PriceList_Version_ID = (SELECT MAX(plV.M_PriceList_Version_ID) FROM M_PriceList pl JOIN M_PriceList_Version plV"
                +" ON pl.M_PriceList_ID = plV.M_PriceList_ID"
                +" WHERE pl.isSOPriceList = 'Y'"
                +" AND pl.ad_org_id = " + this.adOrgID
                +" AND pl.C_Currency_ID = 142"
                +" AND plV.isActive = 'Y')";
        String joinWhere2 = joinWhere;

        try {

            Service service = new Service();
            Call call = (Call) service.createCall();

            // Establecemos la dirección en la que está activado el WebService
            call.setTargetEndpointAddress(new java.net.URL(this.configDGC.getWsdlURL()));

            // Establecemos el nombre del método a invocar
            call.setOperationName(new QName(this.configDGC.getNamespace(), this.configDGC.getDGC_MetodoProducto()));

            // Especificamos el tipo de datos que devuelve el método.
            call.setReturnType(new QName("","Productos"), Productos.class);

            ArrayList<Productos> prodResults = (ArrayList<Productos>) call.invoke(new Object[] {});

            for(Productos prodRes : prodResults){

                System.out.println("DGC : " + prodRes.getCodigo_barra());

                // Guardo linea de auditoria
                MZAuditSipcLin auditSipcLin = new MZAuditSipcLin(getCtx(), 0, get_TrxName());
                auditSipcLin.setZ_AuditSipc_ID(this.auditSipc.get_ID());
                auditSipcLin.setAD_Org_ID(this.auditSipc.getAD_Org_ID());
                auditSipcLin.setName(prodRes.getNombre());

                if(!prodRes.getInterno()){
                    auditSipcLin.setUPC(prodRes.getCodigo_barra());
                    resAux1 += "'" + prodRes.getCodigo_barra() + "',";
                }else{
                    auditSipcLin.setCodigoProducto(prodRes.getCodigo_barra());
                    resAux2 += "'" + prodRes.getCodigo_barra() + "',";
                }

                auditSipcLin.saveEx();
            }

            if(resAux1 != null && resAux1.length() > 0) {
                resAux1 = resAux1.substring(0, resAux1.length()-1);
            }
            if(resAux2 != null && resAux2.length() > 0) {
                resAux2 = resAux2.substring(0, resAux2.length()-1);
            }

            joinWhere += " AND pUPC.UPC in (" + resAux1 + ")";
            joinWhere2 += " AND p.codigodgc in (" + resAux2 + ")";

            res = select1 + joinWhere + select2 + joinWhere2;

        } catch (Exception e) {
            throw new AdempiereException(e.getMessage());
        }

        return res;
    }

    private ArrayList<Datos> getPrecios(String qry){
        ArrayList<Datos> precios = new ArrayList<Datos>();
        Calendar cal = Calendar.getInstance();

        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = DB.prepareStatement(qry, get_TrxName());
            rs = pstmt.executeQuery();
            while (rs.next()) {

                System.out.println(rs.getInt("m_product_id"));

                Datos precio = new Datos();
                precio.setCodigo_establecimiento(this.configDGC.getDGC_Cod_Establecimiento());
                precio.setId_establecimiento(Long.parseLong(this.configDGC.getDGC_ID_Establecimiento()));
                precio.setCodigo_barra(rs.getString(1));
                if(rs.getString(4).equals("Y")){
                    precio.setPrecio(rs.getDouble(2));
                }else{
                    precio.setPrecio((double)0);
                }
                precio.setFecha(cal);
                if(rs.getString(3).equals("Y")){
                    precio.setOferta(true);
                }else{
                    precio.setOferta(false);
                }

                // Obtengo y seteo linea de adutitoria, primero por UPC, sino hay nada, busco por codigo DGC
                String valorBusqueda = rs.getString("upc");
                MZAuditSipcLin auditSipcLin = this.auditSipc.getByUPC(valorBusqueda);
                if ((auditSipcLin == null) || (auditSipcLin.get_ID() <= 0)){
                    auditSipcLin = this.auditSipc.getByCodDGC(valorBusqueda);
                }
                if ((auditSipcLin != null) && (auditSipc.get_ID() > 0)){
                    auditSipcLin.setM_Product_ID(rs.getInt("m_product_id"));
                    auditSipcLin.setPriceSO(new BigDecimal(precio.getPrecio()));
                    auditSipcLin.setWithOfferSO(precio.getOferta());

                    if (rs.getInt("c_doctype_id") > 0){
                        auditSipcLin.setC_DocType_ID(rs.getInt("c_doctype_id"));
                    }
                    String documentNoRef = rs.getString("DocumentNoRef");
                    if ((documentNoRef != null) && (!documentNoRef.trim().equalsIgnoreCase(""))){
                        auditSipcLin.setDocumentNoRef(documentNoRef);
                    }
                    auditSipcLin.setAD_User_ID(rs.getInt("UpdatedBy"));
                    auditSipcLin.setFechaAuditoria(rs.getTimestamp("Updated"));

                    auditSipcLin.saveEx();
                }
                precios.add(precio);
            }

        } catch (Exception e) {
            throw new AdempiereException(e.getMessage());
        } finally {
            DB.close(rs, pstmt);
            rs = null;
            pstmt = null;
        }
        return precios;
    }

    private String sendPrices(ArrayList<Datos> precios){
        String res = "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            MessageFactory factory = MessageFactory.newInstance();
            SOAPMessage soapMsg = factory.createMessage();
            SOAPPart part = soapMsg.getSOAPPart();

            SOAPEnvelope envelope = part.getEnvelope();
            SOAPHeader header = envelope.getHeader();
            SOAPBody body = envelope.getBody();

            envelope.addNamespaceDeclaration("int", this.configDGC.getNamespace());
            SOAPBodyElement element = body.addBodyElement(envelope.createQName(this.configDGC.getDGC_MetodoPrecio(), "int"));

            for(Datos precio : precios){
                Date today = precio.getFecha().getTime();
                String reportDate = df.format(today);

                SOAPElement child = element.addChildElement("precios");
                child.addChildElement("codigo_barra").addTextNode(precio.getCodigo_barra());
                child.addChildElement("codigo_establecimiento").addTextNode(precio.getCodigo_establecimiento());
                child.addChildElement("fecha").addTextNode(reportDate);
                child.addChildElement("id_establecimiento").addTextNode(precio.getId_establecimiento().toString());
                if(precio.getOferta()){
                    child.addChildElement("oferta").addTextNode("true");
                }else{
                    child.addChildElement("oferta").addTextNode("false");
                }
                child.addChildElement("precio").addTextNode(precio.getPrecio().toString());
            }

            soapMsg.saveChanges();
            soapMsg.writeTo(System.out);

            //Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(soapMsg, this.configDGC.getWsdlURL());
            res = soapResponse.getSOAPBody().getFirstChild().getFirstChild().getTextContent();
            System.out.print("\nResponse DGC SOAP Message = "+res);

            soapConnection.close();

        } catch (Exception e) {
            throw new AdempiereException(e.getMessage());
        }

        return res;
    }

}
