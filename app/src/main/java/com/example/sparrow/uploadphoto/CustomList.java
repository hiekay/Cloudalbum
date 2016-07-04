package com.example.sparrow.uploadphoto;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sparrow on 6/12/15.
 */
public class CustomList extends ArrayAdapter<String> {
    /*private String[] urls;
    private Bitmap[] bitmaps;
    private Activity context;

    public CustomList(Activity context, String[] urls, Bitmap[] bitmaps) {
        super(context, R.layout.image_list_view, urls);
        this.context = context;
        this.urls= urls;
        this.bitmaps= bitmaps;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.image_list_view, null, true);
        TextView textViewURL = (TextView) listViewItem.findViewById(R.id.textViewURL);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageDownloaded);

        textViewURL.setText(urls[position]);
        textViewURL.setTextColor(Color.parseColor("#00796B"));
        image.setImageBitmap(Bitmap.createScaledBitmap(bitmaps[position],100,50,false));
        return  listViewItem;
    }*/
    private Activity context;
    private ArrayList<Bitmap> albumCovers = new ArrayList<>();
    private ArrayList<String> albumNames = new ArrayList<>();

    public CustomList(Activity context, ArrayList<String> albumNames, ArrayList<Bitmap> albumCovers) {
        super(context, R.layout.myalbum_item, albumNames);
        this.context = context;
        this.albumNames = albumNames;
        this.albumCovers = albumCovers;
        //this.albumIds = albumIds;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.myalbum_item, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txtv_albumName);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imgv_albumCover);
        //rowView.setTag(albumIds[position]);
        txtTitle.setText(albumNames.get(position));
        txtTitle.setTag(albumNames.get(position));
        imageView.setImageBitmap(albumCovers.get(position));
        return rowView;
    }
}