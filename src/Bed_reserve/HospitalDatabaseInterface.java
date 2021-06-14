package Bed_reserve;

import java.sql.SQLException;
import java.util.List;

public interface HospitalDatabaseInterface {
    public boolean checkHospitalExists(String hospitalId) throws SQLException;

    public void insertHospitalDetail(HospitalDetailsContainer hospital) throws SQLException;

    public void insertHospitalLoginCridentials(String hospitalId, String password) throws SQLException;

    public void updateBedStatus(BedDetailsContainer bed, String hospitalId, String bedType) throws SQLException;

    public String getHospitalPassword(String hospitalId) throws SQLException;

    public void updateHospitalPassword(String hospitalId, String password) throws SQLException;

    public HospitalDetailsContainer getHospitalDetails(String hospitalId) throws SQLException;

    public void updateHospitalDetails(HospitalDetailsContainer hospital) throws SQLException;

    public void cancelReservationDeregisterHospital(String hospitalId) throws SQLException;

    public void deregisterHospital(String hospitalId) throws SQLException;

    public void hospitalAcknowlegdeUser(String adhaarId) throws SQLException;

    public BedDetailsContainer getBedDetails(String hospitalId, String bedType) throws SQLException;

    public void deregisterHospitalBedDetails(String hospitalId, String bedType) throws SQLException;

    public List<ReservationDetailsContainer> getDeregisteredUser(String hospitalId) throws SQLException;

    public void deleteHospitalLogin(String hospitalId) throws SQLException;

    public List<ReservationDetailsContainer> getAdmittedUser(String hospitalId) throws SQLException;

    public void insertBedDetails(BedDetailsContainer bed, String hospitalId, String bedType) throws SQLException;
}

