package Bed_reserve;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reserve_bed {
    private String hospital_id;
    private String[] bed_types = { "", "normal_beds", "oxygen_supported_beds", "icu_beds", "ventilators" };
    private String bed_type = "";
    private String district;
    private String state;
    private String adhaar_id;

    public Reserve_bed(String adhaar_id) {
        this.adhaar_id = adhaar_id;
    }

    Scanner sc = new Scanner(System.in);

    public void reserve_bed(Reserve_bed_interface db) throws SQLException {
        System.out.println("\nTo select the hospital,please provide the required details...");
        Check_bed_availability check = new Check_bed_availability();
        if (check.display_bed_availability(Database.getInstance())) {
            while (true) {
                System.out.print("\nEnter the Hospital Id in which you would like to reserve the bed : \n");
                hospital_id = Validation.get_hospital_id();
                if (Validation.hospital_id_isavailable(hospital_id, db)) {
                    Hospital_details hospital = db.get_hospital_details(hospital_id);
                    district = hospital.getHospital_district();
                    state = hospital.getHospital_state();
                    User_details user = db.get_user_details(adhaar_id);
                    Check_bed_availability.display_hospital_details(hospital);
                    List<Bed_details> beds = new ArrayList<Bed_details>();
                    System.out.println("\nAvailable Bed Details:-");
                    System.out.println("\n\tNormal Bed Details:-");
                    Bed_details normal_bed = db.get_bed_details(hospital_id, "normal_beds");
                    beds.add(normal_bed);
                    Check_bed_availability.display_bed_details(normal_bed);
                    System.out.println("\n\tOxygen Supported Bed Details:-");
                    Bed_details oxygen_bed = db.get_bed_details(hospital_id, "oxygen_supported_beds");
                    beds.add(oxygen_bed);
                    Check_bed_availability.display_bed_details(oxygen_bed);
                    System.out.println("\n\tICU Bed Details:-");
                    Bed_details icu_bed = db.get_bed_details(hospital_id, "icu_beds");
                    beds.add(icu_bed);
                    Check_bed_availability.display_bed_details(icu_bed);
                    System.out.println("\n\tVentilator Details:-");
                    Bed_details ventilator = db.get_bed_details(hospital_id, "ventilators");
                    beds.add(ventilator);
                    Check_bed_availability.display_bed_details(ventilator);
                    System.out.println("\n\tThe Available bed types are");
                    int flag = 0, choice;
                    if (normal_bed.getVacant() > 0) {
                        System.out.println("\n\t\t1.Normal Bed");
                        flag = 1;
                    }
                    if (oxygen_bed.getVacant() > 0) {
                        System.out.println("\n\t\t2.Oxygen Supported Bed");
                        flag = 1;
                    }
                    if (icu_bed.getVacant() > 0) {
                        System.out.println("\n\t\t3.ICU Bed");
                        flag = 1;
                    }
                    if (ventilator.getVacant() > 0) {
                        System.out.println("\n\t\t4.Ventilator");
                        flag = 1;
                    }
                    if (flag == 1) {
                        System.out.print("\n\t\tEnter the choice :");
                        choice = sc.nextInt();
                        bed_type = bed_types[choice];
                        // if beds available..
                        if (bed_type.length() != 0) {
                            Reservation_details reserve = new Reservation_details();
                            reserve.setAdhaar_id(adhaar_id);
                            reserve.setHospital_id(hospital_id);
                            reserve.setDistrict(district);
                            reserve.setState(state);
                            reserve.setBed_type(bed_type);
                            reserve.setStatus("Reserved");
                            reserve.setContact(user.getContact());
                            db.insert_reservation_details(reserve);
                            Bed_details bed = beds.get(choice - 1);
                            Bed_details updated_bed = new Bed_details(bed.getVacant() - 1, bed.getOccupied() + 1,
                                    bed.getTotal());
                            db.updatebedstatus(updated_bed, hospital_id, bed_type);
                            String message = "Dear " + user.getUser_name() + ",\nThe Bed has been reserved at "
                                    + hospital.getHospital_name() + "," + hospital.getHospital_address() + ","
                                    + hospital.getHospital_district() + "," + hospital.getHospital_state()
                                    + ",Contact: " + hospital.getHospital_contact() + ",\nBed Type: " + bed_type
                                    + ".\nReach the Hospital within 24hours of Reservation";
                            // notified to the user through sms
                            Validation.send_sms(message, user.getContact());
                            System.out.println("\n" + message + "else reservation will be cancelled.");
                        }
                    }
                    break;
                } else {
                    System.out.println("\nHospital id does not exits");
                }
            }
        }
    }

    public void reservation_details(Reserve_bed_interface db) throws SQLException {
        Reservation_details reserve = db.get_user_reservation_details(adhaar_id);
        // display reservation details
        System.out.println("\nReservation Details  :");
        System.out.println("\n\tAdhaar id              : " + reserve.getAdhaar_id());
        System.out.println("\n\thospital id            : " + reserve.getHospital_id());
        System.out.println("\n\tContact                : " + reserve.getContact());
        System.out.println("\n\tBed Type               : " + reserve.getBed_type());
        System.out.println("\n\tStatus                 : " + reserve.getStatus());
        System.out.println("\n\tDate and Time          : " + reserve.getDate());
        Hospital_details hospital = db.get_hospital_details(reserve.getHospital_id());
        Check_bed_availability.display_hospital_details(hospital);
    }

    public void cancel_reservation(Reserve_bed_interface db) throws SQLException {
        Reservation_details reserve = db.get_user_reservation_details(adhaar_id);
        // display reservation details
        System.out.println("\nReservation Details  :");
        System.out.println("\n\tAdhaar id              : " + reserve.getAdhaar_id());
        System.out.println("\n\thospital id            : " + reserve.getHospital_id());
        System.out.println("\n\tContact                : " + reserve.getContact());
        System.out.println("\n\tBed Type               : " + reserve.getBed_type());
        System.out.println("\n\tStatus                 : " + reserve.getStatus());
        System.out.println("\n\tDate and Time          : " + reserve.getDate());
        System.out.print("\n\tDo you want to cancel the reservation [Y\\N]: ");
        String cancel = sc.nextLine().toUpperCase();
        if (cancel.equals("Y")) {
            Bed_details bed = db.get_bed_details(reserve.getHospital_id(), reserve.getBed_type());
            Bed_details updated_bed = new Bed_details(bed.getVacant() + 1, bed.getOccupied() - 1, bed.getTotal());
            db.updatebedstatus(updated_bed, reserve.getHospital_id(), reserve.getBed_type());
            db.cancel_reservation(adhaar_id);
            User_details user = db.get_user_details(adhaar_id);
            String message = "Dear " + user.getUser_name() + ",\nYour reservation has been cancelled";
            System.out.println(message);
            // notified to user through sms...
            Validation.send_sms(message, user.getContact());
        }
    }
}
