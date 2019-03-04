package com.practica.ismael.pr10_fct_irp.ui.students.studentForm;

import android.app.Application;

import com.practica.ismael.pr10_fct_irp.R;
import com.practica.ismael.pr10_fct_irp.base.Event;
import com.practica.ismael.pr10_fct_irp.base.Resource;
import com.practica.ismael.pr10_fct_irp.data.Repository;
import com.practica.ismael.pr10_fct_irp.data.local.model.Company;
import com.practica.ismael.pr10_fct_irp.data.local.model.Student;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StudentsFormViewModel extends ViewModel {

    private Application application;
    private Repository repository;
    private LiveData<Student> studentLiveData;
    private LiveData<String> companyNameLiveData;
    private MutableLiveData<String> companyNameAux = new MutableLiveData<>();
    private Event<Boolean> restoreData = new Event<>(false);
    private final MediatorLiveData<Event<String>> successMessage = new MediatorLiveData<>();
    private final MediatorLiveData<Event<String>> errorMessage = new MediatorLiveData<>();
    private LiveData<Resource<Long>> insertResult = new MutableLiveData<>();
    private LiveData<Resource<Integer>> updateResult = new MutableLiveData<>();


    StudentsFormViewModel(Application application, Repository repository) {
        this.application = application;
        this.repository = repository;
        setupSuccessMessage();
        setupErrorMessage();
    }

    private void setupSuccessMessage() {
        successMessage.addSource(insertResult, resource -> {
            if (resource.hasSuccess() && resource.getData() >= 0) {
                successMessage.setValue(new Event<>(application.getString(R.string.student_inserted_successfully)));
            }
        });
        successMessage.addSource(updateResult, resource -> {
            if (resource.hasSuccess() && resource.getData() > 0) {
                successMessage.setValue(new Event<>(application.getString(R.string.student_updated_successfully)));
            }
        });
    }

    private void setupErrorMessage() {
        errorMessage.addSource(insertResult, resource -> {
            if (resource.hasError()) {
                errorMessage.setValue(new Event<>(resource.getException().getMessage()));
            } else if (resource.hasSuccess() && resource.getData() <= 0) {
                errorMessage.setValue(new Event<>(application.getString(R.string.student_error_inserting)));
            }
        });
        errorMessage.addSource(updateResult, resource -> {
            if (resource.hasError()) {
                errorMessage.setValue(new Event<>(resource.getException().getMessage()));
            } else if (resource.hasSuccess() && resource.getData() <= 0) {
                errorMessage.setValue(new Event<>(application.getString(R.string.student_error_updating)));
            }
        });
    }

    public LiveData<Student> getStudent(long studentId) {
        if (studentLiveData == null) {
            studentLiveData = repository.queryStudent(studentId);
            this.restoreData = new Event<>(true);
        }
        return studentLiveData;
    }

    public void insertStudent(Student student) {
        insertResult = repository.insertStudent(student);
    }

    public void updateStudent(Student student) {
        updateResult = repository.updateStudent(student);
    }

    public Event<Boolean> getRestoreData() {
        return restoreData;
    }

    public MutableLiveData<String> getCompanyNameAux() {
        return companyNameAux;
    }

    public void setCompanyNameAux(String name) {
        this.companyNameAux.setValue(name);
    }
}
