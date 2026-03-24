/**
 * UseCase6RoomAllocationService
 *
 * This class demonstrates reservation confirmation and room allocation.
 * Booking requests are processed in FIFO order from a queue, rooms are assigned
 * unique IDs, and inventory is updated immediately.
 *
 * @author Neha
 * @version 6.0
 */

import java.util.*;

// Reservation represents a guest's booking request
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Centralized inventory with availability
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 4);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 1);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void decrementAvailability(String roomType) {
        if (isAvailable(roomType)) {
            inventory.put(roomType, inventory.get(roomType) - 1);
        }
    }

    public void displayInventory() {
        System.out.println("===== Room Inventory =====");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " available");
        }
        System.out.println("==========================\n");
    }
}

// Booking service handles allocation and ensures unique room IDs
class BookingService {
    private RoomInventory inventory;
    private HashMap<String, Set<String>> allocatedRooms; // RoomType -> Assigned IDs
    private Queue<Reservation> bookingQueue;

    public BookingService(RoomInventory inventory, Queue<Reservation> bookingQueue) {
        this.inventory = inventory;
        this.bookingQueue = bookingQueue;
        allocatedRooms = new HashMap<>();
        allocatedRooms.put("Single Room", new HashSet<>());
        allocatedRooms.put("Double Room", new HashSet<>());
        allocatedRooms.put("Suite Room", new HashSet<>());
    }

    // Generate a unique room ID
    private String generateRoomID(String roomType) {
        String id;
        do {
            id = roomType.substring(0, 2).toUpperCase() + "-" + (new Random().nextInt(900) + 100);
        } while (allocatedRooms.get(roomType).contains(id));
        return id;
    }

    // Process all requests in queue
    public void processBookings() {
        System.out.println("Processing booking requests...\n");

        while (!bookingQueue.isEmpty()) {
            Reservation res = bookingQueue.poll();
            String type = res.getRoomType();

            if (inventory.isAvailable(type)) {
                String roomID = generateRoomID(type);
                allocatedRooms.get(type).add(roomID);
                inventory.decrementAvailability(type);

                System.out.println("Reservation Confirmed:");
                System.out.println("Guest: " + res.getGuestName());
                System.out.println("Room Type: " + type);
                System.out.println("Assigned Room ID: " + roomID + "\n");
            } else {
                System.out.println("Reservation Failed (No Availability): " + res.getGuestName() + " - " + type + "\n");
            }
        }
    }
}

// Main application for UC6
public class UseCase6RoomAllocationService {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App - Room Allocation Service (v6.0)\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize booking queue
        Queue<Reservation> bookingQueue = new LinkedList<>();
        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Double Room"));
        bookingQueue.add(new Reservation("Charlie", "Suite Room"));
        bookingQueue.add(new Reservation("Diana", "Single Room"));
        bookingQueue.add(new Reservation("Eve", "Suite Room")); // Will fail, only 1 suite available

        // Display initial inventory
        inventory.displayInventory();

        // Process bookings
        BookingService service = new BookingService(inventory, bookingQueue);
        service.processBookings();

        // Display final inventory
        inventory.displayInventory();

        System.out.println("End of Room Allocation Demonstration\n");
    }
}