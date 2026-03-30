package Vehicles;

public class Economy extends Car{
    private double fuelEfficiency;
    private static int count = 0;
    private final String CARTYPE = "ECONOMY";

    public Economy (String model, String carPlate, double dailyRate, boolean status, int seatingCapacity,
                    double fuelEfficiency){
        super(model, carPlate, dailyRate, status, seatingCapacity);
        carType = CARTYPE;
        carID = "E" + String.format("%02d", count++);
    }
    @Override
    public String toString() {
        return String.format(
                "%s | Fuel: %.1f km/L",
                super.toString(),
                fuelEfficiency
        );
    }
}
