package com.pluralsight;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VehicleTest {

    @Test
    void testVehicleGettersAndPipe() {
        Vehicle v = new Vehicle(1001, 2020, "Toyota", "Camry", "Car", "Blue", 12345, 25999.99);

        assertEquals(1001, v.getVin());
        assertEquals(2020, v.getYear());
        assertEquals("Toyota", v.getMake());
        assertEquals("Camry", v.getModel());
        assertEquals("Car", v.getType());
        assertEquals("Blue", v.getColor());
        assertEquals(12345, v.getOdometer());
        assertEquals(25999.99, v.getPrice(), 0.001);

        String expected = "1001|2020|Toyota|Camry|Car|Blue|12345|25999.99";
        assertEquals(expected, v.toPipe());
    }

    @Test
    void testToStringOutput() {
        Vehicle v = new Vehicle(5555, 2023, "Honda", "Civic", "Car", "Red", 5000, 29999.50);
        String output = v.toString();

        assertTrue(output.contains("Honda"));
        assertTrue(output.contains("Civic"));
        assertTrue(output.contains("Red"));
    }

    @Test
    void testSettersUpdateValues() {
        Vehicle v = new Vehicle(2002, 2018, "Ford", "Fusion", "Car", "Gray", 20000, 19999.99);

        v.setColor("Black");
        v.setOdometer(25000);
        v.setPrice(17999.50);

        assertEquals("Black", v.getColor());
        assertEquals(25000, v.getOdometer());
        assertEquals(17999.50, v.getPrice(), 0.001);
    }

    @Test
    void testEqualsAndHashCode() {
        Vehicle v1 = new Vehicle(3001, 2019, "BMW", "X3", "SUV", "White", 15000, 39999.99);
        Vehicle v2 = new Vehicle(3001, 2019, "BMW", "X3", "SUV", "White", 15000, 39999.99);

        assertEquals(v1, v2);
        assertEquals(v1.hashCode(), v2.hashCode());
    }

    @Test
    void testFromPipeParsing() {
        String pipeData = "4001|2022|Tesla|Model 3|Car|Silver|8000|45999.99";
        Vehicle v = Vehicle.fromPipe(pipeData);

        assertEquals(4001, v.getVin());
        assertEquals(2022, v.getYear());
        assertEquals("Tesla", v.getMake());
        assertEquals("Model 3", v.getModel());
        assertEquals("Car", v.getType());
        assertEquals("Silver", v.getColor());
        assertEquals(8000, v.getOdometer());
        assertEquals(45999.99, v.getPrice(), 0.001);
    }
}
