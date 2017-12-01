package com.example.fauzi.infofilm;

/**
 * Created by Fauzi on 12/1/2017.
 */

public class MovieModel {
    //Deklarasi variable untuk menampung data dari file JSON
    private String results;
    private String poster_path;
    private String overview;
    private String release_date;
    private Object genre_ids;
    private int id;
    private String original_title;
    private String original_language;
    private String title;
    private String backdrop_path;
    private float popularity;
    private float vote_count;
    private boolean video;
    private float vote_average;

    //Method generate SET dan GET
    public String getResults() {
        return results;
    }
    public void setResults(String results) {
        this.results = results;
    }
    public String getPoster_path() {
        return poster_path;
    }
    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
    public String getOverview() {
        return overview;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }
    public String getRelease_date() {
        return release_date;
    }
    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
    public Object getGenre_ids() {
        return genre_ids;
    }
    public void setGenre_ids(Object genre_ids) {
        this.genre_ids = genre_ids;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getOriginal_title() {
        return original_title;
    }
    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }
    public String getOriginal_language() {
        return original_language;
    }
    public void setOriginal_language(String original_language) {this.original_language = original_language;}
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getBackdrop_path() {
        return backdrop_path;
    }
    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }
    public float getPopularity() {
        return popularity;
    }
    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }
    public float getVote_count() {
        return vote_count;
    }
    public void setVote_count(float vote_count) {
        this.vote_count = vote_count;
    }
    public boolean isVideo() {
        return video;
    }
    public void setVideo(boolean video) {
        this.video = video;
    }
    public float getVote_average() {
        return vote_average;
    }
    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }
}
