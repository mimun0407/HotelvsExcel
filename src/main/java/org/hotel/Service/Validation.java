package org.hotel.Service;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Validation {
    public static int getValidInteger(Scanner input, String message) {
        while (true) {
            try {
                System.out.print(message);
                return input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                input.next();
            }
        }
    }
}
