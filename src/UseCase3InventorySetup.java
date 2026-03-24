/**
 * UseCase3InventorySetup
 *
 * This class demonstrates centralized room inventory management
 * using HashMap. Availability is stored and updated through
 * controlled methods.
 *
 * @author Neha
 * @version 3.1
 */

import java.util.HashMap;
import java.util.Map;

// Inventory manager for hotel rooms
class RoomInventory {
    private HashMap<String, Integer> inventory;

    // Constructor initializes room availability
    public RoomInventory() {
        inventory = new HashMap<>();
        // Initialize default availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get current availability for a room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability for a room type
    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        } else {
            System.out.println("Room type not found: " + roomType);
        }
    }

    // Display current inventory state
    public void displayInventory() {
        System.out.println("===== Current Room Inventory =====");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " available");
        }
        System.out.println("==================================\n");
    }
}

// Main application for UC3
public class UseCase3InventorySetup {

    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App - Inventory Setup (v3.1)\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Simulate updates
        System.out.println("Updating availability...");
        inventory.updateAvailability("Single Room", 4);
        inventory.updateAvailability("Suite Room", 1);

        // Display updated inventory
        inventory.displayInventory();

        // Attempt to update a non-existent room type
        inventory.updateAvailability("Presidential Suite", 1);

        System.out.println("End of Inventory Demonstration\n");
    }
}