/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturacion.model.beans;

import facturacion.model.beans.DetalleFactura;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author aaguerra
 */
//@Stateless
//@LocalBean
public class Factura {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
     
    private String proveedor;
    private int ambiente;
    private String establecimiento;
    private String punto_emision;
    private String numero_factura;
    private String fecha;
    private String guia_remision;
    private String ci_ruc;
    private String razon_social;
    private String direccion;
    private String email;
    private String detalle_adicional_etiqueta;
    private String detalle_adicional;
    private List<DetalleFactura> list_detalle;
    
    public Factura(){}

    public Factura(String establecimiento, String punto_emision, String numero_factura, String fecha, String guia_remision, String ci_ruc, String razon_social, String direccion, String email, String detalle_adicional_etiqueta, String detalle_adicional) {
        this.establecimiento = establecimiento;
        this.punto_emision = punto_emision;
        this.numero_factura = numero_factura;
        this.fecha = fecha;
        this.guia_remision = guia_remision;
        this.ci_ruc = ci_ruc;
        this.razon_social = razon_social;
        this.direccion = direccion;
        this.email = email;
        this.detalle_adicional_etiqueta = detalle_adicional_etiqueta;
        this.detalle_adicional = detalle_adicional;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    public String getPunto_emision() {
        return punto_emision;
    }

    public void setPunto_emision(String punto_emision) {
        this.punto_emision = punto_emision;
    }

    public String getNumero_factura() {
        return numero_factura;
    }

    public void setNumero_factura(String numero_factura) {
        this.numero_factura = numero_factura;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getGuia_remision() {
        return guia_remision;
    }

    public void setGuia_remision(String guia_remision) {
        this.guia_remision = guia_remision;
    }

    public String getCi_ruc() {
        return ci_ruc;
    }

    public void setCi_ruc(String ci_ruc) {
        this.ci_ruc = ci_ruc;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDetalle_adicional_etiqueta() {
        return detalle_adicional_etiqueta;
    }

    public void setDetalle_adicional_etiqueta(String detalle_adicional_etiqueta) {
        this.detalle_adicional_etiqueta = detalle_adicional_etiqueta;
    }

    public String getDetalle_adicional() {
        return detalle_adicional;
    }

    public void setDetalle_adicional(String detalle_adicional) {
        this.detalle_adicional = detalle_adicional;
    }

    public int getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(int ambiente) {
        this.ambiente = ambiente;
    }
    
    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public List<DetalleFactura> getList_detalle() {
        return list_detalle;
    }

    public void setList_detalle(List<DetalleFactura> list_detalle) {
        this.list_detalle = list_detalle;
    }
    
    
}
