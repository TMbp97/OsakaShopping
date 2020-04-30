package com.mad.shoppingcartapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.shoppingcartapp.database.DBHelper;

public class ItemDetailsActivity extends AppCompatActivity {

    TextView title,description,price,discount, count;
    Button addQty,rmQty,addCart,orderNow,checkOut;
    ScrollView scroll;

    DBHelper dBhelper;

    ImageView image;
    int cnt = 1;
    int intPrice = 0 , keyPrice;
    LinearLayout address;
    boolean addr = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        dBhelper = new DBHelper(this);

        title = (TextView)findViewById(R.id.itmDetails_title);
        description = (TextView)findViewById(R.id.itmDetails_desc);
        price = (TextView)findViewById(R.id.itmDetails_price);
        count = (TextView)findViewById(R.id.itmDetails_counter);
        image = (ImageView)findViewById(R.id.itmDetials_image);

        addCart = (Button)findViewById(R.id.itmDetails_addCart);
        addQty = (Button)findViewById(R.id.Details_addQty);
        rmQty = (Button)findViewById(R.id.Details_removeQty);
        orderNow = (Button)findViewById(R.id.itmDetails_order);
        checkOut = (Button)findViewById(R.id.itmDetails_checkout);

        scroll = (ScrollView)findViewById(R.id.foodDetails_scroller);
        address = (LinearLayout)findViewById(R.id.address);
        address.setVisibility(View.GONE);

        final Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        description.setText(intent.getStringExtra("desc"));
        price.setText(intent.getStringExtra("price"));

        final byte[] img = intent.getByteArrayExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(img,0,img.length);
        image.setImageBitmap(bitmap);

        intPrice = Integer.parseInt(intent.getStringExtra("price"));
        keyPrice = intPrice;

        addQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cnt = cnt+1;
                intPrice = intPrice+keyPrice;
                count.setText(String.valueOf(cnt));
                price.setText(String.valueOf(intPrice));
            }
        });

        rmQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cnt>1){
                    cnt = cnt - 1;
                    intPrice = intPrice-keyPrice;
                    count.setText(String.valueOf(cnt));
                    price.setText(String.valueOf(intPrice));

                }
            }
        });

        orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(addr == true){
                    address.setVisibility(View.VISIBLE);
                    addr = false;

                    scroll.post(new Runnable() {
                        @Override
                        public void run() {
                            scroll.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });

                }else{
                    address.setVisibility(View.GONE);
                    addr = true;

                    scroll.post(new Runnable() {
                        @Override
                        public void run() {
                            scroll.fullScroll(ScrollView.FOCUS_UP);
                        }
                    });

                }

            }
        });

        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = title.getText().toString();
                String desc = description.getText().toString();
                int price = intPrice;
                int qty = cnt;
                byte[] image = img;

                if(dBhelper.addCart(name,desc,price,qty,image)){

                    Toast.makeText(getApplicationContext(),"Item Added to the Cart is Successfully.",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getApplicationContext(),"Item Adding to Cart is Failed",Toast.LENGTH_SHORT).show();
                }

            }
        });

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemDetailsActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });



    }
}
