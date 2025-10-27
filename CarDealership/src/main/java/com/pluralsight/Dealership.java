package com.pluralsight;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Dealership {

    private String name;
    private String address;
    private String phone;
    private final List<Vehicle> inventory;

    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.inventory = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public void addVehicle(Vehicle vehicle) {
        inventory.add(vehicle);
    }

    public boolean removeVehicle(int vin) {
        return inventory.removeIf(vehicle -> vehicle.getVin() == vin);
    }

    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(inventory);
    }

    public List<Vehicle> getVehiclesByPrice(double min, double max) {
        return inventory.stream()
                .filter(vehicle -> vehicle.getPrice() >= min && vehicle.getPrice() <= max)
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        String m1 = make == null ? "" : make.toLowerCase(Locale.ROOT).trim();
        String m2 =  model == null ? "" : model.toLowerCase(Locale.ROOT).trim();
        return inventory.stream()
                .filter(vehicle ->
                        (m1.isEmpty() || vehicle.getMake().toLowerCase(Locale.ROOT).contains(m1)) &&
                                (m2.isEmpty() || vehicle.getModel().toLowerCase(Locale.ROOT).contains(m2)))
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehiclesByYear(int minYear, int maxYear) {
        return inventory.stream()
                .filter(vehicle -> vehicle.getYear() >= minYear && vehicle.getYear() <= maxYear)
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehiclesByColor(String color) {
        String c = color == null ? "" : color.toLowerCase(Locale.ROOT).trim();
        return inventory.stream()
                .filter(vehicle -> vehicle.getColor().toLowerCase(Locale.ROOT).contains(c))
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehiclesByMileage(long min, long max) {
        return inventory.stream()
                .filter(vehicle -> vehicle.getOdometer() >= min && vehicle.getOdometer() <= max)
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehiclesByType(String type) {
        String t = type == null ? "" : type.toLowerCase(Locale.ROOT).trim();
        return inventory.stream()
                .filter(vehicle -> vehicle.getType().toLowerCase(Locale.ROOT).contains(t))
                .collect(Collectors.toList());
    }
}
