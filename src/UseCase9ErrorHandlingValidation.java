/**
 * UseCase9ErrorHandlingValidation
 *
 * This class demonstrates error handling and validation for booking requests.
 * Invalid room types or requests are detected early using custom exceptions.
 *
 * @author Neha
 * @version 9.0
 */

import java.util.*;

// Custom exception for invalid booking scenarios
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Room Inventory with validation
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public void validateAvailability(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No availability for room type: " + roomType);
        }
    }

    public void allocateRoom(String roomType) throws InvalidBookingException {
        validateAvailability(roomType);
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " available");
        }
        System.out.println();
    }
}

// Main application for UC9
public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App - Error Handling & Validation (v9.0)\n");

        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        // Simulated booking requests
        String[] requests = {"Single Room", "Suite Room", "Penthouse", "Single Room", "Suite Room"};

        for (String roomType : requests) {
            try {
                System.out.println("Processing booking request for: " + roomType);
                inventory.allocateRoom(roomType);
                System.out.println("Booking successful for: " + roomType + "\n");
            } catch (InvalidBookingException e) {
                System.out.println("Booking failed: " + e.getMessage() + "\n");
            }
        }

        // Display final inventory
        inventory.displayInventory();
        System.out.println("End of Error Handling Demonstration\n");
    }
}