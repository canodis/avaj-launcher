package com.avaj.simulator;

import com.avaj.aircraft.AircraftFactory;
import com.avaj.aircraft.Coordinates;
import com.avaj.weather.WeatherTower;
import com.avaj.exceptions.InvalidInputException;
import com.avaj.exceptions.AircraftCreationException;
import com.avaj.aircraft.Flyable;
import com.avaj.simulator.Logger;
import java.io.*;
import java.util.*;

public class Simulator {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java Simulator <scenario file>");
            return;
        }

        String scenarioFile = args[0];

        try {
            List<String> lines = readScenarioFile(scenarioFile);

            if (lines.isEmpty()) {
                throw new InvalidInputException("Scenario file is empty.");
            }

            int simulationSteps = validateSimulationSteps(lines.get(0));
            WeatherTower weatherTower = new WeatherTower();

            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                Flyable aircraft = parseAircraft(line);
                aircraft.registerTower(weatherTower);
            }

            for (int step = 0; step < simulationSteps; step++) {
                weatherTower.changeWeather();
            }
            Logger.getInstance().writeToFile("simulation.txt");

        } catch (FileNotFoundException e) {
            System.out.println("Error: Scenario file not found.");
        } catch (IOException e) {
            System.out.println("Error reading scenario file: " + e.getMessage());
        } catch (InvalidInputException | AircraftCreationException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        }
    }

    private static List<String> readScenarioFile(String fileName) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim());
            }
        }
        return lines;
    }

    private static int validateSimulationSteps(String firstLine) throws InvalidInputException {
        try {
            int steps = Integer.parseInt(firstLine);
            if (steps <= 0) {
                throw new InvalidInputException("Invalid number of simulation steps: " + steps);
            }
            return steps;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid number format for simulation steps: " + firstLine);
        }
    }

    private static Flyable parseAircraft(String line) throws InvalidInputException, AircraftCreationException {
        String[] parts = line.split(" ");

        if (parts.length != 5) {
            throw new InvalidInputException("Invalid line format in scenario file: " + line);
        }

        String type = parts[0];
        String name = parts[1];
        try {
            int longitude = Integer.parseInt(parts[2]);
            int latitude = Integer.parseInt(parts[3]);
            int height = Integer.parseInt(parts[4]);

            Coordinates coordinates = new Coordinates(longitude, latitude, height);
            Flyable aircraft = AircraftFactory.getInstance().newAircraft(type, name, coordinates);

            if (aircraft == null) {
                throw new AircraftCreationException("Unknown aircraft type: " + type);
            }
            return aircraft;

        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid number format in aircraft data: " + line);
        }
    }
}
