package com.practica.ismael.pr10_fct_irp.data.local.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.RESTRICT;

@Entity(foreignKeys = {@ForeignKey(entity = Student.class,
        parentColumns = "idStudent",
        childColumns = "studentMeeting",
        onUpdate = CASCADE,
        onDelete = RESTRICT), @ForeignKey(entity = Company.class,
        parentColumns = "idCompany",
        childColumns = "companyMeeting",
        onUpdate = CASCADE,
        onDelete = RESTRICT), @ForeignKey(entity = Company.class,
        parentColumns = "nameCompany",
        childColumns = "companyNameMeeting",
        onUpdate = CASCADE,
        onDelete = RESTRICT)})

public class Meeting {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idMeeting", index = true)
    private long idMeeting;

    @ColumnInfo(name = "dateMeeting")
    private String dateMeeting;

    @ColumnInfo(name = "timeStart")
    private String timeStart;

    @ColumnInfo(name = "timeEnd")
    private String timeEnd;

    @ColumnInfo(name = "observations")
    private String observations;

    @ColumnInfo(name = "companyMeeting", index = true)
    private long companyMeeting;

    @ColumnInfo(name = "studentMeeting", index = true)
    private long studentMeeting;

    @ColumnInfo(name = "studentNameMeeting")
    private String studentNameMeeting;

    @ColumnInfo(name = "companyNameMeeting")
    private String companyNameMeeting;

    public Meeting(long idMeeting, String dateMeeting, String timeStart, String timeEnd,
                   String observations, long companyMeeting, long studentMeeting,
                   String studentNameMeeting, String companyNameMeeting) {
        this.idMeeting = idMeeting;
        this.dateMeeting = dateMeeting;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.observations = observations;
        this.companyMeeting = companyMeeting;
        this.studentMeeting = studentMeeting;
        this.studentNameMeeting = studentNameMeeting;
        this.companyNameMeeting = companyNameMeeting;
    }

    public long getIdMeeting() {
        return idMeeting;
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

    public String getObservations() {
        return observations;
    }

    public long getCompanyMeeting() {
        return companyMeeting;
    }

    public long getStudentMeeting() {
        return studentMeeting;
    }

    public String getStudentNameMeeting() {
        return studentNameMeeting;
    }

    public String getCompanyNameMeeting() {
        return companyNameMeeting;
    }


}
