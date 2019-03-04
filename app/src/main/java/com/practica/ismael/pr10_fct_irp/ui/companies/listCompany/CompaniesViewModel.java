package com.practica.ismael.pr10_fct_irp.ui.companies.listCompany;

import android.app.Application;

import com.practica.ismael.pr10_fct_irp.R;
import com.practica.ismael.pr10_fct_irp.base.Event;
import com.practica.ismael.pr10_fct_irp.base.Resource;
import com.practica.ismael.pr10_fct_irp.data.Repository;
import com.practica.ismael.pr10_fct_irp.data.local.model.Company;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class CompaniesViewModel extends ViewModel {

    private final MutableLiveData<Event<Company>> editTrigger = new MutableLiveData<>();
    private final Application application;
    private final LiveData<List<Company>> companies;
    private Repository repository;

    private final MutableLiveData<Company> deleteTrigger = new MutableLiveData<>();

    private LiveData<Resource<Integer>> deleteResult;

    private final MediatorLiveData<Event<String>> successMessage = new MediatorLiveData<>();
    private final MediatorLiveData<Event<String>> errorMessage = new MediatorLiveData<>();

    CompaniesViewModel(Application application, Repository repository) {
        this.application = application;
        this.repository = repository;
        deleteResult = Transformations.switchMap(deleteTrigger, repository::deleteCompany);
        companies = this.repository.queryCompanies();
        setupSuccessMessage();
        setupErrorMessage();
    }

    private void setupSuccessMessage() {
        successMessage.addSource(deleteResult, resource -> {
            if (resource.hasSuccess() && resource.getData() >= 0) {
                successMessage.setValue(new Event<>(application.getString(R.string.company_deleted_successfully)));
            }
        });
    }

    private void setupErrorMessage() {
        errorMessage.addSource(deleteResult, resource -> {
            if (resource.hasError()) {
                errorMessage.setValue(new Event<>(resource.getException().getMessage()));
            } else if (resource.hasSuccess() && resource.getData() <= 0) {
                errorMessage.setValue(new Event<>(application.getString(R.string.company_error_deleting)));
            }
        });
    }

    public LiveData<List<Company>> getCompanies() {
        return companies;
    }

    void deleteCompany(Company company) {
        deleteTrigger.setValue(company);
    }

    public void editCompany(Company item) {
        this.editTrigger.setValue(new Event<>(item));
    }

    public LiveData<Event<String>> getSuccessMessage() {
        return successMessage;
    }

    public LiveData<Event<String>> getErrorMessage() {
        return errorMessage;
    }

    public MutableLiveData<Event<Company>> getEditTrigger() {
        return editTrigger;
    }
}
