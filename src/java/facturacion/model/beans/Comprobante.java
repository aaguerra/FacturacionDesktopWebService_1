/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturacion.model.beans;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author aaguerra
 */
//@Stateless
//@LocalBean
public class Comprobante {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    //recepcion
    private String claveAcceso;
    //autorizacion
    private String estado;
    private String numeroAutorizacion;
    private XMLGregorianCalendar fechaAutorizacion;
    private String comprobante;
    //
    private List<Mensaje> mensajes;

    public Comprobante() {}

    public String getEstado() {
        return estado;
    }

    

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNumeroAutorizacion() {
        return numeroAutorizacion;
    }

    public void setNumeroAutorizacion(String numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }

    public XMLGregorianCalendar getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(XMLGregorianCalendar fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }
    
    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }
    
    
    
    
}
