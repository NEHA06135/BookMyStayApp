/**
 * UseCase5BookingRequestQueue
 *
 * This class demonstrates handling booking requests fairly
 * using a Queue to preserve the order of arrival (FIFO).
 *
 * @author Neha
 * @version 5.0
 */

import java.util.LinkedList;
import java.util.Queue;

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

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

// Main application for UC5
public class UseCase5BookingRequestQueue {

    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App - Booking Request Queue (v5.0)\n");

        // Queue to store booking requests
        Queue<Reservation> bookingQueue = new LinkedList<>();

        // Simulate incoming booking requests
        bookingQueue.add(new Reservation("Alice", "Single Room"));
        bookingQueue.add(new Reservation("Bob", "Double Room"));
        bookingQueue.add(new Reservation("Charlie", "Suite Room"));
        bookingQueue.add(new Reservation("Diana", "Single Room"));

        // Display queued requests
        System.out.println("Current Booking Requests in FIFO order:\n");
        for (Reservation res : bookingQueue) {
            res.displayReservation();
        }

        System.out.println("\nAll requests are queued for processing.\n");
    }
}git add src/UseCase5BookingRequestQueue.java