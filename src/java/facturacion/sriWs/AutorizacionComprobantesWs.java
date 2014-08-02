package facturacion.sriWs;

import ec.gob.sri.comprobantes.ws.Mensaje;
import ec.gob.sri.comprobantes.ws.aut.Autorizacion;
import ec.gob.sri.comprobantes.ws.aut.AutorizacionComprobantes;
import ec.gob.sri.comprobantes.ws.aut.AutorizacionComprobantesService;
import ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante;
import ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante.Autorizaciones;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;


public class AutorizacionComprobantesWs
{
    private AutorizacionComprobantesService service;
    public static final String ESTADO_AUTORIZADO = "AUTORIZADO";
    public static final String ESTADO_NO_AUTORIZADO = "NO AUTORIZADO";

    public AutorizacionComprobantesWs(String wsdlLocation)
    {
        try
        {
            this.service = new AutorizacionComprobantesService(new URL(wsdlLocation), new QName("http://ec.gob.sri.ws.autorizacion", "AutorizacionComprobantesService"));
        }
        catch (MalformedURLException ex)
        {
            Logger.getLogger(AutorizacionComprobantesWs.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
    
    public RespuestaComprobante llamadaWSAutorizacionInd(String claveDeAcceso)
    {
        RespuestaComprobante response = null;
        try
        {
            AutorizacionComprobantes port = this.service.getAutorizacionComprobantesPort();
            response = port.autorizacionComprobante(claveDeAcceso);
        }
        catch (Exception e)
        {
            Logger.getLogger(AutorizacionComprobantesWs.class.getName()).log(Level.SEVERE, null, e);
            return response;
        }
        return response;
    }

    public static RespuestaComprobante autorizarComprobanteIndividual(String claveDeAcceso,String urlWsdl)
    {
        StringBuilder mensaje = new StringBuilder();
        RespuestaComprobante respuesta = null;
        try
        {
            
            for (int i = 0; i < 5; i++)
            {
                respuesta = new AutorizacionComprobantesWs( urlWsdl ).llamadaWSAutorizacionInd(claveDeAcceso);
                if (!respuesta.getAutorizaciones().getAutorizacion().isEmpty()) {
                    break;
                }
                Thread.currentThread();Thread.sleep(300L);
            }
            int i;
            if (respuesta != null)
            {
                /*
                i = 0;
                Autorizaciones auto = respuesta.getAutorizaciones();
                System.err.println("    tam autorizaciones = " + auto.getAutorizacion().size());
                for (Autorizacion item : auto.getAutorizacion())
                {//mensaje.append(item.getEstado());
                    System.err.println("    mensaje estado = " + item.getEstado());
                    System.err.println("    numero autorizacion = " + item.getNumeroAutorizacion());
                    System.err.println("    numero fecha autorizacion = " + item.getFechaAutorizacion());
                    for (ec.gob.sri.comprobantes.ws.aut.Mensaje m : item.getMensajes().getMensaje()){
                        System.err.println("        identificador = " + m.getIdentificador());
                        System.err.println("        informacion adicional = " + m.getInformacionAdicional());
                        System.err.println("        mensaje= " + m.getMensaje());
                        System.err.println("        tipo = " + m.getTipo());
                    }
                    //item.setComprobante("<![CDATA[" + item.getComprobante() + "]]>");
                    //System.err.println(" comprobante = " + item.getComprobante());
                }
                        */
            }
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(AutorizacionComprobantesWs.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

}
