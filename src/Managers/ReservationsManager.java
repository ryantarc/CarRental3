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
}