package Bed_reserve;

import java.util.Scanner;

public class ContactUtils {
    public static String getContact() {
        // the contact will be verified by otp...
        Scanner sc = new Scanner(System.in);
        String contact;
        while (true) {
            System.out.print("\n\tEnter Contact No       : ");
            contact = sc.nextLine();
            if (Validation.isdigit(contact)) {
                int otp = (int) (Math.random() * (99999 - 10000 + 1) + 10000);
                String otpStr = Integer.toString(otp);
                String message = "The otp to confirm contact is " + Integer.toString(otp);
                SendSms.sendSms(message, contact);
                System.out.print("\nEnter the OTP : ");
                String otpUser = sc.nextLine();
                if (otpUser.equals(otpStr)) {
                    break;
                } else {
                    System.out.println("\nPlease enter the correct otp sent your registered number");
                    System.out.print("\nResend otp [Y/N] :");
                    String resend = sc.nextLine().toUpperCase();
                    if (resend.equals("Y")) {
                        SendSms.sendSms(message, contact);
                    }
                    System.out.println("\n To Change number,  press  1 ");
                    String back = sc.nextLine();
                    if (back.equals("1")) {
                        continue;
                    }
                }
            } else {
                System.out.println("Invalid  Contact Number.....Please do enter a valid Contact Number..");
            }
        }
        return contact;
    }
}
