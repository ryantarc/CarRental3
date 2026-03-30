package Vehicles;

import java.util.ArrayList;
import java.io.*;

public class InventoryManagement {
    private ArrayList<Car> Inventory;
    private final String dataFile = "inventory.dat";
    private static int count = 0;

    public InventoryManagement() throws IOException {
        Inventory = new ArrayList<>();
        loadfromFile();

    }

    public void addCar(Car car){
        Inventory.add(car);
        savetoFile(Inventory);
        count++;
    }

    public void savetoFile(ArrayList<Car> Inventory) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            for (Car car : Inventory) {
                oos.writeObject(car);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void loadfromFile() throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))){
            for (int i = 0; i<getCount();i++){
                System.out.println(ois.readObject());
                Inventory.add((Car)ois.readObject());
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e){
            System.out.println("Error");
        }
    }
    public static int getCount(){
        return count;
    }
}


