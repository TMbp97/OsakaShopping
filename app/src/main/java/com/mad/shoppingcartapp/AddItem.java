package com.mad.shoppingcartapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.shoppingcartapp.database.DBHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddItem extends AppCompatActivity {


    TextView Title,Description,Price,Discount,id;
    public static DBHelper dBhelper;
    Button add,show,update,delete;
    ImageView promoImage;

    final int IMAGE_REQUEST_CODE = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        dBhelper=new DBHelper(this);

        Title=findViewById(R.id.txttitle);
        Description=findViewById(R.id.txtdescription);
        Price=findViewById(R.id.txtprice);
        Discount=findViewById(R.id.txtdiscount);
        id=findViewById(R.id.txtid);
        promoImage = (ImageView)findViewById(R.id.image_addPromo);

        add=findViewById(R.id.btnadd);
        show =findViewById(R.id.btnshow);
        update=findViewById(R.id.btnupdate);
        delete=findViewById(R.id.btndelete);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddItem.this, MainActivity.class);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddItem.this, CartActivity.class);
                startActivity(intent);
            }
        });


        promoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(
                        AddItem.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        IMAGE_REQUEST_CODE
                );

            }
        });

        //addbtndata();
        btnshow();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == IMAGE_REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
            }else{
                Toast.makeText(getApplicationContext(),"You do not have permission to access gallery",Toast.LENGTH_LONG).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bt = BitmapFactory.decodeStream(inputStream);
                promoImage.setImageBitmap(bt);

            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public static byte[] imageViewToByte(ImageView image){
        Bitmap bt = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bt.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void btnshow(){
        show.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Cursor res =dBhelper.getAllData();
                        if (res.getCount() == 0) {

                            showMessage("ERROR", "Nothing found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            buffer.append(" Id :" + res.getString(0)+"\n");
                            buffer.append(" Title :" + res.getString(1) + "\n");
                            buffer.append(" Description :" + res.getString(2) + "\n");
                            buffer.append(" price:" + res.getString(4) + "\n");
                            buffer.append(" Discount:" + res.getString(5) + "\n\n");
                        }
                        showMessage("Data",buffer.toString());

                    }
                }
        );

    }


    public  void showMessage(String title,String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public  void addItem(View view){
        if(Title.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Title is requerd", Toast.LENGTH_LONG).show();
        }
        else if(Description.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Description is requerd", Toast.LENGTH_LONG).show();
        }else if(Price.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Price is requerd", Toast.LENGTH_LONG).show();
        }else if(Discount.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Discount is requerd", Toast.LENGTH_LONG).show();
        }else {


            String title = Title.getText().toString();
            String description = Description.getText().toString();
            String price = Price.getText().toString();
            //double Price=Double.parseDouble(P);
            String discount = Discount.getText().toString();
            byte[] image = imageViewToByte(promoImage);

            long result = dBhelper.addInfo(title, description, image, price, discount);

            if (result > 0) {
                Toast.makeText(this, "Details added successfully", Toast.LENGTH_LONG).show();
            } else {

                Toast.makeText(this, "Error,Cannot add data", Toast.LENGTH_LONG).show();

            }
        }

    }




}
