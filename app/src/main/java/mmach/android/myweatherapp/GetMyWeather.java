package mmach.android.myweatherapp;

import android.net.Uri;
import android.util.Log;

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

    private static final Uri ENDPOINT = Uri.parse("api.openweathermap.org/data/2.5/weather")
            .buildUpon()
            .appendQueryParameter("q ", CITY + ',' + COUNTRY)
            .appendQueryParameter("units", FAHRENHEIT)
            .appendQueryParameter("AAPID", API_KEY)
            .build();

    public byte[] connectAndGetData() throws IOException {
        URL url = new URL(ENDPOINT.toString());
        HttpURLConnection hcon = (HttpURLConnection) url.openConnection();

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

    public WeatherItem generateWeatherData() {
        return null;
    }


}
