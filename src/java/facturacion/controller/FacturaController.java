/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturacion.controller;

import com.itextpdf.text.DocumentException;
import entity.FacturaDesktop;
import entity.PersonaEmpresaUsuario;
import facturacion.beans.RespuestaComprobantes;
import facturacion.ftp.FtpServer;
import facturacion.model.Empresa;
import facturacion.model.Ice;
import facturacion.model.Item;
import facturacion.model.Iva;
import facturacion.model.Pedido;
import facturacion.model.TokenFacturacion;
import facturacion.model.beans.DetalleFactura;
import facturacion.model.beans.Factura;
import facturacion.model.beans.RespuestaComprobante;
import facturacion.pdf.FacturaPdf;
import facturacion.restClient.EmpresaRest;
import facturacion.restClient.TokenFacturacionRest;
import facturacion.utils.Util;
import facturacion.xml.FacturaXml;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import jpaController.FacturaDesktopJpaController;
import org.jdom2.JDOMException;
import org.jdom2.input.DOMBuilder;
import org.jdom2.output.DOMOutputter;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author aaguerra
 */
//@Stateless
//@LocalBean
public class FacturaController {
    private InputStream inputStreamToken;
    private Factura factura;
    private TokenFacturacion token;
    private Empresa proveedor;
    private FacturaXml facturaXml;
    private File fileToken;
    private org.w3c.dom.Document fileFirmado;
    private Pedido pedido;
    List<Item> itemList;
    
    
    public FacturaController(Factura factura){
        this.factura = factura;
    }
    
    public FacturaController(){    }
    
    public void sendEmailClient() throws IOException, DocumentException, MessagingException, TransformerException, JDOMException{        
        FacturaPdf f = new FacturaPdf(pedido, itemList);
    	f.init();    	
        System.out.println("email====="+this.factura.getEmail());
        Util.sendComprobanteEmailXmlPdf(this.factura.getEmail(),"Prueba","este archivo",this.facturaXml.getDocW3c(),f.facturarGenerarDocumentoFile());
    }
    
    public RespuestaComprobante getAutorizacionSri(){
        System.out.println("cale====="+this.facturaXml.getClaveAcceso());
        RespuestaComprobante res = Util.getAutorizacionSri(this.factura.getAmbiente(), this.facturaXml.getClaveAcceso());
        if (res.getEstado() == 0 ) {
            //this.facturaXml.setRespuesta(res.getAutorizacion());
            this.facturaXml.crearFacturaAutorizadaXml(res);
        }
        return res;
    }
    
    public RespuestaComprobante sendComprobanteSri(){
        //return Util.sendComprobanteFileTmp(this.factura.getAmbiente(), this.fileFirmado);
        //return Util.sendComprobanteFileTmp(this.factura.getAmbiente(), new File("C:\\tributasoft\\prueba\\filefimrado2.xml"));
        return Util.sendComprobanteW3cDocumentSri(this.factura.getAmbiente(), this.fileFirmado );
    }
    
    public boolean saveComprobanteDb(RespuestaComprobante respuesata, UserTransaction ctx, EntityManagerFactory em, String cliente){
        FacturaDesktop f = new FacturaDesktop();
        f.setProveedorId("0702144833");
        f.setNumeroAutorizacion(respuesata.getComprobantes().get(0).getNumeroAutorizacion());
        f.setClaveAcceso(respuesata.getComprobantes().get(0).getClaveAcceso());
        PersonaEmpresaUsuario p = new PersonaEmpresaUsuario();
        p.setCiRuc(cliente);
        f.setClienteId(p);
        try {
            FacturaDesktopJpaController jpa = new FacturaDesktopJpaController(ctx, em);
            jpa.create(f);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }    
    }
    
    public void closeFileFirmado(){
        //fileFirmado.delete();
    }
    
    public boolean getInfoProveedor(String ci_ruc){   
        this.proveedor = EmpresaRest.GetEmpresaDetail(ci_ruc);
        if (this.proveedor != null)
            return true;
        else
            return false;
    }
    
    public boolean getTokenData(){
        this.token = TokenFacturacionRest.getUploadTokenFacturacion(this.proveedor.getCi_ruc());
        return false;
    }
    
    public boolean getTokenFile(){
        this.inputStreamToken = FtpServer.getTokenInputStream(this.token.getFila_ruta());
        this.fileToken = Util.inputStreamToFileTmp(inputStreamToken);
        return false;
    }
    
