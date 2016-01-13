/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author tony
 */
public class Password implements Serializable{
    private String title;
    private String user;
    private String pass;
    private String site;
    private String note;
    
    public Password (/*String title,
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
    @Override
    public String toString(){
        return this.title;
    }
    
    public String getTitle(){
        return title;
    }
    public String getUser(){
        return user;
    }
    public String getPass(){
        return pass;
    }
    public String getSite(){
        return site;
    }
    public String getNote(){
        return note;
    }
    public void setTitle(String title){
        this.title=title;    
    }
    public void setUser(String user){
        this.user=user;
    }
    public void setPass(String pass){
        this.pass=pass;
    }
    public void setSite(String site){
        this.site=site;
    }
    public void setNote(String note){
        this.note=note;
    }
}
