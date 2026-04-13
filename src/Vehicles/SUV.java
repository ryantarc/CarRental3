package Vehicles;

import java.util.Scanner;

public class SUV extends Car{
    private boolean is4WD;
    private boolean thirdRowSeating;
    private static int count = 1;
    private final String CARTYPE = "SUV";

    public SUV (String model, String carPlate, double dailyRate, Car.carStatus status, int seatingCapacity,
                   boolean is4WD,boolean thirdRowSeating){
        super(model, carPlate, dailyRate, status, seatingCapacity);
        this.thirdRowSeating = thirdRowSeating;
        this.is4WD = is4WD;
        carType = CARTYPE;
        carID = "S" + String.format("%02d", count++);
    }
    @Override
    public String toString() {
        return String.format(
                "%s | 4WD:%-5s | 3rdRow:%-5s",
                super.toString(),
                is4WD ? "Yes" : "No",
                thirdRowSeating ? "Yes" : "No"
        );
    }

}
