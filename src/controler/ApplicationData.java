/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import debug.D;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.nio.file.Files;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import model.Password;
import model.Password_Cyph;
import vista.PanelTextView;


//import java.lang.String;

/**
 *
 * @author tony
 * @version 1.02
 * 
 */
public class ApplicationData implements Serializable/*implements Cloneable*/{
    
    public static final int ERROR_10 = 10;// ERROR DATA_FILE_ENCRYPTED not exist
    public static final int ERROR_11 = 11;// ERROR PASSWORD_FILE not exist
    public static final int ERROR_12 = 12;// ERROR PASSWORD user and conf don't match
    public static final int ERROR_13 = 13;// ERROR with Hash password
    public static final int ERROR_14 = 14;// ERROR saving hashed password to disk
    public static final int ERROR_15 = 15;// ERROR Unknown
    public static final int ERROR_16 = 16;// ERROR Hash key ou algoritm error, PasswordHash
    public static final int ERROR_17 = 17;// ERROR reading data from DATA_FILE_ENCRYPTED
    public static final int ERROR_18 = 18;// ERROR in restorePassword()
    public static final int ERROR_19 = 19;// ERROR in passEncryp()
    public static final int ERROR_20 = 20;// ERROR wrong password. password don't match
    public static final int ERROR_21 = 21;// ERROR in passDecryp()
    public static final int ERROR_22 = 22;// ERROR in savePassData()
    public static final int ERROR_23 = 23;// ERROR in savePassData() file encryption
    public static final int ERROR_24 = 24;// ERROR in savePassData() temp file cleaning: DATA_FILE_NAME
    private static final int ERROR_25 = 25;//ERROR in make backup file savePassData()
    public static final int ERROR_26 = 26;// ERROR reading hashed password from disk
    public static final int ERROR_27 = 27;// ERROR FILE_RUN exists, is application running?
    public static final int ERROR_28 = 28;// ERROR Unable to delete FILE_RUN
    public static final int ERROR_29 = 29;// ERROR Unable to create FILE_RUN
    public static final int ERROR_30 = 30;// ERROR Unable to GET port number from file
    public static final int ERROR_31 = 31;// ERROR Unable to SET port number to file
    
    // used in this class, to encryp Strings in the file
    private static final String CHAR_SET_ENCODING = "ISO-8859-1";    
    private static final int KEY_SIZE = 16;// DON'T CHANGE THIS. IT WILL BREAK CODE!
    private static final String CIPHER = "AES";
    private static final String DATA_FILE_NAME = "file2.temp";
    //private static final String KEY = "teste";//Estg0EIPL#123$AS    
    
    //used on FileEncrypDecryp to encryp the final file
    private static final String ALGORITMO = "DES/ECB/PKCS5Padding";// ; DES/ECB/PKCS5Padding
    protected static final String DATA_FILE_ENCRYPTED = "file1.cyph";
    //(System.getProperty("java.class.path")+File.separator+"file1.cyph");
    public static final String PASSWORD_FILE = "file1.pwd";
    private static final String DATA_FILE_BCK = "file1_.bck";//"file1_.bck";
    private static final String FILE_RUN = "file1.run";
    private static final String CYPH_DADOS_TXT = "cyphDados.txt";
    public static String HASHED_SAVED_PASSWORD;// used to encrypt and decrypt file
    // path for data files
    public static String dataFileEncrypted;
    public static String dataFileName;
    public static String dataFileBck;
    public static String passwordFile;
    protected static String fileRun;
    public static String cyphDadosTxt;
    
    public static final int defaultPort = 65111;
    public static int port;
    public static ServerSocket socket;
    
    
    private static ArrayList<Password> passwords;
    private static ArrayList<Password_Cyph> passwords_Cy;
    private static final ApplicationData INSTANCE = new ApplicationData();
    
    public static String KEY;// get from AccessPassword, from file PASSWORD_FILE
    //public static String OLD_KEY;
       
