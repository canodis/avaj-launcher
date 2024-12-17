package com.avaj.aircraft;
import java.util.Map;

import com.avaj.weather.WeatherTower;
import com.avaj.simulator.Logger;

public class Helicopter extends Aircraft {
    public Helicopter(long p_id, String p_name, Coordinates p_coordinates) {
        super(p_id, p_name, p_coordinates);
    }

    @Override
    public void updateConditions() {
        String weather = this.weatherTower.getWeather(this.coordinates);
        switch (weather) {
            case "SUN" -> {
                this.coordinates = new Coordinates(
                    coordinates.getLongitude() + 10,
                    coordinates.getLatitude(),
                    coordinates.getHeight() + 2
                );

                String message = weatherMessages.get(weather);
                Logger.getInstance().log("Helicopter#" + this.name + "(" + this.id + "): " + message);
            }
            case "RAIN" -> {
                this.coordinates = new Coordinates(
                    coordinates.getLongitude() + 5,
                    coordinates.getLatitude(),
                    coordinates.getHeight()
                );

                String message = weatherMessages.get(weather);
                Logger.getInstance().log("Helicopter#" + this.name + "(" + this.id + "): " + message);
            }
            case "FOG" -> {
                this.coordinates = new Coordinates(
                    coordinates.getLongitude() + 1,
                    coordinates.getLatitude(),
                    coordinates.getHeight()
                );

                String message = weatherMessages.get(weather);
                Logger.getInstance().log("Helicopter#" + this.name + "(" + this.id + "): " + message);
            }
            case "SNOW" -> {
                this.coordinates = new Coordinates(
                    coordinates.getLongitude(),
                    coordinates.getLatitude(),
                    coordinates.getHeight() - 12
                );

                String message = weatherMessages.get(weather);
                Logger.getInstance().log("Helicopter#" + this.name + "(" + this.id + "): " + message);
            }
        }

        if (this.coordinates.getHeight() <= 0) {
            Logger.getInstance().log("Helicopter#" + this.name + "(" + this.id + ") landing.");
            this.weatherTower.unregister(this);
        }
    }

    @Override
    public void registerTower(WeatherTower weatherTower) {
        this.weatherTower = weatherTower;
        this.weatherTower.register(this);
        Logger.getInstance().log("Tower says: Helicopter#" + this.name + "(" + this.id + ") registered to weather tower.");
    }

    private final Map<String, String> weatherMessages = Map.of(
        "SUN", "It's so sunny, I feel like I'm in a flying sauna!",
        "RAIN", "Rain again?! Where's my umbrella?",
        "FOG", "Fog so thick, I might accidentally land on someone's roof!",
        "SNOW", "Blizzard detected! I think my blades are frozen!"
    );
}
