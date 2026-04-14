
import Managers.InventoryManager;
import Menu.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("test test Ryan");
        System.out.println("test test Hom");
        Scanner scanner = new Scanner (System.in);
        InventoryManager Inventory = new InventoryManager();
        MainMenu mainMenu = new MainMenu(scanner,Inventory);
        System.out.println("This is testing push n pull");
        mainMenu.start();


        //add return system
        //add reservation system
        //reporting system -> date borrowed date returned fuel spent
        //^^be a separate class called report
        //add fuel tracking if possible

    }
}

//