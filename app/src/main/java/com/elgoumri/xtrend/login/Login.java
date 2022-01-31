package com.elgoumri.xtrend.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.elgoumri.xtrend.Controller.MyDataBase;
import com.elgoumri.xtrend.MainActivity;
import com.elgoumri.xtrend.R;
import com.elgoumri.xtrend.ui.admin.AdminPage;

public class Login extends AppCompatActivity {

    EditText username;
    EditText password;

    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        Button btn1 = (Button) findViewById(R.id.login_button);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //put EditTexts in var
                String user = username.getText().toString();
                String pass = password.getText().toString();

                //call database
                MyDataBase myDataBase = new MyDataBase(Login.this);

                //check if username and password is null
                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)){
                    Toast.makeText(Login.this,"Enter username and password",Toast.LENGTH_SHORT).show();
                }
                else{
                    //put result of bool checkUserPass is in database
                    Boolean check = myDataBase.checkUsernamePassword(user,pass);
                    if(check == true){
                        Toast.makeText(Login.this,"Login Successful",Toast.LENGTH_SHORT).show();

                        if(user.equals("admin") && pass.equals("admin")){
                            Intent intent1 = new Intent(view.getContext(), AdminPage.class);
                            startActivity(intent1);
                        }else{
                            //getID of username
                            int IdUser = myDataBase.getIdPerson(user);

                            //save id of user bcs we need it in product
                            prefs = getSharedPreferences("secret",0);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt("id_user",IdUser);
                            editor.commit();

                            //matnsach tbdl hna dir admin bo7do w user bo7do
                            Intent intent2 = new Intent(view.getContext(), MainActivity.class);
                            startActivity(intent2);
                        }

                    }
                    else{
                        Toast.makeText(Login.this,"Username or password is not correct",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        Button btn2 = (Button) findViewById(R.id.signup_button);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(view.getContext(), SignUp.class);
                startActivity(intent2);
            }
        });
    }

}