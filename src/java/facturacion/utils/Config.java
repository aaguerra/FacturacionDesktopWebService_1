/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturacion.utils;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author aaguerra
 */
public class Config {
    
    Properties properties = null;
	 
    /** Configuration file name */
    public final static String CONFIG_FILE_NAME = "resources/config.properties";
    //public final static String CONFIG_FILE_NAME = "config.properties";
    
    public final static String SPLIT = "SPLIT";
    
    public final static String RecepcionComprobantePrueba = "RecepcionComprobantePrueba";
    public final static String AutorizacionComprobantePrueba = "AutorizacionComprobantePrueba";
    public final static String RecepcionComprobanteProduccion = "RecepcionComprobanteProduccion";
    public final static String AutorizacionComprobanteProduccion = "AutorizacionComprobanteProduccion";
    
    public final static String ServerFtpToken = "ServerFtpToken";
    public final static String PortFtpToken = "PortFtpToken";
    public final static String UserFtpToken = "UserFtpToken";
    public final static String PassFtpToken = "PassFtpToken";
    
    public final static String UserHttpDjangoRestAdmin = "UserHttpDjangoRestAdmin";
    public final static String ClaveHttpDjangoRestAdmin = "ClaveHttpDjangoRestAdmin";
    public final static String DominioIpDjangoRestAdmin = "DominioIpDjangoRestAdmin";
 
    private Config() {
        this.properties = new Properties();
        try {
            properties.load(Config.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//Configuration
 
    /**
     * Implementando Singleton
     *
     * @return
     */
    public static Config getInstance() {
        //return  new Config();
        return ConfigurationHolder.INSTANCE;
    }
 
    private static class ConfigurationHolder {
 
        private static final Config INSTANCE = new Config();
    }
 
    /**
     * Retorna la propiedad de configuración solicitada
     *
     * @param key
     * @return
     */
    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }//getProperty
    
}