    public boolean createFacturaXml(){
        try {
            Pedido pedido = null;
            //this.facturaXml = new FacturaXml();
            this.pedido =  this.createPedido();
            this.itemList = this.creatItems();
            this.facturaXml = new FacturaXml(this.pedido, this.itemList, this.factura.getAmbiente(), 1, (this.proveedor.getContribuyente().getId() == 1)? "NO":"SI");
            facturaXml.crearXml();
            //facturaXml.firmarFactura()
            // creo al lista de item del pedido
            // creo laa cosas normalmente
            
        } catch (ParserConfigurationException ex) {
            System.out.println("Error al crear el archivo xml");
        }
        return false;
    }
    
    
    public void firmar(){
        /*
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream(this.fileToken.available());
        } catch (IOException ex) {
            System.out.println("Error al crear el archivo xml en ByteArrayOutputStream");
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        InputStream tmp = new ByteArrayInputStream(baos.toByteArray()); 
                */
        this.fileFirmado = Util.firmarComprobante(this.factura.getProveedor(), this.facturaXml.getDoc()
                ,Util.fileToInputStream(this.fileToken)
                , this.token.getClave(), this.factura.getPunto_emision()+this.factura.getNumero_factura());
    }
    
    public void closeTokenFile(){
        try {
            this.inputStreamToken.close();
        } catch (IOException ex) {
            Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.fileToken.delete();
    }
    
    public void crearArchivo(){
        
        try {
            DOMBuilder builder = new DOMBuilder();
            org.jdom2.Document jdom = builder.build( (new DOMOutputter()).output( facturaXml.getDoc() ) );
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            FileWriter a = new FileWriter("C:\\tributasoft\\prueba\\filePrueba"+this.factura.getPunto_emision()+this.factura.getNumero_factura()+".xml");            
            xmlOutput.output(jdom, a);
            a.close();
			facturaXml.getClaveAcceso();
            System.out.println("----> firmar factura +++ 16=="+facturaXml.getClaveAcceso());
        } catch (JDOMException e) {
                // TODO Auto-generated catch block
                System.out.println("----> firmar factura +++ 1");

                e.printStackTrace();

        } catch (NullPointerException e) {
                // TODO Auto-generated catch block
                System.out.println("----> firmar factura +++ 2");
                e.printStackTrace();

        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
    	
    }
    
    private List<Item> creatItems(){
        List<Item> items = new ArrayList<Item>();
        Item item = null;
        int tam = this.factura.getList_detalle().size();
        for (DetalleFactura detalle : this.factura.getList_detalle()){
           item = new Item();
           item.setTipo_item(0);//los trato como si todos fueran productos
           item.setCodigo_empresa(detalle.getCodigo_impuesto());
           item.setCodigo_producto(detalle.getCodigo_impuesto());
           item.setProducto(detalle.getNombre_bien_servicio());
           item.setDetalle("");
           item.setCantidad(String.valueOf(detalle.getCantidad()));
           item.setPvp(detalle.getPrecio_unitario());
           Iva iva = new Iva();
           iva.setId(1);
           iva.setCodigo(Integer.parseInt(detalle.getCodigo_impuesto()));
           iva.setPorcentaje(12f);
           item.setIva(iva);
           item.setIce(new Ice());
           item.setDescuento(0f);
           item.setTotal_facturar(item.totalProcesado(Integer.parseInt(item.getCantidad())*item.getPvp()));
           item.setTotalItem( Float.toString(Integer.parseInt(item.getCantidad())*item.getPvp()) );
           items.add(item);
        }
        
        
        return Util.listaItemsNoDuplicados_(items);
    }
    
    private Pedido createPedido(){
        Pedido pedido = null;
        pedido = new Pedido();
        
        pedido.setProveedor_razon_social(this.proveedor.getRazon_social());
        pedido.setProveedor_ci_ruc(this.proveedor.getCi_ruc());
        pedido.setEstablecimiento_proovedor(this.factura.getEstablecimiento());
        pedido.setPunto_emision(this.factura.getPunto_emision());
        pedido.setNumero_documento(this.factura.getNumero_factura());
        pedido.setProveedor_direccion(this.proveedor.getDireccion()+this.proveedor.getCiudad());
        pedido.setNumero_resolucion("");
        //info factura , datos cliente
        pedido.setFecha_factura(Calendar.getInstance());
        pedido.getFecha_factura().set( Integer.parseInt( this.factura.getFecha().substring(0, 4))
                , Integer.parseInt(this.factura.getFecha().substring(4, 6))-1, Integer.parseInt(this.factura.getFecha().substring(6, 8)) );//ano mes dia agrego una fecha 
        pedido.setDireccion(this.factura.getDireccion());
        pedido.setCliente_razon_social(this.factura.getRazon_social());
        if (this.factura.getCi_ruc().length() == 13) {
            pedido.setCliente_ci_ruc(this.factura.getCi_ruc().substring(0, 10));
            pedido.setEstablecimiento_cliente(this.factura.getCi_ruc().substring(10,13));
        } else {
            pedido.setEstablecimiento_cliente("000");
            pedido.setCliente_ci_ruc(this.factura.getCi_ruc());
        }
        pedido.setEstablecimiento_proovedor_direccion(this.proveedor.getDireccion()+this.proveedor.getCiudad());
        pedido.setDetalle(this.factura.getDetalle_adicional());
        pedido.setPropina( 0f );
        
        return pedido;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public void setToken(TokenFacturacion token) {
        this.token = token;
    }
    
        
}
