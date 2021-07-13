package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anshutiwari.covid19tracker.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import model.Articles;

public class NewsActivityAdapter extends RecyclerView.Adapter<NewsActivityAdapter.NewsActivityHolder>{
    Context context;
    ArrayList<Articles> newsArticles;

    public NewsActivityAdapter(Context context,ArrayList<Articles> newsArticles){
        this.newsArticles = newsArticles;
        this.context = context;
    }
    @NonNull
    @Override
    public NewsActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsActivityHolder(LayoutInflater.from(context).inflate(R.layout.cell_for_news,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsActivityHolder holder, int position) {
        Articles currentNewsArticle = newsArticles.get(position);
        if(currentNewsArticle != null && !currentNewsArticle.description.equals("null")){
            holder.mTvNewsTitle.setText(currentNewsArticle.title);
            holder.mTvNewsDescription.setText(currentNewsArticle.description);
            holder.mTvNewsUrl.setText(currentNewsArticle.url);
            Glide.with(context).load(currentNewsArticle.urlToImage)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(holder.mIvNewsImage);
        }
    }

    @Override
    public int getItemCount() {
        return newsArticles != null ? newsArticles.size() : 0;
    }


    public class NewsActivityHolder extends RecyclerView.ViewHolder {

        private TextView mTvNewsTitle;
        private TextView mTvNewsDescription;
        private TextView mTvNewsUrl;
        private ImageView mIvNewsImage;

        public NewsActivityHolder(@NonNull View itemView) {
            super(itemView);

            mTvNewsTitle = itemView.findViewById(R.id.tv_news_title_activity);
            mTvNewsDescription = itemView.findViewById(R.id.tv_news_description_activity);
            mTvNewsUrl = itemView.findViewById(R.id.tv_news_url_activity);
            mIvNewsImage = itemView.findViewById(R.id.iv_news_image_activity);
        }
    }


}
