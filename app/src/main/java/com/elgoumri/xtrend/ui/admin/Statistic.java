package com.elgoumri.xtrend.ui.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.elgoumri.xtrend.Controller.MyDataBase;
import com.elgoumri.xtrend.Model.Product;
import com.elgoumri.xtrend.R;

import java.io.InputStream;

public class Statistic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        MyDataBase myDataBase = new MyDataBase(Statistic.this);

        TextView total_sells = findViewById(R.id.total_sells);

        total_sells.setText(myDataBase.getTotalSells());

        Product winProduct = myDataBase.getWinProd();

        TextView winProd = findViewById(R.id.winprod);
        winProd.setText(winProduct.getLibelle());

        ImageView imgPrd = findViewById(R.id.img_winprd);
        Bitmap bitmap= BitmapFactory.decodeByteArray(winProduct.getImage(),0,winProduct.getImage().length);
        imgPrd.setImageBitmap(bitmap);


    }
}