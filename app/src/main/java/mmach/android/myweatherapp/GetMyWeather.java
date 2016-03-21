package mmach.android.myweatherapp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Mike on 3/20/2016.
 */
public class GetMyWeather {
    private static final String TAG = "GetMyWeather";

    private static final String API_KEY = "0b7bd177ebab35b954dbb172ea1a91f0";
    private static final String CITY = "Oakland";
    private static final String COUNTRY = "USA";
    private static final String FAHRENHEIT = "imperial";

    public static final Uri ENDPOINT = Uri.parse("http://api.openweathermap.org/data/2.5/weather")
            .buildUpon()
            .appendQueryParameter("q", CITY + ',' + COUNTRY)
            .appendQueryParameter("APPID", API_KEY)
            .appendQueryParameter("units", FAHRENHEIT)
            .build();

    public static WeatherItem generateWeatherData() {
        WeatherItem weatherItem = new WeatherItem();
        Log.i(TAG, "Now using generateWeatherData");

        try {
            JSONObject jBody = new JSONObject(convertDataToString());

            JSONArray jWeathers = jBody.getJSONArray("weather");
            JSONObject jWeather = jWeathers.getJSONObject(1);

            JSONObject jDetail = jBody.getJSONObject("main");

            JSONObject jSys = jBody.getJSONObject("sys");

            Log.i(TAG, "The Weather: " + jWeather.getString("main"));
            weatherItem.setWeather(jWeather.getString("main"));

            Log.i(TAG, "The Location: " + jBody.getString("name") + ", " + jSys.get("country"));
            weatherItem.setLocation(jBody.getString("name") + ", " + jSys.get("country"));

            Log.i(TAG, "The High Temp: " + jDetail.getString("temp_max"));
            weatherItem.setHighTemperature(jDetail.getString("temp_max"));

            Log.i(TAG, "The Low Temp: " + jDetail.getString("temp_min"));
            weatherItem.setLowTemperature(jDetail.getString("temp_min"));
        } catch (IOException ioe) {
            Log.d(TAG, "generateWeatherData: " + ioe);
        } catch (JSONException j) {
            Log.d(TAG, "generateWeatherData: " + j);
        }

        return weatherItem;
    }

    public static byte[] connectAndGetData() throws IOException {
        URL url = new URL(ENDPOINT.toString());
        HttpURLConnection hcon = (HttpURLConnection) url.openConnection();
        Log.i(TAG, "Connection to " + ENDPOINT);

        try {
            InputStream in = hcon.getInputStream();
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            int bytesRead = 0;
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            return out.toByteArray();
        } finally {
            hcon.disconnect();
        }
    }

    public static String convertDataToString() throws IOException {
        return new String(connectAndGetData());
    }

}
