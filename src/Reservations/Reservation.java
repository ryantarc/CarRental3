package Reservations;

import java.io.Serializable;

public class Reservation implements Serializable {

    public enum ReservationStatus {
        ACTIVE,
        PENDING_RETURN,
        COMPLETED,
        CANCELLED
    }

    private String customerID;   // <-- NEW: links reservation to a customer
    private String carID;
    private String carType;
    private String carModel;
    private String carPlate;
    private double dailyRate;
    private int rentalDays;
    private double totalCost;
    private String reservationDate;
    private ReservationStatus status;
    private int realRentalDays;
    private double penalty;

    // Updated constructor — customerID is now the first param
    public Reservation(String customerID, String carID, String carType, String carModel,
                       String carPlate, double dailyRate, int rentalDays) {
        this.customerID = customerID;
        this.carID = carID;
        this.carType = carType;
        this.carModel = carModel;
        this.carPlate = carPlate;
        this.dailyRate = dailyRate;
        this.rentalDays = rentalDays;
        this.totalCost = dailyRate * rentalDays;
        this.reservationDate = java.time.LocalDate.now().toString();
        this.status = ReservationStatus.ACTIVE;
        this.penalty = 0;
        this.realRentalDays = rentalDays;
    }

    // Getters
    public String getCustomerID()      { return customerID; }
    public String getCarID()           { return carID; }
    public String getCarType()         { return carType; }
    public String getCarModel()        { return carModel; }
    public String getCarPlate()        { return carPlate; }
    public double getDailyRate()       { return dailyRate; }
    public int getRentalDays()         { return rentalDays; }
    public double getTotalCost()       { return totalCost; }
    public String getReservationDate() { return reservationDate; }
    public ReservationStatus getStatus() { return status; }
    public double getPenalty()         { return penalty; }
    public int getRealRentalDays()     { return realRentalDays; }

    // Setters
    public void setStatus(ReservationStatus status)   { this.status = status; }
    public void setPenalty(double penalty)             { this.penalty = penalty; }
    public void setRealDays(int realRentalDays)        { this.realRentalDays = realRentalDays; }

    @Override
    public String toString() {
        return String.format("%-8s | %-15s | %-4d | RM%-8.2f | %-14s | %-12s",
                carID, carModel, rentalDays, totalCost, status, reservationDate);
    }
}