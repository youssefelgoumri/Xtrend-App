package com.elgoumri.xtrend.ui.accueil;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.elgoumri.xtrend.ListitemAdapter;


import com.elgoumri.xtrend.Model.Product;
import com.elgoumri.xtrend.R;
import com.elgoumri.xtrend.databinding.FragmentAccueilBinding;
import com.elgoumri.xtrend.ui.profil.ProfilFragment;

import java.util.ArrayList;
import java.util.List;

public class AccueilFragment extends Fragment {

    //private AccueilViewModel accueilViewModel;
    private FragmentAccueilBinding binding;

    /*SliderView sliderView;
    int[] images = {
            R.drawable.black_friday2,
            R.drawable.new_year,
            R.drawable.buy_1_get_free,
            R.drawable.kids_clothes};*/

    ImageButton profil;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //accueilViewModel = new ViewModelProvider(this).get(AccueilViewModel.class);
        binding = FragmentAccueilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //go from Home to Profil
        profil = (ImageButton) root.findViewById(R.id.profil_button);
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //you can't use intent , u can use FragmentManagaer
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ProfilFragment llf = new ProfilFragment();
                ft.replace(R.id.nav_host_fragment_activity_main, llf);
                ft.commit();
            }
        });

        /*SliderAdapter sliderAdapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();*/

        //list of products
        List<Product> product = new ArrayList<>();
        product.add(new Product("White dress",150,"product1","d1"));
        product.add(new Product("Hoodie",120,"product2","d2"));
        product.add(new Product("Tshirt",120,"product3","d3"));
        product.add(new Product("Jean",120,"product4","d4"));
        product.add(new Product("Hand-Bag",120,"product5","d5"));
        product.add(new Product("Blue Dress",120,"product6","d6"));

        //using custom adapter to link list and data
        GridView listView = (GridView) root.findViewById(R.id.listView);
        ListitemAdapter adapter = new com.elgoumri.xtrend.ListitemAdapter(getActivity(), product);
        listView.setAdapter(adapter);

        //click on item of list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detailsProduct = new Intent(getActivity(), DetailsProduct.class);
                Product p = adapter.getItem(i);
                //passing product data to details product
                detailsProduct.putExtra("product_image",adapter.getMipmapResIdByName(p.getFlagName()));
                detailsProduct.putExtra("product_image_flag",p.getFlagName());
                detailsProduct.putExtra("product_libelle",p.getLibelle());
                detailsProduct.putExtra("product_prix",p.getPrix());
                detailsProduct.putExtra("product_desc",p.getDesc());
                startActivity(detailsProduct);
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