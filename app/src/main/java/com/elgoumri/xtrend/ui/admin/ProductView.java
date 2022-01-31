package com.elgoumri.xtrend.ui.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.elgoumri.xtrend.Controller.MyDataBase;
import com.elgoumri.xtrend.ui.admin.EditProduct;
import com.elgoumri.xtrend.ListitemAdapter;
import com.elgoumri.xtrend.Model.Product;
import com.elgoumri.xtrend.R;
import com.elgoumri.xtrend.ui.accueil.DetailsProduct;

import java.util.ArrayList;

public class ProductView extends AppCompatActivity {

    MyDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        db = new MyDataBase(getApplication());

        ArrayList<Product> product = db.getProducts();

        //using custom adapter to link list and data
        GridView listView = (GridView) findViewById(R.id.listView);
        ListitemAdapter adapter = new com.elgoumri.xtrend.ListitemAdapter(getApplication(), product);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Product p = adapter.getItem(i);
                Product selected_product = (Product) adapterView.getItemAtPosition(i);

                Intent detailsProduct_intent = new Intent(getApplication(), EditProduct.class);

                detailsProduct_intent.putExtra("id", selected_product.getId_produit());

                startActivity(detailsProduct_intent);
            }
        });
    }
}