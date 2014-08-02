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
import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.XAdESSchemas;
import es.mityc.firmaJava.role.SimpleClaimedRole;
import es.mityc.javasign.EnumFormatoFirma;
import es.mityc.javasign.xml.refs.AllXMLToSign;
import es.mityc.javasign.xml.refs.InternObjectToSign;
import es.mityc.javasign.xml.refs.ObjectToSign;
import org.w3c.dom.Document;

public class XAdESBESSignature extends GenericXMLSignature{
    //private final static String RESOURCE_TO_SIGN = "C:\\Users\\aaguerra\\Desktop\\filePrueba.xml";
    //private final static String SIGN_FILE_NAME = "C:\\Users\\aaguerra\\Desktop\\filefimrado1.xml";
    private final static String RESOURCE_TO_SIGN = "C:\\tributasoft\\prueba\\filePrueba003000000055.xml";
    private final static String SIGN_FILE_NAME = "C:\\tributasoft\\prueba\\filefimrado2.xml";
    
    private Document doc;
    
    public XAdESBESSignature(Document doc){
        this.doc = doc;
    }
    
    @Override
    protected DataToSign createDataToSign() {
        DataToSign dataToSign = new DataToSign();
        dataToSign.setXadesFormat(EnumFormatoFirma.XAdES_BES);
        dataToSign.setEsquema(XAdESSchemas.XAdES_132);
        dataToSign.setXMLEncoding("UTF-8");
        // Se añade un rol de firma
        //dataToSign.addClaimedRol(new SimpleClaimedRole("Rol de firma"));
        dataToSign.setEnveloped(true);
        dataToSign.addObject(new ObjectToSign(new InternObjectToSign("comprobante"), "contenido comprobante", null, "text/xml", null));
        dataToSign.setParentSignNode("comprobante");
        //dataToSign.addObject(new ObjectToSign(new AllXMLToSign(), "comprobante", null, "text/xml", null));
        
        //Document docToSign = getDocument(RESOURCE_TO_SIGN);
        //dataToSign.setDocument(docToSign);
        dataToSign.setDocument(doc);
        return dataToSign;

    }
    @Override
    protected String getSignatureFileName() {
        return SIGN_FILE_NAME;
    }
}