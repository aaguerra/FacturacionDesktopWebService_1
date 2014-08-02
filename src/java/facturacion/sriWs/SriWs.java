/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturacion.sriWs;

/**
 *
 * @author aaguerra
 */
public enum SriWs {
    RecepcionComprobantePrueba("https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantes?wsdl"),
    AutorizacionComprobantePrueba("https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantes?wsdl"),
    RecepcionComprobanteProduccion("https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantes?wsdl"),
    AutorizacionComprobanteProduccion("https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantes?wsdl");
    
    private String url;

    SriWs(String url) {
        this.url = url;
    }
    
    public String getUrl(){
        return this.url;
    }
    
}
