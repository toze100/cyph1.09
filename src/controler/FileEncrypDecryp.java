/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import debug.D;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
 
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;


/**
 *
 * @author tony
 */
public class FileEncrypDecryp {

 private final String algo;
 private final String path;
 private final String password;
 //private static final String PASSWORD = "X#6&uw@j"; //HignDlPs tem de ter 8 bytes
 //private static final String ALGORITMO = "DES/ECB/PKCS5Padding";// ; DES/ECB/PKCS5Padding
 //private final String TEXTO;// = new String("");
  
    public FileEncrypDecryp(String algo,String path, String password) {
     this.algo = algo; //setting algo
     this.path = path;//setting file path   
     this.password = password;//seting password
     
    }
     
    public void encrypt(String fileCyph) throws Exception{
         //generating key
         byte k[] = password.getBytes(); 
         D.deb("PASSWORD: "+password);
         SecretKeySpec key = new SecretKeySpec(k,algo.split("/")[0]);  
         //creating and initialising cipher and cipher streams
         Cipher encrypt =  Cipher.getInstance(algo);  
         encrypt.init(Cipher.ENCRYPT_MODE, key);
         //opening streams
         FileOutputStream fos =new FileOutputStream(fileCyph);//+".enc"
         try(FileInputStream fis =new FileInputStream(path)){
            try(CipherOutputStream cout=new CipherOutputStream(fos, encrypt)){// fos
                copy(fis,cout);
            }
         }
     }
      
     public void decrypt(String file) throws Exception{
         //generating same key
         byte k[] = password.getBytes();   
         SecretKeySpec key = new SecretKeySpec(k,algo.split("/")[0]);  
         //creating and initialising cipher and cipher streams
         Cipher decrypt =  Cipher.getInstance(algo);  
         decrypt.init(Cipher.DECRYPT_MODE, key);
         //opening streams
         FileInputStream fis = new FileInputStream(path);
         try(CipherInputStream cin=new CipherInputStream(fis, decrypt)){  
            try(FileOutputStream fos =new FileOutputStream(file)){ //path.substring(0,path.lastIndexOf("."))
               copy(cin,fos);
           }
         }
      }
      
  private void copy(InputStream is,OutputStream os) throws Exception{
     byte buf[] = new byte[4096];  //4K buffer set
     int read;// = 0;
     while((read = is.read(buf)) != -1)  //reading
        os.write(buf,0,read);  //writing
  }
/*   
     public static void main (String[] args)throws Exception {
      new FileEncrypDecryp(ALGORITMO,"sample.txt").encrypt();
      JOptionPane.showMessageDialog(null, "Ficheiro Encriptado!");
      new FileEncrypDecryp(ALGORITMO,"sample.txt.enc").decrypt();
      JOptionPane.showMessageDialog(null, "Ficheiro Desencriptado!");
      System.out.println("Done...");
  }
  */
}

