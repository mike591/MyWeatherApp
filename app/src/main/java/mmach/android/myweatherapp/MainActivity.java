package mmach.android.myweatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.weather_fragment_layout, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_button:
                WeatherFragment wFragment = (WeatherFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.weatherFragment);
                wFragment.refresh();
                ForecastFragment fFragment = (ForecastFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.forecastFragment);
                fFragment.refresh();
                Toast toast = Toast.makeText(getApplicationContext(),"Refresh", Toast.LENGTH_SHORT);
                toast.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
