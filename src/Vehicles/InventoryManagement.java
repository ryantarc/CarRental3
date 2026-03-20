package Vehicles;

import java.util.ArrayList;
import java.io.*;

public class InventoryManagement {
    private ArrayList<Car> Inventory;

    public InventoryManagement (){
        Inventory = new ArrayList<>();

    }

    public void addCar(Car car){
        Inventory.add(car);
        savetoFile(Inventory);
    }

    public void savetoFile(ArrayList<Car> Inventory){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("inventory.dat"))) {
            for (Car car:Inventory){
                oos.writeObject(car);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadFromFile(ArrayList<Car> Inventory) {
  //      try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream("inventory.dat"))) {

     //   } catch (IOException e) {
     //       throw new RuntimeException(e);
        }
    }


