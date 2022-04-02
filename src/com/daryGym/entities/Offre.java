package com.daryGym.entities;

public class Offre {

    private int id;
    private String titre;
    private String description;
    private float prix;
    private String image;

    public Offre(int id, String titre, String description, float prix, String image) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.prix = prix;
        this.image = image;
    }

    public Offre(String titre, String description, float prix, String image) {
        this.titre = titre;
        this.description = description;
        this.prix = prix;
        this.image = image;
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

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Offre{" + "id=" + id + ", titre=" + titre + ", description=" + description + ", prix=" + prix + ", image=" + image + '}';
    }
    
}
