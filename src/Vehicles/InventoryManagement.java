package Vehicles;

import java.util.ArrayList;

public class InventoryManagement {
    private ArrayList<Car> Inventory;
    public InventoryManagement (){
        Inventory = new ArrayList<>();
    }
    public void addCar(Car car){
        Inventory.add(car);
    }

}
