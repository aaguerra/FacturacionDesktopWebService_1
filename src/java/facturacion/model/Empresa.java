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
public class Empresa {
    
    private String ci_ruc;
	private String razon_social;
	private boolean productoStatus;	
	private Integer id_usuario_empresa_grupo;	
	
	private String email_facturacion_electronica;
	
	private String nombre_comercial;
	private String representante;	
	private String contador;
	private String website;
	private String direccion;
	private String ciudad;
	private Ir ir;
	private Tipo_ci_ruc tipo_ci_ruc;
	private Contribuyente contribuyente;	
	
	private Integer servicioStatus;
	private Integer asignado;
	private int ir_con_tipo_ci;
	private String numero_resolucion;
	private Integer descuento_servicio;
	
	public Empresa(String ci_ruc, String razon_social) {
		this.ci_ruc = ci_ruc;
		this.razon_social = razon_social;
	}
	public Empresa(String ci_ruc, String razon_social, Integer id_usuario_empresa_grupo) {
		this.ci_ruc = ci_ruc;
		this.razon_social = razon_social;
		this.id_usuario_empresa_grupo = id_usuario_empresa_grupo;
	}
	public Empresa() {
		
	}
	public String getRazon_social() {
		return razon_social;
	}
	public void setRazon_social(String razon_social) {
		this.razon_social = razon_social;
	}
	public String getCi_ruc() {
		return ci_ruc;
	}
	public void setCi_ruc(String ci_ruc) {
		this.ci_ruc = ci_ruc;
	}
	public int getProductoStatus() {
		if (productoStatus)
			return 1;
		else
			return 0;
	}
	public void setProductoStatus(boolean productoStatus) {
		this.productoStatus = productoStatus;
	}
	public Integer getId_usuario_empresa_grupo() {
		return id_usuario_empresa_grupo;
	}
	public void setId_usuario_empresa_grupo(Integer id_usuario_empresa_grupo) {
		this.id_usuario_empresa_grupo = id_usuario_empresa_grupo;
	}
	public String getNombre_comercial() {
		return nombre_comercial;
	}
	public void setNombre_comercial(String nombre_comercial) {
		this.nombre_comercial = nombre_comercial;
	}
	public String getRepresentante() {
		return representante;
	}
	public void setRepresentante(String representante) {
		this.representante = representante;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getContador() {
		return contador;
	}
	public void setContador(String contador) {
		this.contador = contador;
	}
	public Ir getIr() {
		return ir;
	}
	public void setIr(Ir ir) {
		this.ir = ir;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Tipo_ci_ruc getTipo_ci_ruc() {
		return tipo_ci_ruc;
	}
	public void setTipo_ci_ruc(Tipo_ci_ruc tipo_ci_ruc) {
		this.tipo_ci_ruc = tipo_ci_ruc;
	}
	public Contribuyente getContribuyente() {
		return contribuyente;
	}
	public void setContribuyente(Contribuyente contribuyente) {
		this.contribuyente = contribuyente;
	}
	public Integer getServicioStatus() {
		return servicioStatus;
	}
	public void setServicioStatus(Integer servicioStatus) {
		this.servicioStatus = servicioStatus;
	}
	public Integer getAsignado() {
		return asignado;
	}
	public void setAsignado(Integer asignado) {
		this.asignado = asignado;
	}
	public int getIr_con_tipo_ci() {
		return ir_con_tipo_ci;
	}
	public void setIr_con_tipo_ci(int ir_con_tipo_ci) {
		this.ir_con_tipo_ci = ir_con_tipo_ci;
	}
	public String getEmail_facturacion_electronica() {
		return email_facturacion_electronica;
	}
	public void setEmail_facturacion_electronica(
			String email_facturacion_electronica) {
		this.email_facturacion_electronica = email_facturacion_electronica;
	}
	public String getNumero_resolucion() {
		return numero_resolucion;
	}
	public void setNumero_resolucion(String numero_resolucion) {
		this.numero_resolucion = numero_resolucion;
	}
	public Integer getDescuento_servicio() {
		return descuento_servicio;
	}
	public void setDescuento_servicio(Integer descuento_servicio) {
		this.descuento_servicio = descuento_servicio;
	}
    
}
