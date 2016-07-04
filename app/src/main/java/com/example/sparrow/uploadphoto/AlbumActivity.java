package com.example.sparrow.uploadphoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/*Start: Created by cbw*/
public class AlbumActivity extends Activity {
    String albumname;
    ArrayList<Integer> photoIds = new ArrayList<>();
    ArrayList<Bitmap> photos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        Intent intent = getIntent();
        albumname = intent.getStringExtra("albumname");

        TextView tv_albumTitle = (TextView)findViewById(R.id.tv_albumTitle);
        tv_albumTitle.setText(albumname);
        System.out.println("here im" + albumname);

        if(albumname.equals("All Photos"))
        {
            for(int i = 0; i < GetAlImages.bitmaps.length; i++)
            {
                photos.add(GetAlImages.bitmaps[i]);
                photoIds.add(i);
            }
        }
        else
        {
            for(int i = 0; i < GetAlImages.bitmaps.length; i++)
            {
                if(GetAlImages.imageDate[i].equals(albumname)) {
                    photos.add(GetAlImages.bitmaps[i]);
                    photoIds.add(i);
                }
            }
        }

        ImageButton btn_returnAlbums = (ImageButton)findViewById(R.id.btn_returnAlbums);
        btn_returnAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        GridView gv_myPhotos = (GridView) findViewById(R.id.gv_myPhotos);
        gv_myPhotos.setAdapter(new ImageAdapter(this));

        gv_myPhotos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //Toast.makeText(AlbumActivity.this, "" + position,
                //Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);
                //System.out.println("here from" + position);
                intent.putExtra("bitmapid", photoIds.get(position));
                startActivity(intent);

            }
        });
    }

    private class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return photos.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(158, 158));
                //imageView.setLayoutParams(new GridView.LayoutParams(400, 400));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(1, 1, 1, 1);
            } else {
                imageView = (ImageView) convertView;
            }
            //System.out.println("here p " + position);
            imageView.setImageBitmap(photos.get(position));

            return imageView;
        }


    }
}
/*End: Created by cbw*/
