package mmach.android.myweatherapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Mike on 3/30/2016.
 */
public class ForecastFragment extends Fragment {
    private WeatherItem[] mWeatherItems;

    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.forecast_fragment_layout, container, false);

        mImageView1 = (ImageView) v.findViewById(R.id.forecast_1);
        mImageView2 = (ImageView) v.findViewById(R.id.forecast_2);
        mImageView3 = (ImageView) v.findViewById(R.id.forecast_3);

        return v;
    }
}
