/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author ASUS
 */
public class Reponse {
   private int id;
    private String datedebut;
    private String description;
    private String datefin;
    private String email;
    private int reclamation_id;
     
    
    public Reponse() {
    }

    public Reponse(int id, String datedebut, String description, String datefin, String email, int reclamation_id) {
        this.id = id;
        this.datedebut = datedebut;
        this.description = description;
        this.datefin = datefin;
        this.email = email;
        this.reclamation_id = reclamation_id;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatedebut() {
        return datedebut;
    }

    public void setDatedebut(String datedebut) {
        this.datedebut = datedebut;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatefin() {
        return datefin;
    }

    public void setDatefin(String datefin) {
        this.datefin = datefin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getReclamation_id() {
        return reclamation_id;
    }

    public void setReclamation_id(int reclamation_id) {
        this.reclamation_id = reclamation_id;
    }
    
    
    

   

   
}
