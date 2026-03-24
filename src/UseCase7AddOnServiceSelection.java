/**
 * UseCase7AddOnServiceSelection
 *
 * This class demonstrates adding optional services to confirmed reservations.
 * Services are mapped to reservation IDs using a Map and List combination.
 * Core booking and inventory logic remain unchanged.
 *
 * @author Neha
 * @version 7.0
 */

import java.util.*;

// Service class representing an add-on
class Service {
    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public void displayService() {
        System.out.println(serviceName + " ($" + cost + ")");
    }
}

// Manager to map services to reservations
class AddOnServiceManager {
    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Add a service to a reservation
    public void addService(String reservationID, Service service) {
        reservationServices.computeIfAbsent(reservationID, k -> new ArrayList<>()).add(service);
    }

    // Calculate total cost for a reservation
    public double calculateTotalServiceCost(String reservationID) {
        List<Service> services = reservationServices.get(reservationID);
        if (services == null) return 0.0;

        double total = 0;
        for (Service s : services) {
            total += s.getCost();
        }
        return total;
    }

    // Display services for a reservation
    public void displayServices(String reservationID) {
        List<Service> services = reservationServices.get(reservationID);
        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }
        System.out.println("Add-On Services for Reservation " + reservationID + ":");
        for (Service s : services) {
            s.displayService();
        }
        System.out.println("Total Additional Cost: $" + calculateTotalServiceCost(reservationID) + "\n");
    }
}

// Main application for UC7
public class UseCase7AddOnServiceSelection {
    public static void main(String[] args) {
        System.out.println("Welcome to Book My Stay App - Add-On Service Selection (v7.0)\n");

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Simulated reservation IDs
        String res1 = "SI-123";
        String res2 = "DO-456";

        // Define some services
        Service breakfast = new Service("Breakfast", 15.0);
        Service spa = new Service("Spa Access", 50.0);
        Service airport = new Service("Airport Pickup", 30.0);

        // Add services to reservations
        serviceManager.addService(res1, breakfast);
        serviceManager.addService(res1, spa);
        serviceManager.addService(res2, airport);

        // Display services
        serviceManager.displayServices(res1);
        serviceManager.displayServices(res2);
    }
}