package Bed_reserve;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReservationBedUtils {
    private String hospitalId;
    private String[] bedTypes = { "", "normal_beds", "oxygen_supported_beds", "icu_beds", "ventilators" };
    private String bedType = "";
    private String district;
    private String state;
    private String adhaarId;

    public ReservationBedUtils(String adhaarId) {
        this.adhaarId = adhaarId;
    }

    Scanner sc = new Scanner(System.in);

    public void reserveBed(ReserveBedInterface reserveBedDb) throws SQLException {
        System.out.println("\nTo select the hospital,please provide the required details...");
        CheckBedAvailability check = new CheckBedAvailability();
        if (check.displayBedAvailability(Database.getInstance())) {
            while (true) {
                System.out.print("\nEnter the Hospital Id in which you would like to reserve the bed : \n");
                hospitalId = LoginUtils.getHospitalId();
                if (reserveBedDb.checkHospitalExists(hospitalId)) {
                    HospitalDetailsContainer hospital = reserveBedDb.getHospitalDetails(hospitalId);
                    district = hospital.getHospitalDistrict();
                    state = hospital.getHospitalState();
                    UserDetailsContainer user = reserveBedDb.getUserDetails(adhaarId);
                    CheckBedAvailability.displayHospitalDetails(hospital);
                    List<BedDetailsContainer> beds = new ArrayList<BedDetailsContainer>();
                    System.out.println("\nAvailable Bed Details:-");
                    System.out.println("\n\tNormal Bed Details:-");
                    BedDetailsContainer normal_bed = reserveBedDb.getBedDetails(hospitalId, "normal_beds");
                    beds.add(normal_bed);
                    CheckBedAvailability.displayBedDetails(normal_bed);
                    System.out.println("\n\tOxygen Supported Bed Details:-");
                    BedDetailsContainer oxygen_bed = reserveBedDb.getBedDetails(hospitalId, "oxygen_supported_beds");
                    beds.add(oxygen_bed);
                    CheckBedAvailability.displayBedDetails(oxygen_bed);
                    System.out.println("\n\tICU Bed Details:-");
                    BedDetailsContainer icu_bed = reserveBedDb.getBedDetails(hospitalId, "icu_beds");
                    beds.add(icu_bed);
                    CheckBedAvailability.displayBedDetails(icu_bed);
                    System.out.println("\n\tVentilator Details:-");
                    BedDetailsContainer ventilator = reserveBedDb.getBedDetails(hospitalId, "ventilators");
                    beds.add(ventilator);
                    CheckBedAvailability.displayBedDetails(ventilator);
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
                        bedType = bedTypes[choice];
                        // if beds available..
                        if (bedType.length() != 0) {
                            ReservationDetailsContainer reserve = new ReservationDetailsContainer();
                            reserve.setAdhaarId(adhaarId);
                            reserve.setHospitalId(hospitalId);
                            reserve.setDistrict(district);
                            reserve.setState(state);
                            reserve.setBedType(bedType);
                            reserve.setStatus("Reserved");
                            reserve.setContact(user.getContact());
                            reserveBedDb.insertReservationDetails(reserve);
                            BedDetailsContainer bed = beds.get(choice - 1);
                            BedDetailsContainer updatedBed = new BedDetailsContainer(bed.getVacant() - 1,
                                    bed.getOccupied() + 1, bed.getTotal());
                            reserveBedDb.updateBedStatus(updatedBed, hospitalId, bedType);
                            String message = "Dear " + user.getUserName() + ",\nThe Bed has been reserved at "
                                    + hospital.getHospitalName() + "," + hospital.getHospitalAddress() + ","
                                    + hospital.getHospitalDistrict() + "," + hospital.getHospitalState() + ",Contact: "
                                    + hospital.getHospitalContact() + ",\nBed Type: " + bedType
                                    + ".\nReach the Hospital within 24hours of Reservation";
                            // notified to the user through sms
                            SendSms.sendSms(message, user.getContact());
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

    public void reservation_details(ReserveBedInterface reserveBedDb) throws SQLException {
        ReservationDetailsContainer reserve = reserveBedDb.getUserReservationDetails(adhaarId);
        // display reservation details
        System.out.println("\nReservation Details  :");
        ReservationBedUtils.displayReservationDetails(reserve);
        HospitalDetailsContainer hospital = reserveBedDb.getHospitalDetails(reserve.getHospitalId());
        CheckBedAvailability.displayHospitalDetails(hospital);
    }

    public static void displayReservationDetails(ReservationDetailsContainer reserve) {
        System.out.println("\n\tAdhaar id              : " + reserve.getAdhaarId());
        System.out.println("\n\thospital id            : " + reserve.getHospitalId());
        System.out.println("\n\tContact                : " + reserve.getContact());
        System.out.println("\n\tBed Type               : " + reserve.getBedType());
        System.out.println("\n\tStatus                 : " + reserve.getStatus());
        System.out.println("\n\tDate and Time          : " + reserve.getDate());
    }

    public void cancelReservation(ReserveBedInterface reserveBedDb) throws SQLException {
        ReservationDetailsContainer reserve = reserveBedDb.getUserReservationDetails(adhaarId);
        // display reservation details
        System.out.println("\nReservation Details  :");
        ReservationBedUtils.displayReservationDetails(reserve);
        System.out.print("\n\tDo you want to cancel the reservation [Y\\N]: ");
        String cancel = sc.nextLine().toUpperCase();
        if (cancel.equals("Y")) {
            BedDetailsContainer bed = reserveBedDb.getBedDetails(reserve.getHospitalId(), reserve.getBedType());
            BedDetailsContainer updated_bed = new BedDetailsContainer(bed.getVacant() + 1, bed.getOccupied() - 1,
                    bed.getTotal());
            reserveBedDb.updateBedStatus(updated_bed, reserve.getHospitalId(), reserve.getBedType());
            reserveBedDb.cancelReservation(adhaarId);
            UserDetailsContainer user = reserveBedDb.getUserDetails(adhaarId);
            String message = "Dear " + user.getUserName() + ",\nYour reservation has been cancelled";
            System.out.println(message);
            // notified to user through sms...
            SendSms.sendSms(message, user.getContact());
        }
    }

    public static void deleteExpiredReservation(ReserveBedInterface reserveBedDb) throws SQLException {
        // delete expired reservation(after 24 hours of reservation)
        List<ReservationDetailsContainer> reservation_list = reserveBedDb.getExpiredReservation();
        int count = 0;
        while (count < reservation_list.size()) {
            ReservationDetailsContainer reserve = reservation_list.get(count);
            BedDetailsContainer bed = reserveBedDb.getBedDetails(reserve.getHospitalId(), reserve.getBedType());
            BedDetailsContainer updated_bed = new BedDetailsContainer(bed.getVacant() + 1, bed.getOccupied() - 1,
                    bed.getTotal());
            reserveBedDb.updateBedStatus(updated_bed, reserve.getHospitalId(), reserve.getBedType());
            reserveBedDb.cancelReservation(reserve.getAdhaarId());
            count++;
        }
    }

    public static void reserveBedOption(ReserveBedInterface reserveBedDb, UserDatabaseInterface userDb,
            GovernmentDbInterface governmentDb) throws SQLException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nTo reserve a bed, Please select the option given below : -");
            System.out.println("\n\t1.Reserve Bed");
            System.out.println("\n\t10.Back");
            System.out.print("\nEnter Choice : ");
            String sub_choice = sc.nextLine();
            if (sub_choice.equals("1")) {
                // Reserve Bed
                // only after viewing the available beds ... the user can reserve the bed.
                String adhaarId = LoginUtils.getAdhaarId();
                if (userDb.checkUserExists(adhaarId)) {
                    while (true) {
                        if (VerificationUtils.verifyUserPassword(userDb, adhaarId)) {
                            break;
                        } else {
                            UserLogin.forgotUserPassword(adhaarId, userDb);
                        }
                    }
                } else {
                    // register user details...
                    UserUtils user = new UserUtils(adhaarId, userDb, reserveBedDb);
                    user.registerUser();
                    System.out.println("\nRegistered Successfully");
                }
                if (!governmentDb.checkUserAffected(adhaarId)) {
                    System.out.println(
                            "\n\tThe holder of this Adhaar id is not afftected by covid...please reach out your nearest Covid test center to take a covid test");
                    continue;
                }
                if (reserveBedDb.checkUserReserved(adhaarId) || reserveBedDb.checkUserAdmitted(adhaarId)) {
                    System.out.println("\n\tThe Bed has been already reserved. Only one bed reservation is Allowed");
                    continue;
                }
                // reserve bed
                ReservationBedUtils reserveBed = new ReservationBedUtils(adhaarId);
                reserveBed.reserveBed(reserveBedDb);
                break;
            } else if (sub_choice.equals("10")) {
                // Back
                break;
            } else {
                System.out.println("Please enter the valid choice");
            }
        }
        
    }
}
