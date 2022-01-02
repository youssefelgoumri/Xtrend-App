package com.elgoumri.xtrend.ui.panier;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.elgoumri.xtrend.Controller.MyDataBase;
import com.elgoumri.xtrend.ListitemAdapter;
import com.elgoumri.xtrend.Model.Product;
import com.elgoumri.xtrend.R;
import com.elgoumri.xtrend.databinding.FragmentPanierBinding;

import java.util.List;

public class PanierFragment extends Fragment {

    private PanierViewModel panierViewModel;
    private FragmentPanierBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        panierViewModel =
                new ViewModelProvider(this).get(PanierViewModel.class);

        binding = FragmentPanierBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        MyDataBase myDataBase = new MyDataBase(getContext());

        List product = myDataBase.getProduct();

            GridView listProduct = (GridView) root.findViewById(R.id.list_product);
            ListitemAdapter adapter = new com.elgoumri.xtrend.ListitemAdapter(getActivity(), product);
            listProduct.setAdapter(adapter);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}