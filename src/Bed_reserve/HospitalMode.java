package Bed_reserve;

import java.sql.SQLException;
import java.util.Scanner;

public class HospitalMode {
    public void hospitalMode(HospitalDatabaseInterface hospitalDb,UserDatabaseInterface userDb,ReserveBedInterface reserveBedDB,GovernmentDbInterface governmentDb) throws SQLException{
        Scanner sc = new Scanner(System.in);
        while (true) {
            HospitalMode.hospitalChoice();
            System.out.print("\nEnter Choice : ");
            String hospitalchoice = sc.nextLine();
            if (hospitalchoice.equals("1")) {
                // Register Hospital
                String hospitalId = LoginUtils.getHospitalId();
                if (hospitalDb.checkHospitalExists(hospitalId)) {
                    System.out.println("\n\tHospital already exists...");
                    continue;
                }
                if (!governmentDb.checkHospitalRegistered(hospitalId)) {
                    System.out.println(
                            "\n\tHospital is not registered in NHP..For more details visit https://www.nhp.gov.in/registration");
                    continue;
                }
                HospitalUtils hospital = new HospitalUtils(hospitalId,hospitalDb, userDb);
                System.out.println("Register hospital :-");
                hospital.registerHospital();
                System.out.println("\n\t Successfully registered");
            } else if (hospitalchoice.equals("2")) {
                // Hospital login
                ReservationBedUtils.deleteExpiredReservation(reserveBedDB);
                HospitalLogin login = new HospitalLogin();
                login.hospitalLogin(reserveBedDB, hospitalDb, userDb,
                        governmentDb);
            } else if (hospitalchoice.equals("10")) {
                // Back
                break;
            } else {
                System.out.println("\nPlease enter the valid choice..");
            }

        }
    }
    private static void hospitalChoice(){
        System.out.println("\nPlease Select the choice given below : ");
        System.out.println("\n1.Register Hospital");
        System.out.println("\n2.Hospital login");
        System.out.println("\n10.Back");
    }
}
