package com.elgoumri.xtrend.ui.accueil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

        MyDataBase db = new MyDataBase(DetailsProduct.this);

        image_product = findViewById(R.id.image_product);
        libelle_product = findViewById(R.id.libelle_product);
        prix_product = findViewById(R.id.prix_product);
        desc_product = findViewById(R.id.desc_product);

        addProduct = findViewById(R.id.add_to_cart);


        Intent i = getIntent();
        int id_product = i.getIntExtra("id",-3);

        /*
        int image = i.getIntExtra("product_image",-2);
        String libelle = i.getStringExtra("product_libelle");
        int prix = i.getIntExtra("product_prix",-1);
        String desc = i.getStringExtra("product_desc");

        String image_flag = i.getStringExtra("product_image_flag");
        */

        Product product = db.getProductById(id_product);


        Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);
        image_product.setImageBitmap(bitmap);
        libelle_product.setText(product.getLibelle());
        prix_product.setText(String.valueOf(product.getPrix()));
        desc_product.setText(product.getDesc());


        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean product_status = db.addToCart(product);

                if (product_status){
                    Toast.makeText(getApplicationContext(), product.getLibelle() + " is added to cart", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), product.getLibelle() + " is already on cart", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public void logo(View view) {
        Intent i = new Intent(getApplication(),AccueilFragment.class);
        startActivity(i);
    }
}