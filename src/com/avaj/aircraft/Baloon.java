package com.avaj.aircraft;
import com.avaj.weather.WeatherTower;
import com.avaj.simulator.Logger;
import java.util.Map;

public class Baloon extends Aircraft {
	public Baloon(long p_id, String p_name, Coordinates p_coordinates) {
		super(p_id, p_name, p_coordinates);
	}

	@Override
	public void updateConditions() {
		String weather = this.weatherTower.getWeather(this.coordinates);
		switch (weather) {
			case "SUN" -> {
				this.coordinates = new Coordinates(
					coordinates.getLongitude() + 2,
					coordinates.getLatitude(),
					coordinates.getHeight() + 4
				);

				String message = weatherMessages.get(weather);
				Logger.getInstance().log("Baloon#" + this.name + "(" + this.id + "): " + message);
			}
			case "RAIN" -> {
				this.coordinates = new Coordinates(
					coordinates.getLongitude(),
					coordinates.getLatitude(),
					coordinates.getHeight() - 5
				);

				String message = weatherMessages.get(weather);
				Logger.getInstance().log("Baloon#" + this.name + "(" + this.id + "): " + message);
			}
			case "FOG" -> {
				this.coordinates = new Coordinates(
					coordinates.getLongitude(),
					coordinates.getLatitude(),
					coordinates.getHeight() - 3
				);

				String message = weatherMessages.get(weather);
				Logger.getInstance().log("Baloon#" + this.name + "(" + this.id + "): " + message);
			}
			case "SNOW" -> {
				this.coordinates = new Coordinates(
					coordinates.getLongitude(),
					coordinates.getLatitude(),
					coordinates.getHeight() - 15
				);

				String message = weatherMessages.get(weather);
				Logger.getInstance().log("Baloon#" + this.name + "(" + this.id + "): " + message);
			}
		}

		if (this.coordinates.getHeight() <= 0) {
			Logger.getInstance().log("Baloon#" + this.name + "(" + this.id + ") landing.");
			this.weatherTower.unregister(this);
		}
	}

	@Override
	public void registerTower(WeatherTower weatherTower) {
		this.weatherTower = weatherTower;
		this.weatherTower.register(this);
		Logger.getInstance().log("Tower says: Baloon#" + this.name + "(" + this.id + ") registered to weather tower.");
	}

	private static final Map<String, String> weatherMessages = Map.of(
		"SUN", "I'm a balloon, not a beach ball! Stop with the heat.",
		"RAIN", "This rain is ruining my vibe... and my fabric.",
		"FOG", "Is this a horror movie? I can't see anything in this fog!",
		"SNOW", "Snow and balloons don't mix! I'm freezing up here."
	);
}
