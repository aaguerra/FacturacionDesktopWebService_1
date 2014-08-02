/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturacion.pdf;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import facturacion.model.Item;
import facturacion.model.Pedido;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;



/**
 *
 * @author aaguerra
 */
public class FacturaPdf {
    private static Font fontBold = new Font(Font.FontFamily.COURIER, 10, Font.BOLD);
    private static Font fontNormal = new Font(Font.FontFamily.COURIER, 9, Font.NORMAL);
	
	private Pedido pedido;
	
	private float base0;
	private float dscto0 ;
	private float base12 ;
	private float dscto12 ;
	private float ice_ ;
	private float iva_ ;
	private float total ;
	private float dsctoNObjetoIva;	
	private float noObjetoIva;
	private float importetotal;
	private float propina;
	
	private List<Item> itemList;
	
	//private List<Item> newListP;	
	
	public FacturaPdf(Pedido pedido, List<Item> itemList) {
		this.pedido = pedido;
		this.itemList = itemList; 
	}
	
	public void init() {
		//System.out.println("inicio pedido detail 1");
		//Map<String, Boolean> map = new HashMap<String, Boolean>();
		
		//Item a = new Item();
		//System.out.println("casa 3");
	  	//String  codigoComparar = "";
		
		//newListP = new ArrayList<Item>();
		
		this.base0 = 0;
		this.dscto0 = 0;
		this.base12 = 0;
		this.dscto12 = 0;
		this.ice_ = 0;
		this.iva_ =0 ;
		this.total = 0;
		this.noObjetoIva = 0;
		this.dsctoNObjetoIva = 0;
		System.out.println("--------==================-------------=========");
		for (Item p : this.itemList) {	
			//p.setTotal_facturar( p.totalProcesado(Float.parseFloat(p.getTotalItem())) );
			if (p.getIva().getCodigo() != 6 ) {
				this.total = this.total + p.getTotal_facturar();
			    this.base0 = this.base0 + p.getBase0();
			    this.dscto0 = this.dscto0 + p.getDscto0();
			    this.base12 = this.base12 + p.getBase12();
			    this.dscto12 = this.dscto12 + p.getDscto12();
			    this.ice_ = this.ice_ + p.getIce_();
			    this.iva_ =  this.iva_ + p.getIva_();
			} else {
				this.total = this.total + p.getTotal_facturar();
				this.noObjetoIva = this.noObjetoIva +  p.getTotal_facturar();
        		this.dsctoNObjetoIva = this.dsctoNObjetoIva + p.getDsctoNObjetoIva();
			}
		}
		
		this.base0 = this.float2d(this.base0);
		this.dscto0 = this.float2d(this.dscto0);
		this.base12 = this.float2d(this.base12);
		this.dscto12 = this.float2d(this.dscto12);
		this.ice_ = this.float2d(this.ice_);
		this.iva_ = this.float2d(this.iva_);
		this.noObjetoIva = this.float2d(this.noObjetoIva);
		this.dsctoNObjetoIva = this.float2d(this.dsctoNObjetoIva);
		this.total = this.float2d(this.total);
		
		this.propina = this.float2d(this.pedido.getPropina());
		this.importetotal = this.float2d(this.total + this.pedido.getPropina());
	}
	
	
	
