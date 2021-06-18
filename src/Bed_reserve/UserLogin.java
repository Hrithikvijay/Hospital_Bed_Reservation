package Bed_reserve;

import java.sql.SQLException;
import java.util.Scanner;

public class UserLogin {
    private static ReserveBedInterface reserveBedDb;
    private static UserDatabaseInterface userDb;

    public UserLogin(ReserveBedInterface reserveBedDb, UserDatabaseInterface userDb) {
        UserLogin.reserveBedDb = reserveBedDb;
        UserLogin.userDb = userDb;
    }

    public void userLogin() throws SQLException {
        Scanner sc = new Scanner(System.in);
        ReservationBedUtils.deleteExpiredReservation(reserveBedDb);
        String adhaarId = LoginUtils.getAdhaarId();
        if (userDb.checkUserExists(adhaarId)) {
            if (VerificationUtils.verifyUserPassword(userDb, adhaarId)) {
                UserDetailsContainer userWelcome = userDb.getUserDetails(adhaarId);
                System.out.println("\nWelcome " + userWelcome.getUserName());
                while (true) {
                    UserLogin.loginChoice();
                    System.out.print("\nEnter Choice : ");
                    String login_choice = sc.nextLine();
                    if (login_choice.equals("1")) {
                        // Cancel reservation
                        if (reserveBedDb.checkUserReserved(adhaarId)) {
                            ReservationBedUtils cancel_bed = new ReservationBedUtils(adhaarId);
                            cancel_bed.cancelReservation(reserveBedDb);
                        } else {
                            System.out.println("\n\tNo reservation has been done...");
                        }
                        System.out.println("\nPress Enter to continue...");
                        sc.nextLine();
                    } else if (login_choice.equals("2")) {
                        // View reservation
                        if (reserveBedDb.checkUserReserved(adhaarId)
                                || reserveBedDb.checkUserAdmitted(adhaarId)) {
                            ReservationBedUtils reservation = new ReservationBedUtils(adhaarId);
                            reservation.reservation_details(reserveBedDb);
                        } else {
                            System.out.println("\n\tNo reservation has been done...");
                        }
                        System.out.println("\nPress Enter to continue...");
                        sc.nextLine();
                    } else if (login_choice.equals("3")) {
                        // User details
                        UserDetailsContainer user = userDb.getUserDetails(adhaarId);
                        UserLogin.displayUserDetails(user);
                        System.out.println("\nPress Enter to continue...");
                        sc.nextLine();
                    } else if (login_choice.equals("4")) {
                        // Update user details
                        System.out.println("\nCurrent user details : -");
                        UserDetailsContainer user = userDb.getUserDetails(adhaarId);
                        UserLogin.displayUserDetails(user);
                        UserUtils update_user = new UserUtils(adhaarId, userDb, reserveBedDb);
                        update_user.updateUserDetails();
                        System.out.println("\nSuccuesfully Updated...");
                        System.out.println("\nPress Enter to continue...");
                        sc.nextLine();
                    } else if (login_choice.equals("5")) {
                        // Change password
                        UserLogin.changeUserPassword(userDb, adhaarId);
                        System.out.println("\nPress Enter to continue...");
                        sc.nextLine();
                    } else if (login_choice.equals("6")) {
                        // Deregister user details
                        if (!reserveBedDb.checkUserAdmitted(adhaarId)) {
                            System.out.print("\n\tDo you want to Deregister [Y\\N]: ");
                            String deregistered = sc.nextLine().toUpperCase();
                            if (deregistered.equals("Y")) {
                                UserUtils user = new UserUtils(adhaarId, userDb, reserveBedDb);
                                user.deregisterUser();
                                System.out.println("\nSuccessfully Deregistered");
                                break;
                            }
                        } else {
                            System.out.println(
                                    "The patient with this adhaar id has been admitted,so the user cannot deregister until discharge..");
                            System.out.println("\nPress Enter to continue...");
                            sc.nextLine();
                        }

                    } else if (login_choice.equals("10")) {
                        // Back
                        break;
                    } else {
                        System.out.println("\nPlease enter the valid choice");
                    }
                }
            }
            else{
                System.out.print("\n\tForgot password [Y\\N]: ");
                String forgot_password = sc.nextLine().toUpperCase();
                if (forgot_password.equals("Y")) {
                    UserLogin.forgotUserPassword(adhaarId, userDb);
                }
            }
        } else {
            System.out.println("\n\tNo Registration has been made..please register to continue");
        }

    }
    private static void loginChoice(){
        System.out.println("\nPlease Select the choice given below : ");
        System.out.println("1.Cancel reservation");
        System.out.println("2.View reservation details");
        System.out.println("3.User details");
        System.out.println("4.Update user details");
        System.out.println("5.Change password");
        System.out.println("6.Deregister user details");
        System.out.println("10.Back");
    }
    
    public static void displayUserDetails(UserDetailsContainer user) {
        System.out.println("\nUser details");
        System.out.println("\n\tAdhaar ID         : " + user.getAdhaarId());
        System.out.println("\n\tUser Name         : " + user.getUserName());
        System.out.println("\n\tAddress           : " + user.getAddress());
        System.out.println("\n\tDistrict          : " + user.getDistrict());
        System.out.println("\n\tState             : " + user.getState());
        System.out.println("\n\tAge               : " + user.getAge());
        System.out.println("\n\tGender            : " + user.getGender());
        System.out.println("\n\tContact No        : " + user.getContact());
    }

    public static void forgotUserPassword(String adhaarId, UserDatabaseInterface db) throws SQLException {
        // password can be changed using otp..
        Scanner sc = new Scanner(System.in);
        int otp = (int) (Math.random() * (99999 - 10000 + 1) + 10000);
        String otpStr = Integer.toString(otp);
        String message = "The otp to change password is " + Integer.toString(otp);
        UserDetailsContainer user = db.getUserDetails(adhaarId);
        String contact = user.getContact();
        SendSms.sendSms(message, contact);
        String otpUser;
        Boolean flag = false;
        while (true) {
            System.out.print("\nEnter the OTP : ");
            otpUser = sc.nextLine();
            if (otpUser.equals(otpStr)) {
                String password = SecurityUtils.encryption(LoginUtils.getPassword());
                db.updateUserPassword(adhaarId, password);
                flag = true;
                break;
            } else {
                System.out.println("\nPlease enter the correct otp sent your registered number");
                System.out.print("\nResend otp [Y/N] :");
                String resend = sc.nextLine().toUpperCase();
                if (resend.equals("Y")) {
                    SendSms.sendSms(message, contact);
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

    private static void changeUserPassword(UserDatabaseInterface db, String adhaarId) throws SQLException {
        // change user password
        Scanner sc = new Scanner(System.in);
        System.out.print("\n\tEnter current password : ");
        String currentPassword = sc.nextLine();
        String password = SecurityUtils.decryption(db.getUserPassword(adhaarId));
        String newPassword;
        if (password.equals(currentPassword)) {
            newPassword = SecurityUtils.encryption(LoginUtils.getPassword());
            db.updateUserPassword(adhaarId, newPassword);
            System.out.println("\n\tSuccessfully changed..");
        }
    }
}
