package com.avaj.simulator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Logger
{
    private static final Logger Instance = new Logger();
    private static List<String> logs = new ArrayList<>();

    public static Logger getInstance() {
        return Instance;
    }

    public void log(String message) {
        logs.add(message);
    }

    public void writeToFile(String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            for (String log : logs) {
                fileWriter.write(log + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

}