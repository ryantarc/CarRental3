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

    // Most rented cars
    public void showMostRentedCars() {
        HashMap<String, Integer> countMap = new HashMap<String, Integer>();

        // Count rentals per model
        for (int i = 0; i < reservation.size(); i++) {
            String model = reservation.get(i).getCarModel();
            if (countMap.containsKey(model)) {
                int count = countMap.get(model);
                countMap.put(model, countMap.getOrDefault(model, 0) + 1);
            } else {
                countMap.put(model, 1);
            }
        }

        System.out.println("\n===== MOST RENTED CARS =====");
        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            System.out.println(entry.getKey() + " rented " + entry.getValue() + " times");
        }
    }

    // Total revenue
    public void showRevenueReport() {
        double totalRevenue = 0;

        for (int i = 0; i < reservation.size(); i++) {
            Reservation r = reservation.get(i);
            totalRevenue += r.getDailyRate() * r.getRentalDays();
        }

        System.out.println("\n===== TOTAL REVENUE =====");
        System.out.println("Total Revenue: RM " + totalRevenue);
    }

    // Revenue by car type
    public void showRevenueByType() {
        HashMap<String, Double> revenueMap = new HashMap<String, Double>();

        for (int i = 0; i < reservation.size(); i++) {
            Reservation r = reservation.get(i);
            String type = r.getCarType();

            if (revenueMap.containsKey(type)) {
                double total = revenueMap.get(type);
                revenueMap.put(type, total + r.getDailyRate() * r.getRentalDays());
            } else {
                revenueMap.put(type, r.getDailyRate() * r.getRentalDays());
            }
        }

        System.out.println("\n===== REVENUE BY CAR TYPE =====");
        for (Map.Entry<String, Double> entry : revenueMap.entrySet()) {
            System.out.println(entry.getKey() + " : RM " + entry.getValue());
        }
    }
}