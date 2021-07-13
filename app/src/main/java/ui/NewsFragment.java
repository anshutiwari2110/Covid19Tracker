package ui;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anshutiwari.covid19tracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.NewsActivityAdapter;
import api.APIClient;
import api.API_Interface;
import model.Articles;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsFragment extends Fragment {
    private RecyclerView mRcNews;
    private ImageView mIvBackButton;
    NewsActivityAdapter newsActivityAdapter;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mRcNews = view.findViewById(R.id.rv_news_activity);
        progressDialog = new ProgressDialog(getActivity());
        FloatingActionButton mFABScrollToTop = view.findViewById(R.id.fab_scroll_to_top);

        mRcNews.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        progressDialog.setMessage("Loading...");
        getHealthNews();
        mFABScrollToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRcNews.smoothScrollToPosition(0);

            }
        });
        return view;
    }

    private void getHealthNews() {
        progressDialog.show();
        API_Interface apiInterface = APIClient.getClient().create(API_Interface.class);
        Call<String> getHealthNews = apiInterface.getHealth();
        getHealthNews.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String value = response.body();
                ArrayList<Articles> articles = new ArrayList<>();

                try {
                    JSONObject jsonObject = new JSONObject(value);

                    JSONArray articlesArray = jsonObject.getJSONArray("articles");

                    for (int i = 0; i < articlesArray.length(); i++) {
                        Articles newArticles = Articles.parseJSONObject(articlesArray.optJSONObject(i));
                        articles.add(newArticles);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressDialog.hide();
                newsActivityAdapter = new NewsActivityAdapter(getActivity(), articles);
                mRcNews.setAdapter(newsActivityAdapter);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.hide();
                //Toast.makeText(NewsActivity.this, "Error encounter", Toast.LENGTH_SHORT).show();
            }
        });
    }
}