package com.example.sparrow.uploadphoto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by sparrow on 6/12/15.
 */
public class GetAlImages {


    public static String[] imageURLs;
    public static Bitmap[] bitmaps;
    public static String[] imageName;
    public static String[] imageTime;
    public static String[] imageDate;

    public static final String JSON_ARRAY="result";
    public static final String IMAGE_URL = "url";
    private String json;
    private JSONArray urls;

    public GetAlImages(String json){
        this.json = json;
        try {
            JSONObject jsonObject = new JSONObject(json);
            urls = jsonObject.getJSONArray(JSON_ARRAY);
            System.out.println("here url" + urls);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getImage(JSONObject jo){
        URL url = null;
        Bitmap image = null;
        try {
            url = new URL(jo.getString(IMAGE_URL));
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void getAllImages() throws JSONException {



        bitmaps = new Bitmap[urls.length()];

        imageURLs = new String[urls.length()];
        imageName = new String[urls.length()];
        imageTime = new String[urls.length()];
        imageDate = new String[urls.length()];

        for(int i=0;i<urls.length();i++){
            imageURLs[i] = urls.getJSONObject(i).getString(IMAGE_URL);
            JSONObject jsonObject = urls.getJSONObject(i);
            bitmaps[i]=getImage(jsonObject);
            //图片信息函数
            imageName[i] = getImageName(imageURLs[i]);
            imageTime[i] = getImageTime(imageURLs[i]);
            imageDate[i] = getImageDate(imageURLs[i]);

        }

        //System.out.println("here!!!");
        Long[] time = new Long[urls.length()];
        for (int i = 0; i < imageTime.length; i++)
        {//System.out.println("here!!!");
            time[i] = Long.parseLong(imageTime[i].substring(2,4) + imageTime[i].substring(5,7) + imageTime[i].substring(8,10) +
            imageTime[i].substring(11,13) + imageTime[i].substring(14,16) + imageTime[i].substring(19,19));
            System.out.println("here date" + time[i]);
        }


        Long tmp;
        Bitmap tmpBitmap;
        String tmpDate;
        for (int i = time.length - 1; i > 0; --i)
        {
            for (int j = 0; j < i; ++j)
            {
                if (time[j + 1] < time[j])
                {
                    tmp = time[j];
                    time[j] = time[j + 1];
                    time[j + 1] = tmp;

                    tmpBitmap = bitmaps[j];
                    bitmaps[j] = bitmaps[j + 1];
                    bitmaps[j + 1] = tmpBitmap;

                    tmpDate = imageDate[j];
                    imageDate[j] = imageDate[j + 1];
                    imageDate[j + 1] = tmpDate;
                }
            }
        }

        for(int i = 0; i < GetAlImages.imageDate.length;i++)
        {
            System.out.println("here" + GetAlImages.imageDate[i]);
        }
    }

    //获取图片的名字
    protected String getImageName(String imageURL) {
        String Imagename = imageURL.substring(41);
        System.out.println(Imagename);
        return Imagename;
    }
    //获取图片的完整时间到分秒
    protected String getImageTime(String imageURL){
        String Imagename = imageURL.substring(41);
        String ImageTime = Imagename.substring(0,Imagename.indexOf("p")-1);
        System.out.println(ImageTime);
        return ImageTime;
    }
    //获取图片的日期到天
    protected String getImageDate(String imageURL) {
        String Imagename = imageURL.substring(41);
        String ImageTime = Imagename.substring(0, Imagename.indexOf("p") - 1);
        String ImageDate = ImageTime.substring(0, ImageTime.indexOf("-"));
        System.out.println(ImageDate);
        return ImageDate;
    }

}