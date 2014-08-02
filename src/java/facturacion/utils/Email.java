/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturacion.utils;

/**
 *
 * @author aaguerra
 */
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Email {
    
    private String username,contrasena, para, asunto, texto;
    private Properties p;
    private Session sesion;
    private Multipart m;
    
    private Message mensaje; 
	
    public Email(String para, String asunto, String texto) throws MessagingException{
    	this.setup();
    	this.para = para;
    	this.asunto = asunto;
    	this.texto = texto;
    }
    
    private void setup() throws MessagingException {
        //datos de conexion
        this.setUsername("aaguerra11@gmail.com");
        this.setContrasena("@fumanchu753963bebe");
        //propiedades de la conexion
        p = new Properties();
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.port", "587");

        //creamos la sesion
        setSesion(crearSesion());
    }
    
    private Session crearSesion() {
        Session session = Session.getInstance(p,
          new javax.mail.Authenticator() {
            @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, contrasena);
                }
          });
        return session;
    } 
    
    public void addArchivo(File file) throws IOException, MessagingException {
    	if (this.m == null)
    		this.m = new MimeMultipart();
    	else
    		System.out.println("---------- ===== no se creo el multi part mime");
    	MimeBodyPart mb = new MimeBodyPart();
        mb.attachFile(file);
        this.m.addBodyPart(mb);
        
    }
    
    public void send() throws AddressException, MessagingException {
    	//Construimos el Mensaje
    	this.mensaje = new MimeMessage(this.sesion);
    	this.mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress(para));
    	this.mensaje.setSubject(asunto);
    	this.mensaje.setText(texto);
    	if (this.m != null)
    		mensaje.setContent(this.m);
    	else
    		System.out.println("---------- ===== no se agrego el mensaje");
    	//Enviamos el Mensaje
    	Transport.send(mensaje);
    }


	public String getContrasena() {
		return contrasena;
	}


	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}


	public Session getSesion() {
		return sesion;
	}


	public void setSesion(Session sesion) {
		this.sesion = sesion;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}
    
}
