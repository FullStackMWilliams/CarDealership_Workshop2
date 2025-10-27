# CarDealership 🚗
**Built by Markus Williams | Year Up United Software Development Track**

*CarDealership* — is a professional-grade console app that simulates a car dealership’s inventory system. It focuses on clean OOP design, file I/O, validation, and user-friendly workflows. This version adds **data integrity**, **duplicate VIN protection**, and **summary reporting** so it behaves like real dealership software.

---

## 🚀 Features

### 🧩 Core
- Load & save inventory from `inventory.csv` (pipe-delimited)
- Add, remove, and search vehicles
- Powerful filters:
  - Price range
  - Make / Model
  - Year range
  - Color
  - Mileage
  - Type (car/truck/suv/van)

### 🧠 Data Integrity
- **Invalid Data Rejection:** Malformed lines are skipped safely (no crashes, no silent corruption)
- **Duplicate VIN Detection:** VINs must be unique; duplicates are ignored or replaceable via prompt
- **Interactive Recovery:** UI can prompt to fix invalid records
- **Fail-Safe Defaults:** Missing/blank file auto-creates a default dealership

### 📊 Summary Reporting
On every file load, the app prints a clear summary:

# 📊 Dealership Data Summary
✅ Vehicles loaded: 38
⚠️ Skipped bad lines: 2
🚫 Duplicates ignored: 1

## 📁 Total records processed: 41


---

# 🧱 Tech Stack
| Component | Purpose |
| --- | --- |
| Java 17+ | Core language |
| IntelliJ IDEA | Recommended IDE |
| JUnit 5 | Unit tests |
| Git/GitHub | Version control |

---

# 🗂️ Project Structure

```
├── src/
│ ├── main/java/com/pluralsight/
│ │ ├── Dealership.java
│ │ ├── DealershipFileManager.java
│ │ ├── UserInterface.java
│ │ ├── Vehicle.java
│ │ └── Program.java (or Main.java)
│ └── test/java/com/pluralsight/
│ ├── VehicleTest.java
│ └── DealershipFileManagerTest.java
├── inventory.csv
└── README.md

````

---

# ▶️ Getting Started

### Requirements
- Java **17+**
- IntelliJ IDEA (Community or Ultimate)

### Run Steps
1. **Clone:**
   ```bash
   git clone https://github.com/FullStackMWilliams/Cafe_Java.git

2. Open the project in IntelliJ IDEA.

3. Let IntelliJ finish indexing.

4. Open Program.java (or Main.java) and click Run ▶️.

5. Use the terminal menu to browse, add, or remove vehicles.

---

# 📄 CSV Format (pipe-delimited)

Header + vehicles (example):
```
Springfield Auto|123 Main St|555-1234
1001|2020|Toyota|Camry|Car|Blue|12345|25999.99
1002|2021|Honda|Civic|Car|Red|9876|21999.99
```

Field order (8 fields per vehicle):

1. VIN (int, unique)

2. Year (int)

3. Make (string)

4. Model (string)

5. Type (string: car/truck/suv/van/…)

6. Color (string)

7. Odometer (long)

8. Price (double)

## ❗ Keep the first line as the dealership header: Name|Address|Phone.

---

# 🧮 Key Implementation Details
## Duplicate VIN Protection (Dealership)

```java
public boolean addVehicle(Vehicle vehicle) {
    for (Vehicle existing : inventory) {
        if (existing.getVin() == vehicle.getVin()) {
            System.out.println("⚠️ A vehicle with VIN " + vehicle.getVin() + " already exists in inventory.");
            return false;
        }
    }
    inventory.add(vehicle);
    return true;
}

public boolean removeVehicleByVin(int vin) {
    return inventory.removeIf(v -> v.getVin() == vin);
}
```

# Safe File Loading with Counters (FileManager)

```java
// increment vehiclesSkipped++ when invalid;
// duplicateCount++ when VIN repeats;
// vehiclesLoaded++ after a successful add.
if (!isValidVehicleData(p)) { vehiclesSkipped++; continue; }
if (dealership.getAllVehicles().stream().anyMatch(v -> v.getVin() == vin)) {
    duplicateCount++; continue;
}
dealership.addVehicle(new Vehicle(vin, year, make, model, type, color, odometer, price));
vehiclesLoaded++;
```
# Summary Report (FileManager)

```java
public void printSummaryReport() {
    System.out.println("\n📊 Dealership Data Summary");
    System.out.println("--------------------------");
    System.out.println("✅ Vehicles loaded:   " + vehiclesLoaded);
    System.out.println("⚠️ Skipped bad lines: " + vehiclesSkipped);
    System.out.println("🚫 Duplicates ignored: " + duplicateCount);
    int total = vehiclesLoaded + vehiclesSkipped + duplicateCount;
    System.out.println("--------------------------");
    System.out.println("📁 Total records processed: " + total + "\n");
}
```

---

## 🧪 Testing
IntelliJ (Library approach)

1. File → Project Structure → Libraries → + → From Maven

2. Add: org.junit.jupiter:junit-jupiter:5.9.3

3. Apply → OK.

Run Tests

- Right-click test/java → Run ‘All Tests’

Included tests:

- VehicleTest — getters, toPipe(), fromPipe(), equals/hashCode, toString()

- DealershipFileManagerTest — save/load, missing/empty file, invalid lines, duplicates

---

# 🧠 Troubleshooting
Problem                        |          Cause                  |                 Solution
--
“Cannot resolve symbol Test”	 |   JUnit not on classpath	       |    Add JUnit via Project Structure → Libraries
--
Vehicles not loading	         |   Bad CSV format	               |    Check 8 fields per line and header present
--
Duplicate VIN blocked	         |   Intended behavior	           |    Replace via prompt or remove existing VIN first
--
Summary always zero	           |   Counters not wired	           |    Ensure counters are incremented in getDealership()
--


