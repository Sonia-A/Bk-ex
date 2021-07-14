package com.example.bk_apk;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    ListView mlistview;
    ArrayList<Model> mlist;

    ProductListAdapter mAdapter =null;

    ImageView imageViewIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle("Product List");

        mlistview = findViewById(R.id.listview);
        mlist =new ArrayList<>();
        mAdapter =new ProductListAdapter(this, R.layout.row, mlist);
        mlistview.setAdapter(mAdapter);

        Cursor cursor =MainActivity.msqlitehelper.getData("SELECT * FROM product_table ORDER BY PRODUCT_NAME ASC");
        mlist.clear();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            String description = cursor.getString(3);
            String category =cursor.getColumnName(4);
            String mfgdate = cursor.getString(5);
            byte[] image =cursor.getBlob(6);

            mlist.add(new Model(id,name,price,description,category,mfgdate,image));

        }
        mAdapter.notifyDataSetChanged();
        if(mlist.size() ==0){
            Toast.makeText(this,"No product data Found!",Toast.LENGTH_SHORT).show();
        }

        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final  CharSequence[] items ={"View Details"};
                AlertDialog.Builder dialog =new AlertDialog.Builder(ProductListActivity.this);

                dialog.setTitle("Choose an Option");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if( which == 0 ){
                            Cursor c= MainActivity.msqlitehelper.getData("SELECT id FROM product_table");
                            ArrayList<Integer> arrID =new ArrayList<Integer>();
                            while (c.moveToNext()){
                                arrID.add(c.getInt(0));
                            }
                            ShowDialogUpdate(ProductListActivity.this,arrID.get(position));

                        }
                        if (which ==1){

//                            Cursor cursor = MainActivity.msqlitehelper.getData()

                        }
                    }
                });
                dialog.show();

            }

        });

    }
    private void ShowDialogUpdate (Activity activity, final  int position){
        final Dialog dialog =new Dialog(activity);
        dialog.setContentView(R.layout.product_details);
        dialog.setTitle("Details");

        imageViewIcon =dialog.findViewById(R.id.imageviewrecord);
        TextView name =dialog.findViewById(R.id.nameupdate);
        TextView price =dialog.findViewById(R.id.priceupdate);
        TextView desc =dialog.findViewById(R.id.descupdate);
        TextView category =dialog.findViewById(R.id.cateupdate);
        TextView mfgdate =dialog.findViewById(R.id.mfgdateupdate);
        Button btnUpdate =dialog.findViewById(R.id.btnupdate);
        Button sharing =dialog.findViewById(R.id.sharing);

        Cursor cursor =MainActivity.msqlitehelper.getData("SELECT * FROM product_table WHERE ID="+position);
        mlist.clear();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String boxname = cursor.getString(1);
            name.setText(boxname);
            String boxprice = cursor.getString(2);
            price.setText(boxprice);
            String boxdescription = cursor.getString(3);
            desc.setText(boxdescription);
            String boxcategory =cursor.getString(4);
            category.setText(boxcategory);
            String boxmfgdate = cursor.getString(5);
            mfgdate.setText(boxmfgdate);
            byte[] image =cursor.getBlob(6);
            imageViewIcon.setImageBitmap(BitmapFactory.decodeByteArray(image,0,image.length));

            mlist.add(new Model(id,boxname,boxprice,boxdescription,boxcategory,boxmfgdate,image));
            mAdapter.notifyDataSetChanged();


        }

        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels*0.95);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels*0.85);
        dialog.getWindow().setLayout(width,height);

        dialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    MainActivity.msqlitehelper.updateData(
                            name.getText().toString().trim(),
                            price.getText().toString().trim(),
                            desc.getText().toString().trim(),
                            category.getText().toString().trim(),
                            mfgdate.getText().toString().trim(),
                            MainActivity.imageViewToByte(imageViewIcon),
                            position
                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"details fetched",Toast.LENGTH_SHORT).show();
                }
               catch (Exception error)
               {
                   Log.e("update error",error.getMessage());
               }
                updateProductList();
            }
        });
        sharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    MainActivity.msqlitehelper.updateData(
                            name.getText().toString().trim(),
                            price.getText().toString().trim(),
                            desc.getText().toString().trim(),
                            category.getText().toString().trim(),
                            mfgdate.getText().toString().trim(),
                            MainActivity.imageViewToByte(imageViewIcon),
                            position
                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),"details fetched",Toast.LENGTH_SHORT).show();
                }
                catch (Exception error)
                {
                    Log.e("update error",error.getMessage());
                }
                SharingData(ProductListActivity.this,position);
            }
        });





    }

    private void SharingData(Activity activity, final  int position) {
        Cursor cursor =MainActivity.msqlitehelper.getData("SELECT * FROM product_table WHERE ID="+position);
        mlist.clear();

        List dat = new ArrayList<String>();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String boxname = cursor.getString(1);
            String boxprice = cursor.getString(2);
            String boxdescription = cursor.getString(3);
            String boxcategory =cursor.getString(4);
            String boxmfgdate = cursor.getString(5);
            byte[] image =cursor.getBlob(6);

            dat.add(id);
            dat.add(boxname);
            dat.add(boxprice);
            dat.add(boxdescription);
            dat.add(boxcategory);
            dat.add(boxmfgdate);

            //imageViewIcon.setImageBitmap(BitmapFactory.decodeByteArray(image,0,image.length));

           // mlist.add(new Model(id,boxname,boxprice,boxdescription,boxcategory,boxmfgdate,image));
        }
        Intent intent =new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String Body ="product info";
        String Sub = dat.toString();
        intent.putExtra(Intent.EXTRA_TEXT,Sub);

        startActivity(Intent.createChooser(intent,"Share Using"));


    }

    private void updateProductList() {
        Cursor cursor = MainActivity.msqlitehelper.getData("SELECT * FROM product_table ORDER BY PRODUCT_NAME ASC");
        mlist.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            String description = cursor.getString(3);
            String category =cursor.getColumnName(4);
            String mfgdate = cursor.getString(5);
            byte[] image =cursor.getBlob(6);

            mlist.add(new Model(id,name,price,description,category,mfgdate,image));    }
        mAdapter.notifyDataSetChanged();
}}