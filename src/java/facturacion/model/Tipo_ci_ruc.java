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
public class Tipo_ci_ruc {
    
    private Integer id;
	private String descripcion;
	
	
	public Tipo_ci_ruc() {
		
	}


	public Tipo_ci_ruc(Integer id, String descripcion) {
		
		this.id = id;
		this.descripcion = descripcion;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}
    
}
