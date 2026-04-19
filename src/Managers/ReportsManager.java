package Managers;

import Reports.Report;
import Reservations.Reservation;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ReportsManager {
    private final String reportFolder = "reports/";

    public ReportsManager() {
        new File(reportFolder).mkdirs();
    }

    // Display one unified table for all cars
    public void generateReport(ArrayList<Reservation> reservations) {
        if (reservations.isEmpty()) {
            System.out.println(" No reports available.");
            return;
        }
        Report report = new Report(reservations);
        report.showFullReport();
    }

    public void updateReport(String carID, ArrayList<Reservation> allReservations) {
        ArrayList<Reservation> carReservations = new ArrayList<>();
        for (Reservation r : allReservations) {
            if (r.getCarID().equals(carID)) {
                carReservations.add(r);
            }
        }
        saveReportToFile(carID, carReservations);
    }

    public void deleteReport(String carID, ReservationsManager reservationsManager) {
        ArrayList<Reservation> all = reservationsManager.getAllReservations();
        boolean found = all.removeIf(r -> r.getCarID().equals(carID));
        if (found) {
            reservationsManager.saveToFile();
            System.out.println(" Report entries for " + carID + " have been deleted.");
        } else {
            System.out.println(" No entries found for Car ID: " + carID);
        }
    }

    public void listReports() {
        File folder = new File(reportFolder);
        File[] files = folder.listFiles((dir, name) -> name.endsWith("_report.dat"));
        if (files == null || files.length == 0) {
            System.out.println(" No report files found.");
            return;
        }
        System.out.println("\n ===== EXISTING REPORTS =====");
        for (File f : files) {
            String carID = f.getName().replace("_report.dat", "");
            System.out.println(" - " + carID);
        }
        System.out.println(" " + "=".repeat(30));
    }

    // ================= FILE I/O =================
    private void saveReportToFile(String carID, ArrayList<Reservation> reservations) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(reportFolder + carID + "_report.dat"))) {
            oos.writeObject(reservations);
        } catch (IOException e) {
            System.out.println(" Error saving report for " + carID + ": " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Reservation> loadReportFromFile(String carID) {
        File file = new File(reportFolder + carID + "_report.dat");
        if (!file.exists()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<Reservation>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(" Error loading report for " + carID + ": " + e.getMessage());
            return null;
        }
    }
}