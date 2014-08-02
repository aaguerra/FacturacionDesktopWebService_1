/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturacion.utils;

import ec.gob.sri.comprobantes.ws.Comprobante;
import ec.gob.sri.comprobantes.ws.Comprobante.Mensajes;
import ec.gob.sri.comprobantes.ws.Mensaje;
import ec.gob.sri.comprobantes.ws.RespuestaSolicitud;
import ec.gob.sri.comprobantes.ws.RespuestaSolicitud.Comprobantes;
import ec.gob.sri.comprobantes.ws.aut.Autorizacion;
import facturacion.model.beans.RespuestaComprobante;
import facturacion.firmarComprobante.XAdESBESSignature;
import facturacion.model.Item;
import facturacion.sriWs.AutorizacionComprobantesWs;
import facturacion.sriWs.EnvioComprobantesWs;
import facturacion.sriWs.SriWs;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.DOMBuilder;
import org.jdom2.output.DOMOutputter;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
/**
 *
 * @author aaguerra
 */
public class Util {
    
    
    public static void sendComprobanteEmailXmlPdf(String para, String asunto, String texto
            ,org.w3c.dom.Document comprobanteFirmado, File pdf) throws MessagingException, IOException, TransformerException{
        System.out.println("casas ------------ = entre a la aprte de enviar el xml 7777777");
    	Email email = new Email(para, asunto, texto);
    	email.addArchivo(createXml(comprobanteFirmado));
    	email.addArchivo(pdf);
    	email.send();
    	System.out.println("casas ------------ = entre a la aprte de enviar el xml 88888");
    }
    
    private static File createXml(org.w3c.dom.Document xml) throws IOException, TransformerException{
    	File archivo = File.createTempFile("tmpFacturaXml", ".xml");
    	//archivo.setp
    	//for output to file, console
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        //for pretty print
        //configuraciones del archivo
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        
        //transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        //transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        //transformer.setOutputProperty(OutputKeys.ENCODING, "US-ASCII");
        DOMSource source = new DOMSource( xml );

        //write to console or file
        //Writer out = new OutputStreamWriter(new FileOutputStream(archivo), "ISO-8859-1");
        //Writer out = new OutputStreamWriter(new FileOutputStream(archivo), "UTF-8");
        //StreamResult console = new StreamResult(System.out);
        StreamResult file = new StreamResult( archivo );

        //write data
        //transformer.transform(source, console);
        //transformer.transform(source, new StreamResult( out ) );
        //transformer.transform(source, new StreamResult( out ) );
        transformer.transform(source, file);
    	return archivo;
    }
    
