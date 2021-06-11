package Bed_reserve;

import java.util.*;
import java.sql.*;

public class Database implements Hospital_interface, Insert_bed_details_interface, User_interface,
        Register_user_interface, Reserve_bed_interface {
    private static Database single = null;

    private Database() {
    }

    public static Database getInstance() {
        if (single == null) {
            single = new Database();
        }
        return single;
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {

        Connection connection = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Please enter your local host password
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bed_reservation", "root", "PASSWORD");
        return connection;

    }

    public void insertHospitalDetail(Hospital_details hosp) throws SQLException {

        Connection connection = null;

        PreparedStatement ps = null;

        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement(
                    "insert into hospital_details(Hospital_Id,Hospital_Name,Address,District,State,Contact)values(?,?,?,?,?,?)");
            ps.setString(1, hosp.getHospital_id());
            ps.setString(2, hosp.getHospital_name());
            ps.setString(3, hosp.getHospital_address());
            ps.setString(4, hosp.getHospital_district());
            ps.setString(5, hosp.getHospital_state());
            ps.setString(6, hosp.getHospital_contact());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void insert_bed_details(Bed_details bed, String hospital_id, String bed_type) throws SQLException {
        Connection connection = null;

        PreparedStatement ps = null;

        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement(
                    "insert into " + bed_type + "(Bed_Id,Hospital_Id,Vacant,Occupied,Total)values(NULL,?,?,?,?)");
            ps.setString(1, hospital_id);
            ps.setInt(2, bed.getVacant());
            ps.setInt(3, bed.getOccupied());
            ps.setInt(4, bed.getTotal());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void updatebedstatus(Bed_details bed, String hospital_id, String bed_type) throws SQLException {
        Connection connection = null;

        PreparedStatement ps = null;

        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement(
                    "update " + bed_type + " set Vacant = ?,Occupied = ? ,Total = ? where Hospital_Id = ?");
            ps.setInt(1, bed.getVacant());
            ps.setInt(2, bed.getOccupied());
            ps.setInt(3, bed.getTotal());
            ps.setString(4, hospital_id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public List<Hospital_details> show_hospital_details(String district) throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Hospital_details> hospital_list = new ArrayList<Hospital_details>();
        try {
            connection = Database.getConnection();
            ps = connection
                    .prepareStatement("select * from  hospital_details where District=(?) order by Hospital_Name");
            ps.setString(1, district);
            rs = ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        while (rs.next()) {
            Hospital_details hospital = new Hospital_details();
            hospital.setHospital_id(rs.getString(1));
            hospital.setHospital_name(rs.getString(2));
            hospital.setHospital_address(rs.getString(3));
            hospital.setHospital_district(rs.getString(4));
            hospital.setHospital_state(rs.getString(5));
            hospital.setHospital_contact(rs.getString(6));
            hospital_list.add(hospital);
        }
        connection.close();
        return hospital_list;
    }

    public Hospital_details get_hospital_details(String hospital_id) throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Hospital_details hospital;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("select * from  hospital_details where Hospital_Id=(?)",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, hospital_id);
            rs = ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        rs.absolute(1);
        hospital = new Hospital_details();
        hospital.setHospital_id(rs.getString(1));
        hospital.setHospital_name(rs.getString(2));
        hospital.setHospital_address(rs.getString(3));
        hospital.setHospital_district(rs.getString(4));
        hospital.setHospital_state(rs.getString(5));
        hospital.setHospital_contact(rs.getString(6));
        connection.close();
        return hospital;
    }

    public Bed_details get_bed_details(String hospital_id, String bed_type) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("select * from " + bed_type + " where Hospital_Id = ? ",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, hospital_id);
            rs = ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        rs.absolute(1);
        Bed_details bed_details = new Bed_details(rs.getInt(3), rs.getInt(4), rs.getInt(5));
        connection.close();
        return bed_details;
    }

    public String get_hospital_password(String hospital_id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String password = "";
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("select * from hospital_login where Hospital_Id=(?)");
            ps.setString(1, hospital_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                password = rs.getString(3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return password;
    }

    public boolean check_hospital_exists(String hospital_id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean is_registered = false;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("select * from hospital_login where Hospital_Id= ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, hospital_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                is_registered = rs.getString(3).length() != 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return is_registered;
    }

    public void insert_hospital_login_cridentials(String hospital_id, String pass) throws SQLException {

        Connection connection = null;

        PreparedStatement ps = null;

        try {

            connection = Database.getConnection();
            ps = connection
                    .prepareStatement("insert into hospital_login(User_id,Hospital_Id,Password)values(NULL,?,?)");
            ps.setString(1, hospital_id);
            ps.setString(2, pass);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public boolean check_user_exists(String adhaar_id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exits = false;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("select * from user_login where Adhaar_id= ? ");
            ps.setString(1, adhaar_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                exits = rs.getString(3).length() != 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return exits;
    }

    public void insertuserDetail(User_details user) throws SQLException {

        Connection connection = null;

        PreparedStatement ps = null;

        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement(
                    "insert into user_details(Adhaar_id,User_Name,Address,District,State,Age,Gender,Contact)values(?,?,?,?,?,?,?,?)");
            ps.setString(1, user.getAdhaar_id());
            ps.setString(2, user.getUser_name());
            ps.setString(3, user.getAddress());
            ps.setString(4, user.getDistrict());
            ps.setString(5, user.getState());
            ps.setString(6, user.getAge());
            ps.setString(7, user.getGender());
            ps.setString(8, user.getContact());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void updateuserDetail(User_details user) throws SQLException {

        Connection connection = null;

        PreparedStatement ps = null;

        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement(
                    "UPDATE user_details SET User_Name = ? ,Address = ? ,District = ? ,State = ? ,Age = ?,Gender = ?,Contact = ? WHERE Adhaar_id = ?");
            ps.setString(1, user.getUser_name());
            ps.setString(2, user.getAddress());
            ps.setString(3, user.getDistrict());
            ps.setString(4, user.getState());
            ps.setString(5, user.getAge());
            ps.setString(6, user.getGender());
            ps.setString(7, user.getContact());
            ps.setString(8, user.getAdhaar_id());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void insert_user_login_cridentials(String adhaar_id, String password) throws SQLException {
        Connection connection = null;

        PreparedStatement ps = null;

        try {

            connection = Database.getConnection();
            ps = connection.prepareStatement("insert into user_login(User_id,Adhaar_Id,Password)values(NULL,?,?)");
            ps.setString(1, adhaar_id);
            ps.setString(2, password);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public boolean check_user_reserved_bed(String adhaar_id, String hospital_id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exits = false;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement(
                    "select * from reservation where Adhaar_id = ? AND Status = ? AND hospital_Id = ?");
            ps.setString(1, adhaar_id);
            ps.setString(2, "Reserved");
            ps.setString(3, hospital_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                exits = rs.getString(3).length() != 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return exits;
    }

    public boolean check_user_reserved(String adhaar_id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exits = false;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("select * from reservation where Adhaar_id = ? AND Status = ?");
            ps.setString(1, adhaar_id);
            ps.setString(2, "Reserved");
            rs = ps.executeQuery();
            if (rs.next()) {
                exits = rs.getString(3).length() != 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return exits;
    }

    public boolean check_user_admitted(String adhaar_id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exits = false;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("select * from reservation where Adhaar_id= ? AND Status= ?");
            ps.setString(1, adhaar_id);
            ps.setString(2, "Admitted");
            rs = ps.executeQuery();
            if (rs.next()) {
                exits = rs.getString(3).length() != 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return exits;
    }

    public List<Reservation_details> get_admitted_user(String hospital_id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Reservation_details> reservation_list = new ArrayList<Reservation_details>();
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("SELECT * FROM reservation  where Status=? and hospital_id=?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, "Admitted");
            ps.setString(2, hospital_id);
            rs = ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        while (rs.next()) {
            Reservation_details reserve = new Reservation_details();
            reserve.setAdhaar_id(rs.getString(2));
            reserve.setHospital_id(rs.getString(3));
            reserve.setDistrict(rs.getString(4));
            reserve.setState(rs.getString(5));
            reserve.setContact(rs.getString(6));
            reserve.setBed_type(rs.getString(7));
            reserve.setStatus(rs.getString(8));
            reserve.setDate(rs.getString(9));
            reservation_list.add(reserve);
        }
        connection.close();
        return reservation_list;
    }

    public User_details get_user_details(String adhaar_id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User_details user;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("select * from user_details where Adhaar_id= (?) ",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, adhaar_id);
            rs = ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        rs.absolute(1);
        user = new User_details();
        user.setAdhaar_id(rs.getString(1));
        user.setUser_name(rs.getString(2));
        user.setAddress(rs.getString(3));
        user.setDistrict(rs.getString(4));
        user.setState(rs.getString(5));
        user.setAge(rs.getString(6));
        user.setGender(rs.getString(7));
        user.setContact(rs.getString(8));
        connection.close();
        return user;
    }

    public void insert_reservation_details(Reservation_details reserve) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {

            connection = Database.getConnection();
            ps = connection.prepareStatement(
                    "insert into reservation(Reservation_Id,Adhaar_id,hospital_Id,District,State,Contact,Bed_Type,Status,Date_and_Time)values(NULL,?,?,?,?,?,?,?,NOW())");
            ps.setString(1, reserve.getAdhaar_id());
            ps.setString(2, reserve.getHospital_id());
            ps.setString(3, reserve.getDistrict());
            ps.setString(4, reserve.getState());
            ps.setString(5, reserve.getContact());
            ps.setString(6, reserve.getBed_type());
            ps.setString(7, reserve.getStatus());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public boolean user_is_reservered(String adhaar_id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean is_reserved = false;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("SELECT Status FROM reservation WHERE Adhaar_id = ? ");
            ps.setString(1, adhaar_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                is_reserved = rs.getString(1).equals("Reserved");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return is_reserved;
    }

    public void cancel_reservation(String adhaar_id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("DELETE FROM reservation WHERE Adhaar_id = ? ");
            ps.setString(1, adhaar_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void cancel_reservation_deregister_hospital(String hospital_id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("DELETE FROM reservation WHERE hospital_id = ? ");
            ps.setString(1, hospital_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void deregister_hospital(String hospital_id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("DELETE FROM hospital_details WHERE Hospital_Id = ? ");
            ps.setString(1, hospital_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void deregister_user(String adhaar_id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("DELETE FROM user_details WHERE Adhaar_id = ? ");
            ps.setString(1, adhaar_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void deregister_hospital_bed_details(String hospital_id, String bed_type) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("DELETE FROM " + bed_type + " WHERE hospital_id = ? ");
            ps.setString(1, hospital_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public Reservation_details get_user_reservation_details(String adhaar_id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Reservation_details reserve;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("SELECT * FROM reservation WHERE Adhaar_id = ? ",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, adhaar_id);
            rs = ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        rs.absolute(1);
        reserve = new Reservation_details();
        reserve.setAdhaar_id(rs.getString(2));
        reserve.setHospital_id(rs.getString(3));
        reserve.setDistrict(rs.getString(4));
        reserve.setState(rs.getString(5));
        reserve.setContact(rs.getString(6));
        reserve.setBed_type(rs.getString(7));
        reserve.setStatus(rs.getString(8));
        reserve.setDate(rs.getString(9));
        connection.close();
        return reserve;
    }

    public List<Reservation_details> get_expired_reservation() throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Reservation_details> reservation_list = new ArrayList<Reservation_details>();
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement(
                    "SELECT * FROM reservation  where Date_and_Time  < NOW() - interval 24 hour and Status = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, "Reserved");
            rs = ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        while (rs.next()) {
            Reservation_details reserve = new Reservation_details();
            reserve.setAdhaar_id(rs.getString(2));
            reserve.setHospital_id(rs.getString(3));
            reserve.setDistrict(rs.getString(4));
            reserve.setState(rs.getString(5));
            reserve.setContact(rs.getString(6));
            reserve.setBed_type(rs.getString(7));
            reserve.setStatus(rs.getString(8));
            reserve.setDate(rs.getString(9));
            reservation_list.add(reserve);
        }
        connection.close();
        return reservation_list;
    }

    public void hospital_acknowlegde_user(String adhaar_id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {

            connection = Database.getConnection();
            ps = connection
                    .prepareStatement("UPDATE reservation SET Status = ? , Date_and_Time = NOW() WHERE Adhaar_id= ? ");
            ps.setString(1, "Admitted");
            ps.setString(2, adhaar_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public String get_user_password(String adhaar_id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String password = "";
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("select * from user_login where Adhaar_id=(?)");
            ps.setString(1, adhaar_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                password = rs.getString(3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return password;
    }

    public void update_user_password(String adhaar_id, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("UPDATE user_login SET Password = ? where Adhaar_id=(?)");
            ps.setString(1, password);
            ps.setString(2, adhaar_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void update_hospital_password(String hospital_id, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("UPDATE hospital_login SET Password = ? where Hospital_Id=(?)");
            ps.setString(1, password);
            ps.setString(2, hospital_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void update_hospital_details(Hospital_details hospital) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement(
                    "UPDATE hospital_details SET Hospital_Name = ?,Address = ?,District = ?,State = ?,Contact = ?  where Hospital_Id=(?)");
            ps.setString(1, hospital.getHospital_name());
            ps.setString(2, hospital.getHospital_address());
            ps.setString(3, hospital.getHospital_district());
            ps.setString(4, hospital.getHospital_state());
            ps.setString(5, hospital.getHospital_contact());
            ps.setString(6, hospital.getHospital_id());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void delete_hospital_login(String hospital_id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("DELETE FROM hospital_login  where Hospital_Id=(?)");
            ps.setString(1, hospital_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void delete_user_login(String adhaar_id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("DELETE FROM user_login  where Adhaar_id=(?)");
            ps.setString(1, adhaar_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public List<Reservation_details> get_deregistered_user(String hospital_id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Reservation_details> reservation_list = new ArrayList<Reservation_details>();
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("SELECT * FROM reservation  where hospital_id = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, hospital_id);
            rs = ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        while (rs.next()) {
            Reservation_details reserve = new Reservation_details();
            reserve.setAdhaar_id(rs.getString(2));
            reserve.setHospital_id(rs.getString(3));
            reserve.setDistrict(rs.getString(4));
            reserve.setState(rs.getString(5));
            reserve.setContact(rs.getString(6));
            reserve.setBed_type(rs.getString(7));
            reserve.setStatus(rs.getString(8));
            reserve.setDate(rs.getString(9));
            reservation_list.add(reserve);
        }
        connection.close();
        return reservation_list;
    }

}
