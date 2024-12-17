package com.avaj.weather;

import com.avaj.aircraft.Coordinates;
import com.avaj.weather.Tower;

public class WeatherTower extends Tower {
    public String getWeather(Coordinates p_coordiantes) {
        return WeatherProvider.getInstance().getCurrentWeather(p_coordiantes);
    }

    public void changeWeather() {
        this.conditionChanged();
    }
}
