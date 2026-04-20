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

        if (inventory.isEmpty()) {
            System.out.println("No existing data. Loading defaults...");
            loadDefaultCars();
            saveToFile(); // save defaults so next run has data
        updateCarCounts();}
    }
    public void loadDefaultCars(){
        inventory.add(new Economy("Toyota Vios", "ABC1111", 150.00, Car.CarStatus.AVAILABLE, 5, 50.00));
        inventory.add(new Economy("Honda City", "DEF2222", 130.00, Car.CarStatus.AVAILABLE, 5, 40.00));
        inventory.add(new Economy("Perodua Bezza", "GHI3333", 120.00, Car.CarStatus.AVAILABLE, 5, 20.00));
        inventory.add(new Economy("Hyundai Accent", "JKL4444", 140.00, Car.CarStatus.AVAILABLE, 5, 50.00));
        inventory.add(new Economy("Nissan Almera", "MNO5555", 200.00, Car.CarStatus.AVAILABLE, 5, 30.00));
        inventory.add(new Luxury("Mercedes-Benz C-Class", "QWE1111", 400.00, Car.CarStatus.AVAILABLE, 5, false));
        inventory.add(new Luxury("BMW 5 Series", "ASD2222", 380.00, Car.CarStatus.AVAILABLE, 5, false));
        inventory.add(new Luxury("Audi A6", "ZXC3333", 380.00, Car.CarStatus.AVAILABLE, 5, true));
        inventory.add(new Luxury("Lexus ES", "RTY4444", 400.00, Car.CarStatus.AVAILABLE, 5, false));
        inventory.add(new Luxury("Honda Accord LX", "FGH5555", 300.00, Car.CarStatus.AVAILABLE, 5, true));
        inventory.add(new SUV("Toyota Fortuner", "QAZ1111", 300.00, Car.CarStatus.AVAILABLE, 7, true, true));
        inventory.add(new SUV("Honda CR-V", "WSX2222", 300.00, Car.CarStatus.AVAILABLE, 5, true, false));
        inventory.add(new SUV("Mazda CX-8", "EDC3333", 350.00, Car.CarStatus.AVAILABLE, 6, true, true));
        inventory.add(new SUV("Hyundai Santa Fe", "RVV4444", 380.00, Car.CarStatus.AVAILABLE, 7, true, true));
        inventory.add(new SUV("Proton X70", "TGB5555", 390.00, Car.CarStatus.AVAILABLE, 5, false, false));
        saveToFile();
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
                "%-5s | %-8s | %-25s | %-10s | %-10s | %-10s | %-8s | Extras\n",
                "ID", "Type", "Model", "Plate", "Rate", "Status", "Seats"
        );
        System.out.println("-------------------------------------------------------------------------------------------");
        for (Car car: inventory){
            System.out.println(car.toString());
        }
    }

    public void displayInventory(String cartype){
        System.out.printf(
                "%-5s | %-8s | %-25s | %-10s | %-10s | %-10s | %-8s | Extras\n",
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
            if (car.getCarID().equalsIgnoreCase(carID)) {
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

    public boolean isEmpty() {
        return inventory.isEmpty();
    }
}


