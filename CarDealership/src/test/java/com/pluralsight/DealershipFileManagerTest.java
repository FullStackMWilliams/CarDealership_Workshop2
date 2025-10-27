package com.pluralsight;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DealershipFileManagerTest {

    private static final Path TEST_FILE = Path.of("test_inventory.csv");

    @BeforeEach
    void setup() throws IOException {
        // Make sure no leftovers from prior tests
        Files.deleteIfExists(TEST_FILE);
    }

    @AfterEach
    void cleanup() throws IOException {
        Files.deleteIfExists(TEST_FILE);
    }

    @Test
    void testGetDealership_ReadsVehiclesCorrectly() throws IOException {
        // Arrange: create a valid dealership file
        String data = """
            Springfield Auto|123 Main St|555-1234
            1001|2020|Toyota|Camry|Car|Blue|12345|25999.99
            1002|2021|Honda|Civic|Car|Red|9876|21999.99
            """;
        Files.writeString(TEST_FILE, data);

        // Act
        DealershipFileManager manager = new DealershipFileManager(TEST_FILE.toString());
        Dealership dealership = manager.getDealership();

        // Assert
        assertEquals("Springfield Auto", dealership.getName());
        assertEquals("123 Main St", dealership.getAddress());
        assertEquals("555-1234", dealership.getPhone());

        List<Vehicle> vehicles = dealership.getAllVehicles();
        assertEquals(2, vehicles.size());

        Vehicle first = vehicles.get(0);
        assertEquals("Toyota", first.getMake());
        assertEquals("Camry", first.getModel());
        assertEquals("Blue", first.getColor());
        assertEquals(12345, first.getOdometer());
        assertEquals(25999.99, first.getPrice(), 0.01);
    }

    @Test
    void testSaveDealership_WritesFileCorrectly() throws IOException {
        // Arrange
        Dealership dealership = new Dealership("Test Motors", "456 Elm St", "555-7890");
        dealership.addVehicle(new Vehicle(3001, 2019, "BMW", "X3", "SUV", "White", 15000, 39999.99));
        dealership.addVehicle(new Vehicle(4002, 2022, "Tesla", "Model 3", "Car", "Silver", 8000, 45999.99));

        DealershipFileManager manager = new DealershipFileManager(TEST_FILE.toString());

        // Act
        manager.saveDealership(dealership);

        // Assert
        assertTrue(Files.exists(TEST_FILE));

        String fileContent = Files.readString(TEST_FILE);
        assertTrue(fileContent.contains("Test Motors|456 Elm St|555-7890"));
        assertTrue(fileContent.contains("BMW|X3|SUV|White"));
        assertTrue(fileContent.contains("Tesla|Model 3|Car|Silver"));
    }

    @Test
    void testGetDealership_ReturnsDefaultIfFileMissing() throws IOException {
        // Arrange
        Files.deleteIfExists(TEST_FILE);

        DealershipFileManager manager = new DealershipFileManager(TEST_FILE.toString());

        // Act
        Dealership dealership = manager.getDealership();

        // Assert
        assertNotNull(dealership);
        assertEquals("Your Dealership", dealership.getName());
    }

    @Test
    void testGetDealership_EmptyFileReturnsDefault() throws IOException {
        // Arrange: create an empty file
        Files.writeString(TEST_FILE, "");

        DealershipFileManager manager = new DealershipFileManager(TEST_FILE.toString());

        // Act
        Dealership dealership = manager.getDealership();

        // Assert
        assertEquals("Your Dealership", dealership.getName());
        assertEquals(0, dealership.getAllVehicles().size());
    }

    @Test
    void testGetDealership_HandlesCorruptDataGracefully() throws IOException {
        // Arrange: bad data line (missing fields)
        String data = """
            Auto Galaxy|42 Orbit Rd|555-0000
            9999|2021|Ford|F150|Truck|Black|badData|moreBadData
            """;
        Files.writeString(TEST_FILE, data);

        DealershipFileManager manager = new DealershipFileManager(TEST_FILE.toString());

        // Act
        Dealership dealership = manager.getDealership();

        // Assert: should still create dealership and skip corrupt vehicles
        assertEquals("Auto Galaxy", dealership.getName());
        assertTrue(dealership.getAllVehicles().isEmpty() || dealership.getAllVehicles().size() == 0);
    }
}
