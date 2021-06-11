package Bed_reserve;

import java.sql.*;

public class Government_db implements Government_db_interface {
    private static Government_db single = null;

    private Government_db() {
    }

    public static Government_db getInstance() {
        if (single == null) {
            single = new Government_db();
        }
        return single;
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {

        Connection connection = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        //Please enter your local host password
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/government", "root", "PASSWORD");
        return connection;
    }

    public boolean check_hospital_registered(String hospital_id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean is_registered = false;
        try {
            connection = Government_db.getConnection();
            ps = connection.prepareStatement("select * from hospital_registry where Hospital_Id = ? ");
            ps.setString(1, hospital_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                is_registered = rs.getString(2).length() != 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return is_registered;
    }

    public boolean check_user_affected(String adhaar_id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean is_registerd = false;
        try {
            connection = Government_db.getConnection();
            ps = connection.prepareStatement("select * from active_covid_adhaar where adhaar_id=(?)");
            ps.setString(1, adhaar_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                is_registerd = rs.getString(2).length() != 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return is_registerd;
    }

    public void patient_cured(String adhaar_id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = Government_db.getConnection();
            ps = connection.prepareStatement("DELETE FROM active_covid_adhaar WHERE Adhaar_id = ? ");
            ps.setString(1, adhaar_id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }
}
