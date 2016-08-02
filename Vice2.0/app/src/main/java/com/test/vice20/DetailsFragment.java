package com.test.vice20;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.vice20.Models.Item;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nolbertoarroyo on 8/1/16.
 */
public class DetailsFragment extends Fragment {
    NewsServiceInterface newsServiceInterface;
    Item currentItem;
    private String id;
    private ImageView articleImage;
    private TextView titleText, authorText, contentText, categoryText, pubDateText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://vice.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        newsServiceInterface = retrofit.create(NewsServiceInterface.class);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_details, container, false);
        setViews(v);
        getItem(id);
        return v;
    }

    public void populateViews() {
        //setting article properties to views
        titleText.setText(currentItem.getTitle());
        authorText.setText(currentItem.getAuthor());
        categoryText.setText(currentItem.getCategory());
        contentText.setText(currentItem.getBody());
        pubDateText.setText(currentItem.getPubDate());
        // uses picasso for image
        Picasso.with(getContext())
                .load(currentItem.getImage())
                .into(articleImage);


    }
    //getItem takes article id and runs a callback to retrieve article from api, runs populateViews() to set article properties to views
    public void getItem(String id) {
        newsServiceInterface.getArticle(id).enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                currentItem = response.body();
                populateViews();
            }


            @Override
            public void onFailure(Call<Item> call, Throwable t) {

            }
        });
    }
    // setter method to receive article position from listFragment
    public void setId(String id) {
        this.id = id;
    }


    public void setViews(View v){
        //finding views from xml
        titleText = (TextView) v.findViewById(R.id.details_frag_title);
        authorText = (TextView) v.findViewById(R.id.details_frag_author);
        categoryText = (TextView) v.findViewById(R.id.details_frag_category);
        contentText = (TextView) v.findViewById(R.id.details_frag_content);
        articleImage = (ImageView) v.findViewById(R.id.details_frag_image);
        pubDateText = (TextView) v.findViewById(R.id.details_frag_pub_date);

    }

}