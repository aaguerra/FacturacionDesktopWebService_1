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
public class Contribuyente {
    
    private Integer id;
	private String descripcion;
	
	
	public Contribuyente() {
		
	}
	
	public Contribuyente(Integer id, String descripcion) {
	
		this.id = id;
		this.descripcion = descripcion;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
    
}
