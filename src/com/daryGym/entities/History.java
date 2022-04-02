/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.daryGym.entities;

/**
 *
 * @author ASUS
 */
public class History {
   private int id;
    private String description;
    private String image;
    private String nomarchive;
    private int idarchive;
     
    
    public History() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNomarchive() {
        return nomarchive;
    }

    public void setNomarchive(String nomarchive) {
        this.nomarchive = nomarchive;
    }

    public int getIdarchive() {
        return idarchive;
    }

    public void setIdarchive(int idarchive) {
        this.idarchive = idarchive;
    }

  
   
}
