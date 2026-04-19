package Menu;

import Managers.InventoryManager;
import Managers.ReportsManager;
import Managers.ReservationsManager;
import Users.Admin;
import Managers.UserManager;
import Managers.AuthManager;

import java.util.Scanner;

public class MainMenu {
    private AdminMenu adminMenu;
    private CustomerMenu customerMenu;
    private Scanner scanner;
    private InventoryManager inventory;
    private InputValidators input;
    private ReservationsManager reservations;
    private UserManager userManager;

    private static final String SUPER_ADMIN_EMAIL = "admin";
    private static final String SUPER_ADMIN_PASSWORD = "password";

    public MainMenu(Scanner scanner, InventoryManager inventory, ReportsManager reportsManager, UserManager userManager) {
        this.scanner = scanner;
        this.inventory = inventory;
        this.userManager = userManager;
        input = new InputValidators();
        reservations = new ReservationsManager(inventory);
        AuthManager authManager = new AuthManager();
        adminMenu = new AdminMenu(scanner, inventory, reportsManager, reservations, userManager, authManager);
        customerMenu = new CustomerMenu(scanner, inventory, reservations);
    }

    public void start() {
        userSelection();
    }

    // ================= LOGIN =================
    public void login() {
        printHeader("ADMIN LOGIN");

        System.out.print(" Email    : ");
        String email = scanner.nextLine().trim();

        System.out.print(" Password : ");
        String password = scanner.nextLine();

        if (email.equals(SUPER_ADMIN_EMAIL) && password.equals(SUPER_ADMIN_PASSWORD)) {
            System.out.println("\n Welcome, Super Admin!");
            pause();
            adminMenu.start("Super Admin");
            return;
        }

        Admin admin = userManager.findAdminByCredentials(email, password);
        if (admin != null) {
            System.out.println("\n Welcome, " + admin.getName() + "! (" + admin.getId() + ")");
            pause();
            adminMenu.start(admin.getName());
            return;
        }

        System.out.println("\n Invalid credentials.");
        pause();
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