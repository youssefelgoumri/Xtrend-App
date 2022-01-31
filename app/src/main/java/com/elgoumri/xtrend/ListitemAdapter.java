package com.elgoumri.xtrend;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elgoumri.xtrend.Model.Product;

import java.util.List;

public class ListitemAdapter extends BaseAdapter {
    private Context context;
    private List<Product> itemList;

    private LayoutInflater inflater;

    public ListitemAdapter(Context context, List<Product> product){
        this.context=context;
        this.itemList=product;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Product getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.list_items,null);

        TextView productName = view.findViewById(R.id.name_id);
        TextView productPrix = view.findViewById(R.id.prix_id);
        ImageView productImage = view.findViewById(R.id.image_id);

        Product product = getItem(i);

        String itemName = product.getLibelle();
        int itemPrix = product.getPrix();
        //int imageId = this.getMipmapResIdByName(product.getFlagName());
        Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(), 0, product.getImage().length);


        productName.setText(itemName);
        productPrix.setText(Integer.toString(itemPrix)+" dh");
        //productImage.setImageResource(imageId);
        productImage.setImageBitmap(bitmap);


        return view;
    }

    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }
}
