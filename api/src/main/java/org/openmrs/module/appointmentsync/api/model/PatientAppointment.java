package org.openmrs.module.appointmentsync.api.model;

public class PatientAppointment {

    private String patientAppointmentId;
    private String identifier;
    private String phone;
    private String names;
    private String startDate;
    private String endDate;
    private String gender;
    private String location;
    private String status;
    private String comment;

    public PatientAppointment() {};

    public PatientAppointment(String patientAppointmentId, String identifier, String phone, String names, String startDate, String endDate, String gender, String location, String status, String comment) {
        this.patientAppointmentId = patientAppointmentId;
        this.identifier = identifier;
        this.phone = phone;
        this.names = names;
        this.startDate = startDate;
        this.endDate = endDate;
        this.gender = gender;
        this.location = location;
        this.status = status;
        this.comment = comment;
    }

    public String getPatientAppointmentId() {
      return this.patientAppointmentId;
    }
    public void setPatientAppointmentId(String value) {
      this.patientAppointmentId = value;
    }

    public String getIdentifier() {
      return this.identifier;
    }
    public void setIdentifier(String value) {
      this.identifier = value;
    }

    public String getPhone() {
      return this.phone;
    }
    public void setPhone(String value) {
      this.phone = value;
    }

    public String getNames() {
      return this.names;
    }
    public void setNames(String value) {
      this.names = value;
    }

    public String getStartDate() {
      return this.startDate;
    }
    public void setStartDate(String value) {
      this.startDate = value;
    }

    public String getEndDate() {
      return this.endDate;
    }
    public void setEndDate(String value) {
      this.endDate = value;
    }

    public String getGender() {
      return this.gender;
    }
    public void setGender(String value) {
      this.gender = value;
    }

    public String getLocation() {
      return this.location;
    }
    public void setLocation(String value) {
      this.location = value;
    }

    public String getStatus() {
      return this.status;
    }
    public void setStatus(String value) {
      this.status = value;
    }

    public String getComment() {
      return this.comment;
    }
    public void setComment(String value) {
      this.comment = value;
    }

    @Override
    public String toString() {
        return "PatientAppointment{" +
                "appointmentId='" + patientAppointmentId + '\'' +
                ", identifier='" + identifier + '\'' +
                ", phone='" + phone + '\'' +
                ", names='" + names + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", gender='" + gender + '\'' +
                ", location='" + location + '\'' +
                ", status='" + status + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
