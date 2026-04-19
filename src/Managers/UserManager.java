package Managers;

import Users.*;
import Vehicles.Car;
import Vehicles.Economy;
import Vehicles.Luxury;
import Vehicles.SUV;

import java.util.ArrayList;

public class UserManager {
    private ArrayList<User> users;
    private FileManager fm;
    private final String filename = "users.dat";

    public UserManager(){
        users = new ArrayList<>();
        fm = new FileManager();
        loadFromFile();
    }

    public void saveToFile(){
        fm.saveToFile(users,filename);
    }
    public void loadFromFile(){
        users = fm.loadFromFile(filename);
        if (users == null) users = new ArrayList<>();
        updateUserIDCounts();

    }

    public void addUser(User user){
        users.add(user);
        saveToFile();
    }
    public void deleteUser(User user){
        users.remove(user);
        saveToFile();
        System.out.println(user.getName());
    }

    public void displayAllUsers(){
        System.out.printf(
                "%-5s | %-15s | %-20s | %-15s | %-12s\n",
                "ID", "Name", "Email", "Phone", "Role"
        );
        System.out.println("--------------------------------------------------------------------------");
        for (User user : users){
            System.out.println(user);
        }
    }

    public void displayCustomers() {
        System.out.printf(
                "%-5s | %-15s | %-20s | %-15s | %-12s\n",
                "ID", "Name", "Email", "Phone", "Driving License"
        );
        System.out.println("--------------------------------------------------------------------------");
        for (User user : users) {
            if (user instanceof Customers) {
                System.out.println(user);
            }
        }
    }

    public void displayAdmins() {
        System.out.printf(
                "%-5s | %-15s | %-20s | %-15s \n",
                "ID", "Name", "Email", "Phone"
        );
        System.out.println("--------------------------------------------------------------------------");
        for (User user : users) {
            if (user instanceof Admin) {
                System.out.println(user);
            }
        }
    }

    private void updateUserIDCounts() {
        int maxC = 0, maxA = 0;

        for (User user : users) {
            String id = user.getId();
            if (id == null) continue;
            int num = Integer.parseInt(id.substring(1));

            switch (id.charAt(0)) {
                case 'C' -> maxC = Math.max(maxC, num);
                case 'A' -> maxA = Math.max(maxA, num);
            }
        }

        Customers.setCount(maxC + 1);
        Admin.setCount(maxA + 1);
    }

    public User findUser(String id){
        for (User user: users){
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;//not found
    }

    public Admin findAdminByCredentials(String email, String password) {
        for (User user : users) {
            if (user instanceof Admin
                    && user.getEmail().equalsIgnoreCase(email)
                    && user.getPassword().equals(password)) {
                return (Admin) user;
            }
        }
        return null;
    }

    public void editUser(User user, String name, String email, String password, String phoneNo) {
        if (!name.isEmpty())     user.setName(name);
        if (!email.isEmpty())    user.setEmail(email);
        if (!password.isEmpty()) user.setPassword(password);
        if (!phoneNo.isEmpty())  user.setPhoneNo(phoneNo);
        saveToFile();
        System.out.println(" User details updated.");
    }

    public boolean isEmpty() {
        return users.isEmpty();
    }





}
