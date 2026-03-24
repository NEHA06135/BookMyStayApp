/**
 * UseCase4RoomSearch
 *
 * This class demonstrates a read-only search operation for available rooms.
 * It retrieves inventory data from RoomInventory without modifying it,
 * and displays room details for only available rooms.
 *
 * @author Neha
 * @version 4.0
 */

import java.util.HashMap;
import java.util.Map;

// Reusing Room and RoomInventory from previous use cases

// Room class (can be same as UC2)
abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Number of Beds: " + beds);
        System.out.println("Price: $" + price);
    }

    public String getRoomType() {
        return roomType;
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 50.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 90.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 150.0);
    }
}

// Centralized Inventory
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 4);  // Reflect previous updates
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 1);
    }

    // Read-only access
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Display current inventory (optional)
    public void displayInventory() {
        System.out.println("===== Room Inventory =====");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " available");
        }
        System.out.println("==========================\n");
    }
}

// Main search application
public class UseCase4RoomSearch {

    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App - Room Search (v4.0)\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize rooms
        Room[] rooms = { new SingleRoom(), new DoubleRoom(), new SuiteRoom() };

        System.out.println("Available Rooms:\n");

        // Search logic: display only rooms with availability > 0
        for (Room room : rooms) {
            int available = inventory.getAvailability(room.getRoomType());
            if (available > 0) {
                room.displayRoomDetails();
                System.out.println("Available: " + available + "\n");
            }
        }

        System.out.println("End of Search Results\n");
    }
}