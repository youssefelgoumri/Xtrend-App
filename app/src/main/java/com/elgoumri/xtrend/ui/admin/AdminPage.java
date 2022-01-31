package com.elgoumri.xtrend.ui.admin;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.elgoumri.xtrend.R;
import com.elgoumri.xtrend.ui.admin.AddProduct;
import com.google.android.material.button.MaterialButton;

public class AdminPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        MaterialButton statisticBtn = (MaterialButton) findViewById(R.id.statistic);
        statisticBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Statistic.class);
                startActivity(intent);
            }
        });

        MaterialButton btn = (MaterialButton) findViewById(R.id.addProduct);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddProduct.class);
                startActivity(intent);
            }
        });


        MaterialButton btn1 = (MaterialButton) findViewById(R.id.editProduct);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ProductView.class);
                startActivity(intent);
            }
        });


        MaterialButton ordersBtn = (MaterialButton) findViewById(R.id.orders);
        ordersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Orders.class);
                startActivity(intent);
            }
        });


    }



}