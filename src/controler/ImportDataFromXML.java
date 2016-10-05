/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.List;
import model.Password;
import org.dom4j.Document;
import org.dom4j.DocumentException;

import org.dom4j.Element;
import org.dom4j.Node;

import org.dom4j.io.SAXReader;


/**
 *
 * @author tony
 */
public class ImportDataFromXML {
        public ImportDataFromXML() throws UnsupportedEncodingException, IOException, DocumentException {
        //ArrayList<Password> registos = new ArrayList<>();
        //Password reg;
        
        File fis = new File("cyphDados.xml");
        SAXReader reader = new SAXReader();
        Document document = reader.read(fis);
        
        Element classElement = document.getRootElement();// why??
        
        List<Node> nodes = document.selectNodes("/Registos/Registo"); 
        
        
        for (Node node:nodes){
            Password reg = new Password();
            
            reg.setTitle(node.selectSingleNode("titulo").getText());
            reg.setUser(node.selectSingleNode("user").getText());
            reg.setPass(node.selectSingleNode("pass").getText());
            reg.setSite(node.selectSingleNode("site").getText());
            reg.setNote(node.selectSingleNode("nota").getText());                       
            
            ApplicationData.getAppData().addPasswords(reg);
        }

    }
}
