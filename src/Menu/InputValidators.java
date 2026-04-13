package Menu;
import Vehicles.Car;

import java.util.Scanner;

public class InputValidators {
    private Scanner scanner;
    public InputValidators () {
        scanner = new Scanner(System.in);
    }

    //input validation methods
    public int getIntInput(String message) {
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input! Enter a number.");
                scanner.nextLine();
            }
        }
    }

    public double getDoubleInput(String message) {
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextDouble();
            } catch (Exception e) {
                System.out.println("Invalid input! Enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    public boolean getBooleanInput(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.next().toLowerCase();

            if (input.equals("y")) return true;
            if (input.equals("n")) return false;

            System.out.println("Invalid input! Enter y/n.");
        }
    }
}
