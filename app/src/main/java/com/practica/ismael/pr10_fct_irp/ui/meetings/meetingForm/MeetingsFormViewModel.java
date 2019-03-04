package com.practica.ismael.pr10_fct_irp.ui.meetings.meetingForm;

import android.app.Application;

import com.practica.ismael.pr10_fct_irp.R;
import com.practica.ismael.pr10_fct_irp.base.Event;
import com.practica.ismael.pr10_fct_irp.base.Resource;
import com.practica.ismael.pr10_fct_irp.data.Repository;
import com.practica.ismael.pr10_fct_irp.data.local.model.Meeting;
import com.practica.ismael.pr10_fct_irp.data.local.model.Student;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MeetingsFormViewModel extends ViewModel {

    private Application application;
    private Repository repository;
    private LiveData<Meeting> meetingLiveData;
    private LiveData<Student> studentLiveData;
    private Event<Boolean> restoreData = new Event<>(false);
    private final MediatorLiveData<Event<String>> successMessage = new MediatorLiveData<>();
    private final MediatorLiveData<Event<String>> errorMessage = new MediatorLiveData<>();
    private LiveData<Resource<Integer>> updateResult = new MutableLiveData<>();
    private LiveData<Resource<Long>> insertResult;

    MeetingsFormViewModel(Application application, Repository repository) {
        this.application = application;
        this.repository = repository;
        setupSuccessMessage();
        setupErrorMessage();
    }

    private void setupSuccessMessage() {
        successMessage.addSource(updateResult, resource -> {
            if (resource.hasSuccess() && resource.getData() > 0) {
                successMessage.setValue(new Event<>(application.getString(R.string.meeting_updated_successfully)));
            }
        });
    }

    private void setupErrorMessage() {
        errorMessage.addSource(updateResult, resource -> {
            if (resource.hasError()) {
                errorMessage.setValue(new Event<>(resource.getException().getMessage()));
            } else if (resource.hasSuccess() && resource.getData() <= 0) {
                errorMessage.setValue(new Event<>(application.getString(R.string.meeting_error_updating)));
            }
        });
    }

    public LiveData<Meeting> getMeeting(long meetingId) {
        if (meetingLiveData == null) {
            meetingLiveData = repository.queryMeeting(meetingId);
            this.restoreData = new Event<>(true);
        }
        return meetingLiveData;
    }

    public LiveData<Student> getStudent(long studentId) {
        if (studentLiveData == null) {
            studentLiveData = repository.queryStudent(studentId);
            this.restoreData = new Event<>(true);
        }
        return studentLiveData;
    }

    public void updateMeeting(Meeting meeting) {
        updateResult = repository.updateMeeting(meeting);
    }

    public void insertMeeting(Meeting meeting) {
        insertResult = repository.insertMeeting(meeting);
    }

    public Event<Boolean> getRestoreData() {
        return restoreData;
    }
}
