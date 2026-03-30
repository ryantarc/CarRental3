
import Menu.AdminMenu;
import Vehicles.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import Vehicles.*;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        InventoryManagement Inventory = new InventoryManagement();
        AdminMenu Menu = new AdminMenu();
        Inventory.loadFromFile();
        Menu.start();
        Inventory.displayInventory();


    }
}

//