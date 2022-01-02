package com.elgoumri.xtrend.Model;

public class Person {
    private static int id=0 ;
    private String nom ;
    private String prenom ;
    private String username ;
    private String adresse ;
    private String num;
    private String password;

    public Person(String nom, String prenom, String username, String adresse, String num, String password) {
        id++;
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.adresse = adresse;
        this.num = num;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNum() {
        return num;
    }

    public void setPhone(String num) {
        this.num = num;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
