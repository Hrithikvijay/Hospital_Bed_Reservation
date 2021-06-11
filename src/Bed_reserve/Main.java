package Bed_reserve;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("----------Welcome to the application----------");
        while (true) {
            System.out.println("\nPlease select the mode of login : ");
            System.out.println("\n\tFor User Mode     : please enter 1 ");
            System.out.println("\n\tFor Hospital Mode : please enter 2 ");
            System.out.print("\nEnter your choice : ");
            String choice = sc.nextLine();
            if (choice.equals("1")) {
                // user mode
                while (true) {
                    System.out.println("\nPlease Select the choice given below : ");
                    System.out.println("\n1.Check Bed Availability");
                    System.out.println("\n2.Register user");
                    System.out.println("\n3.User login");
                    System.out.println("\n10.Back");
                    System.out.print("\nEnter Choice : ");
                    String userchoice = sc.nextLine();
                    if (userchoice.equals("1")) {
                        // Check Bed Availability
                        // The expired reservation will be removed...
                        Validation.delete_expired_reservation(Database.getInstance());
                        Check_bed_availability bed = new Check_bed_availability();
                        if (bed.display_bed_availability(Database.getInstance())) {
                            while (true) {
                                System.out.println("\nTo reserve a bed, Please select the option given below : -");
                                System.out.println("\n\t1.Reserve Bed");
                                System.out.println("\n\t10.Back");
                                System.out.print("\nEnter Choice : ");
                                String sub_choice = sc.nextLine();
                                if (sub_choice.equals("1")) {
                                    // Reserve Bed
                                    // only after viewing the available beds ... the user can reserve the bed.
                                    String adhaar_id = Validation.get_adhaar_id();
                                    if (Validation.check_user_exist(adhaar_id, Database.getInstance())) {
                                        if (Validation.verify_user_password(Database.getInstance(), adhaar_id)) {
                                        }
                                    } else {
                                        // register user details...
                                        Register_user user = new Register_user(adhaar_id);
                                        user.get_user_details();
                                        user.register_user_details(Database.getInstance());
                                        System.out.println("\nRegistered Successfully");
                                    }
                                    if (!Validation.adhaar_id_verification(adhaar_id, Government_db.getInstance())) {
                                        System.out.println(
                                                "\n\tThe holder of this Adhaar id is not afftected by covid...please reach out your nearest Covid test center to take a covid test");
                                        continue;
                                    }
                                    if (Validation.check_user_reserved(adhaar_id, Database.getInstance())) {
                                        System.out.println(
                                                "\n\tThe Bed has been already reserved. Only one bed reservation is Allowed");
                                        continue;
                                    }
                                    // reserve bed
                                    Reserve_bed reserve_bed = new Reserve_bed(adhaar_id);
                                    reserve_bed.reserve_bed(Database.getInstance());
                                    break;
                                } else if (sub_choice.equals("10")) {
                                    // Back
                                    break;
                                } else {
                                    System.out.println("Please enter the valid choice");
                                }
                            }

                        }
                    } else if (userchoice.equals("2")) {
                        // Register user
                        String adhaar_id = Validation.get_adhaar_id();
                        if (Validation.check_user_exist(adhaar_id, Database.getInstance())) {
                            System.out.println("\nAlready a registration has been done with this Adhaar Id.");
                        } else {
                            Register_user user = new Register_user(adhaar_id);
                            user.get_user_details();
                            user.register_user_details(Database.getInstance());
                            System.out.println("\n Register successfully");
                        }
                    } else if (userchoice.equals("3")) {
                        // User login
                        // The expired reservation will be removed...
                        Validation.delete_expired_reservation(Database.getInstance());
                        String adhaar_id = Validation.get_adhaar_id();
                        if (Validation.check_user_exist(adhaar_id, Database.getInstance())) {
                            if (Validation.verify_user_password(Database.getInstance(), adhaar_id)) {
                                while (true) {
                                    System.out.println("\nPlease Select the choice given below : ");
                                    System.out.println("1.Cancel reservation");
                                    System.out.println("2.View reservation details");
                                    System.out.println("3.User details");
                                    System.out.println("4.Update user details");
                                    System.out.println("5.Change password");
                                    System.out.println("6.Deregister user details");
                                    System.out.println("10.Back");
                                    System.out.print("\nEnter Choice : ");
                                    String login_choice = sc.nextLine();
                                    if (login_choice.equals("1")) {
                                        // Cancel reservation
                                        if (Validation.check_user_reserved(adhaar_id, Database.getInstance())) {
                                            Reserve_bed cancel_bed = new Reserve_bed(adhaar_id);
                                            cancel_bed.cancel_reservation(Database.getInstance());
                                        } else {
                                            System.out.println("\n\tNo reservation has been done...");
                                        }
                                        System.out.println("\nPress Enter to continue...");
                                        sc.nextLine();
                                    } else if (login_choice.equals("2")) {
                                        // View reservation
                                        if (Validation.check_user_reserved(adhaar_id, Database.getInstance())
                                                || Validation.check_user_admitted(adhaar_id, Database.getInstance())) {
                                            Reserve_bed reservation = new Reserve_bed(adhaar_id);
                                            reservation.reservation_details(Database.getInstance());
                                        } else {
                                            System.out.println("\n\tNo reservation has been done...");
                                        }
                                        System.out.println("\nPress Enter to continue...");
                                        sc.nextLine();
                                    } else if (login_choice.equals("3")) {
                                        // User details
                                        User_details user = Validation.get_user_details(Database.getInstance(),
                                                adhaar_id);
                                        Check_bed_availability.display_user_details(user);
                                        System.out.println("\nPress Enter to continue...");
                                        sc.nextLine();
                                    } else if (login_choice.equals("4")) {
                                        // Update user details
                                        System.out.println("\nCurrent user details : -");
                                        User_details user = Validation.get_user_details(Database.getInstance(),
                                                adhaar_id);
                                        Check_bed_availability.display_user_details(user);
                                        Register_user update_user = new Register_user(adhaar_id);
                                        update_user.update_user_details(Database.getInstance());
                                        System.out.println("\nSuccuesfully Updated...");
                                        System.out.println("\nPress Enter to continue...");
                                        sc.nextLine();
                                    } else if (login_choice.equals("5")) {
                                        // Change password
                                        Validation.change_user_password(Database.getInstance(), adhaar_id);
                                        System.out.println("\nPress Enter to continue...");
                                        sc.nextLine();
                                    } else if (login_choice.equals("6")) {
                                        // Deregister user details
                                        if (!Validation.check_user_admitted(adhaar_id, Database.getInstance())) {
                                            System.out.print("\n\tDo you want to Deregister [Y\\N]: ");
                                            String deregistered = sc.nextLine().toUpperCase();
                                            if (deregistered.equals("Y")) {
                                                Register_user user = new Register_user(adhaar_id);
                                                user.deregister_user(Database.getInstance(), Database.getInstance());
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
                        } else {
                            System.out.println("\n\tNo Registration has been made..please register to continue");
                        }

                    } else if (userchoice.equals("10")) {
                        // Back
                        break;
                    } else {
                        System.out.println("\nPlease enter the valid choice");
                    }
                }
            } else if (choice.equals("2")) {
                // Hospital mode
                while (true) {
                    System.out.println("\nPlease Select the choice given below : \n");
                    System.out.println("\n1.Register Hospital");
                    System.out.println("\n2.Hospital login");
                    System.out.println("\n10.Back");
                    System.out.print("\nEnter Choice : ");
                    String adminchoice = sc.nextLine();
                    if (adminchoice.equals("1")) {
                        // Register Hospital
                        String hospital_id = Validation.get_hospital_id();
                        if (Validation.hospital_id_isregistered(hospital_id, Database.getInstance())) {
                            System.out.println("\n\tHospital already exists...");
                            continue;
                        }
                        if (!Validation.hospital_id_validation(hospital_id, Government_db.getInstance())) {
                            System.out.println(
                                    "\n\tHospital is not registered in NHP..For more details visit https://www.nhp.gov.in/registration");
                            continue;
                        }
                        Register_hospital hospital = new Register_hospital(hospital_id);
                        hospital.get_hospital_details();
                        hospital.inserthospitaldetails(Database.getInstance());
                        hospital.updateBedDetails(Database.getInstance());
                        System.out.println("\n\t Successfully registered");
                    } else if (adminchoice.equals("2")) {
                        // Hospital login
                        Validation.delete_expired_reservation(Database.getInstance());
                        Hospital_login login = new Hospital_login();
                        login.hospital_login(Database.getInstance(), Database.getInstance(),
                                Government_db.getInstance());
                    } else if (adminchoice.equals("10")) {
                        // Back
                        break;
                    } else {
                        System.out.println("\nPlease enter the valid choice..");
                    }

                }

            } else {
                System.out.println("\nPlease enter the valid choice");
            }
        }

    }
}
