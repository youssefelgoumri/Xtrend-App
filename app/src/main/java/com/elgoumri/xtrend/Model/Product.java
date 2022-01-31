package com.elgoumri.xtrend.Model;

public class Product {
    private int id_produit=0;
    private String libelle;
    private int prix;
    private byte[] image;
    private String desc;
    private String categorie;

    public Product(byte[] image,String libelle,int prix,String desc) {
        id_produit++;
        this.libelle = libelle;
        this.prix=prix;
        this.image=image;
        this.desc=desc;
    }

    public Product(byte[] image,String libelle,int prix,String desc,String categorie) {
        id_produit++;
        this.libelle = libelle;
        this.prix=prix;
        this.image=image;
        this.desc=desc;
        this.categorie=categorie;
    }
    public Product(int id,byte[] image,String libelle,int prix,String desc,String categorie) {
        id_produit=id;
        this.libelle = libelle;
        this.prix=prix;
        this.image=image;
        this.desc=desc;
        this.categorie=categorie;
    }

    public Product(){

    }

    public int getId_produit(){
        return id_produit;
    }

    public String getLibelle(){

        return libelle;
    }

    public int getPrix(){

        return prix;
    }
    public byte[] getImage() {

        return image;
    }
    /*
    public String getFlagName() {

        return image;
    }*/
    public String getDesc(){
        return desc;
    }


    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
