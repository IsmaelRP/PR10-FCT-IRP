package com.practica.ismael.pr10_fct_irp.ui.activity;

import com.practica.ismael.pr10_fct_irp.data.local.model.Company;

import java.time.LocalDateTime;
import java.util.Date;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MutableLiveData<Company> companyEdit = new MutableLiveData<>();
    private MutableLiveData<String> homePage = new MutableLiveData<>();
    private MutableLiveData<Integer> visitDays = new MutableLiveData<>();
    private MutableLiveData<Boolean> initial = new MutableLiveData<>();
    private MutableLiveData<Date> actualDate = new MutableLiveData<>();
    private MutableLiveData<LocalDateTime> actualTime = new MutableLiveData<>();

    public void setCompanyEdit(Company companyEdit) {
        this.companyEdit.setValue(companyEdit);
    }

    public MutableLiveData<Company> getCompanyEdit() {
        return companyEdit;
    }

    public String getHomePage() {
        return homePage.getValue();
    }

    public void setHomePage(String homePage) {
        this.homePage.setValue(homePage);
    }

    public int getVisitDays() {
        return visitDays.getValue();
    }

    public void setVisitDays(int visitDays) {
        this.visitDays.setValue(visitDays);
    }

    public boolean getInitial() {
        return initial.getValue();
    }

    public void setInitial(boolean initial) {
        this.initial.setValue(initial);
    }

    public Date getActualDate() {
        return actualDate.getValue();
    }

    public void setActualDate(Date actualDate) {
        this.actualDate.setValue(actualDate);
    }

    public LocalDateTime getActualTime() {
        return actualTime.getValue();
    }

    public void setActualTime(LocalDateTime actualTime) {
        this.actualTime.setValue(actualTime);
    }
}
