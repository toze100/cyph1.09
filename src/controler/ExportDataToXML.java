/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import model.Password;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


/**
 *
 * @author tony
 */
public class ExportDataToXML {

    public ExportDataToXML() throws UnsupportedEncodingException, IOException {
        ArrayList<Password> data = ApplicationData.getPasswords();
        
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "Registos" );
        Element registo;
        int ord=0;
        
        for (Password reg:data){
            ord++;
            registo= root.addElement("Registo")
	        .addAttribute("num", ""+ord);
            
            registo.addElement("titulo").addText(reg.getTitle());
            registo.addElement("user").addText(reg.getUser());
            registo.addElement("pass").addText(reg.getPass());
            registo.addElement("site").addText(reg.getSite());
            registo.addElement("nota").addText(reg.getNote());            
            
        }

         // Pretty print the document to System.out
         OutputFormat format = OutputFormat.createPrettyPrint();
         XMLWriter writer;
         
         writer = new XMLWriter( System.out, format );
         writer.write( document );
         
         //write to file
         FileOutputStream fos = new FileOutputStream("cyphDados.xml");
         writer = new XMLWriter(fos, format);
         writer.write(document);
         //fos.close();

    }
    
    
}
