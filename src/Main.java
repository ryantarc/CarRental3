
import Managers.InventoryManager;
import Managers.ReportsManager;
import Menu.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner (System.in);
        InventoryManager Inventory = new InventoryManager();
        ReportsManager reportsManager = new ReportsManager();
        MainMenu mainMenu = new MainMenu(scanner, Inventory, reportsManager);

        mainMenu.start();

        //add return system
        //add reservation system
        //reporting system -> date borrowed date returned fuel spent
        //^^be a separate class called report
        //add fuel tracking if possible

    }
}

//