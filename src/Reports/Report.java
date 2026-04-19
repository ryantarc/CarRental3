package Reports;

import Reservations.Reservation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Report {

    private ArrayList<Reservation> reservation;

    public Report(ArrayList<Reservation> reservations) {
        this.reservation = reservations;
    }

    public void showFullReport() {
        printHeader();
        printRentalTable();
        printSummary();
    }

    private void printRentalTable() {
        printTableHeader();
        if (reservation.isEmpty()) {
            System.out.println(" No rental records found.");
        } else {
            for (Reservation r : reservation) {
                double revenue = r.getDailyRate() * r.getRentalDays();
                System.out.printf(" %-8s | %-15s | %-10s | %-8s | %-10s | %-12s | %-14s%n",
                        r.getCarID(),
                        r.getCarModel(),
                        r.getCarType(),
                        r.getRentalDays() + " days",
                        String.format("RM%.2f", r.getDailyRate()),
                        String.format("RM%.2f", revenue),
                        r.getStatus()
                );
            }
        }
        System.out.println(" " + "=".repeat(95));
    }

    private void printTableHeader() {
        System.out.println(" " + "=".repeat(95));
        System.out.printf(" %-8s | %-15s | %-10s | %-8s | %-10s | %-12s | %-14s%n",
                "Car ID", "Car Model", "Type", "Duration", "Daily Rate", "Revenue", "Status");
        System.out.println(" " + "-".repeat(95));
    }

    private void printSummary() {
        double totalRevenue = 0;
        HashMap<String, Double> revenueByType = new HashMap<>();
        HashMap<String, Integer> countByModel = new HashMap<>();

        for (Reservation r : reservation) {
            // Only count COMPLETED rentals in summary
            if (r.getStatus() != Reservation.ReservationStatus.COMPLETED) continue;

            double revenue = r.getDailyRate() * r.getRentalDays();
            totalRevenue += revenue;

            String type = r.getCarType();
            revenueByType.put(type, revenueByType.getOrDefault(type, 0.0) + revenue);

            String model = r.getCarModel();
            countByModel.put(model, countByModel.getOrDefault(model, 0) + 1);
        }

        System.out.println("\n " + "=".repeat(40));
        System.out.println("          REVENUE SUMMARY");
        System.out.println(" " + "=".repeat(40));
        System.out.printf(" %-20s : RM%.2f%n", "Total Revenue", totalRevenue);
        System.out.println(" " + "-".repeat(40));
        System.out.println(" Revenue by Car Type:");
        if (revenueByType.isEmpty()) {
            System.out.println("  No completed rentals yet.");
        } else {
            for (Map.Entry<String, Double> entry : revenueByType.entrySet()) {
                System.out.printf("  %-19s : RM%.2f%n", entry.getKey(), entry.getValue());
            }
        }
        System.out.println(" " + "-".repeat(40));
        System.out.println(" Rental Count by Model:");
        if (countByModel.isEmpty()) {
            System.out.println("  No completed rentals yet.");
        } else {
            for (Map.Entry<String, Integer> entry : countByModel.entrySet()) {
                System.out.printf("  %-19s : %d rental(s)%n", entry.getKey(), entry.getValue());
            }
        }
        System.out.println(" " + "=".repeat(40));
    }

    private void printHeader() {
        System.out.println("\n " + "=".repeat(95));
        System.out.println("                                        REPORT");
        System.out.println(" " + "=".repeat(95));
    }
}