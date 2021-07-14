package com.example.bk_apk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Model> ProductList;

    public ProductListAdapter(Context context, int layout, ArrayList<Model> ProductList) {

        this.context =context;
        this.layout=layout;
        this.ProductList=ProductList;
    }


    @Override
    public int getCount() {
        return ProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return ProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtproduct_name, txtproduct_price,txtproduct_description,txtproduct_category,txtproduction_mfgdate;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View row =view;
        ViewHolder holder =new ViewHolder();
        if(row==null){
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout,null);
            holder.txtproduct_name =row.findViewById(R.id.listproductname);
            holder.txtproduct_price=row.findViewById(R.id.listproductprice);
            holder.txtproduction_mfgdate=row.findViewById(R.id.listproductmfgdate);
            holder.imageView=row.findViewById(R.id.imagelistview);

            row.setTag(holder);
        }
        else {
            holder=(ViewHolder)row.getTag();
        }
        Model model =ProductList.get(position);

        holder.txtproduct_name.setText(model.getProduct_name());
        holder.txtproduct_price.setText(model.getProduct_price());
        holder.txtproduction_mfgdate.setText(model.getProduct_mfgdate());

        byte[] imagerecord =model.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagerecord,0,imagerecord.length);

        holder.imageView.setImageBitmap(bitmap);

        return  row;


    }
}
