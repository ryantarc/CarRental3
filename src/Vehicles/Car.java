package Vehicles;
import java.io.Serializable;


public abstract class Car implements Serializable{

    public enum CarStatus {
        RENTED,
        PENDING,
        AVAILABLE,
        MAINTENANCE,
    }

    protected String carID;
    private String model;
    private String carPlate;
    private double dailyRate;
    protected String carType;
    private CarStatus Status;
    private int seatingCapacity;

    public Car(String model, String carPlate, double dailyRate, CarStatus status, int seatingCapacity) {
        this.model = model;
        this.carPlate = carPlate;
        this.dailyRate = dailyRate;
        this.Status = status;
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

    public CarStatus getStatus() {
        return Status;
    }

    public void setStatus(CarStatus status) {
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
                Status,
                seatingCapacity
        );
    }




}
