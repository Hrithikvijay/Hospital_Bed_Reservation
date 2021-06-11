package Bed_reserve;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Hospital_login {
    Scanner sc = new Scanner(System.in);

    public void hospital_login(Reserve_bed_interface bed_db, Hospital_interface db, Government_db_interface DB)
            throws SQLException {
        String hospital_id = Validation.get_hospital_id();
        String password = Validation.decryption(db.get_hospital_password(hospital_id));
        Boolean flag = false;
        while (true) {
            if (flag) {
                break;
            }
            if (password.equals("")) {
                System.out.println("\nPlease Register to continue..");
                break;
            }
            System.out.print("\n\tEnter Password         : ");
            String hospital_password = sc.nextLine();
            // verify password
            if (password.equals(hospital_password)) {
                Hospital_details hosp = db.get_hospital_details(hospital_id);
                // printing welcome note
                System.out.println("\nWelcome " + hosp.getHospital_name());
                while (true) {
                    System.out.println("\nPlease Select the choice : ");
                    System.out.println("\n\t1.Acknowledge patient");
                    System.out.println("\n\t2.Discharge patient");
                    System.out.println("\n\t3.View the details of patients");
                    System.out.println("\n\t4.Hospital Details");
                    System.out.println("\n\t5.Available bed Details");
                    System.out.println("\n\t6.Update Hospital Details");
                    System.out.println("\n\t7.Update bed Availability");
                    System.out.println("\n\t8.Change password");
                    System.out.println("\n\t9.Deregister Hospital");
                    System.out.println("\n\t10.Back");
                    System.out.print("\n\tEnter the choice : ");
                    String choice = sc.nextLine();
                    if (choice.equals("1")) {
                        // Acknowledge patient
                        System.out.print("\nTo Acknowledge the patient..Please Enter the patient's Adhaar id :\n");
                        String adhaar_id = Validation.get_adhaar_id();
                        if (bed_db.check_user_reserved_bed(adhaar_id, hospital_id)) {
                            // display reservation details
                            Reservation_details reserve = bed_db.get_user_reservation_details(adhaar_id);
                            System.out.println("\nReservation Details  :");
                            System.out.println("\n\tAdhaar_id        : " + reserve.getAdhaar_id());
                            System.out.println("\n\tContact          : " + reserve.getContact());
                            System.out.println("\n\tBed Type         : " + reserve.getBed_type());
                            System.out.println("\n\tStatus           : " + reserve.getStatus());
                            System.out.println("\n\tReservation Time : " + reserve.getDate());
                            System.out.print("\n\tDo you want to Acknowlege the Patient [Y\\N]: ");
                            String acknowledge = sc.nextLine().toUpperCase();
                            if (acknowledge.equals("Y")) {
                                db.hospital_acknowlegde_user(adhaar_id);
                                System.out.println("\n\tSuccessfully Acknowledged...");
                            } else {
                            }
                        } else {
                            System.out.println("\n\nNo reservation has been done for this adhaar id");
                        }
                        System.out.println("\nPress Enter to continue...");
                        sc.nextLine();
                    } else if (choice.equals("2")) {
                        // Discharge patient
                        System.out.print("\nTo Discharge the patient..Please Enter the patient's Adhaar id :\n");
                        String adhaar_id = Validation.get_adhaar_id();
                        if (bed_db.check_user_admitted(adhaar_id)) {
                            Reservation_details reserve = bed_db.get_user_reservation_details(adhaar_id);
                            // Display admission details
                            System.out.println("\nAdmit Details  :");
                            System.out.println("\n\tAdhaar_id        : " + reserve.getAdhaar_id());
                            System.out.println("\n\tContact          : " + reserve.getContact());
                            System.out.println("\n\tBed Type         : " + reserve.getBed_type());
                            System.out.println("\n\tStatus           : " + reserve.getStatus());
                            System.out.println("\n\tAdmit Time       : " + reserve.getDate());
                            System.out.print("\n\tDo you want to Discharge the Patient [Y\\N]: ");
                            String discharge = sc.nextLine().toUpperCase();
                            if (discharge.equals("Y")) {
                                System.out.println("\nReason for discharge : ");
                                System.out.println("\n\t1.Cured");
                                System.out.println("\n\t2.Transferred to another hospital");
                                System.out.print("\n\tEnter the choice : ");
                                int reason = sc.nextInt();
                                if (reason == 1) {
                                    // cured - the adhaar number in government database will be removed.
                                    Bed_details bed = db.get_bed_details(hospital_id, reserve.getBed_type());
                                    Bed_details updated_bed = new Bed_details(bed.getVacant() + 1,
                                            bed.getOccupied() - 1, bed.getTotal());
                                    bed_db.updatebedstatus(updated_bed, hospital_id, reserve.getBed_type());
                                    bed_db.cancel_reservation(adhaar_id);
                                    Validation.patient_cured(adhaar_id, DB);
                                } else if (reason == 2) {
                                    // Transferred - the adhaar number in government database will not be removed.
                                    Bed_details bed = db.get_bed_details(hospital_id, reserve.getBed_type());
                                    Bed_details updated_bed = new Bed_details(bed.getVacant() + 1,
                                            bed.getOccupied() - 1, bed.getTotal());
                                    db.updatebedstatus(updated_bed, hospital_id, reserve.getBed_type());
                                    bed_db.cancel_reservation(adhaar_id);
                                } else {
                                    System.out.println("\nInvalid choice");
                                }
                                System.out.println("\n\tSuccessfully Discharged...");
                            }
                        } else {
                            System.out.println("\n\nNo Admission has been done for this adhaar id");
                        }
                        System.out.println("\nPress Enter to continue...");
                        sc.nextLine();
                    } else if (choice.equals("3")) {
                        // View the details of patients
                        List<Reservation_details> reservation_list = db.get_admitted_user(hospital_id);
                        if (reservation_list.size() > 0) {
                            System.out.println("\n The List patient details...");
                            int count = 0;
                            while (count < reservation_list.size()) {
                                Reservation_details reserve = reservation_list.get(count);
                                User_details user = bed_db.get_user_details(reserve.getAdhaar_id());
                                System.out.println("\nUser details : -");
                                System.out.println("\n\tAdhaar id        : " + reserve.getAdhaar_id());
                                System.out.println("\n\tPatient name     : " + user.getUser_name());
                                System.out.println("\n\tPatient gender   : " + user.getGender());
                                System.out.println("\n\tPatient Age      : " + user.getAge());
                                System.out.println("\n\tPatient Address  : " + user.getAddress());
                                System.out.println("\n\tContact          : " + reserve.getContact());
                                System.out.println("\n\tBed Type         : " + reserve.getBed_type());
                                System.out.println("\n\tStatus           : " + reserve.getStatus());
                                System.out.println("\n\tAdmit Time       : " + reserve.getDate());
                                System.out.println(
                                        "\n---------------------------------------------------------------------\n");
                                count++;
                            }
                        } else {
                            System.out.println("\nNo one admitted in the hospital");
                        }
                        System.out.println("\nPress Enter to continue...");
                        sc.nextLine();
                    } else if (choice.equals("4")) {
                        // Hospital Details
                        Check_bed_availability.display_hospital_details(hosp);
                        System.out.println("\nPress Enter to continue...");
                        sc.nextLine();
                    } else if (choice.equals("5")) {
                        // Available bed Details
                        System.out.println("\nAvailable Bed Details:-");
                        System.out.println("\n\tNormal Bed Details:-");
                        Bed_details normal_bed = db.get_bed_details(hospital_id, "normal_beds");
                        Check_bed_availability.display_bed_details(normal_bed);
                        System.out.println("\n\tOxygen Supported Bed Details:-");
                        Bed_details oxygen_bed = db.get_bed_details(hospital_id, "oxygen_supported_beds");
                        Check_bed_availability.display_bed_details(oxygen_bed);
                        System.out.println("\n\tICU Bed Details:-");
                        Bed_details icu_bed = db.get_bed_details(hospital_id, "icu_beds");
                        Check_bed_availability.display_bed_details(icu_bed);
                        System.out.println("\n\tVentilator Details:-");
                        Bed_details ventilator = db.get_bed_details(hospital_id, "ventilators");
                        Check_bed_availability.display_bed_details(ventilator);
                        System.out.println("\n\tThe Available bed types are");
                        if (normal_bed.getVacant() > 0) {
                            System.out.println("\n\t\t1.Normal Bed");
                        }
                        if (oxygen_bed.getVacant() > 0) {
                            System.out.println("\n\t\t2.Oxygen Supported Bed");
                        }
                        if (icu_bed.getVacant() > 0) {
                            System.out.println("\n\t\t3.ICU Bed");
                        }
                        if (ventilator.getVacant() > 0) {
                            System.out.println("\n\t\t4.Ventilator");
                        }
                        System.out.println("\nPress Enter to continue...");
                        sc.nextLine();
                    } else if (choice.equals("6")) {
                        // Update Hospital Details
                        System.out.println("\nCurrent Hospital details : -");
                        Check_bed_availability.display_hospital_details(hosp);
                        System.out.println("\nUpdate hospital details:-");
                        System.out.print("\n\tEnter Hospital Name    : ");
                        String update_name = sc.nextLine();
                        System.out.print("\n\tEnter Hospital Address : ");
                        String update_address = sc.nextLine();
                        String[] state_and_district = State_and_district.get_district();
                        String update_state = state_and_district[0];
                        String update_district = state_and_district[1];
                        String update_contact = Validation.get_contact();
                        Hospital_details hospital = new Hospital_details();
                        hospital.setHospital_id(hospital_id);
                        hospital.setHospital_name(update_name);
                        hospital.setHospital_address(update_address);
                        hospital.setHospital_district(update_district);
                        hospital.setHospital_state(update_state);
                        hospital.setHospital_contact(update_contact);
                        db.update_hospital_details(hospital);
                        System.out.println("\nUpdated successfully");
                        System.out.println("\nPress Enter to continue...");
                        sc.nextLine();
                    } else if (choice.equals("7")) {
                        // Update bed Availability
                        System.out.println("\nAvailable Bed Details:-");
                        System.out.println("\n\tNormal Bed Details:-");
                        Bed_details normal_bed = db.get_bed_details(hospital_id, "normal_beds");
                        Check_bed_availability.display_bed_details(normal_bed);
                        System.out.println("\n\tOxygen Supported Bed Details:-");
                        Bed_details oxygen_bed = db.get_bed_details(hospital_id, "oxygen_supported_beds");
                        Check_bed_availability.display_bed_details(oxygen_bed);
                        System.out.println("\n\tICU Bed Details:-");
                        Bed_details icu_bed = db.get_bed_details(hospital_id, "icu_beds");
                        Check_bed_availability.display_bed_details(icu_bed);
                        System.out.println("\n\tVentilator Details:-");
                        Bed_details ventilator = db.get_bed_details(hospital_id, "ventilators");
                        Check_bed_availability.display_bed_details(ventilator);
                        Update_bed_details bed = new Update_bed_details();
                        System.out.println("\nTo Update :-");
                        bed.get_bed_details();
                        db.updatebedstatus(bed.getNormal(), hospital_id, "normal_beds");
                        db.updatebedstatus(bed.getIcu(), hospital_id, "icu_beds");
                        db.updatebedstatus(bed.getOxygen(), hospital_id, "oxygen_supported_beds");
                        db.updatebedstatus(bed.getVentilator(), hospital_id, "ventilators");
                        System.out.println("\n\tSuccessfully Updated\n");
                    } else if (choice.equals("8")) {
                        // Change password
                        Validation.change_hospital_password(db, hospital_id);
                        System.out.println("\nPress Enter to continue...");
                        sc.nextLine();
                    } else if (choice.equals("9")) {
                        // Deregister hospital
                        System.out.print("\n\tDo you want to Deregister the Patient [Y\\N]: ");
                        String deregistered = sc.nextLine().toUpperCase();
                        if (deregistered.equals("Y")) {
                            List<Reservation_details> reservation_list = db.get_deregistered_user(hospital_id);
                            db.cancel_reservation_deregister_hospital(hospital_id);
                            db.delete_hospital_login(hospital_id);
                            db.deregister_hospital_bed_details(hospital_id, "icu_beds");
                            db.deregister_hospital_bed_details(hospital_id, "normal_beds");
                            db.deregister_hospital_bed_details(hospital_id, "oxygen_supported_beds");
                            db.deregister_hospital_bed_details(hospital_id, "ventilators");
                            int count = 0;
                            while (count < reservation_list.size()) {
                                Reservation_details reserve = reservation_list.get(count);
                                User_details user = bed_db.get_user_details(reserve.getAdhaar_id());
                                // If the reservation is done for the hospital.. it will be cancelled and
                                // notified to the user through SMS
                                if (reserve.getStatus().equals("Reserved")) {
                                    String message = "Dear " + user.getUser_name()
                                            + ",\nYour reservation has been cancelled";
                                    System.out.println(message);
                                    Validation.send_sms(message, user.getContact());
                                }
                                count++;
                            }
                            db.deregister_hospital(hospital_id);
                            System.out.println("\n\tSuccessfully Deregistered...");
                            flag = true;
                            break;
                        } else {
                            System.out.println("\nPress Enter to continue...");
                            sc.nextLine();
                        }
                    } else if (choice.equals("10")) {
                        // Back
                        flag = true;
                        break;
                    } else {
                        System.out.println("\nInvalid choice");
                    }
                }
            } else {
                System.out.println("\n\tPlease Enter correct login credentials..");
                System.out.print("\n\tForgot password [Y/N] : ");
                String forgot_password = sc.nextLine().toUpperCase();
                if (forgot_password.equals("Y")) {
                    Validation.forgot_hospital_password(hospital_id, db);
                }
            }

        }
    }

}
