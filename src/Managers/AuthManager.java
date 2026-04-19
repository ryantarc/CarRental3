package Managers;

import Users.Customers;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AuthManager {

    private static final String FILE_PATH = "customers.txt";
    private List<Customers> customers = new ArrayList<>();
    private Customers loggedInCustomer = null;

    public AuthManager() {
        loadFromFile();
    }

    // ================= SIGN UP =================
    public Customers signUp(String name, String email, String password, String phoneNo, String drivingLicense) {
        if (findByEmail(email) != null) {
            return null; // email already in use
        }

        Customers newCustomer = new Customers(name, email, password, phoneNo, drivingLicense);
        customers.add(newCustomer);
        saveToFile();
        loggedInCustomer = newCustomer; // auto-login after sign up
        return newCustomer;
    }

    // ================= LOGIN =================
    public Customers login(String email, String password) {
        for (Customers c : customers) {
            if (c.getEmail().equalsIgnoreCase(email) && c.getPassword().equals(password)) {
                loggedInCustomer = c;
                return c;
            }
        }
        return null;
    }

    // ================= LOGOUT =================
    public void logout() {
        loggedInCustomer = null;
    }

    public Customers getLoggedInCustomer() {
        return loggedInCustomer;
    }

    public boolean isLoggedIn() {
        return loggedInCustomer != null;
    }

    // ================= HELPERS =================
    private Customers findByEmail(String email) {
        for (Customers c : customers) {
            if (c.getEmail().equalsIgnoreCase(email)) {
                return c;
            }
        }
        return null;
    }

    // ================= FILE I/O =================
    public void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Customers c : customers) {
                pw.println(c.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving customers: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            int maxCount = 0;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 6) continue;

                String id          = parts[0].trim();
                String name        = parts[1].trim();
                String email       = parts[2].trim();
                String password    = parts[3].trim();
                String phoneNo     = parts[4].trim();
                String license     = parts[5].trim(); // String, not int

                int num = Integer.parseInt(id.substring(1));
                if (num > maxCount) maxCount = num;

                Customers.count = num;
                Customers c = new Customers(name, email, password, phoneNo, license);
                c.setId(id);
                customers.add(c);
            }

            Customers.setCount(maxCount + 1);

        } catch (IOException e) {
            System.out.println("Error loading customers: " + e.getMessage());
        }
    }
}