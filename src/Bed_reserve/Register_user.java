package Bed_reserve;

import java.sql.SQLException;
import java.util.Scanner;

public class Register_user {
    Scanner sc = new Scanner(System.in);
    private String adhaar_id;
    private String user_name;
    private String password;
    private String address;
    private String district;
    private String state;
    private String age;
    private String gender;
    private String contact;

    public Register_user(String adhaar_id) {
        this.adhaar_id = adhaar_id;
    }

    public void get_user_details() {
        System.out.println("Register User Details :-");
        password = Validation.encryption(Validation.get_password());
        System.out.print("\n\tEnter User Name        : ");
        user_name = sc.nextLine();
        System.out.print("\n\tEnter Address          : ");
        address = sc.nextLine();
        String[] state_and_district = State_and_district.get_district();
        state = state_and_district[0];
        district = state_and_district[1];
        System.out.print("\n\tEnter Age              : ");
        age = sc.nextLine();
        System.out.print("\n\tEnter Gender [Male - M , Female - F , Others = O] : ");
        gender = sc.nextLine().toUpperCase();
        contact = Validation.get_contact();
    }

    public void register_user_details(Register_user_interface db) throws SQLException {
        User_details user = new User_details();
        user.setAdhaar_id(adhaar_id);
        user.setUser_name(user_name);
        user.setAddress(address);
        user.setDistrict(district);
        user.setState(state);
        user.setAge(age);
        user.setGender(gender);
        user.setContact(contact);
        db.insertuserDetail(user);
        db.insert_user_login_cridentials(adhaar_id, password);
    }

    public void update_user_details(User_interface db) throws SQLException {
        System.out.println("Update User Details :-");
        System.out.print("\n\tEnter User Name        : ");
        user_name = sc.nextLine();
        System.out.print("\n\tEnter Address          : ");
        address = sc.nextLine();
        String[] state_and_distrit = State_and_district.get_district();
        state = state_and_distrit[0];
        district = state_and_distrit[1];
        System.out.print("\n\tEnter Age              : ");
        age = sc.nextLine();
        System.out.print("\n\tEnter Gender [Male - M , Female - F , Others = O] : ");
        gender = sc.nextLine().toUpperCase();
        contact = Validation.get_contact();
        User_details user = new User_details();
        user.setAdhaar_id(adhaar_id);
        user.setUser_name(user_name);
        user.setAddress(address);
        user.setDistrict(district);
        user.setState(state);
        user.setAge(age);
        user.setGender(gender);
        user.setContact(contact);
        db.updateuserDetail(user);
    }

    public void deregister_user(Reserve_bed_interface db, User_interface DB) throws SQLException {
        // if the user has reserved a bed..then it will be cancelled..
        if (db.check_user_reserved(adhaar_id)) {
            Reservation_details reserve = db.get_user_reservation_details(adhaar_id);
            Bed_details bed = db.get_bed_details(reserve.getHospital_id(), reserve.getBed_type());
            Bed_details updated_bed = new Bed_details(bed.getVacant() + 1, bed.getOccupied() - 1, bed.getTotal());
            db.updatebedstatus(updated_bed, reserve.getHospital_id(), reserve.getBed_type());
            db.cancel_reservation(adhaar_id);
            User_details user = DB.get_user_details(adhaar_id);
            // notified to the user through sms
            String message = "Dear " + user.getUser_name() + ",\nYour reservation has been cancelled";
            System.out.println(message);
            Validation.send_sms(message, user.getContact());
        }
        DB.delete_user_login(adhaar_id);
        DB.deregister_user(adhaar_id);

    }
}
