
import Menu.*;
import Vehicles.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Vehicles.*;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner (System.in);
        InventoryManagement Inventory = new InventoryManagement();
        MainMenu mainMenu = new MainMenu(scanner,Inventory);
        System.out.println("This is testing push n pull");
        mainMenu.start();


        //add booking system
        //add reservation system
        //reporting system -> date borrowed date returned fuel spent
        //^^be a separate class called report
        //add fuel tracking if possible
        //fix persistency issue in car_ID


    }
}

//