/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturacion.model.beans;

import ec.gob.sri.comprobantes.ws.RespuestaSolicitud;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author aaguerra
 */
//@Stateless
//@LocalBean
public class RespuestaComprobante {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    private int estado;//-1=error del sisema rest 0=no hay errores 1=error de recpcion 2=rror de autorizacion
    private String estadoInfo;
    private String msg;
    private int tipo;//1=recpcion 2=autorizacion
    private List<Comprobante> comprobantes;    

    public RespuestaComprobante() {
    }
    
    public int getTipo() {
        return tipo;
    }

    public String getEstadoInfo() {
        return estadoInfo;
    }

    public void setEstadoInfo(String estadoInfo) {
        this.estadoInfo = estadoInfo;
    }

    public List<Comprobante> getComprobantes() {
        return comprobantes;
    }

    public void setComprobantes(List<Comprobante> comprobantes) {
        this.comprobantes = comprobantes;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
