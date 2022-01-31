package com.elgoumri.xtrend.ui.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.elgoumri.xtrend.Controller.MyDataBase;
import com.elgoumri.xtrend.R;

import java.util.ArrayList;

public class Orders extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        MyDataBase db = new MyDataBase(Orders.this);

        ListView order_list = findViewById(R.id.ordersList);

        ArrayList<String> usernme_ord = db.getBuyersUsername();

        ArrayAdapter adapter = new ArrayAdapter(getApplication(), android.R.layout.simple_list_item_1,usernme_ord);
        order_list.setAdapter(adapter);

        if(adapter.isEmpty()){
            Toast.makeText(getApplication(),"Orders is empty",Toast.LENGTH_LONG).show();
        }else{
            order_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String buyer_name = (String) adapterView.getItemAtPosition(i);
                    int id_buyer = db.getIdPerson(buyer_name);
                    Intent intent = new Intent(getApplication(),DetailsOrder.class);
                    intent.putExtra("id_buyer",id_buyer);
                    startActivity(intent);
                }
            });
        }



    }
}