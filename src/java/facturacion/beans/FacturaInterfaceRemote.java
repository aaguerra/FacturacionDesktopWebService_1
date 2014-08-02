/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturacion.beans;

import javax.ejb.Remote;
/**
 *
 * @author aaguerra
 */
@Remote
public interface FacturaInterfaceRemote {
    public RespuestaComprobantes sendFacturaLote(Facturas facturas);
}
