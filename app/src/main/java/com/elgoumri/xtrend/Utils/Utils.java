package com.elgoumri.xtrend.Utils;

public class Utils {
    //version of database
    public static final int DATABASE_VERSION = 1;
    //database name
    public static final String DATABASE_NAME = "Xtrend.db";
        //table person name
    public static final String TABLE_PERSON = "Person";
        //table product name
    public static final String TABLE_PRODUCT = "Product";


    //colums of table person
    public static final String PERSON_ID = "id";
    public static final String PERSON_NOM = "nom";
    public static final String PERSON_PRENOM = "prenom";
    public static final String PERSON_USERNAME = "username";
    public static final String PERSON_ADRESSE = "adresse";
    public static final String PERSON_NUM = "num";
    public static final String PERSON_PASSWORD = "password";

    //colums of table product
    public static final String PRODUCT_ID = "id_produit";
    public static final String PRODUCT_IMAGE = "image_produit";
    public static final String PRODUCT_LIBELLE = "libelle_produit";
    public static final String PRODUCT_PRIX = "prix_produit";
    public static final String PRODUCT_DESC = "desc_produit";
    public static final String PK_PERSON_ID = "id_person";


}
