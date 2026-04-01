package Managers;

import Vehicles.Car;

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

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
    }

    public void addCar(Car car) {
        inventory.add(car);
        saveToFile();
    }

    public void deleteCar(Car car){
        inventory.remove(car);
        saveToFile();
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

    public void addCarUI() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("=== Add New Car ===");

            System.out.print("Enter model: ");
            String model = scanner.nextLine();

            System.out.print("Enter plate number: ");
            String plate = scanner.nextLine();

            System.out.print("Enter daily rate: ");
            double rate = scanner.nextDouble();

            System.out.print("Is available? (true/false): ");
            boolean status = scanner.nextBoolean();

            System.out.print("Enter seating capacity: ");
            int seats = scanner.nextInt();

    }

    public void changeCarStatus(boolean status, Car car){
        car.setStatus(status);
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

        if (!car.isStatus()) {
            System.out.println("Car is currently not available for rent.");
            return false;
        }

        System.out.println("Car is available for rent.");
        return true;
    }


}


