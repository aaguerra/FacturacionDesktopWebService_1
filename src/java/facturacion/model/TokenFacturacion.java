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
public class TokenFacturacion {
    
    private int id;
    private String clave;
    private byte[] file;	
    private String fila_ruta;
    private String imgFile_ruta;
    
    public TokenFacturacion () {	
    }

    public TokenFacturacion(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFila_ruta() {
        return fila_ruta;
    }

    public void setFila_ruta(String fila_ruta) {
        this.fila_ruta = fila_ruta;
    }

    public String getImgFile_ruta() {
        return imgFile_ruta;
    }

    public void setImgFile_ruta(String imgFile_ruta) {
        this.imgFile_ruta = imgFile_ruta;
    }
}
