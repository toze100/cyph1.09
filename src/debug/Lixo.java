/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package debug;
/*
import controler.ApplicationData;
import static controler.ApplicationData.passEncryp;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.NoSuchPaddingException;

*/

/**
 *
 * @author tony
 */
public class Lixo {
    
    /*   
            byte[] title_chbt=null;
            try {
                title_chbt = passEncryp (p.getTitle());
            } catch (NoSuchPaddingException | NoSuchAlgorithmException ex) {
                Logger.getLogger(ApplicationData.class.getName()).log(Level.SEVERE, null, ex);
            }
            String title_chStr=null;            
            try {
                title_chStr = new String (title_chbt,"ISO-8859-1");
                p.setTitle(title_chStr);
                D.deb(title_chStr);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(ApplicationData.class.getName()).log(Level.SEVERE, null, ex);
            }
    */
}

/* ***************************************************************************
O codigo que se segue, gera ficheiro chave e encrita texto em ficheiro,
recuperando-o de seguida
*/
/*
package fileoutputcipherstream;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 *
 * @author tony
 */
/*


public class FileOutputCipherStream {
  public static void main(String args[]) throws Exception {
    KeyGenerator kg = KeyGenerator.getInstance("DES");
    kg.init(new SecureRandom());
    SecretKey key = kg.generateKey();
    SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
    Class spec = Class.forName("javax.crypto.spec.DESKeySpec");
    DESKeySpec ks = (DESKeySpec) skf.getKeySpec(key, spec);
    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("keyfile"));
    oos.writeObject(ks.getKey());

    Cipher c = Cipher.getInstance("DES/CFB8/NoPadding");
    c.init(Cipher.ENCRYPT_MODE, key);
    CipherOutputStream cos = new CipherOutputStream(new FileOutputStream("ciphertext"), c);
    PrintWriter pw = new PrintWriter(new OutputStreamWriter(cos));
    pw.println("Stand and unfold yourself");
    pw.close();
    oos.writeObject(c.getIV());
    oos.close();
    //
    
    ObjectInputStream ois = new ObjectInputStream(new FileInputStream("keyfile"));
    DESKeySpec ks_de = new DESKeySpec((byte[]) ois.readObject());
    SecretKeyFactory skf_de = SecretKeyFactory.getInstance("DES");
    SecretKey key_de = skf_de.generateSecret(ks_de);

    //Cipher c = Cipher.getInstance("DES/CFB8/NoPadding");
    c.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec((byte[]) ois.readObject()));
    CipherInputStream cis = new CipherInputStream(new FileInputStream("ciphertext"), c);
    BufferedReader br = new BufferedReader(new InputStreamReader(cis));
    System.out.println(br.readLine());
  }
}
* ***************************************************************************
*/ 

// Progress bar DEMO
/*
package swingprogressbarexample;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

/**
 *
 * @author tony
 */


    
/*
Java Swing, 2nd Edition
By Marc Loy, Robert Eckstein, Dave Wood, James Elliott, Brian Cole
ISBN: 0-596-00408-7
Publisher: O'Reilly 
*/
// SwingProgressBarExample.java
// A demonstration of the JProgressBar component. The component tracks the
// progress of a for loop.
//

/*

public class SwingProgressBarExample extends JPanel {

  JProgressBar pbar;

  static final int MY_MINIMUM = 0;

  static final int MY_MAXIMUM = 100;

  public SwingProgressBarExample() {
    pbar = new JProgressBar();
    pbar.setMinimum(MY_MINIMUM);
    pbar.setMaximum(MY_MAXIMUM);
    add(pbar);
  }

  public void updateBar(int newValue) {
    pbar.setValue(newValue);
  }

  public static void main(String args[]) {

    final SwingProgressBarExample it = new SwingProgressBarExample();

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setContentPane(it);
    frame.pack();
    frame.setVisible(true);

    for (int i = MY_MINIMUM; i <= MY_MAXIMUM; i++) {
      final int percent = i;

        it.updateBar(percent);
        
        try {
            java.lang.Thread.sleep(10);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(SwingProgressBarExample.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      
    }
    frame.dispose();
  }
}

// main original com 
/*
  public static void main(String args[]) {

    final SwingProgressBarExample it = new SwingProgressBarExample();

    JFrame frame = new JFrame("Progress Bar Example");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(it);
    frame.pack();
    frame.setVisible(true);

    for (int i = MY_MINIMUM; i <= MY_MAXIMUM; i++) {
      final int percent = i;
      try {
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            it.updateBar(percent);
          }
        });
        java.lang.Thread.sleep(100);
      } catch (InterruptedException e) {
        ;
      }
    }
  }
*/