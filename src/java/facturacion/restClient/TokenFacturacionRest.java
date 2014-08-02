/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturacion.restClient;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import facturacion.model.TokenFacturacion;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author aaguerra
 */
public class TokenFacturacionRest {
    
    public static TokenFacturacion getUploadTokenFacturacion(String ci_ruc){
        try {			
                WebTarget webTarget = Uri.uriUploadTokenFacturacion(ci_ruc);
                Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
                invocationBuilder.header("some-header", "true");						

                Response response = invocationBuilder.get();
                /*
                byte[] g;
                if (response.getStatus() == 200) {				
                        //g = mapper.readValue(response.readEntity(String.class),  new TypeReference<List<Empresa>>() {});
                        System.out.println("---------------------------------");
                        //System.out.println(response.readEntity(String.class));
                        g = response.readEntity(byte[].class);
                        System.out.println(g);
                } else {
                        System.out.println("---===> 0111");
                        g = null;
                }
                return null;
                */
                System.out.println("---===> 0111 status == "+response.getStatus());
                return (response.getStatus() == 200) ? (new ObjectMapper()).readValue(response.readEntity(String.class), TokenFacturacion.class) : null;

        }catch (UnrecognizedPropertyException e) {
                System.out.println("erro en los dats de mapeo de la URI");
                return null;
        }catch (ProcessingException e) {
                System.out.println("Error no se pudo conectar a la URI");
                return null;
        } catch (JsonGenerationException e) {
                System.out.println("---===> 1");
                e.printStackTrace();
                return null;
        } catch (JsonMappingException e) {
                System.out.println("---===> 2");
                e.printStackTrace();
                return null;
        } catch (Exception e) {
                System.out.println("---===> 3");
                e.printStackTrace();
                return null;
        }
    }
}
