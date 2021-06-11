package Bed_reserve;

public class Reservation_details {
    private String hospital_id;
    private String bed_type;
    private String district;
    private String state;
    private String adhaar_id;
    private String contact;
    private String status;
    private String date;

    public void setHospital_id(String hospital_id) {
        this.hospital_id = hospital_id;
    }

    public void setBed_type(String bed_type) {
        this.bed_type = bed_type;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setAdhaar_id(String adhaar_id) {
        this.adhaar_id = adhaar_id;
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

    public String getHospital_id() {
        return hospital_id;
    }

    public String getBed_type() {
        return bed_type;
    }

    public String getDistrict() {
        return district;
    }

    public String getState() {
        return state;
    }

    public String getAdhaar_id() {
        return adhaar_id;
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
