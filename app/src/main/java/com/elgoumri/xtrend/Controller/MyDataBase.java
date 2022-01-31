package com.elgoumri.xtrend.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;


import com.elgoumri.xtrend.Model.Person;
import com.elgoumri.xtrend.Model.Product;
import com.elgoumri.xtrend.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MyDataBase extends SQLiteOpenHelper {

    Context context;

    public MyDataBase(Context context) {
        super(context, Utils.DATABASE_NAME, null, Utils.DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String person_query = "CREATE TABLE IF NOT EXISTS " + Utils.TABLE_PERSON +
                " ( " + Utils.PERSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                Utils.PERSON_NOM + " TEXT , " + Utils.PERSON_PRENOM + " TEXT , " +
                Utils.PERSON_USERNAME + " TEXT, " + Utils.PERSON_ADRESSE + " TEXT, "+
                Utils.PERSON_NUM + " TEXT, " + Utils.PERSON_PASSWORD + " TEXT)";

        String product_query = "CREATE TABLE IF NOT EXISTS " + Utils.TABLE_PRODUCT +
                " ( " + Utils.PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Utils.PRODUCT_IMAGE + " BLOB , " + Utils.PRODUCT_LIBELLE + " TEXT , " +
                Utils.PRODUCT_PRIX + " INTEGER , " + Utils.PRODUCT_DESC + " TEXT , " +
                Utils.PRODUCT_CATG + " TEXT)";

        String cart_query = "CREATE TABLE IF NOT EXISTS " + Utils.TABLE_CART +
                " ( " + Utils.CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                Utils.PK_PERSON_ID + " INTEGER , " +
                Utils.PK_PRODUCT_ID + " INTEGER , " +
                "FOREIGN KEY ("+ Utils.PK_PERSON_ID +") REFERENCES "+ Utils.TABLE_PERSON +"("+ Utils.PERSON_ID +"), " +
                "FOREIGN KEY ("+ Utils.PK_PRODUCT_ID +") REFERENCES "+ Utils.TABLE_PRODUCT +"("+ Utils.PRODUCT_ID +"))";

        db.execSQL(person_query);
        db.execSQL(product_query);
        db.execSQL(cart_query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_PERSON);
        db.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_CART);
        onCreate(db);
    }


    public void addPerson(Person person){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Utils.PERSON_NOM, person.getNom());
        contentValues.put(Utils.PERSON_PRENOM, person.getPrenom());
        contentValues.put(Utils.PERSON_USERNAME, person.getUsername());
        contentValues.put(Utils.PERSON_ADRESSE, person.getAdresse());
        contentValues.put(Utils.PERSON_NUM, person.getNum());
        contentValues.put(Utils.PERSON_PASSWORD, person.getPassword());

        database.insert(Utils.TABLE_PERSON,null,contentValues);

        database.close();
    }

    //id of person, we need this in f.k of table Product
    public int getIdPerson(String username){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery( "select id from " + Utils.TABLE_PERSON +
                " where "+Utils.PERSON_USERNAME +" =? ",new String[] {username});
        int id = -1;
        if (cursor.moveToFirst())
            id = cursor.getInt(0);
        cursor.close();
        database.close();
        return id;
    }


    //get buyer person by id
    public Person getPersonById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Person person = new Person();

        String req = "select * from Person where id = " + id ;

        Cursor cursor = db.rawQuery(req,null);

        if(cursor.moveToFirst()){
            person.setId(Integer.parseInt(cursor.getString(0)));
            person.setNom(cursor.getString(1));
            person.setPrenom(cursor.getString(2));
            person.setUsername(cursor.getString(3));
            person.setAdresse(cursor.getString(4));
            person.setPhone(cursor.getString(5));
            person.setPassword(cursor.getString(6));
        }
        return person;

    }




    //add product to database
    public void addProduct(Product product) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

            contentValues.put(Utils.PRODUCT_IMAGE, product.getImage());
            contentValues.put(Utils.PRODUCT_LIBELLE, product.getLibelle());
            contentValues.put(Utils.PRODUCT_PRIX, product.getPrix());
            contentValues.put(Utils.PRODUCT_DESC, product.getDesc());
            contentValues.put(Utils.PRODUCT_CATG, product.getCategorie());

            database.insert(Utils.TABLE_PRODUCT,null,contentValues);

            database.close();

    }


    //get Products from datasase
    public ArrayList<Product> getProducts(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Product> products = new ArrayList<>();

        String select_query = "select * from " + Utils.TABLE_PRODUCT ;
        Cursor cursor = db.rawQuery(select_query, null);

        if (cursor.moveToFirst()) {

            do {

                Product product = new Product();
                product.setId_produit(Integer.parseInt(cursor.getString(0)));
                product.setImage(cursor.getBlob(1));
                product.setLibelle(cursor.getString(2));
                product.setPrix(Integer.parseInt(cursor.getString(3)));
                product.setDesc(cursor.getString(4));

                products.add(product);

            } while (cursor.moveToNext());

        }

        return products;

    }

    //the same getProducts for each category
    public ArrayList<Product> getProductByCategoryName(String catg_name){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Product> products = new ArrayList<>();

        String select_query = "select * from " + Utils.TABLE_PRODUCT + " where " +
                Utils.PRODUCT_CATG + " = '"+catg_name +"'";

        Cursor cursor = db.rawQuery(select_query, null);

        if (cursor.moveToFirst()) {

            do {

                Product product = new Product();
                product.setId_produit(Integer.parseInt(cursor.getString(0)));
                product.setImage(cursor.getBlob(1));
                product.setLibelle(cursor.getString(2));
                product.setPrix(Integer.parseInt(cursor.getString(3)));
                product.setDesc(cursor.getString(4));

                products.add(product);

            } while (cursor.moveToNext());

        }

        return products;

    }

    public Product getProductById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String select_prod = "select * from " + Utils.TABLE_PRODUCT + " where "+Utils.PRODUCT_ID+" = " + id ;
        Cursor cursor = db.rawQuery(select_prod, null);

        Product product = new Product();

        if(cursor.moveToFirst()){
            product.setId_produit(Integer.parseInt(cursor.getString(0)));
            product.setImage(cursor.getBlob(1));
            product.setLibelle(cursor.getString(2));
            product.setPrix(Integer.parseInt(cursor.getString(3)));
            product.setDesc(cursor.getString(4));
            product.setCategorie(cursor.getString(5));
        }
        return product;

    }


    //addProduct to cart
    public boolean addToCart(Product product) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        SharedPreferences preferences = context.getSharedPreferences("secret",0);
        int id_user = preferences.getInt("id_user",-1);

        //check if product already exist
        String libelle_exist = "select * from " + Utils.TABLE_CART +
                " where " + Utils.PK_PRODUCT_ID +" = "+product.getId_produit()+" AND " +
                Utils.PK_PERSON_ID + " = " + id_user;

        Cursor cursor = database.rawQuery(libelle_exist,null);

        boolean exists = (cursor.getCount() > 0);
        if(exists){
            Log.d("myTag01", "already exist");
            return false;
        }else{
            Log.d("myTag00", "added");

            /*
            contentValues.put(Utils.PRODUCT_IMAGE, product.getImage());
            contentValues.put(Utils.PRODUCT_LIBELLE, product.getLibelle());
            contentValues.put(Utils.PRODUCT_PRIX, product.getPrix());
            contentValues.put(Utils.PRODUCT_DESC, product.getDesc());
            contentValues.put(Utils.PK_PERSON_ID, id_user);

            database.insert(Utils.TABLE_PRODUCT,null,contentValues);
             */


            contentValues.put(Utils.PK_PERSON_ID,id_user);
            contentValues.put(Utils.PK_PRODUCT_ID,product.getId_produit());
            database.insert(Utils.TABLE_CART,null,contentValues);


            database.close();

            return true;
        }
    }



    //getProduct of cart
    public ArrayList<Product> getCart(int id_user){
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Product> productList = new ArrayList<>();


        String getAll = "SELECT Product.id_produit,image_produit,libelle_produit,prix_produit,desc_produit FROM "+
                Utils.TABLE_PRODUCT + ", " + Utils.TABLE_CART+
                " where " +Utils.TABLE_CART+"."+ Utils.PK_PERSON_ID +" = " + id_user +
                " AND " +Utils.TABLE_CART+"."+Utils.PK_PRODUCT_ID + " = " +Utils.TABLE_PRODUCT+"."+ Utils.PRODUCT_ID;


        Cursor cursor = database.rawQuery(getAll,null);

        if(cursor.moveToFirst()){
            do {
                Product product = new Product();
                product.setId_produit(Integer.parseInt(cursor.getString(0)));
                product.setImage(cursor.getBlob(1));
                product.setLibelle(cursor.getString(2));
                product.setPrix(Integer.parseInt(cursor.getString(3)));
                product.setDesc(cursor.getString(4));

                productList.add(product);
            }while (cursor.moveToNext());

        }
        return productList;
    }

    public int getTotalCartById(int id_user){
        SQLiteDatabase db = this.getReadableDatabase();
        int total = 0;
        String req = "select SUM("+Utils.PRODUCT_PRIX+") from "+ Utils.TABLE_PRODUCT + " , " + Utils.TABLE_CART +
                " where " + Utils.PK_PERSON_ID + " = "+ id_user +
                " AND " +Utils.TABLE_CART+"."+Utils.PK_PRODUCT_ID + " = " +Utils.TABLE_PRODUCT+"."+ Utils.PRODUCT_ID;

        Cursor cursor = db.rawQuery(req,null);
        if(cursor.moveToFirst()){
            total = Integer.parseInt(cursor.getString(0));
        }
        return total;
    }


    public String getTotalSells(){
        SQLiteDatabase db = this.getReadableDatabase();
        String req = "select COUNT(Product.id_produit) from "+ Utils.TABLE_PRODUCT + " , " + Utils.TABLE_CART +
                " where " +Utils.TABLE_CART+"."+Utils.PK_PRODUCT_ID + " = " +Utils.TABLE_PRODUCT+"."+ Utils.PRODUCT_ID;
        String total="0";
        Cursor cursor = db.rawQuery(req,null);
        if(cursor.moveToFirst()){
            total = cursor.getString(0);
        }
        return total;
    }

    public Product getWinProd(){
        SQLiteDatabase db = this.getReadableDatabase();
        String req = "select * from Product " +
                "where id_produit IN (select Product.id_produit from Product,Cart " +
                "group by Product.id_produit having Cart.id_produit=Product.id_produit)";
        Product product = new Product();
        Cursor cursor = db.rawQuery(req,null);
        if(cursor.moveToFirst()){
            product.setId_produit(Integer.parseInt(cursor.getString(0)));
            product.setImage(cursor.getBlob(1));
            product.setLibelle(cursor.getString(2));
            product.setPrix(Integer.parseInt(cursor.getString(3)));
            product.setDesc(cursor.getString(4));
        }
        return product;
    }



    //all category name
    public ArrayList<String> getAllCategory(){
        SQLiteDatabase database = this.getReadableDatabase();

        ArrayList<String> catList = new ArrayList<>();
        String getAllCat = "select distinct " + Utils.PRODUCT_CATG + " from " + Utils.TABLE_PRODUCT ;

        Cursor cursor = database.rawQuery(getAllCat,null);

        if(cursor.moveToFirst()){
            do {
                catList.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        return catList;
    }

    public void updateData(Product product){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Utils.PRODUCT_IMAGE,product.getImage());
        cv.put(Utils.PRODUCT_LIBELLE,product.getLibelle());
        cv.put(Utils.PRODUCT_PRIX,product.getPrix());
        cv.put(Utils.PRODUCT_DESC,product.getDesc());
        cv.put(Utils.PRODUCT_CATG,product.getCategorie());

        db.update(Utils.TABLE_PRODUCT, cv, "id_produit=?", new String[]{String.valueOf(product.getId_produit())});

    }

    public void deletProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Utils.TABLE_PRODUCT, "id_produit=?", new String[]{String.valueOf(id)});
        db.delete(Utils.TABLE_CART, "id_produit=?", new String[]{String.valueOf(id)});

    }

    public void deleteCart(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Utils.TABLE_CART, "id_person=?", new String[]{String.valueOf(id)});
    }



    //check username login
    public Boolean checkUsername(String username){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from " + Utils.TABLE_PERSON + " where " +
                Utils.PERSON_USERNAME + " =? " , new String[] {username});

        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }


    public Boolean checkUsernamePassword(String username,String password){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from " + Utils.TABLE_PERSON + " where " +
                Utils.PERSON_USERNAME + " =? and "+ Utils.PERSON_PASSWORD +" =?" , new String[] {username,password});

        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public ArrayList<String> getBuyersUsername(){
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<String> buyers = new ArrayList<>();

        String req = "select distinct username from Cart,Person where Person.id = Cart.id_person";

        Cursor cursor = database.rawQuery(req,null);

        if(cursor.moveToFirst()){
            do {
                buyers.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        return buyers;

    }




    //image
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }

    public Person getPerson(int id){
        SQLiteDatabase database = this.getReadableDatabase();
        String var = "SELECT * FROM " +Utils.TABLE_PERSON + " where "+Utils.PERSON_ID+"="+id;
        Cursor cursor = database.rawQuery(var,null);
        if(cursor != null)
            cursor.moveToFirst();
        Person person = new Person(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6));
        return  person;
    }




    public boolean update(String s1,String s2, String s3 ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Utils.PERSON_USERNAME,s1);
        contentValues.put(Utils.PERSON_NUM,s2);
        contentValues.put(Utils.PERSON_ADRESSE,s3);
        db.update(Utils.TABLE_PERSON, contentValues, Utils.PERSON_USERNAME, new String[]{s1});
        return true;
    }

}