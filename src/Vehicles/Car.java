package Vehicles;
import Vehicles.*;
import java.io.*;
import java.io.Serializable;
import java.util.Scanner;
public abstract class Car implements Serializable{
    protected String carID;
    private String model;
    private String carPlate;
    private double dailyRate;
    protected String carType;
    private boolean Status;
    private int seatingCapacity;

    public Car(String model, String carPlate, double dailyRate, boolean status, int seatingCapacity) {
        this.model = model;
        this.carPlate = carPlate;
        this.dailyRate = dailyRate;
        Status = status;
        this.seatingCapacity = seatingCapacity;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    @Override
    public String toString() {
        return String.format(
                "%-5s | %-8s | %-12s | %-10s | RM%-8.2f | %-10s | Seats:%-2d",
                carID,
                carType,
                model,
                carPlate,
                dailyRate,
                (Status ? "Available" : "Rented"),
                seatingCapacity
        );
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
}
