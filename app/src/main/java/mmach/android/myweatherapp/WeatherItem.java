package mmach.android.myweatherapp;

import android.graphics.Bitmap;

/**
 * Created by Mike on 3/20/2016.
 */
public class WeatherItem {
    private String weather;
    private String highTemperature;
    private String lowTemperature;
    private String location;
    private String imgURL;
    private Bitmap weatherIcon;

    public Bitmap getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(Bitmap weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getHighTemperature() {
        return highTemperature;
    }

    public void setHighTemperature(String highTemperature) {
        this.highTemperature = highTemperature;
    }

    public String getLowTemperature() {
        return lowTemperature;
    }

    public void setLowTemperature(String lowTemperature) {
        this.lowTemperature = lowTemperature;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
