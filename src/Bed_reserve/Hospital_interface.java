package Bed_reserve;

import java.sql.SQLException;
import java.util.List;

public interface Hospital_interface {
    public boolean check_hospital_exists(String hospital_id) throws SQLException;

    public void insertHospitalDetail(Hospital_details hospital) throws SQLException;

    public void insert_hospital_login_cridentials(String hospital_id, String pass) throws SQLException;

    public void updatebedstatus(Bed_details bed, String hospital_id, String bed_type) throws SQLException;

    public String get_hospital_password(String hospital_id) throws SQLException;

    public void update_hospital_password(String hospital_id, String password) throws SQLException;

    public Hospital_details get_hospital_details(String hospital_id) throws SQLException;

    public void update_hospital_details(Hospital_details hospital) throws SQLException;

    public void cancel_reservation_deregister_hospital(String hospital_id) throws SQLException;

    public void deregister_hospital(String hospital_id) throws SQLException;

    public void hospital_acknowlegde_user(String adhaar_id) throws SQLException;

    public Bed_details get_bed_details(String hospital_id, String bed_type) throws SQLException;

    public void deregister_hospital_bed_details(String hospital_id, String bed_type) throws SQLException;

    public List<Reservation_details> get_deregistered_user(String hospital_id) throws SQLException;

    public void delete_hospital_login(String hospital_id) throws SQLException;

    public List<Reservation_details> get_admitted_user(String hospital_id) throws SQLException;
}

interface Insert_bed_details_interface {
    public void insert_bed_details(Bed_details bed, String hospital_id, String bed_type) throws SQLException;
}
