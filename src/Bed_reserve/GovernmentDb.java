package Bed_reserve;

import java.sql.*;

public class GovernmentDb implements GovernmentDbInterface {
    private static GovernmentDb single = null;

    private GovernmentDb() {
    }

    public static GovernmentDb getInstance() {
        if (single == null) {
            single = new GovernmentDb();
        }
        return single;
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {

        Connection connection = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        //Please enter your local host password
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/government", "root", "#Hrithik25");
        return connection;
    }

    public boolean checkHospitalRegistered(String hospitalId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean isRegistered = false;
        try {
            connection = GovernmentDb.getConnection();
            ps = connection.prepareStatement("select * from hospital_registry where Hospital_Id = ? ");
            ps.setString(1, hospitalId);
            rs = ps.executeQuery();
            if (rs.next()) {
                isRegistered = rs.getString(2).length() != 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return isRegistered;
    }

    public boolean checkUserAffected(String adhaarId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean isRegisterd = false;
        try {
            connection = GovernmentDb.getConnection();
            ps = connection.prepareStatement("select * from active_covid_adhaar where adhaar_id=(?)");
            ps.setString(1, adhaarId);
            rs = ps.executeQuery();
            if (rs.next()) {
                isRegisterd = rs.getString(2).length() != 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return isRegisterd;
    }

    public void patientCured(String adhaarId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = GovernmentDb.getConnection();
            ps = connection.prepareStatement("DELETE FROM active_covid_adhaar WHERE Adhaar_id = ? ");
            ps.setString(1, adhaarId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }
}
