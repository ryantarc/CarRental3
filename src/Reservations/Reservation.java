package Reservations;

import java.io.Serializable;

public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String carID;
    private String carType;
    private String carModel;
    private String carPlate;
    private double dailyRate;
    private int rentalDays;
    private double totalCost;
    private String reservationDate;
    
    // Constructor
    public Reservation(String carID, String carType, String carModel, 
                      String carPlate, double dailyRate, int rentalDays) {
        this.carID = carID;
        this.carType = carType;
        this.carModel = carModel;
        this.carPlate = carPlate;
        this.dailyRate = dailyRate;
        this.rentalDays = rentalDays;
        this.totalCost = dailyRate * rentalDays;
        this.reservationDate = java.time.LocalDate.now().toString();
    }
    
    // Getters
    public String getCarID() { return carID; }
    public String getCarType() { return carType; }
    public String getCarModel() { return carModel; }
    public String getCarPlate() { return carPlate; }
    public double getDailyRate() { return dailyRate; }
    public int getRentalDays() { return rentalDays; }
    public double getTotalCost() { return totalCost; }
    public String getReservationDate() { return reservationDate; }
    
    @Override
    public String toString() {
        return "Car: " + carModel + " | Days: " + rentalDays + 
               " | Total: RM" + totalCost + " | Date: " + reservationDate;
    }
}