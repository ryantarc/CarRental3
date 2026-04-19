package Menu;

import Managers.*;
import Reservations.Reservation;
import Users.Customers;
import Vehicles.*;
import java.util.Scanner;

public class CustomerMenu {

    private ReservationsManager reservationsManager;
    private InventoryManager inventory;
    private AuthManager authManager;
    private Scanner scanner;
    private InputValidators input;

    public CustomerMenu(Scanner scanner, InventoryManager inventory, ReservationsManager reservationsManager) {
        this.inventory = inventory;
        this.scanner = scanner;
        this.reservationsManager = reservationsManager;
        this.authManager = new AuthManager();
        input = new InputValidators();

        inventory.loadFromFile();
        reservationsManager.loadFromFile(inventory);
    }

    // ================= ENTRY POINT =================
    public void start() {
        int choice;

        do {
            printHeader("CUSTOMER PAGE");
            System.out.println(" 1. Login");
            System.out.println(" 2. Sign Up");
            System.out.println(" 0. Exit");
            System.out.println("________________________________");

            choice = input.getIntInput(" Select option: ");

            switch (choice) {
                case 1 -> { if (loginFlow())  customerDashboard(); }
                case 2 -> { if (signUpFlow()) customerDashboard(); }
                case 0 -> System.out.println("Returning to main menu...");
                default -> System.out.println("Invalid option. Please choose 0-2.");
            }

        } while (choice != 0);
    }

    // ================= AUTH FLOWS =================
    private boolean loginFlow() {
        printHeader("LOGIN");
        scanner.nextLine();

        System.out.print(" Email    : ");
        String email = scanner.nextLine().trim();

        System.out.print(" Password : ");
        String password = scanner.nextLine().trim();

        Customers customer = authManager.login(email, password);

        if (customer == null) {
            System.out.println("\n Invalid email or password.");
            pause();
            return false;
        }

        System.out.println("\n Welcome back, " + customer.getName() + "! (" + customer.getId() + ")");
        pause();
        return true;
    }

    private boolean signUpFlow() {
        printHeader("SIGN UP");
        scanner.nextLine();

        System.out.print(" Full Name         : ");
        String name = scanner.nextLine().trim();

        System.out.print(" Email             : ");
        String email = scanner.nextLine().trim();

        System.out.print(" Password          : ");
        String password = scanner.nextLine().trim();

        String phoneNo;
        while (true) {
            System.out.print(" Phone Number (10 digits)   : ");
            phoneNo = scanner.nextLine().trim();
            if (phoneNo.matches("\\d{10}")) break;
            System.out.println(" Invalid. Phone number must be exactly 10 digits.");
        }

        String license;
        while (true) {
            System.out.print(" Driving License (8 digits) : ");
            license = scanner.nextLine().trim();
            if (license.matches("\\d{8}")) break;
            System.out.println(" Invalid. Driving license must be exactly 8 digits.");
        }

        Customers newCustomer = authManager.signUp(name, email, password, phoneNo, license);

        if (newCustomer == null) {
            System.out.println("\n Email already registered. Please login instead.");
            pause();
            return false;
        }

        System.out.println("\n Account created! Your ID: " + newCustomer.getId());
        System.out.println(" Welcome, " + newCustomer.getName() + "!");
        pause();
        return true;
    }

    // ================= CUSTOMER DASHBOARD =================
    private void customerDashboard() {
        int choice;
        Customers me = authManager.getLoggedInCustomer();

        do {
            printHeader("CUSTOMER MENU");
            System.out.println(" Logged in as: " + me.getName() + " (" + me.getId() + ")");
            System.out.println("________________________________");
            System.out.println(" 1. Reserve New Car");
            System.out.println(" 2. View Inventory");
            System.out.println(" 3. Return Car");
            System.out.println(" 4. Cancel Reservation");
            System.out.println(" 5. Currently Reserving");
            System.out.println(" 6. View Info");
            System.out.println(" 0. Logout");
            System.out.println("________________________________");

            choice = input.getIntInput(" Select option: ");

            switch (choice) {
                case 1 -> reserveCar();
                case 2 -> viewInventory();
                case 3 -> returnCar();
                case 4 -> cancelCar();
                case 5 -> viewCurrentReservations();
                case 6 -> viewInfo();
                case 0 -> {
                    authManager.logout();
                    System.out.println(" Logged out. See you next time!");
                }
                default -> System.out.println("Invalid option. Please choose 0-6.");
            }

        } while (choice != 0);
    }

