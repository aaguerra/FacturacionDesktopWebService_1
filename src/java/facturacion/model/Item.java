/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturacion.model;

import java.util.List;

/**
 *
 * @author aaguerra
 */
public class Item {
    private Boolean edit;	
	private int id_detalle_pedido;
	private int numero;// posiscion en la lista en la que se lo envia
	private int cantidad_despachada; // lo uso en despacho y es la cantidad que me queda de residuo despues de cada despacho	
	//datos para el calculo del total 
	private float base0;
	private float dscto0;
	private float base12;
	private float dscto12;
	private float ice_;
	private float iva_;
	private float dsctoNObjetoIva;	
	private float total_facturar;	
	private float total_descuento;	
	// producto empresa
	private Integer id_producto_empresa;
	private String codigo_producto;
	private String codigo_;
	private Float precio_compra;
	private Float pvp;
	private String empresa;
	private int status_empresa;
	private String cta_compra;	
	private String cta_devolucion_compra;	
	private String cta_venta;	
	private String cta_devolucion_venta;
	private Float descuento;
	private String descuento_;
	//preentacion
	private int status_presentacion;
	private Integer id_presentacion;
	private String detalle;
	private String codigo_barra;
	private Float pvp_presentacion;
	private Integer unidades_por_caja;
	private String unidad_medida;
	// bien o servicio
	private Integer id_bienservicio;
	private String producto;
	private String marca;
	private Iva iva;
	private Ice ice;
	private float valor_ice;
	private String propietario_ci_ruc;
	private String propietario_razon_social;
	
	//detalle del pedido
	private String cantidad;
	private String totalItem;
	private String detalleItem;
	private Integer cantidad_aprobada;
	private String detalle_aprobacion;
	
	//editar
	private Boolean editar_producto_empresa;
	private Boolean editar_presentacion;
	private Boolean editar_bienservicio;
	
	//clasificacion
	private Integer clasificacion;	
	private List<DespachoDetalle> lista;
	
	//servicio empresa
	private Integer id_servicio_empresa;
	private String codigo_empresa;
	private Float precio_empresa;
	private Integer status;	
	
	private Integer tipo_item; // 0 = PRODUCTO, 1= SERVICIO
	
	public Item() {
		
	}	
	/*
	public static Item ProductoToItem(Producto p){
		Item it = new Item();
		//anadir producto		
		it.setTotal_facturar(p.getTotal_facturar());
		it.setTotalItem(p.getTotalItem());
		it.setDetalleItem(p.getDetalleItem());
		it.setCodigo_producto(p.getCodigo_producto());
		it.setDescuento(Float.parseFloat(p.getDescuento_()));
		it.setDescuento_(p.getDescuento_());
		it.setBase0(p.getBase0());
		it.setDscto0(p.getDscto0());
		it.setBase12(p.getBase12());
		it.setDscto12(p.getDscto12());
		it.setIce_(p.getIce_());
		it.setIva_(p.getIva_());
		it.setTotal_facturar(p.getTotal_facturar());
		//resto de atributos
		it.setEdit(p.getEdit());
		it.setId_producto_empresa(p.getId_producto_empresa());		
		it.setPrecio_compra(p.getPrecio_compra());
		it.setPvp(p.getPvp());
		it.setEmpresa(p.getEmpresa());
		it.setId_presentacion(p.getId_presentacion());
		it.setDetalle(p.getDetalle());
		it.setCodigo_barra(p.getCodigo_barra());
		it.setId_bienservicio(p.getId_bienservicio());
		it.setProducto(p.getProducto());
		it.setMarca(p.getMarca());
		it.setIva(p.getIva());
		it.setValor_ice(p.getValor_ice());
		it.setIce(p.getIce());
		it.setPropietario_ci_ruc(p.getPropietario_ci_ruc());
		it.setPropietario_razon_social(p.getPropietario_razon_social());
		it.setCantidad(p.getCantidad());
		it.setTipo_item(p.getTipo_item());
		it.setDsctoNObjetoIva(p.getDsctoNObjetoIva());
		return it;
	}
	
	public static Item ServicioToItem(Servicio p){
		Item it = new Item();
		//anadir servicio		
		it.setTotal_facturar(p.getTotal_facturar());
		it.setTotalItem(p.getTotalItem());
		it.setDetalleItem(p.getDetalleItem());
		it.setCodigo_empresa(p.getCodigo_empresa());
		it.setDescuento(p.getDescuento());	
		it.setDescuento_(p.getDescuento_());
		it.setBase0(p.getBase0());
		it.setDscto0(p.getDscto0());
		it.setBase12(p.getBase12());
		it.setDscto12(p.getDscto12());
		it.setIce_(p.getIce_());
		it.setIva_(p.getIva_());
		it.setTotal_facturar(p.getTotal_facturar());
		//resto de atributos
		it.setPvp(p.getPrecio_empresa());
		it.setEdit(p.getEdit());
		it.setId_servicio_empresa(p.getId());		
		it.setPrecio_empresa(p.getPrecio_empresa());		
		it.setEmpresa(p.getEmpresa());		
		it.setDetalle(p.getDetalle());
		it.setIva(p.getIva());
		it.setValor_ice(p.getIce());		
		it.setCantidad(p.getCantidad());	
		it.setTipo_item(p.getTipo_item());
		it.setDsctoNObjetoIva(p.getDsctoNObjetoIva());
		return it;
	}
        */
	
