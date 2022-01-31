package com.elgoumri.xtrend.ui.catégorie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.elgoumri.xtrend.Controller.MyDataBase;
import com.elgoumri.xtrend.ListitemAdapter;
import com.elgoumri.xtrend.Model.Product;
import com.elgoumri.xtrend.R;
import com.elgoumri.xtrend.ui.accueil.AccueilFragment;
import com.elgoumri.xtrend.ui.accueil.DetailsProduct;

import java.util.ArrayList;

public class ProdsCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prods_category);

        MyDataBase db = new MyDataBase(this);

        Intent i = getIntent();
        String category_name = i.getStringExtra("category_name");

        TextView cat_text = findViewById(R.id.cat_name);
        cat_text.setText(category_name);

        ArrayList<Product> productsInCategory = db.getProductByCategoryName(category_name);

        GridView productView = (GridView) findViewById(R.id.prod_list);
        ListitemAdapter adapter = new com.elgoumri.xtrend.ListitemAdapter(this, productsInCategory);
        productView.setAdapter(adapter);

        productView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Product selected_product = (Product) adapterView.getItemAtPosition(i);

                Intent detailsProduct_intent2 = new Intent(getApplication(), DetailsProduct.class);

                detailsProduct_intent2.putExtra("id", selected_product.getId_produit());
                startActivity(detailsProduct_intent2);

            }
        });


    }

    public void logo(View view) {
        Intent i = new Intent(getApplication(), AccueilFragment.class);
        startActivity(i);
    }
}