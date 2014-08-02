/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturacion.restClient;


import facturacion.utils.Config;
import java.net.URI;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.filter.HttpBasicAuthFilter;
/**
 *
 * @author aaguerra
 */
public class Uri {

    private static URI getBaseURI() {
        return UriBuilder.fromUri( Config.getInstance().getProperty(Config.DominioIpDjangoRestAdmin) ).build();
        //return UriBuilder.fromUri( "http://172.241.225.146:80" ).build();
    }
	
    private static WebTarget getClient() {
        //return ClientBuilder.newClient().register(new HttpBasicAuthFilter("root","@fumanchu753963bebe") ).target(getBaseURI());
        return ClientBuilder.newClient().register(new HttpBasicAuthFilter(Config.getInstance().getProperty(Config.UserHttpDjangoRestAdmin)
                ,Config.getInstance().getProperty(Config.ClaveHttpDjangoRestAdmin))).target(getBaseURI());
    }
 
    public static WebTarget uriPostTokenFacturacion(String ci_ruc) throws ProcessingException{
            return getClient().path("administracion/TokenFacturacion/"+ci_ruc+"/");
    }
    public static WebTarget uriUploadTokenFacturacion(String ci_ruc) throws ProcessingException{
            return getClient().path("administracion/cargarTokenFacturacion/"+ci_ruc+"/");
    }

    public static WebTarget uriUploadImageEmpresa(String ci_ruc) throws ProcessingException{
            return getClient().path("administracion/cargarImgEmpresa/"+ci_ruc+"/");
    }    
    
    //Info empresa
    public static WebTarget uriEmpresaDetail(String cedula) throws ProcessingException{
        return getClient().path("administracion/empresas/"+cedula+"/");
    }
}
