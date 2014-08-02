/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturacion.xml;

import ec.gob.sri.comprobantes.ws.aut.Autorizacion;
import ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante;
import ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante.Autorizaciones;
import facturacion.model.Item;
import facturacion.model.Pedido;
import facturacion.utils.Modulo11;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.DOMOutputter;
/**
 *
 * @author aaguerra
 */
public class FacturaXml {
    private Pedido pedido;
    private List<Item> itemList;
    private Document doc;
    private String obligado_contabilidad;
    private String claveAcceso;
    private int ambiente; //1= pruebas. 2=produccion
    private int emision; //1=emision normal, 2=emision por indisponibilidad del sistema
    private float base0;
    private float dscto0;
    private float base12;
    private float dscto12;
    private float ice_;
    private float iva_;
    private float total ;
    private float noObjetoIva ;
    private float dsctNoObjetoIva ;
    private float totalSinImpuestos;
    private RespuestaComprobante respuesta;
    
    public FacturaXml(Pedido pedido, List<Item> itemList, int ambiente, int emision, String obligado_contabilidad){
    	//System.out.println("----> hola +++ 1");    	
        this.pedido = pedido;
        this.itemList = itemList;
        //System.out.println("----> hola +++ 2");
        this.ambiente = ambiente;
        this.emision = emision;
        this.obligado_contabilidad = obligado_contabilidad;
        //System.out.println("----> hola +++ 3");
    }
    
    public FacturaXml(RespuestaComprobante respuesta){
    	this.respuesta = respuesta;
    }

    public FacturaXml() {
        
    }    
    
