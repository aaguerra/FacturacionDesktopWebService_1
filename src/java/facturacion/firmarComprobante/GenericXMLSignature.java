/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturacion.firmarComprobante;

/**
 *
 * @author aaguerra
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import es.mityc.firmaJava.libreria.utilidades.UtilidadTratarNodo;
import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.FirmaXML;
//import es.mityc.javasign.issues.PassStoreKS;
import es.mityc.javasign.pkstore.CertStoreException;
import es.mityc.javasign.pkstore.IPKStoreManager;
import es.mityc.javasign.pkstore.keystore.KSStore;
import java.io.FileInputStream;
import java.io.InputStream;

public abstract class GenericXMLSignature {
  /*
       * <p>
       * Almacén PKCS12 con el que se desea realizar la firma
       * </p>
       */
      //public final static String PKCS12_RESOURCE = "C:\\Users\\Christian\\Desktop\\CSVdePrueba\\kepti_lenin_pereira_tinoco.p12";
      public final static String PKCS12_RESOURCE = "C:\\Users\\aaguerra\\Desktop\\arpr\\documentacion\\kepti_lenin_pereira_tinoco.p12";
      /*
       * <p>
       * Constraseña de acceso a la clave privada del usuario
       * </p>
       */
      public final static String PKCS12_PASSWORD = "Mijita69152129";
  
      /*
       * <p>
       * Directorio donde se almacenará el resultado de la firma
       * </p>
       */
      public final static String OUTPUT_DIRECTORY = "C:\\Users\\Christian\\Desktop\\";
  
      /*
       * <p>
       * Ejecución del ejemplo. La ejecución consistirá en la firma de los datos
       * creados por el método abstracto <code>createDataToSign</code> mediante el
       * certificado declarado en la constante <code>PKCS12_FILE</code>. El
       * resultado del proceso de firma será almacenado en un fichero XML en el
       * directorio correspondiente a la constante <code>OUTPUT_DIRECTORY</code>
       * del usuario bajo el nombre devuelto por el método abstracto
       * <code>getSignFileName</code>
       * </p>
       */
      public org.w3c.dom.Document execute(InputStream token, String clave) {
          System.out.println("---- 1");
          Document docSigned = null;
          // Obtencion del gestor de claves
          IPKStoreManager storeManager = getPKStoreManager(token, clave);
         if (storeManager == null) {
              System.err.println("El gestor de claves no se ha obtenido correctamente.");
              return null;
         }
         System.out.println("---- 2");
         // Obtencion del certificado para firmar. Utilizaremos el primer
         // certificado del almacen.
         X509Certificate certificate = getFirstCertificate(storeManager);
         if (certificate == null) {
             System.err.println("No existe ningún certificado para firmar.");
             return null;
         }
         System.out.println("---- 3");
         // Obtención de la clave privada asociada al certificado
         PrivateKey privateKey;
         try {
             privateKey = storeManager.getPrivateKey(certificate);
         } catch (CertStoreException e) {
             System.err.println("Error al acceder al almacén.");
             return null;
         }
         System.out.println("---- 4");
         // Obtención del provider encargado de las labores criptográficas
         Provider provider = storeManager.getProvider(certificate);
         System.out.println("---- 5");
         /*
          * Creación del objeto que contiene tanto los datos a firmar como la
          * configuración del tipo de firma
          */
         DataToSign dataToSign = createDataToSign();
         System.out.println("---- 6");
         /*
          * Creación del objeto encargado de realizar la firma
          */
         FirmaXML firma = new FirmaXML();
         System.out.println("---- 7");
         // Firmamos el documento
         
         System.out.println("---- 8");
         try {
             Object[] res = firma.signFile(certificate, dataToSign, privateKey, provider);
             docSigned = (Document) res[0];
             System.out.println("---- 9");
         } catch (Exception ex) {             
             System.err.println("Error realizando la firma");
             ex.printStackTrace();
             System.out.println("---- 10");
             return null;             
         }
         System.out.println("---- 11");
         // Guardamos la firma a un fichero en el home del usuario
         
         //String filePath = OUTPUT_DIRECTORY + File.separatorChar + getSignatureFileName();
         //System.out.println("Firma salvada en en: " + filePath);
         System.out.println("---- 12");
         //saveDocumentToFile(docSigned, getSignatureFileName());
         System.out.println("---- 13");
         
         return docSigned;
     }
 
     /*
      * <p>
      * Crea el objeto DataToSign que contiene toda la información de la firma
      * que se desea realizar. Todas las implementaciones deberán proporcionar
      * una implementación de este método
      * </p>
      * 
      * @return El objeto DataToSign que contiene toda la información de la firma
      *         a realizar
      */
     protected abstract DataToSign createDataToSign();
 
     /*
      * <p>
      * Nombre del fichero donde se desea guardar la firma generada. Todas las
      * implementaciones deberán proporcionar este nombre.
      * </p>
      * 
      * @return El nombre donde se desea guardar la firma generada
      */
     protected abstract String getSignatureFileName();
 
     /*
      * <p>
      * Escribe el documento a un fichero.
      * </p>
      * 
      * @param document
      *            El documento a imprmir
      * @param pathfile
      *            El path del fichero donde se quiere escribir.
      */
     private void saveDocumentToFile(Document document, String pathfile) {
         try {
             FileOutputStream fos = new FileOutputStream(pathfile);
             UtilidadTratarNodo.saveDocumentToOutputStream(document, fos, true);
         } catch (FileNotFoundException e) {
             System.err.println("Error al salvar el documento");
             e.printStackTrace();
             System.exit(-1);
         }
     }
 
     /*
      * <p>
      * Escribe el documento a un fichero. Esta implementacion es insegura ya que
      * dependiendo del gestor de transformadas el contenido podría ser alterado,
      * con lo que el XML escrito no sería correcto desde el punto de vista de
      * validez de la firma.
      * </p>
      * 
      * @param document
      *            El documento a imprmir
      * @param pathfile
      *            El path del fichero donde se quiere escribir.
      */
     @SuppressWarnings("unused")
     private void saveDocumentToFileUnsafeMode(Document document, String pathfile) {
         TransformerFactory tfactory = TransformerFactory.newInstance();
         Transformer serializer;
         try {
             serializer = tfactory.newTransformer();
 
             serializer.transform(new DOMSource(document), new StreamResult(new File(pathfile)));
         } catch (TransformerException e) {
             System.err.println("Error al salvar el documento");
             e.printStackTrace();
             System.exit(-1);
         }
     }
 
     /*
      * <p>
      * Devuelve el <code>Document</code> correspondiente al
      * <code>resource</code> pasado como parámetro
      * </p>
      * 
      * @param resource
      *            El recurso que se desea obtener
      * @return El <code>Document</code> asociado al <code>resource</code>
      */
     protected Document getDocument(String resource) {
         Document doc = null;
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         dbf.setNamespaceAware(true);
         try {
            //doc = dbf.newDocumentBuilder().parse(this.getClass().getResourceAsStream(resource));
            FileInputStream fis = new FileInputStream(resource);
            InputStream is = fis; 
            doc = dbf.newDocumentBuilder().parse(is);
            fis.close();
         } catch (ParserConfigurationException ex) {
             System.err.println("Error al parsear el documento");
             ex.printStackTrace();
             System.exit(-1);
         } catch (SAXException ex) {
             System.err.println("Error al parsear el documento");
             ex.printStackTrace();
             System.exit(-1);
         } catch (IOException ex) {
             System.err.println("Error al parsear el documento");
             ex.printStackTrace();
             System.exit(-1);
         } catch (IllegalArgumentException ex) {
             System.err.println("Error al parsear el documento");
             ex.printStackTrace();
             System.exit(-1);
         }
         return doc;
     }
 
     /*
      * <p>
      * Devuelve el contenido del documento XML
      * correspondiente al <code>resource</code> pasado como parámetro
      * </p> como un <code>String</code>
      * 
      * @param resource
      *            El recurso que se desea obtener
      * @return El contenido del documento XML como un <code>String</code>
      */
     protected String getDocumentAsString(String resource) {
         Document doc = getDocument(resource);
         TransformerFactory tfactory = TransformerFactory.newInstance();
         Transformer serializer;
         StringWriter stringWriter = new StringWriter();
         try {
             serializer = tfactory.newTransformer();
             serializer.transform(new DOMSource(doc), new StreamResult(stringWriter));
         } catch (TransformerException e) {
             System.err.println("Error al imprimir el documento");
             e.printStackTrace();
             System.exit(-1);
         }
 
         return stringWriter.toString();
     }
 
     /*
      * <p>
      * Devuelve el gestor de claves que se va a utilizar
      * </p>
      * 
      * @return El gestor de claves que se va a utilizar</p>
      */
     private IPKStoreManager getPKStoreManager(InputStream token, String clave) {
         IPKStoreManager storeManager = null;
         try {
            System.out.println(PKCS12_RESOURCE);
            System.out.println(PKCS12_PASSWORD);
            //FileInputStream fis = new FileInputStream("C:\\Users\\aaguerra\\Desktop\\arpr\\documentacion\\kepti_lenin_pereira_tinoco.p12");
            //FileInputStream fis = new FileInputStream("C:\\Users\\aaguerra\\Desktop\\arpr\\archivos\\0702144833\\token\\kepti_lenin_pereira_tinoco.p12");
            //FileInputStream fis = new FileInputStream("C:\\Users\\\\aaguerra\\Desktop\\firmado2.p12");
            //InputStream is = fis;            
            KeyStore ks = KeyStore.getInstance("PKCS12");
            //ks.load(this.getClass().getResourceAsStream(PKCS12_RESOURCE), PKCS12_PASSWORD.toCharArray());
            //ks.load(is, PKCS12_PASSWORD.toCharArray());
            ks.load(token, clave.toCharArray());
            storeManager = new KSStore(ks, new PassStoreKS(PKCS12_PASSWORD));
            //fis.close();
         } catch (KeyStoreException ex) {
             System.err.println("No se puede generar KeyStore PKCS12");
             ex.printStackTrace();
             System.exit(-1);
         } catch (NoSuchAlgorithmException ex) {
             System.err.println("No se puede generar KeyStore PKCS12");
             ex.printStackTrace();
             System.exit(-1);
         } catch (CertificateException ex) {
             System.err.println("No se puede generar KeyStore PKCS12");
             ex.printStackTrace();
             System.exit(-1);
         }catch (FileNotFoundException ex){
             System.err.println("No se encontro el archivo");
             ex.printStackTrace();
             System.exit(-1);
         }catch (IOException ex) {
             System.err.println("No se puede generar KeyStore PKCS12");
             ex.printStackTrace();
             System.exit(-1);
         }
         return storeManager;
     }
 
     /*
      * <p>
      * Recupera el primero de los certificados del almacén.
      * </p>
      * 
      * @param storeManager
      *            Interfaz de acceso al almacén
      * @return Primer certificado disponible en el almacén
      */
     private X509Certificate getFirstCertificate(
             final IPKStoreManager storeManager) {
         List<X509Certificate> certs = null;
         try {
             certs = storeManager.getSignCertificates();
         } catch (CertStoreException ex) {
             System.err.println("Fallo obteniendo listado de certificados");
             System.exit(-1);
         }
         if ((certs == null) || (certs.size() == 0)) {
             System.err.println("Lista de certificados vacía");
             System.exit(-1);
         }
 
         X509Certificate certificate = certs.get(0);
         return certificate;
     }
}
