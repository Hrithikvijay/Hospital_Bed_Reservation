package Bed_reserve;

import java.sql.SQLException;
import java.util.List;

public interface UserDatabaseInterface {
    public List<HospitalDetailsContainer> showHospitalDetails(String district) throws SQLException;

    public BedDetailsContainer getBedDetails(String hospitalId, String bedType) throws SQLException;

    public void updateUserDetail(UserDetailsContainer user) throws SQLException;

    public void deregisterUser(String adhaarId) throws SQLException;

    public void deleteUserLogin(String adhaarId) throws SQLException;

    public String getUserPassword(String adhaarId) throws SQLException;

    public UserDetailsContainer getUserDetails(String adhaarId) throws SQLException;

    public void updateUserPassword(String adhaarId, String password) throws SQLException;

    public boolean checkUserExists(String adhaarId) throws SQLException;

    public void insertUserDetail(UserDetailsContainer user) throws SQLException;

    public void insertUserLoginCridentials(String adhaarId, String password) throws SQLException;

    public void cancelReservation(String adhaarId) throws SQLException;

}

interface ReserveBedInterface {
    public HospitalDetailsContainer getHospitalDetails(String hospitalId) throws SQLException;

    public BedDetailsContainer getBedDetails(String hospitalId, String bedType) throws SQLException;

    public boolean checkHospitalExists(String hospitalId) throws SQLException;

    public boolean checkUserReservedBed(String adhaarId, String hospitalId) throws SQLException;

    public boolean checkUserReserved(String adhaarId) throws SQLException;

    public boolean checkUserAdmitted(String adhaarId) throws SQLException;

    public void insertReservationDetails(ReservationDetailsContainer reserve) throws SQLException;

    public void updateBedStatus(BedDetailsContainer bed, String hospitalId, String bedType) throws SQLException;

    public ReservationDetailsContainer getUserReservationDetails(String adhaarId) throws SQLException;

    public boolean userIsReservered(String adhaarId) throws SQLException;

    public UserDetailsContainer getUserDetails(String adhaarId) throws SQLException;

    public void cancelReservation(String adhaarId) throws SQLException;

    public List<ReservationDetailsContainer> getExpiredReservation() throws SQLException;

}