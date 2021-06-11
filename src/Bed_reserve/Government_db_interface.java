package Bed_reserve;

import java.sql.SQLException;

public interface Government_db_interface {
    public boolean check_hospital_registered(String hospital_id) throws SQLException;

    public boolean check_user_affected(String adhaar_id) throws SQLException;

    public void patient_cured(String adhaar_id) throws SQLException;
}
