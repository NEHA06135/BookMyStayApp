/**
 * UseCase8BookingHistoryReport
 *
 * This class demonstrates booking history tracking and reporting.
 * Confirmed reservations are stored in a List to preserve order.
 * Reports can be generated without modifying stored bookings.
 *
 * @author Neha
 * @version 8.0
 */

import java.util.*;

// Reservation class (simplified for history tracking)
class Reservation {
    private String guestName;
    private String roomType;
    private String roomID;

    public Reservation(String guestName, String roomType, String roomID) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomID = roomID;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomID() {
        return roomID;
    }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType + " | Room ID: " + roomID);
    }
}

// Booking history stores confirmed reservations
class BookingHistory {
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add confirmed reservation to history
    public void addReservation(Reservation res) {
        history.add(res);
    }

    // Retrieve all reservations
    public List<Reservation> getAllReservations() {
        return Collections.unmodifiableList(history);
    }

    // Display report of all reservations
    public void generateReport() {
        System.out.println("===== Booking History Report =====");
        if (history.isEmpty()) {
            System.out.println("No reservations have been made yet.");
        } else {
            for (Reservation res : history) {
                res.displayReservation();
            }
        }
        System.out.println("=================================\n");
    }
}

// Main application for UC8
public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App - Booking History & Reporting (v8.0)\n");

        BookingHistory bookingHistory = new BookingHistory();

        // Simulate confirmed reservations
        Reservation r1 = new Reservation("Alice", "Single Room", "SI-123");
        Reservation r2 = new Reservation("Bob", "Double Room", "DO-456");
        Reservation r3 = new Reservation("Charlie", "Suite Room", "SU-789");

        // Add to history
        bookingHistory.addReservation(r1);
        bookingHistory.addReservation(r2);
        bookingHistory.addReservation(r3);

        // Generate report
        bookingHistory.generateReport();

        // Retrieve all reservations without modification
        List<Reservation> allReservations = bookingHistory.getAllReservations();
        System.out.println("Retrieved " + allReservations.size() + " reservations for admin review.\n");
    }
}