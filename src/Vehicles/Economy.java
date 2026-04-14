package Vehicles;

public class Economy extends Car{
    private double fuelEfficiency;
    private static int count = 1;
    private final String CARTYPE = "ECONOMY";

    public Economy (String model, String carPlate, double dailyRate, Car.carStatus status, int seatingCapacity,
                    double fuelEfficiency){
        super(model, carPlate, dailyRate, status, seatingCapacity);
        this.fuelEfficiency = fuelEfficiency;
        this.carType = CARTYPE;
        carID = "E" + String.format("%02d", count++);
    }

    public static void setCount(int value) {
        count = value;
    }


    @Override
    public String toString() {
        return String.format(
                "%s | Fuel Efficiency: %.2f km/L",
                super.toString(),
                fuelEfficiency
        );
    }
}
