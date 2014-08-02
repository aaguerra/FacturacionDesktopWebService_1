/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturacion.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author aaguerra
 */
public class Pedido {
    
    private Integer id;
	private String direccion;
	private String direccion_proveedor;
	private String clave_acceso;
	private String numero_autorizacion;
	private int tipo_ambiente;
	private Calendar fecha;
	private Calendar fecha_factura;
	private Calendar fecha_facturado;
	@SuppressWarnings("unused")
	private String fecha_facturadoHtml;
	private Float total;
	private Float total_aprobado;
	private Integer numeroPedido;
	private String detalle;
	private String proveedor_ci_ruc;
	private String proveedor_razon_social;
	private String proveedor_direccion;
	private String vendedor_ci_ruc;
	private String cliente_ci_ruc;
	private String cliente_razon_social;
	private String establecimiento;
	private String establecimiento_proovedor;
	private String establecimiento_cliente;
	private String establecimiento_proovedor_direccion;
	private String forma_pago;
	private int id_forma_pago;
	private Integer status;
	private String detalle_aprobacion;
	private String colorStatus;
	private String numero_documento;
	private String punto_emision;
	private String numero_resolucion;
	private int status_autorizacion;
	
	private int dias_credito;
	private Float propina;
	
	public Pedido() {
		
	}
	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getFecha() {
		return this.getFormatFecha();
		/*
		return ""+fecha.get(Calendar.DATE)+"-"+fecha.get(Calendar.MONTH)+"-"+fecha.get(Calendar.YEAR)+"  "
				+fecha.get(Calendar.HOUR)+":"+fecha.get(Calendar.MINUTE)+":"+fecha.get(Calendar.SECOND);
				*/
	}

	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public Integer getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(Integer numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public String getProveedor_ci_ruc() {
		return proveedor_ci_ruc;
	}

	public void setProveedor_ci_ruc(String proveedor_ci_ruc) {
		this.proveedor_ci_ruc = proveedor_ci_ruc;
	}

	public String getProveedor_razon_social() {
		return proveedor_razon_social;
	}

	public void setProveedor_razon_social(String proveedor_razon_social) {
		this.proveedor_razon_social = proveedor_razon_social;
	}

	public String getCliente_ci_ruc() {
		return cliente_ci_ruc;
	}

	public void setCliente_ci_ruc(String cliente_ci_ruc) {
		this.cliente_ci_ruc = cliente_ci_ruc;
	}

	public String getCliente_razon_social() {
		return cliente_razon_social;
	}

	public void setCliente_razon_social(String cliente_razon_social) {
		this.cliente_razon_social = cliente_razon_social;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getForma_pago() {
		return forma_pago;
	}

	public void setForma_pago(String forma_pago) {
		this.forma_pago = forma_pago;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getVendedor_ci_ruc() {
		return vendedor_ci_ruc;
	}

	public void setVendedor_ci_ruc(String vendedor_ci_ruc) {
		this.vendedor_ci_ruc = vendedor_ci_ruc;
	}
	
	private String getFormatFecha() {
		try {
		Date date = this.fecha.getTime();
		DateFormat df = new SimpleDateFormat("d MMMM yyyy - HH:mm",new Locale("es", "ES"));  
		return  df.format(date);
		} catch(Exception e) {
			return  ".";
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDetalle_aprobacion() {
		return detalle_aprobacion;
	}

	public void setDetalle_aprobacion(String detalle_aprobacion) {
		this.detalle_aprobacion = detalle_aprobacion;
	}

	public Float getTotal_aprobado() {
		return total_aprobado;
	}

	public void setTotal_aprobado(Float total_aprobado) {
		this.total_aprobado = total_aprobado;
	}

	public int getId_forma_pago() {
		return id_forma_pago;
	}

	public void setId_forma_pago(int id_forma_pago) {
		this.id_forma_pago = id_forma_pago;
	}

	public String getColorStatus() {
		
		switch(this.status) {
			case 3:
				colorStatus = "success"; 
				break;
			case -1:
				colorStatus = "error"; 
				break;
			case 2:
				colorStatus = "info"; 
				break;
			case 1:
				colorStatus = "warning"; 
				break;
			default:
				colorStatus = ""; 
				break;
		}
		return colorStatus;
	}

	public void setColorStatus(String colorStatus) {
		this.colorStatus = colorStatus;
	}
	public String getEstablecimiento_proovedor() {
		return establecimiento_proovedor;
	}
	public void setEstablecimiento_proovedor(String establecimiento_proovedor) {
		this.establecimiento_proovedor = establecimiento_proovedor;
	}
	public String getEstablecimiento_cliente() {
		return establecimiento_cliente;
	}
	public void setEstablecimiento_cliente(String establecimiento_cliente) {
		this.establecimiento_cliente = establecimiento_cliente;
	}
	public String getNumero_documento() {
		return numero_documento;
	}
	public void setNumero_documento(String numero_documento) {
		this.numero_documento = numero_documento;
	}
	public String getPunto_emision() {
		return punto_emision;
	}
	public void setPunto_emision(String punto_emision) {
		this.punto_emision = punto_emision;
	}
	public int getDias_credito() {
		return dias_credito;
	}
	public void setDias_credito(int dias_credito) {
		this.dias_credito = dias_credito;
	}
	public Calendar getFecha_factura() {
		return this.fecha_factura;
	}
	public void setFecha_factura(Calendar fecha_factura) {
		this.fecha_factura = fecha_factura;
	}	
	public String getProveedor_direccion() {
		return proveedor_direccion;
	}
	public void setProveedor_direccion(String proveedor_direccion) {
		this.proveedor_direccion = proveedor_direccion;
	}
	
	public String getFormatFechaFactura() {
		try {
		Date date = this.fecha.getTime();
		DateFormat df = new SimpleDateFormat("d MMMM yyyy",new Locale("es", "ES"));  
		return  df.format(date);
		} catch(Exception e) {
			return  ".";
		}
	}
	
	public String getFechaFactura(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //fecha.set(2013, 0, 22);
        return sdf.format(this.fecha_factura.getTime()); 
    }
	
	public String getFechaFacturaXml(){
	    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
	    //fecha.set(2013, 0, 22);
	    return sdf.format(this.fecha_factura.getTime()); 
	}
	public Float getPropina() {
		return propina;
	}
	public void setPropina(Float propina) {
		this.propina = propina;
	}
	public Calendar getFecha_facturado() {
		return fecha_facturado;
	}
	public void setFecha_facturado(Calendar fecha_facturado) {
		this.fecha_facturado = fecha_facturado;
	}
	public String getNumero_resolucion() {
		return numero_resolucion;
	}
	public void setNumero_resolucion(String numero_resolucion) {
		this.numero_resolucion = numero_resolucion;
	}
	public int getStatus_autorizacion() {
		return status_autorizacion;
	}
	public void setStatus_autorizacion(int status_autorizacion) {
		this.status_autorizacion = status_autorizacion;
	}
	public String getClave_acceso() {
		return clave_acceso;
	}
	public void setClave_acceso(String clave_acceso) {
		this.clave_acceso = clave_acceso;
	}
	public String getNumero_autorizacion() {
		return numero_autorizacion;
	}
	public void setNumero_autorizacion(String numero_autorizacion) {
		this.numero_autorizacion = numero_autorizacion;
	}
	public int getTipo_ambiente() {
		return tipo_ambiente;
	}
	public void setTipo_ambiente(int tipo_ambiente) {
		this.tipo_ambiente = tipo_ambiente;
	}
	public String getDireccion_proveedor() {
		return direccion_proveedor;
	}
	public void setDireccion_proveedor(String direccion_proveedor) {
		this.direccion_proveedor = direccion_proveedor;
	}
	public String getFecha_facturadoHtml() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //fecha.set(2013, 0, 22);
        return sdf.format(this.fecha_facturado.getTime()); 
	}
	public void setFecha_facturadoHtml(String fecha_facturadoHtml) {
		this.fecha_facturadoHtml = fecha_facturadoHtml;
	}
	public String getEstablecimiento_proovedor_direccion() {
		return establecimiento_proovedor_direccion;
	}
	public void setEstablecimiento_proovedor_direccion(String establecimiento_proovedor_direccion) {
		this.establecimiento_proovedor_direccion = establecimiento_proovedor_direccion;
	}
    
}
