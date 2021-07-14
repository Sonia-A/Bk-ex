package com.example.bk_apk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {


    EditText product_name, product_price, product_description,product_category,product_mfgdate;
    Button btnAdd, btnList;
    ImageView imageupload;


    final int REQUEST_CODE_GALLERY= 999;

    public static SQLiteHelper msqlitehelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ActionBar actionBar =getSupportActionBar();
        actionBar.setTitle("New Product");

        product_name = findViewById(R.id.editname);
        product_price = findViewById(R.id.editprice);
        product_description =findViewById(R.id.editdescription);
        product_category=findViewById(R.id.editcategory);
        product_mfgdate =findViewById(R.id.editmanufacturingdate);
        imageupload = findViewById(R.id.imagenewrecord);


        btnAdd = findViewById(R.id.buttonsaveproduct);
        btnList=findViewById(R.id.viewbutton);

        msqlitehelper = new SQLiteHelper(this,"PRODUCT_DB.sqlite", null, 1);
        msqlitehelper.queryData(" CREATE TABLE IF NOT EXISTS product_table (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "PRODUCT_NAME TEXT, PRODUCT_PRICE TEXT, PRODUCT_DESCRIPTION TEXT, PRODUCT_CATEGORY TEXT,PRODUCT_MFGDATE TEXT,BLOB )");

        imageupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY
                );
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    msqlitehelper.recordItem(
                            product_name.getText().toString().trim(),
                            product_price.getText().toString().trim(),
                            product_description.getText().toString().trim(),
                            product_category.getText().toString().trim(),
                            product_mfgdate.getText().toString().trim(),
                            imageViewToByte(imageupload)
                    );
                Toast.makeText(MainActivity.this,"Product added",Toast.LENGTH_SHORT).show();
                product_name.setText("");
                product_price.setText("");
                product_description.setText("");
                product_category.setText("");
                product_mfgdate.setText("");
                imageupload.setImageResource(R.drawable.ic_baseline_add_a_photo_24);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ProductListActivity.class));

            }
        });

    }

    public static byte[] imageViewToByte(ImageView imageupload) {

        Bitmap bitmap =((BitmapDrawable)imageupload.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String [] permissions, @NonNull int[] grantResults) {
            if(requestCode == REQUEST_CODE_GALLERY){
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryIntent.setType("image/*");

                    startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
                }
                else{
                    Toast.makeText(this,"Permission required to access images", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        if (requestCode ==REQUEST_CODE_GALLERY && resultCode ==RESULT_OK){
            Uri imageUri= data.getData();
            CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1).start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode ==RESULT_OK){
                Uri resultUri =result.getUri();
                imageupload.setImageURI(resultUri);
            }
            else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error  = result.getError();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}