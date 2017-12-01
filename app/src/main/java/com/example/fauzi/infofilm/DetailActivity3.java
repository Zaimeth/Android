package com.example.fauzi.infofilm;

/**
 * Created by Fauzi on 12/1/2017.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.example.fauzi.infofilm.MovieModel;
import com.nostra13.universalimageloader.core.ImageLoader;

public class DetailActivity3 extends AppCompatActivity {

    //Deklarasi komponen layout
    private ImageView ivMovieIcon;
    private TextView tvMovie;
    private TextView tvYear;
    private TextView tvStory;
    private TextView rbMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail3);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Deklarasi komponen
        setUpUIViews();

        //Mengambil data yang dioper dari MainActivity
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //Menarik informasi dari Model berisikan data dari JSON
            String json = bundle.getString("movieModel");
            MovieModel movieModel = new Gson().fromJson(json, MovieModel.class);

            //Set value untuk komponen
            ImageLoader.getInstance().displayImage("https://image.tmdb.org/t/p/w500" + movieModel.getPoster_path(), ivMovieIcon);
            tvMovie.setText(movieModel.getTitle());
            tvYear.setText("" + movieModel.getRelease_date());
            rbMovie.setText("" + movieModel.getVote_average());
            tvStory.setText(movieModel.getOverview());
        }
    }

    //Method deklarasi komponen
    private void setUpUIViews() {
        ivMovieIcon = (ImageView) findViewById(R.id.ivIcon);
        tvMovie = (TextView) findViewById(R.id.tvTitle);
        tvYear = (TextView) findViewById(R.id.tvRelease_date);
        tvStory = (TextView) findViewById(R.id.tvOverview);
        rbMovie = (TextView) findViewById(R.id.rating);
    }

}
