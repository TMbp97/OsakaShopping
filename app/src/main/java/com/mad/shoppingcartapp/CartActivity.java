package com.mad.shoppingcartapp;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mad.shoppingcartapp.database.DBHelper;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    int id;
    ListView listView;
    ArrayList<Cart> cartlist;
    CartAdapter adapter = null;
    DBHelper dBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listView = (ListView)findViewById(R.id.cart_list);

        cartlist = new ArrayList<>();
        adapter = new CartAdapter(this,R.layout.activity_cart_item,cartlist);
        listView.setAdapter(adapter);

        dBhelper = new DBHelper(this);

        Cursor cursor = dBhelper.getData("SELECT * FROM cart");
        cartlist.clear();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name  = cursor.getString(1);
            String desc = cursor.getString(2);
            int price = cursor.getInt(3);
            int qty = cursor.getInt(4);
            byte[] image = cursor.getBlob(5);

            cartlist.add(new Cart(id,name,desc,price,qty,image));
        }
        adapter.notifyDataSetChanged();
        if(cartlist.size()==0){
            //if there is no record in table of database
            Toast.makeText(this,"Cart is Empty",Toast.LENGTH_SHORT).show();

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                    String cartid = String.valueOf(cartlist.get(position).getId());
//
//                    Integer deleteRows =dBhelper.deleteCart(cartid);
//                    if(deleteRows>0)
//                        Toast.makeText(CartActivity.this,
//                                "Data deleted",Toast.LENGTH_LONG).show();
//                    else
//                        Toast.makeText(CartActivity.this,
//                                "Data not  deleted",Toast.LENGTH_LONG).show();

                Toast.makeText(getApplicationContext(),"ITEM : "+cartlist.get(position).getName(),Toast.LENGTH_LONG).show();


            }
        });




    }
}
