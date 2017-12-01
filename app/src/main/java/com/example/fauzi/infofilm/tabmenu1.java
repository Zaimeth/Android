package com.example.fauzi.infofilm;

/**
 * Created by Fauzi on 12/1/2017.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.google.gson.Gson;
import com.example.fauzi.infofilm.MovieModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class tabmenu1 extends Fragment {
    //Deklarasi variable untuk tempat menampung link API
    private final String URL_TO_HIT = "https://api.themoviedb.org/3/discover/movie?api_key=50f54409ccd173ae8354ce6d6421946f&sort_by=popularity.desc";

    //Deklarasi komponen layout
    private TextView tvData;
    private ListView lvResults;
    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_tabmenu1,container,false);
        //Membuat dialog loading
        dialog = new ProgressDialog(getActivity());
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");

        //Membuat konfigurasi untuk menampilkan gambar menggunakan ImageLoader
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        //Mendeklarasikan listview
        lvResults = (ListView) v.findViewById(R.id.lvResults);

        //Mengambil data dari API
        new JSONTask().execute(URL_TO_HIT);
        return v;
    }

    public class JSONTask extends AsyncTask<String, String, List<MovieModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<MovieModel> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                //Melakukan koneksi ke URL dalam parameter
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                //Menggunakan InputStream dan StringBuffer untuk membaca file yang diambil dari URL
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line ="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                //Memasukkan hasil file berbentuk JSON ke Variable
                String finalJson = buffer.toString();

                //Mengambil data dari object yang ada di file JSON
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("results");

                //Menampilkan isi dari object JSON ke dalam MovieModelList
                List<MovieModel> movieModelList = new ArrayList<>();

                Gson gson = new Gson();
                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    /**
                     * below single line of code from Gson saves you from writing the json parsing yourself
                     * which is commented below
                     */
                    MovieModel movieModel = gson.fromJson(finalObject.toString(), MovieModel.class);
                    movieModelList.add(movieModel);
                }
                return movieModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return  null;
        }

        @Override
        protected void onPostExecute(final List<MovieModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();

            //Jika ada data, tampilkan di layout row
            //Dan berikanlah aksi untuk pindah ke DetailActivity dengan isi data
            if(result != null) {
                MovieAdapter adapter = new MovieAdapter(getActivity(), R.layout.item_listview, result);
                lvResults.setAdapter(adapter);
                lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {  // list item click opens a new detailed activity
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MovieModel movieModel = result.get(position);
                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra("movieModel", new Gson().toJson(movieModel));
                        startActivity(intent);
                    }
                });
            } else {
                //Tidak ada data
                Toast.makeText(getActivity(), "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
            }
        }


    }


    public class MovieAdapter extends ArrayAdapter {

        private List<MovieModel> movieModelList;
        private int resource;
        private LayoutInflater inflater;

        public MovieAdapter(Context context, int resource, List<MovieModel> objects) {
            super(context, resource, objects);
            movieModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //Membuat tampilan pada MainActivity
            ViewHolder holder = null;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(resource, null);
                holder.ivMovieIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
                holder.tvMovie = (TextView) convertView.findViewById(R.id.tvMovie);
                holder.tvYear = (TextView) convertView.findViewById(R.id.tvYear);
                holder.rbMovieRating = (TextView) convertView.findViewById(R.id.rating);
                holder.tvStory = (TextView) convertView.findViewById(R.id.tvStory);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //Memberi value pada komponen-komponen layout
            final ViewHolder finalHolder = holder;
            ImageLoader.getInstance().displayImage("https://image.tmdb.org/t/p/w500" + movieModelList.get(position).getPoster_path(), holder.ivMovieIcon);
            holder.tvMovie.setText(movieModelList.get(position).getTitle());
            holder.tvYear.setText(movieModelList.get(position).getRelease_date());
            holder.rbMovieRating.setText(""  + movieModelList.get(position).getVote_average());
            holder.tvStory.setText(movieModelList.get(position).getOverview());
            return convertView;
        }

        //Deklarasi class ViewHolder yang dipakai diatas
        class ViewHolder {
            private ImageView ivMovieIcon;
            private TextView tvMovie;
            private TextView tvYear;
            private TextView rbMovieRating;
            private TextView tvStory;
        }
    }

}