	public File facturarGenerarDocumentoFile() throws IOException, DocumentException{
		//FacesContext facesContext = FacesContext.getCurrentInstance();
		//this.id_pedido = Integer.parseInt( facesContext.getExternalContext().getRequestParameterMap().get("pedido") );
		File file = File.createTempFile("tmpFacturaPdf", ".pdf");
		generarFacturaFile( "My Java Zone", "Bienvenidos a My Java Zone ", file);
		return file;
	
	}
        /*
	public void addImage(Document document){
		BufferedImage bufferedImage;
		try
		{
			InputStream img = null;
			img =  FtpServer.getFileInputStream( (TokenFacturacionRest.getImageFacturacion(this.pedido.getProveedor_ci_ruc())).getImgFile_ruta() );
			if (img!= null) {
				bufferedImage = ImageIO.read(img);  
				System.out.println("entre a la imgaen 3");
			    Image foto = Image.getInstance(bufferedImage, null);
			    System.out.println("entre a la imgaen 4");
			    foto.scaleToFit(100, 100);
			    foto.setAbsolutePosition(500f, 713f);
			    //foto.setAlignment(Chunk.ALIGN_MIDDLE);
			    document.add(foto);
			}
		} catch (IOException ioe) {//"Failed to render image"
        } catch ( Exception e )
		{
		    e.printStackTrace();
		}
	}
	
	*/
	public void generarFacturaFile( String nombre, String tituloFactura, File file) throws IOException, DocumentException {
	    		
	      Document document = getDocument();
	      //PdfWriter.getInstance(document, new FileOutputStream("factura.pdf"));
	      PdfWriter.getInstance(document,  new FileOutputStream( file ));
	      document.open();
	      //addImage(document);
	      //System.out.println("casa 2");
	      PdfPTable table = getTableCabecera();
	      document.add(table);
	      
	      table = getTable();
	    
	      //document.add(getHeader(tituloFactura));
	      document.add(getInformation(" "));
		 
	      table.addCell(getCellBox("CODIGO"));
	      table.addCell(getCellBox("DETALLE"));
	      table.addCell(getCellBox("CANT."));
	      table.addCell(getCellBox("P.UNIT."));
	      table.addCell(getCellBox("TOTAL"));
	      
	      
	      
	      for (Item p : itemList) {
	    	  
	    	  	table.addCell(getCellNoBorder( ((p.getTipo_item() == 0)? p.getCodigo_producto():p.getCodigo_empresa()) ));
				table.addCell(getCell( ( (p.getTipo_item() == 0)? (p.getProducto()+" - "+p.getDetalle()):p.getDetalle()  ) ));
				table.addCell(getCell(p.getCantidad()));
				if (p.getIva().getCodigo() != 6 ) {
					table.addCell(getCell(floatPrint2d( ((p.getBase0()+p.getBase12()))/Float.parseFloat(p.getCantidad()) )));
								
					//table.addCell(getCell( floatPrint2d( baseImponible + p.getIva_() ) ) );
					table.addCell(getCell( floatPrint2d( p.getBase0()+p.getBase12() ) ) );
				} else {
					table.addCell(getCell(floatPrint2d(p.getPvp()) ));
					
					//table.addCell(getCell( floatPrint2d( baseImponible + p.getIva_() ) ) );
					table.addCell(getCell( floatPrint2d( Float.parseFloat(p.getTotalItem()) ) ) );
				}
	      }  
	      
		    table.addCell(getCellNoBorder(" "));
		    table.addCell(getCell(" "));
		    table.addCell(getCell(" "));
		    table.addCell(getCell(" "));
		    table.addCell(getCell(" "));
		     
		    document.add(table);
		    document.add(getInformation(" "));
		    
		    table = getTableTotal();
		    
		    table.addCell(getCellBox("BASE 0"));
		    table.addCell(getCellBox("DSCTO."));
		    table.addCell(getCellBox("BASE 12"));
		    table.addCell(getCellBox("DSCTO."));
		    table.addCell(getCellBox("ICE"));
		    table.addCell(getCellBox("IVA"));
		    table.addCell(getCellBox("NO. OBJT."));
		    table.addCell(getCellBox("DSCTO."));
		    table.addCell(getCellBox("T. FACT."));
		    table.addCell(getCellBox("PROPINA"));
		    table.addCell(getCellBox("TOTAL"));
		    
		    
		    table.addCell(getCellBox( floatPrint2d (this.base0) ));
		    table.addCell(getCellBox( floatPrint2d(this.dscto0) ));
		    table.addCell(getCellBox(floatPrint2d(this.base12) ));
		    table.addCell(getCellBox( floatPrint2d(this.dscto12) ));
		    table.addCell(getCellBox( floatPrint2d(this.ice_) ));
		    table.addCell(getCellBox( floatPrint2d(this.iva_) ));
		    table.addCell(getCellBox( floatPrint2d(this.noObjetoIva) ));
		    table.addCell(getCellBox( floatPrint2d(this.dsctoNObjetoIva) ));
		    table.addCell(getCellBox( floatPrint2d( this.float2d(this.total) ) ));
		    table.addCell(getCellBox( floatPrint2d(this.propina) ));
		    table.addCell(getCellBox( floatPrint2d(this.importetotal) ));
		    
		    document.add(table);
		    document.add(getInformation(" "));
		    document.add(getInformation(" "));
	      
			document.close();
	      
		}
		
