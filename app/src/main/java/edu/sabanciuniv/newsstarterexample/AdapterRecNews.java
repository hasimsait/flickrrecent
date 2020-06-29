package edu.sabanciuniv.newsstarterexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import edu.sabanciuniv.newsstarterexample.model.NewsItem;

public class AdapterRecNews extends RecyclerView.Adapter<AdapterRecNews.NewsViewHolder> {

    Context context;
    List<NewsItem> news;
    RecNewsListener listener;

    public AdapterRecNews(Context context, List<NewsItem> news,RecNewsListener listener) {
        this.context = context;
        this.news = news;
        this.listener=listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.new_row_layout,parent,false);
        NewsViewHolder holder= new NewsViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {
        /*holder.txtNewTitle.setText(news.get(position).getTitle());
        holder.txtNewDate.setText(news.get(position).getNewsDate().toString());*/
        holder.txtNewDate.setText(news.get(position).getUid());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.RowClicked(news.get(position));
            }
        });
/*
        if(news.get(position).getBitmap()==null){
            new ImageDownloadTask(holder.imgNew).execute(news.get(position)); gave up on bitmap and switched to Universal Image loader
        }else{
            holder.imgNew.setImageBitmap(news.get(position).getBitmap());
        }
*/      holder.txtNewTitle.setText(news.get(position).getName());
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(news.get(position).getUrl(), holder.imgNew);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public interface RecNewsListener{
        void RowClicked(NewsItem selectedNewsItem);
    }


    class NewsViewHolder extends RecyclerView.ViewHolder{
        ImageView imgNew;
        TextView txtNewTitle;
        TextView txtNewDate;
        LinearLayout root;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNew=itemView.findViewById(R.id.imgNew);
            txtNewTitle=itemView.findViewById(R.id.txtnewtitle);
            txtNewDate=itemView.findViewById(R.id.txtnewdate);
            root=itemView.findViewById(R.id.container);

        }
    }


}
