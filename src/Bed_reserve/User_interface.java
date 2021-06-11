package Bed_reserve;

import java.sql.SQLException;
import java.util.List;

public interface User_interface {
    public List<Hospital_details> show_hospital_details(String district) throws SQLException;

    public Bed_details get_bed_details(String hospital_id, String bed_type) throws SQLException;

    public void updateuserDetail(User_details user) throws SQLException;

    public void deregister_user(String adhaar_id) throws SQLException;

    public void delete_user_login(String adhaar_id) throws SQLException;

    public String get_user_password(String adhaar_id) throws SQLException;

    public User_details get_user_details(String adhaar_id) throws SQLException;

    public void update_user_password(String adhaar_id, String password) throws SQLException;
}

interface Register_user_interface {
    public boolean check_user_exists(String adhaar_id) throws SQLException;

    public void insertuserDetail(User_details user) throws SQLException;

    public void insert_user_login_cridentials(String adhaar_id, String password) throws SQLException;

    public String get_user_password(String adhaar_id) throws SQLException;

    public void cancel_reservation(String adhaar_id) throws SQLException;

}

interface Reserve_bed_interface {
    public Hospital_details get_hospital_details(String hospital_id) throws SQLException;

    public Bed_details get_bed_details(String hospital_id, String bed_type) throws SQLException;

    public boolean check_hospital_exists(String hospital_id) throws SQLException;

    public boolean check_user_reserved_bed(String adhaar_id, String hospital_id) throws SQLException;

    public boolean check_user_reserved(String adhaar_id) throws SQLException;

    public boolean check_user_admitted(String adhaar_id) throws SQLException;

    public void insert_reservation_details(Reservation_details reserve) throws SQLException;

    public void updatebedstatus(Bed_details bed, String hospital_id, String bed_type) throws SQLException;

    public Reservation_details get_user_reservation_details(String adhaar_id) throws SQLException;

    public boolean user_is_reservered(String adhaar_id) throws SQLException;

    public User_details get_user_details(String adhaar_id) throws SQLException;

    public void cancel_reservation(String adhaar_id) throws SQLException;

    public List<Reservation_details> get_expired_reservation() throws SQLException;

}