package edu.sabanciuniv.newsstarterexample.model;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.sabanciuniv.newsstarterexample.R;
/**
 * Created by hasimsait on 26/06/2020.
 */
public class NewsItem implements Serializable{

    private String url;
    private String name;
    private String uid;
    private String description;


    public NewsItem() {
        super();
    }

    public NewsItem(JSONObject current) {
        //https://github.com/codepath/android-oauth-flickr-demo/blob/master/app/src/main/java/com/codepath/apps/restclienttemplate/models/FlickrPhoto.java
        super();
        try{
            this.uid=current.getString("id");
            this.name = current.getString("title");
            // http://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
            //https://farm66.staticflickr.com/65535/50047450258_9a568697c2.jpg
            this.url = "http://farm" + current.getInt("farm") + ".staticflickr.com/" + current.getInt("server") +"/" + uid + "_" + current.getString("secret") + ".jpg";
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


}
