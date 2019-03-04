package com.practica.ismael.pr10_fct_irp.data.local.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.RESTRICT;

@Entity(indices = {@Index(value = {"nameStudent"}, unique = true)} ,foreignKeys = {@ForeignKey(entity = Company.class,
        parentColumns = "idCompany",
        childColumns = "companyStudent",
        onUpdate = CASCADE,
        onDelete = RESTRICT)})

public class Student {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idStudent", index = true)
    private long idStudent;

    @ColumnInfo(name = "nameStudent")
    private String nameStudent;

    @ColumnInfo(name = "telStudent")
    private int telStudent;

    @ColumnInfo(name = "emailStudent")
    private String emailStudent;

    @ColumnInfo(name = "gradeStudent")
    private String gradeStudent;

    @NonNull
    @ColumnInfo(name = "companyStudent", index = true)
    private long companyStudent;

    @ColumnInfo(name = "scheduleStudent")
    private String scheduleStudent;

    @ColumnInfo(name = "nameCompany")
    private String nameCompany;

    public Student(long idStudent, String nameStudent, int telStudent, String emailStudent, String gradeStudent, long companyStudent, String scheduleStudent, String nameCompany) {
        this.idStudent = idStudent;
        this.nameStudent = nameStudent;
        this.telStudent = telStudent;
        this.emailStudent = emailStudent;
        this.gradeStudent = gradeStudent;
        this.companyStudent = companyStudent;
        this.scheduleStudent = scheduleStudent;
        this.nameCompany = nameCompany;
    }

    public long getIdStudent() {
        return idStudent;
    }

    public String getNameStudent() {
        return nameStudent;
    }

    public int getTelStudent() {
        return telStudent;
    }

    public String getEmailStudent() {
        return emailStudent;
    }

    public String getGradeStudent() {
        return gradeStudent;
    }

    public long getCompanyStudent() {
        return companyStudent;
    }

    public String getScheduleStudent() {
        return scheduleStudent;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setIdStudent(long idStudent) {
        this.idStudent = idStudent;
    }

    public void setNameStudent(String nameStudent) {
        this.nameStudent = nameStudent;
    }

    public void setTelStudent(int telStudent) {
        this.telStudent = telStudent;
    }

    public void setEmailStudent(String emailStudent) {
        this.emailStudent = emailStudent;
    }

    public void setGradeStudent(String gradeStudent) {
        this.gradeStudent = gradeStudent;
    }

    public void setCompanyStudent(long companyStudent) {
        this.companyStudent = companyStudent;
    }

    public void setScheduleStudent(String scheduleStudent) {
        this.scheduleStudent = scheduleStudent;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }
}
