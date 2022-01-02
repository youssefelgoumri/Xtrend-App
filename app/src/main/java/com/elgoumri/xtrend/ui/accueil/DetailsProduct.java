package com.elgoumri.xtrend.ui.accueil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elgoumri.xtrend.Controller.MyDataBase;
import com.elgoumri.xtrend.Model.Product;
import com.elgoumri.xtrend.R;

public class DetailsProduct extends AppCompatActivity {

    ImageView image_product ;
    TextView libelle_product;
    TextView prix_product;
    TextView desc_product;

    Button addProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);

        image_product = findViewById(R.id.image_product);
        libelle_product = findViewById(R.id.libelle_product);
        prix_product = findViewById(R.id.prix_product);
        desc_product = findViewById(R.id.desc_product);

        addProduct = findViewById(R.id.add_to_cart);


        Intent i = getIntent();

        int image = i.getIntExtra("product_image",-2);
        String libelle = i.getStringExtra("product_libelle");
        int prix = i.getIntExtra("product_prix",-1);
        String desc = i.getStringExtra("product_desc");

        String image_flag = i.getStringExtra("product_image_flag");




        image_product.setImageResource(image);
        libelle_product.setText(libelle);
        prix_product.setText(String.valueOf(prix));
        desc_product.setText(desc);


        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = new Product(libelle,prix,image_flag,desc);
                MyDataBase myDataBase = new MyDataBase(DetailsProduct.this);
                myDataBase.addProduct(product);
                Toast.makeText(getApplicationContext(), libelle + " is added to cart", Toast.LENGTH_SHORT).show();
            }
        });

    }

}