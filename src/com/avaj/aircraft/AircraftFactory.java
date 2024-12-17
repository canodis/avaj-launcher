package com.avaj.aircraft;
import com.avaj.aircraft.Flyable;

public class AircraftFactory {
    private static final AircraftFactory Instance = new AircraftFactory();
    private static long idCounter = 0;

    public static AircraftFactory getInstance() {
        return Instance;
    }

    public Flyable newAircraft(String p_type, String p_name, Coordinates p_coordinates) {
        if (p_type.equalsIgnoreCase("Baloon")) {
            return new Baloon(nextId(), p_name, p_coordinates);
        } else if (p_type.equalsIgnoreCase("Helicopter")) {
            return new Helicopter(nextId(), p_name, p_coordinates);
        } else if (p_type.equalsIgnoreCase("JetPlane")) {
            return new JetPlane(nextId(), p_name, p_coordinates);
        } else {
            return null;
        }
    }

    private long nextId() {
        return ++idCounter;
    }
}
