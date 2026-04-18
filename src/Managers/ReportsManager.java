package Managers;

import Reports.*;
import Reservations.Reservation;
import java.util.ArrayList;

public class ReportsManager {
    private ArrayList<Report> reports;
    private FileManager fm;
    private final String filename = "reports.dat";

    public ReportsManager(){
        reports = new ArrayList<>();
        fm = new FileManager();
        loadFromFile();
    }

    public void generateReport(ArrayList<Reservation> reservations) {
        Report report = new Report(reservations);
        report.showMostRentedCars();
        report.showRevenueReport();
        report.showRevenueByType();
    }

    public void loadFromFile(){
        reports = fm.loadFromFile(filename);
    }

    public void saveToFile(){
        fm.saveToFile(reports, filename);
    }
}