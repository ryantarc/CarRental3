package Menu;
import Managers.InventoryManager;
import Managers.ReportsManager;
import Managers.ReservationsManager;
import Reservations.Reservation;
import Vehicles.*;
import java.util.Scanner;

public class AdminMenu {
    private InventoryManager Inventory;
    private ReportsManager reportsManager;
    private Scanner scanner;
    private InputValidators input;
    private ReservationsManager reservationsManager;

    //might move the stuff below into a different class for code neatness

    // AdminMenu.java - update constructor
    public AdminMenu(Scanner scanner, InventoryManager Inventory, ReportsManager reportsManager, ReservationsManager reservationsManager) {
        this.Inventory = Inventory;
        this.scanner = scanner;
        this.reportsManager = reportsManager;
        this.reservationsManager = reservationsManager;  // ADD THIS
        input = new InputValidators();
        Inventory.loadFromFile();
    }

    public void start() {
        int choice;

        do {
            displayMenu();
            choice =input.getIntInput("\nEnter choice: ");

            switch (choice) {
                case 1 -> addCarUI();
                case 2 -> viewInventory();
                case 3 -> deleteCar();
                case 4 -> changeCarStatus();
                case 5 -> viewReport();
                case 6 -> viewReservation();
                case 7 -> reviewPendingReservations();
                case 0 -> System.out.println("Exiting system...");
                default -> System.out.println("Invalid choice!");
            }

        } while (choice != 0);
    }
    private void displayMenu() {
        System.out.println("\n===== ADMIN MENU =====");
        System.out.println("1. Add Car");
        System.out.println("2. View Inventory");
        System.out.println("3. Delete Car");
        System.out.println("4. Change Car Status");
        System.out.println("5. View Report");
        System.out.println("6. Display Reservations");
        System.out.println("7. Review Pending Reservations");
        System.out.println("0. Exit");
    }


    private void addCarUI() {
        System.out.println("\nType of Car");
        System.out.println("1.Economy");
        System.out.println("2.Luxury");
        System.out.println("3.SUV");
        System.out.println("0.Exit");
        int choice = input.getIntInput("Enter choice: ");
        if (choice == 0){
            return;
        }
        System.out.print("Model: ");
        String model = scanner.nextLine();

        System.out.print("Plate: ");
        String plate = scanner.nextLine();

        double rate = input.getDoubleInput("Daily Rate: ");
        boolean temp = input.getBooleanInput("Available (y/n): ");
        Car.CarStatus status;

        if (temp){
            status = Car.CarStatus.AVAILABLE;
        } else {
            status = Car.CarStatus.RENTED;
        }

        int seats = input.getIntInput("Seating Capacity: ");

        switch (choice){
            case 1 -> addCarEco(model, plate, rate, status, seats);
            case 2 -> addCarLux(model, plate, rate, status, seats);
            case 3 -> addCarSUV(model, plate, rate, status, seats);
        }
        System.out.println();
    }

    public void addCarSUV(String model, String plate, double rate, Car.CarStatus status, int seats){
        boolean is4WD = input.getBooleanInput("4WD (y/n): ");
        boolean thirdRow = input.getBooleanInput("Third row seating (y/n): ");
        Car suv = new SUV(model, plate, rate, status, seats, is4WD, thirdRow);
        Inventory.addCar(suv);
        System.out.println("SUV added successfully!");
    }

    public void addCarLux(String model, String plate, double rate, Car.CarStatus status, int seats){
        boolean hasSunroof = input.getBooleanInput("hasSunroof (y/n): ");
        Car lux = new Luxury(model, plate, rate, status, seats, hasSunroof);
        Inventory.addCar(lux);
        System.out.println("Luxury Car added successfully!");
    }

    public void addCarEco(String model, String plate, double rate, Car.CarStatus status, int seats){
        double fuelEfficiency = input.getDoubleInput("Fuel Efficiency (km/l): ");
        Car eco = new Economy(model, plate, rate, status, seats,fuelEfficiency);
        Inventory.addCar(eco);
        System.out.println("Economy Car added successfully!");
    }

