package Menu;

import Managers.*;
import Reservations.Reservation;
import Vehicles.*;
import java.util.Scanner;

public class AdminMenu {
    private InventoryManager inventory;
    private ReportsManager reportsManager;
    private Scanner scanner;
    private InputValidators input;
    private ReservationsManager reservationsManager;

    public AdminMenu(Scanner scanner, InventoryManager inventory,
                     ReportsManager reportsManager,
                     ReservationsManager reservationsManager) {

        this.inventory = inventory;
        this.scanner = scanner;
        this.reportsManager = reportsManager;
        this.reservationsManager = reservationsManager;
        input = new InputValidators();

        inventory.loadFromFile();
    }

    public void start() {
        int choice;

        do {
            printHeader("ADMIN MENU");

            System.out.println(" 1. Add Car");
            System.out.println(" 2. View Inventory");
            System.out.println(" 3. Delete Car");
            System.out.println(" 4. Change Car Status");
            System.out.println(" 5. View Report");
            System.out.println(" 6. View Reservations");
            System.out.println(" 7. Review Pending Returns");
            System.out.println(" 0. Back");
            System.out.println("________________________________");

            choice = input.getIntInput(" Select option: ");

            switch (choice) {
                case 1 -> addCarUI();
                case 2 -> viewInventory();
                case 3 -> deleteCar();
                case 4 -> changeCarStatus();
                case 5 -> viewReport();
                case 6 -> viewReservation();
                case 7 -> reviewPendingReservations();
                case 0 -> System.out.println("Returning...");
                default -> System.out.println("Invalid option.");
            }
        } while (choice != 0);
    }

    // ================= ADD CAR =================
    private void addCarUI() {
        printHeader("ADD CAR");

        System.out.println(" 1. Economy");
        System.out.println(" 2. Luxury");
        System.out.println(" 3. SUV");
        System.out.println(" 0. Back");

        int choice = input.getIntInput(" Select type: ");
        if (choice == 0) return;

        System.out.print(" Model           : ");
        String model = scanner.nextLine();

        System.out.print(" Plate Number    : ");
        String plate = scanner.nextLine();

        double rate = input.getDoubleInput(" Daily Rate      : ");
        boolean available = input.getBooleanInput(" Available (y/n) : ");

        Car.CarStatus status = available ? Car.CarStatus.AVAILABLE : Car.CarStatus.RENTED;

        int seats = input.getIntInput(" Seating Capacity: ");

        switch (choice) {
            case 1 -> addCarEco(model, plate, rate, status, seats);
            case 2 -> addCarLux(model, plate, rate, status, seats);
            case 3 -> addCarSUV(model, plate, rate, status, seats);
            default -> System.out.println("Invalid choice.");
        }

        pause();
    }

    public void addCarSUV(String model, String plate, double rate,
                         Car.CarStatus status, int seats) {

        boolean is4WD = input.getBooleanInput(" 4WD (y/n): ");
        boolean thirdRow = input.getBooleanInput(" Third row seating (y/n): ");

        Car suv = new SUV(model, plate, rate, status, seats, is4WD, thirdRow);
        inventory.addCar(suv);

        System.out.println("\nSUV added successfully.");
    }

    public void addCarLux(String model, String plate, double rate,
                         Car.CarStatus status, int seats) {

        boolean hasSunroof = input.getBooleanInput(" Sunroof (y/n): ");

        Car lux = new Luxury(model, plate, rate, status, seats, hasSunroof);
        inventory.addCar(lux);

        System.out.println("\nLuxury car added successfully.");
    }

    public void addCarEco(String model, String plate, double rate,
                         Car.CarStatus status, int seats) {

        double fuelEfficiency = input.getDoubleInput(" Fuel Efficiency (km/l): ");

        Car eco = new Economy(model, plate, rate, status, seats, fuelEfficiency);
        inventory.addCar(eco);

        System.out.println("\nEconomy car added successfully.");
    }

    // ================= VIEW INVENTORY =================
    private void viewInventory() {
        printHeader("INVENTORY");
        if (inventory.isEmpty()) {
            System.out.println("\nInventory is empty.");
            pause();
            return;
        }
        inventory.displayInventory();
        pause();
    }

