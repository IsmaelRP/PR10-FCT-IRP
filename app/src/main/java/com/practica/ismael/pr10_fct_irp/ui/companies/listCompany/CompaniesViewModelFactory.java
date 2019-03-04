package com.practica.ismael.pr10_fct_irp.ui.companies.listCompany;

import android.app.Application;

import com.practica.ismael.pr10_fct_irp.data.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CompaniesViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final Repository repository;

    public CompaniesViewModelFactory(@NonNull Application application, @NonNull Repository repository) {
        this.application = application;
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CompaniesViewModel.class)) {
            return (T) new CompaniesViewModel(application, repository);
        } else {
            throw new IllegalArgumentException("Wrong viewModel class");
        }
    }
}
