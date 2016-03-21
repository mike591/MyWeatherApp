package mmach.android.myweatherapp;
//http://openweathermap.org/api

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by Mike on 3/20/2016.
 */
public class WeatherFragment extends Fragment {
    private static final String TAG = "WeatherFragment";

    private ImageView mImageView;
    private TextView mWeatherTextView;
    private TextView mLocationTextView;
    private TextView mHighsTextView;
    private TextView mLowsTextView;

    private WeatherItem mWeatherItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GenerateWeatherData().execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.weather_fragment, container, false);
        mWeatherTextView = (TextView) v.findViewById(R.id.weatherTextView);
        mLocationTextView = (TextView) v.findViewById(R.id.locationTextView);
        mHighsTextView = (TextView) v.findViewById(R.id.highTemp);
        mLowsTextView = (TextView) v.findViewById(R.id.lowTemp);

        return v;
    }

    public void updateWeather() {
        mWeatherTextView.setText(mWeatherItem.getWeather());
        mLocationTextView.setText(mWeatherItem.getLocation());
        mHighsTextView.setText(mWeatherItem.getHighTemperature());
        mLowsTextView.setText(mWeatherItem.getLowTemperature());
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
        }
    }
}
