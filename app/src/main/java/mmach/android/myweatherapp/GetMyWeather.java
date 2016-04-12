package mmach.android.myweatherapp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Mike on 3/20/2016.
 */
public class GetMyWeather {
    private static final String TAG = "GetMyWeather";

    private static final String API_KEY = "0b7bd177ebab35b954dbb172ea1a91f0";
    private static final String CITY = "Oakland";
    private static final String COUNTRY = "USA";
    private static final String FAHRENHEIT = "imperial";

    public static final Uri WEATHER_ENDPOINT = Uri.parse("http://api.openweathermap.org/data/2.5/weather")
            .buildUpon()
            .appendQueryParameter("q", CITY + ',' + COUNTRY)
            .appendQueryParameter("APPID", API_KEY)
            .appendQueryParameter("units", FAHRENHEIT)
            .build();

    public static final Uri FORECAST_ENDPOINT = Uri.parse("http://api.openweathermap.org/data/2.5/forecast/daily")
            .buildUpon()
            .appendQueryParameter("q", CITY + ',' + COUNTRY)
            .appendQueryParameter("APPID", API_KEY)
            .appendQueryParameter("units", FAHRENHEIT)
            .build();

    public static WeatherItem generateWeatherData() {
        WeatherItem weatherItem = new WeatherItem();
        Log.i(TAG, "Now using generateWeatherData");

        try {
            JSONObject jBody = new JSONObject(convertDataToString(WEATHER_ENDPOINT.toString()));

            //Sometimes there may be a 2nd weather, for now stick with the first weather that appears
            JSONArray jWeathers = jBody.getJSONArray("weather");
            JSONObject jWeather = jWeathers.getJSONObject(0);

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

            String weatherIconURL = generateImageURL(jWeather.getString("icon"));
            Log.i(TAG, "The icon is: " + weatherIconURL);
            weatherItem.setImgURL(weatherIconURL);

        } catch (IOException ioe) {
            Log.d(TAG, "generateWeatherData: " + ioe);
        } catch (JSONException j) {
            Log.d(TAG, "generateWeatherData: " + j);
        }

        return weatherItem;
    }

    public static ArrayList<WeatherItem> generateForcastData() {
        ArrayList<WeatherItem> weatherItems = new ArrayList<WeatherItem>();
        try {
            JSONObject jObject = new JSONObject(convertDataToString(FORECAST_ENDPOINT.toString()));
            JSONArray jForcastArray = jObject.getJSONArray("list");

            WeatherItem tempWeatherItem = new WeatherItem();
            weatherItems.add(tempWeatherItem);
            weatherItems.add(tempWeatherItem);
            weatherItems.add(tempWeatherItem);

            weatherItems.get(0).setImgURL(generateImageURL(
                    jForcastArray
                    .getJSONObject(0)
                    .getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("icon")));
            Log.i(TAG, "generateForcastData: " + weatherItems.get(0).getImgURL());

            weatherItems.get(1).setImgURL(generateImageURL(
                    jForcastArray
                    .getJSONObject(1)
                    .getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("icon")));
            Log.i(TAG, "generateForcastData: " + weatherItems.get(1).getImgURL());

            weatherItems.get(2).setImgURL(generateImageURL(
                    jForcastArray
                    .getJSONObject(2)
                    .getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("icon")));
            Log.i(TAG, "generateForcastData: " + weatherItems.get(2).getImgURL());


            Log.i(TAG, "generateForcastData 2: " + weatherItems.get(0).getImgURL());
            Log.i(TAG, "generateForcastData 2: " + weatherItems.get(1).getImgURL());
            Log.i(TAG, "generateForcastData 2: " + weatherItems.get(2).getImgURL());

        } catch (IOException e) {
            Log.d(TAG, "generateWeatherData: " + e);
        } catch (JSONException e) {
            Log.d(TAG, "generateWeatherData: " + e);
        }

        return weatherItems;
    }

    public static byte[] connectAndGetWeatherData(String endpoint) throws IOException {
        URL url = new URL(endpoint);
        HttpURLConnection hcon = (HttpURLConnection) url.openConnection();
        Log.i(TAG, "Connection to " + endpoint);

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


    public static String convertDataToString(String endpoint) throws IOException {
        return new String(connectAndGetWeatherData(endpoint));
    }


    public static String generateImageURL(String imageCode) {
        return "http://openweathermap.org/img/w/" + imageCode + ".png";
    }

}
