package Vehicles;

public class Luxury extends Car {
    private boolean hasSunroof;
    private static int count = 1;
    private final String CARTYPE = "LUXURY";

    public Luxury (String model, String carPlate, double dailyRate, CarStatus status, int seatingCapacity,
                   boolean hasSunroof){
        super(model, carPlate, dailyRate, status, seatingCapacity);
        carType = CARTYPE;
        carID = "L" + String.format("%02d", count++);
    }

    public static void setCount(int value) {
        count = value;
    }

    @Override
    public String toString() {
        return String.format(
                "%s | Sunroof:%-5s",
                super.toString(),
                hasSunroof ? "Yes" : "No"
        );
    }
}
