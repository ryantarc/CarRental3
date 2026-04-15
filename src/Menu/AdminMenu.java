package Menu;
import Managers.InventoryManager;
import Vehicles.*;
import java.util.Scanner;

public class AdminMenu {
    private InventoryManager Inventory;
    private Scanner scanner;
    private InputValidators input;

    //might move the stuff below into a different class for code neatness

    public AdminMenu(Scanner scanner, InventoryManager Inventory) {
        this.Inventory = Inventory;
        this.scanner = scanner;
        input = new InputValidators();
        Inventory.loadFromFile(); // load once at start
    }
    public void start() {
        int choice;

        do {
            displayMenu();
            choice =input.getIntInput("Enter choice: ");

            switch (choice) {
                case 1 -> addCarUI();
                case 2 -> viewInventory();
                case 3 -> deleteCar();
                case 4 -> changeCarStatus();
                case 5 -> System.out.println("coming soon");

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
        Car.carStatus status;

        if (temp){
            status = Car.carStatus.AVAILABLE;
        } else {
            status = Car.carStatus.RENTED;
        }

        int seats = input.getIntInput("Seating Capacity: ");

        switch (choice){
            case 1 -> addCarEco(model, plate, rate, status, seats);
            case 2 -> addCarLux(model, plate, rate, status, seats);
            case 3 -> addCarSUV(model, plate, rate, status, seats);
        }
        System.out.println();
    }

    public void addCarSUV(String model, String plate, double rate, Car.carStatus status, int seats){
        boolean is4WD = input.getBooleanInput("4WD (y/n): ");
        boolean thirdRow = input.getBooleanInput("Third row seating (y/n): ");
        Car suv = new SUV(model, plate, rate, status, seats, is4WD, thirdRow);
        Inventory.addCar(suv);
        System.out.println("SUV added successfully!");
    }

    public void addCarLux(String model, String plate, double rate, Car.carStatus status, int seats){
        boolean hasSunroof = input.getBooleanInput("hasSunroof (y/n): ");
        Car lux = new Luxury(model, plate, rate, status, seats, hasSunroof);
        Inventory.addCar(lux);
        System.out.println("Luxury Car added successfully!");
    }

    public void addCarEco(String model, String plate, double rate, Car.carStatus status, int seats){
        double fuelEfficiency = input.getDoubleInput("Fuel Efficiency (km/l): ");
        Car eco = new Economy(model, plate, rate, status, seats,fuelEfficiency);
        Inventory.addCar(eco);
        System.out.println("Economy Car added successfully!");
    }

    private void viewInventory() {
        System.out.println("\n===== Inventory =====");
        Inventory.displayInventory();
    }

    private void deleteCar() {
        String carID;
        do {
            viewInventory();
            System.out.println("Enter Car ID of the car you would like to change: ");
            carID = scanner.nextLine();
            Inventory.findCar(carID);

            if (Inventory.findCar(carID)==null){
                System.out.println("Please enter valid Car ID");
            }
        }while (Inventory.findCar(carID)==null);
        Car car = Inventory.findCar(carID);
        Inventory.deleteCar(car);
        System.out.println(car.getCarID() + " " + car.getModel() + " has been removed" );
    }

    private void changeCarStatus(){
        String carID;
        Car.carStatus status = null;
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
        int num = input.getIntInput("Enter:Input");

        switch (num){
            case 1 -> status = Car.carStatus.AVAILABLE;
            case 2 -> status = Car.carStatus.RENTED;
            case 3 -> status = Car.carStatus.MAINTENANCE;
            case 4 -> status = Car.carStatus.PENDING;
            default -> System.out.println("Invalid choice");
        }
        Inventory.changeCarStatus(status,Inventory.findCar(carID));
        System.out.println();
    }

}
