package com.avaj.aircraft;
import java.util.Map;

import com.avaj.weather.WeatherTower;
import com.avaj.simulator.Logger;

public class JetPlane extends Aircraft {
    public JetPlane(long p_id, String p_name, Coordinates p_coordinates) {
        super(p_id, p_name, p_coordinates);
    }

    @Override
    public void updateConditions() {
        String weather = this.weatherTower.getWeather(this.coordinates);
        switch (weather) {
            case "SUN" -> {
                this.coordinates = new Coordinates(
                    coordinates.getLongitude(),
                    coordinates.getLatitude() + 10,
                    coordinates.getHeight() + 2
                );

                String message = weatherMessages.get(weather);
                Logger.getInstance().log("JetPlane#" + this.name + "(" + this.id + "): " + message);
            }
            case "RAIN" -> {
                this.coordinates = new Coordinates(
                    coordinates.getLongitude(),
                    coordinates.getLatitude() + 5,
                    coordinates.getHeight()
                );

                String message = weatherMessages.get(weather);
                Logger.getInstance().log("JetPlane#" + this.name + "(" + this.id + "): " + message);
            }
            case "FOG" -> {
                this.coordinates = new Coordinates(
                    coordinates.getLongitude(),
                    coordinates.getLatitude() + 1,
                    coordinates.getHeight()
                );

                String message = weatherMessages.get(weather);
                Logger.getInstance().log("JetPlane#" + this.name + "(" + this.id + "): " + message);
            }
            case "SNOW" -> {
                this.coordinates = new Coordinates(
                    coordinates.getLongitude(),
                    coordinates.getLatitude(),
                    coordinates.getHeight() - 7
                );

                String message = weatherMessages.get(weather);
                Logger.getInstance().log("JetPlane#" + this.name + "(" + this.id + "): " + message);
            }
        }

        if (this.coordinates.getHeight() <= 0) {
            Logger.getInstance().log("JetPlane#" + this.name + "(" + this.id + ") landing.");
            this.weatherTower.unregister(this);
        }
    }

    @Override
    public void registerTower(WeatherTower weatherTower) {
        this.weatherTower = weatherTower;
        this.weatherTower.register(this);
        Logger.getInstance().log("Tower says: JetPlane#" + this.name + "(" + this.id + ") registered to weather tower.");
    }

    private final Map<String, String> weatherMessages = Map.of(
            "SUN", "Clear skies! Time to break the sound barrier!",
            "RAIN", "Rain? No problem, I'm faster than the drops!",
            "FOG", "Foggy weather? Great, now I'm flying blind...",
            "SNOW", "Snowstorm ahead! Let's hope my wings don't freeze!"
        );
}
