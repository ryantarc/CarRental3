package Managers;

import Vehicles.*;

import java.util.ArrayList;


public class InventoryManager {
    private ArrayList<Car> inventory;
    private FileManager fm;
    private final String filename = "cars.dat";

    public InventoryManager() {
        inventory = new ArrayList<>();
        fm = new FileManager();
        loadFromFile();
    }

    //redefine saveToFile to avoid human errors
    public void saveToFile(){
        fm.saveToFile(inventory,filename);
    }
    public void loadFromFile(){
        inventory=fm.loadFromFile(filename);
        updateCarCounts();
    }

    public void addCar(Car car) {
        inventory.add(car);
        saveToFile();
    }

    public void deleteCar(Car car){
        if (car.getStatus() == Car.CarStatus.RENTED) {
            System.out.println("Cannot delete car. It is currently rented.");
            return;
        }

        inventory.remove(car);
        saveToFile();
        System.out.println(car.getCarID() + " " + car.getModel() + " has been removed" );
    }

    public void displayInventory(){
        System.out.printf(
                "%-5s | %-8s | %-12s | %-10s | %-10s | %-10s | %-8s | Extras\n",
                "ID", "Type", "Model", "Plate", "Rate", "Status", "Seats"
        );
        System.out.println("-------------------------------------------------------------------------------------------");
        for (Car car: inventory){
            System.out.println(car.toString());
        }
    }

    public void displayInventory(String cartype){
        System.out.printf(
                "%-5s | %-8s | %-12s | %-10s | %-10s | %-10s | %-8s | Extras\n",
                "ID", "Type", "Model", "Plate", "Rate", "Status", "Seats"
        );
        System.out.println("-------------------------------------------------------------------------------------------");
        for (Car car: inventory){
            if (car.getCarType().equals(cartype)){
                System.out.println(car.toString());
            }
        }
        System.out.println("Press Enter to continue\n\n");
    }

    public void changeCarStatus(Car.CarStatus status, Car car){
        car.setStatus(status); //fix this later
        saveToFile();
    }

    //!!!!! USEFUL METHOD can use when doing reservation and blablabla to find car
    public Car findCar(String carID){
        for (Car car: inventory){
            if (car.getCarID().equals(carID)) {
                return car;
            }
        }
        return null;//not found
    }


    public boolean checkCarStatus(String carID) {
        Car car = findCar(carID);

        if (car == null) {
            System.out.println("Car not found!");
            return false;
        }

        if (car.getStatus()!= Car.CarStatus.AVAILABLE) { //FIX IT
            System.out.println("Car is currently not available for rent.");
            return false;
        }

        System.out.println("Car is available for rent.");
        return true;
    }

    private void updateCarCounts() {
        int maxE = 0, maxS = 0, maxL = 0;

        for (Car car : inventory) {
            String id = car.getCarID();
            int num = Integer.parseInt(id.substring(1));

            switch (id.charAt(0)) {
                case 'E' -> maxE = Math.max(maxE, num);
                case 'S' -> maxS = Math.max(maxS, num);
                case 'L' -> maxL = Math.max(maxL, num);
            }
        }

        Economy.setCount(maxE + 1);
        SUV.setCount(maxS + 1);
        Luxury.setCount(maxL + 1);
    }
}


