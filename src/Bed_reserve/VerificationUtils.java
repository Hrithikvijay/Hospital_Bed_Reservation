package Bed_reserve;

import java.sql.SQLException;
import java.util.Scanner;

public class VerificationUtils {
    public static boolean verifyUserPassword(UserDatabaseInterface db, String adhaarId) throws SQLException {
        String password = SecurityUtils.decryption(db.getUserPassword(adhaarId));
        Scanner sc = new Scanner(System.in);
        String userPassword;
        System.out.print("\n\tEnter Password         : ");
        userPassword = sc.nextLine();
        if (password.equals(userPassword)) {
            return true;
        } else {
            System.out.println("\n\tPlease Enter correct login credentials..");
        }
        return false;
    }

    public static boolean verifyHospitalPassword(HospitalDatabaseInterface db,String hospitalId) throws SQLException{
        String password = SecurityUtils.decryption(db.getHospitalPassword(hospitalId));
        Scanner sc = new Scanner(System.in);
        String hospitalPassword;
        System.out.print("\n\tEnter Password         : ");
        hospitalPassword = sc.nextLine();
        if (password.equals(hospitalPassword)) {
            return true;
        } else {
            System.out.println("\n\tPlease Enter correct login credentials..");
        }
        return false;
    }
}
