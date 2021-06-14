package Bed_reserve;

public class ReservationDetailsContainer {
    private String hospitalId;
    private String bedType;
    private String district;
    private String state;
    private String adhaarId;
    private String contact;
    private String status;
    private String date;

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setAdhaarId(String adhaarId) {
        this.adhaarId = adhaarId;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public String getBedType() {
        return bedType;
    }

    public String getDistrict() {
        return district;
    }

    public String getState() {
        return state;
    }

    public String getAdhaarId() {
        return adhaarId;
    }

    public String getStatus() {
        return status;
    }

    public String getContact() {
        return contact;
    }

    public String getDate() {
        return date;
    }
}
