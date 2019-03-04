package com.practica.ismael.pr10_fct_irp.data.local.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"nameCompany"}, unique = true)})
public class Company {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idCompany", index = true)
    private long idCompany;

    @ColumnInfo(name = "nameCompany")
    private String nameCompany;

    @ColumnInfo(name = "cif")
    private String cif;

    @ColumnInfo(name = "addressCompany")
    private String addressCompany;

    @ColumnInfo(name = "telCompany")
    private int telCompany;

    @ColumnInfo(name = "emailCompany")
    private String emailCompany;

    @ColumnInfo(name = "logoCompany")
    private String logoCompany;

    @ColumnInfo(name = "nameTutor")
    private String nameTutor;

    @ColumnInfo(name = "telTutor")
    private int telTutor;

    public Company(long idCompany, String nameCompany, String cif, String addressCompany, int telCompany, String emailCompany, String logoCompany, String nameTutor, int telTutor) {
        this.idCompany = idCompany;
        this.nameCompany = nameCompany;
        this.cif = cif;
        this.addressCompany = addressCompany;
        this.telCompany = telCompany;
        this.emailCompany = emailCompany;
        this.logoCompany = logoCompany;
        this.nameTutor = nameTutor;
        this.telTutor = telTutor;
    }

    public long getIdCompany() {
        return idCompany;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public String getCif() {
        return cif;
    }

    public String getAddressCompany() {
        return addressCompany;
    }

    public int getTelCompany() {
        return telCompany;
    }

    public String getEmailCompany() {
        return emailCompany;
    }

    public String getLogoCompany() {
        return logoCompany;
    }

    public String getNameTutor() {
        return nameTutor;
    }

    public int getTelTutor() {
        return telTutor;
    }

    public void setIdCompany(long idCompany) {
        this.idCompany = idCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public void setAddressCompany(String addressCompany) {
        this.addressCompany = addressCompany;
    }

    public void setTelCompany(int telCompany) {
        this.telCompany = telCompany;
    }

    public void setEmailCompany(String emailCompany) {
        this.emailCompany = emailCompany;
    }

    public void setLogoCompany(String logoCompany) {
        this.logoCompany = logoCompany;
    }

    public void setNameTutor(String nameTutor) {
        this.nameTutor = nameTutor;
    }

    public void setTelTutor(int telTutor) {
        this.telTutor = telTutor;
    }
}
