package Managers;

import Reservations.*;
import Vehicles.Car;

import java.util.ArrayList;

public class ReservationsManager {
    private ArrayList<Reservation> reservations;
    private final FileManager fm;
    private final String filename = "reservations.dat";

    public ReservationsManager(InventoryManager inventory) {
        reservations = new ArrayList<>();
        fm = new FileManager();
        loadFromFile(inventory);
    }
    
    public void loadFromFile(InventoryManager inventory) {
        reservations = fm.loadFromFile(filename);
        if (reservations == null) {
            reservations = new ArrayList<>();
        }
        reservations.removeIf(r -> inventory.findCar(r.getCarID()) == null);

        saveToFile();
    }
    
    public void saveToFile() {
        fm.saveToFile(reservations, filename);
    }
    
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        saveToFile();
    }

    public ArrayList<Reservation> getAllReservations() {
        return reservations;
    }

    public void displayReservations() {
        System.out.printf("%-8s | %-15s | %-4s | %-10s | %-8s | %-12s%n",
                "Car ID", "Car Model", "Days", "Total Cost", "Status", "Date");
        System.out.println("-----------------------------------------------------------------------");
        if (hasReservations()){
            for (Reservation r : reservations) {
                System.out.println(r);
            }
        }
        else {
            System.out.println("\nNo Reservations.");
        }
    }

    public void displayReservations(Reservation.ReservationStatus status) {
        System.out.printf("%-8s | %-15s | %-4s | %-10s | %-8s | %-12s%n",
                "Car ID", "Car Model", "Days", "Total Cost", "Status", "Date");
        System.out.println("-----------------------------------------------------------------------");
        if (hasReservations()){
            for (Reservation r : reservations) {
                if (r.getStatus()== status) {
                    System.out.println(r);
                }
            }
        }
        else {
            System.out.println("\nNo active reservations currently.");
        }
    }

    public boolean hasReservations(){
        if (reservations.isEmpty()){
            return false;
        }
        return true;
    }

    public boolean hasReservations(Reservation.ReservationStatus status){
        for (Reservation r: reservations){
            if (r.getStatus() == status){
                return true;
            }
        }
        return false;
    }

    public void returnCar(String carID, int realRentalDays, boolean damaged, boolean lowFuel){
        for (Reservation r : reservations){
            if (r.getCarID().equals(carID) && r.getStatus()== Reservation.ReservationStatus.ACTIVE) {
                double penalty = 0;
                if (realRentalDays > r.getRentalDays()) {
                    int lateDays = realRentalDays - r.getRentalDays();
                    penalty += lateDays * r.getDailyRate() * 2.00;
                }
                if (damaged) {
                    penalty += 100;
                }
                if (lowFuel) {
                    penalty += 50;
                }

                r.setStatus(Reservation.ReservationStatus.PENDING_RETURN);
                r.setPenalty(penalty);
                r.setRealDays(realRentalDays);

                System.out.println("Returned Processed.\n\nPenalty : RM" + penalty);
                System.out.println("Total Cost : " + (r.getTotalCost() + penalty));
                return;
            }
        }
    }

    public void cancelCar(String carID){
        for (Reservation r : reservations){
            if (r.getCarID().equals(carID) && r.getStatus()== Reservation.ReservationStatus.ACTIVE) {
                r.setStatus(Reservation.ReservationStatus.CANCELLED);
                System.out.println("Total Cost : RM0.00" );
                return;
            }
        }
    }

    public Reservation findPendingReservation(String CarID){
        for (Reservation r:reservations){
            if (r.getCarID().equals(CarID) && r.getStatus()== Reservation.ReservationStatus.PENDING_RETURN ){
                return r;
            }
        }
        System.out.println("Reservations not found");
        return null;
    }

    public Reservation findActiveReservation(String CarID){
        for (Reservation r:reservations){
            if (r.getCarID().equals(CarID) && r.getStatus()== Reservation.ReservationStatus.ACTIVE){
                return r;
            }
        }
        System.out.println("Reservations not found");
        return null;
    }


    public void changeReservationStatus(Reservation.ReservationStatus status, Reservation reservation){
        reservation.setStatus(status); //fix this later
        saveToFile();
    }

}