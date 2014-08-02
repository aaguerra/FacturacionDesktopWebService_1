/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webService;

import facturacion.beans.FacturacionComprobanteRemote;
import facturacion.beans.Facturas;
import facturacion.beans.RespuestaComprobantes;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author aaguerra
 */
@WebService(serviceName = "FacturacionComprobanteWs")
@Stateless()
public class FacturacionComprobanteWs {
    @EJB
    private FacturacionComprobanteRemote ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "sendFacturaLote")
    public RespuestaComprobantes sendFacturaLote(@WebParam(name = "facturas") Facturas facturas) {
        return ejbRef.sendFacturaLote(facturas);
    }
    
}
