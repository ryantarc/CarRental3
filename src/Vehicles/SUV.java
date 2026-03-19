package Vehicles;

public class SUV extends Car{
    private boolean is4WD;
    private boolean thirdRowSeating;
    private static int count = 0;
    private final String CARTYPE = "SUV";

    public SUV (String model, String carPlate, double dailyRate, boolean status, int seatingCapacity,
                   boolean is4WD,boolean thirdRowSeating){
        super(model, carPlate, dailyRate, status, seatingCapacity);
        this.thirdRowSeating = thirdRowSeating;
        this.is4WD = is4WD;
        carType = CARTYPE;
        carID = "S" + String.format("%02d", count++);
    }

}
