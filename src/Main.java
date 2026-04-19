
import Managers.InventoryManager;
import Managers.ReportsManager;
import Managers.UserManager;
import Menu.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("test test Ryan");
        System.out.println("test test Hom");
        Scanner scanner = new Scanner (System.in);
        InventoryManager Inventory = new InventoryManager();
        ReportsManager reportsManager = new ReportsManager();
        UserManager userManager = new UserManager();
        MainMenu mainMenu = new MainMenu(scanner,Inventory, reportsManager, userManager);
        System.out.println("This is testing push n pull");
        mainMenu.start();


        //add return system
        //add reservation system
        //reporting system -> date borrowed date returned fuel spent
        //^^be a separate class called report
        //add fuel tracking if possible
        //fix persistency issue in car_ID


    }
}

//