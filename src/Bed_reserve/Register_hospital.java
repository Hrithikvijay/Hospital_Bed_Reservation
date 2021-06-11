package Bed_reserve;

import java.sql.SQLException;
import java.util.Scanner;

public class Register_hospital {
    Scanner sc = new Scanner(System.in);
    private String hospital_id;
    private String hospital_name;
    private String hospital_address;
    private String hospital_district;
    private String hospital_state;
    private String hospital_contact;
    private String hospital_password;

    public Register_hospital(String hospital_id) {
        this.hospital_id = hospital_id;
    }

    public void get_hospital_details() {
        System.out.println("Register hospital :-");
        hospital_password = Validation.encryption(Validation.get_password());
        System.out.print("\n\tEnter Hospital Name    : ");
        hospital_name = sc.nextLine();
        System.out.print("\n\tEnter Hospital Address : ");
        hospital_address = sc.nextLine();
        String[] state_and_distrit = State_and_district.get_district();
        hospital_state = state_and_distrit[0];
        hospital_district = state_and_distrit[1];
        hospital_contact = Validation.get_contact();
    }

    public void inserthospitaldetails(Hospital_interface db) throws SQLException {
        Hospital_details hospital = new Hospital_details();
        hospital.setHospital_id(hospital_id);
        hospital.setHospital_name(hospital_name);
        hospital.setHospital_address(hospital_address);
        hospital.setHospital_district(hospital_district);
        hospital.setHospital_state(hospital_state);
        hospital.setHospital_contact(hospital_contact);
        db.insertHospitalDetail(hospital);
        db.insert_hospital_login_cridentials(hospital_id, hospital_password);
    }

    public void updateBedDetails(Insert_bed_details_interface db) throws SQLException {
        Update_bed_details bed = new Update_bed_details();
        bed.get_bed_details();
        db.insert_bed_details(bed.getNormal(), hospital_id, "normal_beds");
        db.insert_bed_details(bed.getIcu(), hospital_id, "icu_beds");
        db.insert_bed_details(bed.getOxygen(), hospital_id, "oxygen_supported_beds");
        db.insert_bed_details(bed.getVentilator(), hospital_id, "ventilators");
    }

}