    // ================= VIEW RESERVATIONS =================
    private void viewReservation() {
        printHeader("VIEW RESERVATIONS");

        System.out.println(" 1. All");
        System.out.println(" 2. Active");
        System.out.println(" 3. Pending Return");
        System.out.println(" 4. Cancelled");
        System.out.println(" 5. Completed");
        System.out.println(" 0. Back");

        int choice = input.getIntInput(" Select option: ");
        if (choice == 0) return;

        switch (choice) {
            case 1 -> reservationsManager.displayReservations();
            case 2 -> reservationsManager.displayReservations(Reservation.ReservationStatus.ACTIVE);
            case 3 -> reservationsManager.displayReservations(Reservation.ReservationStatus.PENDING_RETURN);
            case 4 -> reservationsManager.displayReservations(Reservation.ReservationStatus.CANCELLED);
            case 5 -> reservationsManager.displayReservations(Reservation.ReservationStatus.COMPLETED);
            default -> System.out.println("Invalid option.");
        }

        pause();
    }

    // ================= REVIEW RETURNS =================
    private void reviewPendingReservations() {

        printHeader("PENDING RETURNS");
        if (!reservationsManager.hasReservations(Reservation.ReservationStatus.PENDING_RETURN)) {
            System.out.println("No pending returns currently.");
            pause();
            return;
        }

        reservationsManager.displayReservations(Reservation.ReservationStatus.PENDING_RETURN);

        System.out.print("\n Enter Car ID: ");
        String carID = scanner.nextLine();

        Car car = inventory.findCar(carID);
        Reservation reservation = reservationsManager.findPendingReservation(carID);

        if (car == null || reservation == null) {
            System.out.println("Invalid Car ID.");
            return;
        }

        int days = input.getIntInput(" Rented days: ");
        boolean damaged = input.getBooleanInput(" Damaged (y/n): ");
        boolean lowFuel = input.getBooleanInput(" Low fuel (y/n): ");

        reservationsManager.returnCar(carID, days, damaged, lowFuel);
        reservationsManager.changeReservationStatus(
                Reservation.ReservationStatus.COMPLETED, reservation);

        inventory.changeCarStatus(Car.CarStatus.AVAILABLE, car);
        reservationsManager.saveToFile();

        System.out.println("\nReturn processed.");
        pause();
    }

    // ================= DELETE =================
    private void deleteCar() {
        String carID;

        if (inventory.isEmpty()) {
            System.out.println("\nInventory is empty.");
            pause();
            return;
        }

        do {
            viewInventory();
            System.out.print("\n Enter Car ID to delete (x to cancel): ");
            carID = scanner.nextLine();

            if (carID.equalsIgnoreCase("x")) return;

            if (inventory.findCar(carID) == null) {
                System.out.println("Invalid Car ID.");
            }

        } while (inventory.findCar(carID) == null);

        inventory.deleteCar(inventory.findCar(carID));
        pause();
    }

    // ================= CHANGE STATUS =================
    private void changeCarStatus() {
        String carID;

        if (inventory.isEmpty()) {
            System.out.println("\nInventory is empty.");
            pause();
            return;
        }

        do {
            viewInventory();
            System.out.print("\n Enter Car ID [x to exit]: ");
            carID = scanner.nextLine();

            if (carID.equalsIgnoreCase("x")) {
                return;
            }

            if (inventory.findCar(carID) == null) {
                System.out.println("Invalid Car ID.");
            }


        } while (inventory.findCar(carID) == null);

        System.out.println(" 1. Available");
        System.out.println(" 2. Rented");
        System.out.println(" 3. Maintenance");
        System.out.println(" 4. Pending Return");

        int choice = input.getIntInput(" Select status: ");

        Car.CarStatus status = switch (choice) {
            case 1 -> Car.CarStatus.AVAILABLE;
            case 2 -> Car.CarStatus.RENTED;
            case 3 -> Car.CarStatus.MAINTENANCE;
            case 4 -> Car.CarStatus.PENDING;
            default -> null;
        };

        if (status != null) {
            inventory.changeCarStatus(status, inventory.findCar(carID));
            System.out.println("\nStatus updated.");
        } else {
            System.out.println("Invalid option.");
        }

        pause();
    }

    // ================= REPORT =================
    private void viewReport() {
        printHeader("REPORT");
        reportsManager.generateReport(reservationsManager.getAllReservations());
        pause();
    }

    // ================= UI =================
    private void printHeader(String title) {
        System.out.println("\n================================");
        System.out.printf("        %s%n", title);
        System.out.println("================================");
    }

    private void pause() {
        System.out.print("\nPress ENTER to continue...");
        scanner.nextLine();
    }
}