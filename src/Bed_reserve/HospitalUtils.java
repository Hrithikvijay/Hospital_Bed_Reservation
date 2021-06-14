package Bed_reserve;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class HospitalUtils {
    Scanner sc = new Scanner(System.in);
    private static String hospitalId;
    private static String hospitalName;
    private static String hospitalAddress;
    private static String hospitalDistrict;
    private static String hospitalState;
    private static String hospitalContact;
    private static String hospitalPassword;
    private static HospitalDatabaseInterface hospitalDb;
    private static UserDatabaseInterface userDb;

    public HospitalUtils(String hospitalId, HospitalDatabaseInterface hospitalDb, UserDatabaseInterface userDb) {
        HospitalUtils.hospitalId = hospitalId;
        HospitalUtils.hospitalDb = hospitalDb;
        HospitalUtils.userDb = userDb;
    }

    public void registerHospital() throws SQLException {
        HospitalUtils.hospitalLoginCridentials();
        HospitalUtils.getHospitalDetails();
        HospitalUtils.inserthospitaldetails();
        HospitalUtils.saveBedDetails();
    }

    private static void hospitalLoginCridentials() {
        hospitalPassword = SecurityUtils.encryption(LoginUtils.getPassword());
    }

    private static void getHospitalDetails() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\n\tEnter Hospital Name    : ");
        hospitalName = sc.nextLine();
        System.out.print("\n\tEnter Hospital Address : ");
        hospitalAddress = sc.nextLine();
        String[] state_and_distrit = StateAndDistrictNames.getDistrict();
        hospitalState = state_and_distrit[0];
        hospitalDistrict = state_and_distrit[1];
        hospitalContact = ContactUtils.getContact();
    }

    public static void inserthospitaldetails() throws SQLException {
        HospitalDetailsContainer hospital = new HospitalDetailsContainer();
        hospital.setHospitalId(hospitalId);
        hospital.setHospitalName(hospitalName);
        hospital.setHospitalAddress(hospitalAddress);
        hospital.setHospitalDistrict(hospitalDistrict);
        hospital.setHospitalState(hospitalState);
        hospital.setHospitalContact(hospitalContact);
        hospitalDb.insertHospitalDetail(hospital);
        hospitalDb.insertHospitalLoginCridentials(hospitalId, hospitalPassword);
    }

    public static void saveBedDetails() throws SQLException {
        GetBedInfo bed = new GetBedInfo();
        bed.getBedDetails();
        hospitalDb.insertBedDetails(bed.getNormal(), hospitalId, "normal_beds");
        hospitalDb.insertBedDetails(bed.getIcu(), hospitalId, "icu_beds");
        hospitalDb.insertBedDetails(bed.getOxygen(), hospitalId, "oxygen_supported_beds");
        hospitalDb.insertBedDetails(bed.getVentilator(), hospitalId, "ventilators");
    }

    public void deregisterHospital() throws SQLException {
        List<ReservationDetailsContainer> reservationList = hospitalDb.getDeregisteredUser(hospitalId);
        hospitalDb.cancelReservationDeregisterHospital(hospitalId);
        hospitalDb.deregisterHospitalBedDetails(hospitalId, "icu_beds");
        hospitalDb.deregisterHospitalBedDetails(hospitalId, "normal_beds");
        hospitalDb.deregisterHospitalBedDetails(hospitalId, "oxygen_supported_beds");
        hospitalDb.deregisterHospitalBedDetails(hospitalId, "ventilators");
        hospitalDb.deleteHospitalLogin(hospitalId);
        hospitalDb.deregisterHospital(hospitalId);
        HospitalUtils.SendCancellationMessage(reservationList);

    }

    public void updateHospitalDetails() throws SQLException {
        System.out.println("\nCurrent Hospital details : -");
        HospitalDetailsContainer hospitalDetails = hospitalDb.getHospitalDetails(hospitalId);
        CheckBedAvailability.displayHospitalDetails(hospitalDetails);
        System.out.println("\nUpdate hospital details:-");
        HospitalUtils.getHospitalDetails();
        HospitalDetailsContainer hospital = new HospitalDetailsContainer();
        hospital.setHospitalId(hospitalId);
        hospital.setHospitalName(hospitalName);
        hospital.setHospitalAddress(hospitalAddress);
        hospital.setHospitalDistrict(hospitalDistrict);
        hospital.setHospitalState(hospitalState);
        hospital.setHospitalContact(hospitalContact);
        hospitalDb.updateHospitalDetails(hospital);
        System.out.println("\nUpdated successfully");
    }

    public static void SendCancellationMessage(List<ReservationDetailsContainer> reservationList) throws SQLException {
        int count = 0;
        while (count < reservationList.size()) {
            ReservationDetailsContainer reserve = reservationList.get(count);
            UserDetailsContainer user = userDb.getUserDetails(reserve.getAdhaarId());
            // If the reservation is done for the hospital.. it will be cancelled and
            // notified to the user through SMS
            if (reserve.getStatus().equals("Reserved")) {
                String message = "Dear " + user.getUserName() + ",\nYour reservation has been cancelled";
                System.out.println(message);
                SendSms.sendSms(message, user.getContact());
            }
            count++;
        }
    }

}
