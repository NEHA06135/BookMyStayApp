/**
 * UseCase12DataPersistenceRecovery
 *
 * This class demonstrates persistence and system recovery using file-based
 * serialization and deserialization for bookings and inventory.
 *
 * @author Neha
 * @version 12.0
 */

import java.io.*;
import java.util.*;

// Serializable Room Inventory
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public void allocateRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available > 0) {
            inventory.put(roomType, available - 1);
        }
    }

    public void incrementRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " available");
        }
        System.out.println();
    }
}

// Serializable Reservation
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    private String guestName;
    private String roomType;
    private String roomID;

    public Reservation(String guestName, String roomType, String roomID) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomID = roomID;
    }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType + " | Room ID: " + roomID);
    }
}

// Persistence service
class PersistenceService {
    private static final String FILE_NAME = "bookmystay_state.ser";

    public static void saveState(RoomInventory inventory, List<Reservation> bookings) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inventory);
            oos.writeObject(bookings);
            System.out.println("System state saved successfully.\n");
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static Object[] loadState() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No previous state found. Starting fresh.\n");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            RoomInventory inventory = (RoomInventory) ois.readObject();
            List<Reservation> bookings = (List<Reservation>) ois.readObject();
            System.out.println("System state restored successfully.\n");
            return new Object[]{inventory, bookings};
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading state: " + e.getMessage());
            return null;
        }
    }
}

// Main application for UC12
public class UseCase12DataPersistenceRecovery {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App - Data Persistence & System Recovery (v12.0)\n");

        Object[] loadedState = PersistenceService.loadState();
        RoomInventory inventory;
        List<Reservation> bookings;

        if (loadedState != null) {
            inventory = (RoomInventory) loadedState[0];
            bookings = (List<Reservation>) loadedState[1];
        } else {
            inventory = new RoomInventory();
            bookings = new ArrayList<>();
        }

        // Simulate new bookings
        Reservation r1 = new Reservation("Alice", "Single Room", "SI-101");
        Reservation r2 = new Reservation("Bob", "Double Room", "DO-102");

        bookings.add(r1);
        bookings.add(r2);

        inventory.allocateRoom("Single Room");
        inventory.allocateRoom("Double Room");

        System.out.println("Bookings after simulation:");
        for (Reservation r : bookings) {
            r.displayReservation();
        }
        System.out.println();
        inventory.displayInventory();

        // Save state for next run
        PersistenceService.saveState(inventory, bookings);

        System.out.println("End of Data Persistence Demonstration\n");
    }
}