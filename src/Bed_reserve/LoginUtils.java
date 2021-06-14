package Bed_reserve;

import java.util.Scanner;

public class LoginUtils {
    public static String getHospitalId() {
        Scanner sc = new Scanner(System.in);
        String hospitalId;
        while (true) {
            System.out.print("\n\tEnter Hospital ID      : ");
            hospitalId = sc.nextLine();
            Boolean isDigit = Validation.isdigit(hospitalId);
            // length must be 10 and all the letter must be digit
            if (hospitalId.length() == 10 && isDigit) {
                break;
            } else {
                System.out.println("\n\tInvalid  Hospital_ID.....Please do enter a valid Hospital_ID..");
            }
        }
        return hospitalId;
    }
    
    public static String getAdhaarId() {
        // adhaar id must be 12 digit.
        Scanner sc = new Scanner(System.in);
        String adhaarId;
        while (true) {
            System.out.print("\n\tEnter Adhaar ID        : ");
            adhaarId = sc.nextLine();
            Boolean isDigit = Validation.isdigit(adhaarId);
            if (adhaarId.length() == 12 && isDigit) {
                break;
            } else {
                System.out.println("\n\tInvalid  Adhaar ID.....Please do enter a valid Adhaar ID..");
            }
        }
        return adhaarId;
    }

    public static String getPassword() {
        // The Password Must have at least one numeric character,lowercase character,
        // uppercase character,special character and Password length should be between 8
        // and 20
        Scanner sc = new Scanner(System.in);
        String password;
        String confirmPassword;
        while (true) {
            System.out.print("\n\tEnter Password         : ");
            password = sc.nextLine();
            if (Validation.validatedPassword(password)) {
                Boolean flag = false;
                while (true) {
                    System.out.print("\n\tConfirm Password       : ");
                    confirmPassword = sc.nextLine();
                    if (password.equals(confirmPassword)) {
                        flag = true;
                        break;
                    } else {
                        System.out.println("\n\tThe passwords did not match... Re-enter Confirm password..");
                    }
                }
                if (flag) {
                    break;
                }

            } else {
                System.out.println(
                        "\n\tThe Password Must have at least one numeric character,lowercase character, uppercase character,special character and Password length should be between 8 and 20");
            }
        }
        return password;
    }
}
