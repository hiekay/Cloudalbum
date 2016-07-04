package com.example.sparrow.uploadphoto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sparrow on 6/12/15.
 */
public class ImageListView extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;

    public static final String GET_IMAGE_URL="http://i.cs.hku.hk/~xbzeng/album/getAllImages.php";

    public GetAlImages getAlImages;

    public static final String BITMAP_ID = "BITMAP_ID";

    final ArrayList<String> albumNames = new ArrayList<String>();

    final ArrayList<Bitmap> albumCovers = new ArrayList<Bitmap>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cloud_pic);

        ImageButton btn_returnMain = (ImageButton)findViewById(R.id.btn_returnMain);
        btn_returnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);

        getURLs();
    }

    private void getImages(){
        class GetImages extends AsyncTask<Void,Void,Void> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ImageListView.this,"Downloading images...","Please wait...",false,false);
            }

            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                loading.dismiss();

                String date, temp_date;
                temp_date = GetAlImages.imageDate[GetAlImages.bitmaps.length - 1];

                //final ArrayList<String> albumNames = new ArrayList<String>();
                //ArrayList<Bitmap> albumCovers = new ArrayList<Bitmap>();
                albumNames.add("All Photos");
                albumNames.add(temp_date);
                albumCovers.add(GetAlImages.bitmaps[GetAlImages.bitmaps.length - 1]);
                albumCovers.add(GetAlImages.bitmaps[GetAlImages.bitmaps.length - 1]);
                for(int i = GetAlImages.bitmaps.length - 1; i >= 0; i--)
                {
                    temp_date = GetAlImages.imageDate[i];
                    int isEqual = 0;
                    for(int j = 1; j < albumNames.size(); j++)
                    {
                        date = albumNames.get(j);
                        if(temp_date.equals(date))
                        {
                            isEqual = 1;
                            break;
                        }
                    }
                    if(isEqual == 0)
                    {
                        albumNames.add(temp_date);
                        albumCovers.add(GetAlImages.bitmaps[i]);
                        System.out.println("here" + i);
                        System.out.println("here date" + temp_date);
                    }

                }
                CustomList adapter = new CustomList(ImageListView.this, albumNames, albumCovers);
                listView.setAdapter(adapter);
                /*End: Created by cbw*/
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    getAlImages.getAllImages();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        GetImages getImages = new GetImages();
        getImages.execute();
    }

    private void getURLs() {
        class GetURLs extends AsyncTask<String,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ImageListView.this,"Loading...","Please Wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                getAlImages = new GetAlImages(s);
                getImages();
            }

            @Override
            protected String doInBackground(String... strings) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }
        }
        GetURLs gu = new GetURLs();
        gu.execute(GET_IMAGE_URL);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        /*Start: Created by cbw*/
        //TextView albumname = (TextView)findViewById(R.id.txtv_albumName);
        Intent intentToAlbum = new Intent(getApplicationContext(), AlbumActivity.class);
        System.out.println("here yes" );
        System.out.println("here " + albumNames.get(position));
        intentToAlbum.putExtra("albumname", albumNames.get(position));
        startActivity(intentToAlbum);
        /*End: Created by cbw*/

    }
}
