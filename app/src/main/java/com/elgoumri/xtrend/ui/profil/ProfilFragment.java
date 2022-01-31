package com.elgoumri.xtrend.ui.profil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.elgoumri.xtrend.Controller.MyDataBase;
import com.elgoumri.xtrend.Model.Person;
import com.elgoumri.xtrend.R;
import com.elgoumri.xtrend.databinding.FragmentProfilBinding;
import com.elgoumri.xtrend.login.Login;
import com.elgoumri.xtrend.login.SignUp;

public class ProfilFragment extends Fragment {

    private ProfilViewModel profilViewModel;
    private FragmentProfilBinding binding;
    EditText username,user_phone,user_adresse;
    TextView idnom,idprenom;
    Button save ;

    MyDataBase myDataBase;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        profilViewModel =
                new ViewModelProvider(this).get(ProfilViewModel.class);



        binding = FragmentProfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        myDataBase = new MyDataBase(getActivity());

        SharedPreferences preferences = this.getActivity().getSharedPreferences("secret",0);
        int id_user = preferences.getInt("id_user",-1);


        username = root.findViewById(R.id.username);
        idnom = root.findViewById(R.id.idnom);
        idprenom = root.findViewById(R.id.idprenom);
        user_phone = root.findViewById(R.id.user_phone);
        user_adresse = root.findViewById(R.id.user_adresse);
        save =root.findViewById(R.id.save);

        Person person = myDataBase.getPerson(id_user);
        username.setText(person.getUsername());
        idnom.setText(person.getNom());
        idprenom.setText(person.getPrenom());
        user_phone.setText(person.getNum());
        user_adresse.setText(person.getAdresse());
        /*save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdate = myDataBase.update(username.getText().toString(),user_phone.getText().toString(),user_adresse.getText().toString());
                    }
                }
        );*/

        Button logout = root.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Login.class);
                startActivity(i);
            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}