    public static InputStream fileToInputStream(File file){
        InputStream inputStream = null;
        try {
            if (file == null) System.out.println("archivo llego nulo");
            else System.out.println("archivo no llego nulo");
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        return inputStream;
    }
    
    public static File inputStreamToFileTmp(InputStream inputStream){
	OutputStream outputStream = null;
        File file = null;
	try {
             file= File.createTempFile("tmpToken",".p12");
		// read this file into InputStream
		//inputStream = new FileInputStream("/Users/mkyong/Downloads/holder.js");
 
		// write the inputStream to a FileOutputStream
		outputStream = 
                    new FileOutputStream( file );
 
		int read = 0;
		byte[] bytes = new byte[1024];
 
		while ((read = inputStream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, read);
		}
 
		System.out.println("Done!");
        } catch (java.lang.NullPointerException e){
            System.out.println("el archivo llego nulo del ervidor ftp");
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (outputStream != null) {
			try {
				// outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
 
		}
	}
        return file;
    }
    
    public static org.w3c.dom.Document firmarComprobante(String proveedor, Document doc,InputStream token, String clave, String data){
        org.w3c.dom.Document fileFirmado = null;
        File fileF = null;
        File aa = null;
        try {    		
            // validar que el token es null ante de firmarlo
            

            XAdESBESSignature firmar = new XAdESBESSignature((new DOMOutputter()).output( doc ));
            //XAdESBESSignature firmar = new XAdESBESSignature();
            fileFirmado = firmar.execute(token, clave);
            //fileFirmado = firmar.execute();
            
            //token.setFile(FtpServer.getTokenInputStream3(token.getFila_ruta()));
            //this.fileFirmado = firmar.execute(token.documentToInputStream(), token.getClave());
            //this.fileFirmado = firmar.execute();
            //creo el archivo firmado
			
            DOMBuilder builder = new DOMBuilder();
            Document jdom = builder.build(fileFirmado);
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            fileF =  File.createTempFile("fileFirmado", ".xml");
            //aa = new File("C:\\tributasoft\\prueba\\filePruebaFirmado"+data+".xml");
            //FileWriter a = new FileWriter("C:\\tributasoft\\prueba\\filePruebaFirmado"+data+".xml");            
            //FileWriter a = new FileWriter(fileF);
            //FileWriter aaa = new FileWriter(aa);
            //xmlOutput.output(jdom, a);
            //xmlOutput.output(jdom, aaa);
            //aaa.close();
            //a.close();
            
			//this.clave_acceso = this.factura.getClaveAcceso();
            System.out.println("----> firmar factura +++ 16");
        } catch (JDOMException e) {
                // TODO Auto-generated catch block
                //System.out.println("----> firmar factura +++ 1");

                e.printStackTrace();
                //return ErrorSri.TOKEN;
        } catch (NullPointerException e) {
                // TODO Auto-generated catch block
                //System.out.println("----> firmar factura +++ 2");
                e.printStackTrace();
                //return ErrorSri.TOKEN;
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
        
    	return fileFirmado;
    	//return ErrorSri.FIRMADO;
    }
    
    
    public static RespuestaComprobante getAutorizacionSri(int ambiente, String claveAcceso ){
        RespuestaComprobante respuesta = new RespuestaComprobante();
        ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante r = null;
        if (ambiente == 1)
            r = AutorizacionComprobantesWs.autorizarComprobanteIndividual(claveAcceso, SriWs.AutorizacionComprobantePrueba.getUrl() );
        else
            r = AutorizacionComprobantesWs.autorizarComprobanteIndividual(claveAcceso, SriWs.AutorizacionComprobanteProduccion.getUrl() );
        if (r != null){
            copyAutorizacionComprobante(respuesta, r, 0,claveAcceso);
        } else {
            copyAutorizacionComprobante(respuesta, r, 2,claveAcceso);
        }
        
        return respuesta;
    }
    
    public static void copyAutorizacionComprobante(RespuestaComprobante r, ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante rc, int estado, String claveAcceso){
        r.setEstado(estado);
        r.setTipo(2);
        r.setComprobantes(new ArrayList<facturacion.model.beans.Comprobante>());
        ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante.Autorizaciones auto = rc.getAutorizaciones();
        System.err.println("    tam autorizaciones = " + auto.getAutorizacion().size());
        for (Autorizacion item : auto.getAutorizacion())
        {//mensaje.append(item.getEstado());
            facturacion.model.beans.Comprobante comprobante = new facturacion.model.beans.Comprobante();
            comprobante.setEstado(item.getEstado());
            comprobante.setNumeroAutorizacion(item.getNumeroAutorizacion());
            comprobante.setClaveAcceso(claveAcceso);
            comprobante.setComprobante(item.getComprobante());
            comprobante.setFechaAutorizacion(item.getFechaAutorizacion());
            System.err.println("    mensaje estado = " + item.getEstado());
            System.err.println("    numero autorizacion = " + item.getNumeroAutorizacion());
            System.err.println("    numero fecha autorizacion = " + item.getFechaAutorizacion());
            comprobante.setMensajes(new ArrayList<facturacion.model.beans.Mensaje>());
            for (ec.gob.sri.comprobantes.ws.aut.Mensaje m : item.getMensajes().getMensaje()){
                facturacion.model.beans.Mensaje mensaje = new facturacion.model.beans.Mensaje();
                mensaje.setIdentificador(m.getIdentificador());
                mensaje.setInformacionAdicional(m.getInformacionAdicional());
                mensaje.setMensaje(m.getMensaje());
                mensaje.setTipo(m.getTipo());
                System.err.println("        identificador = " + m.getIdentificador());
                System.err.println("        informacion adicional = " + m.getInformacionAdicional());
                System.err.println("        mensaje= " + m.getMensaje());
                System.err.println("        tipo = " + m.getTipo());
                comprobante.getMensajes().add(mensaje);
            }
            //item.setComprobante("<![CDATA[" + item.getComprobante() + "]]>");
            //System.err.println(" comprobante = " + item.getComprobante());
            r.getComprobantes().add(comprobante);
        }
    }
    
    
    public static RespuestaComprobante sendComprobanteFileTmp(int ambiente, File fileFirmado){
         RespuestaComprobante respuesta = new RespuestaComprobante();
        RespuestaSolicitud respuestaRecepcion = new RespuestaSolicitud();
        //File aaa = new File("C:\\Users\\aaguerra\\Desktop\\filefimrado1.xml");
        
        if (ambiente == 1){
            System.err.println("============================================================================ 1 "  );
            respuestaRecepcion = EnvioComprobantesWs.obtenerRespuestaEnvio(fileFirmado, SriWs.RecepcionComprobantePrueba.getUrl());
        } else {
            respuestaRecepcion = EnvioComprobantesWs.obtenerRespuestaEnvio(fileFirmado, SriWs.RecepcionComprobanteProduccion.getUrl());
        }
        if (respuestaRecepcion != null) {
            //System.out.println("estado =" + respuestaRecepcion.getEstado()+"=" );
            if (respuestaRecepcion.getEstado().equals("DEVUELTA")) {
                System.out.println("estado f=" + respuestaRecepcion.getEstado());
                copyRespuestaSolicitud(respuesta, respuestaRecepcion, 1);
                return respuesta;
            }
            else if (respuestaRecepcion.getEstado().equals( "RECIBIDA") ) {
                System.out.println("estadorrr f=" + respuestaRecepcion.getEstado());
                copyRespuestaSolicitud(respuesta, respuestaRecepcion, 0);
                return respuesta;
            }
            else {
                copyRespuestaSolicitud(respuesta, respuestaRecepcion, -1);
                return respuesta;
            }
        	
            
			
        } else {
            copyRespuestaSolicitud(respuesta, respuestaRecepcion, 1);
            return respuesta;
        }
    }
    
    public static void copyRespuestaSolicitud(RespuestaComprobante r, RespuestaSolicitud rs, int estado){
        r.setEstado(estado);
        r.setEstadoInfo(rs.getEstado());
        r.setTipo(1);
        r.setComprobantes(new ArrayList<facturacion.model.beans.Comprobante>());
        Comprobantes listcom = rs.getComprobantes();
        for( Comprobante com : listcom.getComprobante()) {
            facturacion.model.beans.Comprobante comprobante = new facturacion.model.beans.Comprobante();
            comprobante.setClaveAcceso(com.getClaveAcceso());
            comprobante.setMensajes(new ArrayList<facturacion.model.beans.Mensaje>());
            System.err.println("clave  = " + com.getClaveAcceso() );
            Mensajes men =  com.getMensajes();            
            for( Mensaje m : men.getMensaje()) {
                facturacion.model.beans.Mensaje mensaje = new facturacion.model.beans.Mensaje();
                mensaje.setIdentificador(m.getIdentificador());
                mensaje.setInformacionAdicional(m.getInformacionAdicional());
                mensaje.setMensaje(m.getMensaje());
                mensaje.setTipo(m.getTipo());
                System.err.println("    identificador = " + m.getIdentificador() );
                System.err.println("    informacion adicional= " + m.getInformacionAdicional() );
                System.err.println("    mensaje  = " + m.getMensaje() );
                System.err.println("    tipo  = " + m.getTipo() );
                System.err.println("    +++++"  );
                comprobante.getMensajes().add(mensaje);
            }
            r.getComprobantes().add(comprobante);
        }
    }
    
    public static RespuestaComprobante sendComprobanteW3cDocumentSri(int ambiente, org.w3c.dom.Document fileFirmado){
        RespuestaComprobante respuesta = new RespuestaComprobante();
        RespuestaSolicitud respuestaRecepcion = new RespuestaSolicitud();
        //File aaa = new File("C:\\Users\\aaguerra\\Desktop\\filefimrado1.xml");
        
        if (ambiente == 1){
            System.err.println("============================================================================ 1 "  );
            respuestaRecepcion = EnvioComprobantesWs.obtenerRespuestaEnvio(fileFirmado, SriWs.RecepcionComprobantePrueba.getUrl());
        } else {
            respuestaRecepcion = EnvioComprobantesWs.obtenerRespuestaEnvio(fileFirmado, SriWs.RecepcionComprobanteProduccion.getUrl());
        }
        if (respuestaRecepcion != null) {
            //System.out.println("estado =" + respuestaRecepcion.getEstado()+"=" );
            if (respuestaRecepcion.getEstado().equals("DEVUELTA")) {
                System.out.println("estado f=" + respuestaRecepcion.getEstado());
                copyRespuestaSolicitud(respuesta, respuestaRecepcion, 1);
                return respuesta;
            }
            else if (respuestaRecepcion.getEstado().equals( "RECIBIDA") ) {
                System.out.println("estadorrr f=" + respuestaRecepcion.getEstado());
                copyRespuestaSolicitud(respuesta, respuestaRecepcion, 0);
                return respuesta;
            }
            else {
                copyRespuestaSolicitud(respuesta, respuestaRecepcion, -1);
                return respuesta;
            }
        	
            
			
        } else {
            copyRespuestaSolicitud(respuesta, respuestaRecepcion, 1);
            return respuesta;
        }
    }
    
    public static List<Item> listaItemsNoDuplicados_(List<Item> detalleItemListx) {
        List<Item> items = new ArrayList<Item>();
        boolean a= true;
        for (Item i : detalleItemListx){
            if(items.size() == 0){
                items.add(i.copy());
            } else {
                a= true;
                for(Item ii : items){
                    if (ii.equals1(i)){
                        a = false;
                        break;
                    }
                }
                if(a){
                    items.add(i.copy());
                }
            }

        }
        System.out.println("TAM VIEJA LIST: "+ detalleItemListx.size());
        System.out.println("TAM NUEVA LIST: "+ items.size());
        return items;
}
    
    
}
