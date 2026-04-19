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

    // ================= DISPLAY ALL =================
    public void displayReservations() {
        printTableHeader();
        if (hasReservations()) {
            for (Reservation r : reservations) {
                System.out.println(r);
            }
        } else {
            System.out.println("\n No reservations found.");
        }
    }

    // Display filtered by status (all customers — for staff use)
    public void displayReservations(Reservation.ReservationStatus status) {
        printTableHeader();
        boolean found = false;
        for (Reservation r : reservations) {
            if (r.getStatus() == status) {
                System.out.println(r);
                found = true;
            }
        }
        if (!found) System.out.println("\n No reservations with status: " + status);
    }

    // ================= DISPLAY BY CUSTOMER =================
    // Display a specific customer's reservations filtered by status
    public void displayReservationsByCustomer(String customerID, Reservation.ReservationStatus status) {
        printTableHeader();
        boolean found = false;
        for (Reservation r : reservations) {
            if (r.getCustomerID().equals(customerID) && r.getStatus() == status) {
                System.out.println(r);
                found = true;
            }
        }
        if (!found) System.out.println("\n No reservations found.");
    }

    // ================= HAS RESERVATIONS =================
    public boolean hasReservations() {
        return !reservations.isEmpty();
    }

    public boolean hasReservations(Reservation.ReservationStatus status) {
        for (Reservation r : reservations) {
            if (r.getStatus() == status) return true;
        }
        return false;
    }

    // Check if a specific customer has any reservation with the given status
    public boolean hasReservationsByCustomer(String customerID, Reservation.ReservationStatus status) {
        for (Reservation r : reservations) {
            if (r.getCustomerID().equals(customerID) && r.getStatus() == status) return true;
        }
        return false;
    }

    // ================= ACTIONS =================
    public void returnCar(String carID, int realRentalDays, boolean damaged, boolean lowFuel) {
        for (Reservation r : reservations) {
            if (r.getCarID().equals(carID) && r.getStatus() == Reservation.ReservationStatus.ACTIVE) {
                double penalty = 0;
                if (realRentalDays > r.getRentalDays()) {
                    int lateDays = realRentalDays - r.getRentalDays();
                    penalty += lateDays * r.getDailyRate() * 2.00;
                }
                if (damaged) penalty += 100;
                if (lowFuel)  penalty += 50;

                r.setStatus(Reservation.ReservationStatus.PENDING_RETURN);
                r.setPenalty(penalty);
                r.setRealDays(realRentalDays);

                System.out.println(" Return processed.");
                System.out.println(" Penalty    : RM" + penalty);
                System.out.println(" Total Cost : RM" + (r.getTotalCost() + penalty));
                return;
            }
        }
    }

    public void cancelCar(String carID) {
        for (Reservation r : reservations) {
            if (r.getCarID().equals(carID) && r.getStatus() == Reservation.ReservationStatus.ACTIVE) {
                r.setStatus(Reservation.ReservationStatus.CANCELLED);
                System.out.println(" Total Cost : RM0.00");
                return;
            }
        }
    }

    public Reservation findPendingReservation(String carID) {
        for (Reservation r : reservations) {
            if (r.getCarID().equals(carID) && r.getStatus() == Reservation.ReservationStatus.PENDING_RETURN) {
                return r;
            }
        }
        System.out.println(" Reservation not found.");
        return null;
    }

    public Reservation findActiveReservation(String carID) {
        for (Reservation r : reservations) {
            if (r.getCarID().equals(carID) && r.getStatus() == Reservation.ReservationStatus.ACTIVE) {
                return r;
            }
        }
        System.out.println(" Reservation not found.");
        return null;
    }

    public void changeReservationStatus(Reservation.ReservationStatus status, Reservation reservation) {
        reservation.setStatus(status);
        saveToFile();
    }

    // ================= HELPERS =================
    private void printTableHeader() {
        System.out.printf(" %-8s | %-15s | %-4s | %-10s | %-14s | %-12s%n",
                "Car ID", "Car Model", "Days", "Total Cost", "Status", "Date");
        System.out.println(" " + "-".repeat(74));
    }
}