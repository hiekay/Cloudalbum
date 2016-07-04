package com.example.sparrow.uploadphoto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by sparrow on 6/12/15.
 */
public class PhotoActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        /*Intent intent = getIntent();
        int i = intent.getIntExtra(ImageListView.BITMAP_ID,0);

        imageView = (ImageView) findViewById(R.id.imageViewFull);
        imageView.setImageBitmap(GetAlImages.bitmaps[i]);*/

        /*Start: Created by cbw*/
        Intent intent = getIntent();
        int bitmapid = intent.getIntExtra("bitmapid",0);

        ImageView iv_myPhoto = (ImageView)findViewById(R.id.iv_myphoto);
        System.out.println("here to" + bitmapid);

        iv_myPhoto.setImageBitmap(GetAlImages.bitmaps[bitmapid]);

        LinearLayout linlayout_myphoto = (LinearLayout)findViewById(R.id.linlayout_myphoto);
        linlayout_myphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*End: Created by cbw*/
    }
}
