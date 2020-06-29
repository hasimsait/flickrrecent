package edu.sabanciuniv.newsstarterexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import edu.sabanciuniv.newsstarterexample.model.NewsItem;

public class NewsDetailsActivity extends AppCompatActivity {
    NewsItem selectedNewsItem;
    TextView newsDetailTitle;
    TextView newsDetailDate;
    ImageView newsDetailImage;
    TextView newsDetailText;
    Button commentButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        newsDetailImage=findViewById(R.id.newsdetailimg);
        newsDetailText=findViewById(R.id.newsdetailtext);
        selectedNewsItem= (NewsItem) getIntent().getSerializableExtra("selectedNewsItem");
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(selectedNewsItem.getUrl()+"_o", newsDetailImage);
        newsDetailText.setText(selectedNewsItem.getName());
        /*if(selectedNewsItem.getBitmap()==null)
        {new ImageDownloadTask(newsDetailImage).execute(selectedNewsItem);}
        else{newsDetailImage.setImageBitmap(selectedNewsItem.getBitmap());}*/

        /*TODO remove these from xml and here
        newsDetailTitle.setText(selectedNewsItem.getTitle());
        newsDetailDate.setText(selectedNewsItem.getNewsDate().toString());
        newsDetailText.setText(selectedNewsItem.getText());
        */
        getSupportActionBar().setTitle(selectedNewsItem.getUid());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent (NewsDetailsActivity.this,CommentsActivity.class);
                //context above may fuck up, replace with main if it does
                i.putExtra("newsID",selectedNewsItem.getId());
                startActivity(i);
            }
        });*/
        //it had the display/add comment functionality originally, we will only display image here

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
