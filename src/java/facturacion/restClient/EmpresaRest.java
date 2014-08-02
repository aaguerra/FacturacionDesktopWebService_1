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
import facturacion.model.Empresa;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**
 *
 * @author aaguerra
 */
public class EmpresaRest {
    
    public static Empresa GetEmpresaDetail(String empresa){
        try {
                Empresa e = new Empresa();
                ObjectMapper mapper = new ObjectMapper();
                System.out.println("empresa="+empresa);
                WebTarget webTarget = Uri.uriEmpresaDetail(empresa);                
                Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
                invocationBuilder.header("some-header", "true");						

                Response response = invocationBuilder.get();
                int in = response.getStatus(); 
                System.out.println("status="+in);
                if( in== 200){
                        String cad = response.readEntity(String.class);
                        e = mapper.readValue(cad, Empresa.class);			
                        System.out.println("FULLLLLLLLL");
                }else{
                        e = null;
                        System.out.println("EMPTYYYYYYYYy");
                }
                return e;
                //return (response.getStatus() == 200) ? mapper.readValue(response.readEntity(String.class),Empresa.class) : null;

        }catch (UnrecognizedPropertyException e) {
                System.out.println("error en los datooooos de mapeo de la URI");
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