    // ================= RESERVE =================
    private void reserveCar() {
        printHeader("RESERVE CAR");

        System.out.println(" Select Car Type:");
        System.out.println(" 1. Economy");
        System.out.println(" 2. Luxury");
        System.out.println(" 3. SUV");

        int choice = input.getIntInput(" Choice: ");

        switch (choice) {
            case 1 -> inventory.displayInventory("ECONOMY");
            case 2 -> inventory.displayInventory("LUXURY");
            case 3 -> inventory.displayInventory("SUV");
            default -> {
                System.out.println(" Invalid choice.");
                return;
            }
        }

        scanner.nextLine();

        System.out.print("\n Enter Car ID: ");
        String carID = scanner.nextLine().trim();

        Car selectedCar = inventory.findCar(carID);

        if (selectedCar == null) {
            System.out.println(" Car not found.");
            return;
        }

        if (selectedCar.getStatus() != Car.CarStatus.AVAILABLE) {
            System.out.println(" Car is not available.");
            return;
        }

        int rentDays = input.getIntInput(" Rental duration (days): ");
        double totalCost = rentDays * selectedCar.getDailyRate();

        printHeader("RESERVATION SUMMARY");
        System.out.println(" Car ID      : " + selectedCar.getCarID());
        System.out.println(" Type        : " + selectedCar.getCarType());
        System.out.println(" Model       : " + selectedCar.getModel());
        System.out.println(" Plate       : " + selectedCar.getCarPlate());
        System.out.println(" Daily Rate  : RM" + selectedCar.getDailyRate());
        System.out.println(" Days        : " + rentDays);
        System.out.println(" Total Cost  : RM" + totalCost);

        System.out.print("\n Confirm reservation? (y/n): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            Reservation newReservation = new Reservation(
                    authManager.getLoggedInCustomer().getId(),
                    selectedCar.getCarID(),
                    selectedCar.getCarType(),
                    selectedCar.getModel(),
                    selectedCar.getCarPlate(),
                    selectedCar.getDailyRate(),
                    rentDays
            );

            reservationsManager.addReservation(newReservation);
            inventory.changeCarStatus(Car.CarStatus.RENTED, selectedCar);
            System.out.println(" Reservation confirmed!");
        } else {
            System.out.println(" Reservation cancelled.");
        }

        pause();
    }

    // ================= VIEW INVENTORY =================
    private void viewInventory() {
        printHeader("INVENTORY");
        inventory.displayInventory();
        pause();
    }

    // ================= RETURN =================
    private void returnCar() {
        printHeader("RETURN CAR");

        String customerID = authManager.getLoggedInCustomer().getId();

        if (!reservationsManager.hasReservationsByCustomer(customerID, Reservation.ReservationStatus.ACTIVE)) {
            System.out.println(" No active reservations found.");
            pause();
            return;
        }

        reservationsManager.displayReservationsByCustomer(customerID, Reservation.ReservationStatus.ACTIVE);

        System.out.print("\n Enter Car ID to return: ");
        String carID = scanner.nextLine().trim();

        Car car = inventory.findCar(carID);
        Reservation reservation = reservationsManager.findActiveReservation(carID);

        if (car == null || reservation == null) {
            System.out.println(" Invalid Car ID.");
            pause();
            return;
        }

        reservationsManager.changeReservationStatus(Reservation.ReservationStatus.PENDING_RETURN, reservation);
        reservationsManager.saveToFile();

        System.out.println(" Car marked for return. Pending staff approval.");
        pause();
    }

    // ================= CANCEL =================
    private void cancelCar() {
        printHeader("CANCEL RESERVATION");

        String customerID = authManager.getLoggedInCustomer().getId();

        if (!reservationsManager.hasReservationsByCustomer(customerID, Reservation.ReservationStatus.ACTIVE)) {
            System.out.println(" No active reservations found.");
            pause();
            return;
        }

        reservationsManager.displayReservationsByCustomer(customerID, Reservation.ReservationStatus.ACTIVE);

        System.out.print("\n Enter Car ID to cancel: ");
        String carID = scanner.nextLine().trim();

        Car car = inventory.findCar(carID);
        Reservation reservation = reservationsManager.findActiveReservation(carID);

        if (car == null || reservation == null) {
            System.out.println(" Invalid Car ID.");
            pause();
            return;
        }

        reservationsManager.cancelCar(carID);
        inventory.changeCarStatus(Car.CarStatus.AVAILABLE, car);
        reservationsManager.saveToFile();

        System.out.println(" Reservation cancelled.");
        pause();
    }

    // ================= CURRENTLY RESERVING =================
    private void viewCurrentReservations() {
        printHeader("CURRENTLY RESERVING");

        String customerID = authManager.getLoggedInCustomer().getId();

        if (!reservationsManager.hasReservationsByCustomer(customerID, Reservation.ReservationStatus.ACTIVE)) {
            System.out.println(" You have no active reservations.");
            pause();
            return;
        }

        reservationsManager.displayReservationsByCustomer(customerID, Reservation.ReservationStatus.ACTIVE);
        pause();
    }

    // ================= VIEW INFO =================
    private void viewInfo() {
        printHeader("MY INFO");

        Customers me = authManager.getLoggedInCustomer();

        System.out.println(" Customer ID      : " + me.getId());
        System.out.println(" Name             : " + me.getName());
        System.out.println(" Email            : " + me.getEmail());
        System.out.println(" Phone            : " + me.getPhoneNo());
        System.out.println(" Driving License  : " + me.getDrivingLicense());

        System.out.println("\n--- Rental History ---");

        String customerID = me.getId();
        boolean found = false;

        System.out.printf(" %-8s | %-18s | %-8s | %-6s | %-10s | %-14s%n",
                "Car ID", "Car Model", "Type", "Days", "Total (RM)", "Status");
        System.out.println(" " + "-".repeat(78));

        for (Reservation r : reservationsManager.getAllReservations()) {
            if (r.getCustomerID().equals(customerID)) {
                System.out.printf(" %-8s | %-18s | %-8s | %-6d | %-10.2f | %-14s%n",
                        r.getCarID(),
                        r.getCarModel(),
                        r.getCarType(),
                        r.getRentalDays(),
                        r.getTotalCost(),
                        r.getStatus());
                found = true;
            }
        }

        if (!found) System.out.println(" No rental history.");

        pause();
    }

    // ================= UI HELPERS =================
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