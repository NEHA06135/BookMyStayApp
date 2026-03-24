/**
 * UseCase10BookingCancellation
 *
 * This class demonstrates safe booking cancellation and inventory rollback.
 * Recently allocated room IDs are tracked using a Stack (LIFO), ensuring
 * predictable and controlled state reversal.
 *
 * @author Neha
 * @version 10.0
 */

import java.util.*;

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

// Room Inventory with cancellation support
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public void incrementAvailability(String roomType) {
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

// Cancellation service
class CancellationService {
    private Stack<String> releasedRoomIDs; // Tracks cancelled rooms (LIFO)
    private List<Reservation> activeReservations;
    private RoomInventory inventory;

    public CancellationService(List<Reservation> activeReservations, RoomInventory inventory) {
        this.activeReservations = activeReservations;
        this.inventory = inventory;
        releasedRoomIDs = new Stack<>();
    }

    public void cancelBooking(String roomID) {
        Reservation toCancel = null;
        for (Reservation r : activeReservations) {
            if (r.getRoomID().equals(roomID)) {
                toCancel = r;
                break;
            }
        }

        if (toCancel == null) {
            System.out.println("Cancellation failed: No reservation with Room ID " + roomID + "\n");
            return;
        }

        // Remove reservation and rollback inventory
        activeReservations.remove(toCancel);
        inventory.incrementAvailability(toCancel.getRoomType());
        releasedRoomIDs.push(toCancel.getRoomID());

        System.out.println("Cancellation successful for Guest: " + toCancel.getGuestName() +
                " | Room ID: " + toCancel.getRoomID() + "\n");
    }

    public void displayReleasedRoomIDs() {
        System.out.println("Released Room IDs (most recent first): " + releasedRoomIDs);
    }
}

// Main application for UC10
public class UseCase10BookingCancellation {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App - Booking Cancellation & Rollback (v10.0)\n");

        RoomInventory inventory = new RoomInventory();

        // Active reservations
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation("Alice", "Single Room", "SI-123"));
        reservations.add(new Reservation("Bob", "Double Room", "DO-456"));
        reservations.add(new Reservation("Charlie", "Suite Room", "SU-789"));

        inventory.displayInventory();

        CancellationService cancellationService = new CancellationService(reservations, inventory);

        // Cancel a booking
        cancellationService.cancelBooking("DO-456"); // Bob
        cancellationService.cancelBooking("SU-999"); // Invalid
        cancellationService.cancelBooking("SI-123"); // Alice

        inventory.displayInventory();
        cancellationService.displayReleasedRoomIDs();

        System.out.println("Remaining Active Reservations:");
        for (Reservation r : reservations) {
            r.displayReservation();
        }

        System.out.println("\nEnd of Booking Cancellation Demonstration\n");
    }
}