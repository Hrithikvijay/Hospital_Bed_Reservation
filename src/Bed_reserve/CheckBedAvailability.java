package Bed_reserve;

import java.sql.SQLException;
import java.util.List;

public class CheckBedAvailability {
    // to display the available beds in list of hospitals in the selected district.
    public Boolean displayBedAvailability(UserDatabaseInterface userDb) throws SQLException {

        List<HospitalDetailsContainer> hospitalList = userDb.getHospitalDetailsList(StateAndDistrictNames.getDistrict()[1]);
        if (hospitalList.size() == 0) {
            System.out.println("\n\tSorry...No hospitals are available in this district");
            return false;
        }
        int count = 0;
        while (count < hospitalList.size()) {
            HospitalDetailsContainer hospital = hospitalList.get(count);
            CheckBedAvailability.displayHospitalDetails(hospital);
            System.out.println("\nAvailable Bed Details:-");
            System.out.println("\n\tNormal Bed Details:-");
            CheckBedAvailability.displayBedDetails(userDb.getBedDetails(hospital.getHospitalId(), "normal_beds"));
            System.out.println("\n\tOxygen Supported Bed Details:-");
            CheckBedAvailability
                    .displayBedDetails(userDb.getBedDetails(hospital.getHospitalId(), "oxygen_supported_beds"));
            System.out.println("\n\tICU Bed Details:-");
            CheckBedAvailability.displayBedDetails(userDb.getBedDetails(hospital.getHospitalId(), "icu_beds"));
            System.out.println("\n\tVentilator Details:-");
            CheckBedAvailability.displayBedDetails(userDb.getBedDetails(hospital.getHospitalId(), "ventilators"));
            System.out.println("\n---------------------------------------------------------------------\n");
            count++;
        }
        return true;

    }

    public static void displayHospitalDetails(HospitalDetailsContainer hospital) {
        System.out.println("\nHospital details : -");
        System.out.println("\n\tHospital ID             : " + hospital.getHospitalId());
        System.out.println("\n\tHospital Name           : " + hospital.getHospitalName());
        System.out.println("\n\tHospital Address        : " + hospital.getHospitalAddress());
        System.out.println("\n\tDistrict                : " + hospital.getHospitalDistrict());
        System.out.println("\n\tState                   : " + hospital.getHospitalState());
        System.out.println("\n\tContact No              : " + hospital.getHospitalContact());
    }

    public static void displayBedDetails(BedDetailsContainer bed) {
        System.out.println("\t\tThe No.of Vacant beds   : " + bed.getVacant());
        System.out.println("\t\tThe No.of Occupied beds : " + bed.getOccupied());
        System.out.println("\t\tThe NO.of Total beds    : " + bed.getTotal());
    }

    
}
