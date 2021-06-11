package Bed_reserve;

import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class Validation {
    public static boolean isdigit(String id) {
        Boolean isDigit = true;
        for (int i = 0; i < id.length(); i++) {
            char digit = id.charAt(i);
            if (!Character.isDigit(digit)) {
                isDigit = false;
                break;
            }
        }
        return isDigit;
    }

    public static String decryption(String pass) {
        String decrypt = "";
        for (int i = 0; i < pass.length(); i++) {
            decrypt += (char) (pass.charAt(i) - 1);
        }
        return decrypt;
    }

    public static String encryption(String pass) {
        String encrypt = "";
        for (int i = 0; i < pass.length(); i++) {
            encrypt += (char) (pass.charAt(i) + 1);
        }
        return encrypt;
    }

    public static String get_hospital_id() {
        Scanner sc = new Scanner(System.in);
        String hospital_id;
        while (true) {
            System.out.print("\n\tEnter Hospital ID      : ");
            hospital_id = sc.nextLine();
            Boolean isDigit = Validation.isdigit(hospital_id);
            // length must be 10 and all the letter must be digit
            if (hospital_id.length() == 10 && isDigit) {
                break;
            } else {
                System.out.println("\n\tInvalid  Hospital_ID.....Please do enter a valid Hospital_ID..");
            }
        }
        return hospital_id;
    }

    public static String get_password() {
        // The Password Must have at least one numeric character,lowercase character,
        // uppercase character,special character and Password length should be between 8
        // and 20
        Scanner sc = new Scanner(System.in);
        String password;
        String confirm_password;
        while (true) {
            System.out.print("\n\tEnter Password         : ");
            password = sc.nextLine();
            if (Validation.validated_password(password)) {
                Boolean flag = false;
                while (true) {
                    System.out.print("\n\tConfirm Password       : ");
                    confirm_password = sc.nextLine();
                    if (password.equals(confirm_password)) {
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

    public static String get_contact() {
        // the contact will be verified by otp...
        Scanner sc = new Scanner(System.in);
        String contact;
        while (true) {
            System.out.print("\n\tEnter Contact No       : ");
            contact = sc.nextLine();
            if (Validation.isdigit(contact)) {
                int otp = (int) (Math.random() * (99999 - 10000 + 1) + 10000);
                String OTP = Integer.toString(otp);
                String message = "The otp to confirm contact is " + Integer.toString(otp);
                Validation.send_sms(message, contact);
                System.out.print("\nEnter the OTP : ");
                String otp_user = sc.nextLine();
                if (otp_user.equals(OTP)) {
                    break;
                } else {
                    System.out.println("\nPlease enter the correct otp sent your registered number");
                    System.out.print("\nResend otp [Y/N] :");
                    String resend = sc.nextLine().toUpperCase();
                    if (resend.equals("Y")) {
                        Validation.send_sms(message, contact);
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

    public static Boolean validated_password(String password) {
        // password validation
        String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=!*])" + "(?=\\S+$).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        if (password.equals("")) {
            return false;
        }
        Matcher match = pattern.matcher(password);
        return match.matches();
    }

    public static String get_adhaar_id() {
        // adhaar id must be 12 digit.
        Scanner sc = new Scanner(System.in);
        String adhaar_id;
        while (true) {
            System.out.print("\n\tEnter Adhaar ID        : ");
            adhaar_id = sc.nextLine();
            Boolean isDigit = Validation.isdigit(adhaar_id);
            if (adhaar_id.length() == 12 && isDigit) {
                break;
            } else {
                System.out.println("\n\tInvalid  Adhaar ID.....Please do enter a valid Adhaar ID..");
            }
        }
        return adhaar_id;
    }

    public static boolean verify_user_password(User_interface db, String adhaar_id) throws SQLException {
        String password = Validation.decryption(db.get_user_password(adhaar_id));
        Scanner sc = new Scanner(System.in);
        String user_password;
        String forgot_password;
        while (true) {
            if (password.equals("")) {
                System.out.println("Please Register to continue..");
                break;
            }
            System.out.print("\n\tEnter Password         : ");
            user_password = sc.nextLine();
            if (password.equals(user_password)) {
                User_details user = db.get_user_details(adhaar_id);
                System.out.println("\nWelcome " + user.getUser_name());
                return true;
            } else {
                System.out.println("\n\tPlease Enter correct login credentials..");
            }
            System.out.print("\n\tForgot password [Y\\N]: ");
            forgot_password = sc.nextLine().toUpperCase();
            if (forgot_password.equals("Y")) {
                Validation.forgot_user_password(adhaar_id, db);
            }
        }
        return false;

    }

    public static void send_sms(String message, String contact) {
        // Here i have used Fast2sms to send sms...
        try {
            String sendId = "BED_RESERVATION_APPLICATION";
            String route = "v3";
            String apiKey = "2krxTyjFlWGc6uNZAEiDvVliziEtYHrWXVjxtfTp3uvixoGZdipTddNLZhRq";
            message = URLEncoder.encode(message, "UTF-8");

            String myUrl = "https://www.fast2sms.com/dev/bulkV2?authorization=" + apiKey + "&sender_id=" + sendId
                    + "&message=" + message + "&route=" + route + "&numbers=" + contact;
            URL url = new URL(myUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("cache-control", "no-store");
            con.getResponseCode();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void forgot_user_password(String adhaar_id, User_interface db) throws SQLException {
        // password can be changed using otp..
        Scanner sc = new Scanner(System.in);
        int otp = (int) (Math.random() * (99999 - 10000 + 1) + 10000);
        String OTP = Integer.toString(otp);
        String message = "The otp to change password is " + Integer.toString(otp);
        User_details user = db.get_user_details(adhaar_id);
        String contact = user.getContact();
        Validation.send_sms(message, contact);
        String otp_user;
        Boolean flag = false;
        while (true) {
            System.out.print("\nEnter the OTP : ");
            otp_user = sc.nextLine();
            if (otp_user.equals(OTP)) {
                String password = Validation.encryption(Validation.get_password());
                db.update_user_password(adhaar_id, password);
                flag = true;
                break;
            } else {
                System.out.println("\nPlease enter the correct otp sent your registered number");
                System.out.print("\nResend otp [Y/N] :");
                String resend = sc.nextLine().toUpperCase();
                if (resend.equals("Y")) {
                    Validation.send_sms(message, contact);
                }
                System.out.println("\nTo return back to password stage,  press  1 ");
                String back = sc.nextLine();
                if (back.equals("1")) {
                    break;
                }
            }
        }
        if (flag) {
            System.out.println("\n\tPassword updated");
        } else {
            System.out.println("\n\tPassword not updated");
        }
    }

    public static void forgot_hospital_password(String hospital_id, Hospital_interface db) throws SQLException {
        // password can be changed using otp..
        Scanner sc = new Scanner(System.in);
        int otp = (int) (Math.random() * (99999 - 10000 + 1) + 10000);
        String OTP = Integer.toString(otp);
        String message = "The otp to change password is " + Integer.toString(otp);
        Hospital_details hospital = db.get_hospital_details(hospital_id);
        String contact = hospital.getHospital_contact();
        Validation.send_sms(message, contact);
        String otp_user;
        Boolean flag = false;
        while (true) {
            System.out.print("\nEnter the OTP : ");
            otp_user = sc.nextLine();
            if (otp_user.equals(OTP)) {
                String password = Validation.encryption(Validation.get_password());
                db.update_hospital_password(hospital_id, password);
                break;
            } else {
                System.out.println("\nPlease enter the correct otp sent your registered number");
                System.out.print("\nResend otp [Y/N] :");
                String resend = sc.nextLine().toUpperCase();
                if (resend.equals("Y")) {
                    Validation.send_sms(message, contact);
                }
                System.out.println("\nTo return back to password stage, press  1 ");
                String back = sc.nextLine();
                if (back.equals("1")) {
                    break;
                }
            }
        }
        if (flag) {
            System.out.println("\n\tPassword updated");
        } else {
            System.out.println("\n\tPassword not updated");
        }
    }

    public static void delete_expired_reservation(Reserve_bed_interface db) throws SQLException {
        // delete expired reservation(after 24 hours of reservation)
        List<Reservation_details> reservation_list = db.get_expired_reservation();
        int count = 0;
        while (count < reservation_list.size()) {
            Reservation_details reserve = reservation_list.get(count);
            Bed_details bed = db.get_bed_details(reserve.getHospital_id(), reserve.getBed_type());
            Bed_details updated_bed = new Bed_details(bed.getVacant() + 1, bed.getOccupied() - 1, bed.getTotal());
            db.updatebedstatus(updated_bed, reserve.getHospital_id(), reserve.getBed_type());
            db.cancel_reservation(reserve.getAdhaar_id());
            count++;
        }
    }

    public static void change_user_password(User_interface db, String adhaar_id) throws SQLException {
        // change user password
        Scanner sc = new Scanner(System.in);
        System.out.print("\n\tEnter current password : ");
        String current_password = sc.nextLine();
        String password = Validation.decryption(db.get_user_password(adhaar_id));
        String new_password;
        if (password.equals(current_password)) {
            new_password = Validation.encryption(Validation.get_password());
            db.update_user_password(adhaar_id, new_password);
            System.out.println("\n\tSuccessfully changed..");
        }
    }

    public static void change_hospital_password(Hospital_interface db, String hospital_id) throws SQLException {
        // change hospital password...
        Scanner sc = new Scanner(System.in);
        System.out.print("\n\tEnter current password : ");
        String current_password = sc.nextLine();
        String password = Validation.decryption(db.get_hospital_password(hospital_id));
        String new_password;
        if (password.equals(current_password)) {
            new_password = Validation.encryption(Validation.get_password());
            db.update_hospital_password(hospital_id, new_password);
            System.out.println("\n\tSuccessfully changed..");
        }
    }

    public static User_details get_user_details(User_interface db, String adhaar_id) throws SQLException {
        return db.get_user_details(adhaar_id);
    }

    public static void patient_cured(String adhaar_id, Government_db_interface db) throws SQLException {
        db.patient_cured(adhaar_id);
    }

    public static boolean hospital_id_validation(String hospital_id, Government_db_interface db) throws SQLException {
        return db.check_hospital_registered(hospital_id);
    }

    public static boolean hospital_id_isregistered(String hospital_id, Hospital_interface db) throws SQLException {
        return db.check_hospital_exists(hospital_id);
    }

    public static boolean hospital_id_isavailable(String hospital_id, Reserve_bed_interface db) throws SQLException {
        return db.check_hospital_exists(hospital_id);
    }

    public static boolean adhaar_id_verification(String adhaar_id, Government_db_interface db) throws SQLException {
        return db.check_user_affected(adhaar_id);
    }

    public static boolean check_user_exist(String adhaar_id, Register_user_interface db) throws SQLException {
        return db.check_user_exists(adhaar_id);
    }

    public static boolean check_user_reserved(String adhaar_id, Reserve_bed_interface db) throws SQLException {
        return db.check_user_reserved(adhaar_id);
    }

    public static boolean check_user_admitted(String adhaar_id, Reserve_bed_interface db) throws SQLException {
        return db.check_user_admitted(adhaar_id);
    }

}