     // constructor
    private ApplicationData() {
        dataFileEncrypted = (System.getProperty("user.dir")+File.separator+DATA_FILE_ENCRYPTED);//("java.class.path")
        dataFileName = (System.getProperty("user.dir")+File.separator+DATA_FILE_NAME);
        dataFileBck = (System.getProperty("user.dir")+File.separator+DATA_FILE_BCK);
        passwordFile = (System.getProperty("user.dir")+File.separator+PASSWORD_FILE);
        fileRun = (System.getProperty("user.dir")+File.separator+FILE_RUN);
        cyphDadosTxt = (System.getProperty("user.dir")+File.separator+CYPH_DADOS_TXT);
        
        D.deb("New ApplicationData();");
        passwords = new ArrayList<>();
        passwords_Cy = new ArrayList<>();
        
        D.deb("new Arraylist<Password> passwords");            
    } // constructor
    
    public static ApplicationData getAppData(){
        D.deb("getAppData();");
        return INSTANCE;
    }
    private static Password_Cyph createPassword_Cyph(Password p, String keyword){
        Password_Cyph p_Cy = new Password_Cyph();
        p_Cy.setTitle(passEncryp(p.getTitle(),keyword));
        p_Cy.setUser(passEncryp(p.getUser(),keyword));
        p_Cy.setPass(passEncryp(p.getPass(),keyword));
        p_Cy.setSite(passEncryp(p.getSite(),keyword));
        p_Cy.setNote(passEncryp(p.getNote(),keyword));
        
        return p_Cy;
    }
    
