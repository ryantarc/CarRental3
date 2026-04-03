package Managers;

import Reservations.*;
import java.util.ArrayList;

public class ReservationsManager {
    private ArrayList<Reservation> reservations;
    private final FileManager fm;
    private final String filename = "reservations.dat";

    public ReservationsManager() {
        reservations = new ArrayList<>();
        fm = new FileManager();
        loadFromFile();
    }
    
    public void loadFromFile() {
        reservations = fm.loadFromFile(filename);
        if (reservations == null) {
            reservations = new ArrayList<>();
        }
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
        if (reservations.isEmpty()) {
            System.out.print("No Reservation has been made yet");
        }
        else {
            System.out.println("===== Reservations =====");

            for (Reservation r : reservations) {
                if (!r.isReturned()) {
                    System.out.println(r);
                }
            }
        }
    }


    public void returnCar(String carID, int realRentalDays, boolean damaged, boolean lowFuel){
        for (Reservation r : reservations){
            if (r.getCarID().equals(carID) && !r.isReturned()) {
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

                r.setReturned(true);
                r.setPenalty(penalty);
                r.setRealDays(realRentalDays);

                System.out.println("Returned Processed. Penalty : RM" + penalty);
                return;


            }

        }
        System.out.println("Reservations not found");

    }
}