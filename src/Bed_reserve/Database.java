package Bed_reserve;

import java.util.*;
import java.sql.*;

public class Database implements HospitalDatabaseInterface, UserDatabaseInterface, ReserveBedInterface {
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
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bed_reservation", "root", "#Hrithik25");
        return connection;

    }

    public void insertHospitalDetail(HospitalDetailsContainer hosp) throws SQLException {

        Connection connection = null;

        PreparedStatement ps = null;

        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement(
                    "insert into hospital_details(Hospital_Id,Hospital_Name,Address,District,State,Contact)values(?,?,?,?,?,?)");
            ps.setString(1, hosp.getHospitalId());
            ps.setString(2, hosp.getHospitalName());
            ps.setString(3, hosp.getHospitalAddress());
            ps.setString(4, hosp.getHospitalDistrict());
            ps.setString(5, hosp.getHospitalState());
            ps.setString(6, hosp.getHospitalContact());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void insertBedDetails(BedDetailsContainer bed, String hospitalId, String bedType) throws SQLException {
        Connection connection = null;

        PreparedStatement ps = null;

        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement(
                    "insert into " + bedType + "(Bed_Id,Hospital_Id,Vacant,Occupied,Total)values(NULL,?,?,?,?)");
            ps.setString(1, hospitalId);
            ps.setInt(2, bed.getVacant());
            ps.setInt(3, bed.getOccupied());
            ps.setInt(4, bed.getTotal());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void updateBedStatus(BedDetailsContainer bed, String hospitalId, String bedType) throws SQLException {
        Connection connection = null;

        PreparedStatement ps = null;

        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement(
                    "update " + bedType + " set Vacant = ?,Occupied = ? ,Total = ? where Hospital_Id = ?");
            ps.setInt(1, bed.getVacant());
            ps.setInt(2, bed.getOccupied());
            ps.setInt(3, bed.getTotal());
            ps.setString(4, hospitalId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public List<HospitalDetailsContainer> showHospitalDetails(String district) throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<HospitalDetailsContainer> hospitalList = new ArrayList<HospitalDetailsContainer>();
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
            HospitalDetailsContainer hospital = new HospitalDetailsContainer();
            hospital.setHospitalId(rs.getString(1));
            hospital.setHospitalName(rs.getString(2));
            hospital.setHospitalAddress(rs.getString(3));
            hospital.setHospitalDistrict(rs.getString(4));
            hospital.setHospitalState(rs.getString(5));
            hospital.setHospitalContact(rs.getString(6));
            hospitalList.add(hospital);
        }
        connection.close();
        return hospitalList;
    }

    public HospitalDetailsContainer getHospitalDetails(String hospitalId) throws SQLException {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        HospitalDetailsContainer hospital;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("select * from  hospital_details where Hospital_Id=(?)",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, hospitalId);
            rs = ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        rs.absolute(1);
        hospital = new HospitalDetailsContainer();
        hospital.setHospitalId(rs.getString(1));
        hospital.setHospitalName(rs.getString(2));
        hospital.setHospitalAddress(rs.getString(3));
        hospital.setHospitalDistrict(rs.getString(4));
        hospital.setHospitalState(rs.getString(5));
        hospital.setHospitalContact(rs.getString(6));
        connection.close();
        return hospital;
    }

    public BedDetailsContainer getBedDetails(String hospitalId, String bedType) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("select * from " + bedType + " where Hospital_Id = ? ",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, hospitalId);
            rs = ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        rs.absolute(1);
        BedDetailsContainer bedDetails = new BedDetailsContainer(rs.getInt(3), rs.getInt(4), rs.getInt(5));
        connection.close();
        return bedDetails;
    }

    public String getHospitalPassword(String hospitalId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String password = "";
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("select * from hospital_login where Hospital_Id=(?)");
            ps.setString(1, hospitalId);
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

    public boolean checkHospitalExists(String hospitalId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean isRegistered = false;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("select * from hospital_login where Hospital_Id= ?");
            ps.setString(1, hospitalId);
            rs = ps.executeQuery();
            if (rs.next()) {
                isRegistered = rs.getString(3).length() != 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return isRegistered;
    }

    public void insertHospitalLoginCridentials(String hospitalId, String pass) throws SQLException {

        Connection connection = null;

        PreparedStatement ps = null;

        try {

            connection = Database.getConnection();
            ps = connection
                    .prepareStatement("insert into hospital_login(User_id,Hospital_Id,Password)values(NULL,?,?)");
            ps.setString(1, hospitalId);
            ps.setString(2, pass);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public boolean checkUserExists(String adhaarId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exits = false;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("select * from user_login where Adhaar_id= ? ");
            ps.setString(1, adhaarId);
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

    public void insertUserDetail(UserDetailsContainer user) throws SQLException {

        Connection connection = null;

        PreparedStatement ps = null;

        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement(
                    "insert into user_details(Adhaar_id,User_Name,Address,District,State,Age,Gender,Contact)values(?,?,?,?,?,?,?,?)");
            ps.setString(1, user.getAdhaarId());
            ps.setString(2, user.getUserName());
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

    public void updateUserDetail(UserDetailsContainer user) throws SQLException {

        Connection connection = null;

        PreparedStatement ps = null;

        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement(
                    "UPDATE user_details SET User_Name = ? ,Address = ? ,District = ? ,State = ? ,Age = ?,Gender = ?,Contact = ? WHERE Adhaar_id = ?");
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getAddress());
            ps.setString(3, user.getDistrict());
            ps.setString(4, user.getState());
            ps.setString(5, user.getAge());
            ps.setString(6, user.getGender());
            ps.setString(7, user.getContact());
            ps.setString(8, user.getAdhaarId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void insertUserLoginCridentials(String adhaarId, String password) throws SQLException {
        Connection connection = null;

        PreparedStatement ps = null;

        try {

            connection = Database.getConnection();
            ps = connection.prepareStatement("insert into user_login(User_id,Adhaar_Id,Password)values(NULL,?,?)");
            ps.setString(1, adhaarId);
            ps.setString(2, password);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public boolean checkUserReservedBed(String adhaarId, String hospitalId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exits = false;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement(
                    "select * from reservation where Adhaar_id = ? AND Status = ? AND hospital_Id = ?");
            ps.setString(1, adhaarId);
            ps.setString(2, "Reserved");
            ps.setString(3, hospitalId);
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

    public boolean checkUserReserved(String adhaarId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exits = false;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("select * from reservation where Adhaar_id = ? AND Status = ?");
            ps.setString(1, adhaarId);
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

    public boolean checkUserAdmitted(String adhaarId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean exits = false;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("select * from reservation where Adhaar_id= ? AND Status= ?");
            ps.setString(1, adhaarId);
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

    public List<ReservationDetailsContainer> getAdmittedUser(String hospitalId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ReservationDetailsContainer> reservationList = new ArrayList<ReservationDetailsContainer>();
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("SELECT * FROM reservation  where Status = ? and hospital_Id = ? ");
            ps.setString(1, "Admitted");
            ps.setString(2, hospitalId);
            rs = ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        while (rs.next()) {
            ReservationDetailsContainer reserve = new ReservationDetailsContainer();
            reserve.setAdhaarId(rs.getString(2));
            reserve.setHospitalId(rs.getString(3));
            reserve.setDistrict(rs.getString(4));
            reserve.setState(rs.getString(5));
            reserve.setContact(rs.getString(6));
            reserve.setBedType(rs.getString(7));
            reserve.setStatus(rs.getString(8));
            reserve.setDate(rs.getString(9));
            reservationList.add(reserve);
        }
        connection.close();
        return reservationList;
    }

    public UserDetailsContainer getUserDetails(String adhaarId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        UserDetailsContainer user;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("select * from user_details where Adhaar_id= (?) ",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, adhaarId);
            rs = ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        rs.absolute(1);
        user = new UserDetailsContainer();
        user.setAdhaarId(rs.getString(1));
        user.setUserName(rs.getString(2));
        user.setAddress(rs.getString(3));
        user.setDistrict(rs.getString(4));
        user.setState(rs.getString(5));
        user.setAge(rs.getString(6));
        user.setGender(rs.getString(7));
        user.setContact(rs.getString(8));
        connection.close();
        return user;
    }

    public void insertReservationDetails(ReservationDetailsContainer reserve) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {

            connection = Database.getConnection();
            ps = connection.prepareStatement(
                    "insert into reservation(Reservation_Id,Adhaar_id,hospital_Id,District,State,Contact,Bed_Type,Status,Date_and_Time)values(NULL,?,?,?,?,?,?,?,NOW())");
            ps.setString(1, reserve.getAdhaarId());
            ps.setString(2, reserve.getHospitalId());
            ps.setString(3, reserve.getDistrict());
            ps.setString(4, reserve.getState());
            ps.setString(5, reserve.getContact());
            ps.setString(6, reserve.getBedType());
            ps.setString(7, reserve.getStatus());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public boolean userIsReservered(String adhaarId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean isReserved = false;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("SELECT Status FROM reservation WHERE Adhaar_id = ? ");
            ps.setString(1, adhaarId);
            rs = ps.executeQuery();
            if (rs.next()) {
                isReserved = rs.getString(1).equals("Reserved");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return isReserved;
    }

    public void cancelReservation(String adhaarId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("DELETE FROM reservation WHERE Adhaar_id = ? ");
            ps.setString(1, adhaarId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void cancelReservationDeregisterHospital(String hospitalId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("DELETE FROM reservation WHERE hospital_Id = ? ");
            ps.setString(1, hospitalId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void deregisterHospital(String hospitalId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("DELETE FROM hospital_details WHERE Hospital_Id = ? ");
            ps.setString(1, hospitalId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void deregisterUser(String adhaarId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("DELETE FROM user_details WHERE Adhaar_id = ? ");
            ps.setString(1, adhaarId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void deregisterHospitalBedDetails(String hospitalId, String bedType) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("DELETE FROM " + bedType + " WHERE Hospital_Id = ? ");
            ps.setString(1, hospitalId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public ReservationDetailsContainer getUserReservationDetails(String adhaarId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ReservationDetailsContainer reserve;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("SELECT * FROM reservation WHERE Adhaar_id = ? ",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, adhaarId);
            rs = ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        rs.absolute(1);
        reserve = new ReservationDetailsContainer();
        reserve.setAdhaarId(rs.getString(2));
        reserve.setHospitalId(rs.getString(3));
        reserve.setDistrict(rs.getString(4));
        reserve.setState(rs.getString(5));
        reserve.setContact(rs.getString(6));
        reserve.setBedType(rs.getString(7));
        reserve.setStatus(rs.getString(8));
        reserve.setDate(rs.getString(9));
        connection.close();
        return reserve;
    }

    public List<ReservationDetailsContainer> getExpiredReservation() throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ReservationDetailsContainer> reservationList = new ArrayList<ReservationDetailsContainer>();
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement(
                    "SELECT * FROM reservation  where Date_and_Time  < NOW() - interval 24 hour and Status = ?");
            ps.setString(1, "Reserved");
            rs = ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        while (rs.next()) {
            ReservationDetailsContainer reserve = new ReservationDetailsContainer();
            reserve.setAdhaarId(rs.getString(2));
            reserve.setHospitalId(rs.getString(3));
            reserve.setDistrict(rs.getString(4));
            reserve.setState(rs.getString(5));
            reserve.setContact(rs.getString(6));
            reserve.setBedType(rs.getString(7));
            reserve.setStatus(rs.getString(8));
            reserve.setDate(rs.getString(9));
            reservationList.add(reserve);
        }
        connection.close();
        return reservationList;
    }

    public void hospitalAcknowlegdeUser(String adhaarId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {

            connection = Database.getConnection();
            ps = connection
                    .prepareStatement("UPDATE reservation SET Status = ? , Date_and_Time = NOW() WHERE Adhaar_id= ? ");
            ps.setString(1, "Admitted");
            ps.setString(2, adhaarId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public String getUserPassword(String adhaarId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String password = "";
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("select * from user_login where Adhaar_id=(?)");
            ps.setString(1, adhaarId);
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

    public void updateUserPassword(String adhaarId, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("UPDATE user_login SET Password = ? where Adhaar_id=(?)");
            ps.setString(1, password);
            ps.setString(2, adhaarId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void updateHospitalPassword(String hospitalId, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("UPDATE hospital_login SET Password = ? where Hospital_Id=(?)");
            ps.setString(1, password);
            ps.setString(2, hospitalId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void updateHospitalDetails(HospitalDetailsContainer hospital) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement(
                    "UPDATE hospital_details SET Hospital_Name = ?,Address = ?,District = ?,State = ?,Contact = ?  where Hospital_Id=(?)");
            ps.setString(1, hospital.getHospitalName());
            ps.setString(2, hospital.getHospitalAddress());
            ps.setString(3, hospital.getHospitalDistrict());
            ps.setString(4, hospital.getHospitalState());
            ps.setString(5, hospital.getHospitalContact());
            ps.setString(6, hospital.getHospitalId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void deleteHospitalLogin(String hospitalId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("DELETE FROM hospital_login  where Hospital_Id=(?)");
            ps.setString(1, hospitalId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void deleteUserLogin(String adhaarId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("DELETE FROM user_login  where Adhaar_id=(?)");
            ps.setString(1, adhaarId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public List<ReservationDetailsContainer> getDeregisteredUser(String hospitalId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ReservationDetailsContainer> reservationList = new ArrayList<ReservationDetailsContainer>();
        try {
            connection = Database.getConnection();
            ps = connection.prepareStatement("SELECT * FROM reservation  where hospital_Id = ?");
            ps.setString(1, hospitalId);
            rs = ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        while (rs.next()) {
            ReservationDetailsContainer reserve = new ReservationDetailsContainer();
            reserve.setAdhaarId(rs.getString(2));
            reserve.setHospitalId(rs.getString(3));
            reserve.setDistrict(rs.getString(4));
            reserve.setState(rs.getString(5));
            reserve.setContact(rs.getString(6));
            reserve.setBedType(rs.getString(7));
            reserve.setStatus(rs.getString(8));
            reserve.setDate(rs.getString(9));
            reservationList.add(reserve);
        }
        connection.close();
        return reservationList;
    }

}
