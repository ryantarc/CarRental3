package Menu;

import Managers.InventoryManager;
import Managers.ReportsManager;
import Managers.ReservationsManager;
import java.util.Scanner;

public class MainMenu {
    private AdminMenu adminMenu;
    private CustomerMenu customerMenu;
    private Scanner scanner;
    private InventoryManager inventory;
    private InputValidators input;
    private ReservationsManager reservations;

    public MainMenu(Scanner scanner, InventoryManager inventory, ReportsManager reportsManager) {
        this.scanner = scanner;
        this.inventory = inventory;
        input = new InputValidators();
        reservations = new ReservationsManager(inventory);
        adminMenu = new AdminMenu(scanner, inventory, reportsManager, reservations);
        customerMenu = new CustomerMenu(scanner, inventory, reservations);
    }

    public void start() {
        userSelection();
    }

    // ================= LOGIN =================
    public void login() {
        printHeader("ADMIN LOGIN");

        System.out.print(" Username : ");
        String username = scanner.nextLine();

        System.out.print(" Password : ");
        String password = scanner.nextLine();

        if (username.equals("admin") && password.equals("password")) {
            System.out.println("Login successful");
            pause();
            adminMenu.start();
        } else {
            System.out.println("Invalid credentials");
            pause();
        }
    }

    // ================= MAIN MENU =================
    public void userSelection() {
        int num;

        do {
            printHeader("MAIN MENU");

            System.out.println(" 1. Customer");
            System.out.println(" 2. Admin");
            System.out.println(" 0. Exit");
            System.out.println("__________________________________");

            num = input.getIntInput(" Select option: ");

            switch (num) {
                case 1 -> {
                    System.out.println("Entering Customer Menu...");
                    pause();
                    customerMenu.start();
                }
                case 2 -> login();
                case 0 -> System.out.println("Exiting system...");
                default -> System.out.println("Invalid option. Please choose 0-2.");
            }

        } while (num != 0);
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