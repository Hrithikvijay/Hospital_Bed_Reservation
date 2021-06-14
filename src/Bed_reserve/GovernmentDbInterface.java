package Bed_reserve;

import java.sql.SQLException;

public interface GovernmentDbInterface {
    public boolean checkHospitalRegistered(String hospitalId) throws SQLException;

    public boolean checkUserAffected(String adhaarId) throws SQLException;

    public void patientCured(String adhaarId) throws SQLException;
}