    private static Password restorePassword(Password_Cyph p_Cy, String keyword){
        Password p = new Password();
        try {
            p.setTitle(passDecryp(p_Cy.getTitle(),keyword));
            p.setUser(passDecryp(p_Cy.getUser(),keyword));
            p.setPass(passDecryp(p_Cy.getPass(),keyword));
            p.setSite(passDecryp(p_Cy.getSite(),keyword));
            p.setNote(passDecryp(p_Cy.getNote(),keyword));
          
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException ex) {
            exitWithError(ERROR_18);
            Logger.getLogger(ApplicationData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
    public static ArrayList<Password> getPasswords(){
        return (ArrayList <Password>) passwords.clone();
    }
    
    private static ArrayList<Password_Cyph> getPasswords_Cy(){
        return (ArrayList <Password_Cyph>) passwords_Cy.clone();
    }
    
    public void addPasswords(Password p){
        passwords.add(p);
        Collections.sort(passwords, passTitleComparator);
    }
    public void removePasswords (Password p){        
        passwords.remove(p);
    }
    
    public Password getPasswordIndex (int index){
        return passwords.get(index);
    }
    public void setEditPassword (int index, Password pass){
        passwords.set(index, pass);
        
        Collections.sort(passwords, passTitleComparator);
    }
    
    public int getNumberPassRecords(){
        return passwords.size();
    }
    // Comparator
    public static Comparator<Password> passTitleComparator = new Comparator<Password>(){
        @Override
        public int compare(Password pass1,Password pass2){
            String passTitle1 = pass1.getTitle().toUpperCase();
            String passTitle2 = pass2.getTitle().toUpperCase();
            return passTitle1.compareTo(passTitle2);
        }        
    };
    
    public static byte[] passEncryp(String text, String keyword) {
        // Este método encripta uma String e devolve um byte [].
        byte[] encrypted = null;
        try {  
            String key = getStringAtFixedLength(keyword,KEY_SIZE,6); // 128 bit key NEEDED
            D.deb("passEncrypt() \""+key+"\"");
            Key aesKey = new SecretKeySpec(key.getBytes(CHAR_SET_ENCODING), CIPHER);
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            encrypted = cipher.doFinal(text.getBytes(CHAR_SET_ENCODING));                      
        } catch (UnsupportedEncodingException | IllegalBlockSizeException |
                BadPaddingException | NoSuchAlgorithmException | 
                NoSuchPaddingException | InvalidKeyException ex) {
            exitWithError(ERROR_19);
            //Logger.getLogger(ApplicationData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return encrypted;
    }
        
    public static String passDecryp(byte[] textCyph, String keyword) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException{
        // Este método desencripta um byte [] e devolve uma String.
        String text = null;
        
        try {  
            String key = getStringAtFixedLength(keyword,KEY_SIZE,6); // 128 bit key NEEDED
            D.deb("passDecrypt() \""+key+"\"");
            Key aesKey = new SecretKeySpec(key.getBytes(CHAR_SET_ENCODING), CIPHER);
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            text = new String (cipher.doFinal(textCyph),CHAR_SET_ENCODING);
            
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            exitWithError(ERROR_21);
            //Logger.getLogger(ApplicationData.class.getName()).log(Level.SEVERE, null, ex);
        }                    
        return text;
    }
    
    private static String getStringAtFixedLength(String str, int l, int from) {        
        if(l <= str.length()){
            D.deb("str devolvida: (l,from): ("+l+","+from+")\""+str.substring(from, l+from)+"\"");
            return str.substring(from, l+from);//get the 16 char, after position 5, depends of public static final int PBKDF2_ITERATIONS = 10000; in PasswordHash
        }else{
            int limite = str.length();
            for (int i=0; i<(l-limite); i++) str += "#"; //D.deb("i="+i+"l-str");
            return str;
        }
    }
    
        //Métodos para guardar e ler dados do disco
    public void savePassData(){
        // Alterar as strings para byte[] e cifralas        
        //Encriptar as Strings
        String keyword = AccessPassword.getPasswordFromFile();
        passwords_Cy.clear();
        for(Password p: getPasswords()){
            Password_Cyph p_Cy = createPassword_Cyph(p,keyword);
            passwords_Cy.add(p_Cy);            
        }        
        File f = new File (dataFileName);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try{            
            fos = new FileOutputStream(f);
            oos = new ObjectOutputStream (fos);
            oos.writeObject(passwords_Cy);
            oos.close();
            D.deb("Ficheiro temp criado: \""+dataFileName+"\"");
            //fos.close();                        
        }catch (IOException e) {
            exitWithError(ERROR_22);
            //JOptionPane.showMessageDialog(null, "IOException! - erro na escrita!");
            //e.printStackTrace();
        }
        finally{  
            passwords_Cy.clear();
            D.deb("Sempre é executado este codigo... ArrayList.clear()");
        }
        // cria novo ficheiro encriptado
        D.deb("keyord: \""+keyword);
        D.deb("HASH readed from disk:");
        D.deb(HASHED_SAVED_PASSWORD);
        try {
            new FileEncrypDecryp(ALGORITMO,dataFileName,
                                 getStringAtFixedLength(HASHED_SAVED_PASSWORD,8,6)).encrypt(dataFileEncrypted);
            D.deb("Ficheiro encriptado criado: \""+dataFileEncrypted+"\"");
        } catch (Exception ex) {
            exitWithError(ERROR_23);
            //JOptionPane.showMessageDialog(null, "Erro na encriptação do ficheiro");
            //Logger.getLogger(ApplicationData.class.getName()).log(Level.SEVERE, null, ex);
        }
        // apaga ficheiro não encriptado - temporario
        try{
            Files.delete(Paths.get(dataFileName));
            D.deb("Ficheiro temp apagado: \""+dataFileName+"\"");
        } catch (IOException ex) {
            exitWithError(ERROR_24);
            //JOptionPane.showMessageDialog(null, "Erro na limpeza de ficheiros temporários");
            //Logger.getLogger(ApplicationData.class.getName()).log(Level.SEVERE, null, ex);
        }                                
    }    
    
    public void readPassData (){        
        
        if (Files.exists(Paths.get(dataFileEncrypted), NOFOLLOW_LINKS)){
            try {
                new FileEncrypDecryp(ALGORITMO,dataFileEncrypted,
                                    getStringAtFixedLength(HASHED_SAVED_PASSWORD,8,6)).decrypt(dataFileName);
                D.deb("Ficheiro temp criado: \""+dataFileName+"\"");
            } catch (Exception ex) {
                Logger.getLogger(ApplicationData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Ficheiro dados Ecriptado ainda não existe!");
            D.deb("Ficheiro de dados não existente");
        }        
        File f = new File (dataFileName);D.deb("readPassData");
        if (f.canRead()){   // f.exists() ??
            try{
                FileInputStream fis = new FileInputStream (f);
                try (ObjectInputStream ois = new ObjectInputStream (fis)) {
                    passwords_Cy= (ArrayList<Password_Cyph>) ois.readObject();
                }
                D.deb("Lido file1.dat e criado ArrayList passwords_Cy");
                D.deb(passwords_Cy.size());
            
            }catch (IOException | ClassNotFoundException ex ) {
                exitWithError(ERROR_17);                            
            }
        }else{
            //JOptionPane.showMessageDialog(null, "Ficheiro temporario não lido");
            D.deb("Ficheiro temporario não lido");
        }          
        // apaga ficheiro não encriptado - Temporario
        if (Files.exists(Paths.get(dataFileName), NOFOLLOW_LINKS)){                    
            try{
                Files.delete(Paths.get(dataFileName));
                D.deb("Ficheiro temp apagado: \""+dataFileName+"\"");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erro na limpeza de ficheiros temporários");
                Logger.getLogger(ApplicationData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            //JOptionPane.showMessageDialog(null, "Ficheiro temporario não existe");
            D.deb("Ficheiro temporario não lido");
        }
        /*
        desencryptar as Strings
        */
        String keyword = AccessPassword.getPasswordFromFile();
        if (passwords_Cy.size()> 0)
            for(Password_Cyph p_Cy: getPasswords_Cy()){
                Password p = restorePassword(p_Cy,keyword);
                passwords.add(p);
            }      
            /*
            Fim de desencriptação das Strings
            */
    }
    
    public void makeDataBCKFile(){
        try{
            Files.copy(Paths.get(dataFileEncrypted),Paths.get(dataFileBck),REPLACE_EXISTING);
        } catch (IOException ex) {
            exitWithError(ERROR_25);
            //Logger.getLogger(ApplicationData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        /*
        Fim de desencriptação das Strings
        */
    public static void exitWithError(int error){        
        JOptionPane.showMessageDialog(null, "Programa terminado: Erro "+error);    
        deleteFileRun();
        System.exit(error);
    }
    /*
    public static void makeFileRun(int p){
        try {
            FileOutputStream fos = new FileOutputStream (fileRun);  
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeInt(p);
            dos.close();
            D.deb("Porta escrita em fileRun: "+p);
        } catch (IOException ex) {
            exitWithError(ERROR_29);
            //Logger.getLogger(ApplicationData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    */
    public static void makeFileRunText(int p){
        try {
            FileWriter fw = new FileWriter (fileRun);  
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(Integer.toString(p));
            bw.close();
            D.deb("Porta escrita em fileRun: "+p);
        } catch (IOException ex) {
            exitWithError(ERROR_29);
            //Logger.getLogger(ApplicationData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void deleteFileRun(){
        if (Files.exists(Paths.get(fileRun), NOFOLLOW_LINKS)){ 
            try {
                Files.delete(Paths.get(fileRun));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Access error: "+Paths.get(fileRun), "ERRO 28", 2);
                System.exit(28);
                //Logger.getLogger(ApplicationData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "File not found: "+Paths.get(fileRun), "ERRO 28", 2);
        }
    }
    
    public static void updateLogFile(){
        
    }
    
    public static void createLogFile(){
        
    }
    public static void exportTxt(PanelTextView ptv){
        int percent;     
        int n=0;
        JTextArea ta = ptv.getTextArea();
        
        ta.append("Inicio de gravação de dados:\n");
        try {
            FileWriter fw = new FileWriter(ApplicationData.cyphDadosTxt);
            BufferedWriter bw = new BufferedWriter(fw) ;                
            percent = n*100/(passwords.size());
            for(Password p: passwords){                                        
                D.deb("Percentagem: "+percent);
                n++;
                percent = n*100/(passwords.size());                    
                bw.write("\r\n #Reg. "+n+"\r\n");
                ta.append("#Reg."+n+"\n");            
                bw.write(p.getTitle());bw.write("\r\n");
                bw.write(p.getUser());bw.write("\r\n");
                bw.write(p.getPass());bw.write("\r\n");
                bw.write(p.getSite());bw.write("\r\n");
                bw.write(p.getNote());bw.write("\r\n");                    
            }
            bw.close();            
            D.deb("Continuing...");                        
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, 
                    "Erro na criação de ficheiro cyphDados.txt"+Paths.get(ApplicationData.cyphDadosTxt),
                    "Erro!", 
                    JOptionPane.ERROR_MESSAGE);
        }
    ta.append("\nSUCESSO: "+n+" registos exportados.\n");
    ta.append("Ficheiro criado em: \n");
    ta.append(ApplicationData.cyphDadosTxt);
    }
}
