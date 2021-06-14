package Bed_reserve;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class HospitalLogin {
    Scanner sc = new Scanner(System.in);

    public void hospitalLogin(ReserveBedInterface reserveBedDb, HospitalDatabaseInterface hospitalDb,
            UserDatabaseInterface userDb, GovernmentDbInterface governmentDb) throws SQLException {
        String hospitalId = LoginUtils.getHospitalId();
        Boolean flag = false;
        if (!hospitalDb.checkHospitalExists(hospitalId)) {
            System.out.println("Please register to continue...");
            flag = true;
        }
        while (true) {
            if (flag) {
                break;
            }
            // verify password
            if (VerificationUtils.verifyHospitalPassword(hospitalDb, hospitalId)) {
                HospitalDetailsContainer hospitalDetails = hospitalDb.getHospitalDetails(hospitalId);
                // printing welcome note
                System.out.println("\nWelcome " + hospitalDetails.getHospitalName());
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
                        HospitalLogin.acknowledgePatient(hospitalDb, reserveBedDb, hospitalId);
                        System.out.println("\nPress Enter to continue...");
                        sc.nextLine();
                    } else if (choice.equals("2")) {
                        // Discharge patient
                        HospitalLogin.dischargePatient(reserveBedDb, hospitalDb, governmentDb, hospitalId);
                        System.out.println("\nPress Enter to continue...");
                        sc.nextLine();
                    } else if (choice.equals("3")) {
                        // View the details of patients
                        HospitalLogin.patientList(hospitalDb, reserveBedDb, hospitalId);
                        System.out.println("\nPress Enter to continue...");
                        sc.nextLine();
                    } else if (choice.equals("4")) {
                        // Hospital Details
                        HospitalDetailsContainer hospital=hospitalDb.getHospitalDetails(hospitalId);
                        CheckBedAvailability.displayHospitalDetails(hospital);
                        System.out.println("\nPress Enter to continue...");
                        sc.nextLine();
                    } else if (choice.equals("5")) {
                        // Available bed Details
                        HospitalLogin.availableBedDetails(hospitalId, hospitalDb);
                        System.out.println("\nPress Enter to continue...");
                        sc.nextLine();
                    } else if (choice.equals("6")) {
                        // Update Hospital Details
                        HospitalUtils hospital = new HospitalUtils(hospitalId, hospitalDb, userDb);
                        hospital.updateHospitalDetails();
                        System.out.println("\nPress Enter to continue...");
                        sc.nextLine();
                    } else if (choice.equals("7")) {
                        // Update bed Availability
                        HospitalLogin.updateBedInfo(hospitalId, hospitalDb);
                        System.out.println("\nPress Enter to continue...");
                        sc.nextLine();
                    } else if (choice.equals("8")) {
                        // Change password
                        HospitalLogin.changeHospitalPassword(hospitalDb, hospitalId);
                        System.out.println("\nPress Enter to continue...");
                        sc.nextLine();
                    } else if (choice.equals("9")) {
                        // Deregister hospital
                        System.out.print("\n\tDo you want to Deregister the Hospital [Y/N]: ");
                        String deregistered = sc.nextLine().toUpperCase();
                        if (deregistered.equals("Y")) {
                            HospitalUtils hospital = new HospitalUtils(hospitalId, hospitalDb, userDb);
                            hospital.deregisterHospital();
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
                System.out.print("\n\tForgot password [Y/N] : ");
                String forgot_password = sc.nextLine().toUpperCase();
                if (forgot_password.equals("Y")) {
                    HospitalLogin.forgotHospitalPassword(hospitalId, hospitalDb);
                }
            }
        }
    }

    private static void forgotHospitalPassword(String hospitalId, HospitalDatabaseInterface hospitalDb)
            throws SQLException {
        // password can be changed using otp..
        Scanner sc = new Scanner(System.in);
        int otp = (int) (Math.random() * (99999 - 10000 + 1) + 10000);
        String otpStr = Integer.toString(otp);
        String message = "The otp to change password is " + Integer.toString(otp);
        HospitalDetailsContainer hospital = hospitalDb.getHospitalDetails(hospitalId);
        String contact = hospital.getHospitalContact();
        SendSms.sendSms(message, contact);
        String otpUser;
        Boolean flag = false;
        while (true) {
            System.out.print("\nEnter the OTP : ");
            otpUser = sc.nextLine();
            if (otpUser.equals(otpStr)) {
                String password = SecurityUtils.encryption(LoginUtils.getPassword());
                hospitalDb.updateHospitalPassword(hospitalId, password);
                flag=true;
                break;
            } else {
                System.out.println("\nPlease enter the correct otp sent your registered number");
                System.out.print("\nResend otp [Y/N] :");
                String resend = sc.nextLine().toUpperCase();
                if (resend.equals("Y")) {
                    SendSms.sendSms(message, contact);
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

    private static void changeHospitalPassword(HospitalDatabaseInterface hospitalDb, String hospitalId)
            throws SQLException {
        // change hospital password...
        Scanner sc = new Scanner(System.in);
        System.out.print("\n\tEnter current password : ");
        String current_password = sc.nextLine();
        String password = SecurityUtils.decryption(hospitalDb.getHospitalPassword(hospitalId));
        String new_password;
        if (password.equals(current_password)) {
            new_password = SecurityUtils.encryption(LoginUtils.getPassword());
            hospitalDb.updateHospitalPassword(hospitalId, new_password);
            System.out.println("\n\tSuccessfully changed..");
        }
    }

    private static void patientList(HospitalDatabaseInterface hospitalDb, ReserveBedInterface reserveBedDb,
            String hospitalId) throws SQLException {
        List<ReservationDetailsContainer> reservationList = hospitalDb.getAdmittedUser(hospitalId);
        if (reservationList.size() > 0) {
            System.out.println("\n The List of patient details...");
            int count = 0;
            while (count < reservationList.size()) {
                ReservationDetailsContainer reserve = reservationList.get(count);
                UserDetailsContainer user = reserveBedDb.getUserDetails(reserve.getAdhaarId());
                HospitalLogin.displayPatientDetails(reserve, user);
                System.out.println("\n---------------------------------------------------------------------\n");
                count++;
            }
        } else {
            System.out.println("\nNo one admitted in the hospital");
        }
    }

    private static void displayPatientDetails(ReservationDetailsContainer reserve, UserDetailsContainer user) {
        System.out.println("\n\tAdhaar id        : " + reserve.getAdhaarId());
        System.out.println("\n\tPatient name     : " + user.getUserName());
        System.out.println("\n\tPatient gender   : " + user.getGender());
        System.out.println("\n\tPatient Age      : " + user.getAge());
        System.out.println("\n\tPatient Address  : " + user.getAddress());
        System.out.println("\n\tContact          : " + user.getContact());
        System.out.println("\n\tBed Type         : " + reserve.getBedType());
        System.out.println("\n\tStatus           : " + reserve.getStatus());
        System.out.println("\n\tAdmit Time       : " + reserve.getDate());
    }

    private static void patientCured(ReserveBedInterface reserveBedDb, GovernmentDbInterface governmentDb,
            HospitalDatabaseInterface hospitalDb, String hospitalId, ReservationDetailsContainer reserve,
            String adhaarId) throws SQLException {
        BedDetailsContainer bed = hospitalDb.getBedDetails(hospitalId, reserve.getBedType());
        BedDetailsContainer updated_bed = new BedDetailsContainer(bed.getVacant() + 1, bed.getOccupied() - 1,
                bed.getTotal());
        reserveBedDb.updateBedStatus(updated_bed, hospitalId, reserve.getBedType());
        reserveBedDb.cancelReservation(adhaarId);
        governmentDb.patientCured(adhaarId);
    }

    private static void patientTransferred(ReserveBedInterface reserveBedDb, HospitalDatabaseInterface hospitalDb,
            String hospitalId, ReservationDetailsContainer reserve, String adhaarId) throws SQLException {
        BedDetailsContainer bed = hospitalDb.getBedDetails(hospitalId, reserve.getBedType());
        BedDetailsContainer updated_bed = new BedDetailsContainer(bed.getVacant() + 1, bed.getOccupied() - 1,
                bed.getTotal());
        reserveBedDb.updateBedStatus(updated_bed, hospitalId, reserve.getBedType());
        reserveBedDb.cancelReservation(adhaarId);
    }

    private static void availableBedDetails(String hospitalId, HospitalDatabaseInterface hospitalDb)
            throws SQLException {
        System.out.println("\nAvailable Bed Details:-");
        System.out.println("\n\tNormal Bed Details:-");
        BedDetailsContainer normal_bed = hospitalDb.getBedDetails(hospitalId, "normal_beds");
        CheckBedAvailability.displayBedDetails(normal_bed);
        System.out.println("\n\tOxygen Supported Bed Details:-");
        BedDetailsContainer oxygen_bed = hospitalDb.getBedDetails(hospitalId, "oxygen_supported_beds");
        CheckBedAvailability.displayBedDetails(oxygen_bed);
        System.out.println("\n\tICU Bed Details:-");
        BedDetailsContainer icu_bed = hospitalDb.getBedDetails(hospitalId, "icu_beds");
        CheckBedAvailability.displayBedDetails(icu_bed);
        System.out.println("\n\tVentilator Details:-");
        BedDetailsContainer ventilator = hospitalDb.getBedDetails(hospitalId, "ventilators");
        CheckBedAvailability.displayBedDetails(ventilator);
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
    }

    private static void updateBedInfo(String hospitalId, HospitalDatabaseInterface hospitalDb) throws SQLException {
        HospitalLogin.availableBedDetails(hospitalId, hospitalDb);
        GetBedInfo bed = new GetBedInfo();
        System.out.println("\nTo Update :-");
        bed.getBedDetails();
        hospitalDb.updateBedStatus(bed.getNormal(), hospitalId, "normal_beds");
        hospitalDb.updateBedStatus(bed.getIcu(), hospitalId, "icu_beds");
        hospitalDb.updateBedStatus(bed.getOxygen(), hospitalId, "oxygen_supported_beds");
        hospitalDb.updateBedStatus(bed.getVentilator(), hospitalId, "ventilators");
        System.out.println("\n\tSuccessfully Updated\n");
    }

    private static void acknowledgePatient(HospitalDatabaseInterface hospitalDb, ReserveBedInterface reserveBedDb,
            String hospitalId) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nTo Acknowledge the patient..Please Enter the patient's Adhaar id :\n");
        String adhaarId = LoginUtils.getAdhaarId();
        if (reserveBedDb.checkUserReservedBed(adhaarId, hospitalId)) {
            // display reservation details
            ReservationDetailsContainer reserve = reserveBedDb.getUserReservationDetails(adhaarId);
            UserDetailsContainer user=reserveBedDb.getUserDetails(adhaarId);
            System.out.println("\nPatient Details      :");
            HospitalLogin.displayPatientDetails(reserve, user);
            System.out.print("\n\tDo you want to Acknowlege the Patient [Y\\N]: ");
            String acknowledge = sc.nextLine().toUpperCase();
            if (acknowledge.equals("Y")) {
                hospitalDb.hospitalAcknowlegdeUser(adhaarId);
                System.out.println("\n\tSuccessfully Acknowledged...");
            } else {
            }
        } else {
            System.out.println("\n\nNo reservation has been done for this adhaar id");
        }
    }

    private static void dischargePatient(ReserveBedInterface reserveBedDb, HospitalDatabaseInterface hospitalDb,
            GovernmentDbInterface governmentDb, String hospitalId) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nTo Discharge the patient..Please Enter the patient's Adhaar id :\n");
        String adhaarId = LoginUtils.getAdhaarId();
        if (reserveBedDb.checkUserAdmitted(adhaarId)) {
            ReservationDetailsContainer reserve = reserveBedDb.getUserReservationDetails(adhaarId);
            UserDetailsContainer user = reserveBedDb.getUserDetails(reserve.getAdhaarId());
            // Display admission details
            System.out.println("\nPatient details : -");
            HospitalLogin.displayPatientDetails(reserve, user);
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
                    HospitalLogin.patientCured(reserveBedDb, governmentDb, hospitalDb, hospitalId, reserve, adhaarId);
                } else if (reason == 2) {
                    // Transferred - the adhaar number in government database will not be removed.
                    HospitalLogin.patientTransferred(reserveBedDb, hospitalDb, hospitalId, reserve, adhaarId);
                } else {
                    System.out.println("\nInvalid choice");
                }
                System.out.println("\n\tSuccessfully Discharged...");
            }
        } else {
            System.out.println("\n\nNo Admission has been done for this adhaar id");
        }
    }

}
