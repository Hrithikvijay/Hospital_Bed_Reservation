package Bed_reserve;

import java.sql.SQLException;
import java.util.Scanner;

public class UserUtils {
    private static String adhaarId;
    private static String userName;
    private static String password;
    private static String address;
    private static String district;
    private static String state;
    private static String age;
    private static String gender;
    private static String contact;
    private static UserDatabaseInterface userDB;
    private static ReserveBedInterface reserveBedDb;

    public UserUtils(String adhaarId, UserDatabaseInterface userDB, ReserveBedInterface reserveBedDb) {
        UserUtils.adhaarId = adhaarId;
        UserUtils.userDB=userDB;
        UserUtils.reserveBedDb=reserveBedDb;
    }
    public void registerUser() throws SQLException{
        if (userDB.checkUserExists(adhaarId)) {
            System.out.println("\nAlready a registration has been done with this Adhaar Id.");
        } else {
            System.out.println("Register User Details :- ");
            UserUtils.userLoginCridentials();
            UserUtils.getUserDetails();
            UserUtils.saveUserDetails();
            System.out.println("\n Registered successfully");
        }
    }

    private static void userLoginCridentials(){
        password = SecurityUtils.encryption(LoginUtils.getPassword());
    }
    private static void getUserDetails() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\n\tEnter User Name        : ");
        userName = sc.nextLine();
        System.out.print("\n\tEnter Address          : ");
        address = sc.nextLine();
        String[] stateAndDistrict = StateAndDistrictNames.getDistrict();
        state = stateAndDistrict[0];
        district = stateAndDistrict[1];
        System.out.print("\n\tEnter Age              : ");
        age = sc.nextLine();
        System.out.print("\n\tEnter Gender [Male - M , Female - F , Others = O] : ");
        gender = sc.nextLine().toUpperCase();
        contact = ContactUtils.getContact();
    }

    private static void saveUserDetails() throws SQLException {
        UserDetailsContainer user = new UserDetailsContainer();
        user.setAdhaarId(adhaarId);
        user.setUserName(userName);
        user.setAddress(address);
        user.setDistrict(district);
        user.setState(state);
        user.setAge(age);
        user.setGender(gender);
        user.setContact(contact);
        userDB.insertUserDetail(user);
        userDB.insertUserLoginCridentials(adhaarId, password);
    }

    public  void updateUserDetails() throws SQLException {
        System.out.println("Update User Details :-");
        UserUtils.getUserDetails();
        UserDetailsContainer user = new UserDetailsContainer();
        user.setAdhaarId(adhaarId);
        user.setUserName(userName);
        user.setAddress(address);
        user.setDistrict(district);
        user.setState(state);
        user.setAge(age);
        user.setGender(gender);
        user.setContact(contact);
        userDB.updateUserDetail(user);
    }

    public void deregisterUser() throws SQLException {
        // if the user has reserved a bed..then it will be cancelled..
        if (reserveBedDb.checkUserReserved(adhaarId)) {
            ReservationDetailsContainer reserve = reserveBedDb.getUserReservationDetails(adhaarId);
            BedDetailsContainer bed = reserveBedDb.getBedDetails(reserve.getHospitalId(), reserve.getBedType());
            BedDetailsContainer updated_bed = new BedDetailsContainer(bed.getVacant() + 1, bed.getOccupied() - 1, bed.getTotal());
            reserveBedDb.updateBedStatus(updated_bed, reserve.getHospitalId(), reserve.getBedType());
            reserveBedDb.cancelReservation(adhaarId);
            UserDetailsContainer user = userDB.getUserDetails(adhaarId);
            // notified to the user through sms
            UserUtils.SendCancellationMessage(user);
        }
        userDB.deleteUserLogin(adhaarId);
        userDB.deregisterUser(adhaarId);
    }

    public static void SendCancellationMessage(UserDetailsContainer user) throws SQLException{
        String message = "Dear " + user.getUserName() + ",\nYour reservation has been cancelled";
        System.out.println(message);
        SendSms.sendSms(message, user.getContact());
    } 

    
}
