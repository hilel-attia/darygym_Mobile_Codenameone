package com.daryGym.entities;

public class Abonnement {

    private int id;
    private String duree;
    private Offre offre;

    public Abonnement(int id, String duree, Offre offre) {
        this.id = id;
        this.duree = duree;
        this.offre = offre;
    }

    public Abonnement(String duree, Offre offre) {
        this.duree = duree;
        this.offre = offre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }
}
