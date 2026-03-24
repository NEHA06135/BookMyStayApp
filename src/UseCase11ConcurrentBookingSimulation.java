/**
 * UseCase11ConcurrentBookingSimulation
 *
 * This class demonstrates thread-safe booking simulation in a multi-user environment.
 * Synchronized blocks ensure that shared resources like inventory and booking queue
 * are updated safely under concurrent access.
 *
 * @author Neha
 * @version 11.0
 */

import java.util.*;
import java.util.concurrent.*;

// Reservation class
class Reservation {
    private String guestName;
    private String roomType;
    private String roomID;

    public Reservation(String guestName, String roomType, String roomID) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomID = roomID;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public String getRoomID() { return roomID; }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType + " | Room ID: " + roomID);
    }
}

// Thread-safe Inventory
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    // Synchronized allocation to prevent race conditions
    public synchronized boolean allocateRoom(String roomType) {
        int available = inventory.getOrDefault(roomType, 0);
        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public synchronized void displayInventory() {
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " available");
        }
        System.out.println();
    }
}

// Booking Processor (Runnable for threading)
class BookingTask implements Runnable {
    private String guestName;
    private String roomType;
    private RoomInventory inventory;

    public BookingTask(String guestName, String roomType, RoomInventory inventory) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        synchronized (inventory) {
            System.out.println(Thread.currentThread().getName() + " processing booking for: " + guestName);
            if (inventory.allocateRoom(roomType)) {
                System.out.println("Booking successful: " + guestName + " -> " + roomType);
            } else {
                System.out.println("Booking failed (no availability): " + guestName + " -> " + roomType);
            }
        }
    }
}

// Main class
public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Welcome to Book My Stay App - Concurrent Booking Simulation (v11.0)\n");

        RoomInventory inventory = new RoomInventory();
        inventory.displayInventory();

        // Simulate multiple guest booking requests
        String[][] bookingRequests = {
                {"Alice", "Single Room"},
                {"Bob", "Suite Room"},
                {"Charlie", "Double Room"},
                {"David", "Single Room"},
                {"Eve", "Suite Room"},
                {"Frank", "Double Room"}
        };

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (String[] req : bookingRequests) {
            executor.submit(new BookingTask(req[0], req[1], inventory));
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("\nFinal Inventory after concurrent bookings:");
        inventory.displayInventory();

        System.out.println("End of Concurrent Booking Simulation\n");
    }
}