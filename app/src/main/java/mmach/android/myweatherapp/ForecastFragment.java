package mmach.android.myweatherapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Mike on 3/30/2016.
 */
public class ForecastFragment extends Fragment {
    private static final String TAG = "ForecastFragment";
    private ArrayList<WeatherItem> mWeatherItems;

    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.forecast_fragment_layout, container, false);

        mImageView1 = (ImageView) v.findViewById(R.id.forecast_1);
        mImageView2 = (ImageView) v.findViewById(R.id.forecast_2);
        mImageView3 = (ImageView) v.findViewById(R.id.forecast_3);

        refresh();
        return v;
    }

    public void refresh() {
        new generateForcastData().execute();
    }

    public void updateForcastImages() {
        mImageView1.setImageBitmap(mWeatherItems.get(0).getWeatherIcon());
        mImageView2.setImageBitmap(mWeatherItems.get(1).getWeatherIcon());
        mImageView3.setImageBitmap(mWeatherItems.get(2).getWeatherIcon());

    }

    public class generateForcastData extends AsyncTask<Void, Void, ArrayList<WeatherItem>> {
        private ArrayList<WeatherItem> asyncWeatherItems;

        @Override
        protected ArrayList<WeatherItem> doInBackground(Void... params) {
            asyncWeatherItems = GetMyWeather.generateForcastData();
            Log.i(TAG, "doInBackground: " + asyncWeatherItems.get(0).getImgURL());
            Log.i(TAG, "doInBackground: " + asyncWeatherItems.get(1).getImgURL());
            Log.i(TAG, "doInBackground: " + asyncWeatherItems.get(2).getImgURL());
            return asyncWeatherItems;
        }

        @Override
        protected void onPostExecute(ArrayList<WeatherItem> weatherItems) {
            mWeatherItems = weatherItems;
            Log.i(TAG, "onPostExecute: " + mWeatherItems.get(0).getImgURL());
            Log.i(TAG, "onPostExecute: " + mWeatherItems.get(1).getImgURL());
            Log.i(TAG, "onPostExecute: " + mWeatherItems.get(2).getImgURL());
            new downloadIconImages().execute();
        }
    }


    private class downloadIconImages extends AsyncTask<Void, Void, ArrayList<Bitmap>> {
        private ArrayList<Bitmap> mBitmaps = new ArrayList<>();

        @Override
        protected ArrayList<Bitmap> doInBackground(Void... params) {
            for (int i = 0; i < mWeatherItems.size(); i++) {
                Bitmap image = connectAndGetIconImages(mWeatherItems.get(i).getImgURL());
                Log.i(TAG, "mWeatherItems image url is: " + mWeatherItems.get(i).getImgURL());
                mBitmaps.add(image);
            }
            return mBitmaps;
        }

        public Bitmap connectAndGetIconImages(String con) {
            Bitmap mBitmap = null;
            try {
                URL url = new URL(con);
                HttpURLConnection hcon = (HttpURLConnection) url.openConnection();
                InputStream in = hcon.getInputStream();
                mBitmap = BitmapFactory.decodeStream(in);
                hcon.disconnect();

            } catch (MalformedURLException e) {
                Log.d(TAG, "connectAndGetIconImages: " + e);
            } catch (IOException e) {
                Log.d(TAG, "connectAndGetIconImages: " + e);
            }

            return mBitmap;
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> bitmaps) {
            for (int i = 0; i < bitmaps.size(); i++) {
                mWeatherItems.get(i).setWeatherIcon(bitmaps.get(i));
            }
            updateForcastImages();
        }
    }
}