	private float float2d(float num){
		return (float) (Math.round(num*100)/100.00);
	}
	
	public float totalProcesado(Float total_){		
		if (this.iva.getCodigo() != 6) {
			if (this.descuento >= 0) {
				if (this.iva.getPorcentaje() == 0 && this.valor_ice ==0) {
					this.dscto0 = this.float2d((total_ * this.descuento)/100);
					this.base0 = this.float2d(total_);
					return float2d( total_- this.dscto0 );
					
				} else if (this.iva.getPorcentaje() > 0 && this.valor_ice ==0) {
					this.base12 = this.float2d( (total_/(1+(this.iva.getPorcentaje()/100))) );
					this.dscto12 =this.float2d( (this.base12*this.descuento)/100 );
					this.iva_= this.float2d( (this.base12-this.dscto12)*(this.iva.getPorcentaje()/100) );
					return float2d( this.base12-this.dscto12+this.iva_ );
					
				} else if (this.iva.getPorcentaje() > 0 && this.valor_ice >=0) {				
					this.base12 = this.float2d( (total_/(1+(this.iva.getPorcentaje()/100))) );
					this.dscto12 =this.float2d( (this.base12*this.descuento)/100 );
					this.iva_= this.float2d( (this.base12-this.dscto12)*(this.iva.getPorcentaje()/100) );
					this.ice_ =this.float2d( ( (this.base12-this.dscto12)/(1+(this.iva.getPorcentaje()/100)) )*(this.valor_ice/100) );								
					return float2d( this.base12-this.dscto12+this.iva_ );
					
				} else
	                System.out.println("descuento no entre erro ---1 ");
			} else{	     
	            System.out.println("descuento no entre ");
	            this.totalItem = String.valueOf(total_);
	            this.base0 = this.float2d(total_);
	            return this.float2d(total_);            
			}
		} else {
			if (this.descuento >= 0) {				
	            System.out.println("descuento entre no objeto iva");
	            this.dsctoNObjetoIva = this.float2d((total_ * this.descuento)/100);
	            return float2d(total_-this.dsctoNObjetoIva);            
			} else{
				System.out.println("entre no objeto iva");
				return this.float2d(total_);
			} 
		}
		System.out.println("descuento no entre --- ");
		return 0;
	}
	
public Item copy(){
		
		Item c = new Item();
		
		c.setCodigo_producto(codigo_producto);
		c.setCodigo_empresa(codigo_empresa);
		c.setProducto(producto);
		c.setDetalle(detalle);
		c.setPvp(pvp);
		c.setCantidad(cantidad);
		c.setTotal_facturar(total_facturar);
		c.setTotalItem(totalItem);
		c.setIva_(iva_);
		c.setIva(new Iva(iva.getId(),iva.getCodigo(),iva.getPorcentaje(),iva.getDescripcion()));
		c.setIce_(ice_);
		c.setIce(ice);
		c.setDscto0(dscto0);
		c.setDscto12(dscto12);
		c.setBase12(base12);
		c.setBase0(base0);
		c.setDetalleItem(detalleItem);
		c.setDescuento(descuento);
		c.setDescuento_(descuento_);
		c.setEdit(edit);
		c.setId_producto_empresa(id_producto_empresa);
		c.setEmpresa(codigo_empresa);
		c.setId_presentacion(id_presentacion);
		c.setCodigo_barra(codigo_barra);
		c.setId_bienservicio(id_bienservicio);
		c.setMarca(marca);
		c.setPropietario_ci_ruc(propietario_ci_ruc);
		c.setPropietario_razon_social(propietario_razon_social);
		c.setTipo_item(tipo_item);
		c.setDsctoNObjetoIva(dsctoNObjetoIva);
		c.setValor_ice(valor_ice);
		return c;
	}
	
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		Item c = (Item)super.clone();
		c.setIva((Iva)c.getIva().clone());			
		return c;
	}	
	

	public boolean equals1(Item o) {
		//if (obj  instanceof Item){
			System.out.println("XXX XXXX XXX xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			//Item o = (Item) obj;
			if (o.getTipo_item() == this.tipo_item) {
				System.out.println("iguaes de item");
				if (this.tipo_item == 0 ) { //producto
					System.out.println("iguaes de item producto");
					if (this.codigo_producto.equals(o.getCodigo_producto())){
						System.out.println("igual producto");
						if (this.iva.getCodigo() != 6) {
							this.cantidad = String.valueOf(  Integer.parseInt(this.cantidad) + Integer.parseInt(o.getCantidad()) );
							this.total_facturar = this.total_facturar + o.getTotal_facturar();
							this.base0 = this.base0 + o.getBase0();
							this.base12 = this.base12 + o.getBase12();
							this.dscto0 = this.dscto0 + o.getDscto0();
							this.dscto12 = this.dscto12 + o.getDscto12();
							this.ice_ = this.ice_ + o.getIce_();
							this.iva_ = this.iva_ + o.getIva_();
						} else {
							this.cantidad = String.valueOf(  Integer.parseInt(this.cantidad) + Integer.parseInt(o.getCantidad()) );
							this.total_facturar = this.total_facturar + o.getTotal_facturar();
						}
							
						return true;
					} else{
						System.out.println("no iguaes de item producto");
						return false;
					}
					
				} else { //servicio
					System.out.println("iguaes de item servicio");
					if (this.codigo_empresa.equals(o.getCodigo_empresa())){
						System.out.println("igual servicio");
						if (this.iva.getCodigo() != 6) {
							this.cantidad = String.valueOf(  Integer.parseInt(this.cantidad) + Integer.parseInt(o.getCantidad()) );
							this.total_facturar = this.total_facturar + o.getTotal_facturar();
							this.base0 = this.base0 + o.getBase0();
							this.base12 = this.base12 + o.getBase12();
							this.dscto0 = this.dscto0 + o.getDscto0();
							this.dscto12 = this.dscto12 + o.getDscto12();
							this.ice_ = this.ice_ + o.getIce_();
							this.iva_ = this.iva_ + o.getIva_();
							//this.toStringValores();
						} else {
							this.cantidad = String.valueOf(  Integer.parseInt(this.cantidad) + Integer.parseInt(o.getCantidad()) );
							this.dsctoNObjetoIva = this.dsctoNObjetoIva + o.getDsctoNObjetoIva();
							this.totalItem = String.valueOf( Float.parseFloat(this.totalItem)  + o.getTotalItem() );
							this.total_facturar = this.total_facturar + o.getTotal_facturar();
						}
						return true;
					} else{
						//System.out.println("no son iguaes de item servicio");
						return false;
					}
				}
			} else{
				return false;
			}	
			/*
		} else	{	
			System.out.println("XXX XXXX error XXX xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			return false;
		}
		*/
	}

	public void toStringValores(){
		System.out.println("========++++++++++===================================================");
		System.out.println("========++++++++++===================================================");
		System.out.println("getIva_ =" + getIva_());
		System.out.println("getIce_ =" + getIce_());
		System.out.println("getIce =" + getValor_ice());
		System.out.println("getDscto12 =" + getDscto12());
		System.out.println("getDscto0 =" + getDscto0());
		System.out.println("getBase12 =" + getBase12());
		System.out.println("getBase0 =" + getBase0());
		System.out.println("getCantidad =" + getCantidad());
		System.out.println("getTotal_facturar =" + getTotal_facturar());
		System.out.println("getTotalItem =" + getTotalItem());
		System.out.println("getDsctoNObjetoIva =" + getDsctoNObjetoIva());
	}
	public void toStringDatos(){
		System.out.println("========++++++++++===================================================");
		System.out.println("getCodigo_producto =" + getCodigo_producto());
		System.out.println("getCodigo_empresa =" +getCodigo_empresa());
		System.out.println("getProducto =" + getProducto());
		System.out.println("getDetalle =" + getDetalle());
		System.out.println("getPvp =" + getPvp());
		System.out.println("getCantidad =" + getCantidad());
		System.out.println("getTotal_facturar =" + getTotal_facturar());
		System.out.println("getTotalItem =" + getTotalItem());
		System.out.println("getIva_ =" + getIva_());
		System.out.println("getIce_ =" + getIce_());
		System.out.println("getIce =" + getValor_ice());
		System.out.println("getDscto12 =" + getDscto12());
		System.out.println("getDscto0 =" + getDscto0());
		System.out.println("getBase12 =" + getBase12());
		System.out.println("getBase0 =" + getBase0());
		System.out.println("getDetalleItem =" + getDetalleItem());
		System.out.println("getDescuento_ =" + getDescuento_());
		System.out.println("getEdit =" + getEdit());
		System.out.println("getId_producto_empresa =" + getId_producto_empresa());
		System.out.println("getPrecio_compra =" + getPrecio_compra());
		System.out.println("getEmpresa =" + getEmpresa());
		System.out.println("getId_presentacion =" + getId_presentacion());
		System.out.println("getCodigo_barra =" + getCodigo_barra());
		System.out.println("getId_bienservicio =" +getId_bienservicio());
		System.out.println("getMarca =" + getMarca());
		System.out.println("getPropietario_ci_ruc =" + getPropietario_ci_ruc());
		System.out.println("getTipo_item =" + getTipo_item());
	}
	
	


	public Integer getId_servicio_empresa() {
		return id_servicio_empresa;
	}

	public void setId_servicio_empresa(Integer id_servicio_empresa) {
		this.id_servicio_empresa = id_servicio_empresa;
	}

	public String getCodigo_empresa() {
		return codigo_empresa;
	}

	public void setCodigo_empresa(String codigo_empresa) {
		this.codigo_empresa = codigo_empresa;
	}

	public Float getPrecio_empresa() {
		return precio_empresa;
	}

	public void setPrecio_empresa(Float precio_empresa) {
		this.precio_empresa = precio_empresa;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(String totalItem) {
		this.totalItem = totalItem;
	}

	public String getDetalleItem() {
		return detalleItem;
	}

	public void setDetalleItem(String detalleItem) {
		this.detalleItem = detalleItem;
	}

	public Integer getCantidad_aprobada() {
		return cantidad_aprobada;
	}

	public void setCantidad_aprobada(Integer cantidad_aprobada) {
		this.cantidad_aprobada = cantidad_aprobada;
	}

	public String getDetalle_aprobacion() {
		return detalle_aprobacion;
	}

	public void setDetalle_aprobacion(String detalle_aprobacion) {
		this.detalle_aprobacion = detalle_aprobacion;
	}

	public Integer getId_bienservicio() {
		return id_bienservicio;
	}

	public void setId_bienservicio(Integer id_bienservicio) {
		this.id_bienservicio = id_bienservicio;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Iva getIva() {
		return iva;
	}

	public void setIva(Iva iva) {
		this.iva = iva;
	}

	public Ice getIce() {
		return ice;
	}

	public void setIce(Ice ice) {
		this.ice = ice;
	}

	public String getPropietario_ci_ruc() {
		return propietario_ci_ruc;
	}

	public void setPropietario_ci_ruc(String propietario_ci_ruc) {
		this.propietario_ci_ruc = propietario_ci_ruc;
	}

	public String getPropietario_razon_social() {
		return propietario_razon_social;
	}

	public void setPropietario_razon_social(String propietario_razon_social) {
		this.propietario_razon_social = propietario_razon_social;
	}

	public String getUnidad_medida() {
		return unidad_medida;
	}

	public void setUnidad_medida(String unidad_medida) {
		this.unidad_medida = unidad_medida;
	}

	public Integer getUnidades_por_caja() {
		return unidades_por_caja;
	}

	public void setUnidades_por_caja(Integer unidades_por_caja) {
		this.unidades_por_caja = unidades_por_caja;
	}

	public Float getPvp_presentacion() {
		return pvp_presentacion;
	}

	public void setPvp_presentacion(Float pvp_presentacion) {
		this.pvp_presentacion = pvp_presentacion;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public int getStatus_presentacion() {
		return status_presentacion;
	}

	public void setStatus_presentacion(int status_presentacion) {
		this.status_presentacion = status_presentacion;
	}

	public Integer getId_presentacion() {
		return id_presentacion;
	}

	public void setId_presentacion(Integer id_presentacion) {
		this.id_presentacion = id_presentacion;
	}

	public String getCodigo_barra() {
		return codigo_barra;
	}

	public void setCodigo_barra(String codigo_barra) {
		this.codigo_barra = codigo_barra;
	}

	public String getDescuento_() {
		return descuento_;
	}

	public void setDescuento_(String descuento_) {
		this.descuento_ = descuento_;
	}

	public Float getDescuento() {
		return descuento;
	}

	public void setDescuento(Float descuento) {
		this.descuento = descuento;
	}

	public String getCta_devolucion_venta() {
		return cta_devolucion_venta;
	}

	public void setCta_devolucion_venta(String cta_devolucion_venta) {
		this.cta_devolucion_venta = cta_devolucion_venta;
	}

	public String getCta_venta() {
		return cta_venta;
	}

	public void setCta_venta(String cta_venta) {
		this.cta_venta = cta_venta;
	}

	public String getCta_devolucion_compra() {
		return cta_devolucion_compra;
	}

	public void setCta_devolucion_compra(String cta_devolucion_compra) {
		this.cta_devolucion_compra = cta_devolucion_compra;
	}

	public String getCta_compra() {
		return cta_compra;
	}

	public void setCta_compra(String cta_compra) {
		this.cta_compra = cta_compra;
	}

	public int getStatus_empresa() {
		return status_empresa;
	}

	public void setStatus_empresa(int status_empresa) {
		this.status_empresa = status_empresa;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public Float getPvp() {
		return pvp;
	}

	public void setPvp(Float pvp) {
		this.pvp = pvp;
	}

	public Float getPrecio_compra() {
		return precio_compra;
	}

	public void setPrecio_compra(Float precio_compra) {
		this.precio_compra = precio_compra;
	}

	public String getCodigo_() {
		return codigo_;
	}

	public void setCodigo_(String codigo_) {
		this.codigo_ = codigo_;
	}

	public String getCodigo_producto() {
		return codigo_producto;
	}

	public void setCodigo_producto(String codigo_producto) {
		this.codigo_producto = codigo_producto;
	}

	public Integer getId_producto_empresa() {
		return id_producto_empresa;
	}

	public void setId_producto_empresa(Integer id_producto_empresa) {
		this.id_producto_empresa = id_producto_empresa;
	}

	public float getTotal_descuento() {
		return total_descuento;
	}

	public void setTotal_descuento(float total_descuento) {
		this.total_descuento = total_descuento;
	}

	public float getTotal_facturar() {
		return total_facturar;
	}

	public void setTotal_facturar(float total_facturar) {
		this.total_facturar = total_facturar;
	}

	public float getIce_() {
		return ice_;
	}

	public void setIce_(float ice_) {
		this.ice_ = ice_;
	}

	public float getDscto12() {
		return dscto12;
	}

	public void setDscto12(float dscto12) {
		this.dscto12 = dscto12;
	}

	public float getBase12() {
		return base12;
	}

	public void setBase12(float base12) {
		this.base12 = base12;
	}

	public float getDscto0() {
		return dscto0;
	}

	public void setDscto0(float dscto0) {
		this.dscto0 = dscto0;
	}

	public float getBase0() {
		return base0;
	}

	public void setBase0(float base0) {
		this.base0 = base0;
	}

	public float getIva_() {
		return iva_;
	}

	public void setIva_(float iva_) {
		this.iva_ = iva_;
	}

	public Boolean getEdit() {
		return edit;
	}

	public void setEdit(Boolean edit) {
		this.edit = edit;
	}

	public int getId_detalle_pedido() {
		return id_detalle_pedido;
	}

	public void setId_detalle_pedido(int id_detalle_pedido) {
		this.id_detalle_pedido = id_detalle_pedido;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getCantidad_despachada() {
		return cantidad_despachada;
	}

	public void setCantidad_despachada(int cantidad_despachada) {
		this.cantidad_despachada = cantidad_despachada;
	}

	public Boolean getEditar_producto_empresa() {
		return editar_producto_empresa;
	}

	public void setEditar_producto_empresa(Boolean editar_producto_empresa) {
		this.editar_producto_empresa = editar_producto_empresa;
	}

	public Boolean getEditar_presentacion() {
		return editar_presentacion;
	}

	public void setEditar_presentacion(Boolean editar_presentacion) {
		this.editar_presentacion = editar_presentacion;
	}

	public Boolean getEditar_bienservicio() {
		return editar_bienservicio;
	}

	public void setEditar_bienservicio(Boolean editar_bienservicio) {
		this.editar_bienservicio = editar_bienservicio;
	}

	public Integer getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(Integer clasificacion) {
		this.clasificacion = clasificacion;
	}

	public List<DespachoDetalle> getLista() {
		return lista;
	}

	public void setLista(List<DespachoDetalle> lista) {
		this.lista = lista;
	}

	public Integer getTipo_item() {
		return tipo_item;
	}

	public void setTipo_item(Integer tipo_item) {
		this.tipo_item = tipo_item;
	}

	public float getDsctoNObjetoIva() {
		return dsctoNObjetoIva;
	}

	public void setDsctoNObjetoIva(float dsctoNObjetoIva) {
		this.dsctoNObjetoIva = dsctoNObjetoIva;
	}

	public float getValor_ice() {
		return valor_ice;
	}

	public void setValor_ice(float valor_ice) {
		this.valor_ice = valor_ice;
	}	
}
