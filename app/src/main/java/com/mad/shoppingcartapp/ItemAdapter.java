package com.mad.shoppingcartapp;

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

public class ItemAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Item> itemlist;

    public ItemAdapter(Context context, int layout, ArrayList<Item> drlist) {
        this.context = context;
        this.layout = layout;
        this.itemlist = drlist;
    }

    @Override
    public int getCount() {
        return itemlist.size();
    }

    @Override
    public Object getItem(int i) {
        return itemlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private class viewHolder{
        ImageView imageView;
        TextView txtTitle,txtDescription,txtprice,txtdiscount;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row=view;
        viewHolder holder=new viewHolder();
        if(row==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);
            holder.txtTitle = row.findViewById(R.id.txt_itmName);
            holder.txtDescription = row.findViewById(R.id.txt_itmDesc);
            holder.imageView = row.findViewById(R.id.itmImage);
            holder.txtprice = row.findViewById(R.id.txt_itmPrice);
            holder.txtdiscount = row.findViewById(R.id.txt_itmDesc);
            row.setTag(holder);

        }
        else{
            holder=(viewHolder)row.getTag();
        }
        Item item=itemlist.get(i);
        byte[] image = item.getImage();
        holder.txtTitle.setText(item.getTitle());
        holder.txtDescription.setText(item.getDescription());
        Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
        holder.imageView.setImageBitmap(bitmap);
        holder.txtprice.setText(item.getPrice());
        holder.txtdiscount.setText(item.getDiscount());

        /*byte[] recordImage = modelmeal.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage, 0,recordImage.length);
        holder.imageView.setImageBitmap(bitmap);            */

        return row;
    }


}
