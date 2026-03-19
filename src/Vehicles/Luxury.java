package Vehicles;

public class Luxury extends Car {
    private boolean hasSunroof;
    private static int count = 0;
    private final String CARTYPE = "LUXURY";

    public Luxury (String model, String carPlate, double dailyRate, boolean status, int seatingCapacity,
                    boolean hasSunroof){
        super(model, carPlate, dailyRate, status, seatingCapacity);
        carType = CARTYPE;
        carID = "L" + String.format("%02d", count++);
    }
}
