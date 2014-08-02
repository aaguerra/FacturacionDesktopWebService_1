/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturacion.model;

/**
 *
 * @author aaguerra
 */
public class DespachoDetalle {
    
    private int cantidad;
	private String detalle;
	
	public DespachoDetalle() {
		
	}
	
	public DespachoDetalle(int cantidad, String detalle) {
		this.cantidad = cantidad;
		this.setDetalle(detalle);
	}
	
	public int getCantidad() {
		return cantidad;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
    
}
