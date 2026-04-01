
package Menu;
import Managers.*;
import Vehicles.*;
import java.util.Scanner;


public class CustomerMenu {

    private InventoryManager Inventory;
    private Scanner scanner;
    private InputValidators input;

    public CustomerMenu(Scanner scanner,InventoryManager Inventory) {
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
                case 1 -> ReserveCar(); //addCarUI();
                case 2 -> viewInventory();
                case 3 -> ReturnCar(); //DeleteCar();



                case 0 -> System.out.println("Exiting system...");
                default -> System.out.println("Invalid choice!");
            }

        } while (choice != 0);
    }
    private void displayMenu() {
        System.out.println("\n===== MENU =====");
        System.out.println("1. Reserve New Car");
        System.out.println("2. View Inventory");
        System.out.println("3. Return Car");
        System.out.println("0. Exit");
    }


    private void ReserveCar() {
        System.out.println("\nType of Car");
        System.out.println("1. Economy");
        System.out.println("2. Luxury");
        System.out.println("3. SUV");
        int choice = input.getIntInput("Enter choice: ");

        switch (choice){
            case 1 -> Inventory.displayInventory("ECONOMY");
            case 2 -> Inventory.displayInventory("LUXURY");
            case 3 -> Inventory.displayInventory("SUV");
            default -> {
                System.out.println("Invalid choice!");
                return;
            }
        }
        System.out.println();

        scanner.nextLine();

        System.out.print("Enter Car ID: ");
        String carIDNum = scanner.nextLine();

        Car selectedCar = Inventory.findCar(carIDNum);

        if (selectedCar == null) {
            System.out.println("Car not found!");
            return;
        }

        if (!selectedCar.isStatus()) {
            System.out.println("Car is not available for rent!");
            return;
        }

        System.out.print("Rental Length (days): ");
        int rentTime = input.getIntInput("");

        double totalCost = rentTime * selectedCar.getDailyRate();

        System.out.println("\n=== Reservation Summary ===");
        System.out.println("Car ID: " + selectedCar.getCarID());
        System.out.println("Car Type: " + selectedCar.getCarType());
        System.out.println("Model: " + selectedCar.getModel());
        System.out.println("Plate Number: " + selectedCar.getCarPlate());
        System.out.println("Daily Rate: RM" + selectedCar.getDailyRate());
        System.out.println("Rental Days: " + rentTime);
        System.out.println("Total Cost: RM" + totalCost);

        System.out.print("\nConfirm reservation? (y/n): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {

            Inventory.changeCarStatus(false,Inventory.findCar(carIDNum));
            System.out.println("Reservation confirmed! Car has been reserved.");
        } else {
            System.out.println("Reservation cancelled.");
        }
    }



    private void viewInventory() {
        System.out.println("\n===== Inventory =====");
        Inventory.displayInventory();
    }

    private void ReturnCar() { //Enos u do this part

        System.out.println("Enos DO" );
    }





}
