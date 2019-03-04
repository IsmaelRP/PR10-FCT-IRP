package com.practica.ismael.pr10_fct_irp.ui.meetings.listMeeting;

import android.app.Application;

import com.practica.ismael.pr10_fct_irp.data.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MeetingsViewModelFactory implements ViewModelProvider.Factory {

    private final Application application;
    private final Repository repository;

    public MeetingsViewModelFactory(@NonNull Application application, @NonNull Repository repository) {
        this.application = application;
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MeetingsViewModel.class)) {
            return (T) new MeetingsViewModel(application, repository);
        } else {
            throw new IllegalArgumentException("Wrong viewModel class");
        }
    }
}
