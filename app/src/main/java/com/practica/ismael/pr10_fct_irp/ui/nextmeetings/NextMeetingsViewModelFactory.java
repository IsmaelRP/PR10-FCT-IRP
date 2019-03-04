package com.practica.ismael.pr10_fct_irp.ui.nextmeetings;

import android.app.Application;

import com.practica.ismael.pr10_fct_irp.data.Repository;
import com.practica.ismael.pr10_fct_irp.ui.companies.listCompany.CompaniesViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class NextMeetingsViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;
    private final Repository repository;

    public NextMeetingsViewModelFactory(@NonNull Application application, @NonNull Repository repository) {
        this.application = application;
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NextMeetingsViewModel.class)) {
            return (T) new NextMeetingsViewModel(application, repository);
        } else {
            throw new IllegalArgumentException("Wrong viewModel class");
        }
    }
}
