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
    private boolean returned;
    private int realRentalDays;
    private double penalty;

    
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
        this.returned = false;
        this.penalty = 0;
        this.realRentalDays = rentalDays;
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
    public boolean isReturned() { return returned; }

    public void setReturned(boolean returned) { this.returned = returned; }
    public void setPenalty(double penalty) { this.penalty = penalty; }
    public void setRealDays(int realRentalDays) { this.realRentalDays = realRentalDays; }

    
    @Override
    public String toString() {
        return "Car ID: " + carID + " | Car: " + carModel + " | Days: " + rentalDays +
               " | Total: RM" + totalCost + " | Date: " + reservationDate;
    }
}