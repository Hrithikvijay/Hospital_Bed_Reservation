package Bed_reserve;

import java.sql.SQLException;
import java.util.List;

public class Check_bed_availability {
    // to display the available beds in list of hospitals in the selected district.
    public Boolean display_bed_availability(User_interface db) throws SQLException {

        List<Hospital_details> hospital_list = db.show_hospital_details(State_and_district.get_district()[1]);
        if (hospital_list.size() == 0) {
            System.out.println("\n\tSorry...No hospitals are available in this district");
            return false;
        }
        int count = 0;
        while (count < hospital_list.size()) {
            Hospital_details hospital = hospital_list.get(count);
            Check_bed_availability.display_hospital_details(hospital);
            System.out.println("\nAvailable Bed Details:-");
            System.out.println("\n\tNormal Bed Details:-");
            Check_bed_availability.display_bed_details(db.get_bed_details(hospital.getHospital_id(), "normal_beds"));
            System.out.println("\n\tOxygen Supported Bed Details:-");
            Check_bed_availability
                    .display_bed_details(db.get_bed_details(hospital.getHospital_id(), "oxygen_supported_beds"));
            System.out.println("\n\tICU Bed Details:-");
            Check_bed_availability.display_bed_details(db.get_bed_details(hospital.getHospital_id(), "icu_beds"));
            System.out.println("\n\tVentilator Details:-");
            Check_bed_availability.display_bed_details(db.get_bed_details(hospital.getHospital_id(), "ventilators"));
            System.out.println("\n---------------------------------------------------------------------\n");
            count++;
        }
        return true;

    }

    public static void display_hospital_details(Hospital_details hospital) {
        System.out.println("\nHospital details");
        System.out.println("\tHospital ID       : " + hospital.getHospital_id());
        System.out.println("\tHospital Name     : " + hospital.getHospital_name());
        System.out.println("\tHospital Address  : " + hospital.getHospital_address());
        System.out.println("\tDistrict          : " + hospital.getHospital_district());
        System.out.println("\tState             : " + hospital.getHospital_state());
        System.out.println("\tContact No        : " + hospital.getHospital_contact());
    }

    public static void display_bed_details(Bed_details bed) {
        System.out.println("\t\tThe No.of Vacant beds   : " + bed.getVacant());
        System.out.println("\t\tThe No.of Occupied beds : " + bed.getOccupied());
        System.out.println("\t\tThe NO.of Total beds    : " + bed.getTotal());
    }

    public static void display_user_details(User_details user) {
        System.out.println("\nUser details");
        System.out.println("\tAdhaar ID         : " + user.getAdhaar_id());
        System.out.println("\tUser Name         : " + user.getUser_name());
        System.out.println("\tAddress           : " + user.getAddress());
        System.out.println("\tDistrict          : " + user.getDistrict());
        System.out.println("\tState             : " + user.getState());
        System.out.println("\tAge               : " + user.getAge());
        System.out.println("\tGender            : " + user.getGender());
        System.out.println("\tContact No        : " + user.getContact());
    }
}
