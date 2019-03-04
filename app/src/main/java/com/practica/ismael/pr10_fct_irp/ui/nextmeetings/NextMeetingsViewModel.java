package com.practica.ismael.pr10_fct_irp.ui.nextmeetings;

import android.app.Application;

import com.practica.ismael.pr10_fct_irp.base.Event;
import com.practica.ismael.pr10_fct_irp.data.Repository;
import com.practica.ismael.pr10_fct_irp.data.local.model.NextMeeting;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NextMeetingsViewModel extends ViewModel {

    private final Application application;
    private final LiveData<List<NextMeeting>> data;
    private final MutableLiveData<Event<NextMeeting>> addTrigger = new MutableLiveData<>();
    private Repository repository;

    NextMeetingsViewModel(Application application, Repository repository) {
        this.application = application;
        this.repository = repository;
        data = this.repository.queryNextMeetings();
    }

    public LiveData<List<NextMeeting>> getData() {
        return data;
    }

    public void addNextMeeting(NextMeeting item) {
        this.addTrigger.setValue(new Event<>(item));
    }

    public MutableLiveData<Event<NextMeeting>> getAddTrigger() {
        return addTrigger;
    }


}
