package Managers;

import Reservations.*;
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

    public boolean displayReservations() {
        boolean hasRented = false;

        System.out.println("\n===== Reservations =====");

        for (Reservation r : reservations) {
            if (!r.isReturned()) {
                System.out.println(r);
                hasRented = true;
            }
        }

        if (!hasRented) {
            System.out.println("\nNo active reservations currently.");
        }

        return hasRented;

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

                System.out.println("Returned Processed.\n\nPenalty : RM" + penalty);
                System.out.println("Total Cost : " + (r.getTotalCost() + penalty));
                return;


            }

        }
        System.out.println("Reservations not found");

    }



    public void cancelCar(String carID, int realRentalDays, boolean damaged, boolean lowFuel){
        for (Reservation r : reservations){
            if (r.getCarID().equals(carID) && !r.isReturned()) {
                
                r.setReturned(true);

                System.out.println("Total Cost : RM0.00" );
                return;


            }

        }
        System.out.println("Reservations not found");

    }
}