/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturacion.ftp;

/**
 *
 * @author aaguerra
 */
import facturacion.utils.Config;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;


public class FtpServer {
	
	
	private static void showServerReply(FTPClient ftpClient) {
	    String[] replies = ftpClient.getReplyStrings();
	    if (replies != null && replies.length > 0) {
	        for (String aReply : replies) {
	            System.out.println("SERVER: " + aReply);
	        }
	    }
	}
	
	public static int sendTokenInputStream(String fileNameServer, String hostDirServer, InputStream localFile ){
	    FTPClient ftpClient = new FTPClient();
	    boolean success = false;
	    BufferedInputStream buffIn = null;
	    try {
	    	ftpClient.connect(Config.getInstance().getProperty(Config.ServerFtpToken), Integer.parseInt( Config.getInstance().getProperty(Config.PortFtpToken)) );
	        ftpClient.login(Config.getInstance().getProperty(Config.UserFtpToken), Config.getInstance().getProperty(Config.PassFtpToken));
	        ftpClient.enterLocalPassiveMode();
	    	/*ftpClient.connect("127.0.0.1", 21);
            ftpClient.login("erpftp", "Tribut@2014");*/
	        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	         
	        int reply = ftpClient.getReplyCode();
            
            System.out.println("Respuesta recibida de conexión FTP:" + reply);
            
            if(!FTPReply.isPositiveCompletion(reply))            
            {
                System.out.println("Imposible conectarse al servidor");
                return -1;
            }
	        
            buffIn = new BufferedInputStream(localFile);//Ruta del archivo para enviar
            ftpClient.enterLocalPassiveMode();
            //crear directorio
            System.out.println(hostDirServer);
            success = ftpClient.makeDirectory(hostDirServer);
            System.out.println("sucess 1133 = "+success);
            //showServerReply(ftpClient);
            success = ftpClient.makeDirectory(hostDirServer+"/token");   
            /*
            System.out.println("sucess 1 = "+success);
            success = ftpClient.makeDirectory("casa111");
            System.out.println("sucess 111 = "+success);
            success = ftpClient.makeDirectory("/usr/erp/token/casa");
            System.out.println("sucess 111 = "+success);
            success = ftpClient.makeDirectory("/casa2");
            System.out.println("sucess 1 = "+success);
            */
    		success = ftpClient.storeFile(hostDirServer+"/token/" + fileNameServer, buffIn);
            //success = ftpClient.storeFile("prueba", buffIn);
    		System.out.println("sucess 2 = "+success);
    		//return (success)? 1:0;
	    } catch (IOException ex) {
	    	
	    } finally {
            try {
                if (ftpClient.isConnected()) {
                	buffIn.close(); //Cerrar envio de arcivos al FTP
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
            	return -1;
                //ex.printStackTrace();
            }
	    }        
	    return (success)? 1:0;
	}
	
	
	public static InputStream getTokenInputStream2(String remote_file_ruta){
	    FTPClient ftpClient = new FTPClient();
	    File downloadFile1 = null;
	    try {
			downloadFile1 = File.createTempFile("tmptokenEmpresa", ".p12");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
                ftpClient.connect(Config.getInstance().getProperty(Config.ServerFtpToken), Integer.parseInt( Config.getInstance().getProperty(Config.PortFtpToken)) );
	        ftpClient.login(Config.getInstance().getProperty(Config.UserFtpToken), Config.getInstance().getProperty(Config.PassFtpToken));
	    	//ftpClient.connect(ConfigurationFtp.getInstance().getProperty(ConfigurationFtp.FTP_SERVER), Integer.parseInt( ConfigurationFtp.getInstance().getProperty(ConfigurationFtp.FTP_PORT)) );
	        //ftpClient.login(ConfigurationFtp.getInstance().getProperty(ConfigurationFtp.FTP_USER), ConfigurationFtp.getInstance().getProperty(ConfigurationFtp.FT_PSWD));
	        //ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	        ftpClient.enterLocalPassiveMode();
	        boolean success;
	        String remoteFile1 = "/0702144833/kepti_lenin_pereira_tinoco.p12";
            downloadFile1 = new File("C:/Users/aaguerra/Desktop/firmado2.p12");
            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
            success = ftpClient.retrieveFile(remoteFile1, outputStream1);
            outputStream1.close();
            
            
            
            //FileInputStream fis = new FileInputStream("C:\\Users\\aaguerra\\Desktop\\arpr\\documentacion\\kepti_lenin_pereira_tinoco.p12");
            FileInputStream fis = new FileInputStream("C:\\Users\\aaguerra\\Desktop\\arpr\\archivos\\0702144833\\kepti_lenin_pereira_tinoco.p12");
            InputStream is = fis; 
            if (success) {
                System.out.println("File #1 has been downloaded successfully. 222adadfsdfadf");
            }else {
                System.out.println("File #1 has been downloaded successfully. 3333");
            };
            return is;
	    } catch (IOException ex) {
	    	 System.out.println("File #1 has been downloaded successfully. 222");
	    } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
            	 System.out.println("File #1 has been downloaded successfully. 3");
            	return null;
                //ex.printStackTrace();
            }
        }
	    return null;
	}
	
	public static InputStream getTokenInputStream(String remote_file_ruta){
	    FTPClient ftpClient = new FTPClient();
	    try {
	    	//ftpClient.connect("127.0.0.1", 21 );
	        //ftpClient.login("erpftp", "Tribut@2014");
                ftpClient.connect(Config.getInstance().getProperty(Config.ServerFtpToken), Integer.parseInt( Config.getInstance().getProperty(Config.PortFtpToken)) );
	        ftpClient.login(Config.getInstance().getProperty(Config.UserFtpToken), Config.getInstance().getProperty(Config.PassFtpToken));
	        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                int reply = ftpClient.getReplyCode();
            
                System.out.println("Respuesta recibida de conexión FTP:" + reply);

                if(!FTPReply.isPositiveCompletion(reply))            
                {
                    System.out.println("Imposible conectarse al servidor");
                    //return -1;
                } else {
                    System.out.println("se conecto al servidor");
                }
	        //ftpClient.enterLocalPassiveMode();
	        // crear directorio
	        //OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(file_ruta));
	        //System.out.println("File #1 has been downloaded successfully. 1");
	        //FileInputStream fis = new FileInputStream("C:\\Users\\aaguerra\\Desktop\\firmado2.p12");
            //InputStream is = fis;
	        System.out.println("File rutaqq="+"1-/"+remote_file_ruta);
	        InputStream is = ftpClient.retrieveFileStream(remote_file_ruta);
	        
	        if (is == null)
	        	System.out.println("File #1 es null token");
	        else
	        	System.out.println("File #1 no es null token");
	        	
	        //return ftpClient.retrieveFileStream(remote_file_ruta);
                return is;
	    } catch (IOException ex) {
	    	 System.out.println("File #1 has been downloaded successfully. 222");
	    } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
            	 System.out.println("File #1 has been downloaded successfully. 3");
            	return null;
                //ex.printStackTrace();
            }
        }
	    return null;
	}
	
	//subir imagenes
	
	public static int sendImgFile(String fileNameServer, String hostDirServer, InputStream localFile ){
		FTPClient ftpClient = new FTPClient();
	    boolean success = false;
	    BufferedInputStream buffIn = null;
	    try {
	    	ftpClient.connect(Config.getInstance().getProperty(Config.ServerFtpToken), Integer.parseInt( Config.getInstance().getProperty(Config.PortFtpToken)) );
	        ftpClient.login(Config.getInstance().getProperty(Config.UserFtpToken), Config.getInstance().getProperty(Config.PassFtpToken));
	        ftpClient.enterLocalPassiveMode();
	    	/*ftpClient.connect("127.0.0.1", 21);
            ftpClient.login("erpftp", "Tribut@2014");*/
	        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	         
	        int reply = ftpClient.getReplyCode();
            
            System.out.println("Respuesta recibida de conexión FTP:" + reply);
            
            if(!FTPReply.isPositiveCompletion(reply))            
            {
                System.out.println("Imposible conectarse al servidor");
                return -1;
            }
	        
            buffIn = new BufferedInputStream(localFile);//Ruta del archivo para enviar
            ftpClient.enterLocalPassiveMode();
            //crear directorio
            success = ftpClient.makeDirectory(hostDirServer);
            System.out.println("sucess 1 = "+success);
            success = ftpClient.makeDirectory(hostDirServer+"/img");
            System.out.println("sucess 233 = "+success);
    		success = ftpClient.storeFile(hostDirServer +"/img/"+ fileNameServer, buffIn);
    		System.out.println("sucess 3 = "+success);
	    } catch (IOException ex) {
	    	
	    } finally {
            try {
                if (ftpClient.isConnected()) {
                	buffIn.close(); //Cerrar envio de arcivos al FTP
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
            	return -1;
                //ex.printStackTrace();
            }
	    }        
	    return (success)? 1:0;
	}
	
	
	public static InputStream getFileInputStream(String remote_file_ruta){
	    FTPClient ftpClient = new FTPClient();
	    try {
	    	ftpClient.connect(Config.getInstance().getProperty(Config.ServerFtpToken), Integer.parseInt( Config.getInstance().getProperty(Config.PortFtpToken)) );
	        ftpClient.login(Config.getInstance().getProperty(Config.UserFtpToken), Config.getInstance().getProperty(Config.PassFtpToken));
	        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	        //ftpClient.enterLocalPassiveMode();
	        // crear directorio
	        //OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(file_ruta));
	        //System.out.println("File #1 has been downloaded successfully. 1");
	        //FileInputStream fis = new FileInputStream("C:\\Users\\aaguerra\\Desktop\\firmado2.p12");
            //InputStream is = fis;
	        System.out.println("File ruta="+"1/"+remote_file_ruta);
	        InputStream is = ftpClient.retrieveFileStream(remote_file_ruta);
	        
	        if (is == null)
	        	System.out.println("File #1 es null");
	        else
	        	System.out.println("File #1 no es null");
	        	
	        //return ftpClient.retrieveFileStream(remote_file_ruta);
            return is;
	    } catch (IOException ex) {
	    	 System.out.println("File #1 has been downloaded successfully. 222");
	    } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
            	 System.out.println("File #1 has been downloaded successfully. 3");
            	return null;
                //ex.printStackTrace();
            }
        }
	    return null;
	}
	
	
	/*
	FTPClient ftp = null;
    
    public FTPUploader(String host, String user, String pwd) throws Exception{
        ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
        ftp.connect(host);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftp.login(user, pwd);
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
    }
    public void uploadFile(String localFileFullName, String fileName, String hostDir)
            throws Exception {
        try(InputStream input = new FileInputStream(new File(localFileFullName))){
        this.ftp.storeFile(hostDir + fileName, input);
        }
    }
 
    public void disconnect(){
        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
            } catch (IOException f) {
                // do nothing as file is already saved to server
            }
        }
    }
    public static void main(String[] args) throws Exception {
        System.out.println("Start");
        FTPUploader ftpUploader = new FTPUploader("ftp.journaldev.com", "ftpUser", "ftpPassword");
        //FTP server path is relative. So if FTP account HOME directory is "/home/pankaj/public_html/" and you need to upload 
        // files to "/home/pankaj/public_html/wp-content/uploads/image2/", you should pass directory parameter as "/wp-content/uploads/image2/"
        ftpUploader.uploadFile("D:\\Pankaj\\images\\MyImage.png", "image.png", "/wp-content/uploads/image2/");
        ftpUploader.disconnect();
        System.out.println("Done");
    }
 
	*/
	 
}