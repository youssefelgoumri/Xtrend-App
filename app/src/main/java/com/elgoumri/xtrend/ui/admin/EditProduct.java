package com.elgoumri.xtrend.ui.admin;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.elgoumri.xtrend.Controller.MyDataBase;
import com.elgoumri.xtrend.Model.Product;
import com.elgoumri.xtrend.R;
import com.elgoumri.xtrend.ui.admin.ProductView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class EditProduct extends AppCompatActivity {

    EditText libelle_product, prix_product, desc_product,ctg_product;
    Button update_button, delete_button;
    ImageButton img_product;
    MyDataBase db;

    byte[] image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        db = new MyDataBase(EditProduct.this);

        img_product =findViewById(R.id.mimage);
        libelle_product = findViewById(R.id.mproduct);
        prix_product = findViewById(R.id.mprix);
        desc_product = findViewById(R.id.mdesc);
        ctg_product =findViewById(R.id.mcatg);
        update_button=findViewById(R.id.edit);
        delete_button=findViewById(R.id.delete);

        Intent i = getIntent();
        int id = i.getIntExtra("id",-2);

        Product product = db.getProductById(id);
        libelle_product.setText(product.getLibelle());
        prix_product.setText(String.valueOf(product.getPrix()));
        desc_product.setText(product.getDesc());
        ctg_product.setText(product.getCategorie());

        img_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentImg = new Intent(Intent.ACTION_GET_CONTENT);
                intentImg.setType("image/*");
                startActivityForResult(intentImg, 100);

            }
        });

        Bitmap bitmap= BitmapFactory.decodeByteArray(product.getImage(),0,product.getImage().length);
        img_product.setImageBitmap(bitmap);
        image = getBytes(bitmap);

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String libelle = libelle_product.getText().toString();
                int prix = Integer.parseInt(prix_product.getText().toString());
                String desc = desc_product.getText().toString();
                String ctg = ctg_product.getText().toString();

                BitmapDrawable drawable = (BitmapDrawable) img_product.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                image = getBytes(bitmap);

                Product product1 = new Product(id,image,libelle,prix,desc,ctg);
                db.updateData(product1);
                Toast.makeText(EditProduct.this,"produit est modifie",Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getApplication(), ProductView.class);
                startActivity(i);
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBilder = new AlertDialog.Builder(EditProduct.this);
                alertBilder.setTitle("supprimer le produit")
                        .setMessage("voulez-vous vraiment supprimer ce produit  ?")
                        .setPositiveButton("oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.deletProduct(id);
                                Intent i = new Intent(getApplication(), ProductView.class);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = alertBilder.create();
                dialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == 100){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap decodeStream = BitmapFactory.decodeStream(inputStream);
                img_product.setImageBitmap(decodeStream);

                image = getBytes(decodeStream);

            }catch(Exception ex){
                Log.e("ex",ex.getMessage());
            }
        }
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}