package Managers;

import Reservations.*;
import java.util.ArrayList;
import java.io.*;


public class ReservationsManager {
    private ArrayList<Reservation> reservations;
    private final FileManager fm;
    private final String filename = "reservations.dat";

    public ReservationsManager(){
        reservations = new ArrayList<>();
        fm = new FileManager();
        loadFromFile();

    }
    public void loadFromFile(){
        reservations = fm.loadFromFile(filename);
    }
    public void saveToFile(){
        fm.saveToFile(reservations,filename);
    }
}
