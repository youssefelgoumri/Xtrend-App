package com.elgoumri.xtrend.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
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
                Utils.PRODUCT_IMAGE + " INTEGER , " + Utils.PRODUCT_LIBELLE + " TEXT , " +
                Utils.PRODUCT_PRIX + " INTEGER , " + Utils.PRODUCT_DESC + " TEXT , " +
                Utils.PK_PERSON_ID + " INTEGER, " +
                "FOREIGN KEY ("+ Utils.PK_PERSON_ID +") REFERENCES "+ Utils.TABLE_PERSON +"("+ Utils.PERSON_ID +") )";

        db.execSQL(person_query);

        db.execSQL(product_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_PERSON);
        db.execSQL("DROP TABLE IF EXISTS " + Utils.TABLE_PRODUCT);
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
        if (cursor.moveToFirst()) id = cursor.getInt(0);
        cursor.close();
        database.close();
        return id;
    }


    public void addProduct(Product product) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        SharedPreferences preferences = context.getSharedPreferences("secret",0);
        int id_user = preferences.getInt("id_user",-1);

        contentValues.put(Utils.PRODUCT_IMAGE, getMipmapResIdByName(product.getFlagName()));
        contentValues.put(Utils.PRODUCT_LIBELLE, product.getLibelle());
        contentValues.put(Utils.PRODUCT_PRIX, product.getPrix());
        contentValues.put(Utils.PRODUCT_DESC, product.getDesc());
        contentValues.put(Utils.PK_PERSON_ID, id_user);

        database.insert(Utils.TABLE_PRODUCT,null,contentValues);

        database.close();

    }
    //image
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }


    //getProduct to display it
    public List<Product> getProduct(){
        SQLiteDatabase database = this.getReadableDatabase();
        List<Product> productList = new ArrayList<>();

        SharedPreferences preferences = context.getSharedPreferences("secret",0);
        int id_user = preferences.getInt("id_user",-1);

        String getAll = "SELECT * FROM "+ Utils.TABLE_PRODUCT +
                " where " + Utils.PK_PERSON_ID +" = " + id_user ;

        Cursor cursor = database.rawQuery(getAll,null);

        if(cursor.moveToFirst()){
            do {
                Product product = new Product();
                product.setId_produit(Integer.parseInt(cursor.getString(0)));
                product.setImage(cursor.getString(1));
                product.setLibelle(cursor.getString(2));
                product.setPrix(Integer.parseInt(cursor.getString(3)));
                product.setDesc(cursor.getString(4));

                productList.add(product);
            }while (cursor.moveToNext());

        }
        return productList;
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


}
