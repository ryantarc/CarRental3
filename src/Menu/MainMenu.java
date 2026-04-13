package Menu;
import Managers.InventoryManager;
import Managers.ReservationsManager;

import java.util.Scanner;
public class MainMenu {
    private AdminMenu adminMenu;
    private CustomerMenu customerMenu;
    private Scanner scanner;
    private InventoryManager inventory;
    private InputValidators input;
    private ReservationsManager reservations;

    public MainMenu(Scanner scanner, InventoryManager inventory){
        this.scanner = scanner;
        this.inventory = inventory;
        adminMenu = new AdminMenu(scanner,inventory);
        input = new InputValidators();
        reservations = new ReservationsManager();
        customerMenu = new CustomerMenu(scanner,inventory,reservations);
    }
    public void start () {
        userSelection();
    }
    public void userSelection(){
        int num;
        do {
            System.out.println("=====USER SELECTION=====");
            System.out.println("1. User");
            System.out.println("2. Admin");
            System.out.println("0. Quit");
            num = input.getIntInput("Enter corresponding number: ");

            switch(num){
                case 1 -> customerMenu.start();
                case 2 -> adminMenu.start();
                case 0 -> System.out.println("Exiting System");
                default -> System.out.println("Invalid number, please enter within range 1-2");
            }
        }while (num!=0);
    }
}
