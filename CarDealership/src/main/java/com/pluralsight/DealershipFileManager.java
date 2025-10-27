package com.pluralsight;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class DealershipFileManager {

    private final Path dataPath;

    public DealershipFileManager() {
        this("inventory.csv");
    }
    public DealershipFileManager(String filename) {
        this.dataPath = Path.of(filename);
    }

    public Dealership getDealership() {

        if (!Files.exists(dataPath)) {
            return new Dealership("Your Dealership", "123 Main st","888-888-8888");

        }

        try (BufferedReader br = Files.newBufferedReader(dataPath, StandardCharsets.UTF_8)) {
            String header = br.readLine();
            if (header == null || header.isBlank()) {
                return new Dealership("Your Dealership","Your address", "000-000-0000");
            }
            String[] d =  header.split("\\|");
            String name = d.length > 0 ? d[0] : "Your Dealership";
            String address = d.length > 1 ? d[1] : "123 Main st";
            String phone = d.length > 2 ? d[2] : "000-000-0000";

            Dealership dealership = new Dealership(name, address, phone);

            String line;
            while ((line = br.readLine()) != null ) {
                if (line.isBlank()) continue;
                String [] p = line.split("\\|");
                if (p.length < 8) continue;


                int vin = parseInt(p[0], 0);
                int year = parseInt(p[1], 0);
                String make = p[2];
                String model = p[3];
                String type = p[4];
                String color = p[5];
                long odometer = parseLong(p[6], 0);
                double price = parseDouble(p[7], 0);

                dealership.addVehicle((new Vehicle(vin, year, make, model, type, color, odometer, price)));
                }

                return dealership;
            } catch (IOException e) {
            e.printStackTrace();
            return new Dealership("Your dealership", "123 Main st", "000-000-0000");

        }
    }

    public void saveDealership(Dealership d) {
        try (BufferedWriter bw = Files.newBufferedWriter(dataPath, StandardCharsets.UTF_8)) {


            bw.write(d.getName() + "|" + d.getAddress() + "|" + d.getPhone());
            bw.newLine();

            for (Vehicle vehicle : d.getAllVehicles()) {
                bw.write(vehicle.toPipe());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Failed to save dealership file: " + e.getMessage());
        }
    }

    private static int parseInt ( String s, int def) {
        try {
            return Integer.parseInt(s.trim());
        } catch (Exception e) {
            return def;
        }
    }

    private static long parseLong(String s, long def) {
        try {
            return Long.parseLong(s.trim());
        } catch (Exception e) {
            return def;
        }
    }
    private static double parseDouble(String  s, double def) {
        try {
            return Double.parseDouble(s.trim());
        } catch (Exception e) {
            return def;
        }
    }
}