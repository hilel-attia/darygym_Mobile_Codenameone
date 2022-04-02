package com.daryGym.entities;

import com.daryGym.gui.front.exercice.DisplayAll;

public class Exercice implements Comparable<Exercice> {

    private int id;
    private String image;
    private String nom;
    private String description;
    private String video;
    private String docs;
    private Exercicecategorie exercicecategorie;

    public Exercice(int id, String image, String nom, String description, String video, String docs, Exercicecategorie exercicecategorie) {
        this.id = id;
        this.image = image;
        this.nom = nom;
        this.description = description;
        this.video = video;
        this.docs = docs;
        this.exercicecategorie = exercicecategorie;
    }

    public Exercice(String image, String nom, String description, String video, String docs, Exercicecategorie exercicecategorie) {
        this.image = image;
        this.nom = nom;
        this.description = description;
        this.video = video;
        this.docs = docs;
        this.exercicecategorie = exercicecategorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getDocs() {
        return docs;
    }

    public void setDocs(String docs) {
        this.docs = docs;
    }

    public Exercicecategorie getExercicecategorie() {
        return exercicecategorie;
    }

    public void setExercicecategorie(Exercicecategorie exercicecategorie) {
        this.exercicecategorie = exercicecategorie;
    }

    @Override
    public int compareTo(Exercice exercice) {
        switch (DisplayAll.exerciceCompareVar) {
            case "nom":
                return this.nom.compareTo(exercice.nom);
            case "description":
                return this.description.compareTo(exercice.description);
            default:
                return 0;
        }
    }
}