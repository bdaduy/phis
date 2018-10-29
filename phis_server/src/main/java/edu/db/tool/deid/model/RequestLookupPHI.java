/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.db.tool.deid.model;

import com.sun.crypto.provider.SunJCE;
import java.security.spec.KeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;


/**
 *
 * @author dbui
 */
public class RequestLookupPHI {

    public String documentId;
    public String patientId;
    public String method;
    public String text;

    public void Decrypt(String password){
        documentId=Decrypt(documentId,password);
        patientId=Decrypt(patientId,password);
        method=Decrypt(method,password);
        text=Decrypt(text,password);
        
    }
    
    public String Decrypt(String encrypted,String password) {
        if(encrypted==null)
            return null;
        try {
            byte[] decodedValue = new BASE64Decoder().decodeBuffer(encrypted);
            
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding", new SunJCE());
      
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            KeySpec spec = new PBEKeySpec(password.toCharArray(), "phi lookup request".getBytes("UTF-8"), 1000, 128);
            SecretKey tmp = factory.generateSecret(spec);
            byte[] encodedBytes = tmp.getEncoded();

            c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(encodedBytes, "AES"), new IvParameterSpec(encodedBytes));
                        
            byte[] decValue = c.doFinal(decodedValue);
            
            return new String(decValue);
        } catch (Exception ex) {
            Logger.getLogger(RequestLookupPHI.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
  }
}
