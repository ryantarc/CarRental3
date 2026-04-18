package Users;

public class Customers extends User{
    private int drivingLicense;
    private int contactInfo;
    public static int count = 1;

    public Customers (String name, int drivingLicense, int contactInfo){
        super(name);
        this.drivingLicense = drivingLicense;
        this.contactInfo = contactInfo;
        id = "C" + String.format("%03d", count++);
    }

    public String toString(){
        return String.format("bleh");
    }
}
