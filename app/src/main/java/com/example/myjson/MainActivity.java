package com.example.myjson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myjson.Adapters.RidesAdapter;
import com.example.myjson.Models.Rides;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    public static TextView jsontextview;

    RecyclerView rides_recycler;
    TextView name;
    TextView location;
    TextView no_of_rides;
    TextView no_of_free_rides;
    TextView credits;
    RidesAdapter ridesAdapter;

    CircleImageView prof_img;

    ArrayList<Rides> ridesArrayList;
    String first_name;
    String last_name;
    String middle_name;
    String Country;
    String profile_image;
    String city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        //jsontextview = (TextView) findViewById(R.id.jsontextview);
        ridesArrayList = new ArrayList<>();

        FetchData fetchData = new FetchData();
        fetchData.execute();

    }

    private void init() {


        name = (TextView) findViewById(R.id.name);
        location = (TextView) findViewById(R.id.location);
        no_of_rides = (TextView) findViewById(R.id.no_of_rides);
        no_of_free_rides = (TextView) findViewById(R.id.no_of_free_rides);
        prof_img = (CircleImageView) findViewById(R.id.prof_img);
        rides_recycler = (RecyclerView) findViewById(R.id.rides_recycler);
        rides_recycler.setLayoutManager(new LinearLayoutManager(this));



    }

    public class FetchData extends AsyncTask<Void,Void,Void> {

        private  final String TAG = com.example.myjson.FetchData.class.getSimpleName();

        String line = "";
        String data = "";
        ArrayList<HashMap<String, String>> contactList;

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url = new URL("https://api.myjson.com/bins/1escek");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                while (line!=null){

                    line = bufferedReader.readLine();
                    data = data+line;

                }

                JSONObject jsonObject = new JSONObject(data);

                JSONObject data = jsonObject.getJSONObject("data");
                JSONObject profile = data.getJSONObject("profile");
                first_name = profile.getString("first_name");
                middle_name = profile.getString("middle_name");
                last_name = profile.getString("last_name");
                profile_image = profile.getString("profile_image");
                city = profile.getString("city");
                Country = profile.getString("Country");

                JSONObject stats = data.getJSONObject("stats");
                int rides = stats.getInt("rides");
                int free_rides = stats.getInt("free_rides");

                JSONObject credits = stats.getJSONObject("credits");
                int value = credits.getInt("value");
                String currency = credits.getString("currency");
                String currency_symbol = credits.getString("currency_symbol");

                JSONArray jsonArray = data.getJSONArray("trips");

                for (int i=0;i<jsonArray.length();i++){

                    JSONObject c = jsonArray.getJSONObject(i);

                    String from = c.getString("from");
                    String to = c.getString("to");
                    int from_time = c.getInt("from_time");
                    int to_time = c.getInt("to_time");
                    int trip_duration_in_mins = c.getInt("trip_duration_in_mins");


                    JSONObject cost = c.getJSONObject("cost");
                    int costvalue = cost.getInt("value");
                    String costcurrency = cost.getString("currency");
                    String costcurrencysymbol = cost.getString("currency_symbol");

                    Rides recyclerrides = new Rides();

                    recyclerrides.setFrom(from);
                    recyclerrides.setTo(to);
                    recyclerrides.setCost(costvalue);
                    recyclerrides.setTime(trip_duration_in_mins);
                    recyclerrides.setCurrency(currency_symbol);
                    ridesArrayList.add(recyclerrides);

                    Log.v(TAG,"JsonObject:" + cost.toString());

                }

                JSONObject theme = data.getJSONObject("theme");
                String dark_colour = theme.getString("dark_colour");
                String light_colour = theme.getString("light_colour");







            } catch (IOException e) {
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            name.setText(first_name + " " + last_name);
            location.setText(city+","+Country);

            if (middle_name != null ){

                Glide.with(MainActivity.this).load(middle_name).into(prof_img);


            }

            ridesAdapter = new RidesAdapter(MainActivity.this,ridesArrayList);
            rides_recycler.setAdapter(ridesAdapter);
            rides_recycler.setHasFixedSize(true);
            ridesAdapter.notifyDataSetChanged();

        }
    }

}
