package mmach.android.myweatherapp;
//http://openweathermap.org/api

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Mike on 3/20/2016.
 */
public class WeatherFragment extends Fragment {

    private ImageView mImageView;
    private TextView mWeatherTextView;
    private TextView mLocationTextView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather_fragment, container, false);
    }
}
