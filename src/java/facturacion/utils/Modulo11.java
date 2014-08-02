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
public class Modulo11 {
     public static String invertirCadena(String cadena) {
	        String cadenaInvertida = "";
	        for (int x = cadena.length() - 1; x >= 0; x--) {
	            cadenaInvertida = cadenaInvertida + cadena.charAt(x);
	        }
	        return cadenaInvertida;
	    }
	 
	    public static int obtenerSumaPorDigitos(String cadena) {
	        int pivote = 2;
	        int longitudCadena = cadena.length();
	        int cantidadTotal = 0;
	        int b = 1;
	        for (int i = 0; i < longitudCadena; i++) {
	            if (pivote == 8) {
	                pivote = 2;
	            }
	            int temporal = Integer.parseInt("" + cadena.substring(i, b));
	            b++;
	            temporal *= pivote;
	            pivote++;
	            cantidadTotal += temporal;
	        }
	        cantidadTotal = 11 - cantidadTotal % 11;
	        return cantidadTotal;
	    }
	    
}
