package mmach.android.myweatherapp;
//http://openweathermap.org/api

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Mike on 3/20/2016.
 */
public class WeatherFragment extends Fragment {
    private static final String TAG = "WeatherFragment";
    private WeatherItem mWeatherItem;

    private ImageView mImageView;
    private TextView mWeatherTextView;
    private TextView mLocationTextView;
    private TextView mHighsTextView;
    private TextView mLowsTextView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        new GenerateWeatherData().execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.weather_fragment_layout, container, false);
        mWeatherTextView = (TextView) v.findViewById(R.id.weatherTextView);
        mLocationTextView = (TextView) v.findViewById(R.id.locationTextView);
        mHighsTextView = (TextView) v.findViewById(R.id.highTemp);
        mLowsTextView = (TextView) v.findViewById(R.id.lowTemp);
        mImageView = (ImageView) v.findViewById(R.id.iconImageView);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.weather_fragment_layout, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_button:
                new GenerateWeatherData().execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateWeather() {
        mWeatherTextView.setText(mWeatherItem.getWeather());
        mLocationTextView.setText(mWeatherItem.getLocation());
        mHighsTextView.setText(mWeatherItem.getHighTemperature());
        mLowsTextView.setText(mWeatherItem.getLowTemperature());
        mImageView.setImageBitmap(mWeatherItem.getWeatherIcon());
    }


    private class GenerateWeatherData extends AsyncTask<Void, Void, WeatherItem> {

        @Override
        protected WeatherItem doInBackground(Void... params) {
            return GetMyWeather.generateWeatherData();
        }

        @Override
        protected void onPostExecute(WeatherItem weatherItem) {
            mWeatherItem = weatherItem;
            updateWeather();
            new DownloadWeatherImage().execute();
        }
    }

    private class DownloadWeatherImage extends AsyncTask<Void, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(Void... params) {
            return getIconImage();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mWeatherItem.setWeatherIcon(bitmap);
            updateWeather();
        }


        private Bitmap getIconImage() {
            Bitmap mBitmap = null;
            try {
                URL url = new URL(mWeatherItem.getImgURL());
                HttpURLConnection hcon = (HttpURLConnection) url.openConnection();
                InputStream in = hcon.getInputStream();
                mBitmap = BitmapFactory.decodeStream(in);
                hcon.disconnect();
            } catch (IOException e) {
                Log.d(TAG, "getIconImage: " + e);
            }
            return mBitmap;
        }
    }
}
