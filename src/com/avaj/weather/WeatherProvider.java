package com.avaj.weather;

import java.util.Random;
import com.avaj.aircraft.Coordinates;

public class WeatherProvider {
    private static final WeatherProvider Instance = new WeatherProvider();
    private static final String[] weather = {"RAIN", "FOG", "SUN", "SNOW"};

    private WeatherProvider() {}

    public static WeatherProvider getInstance() {
        return Instance;
    }

    public String getCurrentWeather(Coordinates p_coordinates) {
        Random random = new Random();
        int index = (p_coordinates.getLongitude() + p_coordinates.getLatitude() + p_coordinates.getHeight() + random.nextInt(50)) % weather.length;
        return weather[index];
    }
}