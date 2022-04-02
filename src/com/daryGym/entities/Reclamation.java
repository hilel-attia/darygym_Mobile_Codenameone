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
public class Reclamation {
   private int id;
    private String titre;
    private String description;
    private String created_At;
    private String prenom;
    private String nom;
    private String type;
    private int statut;
     
    
    public Reclamation() {
    }

    public Reclamation(int id, String titre, String description, String created_At, String prenom, String nom, String type, int statut) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.created_At = created_At;
        this.prenom = prenom;
        this.nom = nom;
        this.type = type;
        this.statut = statut;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_At() {
        return created_At;
    }

    public void setCreated_At(String created_At) {
        this.created_At = created_At;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }
    
    

    

   

   
}
