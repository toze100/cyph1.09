/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

//import static controler.ApplicationData.DATA_FILE_ENCRYPTED;
//import static controler.ApplicationData.fileRun;
import static controler.PasswordHash.*;
import debug.D;
import java.io.BufferedReader;
import java.io.DataInputStream;
//import debug.D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
//import static java.lang.Boolean.FALSE;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
//import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;


/**
 *
 * @author tony
 * Based on:
 * http://www.javacodegeeks.com/2012/05/secure-password-storage-donts-dos-and.html
 */

public class AccessPassword {
    
    //private int result=0;    
    private static final int PASS_FIELD_SIZE = 20;
    public static String userPass;
    private static String hashUserConfPass;
    private static String hashPassSaved;
            
    public AccessPassword(){        
        //String userPass= askUserPass(false);// true if want to confirm password
        if (fileExists(ApplicationData.fileRun)){
            //tests for file1.run that marks app is running
            //file exists, EXIT, and warns user tha is allready running
            ApplicationData.port = getPortFromFileRunText();
            
            if (isRunning(ApplicationData.port)){
                JOptionPane.showMessageDialog(null, "Aviso: A aplicação já se encontra em execução!", "ATENÇÃO!", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
                //exitWithError(ApplicationData.ERROR_27);
                
            }else{
                JOptionPane.showMessageDialog(null, "Aviso: A aplicação recuperou de falha grave! Tente novamente!", "ATENÇÃO!", JOptionPane.WARNING_MESSAGE);
                ApplicationData.deleteFileRun();
                System.exit(0);
                //ApplicationData.makeFileRun(ApplicationData.defaultPort);
            }
            
        }else{
            
            if (isRunning(ApplicationData.defaultPort)){
                JOptionPane.showMessageDialog(null, "Aviso: A aplicação já se encontra em execução!", "ATENÇÃO!", JOptionPane.WARNING_MESSAGE);
                System.exit(0);                
            }
            //lockPort(defaultPort);
            //creates file1.run
            ApplicationData.makeFileRunText(ApplicationData.defaultPort);
            if (fileExists(ApplicationData.dataFileEncrypted)){
                if (fileExists(ApplicationData.passwordFile)){
                    hashPassSaved = getPasswordFromFile();
                    userPass = askUserPass("Password");
                    D.deb("Password from file: \""+hashPassSaved+"\"");
                    try {
                        // compara a password com a hash carregada                    
                        if (!validatePassword(userPass,hashPassSaved)){
                            //ApplicationData.deleteFileRun();                            
                            JOptionPane.showMessageDialog(null, "Password incorrecta!", "ATENÇÃO!", JOptionPane.WARNING_MESSAGE);
                            ApplicationData.deleteFileRun();
                            System.exit(0);                            
                            //ApplicationData.exitWithError(20);
                            //System.exit(0);
                        }                    
                    } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                        ApplicationData.exitWithError(ApplicationData.ERROR_16);
                        Logger.getLogger(AccessPassword.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ApplicationData.HASHED_SAVED_PASSWORD = hashPassSaved;
                    D.deb("Arranque normal: hash from file: \""+ApplicationData.HASHED_SAVED_PASSWORD);
                }else{
                    ApplicationData.exitWithError(ApplicationData.ERROR_11);                
                }

            }else{
                // se ficheiro de dados (encryp) não esxiste, então:
                // pede nova password (muda password) e grava
                changePassword();            
                JOptionPane.showMessageDialog(null, "Nova password definida!"); 
            } 
            ApplicationData.KEY = userPass;
        }
    }
    
    private int getPortFromFileRun(){
        int p=65110; //never used!
        try {
            FileInputStream fis = new FileInputStream (ApplicationData.fileRun);
            DataInputStream dis = new DataInputStream (fis);
            p = dis.readInt();
            dis.close();
        } catch (IOException ex) {
            ApplicationData.exitWithError(ApplicationData.ERROR_30);
            //Logger.getLogger(ApplicationData.class.getName()).log(Level.SEVERE, null, ex);
        }
        D.deb("Porta lida de fileRun: "+p);
        return p;
    }
    
    private int getPortFromFileRunText(){
        int p=65110; //never used!
        try {
            FileReader fr = new FileReader (ApplicationData.fileRun);
            BufferedReader br = new BufferedReader (fr);
            p = Integer.parseInt(br.readLine());
            br.close();
        } catch (IOException ex) {
            ApplicationData.exitWithError(ApplicationData.ERROR_30);
            //Logger.getLogger(ApplicationData.class.getName()).log(Level.SEVERE, null, ex);
        }
        D.deb("Porta lida de fileRun: "+p);
        return p;
    }
    private static boolean isRunning( int portNumber){
        boolean running;// = false;
        
	// static initializer
        try {
            D.deb("Tentativa de bloquear porta: "+portNumber);
            ApplicationData.socket = new ServerSocket(portNumber, 10, InetAddress.getLocalHost());
            running = false;
        } catch (UnknownHostException e) {
            D.deb("Porta não bloqueada - Unknown error");
                // shouldn't happen for localhost
            running = true;
        } catch (IOException e) {
                // port taken, so app is already running
                //System.exit(0);
            D.deb("Porta não bloqueada - IO error");
            running = true;
        }
	// main() and rest of application...
        D.deb("Return: "+running);
        return running;
    }
    /*
    private static void lockPort(int p){
        ServerSocket s;
	// static initializer
        try {
            s = new ServerSocket(p, 10, InetAddress.getLocalHost());
            D.deb("Porta bloqueada com sucesso: "+p);
        } catch (UnknownHostException e) {
                // shouldn't happen for localhost
            D.deb("Porta não bloqueada - Unknown!");
        } catch (IOException e) {
            D.deb("Porta não bloqueada - IO error");
                // port taken, so app is already running
        }
    }
    */
    public static void changePassword(){                                   
        String userConfPass;// = null;
        //String hashUserConfPass = null;
        boolean passVal = false;

        while(!passVal){
            try {
                userPass = askUserPass("Introduza Nova Password");
                userConfPass = askUserPass("Confirme a password");
                hashUserConfPass = createHash(userConfPass);
                passVal = validatePassword(userPass,hashUserConfPass);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                ApplicationData.exitWithError(ApplicationData.ERROR_13);
                //Logger.getLogger(AccessPassword.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /*
        if (fileExists(ApplicationData.passwordFile)){
            ApplicationData.OLD_KEY = AccessPassword.getPasswordFromFile();
        }
        */
        setPasswordToFile();
        ApplicationData.KEY = userPass;
        ApplicationData.HASHED_SAVED_PASSWORD = hashUserConfPass;
        D.deb("valor de userPass: "+userPass);
        D.deb("valor de hashUserConfPass: "+hashUserConfPass);
        D.deb("valor de userPass: "+userPass);
        D.deb("valor de hashUserConfPass: "+hashUserConfPass);
    }
    
    public static String getPasswordFromFile(){
        //String hashPassSaved = null;
        ObjectInputStream ois;// = null;
        try {                    
            ois = new ObjectInputStream (new FileInputStream
                                        (new File(ApplicationData.passwordFile)));
            hashPassSaved= (String)(ois.readObject());
            ois.close();
        } catch (IOException ex) {
        ApplicationData.exitWithError(ApplicationData.ERROR_26);
        } catch (ClassNotFoundException ex) {
            ApplicationData.exitWithError(ApplicationData.ERROR_15);
            //Logger.getLogger(AccessPassword.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hashPassSaved;
    }
    
    public static void setPasswordToFile(){
        ObjectOutputStream oos;// = null;
        try {
            oos = new ObjectOutputStream (new FileOutputStream
                                         (new File(ApplicationData.passwordFile)));
            oos.writeObject(hashUserConfPass);
            oos.close();
        } catch (IOException ex) {
            D.deb("Erro 14: Path do ficheiro: "+ApplicationData.passwordFile);
            ApplicationData.exitWithError(ApplicationData.ERROR_14);
            //Logger.getLogger(AccessPassword.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    /*
    private static void exitWithError(int error){
        JOptionPane.showMessageDialog(null, "Programa terminado: Erro "+error);
        System.exit(error);
    }
    */
    private static String askUserPass(String title){ 
        int result;
        JPasswordField pass = new JPasswordField(PASS_FIELD_SIZE);
        JPanel panel = new JPanel();
        panel.add(pass);
        String [] options = new String []{"OK"};
        
        result = JOptionPane.showOptionDialog(null, panel, title,
            JOptionPane.OK_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null, options, options);        
        return new String (pass.getPassword());        
    }
        
    private static boolean fileExists(String file){              
        return Files.exists(Paths.get(file));
    }
 
}
