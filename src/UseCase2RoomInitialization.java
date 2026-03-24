/**
 * UseCase2RoomInitialization
 *
 * This class demonstrates object modeling for hotel rooms
 * using abstract classes, inheritance, and polymorphism.
 * It also shows static availability for each room type.
 *
 * @author Neha
 * @version 2.1
 */

// Abstract class representing a general Room
abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    // Constructor
    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    // Method to display room details
    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Number of Beds: " + beds);
        System.out.println("Price: $" + price);
    }
}

// Concrete classes for specific room types
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

// Main application for UC2
public class UseCase2RoomInitialization {

    public static void main(String[] args) {
        // Static availability variables
        int availableSingleRooms = 5;
        int availableDoubleRooms = 3;
        int availableSuites = 2;

        // Initialize room objects
        Room single = new SingleRoom();
        Room doubleR = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Display room information
        System.out.println("====================================");
        System.out.println("Welcome to Book My Stay App - Room Overview (v2.1)");
        System.out.println("====================================");

        single.displayRoomDetails();
        System.out.println("Available: " + availableSingleRooms + "\n");

        doubleR.displayRoomDetails();
        System.out.println("Available: " + availableDoubleRooms + "\n");

        suite.displayRoomDetails();
        System.out.println("Available: " + availableSuites + "\n");

        System.out.println("====================================");
        System.out.println("End of Room Overview");
        System.out.println("====================================");
    }
}