    private void viewInventory() {
        System.out.println("\n===== Inventory =====");
        Inventory.displayInventory();
    }

    private void viewReservation(){
        System.out.println("=======VIEW RESERVATIONS=======");
        System.out.println("1.All Reservations");
        System.out.println("2.Active Reservations");
        System.out.println("3.Pending Reservations");
        System.out.println("4.Cancelled Reservations");
        System.out.println("5.Completed Reservations");
        System.out.println("0.Exit");
        int choice = input.getIntInput("Enter choice: ");
        if (choice == 0){
            return;
        }
        switch (choice){
            case 1: System.out.println("======Reservations=====");
                reservationsManager.displayReservations();
                break;
            case 2: System.out.println("======Active Reservations=====");
                reservationsManager.displayReservations(Reservation.ReservationStatus.ACTIVE);
                break;
            case 3: System.out.println("======Pending Reservations=====");
                reservationsManager.displayReservations(Reservation.ReservationStatus.PENDING_RETURN);
                break;
            case 4: System.out.println("======Cancelled Reservations=====");
                reservationsManager.displayReservations(Reservation.ReservationStatus.CANCELLED);
                break;
            case 5: System.out.println("======Completed Reservations=====");
                reservationsManager.displayReservations(Reservation.ReservationStatus.COMPLETED);
                break;
            default:
                System.out.println("Exiting...");
        }
    }

    private void reviewPendingReservations(){
        System.out.println("======Reservations=======");
        reservationsManager.displayReservations(Reservation.ReservationStatus.PENDING_RETURN);

        System.out.print("\nEnter Car ID: ");
        String carID = scanner.nextLine();
        Car car = Inventory.findCar(carID);
        Reservation reservation = reservationsManager.findPendingReservation(carID);
        if (car == null) {
            System.out.println("Invalid Car ID. Returning...");
            return;
        }
        int realRentalDays = input.getIntInput("Rented for (days): ");
        boolean damaged = input.getBooleanInput("Any damages? (y/n): ");
        boolean lowFuel = input.getBooleanInput("Fuel Level Low? (y/n): ");
        reservationsManager.returnCar(carID, realRentalDays, damaged, lowFuel);
        reservationsManager.changeReservationStatus(Reservation.ReservationStatus.COMPLETED,reservation);
        Inventory.changeCarStatus(Car.CarStatus.AVAILABLE, car);
        reservationsManager.saveToFile();

    }

    private void deleteCar() {
        String carID;
        do {
            viewInventory();
            System.out.println("\nEnter Car ID of the car you would like to change [x to exit]: ");
            carID = scanner.nextLine();
            Inventory.findCar(carID);

            if (carID.equalsIgnoreCase("x")){
                System.out.println("Returning to Menu...");
                return;
            }

            if (Inventory.findCar(carID)==null){
                System.out.println("Please enter valid Car ID");
            }

        }while (Inventory.findCar(carID)==null);
        Car car = Inventory.findCar(carID);
        Inventory.deleteCar(car);
    }

    private void changeCarStatus(){
        String carID;
        Car.CarStatus status = null;
        do {
            viewInventory();
            System.out.println("Enter Car ID of the car you would like to change: ");
            carID = scanner.nextLine();
            Inventory.findCar(carID);
            if (Inventory.findCar(carID)==null){
                System.out.println("Please enter valid Car ID");
            }
        }while (Inventory.findCar(carID)==null);
        System.out.println("1. Available");
        System.out.println("2. Rented");
        System.out.println("3. Under Maintenance");
        System.out.println("4. Pending Return");
        int num = input.getIntInput("Enter Input: ");

        switch (num){
            case 1 -> status = Car.CarStatus.AVAILABLE;
            case 2 -> status = Car.CarStatus.RENTED;
            case 3 -> status = Car.CarStatus.MAINTENANCE;
            case 4 -> status = Car.CarStatus.PENDING;
            default -> System.out.println("Invalid choice");
        }
        Inventory.changeCarStatus(status,Inventory.findCar(carID));
        System.out.println();
    }

    // Update viewReport()
    private void viewReport() {
        reportsManager.generateReport(reservationsManager.getAllReservations());
    }

}
