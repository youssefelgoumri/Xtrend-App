package com.elgoumri.xtrend.ui.catégorie;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.elgoumri.xtrend.Controller.MyDataBase;
import com.elgoumri.xtrend.R;
import com.elgoumri.xtrend.databinding.FragmentCategorieBinding;
import com.elgoumri.xtrend.ui.accueil.AccueilFragment;

import java.util.ArrayList;
import java.util.List;

public class CatégorieFragment extends Fragment {

    private CatégorieViewModel catégorieViewModel;
    private FragmentCategorieBinding binding;

    MyDataBase db;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        catégorieViewModel = new ViewModelProvider(this).get(CatégorieViewModel.class);

        binding = FragmentCategorieBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = new MyDataBase(getActivity());

        GridView catg_list = root.findViewById(R.id.ctg_list_view);
        TextView logo = root.findViewById(R.id.logo_name);

        ArrayList<String> catg = db.getAllCategory();
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),R.layout.category_item,R.id.text_catg,catg);
        catg_list.setAdapter(adapter);

        catg_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String cat_name = (String) adapterView.getItemAtPosition(i);

                Toast.makeText(getActivity(),cat_name,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getActivity(),ProdsCategory.class);
                intent.putExtra("category_name",cat_name);
                startActivity(intent);
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