/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturacion.model.beans;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author aaguerra
 */
//@Stateless
//@LocalBean
public class DetalleFactura {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private String codigo_bien_servicio;
    private int cantidad;
    private String nombre_bien_servicio;
    private float precio_unitario;
    private String codigo_impuesto;
    private float valor_ice;

    public DetalleFactura(String codigo_bien_servicio, int cantidad, String nombre_bien_servicio, float precio_unitario, String codigo_impuesto, float valor_ice) {
        this.codigo_bien_servicio = codigo_bien_servicio;
        this.cantidad = cantidad;
        this.nombre_bien_servicio = nombre_bien_servicio;
        this.precio_unitario = precio_unitario;
        this.codigo_impuesto = codigo_impuesto;
        this.valor_ice = valor_ice;
    }

    public DetalleFactura() {}
    
    public String getCodigo_bien_servicio() {
        return codigo_bien_servicio;
    }

    public void setCodigo_bien_servicio(String codigo_bien_servicio) {
        this.codigo_bien_servicio = codigo_bien_servicio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre_bien_servicio() {
        return nombre_bien_servicio;
    }

    public void setNombre_bien_servicio(String nombre_bien_servicio) {
        this.nombre_bien_servicio = nombre_bien_servicio;
    }

    public float getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(float precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public String getCodigo_impuesto() {
        return codigo_impuesto;
    }

    public void setCodigo_impuesto(String codigo_impuesto) {
        this.codigo_impuesto = codigo_impuesto;
    }

    public float getValor_ice() {
        return valor_ice;
    }

    public void setValor_ice(float valor_ice) {
        this.valor_ice = valor_ice;
    }
    
}
