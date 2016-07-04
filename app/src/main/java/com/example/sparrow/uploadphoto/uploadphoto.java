package com.example.sparrow.uploadphoto;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class uploadphoto extends AppCompatActivity implements View.OnClickListener {

    public static final String UPLOAD_URL = "http://i.cs.hku.hk/~xbzeng/album/upload.php";
    public static final String UPLOAD_KEY = "image";

    private int PICK_IMAGE_REQUEST = 1;

    private ImageView imageView;

    private Bitmap bitmap;

    private Uri filePath;

    ImageButton btn_upload;
    ImageButton btn_choose;
    ImageButton btn_view;

    //take photo
    private static int CAM_REQUEST = 2;
    String mCurrentPhotoPath;
    ImageButton buttonTakePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadphoto);

        btn_upload = (ImageButton) findViewById(R.id.btn_upload);
        btn_upload.setOnClickListener(this);
        btn_choose = (ImageButton) findViewById(R.id.btn_choose);
        btn_choose.setOnClickListener(this);
        btn_view = (ImageButton) findViewById(R.id.btn_viewImage);
        btn_view.setOnClickListener(this);

        imageView = (ImageView) findViewById(R.id.imageView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //take photo
        buttonTakePhoto = (ImageButton) findViewById(R.id.buttonTakePhoto);
        buttonTakePhoto.setOnClickListener(this);


    }

    //take photo start
    private File createImageFile()
    {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        Log.e("sdfsdfsdf","sdfsdfsdfsdf");
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = null;
        image = new File(storageDir+"/"+imageFileName+".jpg");

        // Save a file: path for use with ACTION_VIEW intents
        if(image != null){
            mCurrentPhotoPath = image.getAbsolutePath();
        }

        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (Exception ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, CAM_REQUEST);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
    //take photo end

    @Override
    public void onClick(View v) {
            String msg = "";

            switch (v.getId()) {
                case R.id.btn_upload:
                    if (imageView.getDrawable() != null  ){
                        uploadImage();
                    }else {
                        msg += "Please Pick a photo. Then touch me :)";
                    }
                    break;
                case R.id.btn_choose:
                    showFileChooser();
//                    msg += "Choose image";
                    break;
                case R.id.btn_viewImage:
                    viewImage();
//                    msg += "View cloud album";
                    break;
                case R.id.buttonTakePhoto:
                    dispatchTakePictureIntent();
//                    msg += "Take photo";
                    break;

            }

            if (!msg.equals("")) {
                Toast.makeText(uploadphoto.this, msg, Toast.LENGTH_SHORT).show();
            }


    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //take photo
        else if (requestCode == CAM_REQUEST && resultCode == RESULT_OK) {
                bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
                imageView.setImageBitmap(bitmap);
                uploadImage();
//gallery gets the uri of image
                galleryAddPic();
        }
        else {
            String msg = "You just canceled picking any photos :(";
            Toast.makeText(uploadphoto.this, msg, Toast.LENGTH_SHORT).show();
        }



    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String>{

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(uploadphoto.this, "Uploading...", null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();

                data.put(UPLOAD_KEY, uploadImage);
                String result = rh.sendPostRequest(UPLOAD_URL,data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

    private void viewImage() {
        startActivity(new Intent(this, ImageListView.class));
    }

}
