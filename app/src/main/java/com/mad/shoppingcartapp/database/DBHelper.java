package com.mad.shoppingcartapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "shoppingcart.db";

    private static final String TABLE_NAME_ITEM = "item";
    private static final String COLUMN_ITEM_ID = "_id";
    private static final String COLUMN_ITEM_TITLE = "name";
    private static final String COLUMN_ITEM_DESCRIPTION = "description";
    private static final String COLUMN_ITEM_IMAGE = "image";
    private static final String COLUMN_ITEM_PRICE = "price";
    private static final String COLUMN_ITEM_DISCOUNT = "discount";


    private static final String CREATE_TABLE_ITEM = " CREATE TABLE " + TABLE_NAME_ITEM + " (" +
            COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ITEM_TITLE + " TEXT," +
            COLUMN_ITEM_DESCRIPTION + " TEXT," +
            COLUMN_ITEM_IMAGE + " BLOB," +
            COLUMN_ITEM_PRICE + " TEXT," +
            COLUMN_ITEM_DISCOUNT + " TEXT)";


    private static final String TABLE_NAME_CART = "cart";
    private static final String CART_COLUMN_ID = "_id";
    private static final String CART_COLUMN_NAME = "name";
    private static final String CART_COLUMN_DESC = "description";
    private static final String CART_COLUMN_PRICE = "price";
    private static final String CART_COLUMN_QTY = "qty";
    private static final String CART_COLUMN_IMAGE = "image";

    private static final String SQL_CREATE_CART = "CREATE TABLE "+TABLE_NAME_CART+" ("+
            CART_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CART_COLUMN_NAME + " TEXT NOT NULL, " +
            CART_COLUMN_DESC + " TEXT NOT NULL, " +
            CART_COLUMN_PRICE + " INTEGER NOT NULL, " +
            CART_COLUMN_QTY + " INTEGER NOT NULL, " +
            CART_COLUMN_IMAGE + " BLOB);";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ITEM);
        db.execSQL(SQL_CREATE_CART);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_ITEM);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_CART);
        this.onCreate(db);
    }


    public boolean addCart(String name, String desc, int price, int qty, byte[] image) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CART_COLUMN_NAME,name);
        values.put(CART_COLUMN_DESC,desc);
        values.put(CART_COLUMN_PRICE, price);
        values.put(CART_COLUMN_QTY, qty);
        values.put(CART_COLUMN_IMAGE,image);

        long result = db.insert(TABLE_NAME_CART, null, values);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor getData(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql,null);

    }

    public long addInfo(String title, String description, byte[] image, String price, String discount) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(COLUMN_ITEM_TITLE,title);
        values.put(COLUMN_ITEM_DESCRIPTION,description);
        values.put(COLUMN_ITEM_IMAGE,image);
        values.put(COLUMN_ITEM_PRICE,price);
        values.put(COLUMN_ITEM_DISCOUNT,discount);

        long newrow=sqLiteDatabase.insert(TABLE_NAME_ITEM,null,values);

        return newrow;
    }
    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from "+TABLE_NAME_ITEM,null);

        return res;
    }

    public Integer deleteCart(String id){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME_CART,"Id= ?",new String[] {id});
    }


}
