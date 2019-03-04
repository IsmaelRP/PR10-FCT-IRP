package com.practica.ismael.pr10_fct_irp.ui.students.listStudent;

import android.app.Application;

import com.practica.ismael.pr10_fct_irp.R;
import com.practica.ismael.pr10_fct_irp.base.Event;
import com.practica.ismael.pr10_fct_irp.base.Resource;
import com.practica.ismael.pr10_fct_irp.data.Repository;
import com.practica.ismael.pr10_fct_irp.data.local.model.Company;
import com.practica.ismael.pr10_fct_irp.data.local.model.Student;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class StudentsViewModel extends ViewModel {

    private final MutableLiveData<Event<Student>> editTrigger = new MutableLiveData<>();
    private final Application application;
    private final LiveData<List<Student>> students;
    private Repository repository;

    private LiveData<Resource<Integer>> deleteResult;

    private final MutableLiveData<Student> deleteTrigger = new MutableLiveData<>();

    private final MediatorLiveData<Event<String>> successMessage = new MediatorLiveData<>();
    private final MediatorLiveData<Event<String>> errorMessage = new MediatorLiveData<>();

    StudentsViewModel(Application application, Repository repository) {
        this.application = application;
        this.repository = repository;
        deleteResult = Transformations.switchMap(deleteTrigger, repository::deleteStudent);
        setupSuccessMessage();
        setupErrorMessage();
        students = this.repository.queryStudents();
    }

    private void setupSuccessMessage() {
        successMessage.addSource(deleteResult, resource -> {
            if (resource.hasSuccess() && resource.getData() >= 0) {
                successMessage.setValue(new Event<>(application.getString(R.string.student_deleted_successfully)));
            }
        });
    }

    private void setupErrorMessage() {
        errorMessage.addSource(deleteResult, resource -> {
            if (resource.hasError()) {
                errorMessage.setValue(new Event<>(resource.getException().getMessage()));
            } else if (resource.hasSuccess() && resource.getData() <= 0) {
                errorMessage.setValue(new Event<>(application.getString(R.string.student_error_deleting)));
            }
        });
    }

    public LiveData<List<Student>> getStudents() {
        return students;
    }

    void deleteStudent(Student student) {
        deleteTrigger.setValue(student);
    }

    public void editStudent(Student item) {
        this.editTrigger.setValue(new Event<>(item));
    }

    public MutableLiveData<Event<Student>> getEditTrigger() {
        return editTrigger;
    }

    public LiveData<Event<String>> getSuccessMessage() {
        return successMessage;
    }

    public LiveData<Event<String>> getErrorMessage() {
        return errorMessage;
    }
}
