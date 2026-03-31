package Menu;
import Vehicles.*;
import java.util.Scanner;

public class AdminMenu {
    private InventoryManagement Inventory;
    private Scanner scanner;

    public AdminMenu() {
        Inventory = new InventoryManagement();
        scanner = new Scanner(System.in);
        Inventory.loadFromFile(); // load once at start
    }

    public void start() {
        int choice;

        do {
            displayMenu();
            choice = getIntInput("Enter choice: ");

            switch (choice) {
                case 1 -> addCarUI();
                case 2 -> viewInventory();
                case 3 -> deleteCar();
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
        System.out.println("0. Exit");
    }


    private void addCarUI() {
        System.out.println("\nType of Car");
        System.out.println("1.Economy");
        System.out.println("2.Luxury");
        System.out.println("3.SUV");
        int choice = getIntInput("Enter choice: ");
        scanner.nextLine(); // clear buffer

        System.out.print("Model: ");
        String model = scanner.nextLine();

        System.out.print("Plate: ");
        String plate = scanner.nextLine();

        double rate = getDoubleInput("Daily Rate: ");
        boolean status = getBooleanInput("Available (y/n): ");
        int seats = getIntInput("Seating Capacity: ");

        switch (choice){
            case 1 -> addCarSUV(model, plate, rate, status, seats);
            case 2 -> addCarLux(model, plate, rate, status, seats);
            case 3 -> addCarEco(model, plate, rate, status, seats);
        }
    }

    public void addCarSUV(String model, String plate, double rate, boolean status, int seats){
        boolean is4WD = getBooleanInput("4WD (y/n): ");
        boolean thirdRow = getBooleanInput("Third row seating (y/n): ");
        Car suv = new SUV(model, plate, rate, status, seats, is4WD, thirdRow);
        Inventory.addCar(suv);
        System.out.println("SUV added successfully!");
    }
    public void addCarLux(String model, String plate, double rate, boolean status, int seats){
        boolean hasSunroof = getBooleanInput("hasSunroof (y/n): ");
        Car lux = new Luxury(model, plate, rate, status, seats, hasSunroof);
        Inventory.addCar(lux);
        System.out.println("Luxury Car added successfully!");
    }
    public void addCarEco(String model, String plate, double rate, boolean status, int seats){
        double fuelEfficiency = getDoubleInput("Fuel Efficiency (km/l): ");
        Car eco = new Economy(model, plate, rate, status, seats,fuelEfficiency);
        Inventory.addCar(eco);
        System.out.println("Economy Car added successfully!");
    }

    private void viewInventory() {
        System.out.println("\n=== Inventory ===");
        Inventory.displayInventory();
    }

    private void deleteCar() {
        System.out.println("Coming Soon");
    }

//input validation methods
    private int getIntInput(String message) {
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input! Enter a number.");
                scanner.nextLine();
            }
        }
    }

    private double getDoubleInput(String message) {
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextDouble();
            } catch (Exception e) {
                System.out.println("Invalid input! Enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    private boolean getBooleanInput(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.next().toLowerCase();

            if (input.equals("y")) return true;
            if (input.equals("n")) return false;

            System.out.println("Invalid input! Enter y/n.");
        }
    }

}
