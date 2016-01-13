/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
Esta classe destina-se a guardar os dados na forma de byte[] em vez de String
*/
package model;

import java.io.Serializable;

/**
 *
 * @author tony
 */
public class Password_Cyph implements Serializable {
    private byte[] title;
    private byte[] user;
    private byte[] pass;
    private byte[] site;
    private byte[] note;
    
    public Password_Cyph (/*String title,
            String user,
            String pass,
            String site,
            String note*/){     /*
        this.title = title;
        this.user = user;
        this.pass = pass;
        this.site = site;
        this.note = note;      */  
    }
    /*
    @Override
    
    public String toString(){
        return this.title;
    }
    */
    public byte[] getTitle(){
        return title;
    }
    public byte[] getUser(){
        return user;
    }
    public byte[] getPass(){
        return pass;
    }
    public byte[] getSite(){
        return site;
    }
    public byte[] getNote(){
        return note;
    }
    public void setTitle(byte[] title){
        this.title=title;    
    }
    public void setUser(byte[] user){
        this.user=user;
    }
    public void setPass(byte[] pass){
        this.pass=pass;
    }
    public void setSite(byte[] site){
        this.site=site;
    }
    public void setNote(byte[] note){
        this.note=note;
    }
    
}
