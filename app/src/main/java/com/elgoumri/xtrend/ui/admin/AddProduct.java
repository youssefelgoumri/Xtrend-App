package com.elgoumri.xtrend.ui.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.elgoumri.xtrend.Controller.MyDataBase;
import com.elgoumri.xtrend.Model.Product;
import com.elgoumri.xtrend.R;
import com.elgoumri.xtrend.login.Login;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class AddProduct extends AppCompatActivity {

    ImageButton addpic;
    EditText addlibelle;
    EditText addprix;
    EditText adddesc;
    EditText adddcatg;
    Button addProductBtn;

    byte[] image = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        addpic = findViewById(R.id.Addimage);
        addlibelle = findViewById(R.id.Addlibelle);
        addprix = findViewById(R.id.Addprix);
        adddesc = findViewById(R.id.Adddesc);
        adddcatg = findViewById(R.id.Addcatg);

        addProductBtn = findViewById(R.id.AddProductBtn);

        addpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentImg = new Intent(Intent.ACTION_GET_CONTENT);
                intentImg.setType("image/*");
                startActivityForResult(intentImg, 100);

            }
        });

        MyDataBase db = new MyDataBase(AddProduct.this);

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String libelle = addlibelle.getText().toString();
                int prix = Integer.parseInt(addprix.getText().toString());
                String desc = adddesc.getText().toString();
                String catg = adddcatg.getText().toString();


                BitmapDrawable drawable = (BitmapDrawable) addpic.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                image = getBytes(bitmap);

                Product product = new Product(image,libelle,prix,desc,catg);

                if(TextUtils.isEmpty(libelle) || TextUtils.isEmpty(addprix.getText().toString()) || TextUtils.isEmpty(desc) || TextUtils.isEmpty(catg)){
                    Toast.makeText(AddProduct.this,"Entre les infos de produit",Toast.LENGTH_SHORT).show();
                }else {
                    db.addProduct(product);
                    Toast.makeText(AddProduct.this,"le produit "+libelle+" est ajouter",Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    /* onClick function
    public void openGallerie(View view) {
        Intent intentImg = new Intent(Intent.ACTION_GET_CONTENT);
        intentImg.setType("image/*");
        startActivityForResult(intentImg, 100);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == 100){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
                addpic.setImageBitmap(decodeStream);

                image = getBytes(decodeStream);

            }catch(Exception ex){
                Log.e("ex",ex.getMessage());
            }
        }
    }


    //function just for testing image
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }


    public void logo(View view) {
        Intent i = new Intent(getApplication(),AdminPage.class);
        startActivity(i);
    }
}