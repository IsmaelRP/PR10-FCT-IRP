package com.practica.ismael.pr10_fct_irp.ui.meetings.listMeeting;

import android.app.Application;

import com.practica.ismael.pr10_fct_irp.R;
import com.practica.ismael.pr10_fct_irp.base.Event;
import com.practica.ismael.pr10_fct_irp.base.Resource;
import com.practica.ismael.pr10_fct_irp.data.Repository;
import com.practica.ismael.pr10_fct_irp.data.local.model.Company;
import com.practica.ismael.pr10_fct_irp.data.local.model.Meeting;
import com.practica.ismael.pr10_fct_irp.data.local.model.Student;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class MeetingsViewModel extends ViewModel {

    private Repository repository;
    private final Application application;
    private final MutableLiveData<Event<Meeting>> editTrigger = new MutableLiveData<>();
    private final LiveData<List<Meeting>> meetings;

    private LiveData<Resource<Integer>> deleteResult;
    private final MutableLiveData<Meeting> deleteTrigger = new MutableLiveData<>();

    private final MediatorLiveData<Event<String>> successMessage = new MediatorLiveData<>();
    private final MediatorLiveData<Event<String>> errorMessage = new MediatorLiveData<>();

    MeetingsViewModel(Application application, Repository repository) {
        this.application = application;
        this.repository = repository;
        deleteResult = Transformations.switchMap(deleteTrigger, repository::deleteMeeting);
        meetings = this.repository.queryMeetings();
        setupSuccessMessage();
        setupErrorMessage();
    }

    private void setupSuccessMessage() {
        successMessage.addSource(deleteResult, resource -> {
            if (resource.hasSuccess() && resource.getData() >= 0) {
                successMessage.setValue(new Event<>(application.getString(R.string.meeting_deleted_successfully)));
            }
        });
    }

    private void setupErrorMessage() {
        errorMessage.addSource(deleteResult, resource -> {
            if (resource.hasError()) {
                errorMessage.setValue(new Event<>(resource.getException().getMessage()));
            } else if (resource.hasSuccess() && resource.getData() <= 0) {
                errorMessage.setValue(new Event<>(application.getString(R.string.meeting_error_deleting)));
            }
        });
    }

    public LiveData<List<Meeting>> getMeetings() {
        return meetings;
    }

    void deleteMeeting(Meeting meeting) {
        deleteTrigger.setValue(meeting);
    }

    public void editMeeting(Meeting meeting) {
        this.editTrigger.setValue(new Event<>(meeting));
    }

    public MutableLiveData<Event<Meeting>> getEditTrigger() {
        return editTrigger;
    }

    public LiveData<Event<String>> getSuccessMessage() {
        return successMessage;
    }

    public LiveData<Event<String>> getErrorMessage() {
        return errorMessage;
    }
}
