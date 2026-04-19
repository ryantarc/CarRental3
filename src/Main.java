
import Managers.InventoryManager;
import Managers.ReportsManager;
import Managers.UserManager;
import Menu.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner (System.in);
        InventoryManager Inventory = new InventoryManager();
        ReportsManager reportsManager = new ReportsManager();
        UserManager userManager = new UserManager();
        MainMenu mainMenu = new MainMenu(scanner,Inventory, reportsManager, userManager);
        mainMenu.start();
    }
}

//