		private String floatPrint2d(float num){
			int partInt = (int) num;
			float partDecimal = float2d(num-partInt);
			if (Float.toString(partDecimal).length() == 3)
				return Float.toString(num)+"0";
			else
				return Float.toString(num);
		}

		private float float2d(float num){
			return (float) (Math.round(num*100)/100.00);
		}
		private Document getDocument(){
	   	 Document document = new Document(new Rectangle( getConvertCmsToPoints(26), getConvertCmsToPoints(30)));
	      document.setMargins(0, 0, 80, 1);
	      return document;
	    }
	    
	    @SuppressWarnings("unused")
		private Paragraph getHeader(String header) {
		     Paragraph paragraph = new Paragraph();
		    Chunk chunk = new Chunk();
		    paragraph.setAlignment(Element.ALIGN_CENTER);
		    chunk.append( header + getCurrentDateTime() + "\n");
		    chunk.setFont(fontBold);
		    paragraph.add(chunk);
		    return paragraph;
	    }
	    
	    private String getCurrentDateTime() {
	        Date dNow = new Date( );
	          SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yy '-' hh:mm");
	        return ft.format(dNow);
	      }
	    
	    private Paragraph getInformation(String informacion) {
		     Paragraph paragraph = new Paragraph();
		     Chunk chunk = new Chunk();
		    paragraph.setAlignment(Element.ALIGN_CENTER);
		    chunk.append(informacion);
		    chunk.setFont(fontNormal);
		    paragraph.add(chunk);
		     return paragraph;
	     }
	    
	    @SuppressWarnings("unused")
		private Paragraph getInformationFooter(String informacion) {
		      Paragraph paragraph = new Paragraph();
		      Chunk chunk = new Chunk();
		     paragraph.setAlignment(Element.ALIGN_CENTER);
		     chunk.append(informacion);
		     chunk.setFont(new Font(Font.FontFamily.COURIER, 8, Font.NORMAL));
		     paragraph.add(chunk);
		      return paragraph;
	      }
	 
	    private PdfPTable getTable() throws DocumentException {
		      PdfPTable table = new PdfPTable(5);
		      table.setWidths(new int[]{5, 25, 5,5,5});
		      return table;
	    }
	    
