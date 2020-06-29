package edu.sabanciuniv.newsstarterexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import edu.sabanciuniv.newsstarterexample.model.NewsItem;
//this was the final homework for cs310 mobile applications course, i added pagination and changed the api to flicker's to do what you asked it to do, sorry for not starting it immediately
public class MainActivity extends AppCompatActivity implements  AdapterRecNews.RecNewsListener {

    RecyclerView recViewNews;
    ArrayList<NewsItem> newsItemList;
    ProgressBar pgsBar;
    AdapterRecNews adn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        setContentView(R.layout.activity_main);
        recViewNews = findViewById(R.id.recviewnews);
        pgsBar = findViewById(R.id.progress_circ);
        newsItemList = new ArrayList<>();
        adn= new AdapterRecNews(this, newsItemList, this);
        recViewNews.setLayoutManager((new LinearLayoutManager(this)));
        recViewNews.setAdapter(adn);
        recViewNews.addOnScrollListener(newsOnScrollListener);
        getSupportActionBar().setTitle("Flickr Feed");
        newsTask tsk=new newsTask();//get the news GET
        tsk.execute("https://www.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=f9dfc1656db05286fd25449cf39c1b0e&per_page=20&page=");
    }
    private  RecyclerView.OnScrollListener newsOnScrollListener = new RecyclerView.OnScrollListener(){
        //pagination from https://www.youtube.com/watch?v=hFkFBjS7-vQ
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if(isLastItemDisplaying(recyclerView)){
                Log.i("ListActivity","LoadMore");
                //TODO make it so that it loads the rest
                newsTask tsk=new newsTask();//get the news GET
                tsk.execute("https://www.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=f9dfc1656db05286fd25449cf39c1b0e&per_page=20&page=");
            }
        }
    };

    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if(recyclerView.getAdapter().getItemCount() !=0) {
            //get the last visible item on screen
            int lastVisibleItemPosition= ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            return lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1;
        }

        return false;
    }

    class newsTask extends AsyncTask<String,Void,String>{

        @Override
        protected  void onPreExecute(){
            super.onPreExecute();
            pgsBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(String... strings) {
            int page=(newsItemList.size()+20)/20; //0->1 20->2...
            String urlStr=strings[0]+ page +"&format=json&nojsoncallback=1";
            StringBuilder buffer= new StringBuilder();
            try {
                URL url=new URL(urlStr);
                HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line="";
                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            //newsItemList.clear();
            try{
                JSONObject obj =new JSONObject(s);
                if(obj.getString("stat").equals("ok"))
                {
                    JSONObject arrs= obj.getJSONObject("photos");
                    JSONArray arr= arrs.getJSONArray("photo");
                    for(int i=0;i<arr.length();i++) {
                        JSONObject current= (JSONObject) arr.get(i);
                        NewsItem item= new NewsItem(current);
                        newsItemList.add(item);
                    }
                }else {
                //normally this is where you'd display an error message and retry but I spent too much time tryng to get images
                }
                pgsBar.setVisibility(View.GONE);
                adn.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void RowClicked(NewsItem selectedNewsItem) {
        Intent i =new Intent(this,NewsDetailsActivity.class);
        i.putExtra("selectedNewsItem",selectedNewsItem);
        startActivity(i);
    }
}
