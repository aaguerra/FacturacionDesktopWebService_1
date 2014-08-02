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
public class Iva {
    
    private int id;
	private int codigo;
	private float porcentaje;
	private String descripcion;
	
	public Iva(){
		
	}
	
	
	
	public Iva(int id, int codigo, float porcentaje, String descripcion) {
		this.id = id;
		this.codigo = codigo;
		this.porcentaje = porcentaje;
		this.descripcion = descripcion;
	}

	public Iva copy() {
		// TODO Auto-generated method stub
		return new Iva(this.id, this.codigo, this.porcentaje, this.descripcion);
	}	

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (Iva)super.clone();
	}	
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public float getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(float porcentaje) {
		this.porcentaje = porcentaje;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
}
