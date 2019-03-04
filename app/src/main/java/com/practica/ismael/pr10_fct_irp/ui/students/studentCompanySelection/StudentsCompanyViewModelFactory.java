package com.practica.ismael.pr10_fct_irp.ui.students.studentCompanySelection;

import android.app.Application;

import com.practica.ismael.pr10_fct_irp.data.Repository;
import com.practica.ismael.pr10_fct_irp.ui.companies.listCompany.CompaniesViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class StudentsCompanyViewModelFactory implements ViewModelProvider.Factory{

    private final Application application;
    private final Repository repository;

    public StudentsCompanyViewModelFactory(@NonNull Application application, @NonNull Repository repository) {
        this.application = application;
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(StudentsCompanyViewModel.class)) {
            return (T) new StudentsCompanyViewModel(application, repository);
        } else {
            throw new IllegalArgumentException("Wrong viewModel class");
        }
    }
}
