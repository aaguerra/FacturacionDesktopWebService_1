/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturacion.beans;

import facturacion.model.beans.RespuestaComprobante;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author aaguerra
 */
//@Stateless
//@LocalBean
public class RespuestaComprobantes {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    private List<RespuestaComprobante> respuestas;
    
    public RespuestaComprobantes() {}

    public List<RespuestaComprobante> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<RespuestaComprobante> respuestas) {
        this.respuestas = respuestas;
    }
}
