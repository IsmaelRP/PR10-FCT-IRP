package com.practica.ismael.pr10_fct_irp.data.local.model;

import androidx.room.DatabaseView;

@DatabaseView("SELECT m.idMeeting, s.nameStudent, s.idStudent, c.nameCompany," +
        "c.idCompany, m.dateMeeting, m.timeStart, m.timeEnd FROM Student s " +
        "INNER JOIN Company c ON c.idCompany = s.companyStudent " +
        "LEFT JOIN Meeting m ON m.studentMeeting = s.idStudent")
public class NextMeeting {

    private long idMeeting;
    private String nameStudent;
    private long idStudent;
    private String nameCompany;
    private long idCompany;
    private String dateMeeting;
    private String timeStart;
    private String timeEnd;

    public NextMeeting(long idMeeting, String nameStudent, long idStudent, String nameCompany, long idCompany, String dateMeeting, String timeStart, String timeEnd) {
        this.idMeeting = idMeeting;
        this.nameStudent = nameStudent;
        this.idStudent = idStudent;
        this.nameCompany = nameCompany;
        this.idCompany = idCompany;
        this.dateMeeting = dateMeeting;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public String getNameStudent() {
        return nameStudent;
    }

    public long getIdStudent() {
        return idStudent;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public long getIdCompany() {
        return idCompany;
    }

    public String getDateMeeting() {
        return dateMeeting;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setNameStudent(String nameStudent) {
        this.nameStudent = nameStudent;
    }

    public void setIdStudent(long idStudent) {
        this.idStudent = idStudent;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public void setIdCompany(long idCompany) {
        this.idCompany = idCompany;
    }

    public void setDateMeeting(String dateMeeting) {
        this.dateMeeting = dateMeeting;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public long getIdMeeting() {
        return idMeeting;
    }

    public void setIdMeeting(long idMeeting) {
        this.idMeeting = idMeeting;
    }
}