	    private PdfPTable getTableTotal() throws DocumentException {
		      //PdfPTable table = new PdfPTable(7);
		      //table.setWidths(new int[]{5, 5, 5, 5, 5, 5, 5});
	    	PdfPTable table = new PdfPTable(11);
	    	table.setWidths(new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5});
		      return table;
	   }
	    

		public String complete_Numero_documento(String cad) {
			//return numero_documento_html;
	        int tam = 9 - cad.length();
	        for (int i = 0; i < tam ; i++ )
	            cad = "0"+cad;
	        return cad;
		}

	    
	    private PdfPTable getTableCabecera() throws DocumentException, IOException {
	   	 PdfPTable table = new PdfPTable(5);
	     	table.setWidths(new int[]{8, 20, 2, 8,9});
     	
	       table.addCell(getCellBorderTop("Proeedor:"));
	       table.addCell(getCellBorderTop(this.pedido.getProveedor_ci_ruc()));
	       table.addCell(getCellBorderTop("  "));
	       table.addCell(getCellBorderTop(" "));
	       table.addCell(getCellBorderTop(" "));
	       
	       table.addCell(getCellNoBorder("Razon social:"));
	       table.addCell(getCellNoBorder(this.pedido.getProveedor_razon_social()));
	       table.addCell(getCellNoBorder("  "));
	       table.addCell(getCellNoBorder(" "));
	       table.addCell(getCellNoBorder(" "));
	       
	       table.addCell(getCellNoBorder("Vendedor:"));
	       table.addCell(getCellNoBorder(this.pedido.getVendedor_ci_ruc()));
	       table.addCell(getCellNoBorder("  "));
	       table.addCell(getCellNoBorder(" "));
	       table.addCell(getCellNoBorder(" "));
	       
	       table.addCell(getCellNoBorder("Factura:"));
	         table.addCell(getCellNoBorder( this.pedido.getEstablecimiento_proovedor()+"-"+this.pedido.getPunto_emision()+"-"+complete_Numero_documento(this.pedido.getNumero_documento()) ));
	         table.addCell(getCellNoBorder("  "));
	         table.addCell(getCellNoBorder(" "));
	         table.addCell(getCellNoBorder(" "));
	              
	       table.addCell(getCellNoBorder("  "));
	       table.addCell(getCellNoBorder("  "));
	       table.addCell(getCellNoBorder("  "));
	       table.addCell(getCellNoBorder("  "));
	       table.addCell(getCellNoBorder("  "));
	     	
	       table.addCell(getCellBorderTop("Cliente:"));
	       table.addCell(getCellBorderTop(this.pedido.getCliente_ci_ruc()));
	       table.addCell(getCellBorderTop("  "));
	       table.addCell(getCellBorderTop("Fecha:"));
	       table.addCell(getCellBorderTop(this.pedido.getFechaFactura() ));
	       
	       table.addCell(getCellNoBorder("Razon Social:"));
	       table.addCell(getCellNoBorder(this.pedido.getCliente_razon_social()));
	       table.addCell(getCellNoBorder("  "));
	       table.addCell(getCellNoBorder("Dias Credito:"));
	       table.addCell(getCellNoBorder(""+this.pedido.getDias_credito()));
	       
	       table.addCell(getCellNoBorder("Direccion:"));
	       table.addCell(getCellNoBorder(this.pedido.getDireccion()));
	       table.addCell(getCellNoBorder("  "));
	       table.addCell(getCellNoBorder("Forma de Pago:"));
	       table.addCell(getCellNoBorder(this.pedido.getForma_pago()));
	              
	       table.addCell(getCellNoBorder("Establecimiento:"));
	       table.addCell(getCellNoBorder(this.pedido.getEstablecimiento_cliente()));
	       table.addCell(getCellNoBorder("  "));
	       table.addCell(getCellNoBorder("  "));
	       table.addCell(getCellNoBorder("  "));
	       
	       return table;
	   }
	    
	    private PdfPCell getCellBorderTop(String text) throws DocumentException, IOException {
		      Chunk chunk = new Chunk();
		      chunk.append(text);
		      chunk.setFont(fontNormal);
		      PdfPCell cell = new PdfPCell(new Paragraph(chunk));
			   cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			   cell.setBorder(Rectangle.TOP);
			   return cell;
	    }
	    
	    
	    private PdfPCell getCellNoBorder(String text) throws DocumentException, IOException {
		      Chunk chunk = new Chunk();
		      chunk.append(text);
		      chunk.setFont(fontNormal);
		      PdfPCell cell = new PdfPCell(new Paragraph(chunk));
			   cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			   cell.setBorder(Rectangle.NO_BORDER);
			   return cell;
	    }
	    
	    private PdfPCell getCell(String text) throws DocumentException, IOException {
		      Chunk chunk = new Chunk();
		      chunk.append(text);
		      chunk.setFont(fontNormal);
		      PdfPCell cell = new PdfPCell(new Paragraph(chunk));
			   cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			   cell.setBorder(Rectangle.LEFT);
			   return cell;
	   }
	    
	    private PdfPCell getCellBox(String text) throws DocumentException, IOException {
		      Chunk chunk = new Chunk();
		      chunk.append(text);
		      chunk.setFont(fontNormal);
		      PdfPCell cell = new PdfPCell(new Paragraph(chunk));
			   cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			   cell.setBorder(Rectangle.BOX);
			   return cell;
	   }
	    
	    private float getConvertCmsToPoints(float cm) {
	     return cm * 28.4527559067f;
	    }

		public float getDsctoNObjetoIva() {
			return dsctoNObjetoIva;
		}

		public void setDsctoNObjetoIva(float dsctoNObjetoIva) {
			this.dsctoNObjetoIva = dsctoNObjetoIva;
		}

		public float getNoObjetoIva() {
			return noObjetoIva;
		}

		public void setNoObjetoIva(float noObjetoIva) {
			this.noObjetoIva = noObjetoIva;
		}

		public float getImportetotal() {
			return importetotal;
		}

		public void setImportetotal(float importetotal) {
			this.importetotal = importetotal;
		}

		public float getPropina() {
			return propina;
		}

		public void setPropina(float propina) {
			this.propina = propina;
		}
	    

}
