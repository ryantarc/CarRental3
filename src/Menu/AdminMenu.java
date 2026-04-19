package Menu;

import Managers.*;
import Reservations.Reservation;
import Vehicles.*;
import java.util.Scanner;
import Users.Admin;
import Users.Customers;
import Users.User;

public class AdminMenu {
    private InventoryManager inventory;
    private ReportsManager reportsManager;
    private Scanner scanner;
    private UserManager userManager;
    private InputValidators input;
    private ReservationsManager reservationsManager;
    private AuthManager authManager;

    public AdminMenu(Scanner scanner, InventoryManager inventory,
                     ReportsManager reportsManager,
                     ReservationsManager reservationsManager, UserManager userManager, AuthManager authManager) {

        this.inventory = inventory;
        this.scanner = scanner;
        this.reportsManager = reportsManager;
        this.reservationsManager = reservationsManager;
        this.userManager = userManager;
        this.authManager = new AuthManager();
        input = new InputValidators();

        inventory.loadFromFile();
    }

    public void start(String adminName) {
        int choice;

        do {
            printHeader("ADMIN MENU");
            System.out.println(" Logged in as: " + adminName);
            System.out.println("________________________________");
            System.out.println(" 1. Add Car");
            System.out.println(" 2. View Inventory");
            System.out.println(" 3. Delete Car");
            System.out.println(" 4. Change Car Status");
            System.out.println(" 5. View Report");
            System.out.println(" 6. View Reservations");
            System.out.println(" 7. Review Pending Returns");
            System.out.println(" 8. Manage Users");
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
                case 8 -> manageUsers();
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
        String remarks = "";
        if (damaged) {
            System.out.print(" Remarks      : ");
            remarks = scanner.nextLine().trim();  //
        }
        boolean lowFuel = input.getBooleanInput(" Low fuel (y/n): ");

        reservationsManager.returnCar(carID, days, damaged, lowFuel, remarks);
        reservationsManager.changeReservationStatus(
                Reservation.ReservationStatus.COMPLETED, reservation);

        inventory.changeCarStatus(Car.CarStatus.AVAILABLE, car);
        reservationsManager.saveToFile();

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
        reportsManager.generateReport(reservationsManager.getAllReservations());

        System.out.println("\n===== REPORT OPTIONS =====");
        System.out.println("1. Delete a Report by Car ID");
        System.out.println("0. Back");
        int choice = input.getIntInput("Enter choice: ");

        switch (choice) {
            case 1 -> {
                System.out.print("Enter Car ID to delete: ");
                String carID = scanner.nextLine();
                reportsManager.deleteReport(carID, reservationsManager); // pass reservationsManager
            }
            case 0 -> {}
            default -> System.out.println("Invalid choice.");
        }
    }

    // ================= MANAGE USERS =================
    private void manageUsers() {

        int choice;

        do {
            printHeader("MANAGE USERS");
            System.out.println(" 1. Add Admin");
            System.out.println(" 2. Display Users");
            System.out.println(" 3. Edit User Details");
            System.out.println(" 4. Delete User");
            System.out.println(" 0. Back");
            System.out.println("________________________________");

            choice = input.getIntInput(" Select option: ");

            switch(choice){
                case 1 -> addAdmin();
                case 2 -> displayUsers();
                case 3 -> editUser();
                case 4 -> deleteUser();
                default -> System.out.println(" Invalid option.");

            }
        }while (choice != 0);


    }

    // ================= ADD ADMIN =================
    private void addAdmin(){
        printHeader("ADD ADMIN");

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

        Admin newAdmin = new Admin(name, email, password, phoneNo);
        userManager.addUser(newAdmin);

        System.out.println("\n Admin added! ID: " + newAdmin.getId());
        pause();
    }

    // ============== DISPLAY USER =================

    private void displayUsers() {
        printHeader("DISPLAY USERS");
        System.out.println(" 1. All Users");
        System.out.println(" 2. Admins Only");
        System.out.println(" 3. Customers Only");
        System.out.println(" 0. Back");

        int choice = input.getIntInput(" Select option: ");

        switch (choice) {
            case 1 -> {
                System.out.println("\n--- ADMINS ---");
                userManager.displayAdmins();
                System.out.println("\n--- CUSTOMERS ---");
                authManager.loadFromFile();
                authManager.displayCustomers();
            }
            case 2 -> userManager.displayAdmins();
            case 3 -> {
                    authManager.loadFromFile();
                    authManager.displayCustomers();
            }
            case 0 -> { return; }
            default -> System.out.println(" Invalid option.");
        }

        pause();
    }

    // =============== EDIT USER ===============

    private void editUser() {
        printHeader("EDIT USER DETAILS");

        System.out.println(" 1. Edit Admin");
        System.out.println(" 2. Edit Customer");
        System.out.println(" 0. Back");

        int choice = input.getIntInput(" Select option: ");
        if (choice == 0) {
            return;
        }

        switch (choice) {
            case 1 -> {
                userManager.displayAdmins();
                System.out.print("\n Enter Admin ID to edit: ");
                String id = scanner.nextLine().trim();

                User user = userManager.findUser(id);
                if (user == null) {
                    System.out.println("User not found.");
                    pause();
                    return;
                }
                applyEdit(user);
                userManager.editUser(user, user.getName(), user.getEmail(), user.getPassword(), user.getPhoneNo());

            }
            case 2 -> {
                authManager.displayCustomers();
                System.out.print("\n Enter Customer ID to edit: ");
                String id = scanner.nextLine().trim();

                Customers customer = authManager.findCustomerById(id);
                if (customer == null) {
                    System.out.println(" Customer not found.");
                    pause();
                    return;
                }
                applyEdit(customer);
                authManager.saveToFile();


            }
            default -> System.out.println(" Invalid option.");
        }




    }

    // ============== DELETE USER =====================
    private void deleteUser(){
        printHeader("DELETE USER");

        System.out.println(" 1. Delete Admin");
        System.out.println(" 2. Delete Customer");
        System.out.println(" 0. Back");

        int choice = input.getIntInput(" Select option: ");
        if (choice == 0) {
            return;
        }

        switch (choice) {
            case 1 -> {
                userManager.displayAdmins();
                System.out.print("\n Enter Admin ID to delete (x to cancel): ");
                String id = scanner.nextLine().trim();
                if (id.equalsIgnoreCase("x")) return;

                User user = userManager.findUser(id);
                if (user == null) {
                    System.out.println(" Admin not found.");
                    pause();
                    return;
                }

                System.out.print(" Are you sure you want to delete " + user.getName() + "? (y/n): ");
                String confirm = scanner.nextLine().trim();
                if (confirm.equalsIgnoreCase("y")) {
                    userManager.deleteUser(user);
                } else {
                    System.out.println(" Cancelled.");
                }
            }
            case 2 -> {
                authManager.displayCustomers();
                System.out.print("\n Enter Customer ID to delete (x to cancel): ");
                String id = scanner.nextLine().trim();
                if (id.equalsIgnoreCase("x")) return;

                Customers customer = authManager.findCustomerById(id);
                if (customer == null) {
                    System.out.println(" Customer not found.");
                    pause();
                    return;
                }

                System.out.print(" Are you sure you want to delete " + customer.getName() + "? (y/n): ");
                String confirm = scanner.nextLine().trim();
                if (confirm.equalsIgnoreCase("y")) {
                    authManager.deleteCustomer(customer);
                } else {
                    System.out.println(" Cancelled.");
                }
            }
            default -> System.out.println(" Invalid option.");


        }
        pause();

    }

    private void applyEdit(User user) {
        System.out.println("\n Leave blank to keep current value.");

        System.out.print(" Old Name    : " + user.getName() + "\n New Name    : ");
        String name = scanner.nextLine().trim();

        System.out.print(" Old Email   : " + user.getEmail() + "\n New Email    :");
        String email = scanner.nextLine().trim();

        System.out.print(" Old Password : [hidden]\n New Password :");
        String password = scanner.nextLine().trim();

        System.out.print(" New PhoneNo  : " + user.getPhoneNo() + "\n New PhoneNo  :");
        String phone = scanner.nextLine().trim();

        if (!name.isEmpty()){
            user.setName(name);
        }
        if (!email.isEmpty()) {
            user.setEmail(email);
        }
        if (!password.isEmpty()){
            user.setPassword(password);
        }
        if (!phone.isEmpty()){
            user.setPhoneNo(phone);
        }


        System.out.println(" Details updated.");
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