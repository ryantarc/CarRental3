package Vehicles;

import java.util.ArrayList;
import java.io.*;

public class InventoryManagement {
    private ArrayList<Car> inventory;

    public InventoryManagement() {
        inventory = new ArrayList<>();

    }

    public void addCar(Car car) {
        inventory.add(car);
        saveToFile();
    }

    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("cars.dat"))) {
            oos.writeObject(inventory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("cars.dat"))) {
            inventory = (ArrayList<Car>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            inventory = new ArrayList<>(); // fallback
        }
    }
    public void displayInventory(){
        System.out.println(inventory.size());
        System.out.printf(
                "%-5s | %-8s | %-12s | %-10s | %-10s | %-10s | %-8s | Extras\n",
                "ID", "Type", "Model", "Plate", "Rate", "Status", "Seats"
        );
        System.out.println("-------------------------------------------------------------------------------------------");
        for (Car car: inventory){
            System.out.println(car.toString());
        }
    }
}


