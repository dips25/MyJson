package com.example.myjson;

import android.os.AsyncTask;
import android.util.Log;

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

public class FetchData extends AsyncTask<Void,Void,Void> {

    private static final String TAG = FetchData.class.getSimpleName();

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
            String first_name = profile.getString("first_name");
            String middle_name = profile.getString("middle_name");
            String last_name = profile.getString("last_name");
            String profile_image = profile.getString("profile_image");
            String city = profile.getString("city");
            String Country = profile.getString("Country");

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
        //MainActivity.jsontextview.setText(this.data);
    }
}
