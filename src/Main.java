
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

        mainMenu.start();



    }
}

//