package sk.mato.utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readString() {
        return scanner.nextLine();

    }

    public static int readInt() {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine();
                return input;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input, try again");
                scanner.nextLine();
            }

        }
    }
}
