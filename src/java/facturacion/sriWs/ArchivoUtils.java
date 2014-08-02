/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facturacion.sriWs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aaguerra
 */
public class ArchivoUtils {
    public static byte[] archivoToByte(File file) throws IOException{
        byte[] buffer = new byte[(int)file.length()];
        InputStream ios = null;
        try
        {
          ios = new FileInputStream(file);
          if (ios.read(buffer) == -1) {
            throw new IOException("EOF reached while trying to read the whole file");
          }
          return buffer;
        }
        finally
        {
          try
          {
            if (ios != null) {
              ios.close();
            }
          }
          catch (IOException e)
          {
            Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, e);
          }
        }
    }
}
