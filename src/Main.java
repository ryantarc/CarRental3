
import Vehicles.*;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        InventoryManagement Inventory = new InventoryManagement();
        Inventory.addCar(new SUV("Ativa","A033",10,true,5,true,false));
        Inventory.addCar(new Economy("Ativa","A032",10,true,5,5));
        Inventory.addCar(new Luxury("Ativa","A031",10,true,5,true));
        Inventory.addCar(new SUV("Ativa","A030",10,true,5,true,false));

        System.out.println("This is me trying to connect from PC");
        System.out.println("testing out branches!");
    }
}