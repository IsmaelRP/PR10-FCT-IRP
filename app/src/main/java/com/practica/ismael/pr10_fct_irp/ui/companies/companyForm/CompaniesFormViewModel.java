package com.practica.ismael.pr10_fct_irp.ui.companies.companyForm;

import android.app.Application;

import com.practica.ismael.pr10_fct_irp.R;
import com.practica.ismael.pr10_fct_irp.base.Event;
import com.practica.ismael.pr10_fct_irp.base.Resource;
import com.practica.ismael.pr10_fct_irp.data.Repository;
import com.practica.ismael.pr10_fct_irp.data.local.model.Company;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class CompaniesFormViewModel extends ViewModel {


    private Application application;
    private Repository repository;
    private LiveData<Company> companyLiveData;
    private Event<Boolean> restoreData = new Event<>(false);

    private final MediatorLiveData<Event<String>> successMessage = new MediatorLiveData<>();
    private final MediatorLiveData<Event<String>> errorMessage = new MediatorLiveData<>();

    private LiveData<Resource<Long>> insertResult;
    private LiveData<Resource<Integer>> updateResult;

    private final MutableLiveData<Company> insertTrigger = new MutableLiveData<>();
    private final MutableLiveData<Company> updateTrigger = new MutableLiveData<>();


    CompaniesFormViewModel(Application application, Repository repository) {
        this.application = application;
        this.repository = repository;
        insertResult = Transformations.switchMap(insertTrigger, repository::insertCompany);
        updateResult = Transformations.switchMap(updateTrigger, repository::updateCompany);
        setupSuccessMessage();
        setupErrorMessage();
    }

    private void setupSuccessMessage() {
        successMessage.addSource(insertResult, resource -> {
            if (resource.hasSuccess() && resource.getData() >= 0) {
                successMessage.setValue(new Event<>(application.getString(R.string.company_inserted_successfully)));
            }
        });
        successMessage.addSource(updateResult, resource -> {
            if (resource.hasSuccess() && resource.getData() > 0) {
                successMessage.setValue(new Event<>(application.getString(R.string.company_updated_successfully)));
            }
        });
    }

    private void setupErrorMessage() {
        errorMessage.addSource(insertResult, resource -> {
            if (resource.hasError()) {
                errorMessage.setValue(new Event<>(resource.getException().getMessage()));
            } else if (resource.hasSuccess() && resource.getData() <= 0) {
                errorMessage.setValue(new Event<>(application.getString(R.string.company_error_inserting)));
            }
        });
        errorMessage.addSource(updateResult, resource -> {
            if (resource.hasError()) {
                errorMessage.setValue(new Event<>(resource.getException().getMessage()));
            } else if (resource.hasSuccess() && resource.getData() <= 0) {
                errorMessage.setValue(new Event<>(application.getString(R.string.company_error_updating)));
            }
        });
    }

    public LiveData<Company> getCompany(long companyId) {
        if (companyLiveData == null) {
            companyLiveData = repository.queryCompany(companyId);
            this.restoreData = new Event<>(true);
        }
        return companyLiveData;
    }

    public LiveData<Event<String>> getSuccessMessage() {
        return successMessage;
    }

    public LiveData<Event<String>> getErrorMessage() {
        return errorMessage;
    }

    public void insertCompany(Company company) {
        insertTrigger.setValue(company);
    }

    public void updateCompany(Company company) {
        updateTrigger.setValue(company);
    }

    public Event<Boolean> getRestoreData() {
        return restoreData;
    }
}
