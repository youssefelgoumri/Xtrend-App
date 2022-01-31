package com.elgoumri.xtrend.ui.panier;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.elgoumri.xtrend.Controller.MyDataBase;
import com.elgoumri.xtrend.ListitemAdapter;
import com.elgoumri.xtrend.Model.Product;
import com.elgoumri.xtrend.R;
import com.elgoumri.xtrend.databinding.FragmentPanierBinding;
import com.elgoumri.xtrend.ui.accueil.AccueilFragment;

import java.util.ArrayList;
import java.util.List;

public class PanierFragment extends Fragment {

    private PanierViewModel panierViewModel;
    private FragmentPanierBinding binding;

    MyDataBase myDataBase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        panierViewModel =
                new ViewModelProvider(this).get(PanierViewModel.class);

        binding = FragmentPanierBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        myDataBase = new MyDataBase(getActivity());

        TextView total_text = root.findViewById(R.id.total);
        Button delete_products = root.findViewById(R.id.delete_products);


        SharedPreferences preferences = this.getActivity().getSharedPreferences("secret",0);
        int id_user = preferences.getInt("id_user",-1);


        ArrayList<Product> product = myDataBase.getCart(id_user);

        ListView listProduct = (ListView) root.findViewById(R.id.list_product);
        ListitemAdapter adapter = new com.elgoumri.xtrend.ListitemAdapter(getActivity(), product);
        listProduct.setAdapter(adapter);

        if(adapter.isEmpty()){
            Toast.makeText(getActivity(),"Cart is empty",Toast.LENGTH_LONG).show();
        }else{
            int total_number = myDataBase.getTotalCartById(id_user);
            total_text.setText(String.valueOf(total_number) + "dh");

            delete_products.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myDataBase.deleteCart(id_user);
                    Toast.makeText(getActivity(),"Your cart is deleted",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getActivity(),PanierFragment.class);
                }
            });
        }


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}