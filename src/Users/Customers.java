package Users;

import Reservations.Reservation;
import java.util.ArrayList;

public class Customers extends User {
    private int drivingLicense;
    private ArrayList<Reservation> rentalHistory;
    public static int count = 1;

    public Customers(String name, String email, String password, String phoneNo, int drivingLicense) {
        super(name, email, password, phoneNo);
        this.drivingLicense = drivingLicense;
        this.rentalHistory = new ArrayList<>();
        id = "C" + String.format("%03d", count++);
    }

    public static void setCount(int count) {
        Customers.count = count;
    }

    public int getDrivingLicense() {
        return drivingLicense;
    }

    // Used for saving to file: ID|name|email|password|phone|license
    public String toFileString() {
        return String.format("%s|%s|%s|%s|%s|%d",
                getId(),
                getName(),
                getEmail(),
                getPassword(),
                getPhoneNo(),
                drivingLicense
        );
    }

    @Override
    public String toString() {
        return String.format(super.toString() + " | " + drivingLicense);
    }
}