package com.elgoumri.xtrend.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elgoumri.xtrend.Controller.MyDataBase;
import com.elgoumri.xtrend.Model.Person;
import com.elgoumri.xtrend.R;

public class SignUp extends AppCompatActivity {


    EditText nom;
    EditText prenom;
    EditText username;
    EditText adresse;
    EditText num;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Intent intent = getIntent();

        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        username = findViewById(R.id.username);
        adresse = findViewById(R.id.address);
        num = findViewById(R.id.num);
        password = findViewById(R.id.password);

        Button btn1 = (Button) findViewById(R.id.signup_button);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person person = new Person(nom.getText().toString().trim(),prenom.getText().toString().trim(),
                        username.getText().toString().trim(),adresse.getText().toString().trim(),
                        num.getText().toString().trim(),
                        password.getText().toString().trim());

                //put EditTexts in var
                String Nom = nom.getText().toString();
                String Prenom = prenom.getText().toString();
                String USER = username.getText().toString();
                String Adresse = adresse.getText().toString();
                String Num = num.getText().toString();
                String Password = password.getText().toString();

                MyDataBase myDataBase = new MyDataBase(SignUp.this);
                if (TextUtils.isEmpty(Nom) || TextUtils.isEmpty(Prenom) || TextUtils.isEmpty(USER) ||
                        TextUtils.isEmpty(Adresse) || TextUtils.isEmpty(Num) || TextUtils.isEmpty(Password)){
                    Toast.makeText(SignUp.this,"Enter your information",Toast.LENGTH_SHORT).show();
                }else{
                    Boolean check = myDataBase.checkUsername(USER);
                    if (check == true){
                        Toast.makeText(SignUp.this,"Username already exists",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(SignUp.this,"SignUp Successful",Toast.LENGTH_SHORT).show();

                        myDataBase.addPerson(person);
                        //back to login
                        Intent intent2 = new Intent(view.getContext(), Login.class);
                        startActivity(intent2);
                    }
                }

            }
        });

    }
}