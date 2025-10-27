package com.pluralsight;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class UserInterface {
    private final Scanner in = new Scanner(System.in);
    private Dealership dealership;
    private DealershipFileManager fileManager;

    public void display() {
        init();
        boolean running = true;
        while (running) {
            printHeader();
            printMenu();
            System.out.print("👉 Choose option: ");
            String choice = in.nextLine().trim();

            switch (choice) {
                case "1" -> processPriceRange();
                case "2" -> processMakeModel();
                case "3" -> processYearRange();
                case "4" -> processColor();
                case "5" -> processMileageRange();
                case "6" -> processType();
                case "7" -> processAllVehicles();
                case "8" -> processAddVehicle();
                case "9" -> processRemoveVehicle();
                case "0" -> {
                    System.out.println("\n👋 Goodbye!");
                    running = false;
                }
                default -> System.out.println("⚠️  Invalid option. Try again.");
            }
            if (running) pause();
        }
    }

    // ============ init & rendering ============
    private void init() {
        fileManager = new DealershipFileManager(); // defaults to "inventory.csv"
        dealership = fileManager.getDealership();
    }

    private void printHeader() {
        System.out.println("\n=========================================================");
        System.out.printf("   🚗 %s  —  %s  —  %s%n", dealership.getName(), dealership.getAddress(), dealership.getPhone());
        System.out.println("=========================================================");
    }

    private void printMenu() {
        System.out.println("""
                1 - Find vehicles within a price range
                2 - Find vehicles by make / model
                3 - Find vehicles by year range
                4 - Find vehicles by color
                5 - Find vehicles by mileage range
                6 - Find vehicles by type (car, truck, SUV, van)
                7 - List ALL vehicles
                8 - Add a vehicle
                9 - Remove a vehicle
                0 - Quit
                """);
    }

    private void displayVehicles(List<Vehicle> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("⚠️  No matching vehicles found.");
            return;
        }
        System.out.println("VIN     YEAR  MAKE       MODEL        TYPE   COLOR          MILEAGE         PRICE");
        System.out.println("------  ----  ---------- ------------ ------ ---------- ------------  ------------");
        for (Vehicle v : list) {
            System.out.println(v);
        }
    }

    private void pause() {
        System.out.print("\nPress ENTER to continue...");
        in.nextLine();
    }

    // ============ menu handlers ============
    private void processAllVehicles() {
        displayVehicles(dealership.getAllVehicles());
    }

    private void processPriceRange() {
        double min = readDouble("Min price: ");
        double max = readDouble("Max price: ");
        displayVehicles(dealership.getVehiclesByPrice(min, max));
    }

    private void processMakeModel() {
        System.out.print("Make (blank = any): ");
        String make = in.nextLine();
        System.out.print("Model (blank = any): ");
        String model = in.nextLine();
        displayVehicles(dealership.getVehiclesByMakeModel(make, model));
    }

    private void processYearRange() {
        int min = readInt("Min year: ");
        int max = readInt("Max year: ");
        displayVehicles(dealership.getVehiclesByYear(min, max));
    }

    private void processColor() {
        System.out.print("Color: ");
        String color = in.nextLine();
        displayVehicles(dealership.getVehiclesByColor(color));
    }

    private void processMileageRange() {
        long min = readLong("Min mileage: ");
        long max = readLong("Max mileage: ");
        displayVehicles(dealership.getVehiclesByMileage(min, max));
    }

    private void processType() {
        System.out.print("Type (car, truck, suv, van, ...): ");
        String type = in.nextLine();
        displayVehicles(dealership.getVehiclesByType(type));
    }

    private void processAddVehicle() {
        System.out.println("\n➕ Add Vehicle");
        int vin = readInt("VIN (int): ");
        int year = readInt("Year: ");
        System.out.print("Make: ");
        String make = in.nextLine().trim();
        System.out.print("Model: ");
        String model = in.nextLine().trim();
        System.out.print("Type (car/truck/suv/van): ");
        String type = in.nextLine().trim().toLowerCase(Locale.ROOT);
        System.out.print("Color: ");
        String color = in.nextLine().trim();
        long mileage = readLong("Odometer (miles): ");
        double price = readDouble("Price: ");

        Vehicle v = new Vehicle(vin, year, make, model, type, color, mileage, price);
        dealership.addVehicle(v);
        fileManager.saveDealership(dealership);
        System.out.println("✅ Vehicle added and inventory saved.");
    }

    private void processRemoveVehicle() {
        System.out.println("\n🗑  Remove Vehicle");
        int vin = readInt("Enter VIN to remove: ");
        boolean removed = dealership.removeVehicle(vin);
        if (removed) {
            fileManager.saveDealership(dealership);
            System.out.println("✅ Vehicle removed and inventory saved.");
        } else {
            System.out.println("⚠️  No vehicle with that VIN was found.");
        }
    }

    // ============ input helpers ============
    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int i = Integer.parseInt(in.nextLine().trim());
                return i;
            } catch (Exception e) { System.out.println("  Please enter a whole number."); }
        }
    }

    private long readLong(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                long l = Long.parseLong(in.nextLine().trim());
                return l;
            } catch (Exception e) { System.out.println("  Please enter a whole number."); }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double d = Double.parseDouble(in.nextLine().trim());
                return d;
            } catch (Exception e) { System.out.println("  Please enter a number (e.g., 12345.67)."); }
        }
    }
}