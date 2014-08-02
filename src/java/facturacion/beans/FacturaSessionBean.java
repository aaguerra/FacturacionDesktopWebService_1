/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturacion.beans;

import com.itextpdf.text.DocumentException;
import facturacion.controller.FacturaController;
import facturacion.model.beans.Factura;
import facturacion.model.beans.RespuestaComprobante;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.xml.transform.TransformerException;
import org.jdom2.JDOMException;


/**
 *
 * @author aaguerra
 */
@Stateless
@TransactionManagement(value=TransactionManagementType.BEAN)
@LocalBean
public class FacturaSessionBean implements FacturaInterfaceRemote{

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @PersistenceContext(unitName = "EJBModule3PU")
    private EntityManager em;
    @Resource
    private UserTransaction ctx;
    
    
    @Override
    public RespuestaComprobantes sendFacturaLote(Facturas facturas) {
        RespuestaComprobantes respuestas = new RespuestaComprobantes();
        
        respuestas.setRespuestas(new ArrayList<RespuestaComprobante>());
        FacturaController facturaController = new FacturaController();
        
        facturaController.getInfoProveedor(facturas.getFacturas().get(0).getProveedor());                
        
        facturaController.getTokenData();
        System.out.println("=========================");
        facturaController.getTokenFile();
        System.out.println("=========================");
        for (Factura factura : facturas.getFacturas()){
            RespuestaComprobante respuesta = new RespuestaComprobante();
            facturaController.setFactura(factura);
            facturaController.createFacturaXml();
            facturaController.crearArchivo();
            facturaController.firmar();
            //enviarlo
            System.out.println("------------------------");
            facturaController.sendComprobanteSri();
            //imprimirRespuesta(facturaController.sendComprobanteSri(),1);
            System.out.println("+++++++++++++");
            Thread.currentThread();
            try {
                Thread.sleep(9200L);
                //autorizarlo
            } catch (InterruptedException ex) {
                Logger.getLogger(FacturaSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("//////////////////");
            respuesta = facturaController.getAutorizacionSri();
            //imprimirRespuesta(res,2);
            facturaController.saveComprobanteDb(respuesta, ctx, em.getEntityManagerFactory(), null);
            System.out.println("//////////////////");
            if (respuesta.getEstado() == 0) {
                try {
                    facturaController.sendEmailClient();
                } catch (IOException ex) {
                    Logger.getLogger(FacturaSessionBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DocumentException ex) {
                    Logger.getLogger(FacturaSessionBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MessagingException ex) {
                    Logger.getLogger(FacturaSessionBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (TransformerException ex) {
                    Logger.getLogger(FacturaSessionBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JDOMException ex) {
                    Logger.getLogger(FacturaSessionBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("//////////////////");
            //facturaController.closeFileFirmado();
            respuestas.getRespuestas().add(respuesta);
        }
        facturaController.closeTokenFile();
        
        return respuestas;
    }
    
    
}
