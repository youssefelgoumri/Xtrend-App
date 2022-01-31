package com.elgoumri.xtrend.ui.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.elgoumri.xtrend.Controller.MyDataBase;
import com.elgoumri.xtrend.ListitemAdapter;
import com.elgoumri.xtrend.Model.Person;
import com.elgoumri.xtrend.Model.Product;
import com.elgoumri.xtrend.R;

import java.util.ArrayList;

public class DetailsOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_order);

        MyDataBase db = new MyDataBase(DetailsOrder.this);

        TextView nom = findViewById(R.id.name_buyer);
        TextView adresse = findViewById(R.id.address_buyer);
        TextView num = findViewById(R.id.num_buyer);

        Intent i = getIntent();
        int id_buyer = i.getIntExtra("id_buyer",-3);

        Person person = db.getPersonById(id_buyer);

        nom.setText(person.getNom()+ " " +person.getPrenom());
        adresse.setText(person.getAdresse());
        num.setText(person.getNum());

        GridView listView = findViewById(R.id.list_products_buy);

        ArrayList<Product> products = db.getCart(id_buyer);

        ListitemAdapter adapter = new com.elgoumri.xtrend.ListitemAdapter(getApplication(), products);
        listView.setAdapter(adapter);

    }
}