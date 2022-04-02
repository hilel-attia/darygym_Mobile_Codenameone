package com.daryGym.entities;

public class User {

    private int id;
    private String username;
    private String nomComplet;
    private String email;
    private boolean valid;
    private boolean deleted;
    private String password;
    private boolean admin;

    public User(String username, String nomComplet, String email, boolean valid, boolean deleted, String password, boolean admin) {
        this.username = username;
        this.nomComplet = nomComplet;
        this.email = email;
        this.valid = valid;
        this.deleted = deleted;
        this.password = password;
        this.admin = admin;
    }

    public User(int id, String username, String nomComplet, String email, boolean valid, boolean deleted, String password, boolean admin) {
        this.id = id;
        this.username = username;
        this.nomComplet = nomComplet;
        this.email = email;
        this.valid = valid;
        this.deleted = deleted;
        this.password = password;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return email;
    }
}