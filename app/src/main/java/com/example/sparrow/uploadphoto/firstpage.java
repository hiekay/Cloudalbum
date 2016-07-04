package com.example.sparrow.uploadphoto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class firstpage extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_firstpage);
        Handler handler = new Handler();

        handler.postDelayed(new firsthandler(), 3000);
    }

    class firsthandler implements Runnable
    {

        public void run()
        {
            // TODO Auto-generated method stub
            startActivity(new Intent(firstpage.this, uploadphoto.class));
            firstpage.this.finish();
        }
    }




}
