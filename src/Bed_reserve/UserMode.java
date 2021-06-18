package Bed_reserve;

import java.sql.SQLException;
import java.util.Scanner;

public class UserMode {
    public void userMode(UserDatabaseInterface userDb, ReserveBedInterface reserveBedDb,
            GovernmentDbInterface governmentDb) throws SQLException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            UserMode.userChoice();
            System.out.print("\nEnter Choice : ");
            String userchoice = sc.nextLine();
            if (userchoice.equals("1")) {
                // Check Bed Availability
                // The expired reservation will be removed...
                ReservationBedUtils.deleteExpiredReservation(reserveBedDb);
                CheckBedAvailability bed = new CheckBedAvailability();
                if (bed.displayBedAvailability(userDb)) {
                    // Reserve bed
                    ReservationBedUtils.reserveBedOption(reserveBedDb, userDb, governmentDb);
                }
            } else if (userchoice.equals("2")) {
                // Register user
                String adhaarId = LoginUtils.getAdhaarId();
                UserUtils user = new UserUtils(adhaarId, userDb, reserveBedDb);
                user.registerUser();
            } else if (userchoice.equals("3")) {
                // User login
                UserLogin userLogin = new UserLogin(reserveBedDb, userDb);
                userLogin.userLogin();
            } else if (userchoice.equals("10")) {
                // Back
                break;
            } else {
                System.out.println("\nPlease enter the valid choice");
            }
        }
    }

    private static void userChoice() {
        System.out.println("\nPlease Select the choice given below : ");
        System.out.println("\n1.Check Bed Availability");
        System.out.println("\n2.Register user");
        System.out.println("\n3.User login");
        System.out.println("\n10.Back");
    }
}