    public void crearFacturaAutorizadaXml(facturacion.model.beans.RespuestaComprobante autorizaciones){
    	Element root = new Element("autorizacion");
        //root.setAttribute("id", "comprobante");
        //root.setAttribute("version","1.0.0");
        doc = new Document();
        
        this.doc.setRootElement(root);
        
        
        for (facturacion.model.beans.Comprobante autorizacion : autorizaciones.getComprobantes()) {
	        root.addContent(new Element("estado").setText( autorizacion.getEstado() ));
	        root.addContent(new Element("numeroAutorizacion").setText( autorizacion.getNumeroAutorizacion() ));
	        root.addContent(new Element("fechaAutorizacion").setText( ""+autorizacion.getFechaAutorizacion() ) );
	        String cad = "<![CDATA[" + autorizacion.getComprobante() + "]]>";
	        byte ptext[] = cad.getBytes();
	        //System.out.println("--------------------------------------------===");
	        //System.out.println(cad);
	        //System.out.println("--------------------------------------------===");
	        
	        try {
	        	cad = (new String(ptext, "UTF-8"));
	        	//System.out.println(cad);
	        	//System.out.println("--------------------------------------------===");
				root.addContent(new Element("comprobante").setText( cad ) );
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        Element mensajes = new Element("mensajes");
	        Element mensaje1 = new Element("mensaje");
	        for (facturacion.model.beans.Mensaje  mensaje : autorizacion.getMensajes()) {
		        Element mensaje2 = new Element("mensaje");
		        mensaje2.addContent( new Element("identificador").setText( mensaje.getIdentificador() ));
		        mensaje2.addContent( new Element("informacionAdicional").setText( mensaje.getInformacionAdicional() ));
		        mensaje2.addContent( new Element("mensaje").setText( mensaje.getMensaje() ));
		        mensaje2.addContent( new Element("tipo").setText( mensaje.getTipo() ));
		        
		        mensaje1.addContent(mensaje2);
		        break;
	        }
	        
	        mensajes.addContent(mensaje1);
	        
	        root.addContent(mensajes);
	        break;
        }
    }
    
    public void crearXml() throws ParserConfigurationException {
    	//System.out.println("------ paso 11");
		initXml();
        //System.out.println("------ paso 12");
        crearInfoTributaria();
        //System.out.println("------ paso 13");
        crearInfoFactura();
        //System.out.println("------ paso 15");
        crearDetalles();
        crearInfoAdicional();
        
    }
    
    private void initXml() throws ParserConfigurationException{
        /*
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setCoalescing(true);
        dbf.setIgnoringElementContentWhitespace(true);
        dbf.setIgnoringComments(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        */
        Element root = new Element("factura");
        root.setAttribute("id", "comprobante");
        root.setAttribute("version","1.0.0");
        doc = new Document();
        this.doc.setRootElement(root);
    }
    
    private void crearInfoTributaria(){
        Element infoTributaria = new Element("infoTributaria");
        infoTributaria.addContent(new Element("ambiente").setText(Integer.toString(this.ambiente)) );
        infoTributaria.addContent(new Element("tipoEmision").setText( Integer.toString(this.emision) ));
        if (this.pedido.getProveedor_razon_social() == null)
        	System.out.println("------ paso 15 === esta nulo");
        else if (this.pedido.getProveedor_razon_social().isEmpty())
        	System.out.println("------ paso 15 === esta vacio");
        infoTributaria.addContent(new Element("razonSocial").setText( this.pedido.getProveedor_razon_social()));
        infoTributaria.addContent(new Element("ruc").setText( this.pedido.getProveedor_ci_ruc()+this.pedido.getEstablecimiento_proovedor()  ));
        infoTributaria.addContent(new Element("claveAcceso").setText( crearClaveDeAcceso() ));
        infoTributaria.addContent(new Element("codDoc").setText("01"));// este codigo es para facturas
        infoTributaria.addContent(new Element("estab").setText( this.pedido.getEstablecimiento_proovedor() ));
        infoTributaria.addContent(new Element("ptoEmi").setText( this.pedido.getPunto_emision() ));
        infoTributaria.addContent(new Element("secuencial").setText( getSecuencial() ));
        infoTributaria.addContent(new Element("dirMatriz").setText( this.pedido.getProveedor_direccion() ));
        this.doc.getRootElement().addContent(infoTributaria);
    }
    
    private void crearInfoFactura(){
        Element infoFactura = new Element("infoFactura");
        //char a;
        infoFactura.addContent(new Element("fechaEmision").setText( this.pedido.getFechaFactura() ));
    	infoFactura.addContent(new Element("dirEstablecimiento").setText( this.pedido.getEstablecimiento_proovedor_direccion() ));
        //System.out.println("entre a numero -----------------m1");
        if (!this.pedido.getNumero_resolucion().trim().equals("")) {
        	//System.out.println("entre a numero -----------------"+this.pedido.getNumero_resolucion()+"====");
        	infoFactura.addContent(new Element("contribuyenteEspecial").setText( this.pedido.getNumero_resolucion() ));
        }
        
        infoFactura.addContent(new Element("obligadoContabilidad").setText( this.obligado_contabilidad ));// ojo con este valor mira si es dinamico
        
        //a = (this.pedido.getCliente_ci_ruc()).charAt(2);
        //if ()
        	
        if (this.pedido.getCliente_ci_ruc().equals("9999999999999"))
        	infoFactura.addContent(new Element("tipoIdentificacionComprador").setText( "07" ));
        else
        	infoFactura.addContent(new Element("tipoIdentificacionComprador").setText( ((this.pedido.getEstablecimiento_cliente().equals("000"))? "05" : "04" ) ));// revisar este punto en pedido porlod e consumidor final
        // 04=venta con ruc, 05=venta con cedula, 06=venta con pasaporte, 07=venta consumidor final
        infoFactura.addContent(new Element("razonSocialComprador").setText( this.pedido.getCliente_razon_social() ));        
        infoFactura.addContent(new Element("identificacionComprador").setText( this.pedido.getCliente_ci_ruc()
        		+((this.pedido.getEstablecimiento_cliente().equals("000"))? "":this.pedido.getEstablecimiento_cliente()) ));
        
        calcularValoresGenerales();
        infoFactura.addContent(new Element("totalSinImpuestos").setText( floatPrint2d(this.totalSinImpuestos) ));
        infoFactura.addContent(new Element("totalDescuento").setText( floatPrint2d(this.dscto0 + this.dscto12+this.dsctNoObjetoIva) ));
        Element totalConImpuestos = new Element("totalConImpuestos");
        
        
        if ((this.base0-this.dscto0)>0){
	        Element totalImpuesto = new Element("totalImpuesto");
	        totalImpuesto.addContent(new Element("codigo").setText( "2" ));
	        totalImpuesto.addContent(new Element("codigoPorcentaje").setText( "0" ));
	        totalImpuesto.addContent(new Element("baseImponible").setText( floatPrint2d(this.base0-this.dscto0) ));
	        totalImpuesto.addContent(new Element("valor").setText( "0.00" ));
	        totalConImpuestos.addContent(totalImpuesto);
        }
        if ((this.base12-this.dscto12)>0){
	        Element totalImpuesto = new Element("totalImpuesto");
	        totalImpuesto.addContent(new Element("codigo").setText( "2" ));
	        totalImpuesto.addContent(new Element("codigoPorcentaje").setText( "2" ));
	        totalImpuesto.addContent(new Element("baseImponible").setText( floatPrint2d(this.base12-this.dscto12) ));
	        totalImpuesto.addContent(new Element("valor").setText( floatPrint2d(this.iva_) ));
	        totalConImpuestos.addContent(totalImpuesto);
        }
        if (this.noObjetoIva > 0) {
        	Element totalImpuesto = new Element("totalImpuesto");
	        totalImpuesto.addContent(new Element("codigo").setText( "2" ));
	        totalImpuesto.addContent(new Element("codigoPorcentaje").setText( "6" ));
	        totalImpuesto.addContent(new Element("baseImponible").setText( floatPrint2d(this.noObjetoIva) ));
	        totalImpuesto.addContent(new Element("valor").setText( "0.00" ));
	        totalConImpuestos.addContent(totalImpuesto);
        }
        
        infoFactura.addContent(totalConImpuestos);
        infoFactura.addContent(new Element("propina").setText( floatPrint2d(this.pedido.getPropina()) ));
        infoFactura.addContent(new Element("importeTotal").setText( floatPrint2d(this.total + this.pedido.getPropina()+(this.noObjetoIva)) ));
        infoFactura.addContent(new Element("moneda").setText( "DOLAR" ));        
        
        this.doc.getRootElement().addContent(infoFactura);
    }
    

    public void crearInfoAdicional(){
    	//System.out.println("------ paso 16");
    	//System.out.println("------ paso 16 =="+this.pedido.getDetalle()+"==");
    	
		//System.out.println("entre a numero -----------------m11");
		//System.out.println("entre a numero -----------------m1");
		Element infoAdicional = new Element("infoAdicional");
		boolean tmp = false;
		if (!this.pedido.getCliente_ci_ruc().equals("9999999999999")) {
			Element campoAdicionalDirec = new Element("campoAdicional").setText( this.pedido.getDireccion().trim() );
			campoAdicionalDirec.setAttribute("nombre","Direccion");
    		infoAdicional.addContent(campoAdicionalDirec );
    		tmp = true;
		}
		if (this.pedido.getDetalle() != null){
    		if (!this.pedido.getDetalle().trim().equals("")) {
	    		Element campoAdicional = new Element("campoAdicional").setText( this.pedido.getDetalle().trim() );
	    		campoAdicional .setAttribute("nombre","Detalle");
	    		
		    	infoAdicional.addContent(campoAdicional );
		    	tmp = true;
    		}
		}
		if (tmp)
			this.doc.getRootElement().addContent(infoAdicional);
    }
    
    
    private void crearDetalles(){
        
        Element detalles = new Element("detalles");
        
        for (Item p : this.itemList)  {
        	if (p.getIva().getCodigo() != 6)
        		detalles.addContent(crearDetalle(p));
        	else
        		detalles.addContent(crearDetalleNoObjetoIva(p));
        }
        
        this.doc.getRootElement().addContent(detalles);
        
    }
    
    private Element crearDetalleNoObjetoIva(Item p){
        Element detalle = new Element("detalle");
        //System.out.println("tipo item = "+p.getTipo_item());
        //System.out.println("tipo item = "+p.getCodigo_producto());
        //System.out.println("tipo item = "+p.getCodigo_empresa());
        detalle.addContent(new Element("codigoPrincipal").setText( ( (p.getTipo_item() == 0)? p.getCodigo_producto():p.getCodigo_empresa()  ) ));
       
    	detalle.addContent(new Element("descripcion").setText( ( (p.getTipo_item() == 0)? (p.getProducto()+" "+p.getDetalle()):p.getDetalle() ) ));
       
        //detalle.addContent(new Element("descripcion").setText( ( (p.getTipo_item() == 0)? (p.getProducto()+" "+p.getDetalle()):p.getDetalle()  )  ));
        detalle.addContent(new Element("cantidad").setText( p.getCantidad()));
        //System.out.println("base0 ="+p.getBase0()+" dsct0="+p.getDscto0()+" base12="+p.getBase12()+" dsct12="+p.getDscto12()+" total"
        //+ (p.getBase0()+p.getBase12()-p.getDscto0()-p.getDscto12()+p.getIva_()+p.getIce() ) );
        //detalle.addContent(new Element("precioUnitario").setText( floatPrint2d(p.getBase0()+p.getBase12()) ));
        //System.out.println("valor total  = "+p.getTotal_facturar() );
        //System.out.println("valor canti" + p.getCantidad());
        //float puni = ( Float.parseFloat(p.getTotalItem()) )/Float.parseFloat(p.getCantidad());
        //System.out.println("valor punit" + puni);
        detalle.addContent(new Element("precioUnitario").setText( floatPrint2d( p.getPvp() ) ));
        detalle.addContent(new Element("descuento").setText( floatPrint2d( p.getDsctoNObjetoIva() ) ));
        detalle.addContent(new Element("precioTotalSinImpuesto").setText( floatPrint2d( p.getTotal_facturar()) ));
        if (p.getDetalleItem() != null) {
        	if (!p.getDetalleItem().isEmpty()) {
        		Element detallesAdicionales = new Element("detallesAdicionales");
        		Element detAdicional = new Element("detAdicional");
        		detAdicional.setAttribute("nombre","Detalle");
        		detAdicional.setAttribute("valor", p.getDetalleItem());
        		detallesAdicionales.addContent(detAdicional);
        		detalle.addContent(detallesAdicionales);
        	}
        }
        Element impuestos = new Element("impuestos");
        
        Element impuestoIva = new Element("impuesto");
        impuestoIva.addContent(new Element("codigo").setText( "2"));
        impuestoIva.addContent(new Element("codigoPorcentaje").setText( Integer.toString(p.getIva().getCodigo()) ));
        impuestoIva.addContent(new Element("tarifa").setText( "0.00" ));
        impuestoIva.addContent(new Element("baseImponible").setText( floatPrint2d( p.getTotal_facturar() ) ));
        impuestoIva.addContent(new Element("valor").setText( "0.00" ));        
        impuestos.addContent(impuestoIva);
        
        detalle.addContent(impuestos);
        
        return detalle;
    }
    
    private Element crearDetalle(Item p){
        Element detalle = new Element("detalle");
        //System.out.println("tipo item = "+p.getTipo_item());
        //System.out.println("tipo item = "+p.getCodigo_producto());
        //System.out.println("tipo item = "+p.getCodigo_empresa());
        detalle.addContent(new Element("codigoPrincipal").setText( ( (p.getTipo_item() == 0)? p.getCodigo_producto():p.getCodigo_empresa()  ) ));        
        detalle.addContent(new Element("descripcion").setText( ( (p.getTipo_item() == 0)? (p.getProducto()+" "+p.getDetalle()):p.getDetalle())  ));
        detalle.addContent(new Element("cantidad").setText( p.getCantidad()));
        //System.out.println("base0 ="+p.getBase0()+" dsct0="+p.getDscto0()+" base12="+p.getBase12()+" dsct12="+p.getDscto12()+" total"
        //+ (p.getBase0()+p.getBase12()-p.getDscto0()-p.getDscto12()+p.getIva_()+p.getIce() ) );
        //detalle.addContent(new Element("precioUnitario").setText( floatPrint2d(p.getBase0()+p.getBase12()) ));
        //System.out.println("valor total  = "+((p.getBase0()+p.getBase12())-(p.getDscto0()+p.getDscto12())) );
        //System.out.println("valor canti" + p.getCantidad());
        float puni = ((p.getBase0()+p.getBase12()))/Float.parseFloat(p.getCantidad());
        //System.out.println("valor punit" + puni);
        detalle.addContent(new Element("precioUnitario").setText( floatPrint2d( puni ) ));
        detalle.addContent(new Element("descuento").setText( floatPrint2d(p.getDscto0()+p.getDscto12()) ));
        detalle.addContent(new Element("precioTotalSinImpuesto").setText( floatPrint2d( (p.getBase0()+p.getBase12())-(p.getDscto0()+p.getDscto12()) ) ));
        if (p.getDetalleItem() != null) {
        	if (!p.getDetalleItem().isEmpty()) {
        		Element detallesAdicionales = new Element("detallesAdicionales");
        		Element detAdicional = new Element("detAdicional");
        		detAdicional.setAttribute("nombre","Detalle");
        		detAdicional.setAttribute("valor", p.getDetalleItem());
        		detallesAdicionales.addContent(detAdicional);
        		detalle.addContent(detallesAdicionales);
        	}
        }
        Element impuestos = new Element("impuestos");
        
        Element impuestoIva = new Element("impuesto");
        impuestoIva.addContent(new Element("codigo").setText( "2"));
        impuestoIva.addContent(new Element("codigoPorcentaje").setText( Integer.toString(p.getIva().getCodigo()) ));
        impuestoIva.addContent(new Element("tarifa").setText( Integer.toString( (int)p.getIva().getPorcentaje())+".00" ));
        impuestoIva.addContent(new Element("baseImponible").setText( floatPrint2d( (p.getBase0()+p.getBase12())-(p.getDscto0()+p.getDscto12())  ) ));
        impuestoIva.addContent(new Element("valor").setText( floatPrint2d(p.getIva_()) ));        
        impuestos.addContent(impuestoIva);
        
        detalle.addContent(impuestos);
        
        return detalle;
    }
    
    private void calcularValoresGenerales(){
        this.base0 = 0;
        this.dscto0 = 0;
        this.base12 = 0;
        this.dscto12 = 0;
        this.ice_ = 0;
        this.iva_ =0 ;
        this.noObjetoIva = 0;
        this.dsctNoObjetoIva = 0;
        this.total = 0;
        this.totalSinImpuestos = 0;
        
        for (Item p : this.getItemList() ) {	
            //p.setTotal_facturar( p.totalProcesado(Float.parseFloat(p.getTotalItem())) );
        	if (p.getIva().getCodigo() != 6 ) {
        		this.totalSinImpuestos = this.totalSinImpuestos + (p.getBase0()+p.getBase12()-p.getDscto0()-p.getDscto12());//(Float.parseFloat( p.getTotalItem()) - p.getIva_() );
        		this.total = this.total + p.getTotal_facturar();
                this.base0 = this.base0 + p.getBase0(); 
                this.dscto0 = this.dscto0 + p.getDscto0();
                this.base12 = this.base12 + p.getBase12();
                this.dscto12 = this.dscto12 + p.getDscto12();
                this.ice_ = this.ice_ + p.getIce_();
                this.iva_ =  this.iva_ + p.getIva_();
        	}
        	else {
        		this.totalSinImpuestos = this.totalSinImpuestos + p.getTotal_facturar();
        		this.noObjetoIva = this.noObjetoIva +  p.getTotal_facturar();
        		this.dsctNoObjetoIva = this.dsctNoObjetoIva + p.getDsctoNObjetoIva();
        	}
            //System.out.println("total 1 =" + p.getTotal_facturar());

            //System.out.println("total =" + this.total);
        }
    }
    
    private String floatPrint2d(float num){
    		//float2d(num)
            int partInt = (int) num;
            float partDecimal = float2d(num-partInt);
            if (Float.toString(partDecimal).length() == 3)
                return Float.toString(float2d(num))+"0";
            else
                return Float.toString(float2d(num) );
    }
    
    private float float2d(float num){
        return (float) (Math.round(num*100)/100.00);
    }
    
    private String crearClaveDeAcceso(){
        String claveAcceso = this.pedido.getFechaFacturaXml()+"01"+this.pedido.getProveedor_ci_ruc()+this.pedido.getEstablecimiento_proovedor() +this.ambiente
                +this.pedido.getEstablecimiento_proovedor()+this.pedido.getPunto_emision()
                +this.getSecuencial()+"12345678"+this.emision;
        //System.out.println("hola mundo ------ :) = "+claveAcceso);
        //System.out.println(claveAcceso+"-"+getDigitoVerificador(claveAcceso));
        //this.claveAcceso = claveAcceso;
        if ( 11 == getDigitoVerificador(claveAcceso) ) {
        	this.claveAcceso = claveAcceso+"0";
            return this.claveAcceso;
        }else if ( 10 == getDigitoVerificador(claveAcceso) ) {
        	this.claveAcceso = claveAcceso+"1";
            return this.claveAcceso;
        } else {
        	this.claveAcceso = claveAcceso+getDigitoVerificador(claveAcceso);
            return this.claveAcceso;        
        }
    }    
    
    private int getDigitoVerificador(String claveAcceso){      
    	//System.out.println("hola mundo ------ :) 1");
        return Modulo11.obtenerSumaPorDigitos( Modulo11.invertirCadena(claveAcceso));
    }
    
    private String getSecuencial(){
        String cad = this.pedido.getNumero_documento();
        int tam = 9 - cad.length();
        for (int i = 0; i < tam ; i++ )
            cad = "0"+cad;
        return cad;
    }

    public RespuestaComprobante getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(RespuestaComprobante respuesta) {
        this.respuesta = respuesta;
    }
    
    public org.w3c.dom.Document getDocW3c() throws JDOMException {
        return (new DOMOutputter()).output(doc);
    }
    
    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }
    
    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

	public String getObligado_contabilidad() {
		return obligado_contabilidad;
	}

	public void setObligado_contabilidad(String obligado_contabilidad) {
		this.obligado_contabilidad = obligado_contabilidad;
	}

	public String getClaveAcceso() {
		return claveAcceso;
	}

	public void setClaveAcceso(String claveAcceso) {
		this.claveAcceso = claveAcceso;
	}

	public float getNoObjetoIva() {
		return noObjetoIva;
	}

	public void setNoObjetoIva(float noObjetoIva) {
		this.noObjetoIva = noObjetoIva;
	}

	public float getDsctNoObjetoIva() {
		return dsctNoObjetoIva;
	}

	public void setDsctNoObjetoIva(float dsctNoObjetoIva) {
		this.dsctNoObjetoIva = dsctNoObjetoIva;
	}
}
