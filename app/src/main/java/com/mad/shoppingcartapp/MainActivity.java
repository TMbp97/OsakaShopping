package com.mad.shoppingcartapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.mad.shoppingcartapp.database.DBHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Item> itemlist;
    ItemAdapter adapter = null;
    String title,description,discount,price;
    ImageView imageViewIcon;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        listView = findViewById(R.id.itemList);
        itemlist = new ArrayList<>();
        adapter = new ItemAdapter(this ,R.layout.item,itemlist);
        listView.setAdapter(adapter);

        //get all data from db
        Cursor cursor = dbHelper.getData("SELECT * FROM item");
        itemlist.clear();
        while(cursor.moveToNext()){
            int id=cursor.getInt(0);
            title=cursor.getString(1);
            description = cursor.getString(2);
            byte[] image = cursor.getBlob(3);
            price=cursor.getString(4);
            discount= cursor.getString(5);
            itemlist.add(new Item(id,title,description,image,price,discount));
        }

        adapter.notifyDataSetChanged();

        if(itemlist.size()==0){
            //if there is no record in table of database
            Toast.makeText(this,"No record found...",Toast.LENGTH_LONG).show();

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ItemDetailsActivity.class);
                intent.putExtra("title",itemlist.get(position).getTitle());
                intent.putExtra("desc",itemlist.get(position).getDescription());
                intent.putExtra("price",itemlist.get(position).getPrice());
                intent.putExtra("image",itemlist.get(position).getImage());
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });


    }
}
