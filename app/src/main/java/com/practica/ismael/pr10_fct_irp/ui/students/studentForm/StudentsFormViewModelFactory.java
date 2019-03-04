package com.practica.ismael.pr10_fct_irp.ui.students.studentForm;

import android.app.Application;

import com.practica.ismael.pr10_fct_irp.data.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class StudentsFormViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;
    private final Repository repository;

    public StudentsFormViewModelFactory(Application application, Repository repository) {
        this.application = application;
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(StudentsFormViewModel.class)) {
            return (T) new StudentsFormViewModel(application, repository);
        } else {
            throw new IllegalArgumentException("Wrong viewModel class");
        }
    }
}
